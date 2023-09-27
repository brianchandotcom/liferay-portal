/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProviderHandler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ItemSelectorViewReturnTypeProviderHandlerTest {

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(
			ItemSelectorViewReturnTypeProviderHandlerTest.class);

		_bundleContext = _bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			ItemSelectorViewReturnTypeProviderHandler.class);

		_itemSelectorViewReturnTypeProviderHandler = _bundleContext.getService(
			_serviceReference);
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testItemSelectorViewReturnTypeProviderHandler() {
		TestItemSelectorView testItemSelectorView = new TestItemSelectorView();

		ServiceRegistration<ItemSelectorView<?>>
			itemSelectorViewServiceRegistration = registerItemSelectorView(
				testItemSelectorView, "test-view");

		ServiceRegistration<ItemSelectorViewReturnTypeProvider>
			itemSelectorViewReturnTypeProviderServiceRegistration =
				registerItemSelectorViewProvider(
					new TestItemSelectorViewReturnTypeProvider(), "test-view");

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		serviceRegistrations.add(itemSelectorViewServiceRegistration);
		serviceRegistrations.add(
			itemSelectorViewReturnTypeProviderServiceRegistration);

		try {
			List<ItemSelectorReturnType> itemSelectorReturnTypes =
				_itemSelectorViewReturnTypeProviderHandler.
					getSupportedItemSelectorReturnTypes(testItemSelectorView);

			Assert.assertEquals(
				itemSelectorReturnTypes.toString(), 1,
				itemSelectorReturnTypes.size());

			ItemSelectorReturnType itemSelectorReturnType =
				itemSelectorReturnTypes.get(0);

			Assert.assertTrue(
				itemSelectorReturnType instanceof TestItemSelectorReturnType);
		}
		finally {
			_unregister(serviceRegistrations);
		}
	}

	protected ServiceRegistration<ItemSelectorView<?>> registerItemSelectorView(
		ItemSelectorView<?> itemSelectorView, String itemSelectorViewKey) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("item.selector.view.key", itemSelectorViewKey);

		return _bundleContext.registerService(
			(Class<ItemSelectorView<?>>)(Class<?>)ItemSelectorView.class,
			itemSelectorView, properties);
	}

	protected ServiceRegistration<ItemSelectorViewReturnTypeProvider>
		registerItemSelectorViewProvider(
			ItemSelectorViewReturnTypeProvider
				itemSelectorViewReturnTypeProvider,
			String itemSelectorViewKey) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("item.selector.view.key", itemSelectorViewKey);

		return _bundleContext.registerService(
			ItemSelectorViewReturnTypeProvider.class,
			itemSelectorViewReturnTypeProvider, properties);
	}

	private void _unregister(
		List<ServiceRegistration<?>> serviceRegistrations) {

		serviceRegistrations.forEach(ServiceRegistration::unregister);
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private ItemSelectorViewReturnTypeProviderHandler
		_itemSelectorViewReturnTypeProviderHandler;
	private ServiceReference<ItemSelectorViewReturnTypeProviderHandler>
		_serviceReference;

}