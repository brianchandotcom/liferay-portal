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

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testGetBundleContext() {
		Assert.assertNotNull(_bundleContext);
	}

	@Test
	public void testGetRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		Assert.assertNotNull(registry);
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
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(null);

		InterfaceOne registered = registry().getService(InterfaceOne.class);

		assertRegisteredInstance(registeredTuple, registered);
	}

	@Test
	public void testGetServiceByClassName() {
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(null);

		InterfaceOne registered = registry().getService(
			InterfaceOne.class.getName());

		assertRegisteredInstance(registeredTuple, registered);
	}

	@Test
	public void testGetServiceReferenceByClass() {
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(null);

		ServiceReference<InterfaceOne> serviceReference =
			registry().getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne registered = registry().getService(serviceReference);

		assertRegisteredInstance(registeredTuple, registered);
	}

	@Test
	public void testGetServiceReferenceByClassName() {
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(null);

		ServiceReference<InterfaceOne> serviceReference =
			registry().getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne registered = registry().getService(serviceReference);

		assertRegisteredInstance(registeredTuple, registered);
	}

	@Test
	public void testGetServiceReferencesByClass() throws Exception {
		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(null);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry().getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(2, serviceReferences.size());

		registeredTupleA.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(1, serviceReferences.size());

		registeredTupleB.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassAndFilterString()
		throws Exception {

		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(properties);

		String filterString = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry().getServiceReferences(InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		registeredTupleA.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		registeredTupleB.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassName() throws Exception {
		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(null);

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry().getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(2, serviceReferences.length);

		registeredTupleA.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(1, serviceReferences.length);

		registeredTupleB.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServiceReferencesByClassNameAndFilterString()
		throws Exception {

		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(properties);

		String filterString = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry().getServiceReferences(
				InterfaceOne.class.getName(), filterString);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(1, serviceReferences.length);

		registeredTupleA.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, serviceReferences.length);

		registeredTupleB.unregister();

		serviceReferences = registry().getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServicesByClassAndFilterString() throws Exception {
		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(properties);

		String filterString = "(a.property=G)";

		Collection<InterfaceOne> services = registry().getServices(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		registeredTupleA.unregister();

		services = registry().getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		registeredTupleB.unregister();

		services = registry().getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(0, services.size());
	}

	@Test
	public void testGetServicesByClassNameAndFilterString() throws Exception {
		RegisteredTuple<InterfaceOne> registeredTupleA =
			getInstanceRegisteredByClass(null);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		RegisteredTuple<InterfaceOne> registeredTupleB =
			getInstanceRegisteredByClass(properties);

		String filterString = "(a.property=G)";

		InterfaceOne[] services = registry().getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		registeredTupleA.unregister();

		services = registry().getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		registeredTupleB.unregister();

		services = registry().getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(services);
	}

	@Test
	public void testRegisterServiceByClass() {
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(null);

		ServiceReference<InterfaceOne> serviceReference =
			registeredTuple.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registered = registry().getService(serviceReference);

		assertRegisteredInstance(registeredTuple, registered);

		registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testRegisterServiceByClassAndProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "A");
		properties.put("b.property", "B");

		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClass(properties);

		ServiceReference<InterfaceOne> serviceReference =
			registeredTuple.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "A");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "B");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		registeredTuple.unregister();

		InterfaceOne registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testRegisterServiceByClassNames() {
		InterfaceBoth one = getInstance();

		ServiceRegistration<?> serviceRegistration = registry().registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object registered = registry().getService(serviceReference);

		Assert.assertEquals(one, registered);
		Assert.assertTrue(registered instanceof InterfaceOne);
		Assert.assertTrue(registered instanceof InterfaceTwo);

		serviceRegistration.unregister();

		registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testRegisterServiceByClassNamesAndProperties() {
		InterfaceBoth instance = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "E");
		properties.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = registry().registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			instance, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "E");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "F");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		Object registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testRegisterServiceByFilterString() {
		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClassName(null);

		ServiceReference<InterfaceOne> serviceReference =
			registeredTuple.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registered = registry().getService(serviceReference);

		assertRegisteredInstance(registeredTuple, registered);

		registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testRegisterServiceByFilterStringAndProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "C");
		properties.put("b.property", "D");

		RegisteredTuple<InterfaceOne> registeredTuple =
			getInstanceRegisteredByClassName(properties);

		ServiceReference<InterfaceOne> serviceReference =
			registeredTuple.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "C");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "D");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		registeredTuple.unregister();

		InterfaceOne registered = registry().getService(serviceReference);

		Assert.assertNull(registered);
	}

	@Test
	public void testTrackServicesByClass() {
		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry().trackServices(InterfaceOne.class);

		serviceTracker.open();

		TestTrackServicesByClassOrClassName<InterfaceOne>
			trackerTest =
				new TestTrackServicesByClassOrClassName<InterfaceOne>() {

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClass(null);
				}

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClass(properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	@Test
	public void testTrackServicesByClassAndServiceTrackerCustomizer() {
		final InterfaceOne instanceA = getInstance();
		final InterfaceOne instanceB = getInstance();
		final AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		final AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new TestServiceTrackerCustomizer(
					instanceA, instanceB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry().trackServices(
				InterfaceOne.class, serviceTrackerCustomizer);

		serviceTracker.open();

		TestTrackServicesByClassOrClassName<TrackedOne>
			trackerTest =
				new TestTrackServicesByClassOrClassName<TrackedOne>() {

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClass(
						instanceA, referenceA, null);
				}

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClass(
						instanceB, referenceB, properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	@Test
	public void testTrackServicesByClassName() {
		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry().trackServices(InterfaceOne.class.getName());

		serviceTracker.open();

		TestTrackServicesByClassOrClassName<InterfaceOne>
			trackerTest =
				new TestTrackServicesByClassOrClassName<InterfaceOne>() {

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClassName(null);
				}

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClassName(properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	@Test
	public void testTrackServicesByClassNameAndServiceTrackerCustomizer() {
		final InterfaceOne instanceA = getInstance();
		final InterfaceOne instanceB = getInstance();
		final AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		final AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new TestServiceTrackerCustomizer(
					instanceA, instanceB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry().trackServices(
				InterfaceOne.class.getName(), serviceTrackerCustomizer);

		serviceTracker.open();

		TestTrackServicesByClassOrClassName<TrackedOne>
			trackerTest =
				new TestTrackServicesByClassOrClassName<TrackedOne>() {

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClass(
						instanceA, referenceA, null);
				}

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClass(
						instanceB, referenceB, properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	@Test
	public void testTrackServicesByFilter() throws Exception {
		Filter filter = registry().getFilter("(a.property=G)");

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry().trackServices(filter);

		serviceTracker.open();

		TestTrackServicesByFilter<InterfaceOne>
			trackerTest =
				new TestTrackServicesByFilter<InterfaceOne>() {

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClass(null);
				}

				@Override
				RegisteredTuple<InterfaceOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClass(properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	@Test
	public void testTrackServicesByFilterAndServiceTrackerCustomizer()
		throws Exception {

		Filter filter = registry().getFilter("(a.property=G)");

		final InterfaceOne instanceA = getInstance();
		final InterfaceOne instanceB = getInstance();
		final AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		final AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new TestServiceTrackerCustomizer(
					instanceA, instanceB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry().trackServices(filter, serviceTrackerCustomizer);

		serviceTracker.open();

		TestTrackServicesByFilter<TrackedOne>
			trackerTest =
				new TestTrackServicesByFilter<TrackedOne>() {

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleA() {
					return getInstanceRegisteredByClass(
						instanceA, referenceA, null);
				}

				@Override
				RegisteredTuple<TrackedOne> getRegisteredTupleB() {
					Map<String, Object> properties =
						new HashMap<String, Object>();

					properties.put("a.property", "G");

					return getInstanceRegisteredByClass(
						instanceB, referenceB, properties);
				}

			};

		trackerTest.execute(serviceTracker);
	}

	private void assertRegisteredInstance(
		RegisteredTuple<InterfaceOne> registeredTuple,
		InterfaceOne instance) {

		Assert.assertEquals(registeredTuple.getReference(), instance);

		registeredTuple.unregister();
	}

	protected InterfaceBoth getInstance() {
		return new InterfaceBoth() {};
	}

	protected <U> RegisteredTuple<U> getInstanceRegisteredByClass(
		Map<String, Object> properties) {

		InterfaceOne instance = getInstance();

		return getInstanceRegisteredByClass(instance, null, properties);
	}

	protected <U> RegisteredTuple<U> getInstanceRegisteredByClass(
		InterfaceOne instance, AtomicReference<U> reference,
		Map<String, Object> properties) {

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry().registerService(
				InterfaceOne.class, instance, properties);

		Assert.assertNotNull(serviceRegistration);

		return new RegisteredTuple<U>(instance, reference, serviceRegistration);
	}


	protected <U> RegisteredTuple<U> getInstanceRegisteredByClassName(
		Map<String, Object> properties) {

		InterfaceOne instance = getInstance();

		return getInstanceRegisteredByClassName(instance, null, properties);
	}

	protected <U> RegisteredTuple<U> getInstanceRegisteredByClassName(
		InterfaceOne instance, AtomicReference<U> reference,
		Map<String, Object> properties) {

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry().registerService(
				InterfaceOne.class.getName(), instance, properties);

		Assert.assertNotNull(serviceRegistration);

		return new RegisteredTuple<U>(instance, reference, serviceRegistration);
	}

	private Registry registry() {
		return RegistryUtil.getRegistry();
	}

	private BundleContext _bundleContext;

	private class RegisteredTuple<U> {

		public RegisteredTuple(
			InterfaceOne service, AtomicReference<U> reference,
			ServiceRegistration<InterfaceOne> serviceRegistration) {

			_service = service;
			_reference = reference;
			_serviceRegistration = serviceRegistration;
		}

		public U getReference() {
			if (_reference == null) {
				return (U)_service;
			}

			return _reference.get();
		}

		public ServiceReference<InterfaceOne> getServiceReference() {
			return _serviceRegistration.getServiceReference();
		}

		public void unregister() {
			_serviceRegistration.unregister();
		}

		AtomicReference<U> _reference;
		InterfaceOne _service;
		ServiceRegistration<InterfaceOne> _serviceRegistration;

	}

	private class TestServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<InterfaceOne, TrackedOne> {

		public TestServiceTrackerCustomizer(
			InterfaceOne instanceA, InterfaceOne instanceB,
			AtomicReference<TrackedOne> referenceA,
			AtomicReference<TrackedOne> referenceB) {

			_instanceA = instanceA;
			_instanceB = instanceB;
			_referenceA = referenceA;
			_referenceB = referenceB;
		}

		@Override
		public TrackedOne addingService(
			ServiceReference<InterfaceOne> serviceReference) {

			InterfaceOne service = RegistryUtil.getRegistry().getService(
				serviceReference);
			TrackedOne testWrapper = new TrackedOne();

			if (service == _instanceA) {
				_referenceA.set(testWrapper);
			}
			else if (service == _instanceB) {
				_referenceB.set(testWrapper);
			}

			return testWrapper;
		}

		@Override
		public void modifiedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {
		}

		private AtomicReference<TrackedOne> _referenceA;
		private AtomicReference<TrackedOne> _referenceB;
		private InterfaceOne _instanceA;
		private InterfaceOne _instanceB;

	}

	private abstract class TestTrackServicesByClassOrClassName<U> {

		abstract RegisteredTuple<U> getRegisteredTupleA();

		abstract RegisteredTuple<U> getRegisteredTupleB();

		void execute(
			ServiceTracker<InterfaceOne, U> serviceTracker) {

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			RegisteredTuple<U> registeredTupleA = getRegisteredTupleA();
			RegisteredTuple<U> registeredTupleB = getRegisteredTupleB();

			Assert.assertFalse(serviceTracker.isEmpty());
			Assert.assertEquals(
				registeredTupleA.getReference(), serviceTracker.getService());

			U registeredB = serviceTracker.getService(
				registeredTupleB.getServiceReference());

			Assert.assertEquals(registeredTupleB.getReference(), registeredB);

			ServiceReference<InterfaceOne>[] serviceReferences =
				serviceTracker.getServiceReferences();

			Assert.assertEquals(2, serviceReferences.length);

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(2, services.length);

			SortedMap<ServiceReference<InterfaceOne>, U>
				trackedServiceReferences =
					serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(2, trackedServiceReferences.size());
			Assert.assertEquals(
				registeredTupleA.getReference(),
				trackedServiceReferences.get(
					trackedServiceReferences.firstKey()));
			Assert.assertEquals(
				registeredTupleB.getReference(),
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			registeredTupleA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			registeredTupleB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(0, trackedServiceReferences.size());

			serviceTracker.close();
		}
	}

	private abstract class TestTrackServicesByFilter<U> {

		abstract RegisteredTuple<U> getRegisteredTupleA();

		abstract RegisteredTuple<U> getRegisteredTupleB();

		void execute(
			ServiceTracker<InterfaceOne, U> serviceTracker) {

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			RegisteredTuple<U> registeredTupleA = getRegisteredTupleA();
			RegisteredTuple<U> registeredTupleB = getRegisteredTupleB();

			Assert.assertFalse(serviceTracker.isEmpty());
			Assert.assertEquals(
				registeredTupleB.getReference(), serviceTracker.getService());

			U registeredB = serviceTracker.getService(
				registeredTupleB.getServiceReference());

			Assert.assertEquals(registeredTupleB.getReference(), registeredB);

			ServiceReference<InterfaceOne>[] serviceReferences =
				serviceTracker.getServiceReferences();

			Assert.assertEquals(1, serviceReferences.length);

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(1, services.length);

			SortedMap<ServiceReference<InterfaceOne>, U>
				trackedServiceReferences =
					serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(1, trackedServiceReferences.size());
			Assert.assertEquals(
				registeredTupleB.getReference(),
				trackedServiceReferences.get(
					trackedServiceReferences.firstKey()));
			Assert.assertEquals(
				registeredTupleB.getReference(),
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			registeredTupleA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			registeredTupleB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(0, trackedServiceReferences.size());

			serviceTracker.close();
		}
	}

}