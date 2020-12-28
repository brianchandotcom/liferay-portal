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

import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.permission.CommerceApplicationBrandPermission;
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
	property = "model.class.name=com.liferay.commerce.application.model.CommerceApplicationBrand",
	service = ModelResourcePermission.class
)
public class CommerceApplicationBrandModelResourcePermission
	implements ModelResourcePermission<CommerceApplicationBrand> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceApplicationBrand commerceApplicationBrand, String actionId)
		throws PortalException {

		commerceApplicationBrandPermission.check(
			permissionChecker, commerceApplicationBrand, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceApplicationBrandId, String actionId)
		throws PortalException {

		commerceApplicationBrandPermission.check(
			permissionChecker, commerceApplicationBrandId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceApplicationBrand commerceApplicationBrand, String actionId)
		throws PortalException {

		return commerceApplicationBrandPermission.contains(
			permissionChecker, commerceApplicationBrand, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceApplicationBrandId, String actionId)
		throws PortalException {

		return commerceApplicationBrandPermission.contains(
			permissionChecker, commerceApplicationBrandId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceApplicationBrand.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceApplicationBrandPermission
		commerceApplicationBrandPermission;

	@Reference(target = "(resource.name=com.liferay.commerce.application)")
	private PortletResourcePermission _portletResourcePermission;

}