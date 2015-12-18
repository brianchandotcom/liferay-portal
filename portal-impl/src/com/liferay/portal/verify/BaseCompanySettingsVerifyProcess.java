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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;

import java.io.IOException;

import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import javax.portlet.ValidatorException;

/**
 * @author Michael C. Han
 */
public abstract class BaseCompanySettingsVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyProperties();
	}

	protected abstract CompanyLocalService getCompanyLocalService();

	protected abstract Set<String> getLegacyPropertyKeys();

	protected abstract Dictionary<String, String> getPropertyValues(
		long companyId);

	protected abstract SettingsFactory getSettingsFactory();

	protected abstract String getSettingsId();

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
		AbstractCompanySettingsVerifyProcess.class);

}