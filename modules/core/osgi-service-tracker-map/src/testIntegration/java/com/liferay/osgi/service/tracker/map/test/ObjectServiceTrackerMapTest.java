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

package com.liferay.osgi.service.tracker.map.test;

import com.liferay.osgi.service.tracker.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.map.internal.BundleContextWrapper;
import com.liferay.osgi.service.tracker.map.internal.TrackedOne;
import com.liferay.osgi.service.tracker.map.internal.TrackedTwo;

import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.arquillian.liferay.deploymentscenario.annotations.BndFile;

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
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
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
	public void testGetServiceAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		ServiceRegistration<TrackedOne> serviceRegistration = registerService(
			new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

		serviceRegistration.unregister();

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

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
			createServiceTrackerMap(_bundleContext);

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
	public void testGetServiceInvokesCustomizerOnlyOnce()
		throws InvalidSyntaxException {

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "(target=*)",
				new ServiceReferenceMapper<String, TrackedOne>() {

					@Override
					public void map(
						ServiceReference<TrackedOne> serviceReference,
						Emitter<String> emitter) {

						emitter.emit("one");
						emitter.emit("two");
					}

				},
				new ServiceTrackerCustomizer<TrackedOne, TrackedOne>() {

					@Override
					public TrackedOne addingService(
						ServiceReference<TrackedOne> serviceReference) {

						atomicInteger.incrementAndGet();

						return _bundleContext.getService(serviceReference);
					}

					@Override
					public void modifiedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedOne trackedTwo) {
					}

					@Override
					public void removedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedOne trackedTwo) {

						_bundleContext.ungetService(serviceReference);
					}

				});

		serviceTrackerMap.open();

		TrackedOne trackedOne = new TrackedOne("1");

		ServiceRegistration<TrackedOne> serviceRegistration = registerService(
			trackedOne, "one");

		Assert.assertEquals(serviceTrackerMap.getService("one"), trackedOne);
		Assert.assertEquals(serviceTrackerMap.getService("two"), trackedOne);

		Assert.assertEquals(1, atomicInteger.get());

		serviceRegistration.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceIsNullAfterDeregistration() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

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
	public void testGetServiceWithCustomComparator()
		throws InvalidSyntaxException {

		ServiceReferenceMapper<String, TrackedOne>
			propertyServiceReferenceMapper =
				new PropertyServiceReferenceMapper<>("target");

		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "(target=*)",
				propertyServiceReferenceMapper,
				new Comparator<ServiceReference<TrackedOne>>() {

					@Override
					public int compare(
						ServiceReference<TrackedOne> serviceReference1,
						ServiceReference<TrackedOne> serviceReference2) {

						return -1;
					}

				});

		serviceTrackerMap.open();

		TrackedOne trackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			trackedOne1);

		TrackedOne trackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			trackedOne2);

		Assert.assertEquals(
			trackedOne2, serviceTrackerMap.getService("aTarget"));

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();

		registerService(trackedOne2);
		registerService(trackedOne1);

		Assert.assertEquals(
			trackedOne1, serviceTrackerMap.getService("aTarget"));

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithCustomServiceReferenceMapper()
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =

			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "(&(other=*)(target=*))",
				new ServiceReferenceMapper<String, TrackedOne>() {

					@Override
					public void map(
						ServiceReference<TrackedOne> serviceReference,
						Emitter<String> keys) {

						keys.emit(
							serviceReference.getProperty("other") + " - " +
								serviceReference.getProperty("target"));
					}

				});

		serviceTrackerMap.open();

		Dictionary<String, String> properties = new Hashtable<>();

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
			createServiceTrackerMap(_bundleContext);

		registerService(new TrackedOne(), "anotherTarget");

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithModifiedService() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		Hashtable<String, Object> properties = new Hashtable<>();

		properties.put("target", new String[] {"one", "two"});

		ServiceRegistration<TrackedOne> serviceRegistration =
			_bundleContext.registerService(
				TrackedOne.class, new TrackedOne(), properties);

		Assert.assertTrue(serviceTrackerMap.containsKey("one"));
		Assert.assertTrue(serviceTrackerMap.containsKey("two"));

		properties.put("target", new String[] {"two", "three"});

		serviceRegistration.setProperties(properties);

		Assert.assertTrue(serviceTrackerMap.containsKey("two"));
		Assert.assertTrue(serviceTrackerMap.containsKey("three"));
		Assert.assertFalse(serviceTrackerMap.containsKey("one"));

		serviceRegistration.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithMultiPropertyRegistration() {
		try (ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
				createServiceTrackerMap(_bundleContext)) {

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("service.ranking", 1);
			properties.put("target", new String[] {"aTarget1", "aTarget2"});

			ServiceRegistration<TrackedOne> serviceRegistration =
				_bundleContext.registerService(
					TrackedOne.class, new TrackedOne(), properties);

			Assert.assertNotNull(serviceTrackerMap.getService("aTarget1"));
			Assert.assertNotNull(serviceTrackerMap.getService("aTarget2"));

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetServiceWithServiceTrackerCustomizer()
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, TrackedTwo> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "target",
				new ServiceTrackerCustomizer<TrackedOne, TrackedTwo>() {

					@Override
					public TrackedTwo addingService(
						ServiceReference<TrackedOne> serviceReference) {

						return new TrackedTwo(
							_bundleContext.getService(serviceReference));
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

						_bundleContext.ungetService(serviceReference);
					}

				});

		serviceTrackerMap.open();

		TrackedOne trackedOne1 = new TrackedOne();

		registerService(trackedOne1, "one");

		TrackedOne trackedOne2 = new TrackedOne();

		registerService(trackedOne2, "two");

		TrackedTwo trackedTwo1 = serviceTrackerMap.getService("one");

		Assert.assertEquals(trackedOne1, trackedTwo1.getTrackedOne());

		TrackedTwo trackedTwo2 = serviceTrackerMap.getService("two");

		Assert.assertEquals(trackedOne2, trackedTwo2.getTrackedOne());

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithServiceTrackerCustomizerAndServiceReferenceMapper()
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, TrackedTwo> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, ("(target=*)"),
				new ServiceReferenceMapper<String, TrackedOne>() {

					@Override
					public void map(
						ServiceReference<TrackedOne> serviceReference,
						Emitter<String> emitter) {

						TrackedOne trackedOne = _bundleContext.getService(
							serviceReference);

						emitter.emit(
							serviceReference.getProperty("target") + "-" +
								trackedOne.getKey());

						_bundleContext.ungetService(serviceReference);
					}

				},
				new ServiceTrackerCustomizer<TrackedOne, TrackedTwo>() {

					@Override
					public TrackedTwo addingService(
						ServiceReference<TrackedOne> serviceReference) {

						return new TrackedTwo(
							_bundleContext.getService(serviceReference));
					}

					@Override
					public void modifiedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedTwo trackedTwo) {

						removedService(serviceReference, trackedTwo);
					}

					@Override
					public void removedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedTwo trackedTwo) {

						_bundleContext.ungetService(serviceReference);
					}

				});

		serviceTrackerMap.open();

		TrackedOne trackedOne1 = new TrackedOne("1");

		registerService(trackedOne1, "one");

		TrackedOne trackedOne2 = new TrackedOne("2");

		registerService(trackedOne2, "two");

		TrackedTwo trackedTwo1 = serviceTrackerMap.getService("one-1");

		Assert.assertEquals(trackedOne1, trackedTwo1.getTrackedOne());

		TrackedTwo trackedTwo2 = serviceTrackerMap.getService("two-2");

		Assert.assertEquals(trackedOne2, trackedTwo2.getTrackedOne());

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithServiceTrackerCustomizerReturningNull()
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, TrackedTwo> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "target",
				new ServiceTrackerCustomizer<TrackedOne, TrackedTwo>() {

					@Override
					public TrackedTwo addingService(
						ServiceReference<TrackedOne> serviceReference) {

						return null;
					}

					@Override
					public void modifiedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedTwo trackedTwo) {
					}

					@Override
					public void removedService(
						ServiceReference<TrackedOne> serviceReference,
						TrackedTwo trackedTwo) {
					}

				});

		serviceTrackerMap.open();

		TrackedOne trackedOne = new TrackedOne("1");

		ServiceRegistration<TrackedOne> serviceRegistration = registerService(
			trackedOne, "one");

		Assert.assertFalse(serviceTrackerMap.containsKey("one"));

		serviceRegistration.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		registerService(new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testOperationBalancesOutGetServiceAndUngetService() {
		BundleContextWrapper bundleContextWrapper = wrapContext();

		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap(bundleContextWrapper);

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());

		serviceRegistration2.unregister();

		serviceRegistration2 = registerService(new TrackedOne());

		serviceRegistration2.unregister();

		serviceRegistration1.unregister();

		Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
			bundleContextWrapper.getServiceReferenceCountsMap();

		Collection<AtomicInteger> serviceReferenceCounts =
			serviceReferenceCountsMap.values();

		Assert.assertEquals(3, serviceReferenceCounts.size());

		for (AtomicInteger serviceReferenceCount : serviceReferenceCounts) {
			Assert.assertEquals(0, serviceReferenceCount.get());
		}

		serviceTrackerMap.close();
	}

	@Test
	public void testServiceWrapperServiceTrackerCustomizer()
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, ServiceWrapper<TrackedOne>>
			serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
				_bundleContext, TrackedOne.class, "target",
				ServiceTrackerCustomizerFactory.<TrackedOne>serviceWrapper(
					_bundleContext));

		serviceTrackerMap.open();

		try {
			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("property", "aProperty");
			properties.put("target", "aTarget");

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration =
				_bundleContext.registerService(
					TrackedOne.class, trackedOne, properties);

			ServiceWrapper<TrackedOne> serviceWrapper =
				serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(trackedOne, serviceWrapper.getService());

			Map<String, Object> serviceWrapperProperties =
				serviceWrapper.getProperties();

			Assert.assertTrue(serviceWrapperProperties.containsKey("property"));
			Assert.assertTrue(serviceWrapperProperties.containsKey("target"));
			Assert.assertEquals(
				"aProperty", serviceWrapperProperties.get("property"));
			Assert.assertEquals(
				"aTarget", serviceWrapperProperties.get("target"));

			serviceRegistration.unregister();
		}
		finally {
			serviceTrackerMap.close();
		}
	}

	@Test
	public void testUnkeyedServiceReferencesBalanceReferenceCount()
		throws InvalidSyntaxException {

		BundleContextWrapper wrappedBundleContext = wrapContext();

		ServiceTrackerMap<TrackedOne, TrackedOne> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				wrappedBundleContext, TrackedOne.class, null,
				new ServiceReferenceMapper<TrackedOne, TrackedOne>() {

					@Override
					public void map(
						ServiceReference<TrackedOne> serviceReference,
						Emitter<TrackedOne> emitter) {
					}

				});

		serviceTrackerMap.open();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());

		Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
			wrappedBundleContext.getServiceReferenceCountsMap();

		Collection<AtomicInteger> serviceReferenceCounts =
			serviceReferenceCountsMap.values();

		Assert.assertEquals(0, serviceReferenceCounts.size());

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();

		Assert.assertEquals(0, serviceReferenceCounts.size());

		serviceTrackerMap.close();
	}

	@ArquillianResource
	public Bundle bundle;

	protected ServiceTrackerMap<String, TrackedOne> createServiceTrackerMap(
		BundleContext bundleContext) {

		try {
			_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
				bundleContext, TrackedOne.class, "target");
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

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

	protected BundleContextWrapper wrapContext() {
		return new BundleContextWrapper(_bundleContext);
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, TrackedOne> _serviceTrackerMap;

}