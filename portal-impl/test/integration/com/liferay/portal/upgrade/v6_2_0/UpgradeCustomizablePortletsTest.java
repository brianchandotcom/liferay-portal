/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import jodd.util.ArraysUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UpgradeCustomizablePortletsTest
	extends UpgradeCustomizablePortlets {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testBasicPreferencesExtraction() throws Exception {
		Layout layout = getLayout();

		long ownerId = 1234;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;

		String preferences = getPortalPreferencesString();

		preferences = StringUtil.replace(
			preferences,
			new String[] {
				"@plid@", "@portlet_1@", "@portlet_2@", "@portlet_3@",
				"@portlet_4@"
			},
			ArraysUtil.join(
				new String[] {String.valueOf(layout.getPlid())}, _PORTLET_IDS));

		PortalPreferencesWrapper portalPreferencesWrapper =
			getPortalPreferences(ownerId, ownerType, preferences);

		upgradeCustomizablePreferences(
			portalPreferencesWrapper, ownerId, ownerType, preferences);

		Assert.assertEquals(_newPortletIds.size(), 4);
		Assert.assertFalse(PortletConstants.hasUserId(_newPortletIds.get(0)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(1)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(2)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(3)));
		Assert.assertFalse(
			PortletConstants.hasInstanceId(_newPortletIds.get(0)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(1)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(2)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(3)));
	}

	@Test
	public void testUpgrade() throws Exception {
		_invokeSuper = true;

		Layout layout = getLayout();

		addPortletPreferences(layout, "23");
		addPortletPreferences(layout, "71_INSTANCE_LhZwzy867qfr");
		addPortletPreferences(layout, "56_INSTANCE_LhZwzy867qqb");
		addPortletPreferences(layout, "56_INSTANCE_LhZwzy867qxc");

		long ownerId = 1234;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		String preferences = getPortalPreferencesString();

		preferences = StringUtil.replace(
			preferences,
			new String[] {
				"@plid@", "@portlet_1@", "@portlet_2@", "@portlet_3@",
				"@portlet_4@"
			},
			ArraysUtil.join(
				new String[] {String.valueOf(layout.getPlid())}, _PORTLET_IDS));

		PortalPreferencesWrapper portalPreferencesWrapper =
			getPortalPreferences(ownerId, ownerType, preferences);

		portalPreferencesWrapper.store();

		doUpgrade();

		Assert.assertEquals(_newPortletIds.size(), 4);
		Assert.assertFalse(PortletConstants.hasUserId(_newPortletIds.get(0)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(1)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(2)));
		Assert.assertTrue(PortletConstants.hasUserId(_newPortletIds.get(3)));
		Assert.assertFalse(
			PortletConstants.hasInstanceId(_newPortletIds.get(0)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(1)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(2)));
		Assert.assertTrue(
			PortletConstants.hasInstanceId(_newPortletIds.get(3)));

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _newPortletIds.get(0));

		Assert.assertEquals(portletPreferencesList.size(), 1);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _newPortletIds.get(1));

		Assert.assertEquals(portletPreferencesList.size(), 1);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _PORTLET_IDS[1]);

		Assert.assertEquals(portletPreferencesList.size(), 0);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _newPortletIds.get(2));

		Assert.assertEquals(portletPreferencesList.size(), 1);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _PORTLET_IDS[2]);

		Assert.assertEquals(portletPreferencesList.size(), 0);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _newPortletIds.get(3));

		Assert.assertEquals(portletPreferencesList.size(), 1);

		portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				layout.getPlid(), _PORTLET_IDS[3]);

		Assert.assertEquals(portletPreferencesList.size(), 0);

		GroupLocalServiceUtil.deleteGroup(layout.getGroup());
	}

	protected void addPortletPreferences(Layout layout, String portletId)
		throws Exception {

		PortletPreferencesLocalServiceUtil.getPreferences(
			TestPropsValues.getCompanyId(), 0,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			PortletConstants.DEFAULT_PREFERENCES);
	}

	protected Layout getLayout() throws Exception {
		Group group = ServiceTestUtil.addGroup();

		return ServiceTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString(), false);
	}

	protected PortalPreferencesWrapper getPortalPreferences(
			long ownerId, int ownerType, String preferences)
		throws Exception {

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				0, ownerId, ownerType, preferences);

		return new PortalPreferencesWrapper(portalPreferencesImpl);
	}

	protected String getPortalPreferencesString() throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/portal-preferences.xml");

		return StringUtil.read(inputStream);
	}

	@Override
	protected String migratePortletPreferencesToUserPreferences(
			long userId, long plid, String portletId)
		throws Exception {

		String newPortletId = portletId;

		if (_invokeSuper) {
			newPortletId = super.migratePortletPreferencesToUserPreferences(
				userId, plid, portletId);

			_newPortletIds.add(newPortletId);

			return newPortletId;
		}

		if (!PortletConstants.hasInstanceId(portletId)) {
			_newPortletIds.add(portletId);

			return portletId;
		}

		String instanceId = PortletConstants.getInstanceId(portletId);

		newPortletId = PortletConstants.assemblePortletId(
			portletId, userId, instanceId);

		_newPortletIds.add(newPortletId);

		return newPortletId;
	}

	private static final String[] _PORTLET_IDS = new String[] {
		"23", "71_INSTANCE_LhZwzy867qfr", "56_INSTANCE_LhZwzy867qqb",
		"56_INSTANCE_LhZwzy867qxc"
	};

	private boolean _invokeSuper;
	private List<String> _newPortletIds = new UniqueList<String>();

}