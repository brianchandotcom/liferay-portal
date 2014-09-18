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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DefaultSiteMembershipPolicyTest
	extends BaseSiteMembershipPolicyTestCase {

	@Before
	public void setUp() throws Exception {
		_parentGroup = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		UserLocalServiceUtil.unsetGroupUsers(
			_parentGroup.getGroupId(), new long[] {TestPropsValues.getUserId()},
			null);

		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_parentGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		UserLocalServiceUtil.unsetGroupUsers(
			_group.getGroupId(), new long[] {TestPropsValues.getUserId()},
			null);
	}

	@Test
	public void testVerifyLimitedParentMembershipIteration() throws Exception {
		_group.setMembershipRestriction(
			GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

		List<Long> userIds = new ArrayList();

		for (int i = 0; i < 20; i++) {
			User user = UserTestUtil.addUser();

			GroupLocalServiceUtil.setUserGroups(
				user.getUserId(),
				new long[]{_parentGroup.getGroupId(), _group.getGroupId()});

			userIds.add(user.getUserId());
		}

		for (int i = 0; i < DefaultSiteMembershipPolicy.DELETE_INTERVAL; i++) {
			User user = UserTestUtil.addUser();

			GroupLocalServiceUtil.setUserGroups(
				user.getUserId(), new long[]{_group.getGroupId()});

			userIds.add(user.getUserId());
		}

		SiteMembershipPolicy defaultSiteMembershipPolicy =
			new DefaultSiteMembershipPolicy();

		defaultSiteMembershipPolicy.verifyPolicy(_group);

		Assert.assertEquals(
			20, UserLocalServiceUtil.getGroupUsersCount(_group.getGroupId()));

		for (long userId : userIds) {
			UserLocalServiceUtil.deleteUser(userId);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _parentGroup;

}