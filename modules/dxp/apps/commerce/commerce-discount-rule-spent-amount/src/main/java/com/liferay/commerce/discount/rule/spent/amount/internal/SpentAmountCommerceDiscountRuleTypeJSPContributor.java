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

package com.liferay.commerce.discount.rule.spent.amount.internal;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.model.CommerceDiscountRuleConstants;
import com.liferay.commerce.discount.rule.spent.amount.internal.display.context.SpentAmountCommerceDiscountRuleDisplayContext;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.discount.rule.type.jsp.contributor.key=" + CommerceDiscountRuleConstants.TYPE_SPENT_AMOUNT
)
public class SpentAmountCommerceDiscountRuleTypeJSPContributor
	implements CommerceDiscountRuleTypeJSPContributor {

	@Override
	public void render(
			long commerceDiscountId, long commerceDiscountRuleId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		SpentAmountCommerceDiscountRuleDisplayContext
			spentAmountCommerceDiscountRuleDisplayContext =
				new SpentAmountCommerceDiscountRuleDisplayContext(
					_commerceCurrencyService, _commerceDiscountRuleService,
					httpServletRequest);

		httpServletRequest.setAttribute(
			"view.jsp-spentAmountCommerceDiscountRuleDisplayContext",
			spentAmountCommerceDiscountRuleDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.discount.rule.spent.amount)"
	)
	private ServletContext _servletContext;

}