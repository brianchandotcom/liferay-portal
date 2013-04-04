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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.usergroupsadmin.search.UserUserGroupChecker;

import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
public class UserGroupMembershipPolicyRowCheckerTest
	extends BaseUserGroupMembershipPolicyTestCase {

	@Test
	public void testIsCheckerDisabledWhenSettingForbiddenUserGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenUserGroupId = addForbiddenUserGroups()[0];

		UserGroup forbiddenUserGroup = UserGroupLocalServiceUtil.getUserGroup(
			forbiddenUserGroupId);

		UserUserGroupChecker userUserGroupChecker = new UserUserGroupChecker(
			renderResponse, forbiddenUserGroup);

		User user = UserTestUtil.addUser();

		Assert.assertTrue(userUserGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingRequiredUserGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredUserGroupId = addRequiredUserGroups()[0];

		UserGroup requiredUserGroup = UserGroupLocalServiceUtil.getUserGroup(
			requiredUserGroupId);

		UserUserGroupChecker userUserGroupChecker = new UserUserGroupChecker(
			renderResponse, requiredUserGroup);

		User user = UserTestUtil.addUser();

		Assert.assertFalse(userUserGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingForbiddenUserGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenUserGroupId = addForbiddenUserGroups()[0];

		UserGroup forbiddenUserGroup = UserGroupLocalServiceUtil.getUserGroup(
			forbiddenUserGroupId);

		UserUserGroupChecker userUserGroupChecker = new UserUserGroupChecker(
			renderResponse, forbiddenUserGroup);

		User user = UserTestUtil.addUser();

		UserGroupLocalServiceUtil.addUserUserGroup(
			user.getUserId(), forbiddenUserGroupId);

		Assert.assertFalse(userUserGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingRequiredUserGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredUserGroupId = addRequiredUserGroups()[0];

		UserGroup requiredUserGroup = UserGroupLocalServiceUtil.getUserGroup(
			requiredUserGroupId);

		UserUserGroupChecker userUserGroupChecker = new UserUserGroupChecker(
			renderResponse, requiredUserGroup);

		User user = UserTestUtil.addUser();

		UserGroupLocalServiceUtil.addUserUserGroup(
			user.getUserId(), requiredUserGroupId);

		Assert.assertTrue(userUserGroupChecker.isDisabled(user));
	}

}