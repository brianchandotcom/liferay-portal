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

package com.liferay.portal.security.sso.facebook.connect.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;
import com.liferay.portal.security.sso.facebook.connect.constants.LegacyFacebookConnectPropsKeys;
import com.liferay.portal.service.CompanyLocalService;
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
public class FacebookConnectCompanySettingsVerifyProcessTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(
			FacebookConnectCompanySettingsVerifyProcessTest.class);

		_bundleContext = bundle.getBundleContext();

		ServiceReference<CompanyLocalService> companyLocalServiceReference =
			_bundleContext.getServiceReference(CompanyLocalService.class);

		_companyLocalService = _bundleContext.getService(
			companyLocalServiceReference);

		ServiceReference<PrefsProps> prefsPropsServiceReference =
			_bundleContext.getServiceReference(PrefsProps.class);

		_prefsProps = _bundleContext.getService(prefsPropsServiceReference);

		ServiceReference<SettingsFactory> configurationAdminServiceReference =
			_bundleContext.getServiceReference(SettingsFactory.class);

		_settingsFactory = _bundleContext.getService(
			configurationAdminServiceReference);

		UnicodeProperties properties = new UnicodeProperties();

		properties.put(LegacyFacebookConnectPropsKeys.AUTH_ENABLED, "true");
		properties.put(LegacyFacebookConnectPropsKeys.APP_ID, "test_app_id");
		properties.put(
			LegacyFacebookConnectPropsKeys.APP_SECRET, "test_app_secret");
		properties.put(
			LegacyFacebookConnectPropsKeys.GRAPH_URL, "test_graph_url");
		properties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_AUTH_URL,
			"test_oauth_auth_url");
		properties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_REDIRECT_URL,
			"test_oauth_redirect_url");
		properties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_TOKEN_URL,
			"test_oauth_token_url");
		properties.put(
			LegacyFacebookConnectPropsKeys.VERIFIED_ACCOUNT_REQUIRED, "true");

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			_companyLocalService.updatePreferences(
				company.getCompanyId(), properties);
		}
	}

	@AfterClass
	public static void tearDownClass()
		throws InvalidSyntaxException, IOException {

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			Settings settings = getSettings(company.getCompanyId());

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			modifiableSettings.reset();
		}

		_bundleContext = null;
	}

	protected static Settings getSettings(long companyId) {
		try {
			Settings settings = _settingsFactory.getSettings(
				new CompanyServiceSettingsLocator(
					companyId, FacebookConnectConstants.SERVICE_NAME));

			return settings;
		}
		catch (SettingsException e) {
			throw new IllegalStateException(e);
		}
	}

	protected void doVerify() throws VerifyException {
		super.doVerify();

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			PortletPreferences portletPreferences = _prefsProps.getPreferences(
				company.getCompanyId(), true);

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
						LegacyFacebookConnectPropsKeys.AUTH_ENABLED,
						StringPool.BLANK)));

			Settings settings = getSettings(company.getCompanyId());

			Assert.assertEquals(
				StringPool.TRUE,
				settings.getValue(
					FacebookConnectConstants.AUTH_ENABLED, StringPool.FALSE));

			Assert.assertEquals(
					"test_app_id",
					settings.getValue(FacebookConnectConstants.APP_ID, ""));

			Assert.assertEquals(
					"test_app_secret",
					settings.getValue(FacebookConnectConstants.APP_SECRET, ""));

			Assert.assertEquals(
					"test_graph_url",
					settings.getValue(FacebookConnectConstants.GRAPH_URL, ""));

			Assert.assertEquals(
					"test_oauth_auth_url",
					settings.getValue(
						FacebookConnectConstants.OAUTH_AUTH_URL, ""));

			Assert.assertEquals(
					"test_oauth_redirect_url",
					settings.getValue(
						FacebookConnectConstants.OAUTH_REDIRECT_URL, ""));

			Assert.assertEquals(
					"test_oauth_token_url",
					settings.getValue(
						FacebookConnectConstants.OAUTH_TOKEN_URL, ""));

			Assert.assertEquals(
					StringPool.TRUE,
					settings.getValue(
						FacebookConnectConstants.VERIFIED_ACCOUNT_REQUIRED,
						StringPool.FALSE));
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getAllServiceReferences(
					VerifyProcess.class.getName(),
					"(&(objectClass=" + VerifyProcess.class.getName() +
						")(verify.process.name=" +
							"com.liferay.portal." +
								"security.sso.facebook.connect))");

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

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectCompanySettingsVerifyProcessTest.class);

	private static BundleContext _bundleContext;
	private static volatile CompanyLocalService _companyLocalService;
	private static volatile PrefsProps _prefsProps;
	private static volatile SettingsFactory _settingsFactory;

}