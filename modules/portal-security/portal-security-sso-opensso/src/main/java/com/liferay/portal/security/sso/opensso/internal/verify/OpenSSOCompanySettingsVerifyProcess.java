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

import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.sso.opensso.constants.LegacyOpenSSOPropsKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConfigurationKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.service.CompanyLocalService;
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

		dictionary.put(
			OpenSSOConfigurationKeys.EMAIL_ADDRESS_ATTR,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_EMAIL_ADDRESS_ATTR,
				"mail"));
		dictionary.put(
			OpenSSOConfigurationKeys.AUTH_ENABLED,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_AUTH_ENABLED,
				StringPool.FALSE));
		dictionary.put(
			OpenSSOConfigurationKeys.FIRST_NAME_ATTR,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_FIRST_NAME_ATTR,
				"givenname"));
		dictionary.put(
			OpenSSOConfigurationKeys.IMPORT_FROM_LDAP,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_IMPORT_FROM_LDAP,
				StringPool.FALSE));
		dictionary.put(
			OpenSSOConfigurationKeys.LAST_NAME_ATTR,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_LAST_NAME_ATTR,
				"sn"));
		dictionary.put(
			OpenSSOConfigurationKeys.LOGIN_URL,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_LOGIN_URL,
				"http://openssohost.example.com:8080/opensso/UI/Login?goto=" +
					"http://portalhost.example.com:8080/c/portal/login"));
		dictionary.put(
			OpenSSOConfigurationKeys.LOGOUT_ON_SESSION_EXPIRATION,
			_prefsProps.getString(
				companyId,
				LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_ON_SESSION_EXPIRATION,
				StringPool.FALSE));
		dictionary.put(
			OpenSSOConfigurationKeys.LOGOUT_URL,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_URL,
				"http://openssohost.example.com:8080/opensso/UI/Logout?goto=" +
					"http://portalhost.example.com:8080/web/guest/home"));
		dictionary.put(
			OpenSSOConfigurationKeys.SCREEN_NAME_ATTR,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_SCREEN_NAME_ATTR,
				"uid"));
		dictionary.put(
			OpenSSOConfigurationKeys.SERVICE_URL,
			_prefsProps.getString(
				companyId, LegacyOpenSSOPropsKeys.OPENSSO_SERVICE_URL,
				"http://openssohost.example.com:8080/opensso"));

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