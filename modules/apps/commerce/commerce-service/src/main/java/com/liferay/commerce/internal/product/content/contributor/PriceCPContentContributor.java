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

package com.liferay.commerce.internal.product.content.contributor;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.internal.util.CommerceBigDecimalUtil;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.product.content.contributor.name=" + CPContentContributorConstants.PRICE,
	service = CPContentContributor.class
)
public class PriceCPContentContributor implements CPContentContributor {

	@Override
	public String getName() {
		return CPContentContributorConstants.PRICE;
	}

	@Override
	public JSONObject getValue(
			CPInstance cpInstance, HttpServletRequest httpServletRequest)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		if (cpInstance == null) {
			return jsonObject;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				_portal.getScopeGroupId(httpServletRequest));

		if (commerceChannel == null) {
			return jsonObject;
		}

		String ddmFormValues = ParamUtil.getString(
			httpServletRequest, "ddmFormValues");

		List<CommerceOptionValue> commerceOptionValues =
			_commerceOptionValueHelper.getCPDefinitionCommerceOptionValues(
				cpInstance.getCPDefinitionId(), ddmFormValues);

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				_getCommerceProductPriceRequest(
					cpInstance, cpDefinitionInventoryEngine, commerceContext,
					commerceOptionValues));

		CommerceMoney unitPrice = commerceProductPrice.getUnitPrice();

		if (unitPrice.isEmpty()) {
			return jsonObject;
		}

		Locale locale = _portal.getLocale(httpServletRequest);

		jsonObject.put(
			CPContentContributorConstants.PRICE, unitPrice.format(locale));

		CommerceMoney unitPromoPrice = commerceProductPrice.getUnitPromoPrice();

		if (unitPromoPrice.isEmpty()) {
			return jsonObject;
		}

		if (CommerceBigDecimalUtil.gt(
				unitPromoPrice.getPrice(), BigDecimal.ZERO) &&
			CommerceBigDecimalUtil.lte(
				unitPromoPrice.getPrice(), unitPrice.getPrice())) {

			jsonObject.put(
				CPContentContributorConstants.PROMO_PRICE,
				unitPromoPrice.format(locale));
		}

		return jsonObject;
	}

	private CommerceProductPriceRequest _getCommerceProductPriceRequest(
			CPInstance cpInstance,
			CPDefinitionInventoryEngine cpDefinitionInventoryEngine,
			CommerceContext commerceContext,
			List<CommerceOptionValue> commerceOptionValues)
		throws PortalException {

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(
			cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance));
		commerceProductPriceRequest.setSecure(false);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		return commerceProductPriceRequest;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}