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

package com.liferay.headless.builder.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.provider.test.APIApplicationProviderTest;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.publisher.test.util.APIApplicationPublisherUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.HttpHeaders;

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

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags({"LPS-186757", "LPS-184413", "LPS-167253", "LPS-153117"})
@RunWith(Arquillian.class)
public class HeadlessBuilderResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle testBundle = FrameworkUtil.getBundle(
			APIApplicationProviderTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.headless.builder.impl")) {

				CompletableFuture<Void> completableFuture =
					_batchEngineUnitProcessor.processBatchEngineUnits(
						_batchEngineUnitReader.getBatchEngineUnits(bundle));

				completableFuture.join();
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		HTTPTestUtil.invoke(
			null,
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_1,
			Http.Method.DELETE);

		HTTPTestUtil.invoke(
			null,
			"headless-builder/applications/by-external-reference-code/" +
				_API_APPLICATION_ERC_2,
			Http.Method.DELETE);

		APIApplicationPublisherUtil.unpublishRemainingAPIApplications(
			_apiApplicationPublisher);
	}

	@Test
	public void testEndpointIsReachable() throws Exception {
		APIApplication apiApplication = _createAPIApplication(
			"test", _API_APPLICATION_ERC_1, "test");

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			apiApplication.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());

		APIApplicationPublisherUtil.publishApplications(
			_apiApplicationPublisher, apiApplication);

		httpURLConnection = _createHttpURLConnection(
			apiApplication.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(200, httpURLConnection.getResponseCode());

		APIApplicationPublisherUtil.unpublishApplications(
			_apiApplicationPublisher, apiApplication);

		httpURLConnection = _createHttpURLConnection(
			apiApplication.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());
	}

	@Test
	public void testEndpointsAreReachable() throws Exception {
		APIApplication apiApplication1 = _createAPIApplication(
			"test1", _API_APPLICATION_ERC_1, "test1");
		APIApplication apiApplication2 = _createAPIApplication(
			"test2", _API_APPLICATION_ERC_2, "test2");

		HttpURLConnection httpURLConnection = _createHttpURLConnection(
			apiApplication1.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());

		httpURLConnection = _createHttpURLConnection(
			apiApplication2.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());

		APIApplicationPublisherUtil.publishApplications(
			_apiApplicationPublisher, apiApplication1, apiApplication2);

		httpURLConnection = _createHttpURLConnection(
			apiApplication1.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(200, httpURLConnection.getResponseCode());

		httpURLConnection = _createHttpURLConnection(
			apiApplication2.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(200, httpURLConnection.getResponseCode());

		APIApplicationPublisherUtil.unpublishApplications(
			_apiApplicationPublisher, apiApplication1);

		httpURLConnection = _createHttpURLConnection(
			apiApplication1.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(404, httpURLConnection.getResponseCode());

		httpURLConnection = _createHttpURLConnection(
			apiApplication2.getBaseURL(), Http.Method.GET);

		httpURLConnection.connect();

		Assert.assertEquals(200, httpURLConnection.getResponseCode());
	}

	private APIApplication _createAPIApplication(
			String baseURL, String externalReferenceCode, String title)
		throws Exception {

		String endpointExternalReferenceCode =
			externalReferenceCode + "ENDPOINT";

		String schemaExternalReferenceCode = externalReferenceCode + "SCHEMA";

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode", endpointExternalReferenceCode
					).put(
						"httpMethod", "get"
					).put(
						"name", "name"
					).put(
						"path", "path"
					).put(
						"scope", "company"
					))
			).put(
				"apiApplicationToAPISchemas",
				JSONUtil.put(
					JSONUtil.put(
						"apiSchemaToAPIProperties",
						JSONUtil.put(
							JSONUtil.put(
								"description", "description"
							).put(
								"name", "name"
							).put(
								"objectFieldERC", "APPLICATION_STATUS"
							))
					).put(
						"description", "description"
					).put(
						"externalReferenceCode", schemaExternalReferenceCode
					).put(
						"mainObjectDefinitionERC", "MSOD_API_APPLICATION"
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "published"
			).put(
				"baseURL", baseURL
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"title", title
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invoke(
			null,
			"headless-builder/schemas/by-external-reference-code/SCHEMA" +
				"/requestAPISchemaToAPIEndpoints/ENDPOINT",
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			"headless-builder/schemas/by-external-reference-code/SCHEMA" +
				"/responseAPISchemaToAPIEndpoints/ENDPOINT",
			Http.Method.PUT);

		return _apiApplicationProvider.getAPIApplication(
			baseURL, TestPropsValues.getCompanyId());
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

	private static final String _API_APPLICATION_ERC_1 = "APPLICATION1";

	private static final String _API_APPLICATION_ERC_2 = "APPLICATION2";

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

	@Inject
	private APIApplicationPublisher _apiApplicationPublisher;

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

}