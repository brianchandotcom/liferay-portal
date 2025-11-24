/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "bulk.selection.action.key=edit.object.tags",
	service = BulkSelectionAction.class
)
public class EditObjectTagsBulkSelectionAction
	implements BulkSelectionAction<Object> {

	@Override
	public void execute(
			User user, BulkSelection<Object> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		long bulkActionTaskId = GetterUtil.getLong(
			inputMap.get("bulkActionTaskId"));

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			bulkActionTaskId);

		Map<String, Serializable> values = objectEntry.getValues();

		values.put("numberOfItems", bulkSelection.getSize());

		String executionStatus = "completed";
		AtomicInteger numberOfFailedItems = new AtomicInteger(0);
		AtomicInteger numberOfSuccessfulItems = new AtomicInteger(0);

		try {
			values.put("executionStatus", "started");

			objectEntry = _partialUpdateObjectEntry(objectEntry, values);

			values = objectEntry.getValues();

			long companyId = objectEntry.getCompanyId();

			bulkSelection.forEach(
				object -> {
					long objectDefinitionId = _getObjectDefinitionId(companyId);
					String status = "completed";

					try {
						if (object instanceof ObjectEntry) {
							_updateAssetEntry(
								user, inputMap, (ObjectEntry)object);
						}

						numberOfSuccessfulItems.getAndIncrement();
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}

						numberOfFailedItems.getAndIncrement();
					}
					finally {
						_objectEntryLocalService.addObjectEntry(
							0, user.getUserId(), objectDefinitionId,
							ObjectEntryFolderConstants.
								PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
							null,
							HashMapBuilder.<String, Serializable>put(
								"bulkActionTaskId", bulkActionTaskId
							).put(
								"executionStatus", status
							).put(
								"r_cmsBATaskToCMSBATaskItems_c_cmsBulkActionT" +
									"askId",
								bulkActionTaskId
							).put(
								"type", "ObjectEntryFolder"
							).build(),
							new ServiceContext());
					}
				});
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			executionStatus = "failed";
		}
		finally {
			values.put("completionDate", new Date());
			values.put("executionStatus", executionStatus);
			values.put("numberOfFailedItems", numberOfFailedItems.get());
			values.put(
				"numberOfSuccessfulItems", numberOfSuccessfulItems.get());

			_partialUpdateObjectEntry(objectEntry, values);
		}
	}

	private long _getObjectDefinitionId(long companyId) throws PortalException {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BULK_ACTION_TASK_ITEM", companyId);

		return objectDefinition.getObjectDefinitionId();
	}

	private boolean _hasEditPermission(
			AssetEntry assetEntry, PermissionChecker permissionChecker)
		throws PortalException {

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer != null) {
			return assetRenderer.hasEditPermission(permissionChecker);
		}

		return ModelResourcePermissionUtil.contains(
			permissionChecker, assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			ActionKeys.UPDATE);
	}

	private ObjectEntry _partialUpdateObjectEntry(
			ObjectEntry objectEntry, Map<String, Serializable> values)
		throws PortalException {

		return _objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getObjectEntryFolderId(), values, new ServiceContext());
	}

	private Set<String> _toStringSet(
		Map<String, Serializable> map, String key) {

		try {
			return SetUtil.fromArray(
				(String[])map.getOrDefault(key, new String[0]));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return SetUtil.fromArray(new String[0]);
	}

	private void _updateAssetEntry(
			User user, Map<String, Serializable> inputMap,
			ObjectEntry objectEntry)
		throws PortalException {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			objectEntry.getModelClassName(), objectEntry.getObjectEntryId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		if ((assetEntry == null) ||
			!_hasEditPermission(assetEntry, permissionChecker)) {

			return;
		}

		String[] newTagNames = new String[0];

		Set<String> toAddTagNames = _toStringSet(inputMap, "toAddTagNames");

		if (SetUtil.isNotEmpty(toAddTagNames)) {
			newTagNames = (String[])inputMap.get("toAddTagNames");
		}

		if (MapUtil.getBoolean(inputMap, "append")) {
			Set<String> currentTagNames = SetUtil.fromArray(
				assetEntry.getTagNames());

			Set<String> toRemoveTagNames = _toStringSet(
				inputMap, "toRemoveTagNames");

			currentTagNames.removeAll(toRemoveTagNames);

			currentTagNames.addAll(toAddTagNames);

			currentTagNames.removeIf(
				tagName -> !_assetHelper.isValidWord(tagName));

			newTagNames = currentTagNames.toArray(new String[0]);
		}

		_assetEntryLocalService.updateEntry(
			assetEntry.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			assetEntry.getCategoryIds(), newTagNames);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditObjectTagsBulkSelectionAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}