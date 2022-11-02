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

package com.liferay.content.dashboard.web.internal.item.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionActionProviderRegistry;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
public class ContentDashboardItemVersionActionProviderRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testInvalidContentDashboardItemVersionActions()
		throws Exception {

		_withTestItemContentDashboardItemVersionActionProviderRegistered(
			() -> {
				List<ContentDashboardItemVersionActionProvider>
					contentDashboardItemVersionActionProviders =
						_contentDashboardItemVersionActionProviderRegistry.
							getContentDashboardItemVersionActionProviders(
								RandomTestUtil.randomString());

				Assert.assertNotNull(
					contentDashboardItemVersionActionProviders);
				Assert.assertEquals(
					contentDashboardItemVersionActionProviders.toString(), 0,
					contentDashboardItemVersionActionProviders.size());
			});
	}

	@Test
	public void testTestItemContentDashboardItemVersionActions()
		throws Exception {

		_withTestItemContentDashboardItemVersionActionProviderRegistered(
			() -> {
				List<ContentDashboardItemVersionActionProvider>
					contentDashboardItemVersionActionProviders =
						_contentDashboardItemVersionActionProviderRegistry.
							getContentDashboardItemVersionActionProviders(
								TestItem.class.getName());

				Assert.assertNotNull(
					contentDashboardItemVersionActionProviders);
				Assert.assertEquals(
					contentDashboardItemVersionActionProviders.toString(), 1,
					contentDashboardItemVersionActionProviders.size());

				ContentDashboardItemVersionActionProvider
					contentDashboardItemVersionActionProvider =
						contentDashboardItemVersionActionProviders.get(0);

				Assert.assertNotNull(contentDashboardItemVersionActionProvider);
			});
	}

	private void
			_withTestItemContentDashboardItemVersionActionProviderRegistered(
				UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ContentDashboardItemVersionActionProviderRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<ContentDashboardItemVersionActionProvider>
			serviceRegistration = bundleContext.registerService(
				ContentDashboardItemVersionActionProvider.class,
				new TestItemContentDashboardItemVersionActionProvider(), null);

		try {
			unsafeRunnable.run();
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Inject
	private ContentDashboardItemVersionActionProviderRegistry
		_contentDashboardItemVersionActionProviderRegistry;

	private static class TestContentDashboardItemVersionAction
		implements ContentDashboardItemVersionAction {

		@Override
		public String getIcon() {
			return null;
		}

		@Override
		public String getLabel(Locale locale) {
			return null;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String getURL() {
			return null;
		}

	}

	private static class TestItem {
	}

	private static class TestItemContentDashboardItemVersionActionProvider
		implements ContentDashboardItemVersionActionProvider<TestItem> {

		@Override
		public ContentDashboardItemVersionAction
			getContentDashboardItemVersionAction(
				TestItem testItem, HttpServletRequest httpServletRequest) {

			return new TestContentDashboardItemVersionAction();
		}

		@Override
		public boolean isShow(
			TestItem testItem, HttpServletRequest httpServletRequest) {

			return true;
		}

	}

}