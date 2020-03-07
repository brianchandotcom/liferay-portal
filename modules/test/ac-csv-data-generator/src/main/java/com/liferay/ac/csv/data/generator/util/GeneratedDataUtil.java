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

package com.liferay.ac.csv.data.generator.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = GeneratedDataUtil.class)
public class GeneratedDataUtil {

	public boolean containsOrganizationKey(String name) {
		return _addedOrganizationMap.containsKey(name);
	}

	public boolean containsRoleKey(String name) {
		return _addedRoleMap.containsKey(name);
	}

	public boolean containsTeamKey(String name) {
		return _addedTeamMap.containsKey(name);
	}

	public boolean containsUserGroupKey(String name) {
		return _addedUserGroupMap.containsKey(name);
	}

	public boolean containsUserKey(String emailAddress) {
		return _addedUserMap.containsKey(emailAddress);
	}

	public void deleteAll() throws PortalException {
		for (Map.Entry<String, User> e : _addedUserMap.entrySet()) {
			if (_preExistingData.contains(e.getValue())) {
				continue;
			}

			_userLocalService.deleteUser(e.getValue());
		}

		for (Map.Entry<String, Organization> e :
				_addedOrganizationMap.entrySet()) {

			if (_preExistingData.contains(e.getValue())) {
				continue;
			}

			_organizationLocalService.deleteOrganization(e.getValue());
		}

		for (Map.Entry<String, Role> e : _addedRoleMap.entrySet()) {
			if (_preExistingData.contains(e.getValue())) {
				continue;
			}

			_roleLocalService.deleteRole(e.getValue());
		}

		for (Map.Entry<String, UserGroup> e : _addedUserGroupMap.entrySet()) {
			if (_preExistingData.contains(e.getValue())) {
				continue;
			}

			_userGroupLocalService.deleteUserGroup(e.getValue());
		}

		for (Map.Entry<String, Team> e : _addedTeamMap.entrySet()) {
			if (_preExistingData.contains(e.getValue())) {
				continue;
			}

			_teamLocalService.deleteTeam(e.getValue());
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getDefaultGroupId() {
		return _defaultGroupId;
	}

	public long getDefaultUserId() {
		return _defaultUserId;
	}

	public Organization getOrganization(String name) {
		return _addedOrganizationMap.get(name);
	}

	public Role getRole(String name) {
		return _addedRoleMap.get(name);
	}

	public Team getTeam(String name) {
		return _addedTeamMap.get(name);
	}

	public UserGroup getUserGroup(String name) {
		return _addedUserGroupMap.get(name);
	}

	public void putOrganization(String name, Organization organization) {
		_addedOrganizationMap.put(name, organization);
	}

	public void putRole(String name, Role role) {
		_addedRoleMap.put(name, role);
	}

	public void putTeam(String name, Team team) {
		_addedTeamMap.put(name, team);
	}

	public void putUser(String emailAddress, User user) {
		_addedUserMap.put(emailAddress, user);
	}

	public void putUserGroup(String name, UserGroup userGroup) {
		_addedUserGroupMap.put(name, userGroup);
	}

	public void setCompanyId(long companyId) throws PortalException {
		_companyId = companyId;

		_defaultUserId = _userLocalService.getUserIdByEmailAddress(
			_companyId, "test@liferay.com");

		List<Long> userActiveGroupIds = _groupLocalService.getActiveGroupIds(
			_defaultUserId);

		_defaultGroupId = userActiveGroupIds.get(0);
	}

	public void setExistingPortalData() {
		List<User> existingUserList = _userLocalService.getUsers(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		existingUserList.parallelStream(
		).forEach(
			user -> {
				if (!containsUserKey(user.getEmailAddress())) {
					_preExistingData.add(user);

					_addedUserMap.put(user.getEmailAddress(), user);
				}
			}
		);

		List<Organization> existingOrgList =
			_organizationLocalService.getOrganizations(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		existingOrgList.parallelStream(
		).forEach(
			org -> {
				if (!containsOrganizationKey(org.getName())) {
					_preExistingData.add(org);

					_addedOrganizationMap.put(org.getName(), org);
				}
			}
		);

		List<UserGroup> existingUserGroupList =
			_userGroupLocalService.getUserGroups(_companyId);

		existingUserGroupList.parallelStream(
		).forEach(
			userGroup -> {
				if (!containsUserGroupKey(userGroup.getName())) {
					_preExistingData.add(userGroup);

					_addedUserGroupMap.put(userGroup.getName(), userGroup);
				}
			}
		);

		List<Role> existingRoleList = _roleLocalService.getRoles(_companyId);

		existingRoleList.parallelStream(
		).forEach(
			role -> {
				if (!containsRoleKey(role.getName())) {
					_preExistingData.add(role);

					_addedRoleMap.put(role.getName(), role);
				}
			}
		);

		List<Team> existingTeamList = _teamLocalService.getTeams(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		existingTeamList.parallelStream(
		).forEach(
			team -> {
				if (!containsTeamKey(team.getName())) {
					_preExistingData.add(team);

					_addedTeamMap.put(team.getName(), team);
				}
			}
		);
	}

	private volatile HashMap<String, Organization> _addedOrganizationMap =
		new HashMap<>();
	private volatile HashMap<String, Role> _addedRoleMap = new HashMap<>();
	private volatile HashMap<String, Team> _addedTeamMap = new HashMap<>();
	private volatile HashMap<String, UserGroup> _addedUserGroupMap =
		new HashMap<>();
	private volatile HashMap<String, User> _addedUserMap = new HashMap<>();
	private long _companyId;
	private long _defaultGroupId;
	private long _defaultUserId;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	private volatile List<Object> _preExistingData = new ArrayList<>();

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}