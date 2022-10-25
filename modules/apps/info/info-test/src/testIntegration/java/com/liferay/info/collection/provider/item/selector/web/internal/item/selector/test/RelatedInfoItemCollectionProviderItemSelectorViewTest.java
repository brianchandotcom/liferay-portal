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

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class RelatedInfoItemCollectionProviderItemSelectorViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetAvailableRelatedInfoItemCollectionProviders() {
		RelatedInfoItemCollectionProvider testInfoItemCollectionProvider =
			new TestItemRelatedInfoItemCollectionProvider(true);

		ServiceRegistration<RelatedInfoItemCollectionProvider>
			serviceRegistration = _getServiceRegistration(
				testInfoItemCollectionProvider);

		try {
			List<RelatedInfoItemCollectionProvider<?, ?>>
				relatedInfoItemCollectionProviders = ReflectionTestUtil.invoke(
					_itemSelectorView, "_getRelatedInfoItemCollectionProviders",
					new Class<?>[] {
						RelatedInfoItemCollectionProviderItemSelectorCriterion.
							class
					},
					_getRelatedInfoItemCollectionProviderItemSelectorCriterion());

			Assert.assertEquals(
				relatedInfoItemCollectionProviders.toString(), 1,
				relatedInfoItemCollectionProviders.size());

			Assert.assertTrue(
				relatedInfoItemCollectionProviders.contains(
					testInfoItemCollectionProvider));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetNotAvailableRelatedInfoItemCollectionProviders() {
		RelatedInfoItemCollectionProvider testInfoItemCollectionProvider =
			new TestItemRelatedInfoItemCollectionProvider(false);

		ServiceRegistration<RelatedInfoItemCollectionProvider>
			serviceRegistration = _getServiceRegistration(
				testInfoItemCollectionProvider);

		try {
			List<RelatedInfoItemCollectionProvider<?, ?>>
				relatedInfoItemCollectionProviders = ReflectionTestUtil.invoke(
					_itemSelectorView, "_getRelatedInfoItemCollectionProviders",
					new Class<?>[] {
						RelatedInfoItemCollectionProviderItemSelectorCriterion.
							class
					},
					_getRelatedInfoItemCollectionProviderItemSelectorCriterion());

			Assert.assertEquals(
				relatedInfoItemCollectionProviders.toString(), 0,
				relatedInfoItemCollectionProviders.size());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private RelatedInfoItemCollectionProviderItemSelectorCriterion
		_getRelatedInfoItemCollectionProviderItemSelectorCriterion() {

		RelatedInfoItemCollectionProviderItemSelectorCriterion
			relatedInfoItemCollectionProviderItemSelectorCriterion =
				new RelatedInfoItemCollectionProviderItemSelectorCriterion();

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setSourceItemTypes(Arrays.asList(TestItem.class.getName()));

		return relatedInfoItemCollectionProviderItemSelectorCriterion;
	}

	private ServiceRegistration<RelatedInfoItemCollectionProvider>
		_getServiceRegistration(
			RelatedInfoItemCollectionProvider
				relatedInfoItemCollectionProvider) {

		Bundle bundle = FrameworkUtil.getBundle(
			RelatedInfoItemCollectionProviderItemSelectorViewTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			RelatedInfoItemCollectionProvider.class,
			relatedInfoItemCollectionProvider, null);
	}

	@Inject(
		filter = "component.name=com.liferay.info.collection.provider.item.selector.web.internal.item.selector.RelatedInfoItemCollectionProviderItemSelectorView"
	)
	private ItemSelectorView
		<RelatedInfoItemCollectionProviderItemSelectorCriterion>
			_itemSelectorView;

	private static class TestItem {
	}

	private static class TestItemRelatedInfoItemCollectionProvider
		implements RelatedInfoItemCollectionProvider<TestItem, TestItem> {

		public TestItemRelatedInfoItemCollectionProvider(boolean available) {
			_available = available;
		}

		@Override
		public InfoPage<TestItem> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return null;
		}

		@Override
		public String getLabel(Locale locale) {
			return null;
		}

		@Override
		public boolean isAvailable() {
			return _available;
		}

		private final boolean _available;

	}

}