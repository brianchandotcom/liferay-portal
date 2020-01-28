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

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public abstract class SettingsConfigurationAction
	extends LiferayPortlet
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public SettingsConfigurationAction() {
		setParameterNamePrefix("preferences--");
	}

	public String getLocalizedParameter(
		PortletRequest portletRequest, String name) {

		String languageId = ParamUtil.getString(portletRequest, "languageId");

		return getLocalizedParameter(portletRequest, name, languageId);
	}

	public String getLocalizedParameter(
		PortletRequest portletRequest, String name, String languageId) {

		return getParameter(
			portletRequest,
			LocalizationUtil.getLocalizedName(name, languageId));
	}

	public String getParameter(PortletRequest portletRequest, String name) {
		name = _parameterNamePrefix + name + StringPool.DOUBLE_DASH;

		return ParamUtil.getString(portletRequest, name);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		updateMultiValuedKeys(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = PortletConfigurationLayoutUtil.getLayout(themeDisplay);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			layout, portletResource, ActionKeys.CONFIGURATION);

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, _parameterNamePrefix);

		Settings settings = getSettings(actionRequest);

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			String oldValue = settings.getValue(name, null);

			if (!StringUtil.equalsIgnoreBreakLine(value, oldValue)) {
				modifiableSettings.setValue(name, value);
			}
		}

		Map<String, String[]> portletPreferencesMap =
			(Map<String, String[]>)actionRequest.getAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP);

		if (portletPreferencesMap != null) {
			for (Map.Entry<String, String[]> entry :
					portletPreferencesMap.entrySet()) {

				String name = entry.getKey();
				String[] values = entry.getValue();

				String[] oldValues = settings.getValues(name, null);

				if (!Objects.equals(values, oldValues)) {
					modifiableSettings.setValues(name, values);
				}
			}
		}

		postProcess(themeDisplay.getCompanyId(), actionRequest, settings);

		if (!SessionErrors.isEmpty(actionRequest)) {
			return;
		}

		try {
			modifiableSettings.store();
		}
		catch (ValidatorException validatorException) {
			SessionErrors.add(
				actionRequest, ValidatorException.class.getName(),
				validatorException);

			return;
		}

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			portletResource);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION);

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Override
	public void serveResource(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {
	}

	public void setPreference(
		PortletRequest portletRequest, String name, String value) {

		setPreference(portletRequest, name, new String[] {value});
	}

	public void setPreference(
		PortletRequest portletRequest, String name, String[] values) {

		Map<String, String[]> portletPreferencesMap =
			(Map<String, String[]>)portletRequest.getAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP);

		if (portletPreferencesMap == null) {
			portletPreferencesMap = new HashMap<>();

			portletRequest.setAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP, portletPreferencesMap);
		}

		portletPreferencesMap.put(name, values);
	}

	protected PortletConfig getSelPortletConfig(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		Portlet selPortlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		ServletContext servletContext =
			(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

		return PortletConfigFactoryUtil.create(selPortlet, servletContext);
	}

	protected Settings getSettings(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serviceName = ParamUtil.getString(actionRequest, "serviceName");

		String settingsScope = ParamUtil.getString(
			actionRequest, "settingsScope");

		if (settingsScope.equals("company")) {
			return SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					themeDisplay.getCompanyId(), serviceName));
		}
		else if (settingsScope.equals("group")) {
			return SettingsFactoryUtil.getSettings(
				new GroupServiceSettingsLocator(
					themeDisplay.getScopeGroupId(), serviceName));
		}
		else if (settingsScope.equals("portletInstance")) {
			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			return SettingsFactoryUtil.getSettings(
				new PortletInstanceSettingsLocator(
					themeDisplay.getLayout(), portletResource));
		}

		throw new IllegalArgumentException(
			"Invalid settings scope " + settingsScope);
	}

	protected String getSettingsId(ActionRequest actionRequest) {
		String settingsId = ParamUtil.getString(actionRequest, "serviceName");

		String settingsScope = ParamUtil.getString(
			actionRequest, "settingsScope");

		if (settingsScope.equals("portletInstance")) {
			settingsId = ParamUtil.getString(actionRequest, "portletResource");
		}

		return settingsId;
	}

	protected void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException {
	}

	protected void setParameterNamePrefix(String parameterNamePrefix) {
		_parameterNamePrefix = parameterNamePrefix;
	}

	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		SettingsDescriptor settingsDescriptor =
			SettingsFactoryUtil.getSettingsDescriptor(
				getSettingsId(actionRequest));

		Set<String> multiValuedKeys = settingsDescriptor.getMultiValuedKeys();

		for (String multiValuedKey : multiValuedKeys) {
			String multiValuedValue = getParameter(
				actionRequest, multiValuedKey);

			if (multiValuedValue != null) {
				setPreference(
					actionRequest, multiValuedKey,
					StringUtil.split(multiValuedValue));
			}
		}
	}

	protected void validateEmail(
		ActionRequest actionRequest, String emailParam) {

		boolean emailEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, emailParam + "Enabled"));
		String emailSubject = null;
		String emailBody = null;

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		emailSubject = getLocalizedParameter(
			actionRequest, emailParam + "Subject", languageId);
		emailBody = getLocalizedParameter(
			actionRequest, emailParam + "Body", languageId);

		if (emailEnabled) {
			if (Validator.isNull(emailSubject)) {
				SessionErrors.add(actionRequest, emailParam + "Subject");
			}
			else if (Validator.isNull(emailBody)) {
				SessionErrors.add(actionRequest, emailParam + "Body");
			}
		}
	}

	protected void validateEmailFrom(ActionRequest actionRequest) {
		String emailFromName = getParameter(actionRequest, "emailFromName");
		String emailFromAddress = getParameter(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
	}

	private String _parameterNamePrefix;

}