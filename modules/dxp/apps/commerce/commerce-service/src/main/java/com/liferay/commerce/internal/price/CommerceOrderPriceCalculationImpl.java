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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendins
 */
@Component
public class CommerceOrderPriceCalculationImpl
	implements CommerceOrderPriceCalculation {

	@Override
	public CommerceMoney getShippingValue(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
	}

	@Override
	public CommerceMoney getSubtotal(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
	}

	@Override
	public CommerceMoney getTaxValue(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
	}

	@Override
	public CommerceMoney getTotal(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
	}

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

}