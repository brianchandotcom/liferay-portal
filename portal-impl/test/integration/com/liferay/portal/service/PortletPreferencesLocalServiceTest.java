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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.util.PortletPreferencesTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.StrictPortletPreferencesImpl;

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
			TestPropsValues.getCompanyId(), String.valueOf(_MOCK_PORTLET_ID));
	}

	@Test
	public void testAddWithDefaultsAsParameter() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXml =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet, preferencesAsXml);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testAddWithDefaultsWithinPortletObject() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		_portlet.setDefaultPreferences(preferencesAsXML);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout
				(_layout, _portlet);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testAddWithMultipleValuedDefaults() throws Exception {
		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);

		assertNullLayoutPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet, preferencesAsXML);

		PortletPreferencesImpl portletPreferencesImpl =
			PortletPreferencesTestUtil.convert(portletPreferences);

		assertOwnedByLayout(_layout, portletPreferencesImpl);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);
	}

	@Test
	public void testAddWithoutDefaults() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
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
	public void testAddWithoutDefaultsAndWithoutPortletObject()
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

	@Test
	public void testDeleteAllByPlid() throws Exception {
		Portlet portlet2 = getTestPortlets(1)[0];

		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet);

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(
			_layout.getPlid());

		PortletPreferences currentPortletPreferences1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferences2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId());

		Assert.assertNull(
			"The portlet preferences for the portletPreferencesId " +
				portletPreferences1.getPortletPreferencesId() +
				" should be null after deleting the portlet preferences by " +
				"PLID " + _layout.getPlid(),
			currentPortletPreferences1);

		Assert.assertNull(
			"The portlet preferences for the portletPreferencesId " +
				portletPreferences2.getPortletPreferencesId() +
				" should be null after deleting the portlet preferences by " +
				"PLID " + _layout.getPlid(),
			currentPortletPreferences2);
	}

	@Test
	public void testDeleteByPlidAndOwner() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet group2Portlet = getTestPortlets(1)[0];

		PortletPreferences portletPreferencesGroup1 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
				_layout, _portlet);

		PortletPreferences portletPreferencesGroup2 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
				layout2, group2Portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_layout.getPlid());

		PortletPreferences currentPortletPreferences1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferences2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferencesGroup2.getPortletPreferencesId());

		Assert.assertNull(
			"After deleting the porlet preferences owned by the group: " +
				_group.getGroupId() + " the preferences for the portlet " +
				_portlet.getPortletId() + " and the group " +
				_group.getGroupId() + " should not be defined ",
			currentPortletPreferences1);

		Assert.assertNotNull(
			"After deleting the porlet preferences owned by the group: " +
				_group.getGroupId() + " the preferences for the portlet " +
				group2Portlet.getPortletId() +
				" should still be defined",
			currentPortletPreferences2);
	}

	@Test
	public void testDeleteByPortletId() throws Exception {
		Portlet portlet2 = getTestPortlets(1)[0];

		PortletPreferences portletPreferences1 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout
				(_layout, _portlet);

		PortletPreferences portletPreferences2 =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, portlet2);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId());

		PortletPreferences currentPortletPreferences1 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences1.getPortletPreferencesId());

		PortletPreferences currentPortletPreferences2 =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences2.getPortletPreferencesId());

		Assert.assertNull(
			"It has been posible to fetch the portletPreferencesId " +
				portletPreferences1.getPortletPreferencesId() +
				" after deleting it",
			currentPortletPreferences1);

		Assert.assertNotNull(
			"It has not been posible to fetch the portletPreferencesId " +
				portletPreferences2.getPortletPreferencesId(),
			currentPortletPreferences2);
	}

	@Test
	public void testDeleteByPortletPreferencesId() throws Exception {
		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet);

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			portletPreferences.getPortletPreferencesId());

		PortletPreferences portletPreferencesFetched =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertNull(
			"It has been posible to fetch the portletPreferencesId " +
				portletPreferences.getPortletPreferencesId() +
				" after deleting it",
			portletPreferencesFetched);
	}

	@Test
	public void testFetchInexistentPreferences() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

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

		Assert.assertNull(
			"The portlet preferences for (" + " companyId: " +
			TestPropsValues.getCompanyId() + " ownerId: " +
			PortletKeys.PREFS_OWNER_ID_DEFAULT + " ownerType: " +
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT + " PLID: " +
			_layout.getPlid() + " portletId: " + _portlet.getPortletId() +
			") should be null after deleting it ", portletPreferences);
	}

	@Test
	public void testFetchPreferences() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testFetchPreferencesByPortletPreferencesIds() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

		PortletPreferencesIds portletPreferencesIds =
			new PortletPreferencesIds(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPreferences(
				portletPreferencesIds);

		assertPortletPreferenceValues(
			portletPreferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);
	}

	@Test
	public void testGetAllPreferences() throws Exception {
		List<PortletPreferences> initialPortletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences();

		int initialCount = initialPortletPreferences.size();

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		List<PortletPreferences> currentPortletsPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences();

		Assert.assertEquals(
			"After a new portlet preference has been added, the total number "+
				"of portlet preferences should be increased in 1",
			initialCount + 1, currentPortletsPreferences.size());
	}

	@Test
	public void testGetByCompanyGroupOwnerIdAndPortlet() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(GroupTestUtil.addGroup());

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, _portlet);

		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getCompanyId(), _layout.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId(),
				false);

		Assert.assertEquals(
			"The number of portlet preferences fetched by (" +
				" companyId " + _layout.getCompanyId() +
				" groupId " + _layout.getGroupId() +
				" ownerId " + PortletKeys.PREFS_OWNER_ID_DEFAULT +
				" ownerType " + PortletKeys.PREFS_OWNER_TYPE_LAYOUT +
				" portletId " + _portlet.getPortletId() + ") should be 1",
			1, portletPreferences.size());

		Assert.assertEquals(
			"The portlet preferences fetched by (" +
				" companyId " + _layout.getCompanyId() +
				" groupId " + _layout.getGroupId() +
				" ownerId " + PortletKeys.PREFS_OWNER_ID_DEFAULT +
				" ownerType " + PortletKeys.PREFS_OWNER_TYPE_LAYOUT +
				" portletId " + _portlet.getPortletId() +
				") should have the PLID " + _layout.getPlid(),
			_layout.getPlid(), portletPreferences.get(0).getPlid());
	}

	@Test
	public void testGetByCompanyOwnerPortletTwoDifferentsLayoutSameGroup()
		throws Exception {

		Layout layout2 = LayoutTestUtil.addLayout(_group);

		PortletPreferences portalPreferencesLayout =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, _portlet);

		List<PortletPreferences> listPortletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getCompanyId(), _group.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId(),
				false);

		Assert.assertEquals(
			"2 portlet preferences shoud be returned by the search (" +
			" companyId " + _layout.getCompanyId() +
			" groupId " + _group.getGroupId() +
			" ownerId " + PortletKeys.PREFS_OWNER_ID_DEFAULT +
			" ownerType " + PortletKeys.PREFS_OWNER_TYPE_LAYOUT +
			" portletId " + _portlet.getPortletId() + ") ", 2,
			listPortletPreferences.size());

		for (PortletPreferences portletPreferences : listPortletPreferences) {
			if (portalPreferencesLayout.getPortletPreferencesId() ==
					portletPreferences.getPortletPreferencesId()) {

				Assert.assertEquals(
					"The PLID of the portlet preferences " +
						portletPreferences.getPortletPreferencesId() +
						" should be " + _layout.getPlid(),
					portletPreferences.getPlid(), _layout.getPlid());
			}
			else {
				Assert.assertEquals(
					"The PLID of the portlet preferences " +
						portletPreferences.getPortletPreferencesId() +
						" should be " + layout2.getPlid(),
					portletPreferences.getPlid(), layout2.getPlid());
			}
		}
	}

	@Test
	public void testGetByOwnerAndLayout() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet[] portletsGroup2 = getTestPortlets(2);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, portletsGroup2[0]);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, portletsGroup2[1]);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid());

		Assert.assertEquals(
			"The portlet preferences for the group " + _group.getGroupId() +
				" should be 1", 1, portletPreferencesList.size());

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				group2.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				layout2.getPlid());

		Assert.assertEquals(
			"The portlet preferences for the group " + group2.getGroupId() +
				" should be 2", 2, portletPreferencesList.size());
	}

	@Test
	public void testGetByPlid() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(_group);

		Portlet[] portlets = getTestPortlets(2);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, portlets[0]);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, portlets[1]);

		List<PortletPreferences> listPortletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByPlid(
				layout2.getPlid());

		Assert.assertEquals(
			"The number of portlet preferences for the PLID " +
				layout2.getPlid() + " should be 2", 2,
			listPortletPreferences.size());
	}

	@Test
	public void testGetByPortletAndLayout() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(_group);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, _portlet);

		List<PortletPreferences> listPortletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_layout.getPlid(), _portlet.getPortletId());

		Assert.assertEquals(
			"1 portlet preferences should be returned for the PLID " +
			_layout.getPlid() + " and the portletId " +
			_portlet.getPortletId(), 1, listPortletPreferences.size());

		Assert.assertEquals(
			"The search returned a PLID different than the expected: ",
			_layout.getPlid(), listPortletPreferences.get(0).getPlid());

		Assert.assertEquals(
			"The search returned a portletId different than the expected: ",
			_portlet.getPortletId(),
			listPortletPreferences.get(0).getPortletId());
	}

	@Test
	public void testGetByPortletOwnerTypeLayout() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, _portlet);

		List<PortletPreferences> listPortletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(
			"The search of the portlet preferences by type layout only " +
				"has to return one value",
			1, listPortletPreferences.size());

		PortletPreferencesImpl portletPreferenesImpl =
			PortletPreferencesTestUtil.convert(listPortletPreferences.get(0));

		assertOwnedByLayout(_layout, portletPreferenesImpl);
	}

	@Test
	public void testGetNotStrictPortletPreferences() throws Exception {
		MockStrictPortletPreferencesLocalServiceImpl mockService =
			getMockStrictPortletPreferencesService(false);

		javax.portlet.PortletPreferences portletPreferences =
			mockService.getStrictPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertOwnedByLayout(
			_layout, (PortletPreferencesImpl)portletPreferences);
	}

	@Test
	public void testGetPreferencesByOwnerLayoutPortlet() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet[] portletsGroup2 = getTestPortlets(2);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, portletsGroup2[0]);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, portletsGroup2[1]);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet.getPortletId());

		Assert.assertEquals(
			"The returned portletPreferences do not have the expected " +
				"portletId" + portletPreferences.getPortletId(),
			portletPreferences.getPortletId(), _portlet.getPortletId());

		assertPortletPreferencesOwnedByGroup(
			_layout, _group, portletPreferences);
	}

	@Test
	public void testGetPreferencesByOwnerLayoutPortletNotAdded()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesImpl portletPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesLocalServiceUtil.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
					_portlet.getPortletId(), preferencesAsXML);

		Assert.assertArrayEquals(
			"The values for the preference name: " + _PREFERENCE_NAME +
				" should be " + _PREFERENCE_VALUES_SINGLE,
			_PREFERENCE_VALUES_SINGLE,
			portletPreferences.getMap().get(_PREFERENCE_NAME));

		assertOwnedByLayout(_layout, portletPreferences);
	}

	@Test
	public void testGetPreferencesByOwnerLayoutPortletNotAddedWithoutDefault()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		PortletPreferencesImpl portletPreferences = (PortletPreferencesImpl )
			PortletPreferencesLocalServiceUtil.getPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertOwnedByLayout(_layout, portletPreferences);

		assertEmpty(portletPreferences);
	}

	@Test
	public void testGetPreferencesByPortletPreferencesIds() throws Exception {
		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

		PortletPreferencesIds portletPreferencesIds =
			new PortletPreferencesIds(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

		Assert.assertArrayEquals(
			_PREFERENCE_VALUES_SINGLE,
			portletPreferences.getMap().get(_PREFERENCE_NAME));
	}

	@Test
	public void testGetPreferencesCountByOwnerLayoutPortlet() throws Exception {
		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " + _group.getGroupId() +
					" and PLID: " + _layout.getPlid() +
						" ) should be 0", 0, initialCount);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " + _group.getGroupId() +
					" and PLID: " + _layout.getPlid() +
						" ) should be 1", 1, currentCount);
	}

	@Test
	public void testGetPreferencesCountByOwnerLayoutPortletNotDefault()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, true);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " +_group.getGroupId() +
					" and PLID: " + _layout.getPlid() +
						" ) should be 0",
			0, initialCount);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_layout.getPlid(), _portlet, true);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " +_group.getGroupId() +
					" and PLID: " + _layout.getPlid() +
						" ) should be 0",
			0, currentCount);
	}

	@Test
	public void testGetPreferencesCountByOwnerLayoutPortletWithoutLayout()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP, -1,
				_portlet, false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " +_group.getGroupId() +
				" and PLID: " + -1 +
				" ) should be 0",
			0, initialCount);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_GROUP, -1, _portlet.getPortletId(),
			_portlet, null);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP, -1,
				_portlet, false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " groupId: " +_group.getGroupId() +
					" and PLID: " + -1 + " ) should be 1",
			1, currentCount);
	}

	@Test
	public void testGetPreferencesCountByOwnerPortletExcludeDefault()
		throws Exception {

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), true);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " and groupId: " +
				_group.getGroupId() + " ) should be 0",
			0, initialCount);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), true);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " and groupId: " +
				_group.getGroupId() + " ) should be 1",
			0, currentCount);
	}

	@Test
	public void testGetPreferencesCountByOwnerPortletWithoutExcludeDefault()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		Portlet portletGroup2 = getTestPortlets(1)[0];

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " and groupId: " +
				_group.getGroupId() + " ) should be 0",
			0, initialCount);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, portletGroup2);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				_portlet.getPortletId(), false);

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " and groupId: " +
				_group.getGroupId() + " ) should be 1",
			1, currentCount);
	}

	@Test
	public void testGetPreferencesCountByPortletOwnerType() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(_group);

		long initialCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId());

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			layout2, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			_layout, _portlet);

		long currentCount =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portlet.getPortletId());

		Assert.assertEquals(
			"The search of the portlet preferences of the portlet " +
				_portlet.getPortletId() + " and the ownertype layout " +
				"should return 2 results", initialCount + 2, currentCount);
	}

	@Test
	public void testGetPreferencesCountyPortletOwnerTypeLayout()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		Layout layout2 = LayoutTestUtil.addLayout(group2);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByGroup(
			layout2, _portlet);

		long currentCountTypeLayout =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " PLID: " +
				_layout.getPlid() + " and OwnerType layout) should be 1",
			1, currentCountTypeLayout);

		long currentCountTypeGroup =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_GROUP, layout2.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
			_portlet.getPortletId() + " PLID: " +
			_layout.getPlid() + " and OwnerType group) should be 1", 1,
			currentCountTypeGroup);

		long currentCountTypeCompany =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, _layout.getPlid(),
				_portlet.getPortletId());

		Assert.assertEquals(
			"The count of the portlet preferences by (portletId: " +
				_portlet.getPortletId() + " PLID: " +
				_layout.getPlid() + " and OwnerType company) should be 0",
			0, currentCountTypeCompany);
	}

	@Test
	public void testGetStrictPreferences() throws Exception {
		MockStrictPortletPreferencesLocalServiceImpl mockService =
			getMockStrictPortletPreferencesService(true);

		javax.portlet.PortletPreferences portletPreferences =
			mockService.getStrictPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertStrictPortletPreferences(portletPreferences);
	}

	@Test
	public void testGetStrictPreferencesByPortletPreferencesIds()
		throws Exception {

		MockStrictPortletPreferencesLocalServiceImpl mockService =
			getMockStrictPortletPreferencesService(true);

		PortletPreferencesIds portletPreferencesIds =
			new PortletPreferencesIds(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		javax.portlet.PortletPreferences portletPreferences =
			mockService.getStrictPreferences(portletPreferencesIds);

		assertStrictPortletPreferences(portletPreferences);
	}

	@Test
	public void testGetStrictPreferencesNotDefault() throws Exception {
		MockStrictPortletPreferencesLocalServiceImpl mockService =
			getMockStrictPortletPreferencesService(true);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		javax.portlet.PortletPreferences portletPreferences =
			mockService.getStrictPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId(), preferencesAsXML);

		assertStrictPortletPreferences(portletPreferences);
	}

	@Test
	public void testGetWithAutoAdd() throws Exception {

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferences portletPreferences =
			PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
				_layout, _portlet, preferencesAsXML);

		javax.portlet.PortletPreferences initialPortletPreferences =
			PortletPreferencesTestUtil.convert(portletPreferences);

		PortletPreferencesImpl currentPortletPreferences =
			(PortletPreferencesImpl)
				PortletPreferencesLocalServiceUtil.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
					_portlet.getPortletId(), preferencesAsXML);

		Assert.assertArrayEquals(
			"The values for the preference " + _PREFERENCE_NAME +
				" should be " + _PREFERENCE_VALUES_SINGLE,
			initialPortletPreferences.getMap().get(_PREFERENCE_NAME),
			currentPortletPreferences.getMap().get(_PREFERENCE_NAME));

		assertOwnedByLayout(_layout, currentPortletPreferences);
	}

	@Test
	public void testUpdatePreferencesMultipleValues() throws Exception {
		assertNullLayoutPreferences(_layout, _portlet);

		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

		String multiplePreferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId(), multiplePreferencesAsXML);

		javax.portlet.PortletPreferences preferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertPortletPreferenceValues(
			preferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);
	}

	@Test
	public void testUpdatePreferencesMultipleValuesByPortletPreferencesImpl()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		String preferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_SINGLE);

		PortletPreferencesTestUtil.addPortletPreferencesOwnedByLayout(
			_layout, _portlet, preferencesAsXML);

		String multiplePreferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId(), multiplePreferencesAsXML);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId(), portletPreferencesImpl);

		javax.portlet.PortletPreferences preferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertPortletPreferenceValues(
			preferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);
	}

	@Test
	public void testUpdatePreferencesMultipleValuesNoPreviouslyAdded()
		throws Exception {

		assertNullLayoutPreferences(_layout, _portlet);

		String multiplePreferencesAsXML =
			PortletPreferencesTestUtil.getPreferencesAsXMLString(
				_PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			_portlet.getPortletId(), multiplePreferencesAsXML);

		javax.portlet.PortletPreferences preferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				TestPropsValues.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				_portlet.getPortletId());

		assertPortletPreferenceValues(
			preferences, _PREFERENCE_NAME, _PREFERENCE_VALUES_MULTIPLE);
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

	protected void assertPortletPreferencesOwnedByGroup(
		Layout layout, Group group, PortletPreferences portletPreferences) {

		Assert.assertEquals(
			"The portlet preferences PLID is not the same as the layout PLID, ",
			layout.getPlid(), portletPreferences.getPlid());

		Assert.assertEquals(
			"The portlet preferences owner type is not layout, ",
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			portletPreferences.getOwnerType());

		Assert.assertEquals(
			"The portlet preferences owner is not the default owner, ",
			group.getGroupId(), portletPreferences.getOwnerId());
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

	protected void assertStrictPortletPreferences(
		javax.portlet.PortletPreferences portletPreferences) {

		StrictPortletPreferencesImpl strictPortletPreferences =
			(StrictPortletPreferencesImpl)portletPreferences;

		Assert.assertEquals(
			"The PLID of a StrictPortletPreference always should be 0: ", 0,
			strictPortletPreferences.getPlid());

		Assert.assertEquals(
			"The OwnerType of a StrictPortletPreference always should be 0: ",
			0, strictPortletPreferences.getOwnerType());

		Assert.assertEquals(
			"The OwnerId of a StrictPortletPreference always should be 0: ", 0,
			strictPortletPreferences.getOwnerId());

		Assert.assertTrue(
			"The map of preferences of a StrictPortletPreference "+
				"always should be empty",
			strictPortletPreferences.getMap().isEmpty());
	}

	protected MockStrictPortletPreferencesLocalServiceImpl
		getMockStrictPortletPreferencesService(boolean strict) {

		MockStrictPortletPreferencesLocalServiceImpl mockservice = new
			MockStrictPortletPreferencesLocalServiceImpl(strict);

		mockservice.setPortletPreferencesPersistence(
			(PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(
				PortletPreferencesPersistence.class.getName()));

		mockservice.setPortletLocalService(
			(PortletLocalService)PortalBeanLocatorUtil.locate(
				PortletLocalService.class.getName()));

		mockservice.setPortletPreferencesLocalService(
			(PortletPreferencesLocalService)PortalBeanLocatorUtil.locate(
				PortletPreferencesLocalService.class.getName()));

		return mockservice;
	}

	protected Portlet[] getTestPortlets(int numberPortlets) throws Exception {
		Portlet[] results = new Portlet[numberPortlets];

		for (int i = 0; i < results.length; i++) {
			results[i] = PortletLocalServiceUtil.getPortletById(
				TestPropsValues.getCompanyId(),
				String.valueOf(_MOCK_PORTLET_ID + 1 + i));
		}

		return results;
	}

	private static final int _MOCK_PORTLET_ID = 1000;

	private static final String _PREFERENCE_NAME = "testPreferenceName";

	private static final String[] _PREFERENCE_VALUES_MULTIPLE =
		{"testPreferenceValue1", "testPreferenceValue2"};

	private static final String[] _PREFERENCE_VALUES_SINGLE =
		{"testPreferenceValue"};

	private Group _group;
	private Layout _layout;
	private Portlet _portlet;

	private class MockStrictPortletPreferencesLocalServiceImpl
		extends PortletPreferencesLocalServiceImpl {

		public MockStrictPortletPreferencesLocalServiceImpl(boolean strict) {
			this.strict = strict;
		}

		@Override
		public javax.portlet.PortletPreferences getStrictPreferences(
				long companyId, long ownerId, int ownerType, long plid,
				String portletId)
			throws SystemException {

			return getPreferences(
				companyId, ownerId, ownerType, plid, portletId, null, strict);
		}

		protected javax.portlet.PortletPreferences getStrictPreferences(
				long companyId, long ownerId, int ownerType, long plid,
				String portletId, String defaultPreferences)
			throws SystemException {

			return getPreferences(
				companyId, ownerId, ownerType, plid, portletId,
				defaultPreferences, strict);
		}

		private boolean strict;

	}

}