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

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ItemSelectorReturnTypeResolverHandlerTest {

	@Before
	public void setUp() throws Exception {
		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			ItemSelectorReturnTypeResolverHandler.class);

		_itemSelectorReturnTypeResolverHandler = _bundleContext.getService(
			_serviceReference);
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testItemSelectorReturnTypeResolverIsReplacedByServiceRanking() {
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration1 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver1(), 100);
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration2 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver2(), 200);
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration3 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver3(), 50);

		List<ServiceRegistration> serviceRegistrations =
			new CopyOnWriteArrayList<>();

		serviceRegistrations.add(returnTypeResolverServiceRegistration1);
		serviceRegistrations.add(returnTypeResolverServiceRegistration2);
		serviceRegistrations.add(returnTypeResolverServiceRegistration3);

		try {
			ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
				_itemSelectorReturnTypeResolverHandler.
					getItemSelectorReturnTypeResolver(
						TestItemSelectorReturnType.class, String.class);

			Assert.assertTrue(
				itemSelectorReturnTypeResolver instanceof
					TestItemSelectorReturnTypeResolver2);

			serviceRegistrations.remove(returnTypeResolverServiceRegistration2);

			returnTypeResolverServiceRegistration2.unregister();

			itemSelectorReturnTypeResolver =
				_itemSelectorReturnTypeResolverHandler.
					getItemSelectorReturnTypeResolver(
						TestItemSelectorReturnType.class, String.class);

			Assert.assertTrue(
				itemSelectorReturnTypeResolver instanceof
					TestItemSelectorReturnTypeResolver1);

			serviceRegistrations.remove(returnTypeResolverServiceRegistration1);

			returnTypeResolverServiceRegistration1.unregister();

			itemSelectorReturnTypeResolver =
				_itemSelectorReturnTypeResolverHandler.
					getItemSelectorReturnTypeResolver(
						TestItemSelectorReturnType.class, String.class);

			Assert.assertTrue(
				itemSelectorReturnTypeResolver instanceof
					TestItemSelectorReturnTypeResolver3);
		}
		finally {
			_unregister(serviceRegistrations);
		}
	}

	@Test
	public void testItemSelectorReturnTypeResolverIsReturnedByServiceRanking() {
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration1 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver1(), 100);
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration2 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver2(), 200);
		ServiceRegistration<ItemSelectorReturnTypeResolver>
			returnTypeResolverServiceRegistration3 = registerReturnTypeResolver(
				new TestItemSelectorReturnTypeResolver3(), 50);

		List<ServiceRegistration> serviceRegistrations =
			new CopyOnWriteArrayList<>();

		serviceRegistrations.add(returnTypeResolverServiceRegistration1);
		serviceRegistrations.add(returnTypeResolverServiceRegistration2);
		serviceRegistrations.add(returnTypeResolverServiceRegistration3);

		try {
			ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
				_itemSelectorReturnTypeResolverHandler.
					getItemSelectorReturnTypeResolver(
						TestItemSelectorReturnType.class, String.class);

			Assert.assertTrue(
				itemSelectorReturnTypeResolver instanceof
					TestItemSelectorReturnTypeResolver2);
		}
		finally {
			_unregister(serviceRegistrations);
		}
	}

	@ArquillianResource
	public Bundle bundle;

	protected ServiceRegistration<ItemSelectorReturnTypeResolver>
		registerReturnTypeResolver(
			ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver,
			int serviceRanking) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("service.ranking", serviceRanking);

		return _bundleContext.registerService(
			ItemSelectorReturnTypeResolver.class,
			itemSelectorReturnTypeResolver, properties);
	}

	private void _unregister(List<ServiceRegistration> serviceRegistrations) {
		for (ServiceRegistration serviceRegistration : serviceRegistrations) {
			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private ServiceReference<ItemSelectorReturnTypeResolverHandler>
		_serviceReference;

	private abstract class BaseTestItemSelectorReturnTypeResolver
		implements ItemSelectorReturnTypeResolver
			<TestItemSelectorReturnType, String> {

		public Class<TestItemSelectorReturnType>
			getItemSelectorReturnTypeClass() {

			return TestItemSelectorReturnType.class;
		}

		public Class<String> getModelClass() {
			return String.class;
		}

	}

	private class TestItemSelectorReturnType implements ItemSelectorReturnType {
	}

	private class TestItemSelectorReturnTypeResolver1
		extends BaseTestItemSelectorReturnTypeResolver {

		public String getValue(String s, ThemeDisplay themeDisplay)
			throws Exception {

			return "Value 1";
		}

	}

	private class TestItemSelectorReturnTypeResolver2
		extends BaseTestItemSelectorReturnTypeResolver {

		public String getValue(String s, ThemeDisplay themeDisplay)
			throws Exception {

			return "Value 2";
		}

	}

	private class TestItemSelectorReturnTypeResolver3
		extends BaseTestItemSelectorReturnTypeResolver {

		public String getValue(String s, ThemeDisplay themeDisplay)
			throws Exception {

			return "Value 3";
		}

	}

}