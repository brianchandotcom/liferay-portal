/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.web.internal.portlet.action;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalService;
import com.liferay.commerce.product.web.internal.constants.CommerceProductPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antionio Rendina
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=/editProductDefinition"
	},
	service = MVCActionCommand.class
)
public class EditCommerceProductDefinitionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceProductDefinition(ActionRequest actionRequest)
		throws Exception {

		Long commerceProductDefinitionId = ParamUtil.getLong(
			actionRequest, "commerceProductDefinitionId");

		_commerceProductDefinitionLocalService.deleteCommerceProductDefinition(
			commerceProductDefinitionId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.DELETE)) {
			deleteCommerceProductDefinition(actionRequest);
		}
		else if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			Long commerceProductDefinitionId = ParamUtil.getLong(
				actionRequest, "commerceProductDefinitionId");

			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "title");

			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					actionRequest, "description");

			String productTypeName = ParamUtil.getString(
				actionRequest, "productTypeName");

			String baseSKU = ParamUtil.getString(actionRequest, "baseSKU");

			int displayDateMonth = ParamUtil.getInteger(
				actionRequest, "displayDateMonth");
			int displayDateDay = ParamUtil.getInteger(
				actionRequest, "displayDateDay");
			int displayDateYear = ParamUtil.getInteger(
				actionRequest, "displayDateYear");
			int displayDateHour = ParamUtil.getInteger(
				actionRequest, "displayDateHour");
			int displayDateMinute = ParamUtil.getInteger(
				actionRequest, "displayDateMinute");
			int displayDateAmPm = ParamUtil.getInteger(
				actionRequest, "displayDateAmPm");

			if (displayDateAmPm == Calendar.PM) {
				displayDateHour += 12;
			}

			int expirationDateMonth = ParamUtil.getInteger(
				actionRequest, "expirationDateMonth");
			int expirationDateDay = ParamUtil.getInteger(
				actionRequest, "expirationDateDay");
			int expirationDateYear = ParamUtil.getInteger(
				actionRequest, "expirationDateYear");
			int expirationDateHour = ParamUtil.getInteger(
				actionRequest, "expirationDateHour");
			int expirationDateMinute = ParamUtil.getInteger(
				actionRequest, "expirationDateMinute");
			int expirationDateAmPm = ParamUtil.getInteger(
				actionRequest, "expirationDateAmPm");

			if (expirationDateAmPm == Calendar.PM) {
				expirationDateHour += 12;
			}

			Boolean neverExpire = ParamUtil.getBoolean(
				actionRequest, "neverExpire");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceProductDefinition.class.getName(), actionRequest);

			if (commerceProductDefinitionId == 0) {
				_commerceProductDefinitionLocalService.
					addCommerceProductDefinition(
						baseSKU, titleMap, descriptionMap, productTypeName,
						null, displayDateMonth, displayDateDay, displayDateYear,
						displayDateHour, displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						serviceContext);
			}
			else {
				_commerceProductDefinitionLocalService.
					updateCommerceProductDefinition(
						commerceProductDefinitionId, baseSKU, titleMap,
						descriptionMap, productTypeName, null, displayDateMonth,
						displayDateDay, displayDateYear, displayDateHour,
						displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateMinute, 0, neverExpire, serviceContext);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCommerceProductDefinitionLocalService(
		CommerceProductDefinitionLocalService
			commerceProductDefinitionLocalService) {

		_commerceProductDefinitionLocalService =
			commerceProductDefinitionLocalService;
	}

	private CommerceProductDefinitionLocalService
		_commerceProductDefinitionLocalService;

}