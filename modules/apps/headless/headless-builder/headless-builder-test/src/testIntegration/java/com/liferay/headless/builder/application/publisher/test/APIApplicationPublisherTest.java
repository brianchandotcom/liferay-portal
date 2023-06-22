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

package com.liferay.headless.builder.application.publisher.test;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.publisher.test.util.APIApplicationPublisherUtil;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import javax.ws.rs.core.Application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags({"LPS-153117", "LPS-167253", "LPS-184413", "LPS-186757"})
public class APIApplicationPublisherTest extends BaseTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		Bundle testBundle = FrameworkUtil.getBundle(
			APIApplicationPublisherTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		_serviceTracker = new ServiceTracker<Application, Application>(
			bundleContext, Application.class, null) {

			@Override
			public Application addingService(
				ServiceReference<Application> serviceReference) {

				if (GetterUtil.getBoolean(
						serviceReference.getProperty(
							"liferay.headless.builder.application"))) {

					return super.addingService(serviceReference);
				}

				return null;
			}

		};

		_serviceTracker.open();
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

		_serviceTracker.close();

		APIApplicationPublisherUtil.unpublishRemainingAPIApplications(
			_apiApplicationPublisher);
	}

	@Test
	public void testPublish() throws Exception {
		APIApplication apiApplication1 = _addAPIApplication(
			_API_APPLICATION_ERC_1);
		APIApplication apiApplication2 = _addAPIApplication(
			_API_APPLICATION_ERC_2);

		Assert.assertEquals(0, _serviceTracker.size());

		APIApplicationPublisherUtil.publishApplications(
			_apiApplicationPublisher, apiApplication1, apiApplication2);

		Assert.assertEquals(2, _serviceTracker.size());

		APIApplicationPublisherUtil.unpublishApplications(
			_apiApplicationPublisher, apiApplication1, apiApplication2);

		Assert.assertEquals(0, _serviceTracker.size());
	}

	private APIApplication _addAPIApplication(String externalReferenceCode)
		throws Exception {

		String apiEndpointExternalReferenceCode = RandomTestUtil.randomString();
		String apiSchemaExternalReferenceCode = RandomTestUtil.randomString();
		String baseURL = RandomTestUtil.randomString();

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode",
						apiEndpointExternalReferenceCode
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
						"externalReferenceCode", apiSchemaExternalReferenceCode
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
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/requestAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				apiSchemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				apiEndpointExternalReferenceCode),
			Http.Method.PUT);

		return _apiApplicationProvider.getAPIApplication(
			baseURL, TestPropsValues.getCompanyId());
	}

	private static final String _API_APPLICATION_ERC_1 =
		RandomTestUtil.randomString();

	private static final String _API_APPLICATION_ERC_2 =
		RandomTestUtil.randomString();

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

	@Inject
	private APIApplicationPublisher _apiApplicationPublisher;

	private ServiceTracker<Application, Application> _serviceTracker;

}