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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.internal.RegistryWrapper;
import com.liferay.registry.internal.TrackedOne;
import com.liferay.registry.internal.TrackedTwo;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
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
		bundle.start();

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		bundle.stop();

		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}
	}

	@Test
	public void testContainsKey() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration3 =
				registerService(new TrackedOne(), "aTarget2");
			ServiceRegistration<TrackedOne> serviceRegistration4 =
				registerService(new TrackedOne(), "aTarget2");

			Assert.assertTrue(serviceTrackerMap.containsKey("aTarget1"));
			Assert.assertTrue(serviceTrackerMap.containsKey("aTarget2"));
			Assert.assertFalse(serviceTrackerMap.containsKey("aTarget3"));
			Assert.assertFalse(serviceTrackerMap.containsKey(""));

			try {
				serviceTrackerMap.containsKey(null);

				Assert.fail();
			}
			catch (NullPointerException npe) {
			}

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();
			serviceRegistration4.unregister();
		}
	}

	@Test
	public void testGetServiceAfterRemoval() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration =
				registerService(new TrackedOne());

			Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

			serviceRegistration.unregister();

			Assert.assertNull(serviceTrackerMap.getService("aTarget"));
		}
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemoval() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			TrackedOne trackedOne1 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(trackedOne1, 2);

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne2, 1);

			Assert.assertEquals(
				trackedOne1, serviceTrackerMap.getService("aTarget"));

			serviceRegistration1.unregister();

			Assert.assertEquals(
				trackedOne2, serviceTrackerMap.getService("aTarget"));

			serviceRegistration2.unregister();
		}
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemovalInverseOrder() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(trackedOne2, 1);

			TrackedOne trackedOne1 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne1, 2);

			Assert.assertEquals(
				trackedOne1, serviceTrackerMap.getService("aTarget"));

			serviceRegistration2.unregister();

			Assert.assertEquals(
				trackedOne2, serviceTrackerMap.getService("aTarget"));

			serviceRegistration1.unregister();
		}
	}

	@Test
	public void testGetServiceIsNullAfterDeregistration() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne());
			ServiceRegistration<TrackedOne> serviceRegistration3 =
				registerService(new TrackedOne());

			Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();

			Assert.assertNull(serviceTrackerMap.getService("aTarget"));
		}
	}

	@Test
	public void testGetServiceWithCustomResolver() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					TrackedOne.class, "(&(other=*)(target=*))",
					new ServiceReferenceMapper<String, TrackedOne>() {

						@Override
						public void map(
							ServiceReference<TrackedOne> serviceReference,
							Emitter<String> keys) {

							keys.emit(
								serviceReference.getProperty("other") + " - " +
									serviceReference.getProperty("target"));
						}

					})) {

			Dictionary<String, String> properties = new Hashtable<>();

			properties.put("other", "aProperty");
			properties.put("target", "aTarget");

			ServiceRegistration<TrackedOne> serviceRegistration =
				_bundleContext.registerService(
					TrackedOne.class, new TrackedOne(), properties);

			Assert.assertNotNull(
				serviceTrackerMap.getService("aProperty - aTarget"));

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetServiceWithIncorrectKey() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			registerService(new TrackedOne(), "anotherTarget");

			Assert.assertNull(serviceTrackerMap.getService("aTarget"));
		}
	}

	@Test
	public void testGetServiceWithServiceCustomizer() {
		final Registry registry = RegistryUtil.getRegistry();

		try (ServiceTrackerMap<String, TrackedTwo> serviceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					TrackedOne.class, "target",
					new ServiceTrackerCustomizer<TrackedOne, TrackedTwo>() {

						@Override
						public TrackedTwo addingService(
							ServiceReference<TrackedOne> serviceReference) {

							return new TrackedTwo(
								registry.getService(serviceReference));
						}

						@Override
						public void modifiedService(
							ServiceReference<TrackedOne> serviceReference,
							TrackedTwo service) {

							removedService(serviceReference, service);
						}

						@Override
						public void removedService(
							ServiceReference<TrackedOne> serviceReference,
							TrackedTwo service) {

							registry.ungetService(serviceReference);
						}

					})) {

			TrackedOne trackedOne1 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(trackedOne1, "trackedOne1");

			TrackedOne trackedOne2 = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne2, "trackedOne2");

			TrackedTwo trackedTwo1 = serviceTrackerMap.getService(
				"trackedOne1");

			Assert.assertEquals(trackedOne1, trackedTwo1.getTrackedOne());

			TrackedTwo trackedTwo2 = serviceTrackerMap.getService(
				"trackedOne2");

			Assert.assertEquals(trackedOne2, trackedTwo2.getTrackedOne());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
		}
	}

	@Test
	public void testGetServiceWithServiceCustomizerAndServiceReferenceMapper() {
		final Registry registry = RegistryUtil.getRegistry();

		try (ServiceTrackerMap<String, TrackedTwo> serviceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					TrackedOne.class, ("(target=*)"),
					new ServiceReferenceMapper<String, TrackedOne>() {

						@Override
						public void map(
							ServiceReference<TrackedOne> serviceReference,
							Emitter<String> emitter) {

							TrackedOne service = registry.getService(
								serviceReference);

							String targetProperty =
								(String)serviceReference.getProperty("target");

							emitter.emit(
								targetProperty + "-" + service.getKey());

							registry.ungetService(serviceReference);
						}

					},
					new ServiceTrackerCustomizer<TrackedOne, TrackedTwo>() {

						@Override
						public TrackedTwo addingService(
							ServiceReference<TrackedOne> serviceReference) {

							return new TrackedTwo(
								registry.getService(serviceReference));
						}

						@Override
						public void modifiedService(
							ServiceReference<TrackedOne> serviceReference,
							TrackedTwo service) {

							removedService(serviceReference, service);
						}

						@Override
						public void removedService(
							ServiceReference<TrackedOne> serviceReference,
							TrackedTwo service) {

							registry.ungetService(serviceReference);
						}

					})) {

			TrackedOne trackedOne1 = new TrackedOne("1");

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(trackedOne1, "trackedOne1");

			TrackedOne trackedOne2 = new TrackedOne("2");

			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(trackedOne2, "trackedOne2");

			TrackedTwo trackedTwo1 = serviceTrackerMap.getService(
				"trackedOne1-1");

			Assert.assertEquals(trackedOne1, trackedTwo1.getTrackedOne());

			TrackedTwo trackedTwo2 = serviceTrackerMap.getService(
				"trackedOne2-2");

			Assert.assertEquals(trackedOne2, trackedTwo2.getTrackedOne());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
		}
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration =
				registerService(new TrackedOne());

			Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testKeySet() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration3 =
				registerService(new TrackedOne(), "aTarget2");
			ServiceRegistration<TrackedOne> serviceRegistration4 =
				registerService(new TrackedOne(), "aTarget2");

			Set<String> targets = new HashSet<>();

			targets.add("aTarget1");
			targets.add("aTarget2");

			Assert.assertEquals(targets, serviceTrackerMap.keySet());

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();
			serviceRegistration4.unregister();
		}
	}

	@Test
	public void testKeySetReturnsUnmodifiableSet() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap()) {

			ServiceRegistration<TrackedOne> serviceRegistration1 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration2 =
				registerService(new TrackedOne(), "aTarget1");
			ServiceRegistration<TrackedOne> serviceRegistration3 =
				registerService(new TrackedOne(), "aTarget2");
			ServiceRegistration<TrackedOne> serviceRegistration4 =
				registerService(new TrackedOne(), "aTarget2");

			Set<String> keySet = serviceTrackerMap.keySet();

			try {
				keySet.remove("aTarget1");

				Assert.fail();
			}
			catch (UnsupportedOperationException uoe) {
			}

			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
			serviceRegistration3.unregister();
			serviceRegistration4.unregister();
		}
	}

	@Test
	public void testUnkeyedServiceReferencesBalanceRefCount() {
		RegistryWrapper registryWrapper = getRegistryWrapper();

		try (ServiceTrackerMap<TrackedOne, TrackedOne> serviceTrackerMap =
				ServiceTrackerCollections.openSingleValueMap(
					TrackedOne.class, null,
					new ServiceReferenceMapper<TrackedOne, TrackedOne>() {

						@Override
						public void map(
							ServiceReference<TrackedOne> serviceReference,
							Emitter<TrackedOne> emitter) {
						}

					}
				)) {

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
		finally {
			RegistryUtil.setRegistry(registryWrapper.getWrappedRegistry());
		}
	}

	@ArquillianResource
	public Bundle bundle;

	protected ServiceTrackerMap<String, TrackedOne> createServiceTrackerMap() {
		return ServiceTrackerCollections.openSingleValueMap(
			TrackedOne.class, "target");
	}

	protected RegistryWrapper getRegistryWrapper() {
		Registry registry = RegistryUtil.getRegistry();

		RegistryWrapper registryWrapper = new RegistryWrapper(registry);

		RegistryUtil.setRegistry(registryWrapper);

		return registryWrapper;
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
	private ServiceTrackerMap<String, TrackedOne> _serviceTrackerMap;

}