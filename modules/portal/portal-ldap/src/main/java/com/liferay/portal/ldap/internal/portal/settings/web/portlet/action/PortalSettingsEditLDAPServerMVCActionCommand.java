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

package com.liferay.portal.ldap.internal.portal.settings.web.portlet.action;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.portal.kernel.ldap.DuplicateLDAPServerNameException;
import com.liferay.portal.kernel.ldap.LDAPFilterException;
import com.liferay.portal.kernel.ldap.LDAPServerNameException;
import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.ldap.constants.LDAPConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS,
		"mvc.command.name=/portal_settings/edit_ldap_server"
	},
	service = MVCActionCommand.class

)
public class PortalSettingsEditLDAPServerMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLDAPServer(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLDAPServer(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			String mvcPath = "/edit_ldap_server.jsp";

			if (e instanceof DuplicateLDAPServerNameException ||
				e instanceof LDAPFilterException ||
				e instanceof LDAPServerNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected void deleteLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		_ldapServerConfigurationProvider.delete(
			themeDisplay.getCompanyId(), ldapServerId);
	}

	@Reference(unbind = "-")
	protected void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	protected void updateLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(
			actionRequest, LDAPConstants.LDAP_SERVER_ID);

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "ldap--");

		validateLDAPServerName(
			ldapServerId, themeDisplay.getCompanyId(), properties);

		validateSearchFilters(actionRequest);

		Dictionary<String, Object> dictionary = null;

		if (ldapServerId <= 0) {
			ldapServerId = _counterLocalService.increment();

			dictionary = new HashMapDictionary<>();
		}
		else {
			dictionary =
				_ldapServerConfigurationProvider.getConfigurationProperties(
					themeDisplay.getCompanyId(), ldapServerId);
		}

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			dictionary.put(entry.getKey(), entry.getValue());
		}

		splitStringArrays(dictionary, LDAPConstants.CONTACT_CUSTOM_MAPPINGS);
		splitStringArrays(dictionary, LDAPConstants.CONTACT_MAPPINGS);
		splitStringArrays(
			dictionary, LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES);
		splitStringArrays(dictionary, LDAPConstants.GROUP_MAPPINGS);
		splitStringArrays(dictionary, LDAPConstants.USER_CUSTOM_MAPPINGS);
		splitStringArrays(
			dictionary, LDAPConstants.USER_DEFAULT_OBJECT_CLASSES);
		splitStringArrays(dictionary, LDAPConstants.USER_MAPPINGS);

		_ldapServerConfigurationProvider.updateProperties(
			themeDisplay.getCompanyId(), ldapServerId, dictionary);
	}

	protected void validateLDAPServerName(
			long ldapServerId, long companyId, UnicodeProperties properties)
		throws Exception {

		String serverName = properties.getProperty(LDAPConstants.SERVER_NAME);

		if (Validator.isNull(serverName)) {
			throw new LDAPServerNameException();
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(
				companyId, false);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			String existingServerName = ldapServerConfiguration.serverName();

			if (serverName.equals(existingServerName) &&
				(ldapServerId != ldapServerConfiguration.ldapServerId())) {

				throw new DuplicateLDAPServerNameException();
			}
		}
	}

	protected void validateSearchFilters(ActionRequest actionRequest)
		throws Exception {

		String userFilter = ParamUtil.getString(
			actionRequest, "importUserSearchFilter");

		LDAPUtil.validateFilter(userFilter, "importUserSearchFilter");

		String groupFilter = ParamUtil.getString(
			actionRequest, "importGroupSearchFilter");

		LDAPUtil.validateFilter(groupFilter, "importGroupSearchFilter");
	}

	private void splitStringArrays(
		Dictionary<String, Object> dictionary, String property) {

		Object propertyValue = dictionary.get(property);

		if (Validator.isNull(propertyValue)) {
			return;
		}

		if (propertyValue instanceof String) {
			String propertyValues = StringUtil.replace(
				(String)propertyValue, StringPool.COMMA, StringPool.PIPE);

			dictionary.put(property, propertyValues);
		}
	}

	private static ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;

	private volatile CounterLocalService _counterLocalService;

}