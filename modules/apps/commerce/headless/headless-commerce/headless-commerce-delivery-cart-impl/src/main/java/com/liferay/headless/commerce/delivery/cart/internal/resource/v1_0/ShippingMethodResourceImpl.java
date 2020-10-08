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

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShippingOptionLabelComparator;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.ShippingOption;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.ShippingMethodResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/shipping-method.properties",
	scope = ServiceScope.PROTOTYPE, service = ShippingMethodResource.class
)
public class ShippingMethodResourceImpl extends BaseShippingMethodResourceImpl {

	@Override
	public Page<ShippingMethod> getCartShippingMethodsPage(Long cartId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		if (shippingAddress != null) {
			return Page.of(
				transform(
					_commerceShippingMethodService.getCommerceShippingMethods(
						commerceChannel.getGroupId(),
						shippingAddress.getCommerceCountryId(), true),
					shippingMethod -> _toShippingMethod(
						shippingMethod, commerceChannel, commerceOrder)));
		}

		return super.getCartShippingMethodsPage(cartId);
	}

	private ShippingOption[] _getShippingOptions(
			CommerceShippingMethod commerceShippingMethod,
			CommerceChannel commerceChannel, CommerceOrder commerceOrder)
		throws PortalException {

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceChannel.getSiteGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, commerceOrder,
				contextAcceptLanguage.getPreferredLocale());

		return transformToArray(
			ListUtil.sort(
				commerceShippingOptions,
				new CommerceShippingOptionLabelComparator()),
			shippingOption -> _toShippingOption(
				shippingOption, commerceContext),
			ShippingOption.class);
	}

	private ShippingMethod _toShippingMethod(
			CommerceShippingMethod commerceShippingMethod,
			CommerceChannel commerceChannel, CommerceOrder commerceOrder)
		throws PortalException {

		return new ShippingMethod() {
			{
				description = commerceShippingMethod.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = commerceShippingMethod.getCommerceShippingMethodId();
				name = commerceShippingMethod.getName(
					contextAcceptLanguage.getPreferredLocale());
				shippingOptions = _getShippingOptions(
					commerceShippingMethod, commerceChannel, commerceOrder);
			}
		};
	}

	private ShippingOption _toShippingOption(
			CommerceShippingOption commerceShippingOption,
			CommerceContext commerceContext)
		throws PortalException {

		BigDecimal commerceShippingOptionAmount =
			commerceShippingOption.getAmount();

		return new ShippingOption() {
			{
				amount = commerceShippingOptionAmount.doubleValue();
				amountFormatted = _commercePriceFormatter.format(
					commerceContext.getCommerceCurrency(),
					commerceShippingOption.getAmount(),
					contextAcceptLanguage.getPreferredLocale());
				label = commerceShippingOption.getLabel();
				name = commerceShippingOption.getName();
			}
		};
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}