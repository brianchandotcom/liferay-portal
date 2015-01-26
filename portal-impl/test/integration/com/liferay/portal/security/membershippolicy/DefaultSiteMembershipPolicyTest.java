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

import com.liferay.portal.kernel.events.IntervalAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class DefaultSiteMembershipPolicyTest
	extends BaseSiteMembershipPolicyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_parentGroup = GroupTestUtil.addGroup();

		UserLocalServiceUtil.unsetGroupUsers(
			_parentGroup.getGroupId(), new long[] {TestPropsValues.getUserId()},
			null);

		_group = GroupTestUtil.addGroup(_parentGroup.getGroupId());

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
				new long[] {_parentGroup.getGroupId(), _group.getGroupId()});

			userIds.add(user.getUserId());
		}

		for (int i = 0; i < IntervalAction.DEFAULT_INTERVAL; i++) {
			User user = UserTestUtil.addUser();

			GroupLocalServiceUtil.setUserGroups(
				user.getUserId(), new long[] {_group.getGroupId()});

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