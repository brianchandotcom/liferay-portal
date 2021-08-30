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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.internal.util.CommercePriceConverterUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceImpl;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.pricing.modifier.CommercePriceModifierHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Alberti
 */
public class CommerceProductPriceCalculationV2Impl
	extends BaseCommerceProductPriceCalculation {

	public CommerceProductPriceCalculationV2Impl(
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceDiscountCalculation commerceDiscountCalculation,
		CommerceMoneyFactory commerceMoneyFactory,
		CommercePriceEntryLocalService commercePriceEntryLocalService,
		CommercePriceListLocalService commercePriceListLocalService,
		CommercePriceModifierHelper commercePriceModifierHelper,
		CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService,
		CommerceTaxCalculation commerceTaxCalculation,
		ConfigurationProvider configurationProvider,
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService,
		CPInstanceLocalService cpInstanceLocalService,
		Map<String, CommerceDiscountApplicationStrategy>
			commerceDiscountApplicationStrategyMap,
		Map<String, CommercePriceListDiscovery> commercePriceListDiscoveryMap) {

		super(
			commerceMoneyFactory, commerceTaxCalculation,
			cpDefinitionOptionRelLocalService, cpInstanceLocalService);

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceDiscountCalculation = commerceDiscountCalculation;
		_commercePriceEntryLocalService = commercePriceEntryLocalService;
		_commercePriceListLocalService = commercePriceListLocalService;
		_commercePriceModifierHelper = commercePriceModifierHelper;
		_commerceTierPriceEntryLocalService =
			commerceTierPriceEntryLocalService;
		_configurationProvider = configurationProvider;
		_commerceDiscountApplicationStrategyMap =
			commerceDiscountApplicationStrategyMap;
		_commercePriceListDiscoveryMap = commercePriceListDiscoveryMap;
	}

	@Override
	public CommerceMoney getBasePrice(
			long cpInstanceId, CommerceCurrency commerceCurrency)
		throws PortalException {

		return commerceMoneyFactory.create(
			commerceCurrency,
			_getBasePrice(
				cpInstanceId, commerceCurrency,
				CommercePriceListConstants.TYPE_PRICE_LIST));
	}

	@Override
	public CommerceMoney getBasePromoPrice(
			long cpInstanceId, CommerceCurrency commerceCurrency)
		throws PortalException {

		return commerceMoneyFactory.create(
			commerceCurrency,
			_getBasePrice(
				cpInstanceId, commerceCurrency,
				CommercePriceListConstants.TYPE_PROMOTION));
	}

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			CommerceProductPriceRequest commerceProductPriceRequest)
		throws PortalException {

		long cpInstanceId = commerceProductPriceRequest.getCpInstanceId();
		int quantity = commerceProductPriceRequest.getQuantity();

		CommerceContext commerceContext =
			commerceProductPriceRequest.getCommerceContext();

		long commercePriceListId = _getCommercePriceListId(
			cpInstanceId, commerceContext);

		CommerceMoney unitPriceCommerceMoney = _getUnitPrice(
			commercePriceListId, cpInstanceId, quantity, commerceContext);

		BigDecimal finalPrice = unitPriceCommerceMoney.getPrice();

		long commercePromoPriceListId = _getCommercePromoPriceListId(
			cpInstanceId, commerceContext);

		CommerceMoney promoPriceCommerceMoney = _getPromoPrice(
			commercePromoPriceListId, cpInstanceId, quantity, commerceContext);

		if (!promoPriceCommerceMoney.isEmpty() &&
			CommerceBigDecimalUtil.gt(
				promoPriceCommerceMoney.getPrice(), BigDecimal.ZERO) &&
			CommerceBigDecimalUtil.lte(
				promoPriceCommerceMoney.getPrice(),
				unitPriceCommerceMoney.getPrice())) {

			finalPrice = promoPriceCommerceMoney.getPrice();

			commercePriceListId = commercePromoPriceListId;
		}

		BigDecimal[] updatedPrices = getUpdatedPrices(
			unitPriceCommerceMoney, promoPriceCommerceMoney, finalPrice,
			commerceContext,
			commerceProductPriceRequest.getCommerceOptionValues());

		finalPrice = updatedPrices[2];

		CommerceDiscountValue commerceDiscountValue;

		BigDecimal finalPriceWithTaxAmount = getConvertedPrice(
			cpInstanceId, finalPrice, false, commerceContext);

		boolean discountsTargetNetPrice = true;

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(
				commerceContext.getCommerceChannelId());

		if (commerceChannel != null) {
			discountsTargetNetPrice =
				commerceChannel.isDiscountsTargetNetPrice();
		}

		if (discountsTargetNetPrice) {
			commerceDiscountValue = _getCommerceDiscountValue(
				cpInstanceId, commercePriceListId, quantity, finalPrice,
				commerceContext);

			finalPrice = finalPrice.multiply(BigDecimal.valueOf(quantity));

			if (commerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					commerceDiscountValue.getDiscountAmount();

				finalPrice = finalPrice.subtract(
					discountAmountCommerceMoney.getPrice());
			}

			finalPriceWithTaxAmount = getConvertedPrice(
				cpInstanceId, finalPrice, false, commerceContext);
		}
		else {
			commerceDiscountValue = _getCommerceDiscountValue(
				cpInstanceId, commercePriceListId, quantity,
				finalPriceWithTaxAmount, commerceContext);

			finalPriceWithTaxAmount = finalPriceWithTaxAmount.multiply(
				BigDecimal.valueOf(quantity));

			if (commerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					commerceDiscountValue.getDiscountAmount();

				finalPriceWithTaxAmount = finalPriceWithTaxAmount.subtract(
					discountAmountCommerceMoney.getPrice());
			}

			finalPrice = getConvertedPrice(
				cpInstanceId, finalPriceWithTaxAmount, true, commerceContext);
		}

		// fill data

		CommerceProductPriceImpl commerceProductPriceImpl =
			_getCommerceProductPriceImpl();

		commerceProductPriceImpl.setCommercePriceListId(commercePriceListId);
		commerceProductPriceImpl.setUnitPrice(
			commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), updatedPrices[0]));
		commerceProductPriceImpl.setUnitPromoPrice(
			commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), updatedPrices[1]));
		commerceProductPriceImpl.setQuantity(quantity);

		if (discountsTargetNetPrice) {
			commerceProductPriceImpl.setCommerceDiscountValue(
				commerceDiscountValue);
		}
		else {
			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			commerceProductPriceImpl.setCommerceDiscountValue(
				CommercePriceConverterUtil.getConvertedCommerceDiscountValue(
					commerceDiscountValue,
					updatedPrices[2].multiply(BigDecimal.valueOf(quantity)),
					finalPrice, commerceMoneyFactory,
					RoundingMode.valueOf(commerceCurrency.getRoundingMode())));
		}

		commerceProductPriceImpl.setFinalPrice(
			commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), finalPrice));

		if (commerceProductPriceRequest.isCalculateTax() ||
			_hasGrossPricePriceList(cpInstanceId, commerceContext)) {

			setCommerceProductPriceWithTaxAmount(
				cpInstanceId, finalPriceWithTaxAmount, commerceProductPriceImpl,
				commerceContext, commerceDiscountValue,
				discountsTargetNetPrice);
		}

		return commerceProductPriceImpl;
	}

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			long cpInstanceId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(secure);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			Collections.emptyList());

		return getCommerceProductPrice(commerceProductPriceRequest);
	}

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		return getCommerceProductPrice(
			cpInstanceId, quantity, true, commerceContext);
	}

	@Override
	public CommerceMoney getFinalPrice(
			long cpInstanceId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceProductPrice commerceProductPrice = getCommerceProductPrice(
			cpInstanceId, quantity, commerceContext);

		if (commerceProductPrice == null) {
			return commerceMoneyFactory.emptyCommerceMoney();
		}

		return commerceProductPrice.getFinalPrice();
	}

	@Override
	public CommerceMoney getFinalPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		return getFinalPrice(cpInstanceId, quantity, true, commerceContext);
	}

	@Override
	public CommerceMoney getPromoPrice(
			long cpInstanceId, int quantity, CommerceCurrency commerceCurrency,
			boolean secure, CommerceContext commerceContext)
		throws PortalException {

		long commercePromoPriceListId = _getCommercePromoPriceListId(
			cpInstanceId, commerceContext);

		return _getPromoPrice(
			commercePromoPriceListId, cpInstanceId, quantity, commerceContext);
	}

	@Override
	public CommerceMoney getUnitMaxPrice(
			long cpDefinitionId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = commerceMoneyFactory.emptyCommerceMoney();

		List<CPInstance> cpInstances =
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommerceCurrency(), secure, commerceContext);

			if (commerceMoney.isEmpty() ||
				CommerceBigDecimalUtil.lt(
					commerceMoney.getPrice(),
					cpInstanceCommerceMoney.getPrice())) {

				commerceMoney = cpInstanceCommerceMoney;
			}
		}

		return commerceMoney;
	}

	@Override
	public CommerceMoney getUnitMaxPrice(
			long cpDefinitionId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		return getUnitMaxPrice(cpDefinitionId, quantity, true, commerceContext);
	}

	@Override
	public CommerceMoney getUnitMinPrice(
			long cpDefinitionId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = commerceMoneyFactory.emptyCommerceMoney();

		List<CPInstance> cpInstances =
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommerceCurrency(), secure, commerceContext);

			if (commerceMoney.isEmpty() ||
				CommerceBigDecimalUtil.gt(
					commerceMoney.getPrice(),
					cpInstanceCommerceMoney.getPrice())) {

				commerceMoney = cpInstanceCommerceMoney;
			}
		}

		return commerceMoney;
	}

	@Override
	public CommerceMoney getUnitMinPrice(
			long cpDefinitionId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		return getUnitMinPrice(cpDefinitionId, quantity, true, commerceContext);
	}

	@Override
	public CommerceMoney getUnitPrice(
			long cpInstanceId, int quantity, CommerceCurrency commerceCurrency,
			boolean secure, CommerceContext commerceContext)
		throws PortalException {

		long commercePriceListId = _getCommercePriceListId(
			cpInstanceId, commerceContext);

		return _getUnitPrice(
			commercePriceListId, cpInstanceId, quantity, commerceContext);
	}

	private CommerceDiscountValue _calculateCommerceDiscountValue(
			BigDecimal[] values, int quantity, BigDecimal finalPrice,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		CommerceDiscountApplicationStrategy
			commerceDiscountApplicationStrategy =
				_getCommerceDiscountApplicationStrategy();

		BigDecimal discountedAmount =
			commerceDiscountApplicationStrategy.applyCommerceDiscounts(
				finalPrice, values);

		BigDecimal currentDiscountAmount = finalPrice.subtract(
			discountedAmount);

		currentDiscountAmount = currentDiscountAmount.setScale(
			_SCALE, roundingMode);

		CommerceMoney discountAmountCommerceMoney = commerceMoneyFactory.create(
			commerceCurrency,
			currentDiscountAmount.multiply(new BigDecimal(quantity)));

		return new CommerceDiscountValue(
			0, discountAmountCommerceMoney,
			_getDiscountPercentage(discountedAmount, finalPrice, roundingMode),
			values);
	}

	private BigDecimal _getBasePrice(
			long cpInstanceId, CommerceCurrency commerceCurrency, String type)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.
				fetchCatalogBaseCommercePriceListByType(
					cpInstance.getGroupId(), type);

		if (commercePriceList == null) {
			return BigDecimal.ZERO;
		}

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), false);

		if (commercePriceEntry == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal price = commercePriceEntry.getPrice();

		CommerceCurrency priceListCurrency =
			commercePriceList.getCommerceCurrency();

		if (priceListCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				priceListCurrency.getRate(),
				RoundingMode.valueOf(priceListCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		return price;
	}

	private long _getBasePriceListId(CPInstance cpInstance)
		throws PortalException {

		CommerceCatalog commerceCatalog = cpInstance.getCommerceCatalog();

		CommercePriceList basePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				commerceCatalog.getGroupId());

		if (basePriceList != null) {
			return basePriceList.getCommercePriceListId();
		}

		_log.error(
			"There is no base price list configured for the current catalog");

		return 0;
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

	private CommerceDiscountValue _getCommerceDiscountValue(
			long cpInstanceId, long commercePriceListId, int quantity,
			BigDecimal finalPrice, CommerceContext commerceContext)
		throws PortalException {

		if ((finalPrice == null) ||
			CommerceBigDecimalUtil.lte(finalPrice, BigDecimal.ZERO)) {

			return null;
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceListId, cpInstance.getCPInstanceUuid(), true);

		if (commercePriceEntry == null) {
			return _commerceDiscountCalculation.getProductCommerceDiscountValue(
				cpInstanceId, quantity, finalPrice, commerceContext);
		}

		BigDecimal[] values = new BigDecimal[4];

		if (!commercePriceEntry.isHasTierPrice() &&
			!commercePriceEntry.isDiscountDiscovery()) {

			values[0] = commercePriceEntry.getDiscountLevel1();
			values[1] = commercePriceEntry.getDiscountLevel2();
			values[2] = commercePriceEntry.getDiscountLevel3();
			values[3] = commercePriceEntry.getDiscountLevel4();

			return _calculateCommerceDiscountValue(
				values, quantity, finalPrice, commerceContext);
		}

		if (!commercePriceEntry.isBulkPricing()) {
			return _commerceDiscountCalculation.getProductCommerceDiscountValue(
				cpInstanceId, quantity, finalPrice, commerceContext);
		}

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryLocalService.
				findClosestCommerceTierPriceEntry(
					commercePriceEntry.getCommercePriceEntryId(), quantity);

		if ((commerceTierPriceEntry == null) ||
			commerceTierPriceEntry.isDiscountDiscovery()) {

			return _commerceDiscountCalculation.getProductCommerceDiscountValue(
				cpInstanceId, quantity, finalPrice, commerceContext);
		}

		values[0] = commerceTierPriceEntry.getDiscountLevel1();
		values[1] = commerceTierPriceEntry.getDiscountLevel2();
		values[2] = commerceTierPriceEntry.getDiscountLevel3();
		values[3] = commerceTierPriceEntry.getDiscountLevel4();

		return _calculateCommerceDiscountValue(
			values, quantity, finalPrice, commerceContext);
	}

	private CommerceMoney _getCommerceMoney(
			long commercePriceListId, CommerceCurrency commerceCurrency,
			BigDecimal price)
		throws PortalException {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		CommerceCurrency priceListCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commercePriceList.getCommerceCurrencyId());

		if (priceListCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				priceListCurrency.getRate(),
				RoundingMode.valueOf(priceListCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		if (price != null) {
			return commerceMoneyFactory.create(commerceCurrency, price);
		}

		return commerceMoneyFactory.emptyCommerceMoney();
	}

	private BigDecimal _getCommercePrice(
			long commercePriceListId, CommercePriceEntry commercePriceEntry,
			int quantity)
		throws PortalException {

		if (commercePriceEntry == null) {
			return null;
		}

		BigDecimal commercePrice = commercePriceEntry.getPrice();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceEntry.getCommercePriceListId());

		CommercePriceList modifierCommercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commercePriceList.getCommerceCurrencyId());

		if (!commercePriceEntry.isHasTierPrice()) {
			if ((commercePriceEntry.getCommercePriceListId() !=
					commercePriceListId) &&
				(commercePriceList.isNetPrice() ==
					modifierCommercePriceList.isNetPrice())) {

				commercePrice =
					_commercePriceModifierHelper.applyCommercePriceModifier(
						commercePriceListId, cpInstance.getCPDefinitionId(),
						commercePriceEntry.getPriceCommerceMoney(
							commerceCurrency.getCommerceCurrencyId()));
			}

			return commercePrice;
		}

		if (commercePriceEntry.isBulkPricing()) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				_commerceTierPriceEntryLocalService.
					findClosestCommerceTierPriceEntry(
						commercePriceEntry.getCommercePriceEntryId(), quantity);

			if (commerceTierPriceEntry == null) {
				return commercePriceEntry.getPrice();
			}

			if ((commercePriceEntry.getCommercePriceListId() !=
					commercePriceListId) &&
				(commercePriceList.isNetPrice() ==
					modifierCommercePriceList.isNetPrice())) {

				return _commercePriceModifierHelper.applyCommercePriceModifier(
					commercePriceListId, cpInstance.getCPDefinitionId(),
					commerceTierPriceEntry.getPriceCommerceMoney(
						commerceCurrency.getCommerceCurrencyId()));
			}

			return commerceTierPriceEntry.getPrice();
		}

		if ((commercePriceEntry.getCommercePriceListId() !=
				commercePriceListId) &&
			(commercePriceList.isNetPrice() ==
				modifierCommercePriceList.isNetPrice())) {

			return _commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceListId, cpInstance.getCPDefinitionId(),
				commerceMoneyFactory.create(commerceCurrency, commercePrice));
		}

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			_commerceTierPriceEntryLocalService.findCommerceTierPriceEntries(
				commercePriceEntry.getCommercePriceEntryId(), quantity);

		if (commerceTierPriceEntries.isEmpty()) {
			return commercePrice;
		}

		commercePrice = BigDecimal.ZERO;

		CommerceTierPriceEntry commerceTierPriceEntry1 =
			commerceTierPriceEntries.get(0);

		int totalTierCounter = 0;

		int tierCounter =
			commerceTierPriceEntry1.getMinQuantity() - totalTierCounter - 1;

		BigDecimal currentPrice = commercePriceEntry.getPrice();

		currentPrice = currentPrice.multiply(BigDecimal.valueOf(tierCounter));

		commercePrice = commercePrice.add(currentPrice);

		totalTierCounter += tierCounter;

		for (int i = 0; i < (commerceTierPriceEntries.size() - 1); i++) {
			CommerceTierPriceEntry commerceTierPriceEntry2 =
				commerceTierPriceEntries.get(i);

			currentPrice = commerceTierPriceEntry2.getPrice();

			CommerceTierPriceEntry commerceTierPriceEntry3 =
				commerceTierPriceEntries.get(i + 1);

			tierCounter =
				commerceTierPriceEntry3.getMinQuantity() - totalTierCounter - 1;

			currentPrice = currentPrice.multiply(
				BigDecimal.valueOf(tierCounter));

			commercePrice = commercePrice.add(currentPrice);

			totalTierCounter += tierCounter;
		}

		totalTierCounter = quantity - totalTierCounter;

		CommerceTierPriceEntry commerceTierPriceEntry2 =
			commerceTierPriceEntries.get(commerceTierPriceEntries.size() - 1);

		currentPrice = commerceTierPriceEntry2.getPrice();

		currentPrice = currentPrice.multiply(
			BigDecimal.valueOf(totalTierCounter));

		commercePrice = commercePrice.add(currentPrice);

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		return commercePrice.divide(
			BigDecimal.valueOf(quantity), _SCALE, roundingMode);
	}

	private BigDecimal _getCommercePrice(
			long cpInstanceId, long commercePriceListId,
			CommerceMoney unitPriceCommerceMoney)
		throws PortalException {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList == null) {
			return null;
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		return _commercePriceModifierHelper.applyCommercePriceModifier(
			commercePriceListId, cpInstance.getCPDefinitionId(),
			unitPriceCommerceMoney);
	}

	private CommercePriceList _getCommercePriceList(
			long cpInstanceId, CommerceContext commerceContext, String type)
		throws PortalException {

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		CommercePriceListDiscovery commercePriceListDiscovery =
			_getCommercePriceListDiscovery(type);

		if (commercePriceListDiscovery == null) {
			return null;
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		long commerceOrderTypeId = 0;

		if (commerceOrder != null) {
			commerceOrderTypeId = commerceOrder.getCommerceOrderTypeId();
		}

		return commercePriceListDiscovery.getCommercePriceList(
			cpInstance.getGroupId(), commerceAccountId,
			commerceContext.getCommerceChannelId(), commerceOrderTypeId,
			cpInstance.getCPInstanceUuid(), type);
	}

	private CommercePriceListDiscovery _getCommercePriceListDiscovery(
			String type)
		throws PortalException {

		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommercePricingConfiguration.class);

		String discoveryMethod = CommercePricingConstants.ORDER_BY_HIERARCHY;

		if (type.equals(CommercePriceListConstants.TYPE_PRICE_LIST)) {
			discoveryMethod =
				commercePricingConfiguration.commercePriceListDiscovery();
		}
		else if (type.equals(CommercePriceListConstants.TYPE_PROMOTION)) {
			discoveryMethod =
				commercePricingConfiguration.commercePromotionDiscovery();
		}

		if (!_commercePriceListDiscoveryMap.containsKey(discoveryMethod)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No commerce price list discovery specified for " +
						discoveryMethod);
			}

			return null;
		}

		return _commercePriceListDiscoveryMap.get(discoveryMethod);
	}

	private long _getCommercePriceListId(
			long cpInstanceId, CommerceContext commerceContext)
		throws PortalException {

		CommercePriceList commercePriceList = _getCommercePriceList(
			cpInstanceId, commerceContext,
			CommercePriceListConstants.TYPE_PRICE_LIST);

		long commercePriceListId = 0;

		if (commercePriceList != null) {
			commercePriceListId = commercePriceList.getCommercePriceListId();
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceListId, cpInstance.getCPInstanceUuid(), true);

		if (commercePriceEntry != null) {
			return commercePriceEntry.getCommercePriceListId();
		}

		boolean hasCommercePriceModifiers =
			_commercePriceModifierHelper.hasCommercePriceModifiers(
				commercePriceListId, cpInstance.getCPDefinitionId());

		if (hasCommercePriceModifiers) {
			return commercePriceListId;
		}

		return _getBasePriceListId(cpInstance);
	}

	private CommerceProductPriceImpl _getCommerceProductPriceImpl() {
		CommerceProductPriceImpl commerceProductPriceImpl =
			new CommerceProductPriceImpl();

		commerceProductPriceImpl.setFinalPrice(
			commerceMoneyFactory.emptyCommerceMoney());
		commerceProductPriceImpl.setFinalPriceWithTaxAmount(
			commerceMoneyFactory.emptyCommerceMoney());
		commerceProductPriceImpl.setUnitPrice(
			commerceMoneyFactory.emptyCommerceMoney());
		commerceProductPriceImpl.setUnitPriceWithTaxAmount(
			commerceMoneyFactory.emptyCommerceMoney());
		commerceProductPriceImpl.setUnitPromoPrice(
			commerceMoneyFactory.emptyCommerceMoney());
		commerceProductPriceImpl.setUnitPromoPriceWithTaxAmount(
			commerceMoneyFactory.emptyCommerceMoney());

		return commerceProductPriceImpl;
	}

	private long _getCommercePromoPriceListId(
			long cpInstanceId, CommerceContext commerceContext)
		throws PortalException {

		CommercePriceList commercePriceList = _getCommercePriceList(
			cpInstanceId, commerceContext,
			CommercePriceListConstants.TYPE_PROMOTION);

		if (commercePriceList != null) {
			return commercePriceList.getCommercePriceListId();
		}

		return 0;
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

	private CommerceMoney _getPromoPrice(
			long commercePriceListId, long cpInstanceId, int quantity,
			CommerceContext commerceContext)
		throws PortalException {

		if (commercePriceListId <= 0) {
			return commerceMoneyFactory.emptyCommerceMoney();
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceListId, cpInstance.getCPInstanceUuid(), true);

		if (commercePriceEntry != null) {
			BigDecimal promoPrice = _getCommercePrice(
				commercePriceListId, commercePriceEntry, quantity);

			if (!commercePriceList.isNetPrice()) {
				promoPrice = getConvertedPrice(
					cpInstance.getCPInstanceId(), promoPrice, true,
					commerceContext);
			}

			return _getCommerceMoney(
				commercePriceListId, commerceContext.getCommerceCurrency(),
				promoPrice);
		}

		if (!_commercePriceModifierHelper.hasCommercePriceModifiers(
				commercePriceListId, cpInstance.getCPDefinitionId())) {

			return commerceMoneyFactory.emptyCommerceMoney();
		}

		CommerceMoney unitPriceCommerceMoney = getUnitPrice(
			cpInstanceId, quantity, commerceContext.getCommerceCurrency(),
			false, commerceContext);

		BigDecimal promoPrice = _getCommercePrice(
			cpInstanceId, commercePriceListId, unitPriceCommerceMoney);

		if (!commercePriceList.isNetPrice()) {
			promoPrice = getConvertedPrice(
				cpInstance.getCPInstanceId(), promoPrice, true,
				commerceContext);
		}

		return _getCommerceMoney(
			commercePriceListId, commerceContext.getCommerceCurrency(),
			promoPrice);
	}

	private CommerceMoney _getUnitPrice(
			long commercePriceListId, long cpInstanceId, int quantity,
			CommerceContext commerceContext)
		throws PortalException {

		if (commercePriceListId == 0) {
			return commerceMoneyFactory.emptyCommerceMoney();
		}

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceListId, cpInstance.getCPInstanceUuid(), true);

		if (commercePriceEntry != null) {
			BigDecimal unitPrice = _getCommercePrice(
				commercePriceList.getCommercePriceListId(), commercePriceEntry,
				quantity);

			if (!commercePriceList.isNetPrice()) {
				unitPrice = getConvertedPrice(
					cpInstance.getCPInstanceId(), unitPrice, true,
					commerceContext);
			}

			return _getCommerceMoney(
				commercePriceListId, commerceContext.getCommerceCurrency(),
				unitPrice);
		}

		CommercePriceEntry commerceBasePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				_getBasePriceListId(cpInstance), cpInstance.getCPInstanceUuid(),
				false);

		if (commerceBasePriceEntry == null) {
			return commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
		}

		BigDecimal unitPrice = _getCommercePrice(
			commercePriceList.getCommercePriceListId(), commerceBasePriceEntry,
			quantity);

		if (!commercePriceList.isNetPrice()) {
			unitPrice = getConvertedPrice(
				cpInstance.getCPInstanceId(), unitPrice, true, commerceContext);
		}

		return _getCommerceMoney(
			commercePriceListId, commerceContext.getCommerceCurrency(),
			unitPrice);
	}

	private boolean _hasGrossPricePriceList(
			long cpInstanceId, CommerceContext commerceContext)
		throws PortalException {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCommercePriceList(
				_getCommercePriceListId(cpInstanceId, commerceContext));

		if ((commercePriceList != null) && !commercePriceList.isNetPrice()) {
			return true;
		}

		CommercePriceList commercePromoPriceList =
			_commercePriceListLocalService.fetchCommercePriceList(
				_getCommercePromoPriceListId(cpInstanceId, commerceContext));

		if ((commercePromoPriceList != null) &&
			!commercePromoPriceList.isNetPrice()) {

			return true;
		}

		return false;
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private static final int _SCALE = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductPriceCalculationV2Impl.class);

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final Map<String, CommerceDiscountApplicationStrategy>
		_commerceDiscountApplicationStrategyMap;
	private final CommerceDiscountCalculation _commerceDiscountCalculation;
	private final CommercePriceEntryLocalService
		_commercePriceEntryLocalService;
	private final Map<String, CommercePriceListDiscovery>
		_commercePriceListDiscoveryMap;
	private final CommercePriceListLocalService _commercePriceListLocalService;
	private final CommercePriceModifierHelper _commercePriceModifierHelper;
	private final CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;
	private final ConfigurationProvider _configurationProvider;

}