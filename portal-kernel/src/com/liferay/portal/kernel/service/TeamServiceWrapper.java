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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link TeamService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamService
 * @generated
 */
@ProviderType
public class TeamServiceWrapper implements TeamService,
	ServiceWrapper<TeamService> {
	public TeamServiceWrapper(TeamService teamService) {
		_teamService = teamService;
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link #addTeam(long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Team addTeam(long groupId,
		String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.addTeam(groupId, name, description);
	}

	@Override
	public com.liferay.portal.kernel.model.Team addTeam(long groupId,
		String name, String description, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.addTeam(groupId, name, description, serviceContext);
	}

	@Override
	public void deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_teamService.deleteTeam(teamId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Team> getGroupTeams(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.getGroupTeams(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.getTeam(teamId);
	}

	@Override
	public com.liferay.portal.kernel.model.Team getTeam(long groupId,
		String name) throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.getTeam(groupId, name);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Team> getUserTeams(
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.getUserTeams(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Team> getUserTeams(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.getUserTeams(userId, groupId);
	}

	@Override
	public boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.hasUserTeam(userId, teamId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Team> search(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Team> obc) {
		return _teamService.search(groupId, name, description, params, start,
			end, obc);
	}

	@Override
	public int searchCount(long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params) {
		return _teamService.searchCount(groupId, name, description, params);
	}

	@Override
	public com.liferay.portal.kernel.model.Team updateTeam(long teamId,
		String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamService.updateTeam(teamId, name, description);
	}

	@Override
	public TeamService getWrappedService() {
		return _teamService;
	}

	@Override
	public void setWrappedService(TeamService teamService) {
		_teamService = teamService;
	}

	private TeamService _teamService;
}