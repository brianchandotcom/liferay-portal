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

package com.liferay.segments.odata.retriever.test;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.segments.odata.retriever.UserODataRetriever;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class UserODataRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetUsersFilterByDateModifiedEquals() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(dateModified eq %s) and (firstName eq \"%s\")", firstName,
				ISO8601Utils.format(_user1.getModifiedDate())),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedGreater() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(dateModified gt %s) and (firstName eq \"%s\")", firstName,
				ISO8601Utils.format(_user1.getModifiedDate())),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user2, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(dateModified ge %s) and (firstName eq \"%s\")", firstName,
				ISO8601Utils.format(new Date())),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user2, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLower() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(dateModified lt %s) and (firstName eq \"%s\")", firstName,
				ISO8601Utils.format(_user2.getModifiedDate())),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLowerOrEquals()
		throws Exception {

		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(dateModified le %s) and (firstName eq \"%s\")", firstName,
				ISO8601Utils.format(new Date())),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByEmailAddress() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(emailAddress eq \"" + _user1.getEmailAddress() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstName() throws Exception {
		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + _user1.getFirstName() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameAndLastName() throws Exception {
		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + _user1.getFirstName() +
				"\") and (lastName eq " + "\"" + _user1.getLastName() + "\") ",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastName() throws Exception {
		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + _user1.getFirstName() +
				"\") or (lastName eq \"" + _user2.getLastName() + "\") ",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 2, users.size());
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstName()
		throws Exception {

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + _user1.getFirstName() +
				"\") or (lastName eq \"nonexistentLastName\") ",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstNameAndLastName()
		throws Exception {

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + _user1.getFirstName() +
				"\") or (lastName eq \"" + _user1.getLastName() + "\") ",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByGroupId() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + firstName + "\") and (groupId eq \"" +
				_group2.getGroupId() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByGroupIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + firstName + "\") and (groupIds eq \"" +
				_group2.getGroupId() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByGroupIdsWithOr() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + firstName + "\") and ((groupIds eq \"" +
				_group2.getGroupId() + "\") or (groupIds eq \"" +
					_group1.getGroupId() + "\"))",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 2, users.size());
	}

	@Test
	public void testGetUsersFilterByLastName() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());
		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(lastName eq \"" + _user1.getLastName() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByMultipleGroupIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(firstName eq \"" + firstName + "\") and (groupIds eq \"" +
				_group2.getGroupId() + "\") and (groupIds eq \"" +
					_group1.getGroupId() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByScreenName() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());
		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		List<User> users = _userODataRetriever.getUsers(
			_group1.getCompanyId(),
			"(screenName eq \"" + _user1.getScreenName() + "\")",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@Inject
	private UserODataRetriever _userODataRetriever;

}