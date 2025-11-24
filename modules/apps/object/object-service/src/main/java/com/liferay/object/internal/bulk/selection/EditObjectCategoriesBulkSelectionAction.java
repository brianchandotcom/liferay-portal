/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "bulk.selection.action.key=edit.object.categories",
	service = BulkSelectionAction.class
)
public class EditObjectCategoriesBulkSelectionAction
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

			long companyId = objectEntry.getCompanyId();

			bulkSelection.forEach(
				object -> {
					long objectDefinitionId = _getObjectDefinitionId(companyId);
					String status = "completed";

					try {
						if (object instanceof ObjectEntry) {
							_updateAssetEntry(
								user, inputMap, (ObjectEntry)object);

							numberOfSuccessfulItems.getAndIncrement();
						}
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}

						numberOfFailedItems.getAndIncrement();
						status = "failed";
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
								"r_cmsBATaskToCMSBATaskItems_c_cmsBulkAct" +
									"ionTaskId",
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

	private ObjectEntry _partialUpdateObjectEntry(
			ObjectEntry objectEntry, Map<String, Serializable> values)
		throws PortalException {

		return _objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getObjectEntryFolderId(), values, new ServiceContext());
	}

	private Set<Long> _toLongSet(Map<String, Serializable> map, String key) {
		try {
			Serializable values = map.get(key);

			if (values instanceof Long[]) {
				return SetUtil.fromArray((Long[])values);
			}

			Set<Long> set = new HashSet<>();

			for (Integer value : (Integer[])values) {
				set.add(value.longValue());
			}

			return set;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return SetUtil.fromArray(new Long[0]);
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
			!ModelResourcePermissionUtil.contains(
				permissionChecker, assetEntry.getGroupId(),
				assetEntry.getClassName(), assetEntry.getClassPK(),
				ActionKeys.UPDATE)) {

			return;
		}

		long[] newCategoryIds = new long[0];

		Set<Long> toAddCategoryIds = _toLongSet(inputMap, "toAddCategoryIds");

		if (SetUtil.isNotEmpty(toAddCategoryIds)) {
			newCategoryIds = ArrayUtil.toLongArray(toAddCategoryIds);
		}

		if (MapUtil.getBoolean(inputMap, "append")) {
			Set<Long> currentCategoryIds = SetUtil.fromArray(
				assetEntry.getCategoryIds());

			Set<Long> toRemoveCategoryIds = _toLongSet(
				inputMap, "toRemoveCategoryIds");

			currentCategoryIds.removeAll(toRemoveCategoryIds);

			currentCategoryIds.addAll(toAddCategoryIds);

			newCategoryIds = ArrayUtil.toLongArray(currentCategoryIds);
		}

		_assetEntryLocalService.updateEntry(
			assetEntry.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(), newCategoryIds,
			assetEntry.getTagNames());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditObjectCategoriesBulkSelectionAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}