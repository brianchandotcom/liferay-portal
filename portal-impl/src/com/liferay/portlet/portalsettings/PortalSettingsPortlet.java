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

package com.liferay.portlet.portalsettings;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.AccountNameException;
import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.DuplicatePasswordPolicyException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.PasswordPolicyNameException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.RequiredPasswordPolicyException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.ldap.DuplicateLDAPServerNameException;
import com.liferay.portal.kernel.ldap.LDAPServerNameException;
import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Philip Jones
 */
public class PortalSettingsPortlet extends MVCPortlet {

	public void deleteLDAPServer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		// Remove portletPreferences

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String[] keys = new String[_KEYS.length];

		for (int i = 0; i < _KEYS.length; i++) {
			keys[i] = _KEYS[i] + postfix;
		}

		CompanyServiceUtil.removePreferences(themeDisplay.getCompanyId(), keys);

		// Update portletPreferences

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			themeDisplay.getCompanyId(), true);

		UnicodeProperties properties = new UnicodeProperties();

		String ldapServerIds = portletPreferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.removeFromList(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.put("ldap.server.ids", ldapServerIds);

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	public void editCompany(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		validateCAS(actionRequest);
		validateLDAP(actionRequest);
		validateSocialInteractions(actionRequest);
		updateCompany(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	public void editLDAPServer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		validateLDAPServerName(
			ldapServerId, themeDisplay.getCompanyId(), properties);

		validateSearchFilters(actionRequest);

		if (ldapServerId <= 0) {
			properties = addLDAPServer(themeDisplay.getCompanyId(), properties);
		}

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	protected UnicodeProperties addLDAPServer(
			long companyId, UnicodeProperties properties)
		throws Exception {

		String defaultPostfix = LDAPSettingsUtil.getPropertyPostfix(0);

		Set<String> defaultKeys = new HashSet<>(_KEYS.length);

		for (String key : _KEYS) {
			defaultKeys.add(key + defaultPostfix);
		}

		long ldapServerId = CounterLocalServiceUtil.increment();

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Set<String> keysSet = properties.keySet();

		String[] keys = keysSet.toArray(new String[keysSet.size()]);

		for (String key : keys) {
			if (defaultKeys.contains(key)) {
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

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		String ldapServerIds = portletPreferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.add(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.setProperty("ldap.server.ids", ldapServerIds);

		return properties;
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest,
				DuplicatePasswordPolicyException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchPasswordPolicyException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, PasswordPolicyNameException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest,
				RequiredPasswordPolicyException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, AddressCityException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, AccountNameException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, AddressStreetException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, AddressZipException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, CompanyMxException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, CompanyVirtualHostException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, CompanyWebIdException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, EmailAddressException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, LocaleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchCountryException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchListTypeException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRegionException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PhoneNumberException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, WebsiteURLException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchListTypeException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicatePasswordPolicyException ||
			cause instanceof NoSuchPasswordPolicyException ||
			cause instanceof PrincipalException ||
			cause instanceof PasswordPolicyNameException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredPasswordPolicyException ||
			cause instanceof AddressCityException ||
			cause instanceof AccountNameException ||
			cause instanceof AddressStreetException ||
			cause instanceof AddressZipException ||
			cause instanceof CompanyMxException ||
			cause instanceof CompanyVirtualHostException ||
			cause instanceof CompanyWebIdException ||
			cause instanceof EmailAddressException ||
			cause instanceof LocaleException ||
			cause instanceof NoSuchCountryException ||
			cause instanceof NoSuchListTypeException ||
			cause instanceof NoSuchRegionException ||
			cause instanceof PhoneNumberException ||
			cause instanceof WebsiteURLException ||
			cause instanceof NoSuchListTypeException) {
				return true;
		}

		return false;
	}

	protected void updateCompany(ActionRequest actionRequest)
		throws IOException, PortalException {

		long companyId = PortalUtil.getCompanyId(actionRequest);

		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname");
		String mx = ParamUtil.getString(actionRequest, "mx");
		String homeURL = ParamUtil.getString(actionRequest, "homeURL");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		String name = ParamUtil.getString(actionRequest, "name");
		String legalName = ParamUtil.getString(actionRequest, "legalName");
		String legalId = ParamUtil.getString(actionRequest, "legalId");
		String legalType = ParamUtil.getString(actionRequest, "legalType");
		String sicCode = ParamUtil.getString(actionRequest, "sicCode");
		String tickerSymbol = ParamUtil.getString(
			actionRequest, "tickerSymbol");
		String industry = ParamUtil.getString(actionRequest, "industry");
		String type = ParamUtil.getString(actionRequest, "type");
		String size = ParamUtil.getString(actionRequest, "size");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);
		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);
		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		CompanyServiceUtil.updateCompany(
			companyId, virtualHostname, mx, homeURL, !deleteLogo, logoBytes,
			name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size, languageId, timeZoneId, addresses,
			emailAddresses, phones, websites, properties);

		PortalUtil.resetCDNHosts();
	}

	protected void validateCAS(ActionRequest actionRequest) {
		boolean casEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.CAS_AUTH_ENABLED + "--");

		if (!casEnabled) {
			return;
		}

		String casLoginURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_LOGIN_URL + "--");
		String casLogoutURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_LOGOUT_URL + "--");
		String casServerName = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVER_NAME + "--");
		String casServerURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVER_URL + "--");
		String casServiceURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVICE_URL + "--");
		String casNoSuchUserRedirectURL = ParamUtil.getString(
			actionRequest,
			"settings--" + PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL + "--");

		if (!Validator.isUrl(casLoginURL)) {
			SessionErrors.add(actionRequest, "casLoginURLInvalid");
		}

		if (!Validator.isUrl(casLogoutURL)) {
			SessionErrors.add(actionRequest, "casLogoutURLInvalid");
		}

		if (Validator.isNull(casServerName)) {
			SessionErrors.add(actionRequest, "casServerNameInvalid");
		}

		if (!Validator.isUrl(casServerURL)) {
			SessionErrors.add(actionRequest, "casServerURLInvalid");
		}

		if (Validator.isNotNull(casServiceURL) &&
			!Validator.isUrl(casServiceURL)) {
				SessionErrors.add(actionRequest, "casServiceURLInvalid");
		}

		if (Validator.isNotNull(casNoSuchUserRedirectURL) &&
			!Validator.isUrl(casNoSuchUserRedirectURL)) {
				SessionErrors.add(actionRequest, "casNoSuchUserURLInvalid");
		}
	}

	protected void validateLDAP(ActionRequest actionRequest) {
		if (!PropsValues.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED) {
			return;
		}

		boolean ldapExportEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.LDAP_EXPORT_ENABLED + "--");
		boolean ldapImportEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.LDAP_IMPORT_ENABLED + "--");

		if (ldapExportEnabled && ldapImportEnabled) {
			SessionErrors.add(
				actionRequest, "ldapExportAndImportOnPasswordAutogeneration");
		}
	}

	protected void validateLDAPServerName(
			long ldapServerId, long companyId, UnicodeProperties properties)
		throws Exception {

		String ldapServerName = properties.getProperty(
			"ldap.server.name." + ldapServerId);

		if (Validator.isNull(ldapServerName)) {
			throw new LDAPServerNameException();
		}

		long[] existingLDAPServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long existingLDAPServerId : existingLDAPServerIds) {
			if (ldapServerId == existingLDAPServerId) {
				continue;
			}

			String existingLDAPServerName = PrefsPropsUtil.getString(
				companyId, "ldap.server.name." + existingLDAPServerId);

			if (ldapServerName.equals(existingLDAPServerName)) {
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

	protected void validateSocialInteractions(ActionRequest actionRequest) {
		boolean socialInteractionsEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--socialInteractionsEnabled--");

		if (!socialInteractionsEnabled) {
			return;
		}

		boolean socialInteractionsAnyUserEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--socialInteractionsAnyUserEnabled--");

		if (socialInteractionsAnyUserEnabled) {
			return;
		}

		boolean socialInteractionsSocialRelationTypesEnabled =
			ParamUtil.getBoolean(
				actionRequest,
				"settings--socialInteractionsSocialRelationTypesEnabled--");
		String socialInteractionsSocialRelationTypes = ParamUtil.getString(
			actionRequest, "settings--socialInteractionsSocialRelationTypes--");

		if (socialInteractionsSocialRelationTypesEnabled &&
			Validator.isNull(socialInteractionsSocialRelationTypes)) {

			SessionErrors.add(
				actionRequest, "socialInteractionsSocialRelationTypes");
		}

		boolean socialInteractionsSitesEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--socialInteractionsSitesEnabled--");

		if (!socialInteractionsSocialRelationTypesEnabled &&
			!socialInteractionsSitesEnabled) {

			SessionErrors.add(actionRequest, "socialInteractionsInvalid");
		}
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