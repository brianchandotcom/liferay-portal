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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides the local service interface for Team. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface TeamLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamLocalServiceUtil} to access the team local service. Add custom service methods to <code>com.liferay.portal.service.impl.TeamLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Team addTeam(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the team to the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public Team addTeam(Team team);

	public void addUserGroupTeam(long userGroupId, long teamId);

	public void addUserGroupTeam(long userGroupId, Team team);

	public void addUserGroupTeams(long userGroupId, List<Team> teams);

	public void addUserGroupTeams(long userGroupId, long[] teamIds);

	public void addUserTeam(long userId, long teamId);

	public void addUserTeam(long userId, Team team);

	public void addUserTeams(long userId, List<Team> teams);

	public void addUserTeams(long userId, long[] teamIds);

	public void clearUserGroupTeams(long userGroupId);

	public void clearUserTeams(long userId);

	/**
	 * Creates a new team with the primary key. Does not add the team to the database.
	 *
	 * @param teamId the primary key for the new team
	 * @return the new team
	 */
	@Transactional(enabled = false)
	public Team createTeam(long teamId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamId the primary key of the team
	 * @return the team that was removed
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public Team deleteTeam(long teamId) throws PortalException;

	/**
	 * Deletes the team from the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	public Team deleteTeam(Team team) throws PortalException;

	public void deleteTeams(long groupId) throws PortalException;

	public void deleteUserGroupTeam(long userGroupId, long teamId);

	public void deleteUserGroupTeam(long userGroupId, Team team);

	public void deleteUserGroupTeams(long userGroupId, List<Team> teams);

	public void deleteUserGroupTeams(long userGroupId, long[] teamIds);

	public void deleteUserTeam(long userId, long teamId);

	public void deleteUserTeam(long userId, Team team);

	public void deleteUserTeams(long userId, List<Team> teams);

	public void deleteUserTeams(long userId, long[] teamIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team fetchTeam(long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team fetchTeam(long groupId, String name);

	/**
	 * Returns the team matching the UUID and group.
	 *
	 * @param uuid the team's UUID
	 * @param groupId the primary key of the group
	 * @return the matching team, or <code>null</code> if a matching team could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team fetchTeamByUuidAndGroupId(String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getGroupTeams(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupTeamsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the team with the primary key.
	 *
	 * @param teamId the primary key of the team
	 * @return the team
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team getTeam(long teamId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team getTeam(long groupId, String name) throws PortalException;

	/**
	 * Returns the team matching the UUID and group.
	 *
	 * @param uuid the team's UUID
	 * @param groupId the primary key of the group
	 * @return the matching team
	 * @throws PortalException if a matching team could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Team getTeamByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of teams
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getTeams(int start, int end);

	/**
	 * Returns all the teams matching the UUID and company.
	 *
	 * @param uuid the UUID of the teams
	 * @param companyId the primary key of the company
	 * @return the matching teams, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getTeamsByUuidAndCompanyId(String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getTeamsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Team> orderByComparator);

	/**
	 * Returns the number of teams.
	 *
	 * @return the number of teams
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTeamsCount();

	/**
	 * Returns the userGroupIds of the user groups associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userGroupIds of user groups associated with the team
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserGroupPrimaryKeys(long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserGroupTeams(long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserGroupTeams(long userGroupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserGroupTeams(
		long userGroupId, int start, int end,
		OrderByComparator<Team> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserGroupTeamsCount(long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserOrUserGroupTeams(long groupId, long userId);

	/**
	 * Returns the userIds of the users associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userIds of users associated with the team
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserPrimaryKeys(long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserTeams(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserTeams(long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserTeams(
		long userId, int start, int end,
		OrderByComparator<Team> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> getUserTeams(long userId, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserTeamsCount(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupTeam(long userGroupId, long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupTeams(long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserTeam(long userId, long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserTeams(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Team> search(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params);

	public void setUserGroupTeams(long userGroupId, long[] teamIds);

	public void setUserTeams(long userId, long[] teamIds);

	public Team updateTeam(long teamId, String name, String description)
		throws PortalException;

	/**
	 * Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public Team updateTeam(Team team);

}