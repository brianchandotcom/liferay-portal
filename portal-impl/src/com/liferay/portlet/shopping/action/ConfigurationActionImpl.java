/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.util.ShoppingPreferences;

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

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ShoppingPreferences preferences = ShoppingPreferences.getInstance(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId());

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");
		String tabs3 = ParamUtil.getString(actionRequest, "tabs3");

		if (tabs2.equals("payment-settings")) {
			updatePayment(actionRequest, preferences);
		}
		else if (tabs2.equals("shipping-calculation")) {
			updateShippingCalculation(actionRequest, preferences);
		}
		else if (tabs2.equals("insurance-calculation")) {
			updateInsuranceCalculation(actionRequest, preferences);
		}
		else if (tabs2.equals("emails")) {
			if (tabs3.equals("email-from")) {
				updateEmailFrom(actionRequest, preferences);
			}
			else if (tabs3.equals("confirmation-email")) {
				updateEmailOrderConfirmation(actionRequest, preferences);
			}
			else if (tabs3.equals("shipping-email")) {
				updateEmailOrderShipping(actionRequest, preferences);
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doRefresh",
				PortletKeys.SHOPPING);
		}
	}

	protected void updateEmailFrom(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		String emailFromName = ParamUtil.getString(
			actionRequest, "emailFromName");
		String emailFromAddress = ParamUtil.getString(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress)) {
			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			preferences.setEmailFromName(emailFromName);
			preferences.setEmailFromAddress(emailFromAddress);
		}
	}

	protected void updateEmailOrderConfirmation(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		boolean emailOrderConfirmationEnabled = ParamUtil.getBoolean(
			actionRequest, "emailOrderConfirmationEnabled");
		String emailOrderConfirmationSubject = ParamUtil.getString(
			actionRequest, "emailOrderConfirmationSubject");
		String emailOrderConfirmationBody = ParamUtil.getString(
			actionRequest, "emailOrderConfirmationBody");

		if (Validator.isNull(emailOrderConfirmationSubject)) {
			SessionErrors.add(actionRequest, "emailOrderConfirmationSubject");
		}
		else if (Validator.isNull(emailOrderConfirmationBody)) {
			SessionErrors.add(actionRequest, "emailOrderConfirmationBody");
		}
		else {
			preferences.setEmailOrderConfirmationEnabled(
				emailOrderConfirmationEnabled);
			preferences.setEmailOrderConfirmationSubject(
				emailOrderConfirmationSubject);
			preferences.setEmailOrderConfirmationBody(
				emailOrderConfirmationBody);
		}
	}

	protected void updateEmailOrderShipping(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		boolean emailOrderShippingEnabled = ParamUtil.getBoolean(
			actionRequest, "emailOrderShippingEnabled");
		String emailOrderShippingSubject = ParamUtil.getString(
			actionRequest, "emailOrderShippingSubject");
		String emailOrderShippingBody = ParamUtil.getString(
			actionRequest, "emailOrderShippingBody");

		if (Validator.isNull(emailOrderShippingSubject)) {
			SessionErrors.add(actionRequest, "emailOrderShippingSubject");
		}
		else if (Validator.isNull(emailOrderShippingBody)) {
			SessionErrors.add(actionRequest, "emailOrderShippingBody");
		}
		else {
			preferences.setEmailOrderShippingEnabled(emailOrderShippingEnabled);
			preferences.setEmailOrderShippingSubject(emailOrderShippingSubject);
			preferences.setEmailOrderShippingBody(emailOrderShippingBody);
		}
	}

	protected void updateInsuranceCalculation(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		String insuranceFormula = ParamUtil.getString(
			actionRequest, "insuranceFormula");

		String[] insurance = new String[5];

		for (int i = 0; i < insurance.length; i++) {
			insurance[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "insurance" + i));
		}

		preferences.setInsuranceFormula(insuranceFormula);
		preferences.setInsurance(insurance);
	}

	protected void updatePayment(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		String payPalEmailAddress = ParamUtil.getString(
			actionRequest, "payPalEmailAddress");
		String[] ccTypes = StringUtil.split(
			ParamUtil.getString(actionRequest, "ccTypes"));
		String currencyId = ParamUtil.getString(actionRequest, "currencyId");
		String taxState = ParamUtil.getString(actionRequest, "taxState");
		double taxRate = ParamUtil.getDouble(actionRequest, "taxRate") / 100;
		double minOrder = ParamUtil.getDouble(actionRequest, "minOrder");

		preferences.setPayPalEmailAddress(payPalEmailAddress);
		preferences.setCcTypes(ccTypes);
		preferences.setCurrencyId(currencyId);
		preferences.setTaxState(taxState);
		preferences.setTaxRate(taxRate);
		preferences.setMinOrder(minOrder);
	}

	protected void updateShippingCalculation(
			ActionRequest actionRequest, ShoppingPreferences preferences)
		throws Exception {

		String shippingFormula = ParamUtil.getString(
			actionRequest, "shippingFormula");

		String[] shipping = new String[5];

		for (int i = 0; i < shipping.length; i++) {
			shipping[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "shipping" + i));
		}

		preferences.setShippingFormula(shippingFormula);
		preferences.setShipping(shipping);
	}

}