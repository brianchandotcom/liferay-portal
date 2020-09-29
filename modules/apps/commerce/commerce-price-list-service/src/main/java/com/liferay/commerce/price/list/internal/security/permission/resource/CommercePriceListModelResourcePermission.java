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

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceList",
	service = ModelResourcePermission.class
)
public class CommercePriceListModelResourcePermission
	implements ModelResourcePermission<CommercePriceList> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker, commercePriceList, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker, commercePriceListId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceList, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceListId, actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceList.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

	@Reference(
		target = "(resource.name=" + CommercePriceListConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}