/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portalsettings.action;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ryan Park
 */
public class EditLDAPServerAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
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
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.portal_settings.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(getForward(
			renderRequest, "portlet.portal_settings.edit_ldap_server"));
	}

	protected UnicodeProperties addLDAPServer(
			long companyId, UnicodeProperties properties)
		throws Exception {

		String defaultPostfix = LDAPSettingsUtil.getPropertyPostfix(0);

		String[] defaultKeys = new String[_KEYS.length];

		for (int i = 0; i < _KEYS.length; i++) {
			defaultKeys[i] = _KEYS[i] + defaultPostfix;
		}

		long ldapServerId = CounterLocalServiceUtil.increment();

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String[] keys = properties.keySet().toArray(new String[0]);

		for (String key : keys) {
			if (ArrayUtil.contains(defaultKeys, key)) {
				String value = properties.remove(key);

				if (key.equals(
						PropsKeys.LDAP_SECURITY_CREDENTIALS + defaultPostfix) &&
					value.equals(Portal.TEMP_OBFUSCATION_VALUE)) {

					value = PrefsPropsUtil.getString(
						PropsKeys.LDAP_SECURITY_CREDENTIALS);
				}

				properties.setProperty(
					key.replace(defaultPostfix, postfix), value);
			}
		}

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		String ldapServerIds = preferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.add(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.setProperty("ldap.server.ids", ldapServerIds);

		return properties;
	}

	protected void deleteLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		// Remove preferences

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String[] keys = new String[_KEYS.length];

		for (int i = 0; i < _KEYS.length; i++) {
			keys[i] = _KEYS[i] + postfix;
		}

		CompanyServiceUtil.removePreferences(themeDisplay.getCompanyId(), keys);

		// Update preferences

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			themeDisplay.getCompanyId());

		UnicodeProperties properties = new UnicodeProperties();

		String ldapServerIds = preferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.remove(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.put("ldap.server.ids", ldapServerIds);

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	protected void updateLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		if (ldapServerId <= 0) {
			properties = addLDAPServer(themeDisplay.getCompanyId(), properties);
		}

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	private static final String[] _KEYS = {
		PropsKeys.LDAP_AUTH_SEARCH_FILTER, PropsKeys.LDAP_BASE_DN,
		PropsKeys.LDAP_BASE_PROVIDER_URL,
		PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS, PropsKeys.LDAP_CONTACT_MAPPINGS,
		PropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES,
		PropsKeys.LDAP_GROUP_MAPPINGS, PropsKeys.LDAP_GROUPS_DN,
		PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER,
		PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER,
		PropsKeys.LDAP_SECURITY_CREDENTIALS, PropsKeys.LDAP_SECURITY_PRINCIPAL,
		PropsKeys.LDAP_SERVER_NAME, PropsKeys.LDAP_USER_CUSTOM_MAPPINGS,
		PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES,
		PropsKeys.LDAP_USER_MAPPINGS, PropsKeys.LDAP_USERS_DN
	};

}