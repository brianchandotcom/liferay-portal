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

package com.liferay.trash.service.webserver.test;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.webserver.test.BaseWebServerTestCase;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public class WebServerTrashTest extends BaseWebServerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(null, group.getGroupId());

		String portletId = PortletProviderUtil.getPortletId(
			TrashEntry.class.getName(), PortletProvider.Action.VIEW);

		_role = RoleTestUtil.addRole(
			"Trash Admin", RoleConstants.TYPE_REGULAR, portletId,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			ActionKeys.ACCESS_IN_CONTROL_PANEL);

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.VIEW);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.VIEW);
	}

	@Test
	public void testRequestFileInTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), "Test Trash.txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		MockHttpServletResponse mockHttpServletResponse = testRequestFile(
			fileEntry, _user, false);

		Assert.assertEquals(
			MockHttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		DLTrashServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

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

		Map<String, String> params = new HashMap<>();

		if (statusInTrash) {
			params.put(
				"status", String.valueOf(WorkflowConstants.STATUS_IN_TRASH));
		}

		MockHttpServletResponse mockHttpServletResponse = service(
			Method.GET, path, null, params, user, null);

		resetPermissionThreadLocal();

		return mockHttpServletResponse;
	}

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}