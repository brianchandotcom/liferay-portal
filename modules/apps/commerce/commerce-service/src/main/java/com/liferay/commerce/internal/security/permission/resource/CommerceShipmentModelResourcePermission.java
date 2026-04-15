/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.permission.CommerceShipmentPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceShipment",
	service = ModelResourcePermission.class
)
public class CommerceShipmentModelResourcePermission
	implements ModelResourcePermission<CommerceShipment> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException {

		_commerceShipmentPermission.check(
			permissionChecker, commerceShipment, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException {

		_commerceShipmentPermission.check(
			permissionChecker, commerceShipmentId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException {

		return _commerceShipmentPermission.contains(
			permissionChecker, commerceShipment, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException {

		return _commerceShipmentPermission.contains(
			permissionChecker, commerceShipmentId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceShipment.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceShipmentPermission _commerceShipmentPermission;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_SHIPMENT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}