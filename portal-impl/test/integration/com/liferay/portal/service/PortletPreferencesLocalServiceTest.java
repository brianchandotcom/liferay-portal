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
import com.liferay.portal.model.PortletPreferencesIds;
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
	public void testAddPortletPreferencesWithDefaultMultipleXML()
		throws Exception {

		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _MULTIPLE_VALUES);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet, portletPreferencesXML);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertOwner(_layout, portletPreferencesImpl);
		assertValues(portletPreferences, _NAME, _MULTIPLE_VALUES);
	}

	@Test
	public void testAddPortletPreferencesWithDefaultNullXML() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet, null);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertOwner(_layout, portletPreferencesImpl);
		assertEmptyPortletPreferencesMap(portletPreferencesImpl);

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesTestUtil.fetchLayoutJxPortletPreferences(
				_layout, _portlet);

		assertOwner(_layout, (PortletPreferencesImpl)jxPortletPreferences);
		assertEmptyPortletPreferencesMap(jxPortletPreferences);
	}

	@Test
	public void testAddPortletPreferencesWithDefaultNullXMLAndNullPortlet()
		throws Exception {

		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId(), null, null);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertOwner(_layout, portletPreferencesImpl);
		assertEmptyPortletPreferencesMap(portletPreferencesImpl);
	}

	@Test
	public void testAddPortletPreferencesWithDefaultSingleXML()
		throws Exception {

		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet, portletPreferencesXML);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertOwner(_layout, portletPreferencesImpl);
		assertValues(portletPreferences, _NAME, _SINGLE_VALUE);
	}

	@Test
	public void testAddPortletPreferencesWithPortlet() throws Exception {

		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		_portlet.setDefaultPreferences(portletPreferencesXML);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertOwner(_layout, portletPreferencesImpl);
		assertValues(portletPreferences, _NAME, _SINGLE_VALUE);
	}

	@Test
	public void testDeletePortletPreferencesByPlid() throws Exception {
		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(
			_layout.getPlid());

		PortletPreferences currentPortletPreferences1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferences2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferences1);

		Assert.assertNull(currentPortletPreferences2);
	}

	@Test
	public void testDeletePortletPreferencesByPlidAndGroupOwner()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferencesGroup1 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				_layout, _portlet);

		PortletPreferences portletPreferencesGroup2 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				layout2, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_layout.getPlid());

		PortletPreferences currentPortletPreferencesGroup1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferencesGroup2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup2.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferencesGroup1);

		Assert.assertNotNull(currentPortletPreferencesGroup2);
	}

	@Test
	public void testDeletePortletPreferencesByPlidAndGroupOwnerAndPortletId()
		throws Exception {

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		PortletPreferences portletPreferencesGroup1 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				_layout, _portlet);

		PortletPreferences portletPreferencesGroup2 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				layout2, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_layout.getPlid(), _portlet.getPortletId());

		PortletPreferences currentPortletPreferencesLayout1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferencesLayout2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup2.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferencesLayout1);

		Assert.assertNotNull(currentPortletPreferencesLayout2);
	}

	@Test
	public void testDeletePortletPreferencesByPlidAndLayoutOwner()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferencesLayout1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferences portletPreferencesLayout2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				layout2, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid());

		PortletPreferences currentPortletPreferencesLayout1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesLayout1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferencesLayout2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesLayout2.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferencesLayout1);

		Assert.assertNotNull(currentPortletPreferencesLayout2);
	}

	@Test
	public void testDeletePortletPreferencesByPlidAndLayoutOwnerAndPortletId()
		throws Exception {

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferencesLayout1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferences portletPreferencesLayout2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId());

		PortletPreferences currentPortletPreferencesLayout1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesLayout1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferencesLayout2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesLayout2.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferencesLayout1);

		Assert.assertNotNull(currentPortletPreferencesLayout2);
	}

	@Test
	public void testDeletePortletPreferencesByPortletPreferencesId()
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			portletPreferences.getPortletPreferencesId());

		PortletPreferences currentPortletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertNull(currentPortletPreferences);
	}

	@Test
	public void testFetchNonExistentPreferences() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId());

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertNull(portletPreferences);
	}

	@Test
	public void testFetchPreferences() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertValues(portletPreferences, _NAME, _SINGLE_VALUE);
	}

	@Test
	public void testFetchPreferencesByPortletPreferencesIds() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		PortletPreferencesIds portletPreferencesIds =
			new PortletPreferencesIds(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				portletPreferencesIds);

		assertValues(portletPreferences, _NAME, _SINGLE_VALUE);
	}

	protected void assertEmptyPortletPreferencesMap(
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)jxPortletPreferences;

		Map<String, String[]> portletPreferencesMap =
			portletPreferencesImpl.getMap();

		Assert.assertTrue(portletPreferencesMap.isEmpty());
	}

	protected void assertNullLayoutJxPortletPreferences(
			Layout layout, Portlet portlet)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesTestUtil.
				fetchLayoutJxPortletPreferences(layout, portlet);

		Assert.assertNull(portletPreferencesImpl);
	}

	protected void assertOwner(
		Layout layout, PortletPreferencesImpl portletPreferencesImpl) {

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl.getOwnerType());
		Assert.assertEquals(layout.getPlid(), portletPreferencesImpl.getPlid());
	}

	protected void assertValues(
			javax.portlet.PortletPreferences jxPortletPreferences, String name,
			String[] values)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)jxPortletPreferences;

		Map<String, String[]> portletPreferencesMap =
			portletPreferencesImpl.getMap();

		Assert.assertFalse(portletPreferencesMap.isEmpty());
		Assert.assertArrayEquals(values, portletPreferencesMap.get(name));
	}

	protected void assertValues(
			PortletPreferences portletPreferences, String name, String[] values)
		throws Exception {

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences);

		assertValues(portletPreferencesImpl, name, values);
	}

	private static final String[] _MULTIPLE_VALUES = {"value1", "value2"};

	private static final String _NAME = "name";

	private static final int _PORTLET_ID = 1000;

	private static final String[] _SINGLE_VALUE = {"value"};

	private Group _group;
	private Layout _layout;
	private Portlet _portlet;

}