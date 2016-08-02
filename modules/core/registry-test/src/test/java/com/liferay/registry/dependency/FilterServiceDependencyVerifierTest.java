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
import com.liferay.registry.ServiceReference;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class FilterServiceDependencyVerifierTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(null);
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testVerifyImplementations() {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(TestInstance1.class, new TestInstance1());

		Map<String, Object> properties = new HashMap<>();

		properties.put("type", "test");

		registry.registerService(
			TestInterface2.class, new TestInstance2(), properties);

		ServiceReference<?> serviceReference = registry.getServiceReference(
			TestInstance1.class);

		Filter filter1 = registry.getFilter(
			"(objectClass=com.liferay.registry.dependency." +
				"FilterServiceDependencyVerifierTest$TestInstance1)");

		FilterServiceDependencyVerifier filterServiceDependencyVerifier1 =
			new FilterServiceDependencyVerifier(filter1);

		Assert.assertTrue(
			filterServiceDependencyVerifier1.verify(serviceReference));

		Filter filter2 = registry.getFilter(
			"(&(objectClass=" + TestInterface2.class.getName() +
				")(type=test))");

		FilterServiceDependencyVerifier filterServiceDependencyVerifier2 =
			new FilterServiceDependencyVerifier(filter2);

		ServiceReference<?> serviceReference2 = registry.getServiceReference(
			TestInterface2.class);

		Assert.assertFalse(
			filterServiceDependencyVerifier2.verify(serviceReference));
		Assert.assertTrue(
			filterServiceDependencyVerifier2.verify(serviceReference2));
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