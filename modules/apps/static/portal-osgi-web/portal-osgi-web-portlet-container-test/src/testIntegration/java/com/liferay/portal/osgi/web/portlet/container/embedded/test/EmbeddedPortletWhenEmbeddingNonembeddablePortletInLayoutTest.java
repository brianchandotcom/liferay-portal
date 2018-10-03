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

package com.liferay.portal.osgi.web.portlet.container.embedded.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.osgi.web.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class EmbeddedPortletWhenEmbeddingNonembeddablePortletInLayoutTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

		_testNonembeddedPortlet = new TestNonembeddedPortlet();

		Dictionary<String, Object> properties = new Hashtable<>();

		setUpPortlet(
			_testNonembeddedPortlet, properties,
			_testNonembeddedPortlet.getPortletId(), false);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		StringBundler sb = new StringBundler(_layoutStaticPortletsAll.length);

		for (String portletId : _layoutStaticPortletsAll) {
			sb.append(portletId);
		}

		PropsUtil.set(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL, sb.toString());

		super.tearDown();
	}

	@Test
	public void testShouldNotReturnItFromAllPortlets() throws Exception {
		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testNonembeddedPortlet.getPortletId(), _testNonembeddedPortlet,
			null);

		List<Portlet> allPortlets = _layoutTypePortlet.getAllPortlets();

		Assert.assertFalse(
			allPortlets.toString(),
			allPortlets.contains(_testNonembeddedPortlet));
	}

	@Test
	public void testShouldNotReturnItFromEmbeddedPortlets() throws Exception {
		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testNonembeddedPortlet.getPortletId(), _testNonembeddedPortlet,
			null);

		List<Portlet> embeddedPortlets =
			_layoutTypePortlet.getEmbeddedPortlets();

		Assert.assertFalse(
			embeddedPortlets.toString(),
			embeddedPortlets.contains(_testNonembeddedPortlet));
	}

	@Test
	public void testShouldNotReturnItFromExplicitlyAddedPortlets()
		throws Exception {

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testNonembeddedPortlet.getPortletId(), _testNonembeddedPortlet,
			null);

		List<Portlet> explicitlyAddedPortlets =
			_layoutTypePortlet.getExplicitlyAddedPortlets();

		Assert.assertFalse(
			explicitlyAddedPortlets.toString(),
			explicitlyAddedPortlets.contains(_testNonembeddedPortlet));
	}

	@Test
	public void testShouldReturnItsConfiguration() throws Exception {
		String defaultPreferences = RandomTestUtil.randomString();

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testNonembeddedPortlet.getPortletId(), _testNonembeddedPortlet,
			defaultPreferences);

		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _testNonembeddedPortlet.getPortletId());

		Assert.assertEquals(
			portletPreferences.toString(), 1, portletPreferences.size());

		PortletPreferences embeddedPortletPreference = portletPreferences.get(
			0);

		Assert.assertEquals(
			defaultPreferences, embeddedPortletPreference.getPreferences());
	}

	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

	private TestNonembeddedPortlet _testNonembeddedPortlet;

}