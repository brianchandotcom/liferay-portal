/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.security.permission.resource;

import com.liferay.commerce.pricing.constants.CommercePricingClassConstants;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.permission.CommercePricingClassPermission;
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
	property = "model.class.name=com.liferay.commerce.pricing.model.CommercePricingClass",
	service = ModelResourcePermission.class
)
public class CommercePricingClassModelResourcePermission
	implements ModelResourcePermission<CommercePricingClass> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePricingClass commercePricingClass, String actionId)
		throws PortalException {

		commercePricingClassPermission.check(
			permissionChecker, commercePricingClass, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePricingClassId,
			String actionId)
		throws PortalException {

		commercePricingClassPermission.check(
			permissionChecker, commercePricingClassId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePricingClass commercePricingClass, String actionId)
		throws PortalException {

		return commercePricingClassPermission.contains(
			permissionChecker, commercePricingClass, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePricingClassId,
			String actionId)
		throws PortalException {

		return commercePricingClassPermission.contains(
			permissionChecker, commercePricingClassId, actionId);
	}

	@Override
	public String getModelName() {
		return CommercePricingClass.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommercePricingClassPermission commercePricingClassPermission;

	@Reference(
		target = "(resource.name=" + CommercePricingClassConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}