/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.TeamImpl;
import com.liferay.portal.model.impl.TeamModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the team service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TeamPersistence
 * @see TeamUtil
 * @generated
 */
public class TeamPersistenceImpl extends BasePersistenceImpl<Team>
	implements TeamPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TeamUtil} to access the team persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TeamImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, TeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, TeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			TeamModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, TeamImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			TeamModelImpl.GROUPID_COLUMN_BITMASK |
			TeamModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, TeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, TeamImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the team in the entity cache if it is enabled.
	 *
	 * @param team the team
	 */
	public void cacheResult(Team team) {
		EntityCacheUtil.putResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamImpl.class, team.getPrimaryKey(), team);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] { Long.valueOf(team.getGroupId()), team.getName() },
			team);

		team.resetOriginalValues();
	}

	/**
	 * Caches the teams in the entity cache if it is enabled.
	 *
	 * @param teams the teams
	 */
	public void cacheResult(List<Team> teams) {
		for (Team team : teams) {
			if (EntityCacheUtil.getResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
						TeamImpl.class, team.getPrimaryKey()) == null) {
				cacheResult(team);
			}
			else {
				team.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all teams.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TeamImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TeamImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the team.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Team team) {
		EntityCacheUtil.removeResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamImpl.class, team.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(team);
	}

	@Override
	public void clearCache(List<Team> teams) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Team team : teams) {
			EntityCacheUtil.removeResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
				TeamImpl.class, team.getPrimaryKey());

			clearUniqueFindersCache(team);
		}
	}

	protected void clearUniqueFindersCache(Team team) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] { Long.valueOf(team.getGroupId()), team.getName() });
	}

	/**
	 * Creates a new team with the primary key. Does not add the team to the database.
	 *
	 * @param teamId the primary key for the new team
	 * @return the new team
	 */
	public Team create(long teamId) {
		Team team = new TeamImpl();

		team.setNew(true);
		team.setPrimaryKey(teamId);

		return team;
	}

	/**
	 * Removes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamId the primary key of the team
	 * @return the team that was removed
	 * @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team remove(long teamId) throws NoSuchTeamException, SystemException {
		return remove(Long.valueOf(teamId));
	}

	/**
	 * Removes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the team
	 * @return the team that was removed
	 * @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Team remove(Serializable primaryKey)
		throws NoSuchTeamException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Team team = (Team)session.get(TeamImpl.class, primaryKey);

			if (team == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTeamException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(team);
		}
		catch (NoSuchTeamException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Team removeImpl(Team team) throws SystemException {
		team = toUnwrappedModel(team);

		try {
			clearUsers.clear(team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}

		try {
			clearUserGroups.clear(team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (team.isCachedModel()) {
				team = (Team)session.get(TeamImpl.class, team.getPrimaryKeyObj());
			}

			if (team != null) {
				session.delete(team);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (team != null) {
			clearCache(team);
		}

		return team;
	}

	@Override
	public Team updateImpl(com.liferay.portal.model.Team team)
		throws SystemException {
		team = toUnwrappedModel(team);

		boolean isNew = team.isNew();

		TeamModelImpl teamModelImpl = (TeamModelImpl)team;

		Session session = null;

		try {
			session = openSession();

			if (team.isNew()) {
				session.save(team);

				team.setNew(false);
			}
			else {
				session.merge(team);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !TeamModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((teamModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(teamModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { Long.valueOf(teamModelImpl.getGroupId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
			TeamImpl.class, team.getPrimaryKey(), team);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] { Long.valueOf(team.getGroupId()), team.getName() },
				team);
		}
		else {
			if ((teamModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(teamModelImpl.getOriginalGroupId()),
						
						teamModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
					new Object[] { Long.valueOf(team.getGroupId()), team.getName() },
					team);
			}
		}

		return team;
	}

	protected Team toUnwrappedModel(Team team) {
		if (team instanceof TeamImpl) {
			return team;
		}

		TeamImpl teamImpl = new TeamImpl();

		teamImpl.setNew(team.isNew());
		teamImpl.setPrimaryKey(team.getPrimaryKey());

		teamImpl.setTeamId(team.getTeamId());
		teamImpl.setCompanyId(team.getCompanyId());
		teamImpl.setUserId(team.getUserId());
		teamImpl.setUserName(team.getUserName());
		teamImpl.setCreateDate(team.getCreateDate());
		teamImpl.setModifiedDate(team.getModifiedDate());
		teamImpl.setGroupId(team.getGroupId());
		teamImpl.setName(team.getName());
		teamImpl.setDescription(team.getDescription());

		return teamImpl;
	}

	/**
	 * Returns the team with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the team
	 * @return the team
	 * @throws com.liferay.portal.NoSuchModelException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Team findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the team with the primary key or throws a {@link com.liferay.portal.NoSuchTeamException} if it could not be found.
	 *
	 * @param teamId the primary key of the team
	 * @return the team
	 * @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team findByPrimaryKey(long teamId)
		throws NoSuchTeamException, SystemException {
		Team team = fetchByPrimaryKey(teamId);

		if (team == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + teamId);
			}

			throw new NoSuchTeamException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				teamId);
		}

		return team;
	}

	/**
	 * Returns the team with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the team
	 * @return the team, or <code>null</code> if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Team fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the team with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param teamId the primary key of the team
	 * @return the team, or <code>null</code> if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team fetchByPrimaryKey(long teamId) throws SystemException {
		Team team = (Team)EntityCacheUtil.getResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
				TeamImpl.class, teamId);

		if (team == _nullTeam) {
			return null;
		}

		if (team == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				team = (Team)session.get(TeamImpl.class, Long.valueOf(teamId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (team != null) {
					cacheResult(team);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(TeamModelImpl.ENTITY_CACHE_ENABLED,
						TeamImpl.class, teamId, _nullTeam);
				}

				closeSession(session);
			}
		}

		return team;
	}

	/**
	 * Returns all the teams where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the teams where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of matching teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the teams where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Team> list = (List<Team>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Team team : list) {
				if ((groupId != team.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TEAM_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TeamModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<Team>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first team in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching team
	 * @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTeamException, SystemException {
		Team team = fetchByGroupId_First(groupId, orderByComparator);

		if (team != null) {
			return team;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTeamException(msg.toString());
	}

	/**
	 * Returns the first team in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching team, or <code>null</code> if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Team> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last team in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching team
	 * @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTeamException, SystemException {
		Team team = fetchByGroupId_Last(groupId, orderByComparator);

		if (team != null) {
			return team;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTeamException(msg.toString());
	}

	/**
	 * Returns the last team in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching team, or <code>null</code> if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<Team> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the teams before and after the current team in the ordered set where groupId = &#63;.
	 *
	 * @param teamId the primary key of the current team
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next team
	 * @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team[] findByGroupId_PrevAndNext(long teamId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTeamException, SystemException {
		Team team = findByPrimaryKey(teamId);

		Session session = null;

		try {
			session = openSession();

			Team[] array = new TeamImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, team, groupId,
					orderByComparator, true);

			array[1] = team;

			array[2] = getByGroupId_PrevAndNext(session, team, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Team getByGroupId_PrevAndNext(Session session, Team team,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TEAM_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(TeamModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(team);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Team> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the teams that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching teams that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the teams that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of matching teams that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the teams that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching teams that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TEAM_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(TeamModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(TeamModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Team.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, TeamImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, TeamImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<Team>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the teams before and after the current team in the ordered set of teams that the user has permission to view where groupId = &#63;.
	 *
	 * @param teamId the primary key of the current team
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next team
	 * @throws com.liferay.portal.NoSuchTeamException if a team with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team[] filterFindByGroupId_PrevAndNext(long teamId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTeamException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(teamId, groupId, orderByComparator);
		}

		Team team = findByPrimaryKey(teamId);

		Session session = null;

		try {
			session = openSession();

			Team[] array = new TeamImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, team, groupId,
					orderByComparator, true);

			array[1] = team;

			array[2] = filterGetByGroupId_PrevAndNext(session, team, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Team filterGetByGroupId_PrevAndNext(Session session, Team team,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TEAM_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(TeamModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(TeamModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Team.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, TeamImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, TeamImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(team);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Team> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the team where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchTeamException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching team
	 * @throws com.liferay.portal.NoSuchTeamException if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team findByG_N(long groupId, String name)
		throws NoSuchTeamException, SystemException {
		Team team = fetchByG_N(groupId, name);

		if (team == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTeamException(msg.toString());
		}

		return team;
	}

	/**
	 * Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching team, or <code>null</code> if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team fetchByG_N(long groupId, String name) throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching team, or <code>null</code> if a matching team could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Team fetchByG_N(long groupId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof Team) {
			Team team = (Team)result;

			if ((groupId != team.getGroupId()) ||
					!Validator.equals(name, team.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_TEAM_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			query.append(TeamModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<Team> list = q.list();

				result = list;

				Team team = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					team = list.get(0);

					cacheResult(team);

					if ((team.getGroupId() != groupId) ||
							(team.getName() == null) ||
							!team.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, team);
					}
				}

				return team;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Team)result;
			}
		}
	}

	/**
	 * Returns all the teams.
	 *
	 * @return the teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams
	 * @throws SystemException if a system exception occurred
	 */
	public List<Team> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Team> list = (List<Team>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TEAM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TEAM.concat(TeamModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Team>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Team>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the teams where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (Team team : findByGroupId(groupId)) {
			remove(team);
		}
	}

	/**
	 * Removes the team where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the team that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Team removeByG_N(long groupId, String name)
		throws NoSuchTeamException, SystemException {
		Team team = findByG_N(groupId, name);

		return remove(team);
	}

	/**
	 * Removes all the teams from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Team team : findAll()) {
			remove(team);
		}
	}

	/**
	 * Returns the number of teams where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching teams
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TEAM_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of teams that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching teams that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_TEAM_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Team.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of teams where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching teams
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TEAM_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of teams.
	 *
	 * @return the number of teams
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TEAM);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the users associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @return the users associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the team.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the team
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of users associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS,
			com.liferay.portal.model.impl.UserImpl.class,
			TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the users associated with the team.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the team
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERS,
						finderArgs);
				}
				else {
					userPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Long.class,
			TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getUsersSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of users associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @return the number of users associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Boolean.class,
			TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the team; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, userPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the team has any users associated with it.
	 *
	 * @param pk the primary key of the team to check for associations with users
	 * @return <code>true</code> if the team has any users associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Clears all associations between the team and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team to clear the associated users from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userPKs the primary keys of the users to be associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.remove(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param users the users to be associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Returns all the user groups associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @return the user groups associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the team.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the team
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of user groups associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS,
			com.liferay.portal.model.impl.UserGroupImpl.class,
			TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME, "getUserGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERGROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the team.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the team
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERGROUPS.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERGROUPS.concat(com.liferay.portal.model.impl.UserGroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs);
				}
				else {
					userGroupPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS, Long.class,
			TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME,
			"getUserGroupsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERGROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of user groups associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @return the number of user groups associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			TeamModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS, Boolean.class,
			TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME,
			"containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user group is associated with the team.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the team; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, userGroupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the team has any user groups associated with it.
	 *
	 * @param pk the primary key of the team to check for associations with user groups
	 * @return <code>true</code> if the team has any user groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Clears all associations between the team and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team to clear the associated user groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroupPKs the primary keys of the user groups to be associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			Set<Long> userGroupPKSet = SetUtil.fromArray(userGroupPKs);

			List<com.liferay.portal.model.UserGroup> userGroups = getUserGroups(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				if (!userGroupPKSet.remove(userGroup.getPrimaryKey())) {
					removeUserGroup.remove(pk, userGroup.getPrimaryKey());
				}
			}

			for (Long userGroupPK : userGroupPKSet) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the team
	 * @param userGroups the user groups to be associated with the team
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(TeamModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Initializes the team persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Team")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Team>> listenersList = new ArrayList<ModelListener<Team>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Team>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsUser = new ContainsUser();

		addUser = new AddUser();
		clearUsers = new ClearUsers();
		removeUser = new RemoveUser();

		containsUserGroup = new ContainsUserGroup();

		addUserGroup = new AddUserGroup();
		clearUserGroups = new ClearUserGroups();
		removeUserGroup = new RemoveUserGroup();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(TeamImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;

	protected class ContainsUser {
		protected ContainsUser() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long teamId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(teamId), new Long(userId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUser {
		protected AddUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Teams (teamId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long teamId, long userId) throws SystemException {
			if (!containsUser.contains(teamId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Team> listener : listeners) {
					listener.onBeforeAddAssociation(teamId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Team.class.getName(), teamId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(teamId), new Long(userId)
					});

				for (ModelListener<Team> listener : listeners) {
					listener.onAfterAddAssociation(teamId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Team.class.getName(), teamId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUsers {
		protected ClearUsers() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE teamId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long teamId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(teamId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Team> listener : listeners) {
						listener.onBeforeRemoveAssociation(teamId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Team.class.getName(), teamId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(teamId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Team> listener : listeners) {
						listener.onAfterRemoveAssociation(teamId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Team.class.getName(), teamId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE teamId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long teamId, long userId)
			throws SystemException {
			if (containsUser.contains(teamId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Team> listener : listeners) {
					listener.onBeforeRemoveAssociation(teamId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Team.class.getName(), teamId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(teamId), new Long(userId)
					});

				for (ModelListener<Team> listener : listeners) {
					listener.onAfterRemoveAssociation(teamId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Team.class.getName(), teamId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long teamId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(teamId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO UserGroups_Teams (teamId, userGroupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long teamId, long userGroupId)
			throws SystemException {
			if (!containsUserGroup.contains(teamId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Team> listener : listeners) {
					listener.onBeforeAddAssociation(teamId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						Team.class.getName(), teamId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(teamId), new Long(userGroupId)
					});

				for (ModelListener<Team> listener : listeners) {
					listener.onAfterAddAssociation(teamId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						Team.class.getName(), teamId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM UserGroups_Teams WHERE teamId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long teamId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(teamId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Team> listener : listeners) {
						listener.onBeforeRemoveAssociation(teamId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							Team.class.getName(), teamId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(teamId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Team> listener : listeners) {
						listener.onAfterRemoveAssociation(teamId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onAfterRemoveAssociation(userGroup.getPrimaryKey(),
							Team.class.getName(), teamId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM UserGroups_Teams WHERE teamId = ? AND userGroupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long teamId, long userGroupId)
			throws SystemException {
			if (containsUserGroup.contains(teamId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Team> listener : listeners) {
					listener.onBeforeRemoveAssociation(teamId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						Team.class.getName(), teamId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(teamId), new Long(userGroupId)
					});

				for (ModelListener<Team> listener : listeners) {
					listener.onAfterRemoveAssociation(teamId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						Team.class.getName(), teamId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_TEAM = "SELECT team FROM Team team";
	private static final String _SQL_SELECT_TEAM_WHERE = "SELECT team FROM Team team WHERE ";
	private static final String _SQL_COUNT_TEAM = "SELECT COUNT(team) FROM Team team";
	private static final String _SQL_COUNT_TEAM_WHERE = "SELECT COUNT(team) FROM Team team WHERE ";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Teams ON (Users_Teams.userId = User_.userId) WHERE (Users_Teams.teamId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE teamId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE teamId = ? AND userId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN UserGroups_Teams ON (UserGroups_Teams.userGroupId = UserGroup.userGroupId) WHERE (UserGroups_Teams.teamId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM UserGroups_Teams WHERE teamId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM UserGroups_Teams WHERE teamId = ? AND userGroupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "team.groupId = ?";
	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "team.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "team.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "team.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(team.name IS NULL OR team.name = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "team.teamId";
	private static final String _FILTER_SQL_SELECT_TEAM_WHERE = "SELECT DISTINCT {team.*} FROM Team team WHERE ";
	private static final String _FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Team.*} FROM (SELECT DISTINCT team.teamId FROM Team team WHERE ";
	private static final String _FILTER_SQL_SELECT_TEAM_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Team ON TEMP_TABLE.teamId = Team.teamId";
	private static final String _FILTER_SQL_COUNT_TEAM_WHERE = "SELECT COUNT(DISTINCT team.teamId) AS COUNT_VALUE FROM Team team WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "team";
	private static final String _FILTER_ENTITY_TABLE = "Team";
	private static final String _ORDER_BY_ENTITY_ALIAS = "team.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Team.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Team exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Team exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(TeamPersistenceImpl.class);
	private static Team _nullTeam = new TeamImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Team> toCacheModel() {
				return _nullTeamCacheModel;
			}
		};

	private static CacheModel<Team> _nullTeamCacheModel = new CacheModel<Team>() {
			public Team toEntityModel() {
				return _nullTeam;
			}
		};
}