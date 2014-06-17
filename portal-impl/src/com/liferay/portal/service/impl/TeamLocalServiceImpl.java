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

import com.liferay.portal.DuplicateTeamException;
import com.liferay.portal.TeamNameException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.TeamLocalServiceBaseImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamLocalServiceImpl extends TeamLocalServiceBaseImpl {

	@Override
	public Team addTeam(
			long userId, long groupId, String name, String description)
		throws PortalException {

		// Team

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(0, groupId, name);

		long teamId = counterLocalService.increment();

		Team team = teamPersistence.create(teamId);

		team.setUserId(userId);
		team.setCompanyId(user.getCompanyId());
		team.setUserName(user.getFullName());
		team.setCreateDate(now);
		team.setModifiedDate(now);
		team.setGroupId(groupId);
		team.setName(name);
		team.setDescription(description);

		teamPersistence.update(team);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), groupId, userId, Team.class.getName(),
			team.getTeamId(), false, true, true);

		// Role

		roleLocalService.addRole(
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

		resourceLocalService.deleteResource(
			team.getCompanyId(), Team.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, team.getTeamId());

		// Role

		Role role = team.getRole();

		roleLocalService.deleteRole(role);

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
	public List<Team> getGroupTeams(long groupId) {
		return teamPersistence.findByGroupId(groupId);
	}

	@Override
	public Team getTeam(long groupId, String name) throws PortalException {
		return teamPersistence.findByG_N(groupId, name);
	}

	@Override
	public List<Team> getUserTeams(long userId, long groupId) {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersTeams", userId);

		return search(
			groupId, null, null, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<Team> search(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator obc) {

		return teamFinder.findByG_N_D(
			groupId, name, description, params, start, end, obc);
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

		Date now = new Date();

		Team team = teamPersistence.findByPrimaryKey(teamId);

		validate(teamId, team.getGroupId(), name);

		team.setModifiedDate(now);
		team.setName(name);
		team.setDescription(description);

		teamPersistence.update(team);

		return team;
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

}