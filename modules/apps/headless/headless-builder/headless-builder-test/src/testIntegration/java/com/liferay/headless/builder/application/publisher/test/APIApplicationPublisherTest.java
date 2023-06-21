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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

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
public class APIApplicationPublisherTest extends BaseTestCase {

	@Test
	public void testPublish() throws Exception {
		CountDownLatch addedCountLatch = new CountDownLatch(2);
		CountDownLatch removedCountLatch = new CountDownLatch(2);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

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

		APIApplication apiApplication1 = _addAPIApplication(
			_API_APPLICATION_ERC_1);
		APIApplication apiApplication2 = _addAPIApplication(
			_API_APPLICATION_ERC_2);

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

}