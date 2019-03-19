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

package com.liferay.portal.settings.authentication.ldap.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletContextFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.ldap.DuplicateLDAPServerNameException;
import com.liferay.portal.security.ldap.LDAPServerNameException;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
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
			if (e instanceof DuplicateLDAPServerNameException ||
				e instanceof LDAPFilterException ||
				e instanceof LDAPServerNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				PortletURL portletURL = PortletURLFactoryUtil.create(
					actionRequest,
					ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter(
					"mvcRenderCommandName",
					"/portal_settings/edit_ldap_server");

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				portletURL.setParameter("redirect", redirect);

				actionResponse.sendRedirect(portletURL.toString());

				return;
			}

			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");

				return;
			}

			throw e;
		}
	}

	@Activate
	protected void activate() {
		_portletContext = _portletContextFactory.createUntrackedInstance(
			_portlet, _servletContext);
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
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(
		target = "(javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS + ")",
		unbind = "-"
	)
	protected void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	@Reference(unbind = "-")
	protected void setPortletContextFactory(
		PortletContextFactory portletContextFactory) {

		_portletContextFactory = portletContextFactory;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.authentication.ldap.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
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

		_splitStringArrays(dictionary, LDAPConstants.CONTACT_CUSTOM_MAPPINGS);
		_splitStringArrays(dictionary, LDAPConstants.CONTACT_MAPPINGS);
		_splitStringArrays(
			dictionary, LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES);
		_splitStringArrays(dictionary, LDAPConstants.GROUP_MAPPINGS);
		_splitStringArrays(dictionary, LDAPConstants.USER_CUSTOM_MAPPINGS);
		_splitStringArrays(
			dictionary, LDAPConstants.USER_DEFAULT_OBJECT_CLASSES);
		_splitStringArrays(dictionary, LDAPConstants.USER_MAPPINGS);

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
			_ldapServerConfigurationProvider.getConfigurations(companyId);

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

		_ldapFilterValidator.validate(userFilter, "importUserSearchFilter");

		String groupFilter = ParamUtil.getString(
			actionRequest, "importGroupSearchFilter");

		_ldapFilterValidator.validate(groupFilter, "importGroupSearchFilter");
	}

	private void _splitStringArrays(
		Dictionary<String, Object> dictionary, String property) {

		Object propertyValue = dictionary.get(property);

		if (propertyValue == null) {
			return;
		}

		if (propertyValue instanceof String) {
			String[] propertyValues = StringUtil.split((String)propertyValue);

			dictionary.put(property, propertyValues);
		}
	}

	private static ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;

	private CounterLocalService _counterLocalService;

	@Reference
	private LDAPFilterValidator _ldapFilterValidator;

	private Portal _portal;
	private Portlet _portlet;
	private PortletContext _portletContext;
	private PortletContextFactory _portletContextFactory;
	private ServletContext _servletContext;

}