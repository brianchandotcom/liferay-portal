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

package com.liferay.portal.security.sso.token.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.security.sso.token.constants.LegacyTokenPropsKeys;
import com.liferay.portal.security.sso.token.constants.TokenConfigurationKeys;
import com.liferay.portal.security.sso.token.constants.TokenConstants;

import java.io.IOException;

import java.util.Dictionary;
import java.util.List;

import javax.portlet.ValidatorException;

/**
 * @author Christopher Kian
 */
public class UpgradeTokenConfiguration extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration();
	}

	private void _storeSettings(
			long companyId, String settingsId,
			Dictionary<String, String> dictionary)
		throws IOException, SettingsException, ValidatorException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new CompanyServiceSettingsLocator(companyId, settingsId));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		SettingsDescriptor settingsDescriptor =
			SettingsFactoryUtil.getSettingsDescriptor(settingsId);

		for (String name : settingsDescriptor.getAllKeys()) {
			String value = dictionary.get(name);

			if (value == null) {
				continue;
			}

			String oldValue = settings.getValue(name, null);

			if (!value.equals(oldValue)) {
				modifiableSettings.setValue(name, value);
			}
		}

		modifiableSettings.store();
	}

	private void _upgradeConfiguration() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			Dictionary<String, String> dictionary = new HashMapDictionary<>();

			for (String[] renamePropertykeys : _RENAME_PROPERTY_KEYS_ARRAY) {
				String propertyValue = PrefsPropsUtil.getString(
					company.getCompanyId(), renamePropertykeys[0]);

				if (propertyValue != null) {
					dictionary.put(renamePropertykeys[1], propertyValue);
				}
			}

			if (!dictionary.isEmpty()) {
				_storeSettings(
					company.getCompanyId(), TokenConstants.SERVICE_NAME,
					dictionary);
			}

			CompanyLocalServiceUtil.removePreferences(
				company.getCompanyId(),
				ArrayUtil.append(
					LegacyTokenPropsKeys.SHIBBOLETH_KEYS,
					LegacyTokenPropsKeys.SITEMINDER_KEYS));
		}
	}

	private static final String[][] _RENAME_PROPERTY_KEYS_ARRAY = {
		{
			LegacyTokenPropsKeys.SHIBBOLETH_AUTH_ENABLED,
			TokenConfigurationKeys.AUTH_ENABLED
		},
		{
			LegacyTokenPropsKeys.SHIBBOLETH_IMPORT_FROM_LDAP,
			TokenConfigurationKeys.IMPORT_FROM_LDAP
		},
		{
			LegacyTokenPropsKeys.SHIBBOLETH_LOGOUT_URL,
			TokenConfigurationKeys.LOGOUT_REDIRECT_URL
		},
		{
			LegacyTokenPropsKeys.SHIBBOLETH_USER_HEADER,
			TokenConfigurationKeys.USER_TOKEN_NAME
		},
		{
			LegacyTokenPropsKeys.SITEMINDER_AUTH_ENABLED,
			TokenConfigurationKeys.AUTH_ENABLED
		},
		{
			LegacyTokenPropsKeys.SITEMINDER_IMPORT_FROM_LDAP,
			TokenConfigurationKeys.IMPORT_FROM_LDAP
		},
		{
			LegacyTokenPropsKeys.SITEMINDER_USER_HEADER,
			TokenConfigurationKeys.USER_TOKEN_NAME
		}
	};

}