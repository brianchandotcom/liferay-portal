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

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.internal.InterfaceBoth;
import com.liferay.registry.internal.InterfaceOne;
import com.liferay.registry.internal.InterfaceTwo;
import com.liferay.registry.internal.TrackedOne;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

/**
 * @author Raymond Augé
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class RegistryTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_registry = RegistryUtil.getRegistry();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();
	}

	@Test
	public void testGetBundleContext() {
		Assert.assertNotNull(_bundleContext);
	}

	@Test
	public void testGetRegistry() {
		Assert.assertNotNull(_registry);
	}

	@Test
	public void testGetRegistryService() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Registry registry = _bundleContext.getService(serviceReference);

		Assert.assertNotNull(registry);
	}

	@Test
	public void testGetRegistryServiceReference() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Assert.assertNotNull(serviceReference);
	}

	@Test
	public void testGetServiceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			InterfaceOne.class);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassName() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			InterfaceOne.class.getName());

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassNameAndFilterString() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		InterfaceOne[] interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(interfaceOnes), 1, interfaceOnes.length);

		serviceRegistrationA.unregister();

		interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(interfaceOnes), 1, interfaceOnes.length);

		serviceRegistrationB.unregister();

		interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(interfaceOnes);
	}

	@Test
	public void testGetServiceReferenceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferenceByClassName() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferencesByClass() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, interfaceOneB);

		Assert.assertNotNull(serviceRegistrationB);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 2, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassAndFilterString()
		throws Exception {

		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassName() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, interfaceOneB);

		Assert.assertNotNull(serviceRegistrationB);

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(
			Arrays.toString(serviceReferences), 2, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServiceReferencesByClassNameAndFilterString()
		throws Exception {

		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(
				InterfaceOne.class.getName(), filterString);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServicesByClassAndFilterString() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<InterfaceOne> interfaceOnes = _registry.getServices(
			InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 1, interfaceOnes.size());

		serviceRegistrationA.unregister();

		interfaceOnes = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 1, interfaceOnes.size());

		serviceRegistrationB.unregister();

		interfaceOnes = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 0, interfaceOnes.size());
	}

	@Test
	public void testRegisterServiceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassAndProperties() {
		InterfaceOne interfaceOne = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "A");
		properties.put("b.property", "B");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class, interfaceOne, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("A", serviceReference.getProperty("a.property"));
		Assert.assertEquals("B", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassNames() {
		InterfaceBoth interfaceOne = new InterfaceBoth() {};

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);
		Assert.assertTrue(registeredInterfaceOne instanceof InterfaceOne);
		Assert.assertTrue(registeredInterfaceOne instanceof InterfaceTwo);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassNamesAndProperties() {
		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "E");
		properties.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			new InterfaceBoth() {}, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("E", serviceReference.getProperty("a.property"));
		Assert.assertEquals("F", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		Object registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByFilterString() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class.getName(), interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByFilterStringAndProperties() {
		InterfaceOne interfaceOne = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "C");
		properties.put("b.property", "D");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class.getName(), interfaceOne, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("C", serviceReference.getProperty("a.property"));
		Assert.assertEquals("D", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testTrackServicesByClass() {
		TestTrackServices testTrackServices = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				return _registry.trackServices(InterfaceOne.class);
			}

		};

		testTrackServices.test(2);
	}

	@Test
	public void testTrackServicesByClassAndServiceTrackerCustomizer() {
		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						return _registry.trackServices(
							InterfaceOne.class, serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(2, 2, 0, 2);
	}

	@Test
	public void testTrackServicesByClassName() {
		TestTrackServices test = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				return _registry.trackServices(InterfaceOne.class.getName());
			}

		};

		test.test(2);
	}

	@Test
	public void testTrackServicesByClassNameAndServiceTrackerCustomizer() {
		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						return _registry.trackServices(
							InterfaceOne.class.getName(),
							serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(2, 2, 0, 2);
	}

	@Test
	public void testTrackServicesByFilter() throws Exception {
		TestTrackServices testTrackServices = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				Filter filter = _registry.getFilter("(a.property=G)");

				return _registry.trackServices(filter);
			}

		};

		testTrackServices.test(1);
	}

	@Test
	public void testTrackServicesByFilterAndServiceTrackerCustomizer()
		throws Exception {

		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						Filter filter = _registry.getFilter("(a.property=G)");

						return _registry.trackServices(
							filter, serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(1, 1, 0, 1);
	}

	protected InterfaceOne getInstance() {
		return new InterfaceOne() {};
	}

	@ArquillianResource
	private Bundle _bundle;

	@ArquillianResource
	private BundleContext _bundleContext;

	private Registry _registry;

	private class MockServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<InterfaceOne, TrackedOne> {

		public MockServiceTrackerCustomizer(
			InterfaceOne interfaceOneA, InterfaceOne interfaceOneB,
			AtomicReference<TrackedOne> referenceA,
			AtomicReference<TrackedOne> referenceB) {

			_interfaceOneA = interfaceOneA;
			_interfaceOneB = interfaceOneB;
			_referenceA = referenceA;
			_referenceB = referenceB;
		}

		@Override
		public TrackedOne addingService(
			ServiceReference<InterfaceOne> serviceReference) {

			InterfaceOne service = _registry.getService(serviceReference);

			TrackedOne trackedOne = new TrackedOne();

			if (service == _interfaceOneA) {
				_referenceA.set(trackedOne);
			}
			else if (service == _interfaceOneB) {
				_referenceB.set(trackedOne);
			}

			_addingInvocationCount.incrementAndGet();

			return trackedOne;
		}

		public int getAddingInvocationCount() {
			return _addingInvocationCount.get();
		}

		public int getModifiedInvocationCount() {
			return _modifiedInvocationCount.get();
		}

		public int getRemovedInvocationCount() {
			return _removedInvocationCount.get();
		}

		@Override
		public void modifiedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne trackedOne) {

			_modifiedInvocationCount.incrementAndGet();
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne trackedOne) {

			_removedInvocationCount.incrementAndGet();
		}

		private final AtomicInteger _addingInvocationCount = new AtomicInteger(
			0);
		private final InterfaceOne _interfaceOneA;
		private final InterfaceOne _interfaceOneB;
		private final AtomicInteger _modifiedInvocationCount =
			new AtomicInteger(0);
		private final AtomicReference<TrackedOne> _referenceA;
		private final AtomicReference<TrackedOne> _referenceB;
		private final AtomicInteger _removedInvocationCount = new AtomicInteger(
			0);

	}

	private abstract class TestTrackServices {

		public abstract ServiceTracker<InterfaceOne, InterfaceOne>
			getServiceTracker();

		public void test(int expectedServicesCount) {
			ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
				getServiceTracker();

			serviceTracker.open();

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			InterfaceOne interfaceOneA = getInstance();

			ServiceRegistration<InterfaceOne> serviceRegistrationA =
				_registry.registerService(InterfaceOne.class, interfaceOneA);

			Assert.assertNotNull(serviceRegistrationA);

			InterfaceOne interfaceOneB = getInstance();

			Map<String, Object> properties = new HashMap<>();

			properties.put("a.property", "G");

			ServiceRegistration<InterfaceOne> serviceRegistrationB =
				_registry.registerService(
					InterfaceOne.class, interfaceOneB, properties);

			Assert.assertNotNull(serviceRegistrationB);

			Assert.assertFalse(serviceTracker.isEmpty());
			Assert.assertEquals(
				(expectedServicesCount == 2) ? interfaceOneA : interfaceOneB,
				serviceTracker.getService());
			Assert.assertEquals(
				interfaceOneB,
				serviceTracker.getService(
					serviceRegistrationB.getServiceReference()));

			ServiceReference<InterfaceOne>[] serviceReferences =
				serviceTracker.getServiceReferences();

			Assert.assertEquals(
				Arrays.toString(serviceReferences), expectedServicesCount,
				serviceReferences.length);

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(
				Arrays.toString(services), expectedServicesCount,
				services.length);

			SortedMap<ServiceReference<InterfaceOne>, InterfaceOne>
				trackedServiceReferences =
					serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(
				trackedServiceReferences.toString(), expectedServicesCount,
				trackedServiceReferences.size());
			Assert.assertEquals(
				(expectedServicesCount == 2) ? interfaceOneA : interfaceOneB,
				trackedServiceReferences.get(
					trackedServiceReferences.firstKey()));
			Assert.assertEquals(
				interfaceOneB,
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			serviceRegistrationA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			serviceRegistrationB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(
				trackedServiceReferences.toString(), 0,
				trackedServiceReferences.size());

			serviceTracker.close();
		}

	}

	private abstract class TestTrackServicesByServiceTrackerCustomizer {

		public abstract ServiceTracker<InterfaceOne, TrackedOne>
			getServiceTracker(
				ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
					serviceTrackerCustomizer);

		public void test(
			int expectedServicesCount, int expectedAddingInvocationCount,
			int expectedModifiedInvocationCount,
			int expectedRemovedInvocationCount) {

			InterfaceOne interfaceOneA = getInstance();
			InterfaceOne interfaceOneB = getInstance();
			AtomicReference<TrackedOne> referenceA = new AtomicReference<>();
			AtomicReference<TrackedOne> referenceB = new AtomicReference<>();

			MockServiceTrackerCustomizer mockServiceTrackerCustomizer =
				new MockServiceTrackerCustomizer(
					interfaceOneA, interfaceOneB, referenceA, referenceB);

			ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
				getServiceTracker(mockServiceTrackerCustomizer);

			serviceTracker.open();

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			ServiceRegistration<InterfaceOne> serviceRegistrationA =
				_registry.registerService(InterfaceOne.class, interfaceOneA);

			Assert.assertNotNull(serviceRegistrationA);

			Map<String, Object> properties = new HashMap<>();

			properties.put("a.property", "G");

			ServiceRegistration<InterfaceOne> serviceRegistrationB =
				_registry.registerService(
					InterfaceOne.class, interfaceOneB, properties);

			Assert.assertNotNull(serviceRegistrationB);

			Assert.assertFalse(serviceTracker.isEmpty());

			if (expectedServicesCount == 2) {
				Assert.assertEquals(
					referenceA.get(), serviceTracker.getService());
			}
			else {
				Assert.assertEquals(
					referenceB.get(), serviceTracker.getService());
			}

			Assert.assertEquals(
				referenceB.get(),
				serviceTracker.getService(
					serviceRegistrationB.getServiceReference()));

			ServiceReference<InterfaceOne>[] serviceReferences =
				serviceTracker.getServiceReferences();

			Assert.assertEquals(
				Arrays.toString(serviceReferences), expectedServicesCount,
				serviceReferences.length);

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(
				Arrays.toString(services), expectedServicesCount,
				services.length);

			SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
				trackedServiceReferences =
					serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(
				trackedServiceReferences.toString(), expectedServicesCount,
				trackedServiceReferences.size());

			if (expectedServicesCount == 2) {
				Assert.assertEquals(
					referenceA.get(),
					trackedServiceReferences.get(
						trackedServiceReferences.firstKey()));
			}
			else {
				Assert.assertEquals(
					referenceB.get(),
					trackedServiceReferences.get(
						trackedServiceReferences.firstKey()));
			}

			Assert.assertEquals(
				referenceB.get(),
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			serviceRegistrationA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			serviceRegistrationB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(
				trackedServiceReferences.toString(), 0,
				trackedServiceReferences.size());

			assertInvocationCounts(
				mockServiceTrackerCustomizer, expectedAddingInvocationCount,
				expectedModifiedInvocationCount,
				expectedRemovedInvocationCount);

			serviceTracker.close();
		}

		protected void assertInvocationCounts(
			MockServiceTrackerCustomizer mockServiceTrackerCustomizer,
			int expectedAddingInvocationCount,
			int expectedModifiedInvocationCount,
			int expectedRemovedInvocationCount) {

			Assert.assertEquals(
				expectedAddingInvocationCount,
				mockServiceTrackerCustomizer.getAddingInvocationCount());
			Assert.assertEquals(
				expectedModifiedInvocationCount,
				mockServiceTrackerCustomizer.getModifiedInvocationCount());
			Assert.assertEquals(
				expectedRemovedInvocationCount,
				mockServiceTrackerCustomizer.getRemovedInvocationCount());
		}

	}

}