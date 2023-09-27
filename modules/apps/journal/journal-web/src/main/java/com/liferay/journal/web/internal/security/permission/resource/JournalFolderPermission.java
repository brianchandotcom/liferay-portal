/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.security.permission.resource;

import com.liferay.journal.model.JournalFolder;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;

/**
 * @author Preston Crary
 */
public class JournalFolderPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException {

		ModelResourcePermission<JournalFolder> modelResourcePermission =
			_journalFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, folder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<JournalFolder> modelResourcePermission =
			_journalFolderModelResourcePermissionSnapshot.get();

		return ModelResourcePermissionUtil.contains(
			modelResourcePermission, permissionChecker, groupId, folderId,
			actionId);
	}

	private static final Snapshot<ModelResourcePermission<JournalFolder>>
		_journalFolderModelResourcePermissionSnapshot = new Snapshot<>(
			JournalFolderPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.journal.model.JournalFolder)");

}