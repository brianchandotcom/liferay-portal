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
import com.liferay.osgi.util.test.instances.TestInstance;
import com.liferay.osgi.util.test.services.TrackedOne;
import com.liferay.osgi.util.test.services.TrackedTwo;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

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
@BndFile("test/integration/test-bnd.bnd")
@RunWith(Arquillian.class)
public class ReflectionServiceTrackerInstancesTest {

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
	public void whenRegisteringServicesTheyShouldBeInjectedInTheInstance()
		throws IOException {

		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne.class, trackedOne, 0);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr2 = registerService(
			TrackedTwo.class, trackedTwo, 0);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		reflectionServiceTracker.close();
	}

	@Test
	public void whenThereAreNoRegisteredServicesShouldInjectNull() {
		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		reflectionServiceTracker.close();
	}

	@Test
	public void whenUnregisteringServicesShouldInjectNull() {
		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne.class, trackedOne, 0);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr2 = registerService(
			TrackedTwo.class, trackedTwo, 0);

		sr1.unregister();
		sr2.unregister();

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		reflectionServiceTracker.close();
	}

	@Test
	public void
	whenMoreThanOneServiceExistsShouldInjectTheOneWithHigherServiceRanking() {
		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne.class, trackedOne, 2);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr2 = registerService(
			TrackedTwo.class, trackedTwo, 2);

		TrackedOne trackedOne2 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr3 = registerService(
			TrackedOne.class, trackedOne2, 1);

		TrackedTwo trackedTwo2 = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr4 = registerService(
			TrackedTwo.class, trackedTwo2, 1);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();
	}

	public void
	whenDeregisteringServiceShouldInjectTheNextWithHigherServiceRanking() {
		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne.class, trackedOne, 2);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr2 = registerService(
			TrackedTwo.class, trackedTwo, 2);

		TrackedOne trackedOne2 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr3 = registerService(
			TrackedOne.class, trackedOne2, 1);

		TrackedTwo trackedTwo2 = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr4 = registerService(
			TrackedTwo.class, trackedTwo2, 1);

		sr3.unregister();
		sr4.unregister();

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		reflectionServiceTracker.close();
	}

	private <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, int ranking) {

		Dictionary<String, Integer> properties =
			new Hashtable<String, Integer>();

		properties.put("service.ranking", ranking);

		return _bundleContext.registerService(clazz, service, properties);
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;

}