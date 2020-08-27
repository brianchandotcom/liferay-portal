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

package com.liferay.commerce.pricing.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Alberti
 */
@ExtendedObjectClassDefinition(
	category = "pricing", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.pricing.configuration.CommercePricingConfiguration",
	localization = "content/Language",
	name = "commerce-pricing-configuration-name"
)
public interface CommercePricingConfiguration {

	@Meta.AD(
		deflt = CommercePricingConstants.VERSION_2_0,
		name = "pricing-calculation-key", required = false
	)
	public String commercePricingCalculationKey();

	@Meta.AD(
		deflt = CommercePricingConstants.ORDER_BY_HIERARCHY,
		name = "price-list-discovery-method", required = false
	)
	public String commercePriceListDiscovery();

	@Meta.AD(
		deflt = CommercePricingConstants.ORDER_BY_HIERARCHY,
		name = "promotion-discovery-method", required = false
	)
	public String commercePromotionDiscovery();

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Meta.AD(
		deflt = "" + CommercePricingConstants.TAX_INCLUDED_IN_FINAL_PRICE,
		name = "tax-display",
		optionLabels = {
			"tax-included-in-final-price", "tax-excluded-from-final-price"
		},
		optionValues = {"0", "1"}, required = false
	)
	public int commerceDisplayTax();

	@Meta.AD(
		deflt = "" + CommercePricingConstants.DISCOUNT_CHAIN_METHOD,
		name = "discount-application-strategy", required = false
	)
	public String commerceDiscountApplicationStrategy();

}