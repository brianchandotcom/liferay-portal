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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
		"mvc.command.name=checkCPInstance"
	},
	service = MVCActionCommand.class
)
public class CheckCPInstanceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		try {
			CPInstance cpInstance = _cpInstanceHelper.getCPInstance(
				cpDefinitionId, ddmFormValues);

			if (cpInstance != null) {
				jsonObject.put("cpInstanceExist", true);
				jsonObject.put("cpInstanceId", cpInstance.getCPInstanceId());
				jsonObject.put("gtin", cpInstance.getGtin());
				jsonObject.put(
					"manufacturerPartNumber",
					cpInstance.getManufacturerPartNumber());

				String formattedPrice = _commercePriceFormatter.format(
					httpServletRequest, cpInstance.getPrice());

				jsonObject.put("price", formattedPrice);

				jsonObject.put("sku", cpInstance.getSku());

				CPDefinitionInventory cpDefinitionInventory =
					_cpDefinitionInventoryLocalService.
						fetchCPDefinitionInventoryByCPDefinitionId(
							cpDefinitionId);

				CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
					_cpDefinitionInventoryEngineRegistry.
						getCPDefinitionInventoryEngine(cpDefinitionInventory);

				boolean displayAvailability =
					cpDefinitionInventoryEngine.isDisplayAvailability(
						cpInstance);

				boolean available = false;

				if (cpDefinitionInventoryEngine.getStockQuantity(cpInstance) >
						cpDefinitionInventoryEngine.getMinStockQuantity(
							cpInstance)) {

					available = true;
				}

				if (displayAvailability && available) {
					jsonObject.put(
						"availability",
						getAvailabilityLabel(
							httpServletRequest,
							CPContentContributorConstants.AVAILABLE));
				}

				if (displayAvailability && !available) {
					jsonObject.put(
						"availability",
						getAvailabilityLabel(
							httpServletRequest,
							CPContentContributorConstants.UNAVAILABLE));
				}

				if (!available &&
					cpDefinitionInventoryEngine.isBackOrderAllowed(
						cpInstance)) {

					jsonObject.put(
						"availabilityRange",
						getAvailabilityRangeLabel(
							httpServletRequest,
							cpDefinitionInventoryEngine.getAvailabilityRange(
								cpInstance, _portal.getLocale(actionRequest))));
				}

				boolean displayStockQuantity =
					cpDefinitionInventoryEngine.isDisplayStockQuantity(
						cpInstance);

				if (displayStockQuantity) {
					jsonObject.put(
						"stockQuantity",
						getStockQuantityLabel(
							httpServletRequest,
							cpDefinitionInventoryEngine.getStockQuantity(
								cpInstance)));
				}
			}
			else {
				jsonObject.put("cpInstanceExist", false);
			}

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("error", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultErrorMessage(actionRequest);
		hideDefaultSuccessMessage(actionRequest);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		writeJSON(actionResponse, jsonObject);
	}

	protected String getAvailabilityLabel(
		HttpServletRequest httpServletRequest, String availability) {

		if (Validator.isNull(availability)) {
			return StringPool.BLANK;
		}

		return LanguageUtil.format(
			httpServletRequest, "availability-x", availability, true);
	}

	protected String getAvailabilityRangeLabel(
		HttpServletRequest httpServletRequest, String availabilityRange) {

		if (Validator.isNull(availabilityRange)) {
			return StringPool.BLANK;
		}

		return LanguageUtil.format(
			httpServletRequest, "product-will-be-available-in-x",
			availabilityRange);
	}

	protected String getStockQuantityLabel(
		HttpServletRequest httpServletRequest, int stockQuantity) {

		if (stockQuantity <= 0) {
			return StringPool.BLANK;
		}

		return LanguageUtil.format(
			httpServletRequest, "stock-quantity-x", stockQuantity);
	}

	protected void writeJSON(ActionResponse actionResponse, Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckCPInstanceMVCActionCommand.class);

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}