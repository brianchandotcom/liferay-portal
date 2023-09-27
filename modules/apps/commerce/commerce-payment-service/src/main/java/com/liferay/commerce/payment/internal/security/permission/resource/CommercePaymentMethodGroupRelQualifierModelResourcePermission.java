/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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