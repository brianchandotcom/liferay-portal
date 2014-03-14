/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailOrderConfirmation", false);
		validateEmail(actionRequest, "emailOrderShipping", false);
		validateEmailFrom(actionRequest);

		updateInsuranceCalculation(actionRequest);
		updatePayment(actionRequest);
		updateShippingCalculation(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void updateInsuranceCalculation(ActionRequest actionRequest)
		throws Exception {

		String[] insurance = new String[5];

		for (int i = 0; i < insurance.length; i++) {
			insurance[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "insurance" + i));
		}

		setPreference(actionRequest, "insurance", StringUtil.merge(insurance));
	}

	protected void updatePayment(ActionRequest actionRequest) throws Exception {
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

	protected void updateShippingCalculation(ActionRequest actionRequest)
		throws Exception {

		String[] shipping = new String[5];

		for (int i = 0; i < shipping.length; i++) {
			shipping[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "shipping" + i));
		}

		setPreference(actionRequest, "shipping", StringUtil.merge(shipping));
	}

}