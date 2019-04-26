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

package com.liferay.portal.verify.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Michael C. Han
 */
public abstract class BaseCompanySettingsVerifyProcessTestCase
	extends BaseVerifyProcessTestCase {

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			BaseCompanySettingsVerifyProcessTestCase.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

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
			return settingsFactory.getSettings(
				new CompanyServiceSettingsLocator(companyId, getSettingsId()));
		}
		catch (SettingsException se) {
			throw new IllegalStateException(se);
		}
	}

	protected abstract String getSettingsId();

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getServiceReferences(
					VerifyProcess.class.getName(),
					StringBundler.concat(
						"(&(objectClass=", VerifyProcess.class.getName(),
						")(verify.process.name=", getVerifyProcessName(),
						"))"));

			if (ArrayUtil.isEmpty(serviceReferences)) {
				throw new IllegalStateException("Unable to get verify process");
			}

			return (VerifyProcess)_bundleContext.getService(
				serviceReferences[0]);
		}
		catch (Exception ise) {
			throw new IllegalStateException("Unable to get verify process");
		}
	}

	protected abstract String getVerifyProcessName();

	protected abstract void populateLegacyProperties(
		UnicodeProperties properties);

	@Inject
	protected CompanyLocalService companyLocalService;

	@Inject
	protected PrefsProps prefsProps;

	@Inject
	protected SettingsFactory settingsFactory;

	private static BundleContext _bundleContext;

}