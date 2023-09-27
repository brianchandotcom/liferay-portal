/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.permission.CommerceOrderTypePermission;
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
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderType",
	service = ModelResourcePermission.class
)
public class CommerceOrderTypeModelResourcePermission
	implements ModelResourcePermission<CommerceOrderType> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderType, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderTypeId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderType, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderTypeId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceOrderType.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceOrderTypePermission _commerceOrderTypePermission;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_ORDER_TYPE + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}