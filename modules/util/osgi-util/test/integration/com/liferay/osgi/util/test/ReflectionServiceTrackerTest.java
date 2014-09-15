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
import com.liferay.osgi.util.exception.ServiceUnavailableException;
import com.liferay.osgi.util.service.ReflectionServiceTracker;
import com.liferay.osgi.util.test.instances.TestInstance;
import com.liferay.osgi.util.test.instances.TestInterface;
import com.liferay.osgi.util.test.services.InterfaceOne;
import com.liferay.osgi.util.test.services.InterfaceTwo;
import com.liferay.osgi.util.test.services.TrackedOne;
import com.liferay.osgi.util.test.services.TrackedTwo;

import java.io.IOException;

import java.util.Hashtable;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

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
public class ReflectionServiceTrackerTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@Test
	public void testReflectionServiceTracker() throws IOException {
		TestInstance testInstance = new TestInstance();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = _bundleContext.registerService(
			TrackedOne.class, trackedOne, null);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr2 = _bundleContext.registerService(
			TrackedTwo.class, trackedTwo, null);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		trackedOne = new TrackedOne();
		sr1 = _bundleContext.registerService(
			TrackedOne.class, trackedOne, new Hashtable<String, Object>() { {
				put("service.ranking", 2);
			}});

		trackedTwo = new TrackedTwo();
		sr2 = _bundleContext.registerService(
			TrackedTwo.class, trackedTwo, new Hashtable<String, Object>() { {
				put("service.ranking", 2);
			}});
		TrackedOne trackedOne2 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr3 = _bundleContext.registerService(
			TrackedOne.class, trackedOne2, new Hashtable<String, Object>() { {
				put("service.ranking", 1);
			}});

		TrackedTwo trackedTwo2 = new TrackedTwo();
		ServiceRegistration<TrackedTwo> sr4 = _bundleContext.registerService(
			TrackedTwo.class, trackedTwo2, new Hashtable<String, Object>() { {
				put("service.ranking", 1);
			}});

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		Assert.assertEquals(trackedOne2, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo2, testInstance.getTrackedTwo());
		sr3.unregister();

		sr4.unregister();

		Assert.assertNull(testInstance.getTrackedOne());
		Assert.assertNull(testInstance.getTrackedTwo());

		reflectionServiceTracker.close();
	}

	@Test
	public void testReflectionServiceTrackerWithInterfaces()
		throws IOException {

		TestInterface testInstance = new TestInterface();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		Assert.assertNotNull(testInstance.getTrackedOne());
		Assert.assertNotNull(testInstance.getTrackedTwo());

		try {
			testInstance.getTrackedOne().noop();

			Assert.fail();
		}
		catch (ServiceUnavailableException sue) {
		}

		try {
			testInstance.getTrackedTwo().noop();

			Assert.fail();
		}
		catch (ServiceUnavailableException sue) {
		}

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<InterfaceOne> sr1 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne, null);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<InterfaceTwo> sr2 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo, null);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		Assert.assertNotNull(testInstance.getTrackedOne());
		Assert.assertNotNull(testInstance.getTrackedTwo());

		trackedOne = new TrackedOne();
		sr1 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne, new Hashtable<String, Object>() { {
				put("service.ranking", 2);
			}});

		trackedTwo = new TrackedTwo();
		sr2 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo, new Hashtable<String, Object>() { {
				put("service.ranking", 2);
			}});
		TrackedOne trackedOne2 = new TrackedOne();
		ServiceRegistration<InterfaceOne> sr3 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne2, new Hashtable<String, Object>() { {
				put("service.ranking", 1);
			}});

		TrackedTwo trackedTwo2 = new TrackedTwo();
		ServiceRegistration<InterfaceTwo> sr4 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo2, new Hashtable<String, Object>() { {
				put("service.ranking", 1);
			}});

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		Assert.assertEquals(trackedOne2, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo2, testInstance.getTrackedTwo());
		sr3.unregister();

		sr4.unregister();

		Assert.assertNotNull(testInstance.getTrackedOne());
		Assert.assertNotNull(testInstance.getTrackedTwo());

		try {
			testInstance.getTrackedOne().noop();

			Assert.fail();
		}
		catch (ServiceUnavailableException sue) {
		}

		try {
			testInstance.getTrackedTwo().noop();

			Assert.fail();
		}
		catch (ServiceUnavailableException sue) {
		}

		reflectionServiceTracker.close();
	}

	@Test
	public void testReflectionServiceTrackerWithInterfacesAndModifiedService()
		throws IOException {

		TestInterface testInstance = new TestInterface();

		ReflectionServiceTracker reflectionServiceTracker =
			new ReflectionServiceTracker(testInstance);

		Assert.assertNotNull(testInstance.getTrackedOne());
		Assert.assertNotNull(testInstance.getTrackedTwo());

		TrackedOne trackedOne = new TrackedOne();
		ServiceRegistration<InterfaceOne> sr1 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne, null);

		TrackedTwo trackedTwo = new TrackedTwo();
		ServiceRegistration<InterfaceTwo> sr2 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo, null);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();

		Assert.assertNotNull(testInstance.getTrackedOne());
		Assert.assertNotNull(testInstance.getTrackedTwo());

		trackedOne = new TrackedOne();
		sr1 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne, new Hashtable<String, Object>() {
				{
					put("service.ranking", 2);
				}
			}
		);

		trackedTwo = new TrackedTwo();
		sr2 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo, new Hashtable<String, Object>() {
				{
					put("service.ranking", 2);
				}
			}
		);
		TrackedOne trackedOne2 = new TrackedOne();
		ServiceRegistration<InterfaceOne> sr3 = _bundleContext.registerService(
			InterfaceOne.class, trackedOne2, new Hashtable<String, Object>() {
				{
					put("service.ranking", 1);
				}
			}
		);

		TrackedTwo trackedTwo2 = new TrackedTwo();
		ServiceRegistration<InterfaceTwo> sr4 = _bundleContext.registerService(
			InterfaceTwo.class, trackedTwo2, new Hashtable<String, Object>() {
				{
					put("service.ranking", 1);
				}
			}
		);

		Assert.assertEquals(trackedOne, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo, testInstance.getTrackedTwo());

		sr3.setProperties(new Hashtable<String, Object>() {
			{
				put("service.ranking", 3);
			}
		});
		sr4.setProperties(new Hashtable<String, Object>() {
			{
				put("service.ranking", 3);
			}
		});

		Assert.assertEquals(trackedOne2, testInstance.getTrackedOne());
		Assert.assertEquals(trackedTwo2, testInstance.getTrackedTwo());

		sr1.unregister();
		sr2.unregister();
		sr3.unregister();
		sr4.unregister();

		reflectionServiceTracker.close();
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;

}