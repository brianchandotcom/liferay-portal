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

package com.liferay.portal.verify.test;

import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Michael C. Han
 */
public abstract class BaseCompanySettingsVerifyProcessTestCase
	extends BaseVerifyProcessTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		ServiceReference<CompanyLocalService> companyLocalServiceReference =
			registry.getServiceReference(CompanyLocalService.class);

		companyLocalService = registry.getService(companyLocalServiceReference);

		ServiceReference<PrefsProps> prefsPropsServiceReference =
			registry.getServiceReference(PrefsProps.class);

		prefsProps = registry.getService(prefsPropsServiceReference);

		ServiceReference<SettingsFactory> configurationAdminServiceReference =
			registry.getServiceReference(SettingsFactory.class);

		settingsFactory = registry.getService(
			configurationAdminServiceReference);

		UnicodeProperties properties = new UnicodeProperties();

		populateLegacyProperties(properties);

		List<Company> companies = companyLocalService.getCompanies(false);

		for (Company company : companies) {
			companyLocalService.updatePreferences(
				company.getCompanyId(), properties);
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		List<Company> companies = companyLocalService.getCompanies(false);

		for (Company company : companies) {
			Settings settings = getSettings(company.getCompanyId());

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			modifiableSettings.reset();

			modifiableSettings.store();
		}

		super.tearDown();
	}

	@Override
	protected void doVerify() throws VerifyException {
		super.doVerify();

		List<Company> companies = companyLocalService.getCompanies(false);

		for (Company company : companies) {
			PortletPreferences portletPreferences = prefsProps.getPreferences(
				company.getCompanyId(), true);

			Settings settings = getSettings(company.getCompanyId());

			Assert.assertNotNull(settings);

			doVerify(portletPreferences, settings);
		}
	}

	protected abstract void doVerify(
		PortletPreferences portletPreferences, Settings settings);

	protected Settings getSettings(long companyId) {
		try {
			Settings settings = settingsFactory.getSettings(
				new CompanyServiceSettingsLocator(companyId, getSettingsId()));

			return settings;
		}
		catch (SettingsException se) {
			throw new IllegalStateException(se);
		}
	}

	protected abstract String getSettingsId();

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			Registry registry = RegistryUtil.getRegistry();

			ServiceReference<?>[] serviceReferences =
				registry.getServiceReferences(
					VerifyProcess.class.getName(),
					"(&(objectClass=" + VerifyProcess.class.getName() +
						")(verify.process.name=" + getVerifyProcessName() +
							"))");

			if (ArrayUtil.isEmpty(serviceReferences)) {
				throw new IllegalStateException("Unable to get verify process");
			}

			return (VerifyProcess)registry.getService(serviceReferences[0]);
		}
		catch (Exception ise) {
			throw new IllegalStateException("Unable to get verify process");
		}
	}

	protected abstract String getVerifyProcessName();

	protected abstract void populateLegacyProperties(
		UnicodeProperties properties);

	protected CompanyLocalService companyLocalService;
	protected PrefsProps prefsProps;
	protected SettingsFactory settingsFactory;

}