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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.upgrade.util.UpgradePortletId;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class UpgradePortletIdTest extends UpgradePortletId {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testUpgradePortletId() throws Exception {
		doTestUpgrade();
	}

	@Test
	public void testUpgradeUninstanceablePortletId() throws Exception {
		_testUninstanceable = true;

		doTestUpgrade();
	}

	protected Layout addLayout() throws Exception {
		Group group = GroupTestUtil.addGroup();

		return LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString(), false);
	}

	protected void addPortletPreferences(Layout layout, String portletId)
		throws Exception {

		PortletPreferencesLocalServiceUtil.getPreferences(
			TestPropsValues.getCompanyId(), 0,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			PortletConstants.DEFAULT_PREFERENCES);
	}

	protected void doTestUpgrade() throws Exception {
		String oldPortletId = StringPool.BLANK;
		String oldRootPortletId = StringPool.BLANK;

		if (_testUninstanceable) {
			oldPortletId = _UNINSTANCEABLE_PORTLET_ID;
			oldRootPortletId = _UNINSTANCEABLE_PORTLET_ID;
		}
		else {
			oldPortletId = _OLD_PORTLET_ID;
			oldRootPortletId = _OLD_ROOT_PORTLET_ID;
		}

		Layout layout = addLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(), oldPortletId);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		addPortletPreferences(layout, oldPortletId);

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		roleIdsToActionIds.put(
			role.getRoleId(), new String[] {ActionKeys.CONFIGURATION});

		ResourcePermissionServiceUtil.setIndividualResourcePermissions(
			layout.getGroupId(), TestPropsValues.getCompanyId(),
			oldRootPortletId, oldPortletId, roleIdsToActionIds);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			oldRootPortletId);

		PortletLocalServiceUtil.destroyPortlet(portlet);

		doUpgrade();

		CacheRegistryUtil.clear();

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		String newPortletId = StringPool.BLANK;
		String newRootPortletId = StringPool.BLANK;

		if (_testUninstanceable) {
			newPortletId = getNewPortletId(layoutTypePortlet, oldPortletId);
			newRootPortletId = oldPortletId;

			Assert.assertTrue(
				newPortletId.contains(PortletConstants.INSTANCE_SEPARATOR));
		}
		else {
			newPortletId = _NEW_PORTLET_ID;
			newRootPortletId = _NEW_ROOT_PORTLET_ID;
		}

		portlet.setCompanyId(TestPropsValues.getCompanyId());
		portlet.setPortletId(newRootPortletId);

		List<String> portletActions =
			ResourceActionsUtil.getPortletResourceActions(
				portlet.getPortletId());

		ResourceActionLocalServiceUtil.checkResourceActions(
			portlet.getPortletId(), portletActions);

		PortletLocalServiceUtil.checkPortlet(portlet);

		Assert.assertTrue(layoutTypePortlet.hasPortletId(newPortletId));

		boolean hasViewPermission =
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				TestPropsValues.getCompanyId(), newRootPortletId,
				ResourceConstants.SCOPE_INDIVIDUAL, newPortletId,
				role.getRoleId(), ActionKeys.VIEW);

		Assert.assertFalse(hasViewPermission);

		boolean hasConfigurationPermission =
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				TestPropsValues.getCompanyId(), newRootPortletId,
				ResourceConstants.SCOPE_INDIVIDUAL, newPortletId,
				role.getRoleId(), ActionKeys.CONFIGURATION);

		Assert.assertTrue(hasConfigurationPermission);

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

	@Override
	protected String[][] getRenamePortletIdsArray() {
		if (!_testUninstanceable) {
			return new String[][] {
				new String[] {
					_OLD_ROOT_PORTLET_ID, _NEW_ROOT_PORTLET_ID
				}
			};
		}

		return new String[0][0];
	}

	@Override
	protected String[] getUninstanceablePortletIds() {
		if (_testUninstanceable) {
			return new String[] {_UNINSTANCEABLE_PORTLET_ID};
		}

		return new String[0];
	}

	private static final String _NEW_PORTLET_ID =
		"71_test_INSTANCE_LhZwzy867qfr";

	private static final String _NEW_ROOT_PORTLET_ID = "71_test";

	private static final String _OLD_PORTLET_ID = "71_INSTANCE_LhZwzy867qfr";

	private static final String _OLD_ROOT_PORTLET_ID = "71";

	private static final String _UNINSTANCEABLE_PORTLET_ID = "20";

	private boolean _testUninstanceable = false;

}