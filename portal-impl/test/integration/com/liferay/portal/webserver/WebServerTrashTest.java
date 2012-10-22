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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
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
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.webdav.methods.Method;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WebServerTrashTest extends BaseWebServerTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = ServiceTestUtil.addUser(
			null, true, new long[] {group.getGroupId()});

		try {
			_role = RoleLocalServiceUtil.getRole(
				TestPropsValues.getCompanyId(), "Trash Admin");
		}
		catch (NoSuchRoleException nsre) {
			_role = RoleLocalServiceUtil.addRole(
				TestPropsValues.getUserId(), null, 0, "Trash Admin", null, null,
				RoleConstants.TYPE_REGULAR, null);
		}

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_user.getCompanyId(), PortletKeys.TRASH,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			ActionKeys.ACCESS_IN_CONTROL_PANEL);

	}

	@Override
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
		FileEntry fileEntry = addFileEntry(false, "Test Trash.txt");

		MockHttpServletResponse mockHttpServletResponse =  testRequestFile(
			fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		mockHttpServletResponse = testRequestFile(fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());

		mockHttpServletResponse = testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_UNAUTHORIZED,
			mockHttpServletResponse.getStatus());

		RoleLocalServiceUtil.addUserRoles(
			_user.getUserId(), new long[] {_role.getRoleId()});

		mockHttpServletResponse = testRequestFile(fileEntry, _user, true);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	protected void resetPermissionThreadLocal() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	protected MockHttpServletResponse testRequestFile(
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

		MockHttpServletResponse mockHttpServletResponse = service(
			Method.GET, path, null, params, user, null);

		resetPermissionThreadLocal();

		return mockHttpServletResponse;
	}

	private Role _role;
	private User _user;

}