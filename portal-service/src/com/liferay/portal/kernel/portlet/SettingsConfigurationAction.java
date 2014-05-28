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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import javax.servlet.ServletContext;

/**
 * @author Iván Zaera
 */
public class SettingsConfigurationAction
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

				if (!Validator.equals(values, oldValues)) {
					modifiableSettings.setValues(name, values);
				}
			}
		}

		postProcess(themeDisplay.getCompanyId(), actionRequest, settings);

		if (SessionErrors.isEmpty(actionRequest)) {
			try {
				modifiableSettings.store();
			}
			catch (ValidatorException ve) {
				SessionErrors.add(
					actionRequest, ValidatorException.class.getName(), ve);

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
	}

	@Override
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		PortletConfig selPortletConfig = getSelPortletConfig(renderRequest);

		String configTemplate = selPortletConfig.getInitParameter(
			"config-template");

		if (Validator.isNotNull(configTemplate)) {
			return configTemplate;
		}

		String configJSP = selPortletConfig.getInitParameter("config-jsp");

		if (Validator.isNotNull(configJSP)) {
			return configJSP;
		}

		return "/configuration.jsp";
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
			portletPreferencesMap = new HashMap<String, String[]>();

			portletRequest.setAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP, portletPreferencesMap);
		}

		portletPreferencesMap.put(name, values);
	}

	protected void addMultiValuedKeys(String... multiValuedKeys) {
		Collections.addAll(_multiValuedKeys, multiValuedKeys);
	}

	protected PortletConfig getSelPortletConfig(PortletRequest portletRequest)
		throws SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		Portlet selPortlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		ServletContext servletContext =
			(ServletContext)portletRequest.getAttribute(WebKeys.CTX);

		PortletConfig selPortletConfig = PortletConfigFactoryUtil.create(
			selPortlet, servletContext);

		return selPortletConfig;
	}

	protected Settings getSettings(ActionRequest actionRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serviceName = ParamUtil.getString(actionRequest, "serviceName");

		String settingsScope = ParamUtil.getString(
			actionRequest, "settingsScope");

		if (settingsScope.equals("company")) {
			return SettingsFactoryUtil.getCompanyServiceSettings(
				themeDisplay.getCompanyId(), serviceName);
		}
		else if (settingsScope.equals("group")) {
			return SettingsFactoryUtil.getGroupServiceSettings(
				themeDisplay.getSiteGroupId(), serviceName);
		}
		else if (settingsScope.equals("portletInstance")) {
			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			return SettingsFactoryUtil.getPortletInstanceSettings(
				themeDisplay.getLayout(), portletResource);
		}

		throw new IllegalArgumentException(
			"Invalid settings scope " + settingsScope);
	}

	@SuppressWarnings("unused")
	protected void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException, SystemException {
	}

	protected void setParameterNamePrefix(String parameterNamePrefix) {
		_parameterNamePrefix = parameterNamePrefix;
	}

	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		for (String multiValuedKey : _multiValuedKeys) {
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

	private List<String> _multiValuedKeys = new ArrayList<String>();
	private String _parameterNamePrefix;

}