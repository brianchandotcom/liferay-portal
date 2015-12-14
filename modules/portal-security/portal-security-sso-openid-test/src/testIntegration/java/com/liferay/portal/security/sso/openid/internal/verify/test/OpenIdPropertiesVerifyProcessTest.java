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

package com.liferay.portal.security.sso.openid.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.sso.openid.constants.LegacyOpenIdPropsKeys;
import com.liferay.portal.security.sso.openid.constants.OpenIdConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import java.io.IOException;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class OpenIdPropertiesVerifyProcessTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		UnicodeProperties properties = new UnicodeProperties();

		properties.put(LegacyOpenIdPropsKeys.OPENID_AUTH_ENABLED, "false");

		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			CompanyLocalServiceUtil.updatePreferences(
				company.getCompanyId(), properties);
		}

		Bundle bundle = FrameworkUtil.getBundle(
			OpenIdPropertiesVerifyProcessTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@AfterClass
	public static void tearDownClass()
		throws InvalidSyntaxException, IOException {

		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			try {
				Settings settings = getSettings(company.getCompanyId());
				settings.getModifiableSettings().reset();
			}
			catch (SettingsException e) {
				throw new IOException(e);
			}
		}

		_bundleContext = null;
	}

	protected static Settings getSettings(long companyId)
		throws SettingsException {

		Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId, OpenIdConstants.SERVICE_NAME));

		return settings;
	}

	protected void doVerify() throws VerifyException {
		super.doVerify();

		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			PortletPreferences portletPreferences =
				PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

			Assert.assertTrue(
					Validator.isNull(
						portletPreferences.getValue(
							LegacyOpenIdPropsKeys.OPENID_AUTH_ENABLED,
							StringPool.BLANK)));

			Settings settings;

			try {
				settings = getSettings(company.getCompanyId());
			}
			catch (SettingsException e) {
				throw new VerifyException(e);
			}

			Assert.assertEquals(
					StringPool.FALSE,
					settings.getValue(
						OpenIdConstants.AUTH_ENABLED, StringPool.TRUE));
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getAllServiceReferences(
					VerifyProcess.class.getName(),
					"(&(objectClass=" + VerifyProcess.class.getName() +
						")(verify.process.name=com.liferay.portal.openid))");

			if (ArrayUtil.isEmpty(serviceReferences)) {
				throw new IllegalStateException("Unable to get verify process");
			}

			return (VerifyProcess)_bundleContext.getService(
				serviceReferences[0]);
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException("Unable to get verify process");
		}
	}

	private static BundleContext _bundleContext;

}