/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.permission.CommerceOrderTypePermission;
import com.liferay.commerce.service.CommerceOrderTypeRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderTypeRel",
	service = ModelResourcePermission.class
)
public class CommerceOrderTypeRelModelResourcePermission
	implements ModelResourcePermission<CommerceOrderTypeRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderTypeRel commerceOrderTypeRel, String actionId)
		throws PortalException {

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderTypeRel.getCommerceOrderTypeId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderTypeRelId,
			String actionId)
		throws PortalException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
				commerceOrderTypeRelId);

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderTypeRel.getCommerceOrderTypeId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderTypeRel commerceOrderTypeRel, String actionId)
		throws PortalException {

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderTypeRel.getCommerceOrderTypeId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderTypeRelId,
			String actionId)
		throws PortalException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
				commerceOrderTypeRelId);

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderTypeRel.getCommerceOrderTypeId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceOrderTypeRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private CommerceOrderTypePermission _commerceOrderTypePermission;

	@Reference
	private CommerceOrderTypeRelLocalService _commerceOrderTypeRelLocalService;

}