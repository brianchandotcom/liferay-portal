/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.model.ObjectEntry;
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

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			GetterUtil.getLong(inputMap.get("bulkActionTaskId")));

		Map<String, Serializable> values = objectEntry.getValues();

		values.put("numberOfItems", bulkSelection.getSize());

		String executionStatus = "completed";
		AtomicInteger numberOfFailedItems = new AtomicInteger(0);
		AtomicInteger numberOfSuccessfulItems = new AtomicInteger(0);

		Set<Long> toAddCategoryIds = _toLongSet(inputMap, "toAddCategoryIds");
		Set<Long> toRemoveCategoryIds = _toLongSet(
			inputMap, "toRemoveCategoryIds");

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try {
			values.put("executionStatus", "started");

			bulkSelection.forEach(
				object -> {
					try {
						if (object instanceof ObjectEntry) {
							ObjectEntry objectObjectEntry = (ObjectEntry)object;

							AssetEntry assetEntry =
								_assetEntryLocalService.fetchEntry(
									objectObjectEntry.getModelClassName(),
									objectObjectEntry.getObjectEntryId());

							if (!ModelResourcePermissionUtil.contains(
									permissionChecker, assetEntry.getGroupId(),
									assetEntry.getClassName(),
									assetEntry.getClassPK(),
									ActionKeys.UPDATE)) {

								return;
							}

							long[] newCategoryIds = new long[0];

							if (SetUtil.isNotEmpty(toAddCategoryIds)) {
								newCategoryIds = ArrayUtil.toLongArray(
									toAddCategoryIds);
							}

							if (MapUtil.getBoolean(inputMap, "append")) {
								Set<Long> currentCategoryIds =
									SetUtil.fromArray(
										assetEntry.getCategoryIds());

								currentCategoryIds.removeAll(
									toRemoveCategoryIds);

								currentCategoryIds.addAll(toAddCategoryIds);

								newCategoryIds = ArrayUtil.toLongArray(
									currentCategoryIds);
							}

							_assetEntryLocalService.updateEntry(
								assetEntry.getUserId(), assetEntry.getGroupId(),
								assetEntry.getClassName(),
								assetEntry.getClassPK(), newCategoryIds,
								assetEntry.getTagNames());
						}
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}
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

	private static final Log _log = LogFactoryUtil.getLog(
		EditObjectCategoriesBulkSelectionAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}