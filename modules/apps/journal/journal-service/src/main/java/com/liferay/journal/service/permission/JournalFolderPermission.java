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

package com.liferay.journal.service.permission;

import com.liferay.journal.exception.NoSuchFolderException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalFolder"},
	service = BaseModelPermissionChecker.class
)
public class JournalFolderPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, folder, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalFolder.class.getName(),
				folder.getFolderId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalFolder.class.getName(), folderId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException {

		String portletId = PortletProviderUtil.getPortletId(
			JournalArticle.class.getName(), PortletProvider.Action.EDIT);

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, folder.getGroupId(),
			JournalFolder.class.getName(), folder.getFolderId(), portletId,
			actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			try {
				long folderId = folder.getFolderId();

				while (folderId !=
							JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					folder = _journalFolderLocalService.getFolder(folderId);

					if (!_hasPermission(permissionChecker, folder, actionId)) {
						return false;
					}

					folderId = folder.getParentFolderId();
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!folder.isInTrash()) {
					throw nsfe;
				}
			}

			return JournalPermission.contains(
				permissionChecker, folder.getGroupId(), actionId);
		}

		return _hasPermission(permissionChecker, folder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return JournalPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			JournalFolder folder = _journalFolderLocalService.getJournalFolder(
				folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, groupId, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, JournalFolder folder,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				folder.getCompanyId(), JournalFolder.class.getName(),
				folder.getFolderId(), folder.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				folder.getGroupId(), JournalFolder.class.getName(),
				folder.getFolderId(), actionId)) {

			return true;
		}

		return false;
	}

	private static JournalFolderLocalService _journalFolderLocalService;

}