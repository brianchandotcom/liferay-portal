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

package com.liferay.click.to.chat.web.internal.portlet.action;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseFormMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/click_to_chat/click_to_chat_configuration_form"
	},
	service = MVCActionCommand.class
)
public class ClickToChatConfigurationFormMVCActionCommand
	extends BaseFormMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		_saveClickToChatConfiguration(
			actionRequest, _configurationProvider, themeDisplay.getCompanyId());
	}

	@Override
	protected void doValidateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	private void _saveClickToChatConfiguration(
			ActionRequest actionRequest,
			ConfigurationProvider configurationProvider, long companyId)
		throws Exception {

		Dictionary<String, Object> properties = new Hashtable<>();

		_setBooleanProperties(
			actionRequest, properties, "enabled", "guestUsersAllowed");
		_setStringProperties(
			actionRequest, properties, "chatProviderId",
			"chatProviderAccountId", "siteSettingsStrategy");

		configurationProvider.saveCompanyConfiguration(
			ClickToChatConfiguration.class, companyId, properties);
	}

	private void _setBooleanProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			boolean value = ParamUtil.getBoolean(
				actionRequest,
				"TypeSettingsProperties--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	private void _setStringProperties(
		ActionRequest actionRequest, Dictionary<String, Object> properties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			String value = ParamUtil.getString(
				actionRequest,
				"TypeSettingsProperties--" + propertyName + "--");

			properties.put(propertyName, value);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}