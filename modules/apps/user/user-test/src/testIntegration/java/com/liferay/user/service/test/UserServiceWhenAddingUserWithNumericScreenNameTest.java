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
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
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
public class UserServiceWhenAddingUserWithNumericScreenNameTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldAddUser() throws Exception {
		long numericScreenName = RandomTestUtil.nextLong();

		_user = UserTestUtil.addUser(String.valueOf(numericScreenName));

		Assert.assertEquals(
			String.valueOf(numericScreenName), _user.getScreenName());
	}

	@Test
	public void testShouldAddUserWhenScreenNameMatchesExistingGroupId()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(String.valueOf(_group.getGroupId()));

		Assert.assertEquals(
			String.valueOf(_group.getGroupId()), _user.getScreenName());
	}

	@Test(expected = UserScreenNameException.MustNotBeNumeric.class)
	public void testShouldThrowException() throws Exception {
		try {
			PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC = false;

			UserTestUtil.addUser(String.valueOf(RandomTestUtil.nextLong()));
		}
		finally {
			PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}