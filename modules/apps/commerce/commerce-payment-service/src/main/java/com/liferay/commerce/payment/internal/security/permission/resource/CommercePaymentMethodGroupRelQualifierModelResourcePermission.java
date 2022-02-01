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

package com.liferay.commerce.payment.internal.security.permission.resource;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
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
	property = "model.class.name=com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier",
	service = ModelResourcePermission.class
)
public class CommercePaymentMethodGroupRelQualifierModelResourcePermission
	implements ModelResourcePermission<CommercePaymentMethodGroupRelQualifier> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier,
			String actionId)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commercePaymentMethodGroupRel.getGroupId());

		_commerceChannelModelResourcePermission.check(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePaymentMethodGroupRelQualifierId, String actionId)
		throws PortalException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				commercePaymentMethodGroupRelQualifierLocalService.
					getCommercePaymentMethodGroupRelQualifier(
						commercePaymentMethodGroupRelQualifierId);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commercePaymentMethodGroupRel.getGroupId());

		_commerceChannelModelResourcePermission.check(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier,
			String actionId)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commercePaymentMethodGroupRel.getGroupId());

		return _commerceChannelModelResourcePermission.contains(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePaymentMethodGroupRelQualifierId, String actionId)
		throws PortalException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				commercePaymentMethodGroupRelQualifierLocalService.
					getCommercePaymentMethodGroupRelQualifier(
						commercePaymentMethodGroupRelQualifierId);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId());

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commercePaymentMethodGroupRel.getGroupId());

		return _commerceChannelModelResourcePermission.contains(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public String getModelName() {
		return CommercePaymentMethodGroupRelQualifier.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceChannelLocalService commerceChannelLocalService;

	@Reference
	protected CommercePaymentMethodGroupRelLocalService
		commercePaymentMethodGroupRelLocalService;

	@Reference
	protected CommercePaymentMethodGroupRelQualifierLocalService
		commercePaymentMethodGroupRelQualifierLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

}