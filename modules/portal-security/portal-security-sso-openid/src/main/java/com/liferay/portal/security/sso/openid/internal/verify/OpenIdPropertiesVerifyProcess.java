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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.sso.openid.constants.LegacyOpenIdPropsKeys;
import com.liferay.portal.security.sso.openid.constants.OpenIdConstants;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;

import java.io.IOException;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
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
		verifyOpenIdProperties();
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
			long companyId, Dictionary<String, Object> dictionary)
		throws IOException, SettingsException, ValidatorException {

		Settings settings = _settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(
				companyId, OpenIdConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		SettingsDescriptor settingsDescriptor =
			_settingsFactory.getSettingsDescriptor(
				OpenIdConstants.SERVICE_NAME);

		for (String name : settingsDescriptor.getAllKeys()) {
			String value = GetterUtil.getString(dictionary.get(name));

			String oldValue = settings.getValue(name, null);

			if (!value.equals(oldValue)) {
				modifiableSettings.setValue(name, value);
			}
		}

		modifiableSettings.store();
	}

	protected void verifyOpenIdAuthProperties(long companyId)
		throws IOException, SettingsException, ValidatorException {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			OpenIdConstants.AUTH_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyOpenIdPropsKeys.OPENID_AUTH_ENABLED, false));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding OpenID auth configuration for company " + companyId +
					" with properties: " + dictionary);
		}

		storeSettings(companyId, dictionary);
	}

	protected void verifyOpenIdProperties() throws Exception {
		List<Company> companies =_companyLocalService.getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			verifyOpenIdAuthProperties(companyId);

			Set<String> keys = new HashSet<>();

			keys.addAll(Arrays.asList(LegacyOpenIdPropsKeys.OPENID_KEYS));

			if (_log.isInfoEnabled()) {
				_log.info(
					"Removing preference keys " + keys + " for company " +
						companyId);
			}

			_companyLocalService.removePreferences(
				companyId, keys.toArray(new String[keys.size()]));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdPropertiesVerifyProcess.class);

	private volatile CompanyLocalService _companyLocalService;
	private volatile PrefsProps _prefsProps;
	private volatile SettingsFactory _settingsFactory;

}