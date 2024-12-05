/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = ModelResourcePermission.class
)
public class ObjectEntryFolderModelResourcePermission
	implements ModelResourcePermission<ObjectEntryFolder> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectEntryFolderId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntryFolderId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectEntryFolder.class.getName(),
				objectEntryFolderId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			ObjectEntryFolder objectEntryFolder, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntryFolder, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectEntryFolder.class.getName(),
				objectEntryFolder.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectEntryFolderId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			ObjectEntryFolder objectEntryFolder, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				ObjectEntryFolder.class.getName(),
				objectEntryFolder.getPrimaryKey(),
				objectEntryFolder.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				objectEntryFolder.getGroupId(),
				ObjectEntryFolder.class.getName(),
				objectEntryFolder.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return ObjectEntryFolder.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}