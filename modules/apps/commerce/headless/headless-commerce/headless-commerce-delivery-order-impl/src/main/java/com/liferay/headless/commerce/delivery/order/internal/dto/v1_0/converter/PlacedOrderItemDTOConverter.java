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

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Settings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem",
	service = DTOConverter.class
)
public class PlacedOrderItemDTOConverter
	implements DTOConverter<CommerceOrderItem, PlacedOrderItem> {

	@Override
	public String getContentType() {
		return PlacedOrderItem.class.getSimpleName();
	}

	@Override
	public PlacedOrderItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		PlacedOrderItemDTOConverterContext placedOrderItemDTOConverterContext =
			(PlacedOrderItemDTOConverterContext)dtoConverterContext;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				(Long)placedOrderItemDTOConverterContext.getId());

		Locale locale = placedOrderItemDTOConverterContext.getLocale();

		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		return new PlacedOrderItem() {
			{
				adaptiveMediaImageHTMLTag =
					_cpInstanceHelper.getCPInstanceAdaptiveMediaImageHTMLTag(
						placedOrderItemDTOConverterContext.getAccountId(),
						commerceOrderItem.getCompanyId(),
						commerceOrderItem.getCPInstanceId());
				customFields = expandoBridge.getAttributes();
				errorMessages = _getErrorMessages(commerceOrderItem, locale);
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName(
					_language.getLanguageId(locale));
				options = commerceOrderItem.getJson();
				parentOrderItemId =
					commerceOrderItem.getParentCommerceOrderItemId();
				price = _getPrice(commerceOrderItem, locale);
				productId = commerceOrderItem.getCProductId();
				productURLs = LanguageUtils.getLanguageIdMap(
					_cpDefinitionLocalService.getUrlTitleMap(
						commerceOrderItem.getCPDefinitionId()));
				quantity = commerceOrderItem.getQuantity();
				replacedSku = commerceOrderItem.getReplacedSku();
				settings = _getSettings(commerceOrderItem.getCPInstanceId());
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = commerceOrderItem.isSubscription();
				thumbnail = _cpInstanceHelper.getCPInstanceThumbnailSrc(
					placedOrderItemDTOConverterContext.getAccountId(),
					commerceOrderItem.getCPInstanceId());

				setVirtualItemURLs(
					() -> {
						try {
							CommerceVirtualOrderItem commerceVirtualOrderItem =
								_commerceVirtualOrderItemService.
									fetchCommerceVirtualOrderItemByCommerceOrderItemId(
										commerceOrderItem.
											getCommerceOrderItemId());

							if (commerceVirtualOrderItem == null) {
								return null;
							}

							String url = commerceVirtualOrderItem.getUrl();

							if (Validator.isBlank(url)) {
								url =
									_commerceMediaResolver.
										getDownloadVirtualOrderItemURL(
											commerceVirtualOrderItem.
												getCommerceVirtualOrderItemId());
							}

							return new String[] {url};
						}
						catch (PortalException portalException) {
							if (_log.isDebugEnabled()) {
								_log.debug(portalException);
							}

							return null;
						}
					});
			}
		};
	}

	private String[] _getErrorMessages(
		CommerceOrderItem commerceOrderItem, Locale locale) {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
				locale);

			return new String[] {
				_language.get(
					resourceBundle, "the-product-is-no-longer-available")
			};
		}

		return null;
	}

	private Price _getPrice(CommerceOrderItem commerceOrderItem, Locale locale)
		throws Exception {

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		CommerceOrderItemPrice commerceOrderItemPrice =
			_commerceOrderPriceCalculation.getCommerceOrderItemPricePerUnit(
				commerceCurrency, commerceOrderItem);

		CommerceMoney unitPriceCommerceMoney =
			commerceOrderItemPrice.getUnitPrice();

		BigDecimal unitPrice = unitPriceCommerceMoney.getPrice();

		Price price = new Price() {
			{
				currency = commerceCurrency.getName(locale);
				price = unitPrice.doubleValue();
				priceFormatted = unitPriceCommerceMoney.format(locale);
			}
		};

		CommerceMoney promoPriceCommerceMoney =
			commerceOrderItemPrice.getPromoPrice();

		if (promoPriceCommerceMoney != null) {
			BigDecimal unitPromoPrice = promoPriceCommerceMoney.getPrice();

			if (unitPromoPrice != null) {
				price.setPromoPrice(unitPromoPrice.doubleValue());
				price.setPromoPriceFormatted(
					promoPriceCommerceMoney.format(locale));
			}
		}

		CommerceMoney discountAmountCommerceMoney =
			commerceOrderItemPrice.getDiscountAmount();

		if (discountAmountCommerceMoney != null) {
			BigDecimal discountAmount = discountAmountCommerceMoney.getPrice();

			if (discountAmount != null) {
				price.setDiscount(discountAmount.doubleValue());
				price.setDiscountFormatted(
					discountAmountCommerceMoney.format(locale));
				price.setDiscountPercentage(
					_commercePriceFormatter.format(
						commerceOrderItemPrice.getDiscountPercentage(),
						locale));

				BigDecimal discountPercentageLevel1 =
					commerceOrderItemPrice.getDiscountPercentageLevel1();
				BigDecimal discountPercentageLevel2 =
					commerceOrderItemPrice.getDiscountPercentageLevel2();
				BigDecimal discountPercentageLevel3 =
					commerceOrderItemPrice.getDiscountPercentageLevel3();
				BigDecimal discountPercentageLevel4 =
					commerceOrderItemPrice.getDiscountPercentageLevel4();

				price.setDiscountPercentageLevel1(
					discountPercentageLevel1.doubleValue());
				price.setDiscountPercentageLevel2(
					discountPercentageLevel2.doubleValue());
				price.setDiscountPercentageLevel3(
					discountPercentageLevel3.doubleValue());
				price.setDiscountPercentageLevel4(
					discountPercentageLevel4.doubleValue());
			}
		}

		CommerceMoney finalPriceCommerceMoney =
			commerceOrderItemPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		if (finalPrice != null) {
			price.setFinalPriceFormatted(
				finalPriceCommerceMoney.format(locale));
			price.setFinalPrice(finalPrice.doubleValue());
		}

		return price;
	}

	private Settings _getSettings(long cpInstanceId) {
		Settings settings = new Settings();

		int minOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
		int maxOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
		int multipleQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY;

		CPDefinitionInventory cpDefinitionInventory = null;

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance != null) {
			cpDefinitionInventory =
				_cpDefinitionInventoryLocalService.
					fetchCPDefinitionInventoryByCPDefinitionId(
						cpInstance.getCPDefinitionId());
		}

		if (cpDefinitionInventory != null) {
			minOrderQuantity = cpDefinitionInventory.getMinOrderQuantity();
			maxOrderQuantity = cpDefinitionInventory.getMaxOrderQuantity();
			multipleQuantity = cpDefinitionInventory.getMultipleOrderQuantity();

			int[] allowedOrderQuantitiesArray =
				cpDefinitionInventory.getAllowedOrderQuantitiesArray();

			if ((allowedOrderQuantitiesArray != null) &&
				(allowedOrderQuantitiesArray.length > 0)) {

				settings.setAllowedQuantities(
					ArrayUtil.toArray(allowedOrderQuantitiesArray));
			}
		}

		settings.setMinQuantity(minOrderQuantity);
		settings.setMaxQuantity(maxOrderQuantity);
		settings.setMultipleQuantity(multipleQuantity);

		return settings;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PlacedOrderItemDTOConverter.class);

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Language _language;

}