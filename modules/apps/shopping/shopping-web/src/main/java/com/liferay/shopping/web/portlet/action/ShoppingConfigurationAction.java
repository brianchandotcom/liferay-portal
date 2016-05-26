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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.constants.ShoppingPortletKeys;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN
	},
	service = ConfigurationAction.class
)
public class ShoppingConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailOrderConfirmation");
		validateEmail(actionRequest, "emailOrderShipping");
		validateEmailFrom(actionRequest);

		updatePayment(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void updateInsuranceCalculation(ActionRequest actionRequest) {
		String[] insurance = new String[5];

		for (int i = 0; i < insurance.length; i++) {
			insurance[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "insurance" + i));
		}

		setPreference(actionRequest, "insurance", insurance);
	}

	@Override
	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		super.updateMultiValuedKeys(actionRequest);

		updateInsuranceCalculation(actionRequest);
		updateShippingCalculation(actionRequest);
	}

	protected void updatePayment(ActionRequest actionRequest) {
		String taxRatePercent = ParamUtil.getString(actionRequest, "taxRate");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		NumberFormat percentFormat = NumberFormat.getPercentInstance(
			themeDisplay.getLocale());

		try {
			double taxRate = GetterUtil.getDouble(
				percentFormat.parse(taxRatePercent));

			setPreference(actionRequest, "taxRate", String.valueOf(taxRate));
		}
		catch (ParseException pe) {
			SessionErrors.add(actionRequest, "taxRate");
		}
	}

	protected void updateShippingCalculation(ActionRequest actionRequest) {
		String[] shipping = new String[5];

		for (int i = 0; i < shipping.length; i++) {
			shipping[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "shipping" + i));
		}

		setPreference(actionRequest, "shipping", shipping);
	}

}