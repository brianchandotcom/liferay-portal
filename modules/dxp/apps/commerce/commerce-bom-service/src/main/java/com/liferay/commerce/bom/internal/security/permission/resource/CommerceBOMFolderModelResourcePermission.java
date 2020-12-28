/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.internal.security.permission.resource;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.permission.CommerceBOMFolderPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.bom.model.CommerceBOMFolder",
	service = ModelResourcePermission.class
)
public class CommerceBOMFolderModelResourcePermission
	implements ModelResourcePermission<CommerceBOMFolder> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceBOMFolder commerceBOMFolder, String actionId)
		throws PortalException {

		commerceBOMFolderPermission.check(
			permissionChecker, commerceBOMFolder, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceBOMFolderId,
			String actionId)
		throws PortalException {

		commerceBOMFolderPermission.check(
			permissionChecker, commerceBOMFolderId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceBOMFolder commerceBOMFolder, String actionId)
		throws PortalException {

		return commerceBOMFolderPermission.contains(
			permissionChecker, commerceBOMFolder, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceBOMFolderId,
			String actionId)
		throws PortalException {

		return commerceBOMFolderPermission.contains(
			permissionChecker, commerceBOMFolderId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceBOMFolder.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceBOMFolderPermission commerceBOMFolderPermission;

	@Reference(target = "(resource.name=com.liferay.commerce.bom)")
	private PortletResourcePermission _portletResourcePermission;

}