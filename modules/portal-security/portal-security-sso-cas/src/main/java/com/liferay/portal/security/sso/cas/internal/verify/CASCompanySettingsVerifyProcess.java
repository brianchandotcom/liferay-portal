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

package com.liferay.portal.security.sso.cas.internal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.sso.cas.constants.CASConfigurationKeys;
import com.liferay.portal.security.sso.cas.constants.CASConstants;
import com.liferay.portal.security.sso.cas.constants.LegacyCASPropsKeys;
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
	property = {"verify.process.name=com.liferay.portal.security.sso.cas"},
	service = VerifyProcess.class
)
public class CASCompanySettingsVerifyProcess
	extends BaseCompanySettingsVerifyProcess {

	@Override
	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	@Override
	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyCASPropsKeys.CAS_KEYS);
	}

	@Override
	protected Dictionary<String, String> getPropertyValues(long companyId) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put(
			CASConfigurationKeys.AUTH_ENABLED,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_AUTH_ENABLED,
				StringPool.FALSE));
		dictionary.put(
			CASConfigurationKeys.IMPORT_FROM_LDAP,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_IMPORT_FROM_LDAP,
				StringPool.FALSE));
		dictionary.put(
			CASConfigurationKeys.LOGIN_URL,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_LOGIN_URL,
				"https://localhost:8443/cas-web/login"));
		dictionary.put(
			CASConfigurationKeys.LOGOUT_ON_SESSION_EXPIRATION,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_LOGOUT_ON_SESSION_EXPIRATION,
				StringPool.FALSE));
		dictionary.put(
			CASConfigurationKeys.LOGOUT_URL,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_LOGOUT_URL,
				"https://localhost:8443/cas-web/logout"));
		dictionary.put(
			CASConfigurationKeys.NO_SUCH_USER_REDIRECT_URL,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL,
				"http://localhost:8080"));
		dictionary.put(
			CASConfigurationKeys.SERVER_NAME,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_SERVER_NAME,
				"https://localhost:8080"));
		dictionary.put(
			CASConfigurationKeys.SERVER_URL,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_SERVER_URL,
				"https://localhost:8443/cas-web/"));
		dictionary.put(
			CASConfigurationKeys.SERVICE_URL,
			_prefsProps.getString(
				companyId, LegacyCASPropsKeys.CAS_SERVICE_URL,
				"https://localhost:8080"));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Adding CAS configuration for company " + companyId +
					" with properties: " + dictionary);
		}

		return dictionary;
	}

	@Override
	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Override
	protected String getSettingsId() {
		return CASConstants.SERVICE_NAME;
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

	private static final Log _log = LogFactoryUtil.getLog(
		CASCompanySettingsVerifyProcess.class);

	private CompanyLocalService _companyLocalService;
	private PrefsProps _prefsProps;
	private SettingsFactory _settingsFactory;

}