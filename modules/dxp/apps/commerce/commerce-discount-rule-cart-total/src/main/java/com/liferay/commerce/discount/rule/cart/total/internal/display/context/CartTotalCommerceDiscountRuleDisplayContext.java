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

package com.liferay.commerce.discount.rule.cart.total.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CartTotalCommerceDiscountRuleDisplayContext {

	public CartTotalCommerceDiscountRuleDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceDiscountRuleService commerceDiscountRuleService,
		HttpServletRequest httpServletRequest) {

		_commerceCurrencyService = commerceCurrencyService;
		_commerceDiscountRuleService = commerceDiscountRuleService;
		_httpServletRequest = httpServletRequest;
	}

	public CommerceDiscountRule getCommerceDiscountRule()
		throws PortalException {

		if (_commerceDiscountRule != null) {
			return _commerceDiscountRule;
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			_httpServletRequest, "commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		return _commerceDiscountRule;
	}

	public String getDefaultCommerceCurrencyCode() {
		CommerceCurrency commerceCurrency = getCommerceCurrency();

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public String getTypeSettings() throws Exception {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return StringPool.BLANK;
		}

		String type = BeanParamUtil.getString(
			commerceDiscountRule, _httpServletRequest, "type");

		String typeSettings = commerceDiscountRule.getSettingsProperty(type);

		CommerceCurrency commerceCurrency = getCommerceCurrency();

		if (commerceCurrency == null) {
			return typeSettings;
		}

		DecimalFormat decimalFormat = new DecimalFormat(
			commerceCurrency.getFormatPattern());

		decimalFormat.setParseBigDecimal(true);

		BigDecimal value = (BigDecimal)decimalFormat.parse(typeSettings);

		value = commerceCurrency.round(value);

		return value.toPlainString();
	}

	protected CommerceCurrency getCommerceCurrency() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCurrencyService.fetchPrimaryCommerceCurrency(
			themeDisplay.getScopeGroupId());
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final HttpServletRequest _httpServletRequest;

}