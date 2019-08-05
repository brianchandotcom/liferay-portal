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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.time.Instant;

import java.util.ArrayList;
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
public class UserODataRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetUsersFilterByAncestorOrganizationIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomBoolean());

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		_userLocalService.addOrganizationUser(
			organization.getOrganizationId(), _user1);

		String filterString = String.format(
			"(firstName eq '%s') and (ancestorOrganizationIds eq '%s')",
			firstName, parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByCompanyId() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		String filterString = String.format(
			"(firstName eq '%s') and (companyId eq '%s')", firstName,
			_group1.getCompanyId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
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

		Date modifiedDate = _user1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		_user2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_userLocalService.updateUser(_user2);

		String filterString = String.format(
			"(dateModified eq %s) and (firstName eq '%s')",
			ISO8601Utils.format(_user1.getModifiedDate()), firstName);

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		Date modifiedDate = _user1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		_user2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_userLocalService.updateUser(_user2);

		String filterString = String.format(
			"(dateModified gt %s) and (firstName eq '%s')",
			ISO8601Utils.format(_user1.getModifiedDate()), firstName);

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		Date modifiedDate = _user1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		_user2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_userLocalService.updateUser(_user2);

		String filterString = String.format(
			"(dateModified ge %s) and (firstName eq '%s')",
			ISO8601Utils.format(_user2.getModifiedDate()), firstName);

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		Date modifiedDate = _user1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		_user2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_userLocalService.updateUser(_user2);

		String filterString = String.format(
			"(dateModified lt %s) and (firstName eq '%s')",
			ISO8601Utils.format(_user2.getModifiedDate()), firstName);

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		Date modifiedDate = _user1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		_user2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_userLocalService.updateUser(_user2);

		String filterString = String.format(
			"(dateModified le %s) and (firstName eq '%s')",
			ISO8601Utils.format(modifiedDate), firstName);

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByEmailAddress() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString =
			"(emailAddress eq '" + _user1.getEmailAddress() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = "(firstName eq '" + _user1.getFirstName() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameAndLastName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = StringBundler.concat(
			"(firstName eq '", _user1.getFirstName(), "') and (lastName eq ",
			"'", _user1.getLastName(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameAndNotTeamIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		_team = _addTeam();

		_userLocalService.addTeamUser(_team.getTeamId(), _user1);

		String filterString = String.format(
			"(firstName eq '%s') and (not (teamIds eq '%s'))", firstName,
			_team.getTeamId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			1);

		Assert.assertEquals(_user2, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());
		_user2 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = StringBundler.concat(
			"(firstName eq '", _user1.getFirstName(), "') or (lastName eq '",
			_user2.getLastName(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(2, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertTrue(users.contains(_user1));
		Assert.assertTrue(users.contains(_user2));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstName()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = StringBundler.concat(
			"(firstName eq '", _user1.getFirstName(), "') or (lastName eq ",
			"'nonexistentLastName')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByFirstNameOrLastNameWithSameFirstNameAndLastName()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = StringBundler.concat(
			"(firstName eq '", _user1.getFirstName(), "') or (lastName eq '",
			_user1.getLastName(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		String filterString = StringBundler.concat(
			"(firstName eq '", firstName, "') and (groupId eq '",
			_group2.getGroupId(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		String filterString = StringBundler.concat(
			"(firstName eq '", firstName, "') and (groupIds eq '",
			_group2.getGroupId(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		String filterString = StringBundler.concat(
			"(firstName eq '", firstName, "') and ((groupIds eq '",
			_group2.getGroupId(), "') or (groupIds eq '", _group1.getGroupId(),
			"'))");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(2, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertTrue(users.contains(_user1));
		Assert.assertTrue(users.contains(_user2));
	}

	@Test
	public void testGetUsersFilterByGroupIdWithAnd() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		String filterString = String.format(
			"(firstName eq '%s') and (groupId eq '%s') and (groupId eq '%s')",
			firstName, _group1.getGroupId(), _group2.getGroupId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByJobTitle() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		_user1.setJobTitle(RandomTestUtil.randomString());

		_userLocalService.updateUser(_user1);

		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		String filterString = "(jobTitle eq '" + _user1.getJobTitle() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByJobTitleContains() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String jobTitlePrefix = RandomTestUtil.randomString();

		_user1.setJobTitle(jobTitlePrefix + RandomTestUtil.randomString());

		_userLocalService.updateUser(_user1);

		_user2 = UserTestUtil.addUser(
			_group1.getGroupId(), LocaleUtil.getDefault());

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(),
			"contains(jobTitle, '" + jobTitlePrefix + "')",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByLastName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());
		_user2 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = "(lastName eq '" + _user1.getLastName() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

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

		String filterString = StringBundler.concat(
			"(firstName eq '", firstName, "') and (groupIds eq '",
			_group2.getGroupId(), "') and (groupIds eq '", _group1.getGroupId(),
			"')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByOrganizationIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user1.getUserId()});

		String filterString = String.format(
			"(firstName eq '%s') and (organizationIds eq '%s')", firstName,
			organization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByRoleIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(_role.getRoleId(), _user1);

		String filterString = String.format(
			"(firstName eq '%s') and (roleIds eq '%s')", firstName,
			_role.getRoleId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByScopeGroupId() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(),
			new long[] {_group1.getGroupId(), _group2.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		String filterString = StringBundler.concat(
			"(firstName eq '", firstName, "') and (scopeGroupId eq '",
			_group2.getGroupId(), "')");

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByScreenName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());
		_user2 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString =
			"(screenName eq '" + _user1.getScreenName() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByTeamIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		_team = _addTeam();

		_userLocalService.addTeamUser(_team.getTeamId(), _user1);

		String filterString = String.format(
			"(firstName eq '%s') and (teamIds eq '%s')", firstName,
			_team.getTeamId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByUserGroupIds() throws Exception {
		String firstName = RandomTestUtil.randomString();

		_user1 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});
		_user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), firstName,
			RandomTestUtil.randomString(), new long[] {_group1.getGroupId()});

		_userGroup = UserGroupTestUtil.addUserGroup();

		_userLocalService.addUserGroupUser(_userGroup.getUserGroupId(), _user1);

		String filterString = String.format(
			"(firstName eq '%s') and (userGroupIds eq '%s')", firstName,
			_userGroup.getUserGroupId());

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByUserId() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString = "(userId eq '" + _user1.getUserId() + "')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	@Test
	public void testGetUsersFilterByUserName() throws Exception {
		_user1 = UserTestUtil.addUser(_group1.getGroupId());
		_user2 = UserTestUtil.addUser(_group1.getGroupId());

		String filterString =
			"(userName eq '" + StringUtil.toLowerCase(_user1.getFullName()) +
				"')";

		int count = _oDataRetriever.getResultsCount(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<User> users = _oDataRetriever.getResults(
			_group1.getCompanyId(), filterString, LocaleUtil.getDefault(), 0,
			2);

		Assert.assertEquals(_user1, users.get(0));
	}

	private Team _addTeam() throws PortalException {
		return _teamLocalService.addTeam(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject(filter = "model.class.name=com.liferay.portal.kernel.model.User")
	private ODataRetriever<User> _oDataRetriever;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private Team _team;

	@Inject
	private TeamLocalService _teamLocalService;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserLocalService _userLocalService;

}