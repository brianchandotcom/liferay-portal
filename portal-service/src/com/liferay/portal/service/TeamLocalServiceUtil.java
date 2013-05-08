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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Team. This utility wraps
 * {@link com.liferay.portal.service.impl.TeamLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalService
 * @see com.liferay.portal.service.base.TeamLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.TeamLocalServiceImpl
 * @generated
 */
public class TeamLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.TeamLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the team to the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Team addTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTeam(team);
	}

	/**
	* Creates a new team with the primary key. Does not add the team to the database.
	*
	* @param teamId the primary key for the new team
	* @return the new team
	*/
	public static com.liferay.portal.model.Team createTeam(long teamId) {
		return getService().createTeam(teamId);
	}

	/**
	* Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param teamId the primary key of the team
	* @return the team that was removed
	* @throws PortalException if a team with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Team deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTeam(teamId);
	}

	/**
	* Deletes the team from the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Team deleteTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTeam(team);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Team fetchTeam(long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTeam(teamId);
	}

	/**
	* Returns the team with the primary key.
	*
	* @param teamId the primary key of the team
	* @return the team
	* @throws PortalException if a team with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeam(teamId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeams(start, end);
	}

	/**
	* Returns the number of teams.
	*
	* @return the number of teams
	* @throws SystemException if a system exception occurred
	*/
	public static int getTeamsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeamsCount();
	}

	/**
	* Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTeam(team);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserTeam(userId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserTeam(long userId,
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserTeam(userId, team);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserTeams(long userId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserTeams(userId, teamIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserTeams(userId, Teams);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearUserTeams(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearUserTeams(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTeam(userId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserTeam(long userId,
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTeam(userId, team);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserTeams(long userId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTeams(userId, teamIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserTeams(userId, Teams);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId, start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserTeamsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeamsCount(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserTeam(userId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserTeams(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserTeams(userId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setUserTeams(long userId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setUserTeams(userId, teamIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserGroupTeam(long userGroupId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupTeam(userGroupId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupTeam(userGroupId, team);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserGroupTeams(long userGroupId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupTeams(userGroupId, teamIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupTeams(userGroupId, Teams);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearUserGroupTeams(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearUserGroupTeams(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserGroupTeam(long userGroupId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupTeam(userGroupId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupTeam(userGroupId, team);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserGroupTeams(long userGroupId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupTeams(userGroupId, teamIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroupTeams(userGroupId, Teams);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupTeams(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupTeams(userGroupId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserGroupTeams(userGroupId, start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserGroupTeamsCount(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupTeamsCount(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserGroupTeam(long userGroupId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroupTeam(userGroupId, teamId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserGroupTeams(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroupTeams(userGroupId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setUserGroupTeams(long userGroupId, long[] teamIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setUserGroupTeams(userGroupId, teamIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portal.model.Team addTeam(long userId,
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTeam(userId, groupId, name, description);
	}

	public static void deleteTeams(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTeams(groupId);
	}

	public static java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupTeams(groupId);
	}

	public static com.liferay.portal.model.Team getTeam(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeam(groupId, name);
	}

	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Team> search(
		long groupId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(groupId, name, description, params, start, end, obc);
	}

	public static int searchCount(long groupId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(groupId, name, description, params);
	}

	public static com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTeam(teamId, name, description);
	}

	public static TeamLocalService getService() {
		if (_service == null) {
			_service = (TeamLocalService)PortalBeanLocatorUtil.locate(TeamLocalService.class.getName());

			ReferenceRegistry.registerReference(TeamLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(TeamLocalService service) {
	}

	private static TeamLocalService _service;
}