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