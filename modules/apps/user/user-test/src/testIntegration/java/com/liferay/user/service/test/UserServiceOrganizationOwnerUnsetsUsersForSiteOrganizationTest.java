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

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserServiceOrganizationOwnerUnsetsUsersForSiteOrganizationTest
	extends BaseUserServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_organization = OrganizationTestUtil.addOrganization(true);

		_group = _organization.getGroup();

		_organizationOwnerUser = UserTestUtil.addOrganizationOwnerUser(
			_organization);
	}

	@Test
	public void testShouldUnsetSiteAdmin() throws Exception {
		User groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

		try {
			unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, groupAdminUser);

			Assert.assertFalse(
				userLocalService.hasGroupUser(
					_group.getGroupId(), groupAdminUser.getUserId()));
		}
		finally {
			userLocalService.deleteUser(groupAdminUser);
		}
	}

	@Test
	public void testShouldUnsetSiteOwner() throws Exception {
		User groupOwnerUser = UserTestUtil.addGroupOwnerUser(_group);

		try {
			unsetGroupUsers(
				_group.getGroupId(), _organizationOwnerUser, groupOwnerUser);

			Assert.assertFalse(
				userLocalService.hasGroupUser(
					_group.getGroupId(), groupOwnerUser.getUserId()));
		}
		finally {
			userLocalService.deleteUser(groupOwnerUser);
		}
	}

	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _organizationOwnerUser;

}