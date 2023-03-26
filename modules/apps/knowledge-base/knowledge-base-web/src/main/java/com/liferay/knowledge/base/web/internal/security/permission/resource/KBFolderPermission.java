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

package com.liferay.knowledge.base.web.internal.security.permission.resource;

import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Preston Crary
 */
public class KBFolderPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, KBFolder kbFolder,
			String actionId)
		throws PortalException {

		ModelResourcePermission<KBFolder> modelResourcePermission =
			_kbFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, kbFolder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long kbFolderId,
			String actionId)
		throws PortalException {

		if (kbFolderId == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}

			PortletResourcePermission portletResourcePermission =
				_portletResourcePermissionSnapshot.get();

			return portletResourcePermission.contains(
				permissionChecker, groupId, actionId);
		}

		ModelResourcePermission<KBFolder> modelResourcePermission =
			_kbFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, kbFolderId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<KBFolder>>
		_kbFolderModelResourcePermissionSnapshot = new Snapshot<>(
			KBFolderPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.knowledge.base.model.KBFolder)");
	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			KBFolderPermission.class, PortletResourcePermission.class,
			"(resource.name=" + KBConstants.RESOURCE_NAME_ADMIN + ")");

}