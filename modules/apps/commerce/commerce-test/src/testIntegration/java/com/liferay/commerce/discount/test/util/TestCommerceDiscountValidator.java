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

package com.liferay.commerce.discount.test.util;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Objects;

/**
 * @author Riccardo Alberti
 */
public class TestCommerceDiscountValidator
	implements CommerceDiscountValidator {

	public static final String KEY = "test";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceDiscountValidatorResult validate(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		if (Objects.equals(commerceDiscount.getTitle(), "validDiscount")) {
			return new CommerceDiscountValidatorResult(true);
		}

		return new CommerceDiscountValidatorResult(
			commerceDiscount.getCommerceDiscountId(), false,
			"the-discount-is-not-active");
	}

}