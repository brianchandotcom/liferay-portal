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

package com.liferay.osgi.util.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.osgi.util.service.ReflectionServiceTracker;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunWith(Arquillian.class)
public class TrackingClassesTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();
	}

	@Test
	public void testInjectNullWhenNoServicesAreRegistered() {
		TestInstance testInstance = new TestInstance();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInstance)) {

			Assert.assertNull(testInstance.getTrackedOne());
			Assert.assertNull(testInstance.getTrackedTwo());
		}
	}

	@Test
	public void testInjectNullWhenUnregisteringServices() {
		TestInstance testInstance = new TestInstance();

		try (ReflectionServiceTracker reflectionServiceTracker =
				new ReflectionServiceTracker(testInstance)) {

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, TrackedOne.class, trackedOne, 0);

			TrackedTwo trackedTwo = new TrackedTwo();

			ServiceRegistration<TrackedTwo> serviceRegistration2 =
				ReflectionServiceTrackerTestUtil.registerServiceWithRanking(
					_bundleContext, TrackedTwo.class, trackedTwo, 0);

			serviceRegistration1.unregister();

			serviceRegistration2.unregister();

			Assert.assertNull(testInstance.getTrackedOne());
			Assert.assertNull(testInstance.getTrackedTwo());
		}
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;

}