/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.validator;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = {
		"commerce.discount.validator.key=" + DefaultCommerceDiscountValidator.KEY,
		"commerce.discount.validator.priority:Integer=10",
		"commerce.discount.validator.type=" + CommerceDiscountConstants.VALIDATOR_TYPE_PRE_QUALIFICATION
	},
	service = CommerceDiscountValidator.class
)
public class DefaultCommerceDiscountValidator
	implements CommerceDiscountValidator {

	public static final String KEY = "default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceDiscountValidatorResult validate(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		if (!commerceDiscount.isActive()) {
			return new CommerceDiscountValidatorResult(
				commerceDiscount.getCommerceDiscountId(), false,
				"the-discount-is-not-active");
		}

		return new CommerceDiscountValidatorResult(true);
	}

}