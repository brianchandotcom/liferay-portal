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

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.staging.StagingGroupHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class StagingGroupHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_addLocalStagingGroups();

		_addRemoteStagingGroups();

		_localLiveScopeGroup = _addScopeGroup(_localLiveGroup);
		_localStagingScopeGroup = _addScopeGroup(_localStagingGroup);
		_remoteLiveScopeGroup = _addScopeGroup(_remoteLiveGroup);
		_remoteStagingScopeGroup = _addScopeGroup(_remoteStagingGroup);

		_regularGroup = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		try {
			GroupLocalServiceUtil.deleteGroup(_localLiveGroup.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
		}

		try {
			GroupLocalServiceUtil.deleteGroup(_localStagingGroup.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
		}

		try {
			GroupLocalServiceUtil.deleteGroup(_regularGroup.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
		}

		try {
			GroupLocalServiceUtil.deleteGroup(_remoteLiveGroup.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
		}

		try {
			GroupLocalServiceUtil.deleteGroup(_remoteStagingGroup.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
		}
	}

	@Test
	public void testFetchLiveGroup() throws Exception {
		Assert.assertEquals(
			_localLiveGroup,
			_stagingGroupHelper.fetchLiveGroup(_localStagingGroup));
		Assert.assertEquals(
			_localLiveGroup,
			_stagingGroupHelper.fetchLiveGroup(_localStagingScopeGroup));

		Assert.assertNull(_stagingGroupHelper.fetchLiveGroup(_localLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLiveGroup(_localLiveScopeGroup));

		Assert.assertEquals(
			_remoteLiveGroup,
			_stagingGroupHelper.fetchLiveGroup(_remoteStagingGroup));
		Assert.assertEquals(
			_remoteLiveGroup,
			_stagingGroupHelper.fetchLiveGroup(_remoteStagingScopeGroup));

		Assert.assertNull(_stagingGroupHelper.fetchLiveGroup(_remoteLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLiveGroup(_remoteLiveScopeGroup));

		Assert.assertNull(_stagingGroupHelper.fetchLiveGroup(_regularGroup));
	}

	@Test
	public void testFetchLocalLiveGroup() throws Exception {
		Assert.assertEquals(
			_localLiveGroup,
			_stagingGroupHelper.fetchLocalLiveGroup(_localStagingGroup));
		Assert.assertEquals(
			_localLiveGroup,
			_stagingGroupHelper.fetchLocalLiveGroup(_localStagingScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_localLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_localLiveScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_remoteStagingGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_remoteStagingScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_remoteLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_remoteLiveScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalLiveGroup(_regularGroup));
	}

	@Test
	public void testFetchLocalStagingGroup() throws Exception {
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(_localStagingGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(
				_localStagingScopeGroup));

		Assert.assertEquals(
			_localStagingGroup,
			_stagingGroupHelper.fetchLocalStagingGroup(_localLiveGroup));
		Assert.assertEquals(
			_localStagingGroup,
			_stagingGroupHelper.fetchLocalStagingGroup(_localLiveScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(_remoteStagingGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(
				_remoteStagingScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(_remoteLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(_remoteLiveScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchLocalStagingGroup(_regularGroup));
	}

	@Test
	public void testFetchRemoteLiveGroup() throws Exception {
		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_localStagingGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_localStagingScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_localLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_localLiveScopeGroup));

		Assert.assertEquals(
			_remoteLiveGroup,
			_stagingGroupHelper.fetchRemoteLiveGroup(_remoteStagingGroup));
		Assert.assertEquals(
			_remoteLiveGroup,
			_stagingGroupHelper.fetchRemoteLiveGroup(_remoteStagingScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_remoteLiveGroup));
		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_remoteLiveScopeGroup));

		Assert.assertNull(
			_stagingGroupHelper.fetchRemoteLiveGroup(_regularGroup));
	}

	@Test
	public void testIsLiveGroup() {
		Assert.assertFalse(_stagingGroupHelper.isLiveGroup(_localStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLiveGroup(_localStagingScopeGroup));

		Assert.assertTrue(_stagingGroupHelper.isLiveGroup(_localLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLiveGroup(_localLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLiveGroup(_remoteStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLiveGroup(_remoteStagingScopeGroup));

		Assert.assertTrue(_stagingGroupHelper.isLiveGroup(_remoteLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLiveGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(_stagingGroupHelper.isLiveGroup(_regularGroup));
	}

	@Test
	public void testIsLocalLiveGroup() {
		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_localStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_localStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isLocalLiveGroup(_localLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLocalLiveGroup(_localLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_remoteStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_remoteStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_remoteLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalLiveGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(_stagingGroupHelper.isLocalLiveGroup(_regularGroup));
	}

	@Test
	public void testIsLocalStagingGroup() {
		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingGroup(_localStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingGroup(_localStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_localLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_localLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_remoteStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_remoteStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_remoteLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingGroup(_regularGroup));
	}

	@Test
	public void testIsLocalStagingOrLocalLiveGroup() {
		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_localStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_localStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_localLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_localLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_remoteStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_remoteStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_remoteLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(
				_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isLocalStagingOrLocalLiveGroup(_regularGroup));
	}

	@Test
	public void testIsRemoteLiveGroup() {
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_localStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_localStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_localLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_localLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_remoteStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_remoteStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isRemoteLiveGroup(_remoteLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isRemoteLiveGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteLiveGroup(_regularGroup));
	}

	@Test
	public void testIsRemoteStagingGroup() {
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_localStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_localStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_localLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_localLiveScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingGroup(_remoteStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingGroup(_remoteStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_remoteLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingGroup(_regularGroup));
	}

	@Test
	public void testIsRemoteStagingOrRemoteLiveGroup() {
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_localStagingGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_localStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_localLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_localLiveScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_remoteStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_remoteStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_remoteLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isRemoteStagingOrRemoteLiveGroup(
				_regularGroup));
	}

	@Test
	public void testIsStagedPortlet() {
		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_localStagingGroup, _PORTLET_ID_BOOKMARKS));
		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_localStagingScopeGroup, _PORTLET_ID_BOOKMARKS));

		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_remoteStagingGroup, _PORTLET_ID_BOOKMARKS));
		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_remoteStagingScopeGroup, _PORTLET_ID_BOOKMARKS));

		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_localLiveGroup, _PORTLET_ID_BOOKMARKS));
		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_localLiveScopeGroup, _PORTLET_ID_BOOKMARKS));

		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_remoteLiveGroup, _PORTLET_ID_BOOKMARKS));
		Assert.assertTrue(
			_stagingGroupHelper.isStagedPortlet(
				_remoteLiveScopeGroup, _PORTLET_ID_BOOKMARKS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_regularGroup, _PORTLET_ID_BOOKMARKS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_localStagingGroup, _PORTLET_ID_BLOGS));
		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_localStagingScopeGroup, _PORTLET_ID_BLOGS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_remoteStagingGroup, _PORTLET_ID_BLOGS));
		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_remoteStagingScopeGroup, _PORTLET_ID_BLOGS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_localLiveGroup, _PORTLET_ID_BLOGS));
		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_localLiveScopeGroup, _PORTLET_ID_BLOGS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_remoteLiveGroup, _PORTLET_ID_BLOGS));
		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_remoteLiveScopeGroup, _PORTLET_ID_BLOGS));

		Assert.assertFalse(
			_stagingGroupHelper.isStagedPortlet(
				_regularGroup, _PORTLET_ID_BLOGS));
	}

	@Test
	public void testIsStagingGroup() {
		Assert.assertTrue(
			_stagingGroupHelper.isStagingGroup(_localStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingGroup(_localStagingScopeGroup));

		Assert.assertFalse(_stagingGroupHelper.isStagingGroup(_localLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isStagingGroup(_localLiveScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isStagingGroup(_remoteStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingGroup(_remoteStagingScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isStagingGroup(_remoteLiveGroup));
		Assert.assertFalse(
			_stagingGroupHelper.isStagingGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(_stagingGroupHelper.isStagingGroup(_regularGroup));
	}

	@Test
	public void testIsStagingOrLiveGroup() {
		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_localStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_localStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_localLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_localLiveScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_remoteStagingGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_remoteStagingScopeGroup));

		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_remoteLiveGroup));
		Assert.assertTrue(
			_stagingGroupHelper.isStagingOrLiveGroup(_remoteLiveScopeGroup));

		Assert.assertFalse(
			_stagingGroupHelper.isStagingOrLiveGroup(_regularGroup));
	}

	private void _addLocalStagingGroups() throws Exception {
		_localLiveGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"staged--staged-portlet_" + _PORTLET_ID_BLOGS + "--", "false");
		serviceContext.setAttribute(
			"staged--staged-portlet_" + _PORTLET_ID_BOOKMARKS + "--", "true");

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _localLiveGroup, false, false,
			serviceContext);

		_localStagingGroup = GroupLocalServiceUtil.getStagingGroup(
			_localLiveGroup.getGroupId());

		Assert.assertTrue(_localStagingGroup != null);
	}

	private void _addRemoteStagingGroups() throws Exception {
		_remoteLiveGroup = GroupTestUtil.addGroup();
		_remoteStagingGroup = GroupTestUtil.addGroup();

		_setPortalProperty(
			"TUNNELING_SERVLET_SHARED_SECRET",
			"F0E1D2C3B4A5968778695A4B3C2D1E0F");

		_setPortalProperty("TUNNELING_SERVLET_SHARED_SECRET_HEX", true);

		int serverPort = PortalUtil.getPortalServerPort(false);
		String pathContext = PortalUtil.getPathContext();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"staged--staged-portlet_" + _PORTLET_ID_BLOGS + "--", "false");
		serviceContext.setAttribute(
			"staged--staged-portlet_" + _PORTLET_ID_BOOKMARKS + "--", "true");

		StagingLocalServiceUtil.enableRemoteStaging(
			TestPropsValues.getUserId(), _remoteStagingGroup, false, false,
			"localhost", serverPort, pathContext, false,
			_remoteLiveGroup.getGroupId(), serviceContext);

		GroupUtil.clearCache();

		_remoteLiveGroup = GroupLocalServiceUtil.getGroup(
			_remoteLiveGroup.getGroupId());
	}

	private Group _addScopeGroup(Group group) throws Exception {
		Layout layout = LayoutTestUtil.addLayout(group);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), String.valueOf(layout.getPlid()));

		return GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
	}

	private void _setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	private static final String _PORTLET_ID_BLOGS =
		"com_liferay_blogs_web_portlet_BlogsPortlet";

	private static final String _PORTLET_ID_BOOKMARKS =
		"com_liferay_bookmarks_web_portlet_BookmarksPortlet";

	private Group _localLiveGroup;
	private Group _localLiveScopeGroup;
	private Group _localStagingGroup;
	private Group _localStagingScopeGroup;
	private Group _regularGroup;
	private Group _remoteLiveGroup;
	private Group _remoteLiveScopeGroup;
	private Group _remoteStagingGroup;
	private Group _remoteStagingScopeGroup;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}