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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.util.test.PortletPreferencesTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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

	public static class
		IsCacheableWhenThereAreEmbeddedPortletsAndNoStaticPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFalseIfThereIsANonlayoutCacheableRootPortlet()
			throws Exception {

			Portlet noncacheablePortlet =
				PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				noncacheablePortlet.getPortletId(), noncacheablePortlet, null);

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(), _layout.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED,
				noncacheablePortlet.getPortletId(), noncacheablePortlet, null);

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

			String[] layoutStaticPortletsAll =
				PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

			PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, "");

			try {
				String cacheablePortletId = PortletProviderUtil.getPortletId(
					"com.liferay.journal.model.JournalArticle",
					PortletProvider.Action.ADD);

				Portlet cacheablePortlet =
					PortletLocalServiceUtil.getPortletById(cacheablePortletId);

				PortletPreferencesTestUtil.addLayoutPortletPreferences(
					_layout, cacheablePortlet);

				Assert.assertTrue(_layoutTypePortlet.isCacheable());
			}
			finally {
				PropsUtil.set(
					PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
					StringUtil.merge(
						layoutStaticPortletsAll, StringPool.COMMA));
			}
		}

		@After
		public void tearDown() {
			_tearDown();
		}

	}

	public static class
		IsCacheableWhenThereAreNoStaticPortletsAndNoEmbeddedPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void
				shouldReturnFalseIfANonlayoutCacheableRootPortletIsInstalled()
			throws Exception {

			Portlet noncacheablePortlet =
				PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), _layout,
				noncacheablePortlet.getPortletId(), "column-1",
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

			String[] layoutStaticPortletsAll =
				PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

			PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, "");

			try {
				String cacheablePortletId = PortletProviderUtil.getPortletId(
					"com.liferay.journal.model.JournalArticle",
					PortletProvider.Action.ADD);

				LayoutTestUtil.addPortletToLayout(
					TestPropsValues.getUserId(), _layout, cacheablePortletId,
					"column-1", new HashMap<String, String[]>());

				Assert.assertTrue(_layoutTypePortlet.isCacheable());
			}
			finally {
				PropsUtil.set(
					PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
					StringUtil.merge(
						layoutStaticPortletsAll, StringPool.COMMA));
			}
		}

		@After
		public void tearDown() {
			_tearDown();
		}

	}

	public static class
		IsCacheableWhenThereAreStaticPortletsAndNoEmbeddedPortlets {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFalseIfThereIsANonlayoutCacheableRootPortlet()
			throws Exception {

			String[] layoutStaticPortletsAll =
				PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

			PropsValues.LAYOUT_STATIC_PORTLETS_ALL = StringPool.EMPTY_ARRAY;

			try {
				Portlet noncacheablePortlet =
					PortletLocalServiceUtil.getPortletById(PortletKeys.LOGIN);

				String cacheablePortletId = PortletProviderUtil.getPortletId(
					"com.liferay.journal.model.JournalArticle",
					PortletProvider.Action.ADD);

				PropsUtil.set(
					PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
					noncacheablePortlet.getPortletId() + "," +
						cacheablePortletId);

				Assert.assertFalse(_layoutTypePortlet.isCacheable());
			}
			finally {
				PropsValues.LAYOUT_STATIC_PORTLETS_ALL =
					layoutStaticPortletsAll;
			}
		}

		@Test
		public void shouldReturnTrueIfAllRootPortletsAreLayoutCacheable()
			throws Exception {

			String[] layoutStaticPortletsAll =
				PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

			PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, "");

			try {
				String cacheablePortletId = PortletProviderUtil.getPortletId(
					"com.liferay.journal.model.JournalArticle",
					PortletProvider.Action.ADD);

				PropsUtil.set(
					PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, cacheablePortletId);

				Assert.assertTrue(_layoutTypePortlet.isCacheable());
			}
			finally {
				PropsUtil.set(
					PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
					StringUtil.merge(
						layoutStaticPortletsAll, StringPool.COMMA));
			}
		}

		@After
		public void tearDown() {
			_tearDown();
		}

	}

	private static void _setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group, false);

		_layoutTypePortlet = (LayoutTypePortlet)_layout.getLayoutType();

		_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;
	}

	private static void _tearDown() {
		StringBundler sb = new StringBundler(_layoutStaticPortletsAll.length);

		for (String layoutStaticPortlet : _layoutStaticPortletsAll) {
			sb.append(layoutStaticPortlet);
		}

		PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, sb.toString());
	}

	@DeleteAfterTestRun
	private static Group _group;

	private static Layout _layout;
	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

}