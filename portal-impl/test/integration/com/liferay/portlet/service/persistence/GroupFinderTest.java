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

package com.liferay.portlet.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.GroupFinderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.ResourcePermissionTestUtil;
import com.liferay.portal.util.ResourceTypePermissionTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 * @author László Csontos
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class GroupFinderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();

		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(),
			StringUtil.valueOf(_group.getGroupId()),
			ResourceConstants.SCOPE_GROUP);

		_bookmarkFolderResourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				BookmarksFolder.class.getName(), ActionKeys.VIEW);

		_resourceTypePermission =
			ResourceTypePermissionTestUtil.addResourceTypePermission(
				_bookmarkFolderResourceAction.getBitwiseValue(),
				_group.getGroupId(), _bookmarkFolderResourceAction.getName());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);

		ResourcePermissionLocalServiceUtil.deleteResourcePermission(
			_resourcePermission);

		ResourceTypePermissionLocalServiceUtil.deleteResourceTypePermission(
			_resourceTypePermission);
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourcePermissions()
		throws Exception {

		List<Group> groups = doFindByC_C_N_D(
			_arbitraryResourceAction.getActionId(),
			_resourcePermission.getName(), _resourcePermission.getRoleId());

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByC_C_N_D should have returned the group " +
				_group.getGroupId());
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourceTypePermissions()
		throws Exception {

		List<Group> groups = doFindByC_C_N_D(
			_bookmarkFolderResourceAction.getActionId(),
			_resourceTypePermission.getName(),
			_resourceTypePermission.getRoleId());

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByC_C_N_D should have returned the group " +
			_group.getGroupId());
	}

	@Test
	public void testFindByLayouts() throws Exception {
		List<Group> groups = doFindByLayouts(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		int initialGroupCount = groups.size();

		GroupTestUtil.addGroup("rootGroup");
		Group parentGroup = GroupTestUtil.addGroup("parentGroup");
		Group childGroup1 = GroupTestUtil.addGroup(
			parentGroup.getGroupId(), "childGroup1");
		Group childGroup2 = GroupTestUtil.addGroup(
			parentGroup.getGroupId(), "childGroup2");

		LayoutTestUtil.addLayout(
			parentGroup.getGroupId(), ServiceTestUtil.randomString(), false);
		LayoutTestUtil.addLayout(
			childGroup1.getGroupId(), ServiceTestUtil.randomString(), false);
		LayoutTestUtil.addLayout(
			childGroup2.getGroupId(), ServiceTestUtil.randomString(), true);

		groups = doFindByLayouts(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Assert.assertEquals(initialGroupCount + 1, groups.size());

		groups = doFindByLayouts(parentGroup.getGroupId());

		Assert.assertEquals(2, groups.size());

		groups = doFindByLayouts(childGroup1.getGroupId());

		Assert.assertTrue(groups.isEmpty());
	}

	protected void addLayout(long groupId) throws Exception {
		LayoutTestUtil.addLayout(
			groupId, ServiceTestUtil.randomString(), false);

		LayoutTestUtil.addLayout(groupId, ServiceTestUtil.randomString(), true);
	}

	protected List<Group> doFindByC_C_N_D(
			String actionId, String name, long roleId)
		throws Exception {

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		List<Object> rolePermissions = new ArrayList<Object>();

		rolePermissions.add(name);
		rolePermissions.add(new Integer(ResourceConstants.SCOPE_GROUP));
		rolePermissions.add(actionId);
		rolePermissions.add(roleId);

		groupParams.put("rolePermissions", rolePermissions);

		long[] classNameIds = {PortalUtil.getClassNameId(Group.class)};

		return GroupFinderUtil.findByC_C_PG_N_D(
			TestPropsValues.getCompanyId(), classNameIds,
			GroupConstants.ANY_PARENT_GROUP_ID, new String[] {null},
			new String[] {null}, groupParams, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	protected List<Group> doFindByLayouts(long parentGroupId) throws Exception {
		return GroupFinderUtil.findByLayouts(
			TestPropsValues.getCompanyId(), parentGroupId, true, -1, -1);
	}

	private static ResourceAction _arbitraryResourceAction;
	private static ResourceAction _bookmarkFolderResourceAction;
	private static Group _group;
	private static ResourcePermission _resourcePermission;
	private static ResourceTypePermission _resourceTypePermission;

}