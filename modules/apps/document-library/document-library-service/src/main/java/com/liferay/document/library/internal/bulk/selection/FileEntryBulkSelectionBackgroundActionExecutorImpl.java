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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.document.library.bulk.selection.FileEntryBulkSelectionBackgroundActionExecutor;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class FileEntryBulkSelectionBackgroundActionExecutorImpl
	implements FileEntryBulkSelectionBackgroundActionExecutor {

	public FileEntryBulkSelectionBackgroundActionExecutorImpl(
		BulkSelection<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor>
			bulkSelection,
		PermissionChecker permissionChecker,
		ModelResourcePermission<FileEntry> fileEntryModelResourcePermission,
		AssetEntryLocalService assetEntryLocalService) {

		_bulkSelection = bulkSelection;
		_permissionChecker = permissionChecker;
		_fileEntryModelResourcePermission = fileEntryModelResourcePermission;
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public void updateFileEntryTags(
		String[] toAddTagNames, String[] toRemoveTagNames, boolean append) {

		try {
			Set<String> toAddTagNamesSet = SetUtil.fromArray(toAddTagNames);

			Set<String> toRemoveTagNamesSet = SetUtil.fromArray(
				toRemoveTagNames);

			Stream<FileEntry> fileEntryStream = _bulkSelection.stream();

			fileEntryStream.forEach(
				fileEntry -> {
					try {
						if (!_fileEntryModelResourcePermission.contains(
								_permissionChecker, fileEntry,
								ActionKeys.UPDATE)) {

							return;
						}

						AssetEntry assetEntry =
							_assetEntryLocalService.fetchEntry(
								DLFileEntryConstants.getClassName(),
								fileEntry.getFileEntryId());

						String[] newTagNames = toAddTagNames;

						if (append) {
							Set<String> currentTagNamesSet = SetUtil.fromArray(
								assetEntry.getTagNames());

							currentTagNamesSet.removeAll(toRemoveTagNamesSet);
							currentTagNamesSet.addAll(toAddTagNamesSet);

							newTagNames = currentTagNamesSet.toArray(
								new String[currentTagNamesSet.size()]);
						}

						_assetEntryLocalService.updateEntry(
							assetEntry.getUserId(), assetEntry.getGroupId(),
							assetEntry.getClassName(), assetEntry.getClassPK(),
							assetEntry.getCategoryIds(), newTagNames);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(pe, pe);
						}
					}
				});
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryBulkSelectionBackgroundActionExecutorImpl.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BulkSelection
		<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor>
			_bulkSelection;
	private final ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;
	private final PermissionChecker _permissionChecker;

}