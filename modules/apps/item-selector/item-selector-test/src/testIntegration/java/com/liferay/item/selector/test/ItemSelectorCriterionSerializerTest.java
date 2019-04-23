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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorCriterionSerializer;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ItemSelectorCriterionSerializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ItemSelectorCriterionSerializerTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testSerializationAndDeserializationWithProvidedSupportedReturnTypes() {
		TestItemSelectorView testItemSelectorView = new TestItemSelectorView();

		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration = registerItemSelectorView(
				testItemSelectorView, "test-view");

		ServiceRegistration<ItemSelectorViewReturnTypeProvider>
			itemSelectorViewReturnTypeProviderServiceRegistration =
				registerItemSelectorViewProvider(
					new TestItemSelectorViewReturnTypeProvider(), "test-view");

		List serviceRegistrations = new ArrayList<>();

		serviceRegistrations.add(itemSelectorViewServiceRegistration);
		serviceRegistrations.add(
			itemSelectorViewReturnTypeProviderServiceRegistration);

		try {
			ItemSelectorCriterion itemSelectorCriterion =
				new TestItemSelectorCriterion();

			itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				new TestItemSelectorReturnType());

			String serializedItemSelectorCriterionJSON =
				_itemSelectorCriterionSerializer.serialize(
					itemSelectorCriterion);

			ItemSelectorCriterion deserializedItemSelectorCriterion =
				_itemSelectorCriterionSerializer.deserialize(
					itemSelectorCriterion.getClass(),
					serializedItemSelectorCriterionJSON);

			List<ItemSelectorReturnType>
				deserializedDesiredItemSelectorReturnTypes =
					deserializedItemSelectorCriterion.
						getDesiredItemSelectorReturnTypes();

			Assert.assertEquals(
				deserializedDesiredItemSelectorReturnTypes.toString(), 1,
				deserializedDesiredItemSelectorReturnTypes.size());

			ItemSelectorReturnType deserializedItemSelectorReturnType =
				deserializedDesiredItemSelectorReturnTypes.get(0);

			Class<? extends ItemSelectorReturnType>
				deserializedItemSelectorReturnTypeClass =
					deserializedItemSelectorReturnType.getClass();

			Class<TestItemSelectorReturnType>
				expectedItemSelectorReturnTypeClass =
					TestItemSelectorReturnType.class;

			Assert.assertEquals(
				expectedItemSelectorReturnTypeClass.getName(),
				deserializedItemSelectorReturnTypeClass.getName());
		}
		finally {
			_unregister(serviceRegistrations);
		}
	}

	protected ServiceRegistration<ItemSelectorView> registerItemSelectorView(
		ItemSelectorView itemSelectorView, String itemSelectorViewKey) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("item.selector.view.key", itemSelectorViewKey);

		return _bundleContext.registerService(
			ItemSelectorView.class, itemSelectorView, properties);
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

	private void _unregister(List<ServiceRegistration> serviceRegistrations) {
		serviceRegistrations.forEach(ServiceRegistration::unregister);
	}

	private BundleContext _bundleContext;

	@Inject
	private ItemSelectorCriterionSerializer _itemSelectorCriterionSerializer;

}