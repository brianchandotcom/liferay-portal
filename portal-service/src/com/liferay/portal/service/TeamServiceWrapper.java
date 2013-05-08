/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link TeamService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       TeamService
 * @generated
 */
public class TeamServiceWrapper implements TeamService,
	ServiceWrapper<TeamService> {
	public TeamServiceWrapper(TeamService teamService) {
		_teamService = teamService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _teamService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_teamService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Team addTeam(long groupId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.addTeam(groupId, name, description);
	}

	public void deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_teamService.deleteTeam(teamId);
	}

	public java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.getGroupTeams(groupId);
	}

	public com.liferay.portal.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.getTeam(teamId);
	}

	public com.liferay.portal.model.Team getTeam(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.getTeam(groupId, name);
	}

	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.getUserTeams(userId);
	}

	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.getUserTeams(userId, groupId);
	}

	public boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.hasUserTeam(userId, teamId);
	}

	public com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamService.updateTeam(teamId, name, description);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public TeamService getWrappedTeamService() {
		return _teamService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedTeamService(TeamService teamService) {
		_teamService = teamService;
	}

	public TeamService getWrappedService() {
		return _teamService;
	}

	public void setWrappedService(TeamService teamService) {
		_teamService = teamService;
	}

	private TeamService _teamService;
}