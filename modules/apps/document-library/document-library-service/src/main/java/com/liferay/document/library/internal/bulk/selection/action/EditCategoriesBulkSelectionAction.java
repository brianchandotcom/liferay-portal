/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.internal.bulk.selection.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "bulk.selection.action.key=edit.categories",
	service = BulkSelectionAction.class
)
public class EditCategoriesBulkSelectionAction
	implements BulkSelectionAction<AssetEntry> {

	@Override
	public void execute(
			User user, BulkSelection<AssetEntry> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		Set<Long> toAddCategoryIdsSet = _toLongSet(
			inputMap, "toAddCategoryIds");

		Set<Long> toRemoveCategoryIdsSet = _toLongSet(
			inputMap, "toRemoveCategoryIds");

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		bulkSelection.forEach(
			assetEntry -> {
				try {
					if (!BaseModelPermissionCheckerUtil.
							containsBaseModelPermission(
								permissionChecker, assetEntry.getGroupId(),
								assetEntry.getClassName(),
								assetEntry.getClassPK(), ActionKeys.UPDATE)) {

						return;
					}

					long[] newCategoryIds = new long[0];

					if (SetUtil.isNotEmpty(toAddCategoryIdsSet)) {
						newCategoryIds = _getLongArray(
							inputMap, "toAddCategoryIds");
					}

					if (MapUtil.getBoolean(inputMap, "append")) {
						Set<Long> currentCategoryIdsSet = SetUtil.fromArray(
							assetEntry.getCategoryIds());

						currentCategoryIdsSet.removeAll(toRemoveCategoryIdsSet);

						currentCategoryIdsSet.addAll(toAddCategoryIdsSet);

						newCategoryIds = ArrayUtil.toLongArray(
							currentCategoryIdsSet);
					}

					_assetEntryLocalService.updateEntry(
						assetEntry.getUserId(), assetEntry.getGroupId(),
						assetEntry.getClassName(), assetEntry.getClassPK(),
						newCategoryIds, assetEntry.getTagNames());
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}
				}
			});
	}

	private long[] _getLongArray(Map<String, Serializable> map, String key) {
		return ArrayUtil.toArray((Long[])map.getOrDefault(key, new Long[0]));
	}

	private Set<Long> _toLongSet(
		Map<String, Serializable> inputMap, String key) {

		try {
			return SetUtil.fromArray(
				(Long[])inputMap.getOrDefault(key, new Long[0]));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return SetUtil.fromArray(new Long[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCategoriesBulkSelectionAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}