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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

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

	private class TestInstance1 implements TestInterface1 {
	}

	private class TestInstance2 implements TestInterface2 {
	}

	private interface TestInterface1 {
	}

	private interface TestInterface2 {
	}

}