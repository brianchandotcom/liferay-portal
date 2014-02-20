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

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	public void testDeleteGroupPortletPreferencesByPlid() throws Exception {
		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				layout, portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_layout.getPlid());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId()));
		Assert.assertNotNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId()));
	}

	@Test
	public void testDeleteGroupPortletPreferencesByPlidAndPortletId()
		throws Exception {

		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addGroupPortletPreferences(
				layout, portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_layout.getPlid(), _portlet.getPortletId());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId()));
		Assert.assertNotNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId()));
	}

	@Test
	public void testDeleteLayoutPortletPreferencesByPlid() throws Exception {
		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				layout, portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId()));

		Assert.assertNotNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId()));
	}

	@Test
	public void testDeleteLayoutPortletPreferencesByPlidAndPortletId()
		throws Exception {

		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId()));
		Assert.assertNotNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId()));
	}

	@Test
	public void testDeletePortletPreferencesByPlid() throws Exception {
		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(
			_layout.getPlid());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId()));
		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId()));
	}

	@Test
	public void testDeletePortletPreferencesByPortletPreferencesId()
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addLayoutPortletPreferences(
				_layout, _portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			portletPreferences.getPortletPreferencesId());

		Assert.assertNull(
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId()));
	}

	@Test
	public void testFetchNonexistentPreferences() throws Exception {
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

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertNull(jxPortletPreferences);
	}

	@Test
	public void testFetchPreferences() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertValues(jxPortletPreferences, _NAME, _SINGLE_VALUE);
	}

	@Test
	public void testFetchPreferencesByPortletPreferencesIds() throws Exception {
		assertNullLayoutJxPortletPreferences(_layout, _portlet);

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(
				_NAME, _SINGLE_VALUE);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		PortletPreferencesIds portletPreferencesIds = new PortletPreferencesIds(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId());

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				portletPreferencesIds);

		assertValues(jxPortletPreferences, _NAME, _SINGLE_VALUE);
	}

	@Test
	public void testGetAllPortletPreferences() throws Exception {
		List<PortletPreferences> initialPortletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences();

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		List<PortletPreferences> currentPortletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences();

		Assert.assertEquals(
			initialPortletPreferencesList.size() + 1,
			currentPortletPreferencesList.size());
	}

	@Test
	public void
			testGetGroupPortletPreferencesByCompanyIdAndGroupIdAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(GroupTestUtil.addGroup());

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getCompanyId(), _layout.getGroupId(),
				_layout.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), false);

		Assert.assertEquals(1, portletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		Assert.assertEquals(_layout.getPlid(), portletPreferences.getPlid());
	}

	@Test
	public void testGetGroupPortletPreferencesByOwnerAndPlid()
		throws Exception {

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Portlet portlet1 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, portlet1);

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 2));

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, portlet2);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid());

		Assert.assertEquals(1, portletPreferencesList.size());

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				layout.getPlid());

		Assert.assertEquals(2, portletPreferencesList.size());
	}

	@Test
	public void testGetGroupPortletPreferencesByOwnerAndPlidAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(GroupTestUtil.addGroup());

		Portlet portlet1 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, portlet1);

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 2));

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, portlet2);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet.getPortletId());

		Assert.assertEquals(
			portletPreferences.getPortletId(), _portlet.getPortletId());

		assertOwner(
			_group,
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferences));
	}

	@Test
	public void testGetGroupPortletPreferencesByPlidAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_TYPE_GROUP, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(1, portletPreferencesList.size());

		PortletPreferencesImpl portletPreferenesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferencesList.get(0));

		assertOwner(_layout.getGroup(), portletPreferenesImpl);
	}

	@Test
	public void testGetGroupPortletPreferencesCountByOwnerAndNotPlidAndPortlet()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP, -1,
				_portlet, false);

		Assert.assertEquals(0, initialCount);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_GROUP, -1, _portlet.getPortletId(),
			_portlet, null);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP, -1,
				_portlet, false);

		Assert.assertEquals(1, actualCount);
	}

	@Test
	public void testGetGroupPortletPreferencesCountByOwnerAndPlidAndPortlet()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, false);

		Assert.assertEquals(0, initialCount);

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, false);

		Assert.assertEquals(1, actualCount);
	}

	@Test
	public void
			testGetGroupPortletPreferencesCountByOwnerAndPlidAndPortletExcludeDefault()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, true);

		Assert.assertEquals(0, initialCount);

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, true);

		Assert.assertEquals(0, actualCount);
	}

	@Test
	public void testGetGroupPortletPreferencesCountByOwnerAndPortletId()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), false);

		Assert.assertEquals(0, initialCount);

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(GroupTestUtil.addGroup());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, portlet);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), false);

		Assert.assertEquals(1, actualCount);
	}

	@Test
	public void
			testGetGroupPortletPreferencesCountByOwnerAndPortletIdExcludeDefault()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), true);

		Assert.assertEquals(0, initialCount);

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), true);

		Assert.assertEquals(0, actualCount);
	}

	@Test
	public void
			testGetLayoutPortletPreferencesByCompanyIdAndGroupIdAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(_layout.getGroup());

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getCompanyId(), _layout.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId(),
				false);

		Assert.assertEquals(2, portletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		Assert.assertEquals(
			_portlet.getPortletId(), portletPreferences.getPortletId());
	}

	@Test
	public void testGetLayoutPortletPreferencesByPlidAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(1, portletPreferencesList.size());

		PortletPreferencesImpl portletPreferenesImpl =
			PortletPreferencesTestUtil.toPortletPreferencesImpl(
				portletPreferencesList.get(0));

		assertOwner(_layout, portletPreferenesImpl);
	}

	@Test
	public void testGetLayoutPortletPreferencesCountByPlidAndPortletId()
		throws Exception {

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(GroupTestUtil.addGroup());

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, _portlet);

		long actualCountTypeLayout =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(1, actualCountTypeLayout);

		long actualCountTypeGroup =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_GROUP, layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(1, actualCountTypeGroup);

		long actualCountTypeCompany =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(0, actualCountTypeCompany);
	}

	@Test
	public void testGetLayoutPortletPreferencesCountByPortletId()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId());

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(_group);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, _portlet);

		PortletPreferencesTestUtil.addGroupPortletPreferences(
			_layout, _portlet);

		long actualCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId());

		Assert.assertEquals(initialCount + 2, actualCount);
	}

	@Test
	public void testGetLayoutPrivatePortletPreferences() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			GroupTestUtil.addGroup().getGroupId(),
			ServiceTestUtil.randomString(), true);

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getCompanyId(), layout.getGroupId(), layout.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, _portlet.getPortletId(),
				true);

		Assert.assertEquals(1, portletPreferencesList.size());
	}

	@Test
	public void testGetNotLayoutPrivatePortletPreferences() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			GroupTestUtil.addGroup().getGroupId(),
			ServiceTestUtil.randomString(), false);

		PortletPreferencesTestUtil.addGroupPortletPreferences(layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getCompanyId(), layout.getGroupId(), layout.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, _portlet.getPortletId(),
				false);

		Assert.assertEquals(1, portletPreferencesList.size());
	}

	@Test
	public void testGetPortletPreferencesByPlid() throws Exception {
		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(_group);

		Portlet portlet1 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 1));

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, portlet1);

		Portlet portlet2 = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), String.valueOf(_PORTLET_ID + 2));

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, portlet2);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByPlid(
				layout.getPlid());

		Assert.assertEquals(2, portletPreferencesList.size());
	}

	@Test
	public void testGetPortletPreferencesByPlidAndPortletId() throws Exception {
		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet);

		Layout layout = LayoutTestUtil.addLayout(_group);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			layout, _portlet);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getPlid(), _portlet.getPortletId());

		Assert.assertEquals(1, portletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		Assert.assertEquals(_layout.getPlid(), portletPreferences.getPlid());
		Assert.assertEquals(
			_portlet.getPortletId(), portletPreferences.getPortletId());
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
		Group group, PortletPreferencesImpl portletPreferencesImpl) {

		Assert.assertEquals(
			group.getGroupId(), portletPreferencesImpl.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			portletPreferencesImpl.getOwnerType());
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