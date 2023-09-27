/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.constants.CommerceInventoryAvailabilityConstants;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPJSONUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Availability;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ReplacementSku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.converter.SkuDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0.SkuOptionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(property = "dto.class.name=CPSku", service = DTOConverter.class)
public class SkuDTOConverter implements DTOConverter<CPInstance, Sku> {

	@Override
	public String getContentType() {
		return Product.class.getSimpleName();
	}

	@Override
	public Sku toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		SkuDTOConverterContext cpSkuDTOConverterConvertContext =
			(SkuDTOConverterContext)dtoConverterContext;

		CommerceContext commerceContext =
			cpSkuDTOConverterConvertContext.getCommerceContext();

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			(Long)cpSkuDTOConverterConvertContext.getId());

		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			_cpInstanceUnitOfMeasureLocalService.
				getActiveCPInstanceUnitOfMeasures(cpInstance.getCPInstanceId());

		JSONArray jsonArray = CPJSONUtil.toJSONArray(
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
					cpInstance.getCPInstanceId()));

		SkuOption[] skuOptionsArray = _getSkuOptions(
			_cpInstanceHelper.getCPDefinitionOptionValueRelsMap(
				cpInstance.getCPDefinitionId(), jsonArray.toString()));

		CPInstance replacementCPInstance =
			_cpInstanceLocalService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid());

		return new Sku() {
			{
				availability = _getAvailability(
					cpInstance.getGroupId(),
					commerceContext.getCommerceChannelGroupId(),
					cpSkuDTOConverterConvertContext.getCompanyId(), cpInstance,
					cpInstance.getSku(),
					cpSkuDTOConverterConvertContext.getUnitOfMeasureKey(),
					cpSkuDTOConverterConvertContext.getLocale());
				depth = cpInstance.getDepth();
				discontinued = cpInstance.isDiscontinued();
				discontinuedDate = cpInstance.getDiscontinuedDate();
				displayDate = cpInstance.getDisplayDate();
				expirationDate = cpInstance.getExpirationDate();
				gtin = cpInstance.getGtin();
				height = cpInstance.getHeight();
				id = cpInstance.getCPInstanceId();
				incomingQuantityLabel =
					_cpContentHelper.getIncomingQuantityLabel(
						cpSkuDTOConverterConvertContext.getCompanyId(),
						cpSkuDTOConverterConvertContext.getLocale(),
						cpInstance.getSku(),
						cpSkuDTOConverterConvertContext.getUnitOfMeasureKey(),
						cpSkuDTOConverterConvertContext.getUser());
				manufacturerPartNumber = cpInstance.getManufacturerPartNumber();
				price = _getPrice(
					cpSkuDTOConverterConvertContext.getCommerceContext(),
					cpInstance,
					JSONUtil.toString(
						JSONUtil.toJSONArray(
							skuOptionsArray,
							skuOption -> _jsonFactory.createJSONObject(
								skuOption.toString()))),
					cpSkuDTOConverterConvertContext.getLocale(),
					cpSkuDTOConverterConvertContext.getQuantity(),
					cpSkuDTOConverterConvertContext.getUnitOfMeasureKey());
				published = cpInstance.isPublished();
				purchasable = cpInstance.isPurchasable();
				sku = cpInstance.getSku();
				skuOptions = skuOptionsArray;
				skuUnitOfMeasures = TransformUtil.transformToArray(
					cpInstanceUnitOfMeasures,
					cpInstanceUnitOfMeasure -> _toSkuUnitOfMeasure(
						commerceContext, cpInstanceUnitOfMeasure,
						cpSkuDTOConverterConvertContext.getLocale()),
					SkuUnitOfMeasure.class);
				weight = cpInstance.getWeight();
				width = cpInstance.getWidth();

				setBackOrderAllowed(
					() -> {
						CPDefinitionInventory cpDefinitionInventory =
							_cpDefinitionInventoryLocalService.
								fetchCPDefinitionInventoryByCPDefinitionId(
									cpInstance.getCPDefinitionId());

						CPDefinitionInventoryEngine
							cpDefinitionInventoryEngine =
								_cpDefinitionInventoryEngineRegistry.
									getCPDefinitionInventoryEngine(
										cpDefinitionInventory);

						return cpDefinitionInventoryEngine.isBackOrderAllowed(
							cpInstance);
					});
				setDisplayDiscountLevels(
					() -> {
						CommercePriceConfiguration commercePriceConfiguration =
							_configurationProvider.getConfiguration(
								CommercePriceConfiguration.class,
								new SystemSettingsLocator(
									CommerceConstants.
										SERVICE_NAME_COMMERCE_PRICE));

						return commercePriceConfiguration.
							displayDiscountLevels();
					});
				setProductId(
					() -> {
						CPDefinition cpDefinition =
							cpInstance.getCPDefinition();

						return cpDefinition.getCProductId();
					});
				setReplacementSku(
					() -> {
						if (replacementCPInstance == null) {
							return null;
						}

						CPDefinition replacementCPDefinition =
							replacementCPInstance.getCPDefinition();

						JSONArray jsonArray = CPJSONUtil.toJSONArray(
							_cpDefinitionOptionRelLocalService.
								getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
									replacementCPInstance.getCPInstanceId()));

						SkuOption[] replacementSkuSkuOptions = _getSkuOptions(
							_cpInstanceHelper.getCPDefinitionOptionValueRelsMap(
								replacementCPInstance.getCPDefinitionId(),
								jsonArray.toString()));

						return new ReplacementSku() {
							{
								price = _getPrice(
									cpSkuDTOConverterConvertContext.
										getCommerceContext(),
									replacementCPInstance,
									JSONUtil.toString(
										JSONUtil.toJSONArray(
											replacementSkuSkuOptions,
											replacementSkuSkuOption ->
												_jsonFactory.createJSONObject(
													replacementSkuSkuOption.
														toString()))),
									cpSkuDTOConverterConvertContext.getLocale(),
									cpSkuDTOConverterConvertContext.
										getQuantity(),
									StringPool.BLANK);
								sku = replacementCPInstance.getSku();
								skuId = replacementCPInstance.getCPInstanceId();
								urls = LanguageUtils.getLanguageIdMap(
									_cpDefinitionLocalService.getUrlTitleMap(
										replacementCPInstance.
											getCPDefinitionId()));

								setProductConfiguration(
									() -> {
										if (replacementCPDefinition == null) {
											return null;
										}

										return _productConfigurationDTOConverter.
											toDTO(
												new DefaultDTOConverterContext(
													_dtoConverterRegistry,
													replacementCPDefinition.
														getCPDefinitionId(),
													cpSkuDTOConverterConvertContext.
														getLocale(),
													null, null));
									});
								setSkuOptions(
									() -> SkuOptionUtil.getSkuOptions(
										_cpInstanceHelper.
											getCPInstanceCPDefinitionOptionRelsMap(
												replacementCPInstance.
													getCPInstanceId()),
										_cpInstanceLocalService));
							}
						};
					});
				setReplacementSkuExternalReferenceCode(
					() -> {
						if (replacementCPInstance != null) {
							return replacementCPInstance.
								getExternalReferenceCode();
						}

						return null;
					});
				setReplacementSkuId(
					() -> {
						if (replacementCPInstance != null) {
							return replacementCPInstance.getCPInstanceId();
						}

						return null;
					});
				setTierPrices(
					() -> {
						if (!cpInstanceUnitOfMeasures.isEmpty()) {
							return null;
						}

						CommerceCurrency commerceCurrency =
							commerceContext.getCommerceCurrency();

						CommercePriceEntry commercePriceEntry =
							_commerceProductPriceCalculation.
								getUnitCommercePriceEntry(
									commerceContext,
									cpInstance.getCPInstanceId(),
									StringPool.BLANK);

						if (commercePriceEntry == null) {
							return null;
						}

						return TransformUtil.transformToArray(
							_commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS,
									new CommerceTierPriceEntryMinQuantityComparator(
										true)),
							commerceTierPriceEntry -> _toTierPrice(
								commerceCurrency, commerceTierPriceEntry, null,
								cpSkuDTOConverterConvertContext.getLocale()),
							TierPrice.class);
					});
			}
		};
	}

	private Availability _getAvailability(
			long commerceCatalogGroupId, long commerceChannelGroupId,
			long companyId, CPInstance cpInstance, String sku,
			String unitOfMeasureKey, Locale locale)
		throws Exception {

		Availability availability = new Availability();

		if (_cpDefinitionInventoryEngine.isDisplayAvailability(cpInstance)) {
			if (Objects.equals(
					_commerceInventoryEngine.getAvailabilityStatus(
						cpInstance.getCompanyId(), commerceCatalogGroupId,
						commerceChannelGroupId,
						_cpDefinitionInventoryEngine.getMinStockQuantity(
							cpInstance),
						cpInstance.getSku(), unitOfMeasureKey),
					CommerceInventoryAvailabilityConstants.AVAILABLE)) {

				availability.setLabel_i18n(_language.get(locale, "available"));
				availability.setLabel("available");
			}
			else {
				availability.setLabel_i18n(
					_language.get(locale, "unavailable"));
				availability.setLabel("unavailable");
			}
		}

		if (_cpDefinitionInventoryEngine.isDisplayStockQuantity(cpInstance)) {
			availability.setStockQuantity(
				_commerceInventoryEngine.getStockQuantity(
					companyId, commerceCatalogGroupId, commerceChannelGroupId,
					sku, unitOfMeasureKey));
		}

		return availability;
	}

	private CommerceProductPriceRequest _getCommerceProductPriceRequest(
			CommerceContext commerceContext,
			List<CommerceOptionValue> commerceOptionValues, long cpInstanceId,
			BigDecimal quantity, String unitOfMeasureKey)
		throws Exception {

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCalculateTax(
			_isTaxIncludedInPrice(commerceContext.getCommerceChannelId()));
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);
		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setUnitOfMeasureKey(unitOfMeasureKey);

		return commerceProductPriceRequest;
	}

	private String[] _getFormattedDiscountPercentages(
			BigDecimal[] discountPercentages, Locale locale)
		throws Exception {

		List<String> formattedDiscountPercentages = new ArrayList<>();

		for (BigDecimal percentage : discountPercentages) {
			formattedDiscountPercentages.add(
				_commercePriceFormatter.format(percentage, locale));
		}

		return formattedDiscountPercentages.toArray(new String[0]);
	}

	private Price _getPrice(
			CommerceContext commerceContext, CPInstance cpInstance,
			String formFieldValues, Locale locale, BigDecimal quantity,
			String unitOfMeasureKey)
		throws Exception {

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				_getCommerceProductPriceRequest(
					commerceContext,
					_commerceOptionValueHelper.
						getCPDefinitionCommerceOptionValues(
							cpInstance.getCPDefinitionId(), formFieldValues),
					cpInstance.getCPInstanceId(), quantity, unitOfMeasureKey));

		if (commerceProductPrice == null) {
			return new Price();
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		CommerceMoney unitPriceCommerceMoney =
			commerceProductPrice.getUnitPrice();

		CommerceMoney unitPromoPriceCommerceMoney =
			commerceProductPrice.getUnitPromoPrice();

		BigDecimal unitPrice = unitPriceCommerceMoney.getPrice();

		Price price = new Price() {
			{
				currency = commerceCurrency.getName(locale);
				price = unitPrice.doubleValue();
				priceFormatted = unitPriceCommerceMoney.format(locale);
				priceOnApplication =
					commerceProductPrice.isPriceOnApplication();
			}
		};

		BigDecimal unitPromoPrice = unitPromoPriceCommerceMoney.getPrice();

		if ((unitPromoPrice != null) &&
			(unitPromoPrice.compareTo(BigDecimal.ZERO) > 0) &&
			((unitPromoPrice.compareTo(unitPrice) < 0) ||
			 unitPriceCommerceMoney.isPriceOnApplication())) {

			price.setPromoPrice(unitPromoPrice.doubleValue());
			price.setPromoPriceFormatted(
				unitPromoPriceCommerceMoney.format(locale));
		}

		CommerceDiscountValue discountValue =
			commerceProductPrice.getDiscountValue();

		if (discountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				discountValue.getDiscountAmount();

			CommerceMoney finalPriceCommerceMoney =
				commerceProductPrice.getFinalPrice();

			price.setDiscount(discountAmountCommerceMoney.format(locale));
			price.setDiscountPercentage(
				_commercePriceFormatter.format(
					discountValue.getDiscountPercentage(), locale));
			price.setDiscountPercentages(
				_getFormattedDiscountPercentages(
					discountValue.getPercentages(), locale));
			price.setFinalPrice(finalPriceCommerceMoney.format(locale));
		}

		return price;
	}

	private SkuOption[] _getSkuOptions(
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionValueRelsMap)
		throws Exception {

		if (MapUtil.isNotEmpty(cpDefinitionOptionValueRelsMap)) {
			return SkuOptionUtil.getSkuOptions(
				cpDefinitionOptionValueRelsMap, _cpInstanceLocalService);
		}

		return null;
	}

	private boolean _isTaxIncludedInPrice(long commerceChannelId)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(commerceChannelId);

		String priceDisplayType = commerceChannel.getPriceDisplayType();

		if (priceDisplayType.equals(
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			return true;
		}

		return false;
	}

	private SkuUnitOfMeasure _toSkuUnitOfMeasure(
			CommerceContext commerceContext,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws Exception {

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		CommercePriceEntry commercePriceEntry =
			_commerceProductPriceCalculation.getUnitCommercePriceEntry(
				commerceContext, cpInstanceUnitOfMeasure.getCPInstanceId(),
				cpInstanceUnitOfMeasure.getKey());

		return new SkuUnitOfMeasure() {
			{
				key = cpInstanceUnitOfMeasure.getKey();
				name = cpInstanceUnitOfMeasure.getName(locale);
				precision = cpInstanceUnitOfMeasure.getPrecision();
				primary = cpInstanceUnitOfMeasure.isPrimary();
				priority = cpInstanceUnitOfMeasure.getPriority();

				setIncrementalOrderQuantity(
					() -> {
						BigDecimal incrementalOrderQuantity =
							cpInstanceUnitOfMeasure.
								getIncrementalOrderQuantity();

						if (incrementalOrderQuantity == null) {
							return null;
						}

						return incrementalOrderQuantity.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
				setPrice(
					() -> {
						if (commercePriceEntry == null) {
							return null;
						}

						BigDecimal commercePriceEntryPrice =
							commercePriceEntry.getPrice();

						return new Price() {
							{
								currency = commerceCurrency.getName(locale);
								price = commercePriceEntryPrice.doubleValue();
								priceFormatted = _commercePriceFormatter.format(
									commerceCurrency, commercePriceEntryPrice,
									locale);
								priceOnApplication =
									commercePriceEntry.isPriceOnApplication();
							}
						};
					});
				setRate(
					() -> {
						BigDecimal rate = cpInstanceUnitOfMeasure.getRate();

						if (rate == null) {
							return null;
						}

						return rate.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
				setTierPrices(
					() -> {
						if (commercePriceEntry == null) {
							return null;
						}

						return TransformUtil.transformToArray(
							_commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS,
									new CommerceTierPriceEntryMinQuantityComparator(
										true)),
							commerceTierPriceEntry -> _toTierPrice(
								commerceCurrency, commerceTierPriceEntry,
								cpInstanceUnitOfMeasure, locale),
							TierPrice.class);
					});
			}
		};
	}

	private TierPrice _toTierPrice(
		CommerceCurrency commerceCurrency,
		CommerceTierPriceEntry commerceTierPriceEntry,
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale) {

		BigDecimal commerceTierPriceEntryPrice =
			commerceTierPriceEntry.getPrice();

		try {
			return new TierPrice() {
				{
					currency = commerceCurrency.getName(locale);
					price = commerceTierPriceEntryPrice.doubleValue();
					priceFormatted = _commercePriceFormatter.format(
						commerceCurrency, commerceTierPriceEntryPrice, locale);
					quantity = _commerceQuantityFormatter.format(
						cpInstanceUnitOfMeasure,
						commerceTierPriceEntry.getMinQuantity());
				}
			};
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SkuDTOConverter.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private CPDefinitionInventoryEngine _cpDefinitionInventoryEngine;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.ProductConfigurationDTOConverter)"
	)
	private DTOConverter<CPDefinitionInventory, ProductConfiguration>
		_productConfigurationDTOConverter;

}