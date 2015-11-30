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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

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
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunWith(Arquillian.class)
public class ServiceTrackerListTest {

	@Before
	public void setUp() throws BundleException {
		bundle.start();

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() throws Exception {
		bundle.stop();

		if (_serviceTrackerList != null) {
			_serviceTrackerList.close();

			_serviceTrackerList = null;
		}
	}

	@Test
	public void testGetServiceWithCustomComparator() throws Exception {
		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(
				_bundleContext, Object.class,
				new Comparator<ServiceReference<Object>>() {

					@Override
					public int compare(
						ServiceReference<Object> serviceReference1,
						ServiceReference<Object> serviceReference2) {

						return 0;
					}

				});

		Object[] services = {new Object(), new Object()};

		Collection<ServiceRegistration<Object>> serviceRegistrations =
			registerServices(Object.class, services);

		Assert.assertEquals(2, serviceTrackerList.size());

		unregister(serviceRegistrations);
	}

	@Test
	public void testGetServiceWithServiceTrackerCustomizer() throws Exception {
		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(
				_bundleContext, Object.class, null,
				new ServiceTrackerCustomizer<Object, Object>() {

					@Override
					public Object addingService(
						ServiceReference<Object> reference) {

						return new CustomizedService();
					}

					@Override
					public void modifiedService(
						ServiceReference<Object> reference, Object service) {
					}

					@Override
					public void removedService(
						ServiceReference<Object> reference, Object service) {
					}

				});

		ServiceRegistration<Object> serviceRegistration = registerService(
			Object.class, new Object());

		for (Object service : serviceTrackerList) {
			Assert.assertTrue(service instanceof CustomizedService);
		}

		serviceRegistration.unregister();
	}

	@Test
	public void testServiceInsertion() throws Exception {
		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(_bundleContext, Object.class);

		Assert.assertEquals(0, serviceTrackerList.size());

		ServiceRegistration<Object> serviceRegistration = registerService(
			Object.class, new Object());

		Assert.assertEquals(1, serviceTrackerList.size());

		serviceRegistration.unregister();
	}

	@Test
	public void testServiceIterationOrderWithCustomComparator()
		throws Exception {

		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(
				_bundleContext, Object.class,
				new Comparator<ServiceReference<Object>>() {

					@Override
					public int compare(
						ServiceReference<Object> serviceReference1,
						ServiceReference<Object> serviceReference2) {

						int serviceRanking1 =
							(Integer)serviceReference1.getProperty(
								"service.ranking");
						int serviceRanking2 =
							(Integer)serviceReference2.getProperty(
								"service.ranking");

						return serviceRanking1 - serviceRanking2;
					}

				});

		Object[] services = {new Object(), new Object()};

		Collection<ServiceRegistration<Object>> serviceRegistrations =
			registerServices(Object.class, services, "service.ranking");

		int i = 0;

		for (Object service : serviceTrackerList) {
			Assert.assertSame(services[i], service);

			i++;
		}

		unregister(serviceRegistrations);
	}

	@Test
	public void testServiceIterationOrderWithDefaultComparator()
		throws Exception {

		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(_bundleContext, Object.class);

		Object[] services = {new Object(), new Object()};

		Collection<ServiceRegistration<Object>> serviceRegistrations =
			registerServices(Object.class, services, "service.ranking");

		int i = 0;

		for (Object service : serviceTrackerList) {
			Assert.assertSame(services[services.length - 1 - i], service);

			i++;
		}

		unregister(serviceRegistrations);
	}

	@Test
	public void testServiceRemoval() throws Exception {
		ServiceTrackerList<Object, Object> serviceTrackerList =
			ServiceTrackerListFactory.open(_bundleContext, Object.class);

		Assert.assertEquals(0, serviceTrackerList.size());

		ServiceRegistration<Object> serviceRegistration = registerService(
			Object.class, new Object());

		serviceRegistration.unregister();

		Assert.assertEquals(0, serviceTrackerList.size());
	}

	@ArquillianResource
	public Bundle bundle;

	public static class CustomizedService {
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