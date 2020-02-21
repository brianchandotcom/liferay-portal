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

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jesse Yeh
 */
@RunWith(Arquillian.class)
public class UserSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);
		_groupMemberUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		_nonGroupMemberUser = UserTestUtil.addUser();
	}

	@Test
	public void testSearchUsersAsGroupAdmin() throws Exception {
		Long[] testUserIds = {
			_groupAdminUser.getUserId(), _groupMemberUser.getUserId(),
			_nonGroupMemberUser.getUserId()
		};

		Stream<User> usersStream = _getUsers().stream();

		List<Long> userIds = usersStream.map(
			User::getUserId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(
			Stream.of(
				testUserIds
			).allMatch(
				userIds::contains
			));
	}

	private List<User> _getUsers() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_groupAdminUser));

		LinkedHashMap<String, Object> userParams =
			LinkedHashMapBuilder.<String, Object>put(
				Field.GROUP_ID, Long.valueOf(_groupAdminUser.getGroupIds()[0])
			).build();

		String keywords = "";
		int status = 0;
		int start = 0;
		int end = 20;

		Hits hits = _userLocalService.search(
			TestPropsValues.getCompanyId(), keywords, status, userParams, start,
			end, new Sort());

		return UsersAdminUtil.getUsers(hits);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupAdminUser;

	@DeleteAfterTestRun
	private User _groupMemberUser;

	@DeleteAfterTestRun
	private User _nonGroupMemberUser;

	@Inject
	private UserLocalService _userLocalService;

}