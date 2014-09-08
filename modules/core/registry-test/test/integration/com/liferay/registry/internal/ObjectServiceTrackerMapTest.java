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

package com.liferay.registry.internal;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
@RunWith(Arquillian.class)
public class ObjectServiceTrackerMapTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();

		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}
	}

	@Test
	public void testGetServiceAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		ServiceRegistration<TrackedOne> serviceRegistration = registerService(
			new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

		serviceRegistration.unregister();

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		TrackedOne trackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			trackedOne1, 2);

		TrackedOne trackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			trackedOne2, 1);

		Assert.assertEquals(
			trackedOne1, serviceTrackerMap.getService("aTarget"));

		serviceRegistration1.unregister();

		Assert.assertEquals(
			trackedOne2, serviceTrackerMap.getService("aTarget"));

		serviceRegistration2.unregister();
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemovalInverseOrder() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		TrackedOne trackedOne2 = new TrackedOne();

		registerService(trackedOne2, 1);

		TrackedOne trackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			trackedOne1, 2);

		Assert.assertEquals(
			trackedOne1, serviceTrackerMap.getService("aTarget"));

		serviceRegistration1.unregister();

		Assert.assertEquals(
			trackedOne2, serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceIsNullAfterDeregistration() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration3 = registerService(
			new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();
		serviceRegistration3.unregister();

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithCustomComparator() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				ServiceTrackerCollections.singleValueMap(
					TrackedOne.class, "(target=*)",
					new ServiceTrackerCollections.
						PropertyServiceReferenceMapper<String>("target"),
					new Comparator<ServiceReference<TrackedOne>>() {

						@Override
						public int compare(
							ServiceReference<TrackedOne> serviceReference1,
							ServiceReference<TrackedOne> serviceReference2) {

							return -1;
						}

					}
				)) {

			serviceTrackerMap.open();

			TrackedOne trackedOne1 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(trackedOne1);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne2);

			Assert.assertEquals(
				trackedOne2, serviceTrackerMap.getService("aTarget"));

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			registerService(trackedOne2);
			registerService(trackedOne1);

			Assert.assertEquals(
				trackedOne1, serviceTrackerMap.getService("aTarget"));
		}
	}

	@Test
	public void testGetServiceWithCustomResolver() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =

			ServiceTrackerCollections.singleValueMap(
				TrackedOne.class, "(&(other=*)(target=*))",
				new ServiceReferenceMapper<String>() {

					@Override
					public void map(
						ServiceReference<?> serviceReference,
						Emitter<String> keys) {

						keys.emit(
							serviceReference.getProperty("other") + " - " +
								serviceReference.getProperty("target"));
					}

				});

		serviceTrackerMap.open();

		Dictionary<String, String> properties = new Hashtable<String, String>();

		properties.put("other", "aProperty");
		properties.put("target", "aTarget");

		_bundleContext.registerService(
			TrackedOne.class, new TrackedOne(), properties);

		Assert.assertNotNull(
			serviceTrackerMap.getService("aProperty - aTarget"));
	}

	@Test
	public void testGetServiceWithIncorrectKey() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		registerService(new TrackedOne(), "anotherTarget");

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		registerService(new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testOperationBalancesOutGetServiceAndUngetService() {
		Registry registry = RegistryUtil.getRegistry();

		RegistryWrapper registryWrapper = new RegistryWrapper(registry);

		RegistryUtil.setRegistry(registryWrapper);

		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne());

			serviceRegistration2.unregister();

			serviceRegistration2 = registerService(new TrackedOne());

			serviceRegistration2.unregister();

			serviceRegistration1.unregister();

			Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
				registryWrapper.getServiceReferenceCountsMap();

			Collection<AtomicInteger> serviceReferenceCounts =
				serviceReferenceCountsMap.values();

			Assert.assertEquals(3, serviceReferenceCounts.size());

			for (AtomicInteger serviceReferenceCount : serviceReferenceCounts) {
				Assert.assertEquals(0, serviceReferenceCount.get());
			}
		}

		RegistryUtil.setRegistry(registry);
	}

	@Test
	public void testUnkeyedServiceReferencesBalanceRefCount() {
		Registry registry = RegistryUtil.getRegistry();

		RegistryWrapper registryWrapper = new RegistryWrapper(registry);

		try (ServiceTrackerMap<TrackedOne, TrackedOne> serviceTrackerMap =
				ServiceTrackerCollections.singleValueMap(
					TrackedOne.class, null,
					new ServiceReferenceMapper<TrackedOne>() {

						@Override
						public void map(
							ServiceReference<?> serviceReference,
							Emitter<TrackedOne> emitter) {
						}

					}
				)) {

			serviceTrackerMap.open();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne());

			Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
				registryWrapper.getServiceReferenceCountsMap();

			Collection<AtomicInteger> serviceReferenceCounts =
				serviceReferenceCountsMap.values();

			Assert.assertEquals(0, serviceReferenceCounts.size());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();

			Assert.assertEquals(0, serviceReferenceCounts.size());
		}
	}

	@ArquillianResource
	public Bundle _bundle;

	protected ServiceTrackerMap<String, TrackedOne> createServiceTrackerMap() {
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			TrackedOne.class, "target");

		_serviceTrackerMap.open();

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

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put("service.ranking", ranking);
		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, String target) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, TrackedOne> _serviceTrackerMap;

}