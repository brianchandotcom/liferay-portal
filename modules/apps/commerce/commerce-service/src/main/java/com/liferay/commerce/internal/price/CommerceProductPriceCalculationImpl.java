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
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceImpl;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "commerce.price.calculation.key=v1.0",
	service = CommerceProductPriceCalculation.class
)
public class CommerceProductPriceCalculationImpl
	implements CommerceProductPriceCalculation {

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			long cpInstanceId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney unitPriceMoney = getUnitPrice(
			cpInstanceId, quantity, commerceContext.getCommerceCurrency(),
			secure, commerceContext);

		CommerceMoney promoPriceMoney = getPromoPrice(
			cpInstanceId, quantity, commerceContext.getCommerceCurrency(),
			secure, commerceContext);

		CommerceProductPriceImpl commerceProductPriceImpl =
			new CommerceProductPriceImpl();

		commerceProductPriceImpl.setQuantity(quantity);
		commerceProductPriceImpl.setUnitPrice(unitPriceMoney);
		commerceProductPriceImpl.setUnitPromoPrice(promoPriceMoney);

		BigDecimal finalPrice = unitPriceMoney.getPrice();

		BigDecimal promoPrice = promoPriceMoney.getPrice();

		if ((promoPrice != null) &&
			(promoPrice.compareTo(BigDecimal.ZERO) > 0) &&
			(promoPrice.compareTo(unitPriceMoney.getPrice()) <= 0)) {

			finalPrice = promoPriceMoney.getPrice();
		}

		CommerceDiscountValue commerceDiscountValue =
			_commerceDiscountCalculation.getProductCommerceDiscountValue(
				cpInstanceId, quantity, finalPrice, commerceContext);

		finalPrice = finalPrice.multiply(BigDecimal.valueOf(quantity));

		if (commerceDiscountValue != null) {
			CommerceMoney discountAmountMoney =
				commerceDiscountValue.getDiscountAmount();

			finalPrice = finalPrice.subtract(discountAmountMoney.getPrice());
		}

		commerceProductPriceImpl.setCommerceDiscountValue(
			commerceDiscountValue);
		commerceProductPriceImpl.setFinalPrice(
			_commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), finalPrice));

		return commerceProductPriceImpl;
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

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		Optional<CommercePriceList> commercePriceList = _getPriceList(
			cpInstance.getGroupId(), commerceContext);

		if (commercePriceList.isPresent()) {
			BigDecimal priceListPrice = _getPriceListPrice(
				cpInstanceId, quantity, commercePriceList.get(),
				commerceContext.getCommerceCurrency(), true);

			if (priceListPrice != null) {
				return _commerceMoneyFactory.create(
					commerceCurrency, priceListPrice);
			}
		}

		BigDecimal price = cpInstance.getPromoPrice();

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpInstance.getGroupId());

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

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	@Override
	public CommerceMoney getUnitMaxPrice(
			long cpDefinitionId, int quantity, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = null;
		BigDecimal maxPrice = BigDecimal.ZERO;

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
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
			_cpInstanceLocalService.getCPDefinitionInstances(
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

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		Optional<CommercePriceList> commercePriceList = _getPriceList(
			cpInstance.getGroupId(), commerceContext);

		if (commercePriceList.isPresent()) {
			BigDecimal priceListPrice = _getPriceListPrice(
				cpInstance.getCPInstanceId(), quantity, commercePriceList.get(),
				commerceContext.getCommerceCurrency(), false);

			if (priceListPrice != null) {
				return _commerceMoneyFactory.create(
					commerceCurrency, priceListPrice);
			}
		}

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpInstance.getGroupId());

		CommerceCurrency catalogCommerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceCatalog.getCompanyId(),
				commerceCatalog.getCommerceCurrencyCode());

		BigDecimal price = cpInstance.getPrice();

		if (catalogCommerceCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				catalogCommerceCurrency.getRate(),
				RoundingMode.valueOf(
					catalogCommerceCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	private Optional<CommercePriceList> _getPriceList(
			long groupId, CommerceContext commerceContext)
		throws PortalException {

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount == null) {
			return Optional.empty();
		}

		List<CommerceAccountGroup> commerceAccountGroups =
			_commerceAccountGroupLocalService.
				getCommerceAccountGroupsByCommerceAccountId(
					commerceAccount.getCommerceAccountId());

		Stream<CommerceAccountGroup> stream = commerceAccountGroups.stream();

		long[] commerceAccountGroupIds = stream.mapToLong(
			CommerceAccountGroup::getCommerceAccountGroupId
		).toArray();

		return _commercePriceListLocalService.getCommercePriceList(
			commerceAccount.getCompanyId(), groupId,
			commerceAccount.getCommerceAccountId(), commerceAccountGroupIds);
	}

	private BigDecimal _getPriceListPrice(
			long cpInstanceId, int quantity,
			CommercePriceList commercePriceList,
			CommerceCurrency commerceCurrency, boolean promo)
		throws PortalException {

		BigDecimal price = null;

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				cpInstanceId, commercePriceList.getCommercePriceListId());

		if (commercePriceEntry != null) {
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
							commercePriceEntry.getCommercePriceEntryId(),
							quantity);

				if (commerceTierPriceEntry != null) {
					if (promo) {
						price = commerceTierPriceEntry.getPromoPrice();
					}
					else {
						price = commerceTierPriceEntry.getPrice();
					}
				}
			}

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
		}

		return price;
	}

	@Reference
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference(target = "(commerce.discount.calculation.key=v1.0)")
	private CommerceDiscountCalculation _commerceDiscountCalculation;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}