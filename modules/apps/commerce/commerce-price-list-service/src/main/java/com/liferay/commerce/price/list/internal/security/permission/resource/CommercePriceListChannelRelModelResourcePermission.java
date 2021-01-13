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

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceListChannelRel",
	service = ModelResourcePermission.class
)
public class CommercePriceListChannelRelModelResourcePermission
	implements ModelResourcePermission<CommercePriceListChannelRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceListChannelRel commercePriceListChannelRel,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceListChannelRelId, String actionId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceListChannelRel commercePriceListChannelRel,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceListChannelRelId, String actionId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceListChannelRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListChannelRelLocalService
		commercePriceListChannelRelLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}