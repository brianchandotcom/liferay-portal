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

package com.liferay.site.teams.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class TeamStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_user != null) {
			UserLocalServiceUtil.deleteUser(_user);
		}

		if (_userGroup != null) {
			UserGroupLocalServiceUtil.deleteUserGroup(_userGroup);
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Team team = TeamLocalServiceUtil.addTeam(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		_user = UserTestUtil.addUser();

		UserLocalServiceUtil.addTeamUser(team.getTeamId(), _user);

		_userGroup = UserGroupLocalServiceUtil.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		UserGroupLocalServiceUtil.addTeamUserGroup(
			team.getTeamId(), _userGroup);

		return team;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		TeamLocalServiceUtil.deleteTeam((Team)stagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return TeamLocalServiceUtil.getTeamByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Team.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		Team team = (Team)stagedModel;
		Team importedTeam = (Team)importedStagedModel;

		Assert.assertEquals(team.getName(), importedTeam.getName());
		Assert.assertEquals(
			team.getDescription(), importedTeam.getDescription());

		List<User> teamUsers = UserLocalServiceUtil.getTeamUsers(
			importedTeam.getTeamId());

		Assert.assertEquals(teamUsers.toString(), 1, teamUsers.size());
		Assert.assertEquals(_user, teamUsers.get(0));

		List<UserGroup> teamUserGroups =
			UserGroupLocalServiceUtil.getTeamUserGroups(
				importedTeam.getTeamId());

		Assert.assertEquals(
			teamUserGroups.toString(), 1, teamUserGroups.size());
		Assert.assertEquals(_userGroup, teamUserGroups.get(0));
	}

	private User _user;
	private UserGroup _userGroup;

}