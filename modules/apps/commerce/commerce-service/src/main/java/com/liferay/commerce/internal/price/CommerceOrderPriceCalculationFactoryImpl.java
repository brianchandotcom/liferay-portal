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

import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceOrderPriceCalculationFactory;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	configurationPid = "com.liferay.commerce.pricing.configuration.CommercePricingConfiguration",
	enabled = false, immediate = true,
	service = CommerceOrderPriceCalculationFactory.class
)
public class CommerceOrderPriceCalculationFactoryImpl
	implements CommerceOrderPriceCalculationFactory {

	@Override
	public CommerceOrderPriceCalculation getCommerceOrderPriceCalculation() {
		return new CommerceOrderPriceCalculationV2Impl(
			_commerceChannelLocalService, _commerceDiscountCalculationV2,
			_commerceMoneyFactory, _commerceTaxCalculation);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commercePricingConfiguration = ConfigurableUtil.createConfigurable(
			CommercePricingConfiguration.class, properties);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(target = "(commerce.discount.calculation.key=v2.0)")
	private CommerceDiscountCalculation _commerceDiscountCalculationV2;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	private volatile CommercePricingConfiguration _commercePricingConfiguration;

	@Reference
	private CommerceTaxCalculation _commerceTaxCalculation;

}