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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the team service. This utility wraps {@link TeamPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TeamPersistence
 * @see TeamPersistenceImpl
 * @generated
 */
@ProviderType
public class TeamUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Team team) {
		getPersistence().clearCache(team);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Team> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Team> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Team> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Team update(Team team) {
		return getPersistence().update(team);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Team update(Team team, ServiceContext serviceContext) {
		return getPersistence().update(team, serviceContext);
	}

	/**
	* Returns all the teams where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching teams
	*/
	public static java.util.List<com.liferay.portal.model.Team> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the teams where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams
	*/
	public static java.util.List<com.liferay.portal.model.Team> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the teams where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams
	*/
	public static java.util.List<com.liferay.portal.model.Team> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team
	* @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team, or <code>null</code> if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team
	* @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team, or <code>null</code> if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the teams before and after the current team in the ordered set where groupId = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	*/
	public static com.liferay.portal.model.Team[] findByGroupId_PrevAndNext(
		long teamId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(teamId, groupId, orderByComparator);
	}

	/**
	* Returns all the teams that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching teams that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Team> filterFindByGroupId(
		long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the teams that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Team> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the teams that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Team> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the teams before and after the current team in the ordered set of teams that the user has permission to view where groupId = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	*/
	public static com.liferay.portal.model.Team[] filterFindByGroupId_PrevAndNext(
		long teamId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(teamId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the teams where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of teams where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching teams
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of teams that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching teams that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the team where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchTeamException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching team
	* @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team findByG_N(long groupId,
		java.lang.String name) throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public static com.liferay.portal.model.Team fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the team where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the team that was removed
	*/
	public static com.liferay.portal.model.Team removeByG_N(long groupId,
		java.lang.String name) throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of teams where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching teams
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Caches the team in the entity cache if it is enabled.
	*
	* @param team the team
	*/
	public static void cacheResult(com.liferay.portal.model.Team team) {
		getPersistence().cacheResult(team);
	}

	/**
	* Caches the teams in the entity cache if it is enabled.
	*
	* @param teams the teams
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Team> teams) {
		getPersistence().cacheResult(teams);
	}

	/**
	* Creates a new team with the primary key. Does not add the team to the database.
	*
	* @param teamId the primary key for the new team
	* @return the new team
	*/
	public static com.liferay.portal.model.Team create(long teamId) {
		return getPersistence().create(teamId);
	}

	/**
	* Removes the team with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param teamId the primary key of the team
	* @return the team that was removed
	* @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	*/
	public static com.liferay.portal.model.Team remove(long teamId)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().remove(teamId);
	}

	public static com.liferay.portal.model.Team updateImpl(
		com.liferay.portal.model.Team team) {
		return getPersistence().updateImpl(team);
	}

	/**
	* Returns the team with the primary key or throws a {@link com.liferay.portal.NoSuchTeamException} if it could not be found.
	*
	* @param teamId the primary key of the team
	* @return the team
	* @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	*/
	public static com.liferay.portal.model.Team findByPrimaryKey(long teamId)
		throws com.liferay.portal.NoSuchTeamException {
		return getPersistence().findByPrimaryKey(teamId);
	}

	/**
	* Returns the team with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param teamId the primary key of the team
	* @return the team, or <code>null</code> if a team with the primary key could not be found
	*/
	public static com.liferay.portal.model.Team fetchByPrimaryKey(long teamId) {
		return getPersistence().fetchByPrimaryKey(teamId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Team> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the teams.
	*
	* @return the teams
	*/
	public static java.util.List<com.liferay.portal.model.Team> findAll() {
		return getPersistence().findAll();
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
	public static java.util.List<com.liferay.portal.model.Team> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams
	*/
	public static java.util.List<com.liferay.portal.model.Team> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the teams from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of teams.
	*
	* @return the number of teams
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of users associated with the team.
	*
	* @param pk the primary key of the team
	* @return long[] of the primaryKeys of users associated with the team
	*/
	public static long[] getUserPrimaryKeys(long pk) {
		return getPersistence().getUserPrimaryKeys(pk);
	}

	/**
	* Returns all the users associated with the team.
	*
	* @param pk the primary key of the team
	* @return the users associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of users associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end) {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the team.
	*
	* @param pk the primary key of the team
	* @return the number of users associated with the team
	*/
	public static int getUsersSize(long pk) {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the team.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the team; <code>false</code> otherwise
	*/
	public static boolean containsUser(long pk, long userPK) {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the team has any users associated with it.
	*
	* @param pk the primary key of the team to check for associations with users
	* @return <code>true</code> if the team has any users associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUsers(long pk) {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	*/
	public static void addUser(long pk, long userPK) {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param user the user
	*/
	public static void addUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users
	*/
	public static void addUsers(long pk, long[] userPKs) {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users
	*/
	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the team and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team to clear the associated users from
	*/
	public static void clearUsers(long pk) {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	*/
	public static void removeUser(long pk, long userPK) {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param user the user
	*/
	public static void removeUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users
	*/
	public static void removeUsers(long pk, long[] userPKs) {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users
	*/
	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users to be associated with the team
	*/
	public static void setUsers(long pk, long[] userPKs) {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users to be associated with the team
	*/
	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().setUsers(pk, users);
	}

	/**
	* Returns the primaryKeys of user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return long[] of the primaryKeys of user groups associated with the team
	*/
	public static long[] getUserGroupPrimaryKeys(long pk) {
		return getPersistence().getUserGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return the user groups associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) {
		return getPersistence().getUserGroups(pk);
	}

	/**
	* Returns a range of all the user groups associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of user groups associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end) {
		return getPersistence().getUserGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the user groups associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the team
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().getUserGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return the number of user groups associated with the team
	*/
	public static int getUserGroupsSize(long pk) {
		return getPersistence().getUserGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the user group is associated with the team.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the team; <code>false</code> otherwise
	*/
	public static boolean containsUserGroup(long pk, long userGroupPK) {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	/**
	* Returns <code>true</code> if the team has any user groups associated with it.
	*
	* @param pk the primary key of the team to check for associations with user groups
	* @return <code>true</code> if the team has any user groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUserGroups(long pk) {
		return getPersistence().containsUserGroups(pk);
	}

	/**
	* Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	*/
	public static void addUserGroup(long pk, long userGroupPK) {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	/**
	* Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroup the user group
	*/
	public static void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().addUserGroup(pk, userGroup);
	}

	/**
	* Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void addUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	/**
	* Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups
	*/
	public static void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().addUserGroups(pk, userGroups);
	}

	/**
	* Clears all associations between the team and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team to clear the associated user groups from
	*/
	public static void clearUserGroups(long pk) {
		getPersistence().clearUserGroups(pk);
	}

	/**
	* Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	*/
	public static void removeUserGroup(long pk, long userGroupPK) {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	/**
	* Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroup the user group
	*/
	public static void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	/**
	* Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void removeUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	/**
	* Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups
	*/
	public static void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	/**
	* Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups to be associated with the team
	*/
	public static void setUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	/**
	* Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups to be associated with the team
	*/
	public static void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().setUserGroups(pk, userGroups);
	}

	public static TeamPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TeamPersistence)PortalBeanLocatorUtil.locate(TeamPersistence.class.getName());

			ReferenceRegistry.registerReference(TeamUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(TeamPersistence persistence) {
	}

	private static TeamPersistence _persistence;
}