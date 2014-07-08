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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link TeamLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalService
 * @generated
 */
@ProviderType
public class TeamLocalServiceWrapper implements TeamLocalService,
	ServiceWrapper<TeamLocalService> {
	public TeamLocalServiceWrapper(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	/**
	* Adds the team to the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was added
	*/
	@Override
	public com.liferay.portal.model.Team addTeam(
		com.liferay.portal.model.Team team) {
		return _teamLocalService.addTeam(team);
	}

	/**
	* Creates a new team with the primary key. Does not add the team to the database.
	*
	* @param teamId the primary key for the new team
	* @return the new team
	*/
	@Override
	public com.liferay.portal.model.Team createTeam(long teamId) {
		return _teamLocalService.createTeam(teamId);
	}

	/**
	* Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param teamId the primary key of the team
	* @return the team that was removed
	* @throws PortalException if a team with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Team deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.deleteTeam(teamId);
	}

	/**
	* Deletes the team from the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.Team deleteTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.deleteTeam(team);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _teamLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _teamLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _teamLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _teamLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _teamLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _teamLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Team fetchTeam(long teamId) {
		return _teamLocalService.fetchTeam(teamId);
	}

	/**
	* Returns the team with the primary key.
	*
	* @param teamId the primary key of the team
	* @return the team
	* @throws PortalException if a team with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.getTeam(teamId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _teamLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of teams
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Team> getTeams(int start,
		int end) {
		return _teamLocalService.getTeams(start, end);
	}

	/**
	* Returns the number of teams.
	*
	* @return the number of teams
	*/
	@Override
	public int getTeamsCount() {
		return _teamLocalService.getTeamsCount();
	}

	/**
	* Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was updated
	*/
	@Override
	public com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team) {
		return _teamLocalService.updateTeam(team);
	}

	@Override
	public void addUserTeam(long userId, long teamId) {
		_teamLocalService.addUserTeam(userId, teamId);
	}

	@Override
	public void addUserTeam(long userId, com.liferay.portal.model.Team team) {
		_teamLocalService.addUserTeam(userId, team);
	}

	@Override
	public void addUserTeams(long userId, long[] teamIds) {
		_teamLocalService.addUserTeams(userId, teamIds);
	}

	@Override
	public void addUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams) {
		_teamLocalService.addUserTeams(userId, Teams);
	}

	@Override
	public void clearUserTeams(long userId) {
		_teamLocalService.clearUserTeams(userId);
	}

	@Override
	public void deleteUserTeam(long userId, long teamId) {
		_teamLocalService.deleteUserTeam(userId, teamId);
	}

	@Override
	public void deleteUserTeam(long userId, com.liferay.portal.model.Team team) {
		_teamLocalService.deleteUserTeam(userId, team);
	}

	@Override
	public void deleteUserTeams(long userId, long[] teamIds) {
		_teamLocalService.deleteUserTeams(userId, teamIds);
	}

	@Override
	public void deleteUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams) {
		_teamLocalService.deleteUserTeams(userId, Teams);
	}

	/**
	* Returns the userIds of the users associated with the team.
	*
	* @param teamId the teamId of the team
	* @return long[] the userIds of users associated with the team
	*/
	@Override
	public long[] getUserPrimaryKeys(long teamId) {
		return _teamLocalService.getUserPrimaryKeys(teamId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId) {
		return _teamLocalService.getUserTeams(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end) {
		return _teamLocalService.getUserTeams(userId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _teamLocalService.getUserTeams(userId, start, end,
			orderByComparator);
	}

	@Override
	public int getUserTeamsCount(long userId) {
		return _teamLocalService.getUserTeamsCount(userId);
	}

	@Override
	public boolean hasUserTeam(long userId, long teamId) {
		return _teamLocalService.hasUserTeam(userId, teamId);
	}

	@Override
	public boolean hasUserTeams(long userId) {
		return _teamLocalService.hasUserTeams(userId);
	}

	@Override
	public void setUserTeams(long userId, long[] teamIds) {
		_teamLocalService.setUserTeams(userId, teamIds);
	}

	@Override
	public void addUserGroupTeam(long userGroupId, long teamId) {
		_teamLocalService.addUserGroupTeam(userGroupId, teamId);
	}

	@Override
	public void addUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team) {
		_teamLocalService.addUserGroupTeam(userGroupId, team);
	}

	@Override
	public void addUserGroupTeams(long userGroupId, long[] teamIds) {
		_teamLocalService.addUserGroupTeams(userGroupId, teamIds);
	}

	@Override
	public void addUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams) {
		_teamLocalService.addUserGroupTeams(userGroupId, Teams);
	}

	@Override
	public void clearUserGroupTeams(long userGroupId) {
		_teamLocalService.clearUserGroupTeams(userGroupId);
	}

	@Override
	public void deleteUserGroupTeam(long userGroupId, long teamId) {
		_teamLocalService.deleteUserGroupTeam(userGroupId, teamId);
	}

	@Override
	public void deleteUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team) {
		_teamLocalService.deleteUserGroupTeam(userGroupId, team);
	}

	@Override
	public void deleteUserGroupTeams(long userGroupId, long[] teamIds) {
		_teamLocalService.deleteUserGroupTeams(userGroupId, teamIds);
	}

	@Override
	public void deleteUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams) {
		_teamLocalService.deleteUserGroupTeams(userGroupId, Teams);
	}

	/**
	* Returns the userGroupIds of the user groups associated with the team.
	*
	* @param teamId the teamId of the team
	* @return long[] the userGroupIds of user groups associated with the team
	*/
	@Override
	public long[] getUserGroupPrimaryKeys(long teamId) {
		return _teamLocalService.getUserGroupPrimaryKeys(teamId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId) {
		return _teamLocalService.getUserGroupTeams(userGroupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end) {
		return _teamLocalService.getUserGroupTeams(userGroupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _teamLocalService.getUserGroupTeams(userGroupId, start, end,
			orderByComparator);
	}

	@Override
	public int getUserGroupTeamsCount(long userGroupId) {
		return _teamLocalService.getUserGroupTeamsCount(userGroupId);
	}

	@Override
	public boolean hasUserGroupTeam(long userGroupId, long teamId) {
		return _teamLocalService.hasUserGroupTeam(userGroupId, teamId);
	}

	@Override
	public boolean hasUserGroupTeams(long userGroupId) {
		return _teamLocalService.hasUserGroupTeams(userGroupId);
	}

	@Override
	public void setUserGroupTeams(long userGroupId, long[] teamIds) {
		_teamLocalService.setUserGroupTeams(userGroupId, teamIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _teamLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_teamLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.Team addTeam(long userId, long groupId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.addTeam(userId, groupId, name, description);
	}

	@Override
	public void deleteTeams(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_teamLocalService.deleteTeams(groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId) {
		return _teamLocalService.getGroupTeams(groupId);
	}

	@Override
	public com.liferay.portal.model.Team getTeam(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.getTeam(groupId, name);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId) {
		return _teamLocalService.getUserTeams(userId, groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> search(long groupId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> obc) {
		return _teamLocalService.search(groupId, name, description, params,
			start, end, obc);
	}

	@Override
	public int searchCount(long groupId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {
		return _teamLocalService.searchCount(groupId, name, description, params);
	}

	@Override
	public com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _teamLocalService.updateTeam(teamId, name, description);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public TeamLocalService getWrappedTeamLocalService() {
		return _teamLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedTeamLocalService(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	@Override
	public TeamLocalService getWrappedService() {
		return _teamLocalService;
	}

	@Override
	public void setWrappedService(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	private TeamLocalService _teamLocalService;
}