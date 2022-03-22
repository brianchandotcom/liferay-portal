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

package com.liferay.commerce.pricing.internal.type;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.type.CommercePriceModifierType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.price.modifier.type.key=" + CommercePriceModifierConstants.MODIFIER_TYPE_PERCENTAGE,
		"commerce.price.modifier.type.order:Integer=20"
	},
	service = CommercePriceModifierType.class
)
public class PercentageCommercePriceModifierTypeImpl
	implements CommercePriceModifierType {

	@Override
	public BigDecimal evaluate(
			BigDecimal originalPrice,
			CommercePriceModifier commercePriceModifier)
		throws PortalException {

		BigDecimal modifierAmount = commercePriceModifier.getModifierAmount();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceModifier.getCommercePriceListId());

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		BigDecimal percentage = BigDecimal.ONE.add(
			modifierAmount.scaleByPowerOfTen(-2));

		MathContext mathContext = new MathContext(
			percentage.precision(), roundingMode);

		return originalPrice.multiply(percentage, mathContext);
	}

	@Override
	public String getKey() {
		return CommercePriceModifierConstants.MODIFIER_TYPE_PERCENTAGE;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "percentage");
	}

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}