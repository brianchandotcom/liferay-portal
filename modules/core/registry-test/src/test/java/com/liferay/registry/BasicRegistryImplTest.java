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

package com.liferay.registry;

import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class BasicRegistryImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testEmptyServiceTracker() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<EventListener, Object> serviceTracker =
			registry.trackServices(EventListener.class);

		serviceTracker.open();

		Assert.assertNull(serviceTracker.getService());
	}

	@Test
	public void testServiceRanking() {
		Registry registry = RegistryUtil.getRegistry();

		Foo foo1 = new Foo();

		Map<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1);

		ServiceRegistration<Foo> serviceRegistration1 =
			registry.registerService(Foo.class, foo1, properties);

		ServiceReference<Foo> serviceReference = registry.getServiceReference(
			Foo.class);

		Assert.assertSame(foo1, registry.getService(serviceReference));

		Foo foo2 = new Foo();

		properties = new HashMap<>();

		properties.put("service.ranking", 2);

		ServiceRegistration<Foo> serviceRegistration2 =
			registry.registerService(Foo.class, foo2, properties);

		serviceReference = registry.getServiceReference(Foo.class);

		Assert.assertSame(foo2, registry.getService(serviceReference));

		serviceRegistration2.unregister();

		serviceReference = registry.getServiceReference(Foo.class);

		Assert.assertSame(foo1, registry.getService(serviceReference));

		serviceRegistration1.unregister();
	}

	@Test
	public void testServiceTrackerCount() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<Foo, Foo> serviceTracker = registry.trackServices(
			Foo.class);

		serviceTracker.open();

		Assert.assertEquals(0, serviceTracker.size());

		ServiceRegistration<Foo> serviceRegistrationA =
			registry.registerService(
				Foo.class,
				new Foo() {
				});

		Assert.assertEquals(1, serviceTracker.size());

		ServiceRegistration<Foo> serviceRegistrationB =
			registry.registerService(
				Foo.class,
				new Foo() {
				});

		Assert.assertEquals(2, serviceTracker.size());

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());
	}

	@Test
	public void testServiceTrackerCustomizerCalledOncePerEvent() {
		Registry registry = RegistryUtil.getRegistry();

		AtomicInteger addingState = new AtomicInteger(0);
		AtomicInteger midifiedState = new AtomicInteger(0);
		AtomicInteger removedState = new AtomicInteger(0);

		ServiceTracker<Foo, Foo> serviceTracker = registry.trackServices(
			Foo.class,
			new FooServiceTracker(addingState, midifiedState, removedState));

		serviceTracker.open();

		ServiceRegistration<Foo> serviceRegistration = registry.registerService(
			Foo.class,
			new Foo() {
			});

		Assert.assertEquals(1, addingState.get());

		serviceRegistration.setProperties(
			Collections.<String, Object>emptyMap());

		Assert.assertEquals(1, midifiedState.get());

		serviceRegistration.unregister();

		Assert.assertEquals(1, removedState.get());
	}

	public class Foo {
	}

	public class FooServiceTracker
		implements ServiceTrackerCustomizer<Foo, Foo> {

		public FooServiceTracker(
			AtomicInteger addingState, AtomicInteger modifiedState,
			AtomicInteger removedState) {

			_addingState = addingState;
			_modifiedState = modifiedState;
			_removedState = removedState;
		}

		@Override
		public Foo addingService(ServiceReference<Foo> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			_addingState.incrementAndGet();

			return registry.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<Foo> serviceReference, Foo service) {

			_modifiedState.incrementAndGet();
		}

		@Override
		public void removedService(
			ServiceReference<Foo> serviceReference, Foo service) {

			_removedState.incrementAndGet();
		}

		private final AtomicInteger _addingState;
		private final AtomicInteger _modifiedState;
		private final AtomicInteger _removedState;

	}

}