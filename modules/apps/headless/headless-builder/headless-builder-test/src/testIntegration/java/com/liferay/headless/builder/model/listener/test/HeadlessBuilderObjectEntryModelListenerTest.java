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

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.application.publisher.test.APIApplicationPublisherTest;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;

import javax.ws.rs.core.Application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class HeadlessBuilderObjectEntryModelListenerTest extends BaseTestCase {

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

	@Test
	public void test() throws Exception {

		// Success and publish it

		int initialSize = _serviceTracker.size();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _ERC_1
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		Assert.assertEquals(initialSize + 1, _serviceTracker.size());

		// Unpublish

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "unpublished"
			).toString(),
			"headless-builder/applications/by-external-reference-code/" +
				_ERC_1,
			Http.Method.PATCH);

		Assert.assertEquals(initialSize, _serviceTracker.size());

		// Create and publish

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _ERC_2
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals(initialSize + 1, _serviceTracker.size());

		// Delete API Application

		HTTPTestUtil.invoke(
			null,
			"headless-builder/applications/by-external-reference-code/" +
				_ERC_2,
			Http.Method.DELETE);

		Assert.assertEquals(initialSize, _serviceTracker.size());

		// Create API Application with Endpoint and Schema

		String apiEndpointExternalReferenceCode = RandomTestUtil.randomString();

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
						"path", RandomTestUtil.randomString()
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
						"externalReferenceCode", RandomTestUtil.randomString()
					).put(
						"mainObjectDefinitionERC", "L_API_APPLICATION"
					).put(
						"name", "name"
					))
			).put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals(initialSize + 1, _serviceTracker.size());
	}

	private static final String _ERC_1 = RandomTestUtil.randomString();

	private static final String _ERC_2 = RandomTestUtil.randomString();

	private ServiceTracker<Application, Application> _serviceTracker;

}