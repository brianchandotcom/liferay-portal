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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.provider.test.APIApplicationProviderTest;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags({"LPS-186757", "LPS-184413", "LPS-167253", "LPS-153117"})
@RunWith(Arquillian.class)
public class APIApplicationPublisherTest {

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

					Boolean property = (Boolean)serviceReference.getProperty(
						"liferay.headless.builder.application");

					if ((property != null) && property) {
						addedCountLatch.countDown();

						return super.addingService(serviceReference);
					}

					return null;
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application service) {

					Boolean property = (Boolean)serviceReference.getProperty(
						"liferay.headless.builder.application");

					if ((property != null) && property) {
						removedCountLatch.countDown();

						super.removedService(serviceReference, service);
					}
				}

			};

		APIApplication apiApplication = _createAPIApplication("test", "test");

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

					Boolean property = (Boolean)serviceReference.getProperty(
						"liferay.headless.builder.application");

					if ((property != null) && property) {
						addedCountLatch.countDown();

						return super.addingService(serviceReference);
					}

					return null;
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application service) {

					Boolean property = (Boolean)serviceReference.getProperty(
						"liferay.headless.builder.application");

					if ((property != null) && property) {
						removedCountLatch.countDown();

						super.removedService(serviceReference, service);
					}
				}

			};

		APIApplication apiApplication1 = _createAPIApplication(
			"test1", "test1");
		APIApplication apiApplication2 = _createAPIApplication(
			"test2", "test2");

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

	private APIApplication _createAPIApplication(String baseURL, String title)
		throws Exception {

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"apiApplicationToAPIEndpoints",
				JSONUtil.put(
					JSONUtil.put(
						"description", "description"
					).put(
						"externalReferenceCode", "ENDPOINT"
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
						"externalReferenceCode", "SCHEMA"
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

	@Inject
	private APIApplicationProvider _apiApplicationProvider;

	@Inject
	private APIApplicationPublisher _apiApplicationPublisher;

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

}