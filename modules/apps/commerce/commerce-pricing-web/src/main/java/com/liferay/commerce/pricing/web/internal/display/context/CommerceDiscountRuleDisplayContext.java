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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributorRegistry;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRuleDisplayContext
	extends BasePricingDisplayContext {

	public CommerceDiscountRuleDisplayContext(
		CommerceDiscountRuleService commerceDiscountRuleService,
		CommerceDiscountRuleTypeJSPContributorRegistry
			commerceDiscountRuleTypeJSPContributorRegistry,
		CommerceDiscountRuleTypeRegistry commerceDiscountRuleTypeRegistry,
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		_commerceDiscountRuleService = commerceDiscountRuleService;
		_commerceDiscountRuleTypeJSPContributorRegistry =
			commerceDiscountRuleTypeJSPContributorRegistry;
		_commerceDiscountRuleTypeRegistry = commerceDiscountRuleTypeRegistry;
	}

	public CommerceDiscountRule getCommerceDiscountRule()
		throws PortalException {

		if (_commerceDiscountRule != null) {
			return _commerceDiscountRule;
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(),
			"commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		return _commerceDiscountRule;
	}

	public long getCommerceDiscountRuleId() throws PortalException {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return 0;
		}

		return commerceDiscountRule.getCommerceDiscountRuleId();
	}

	public CommerceDiscountRuleTypeJSPContributor
		getCommerceDiscountRuleTypeJSPContributor(String key) {

		return _commerceDiscountRuleTypeJSPContributorRegistry.
			getCommerceDiscountRuleTypeJSPContributor(key);
	}

	public String getCommerceDiscountRuleTypeLabel(Locale locale)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		List<CommerceDiscountRuleType> commerceDiscountRuleTypes =
			_commerceDiscountRuleTypeRegistry.getCommerceDiscountRuleTypes();

		for (CommerceDiscountRuleType commerceDiscountRuleType :
				commerceDiscountRuleTypes) {

			if (commerceDiscountRuleType.equals(
					commerceDiscountRule.getType())) {

				return commerceDiscountRuleType.getLabel(locale);
			}
		}

		return commerceDiscountRule.getType();
	}

	public List<CommerceDiscountRuleType> getCommerceDiscountRuleTypes() {
		return _commerceDiscountRuleTypeRegistry.getCommerceDiscountRuleTypes();
	}

	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final CommerceDiscountRuleTypeJSPContributorRegistry
		_commerceDiscountRuleTypeJSPContributorRegistry;
	private final CommerceDiscountRuleTypeRegistry
		_commerceDiscountRuleTypeRegistry;

}