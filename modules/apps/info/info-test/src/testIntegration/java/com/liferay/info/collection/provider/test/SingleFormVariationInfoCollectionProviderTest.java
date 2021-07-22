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

package com.liferay.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.pagination.InfoPage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
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
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SingleFormVariationInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSingleFormVariationInfoCollectionProvider() {
		Bundle bundle = FrameworkUtil.getBundle(
			SingleFormVariationInfoCollectionProviderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<InfoCollectionProvider<?>> serviceRegistration =
			bundleContext.registerService(
				(Class<InfoCollectionProvider<?>>)
					(Class<?>)InfoCollectionProvider.class,
				new TestSingleFormVariationInfoCollectionProvider(), null);

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoCollectionProvider.class, Object.class.getName());

		Assert.assertNotNull(infoCollectionProvider);
		Assert.assertTrue(
			infoCollectionProvider instanceof
				SingleFormVariationInfoCollectionProvider);

		SingleFormVariationInfoCollectionProvider<?>
			singleFormVariationInfoCollectionProvider =
				(SingleFormVariationInfoCollectionProvider<?>)
					infoCollectionProvider;

		Assert.assertEquals(
			singleFormVariationInfoCollectionProvider.getFormVariationKey(),
			String.class.getName());

		serviceRegistration.unregister();
	}

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private static class TestSingleFormVariationInfoCollectionProvider
		implements SingleFormVariationInfoCollectionProvider<Object> {

		@Override
		public InfoPage<Object> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return InfoPage.of(
				Collections.singletonList(
					TestSingleFormVariationInfoCollectionProvider.class.
						getName()));
		}

		@Override
		public String getFormVariationKey() {
			return String.class.getName();
		}

		@Override
		public String getLabel(Locale locale) {
			return StringPool.BLANK;
		}

	}

}