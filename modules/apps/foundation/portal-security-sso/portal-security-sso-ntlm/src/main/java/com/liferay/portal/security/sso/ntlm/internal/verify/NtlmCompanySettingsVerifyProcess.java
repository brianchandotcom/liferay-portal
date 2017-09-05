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

package com.liferay.portal.security.sso.ntlm.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.security.sso.ntlm.constants.LegacyNtlmPropsKeys;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConfigurationKeys;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
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
	property = {"verify.process.name=com.liferay.portal.security.sso.ntlm"},
	service = VerifyProcess.class
)
public class NtlmCompanySettingsVerifyProcess
	extends BaseCompanySettingsVerifyProcess {

	@Override
	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	@Override
	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyNtlmPropsKeys.NTLM_AUTH_KEYS);
	}

	@Override
	protected Dictionary<String, String> getPropertyValues(long companyId) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		String domain = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_DOMAIN);

		if (domain != null) {
			dictionary.put(NtlmConfigurationKeys.AUTH_DOMAIN, domain);
		}

		String domainController = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_DOMAIN_CONTROLLER);

		if (domainController != null) {
			dictionary.put(
				NtlmConfigurationKeys.AUTH_DOMAIN_CONTROLLER, domainController);
		}

		String domainControllerName = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_DOMAIN_CONTROLLER_NAME);

		if (domainControllerName != null) {
			dictionary.put(
				NtlmConfigurationKeys.AUTH_DOMAIN_CONTROLLER_NAME,
				domainControllerName);
		}

		String enabled = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_ENABLED);

		if (enabled != null) {
			dictionary.put(NtlmConfigurationKeys.AUTH_ENABLED, enabled);
		}

		String negotiateFlags = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_NEGOTIATE_FLAGS);

		if (negotiateFlags != null) {
			dictionary.put(
				NtlmConfigurationKeys.AUTH_NEGOTIATE_FLAGS, negotiateFlags);
		}

		String serviceAccount = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_SERVICE_ACCOUNT);

		if (serviceAccount != null) {
			dictionary.put(
				NtlmConfigurationKeys.AUTH_SERVICE_ACCOUNT, serviceAccount);
		}

		String servicePassword = _prefsProps.getString(
			companyId, LegacyNtlmPropsKeys.NTLM_AUTH_SERVICE_PASSWORD);

		if (servicePassword != null) {
			dictionary.put(
				NtlmConfigurationKeys.AUTH_SERVICE_PASSWORD, servicePassword);
		}

		return dictionary;
	}

	@Override
	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Override
	protected String getSettingsId() {
		return NtlmConstants.SERVICE_NAME;
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