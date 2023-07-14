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
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPJSONUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Availability;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.DDMOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ReplacementSku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.converter.SkuDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0.SkuOptionUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.math.BigDecimal;

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

		JSONArray jsonArray = CPJSONUtil.toJSONArray(
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
					cpInstance.getCPInstanceId()));

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionValueRelsMap =
				_cpInstanceHelper.getCPDefinitionOptionValueRelsMap(
					cpInstance.getCPDefinitionId(), jsonArray.toString());

		DDMOption[] ddmOptions = _getDDMOptions(cpDefinitionOptionValueRelsMap);

		CPInstance replacementCPInstance =
			_cpInstanceLocalService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid());

		return new Sku() {
			{
				availability = _getAvailability(
					cpInstance.getGroupId(),
					commerceContext.getCommerceChannelGroupId(),
					cpSkuDTOConverterConvertContext.getCompanyId(),
					cpInstance.getSku(), cpInstance,
					cpSkuDTOConverterConvertContext.getLocale());
				DDMOptions = ddmOptions;
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
						cpSkuDTOConverterConvertContext.getUser());
				manufacturerPartNumber = cpInstance.getManufacturerPartNumber();
				price = _getPrice(
					cpSkuDTOConverterConvertContext.getCommerceContext(),
					cpInstance,
					JSONUtil.toString(
						JSONUtil.toJSONArray(
							ddmOptions,
							ddmOption -> _jsonFactory.createJSONObject(
								ddmOption.toString()))),
					cpSkuDTOConverterConvertContext.getLocale(),
					cpSkuDTOConverterConvertContext.getQuantity());
				published = cpInstance.isPublished();
				purchasable = cpInstance.isPurchasable();
				sku = cpInstance.getSku();
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

						DDMOption[] replacementSkuDDMOptions = _getDDMOptions(
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
											replacementSkuDDMOptions,
											replacementSkuDDMOption ->
												_jsonFactory.createJSONObject(
													replacementSkuDDMOption.
														toString()))),
									cpSkuDTOConverterConvertContext.getLocale(),
									cpSkuDTOConverterConvertContext.
										getQuantity());
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
				setSkuOptions(
					() -> {
						if (MapUtil.isNotEmpty(
								cpDefinitionOptionValueRelsMap)) {

							return SkuOptionUtil.getSkuOptions(
								cpDefinitionOptionValueRelsMap,
								_cpInstanceLocalService);
						}

						return null;
					});
			}
		};
	}

	private Availability _getAvailability(
			long commerceCatalogGroupId, long commerceChannelGroupId,
			long companyId, String sku, CPInstance cpInstance, Locale locale)
		throws Exception {

		Availability availability = new Availability();

		if (_cpDefinitionInventoryEngine.isDisplayAvailability(cpInstance)) {
			if (Objects.equals(
					_commerceInventoryEngine.getAvailabilityStatus(
						cpInstance.getCompanyId(), commerceCatalogGroupId,
						commerceChannelGroupId,
						_cpDefinitionInventoryEngine.getMinStockQuantity(
							cpInstance),
						cpInstance.getSku()),
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
					sku));
		}

		return availability;
	}

	private CommerceProductPriceRequest _getCommerceProductPriceRequest(
		CommerceContext commerceContext,
		List<CommerceOptionValue> commerceOptionValues, long cpInstanceId,
		int quantity) {

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);
		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(true);

		return commerceProductPriceRequest;
	}

	private DDMOption[] _getDDMOptions(
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionValueRelsMap) {

		List<DDMOption> ddmOptions = new ArrayList<>();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				entry : cpDefinitionOptionValueRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			ddmOptions.add(
				new DDMOption() {
					{
						key = cpDefinitionOptionRel.getKey();
						required =
							cpDefinitionOptionRel.isRequired() ||
							cpDefinitionOptionRel.isSkuContributor();
						value = TransformUtil.transformToArray(
							entry.getValue(),
							CPDefinitionOptionValueRel::getKey, String.class);
					}
				});
		}

		return ddmOptions.toArray(new DDMOption[0]);
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
			String ddmFormValues, Locale locale, int quantity)
		throws Exception {

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				_getCommerceProductPriceRequest(
					commerceContext,
					_commerceOptionValueHelper.
						getCPDefinitionCommerceOptionValues(
							cpInstance.getCPDefinitionId(), ddmFormValues),
					cpInstance.getCPInstanceId(), quantity));

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

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

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