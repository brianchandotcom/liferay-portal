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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.constants.CommerceInventoryAvailabilityConstants;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Availability;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
	service = {DTOConverter.class, MappedProductDTOConverter.class}
)
public class MappedProductDTOConverter
	implements DTOConverter<CSDiagramEntry, MappedProduct> {

	@Override
	public String getContentType() {
		return MappedProduct.class.getSimpleName();
	}

	@Override
	public MappedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		MappedProductDTOConverterContext mappedProductDTOConverterContext =
			(MappedProductDTOConverterContext)dtoConverterContext;

		CommerceContext commerceContext =
			mappedProductDTOConverterContext.getCommerceContext();

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryLocalService.getCSDiagramEntry(
				(Long)mappedProductDTOConverterContext.getId());

		long cpInstanceId = GetterUtil.getLong(
			mappedProductDTOConverterContext.getReplacementCPInstanceId(),
			csDiagramEntry.getCPInstanceId());

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		long cProductId = GetterUtil.getLong(
			mappedProductDTOConverterContext.getReplacementCProductId(),
			csDiagramEntry.getCProductId());

		if (cpInstance != null) {
			CProduct cProduct =
				_cProductLocalService.getCProductByCPInstanceUuid(
					cpInstance.getCPInstanceUuid());

			cProductId = cProduct.getCProductId();
		}

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(cProductId);

		if ((cpDefinition != null) &&
			!_commerceProductViewPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				CommerceUtil.getCommerceAccountId(commerceContext),
				cpDefinition.getCPDefinitionId())) {

			return null;
		}

		CPInstance firstAvailableReplacementCPInstance =
			_cpInstanceHelper.fetchFirstAvailableReplacementCPInstance(
				commerceContext.getCommerceChannelGroupId(), cpInstanceId);

		return new MappedProduct() {
			{
				actions = mappedProductDTOConverterContext.getActions();
				id = csDiagramEntry.getCSDiagramEntryId();
				options = _getOptions(cpInstance);
				price = _getPrice(
					commerceContext, cpInstance,
					mappedProductDTOConverterContext.getLocale(), 1);
				quantity = csDiagramEntry.getQuantity();
				sequence = csDiagramEntry.getSequence();

				setAvailability(
					() -> {
						if (cpInstance == null) {
							return null;
						}

						return _getAvailability(
							commerceContext.getCommerceChannelGroupId(),
							mappedProductDTOConverterContext.getCompanyId(),
							cpInstance,
							mappedProductDTOConverterContext.getLocale(),
							cpInstance.getSku());
					});
				setFirstAvailableReplacementMappedProduct(
					() -> {
						MappedProduct firstAvailableReplacementMappedProduct =
							null;

						if ((cpInstance != null) &&
							cpInstance.isDiscontinued() &&
							(firstAvailableReplacementCPInstance != null)) {

							mappedProductDTOConverterContext.
								setReplacementCPInstanceId(
									firstAvailableReplacementCPInstance.
										getCPInstanceId());

							CPDefinition firstAvailableReplacementCPDefinition =
								firstAvailableReplacementCPInstance.
									getCPDefinition();

							mappedProductDTOConverterContext.
								setReplacementCProductId(
									firstAvailableReplacementCPDefinition.
										getCProductId());

							firstAvailableReplacementMappedProduct =
								MappedProductDTOConverter.this.toDTO(
									mappedProductDTOConverterContext);
						}

						mappedProductDTOConverterContext.
							setReplacementCPInstanceId(null);
						mappedProductDTOConverterContext.
							setReplacementCProductId(null);

						return firstAvailableReplacementMappedProduct;
					});
				setProductConfiguration(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						return _productConfigurationDTOConverter.toDTO(
							new DefaultDTOConverterContext(
								_dtoConverterRegistry,
								cpDefinition.getCPDefinitionId(),
								mappedProductDTOConverterContext.getLocale(),
								null, null));
					});
				setProductExternalReferenceCode(
					() -> {
						if (cpDefinition == null) {
							return StringPool.BLANK;
						}

						CProduct cProduct = cpDefinition.getCProduct();

						return cProduct.getExternalReferenceCode();
					});
				setProductId(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						return cpDefinition.getCProductId();
					});
				setProductName(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						return LanguageUtils.getLanguageIdMap(
							cpDefinition.getNameMap());
					});
				setProductOptions(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						List<ProductOption> productOptions = new ArrayList<>();

						for (CPDefinitionOptionRel cpDefinitionOptionRel :
								_cpDefinitionOptionRelLocalService.
									getCPDefinitionOptionRels(
										cpDefinition.getCPDefinitionId())) {

							productOptions.add(
								_productOptionDTOConverter.toDTO(
									new DefaultDTOConverterContext(
										cpDefinitionOptionRel.
											getCPDefinitionOptionRelId(),
										mappedProductDTOConverterContext.
											getLocale())));
						}

						return productOptions.toArray(new ProductOption[0]);
					});
				setPurchasable(
					() -> {
						if (cpInstance == null) {
							return null;
						}

						return cpInstance.isPurchasable();
					});
				setReplacementMappedProduct(
					() -> {
						MappedProduct replacementMappedProduct = null;

						if ((cpInstance != null) &&
							cpInstance.isDiscontinued()) {

							CPInstance replacementCPInstance =
								_cpInstanceHelper.fetchReplacementCPInstance(
									cpInstance.getReplacementCProductId(),
									cpInstance.getReplacementCPInstanceUuid());

							if (replacementCPInstance == null) {
								return null;
							}

							mappedProductDTOConverterContext.
								setReplacementCPInstanceId(
									replacementCPInstance.getCPInstanceId());

							mappedProductDTOConverterContext.
								setReplacementCProductId(
									cpInstance.getReplacementCProductId());

							replacementMappedProduct =
								MappedProductDTOConverter.this.toDTO(
									mappedProductDTOConverterContext);
						}

						mappedProductDTOConverterContext.
							setReplacementCPInstanceId(null);
						mappedProductDTOConverterContext.
							setReplacementCProductId(null);

						return replacementMappedProduct;
					});
				setReplacementMessage(
					() -> {
						if ((cpInstance != null) &&
							cpInstance.isDiscontinued() &&
							(firstAvailableReplacementCPInstance != null) &&
							(cpInstance.getCPInstanceId() ==
								csDiagramEntry.getCPInstanceId())) {

							return LanguageUtil.format(
								mappedProductDTOConverterContext.getLocale(),
								"x-has-been-replaced-by-x",
								new String[] {
									csDiagramEntry.getSku(),
									firstAvailableReplacementCPInstance.getSku()
								});
						}

						return null;
					});
				setSku(
					() -> {
						if (cpInstance == null) {
							return null;
						}

						return cpInstance.getSku();
					});
				setSkuExternalReferenceCode(
					() -> {
						if (cpInstance == null) {
							return StringPool.BLANK;
						}

						return cpInstance.getExternalReferenceCode();
					});
				setSkuId(
					() -> {
						if (cpInstance == null) {
							return null;
						}

						return cpInstance.getCPInstanceId();
					});
				setThumbnail(
					() -> {
						if (cpDefinition == null) {
							return StringPool.BLANK;
						}

						return cpDefinition.getDefaultImageThumbnailSrc(
							CommerceUtil.getCommerceAccountId(commerceContext));
					});
				setType(
					() -> {
						if (csDiagramEntry.isDiagram()) {
							return MappedProduct.Type.create(
								Type.DIAGRAM.getValue());
						}

						if (csDiagramEntry.getCPInstanceId() > 0) {
							return MappedProduct.Type.create(
								Type.SKU.getValue());
						}

						return MappedProduct.Type.create(
							Type.EXTERNAL.getValue());
					});
				setUrls(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						return LanguageUtils.getLanguageIdMap(
							_cpDefinitionLocalService.getUrlTitleMap(
								cpDefinition.getCPDefinitionId()));
					});
			}
		};
	}

	private Availability _getAvailability(
			long commerceChannelGroupId, long companyId, CPInstance cpInstance,
			Locale locale, String sku)
		throws Exception {

		Availability availability = new Availability();

		if (_cpDefinitionInventoryEngine.isDisplayAvailability(cpInstance)) {
			String availabilityStatus =
				_commerceInventoryEngine.getAvailabilityStatus(
					cpInstance.getCompanyId(), commerceChannelGroupId,
					_cpDefinitionInventoryEngine.getMinStockQuantity(
						cpInstance),
					cpInstance.getSku());

			if (Objects.equals(
					availabilityStatus,
					CommerceInventoryAvailabilityConstants.AVAILABLE)) {

				availability.setLabel_i18n(
					LanguageUtil.get(locale, "available"));
				availability.setLabel("available");
			}
			else {
				availability.setLabel_i18n(
					LanguageUtil.get(locale, "unavailable"));
				availability.setLabel("unavailable");
			}
		}

		if (_cpDefinitionInventoryEngine.isDisplayStockQuantity(cpInstance)) {
			availability.setStockQuantity(
				_commerceInventoryEngine.getStockQuantity(
					companyId, commerceChannelGroupId, sku));
		}

		return availability;
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

	private Map<String, String> _getOptions(CPInstance cpInstance)
		throws Exception {

		if (cpInstance == null) {
			return null;
		}

		Map<String, String> options = new HashMap<>();

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
						cpInstance.getCPInstanceId());

		JSONArray keyValuesJSONArray = _jsonHelper.toJSONArray(
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys);

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelsMap =
				_cpInstanceHelper.getCPDefinitionOptionRelsMap(
					cpInstance.getCPDefinitionId(),
					keyValuesJSONArray.toString());

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				entry : cpDefinitionOptionRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				entry.getValue();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				options.put(
					String.valueOf(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId()),
					String.valueOf(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId()));
			}
		}

		return options;
	}

	private Price _getPrice(
			CommerceContext commerceContext, CPInstance cpInstance,
			Locale locale, int quantity)
		throws Exception {

		if (cpInstance == null) {
			return null;
		}

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), quantity, true, commerceContext);

		if (commerceProductPrice == null) {
			return new Price();
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		CommerceMoney unitPriceCommerceMoney =
			commerceProductPrice.getUnitPrice();

		Price price = new Price() {
			{
				currency = commerceCurrency.getName(locale);
				priceFormatted = unitPriceCommerceMoney.format(locale);

				setPrice(
					() -> {
						BigDecimal unitPrice =
							unitPriceCommerceMoney.getPrice();

						return unitPrice.doubleValue();
					});
			}
		};

		CommerceMoney unitPromoPriceCommerceMoney =
			commerceProductPrice.getUnitPromoPrice();

		BigDecimal unitPromoPrice = unitPromoPriceCommerceMoney.getPrice();

		if ((unitPromoPrice != null) &&
			(unitPromoPrice.compareTo(BigDecimal.ZERO) > 0) &&
			(unitPromoPrice.compareTo(unitPriceCommerceMoney.getPrice()) < 0)) {

			price.setPromoPrice(unitPromoPrice.doubleValue());
			price.setPromoPriceFormatted(
				unitPromoPriceCommerceMoney.format(locale));
		}

		CommerceDiscountValue discountValue =
			commerceProductPrice.getDiscountValue();

		if (discountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				discountValue.getDiscountAmount();

			price.setDiscount(discountAmountCommerceMoney.format(locale));

			price.setDiscountPercentage(
				_commercePriceFormatter.format(
					discountValue.getDiscountPercentage(), locale));
			price.setDiscountPercentages(
				_getFormattedDiscountPercentages(
					discountValue.getPercentages(), locale));

			CommerceMoney finalPriceCommerceMoney =
				commerceProductPrice.getFinalPrice();

			price.setFinalPrice(finalPriceCommerceMoney.format(locale));
		}

		return price;
	}

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPDefinitionInventoryEngine _cpDefinitionInventoryEngine;

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
	private CProductLocalService _cProductLocalService;

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JsonHelper _jsonHelper;

	@Reference
	private ProductConfigurationDTOConverter _productConfigurationDTOConverter;

	@Reference
	private ProductOptionDTOConverter _productOptionDTOConverter;

}