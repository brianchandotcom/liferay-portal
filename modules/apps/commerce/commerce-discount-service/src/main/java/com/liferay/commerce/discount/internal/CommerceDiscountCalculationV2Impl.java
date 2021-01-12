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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalService;
import com.liferay.commerce.discount.validator.helper.CommerceDiscountValidatorHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, property = "commerce.discount.calculation.key=v2.0",
	service = CommerceDiscountCalculation.class
)
public class CommerceDiscountCalculationV2Impl
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

		return getProductCommerceDiscountValue(
			cpInstanceId, 0, quantity, productUnitPrice, commerceContext);
	}

	@Override
	public CommerceDiscountValue getProductCommerceDiscountValue(
			long cpInstanceId, long commercePriceListId, int quantity,
			BigDecimal productUnitPrice, CommerceContext commerceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		List<CommercePriceListDiscountRel> commercePriceListDiscountRels =
			_commercePriceListDiscountRelLocalService.
				getCommercePriceListDiscountRels(commercePriceListId);

		if ((commercePriceListDiscountRels != null) &&
			!commercePriceListDiscountRels.isEmpty()) {

			Stream<CommercePriceListDiscountRel> stream =
				commercePriceListDiscountRels.stream();

			long[] commerceDiscountIds = stream.mapToLong(
				CommercePriceListDiscountRel::getCommerceDiscountId
			).toArray();

			List<CommerceDiscount> commerceDiscounts =
				commerceDiscountLocalService.getPriceListCommerceDiscounts(
					commerceDiscountIds, cpInstance.getCPDefinitionId());

			if (commerceDiscounts.isEmpty()) {
				return null;
			}

			return _getCommerceDiscountValues(
				productUnitPrice, quantity, commerceContext, commerceDiscounts);
		}

		List<CommerceDiscount> commerceDiscounts =
			getProductCommerceDiscountByHierarchy(
				cpInstance.getCompanyId(), commerceContext.getCommerceAccount(),
				commerceContext.getCommerceChannelId(),
				cpInstance.getCPDefinitionId());

		if (commerceDiscounts.isEmpty()) {
			return null;
		}

		return _getCommerceDiscountValues(
			productUnitPrice, quantity, commerceContext, commerceDiscounts);
	}

	public void unsetCommerceDiscountApplicationStrategy(
		CommerceDiscountApplicationStrategy commerceDiscountApplicationStrategy,
		Map<String, Object> properties) {

		String commerceDiscountApplicationStrategyKey = GetterUtil.getString(
			properties.get("commerce.discount.application.strategy.key"));

		_commerceDiscountApplicationStrategyMap.remove(
			commerceDiscountApplicationStrategyKey);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setCommerceDiscountApplicationStrategy(
		CommerceDiscountApplicationStrategy commerceDiscountApplicationStrategy,
		Map<String, Object> properties) {

		String commerceDiscountApplicationStrategyKey = GetterUtil.getString(
			properties.get("commerce.discount.application.strategy.key"));

		_commerceDiscountApplicationStrategyMap.put(
			commerceDiscountApplicationStrategyKey,
			commerceDiscountApplicationStrategy);
	}

	private CommerceDiscountApplicationStrategy
			_getCommerceDiscountApplicationStrategy()
		throws ConfigurationException {

		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommercePricingConfiguration.class);

		String commerceDiscountApplicationStrategy =
			commercePricingConfiguration.commerceDiscountApplicationStrategy();

		if (!_commerceDiscountApplicationStrategyMap.containsKey(
				commerceDiscountApplicationStrategy)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"No commerce discount application strategy specified for " +
						commerceDiscountApplicationStrategy);
			}

			return null;
		}

		return _commerceDiscountApplicationStrategyMap.get(
			commerceDiscountApplicationStrategy);
	}

	private BigDecimal _getCommerceDiscountLevel(
			BigDecimal currentDiscountLevel, BigDecimal commercePrice,
			CommerceCurrency commerceCurrency, long commerceDiscountId,
			BigDecimal commerceDiscountValue, boolean usePercentage)
		throws PortalException {

		if ((commerceDiscountValue == null) ||
			CommerceBigDecimalUtil.isZero(commercePrice)) {

			return null;
		}

		BigDecimal discountAmount = BigDecimal.ZERO;

		if (usePercentage) {
			discountAmount = commercePrice.multiply(commerceDiscountValue);
			discountAmount = discountAmount.divide(_ONE_HUNDRED);

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceDiscountId);

			BigDecimal maximumDiscountAmount =
				commerceDiscount.getMaximumDiscountAmount();

			if (CommerceBigDecimalUtil.gt(
					maximumDiscountAmount, BigDecimal.ZERO) &&
				CommerceBigDecimalUtil.gt(
					discountAmount, maximumDiscountAmount)) {

				discountAmount = commerceDiscount.getMaximumDiscountAmount();
			}
		}
		else {
			discountAmount = commerceDiscountValue;

			if (CommerceBigDecimalUtil.gt(
					commerceDiscountValue, commercePrice)) {

				discountAmount = commercePrice;
			}
		}

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		BigDecimal discountedAmount = commercePrice.subtract(discountAmount);

		BigDecimal discountPercentage = _getDiscountPercentage(
			discountedAmount, commercePrice, roundingMode);

		if ((currentDiscountLevel == null) ||
			CommerceBigDecimalUtil.gt(
				discountPercentage, currentDiscountLevel)) {

			if (usePercentage) {
				return commerceDiscountValue;
			}

			return discountPercentage;
		}

		return currentDiscountLevel;
	}

	private BigDecimal[] _getCommerceDiscountLevels(
			String couponCode, BigDecimal commercePrice,
			CommerceContext commerceContext,
			List<CommerceDiscount> commerceDiscounts)
		throws PortalException {

		if (couponCode.isEmpty()) {
			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				couponCode = commerceOrder.getCouponCode();
			}
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		BigDecimal[] levels = {
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
		};

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			String discountCouponCode = commerceDiscount.getCouponCode();

			if (!_isValidCouponCode(
					commerceDiscount.getCommerceDiscountId(), couponCode,
					discountCouponCode, commerceContext)) {

				continue;
			}

			if (_isValidDiscount(commerceContext, commerceDiscount)) {
				String discountLevel = commerceDiscount.getLevel();

				if (discountLevel.isEmpty() ||
					discountLevel.equals(CommerceDiscountConstants.LEVEL_L1)) {

					levels[0] = _getCommerceDiscountLevel(
						levels[0], commercePrice, commerceCurrency,
						commerceDiscount.getCommerceDiscountId(),
						commerceDiscount.getLevel1(),
						commerceDiscount.isUsePercentage());
				}

				if (commerceDiscount.isUsePercentage()) {
					if (discountLevel.isEmpty() ||
						discountLevel.equals(
							CommerceDiscountConstants.LEVEL_L2)) {

						levels[1] = _getCommerceDiscountLevel(
							levels[1], commercePrice, commerceCurrency,
							commerceDiscount.getCommerceDiscountId(),
							commerceDiscount.getLevel2(),
							commerceDiscount.isUsePercentage());
					}

					if (discountLevel.isEmpty() ||
						discountLevel.equals(
							CommerceDiscountConstants.LEVEL_L3)) {

						levels[2] = _getCommerceDiscountLevel(
							levels[2], commercePrice, commerceCurrency,
							commerceDiscount.getCommerceDiscountId(),
							commerceDiscount.getLevel3(),
							commerceDiscount.isUsePercentage());
					}

					if (discountLevel.isEmpty() ||
						discountLevel.equals(
							CommerceDiscountConstants.LEVEL_L4)) {

						levels[3] = _getCommerceDiscountLevel(
							levels[3], commercePrice, commerceCurrency,
							commerceDiscount.getCommerceDiscountId(),
							commerceDiscount.getLevel4(),
							commerceDiscount.isUsePercentage());
					}
				}
			}
		}

		return levels;
	}

	private CommerceDiscountValue _getCommerceDiscountValue(
			CommerceOrder commerceOrder, BigDecimal amount,
			CommerceContext commerceContext, String discountType)
		throws PortalException {

		if ((amount == null) ||
			CommerceBigDecimalUtil.lte(amount, BigDecimal.ZERO)) {

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

		BigDecimal[] commerceDiscountLevels = _getCommerceDiscountLevels(
			commerceOrder.getCouponCode(), amount, commerceContext,
			commerceDiscounts);

		CommerceDiscountApplicationStrategy
			commerceDiscountApplicationStrategy =
				_getCommerceDiscountApplicationStrategy();

		BigDecimal discountedAmount =
			commerceDiscountApplicationStrategy.applyCommerceDiscounts(
				amount, commerceDiscountLevels);

		BigDecimal currentDiscountAmount = amount.subtract(discountedAmount);

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		currentDiscountAmount = currentDiscountAmount.setScale(
			_SCALE, roundingMode);

		CommerceMoney discountAmountCommerceMoney =
			_commerceMoneyFactory.create(
				commerceCurrency, currentDiscountAmount);

		return new CommerceDiscountValue(
			0, discountAmountCommerceMoney,
			_getDiscountPercentage(discountedAmount, amount, roundingMode),
			commerceDiscountLevels);
	}

	private CommerceDiscountValue _getCommerceDiscountValues(
			BigDecimal commercePrice, int quantity,
			CommerceContext commerceContext,
			List<CommerceDiscount> commerceDiscounts)
		throws PortalException {

		BigDecimal[] commerceDiscountLevels = _getCommerceDiscountLevels(
			StringPool.BLANK, commercePrice, commerceContext,
			commerceDiscounts);

		CommerceDiscountApplicationStrategy
			commerceDiscountApplicationStrategy =
				_getCommerceDiscountApplicationStrategy();

		BigDecimal discountedAmount =
			commerceDiscountApplicationStrategy.applyCommerceDiscounts(
				commercePrice, commerceDiscountLevels);

		BigDecimal currentDiscountAmount = commercePrice.subtract(
			discountedAmount);

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		currentDiscountAmount = currentDiscountAmount.setScale(
			_SCALE, roundingMode);

		if (CommerceBigDecimalUtil.isZero(currentDiscountAmount)) {
			return null;
		}

		CommerceMoney discountAmountCommerceMoney =
			_commerceMoneyFactory.create(
				commerceCurrency,
				currentDiscountAmount.multiply(new BigDecimal(quantity)));

		return new CommerceDiscountValue(
			0, discountAmountCommerceMoney,
			_getDiscountPercentage(
				discountedAmount, commercePrice, roundingMode),
			commerceDiscountLevels);
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

	private boolean _isValidCouponCode(
			long commerceDiscountId, String couponCode,
			String discountCouponCode, CommerceContext commerceContext)
		throws PortalException {

		long commerceAccountId = 0;

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		if ((Validator.isBlank(discountCouponCode) ||
			 Objects.equals(couponCode, discountCouponCode)) &&
			_commerceDiscountUsageEntryLocalService.
				validateDiscountLimitationUsage(
					commerceAccountId, commerceDiscountId)) {

			return true;
		}

		return false;
	}

	private boolean _isValidDiscount(
			CommerceContext commerceContext, CommerceDiscount commerceDiscount)
		throws PortalException {

		return _commerceDiscountValidatorHelper.isValid(
			commerceContext, commerceDiscount,
			CommerceDiscountConstants.VALIDATOR_TYPE_POST_QUALIFICATION);
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private static final int _SCALE = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountCalculationV2Impl.class);

	private final Map<String, CommerceDiscountApplicationStrategy>
		_commerceDiscountApplicationStrategyMap = new ConcurrentHashMap<>();

	@Reference
	private CommerceDiscountUsageEntryLocalService
		_commerceDiscountUsageEntryLocalService;

	@Reference
	private CommerceDiscountValidatorHelper _commerceDiscountValidatorHelper;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceListDiscountRelLocalService
		_commercePriceListDiscountRelLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}