/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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