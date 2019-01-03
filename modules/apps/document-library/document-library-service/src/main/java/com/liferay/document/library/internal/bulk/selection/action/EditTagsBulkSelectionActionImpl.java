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
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	service = {
		BulkSelectionAction.class, EditTagsBulkSelectionAction.class,
		EditTagsBulkSelectionActionImpl.class
	}
)
public class EditTagsBulkSelectionActionImpl
	implements EditTagsBulkSelectionAction {

	@Override
	public void execute(
			BulkSelection<FileEntry> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		String[] toAddTagNames = (String[])inputMap.getOrDefault(
			"toAddTagNames", new String[0]);

		Set<String> toAddTagNamesSet = SetUtil.fromArray(toAddTagNames);

		Set<String> toRemoveTagNamesSet = SetUtil.fromArray(
			(String[])inputMap.getOrDefault("toRemoveTagNames", new String[0]));

		Stream<FileEntry> fileEntryStream = bulkSelection.stream();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(
				_userLocalService.getUser(MapUtil.getLong(inputMap, "userId")));

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

					String[] newTagNames = toAddTagNames;

					if (MapUtil.getBoolean(inputMap, "append")) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		EditTagsBulkSelectionActionImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}