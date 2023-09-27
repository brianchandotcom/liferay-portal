/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.validator;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = {
		"commerce.discount.validator.key=" + OrderCommerceDiscountValidator.KEY,
		"commerce.discount.validator.priority:Integer=40",
		"commerce.discount.validator.type=" + CommerceDiscountConstants.VALIDATOR_TYPE_TARGET
	},
	service = CommerceDiscountValidator.class
)
public class OrderCommerceDiscountValidator
	implements CommerceDiscountValidator {

	public static final String KEY = "order";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceDiscountValidatorResult validate(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		String target = commerceDiscount.getTarget();

		if (Objects.equals(target, CommerceDiscountConstants.TARGET_SHIPPING) ||
			Objects.equals(target, CommerceDiscountConstants.TARGET_SUBTOTAL) ||
			Objects.equals(target, CommerceDiscountConstants.TARGET_TOTAL)) {

			return new CommerceDiscountValidatorResult(true);
		}

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			int validCommerceDiscountsCount =
				_commerceDiscountLocalService.getValidCommerceDiscountsCount(
					commerceDiscount.getCommerceDiscountId(),
					commerceOrderItem.getCPDefinitionId(),
					commerceOrderItem.getCPInstanceId(),
					commerceOrderItem.getUnitOfMeasureKey());

			if (validCommerceDiscountsCount > 0) {
				return new CommerceDiscountValidatorResult(true);
			}
		}

		return new CommerceDiscountValidatorResult(
			commerceDiscount.getCommerceDiscountId(), false,
			"the-discount-is-not-applicable-to-the-current-order");
	}

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

}