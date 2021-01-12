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

package com.liferay.commerce.discount.internal.validator;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleLocalService;
import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.discount.validator.key=" + RulesCommerceDiscountValidator.KEY,
		"commerce.discount.validator.priority:Integer=30",
		"commerce.discount.validator.type=" + CommerceDiscountConstants.VALIDATOR_TYPE_POST_QUALIFICATION
	},
	service = CommerceDiscountValidator.class
)
public class RulesCommerceDiscountValidator
	implements CommerceDiscountValidator {

	public static final String KEY = "rules";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceDiscountValidatorResult validate(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		List<CommerceDiscountRule> commerceDiscountRules =
			_commerceDiscountRuleLocalService.getCommerceDiscountRules(
				commerceDiscount.getCommerceDiscountId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (commerceDiscountRules.isEmpty()) {
			return new CommerceDiscountValidatorResult(true);
		}

		for (CommerceDiscountRule commerceDiscountRule :
				commerceDiscountRules) {

			CommerceDiscountRuleType commerceDiscountRuleType =
				_commerceDiscountRuleTypeRegistry.getCommerceDiscountRuleType(
					commerceDiscountRule.getType());

			boolean commerceDiscountRuleTypeEvaluation =
				commerceDiscountRuleType.evaluate(
					commerceDiscountRule, commerceContext);

			if (!commerceDiscountRuleTypeEvaluation &&
				commerceDiscount.isRulesConjunction()) {

				return new CommerceDiscountValidatorResult(
					commerceDiscount.getCommerceDiscountId(), false,
					"the-discount-is-not-valid");
			}
			else if (commerceDiscountRuleTypeEvaluation &&
					 !commerceDiscount.isRulesConjunction()) {

				return new CommerceDiscountValidatorResult(true);
			}
		}

		if (commerceDiscount.isRulesConjunction()) {
			return new CommerceDiscountValidatorResult(true);
		}

		return new CommerceDiscountValidatorResult(
			commerceDiscount.getCommerceDiscountId(), false,
			"the-discount-is-not-valid");
	}

	@Reference
	private CommerceDiscountRuleLocalService _commerceDiscountRuleLocalService;

	@Reference
	private CommerceDiscountRuleTypeRegistry _commerceDiscountRuleTypeRegistry;

}