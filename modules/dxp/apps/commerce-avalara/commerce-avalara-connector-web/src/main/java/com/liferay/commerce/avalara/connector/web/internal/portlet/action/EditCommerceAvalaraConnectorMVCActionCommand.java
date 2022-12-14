/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.avalara.connector.web.internal.portlet.action;

import com.liferay.commerce.avalara.connector.CommerceAvalaraConnector;
import com.liferay.commerce.avalara.connector.configuration.CommerceAvalaraConnectorConfiguration;
import com.liferay.commerce.avalara.connector.web.internal.constants.CommerceAvalaraPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Katie Nesterovich
 */
@Component(
	property = {
		"javax.portlet.name=" + CommerceAvalaraPortletKeys.COMMERCE_AVALARA,
		"mvc.command.name=/commerce_avalara/edit_commerce_avalara_connector"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAvalaraConnectorMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_updateCommerceTaxAvalara(actionRequest);

			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals("verifyConnection")) {
				_verifyConnection(actionRequest);
				hideDefaultSuccessMessage(actionRequest);
				SessionMessages.add(actionRequest, "connectionSuccessful");
			}
		}
		catch (Throwable throwable) {
			SessionErrors.add(actionRequest, throwable.getClass(), throwable);

			hideDefaultErrorMessage(actionRequest);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	private void _updateCommerceTaxAvalara(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Settings settings = _settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(
				themeDisplay.getCompanyId(),
				CommerceAvalaraConnectorConfiguration.class.getName()));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String accountNumber = ParamUtil.getString(
			actionRequest, "accountNumber");

		modifiableSettings.setValue("accountNumber", accountNumber);

		String licenseKey = ParamUtil.getString(actionRequest, "licenseKey");

		modifiableSettings.setValue("licenseKey", licenseKey);

		String serviceURL = ParamUtil.getString(actionRequest, "serviceURL");

		modifiableSettings.setValue("serviceURL", serviceURL);

		modifiableSettings.store();
	}

	private void _verifyConnection(ActionRequest actionRequest)
		throws Exception {

		String accountNumber = ParamUtil.getString(
			actionRequest, "accountNumber");
		String licenseKey = ParamUtil.getString(actionRequest, "licenseKey");
		String serviceURL = ParamUtil.getString(actionRequest, "serviceURL");

		_commerceAvalaraConnector.verifyConnection(
			accountNumber, licenseKey, serviceURL);
	}

	@Reference
	private CommerceAvalaraConnector _commerceAvalaraConnector;

	@Reference
	private SettingsFactory _settingsFactory;

}