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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for Team. This utility wraps
 * <code>com.liferay.portal.service.impl.TeamLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalService
 * @generated
 */
public class TeamLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.TeamLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Team addTeam(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().addTeam(
			userId, groupId, name, description, serviceContext);
	}

	/**
	 * Adds the team to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TeamLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param team the team
	 * @return the team that was added
	 */
	public static Team addTeam(Team team) {
		return getService().addTeam(team);
	}

	public static void addUserGroupTeam(long userGroupId, long teamId) {
		getService().addUserGroupTeam(userGroupId, teamId);
	}

	public static void addUserGroupTeam(long userGroupId, Team team) {
		getService().addUserGroupTeam(userGroupId, team);
	}

	public static void addUserGroupTeams(long userGroupId, List<Team> teams) {
		getService().addUserGroupTeams(userGroupId, teams);
	}

	public static void addUserGroupTeams(long userGroupId, long[] teamIds) {
		getService().addUserGroupTeams(userGroupId, teamIds);
	}

	public static void addUserTeam(long userId, long teamId) {
		getService().addUserTeam(userId, teamId);
	}

	public static void addUserTeam(long userId, Team team) {
		getService().addUserTeam(userId, team);
	}

	public static void addUserTeams(long userId, List<Team> teams) {
		getService().addUserTeams(userId, teams);
	}

	public static void addUserTeams(long userId, long[] teamIds) {
		getService().addUserTeams(userId, teamIds);
	}

	public static void clearUserGroupTeams(long userGroupId) {
		getService().clearUserGroupTeams(userGroupId);
	}

	public static void clearUserTeams(long userId) {
		getService().clearUserTeams(userId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new team with the primary key. Does not add the team to the database.
	 *
	 * @param teamId the primary key for the new team
	 * @return the new team
	 */
	public static Team createTeam(long teamId) {
		return getService().createTeam(teamId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TeamLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param teamId the primary key of the team
	 * @return the team that was removed
	 * @throws PortalException if a team with the primary key could not be found
	 */
	public static Team deleteTeam(long teamId) throws PortalException {
		return getService().deleteTeam(teamId);
	}

	/**
	 * Deletes the team from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TeamLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param team the team
	 * @return the team that was removed
	 * @throws PortalException
	 */
	public static Team deleteTeam(Team team) throws PortalException {
		return getService().deleteTeam(team);
	}

	public static void deleteTeams(long groupId) throws PortalException {
		getService().deleteTeams(groupId);
	}

	public static void deleteUserGroupTeam(long userGroupId, long teamId) {
		getService().deleteUserGroupTeam(userGroupId, teamId);
	}

	public static void deleteUserGroupTeam(long userGroupId, Team team) {
		getService().deleteUserGroupTeam(userGroupId, team);
	}

	public static void deleteUserGroupTeams(
		long userGroupId, List<Team> teams) {

		getService().deleteUserGroupTeams(userGroupId, teams);
	}

	public static void deleteUserGroupTeams(long userGroupId, long[] teamIds) {
		getService().deleteUserGroupTeams(userGroupId, teamIds);
	}

	public static void deleteUserTeam(long userId, long teamId) {
		getService().deleteUserTeam(userId, teamId);
	}

	public static void deleteUserTeam(long userId, Team team) {
		getService().deleteUserTeam(userId, team);
	}

	public static void deleteUserTeams(long userId, List<Team> teams) {
		getService().deleteUserTeams(userId, teams);
	}

	public static void deleteUserTeams(long userId, long[] teamIds) {
		getService().deleteUserTeams(userId, teamIds);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static Team fetchTeam(long teamId) {
		return getService().fetchTeam(teamId);
	}

	public static Team fetchTeam(long groupId, String name) {
		return getService().fetchTeam(groupId, name);
	}

	/**
	 * Returns the team matching the UUID and group.
	 *
	 * @param uuid the team's UUID
	 * @param groupId the primary key of the group
	 * @return the matching team, or <code>null</code> if a matching team could not be found
	 */
	public static Team fetchTeamByUuidAndGroupId(String uuid, long groupId) {
		return getService().fetchTeamByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static List<Team> getGroupTeams(long groupId) {
		return getService().getGroupTeams(groupId);
	}

	public static int getGroupTeamsCount(long groupId) {
		return getService().getGroupTeamsCount(groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the team with the primary key.
	 *
	 * @param teamId the primary key of the team
	 * @return the team
	 * @throws PortalException if a team with the primary key could not be found
	 */
	public static Team getTeam(long teamId) throws PortalException {
		return getService().getTeam(teamId);
	}

	public static Team getTeam(long groupId, String name)
		throws PortalException {

		return getService().getTeam(groupId, name);
	}

	/**
	 * Returns the team matching the UUID and group.
	 *
	 * @param uuid the team's UUID
	 * @param groupId the primary key of the group
	 * @return the matching team
	 * @throws PortalException if a matching team could not be found
	 */
	public static Team getTeamByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return getService().getTeamByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of teams
	 */
	public static List<Team> getTeams(int start, int end) {
		return getService().getTeams(start, end);
	}

	/**
	 * Returns all the teams matching the UUID and company.
	 *
	 * @param uuid the UUID of the teams
	 * @param companyId the primary key of the company
	 * @return the matching teams, or an empty list if no matches were found
	 */
	public static List<Team> getTeamsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getTeamsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of teams matching the UUID and company.
	 *
	 * @param uuid the UUID of the teams
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching teams, or an empty list if no matches were found
	 */
	public static List<Team> getTeamsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Team> orderByComparator) {

		return getService().getTeamsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of teams.
	 *
	 * @return the number of teams
	 */
	public static int getTeamsCount() {
		return getService().getTeamsCount();
	}

	/**
	 * Returns the userGroupIds of the user groups associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userGroupIds of user groups associated with the team
	 */
	public static long[] getUserGroupPrimaryKeys(long teamId) {
		return getService().getUserGroupPrimaryKeys(teamId);
	}

	public static List<Team> getUserGroupTeams(long userGroupId) {
		return getService().getUserGroupTeams(userGroupId);
	}

	public static List<Team> getUserGroupTeams(
		long userGroupId, int start, int end) {

		return getService().getUserGroupTeams(userGroupId, start, end);
	}

	public static List<Team> getUserGroupTeams(
		long userGroupId, int start, int end,
		OrderByComparator<Team> orderByComparator) {

		return getService().getUserGroupTeams(
			userGroupId, start, end, orderByComparator);
	}

	public static int getUserGroupTeamsCount(long userGroupId) {
		return getService().getUserGroupTeamsCount(userGroupId);
	}

	public static List<Team> getUserOrUserGroupTeams(
		long groupId, long userId) {

		return getService().getUserOrUserGroupTeams(groupId, userId);
	}

	/**
	 * Returns the userIds of the users associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userIds of users associated with the team
	 */
	public static long[] getUserPrimaryKeys(long teamId) {
		return getService().getUserPrimaryKeys(teamId);
	}

	public static List<Team> getUserTeams(long userId) {
		return getService().getUserTeams(userId);
	}

	public static List<Team> getUserTeams(long userId, int start, int end) {
		return getService().getUserTeams(userId, start, end);
	}

	public static List<Team> getUserTeams(
		long userId, int start, int end,
		OrderByComparator<Team> orderByComparator) {

		return getService().getUserTeams(userId, start, end, orderByComparator);
	}

	public static List<Team> getUserTeams(long userId, long groupId) {
		return getService().getUserTeams(userId, groupId);
	}

	public static int getUserTeamsCount(long userId) {
		return getService().getUserTeamsCount(userId);
	}

	public static boolean hasUserGroupTeam(long userGroupId, long teamId) {
		return getService().hasUserGroupTeam(userGroupId, teamId);
	}

	public static boolean hasUserGroupTeams(long userGroupId) {
		return getService().hasUserGroupTeams(userGroupId);
	}

	public static boolean hasUserTeam(long userId, long teamId) {
		return getService().hasUserTeam(userId, teamId);
	}

	public static boolean hasUserTeams(long userId) {
		return getService().hasUserTeams(userId);
	}

	public static List<Team> search(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> orderByComparator) {

		return getService().search(
			groupId, name, description, params, start, end, orderByComparator);
	}

	public static int searchCount(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params) {

		return getService().searchCount(groupId, name, description, params);
	}

	public static void setUserGroupTeams(long userGroupId, long[] teamIds) {
		getService().setUserGroupTeams(userGroupId, teamIds);
	}

	public static void setUserTeams(long userId, long[] teamIds) {
		getService().setUserTeams(userId, teamIds);
	}

	public static Team updateTeam(long teamId, String name, String description)
		throws PortalException {

		return getService().updateTeam(teamId, name, description);
	}

	/**
	 * Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TeamLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param team the team
	 * @return the team that was updated
	 */
	public static Team updateTeam(Team team) {
		return getService().updateTeam(team);
	}

	public static TeamLocalService getService() {
		return _service;
	}

	public static void setService(TeamLocalService service) {
		_service = service;
	}

	private static volatile TeamLocalService _service;

}