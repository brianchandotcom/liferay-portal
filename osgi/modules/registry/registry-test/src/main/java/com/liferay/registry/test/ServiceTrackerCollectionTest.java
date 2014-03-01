/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.registry.test;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollectionTest {

	@Test
	public void testByClass() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(InterfaceOne.class);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(2, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());

		serviceTrackerList.remove(b);

		Assert.assertEquals(0, serviceTrackerList.size());
	}

	@Test
	public void testByClassFilter() throws Exception {
		final Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(InterfaceOne.class, filter);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a, properties);

		Assert.assertNotNull(serviceRegistrationA);

		boolean added = serviceTrackerList.add(b);

		Assert.assertFalse(added);

		Assert.assertEquals(1, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceRegistrationA.unregister();

		Assert.assertEquals(0, serviceTrackerList.size());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
	}

	@Test
	public void testByClassFilterMap() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(
				InterfaceOne.class, filter, properties);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(1, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
	}

	@Test
	public void testByClassFilterSTCustomizer() throws Exception {
		final Registry registry = RegistryUtil.getRegistry();
		final AtomicInteger counter = new AtomicInteger();

		ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> customizer =
			new TestCustomizer(counter);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(
				InterfaceOne.class, filter, customizer);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a, properties);

		Assert.assertNotNull(serviceRegistrationA);

		boolean added = serviceTrackerList.add(b);

		Assert.assertFalse(added);

		Assert.assertEquals(1, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());
		Assert.assertEquals(1, counter.intValue());

		serviceRegistrationA.unregister();

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(2, counter.intValue());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(2, counter.intValue());
	}

	@Test
	public void testByClassFilterSTCustomizerMap() throws Exception {
		final Registry registry = RegistryUtil.getRegistry();
		final AtomicInteger counter = new AtomicInteger();

		ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> customizer =
			new TestCustomizer(counter);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(
				InterfaceOne.class, filter, customizer, properties);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(1, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());
		Assert.assertEquals(1, counter.intValue());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());
		Assert.assertEquals(1, counter.intValue());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(2, counter.intValue());
	}

	@Test
	public void testByClassMap() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(InterfaceOne.class, properties);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a, properties);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(2, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(2, services.size());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
	}

	@Test
	public void testByClassSTCustomizer() {
		final Registry registry = RegistryUtil.getRegistry();
		final AtomicInteger counter = new AtomicInteger();

		ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> customizer =
			new TestCustomizer(counter);

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(InterfaceOne.class, customizer);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(2, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Assert.assertEquals(2, counter.intValue());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());
		Assert.assertEquals(3, counter.intValue());

		serviceTrackerList.remove(b);

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(4, counter.intValue());
	}

	@Test
	public void testByClassSTCustomizerMap() throws Exception {
		final Registry registry = RegistryUtil.getRegistry();
		final AtomicInteger counter = new AtomicInteger();

		ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> customizer =
			new TestCustomizer(counter);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(
				InterfaceOne.class, customizer, properties);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a, properties);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(2, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(2, services.size());
		Assert.assertEquals(2, counter.intValue());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());
		Assert.assertEquals(3, counter.intValue());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(4, counter.intValue());
	}

	@Test
	public void testByClassSTCustomizerMap_2() throws Exception {
		final Registry registry = RegistryUtil.getRegistry();
		final AtomicInteger counter = new AtomicInteger();

		ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> customizer =
			new TestCustomizer(counter);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceTrackerList<InterfaceOne> serviceTrackerList =
			ServiceTrackerCollections.list(
				InterfaceOne.class, customizer, properties);

		Assert.assertEquals(0, serviceTrackerList.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);

		Assert.assertNotNull(serviceRegistrationA);

		serviceTrackerList.add(b);

		Assert.assertEquals(2, serviceTrackerList.size());

		for (InterfaceOne interfaceOne : serviceTrackerList) {
			Assert.assertNotNull(interfaceOne);
		}

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());
		Assert.assertEquals(2, counter.intValue());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTrackerList.size());
		Assert.assertEquals(3, counter.intValue());

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(1, services.size());

		serviceTrackerList.remove(b);

		services = registry.getServices(InterfaceOne.class, "(a.property=G)");

		Assert.assertEquals(0, services.size());

		Assert.assertEquals(0, serviceTrackerList.size());
		Assert.assertEquals(4, counter.intValue());
	}

	private InterfaceOne getInstance() {
		return new InterfaceOne() {/**/};
	}

	private class TestCustomizer
		implements ServiceTrackerCustomizer<InterfaceOne, InterfaceOne> {

		public TestCustomizer(AtomicInteger counter) {
			_counter = counter;
		}

		@Override
		public InterfaceOne addingService(
			ServiceReference<InterfaceOne> serviceReference) {

			InterfaceOne service = RegistryUtil.getRegistry().getService(
				serviceReference);

			_counter.incrementAndGet();

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<InterfaceOne> serviceReference,
			InterfaceOne service) {

			_counter.incrementAndGet();
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			InterfaceOne service) {

			RegistryUtil.getRegistry().ungetService(serviceReference);

			_counter.incrementAndGet();
		}

		private AtomicInteger _counter;

	}

}