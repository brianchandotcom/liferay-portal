/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.web.internal.portlet.action;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/instance_settings/edit_vies_account_entry_validator_configuration"
	},
	service = MVCActionCommand.class
)
public class EditVIESAccountEntryValidatorConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			throw new PortletException(
				new PrincipalException.MustBeCompanyAdmin(
					permissionChecker.getUserId()));
		}

		String viesEndpointURL = ParamUtil.getString(
			actionRequest, "viesEndpointURL");

		if (!Validator.isUrl(viesEndpointURL)) {
			SessionErrors.add(actionRequest, "viesEndpointURLInvalid");

			hideDefaultErrorMessage(actionRequest);

			sendRedirect(actionRequest, actionResponse);

			return;
		}

		_configurationProvider.saveCompanyConfiguration(
			VIESAccountEntryValidatorConfiguration.class,
			themeDisplay.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"checkInterval",
				Math.max(
					1, ParamUtil.getInteger(actionRequest, "checkInterval", 15))
			).put(
				"countries",
				StringUtil.split(
					ParamUtil.getString(actionRequest, "countries"))
			).put(
				"enabled", ParamUtil.getBoolean(actionRequest, "enabled")
			).put(
				"viesEndpointURL", viesEndpointURL
			).build());

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}