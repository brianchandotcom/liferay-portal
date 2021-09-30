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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.DuplicateTeamException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.TeamNameException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.TeamLocalServiceBaseImpl;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamLocalServiceImpl extends TeamLocalServiceBaseImpl {

	@Override
	public Team addTeam(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Team

		User user = userPersistence.findByPrimaryKey(userId);

		validate(0, groupId, name);

		long teamId = counterLocalService.increment();

		Team team = teamPersistence.create(teamId);

		team.setUuid(serviceContext.getUuid());
		team.setCompanyId(user.getCompanyId());
		team.setUserId(userId);
		team.setUserName(user.getFullName());
		team.setGroupId(groupId);
		team.setName(name);
		team.setDescription(description);

		team = teamPersistence.update(team);

		// Resources

		_resourceLocalService.addResources(
			user.getCompanyId(), groupId, userId, Team.class.getName(),
			team.getTeamId(), false, true, true);

		// Role

		_roleLocalService.addRole(
			userId, Team.class.getName(), teamId, String.valueOf(teamId), null,
			null, RoleConstants.TYPE_PROVIDER, null, null);

		return team;
	}

	@Override
	public Team deleteTeam(long teamId) throws PortalException {
		Team team = teamPersistence.findByPrimaryKey(teamId);

		return deleteTeam(team);
	}

	@Override
	public Team deleteTeam(Team team) throws PortalException {

		// Team

		teamPersistence.remove(team);

		// Resources

		_resourceLocalService.deleteResource(
			team.getCompanyId(), Team.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, team.getTeamId());

		// Group

		List<Group> groups = _groupPersistence.findByC_S(
			team.getCompanyId(), true);

		for (Group group : groups) {
			UnicodeProperties typeSettingsUnicodeUnicodeProperties =
				group.getTypeSettingsProperties();

			List<Long> defaultTeamIds = ListUtil.fromArray(
				StringUtil.split(
					typeSettingsUnicodeUnicodeProperties.getProperty(
						"defaultTeamIds"),
					0L));

			if (defaultTeamIds.contains(team.getTeamId())) {
				defaultTeamIds.remove(team.getTeamId());

				typeSettingsUnicodeUnicodeProperties.setProperty(
					"defaultTeamIds",
					ListUtil.toString(defaultTeamIds, StringPool.BLANK));

				_groupLocalService.updateGroup(
					group.getGroupId(),
					typeSettingsUnicodeUnicodeProperties.toString());
			}
		}

		// Role

		_roleLocalService.deleteRole(team.getRole());

		return team;
	}

	@Override
	public void deleteTeams(long groupId) throws PortalException {
		List<Team> teams = teamPersistence.findByGroupId(groupId);

		for (Team team : teams) {
			deleteTeam(team.getTeamId());
		}
	}

	@Override
	public Team fetchTeam(long groupId, String name) {
		return teamPersistence.fetchByG_N(groupId, name);
	}

	@Override
	public List<Team> getGroupTeams(long groupId) {
		return teamPersistence.findByGroupId(groupId);
	}

	@Override
	public int getGroupTeamsCount(long groupId) {
		return teamPersistence.countByGroupId(groupId);
	}

	@Override
	public Team getTeam(long groupId, String name) throws PortalException {
		return teamPersistence.findByG_N(groupId, name);
	}

	@Override
	public List<Team> getUserOrUserGroupTeams(long groupId, long userId) {
		return teamFinder.findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public List<Team> getUserTeams(long userId, long groupId) {
		return search(
			groupId, null, null,
			LinkedHashMapBuilder.<String, Object>put(
				"usersTeams", userId
			).build(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public List<Team> search(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> orderByComparator) {

		return teamFinder.findByG_N_D(
			groupId, name, description, params, start, end, orderByComparator);
	}

	@Override
	public int searchCount(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params) {

		return teamFinder.countByG_N_D(groupId, name, description, params);
	}

	@Override
	public Team updateTeam(long teamId, String name, String description)
		throws PortalException {

		Team team = teamPersistence.findByPrimaryKey(teamId);

		validate(teamId, team.getGroupId(), name);

		team.setName(name);
		team.setDescription(description);

		return teamPersistence.update(team);
	}

	protected void validate(long teamId, long groupId, String name)
		throws PortalException {

		if (Validator.isNull(name) || Validator.isNumber(name) ||
			(name.indexOf(CharPool.COMMA) != -1) ||
			(name.indexOf(CharPool.STAR) != -1)) {

			throw new TeamNameException();
		}

		Team team = teamPersistence.fetchByG_N(groupId, name);

		if ((team != null) && (team.getTeamId() != teamId)) {
			throw new DuplicateTeamException("{teamId=" + teamId + "}");
		}
	}

	@BeanReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@BeanReference(type = GroupPersistence.class)
	private GroupPersistence _groupPersistence;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

}