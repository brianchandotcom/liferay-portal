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

package com.liferay.osgi.service.tracker.collections.list.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.test.TrackedOne;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

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
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class ServiceTrackerListTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(ServiceTrackerListTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		if (_serviceTrackerList != null) {
			_serviceTrackerList.close();

			_serviceTrackerList = null;
		}
	}

	@Test
	public void testGetServiceWithCustomComparator() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class,
					new Comparator<ServiceReference<TrackedOne>>() {

						@Override
						public int compare(
							ServiceReference<TrackedOne> serviceReference1,
							ServiceReference<TrackedOne> serviceReference2) {

							return 0;
						}

					})) {

			TrackedOne[] services = {new TrackedOne(), new TrackedOne()};

			Collection<ServiceRegistration<TrackedOne>> serviceRegistrations =
				registerServices(TrackedOne.class, services);

			Assert.assertEquals(
				serviceTrackerList.toString(), 2, serviceTrackerList.size());

			unregister(serviceRegistrations);
		}
	}

	@Test
	public void testGetServiceWithServiceTrackerCustomizer() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class, null,
					new ServiceTrackerCustomizer<TrackedOne, TrackedOne>() {

						@Override
						public TrackedOne addingService(
							ServiceReference<TrackedOne> reference) {

							return new CustomizedService();
						}

						@Override
						public void modifiedService(
							ServiceReference<TrackedOne> reference,
							TrackedOne service) {
						}

						@Override
						public void removedService(
							ServiceReference<TrackedOne> reference,
							TrackedOne service) {
						}

					})) {

			ServiceRegistration<TrackedOne> serviceRegistration =
				registerService(TrackedOne.class, new TrackedOne());

			for (TrackedOne service : serviceTrackerList) {
				Assert.assertTrue(service instanceof CustomizedService);
			}

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testServiceInsertion() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class)) {

			Assert.assertEquals(
				serviceTrackerList.toString(), 0, serviceTrackerList.size());

			ServiceRegistration<TrackedOne> serviceRegistration =
				registerService(TrackedOne.class, new TrackedOne());

			Assert.assertEquals(
				serviceTrackerList.toString(), 1, serviceTrackerList.size());

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testServiceIterationOrderWithCustomComparator() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class,
					new Comparator<ServiceReference<TrackedOne>>() {

						@Override
						public int compare(
							ServiceReference<TrackedOne> serviceReference1,
							ServiceReference<TrackedOne> serviceReference2) {

							int serviceRanking1 =
								(Integer)serviceReference1.getProperty(
									"service.ranking");
							int serviceRanking2 =
								(Integer)serviceReference2.getProperty(
									"service.ranking");

							return serviceRanking1 - serviceRanking2;
						}

					})) {

			TrackedOne[] services = {new TrackedOne(), new TrackedOne()};

			Collection<ServiceRegistration<TrackedOne>> serviceRegistrations =
				registerServices(TrackedOne.class, services, "service.ranking");

			int i = 0;

			for (TrackedOne service : serviceTrackerList) {
				Assert.assertSame(services[i], service);

				i++;
			}

			unregister(serviceRegistrations);
		}
	}

	@Test
	public void testServiceIterationOrderWithDefaultComparator() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class)) {

			TrackedOne[] services = {new TrackedOne(), new TrackedOne()};

			Collection<ServiceRegistration<TrackedOne>> serviceRegistrations =
				registerServices(TrackedOne.class, services, "service.ranking");

			int i = 0;

			for (TrackedOne service : serviceTrackerList) {
				Assert.assertSame(services[services.length - 1 - i], service);

				i++;
			}

			unregister(serviceRegistrations);
		}
	}

	@Test
	public void testServiceRemoval() {
		try (ServiceTrackerList<TrackedOne, TrackedOne> serviceTrackerList =
				ServiceTrackerListFactory.open(
					_bundleContext, TrackedOne.class)) {

			Assert.assertEquals(
				serviceTrackerList.toString(), 0, serviceTrackerList.size());

			ServiceRegistration<TrackedOne> serviceRegistration =
				registerService(TrackedOne.class, new TrackedOne());

			serviceRegistration.unregister();

			Assert.assertEquals(
				serviceTrackerList.toString(), 0, serviceTrackerList.size());
		}
	}

	public static class CustomizedService extends TrackedOne {
	}

	protected <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		Hashtable<String, Object> properties = new Hashtable<>();

		return registerService(clazz, service, properties);
	}

	protected <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Dictionary<String, Object> properties) {

		return _bundleContext.registerService(clazz, service, properties);
	}

	protected <T> Collection<ServiceRegistration<T>> registerServices(
		Class<T> clazz, T[] services) {

		return registerServices(clazz, services, null);
	}

	protected <T> Collection<ServiceRegistration<T>> registerServices(
		Class<T> clazz, T[] services, String property) {

		Collection<ServiceRegistration<T>> serviceRegistrations =
			new ArrayList<>();

		for (int i = 0; i < services.length; i++) {
			Dictionary<String, Object> properties = new Hashtable<>();

			if (property != null) {
				properties.put(property, i + 1);
			}

			serviceRegistrations.add(
				registerService(clazz, services[i], properties));
		}

		return serviceRegistrations;
	}

	protected <T> void unregister(
		Collection<ServiceRegistration<T>> serviceRegistrations) {

		for (ServiceRegistration<T> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;
	private ServiceTrackerList<Object, Object> _serviceTrackerList;

}