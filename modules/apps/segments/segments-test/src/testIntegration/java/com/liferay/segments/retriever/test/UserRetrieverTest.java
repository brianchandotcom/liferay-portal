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

package com.liferay.segments.retriever.test;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.segments.retriever.UserRetriever;
import com.liferay.segments.util.PaginationTestUtil;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class UserRetrieverTest {

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

		Thread.sleep(1000);

		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(firstName eq '%s') and (dateModified eq %s)", firstName,
				ISO8601Utils.format(_user1.getModifiedDate())),
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedGreater() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Thread.sleep(1000);

		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(firstName eq '%s') and (dateModified gt %s)", firstName,
				ISO8601Utils.format(_user1.getModifiedDate())),
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user2, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Thread.sleep(1000);
		Date inBetween = new Date();

		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(firstName eq '%s') and (dateModified ge %s)", firstName,
				ISO8601Utils.format(inBetween)),
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user2, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLower() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Thread.sleep(1000);

		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(firstName eq '%s') and (dateModified lt %s)", firstName,
				ISO8601Utils.format(_user2.getModifiedDate())),
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLowerOrEquals()
		throws Exception {

		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Date inBetween = new Date();
		Thread.sleep(1000);

		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			String.format(
				"(firstName eq '%s') and (dateModified le %s)", firstName,
				ISO8601Utils.format(inBetween)),
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByEmailAddress() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		String expectedEmailAddress = _user1.getEmailAddress();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(emailAddress eq '" + expectedEmailAddress + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstName() throws Exception {
		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		String expectedFirstName = _user1.getFirstName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + expectedFirstName + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameAndLastName() throws Exception {
		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		String expectedFirstName = _user1.getFirstName();
		String expectedLastName = _user1.getLastName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + expectedFirstName + "') and (lastName eq '" +
				expectedLastName + "') ",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(1, pageItems.getTotalCount());

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

		String expectedFirstName = _user1.getFirstName();
		String expectedLastName = _user2.getLastName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + expectedFirstName + "') or (lastName eq '" +
				expectedLastName + "') ",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(2, pageItems.getTotalCount());
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstName()
		throws Exception {

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		String expectedFirstName = _user1.getFirstName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + expectedFirstName +
				"') or (lastName eq 'nonExistingLastName') ",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstNameAndLastName()
		throws Exception {

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId()});

		String expectedFirstName = _user1.getFirstName();
		String expectedLastName = _user1.getLastName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + expectedFirstName + "') or (lastName eq '" +
				expectedLastName + "') ",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

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

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + firstName + "') and (groupId eq '" +
				_group2.getGroupId() + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

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

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + firstName + "') and (groupIds eq '" +
				_group2.getGroupId() + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

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

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + firstName + "') and ((groupIds eq '" +
				_group2.getGroupId() + "') or (groupIds eq '" +
					_group1.getGroupId() + "'))",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(2, pageItems.getTotalCount());
	}

	@Test
	public void testGetUsersFilterByLastName() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		String expectedLastName = _user1.getLastName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(), "(lastName eq '" + expectedLastName + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

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

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(firstName eq '" + firstName + "') and (groupIds eq '" +
				_group2.getGroupId() + "') and (groupIds eq '" +
					_group1.getGroupId() + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByScreenName() throws Exception {
		_user1 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		String expectedScreenName = _user1.getScreenName();

		PageItems<User> pageItems = _segmentsService.getUsers(
			_group1.getCompanyId(),
			"(screenName eq '" + expectedScreenName + "')",
			LocaleUtil.getDefault(), PaginationTestUtil.of(10, 1));

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<User> users = (List<User>)pageItems.getItems();

		Assert.assertEquals(_user1, users.get(0));
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private UserRetriever _segmentsService;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@Inject
	private UserLocalService _userLocalService;

}