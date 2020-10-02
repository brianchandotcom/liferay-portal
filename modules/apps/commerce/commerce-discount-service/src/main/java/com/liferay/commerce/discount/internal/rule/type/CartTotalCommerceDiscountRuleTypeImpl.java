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

package com.liferay.commerce.discount.internal.rule.type;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.constants.CommerceDiscountRuleConstants;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.discount.rule.type.key=" + CommerceDiscountRuleConstants.TYPE_CART_TOTAL,
		"commerce.discount.rule.type.order:Integer=10"
	},
	service = CommerceDiscountRuleType.class
)
public class CartTotalCommerceDiscountRuleTypeImpl
	implements CommerceDiscountRuleType {

	@Override
	public boolean evaluate(
			CommerceDiscountRule commerceDiscountRule,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder == null) {
			return false;
		}

		CommerceMoney orderPriceCommerceMoney =
			_commerceOrderPriceCalculation.getSubtotal(
				commerceOrder, commerceContext);

		if (orderPriceCommerceMoney == null) {
			return false;
		}

		BigDecimal orderPrice = orderPriceCommerceMoney.getPrice();

		String settingsProperty = commerceDiscountRule.getSettingsProperty(
			commerceDiscountRule.getType());

		BigDecimal cartTotal = new BigDecimal(settingsProperty);

		if (CommerceBigDecimalUtil.gt(orderPrice, cartTotal)) {
			return true;
		}

		return false;
	}

	@Override
	public String getKey() {
		return CommerceDiscountRuleConstants.TYPE_CART_TOTAL;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, CommerceDiscountRuleConstants.TYPE_CART_TOTAL);
	}

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

}