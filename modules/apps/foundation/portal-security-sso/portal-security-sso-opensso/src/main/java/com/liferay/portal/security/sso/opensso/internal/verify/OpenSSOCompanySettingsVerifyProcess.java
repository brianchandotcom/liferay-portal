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

package com.liferay.portal.security.sso.opensso.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.security.sso.opensso.constants.LegacyOpenSSOPropsKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConfigurationKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.verify.BaseCompanySettingsVerifyProcess;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Dictionary;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Greenwald
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.portal.security.sso.opensso"},
	service = VerifyProcess.class
)
public class OpenSSOCompanySettingsVerifyProcess
	extends BaseCompanySettingsVerifyProcess {

	@Override
	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	@Override
	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyOpenSSOPropsKeys.OPENSSO_KEYS);
	}

	@Override
	protected Dictionary<String, String> getPropertyValues(long companyId) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		String emailAddressAttr = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_EMAIL_ADDRESS_ATTR);

		if (emailAddressAttr != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.EMAIL_ADDRESS_ATTR, emailAddressAttr);
		}

		String enabled = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_AUTH_ENABLED);

		if (enabled != null) {
			dictionary.put(OpenSSOConfigurationKeys.AUTH_ENABLED, enabled);
		}

		String firstNameAttr = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_FIRST_NAME_ATTR);

		if (firstNameAttr != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.FIRST_NAME_ATTR, firstNameAttr);
		}

		String importFromLDAP = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_IMPORT_FROM_LDAP);

		if (importFromLDAP != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.IMPORT_FROM_LDAP, importFromLDAP);
		}

		String lastNameAttr = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_LAST_NAME_ATTR);

		if (lastNameAttr != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.LAST_NAME_ATTR, lastNameAttr);
		}

		String loginURL = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_LOGIN_URL);

		if (loginURL != null) {
			dictionary.put(OpenSSOConfigurationKeys.LOGIN_URL, loginURL);
		}

		String logoutOnSessionExpiration = _prefsProps.getString(
			companyId,
			LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_ON_SESSION_EXPIRATION);

		if (logoutOnSessionExpiration != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.LOGOUT_ON_SESSION_EXPIRATION,
				logoutOnSessionExpiration);
		}

		String logoutURL = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_URL);

		if (logoutURL != null) {
			dictionary.put(OpenSSOConfigurationKeys.LOGOUT_URL, logoutURL);
		}

		String screenNameAttr = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_SCREEN_NAME_ATTR);

		if (screenNameAttr != null) {
			dictionary.put(
				OpenSSOConfigurationKeys.SCREEN_NAME_ATTR, screenNameAttr);
		}

		String serviceURL = _prefsProps.getString(
			companyId, LegacyOpenSSOPropsKeys.OPENSSO_SERVICE_URL);

		if (serviceURL != null) {
			dictionary.put(OpenSSOConfigurationKeys.SERVICE_URL, serviceURL);
		}

		return dictionary;
	}

	@Override
	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Override
	protected String getSettingsId() {
		return OpenSSOConstants.SERVICE_NAME;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setPrefsProps(PrefsProps prefsProps) {
		_prefsProps = prefsProps;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private CompanyLocalService _companyLocalService;
	private PrefsProps _prefsProps;
	private SettingsFactory _settingsFactory;

}