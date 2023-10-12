/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.entry.type.COREntryType;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeItem;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeRegistry;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.price.CommerceProductOptionValueRelativePriceRequest;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.configuration.CPDefinitionOptionRelConfiguration;
import com.liferay.commerce.product.constants.CPDefinitionLinkTypeConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPJSONUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=CPDefinitionOptionValueRel",
	service = DTOConverter.class
)
public class ProductOptionValueDTOConverter
	implements DTOConverter<CPDefinitionOptionValueRel, ProductOptionValue> {

	@Override
	public String getContentType() {
		return ProductOptionValue.class.getSimpleName();
	}

	@Override
	public ProductOptionValue toDTO(
			DTOConverterContext dtoConverterContext,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionValueRel.getCPDefinitionOptionRel();

		CPInstance cpInstance = _cpInstanceLocalService.fetchCProductInstance(
			cpDefinitionOptionValueRel.getCProductId(),
			cpDefinitionOptionValueRel.getCPInstanceUuid());

		BigDecimal priceBigDecimal = cpDefinitionOptionValueRel.getPrice();

		CPDefinitionOptionRelConfiguration cpDefinitionOptionRelConfiguration =
			_configurationProvider.getCompanyConfiguration(
				CPDefinitionOptionRelConfiguration.class,
				cpDefinitionOptionRel.getCompanyId());

		return new ProductOptionValue() {
			{
				id =
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId();
				key = cpDefinitionOptionValueRel.getKey();
				name = cpDefinitionOptionValueRel.getName(
					_language.getLanguageId(dtoConverterContext.getLocale()));
				preselected = cpDefinitionOptionValueRel.isPreselected();
				price = (priceBigDecimal == null) ? BigDecimal.ZERO.toString() :
					priceBigDecimal.toString();
				priceType = cpDefinitionOptionRel.getPriceType();
				priority = cpDefinitionOptionValueRel.getPriority();
				productOptionId =
					cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				quantity = String.valueOf(
					cpDefinitionOptionValueRel.getQuantity());

				skuId =
					(cpInstance == null) ? null : cpInstance.getCPInstanceId();
				unitOfMeasureKey =
					cpDefinitionOptionValueRel.getUnitOfMeasureKey();

				setInfoMessage(
					() -> {
						if (cpInstance == null) {
							return null;
						}

						if (FeatureFlagManagerUtil.isEnabled(
								"COMMERCE-11922") &&
							cpDefinitionOptionRelConfiguration.
								showUnselectableOptions()) {

							Long skuId = (Long)dtoConverterContext.getAttribute(
								"skuId");

							if (Validator.isNull(skuId)) {
								return null;
							}

							CPInstance selectedCPInstance =
								_cpInstanceLocalService.fetchCPInstance(skuId);

							if (selectedCPInstance == null) {
								return null;
							}

							JSONArray selectedJSONArray =
								CPJSONUtil.toJSONArray(
									_cpDefinitionOptionRelLocalService.
										getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
											selectedCPInstance.
												getCPInstanceId()));

							List<CommerceOptionValue> commerceOptionValues =
								_commerceOptionValueHelper.
									getCPDefinitionCommerceOptionValues(
										selectedCPInstance.getCPDefinitionId(),
										selectedJSONArray.toString());

							String corEntryInfoMessage =
								_getCOREntryInfoMessage(
									commerceOptionValues, cpDefinitionOptionRel,
									cpDefinitionOptionValueRel, cpInstance,
									dtoConverterContext);

							if (Validator.isNotNull(corEntryInfoMessage)) {
								return corEntryInfoMessage;
							}

							String cpDefinitionLinkInfoMessage =
								_getCPDefinitionLinkInfoMessage(
									commerceOptionValues,
									cpDefinitionOptionValueRel.getKey(),
									cpInstance, dtoConverterContext, false);

							if (Validator.isNotNull(
									cpDefinitionLinkInfoMessage)) {

								return cpDefinitionLinkInfoMessage;
							}
						}

						return null;
					});
				setRelativePriceFormatted(
					() -> {
						CommerceContext commerceContext =
							(CommerceContext)dtoConverterContext.getAttribute(
								"commerceContext");
						Long productOptionValueId =
							(Long)dtoConverterContext.getAttribute(
								"productOptionValueId");
						Long skuId = (Long)dtoConverterContext.getAttribute(
							"skuId");

						if ((commerceContext == null) ||
							Validator.isNull(productOptionValueId) ||
							Validator.isNull(skuId)) {

							return null;
						}

						CPInstance selectedCPInstance =
							_cpInstanceLocalService.fetchCPInstance(skuId);

						if (selectedCPInstance == null) {
							return null;
						}

						JSONArray clonedJSONArray = _getClonedJSONArray(
							cpDefinitionOptionRel, cpDefinitionOptionValueRel,
							selectedCPInstance);

						if (clonedJSONArray == null) {
							return null;
						}

						CPInstance cpInstance =
							_cpInstanceHelper.fetchCPInstance(
								cpDefinitionOptionRel.getCPDefinitionId(),
								clonedJSONArray.toString());

						CPDefinitionOptionValueRel
							selectedCPDefinitionOptionValueRel =
								_cpDefinitionOptionValueRelLocalService.
									getCPDefinitionOptionValueRel(
										productOptionValueId);

						CommerceProductOptionValueRelativePriceRequest.Builder
							builder =
								new CommerceProductOptionValueRelativePriceRequest.Builder(
									commerceContext,
									cpDefinitionOptionValueRel);

						CommerceMoney commerceMoney =
							_commerceProductPriceCalculation.
								getCPDefinitionOptionValueRelativePrice(
									builder.cpInstanceId(
										(cpInstance == null) ? 0 :
											cpInstance.getCPInstanceId()
									).cpInstanceMinQuantity(
										_getMinOrderQuantity(cpInstance)
									).cpInstanceUnitOfMeasureKey(
										cpDefinitionOptionValueRel.
											getUnitOfMeasureKey()
									).selectedCPInstanceId(
										selectedCPInstance.getCPInstanceId()
									).selectedCPInstanceMinQuantity(
										_getMinOrderQuantity(selectedCPInstance)
									).selectedCPDefinitionOptionValueRel(
										selectedCPDefinitionOptionValueRel
									).selectedCPInstanceUnitOfMeasureKey(
										selectedCPDefinitionOptionValueRel.
											getUnitOfMeasureKey()
									).build());

						return commerceMoney.format(
							dtoConverterContext.getLocale());
					});
				setSelectable(
					() -> {
						if (cpInstance == null) {
							return true;
						}

						if (FeatureFlagManagerUtil.isEnabled(
								"COMMERCE-11922") &&
							cpDefinitionOptionRelConfiguration.
								showUnselectableOptions()) {

							Long skuId = (Long)dtoConverterContext.getAttribute(
								"skuId");

							if (Validator.isNull(skuId)) {
								return true;
							}

							CPInstance selectedCPInstance =
								_cpInstanceLocalService.fetchCPInstance(skuId);

							if (selectedCPInstance == null) {
								return true;
							}

							JSONArray selectedJSONArray =
								CPJSONUtil.toJSONArray(
									_cpDefinitionOptionRelLocalService.
										getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
											selectedCPInstance.
												getCPInstanceId()));

							List<CommerceOptionValue> commerceOptionValues =
								_commerceOptionValueHelper.
									getCPDefinitionCommerceOptionValues(
										selectedCPInstance.getCPDefinitionId(),
										selectedJSONArray.toString());

							if (Validator.isNotNull(
									_getCOREntryInfoMessage(
										commerceOptionValues,
										cpDefinitionOptionRel,
										cpDefinitionOptionValueRel, cpInstance,
										dtoConverterContext)) ||
								Validator.isNotNull(
									_getCPDefinitionLinkInfoMessage(
										commerceOptionValues,
										cpDefinitionOptionValueRel.getKey(),
										cpInstance, dtoConverterContext,
										true))) {

								return false;
							}
						}

						return true;
					});
				setVisible(
					() -> {
						if (!cpDefinitionOptionRel.isSkuContributor() ||
							(FeatureFlagManagerUtil.isEnabled(
								"COMMERCE-11922") &&
							 cpDefinitionOptionRelConfiguration.
								 showUnselectableOptions())) {

							return true;
						}

						Long skuId = (Long)dtoConverterContext.getAttribute(
							"skuId");

						if (Validator.isNull(skuId)) {
							return true;
						}

						CPInstance selectedCPInstance =
							_cpInstanceLocalService.fetchCPInstance(skuId);

						if (selectedCPInstance == null) {
							return true;
						}

						JSONArray clonedJSONArray = _getClonedJSONArray(
							cpDefinitionOptionRel, cpDefinitionOptionValueRel,
							selectedCPInstance);

						if (clonedJSONArray == null) {
							return true;
						}

						CPInstance cpInstance =
							_cpInstanceHelper.fetchCPInstance(
								cpDefinitionOptionRel.getCPDefinitionId(),
								clonedJSONArray.toString());

						if (cpInstance == null) {
							return false;
						}

						return true;
					});
			}
		};
	}

	private JSONArray _getClonedJSONArray(
			CPDefinitionOptionRel cpDefinitionOptionRel,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
			CPInstance cpInstance)
		throws PortalException {

		JSONArray jsonArray = CPJSONUtil.toJSONArray(
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
					cpInstance.getCPInstanceId()));

		JSONArray clonedJSONArray = _jsonFactory.createJSONArray(
			jsonArray.toString());

		if (_updateJSONArray(
				cpDefinitionOptionRel.getKey(),
				cpDefinitionOptionValueRel.getKey(), clonedJSONArray)) {

			return clonedJSONArray;
		}

		return null;
	}

	private String _getCOREntryInfoMessage(
			List<CommerceOptionValue> commerceOptionValues,
			CPDefinitionOptionRel cpDefinitionOptionRel,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
			CPInstance cpInstance, DTOConverterContext dtoConverterContext)
		throws Exception {

		String infoMessage = null;

		List<COREntryTypeItem> corEntryTypeItems = new ArrayList<>();

		for (CommerceOptionValue commerceOptionValue : commerceOptionValues) {
			if (Objects.equals(
					commerceOptionValue.getOptionValueKey(),
					cpDefinitionOptionValueRel.getKey())) {

				continue;
			}

			if (commerceOptionValue.getCPInstanceId() > 0) {
				CPInstance commerceOptionValueCPInstance =
					_cpInstanceLocalService.getCPInstance(
						commerceOptionValue.getCPInstanceId());

				corEntryTypeItems.add(
					new COREntryTypeItem(
						commerceOptionValueCPInstance.getCPDefinitionId(),
						commerceOptionValueCPInstance.getCPInstanceId(),
						cpDefinitionOptionValueRel.getQuantity()));
			}
		}

		corEntryTypeItems.add(
			new COREntryTypeItem(
				cpInstance.getCPDefinitionId(), cpInstance.getCPInstanceId(),
				cpDefinitionOptionValueRel.getQuantity()));

		COREntryType corEntryType = _corEntryTypeRegistry.getCOREntryType(
			COREntryConstants.TYPE_PRODUCTS_LIMIT);

		List<COREntry> corEntries = _corEntryLocalService.getCOREntries(
			cpDefinitionOptionRel.getCompanyId(), true,
			COREntryConstants.TYPE_PRODUCTS_LIMIT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (COREntry corEntry : corEntries) {
			if (!corEntryType.evaluate(corEntry, corEntryTypeItems)) {
				infoMessage = corEntryType.getErrorMessage(
					corEntry, null, dtoConverterContext.getLocale());
			}
		}

		return infoMessage;
	}

	private String _getCPDefinitionLinkInfoMessage(
			List<CommerceOptionValue> commerceOptionValues,
			String cpDefinitionOptionValueRelKey, CPInstance cpInstance,
			DTOConverterContext dtoConverterContext, boolean exclude)
		throws Exception {

		for (CommerceOptionValue commerceOptionValue : commerceOptionValues) {
			if (Objects.equals(
					commerceOptionValue.getOptionValueKey(),
					cpDefinitionOptionValueRelKey) ||
				(commerceOptionValue.getCPInstanceId() <= 0)) {

				continue;
			}

			CPInstance commerceOptionValueCPInstance =
				_cpInstanceLocalService.getCPInstance(
					commerceOptionValue.getCPInstanceId());

			List<CPDefinitionLink> excludeCPDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					commerceOptionValueCPInstance.getCPDefinitionId(),
					CPDefinitionLinkTypeConstants.EXCLUDE,
					WorkflowConstants.STATUS_APPROVED);

			for (CPDefinitionLink cpDefinitionLink : excludeCPDefinitionLinks) {
				CProduct cProduct = cpDefinitionLink.getCProduct();

				if (cpInstance.getCPDefinitionId() ==
						cProduct.getPublishedCPDefinitionId()) {

					CPDefinition cpDefinition = cpInstance.getCPDefinition();
					CPDefinition publishedCPDefinition =
						_cpDefinitionLocalService.getCPDefinition(
							cProduct.getPublishedCPDefinitionId());

					String languageId = _language.getLanguageId(
						dtoConverterContext.getLocale());

					return _language.format(
						dtoConverterContext.getLocale(),
						"x-cannot-be-combined-with-x",
						new String[] {
							cpDefinition.getName(languageId),
							publishedCPDefinition.getName(languageId)
						});
				}
			}

			if (exclude) {
				continue;
			}

			List<CPDefinitionLink> includeCPDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					commerceOptionValueCPInstance.getCPDefinitionId(),
					CPDefinitionLinkTypeConstants.INCLUDE,
					WorkflowConstants.STATUS_APPROVED);

			for (CPDefinitionLink cpDefinitionLink : includeCPDefinitionLinks) {
				CProduct cProduct = cpDefinitionLink.getCProduct();

				if (cpInstance.getCPDefinitionId() ==
						cProduct.getPublishedCPDefinitionId()) {

					CPDefinition cpDefinition = cpInstance.getCPDefinition();
					CPDefinition publishedCPDefinition =
						_cpDefinitionLocalService.getCPDefinition(
							cProduct.getPublishedCPDefinitionId());

					String languageId = _language.getLanguageId(
						dtoConverterContext.getLocale());

					return _language.format(
						dtoConverterContext.getLocale(),
						"x-requires-product-x-to-be-purchased-also",
						new String[] {
							cpDefinition.getName(languageId),
							publishedCPDefinition.getName(languageId)
						});
				}
			}
		}

		return null;
	}

	private BigDecimal _getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		if (cpInstance == null) {
			return BigDecimal.ZERO;
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		return cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance);
	}

	private boolean _updateJSONArray(
		String key, String value, JSONArray jsonArray1) {

		for (int i = 0; i < jsonArray1.length(); i++) {
			JSONObject jsonObject = jsonArray1.getJSONObject(i);

			String keyValue = jsonObject.getString("key");

			if (!Objects.equals(key, keyValue)) {
				continue;
			}

			JSONArray jsonArray2 = _jsonFactory.createJSONArray();

			jsonObject.put("value", jsonArray2.put(value));

			return true;
		}

		return false;
	}

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private COREntryLocalService _corEntryLocalService;

	@Reference
	private COREntryTypeRegistry _corEntryTypeRegistry;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}