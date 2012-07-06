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

package com.liferay.portal.webserver;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.webdav.methods.Method;

import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WebServerTrashTest extends BaseWebServerTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = ServiceTestUtil.addUser(
			null, true, new long[]{TestPropsValues.getGroupId()});

		try {
			_role = RoleLocalServiceUtil.getRole(
				TestPropsValues.getCompanyId(), "Trash Admin");
		}
		catch (NoSuchRoleException nsre) {
			_role = RoleLocalServiceUtil.addRole(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				"Trash Admin", null, null, RoleConstants.TYPE_REGULAR);
		}

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_user.getCompanyId(), PortletKeys.TRASH,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			_role.getRoleId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);

	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		if (_user != null) {
			UserLocalServiceUtil.deleteUser(_user.getUserId());
		}

		if (_role !=null) {
			RoleLocalServiceUtil.deleteRole(_role.getRoleId());
		}
	}
	
	@Test
	public void testRequestFileInTrash() throws Exception {
		FileEntry fileEntry = addFileEntry(false, "Trash-Test.txt");

		MockHttpServletResponse response = 
			_testRequestFile(fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, response.getStatus());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		response = _testRequestFile(fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_NOT_FOUND, response.getStatus());

		response = _testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_UNAUTHORIZED, response.getStatus());

		_grantPermissionToManageTrash(_user);

		response = _testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, response.getStatus());
	}
	
	private MockHttpServletResponse _testRequestFile (
			FileEntry fileEntry, User user, boolean statusInTrash) 
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getGroupId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		String path = sb.toString();

		Map<String, String> params = new HashMap<String, String>();
		
		if (statusInTrash) {
			params.put(
				"status", String.valueOf(WorkflowConstants.STATUS_IN_TRASH));
		}

		MockHttpServletResponse response = service(
			Method.GET, path, null, params, user, null);

		_resetPermissionThreadLocal();

		return response;
	}

	private void _grantPermissionToManageTrash(User user) throws Exception {
		long[] roleIds = new long[] {_role.getRoleId()};
		
		RoleLocalServiceUtil.addUserRoles(
			user.getUserId(), roleIds);
	}
	
	private void _resetPermissionThreadLocal() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}
	
	private Role _role;
	private User _user;
	
}


