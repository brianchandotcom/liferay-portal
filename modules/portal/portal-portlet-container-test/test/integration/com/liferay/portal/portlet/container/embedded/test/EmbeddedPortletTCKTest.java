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

package com.liferay.portal.portlet.container.embedded.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * A portlet is considered explicitly added to a layout if all these
 * circumstances are satisfied: (See
 * LayoutTypePortletImpl.getExplicitlyAddedPortlets method)
 *
 * - the portlet has been added to any columns in the layout.
 *
 *
 * A portlet is considered embedded under these circumstances: (See
 * LayoutTypePortletImpl.getEmbeddedPortlets method)
 *
 * - a portlet preference exists for the portlet in an specific layout, being
 * its owner id 'default', and its owner type 'layout'.
 *
 * Once the first condition is satisfied, if any of the following premises is
 * true, then the portlet is NOT embedded.
 *
 * - the portlet does not exist in Liferay's database.
 * - the portlet is present in any column of the layout.
 * - the portlet is defined in the static portlets of the layout.
 * - the portlet is not ready.
 * - the portlet is undeployed.
 * - the portlet is not active.
 *
 *
 * A portlet is considered static if all these circumstances are satisfied: (See
 * LayoutTypePortletImpl.getStaticPortlets method)
 *
 * - the portlet has been added to the layout.static.portlets.all portal
 * property.
 * - any layout' column has defined the portletId as a non-static
 *
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class EmbeddedPortletTCKTest {

	@RunWith(Arquillian.class)
	public static class WhenEmbeddingEmbeddablePortletInLayout {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				TransactionalTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();

			_layout = LayoutTestUtil.addLayout(_group);

			_layoutTypePortlet = (LayoutTypePortlet) _layout.getLayoutType();

			_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;
		}

		@Test
		public void shouldNotReturnItFromExplicitlyAddedPortlets()
			throws Exception {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				PortletKeys.LOGIN);

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				portlet.getPortletId(), portlet, null);

			List<Portlet> explicitlyAddedPortlets =
				_layoutTypePortlet.getExplicitlyAddedPortlets();

			Assert.assertFalse(explicitlyAddedPortlets.contains(portlet));
		}

		@Test
		public void shouldReturnItFromAllPortlets() throws Exception {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				PortletKeys.LOGIN);

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				portlet.getPortletId(), portlet, null);

			List<Portlet> allPortlets = _layoutTypePortlet.getAllPortlets();

			Assert.assertTrue(allPortlets.contains(portlet));
		}

		@Test
		public void shouldReturnItFromEmbeddedPortlets() throws Exception {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				PortletKeys.LOGIN);

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				portlet.getPortletId(), portlet, null);

			List<Portlet> embeddedPortlets =
				_layoutTypePortlet.getEmbeddedPortlets();

			Assert.assertTrue(embeddedPortlets.contains(portlet));
		}

		@Test
		public void shouldReturnItsConfiguration() throws Exception {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				PortletKeys.LOGIN);

			String defaultPreferences = RandomTestUtil.randomString();

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				portlet.getPortletId(), portlet, defaultPreferences);

			List<PortletPreferences> portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					_layout.getPlid(), portlet.getPortletId());

			Assert.assertEquals(1, portletPreferences.size());

			PortletPreferences embeddedPortletPreference =
				portletPreferences.get(0);

			Assert.assertEquals(
				defaultPreferences, embeddedPortletPreference.getPreferences());
		}

		@After
		public void tearDown() {
			StringBundler sb = new StringBundler(
				_layoutStaticPortletsAll.length);

			for (String layoutStaticPortlet : _layoutStaticPortletsAll) {
				sb.append(layoutStaticPortlet);
			}

			PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, sb.toString());
		}

	}

	@RunWith(Arquillian.class)
	public static class WhenEmbeddingNonEmbeddablePortletInLayout
		extends BasePortletContainerTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				TransactionalTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			super.setUp();

			_layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

			_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

			_nonEmbeddedPortlet = new TestNonEmbeddedPortlet();

			Dictionary<String, Object> properties = new Hashtable<>();

			setUpPortlet(
				_nonEmbeddedPortlet, properties,
				_nonEmbeddedPortlet.getPortletId(), false);
		}

		@Test
		public void shouldNotReturnItFromAllPortlets() throws Exception {
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				_nonEmbeddedPortlet.getPortletId(), _nonEmbeddedPortlet, null);

			List<Portlet> allPortlets = _layoutTypePortlet.getAllPortlets();

			Assert.assertFalse(allPortlets.contains(_nonEmbeddedPortlet));
		}

		@Test
		public void shouldNotReturnItFromEmbeddedPortlets() throws Exception {
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				_nonEmbeddedPortlet.getPortletId(), _nonEmbeddedPortlet, null);

			List<Portlet> embeddedPortlets =
				_layoutTypePortlet.getEmbeddedPortlets();

			Assert.assertFalse(embeddedPortlets.contains(_nonEmbeddedPortlet));
		}

		@Test
		public void shouldNotReturnItFromExplicitlyAddedPortlets()
			throws Exception {

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				_nonEmbeddedPortlet.getPortletId(), _nonEmbeddedPortlet, null);

			List<Portlet> explicitlyAddedPortlets =
				_layoutTypePortlet.getExplicitlyAddedPortlets();

			Assert.assertFalse(
				explicitlyAddedPortlets.contains(_nonEmbeddedPortlet));
		}

		@Test
		public void shouldReturnItsConfiguration() throws Exception {
			String defaultPreferences = RandomTestUtil.randomString();

			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				_nonEmbeddedPortlet.getPortletId(), _nonEmbeddedPortlet,
				defaultPreferences);

			List<PortletPreferences> portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					layout.getPlid(), _nonEmbeddedPortlet.getPortletId());

			Assert.assertEquals(1, portletPreferences.size());

			PortletPreferences embeddedPortletPreference =
				portletPreferences.get(0);

			Assert.assertEquals(
				defaultPreferences, embeddedPortletPreference.getPreferences());
		}

		@After
		public void tearDown() throws Exception {
			StringBundler sb = new StringBundler(
				_layoutStaticPortletsAll.length);

			for (String layoutStaticPortlet : _layoutStaticPortletsAll) {
				sb.append(layoutStaticPortlet);
			}

			PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, sb.toString());

			super.tearDown();
		}

		private TestNonEmbeddedPortlet _nonEmbeddedPortlet;

	}

	@DeleteAfterTestRun
	private static Group _group;

	private static Layout _layout;
	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

}