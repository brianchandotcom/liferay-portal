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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBMessagePermissionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		String name = "Test Category";
		String description = "This is a test category.";
		String displayStyle = MBCategoryConstants.DEFAULT_DISPLAY_STYLE;
		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean allowAnonymous = false;
		boolean mailingListActive = false;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		serviceContext.setGroupPermissions(
			new String[]{ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});
		serviceContext.setGuestPermissions(
			new String[]{ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});

		_category = MBCategoryServiceUtil.addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, name, description,
			displayStyle, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, allowAnonymous, mailingListActive, serviceContext);

		_message = MBTestUtil.addMessage(_group.getGroupId());

		_submessage = MBTestUtil.addMessage(
			_group.getGroupId(), _category.getCategoryId());

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testGetMessageWithoutRootPermission() throws Exception {
		checkMessageRootPermission(false);
	}

	@Test
	public void testGetMessageWithRootPermission() throws Exception {
		checkMessageRootPermission(true);
	}

	protected void checkMessageRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		boolean hasViewPermission = MBMessagePermission.contains(
			permissionChecker, _message.getMessageId(), ActionKeys.VIEW);

		boolean hasSubmessageViewPermission = MBMessagePermission.contains(
			permissionChecker, _submessage.getMessageId(), ActionKeys.VIEW);

		if (!hasRootPermission) {
			Assert.assertFalse(hasViewPermission);
			Assert.assertFalse(hasSubmessageViewPermission);
		}
		else {
			Assert.assertTrue(hasViewPermission);
			Assert.assertTrue(hasSubmessageViewPermission);
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	private MBCategory _category;
	private Group _group;
	private MBMessage _message;
	private MBMessage _submessage;

}