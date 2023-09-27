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
import com.liferay.commerce.price.CommerceProductOptionValueRelativePriceRequest;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPJSONUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

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
				setVisible(
					() -> {
						if (!cpDefinitionOptionRel.isSkuContributor()) {
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
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

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