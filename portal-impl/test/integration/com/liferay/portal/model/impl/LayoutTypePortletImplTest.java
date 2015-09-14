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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.util.test.PortletPreferencesTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class LayoutTypePortletImplTest {

	public static void setUpScenario() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group, false);

		_layoutTypePortlet = (LayoutTypePortlet)_layout.getLayoutType();

		_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;
	}

	public static void tearDownScenario() {
		StringBundler sb = new StringBundler(_layoutStaticPortletsAll.length);

		for (String layoutStaticPortlet : _layoutStaticPortletsAll) {
			sb.append(layoutStaticPortlet);
		}

		PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, sb.toString());
	}

	public static class
		IsCacheableWhenThereAreEmbeddedPortletsAndNoStaticPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			setUpScenario();
		}

		@Test
		public void
				shouldReturnFalseIfThereIsANonLayoutCacheableRootPortlet()
			throws Exception {

			Portlet nonCacheablePortlet =
				PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, nonCacheablePortlet);

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			Portlet cacheablePortlet = PortletLocalServiceUtil.getPortletById(
				cacheablePortletId);

			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, cacheablePortlet);

			Assert.assertFalse(_layoutTypePortlet.isCacheable());
		}

		@Test
		public void shouldReturnTrueIfAllRootPortletsAreLayoutCacheable()
			throws Exception {

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			Portlet cacheablePortlet = PortletLocalServiceUtil.getPortletById(
				cacheablePortletId);

			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, cacheablePortlet);

			Assert.assertTrue(_layoutTypePortlet.isCacheable());
		}

		@After
		public void tearDown() {
			tearDownScenario();
		}

	}

	public static class
		IsCacheableWhenThereAreNoStaticPortletsAndNoEmbeddedPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			setUpScenario();
		}

		@After
		public void tearDown() {
			tearDownScenario();
		}

		@Test
		public void
				shouldReturnFalseIfANonLayoutCacheableRootPortletIsInstalled()
			throws Exception {

			Portlet nonCacheablePortlet =
				PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layout,
				nonCacheablePortlet.getPortletId(), "column-1",
				new HashMap<String, String[]>());

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layout, cacheablePortletId,
				"column-1", new HashMap<String, String[]>());

			Assert.assertFalse(_layoutTypePortlet.isCacheable());
		}

		@Test
		public void shouldReturnTrueIfInstalledRootPortletsAreLayoutCacheable()
			throws Exception {

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layout, cacheablePortletId,
				"column-1", new HashMap<String, String[]>());

			Assert.assertTrue(_layoutTypePortlet.isCacheable());
		}
	}

	public static class
		IsCacheableWhenThereAreStaticPortletsAndNoEmbeddedPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			setUpScenario();
		}

		@After
		public void tearDown() {
			tearDownScenario();
		}

		@Test
		public void
				shouldReturnFalseIfThereIsANonLayoutCacheableRootPortlet()
			throws Exception {

			Portlet nonCacheablePortlet =
				PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			PropsUtil.set(
				PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
				nonCacheablePortlet.getPortletId() + "," + cacheablePortletId);

			Assert.assertFalse(_layoutTypePortlet.isCacheable());
		}

		@Test
		public void shouldReturnTrueIfAllRootPortletsAreLayoutCacheable()
			throws Exception {

			String cacheablePortletId = PortletProviderUtil.getPortletId(
				"com.liferay.journal.model.JournalArticle",
				PortletProvider.Action.ADD);

			PropsUtil.set(
				PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, cacheablePortletId);

			Assert.assertTrue(_layoutTypePortlet.isCacheable());
		}
	}

	@DeleteAfterTestRun
	private static Group _group;

	private static Layout _layout;
	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

}