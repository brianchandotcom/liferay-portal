/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.util.PortletPreferencesTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.PortletPreferencesImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletPreferencesLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		_portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID));
	}

	@Test
	public void testAddPortletPreferencesFromPortlet() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		_portlet.setDefaultPreferences(preferencesAsXML);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences
				(_layout, _portlet);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testAddPortletPreferencesWithDefaultPreferences()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXml =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet, preferencesAsXml);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testAddPortletPreferencesWithMultipleDefaultPreferences()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet, preferencesAsXML);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);
	}

	@Test
	public void testAddPortletPreferencesWithoutDefaultPreferences()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		javax.portlet.PortletPreferences fetchedPortletPreferences =
			PortletPreferencesTestUtil.fetchLayoutPreferences(
				_layout, _portlet);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertEmpty(portletPreferencesImpl);

		assertOwnedByLayout(
			_layout, (PortletPreferencesImpl)fetchedPortletPreferences);

		assertEmpty(fetchedPortletPreferences);
	}

	@Test
	public void testAddPortletPreferencesWithoutDefaultPreferencesAndPortlet()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId(), null, null);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertEmpty(portletPreferencesImpl);
	}

	protected void assertEmpty(
			javax.portlet.PortletPreferences portletPreferences)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)portletPreferences;

		Assert.assertTrue(
			"The portlet preferences defined by (ownerType: " +
				portletPreferencesImpl.getOwnerType() + " ownerId: " +
				portletPreferencesImpl.getOwnerId() + " and plid: " +
				portletPreferencesImpl.getPlid() +
				") has defined the preferences: "
				+portletPreferencesImpl.getMap().keySet() +
				" and was expected to be empty",
			portletPreferencesImpl.getMap().isEmpty());
	}

	protected void assertNullLayoutPreferences(Layout layout, Portlet portlet)
		throws Exception {

		PortletPreferencesImpl preferences =
			(PortletPreferencesImpl)PortletPreferencesTestUtil.
				fetchLayoutPreferences(layout, portlet);

		Assert.assertNull(
			"Any portlet preferences was expected to be defined for " +
				"(layoutId: " + layout.getPlid() + " and portletId: " +
				portlet.getPortletId() +
				")",
			preferences);
	}

	protected void assertOwnedByLayout(
		Layout layout, PortletPreferencesImpl portletPreferences) {

		Assert.assertEquals(
			"The portlet preferences PLID is not the same as the layout PLID, ",
			layout.getPlid(), portletPreferences.getPlid());

		Assert.assertEquals(
			"The portlet preferences owner type is not layout, ",
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferences.getOwnerType());

		Assert.assertEquals(
			"The portlet preferences owner is not the default owner, ",
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferences.getOwnerId());
	}

	protected void assertPortletPreferenceValues(
			javax.portlet.PortletPreferences portletPreferences,
			String preferenceName, String[] preferenceValues)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)portletPreferences;

		Map<String, String[]> portletPreferencesMap =
			portletPreferencesImpl.getMap();

		Assert.assertFalse(
			"There are not portlet preferences defined for "+
				"(ownerType: " + portletPreferencesImpl.getOwnerType() +
				" ownerId: " + portletPreferencesImpl.getOwnerId() + " and " +
				"plid: " + portletPreferencesImpl.getPlid() +
				")",portletPreferencesMap.isEmpty());

		Assert.assertArrayEquals(
			"The value of the portlet preference " +
				preferenceName + " defined for (ownerType: " +
				portletPreferencesImpl.getOwnerType() + " ownerId: " +
				portletPreferencesImpl.getOwnerId() + " and plid: " +
				portletPreferencesImpl.getPlid() +
				") is not the expected", preferenceValues,
			portletPreferencesMap.get(preferenceName));
	}

	protected void assertPortletPreferenceValues(
			PortletPreferences portletPreferences, String preferenceName,
			String[] preferenceValues)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertPortletPreferenceValues(
			portletPreferencesImpl, preferenceName, preferenceValues);
	}

	private static final int _PORTLET_ID = 1000;

	private static final String _PREFERENCE_NAME = "testPreferenceName";

	private static final String[] _PREFERENCE_VALUES_MULTIPLE =
		{"testPreferenceValue1", "testPreferenceValue2"};

	private static final String[] _PREFERENCE_VALUES_SINGLE =
		{"testPreferenceValue"};

	private Group _group;
	private Layout _layout;
	private Portlet _portlet;

}