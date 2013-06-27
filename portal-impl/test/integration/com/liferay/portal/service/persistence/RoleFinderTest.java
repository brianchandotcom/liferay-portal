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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.ResourceBlockPermissionTestUtil;
import com.liferay.portal.util.ResourceBlockTestUtil;
import com.liferay.portal.util.ResourcePermissionTestUtil;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class RoleFinderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		List<Role> roles = RoleLocalServiceUtil.getRoles(
			RoleConstants.TYPE_REGULAR, StringPool.BLANK);

		_arbitraryRole = roles.get(0);

		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(), _arbitraryRole.getRoleId());

		_bookmarkFolderResourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				BookmarksFolder.class.getName(), ActionKeys.VIEW);

		_resourceBlock = ResourceBlockTestUtil.addResourceBlock(
			_bookmarkFolderResourceAction.getName());

		_resourceBlockPermission =
			ResourceBlockPermissionTestUtil.addResourceBlockPermission(
				_resourceBlock.getResourceBlockId(), _arbitraryRole.getRoleId(),
				_bookmarkFolderResourceAction.getBitwiseValue());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ResourcePermissionLocalServiceUtil.deleteResourcePermission(
			_resourcePermission);

		ResourceBlockLocalServiceUtil.deleteResourceBlock(_resourceBlock);

		ResourceBlockPermissionLocalServiceUtil.deleteResourceBlockPermission(
			_resourceBlockPermission);
	}

	@Test
	public void testFindByC_N_S_P_A() throws Exception {
		List<Role> roles = RoleFinderUtil.findByC_N_S_P_A(
			_resourcePermission.getCompanyId(), _resourcePermission.getName(),
			_resourcePermission.getScope(), _resourcePermission.getPrimKey(),
			_arbitraryResourceAction.getActionId());

		for (Role role : roles) {
			if (role.getRoleId() == _arbitraryRole.getRoleId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByC_N_S_P_A should have returned the role " +
				_arbitraryRole.getRoleId());
	}

	@Test
	public void testFindByR_N_A() throws Exception {
		List<Role> roles = RoleFinderUtil.findByR_N_A(
			_resourceBlock.getResourceBlockId(), _resourceBlock.getName(),
			_bookmarkFolderResourceAction.getActionId());

		for (Role role : roles) {
			if (role.getRoleId() == _arbitraryRole.getRoleId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByR_N_A should have returned the role " +
				_arbitraryRole.getRoleId());
	}

	private static ResourceAction _arbitraryResourceAction;
	private static Role _arbitraryRole;
	private static ResourceAction _bookmarkFolderResourceAction;
	private static ResourceBlock _resourceBlock;
	private static ResourceBlockPermission _resourceBlockPermission;
	private static ResourcePermission _resourcePermission;

}