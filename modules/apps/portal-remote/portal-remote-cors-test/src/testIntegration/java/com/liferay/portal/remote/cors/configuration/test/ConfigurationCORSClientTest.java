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

package com.liferay.portal.remote.cors.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.client.test.BaseCORSClientTestCase;
import com.liferay.portal.remote.cors.client.test.CORSTestApplication;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

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
			_companyId, _URL_PATTERNS_DUPLICATE, "http://www.liferay.com");

		try {
			_createFactoryConfiguration(
				_companyId, _URL_PATTERNS_DUPLICATE, "http://www.google.com");
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
			0, _URL_PATTERNS_SYSTEM_ONLY, "http://www.liferay.com");

		_createFactoryConfiguration(
			_companyId, _URL_PATTERNS_INSTANCE_ONLY, "http://www.google.com");

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("osgi.jaxrs.name", "test-cors-2");

		registerJaxRsApplication(new CORSTestApplication(), "", properties);

		assertJaxRSUrl(
			"/cors-app/system/only/path/whatever", "GET", false, true,
			"http://www.liferay.com");

		assertJaxRSUrl(
			"/cors-app/instance/only/path/whatever", "GET", false, true,
			"http://www.google.com");
	}

	@Test
	public void testOverwrittenConfiguration() throws Exception {
		_createFactoryConfiguration(
			0, _URL_PATTERNS_OVERWRITTEN, "http://www.google.com");

		_createFactoryConfiguration(
			_companyId, _URL_PATTERNS_OVERWRITTEN, "http://www.liferay.com");

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("osgi.jaxrs.name", "test-cors-3");

		registerJaxRsApplication(new CORSTestApplication(), "", properties);

		assertJaxRSUrl(
			"/cors-app/overwritten/path/whatever", "GET", false, false,
			"http://www.google.com");

		assertJaxRSUrl(
			"/cors-app/overwritten/path/whatever", "GET", false, true,
			"http://www.liferay.com");
	}

	private void _createFactoryConfiguration(
			long companyId, String[] urlPatterns, String allowedOrigin)
		throws Exception {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("companyId", companyId);

		properties.put("filter.mapping.url.pattern", urlPatterns);

		String[] headers = new String[_HEADERS_BASE.length + 1];

		for (int i = 0; i < _HEADERS_BASE.length; ++i) {
			headers[i] = _HEADERS_BASE[i];
		}

		headers[headers.length - 1] =
			_HEADER_ACCESS_CONTROL_ALLOWED_ORIGIN + allowedOrigin;
		properties.put("headers", headers);

		createFactoryConfiguration(
			PortalCORSConfiguration.class.getName(), properties);
	}

	private static final String _HEADER_ACCESS_CONTROL_ALLOWED_ORIGIN =
		"Access-Control-Allow-Origin: ";

	private static final String[] _HEADERS_BASE = {
		"Access-Control-Allow-Credentials: true",
		"Access-Control-Allow-Headers: *", "Access-Control-Allow-Methods: *"
	};

	private static final String[] _URL_PATTERNS_DUPLICATE = {
		"/o/cors-app/duplicate/path/*"
	};

	private static final String[] _URL_PATTERNS_INSTANCE_ONLY = {
		"/o/cors-app/instance/only/path/*"
	};

	private static final String[] _URL_PATTERNS_OVERWRITTEN = {
		"/o/cors-app/overwritten/path/*"
	};

	private static final String[] _URL_PATTERNS_SYSTEM_ONLY = {
		"/o/cors-app/system/only/path/*"
	};

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static ConfigurationProvider _configurationProvider;

	private long _companyId;

	@Inject
	private CompanyLocalService _companyLocalService;

}