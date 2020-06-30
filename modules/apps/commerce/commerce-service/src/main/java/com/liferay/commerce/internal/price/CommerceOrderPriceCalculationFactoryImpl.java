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

import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceOrderPriceCalculationFactory;
import com.liferay.commerce.price.CommercePriceCalculationRegistry;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
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
	immediate = true, service = CommerceOrderPriceCalculationFactory.class
)
public class CommerceOrderPriceCalculationFactoryImpl
	implements CommerceOrderPriceCalculationFactory {

	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
		_commercePricingConfiguration = ConfigurableUtil.createConfigurable(
			CommercePricingConfiguration.class, properties);
	}

	@Override
	public CommerceOrderPriceCalculation getCommerceOrderPriceCalculation() {
		return _commercePriceCalculationRegistry.
			getCommerceOrderPriceCalculation(
				_commercePricingConfiguration.commercePricingCalculationKey());
	}

	@Reference
	private CommercePriceCalculationRegistry _commercePriceCalculationRegistry;

	private volatile CommercePricingConfiguration _commercePricingConfiguration;

}