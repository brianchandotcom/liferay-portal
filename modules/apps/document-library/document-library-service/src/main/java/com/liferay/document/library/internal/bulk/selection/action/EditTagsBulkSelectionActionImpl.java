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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portlet.asset.util.AssetUtil;

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
			User user, BulkSelection<AssetEntry> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		String[] toAddTagNames = (String[])inputMap.getOrDefault(
			"toAddTagNames", new String[0]);

		Set<String> toAddTagNamesSet = SetUtil.fromArray(toAddTagNames);

		Set<String> toRemoveTagNamesSet = SetUtil.fromArray(
			(String[])inputMap.getOrDefault("toRemoveTagNames", new String[0]));

		Stream<AssetEntry> assetEntryStream = bulkSelection.stream();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		assetEntryStream.forEach(
			assetEntry -> {
				try {
					if (!BaseModelPermissionCheckerUtil.
							containsBaseModelPermission(
								permissionChecker, assetEntry.getGroupId(),
								assetEntry.getClassName(),
								assetEntry.getClassPK(), ActionKeys.UPDATE)) {

						return;
					}

					String[] newTagNames = toAddTagNames;

					if (MapUtil.getBoolean(inputMap, "append")) {
						Set<String> currentTagNamesSet = SetUtil.fromArray(
							assetEntry.getTagNames());

						currentTagNamesSet.removeAll(toRemoveTagNamesSet);

						currentTagNamesSet.addAll(toAddTagNamesSet);

						currentTagNamesSet.removeIf(
							tagName -> !AssetUtil.isValidWord(tagName));

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

}