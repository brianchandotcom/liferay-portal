/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.cors.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.remote.cors.client.test.BaseCORSClientTestCase;
import com.liferay.portal.remote.cors.client.test.CORSTestApplication;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class ConfigurationCORSClientTest extends BaseCORSClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Company company = _companyLocalService.getCompanyByVirtualHost(
			"localhost");

		_companyId = company.getCompanyId();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDuplicateConfiguration() throws Exception {
		_createFactoryConfiguration(
			"http://www.liferay.com", _companyId, false,
			"/o/cors-app/duplicate/path/*");

		try {
			_createFactoryConfiguration(
				"http://www.google.com", _companyId, false,
				"/o/cors-app/duplicate/path/*");
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertTrue(
				throwable instanceof ConfigurationModelListenerException);
		}
	}

	@Test
	public void testDuplicateInstanceConfiguration() throws Exception {
		_createFactoryConfiguration(
			"http://www.liferay.com", _companyId, true,
			"/o/cors-app/duplicate-instance/path/*");

		try {
			_createFactoryConfiguration(
				"http://www.google.com", _companyId, true,
				"/o/cors-app/duplicate-instance/path/*");
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertTrue(
				throwable instanceof ConfigurationModelListenerException);
		}
	}

	@Test
	public void testNonoverwrittenConfiguration() throws Exception {
		_createFactoryConfiguration(
			"http://www.liferay.com", 0, false,
			"/o/cors-app/system/only/path/*");
		_createFactoryConfiguration(
			"http://www.google.com", _companyId, true,
			"/o/cors-app/instance/only/path/*");

		registerJaxRsApplication(
			new CORSTestApplication(), "",
			HashMapDictionaryBuilder.<String, Object>put(
				"osgi.jaxrs.name", "test-cors-2"
			).build());

		assertJaxRSUrl(
			"/cors-app/instance/only/path/whatever", "GET", false, true,
			"http://www.google.com");
		assertJaxRSUrl(
			"/cors-app/system/only/path/whatever", "GET", false, true,
			"http://www.liferay.com");
	}

	@Test
	public void testOverwrittenConfiguration() throws Exception {
		_createFactoryConfiguration(
			"http://www.google.com", 0, false,
			"/o/cors-app/overwritten/path/*");
		_createFactoryConfiguration(
			"http://www.liferay.com", _companyId, true,
			"/o/cors-app/overwritten/path/*");

		registerJaxRsApplication(
			new CORSTestApplication(), "",
			HashMapDictionaryBuilder.<String, Object>put(
				"osgi.jaxrs.name", "test-cors-3"
			).build());

		assertJaxRSUrl(
			"/cors-app/overwritten/path/whatever", "GET", false, false,
			"http://www.google.com");
		assertJaxRSUrl(
			"/cors-app/overwritten/path/whatever", "GET", false, true,
			"http://www.liferay.com");
	}

	private void _createFactoryConfiguration(
			String allowedOrigin, long companyId, Boolean scoped,
			String urlPattern)
		throws Exception {

		String portalCorsConfigurationClassName =
			PortalCORSConfiguration.class.getName();

		if (scoped) {
			portalCorsConfigurationClassName += ".scoped";
		}

		createFactoryConfiguration(
			portalCorsConfigurationClassName,
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", companyId
			).put(
				"filter.mapping.url.pattern", new String[] {urlPattern}
			).put(
				"headers",
				new String[] {
					"Access-Control-Allow-Credentials: true",
					"Access-Control-Allow-Headers: *",
					"Access-Control-Allow-Methods: *",
					"Access-Control-Allow-Origin: " + allowedOrigin
				}
			).build());
	}

	private long _companyId;

	@Inject
	private CompanyLocalService _companyLocalService;

}