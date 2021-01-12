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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.discount.validator.key=" + QualifiersCommerceDiscountValidator.KEY,
		"commerce.discount.validator.priority:Integer=20",
		"commerce.discount.validator.type=" + CommerceDiscountConstants.VALIDATOR_TYPE_PRE_QUALIFICATION
	},
	service = CommerceDiscountValidator.class
)
public class QualifiersCommerceDiscountValidator
	implements CommerceDiscountValidator {

	public static final String KEY = "qualifiers";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceDiscountValidatorResult validate(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		long commerceAccountId = 0;

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		int validCommerceDiscountsCount =
			_commerceDiscountLocalService.getValidCommerceDiscountsCount(
				commerceAccountId, commerceContext.getCommerceAccountGroupIds(),
				commerceContext.getCommerceChannelId(),
				commerceDiscount.getCommerceDiscountId());

		if (validCommerceDiscountsCount == 0) {
			return new CommerceDiscountValidatorResult(
				commerceDiscount.getCommerceDiscountId(), false,
				"the-account-is-not-qualified-to-use-the-discount");
		}

		return new CommerceDiscountValidatorResult(true);
	}

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

}