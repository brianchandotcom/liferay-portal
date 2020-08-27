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

package com.liferay.commerce.discount.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x)
 */
@Component(
	property = "commerce.discount.calculation.key=v1.0",
	service = CommerceDiscountCalculation.class
)
@Deprecated
public class CommerceDiscountCalculationImpl
	extends BaseCommerceDiscountCalculation {

	@Override
	public CommerceDiscountValue getOrderShippingCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal shippingAmount,
			CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return null;
		}

		return _getCommerceDiscountValue(
			commerceOrder, shippingAmount, commerceContext,
			CommerceDiscountConstants.TARGET_SHIPPING);
	}

	@Override
	public CommerceDiscountValue getOrderSubtotalCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal subtotalAmount,
			CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return null;
		}

		return _getCommerceDiscountValue(
			commerceOrder, subtotalAmount, commerceContext,
			CommerceDiscountConstants.TARGET_SUBTOTAL);
	}

	@Override
	public CommerceDiscountValue getOrderTotalCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal totalAmount,
			CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return null;
		}

		return _getCommerceDiscountValue(
			commerceOrder, totalAmount, commerceContext,
			CommerceDiscountConstants.TARGET_TOTAL);
	}

	@Override
	public CommerceDiscountValue getProductCommerceDiscountValue(
			long cpInstanceId, int quantity, BigDecimal productUnitPrice,
			CommerceContext commerceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		List<CommerceDiscount> commerceDiscounts =
			getProductCommerceDiscountByHierarchy(
				cpInstance.getCompanyId(), commerceContext.getCommerceAccount(),
				commerceContext.getCommerceChannelId(),
				cpInstance.getCPDefinitionId());

		if (commerceDiscounts.isEmpty()) {
			return null;
		}

		String couponCode = null;

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder != null) {
			couponCode = commerceOrder.getCouponCode();
		}

		return _getCommerceDiscountValue(
			productUnitPrice, quantity, couponCode, commerceContext,
			commerceDiscounts);
	}

	@Override
	public CommerceDiscountValue getProductCommerceDiscountValue(
			long cpInstanceId, long commercePriceListId, int quantity,
			BigDecimal productUnitPrice, CommerceContext commerceContext)
		throws PortalException {

		return getProductCommerceDiscountValue(
			cpInstanceId, quantity, productUnitPrice, commerceContext);
	}

	private CommerceDiscountValue _getCommerceDiscountValue(
			BigDecimal amount, int quantity, String couponCode,
			CommerceContext commerceContext,
			List<CommerceDiscount> commerceDiscounts)
		throws PortalException {

		List<CommerceDiscountValue> commerceDiscountValues = new ArrayList<>();

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			String discountCouponCode = commerceDiscount.getCouponCode();

			if (!Validator.isBlank(discountCouponCode) &&
				!Objects.equals(couponCode, discountCouponCode)) {

				continue;
			}

			if (_isValidDiscount(commerceContext, commerceDiscount)) {
				commerceDiscountValues.add(
					_getCommerceDiscountValue(
						commerceDiscount, amount, quantity, commerceCurrency));
			}
		}

		BigDecimal currentDiscountAmount = BigDecimal.ZERO;

		CommerceDiscountValue selectedDiscount = null;

		for (CommerceDiscountValue commerceDiscountValue :
				commerceDiscountValues) {

			if (commerceDiscountValue == null) {
				continue;
			}

			CommerceMoney discountAmount =
				commerceDiscountValue.getDiscountAmount();

			if (currentDiscountAmount.compareTo(discountAmount.getPrice()) <
					0) {

				currentDiscountAmount = discountAmount.getPrice();
				selectedDiscount = commerceDiscountValue;
			}
		}

		return selectedDiscount;
	}

	private CommerceDiscountValue _getCommerceDiscountValue(
		CommerceDiscount commerceDiscount, BigDecimal amount, int quantity,
		CommerceCurrency commerceCurrency) {

		if ((amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0)) {
			return null;
		}

		BigDecimal[] values = new BigDecimal[4];

		if (commerceDiscount.isUsePercentage()) {
			values[0] = commerceDiscount.getLevel1();
			values[1] = commerceDiscount.getLevel2();
			values[2] = commerceDiscount.getLevel3();
			values[3] = commerceDiscount.getLevel4();
		}

		BigDecimal currentDiscountAmount = BigDecimal.ZERO;

		BigDecimal discountedAmount = amount;

		if (commerceDiscount.isUsePercentage()) {
			currentDiscountAmount = _getDiscountAmount(
				discountedAmount, commerceDiscount.getLevel1());

			discountedAmount = discountedAmount.subtract(currentDiscountAmount);

			currentDiscountAmount = _getDiscountAmount(
				discountedAmount, commerceDiscount.getLevel2());

			discountedAmount = discountedAmount.subtract(currentDiscountAmount);

			currentDiscountAmount = _getDiscountAmount(
				discountedAmount, commerceDiscount.getLevel3());

			discountedAmount = discountedAmount.subtract(currentDiscountAmount);

			currentDiscountAmount = amount.subtract(discountedAmount);

			BigDecimal maximumDiscountAmount =
				commerceDiscount.getMaximumDiscountAmount();

			if ((maximumDiscountAmount.compareTo(BigDecimal.ZERO) > 0) &&
				(currentDiscountAmount.compareTo(maximumDiscountAmount) > 0)) {

				currentDiscountAmount =
					commerceDiscount.getMaximumDiscountAmount();

				discountedAmount = amount.subtract(currentDiscountAmount);
			}
		}
		else {
			currentDiscountAmount = commerceDiscount.getLevel1();

			if (currentDiscountAmount.compareTo(discountedAmount) > 0) {
				currentDiscountAmount = discountedAmount;
			}

			discountedAmount = discountedAmount.subtract(currentDiscountAmount);
		}

		CommerceMoney discountAmount = _commerceMoneyFactory.create(
			commerceCurrency,
			currentDiscountAmount.multiply(new BigDecimal(quantity)));

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		return new CommerceDiscountValue(
			commerceDiscount.getCommerceDiscountId(), discountAmount,
			_getDiscountPercentage(discountedAmount, amount, roundingMode),
			values);
	}

	private CommerceDiscountValue _getCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal amount,
			CommerceContext commerceContext, String discountType)
		throws PortalException {

		if ((amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0)) {
			return null;
		}

		List<CommerceDiscount> commerceDiscounts =
			getOrderCommerceDiscountByHierarchy(
				commerceOrder.getCompanyId(),
				commerceContext.getCommerceAccount(),
				commerceContext.getCommerceChannelId(), discountType);

		if (commerceDiscounts.isEmpty()) {
			return null;
		}

		return _getCommerceDiscountValue(
			amount, 1, commerceOrder.getCouponCode(), commerceContext,
			commerceDiscounts);
	}

	private BigDecimal _getDiscountAmount(
		BigDecimal amount, BigDecimal percentage) {

		if (percentage == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal discountedAmount = amount.multiply(percentage);

		return discountedAmount.divide(_ONE_HUNDRED);
	}

	private BigDecimal _getDiscountPercentage(
		BigDecimal discountedAmount, BigDecimal amount,
		RoundingMode roundingMode) {

		double actualPrice = discountedAmount.doubleValue();
		double originalPrice = amount.doubleValue();

		double percentage = actualPrice / originalPrice;

		BigDecimal discountPercentage = new BigDecimal(percentage);

		discountPercentage = discountPercentage.multiply(_ONE_HUNDRED);

		MathContext mathContext = new MathContext(
			discountPercentage.precision(), roundingMode);

		return _ONE_HUNDRED.subtract(discountPercentage, mathContext);
	}

	private boolean _isValidDiscount(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		List<CommerceDiscountRule> commerceDiscountRules =
			_commerceDiscountRuleLocalService.getCommerceDiscountRules(
				commerceDiscount.getCommerceDiscountId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (commerceDiscountRules.isEmpty()) {
			return true;
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

				return false;
			}
			else if (commerceDiscountRuleTypeEvaluation &&
					 !commerceDiscount.isRulesConjunction()) {

				return true;
			}
		}

		return commerceDiscount.isRulesConjunction();
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	@Reference
	private CommerceDiscountRuleLocalService _commerceDiscountRuleLocalService;

	@Reference
	private CommerceDiscountRuleTypeRegistry _commerceDiscountRuleTypeRegistry;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}