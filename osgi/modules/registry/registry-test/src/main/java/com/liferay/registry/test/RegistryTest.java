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
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Raymond Augé
 */
public class RegistryTest {

	@Test
	public void bundleContextIsNotNull() {
		Assert.assertNotNull(_bundleContext);
	}

	@Test
	public void getServiceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne actual = registry.getService(InterfaceOne.class);

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();
	}

	@Test
	public void getServiceByString() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne actual = registry.getService(InterfaceOne.class.getName());

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();
	}

	@Test
	public void getServiceByStringAndfilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		String filter = "(a.property=G)";

		InterfaceOne[] services = registry.getServices(
			InterfaceOne.class.getName(), filter);

		Assert.assertEquals(1, services.length);

		serviceRegistrationA.unregister();

		services = registry.getServices(InterfaceOne.class.getName(), filter);

		Assert.assertEquals(1, services.length);

		serviceRegistrationB.unregister();

		services = registry.getServices(InterfaceOne.class.getName(), filter);

		Assert.assertNull(services);
	}

	@Test
	public void getServiceReferenceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			registry.getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();
	}

	@Test
	public void getServiceReferenceByString() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			registry.getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();
	}

	@Test
	public void getServiceReferencesByClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry.getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(2, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void getServiceReferencesByClassAndFilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		String filter = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry.getServiceReferences(InterfaceOne.class, filter);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, filter);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, filter);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void getServiceReferencesByString() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry.getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(2, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void getServiceReferencesByStringAndFilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		String filter = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry.getServiceReferences(InterfaceOne.class.getName(), filter);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), filter);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), filter);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void getServicesByClassAndFilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);

		String filter = "(a.property=G)";

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, filter);

		Assert.assertEquals(1, services.size());

		serviceRegistrationA.unregister();

		services = registry.getServices(InterfaceOne.class, filter);

		Assert.assertEquals(1, services.size());

		serviceRegistrationB.unregister();

		services = registry.getServices(InterfaceOne.class, filter);

		Assert.assertEquals(0, services.size());
	}

	@Test
	public void registerServiceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();

		actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registerServiceByClassAndMap() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a.property", "A");
		map.put("b.property", "B");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one, map);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Assert.assertEquals(serviceReference.getProperty("a.property"), "A");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "B");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registerServiceByString() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class.getName(), one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertEquals(one, actual);

		serviceRegistration.unregister();

		actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registerServiceByStringAndMap() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a.property", "C");
		map.put("b.property", "D");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class.getName(), one, map);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Assert.assertEquals(serviceReference.getProperty("a.property"), "C");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "D");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registerServiceByStrings() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceBoth one = new InterfaceBoth() {/**/};

		String[] classNames = new String[] {
			InterfaceOne.class.getName(), InterfaceTwo.class.getName()
		};

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			classNames, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object actual = registry.getService(serviceReference);

		Assert.assertEquals(one, actual);
		Assert.assertTrue(actual instanceof InterfaceOne);
		Assert.assertTrue(actual instanceof InterfaceTwo);

		serviceRegistration.unregister();

		actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registerServiceByStringsAndMap() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceBoth one = new InterfaceBoth() {/**/};

		String[] classNames = new String[] {
			InterfaceOne.class.getName(), InterfaceTwo.class.getName()
		};

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a.property", "E");
		map.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			classNames, one, map);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Assert.assertEquals(serviceReference.getProperty("a.property"), "E");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "F");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		Object actual = registry.getService(serviceReference);

		Assert.assertNull(actual);
	}

	@Test
	public void registryWasWired() {
		Registry registry = RegistryUtil.getRegistry();

		Assert.assertNotNull(registry);
	}

	@Test
	public void serviceExists() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Registry registry = _bundleContext.getService(serviceReference);

		Assert.assertNotNull(registry);
	}

	@Test
	public void serviceReferenceExists() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Assert.assertNotNull(serviceReference);
	}

	@Before
	public void setup() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void trackServicesByClass() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(InterfaceOne.class);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(a, serviceTracker.getService());
		Assert.assertEquals(
			b,
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(2, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(2, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, InterfaceOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(2, trackedServiceReferences.size());
		Assert.assertEquals(
			a,
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			b,
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void trackServicesByClassAndCustomizer() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne> customizer =
			new TestCustomizer(a, b, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(InterfaceOne.class, customizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceA.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(2, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(2, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReference =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReference);
		Assert.assertEquals(2, trackedServiceReference.size());
		Assert.assertEquals(
			referenceA.get(),
			trackedServiceReference.get(trackedServiceReference.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReference.get(trackedServiceReference.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReference = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReference);
		Assert.assertEquals(0, trackedServiceReference.size());

		serviceTracker.close();
	}

	@Test
	public void trackServicesByFilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(filter);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(b, serviceTracker.getService());
		Assert.assertEquals(
			b,
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(1, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(1, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, InterfaceOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(1, trackedServiceReferences.size());
		Assert.assertEquals(
			b,
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			b,
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void trackServicesByFilterAndCustomizer() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne> customizer =
			new TestCustomizer(a, b, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(filter, customizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceB.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(1, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(1, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(1, trackedServiceReferences.size());
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void trackServicesByString() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(InterfaceOne.class.getName());

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(a, serviceTracker.getService());
		Assert.assertEquals(
			b,
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(2, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(2, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, InterfaceOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(2, trackedServiceReferences.size());
		Assert.assertEquals(
			a,
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			b,
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void trackServicesByStringAndCustomizer() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne a = getInstance();
		InterfaceOne b = getInstance();

		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne> customizer =
			new TestCustomizer(a, b, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(InterfaceOne.class.getName(), customizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, a);
		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, b, map);

		Assert.assertNotNull(serviceRegistrationA);
		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceA.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));
		Assert.assertEquals(2, serviceTracker.getServiceReferences().length);
		Assert.assertEquals(2, serviceTracker.getServices().length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(2, trackedServiceReferences.size());
		Assert.assertEquals(
			referenceA.get(),
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	private InterfaceOne getInstance() {
		return new InterfaceOne() {/**/};
	}

	private BundleContext _bundleContext;

	private class TestCustomizer
		implements ServiceTrackerCustomizer<InterfaceOne, TrackedOne> {

		public TestCustomizer(
			InterfaceOne a, InterfaceOne b,
			AtomicReference<TrackedOne> referenceA,
			AtomicReference<TrackedOne> referenceB) {

			_a = a;
			_b = b;
			_referenceA = referenceA;
			_referenceB = referenceB;
		}

		@Override
		public TrackedOne addingService(
			ServiceReference<InterfaceOne> serviceReference) {

			InterfaceOne service = RegistryUtil.getRegistry().getService(
				serviceReference);
			TrackedOne testWrapper = new TrackedOne();

			if (service == _a) {
				_referenceA.set(testWrapper);
			}
			else if (service == _b) {
				_referenceB.set(testWrapper);
			}

			return testWrapper;
		}

		@Override
		public void modifiedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {

			//
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {

			//
		}

		private AtomicReference<TrackedOne> _referenceA;
		private AtomicReference<TrackedOne> _referenceB;
		private InterfaceOne _a;
		private InterfaceOne _b;

	}
}