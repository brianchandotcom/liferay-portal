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

package com.liferay.commerce.shipping.engine.fixed.internal.security.permission.resource;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierLocalService;
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
	property = "model.class.name=com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier",
	service = ModelResourcePermission.class
)
public class CommerceShippingFixedOptionQualifierModelResourcePermission
	implements ModelResourcePermission<CommerceShippingFixedOptionQualifier> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier,
			String actionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceShippingFixedOption.getGroupId());

		_commerceChannelModelResourcePermission.check(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceShippingFixedOptionQualifierId, String actionId)
		throws PortalException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				commerceShippingFixedOptionQualifierLocalService.
					getCommerceShippingFixedOptionQualifier(
						commerceShippingFixedOptionQualifierId);

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceShippingFixedOption.getGroupId());

		_commerceChannelModelResourcePermission.check(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier,
			String actionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceShippingFixedOption.getGroupId());

		return _commerceChannelModelResourcePermission.contains(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceShippingFixedOptionQualifierId, String actionId)
		throws PortalException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				commerceShippingFixedOptionQualifierLocalService.
					getCommerceShippingFixedOptionQualifier(
						commerceShippingFixedOptionQualifierId);

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceShippingFixedOption.getGroupId());

		return _commerceChannelModelResourcePermission.contains(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceShippingFixedOptionQualifier.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceChannelLocalService commerceChannelLocalService;

	@Reference
	protected CommerceShippingFixedOptionLocalService
		commerceShippingFixedOptionLocalService;

	@Reference
	protected CommerceShippingFixedOptionQualifierLocalService
		commerceShippingFixedOptionQualifierLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

}