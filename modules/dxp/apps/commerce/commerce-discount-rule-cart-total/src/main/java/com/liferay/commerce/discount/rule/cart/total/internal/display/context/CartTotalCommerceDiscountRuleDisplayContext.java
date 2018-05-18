/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				themeDisplay.getScopeGroupId());

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public String getTypeSettings() throws PortalException {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return StringPool.BLANK;
		}

		String type = BeanParamUtil.getString(
			commerceDiscountRule, _httpServletRequest, "type");

		return commerceDiscountRule.getSettingsProperty(type);
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final HttpServletRequest _httpServletRequest;

}