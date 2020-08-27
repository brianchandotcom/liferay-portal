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
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.internal.util.CommercePriceConverterUtil;
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
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class CommerceProductPriceCalculationImpl
	extends BaseCommerceProductPriceCalculation {

	public CommerceProductPriceCalculationImpl(
		CommerceCatalogLocalService commerceCatalogLocalService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceDiscountCalculation commerceDiscountCalculation,
		CommerceMoneyFactory commerceMoneyFactory,
		CommercePriceEntryLocalService commercePriceEntryLocalService,
		CommercePriceListDiscovery commercePriceListDiscovery,
		CommercePriceListLocalService commercePriceListLocalService,
		CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService,
		CommerceTaxCalculation commerceTaxCalculation,
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService,
		CPInstanceLocalService cpInstanceLocalService) {

		super(
			commerceMoneyFactory, commerceTaxCalculation,
			cpDefinitionOptionRelLocalService, cpInstanceLocalService);

		_commerceCatalogLocalService = commerceCatalogLocalService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceDiscountCalculation = commerceDiscountCalculation;
		_commercePriceEntryLocalService = commercePriceEntryLocalService;
		_commercePriceListDiscovery = commercePriceListDiscovery;
		_commercePriceListLocalService = commercePriceListLocalService;
		_commerceTierPriceEntryLocalService =
			commerceTierPriceEntryLocalService;
	}

	@Override
	public CommerceMoney getBasePrice(
			long cpInstanceId, CommerceCurrency commerceCurrency)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		return _getCurrencyConvertedPrice(
			cpInstance.getGroupId(), commerceCurrency, cpInstance.getPrice());
	}

	@Override
	public CommerceMoney getBasePromoPrice(
			long cpInstanceId, CommerceCurrency commerceCurrency)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		return _getCurrencyConvertedPrice(
			cpInstance.getGroupId(), commerceCurrency,
			cpInstance.getPromoPrice());
	}

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			CommerceProductPriceRequest commerceProductPriceRequest)
		throws PortalException {

		long cpInstanceId = commerceProductPriceRequest.getCpInstanceId();
		int quantity = commerceProductPriceRequest.getQuantity();
		boolean secure = commerceProductPriceRequest.isSecure();

		CommerceContext commerceContext =
			commerceProductPriceRequest.getCommerceContext();

		CommerceMoney unitPriceMoney = getUnitPrice(
			cpInstanceId, quantity, commerceContext.getCommerceCurrency(),
			secure, commerceContext);

		BigDecimal finalPrice = unitPriceMoney.getPrice();

		CommerceMoney promoPriceMoney = getPromoPrice(
			cpInstanceId, quantity, commerceContext.getCommerceCurrency(),
			secure, commerceContext);

		BigDecimal promoPrice = promoPriceMoney.getPrice();

		BigDecimal unitPrice = unitPriceMoney.getPrice();

		if ((promoPrice != null) &&
			(promoPrice.compareTo(BigDecimal.ZERO) > 0) &&
			(promoPrice.compareTo(unitPrice) <= 0)) {

			finalPrice = promoPriceMoney.getPrice();
		}

		BigDecimal[] updatedPrices = getUpdatedPrices(
			unitPrice, promoPrice, finalPrice, commerceContext,
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
			commerceDiscountValue =
				_commerceDiscountCalculation.getProductCommerceDiscountValue(
					cpInstanceId, quantity, finalPrice, commerceContext);

			finalPrice = finalPrice.multiply(BigDecimal.valueOf(quantity));

			if (commerceDiscountValue != null) {
				CommerceMoney discountAmountMoney =
					commerceDiscountValue.getDiscountAmount();

				finalPrice = finalPrice.subtract(
					discountAmountMoney.getPrice());
			}

			finalPriceWithTaxAmount = getConvertedPrice(
				cpInstanceId, finalPrice, false, commerceContext);
		}
		else {
			commerceDiscountValue =
				_commerceDiscountCalculation.getProductCommerceDiscountValue(
					cpInstanceId, quantity, finalPriceWithTaxAmount,
					commerceContext);

			finalPriceWithTaxAmount = finalPriceWithTaxAmount.multiply(
				BigDecimal.valueOf(quantity));

			if (commerceDiscountValue != null) {
				CommerceMoney discountAmountMoney =
					commerceDiscountValue.getDiscountAmount();

				finalPriceWithTaxAmount = finalPriceWithTaxAmount.subtract(
					discountAmountMoney.getPrice());
			}

			finalPrice = getConvertedPrice(
				cpInstanceId, finalPriceWithTaxAmount, true, commerceContext);
		}

		// fill data

		CommerceProductPriceImpl commerceProductPriceImpl =
			new CommerceProductPriceImpl();

		commerceProductPriceImpl.setCommercePriceListId(
			_getUsedCommercePriceList(cpInstanceId, commerceContext));
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

		if (commerceProductPriceRequest.isCalculateTax()) {
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

		boolean calculateTax = false;

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(
				commerceContext.getCommerceChannelId());

		if (commerceChannel != null) {
			calculateTax = Objects.equals(
				commerceChannel.getPriceDisplayType(),
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE);
		}

		long commercePriceListId = _getUsedCommercePriceList(
			cpInstanceId, commerceContext);

		if (commercePriceListId > 0) {
			CommercePriceList commercePriceList =
				_commercePriceListLocalService.getCommercePriceList(
					commercePriceListId);

			calculateTax = calculateTax || !commercePriceList.isNetPrice();
		}

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(secure);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(null);
		commerceProductPriceRequest.setCalculateTax(calculateTax);

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
			return null;
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

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		Optional<CommercePriceList> commercePriceList = _getPriceList(
			cpInstance.getGroupId(), commerceContext);

		if (commercePriceList.isPresent()) {
			Optional<BigDecimal> priceListPrice = _getPriceListPrice(
				cpInstanceId, quantity, commercePriceList.get(),
				commerceContext, true);

			if (priceListPrice.isPresent()) {
				return commerceMoneyFactory.create(
					commerceCurrency, priceListPrice.get());
			}
		}

		return _getCurrencyConvertedPrice(
			cpInstance.getGroupId(), commerceCurrency,
			cpInstance.getPromoPrice());
	}

	@Override
	public CommerceMoney getUnitMaxPrice(
			long cpDefinitionId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = null;
		BigDecimal maxPrice = BigDecimal.ZERO;

		List<CPInstance> cpInstances =
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommerceCurrency(), secure, commerceContext);

			if (maxPrice.compareTo(cpInstanceCommerceMoney.getPrice()) < 0) {
				commerceMoney = cpInstanceCommerceMoney;

				maxPrice = commerceMoney.getPrice();
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

		CommerceMoney commerceMoney = null;
		BigDecimal minPrice = BigDecimal.ZERO;

		List<CPInstance> cpInstances =
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommerceCurrency(), secure, commerceContext);

			if ((commerceMoney == null) ||
				(minPrice.compareTo(cpInstanceCommerceMoney.getPrice()) > 0)) {

				commerceMoney = cpInstanceCommerceMoney;

				minPrice = commerceMoney.getPrice();
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

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		Optional<CommercePriceList> commercePriceList = _getPriceList(
			cpInstance.getGroupId(), commerceContext);

		if (commercePriceList.isPresent()) {
			Optional<BigDecimal> priceListPrice = _getPriceListPrice(
				cpInstanceId, quantity, commercePriceList.get(),
				commerceContext, false);

			if (priceListPrice.isPresent()) {
				return commerceMoneyFactory.create(
					commerceCurrency, priceListPrice.get());
			}
		}

		return _getCurrencyConvertedPrice(
			cpInstance.getGroupId(), commerceCurrency, cpInstance.getPrice());
	}

	private CommerceMoney _getCurrencyConvertedPrice(
			long groupId, CommerceCurrency commerceCurrency, BigDecimal price)
		throws NoSuchCurrencyException {

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(groupId);

		CommerceCurrency catalogCommerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceCatalog.getCompanyId(),
				commerceCatalog.getCommerceCurrencyCode());

		if (catalogCommerceCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				catalogCommerceCurrency.getRate(),
				RoundingMode.valueOf(
					catalogCommerceCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		return commerceMoneyFactory.create(commerceCurrency, price);
	}

	private Optional<CommercePriceList> _getPriceList(
			long groupId, CommerceContext commerceContext)
		throws PortalException {

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		CommercePriceList commercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				groupId, commerceAccountId,
				commerceContext.getCommerceChannelId(), null,
				CommercePriceListConstants.TYPE_PRICE_LIST);

		return Optional.ofNullable(commercePriceList);
	}

	private Optional<BigDecimal> _getPriceListPrice(
			long cpInstanceId, int quantity,
			CommercePriceList commercePriceList,
			CommerceContext commerceContext, boolean promo)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		BigDecimal price = null;

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), true);

		if (commercePriceEntry == null) {
			return Optional.empty();
		}

		if (promo) {
			price = commercePriceEntry.getPromoPrice();
		}
		else {
			price = commercePriceEntry.getPrice();
		}

		if (commercePriceEntry.isHasTierPrice()) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				_commerceTierPriceEntryLocalService.
					findClosestCommerceTierPriceEntry(
						commercePriceEntry.getCommercePriceEntryId(), quantity);

			if (commerceTierPriceEntry != null) {
				if (promo) {
					price = commerceTierPriceEntry.getPromoPrice();
				}
				else {
					price = commerceTierPriceEntry.getPrice();
				}
			}
		}

		if (!commercePriceList.isNetPrice()) {
			price = getConvertedPrice(
				cpInstance.getCPInstanceId(), price, true, commerceContext);
		}

		CommerceCurrency priceListCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commercePriceList.getCommerceCurrencyId());

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		if (priceListCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				priceListCurrency.getRate(),
				RoundingMode.valueOf(priceListCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		return Optional.ofNullable(price);
	}

	private long _getUsedCommercePriceList(
			long cpInstanceId, CommerceContext commerceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		Optional<CommercePriceList> commercePriceList = _getPriceList(
			cpInstance.getGroupId(), commerceContext);

		if (commercePriceList.isPresent()) {
			CommercePriceList commercePriceListActive = commercePriceList.get();

			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryLocalService.fetchCommercePriceEntry(
					commercePriceListActive.getCommercePriceListId(),
					cpInstance.getCPInstanceUuid(), true);

			if (commercePriceEntry == null) {
				return 0;
			}

			return commercePriceEntry.getCommercePriceListId();
		}

		return 0;
	}

	private final CommerceCatalogLocalService _commerceCatalogLocalService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommerceDiscountCalculation _commerceDiscountCalculation;
	private final CommercePriceEntryLocalService
		_commercePriceEntryLocalService;
	private final CommercePriceListDiscovery _commercePriceListDiscovery;
	private final CommercePriceListLocalService _commercePriceListLocalService;
	private final CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

}