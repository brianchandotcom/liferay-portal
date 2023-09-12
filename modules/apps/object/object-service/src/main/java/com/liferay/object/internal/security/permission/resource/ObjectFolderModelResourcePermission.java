/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectFolder",
	service = ModelResourcePermission.class
)
public class ObjectFolderModelResourcePermission
	implements ModelResourcePermission<ObjectFolder> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectFolderId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectFolderId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectFolder.class.getName(), objectFolderId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, ObjectFolder objectFolder,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectFolder, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectFolder.class.getName(),
				objectFolder.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectFolderId,
			String actionId)
		throws PortalException {

		ObjectFolder objectFolder;

		if (objectFolderId == 0) {
			objectFolder =
				_objectFolderLocalService.addOrGetUncategorizedObjectFolder(
					permissionChecker.getCompanyId());
		}
		else {
			objectFolder = _objectFolderLocalService.getObjectFolder(
				objectFolderId);
		}

		return contains(permissionChecker, objectFolder, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, ObjectFolder objectFolder,
			String actionId)
		throws PortalException {

		if (objectFolder.isUncategorized() ||
			permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), ObjectFolder.class.getName(),
				objectFolder.getPrimaryKey(), objectFolder.getUserId(),
				actionId) ||
			permissionChecker.hasPermission(
				null, ObjectFolder.class.getName(),
				objectFolder.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return ObjectFolder.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}