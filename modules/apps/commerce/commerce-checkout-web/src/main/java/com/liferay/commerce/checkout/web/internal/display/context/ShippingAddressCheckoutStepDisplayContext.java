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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class ShippingAddressCheckoutStepDisplayContext
	extends BaseAddressCheckoutStepDisplayContext {

	public ShippingAddressCheckoutStepDisplayContext(
		AccountEntryLocalService accountEntryLocalService,
		ModelResourcePermission<AccountEntry>
			accountEntryModelResourcePermission,
		AccountRoleLocalService accountRoleLocalService,
		CommerceAddressService commerceAddressService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		CommerceChannelLocalService commerceChannelLocalService,
		HttpServletRequest httpServletRequest,
		PortletResourcePermission portletResourcePermission) {

		super(
			accountEntryLocalService, accountEntryModelResourcePermission,
			accountRoleLocalService, commerceAddressService,
			commerceChannelAccountEntryRelLocalService,
			commerceChannelLocalService, httpServletRequest,
			portletResourcePermission);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		CommerceContext commerceContext = getCommerceContext();
		CommerceOrder commerceOrder = getCommerceOrder();

		return commerceAddressService.getShippingCommerceAddresses(
			commerceContext.getCommerceChannelId(),
			AccountEntry.class.getName(), commerceOrder.getCommerceAccountId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public String getCommerceCountrySelectionColumnName() {
		return "shippingAllowed";
	}

	@Override
	public String getCommerceCountrySelectionMethodName() {
		return "get-shipping-countries";
	}

	@Override
	public long getDefaultCommerceAddressId(long commerceChannelId)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		long shippingAddressId = commerceOrder.getShippingAddressId();

		if (shippingAddressId > 0) {
			return shippingAddressId;
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		if (accountEntry == null) {
			return shippingAddressId;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					accountEntry.getAccountEntryId(), commerceChannelId,
					CommerceChannelAccountEntryRelConstants.
						TYPE_SHIPPING_ADDRESS);

		if (commerceChannelAccountEntryRel == null) {
			return shippingAddressId;
		}

		CommerceAddress commerceAddress =
			commerceAddressService.fetchCommerceAddress(
				commerceChannelAccountEntryRel.getClassPK());

		if (commerceAddress != null) {
			shippingAddressId = commerceAddress.getCommerceAddressId();
		}

		return shippingAddressId;
	}

	@Override
	public String getParamName() {
		return CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME;
	}

	@Override
	public String getTitle() {
		return "shipping-address";
	}

}