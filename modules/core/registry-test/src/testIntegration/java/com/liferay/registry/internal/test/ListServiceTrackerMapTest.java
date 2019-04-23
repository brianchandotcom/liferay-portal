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

package com.liferay.registry.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.internal.RegistryWrapper;
import com.liferay.registry.internal.TrackedOne;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class ListServiceTrackerMapTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			ListServiceTrackerMapTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGestServiceWithDifferentRanking() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			TrackedOne trackedOne1 = new TrackedOne();

			_serviceRegistrations.add(registerService(trackedOne1, 1));

			TrackedOne trackedOne3 = new TrackedOne();

			_serviceRegistrations.add(registerService(trackedOne3, 3));

			TrackedOne trackedOne2 = new TrackedOne();

			_serviceRegistrations.add(registerService(trackedOne2, 2));

			List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(services.toString(), 3, services.size());

			Iterator<? extends TrackedOne> iterator = services.iterator();

			Assert.assertEquals(trackedOne3, iterator.next());
			Assert.assertEquals(trackedOne2, iterator.next());
			Assert.assertEquals(trackedOne1, iterator.next());
		}
	}

	@Test
	public void testGestServiceWithUnregistering() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			TrackedOne trackedOne1 = new TrackedOne();

			_serviceRegistrations.add(registerService(trackedOne1, 1));

			TrackedOne trackedOne3 = new TrackedOne();

			_serviceRegistrations.add(registerService(trackedOne3, 3));

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne2, 2);

			List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(services.toString(), 3, services.size());

			Iterator<? extends TrackedOne> iterator = services.iterator();

			serviceRegistration2.unregister();

			// Deregistering a service should not affect an already existing
			// collection or iterator

			Assert.assertEquals(trackedOne3, iterator.next());
			Assert.assertEquals(trackedOne2, iterator.next());
			Assert.assertEquals(trackedOne1, iterator.next());

			services = serviceTrackerMap.getService("aTarget");

			// Getting the list of services should return a list with the
			// affected changes

			Assert.assertEquals(services.toString(), 2, services.size());

			iterator = services.iterator();

			Assert.assertEquals(trackedOne3, iterator.next());
			Assert.assertEquals(trackedOne1, iterator.next());
		}
	}

	@Test
	public void testGetServicesIsNullAfterDeregistration() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne());

			Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertNull(serviceTrackerMap.getService("aTarget"));
		}
	}

	@Test
	public void testGetServicesWithDifferentKeys() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			_serviceRegistrations.add(
				registerService(new TrackedOne(), "aTarget"));
			_serviceRegistrations.add(
				registerService(new TrackedOne(), "anotherTarget"));
			_serviceRegistrations.add(
				registerService(new TrackedOne(), "aTarget"));
			_serviceRegistrations.add(
				registerService(new TrackedOne(), "anotherTarget"));
			_serviceRegistrations.add(
				registerService(new TrackedOne(), "anotherTarget"));

			List<TrackedOne> aTargetList = serviceTrackerMap.getService(
				"aTarget");

			Assert.assertNotNull(aTargetList);
			Assert.assertEquals(aTargetList.toString(), 2, aTargetList.size());

			List<TrackedOne> anotherTargetList = serviceTrackerMap.getService(
				"anotherTarget");

			Assert.assertNotNull(anotherTargetList);
			Assert.assertEquals(
				anotherTargetList.toString(), 3, anotherTargetList.size());
		}
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			_serviceRegistrations.add(registerService(new TrackedOne()));

			List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(services.toString(), 1, services.size());
		}
	}

	@Test
	public void testGetServiceWithSimpleRegistrationTwice() {
		try (ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
				createServiceTrackerMap()) {

			_serviceRegistrations.add(registerService(new TrackedOne()));
			_serviceRegistrations.add(registerService(new TrackedOne()));

			List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(services.toString(), 2, services.size());
		}
	}

	@Test
	public void testUnkeyedServiceReferencesBalanceRefCount() {
		Registry registry = RegistryUtil.getRegistry();

		RegistryWrapper registryWrapper = new RegistryWrapper(registry);

		RegistryUtil.setRegistry(registryWrapper);

		try (ServiceTrackerMap<TrackedOne, TrackedOne> serviceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					TrackedOne.class, null,
					new ServiceReferenceMapper<TrackedOne, TrackedOne>() {

						@Override
						public void map(
							ServiceReference<TrackedOne> serviceReference,
							Emitter<TrackedOne> emitter) {
						}

					})) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne());

			Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
				registryWrapper.getServiceReferenceCountsMap();

			Collection<AtomicInteger> serviceReferenceCounts =
				serviceReferenceCountsMap.values();

			Assert.assertEquals(
				serviceReferenceCounts.toString(), 0,
				serviceReferenceCounts.size());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertEquals(
				serviceReferenceCounts.toString(), 0,
				serviceReferenceCounts.size());
		}
		finally {
			RegistryUtil.setRegistry(registry);
		}
	}

	protected ServiceTrackerMap<String, List<TrackedOne>>
		createServiceTrackerMap() {

		_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
			TrackedOne.class, "target");

		return _serviceTrackerMap;
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne) {

		return registerService(trackedOne, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, int ranking) {

		return registerService(trackedOne, ranking, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, int ranking, String target) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("service.ranking", ranking);
		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, String target) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	private BundleContext _bundleContext;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private ServiceTrackerMap<String, List<TrackedOne>> _serviceTrackerMap;

}