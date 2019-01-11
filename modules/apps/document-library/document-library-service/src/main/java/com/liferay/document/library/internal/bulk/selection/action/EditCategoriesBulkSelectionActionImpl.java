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
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	service = {
		BulkSelectionAction.class, EditCategoriesBulkSelectionAction.class,
		EditCategoriesBulkSelectionActionImpl.class
	}
)
public class EditCategoriesBulkSelectionActionImpl
	implements EditCategoriesBulkSelectionAction {

	@Override
	public void execute(
			User user, BulkSelection<FileEntry> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		long[] toAddCategoryIds = (long[])inputMap.getOrDefault(
			"toAddCategoryIds", new long[0]);

		Set<Long> toAddCategoryIdsSet = SetUtil.fromArray(toAddCategoryIds);

		Set<Long> toRemoveCategoryIdsSet = SetUtil.fromArray(
			(long[])inputMap.getOrDefault("toRemoveCategoryIds", new long[0]));

		Stream<FileEntry> fileEntryStream = bulkSelection.stream();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		fileEntryStream.forEach(
			fileEntry -> {
				try {
					if (!_fileEntryModelResourcePermission.contains(
							permissionChecker, fileEntry, ActionKeys.UPDATE)) {

						return;
					}

					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId());

					long[] newCategoryIds = toAddCategoryIds;

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
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe, pe);
					}
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCategoriesBulkSelectionActionImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

}