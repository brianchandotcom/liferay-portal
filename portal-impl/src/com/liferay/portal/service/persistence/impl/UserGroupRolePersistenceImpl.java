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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserGroupRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.UserGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupRoleModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the user group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupRolePersistenceImpl
	extends BasePersistenceImpl<UserGroupRole>
	implements UserGroupRolePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserGroupRoleUtil</code> to access the user group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserGroupRoleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the user group roles where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupRole userGroupRole : list) {
					if (userId != userGroupRole.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByUserId_First(
			long userId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByUserId_First(
			userId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByUserId_First(
		long userId, OrderByComparator<UserGroupRole> orderByComparator) {

		List<UserGroupRole> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByUserId_Last(
			long userId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByUserId_Last(
			userId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByUserId_Last(
		long userId, OrderByComparator<UserGroupRole> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserGroupRole> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where userId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole[] findByUserId_PrevAndNext(
			long userGroupRoleId, long userId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userGroupRole, userId, orderByComparator, true);

			array[1] = userGroupRole;

			array[2] = getByUserId_PrevAndNext(
				session, userGroupRole, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupRole getByUserId_PrevAndNext(
		Session session, UserGroupRole userGroupRole, long userId,
		OrderByComparator<UserGroupRole> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group roles where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserGroupRole userGroupRole :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByUserId(long userId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserId;

			finderArgs = new Object[] {userId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"userGroupRole.userId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the user group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupRole userGroupRole : list) {
					if (groupId != userGroupRole.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByGroupId_First(
			long groupId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByGroupId_First(
			groupId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByGroupId_First(
		long groupId, OrderByComparator<UserGroupRole> orderByComparator) {

		List<UserGroupRole> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByGroupId_Last(
			long groupId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByGroupId_Last(
		long groupId, OrderByComparator<UserGroupRole> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupRole> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where groupId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole[] findByGroupId_PrevAndNext(
			long userGroupRoleId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, userGroupRole, groupId, orderByComparator, true);

			array[1] = userGroupRole;

			array[2] = getByGroupId_PrevAndNext(
				session, userGroupRole, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupRole getByGroupId_PrevAndNext(
		Session session, UserGroupRole userGroupRole, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group roles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (UserGroupRole userGroupRole :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"userGroupRole.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the user group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByRoleId(long roleId, int start, int end) {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRoleId;
				finderArgs = new Object[] {roleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRoleId;
			finderArgs = new Object[] {roleId, start, end, orderByComparator};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupRole userGroupRole : list) {
					if (roleId != userGroupRole.getRoleId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(roleId);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByRoleId_First(
			long roleId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByRoleId_First(
			roleId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByRoleId_First(
		long roleId, OrderByComparator<UserGroupRole> orderByComparator) {

		List<UserGroupRole> list = findByRoleId(
			roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByRoleId_Last(
			long roleId, OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByRoleId_Last(
			roleId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByRoleId_Last(
		long roleId, OrderByComparator<UserGroupRole> orderByComparator) {

		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupRole> list = findByRoleId(
			roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where roleId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole[] findByRoleId_PrevAndNext(
			long userGroupRoleId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(
				session, userGroupRole, roleId, orderByComparator, true);

			array[1] = userGroupRole;

			array[2] = getByRoleId_PrevAndNext(
				session, userGroupRole, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupRole getByRoleId_PrevAndNext(
		Session session, UserGroupRole userGroupRole, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (UserGroupRole userGroupRole :
				findByRoleId(
					roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByRoleId(long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRoleId;

			finderArgs = new Object[] {roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(roleId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 =
		"userGroupRole.roleId = ?";

	private FinderPath _finderPathWithPaginationFindByU_G;
	private FinderPath _finderPathWithoutPaginationFindByU_G;
	private FinderPath _finderPathCountByU_G;

	/**
	 * Returns all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByU_G(long userId, long groupId) {
		return findByU_G(
			userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end) {

		return findByU_G(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findByU_G(userId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU_G;
				finderArgs = new Object[] {userId, groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU_G;
			finderArgs = new Object[] {
				userId, groupId, start, end, orderByComparator
			};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupRole userGroupRole : list) {
					if ((userId != userGroupRole.getUserId()) ||
						(groupId != userGroupRole.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_USERID_2);

			sb.append(_FINDER_COLUMN_U_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(groupId);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByU_G_First(
			long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByU_G_First(
			userId, groupId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByU_G_First(
		long userId, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		List<UserGroupRole> list = findByU_G(
			userId, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByU_G_Last(
			long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByU_G_Last(
			userId, groupId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByU_G_Last(
		long userId, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		int count = countByU_G(userId, groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupRole> list = findByU_G(
			userId, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole[] findByU_G_PrevAndNext(
			long userGroupRoleId, long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = getByU_G_PrevAndNext(
				session, userGroupRole, userId, groupId, orderByComparator,
				true);

			array[1] = userGroupRole;

			array[2] = getByU_G_PrevAndNext(
				session, userGroupRole, userId, groupId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupRole getByU_G_PrevAndNext(
		Session session, UserGroupRole userGroupRole, long userId, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_U_G_USERID_2);

		sb.append(_FINDER_COLUMN_U_G_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group roles where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByU_G(long userId, long groupId) {
		for (UserGroupRole userGroupRole :
				findByU_G(
					userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByU_G(long userId, long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_G;

			finderArgs = new Object[] {userId, groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_USERID_2);

			sb.append(_FINDER_COLUMN_U_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_G_USERID_2 =
		"userGroupRole.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_GROUPID_2 =
		"userGroupRole.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_R;
	private FinderPath _finderPathWithoutPaginationFindByG_R;
	private FinderPath _finderPathCountByG_R;

	/**
	 * Returns all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByG_R(long groupId, long roleId) {
		return findByG_R(
			groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end) {

		return findByG_R(groupId, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findByG_R(groupId, roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	@Override
	public List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_R;
				finderArgs = new Object[] {groupId, roleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_R;
			finderArgs = new Object[] {
				groupId, roleId, start, end, orderByComparator
			};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupRole userGroupRole : list) {
					if ((groupId != userGroupRole.getGroupId()) ||
						(roleId != userGroupRole.getRoleId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(roleId);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByG_R_First(
			long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByG_R_First(
			groupId, roleId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByG_R_First(
		long groupId, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		List<UserGroupRole> list = findByG_R(
			groupId, roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByG_R_Last(
			long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByG_R_Last(
			groupId, roleId, orderByComparator);

		if (userGroupRole != null) {
			return userGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByG_R_Last(
		long groupId, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		int count = countByG_R(groupId, roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupRole> list = findByG_R(
			groupId, roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole[] findByG_R_PrevAndNext(
			long userGroupRoleId, long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByPrimaryKey(userGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupRole[] array = new UserGroupRoleImpl[3];

			array[0] = getByG_R_PrevAndNext(
				session, userGroupRole, groupId, roleId, orderByComparator,
				true);

			array[1] = userGroupRole;

			array[2] = getByG_R_PrevAndNext(
				session, userGroupRole, groupId, roleId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupRole getByG_R_PrevAndNext(
		Session session, UserGroupRole userGroupRole, long groupId, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group roles where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 */
	@Override
	public void removeByG_R(long groupId, long roleId) {
		for (UserGroupRole userGroupRole :
				findByG_R(
					groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByG_R(long groupId, long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_R;

			finderArgs = new Object[] {groupId, roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(roleId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_R_GROUPID_2 =
		"userGroupRole.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_R_ROLEID_2 =
		"userGroupRole.roleId = ?";

	private FinderPath _finderPathFetchByU_G_R;
	private FinderPath _finderPathCountByU_G_R;

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or throws a <code>NoSuchUserGroupRoleException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole findByU_G_R(long userId, long groupId, long roleId)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByU_G_R(userId, groupId, roleId);

		if (userGroupRole == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", roleId=");
			sb.append(roleId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserGroupRoleException(sb.toString());
		}

		return userGroupRole;
	}

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByU_G_R(long userId, long groupId, long roleId) {
		return fetchByU_G_R(userId, groupId, roleId, true);
	}

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	@Override
	public UserGroupRole fetchByU_G_R(
		long userId, long groupId, long roleId, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {userId, groupId, roleId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_G_R, finderArgs, this);
		}

		if (result instanceof UserGroupRole) {
			UserGroupRole userGroupRole = (UserGroupRole)result;

			if ((userId != userGroupRole.getUserId()) ||
				(groupId != userGroupRole.getGroupId()) ||
				(roleId != userGroupRole.getRoleId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_R_USERID_2);

			sb.append(_FINDER_COLUMN_U_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(groupId);

				queryPos.add(roleId);

				List<UserGroupRole> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_G_R, finderArgs, list);
					}
				}
				else {
					UserGroupRole userGroupRole = list.get(0);

					result = userGroupRole;

					cacheResult(userGroupRole);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (UserGroupRole)result;
		}
	}

	/**
	 * Removes the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the user group role that was removed
	 */
	@Override
	public UserGroupRole removeByU_G_R(long userId, long groupId, long roleId)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = findByU_G_R(userId, groupId, roleId);

		return remove(userGroupRole);
	}

	/**
	 * Returns the number of user group roles where userId = &#63; and groupId = &#63; and roleId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	@Override
	public int countByU_G_R(long userId, long groupId, long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_G_R;

			finderArgs = new Object[] {userId, groupId, roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_USERGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_R_USERID_2);

			sb.append(_FINDER_COLUMN_U_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(groupId);

				queryPos.add(roleId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_G_R_USERID_2 =
		"userGroupRole.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_R_GROUPID_2 =
		"userGroupRole.groupId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_R_ROLEID_2 =
		"userGroupRole.roleId = ?";

	public UserGroupRolePersistenceImpl() {
		setModelClass(UserGroupRole.class);

		setModelImplClass(UserGroupRoleImpl.class);
		setModelPKClass(long.class);

		setTable(UserGroupRoleTable.INSTANCE);
	}

	/**
	 * Caches the user group role in the entity cache if it is enabled.
	 *
	 * @param userGroupRole the user group role
	 */
	@Override
	public void cacheResult(UserGroupRole userGroupRole) {
		if (userGroupRole.getCtCollectionId() != 0) {
			userGroupRole.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey(),
			userGroupRole);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_R,
			new Object[] {
				userGroupRole.getUserId(), userGroupRole.getGroupId(),
				userGroupRole.getRoleId()
			},
			userGroupRole);

		userGroupRole.resetOriginalValues();
	}

	/**
	 * Caches the user group roles in the entity cache if it is enabled.
	 *
	 * @param userGroupRoles the user group roles
	 */
	@Override
	public void cacheResult(List<UserGroupRole> userGroupRoles) {
		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (userGroupRole.getCtCollectionId() != 0) {
				userGroupRole.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					UserGroupRoleImpl.class, userGroupRole.getPrimaryKey()) ==
						null) {

				cacheResult(userGroupRole);
			}
			else {
				userGroupRole.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user group roles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserGroupRoleImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user group role.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserGroupRole userGroupRole) {
		EntityCacheUtil.removeResult(
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((UserGroupRoleModelImpl)userGroupRole, true);
	}

	@Override
	public void clearCache(List<UserGroupRole> userGroupRoles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			EntityCacheUtil.removeResult(
				UserGroupRoleImpl.class, userGroupRole.getPrimaryKey());

			clearUniqueFindersCache(
				(UserGroupRoleModelImpl)userGroupRole, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(UserGroupRoleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UserGroupRoleModelImpl userGroupRoleModelImpl) {

		Object[] args = new Object[] {
			userGroupRoleModelImpl.getUserId(),
			userGroupRoleModelImpl.getGroupId(),
			userGroupRoleModelImpl.getRoleId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_G_R, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_R, args, userGroupRoleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		UserGroupRoleModelImpl userGroupRoleModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				userGroupRoleModelImpl.getUserId(),
				userGroupRoleModelImpl.getGroupId(),
				userGroupRoleModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_R, args);
		}

		if ((userGroupRoleModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_G_R.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				userGroupRoleModelImpl.getOriginalUserId(),
				userGroupRoleModelImpl.getOriginalGroupId(),
				userGroupRoleModelImpl.getOriginalRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_R, args);
		}
	}

	/**
	 * Creates a new user group role with the primary key. Does not add the user group role to the database.
	 *
	 * @param userGroupRoleId the primary key for the new user group role
	 * @return the new user group role
	 */
	@Override
	public UserGroupRole create(long userGroupRoleId) {
		UserGroupRole userGroupRole = new UserGroupRoleImpl();

		userGroupRole.setNew(true);
		userGroupRole.setPrimaryKey(userGroupRoleId);

		userGroupRole.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userGroupRole;
	}

	/**
	 * Removes the user group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role that was removed
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole remove(long userGroupRoleId)
		throws NoSuchUserGroupRoleException {

		return remove((Serializable)userGroupRoleId);
	}

	/**
	 * Removes the user group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user group role
	 * @return the user group role that was removed
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole remove(Serializable primaryKey)
		throws NoSuchUserGroupRoleException {

		Session session = null;

		try {
			session = openSession();

			UserGroupRole userGroupRole = (UserGroupRole)session.get(
				UserGroupRoleImpl.class, primaryKey);

			if (userGroupRole == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserGroupRoleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userGroupRole);
		}
		catch (NoSuchUserGroupRoleException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected UserGroupRole removeImpl(UserGroupRole userGroupRole) {
		if (!CTPersistenceHelperUtil.isRemove(userGroupRole)) {
			return userGroupRole;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userGroupRole)) {
				userGroupRole = (UserGroupRole)session.get(
					UserGroupRoleImpl.class, userGroupRole.getPrimaryKeyObj());
			}

			if (userGroupRole != null) {
				session.delete(userGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userGroupRole != null) {
			clearCache(userGroupRole);
		}

		return userGroupRole;
	}

	@Override
	public UserGroupRole updateImpl(UserGroupRole userGroupRole) {
		boolean isNew = userGroupRole.isNew();

		if (!(userGroupRole instanceof UserGroupRoleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userGroupRole.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userGroupRole);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userGroupRole proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserGroupRole implementation " +
					userGroupRole.getClass());
		}

		UserGroupRoleModelImpl userGroupRoleModelImpl =
			(UserGroupRoleModelImpl)userGroupRole;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(userGroupRole)) {
				if (!isNew) {
					session.evict(
						UserGroupRoleImpl.class,
						userGroupRole.getPrimaryKeyObj());
				}

				session.save(userGroupRole);

				userGroupRole.setNew(false);
			}
			else {
				userGroupRole = (UserGroupRole)session.merge(userGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userGroupRole.getCtCollectionId() != 0) {
			userGroupRole.resetOriginalValues();

			return userGroupRole;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {userGroupRoleModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {userGroupRoleModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {userGroupRoleModelImpl.getRoleId()};

			FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByRoleId, args);

			args = new Object[] {
				userGroupRoleModelImpl.getUserId(),
				userGroupRoleModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_G, args);

			args = new Object[] {
				userGroupRoleModelImpl.getGroupId(),
				userGroupRoleModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_R, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_R, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((userGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupRoleModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {userGroupRoleModelImpl.getUserId()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((userGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userGroupRoleModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {userGroupRoleModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((userGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupRoleModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);

				args = new Object[] {userGroupRoleModelImpl.getRoleId()};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);
			}

			if ((userGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_G.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupRoleModelImpl.getOriginalUserId(),
					userGroupRoleModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_G, args);

				args = new Object[] {
					userGroupRoleModelImpl.getUserId(),
					userGroupRoleModelImpl.getGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_G, args);
			}

			if ((userGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_R.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupRoleModelImpl.getOriginalGroupId(),
					userGroupRoleModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_R, args);

				args = new Object[] {
					userGroupRoleModelImpl.getGroupId(),
					userGroupRoleModelImpl.getRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_R, args);
			}
		}

		EntityCacheUtil.putResult(
			UserGroupRoleImpl.class, userGroupRole.getPrimaryKey(),
			userGroupRole, false);

		clearUniqueFindersCache(userGroupRoleModelImpl, false);
		cacheUniqueFindersCache(userGroupRoleModelImpl);

		userGroupRole.resetOriginalValues();

		return userGroupRole;
	}

	/**
	 * Returns the user group role with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group role
	 * @return the user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserGroupRoleException {

		UserGroupRole userGroupRole = fetchByPrimaryKey(primaryKey);

		if (userGroupRole == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserGroupRoleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userGroupRole;
	}

	/**
	 * Returns the user group role with the primary key or throws a <code>NoSuchUserGroupRoleException</code> if it could not be found.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole findByPrimaryKey(long userGroupRoleId)
		throws NoSuchUserGroupRoleException {

		return findByPrimaryKey((Serializable)userGroupRoleId);
	}

	/**
	 * Returns the user group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group role
	 * @return the user group role, or <code>null</code> if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(UserGroupRole.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		UserGroupRole userGroupRole = null;

		Session session = null;

		try {
			session = openSession();

			userGroupRole = (UserGroupRole)session.get(
				UserGroupRoleImpl.class, primaryKey);

			if (userGroupRole != null) {
				cacheResult(userGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return userGroupRole;
	}

	/**
	 * Returns the user group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role, or <code>null</code> if a user group role with the primary key could not be found
	 */
	@Override
	public UserGroupRole fetchByPrimaryKey(long userGroupRoleId) {
		return fetchByPrimaryKey((Serializable)userGroupRoleId);
	}

	@Override
	public Map<Serializable, UserGroupRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(UserGroupRole.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserGroupRole> map =
			new HashMap<Serializable, UserGroupRole>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserGroupRole userGroupRole = fetchByPrimaryKey(primaryKey);

			if (userGroupRole != null) {
				map.put(primaryKey, userGroupRole);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (UserGroupRole userGroupRole :
					(List<UserGroupRole>)query.list()) {

				map.put(userGroupRole.getPrimaryKeyObj(), userGroupRole);

				cacheResult(userGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the user group roles.
	 *
	 * @return the user group roles
	 */
	@Override
	public List<UserGroupRole> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of user group roles
	 */
	@Override
	public List<UserGroupRole> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user group roles
	 */
	@Override
	public List<UserGroupRole> findAll(
		int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user group roles
	 */
	@Override
	public List<UserGroupRole> findAll(
		int start, int end, OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERGROUPROLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERGROUPROLE;

				sql = sql.concat(UserGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserGroupRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the user group roles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserGroupRole userGroupRole : findAll()) {
			remove(userGroupRole);
		}
	}

	/**
	 * Returns the number of user group roles.
	 *
	 * @return the number of user group roles
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupRole.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USERGROUPROLE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userGroupRoleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERGROUPROLE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return UserGroupRoleModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "UserGroupRole";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("roleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("userGroupRoleId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"userId", "groupId", "roleId"});
	}

	/**
	 * Initializes the user group role persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] {Long.class.getName()},
			UserGroupRoleModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			UserGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRoleId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRoleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByRoleId", new String[] {Long.class.getName()},
			UserGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRoleId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByU_G = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_G = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			UserGroupRoleModelImpl.USERID_COLUMN_BITMASK |
			UserGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByU_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_R = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_R = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			UserGroupRoleModelImpl.GROUPID_COLUMN_BITMASK |
			UserGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByG_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_R",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByU_G_R = new FinderPath(
			UserGroupRoleImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByU_G_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			UserGroupRoleModelImpl.USERID_COLUMN_BITMASK |
			UserGroupRoleModelImpl.GROUPID_COLUMN_BITMASK |
			UserGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByU_G_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_G_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserGroupRoleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERGROUPROLE =
		"SELECT userGroupRole FROM UserGroupRole userGroupRole";

	private static final String _SQL_SELECT_USERGROUPROLE_WHERE =
		"SELECT userGroupRole FROM UserGroupRole userGroupRole WHERE ";

	private static final String _SQL_COUNT_USERGROUPROLE =
		"SELECT COUNT(userGroupRole) FROM UserGroupRole userGroupRole";

	private static final String _SQL_COUNT_USERGROUPROLE_WHERE =
		"SELECT COUNT(userGroupRole) FROM UserGroupRole userGroupRole WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroupRole.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserGroupRole exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserGroupRole exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupRolePersistenceImpl.class);

}