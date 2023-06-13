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

package com.liferay.headless.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.builder.application.HeadlessBuilderApplication;
import com.liferay.headless.builder.application.HeadlessBuilderApplicationFactory;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemFieldValuesProvider;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemFormProvider;
import com.liferay.headless.builder.test.info.item.provider.TestEntryInfoItemObjectProvider;
import com.liferay.headless.builder.test.model.TestEntry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class HeadlessBuilderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(HeadlessBuilderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_infoItemFieldValuesProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemFieldValuesProvider.class,
				new TestEntryInfoItemFieldValuesProvider(), null);
		_infoItemFormProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemFormProvider.class, new TestEntryInfoItemFormProvider(),
				null);
		_infoItemObjectProviderServiceRegistration =
			bundleContext.registerService(
				InfoItemObjectProvider.class,
				new TestEntryInfoItemObjectProvider(), null);
	}

	@After
	public void tearDown() {
		_infoItemFieldValuesProviderServiceRegistration.unregister();
		_infoItemFormProviderServiceRegistration.unregister();
		_infoItemObjectProviderServiceRegistration.unregister();
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testHeadlessBuilderApplication() throws Exception {
		_withHeadlessBuilderApplication(
			TestPropsValues.getCompanyId(),
			() -> {
				JSONObject jsonObject = _invoke(
					"/headless-builder-old/v1.0/test-entries/" +
						_testEntry.getTestEntryId(),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"date", _formatDate(_testEntry.getDateField())
					).put(
						"number", (int)_testEntry.getLongField()
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testHeadlessBuilderApplicationOnADifferentCompany()
		throws Exception {

		_withHeadlessBuilderApplication(
			0,
			() -> {
				JSONObject jsonObject = _invoke(
					"/headless-builder-old/v1.0/test-entries/" +
						_testEntry.getTestEntryId(),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"status", "NOT_FOUND"
					).put(
						"title", "The operation could not be found."
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@Test
	public void testHeadlessBuilderApplicationWithoutFeatureFlag()
		throws Exception {

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			"/headless-builder-old/v1.0/test-entries/" +
				_testEntry.getTestEntryId(),
			Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());
	}

	@FeatureFlags("LPS-171047")
	@Test
	public void testMissingHeadlessBuilderApplication() throws Exception {
		JSONObject jsonObject = _invoke(
			"/headless-builder-old/v1.0/test-entries/1234", Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title", "The operation could not be found."
			).toString(),
			jsonObject.toString(), true);
	}

	private HttpURLConnection _createHttpURLConnection(
			String endpoint, Http.Method method)
		throws Exception {

		URL url = new URL("http://localhost:8080/o/" + endpoint);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT, "*/*");

		httpURLConnection.setRequestProperty(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		String encodedUserNameAndPassword = Base64.encode(
			"test@liferay.com:test".getBytes(StandardCharsets.UTF_8));

		httpURLConnection.setRequestProperty(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		httpURLConnection.setRequestMethod(method.toString());

		return httpURLConnection;
	}

	private String _formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		return simpleDateFormat.format(date);
	}

	private JSONObject _invoke(String endpoint, Http.Method method)
		throws Exception {

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			endpoint, method);

		httpURLConnection.connect();

		try {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(httpURLConnection.getInputStream()));
		}
		catch (FileNotFoundException fileNotFoundException) {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(httpURLConnection.getErrorStream()));
		}
	}

	private OpenAPIYAML _readOpenAPIYAML(String yamlFile) throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream(
				yamlFile)) {

			return YAMLUtil.loadOpenAPIYAML(StringUtil.read(inputStream));
		}
	}

	private void _withHeadlessBuilderApplication(
			long companyId, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		HeadlessBuilderApplication headlessBuilderApplication =
			_headlessBuilderApplicationFactory.getHeadlessBuilderApplication(
				companyId, _readOpenAPIYAML("/rest-openapi.yaml"));

		HeadlessBuilderApplication.Handle handle =
			headlessBuilderApplication.deploy();

		try {
			unsafeRunnable.run();
		}
		finally {
			handle.undeploy();
		}
	}

	@Inject
	private HeadlessBuilderApplicationFactory
		_headlessBuilderApplicationFactory;

	private ServiceRegistration<?>
		_infoItemFieldValuesProviderServiceRegistration;
	private ServiceRegistration<?> _infoItemFormProviderServiceRegistration;
	private ServiceRegistration<?> _infoItemObjectProviderServiceRegistration;
	private final TestEntry _testEntry = TestEntry.INSTANCE;

}