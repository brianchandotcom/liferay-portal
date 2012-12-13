/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
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
public class UpgradePortletIdsTest extends UpgradePortletId {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testUpgrade() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			initialRootPortletId);

		Layout layout = getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(), initialInstancePortletId);

		addPortletPreferences(layout, initialInstancePortletId);

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		roleIdsToActionIds.put(
			role.getRoleId(), new String[] {ActionKeys.CONFIGURATION});

		ResourcePermissionServiceUtil.setIndividualResourcePermissions(
			layout.getGroupId(), companyId, initialRootPortletId,
			initialInstancePortletId, roleIdsToActionIds);

		PortletLocalServiceUtil.destroyPortlet(portlet);

		doUpgrade();

		portlet.setCompanyId(companyId);
		portlet.setPortletId(subsequentRootPortletId);

		List<String> portletActions =
			ResourceActionsUtil.getPortletResourceActions(
				portlet.getPortletId());

		ResourceActionLocalServiceUtil.checkResourceActions(
			portlet.getPortletId(), portletActions);

		PortletLocalServiceUtil.checkPortlet(portlet);

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		Assert.assertTrue(
			layoutTypePortlet.hasPortletId(subsequentInstancePortletId));

		boolean hasViewPermission =
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				companyId, subsequentRootPortletId,
				ResourceConstants.SCOPE_INDIVIDUAL, subsequentInstancePortletId,
				role.getRoleId(), ActionKeys.VIEW);

		Assert.assertFalse(hasViewPermission);

		boolean hasConfigurationPermission =
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				companyId, subsequentRootPortletId,
				ResourceConstants.SCOPE_INDIVIDUAL, subsequentInstancePortletId,
				role.getRoleId(), ActionKeys.CONFIGURATION);

		Assert.assertTrue(hasConfigurationPermission);
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

	@Override
	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				initialRootPortletId,
				subsequentRootPortletId
			}
		};
	}

	protected String initialInstancePortletId = "71_INSTANCE_LhZwzy867qfr";
	protected String initialRootPortletId = "71";
	protected String subsequentInstancePortletId =
		"71_test_INSTANCE_LhZwzy867qfr";
	protected String subsequentRootPortletId = "71_test";

}