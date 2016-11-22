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

package com.liferay.registry.dependency;

import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.internal.TrackedOne;
import com.liferay.registry.internal.TrackedTwo;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ServiceDependencyManagerTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(null);
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testDependenciesFulfilled() {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(TestInterface1.class, new TestInstance1());
		registry.registerService(TestInterface2.class, new TestInstance2());

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
				}

				@Override
				public void destroy() {
				}

			});

		serviceDependencyManager.registerDependencies(TestInterface2.class);
	}

	@Test
	public void testNoDependencies() {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(TestInterface1.class, new TestInstance1());

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					Assert.fail();
				}

				@Override
				public void destroy() {
				}

			});

		serviceDependencyManager.registerDependencies(TestInterface2.class);
	}

	@Test
	public void testRegisterClassDependencies() throws InterruptedException {
		Registry registry = RegistryUtil.getRegistry();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		final AtomicBoolean dependenciesSatisfied = new AtomicBoolean(false);

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					dependenciesSatisfied.set(true);
				}

				@Override
				public void destroy() {
				}

			});

		serviceDependencyManager.registerDependencies(
			TrackedOne.class, TrackedTwo.class);

		registry.registerService(TrackedOne.class, new TrackedOne());
		registry.registerService(
			TrackedTwo.class, new TrackedTwo(new TrackedOne()));

		Thread.sleep(100);

		Assert.assertTrue(dependenciesSatisfied.get());
	}

	@Test
	public void testRegisterFilterAndClassDependencies()
		throws InterruptedException {

		Registry registry = RegistryUtil.getRegistry();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		final AtomicBoolean dependenciesSatisfied = new AtomicBoolean(false);

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					dependenciesSatisfied.set(true);
				}

				@Override
				public void destroy() {
				}

			});

		Filter filter = registry.getFilter(
			"(objectClass=" + TrackedOne.class.getName() + ")");

		serviceDependencyManager.registerDependencies(
			new Class<?>[] {TrackedTwo.class}, new Filter[] {filter});

		registry.registerService(TrackedOne.class, new TrackedOne());
		registry.registerService(
			TrackedTwo.class, new TrackedTwo(new TrackedOne()));

		Thread.sleep(100);

		Assert.assertTrue(dependenciesSatisfied.get());
	}

	@Test
	public void testRegisterFilterDependencies() throws InterruptedException {
		Registry registry = RegistryUtil.getRegistry();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		final AtomicBoolean dependenciesSatisfied = new AtomicBoolean(false);

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					dependenciesSatisfied.set(true);
				}

				@Override
				public void destroy() {
				}

			});

		Filter filter1 = registry.getFilter(
			"(objectClass=" + TrackedOne.class.getName() + ")");
		Filter filter2 = registry.getFilter(
			"(objectClass=" + TrackedTwo.class.getName() + ")");

		serviceDependencyManager.registerDependencies(filter1, filter2);

		registry.registerService(TrackedOne.class, new TrackedOne());
		registry.registerService(
			TrackedTwo.class, new TrackedTwo(new TrackedOne()));

		Thread.sleep(100);

		Assert.assertTrue(dependenciesSatisfied.get());
	}

	@Test
	public void testWaitForDependencies() {
		Registry registry = RegistryUtil.getRegistry();

		final ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		final AtomicBoolean dependenciesSatisfied = new AtomicBoolean(false);

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					dependenciesSatisfied.set(true);
				}

				@Override
				public void destroy() {
				}

			});

		Filter filter1 = registry.getFilter(
			"(objectClass=" + TrackedOne.class.getName() + ")");
		Filter filter2 = registry.getFilter(
			"(objectClass=" + TrackedTwo.class.getName() + ")");

		serviceDependencyManager.registerDependencies(filter1, filter2);

		Thread dependencyWaiter1 = new Thread(
			new Runnable() {

				@Override
				public void run() {
					serviceDependencyManager.waitForDependencies(0);
				}

			});

		dependencyWaiter1.setDaemon(true);

		dependencyWaiter1.start();

		Thread dependencyWaiter2 = new Thread(
			new Runnable() {

				@Override
				public void run() {
					serviceDependencyManager.waitForDependencies(0);
				}

			});

		dependencyWaiter2.setDaemon(true);

		dependencyWaiter2.start();

		try {
			Thread.sleep(250);

			registry.registerService(TrackedOne.class, new TrackedOne());
			registry.registerService(
				TrackedTwo.class, new TrackedTwo(new TrackedOne()));

			Thread.sleep(250);

			Assert.assertFalse(
				"Dependencies 1 should have been fulfilled",
				dependencyWaiter1.isAlive());
			Assert.assertFalse(
				"Dependencies 2 should have been fulfilled",
				dependencyWaiter2.isAlive());

			Assert.assertTrue(dependenciesSatisfied.get());
		}
		catch (InterruptedException ie) {
		}
	}

	@Test
	public void testWaitForDependenciesUnfilled() {
		Registry registry = RegistryUtil.getRegistry();

		final ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		final AtomicBoolean dependenciesSatisfied = new AtomicBoolean(false);

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					dependenciesSatisfied.set(true);
				}

				@Override
				public void destroy() {
				}

			});

		Filter filter1 = registry.getFilter(
			"(objectClass=" + TrackedOne.class.getName() + ")");
		Filter filter2 = registry.getFilter(
			"(objectClass=" + TrackedTwo.class.getName() + ")");

		serviceDependencyManager.registerDependencies(filter1, filter2);

		registry.registerService(TrackedOne.class, new TrackedOne());

		Thread dependencyWaiter = new Thread(
			new Runnable() {

				@Override
				public void run() {
					serviceDependencyManager.waitForDependencies(100);
				}

			});

		dependencyWaiter.setDaemon(true);

		dependencyWaiter.start();

		try {
			Thread.sleep(250);

			if (!dependencyWaiter.isAlive()) {
				Assert.assertFalse(dependenciesSatisfied.get());

				return;
			}

			Assert.fail("Dependencies should have timed out");
		}
		catch (InterruptedException ie) {
		}
	}

	private static class TestInstance1 implements TestInterface1 {
	}

	private static class TestInstance2 implements TestInterface2 {
	}

	private interface TestInterface1 {
	}

	private interface TestInterface2 {
	}

}