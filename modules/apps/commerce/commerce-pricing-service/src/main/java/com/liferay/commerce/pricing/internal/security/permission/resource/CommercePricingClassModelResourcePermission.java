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
	enabled = false, immediate = true,
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