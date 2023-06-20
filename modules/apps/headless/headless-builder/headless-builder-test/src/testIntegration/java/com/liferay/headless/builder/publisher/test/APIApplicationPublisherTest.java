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

package com.liferay.headless.builder.publisher.test;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.test.BaseHeadlessBuilderTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags({"LPS-186757", "LPS-184413", "LPS-167253", "LPS-153117"})
public class APIApplicationPublisherTest extends BaseHeadlessBuilderTestCase {

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
	}

	@Test
	public void testPublishAPIApplication() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		CountDownLatch addedCountLatch = new CountDownLatch(1);
		CountDownLatch removedCountLatch = new CountDownLatch(1);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Application, Application>(
				bundleContext, Application.class, null) {

				@Override
				public Application addingService(
					ServiceReference<Application> serviceReference) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						addedCountLatch.countDown();

						return super.addingService(serviceReference);
					}

					return null;
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application service) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						removedCountLatch.countDown();

						super.removedService(serviceReference, service);
					}
				}

			};

		APIApplication apiApplication = _createAPIApplication(
			"test", _API_APPLICATION_ERC_1, "test");

		try {
			serviceTracker.open();

			Assert.assertEquals(0, serviceTracker.size());

			_apiApplicationPublisher.publish(apiApplication);

			addedCountLatch.await(1, TimeUnit.MINUTES);

			Assert.assertEquals(1, serviceTracker.size());

			_apiApplicationPublisher.unpublish(apiApplication);

			removedCountLatch.await(1, TimeUnit.MINUTES);

			Assert.assertEquals(0, serviceTracker.size());
		}
		finally {
			serviceTracker.close();

			_apiApplicationPublisher.unpublish(apiApplication);
		}
	}

	@Test
	public void testPublishMultipleAPIApplications() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		CountDownLatch addedCountLatch = new CountDownLatch(2);
		CountDownLatch removedCountLatch = new CountDownLatch(2);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Application, Application>(
				bundleContext, Application.class, null) {

				@Override
				public Application addingService(
					ServiceReference<Application> serviceReference) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						addedCountLatch.countDown();

						return super.addingService(serviceReference);
					}

					return null;
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application service) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						removedCountLatch.countDown();

						super.removedService(serviceReference, service);
					}
				}

			};

		APIApplication apiApplication1 = _createAPIApplication(
			"test1", _API_APPLICATION_ERC_1, "test1");
		APIApplication apiApplication2 = _createAPIApplication(
			"test2", _API_APPLICATION_ERC_2, "test2");

		try {
			serviceTracker.open();

			Assert.assertEquals(0, serviceTracker.size());

			_apiApplicationPublisher.publish(apiApplication1);
			_apiApplicationPublisher.publish(apiApplication2);

			addedCountLatch.await(1, TimeUnit.MINUTES);

			Assert.assertEquals(2, serviceTracker.size());

			_apiApplicationPublisher.unpublish(apiApplication1);
			_apiApplicationPublisher.unpublish(apiApplication2);

			removedCountLatch.await(1, TimeUnit.MINUTES);

			Assert.assertEquals(0, serviceTracker.size());
		}
		finally {
			serviceTracker.close();

			_apiApplicationPublisher.unpublish(apiApplication1);
			_apiApplicationPublisher.unpublish(apiApplication2);
		}
	}

	private APIApplication _createAPIApplication(
			String baseURL, String externalReferenceCode, String title)
		throws Exception {

		String schemaExternalReferenceCode = externalReferenceCode + "_SCHEMA";
		String endpointExternalReferenceCode =
			externalReferenceCode + "_ENDPOINT";

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
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				schemaExternalReferenceCode, "/requestAPISchemaToAPIEndpoints/",
				endpointExternalReferenceCode),
			Http.Method.PUT);
		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				"headless-builder/schemas/by-external-reference-code/",
				schemaExternalReferenceCode,
				"/responseAPISchemaToAPIEndpoints/",
				endpointExternalReferenceCode),
			Http.Method.PUT);

		return _apiApplicationProvider.getAPIApplication(
			baseURL, TestPropsValues.getCompanyId());
	}

	private static final String _API_APPLICATION_ERC_1 = "APPLICATION1";

	private static final String _API_APPLICATION_ERC_2 = "APPLICATION2";

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

	@Inject
	private APIApplicationPublisher _apiApplicationPublisher;

}