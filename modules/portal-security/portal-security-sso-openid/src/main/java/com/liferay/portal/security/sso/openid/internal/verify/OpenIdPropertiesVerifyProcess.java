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

package com.liferay.portal.security.sso.openid.internal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.sso.openid.constants.LegacyOpenIdPropsKeys;
import com.liferay.portal.security.sso.openid.constants.OpenIdConstants;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;

import java.io.IOException;

import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.portal.security.sso.openid"},
	service = VerifyProcess.class
)
public class OpenIdPropertiesVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyProperties();
	}

	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyOpenIdPropsKeys.OPENID_KEYS);
	}

	protected Dictionary<String, String> getPropertyValues(long companyId) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put(
			OpenIdConstants.AUTH_ENABLED,
			_prefsProps.getString(
				companyId, LegacyOpenIdPropsKeys.OPENID_AUTH_ENABLED,
				StringPool.FALSE));

		return dictionary;
	}

	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	protected String getSettingsId() {
		return OpenIdConstants.SERVICE_NAME;
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

	protected void storeSettings(
			long companyId, String settingsId,
			Dictionary<String, String> dictionary)
		throws IOException, SettingsException, ValidatorException {

		Settings settings = getSettingsFactory().getSettings(
			new CompanyServiceSettingsLocator(companyId, settingsId));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		SettingsDescriptor settingsDescriptor =
			getSettingsFactory().getSettingsDescriptor(settingsId);

		for (String name : settingsDescriptor.getAllKeys()) {
			String value = dictionary.get(name);

			String oldValue = settings.getValue(name, null);

			if (!value.equals(oldValue)) {
				modifiableSettings.setValue(name, value);
			}
		}

		modifiableSettings.store();
	}

	protected void verifyProperties() throws Exception {
		List<Company> companies = getCompanyLocalService().getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			Dictionary<String, String> dictionary = getPropertyValues(
				companyId);

			storeSettings(companyId, getSettingsId(), dictionary);

			Set<String> keys = getLegacyPropertyKeys();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Removing preference keys " + keys + " for company " +
						companyId);
			}

			getCompanyLocalService().removePreferences(
				companyId, keys.toArray(new String[keys.size()]));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdPropertiesVerifyProcess.class);

	private volatile CompanyLocalService _companyLocalService;
	private volatile PrefsProps _prefsProps;
	private volatile SettingsFactory _settingsFactory;

}