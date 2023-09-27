/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Peter Fellwock
 */
@RunWith(Arquillian.class)
public class TeamFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addUserGroup(_user.getUserId(), _group);

		UserGroupLocalServiceUtil.addUserUserGroup(
			_user.getUserId(), _userGroup);
	}

	@Test
	public void testGetUserOrUserGroupTeams() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		Team userTeam = TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), "", serviceContext);

		TeamLocalServiceUtil.addUserTeam(
			_user.getUserId(), userTeam.getTeamId());

		List<Team> userOrUserGroupTeams1 =
			TeamLocalServiceUtil.getUserOrUserGroupTeams(
				_group.getGroupId(), _user.getUserId());

		Assert.assertEquals(
			userOrUserGroupTeams1.toString(), 1, userOrUserGroupTeams1.size());
		Assert.assertTrue(
			userOrUserGroupTeams1.toString(),
			userOrUserGroupTeams1.contains(userTeam));

		Team groupTeam = TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), "", serviceContext);

		TeamLocalServiceUtil.addUserGroupTeam(
			_userGroup.getUserGroupId(), groupTeam.getTeamId());

		List<Team> userOrUserGroupTeams2 =
			TeamLocalServiceUtil.getUserOrUserGroupTeams(
				_group.getGroupId(), _user.getUserId());

		Assert.assertEquals(
			userOrUserGroupTeams2.toString(), 2, userOrUserGroupTeams2.size());
		Assert.assertTrue(
			userOrUserGroupTeams2.toString(),
			userOrUserGroupTeams2.contains(userTeam));
		Assert.assertTrue(
			userOrUserGroupTeams2.toString(),
			userOrUserGroupTeams2.contains(groupTeam));
	}

	@Test
	public void testSearchTeams() throws Exception {
		TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(), "team", "",
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		List<Team> teams = TeamLocalServiceUtil.search(
			_group.getGroupId(), "team", "team", null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(teams.toString(), 1, teams.size());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

}