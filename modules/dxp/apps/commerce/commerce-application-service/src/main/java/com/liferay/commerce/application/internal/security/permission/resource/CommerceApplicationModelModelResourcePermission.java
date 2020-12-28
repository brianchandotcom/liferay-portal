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

package com.liferay.commerce.application.internal.security.permission.resource;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.permission.CommerceApplicationModelPermission;
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
	property = "model.class.name=com.liferay.commerce.application.model.CommerceApplicationModel",
	service = ModelResourcePermission.class
)
public class CommerceApplicationModelModelResourcePermission
	implements ModelResourcePermission<CommerceApplicationModel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceApplicationModel commerceApplicationModel, String actionId)
		throws PortalException {

		commerceApplicationModelPermission.check(
			permissionChecker, commerceApplicationModel, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceApplicationModelId, String actionId)
		throws PortalException {

		commerceApplicationModelPermission.check(
			permissionChecker, commerceApplicationModelId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceApplicationModel commerceApplicationModel, String actionId)
		throws PortalException {

		return commerceApplicationModelPermission.contains(
			permissionChecker, commerceApplicationModel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceApplicationModelId, String actionId)
		throws PortalException {

		return commerceApplicationModelPermission.contains(
			permissionChecker, commerceApplicationModelId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceApplicationModel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceApplicationModelPermission
		commerceApplicationModelPermission;

	@Reference(target = "(resource.name=com.liferay.commerce.application)")
	private PortletResourcePermission _portletResourcePermission;

}