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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class BaseUpgradePortletIdTest extends BaseUpgradePortletId {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		for (String portletId : _PORTLET_IDS) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				TestPropsValues.getCompanyId(), portletId);

			_portlets.add(portlet);

			PortletLocalServiceUtil.destroyPortlet(portlet);
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (Portlet portlet : _portlets) {
			if (!portlet.isUndeployedPortlet()) {
				PortletLocalServiceUtil.deployPortlet(portlet);
			}
		}

		_portlets.clear();
	}

	@After
	public void tearDown() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			String[][] renamePortletIdsArray = getRenamePortletIdsArray();

			for (String[] renamePortletIds : renamePortletIdsArray) {
				String oldRootPortletId = renamePortletIds[1];
				String newRootPortletId = renamePortletIds[0];

				updatePortlet(oldRootPortletId, newRootPortletId);
				updateLayoutRevisions(
					oldRootPortletId, newRootPortletId, false);
				updateLayouts(oldRootPortletId, newRootPortletId, false);
			}
		}
		finally {
			connection = null;
		}

		for (String portletId : _PORTLET_IDS) {
			runSQL(
				"delete from Portlet where portletId = '" + portletId +
					"_test'");

			runSQL(
				"delete from ResourceAction where name = '" + portletId +
					"_test'");

			runSQL(
				"delete from ResourcePermission where name = '" + portletId +
					"_test'");
		}
	}

	@Test
	public void testUpgradePortletId() throws Exception {
		doTestUpgrade();
	}

	@Test
	public void testUpgradeUninstanceablePortletId() throws Exception {
		_testInstanceable = false;

		try {
			doTestUpgrade();
		}
		finally {
			_testInstanceable = true;
		}
	}

	protected Layout addLayout() throws Exception {
		Group group = GroupTestUtil.addGroup();

		return LayoutTestUtil.addLayout(group, false);
	}

	protected void addPortletPreferences(Layout layout, String portletId)
		throws Exception {

		PortletPreferencesLocalServiceUtil.getPreferences(
			TestPropsValues.getCompanyId(), 0,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			PortletConstants.DEFAULT_PREFERENCES);
	}

	protected void doTestUpgrade() throws Exception {
		Layout layout = addLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		roleIdsToActionIds.put(
			role.getRoleId(), new String[] {ActionKeys.CONFIGURATION});

		Portlet portlet = null;

		for (String oldPortletId : _PORTLET_IDS) {
			String portletId = getPortletId(oldPortletId);

			portlet = PortletLocalServiceUtil.getPortletById(
				TestPropsValues.getCompanyId(), portletId);

			layoutTypePortlet.addPortletId(
				TestPropsValues.getUserId(), portletId, false);

			addPortletPreferences(layout, portletId);

			String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				TestPropsValues.getCompanyId(), oldPortletId,
				ResourceConstants.SCOPE_INDIVIDUAL, portletPrimaryKey,
				roleIdsToActionIds);

			PortletLocalServiceUtil.destroyPortlet(portlet);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		upgrade();

		CacheRegistryUtil.clear();

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		for (String portletId : _PORTLET_IDS) {
			String newRootPortletId = portletId;

			if (_testInstanceable) {
				newRootPortletId += "_test";
			}

			String newPortletId = getNewPortletId(
				layoutTypePortlet, newRootPortletId);

			portlet.setCompanyId(TestPropsValues.getCompanyId());
			portlet.setPortletId(newPortletId);

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(newRootPortletId);

			ResourceActionLocalServiceUtil.checkResourceActions(
				newRootPortletId, portletActions);

			PortletLocalServiceUtil.checkPortlet(portlet);

			Assert.assertTrue(layoutTypePortlet.hasPortletId(newPortletId));

			String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), newPortletId);

			boolean hasViewPermission =
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					TestPropsValues.getCompanyId(), newRootPortletId,
					ResourceConstants.SCOPE_INDIVIDUAL, portletPrimaryKey,
					role.getRoleId(), ActionKeys.VIEW);

			Assert.assertFalse(hasViewPermission);

			boolean hasConfigurationPermission =
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					TestPropsValues.getCompanyId(), newRootPortletId,
					ResourceConstants.SCOPE_INDIVIDUAL, portletPrimaryKey,
					role.getRoleId(), ActionKeys.CONFIGURATION);

			Assert.assertTrue(hasConfigurationPermission);
		}

		GroupLocalServiceUtil.deleteGroup(layout.getGroup());
	}

	protected String getNewPortletId(
		LayoutTypePortlet layoutTypePortlet, String oldPortletId) {

		List<String> portletIds = layoutTypePortlet.getPortletIds();

		for (String portletId : portletIds) {
			if (portletId.startsWith(oldPortletId)) {
				return portletId;
			}
		}

		return oldPortletId;
	}

	protected String getPortletId(String portletId) {
		if (_testInstanceable) {
			return portletId + _INSTANCE_ID;
		}

		return portletId;
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		if (_testInstanceable) {
			return new String[][] {
				new String[] {_PORTLET_IDS[0], _PORTLET_IDS[0] + "_test"},
				new String[] {_PORTLET_IDS[1], _PORTLET_IDS[1] + "_test"}
			};
		}

		return new String[0][0];
	}

	@Override
	protected String[] getUninstanceablePortletIds() {
		if (!_testInstanceable) {
			return _PORTLET_IDS;
		}

		return new String[0];
	}

	private static final String _INSTANCE_ID = "_INSTANCE_LhZwzy867qfr";

	private static final String[] _PORTLET_IDS =
		{"47", com.liferay.portlet.util.test.PortletKeys.TEST};

	private static final List<Portlet> _portlets = new ArrayList<>();

	private boolean _testInstanceable = true;

}