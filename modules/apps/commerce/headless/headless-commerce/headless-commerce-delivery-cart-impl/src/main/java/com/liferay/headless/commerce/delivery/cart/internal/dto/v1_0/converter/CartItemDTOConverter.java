/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Settings;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem",
	service = DTOConverter.class
)
public class CartItemDTOConverter
	implements DTOConverter<CommerceOrderItem, CartItem> {

	@Override
	public String getContentType() {
		return CartItem.class.getSimpleName();
	}

	@Override
	public CartItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CartItemDTOConverterContext cartItemDTOConverterContext =
			(CartItemDTOConverterContext)dtoConverterContext;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				(Long)cartItemDTOConverterContext.getId());

		Locale locale = cartItemDTOConverterContext.getLocale();

		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		return new CartItem() {
			{
				adaptiveMediaImageHTMLTag =
					_cpInstanceHelper.getCPInstanceAdaptiveMediaImageHTMLTag(
						cartItemDTOConverterContext.getAccountId(),
						commerceOrderItem.getCompanyId(),
						commerceOrderItem.getCPInstanceId());
				customFields = expandoBridge.getAttributes();
				errorMessages = _getErrorMessages(commerceOrderItem, locale);
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName(
					_language.getLanguageId(locale));
				options = commerceOrderItem.getJson();
				parentCartItemId =
					commerceOrderItem.getParentCommerceOrderItemId();
				price = _getPrice(commerceOrderItem, locale);
				productId = commerceOrderItem.getCProductId();
				productURLs = LanguageUtils.getLanguageIdMap(
					_cpDefinitionLocalService.getUrlTitleMap(
						commerceOrderItem.getCPDefinitionId()));
				quantity = _commerceQuantityFormatter.format(
					commerceOrderItem.getCPInstanceId(),
					commerceOrderItem.getQuantity(),
					commerceOrderItem.getUnitOfMeasureKey());
				replacedSku = commerceOrderItem.getReplacedSku();
				replacedSkuId = commerceOrderItem.getReplacedCPInstanceId();
				settings = _getSettings(commerceOrderItem.getCPInstanceId());
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = commerceOrderItem.isSubscription();
				thumbnail = _cpInstanceHelper.getCPInstanceThumbnailSrc(
					cartItemDTOConverterContext.getAccountId(),
					commerceOrderItem.getCPInstanceId());

				setSkuUnitOfMeasure(
					() -> {
						String unitOfMeasureKey =
							commerceOrderItem.getUnitOfMeasureKey();

						if (Validator.isNull(unitOfMeasureKey)) {
							return null;
						}

						CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
							_cpInstanceUnitOfMeasureLocalService.
								fetchCPInstanceUnitOfMeasure(
									commerceOrderItem.getCPInstanceId(),
									unitOfMeasureKey);

						if (cpInstanceUnitOfMeasure == null) {
							return null;
						}

						return new SkuUnitOfMeasure() {
							{
								key = unitOfMeasureKey;
								name = cpInstanceUnitOfMeasure.getName(locale);
								precision =
									cpInstanceUnitOfMeasure.getPrecision();
								primary = cpInstanceUnitOfMeasure.isPrimary();
								priority =
									cpInstanceUnitOfMeasure.getPriority();

								setIncrementalOrderQuantity(
									() -> {
										BigDecimal incrementalOrderQuantity =
											cpInstanceUnitOfMeasure.
												getIncrementalOrderQuantity();

										if (incrementalOrderQuantity == null) {
											return null;
										}

										return incrementalOrderQuantity.
											setScale(
												cpInstanceUnitOfMeasure.
													getPrecision(),
												RoundingMode.HALF_UP);
									});
								setRate(
									() -> {
										BigDecimal rate =
											cpInstanceUnitOfMeasure.getRate();

										if (rate == null) {
											return null;
										}

										return rate.setScale(
											cpInstanceUnitOfMeasure.
												getPrecision(),
											RoundingMode.HALF_UP);
									});
							}
						};
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
				priceOnApplication =
					commerceOrderItemPrice.isPriceOnApplication();
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

		BigDecimal minOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
		BigDecimal maxOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
		BigDecimal multipleQuantity =
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

			BigDecimal[] allowedOrderQuantitiesArray =
				cpDefinitionInventory.getAllowedOrderQuantitiesArray();

			if ((allowedOrderQuantitiesArray != null) &&
				(allowedOrderQuantitiesArray.length > 0)) {

				settings.setAllowedQuantities(allowedOrderQuantitiesArray);
			}
		}

		if (minOrderQuantity != null) {
			settings.setMinQuantity(
				BigDecimalUtil.stripTrailingZeros(minOrderQuantity));
		}

		if (maxOrderQuantity != null) {
			settings.setMaxQuantity(
				BigDecimalUtil.stripTrailingZeros(maxOrderQuantity));
		}

		if (multipleQuantity != null) {
			settings.setMultipleQuantity(
				BigDecimalUtil.stripTrailingZeros(multipleQuantity));
		}

		return settings;
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

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
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@Reference
	private Language _language;

}