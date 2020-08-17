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
import com.liferay.portal.kernel.exception.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupGroupRoleTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl;

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
 * The persistence implementation for the user group group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupGroupRolePersistenceImpl
	extends BasePersistenceImpl<UserGroupGroupRole>
	implements UserGroupGroupRolePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserGroupGroupRoleUtil</code> to access the user group group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserGroupGroupRoleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserGroupId;
	private FinderPath _finderPathWithoutPaginationFindByUserGroupId;
	private FinderPath _finderPathCountByUserGroupId;

	/**
	 * Returns all the user group group roles where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId) {
		return findByUserGroupId(
			userGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end) {

		return findByUserGroupId(userGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findByUserGroupId(
			userGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserGroupId;
				finderArgs = new Object[] {userGroupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserGroupId;
			finderArgs = new Object[] {
				userGroupId, start, end, orderByComparator
			};
		}

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if (userGroupId != userGroupGroupRole.getUserGroupId()) {
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

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Returns the first user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByUserGroupId_First(
			long userGroupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByUserGroupId_First(
			userGroupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userGroupId=");
		sb.append(userGroupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByUserGroupId_First(
		long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		List<UserGroupGroupRole> list = findByUserGroupId(
			userGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByUserGroupId_Last(
			long userGroupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByUserGroupId_Last(
			userGroupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userGroupId=");
		sb.append(userGroupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByUserGroupId_Last(
		long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		int count = countByUserGroupId(userGroupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByUserGroupId(
			userGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupGroupRoleId the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
			long userGroupGroupRoleId, long userGroupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(
			userGroupGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByUserGroupId_PrevAndNext(
				session, userGroupGroupRole, userGroupId, orderByComparator,
				true);

			array[1] = userGroupGroupRole;

			array[2] = getByUserGroupId_PrevAndNext(
				session, userGroupGroupRole, userGroupId, orderByComparator,
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

	protected UserGroupGroupRole getByUserGroupId_PrevAndNext(
		Session session, UserGroupGroupRole userGroupGroupRole,
		long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

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
			sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group group roles where userGroupId = &#63; from the database.
	 *
	 * @param userGroupId the user group ID
	 */
	@Override
	public void removeByUserGroupId(long userGroupId) {
		for (UserGroupGroupRole userGroupGroupRole :
				findByUserGroupId(
					userGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByUserGroupId(long userGroupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserGroupId;

			finderArgs = new Object[] {userGroupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

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

	private static final String _FINDER_COLUMN_USERGROUPID_USERGROUPID_2 =
		"userGroupGroupRole.userGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the user group group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if (groupId != userGroupGroupRole.getGroupId()) {
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

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Returns the first user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByGroupId_First(
			long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByGroupId_First(
			groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByGroupId_First(
		long groupId, OrderByComparator<UserGroupGroupRole> orderByComparator) {

		List<UserGroupGroupRole> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByGroupId_Last(
			long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByGroupId_Last(
		long groupId, OrderByComparator<UserGroupGroupRole> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param userGroupGroupRoleId the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
			long userGroupGroupRoleId, long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(
			userGroupGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, userGroupGroupRole, groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByGroupId_PrevAndNext(
				session, userGroupGroupRole, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByGroupId_PrevAndNext(
		Session session, UserGroupGroupRole userGroupGroupRole, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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
			sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
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
						userGroupGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group group roles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (UserGroupGroupRole userGroupGroupRole :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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
		"userGroupGroupRole.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the user group group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end) {

		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if (roleId != userGroupGroupRole.getRoleId()) {
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

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Returns the first user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByRoleId_First(
			long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByRoleId_First(
			roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByRoleId_First(
		long roleId, OrderByComparator<UserGroupGroupRole> orderByComparator) {

		List<UserGroupGroupRole> list = findByRoleId(
			roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByRoleId_Last(
			long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByRoleId_Last(
			roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByRoleId_Last(
		long roleId, OrderByComparator<UserGroupGroupRole> orderByComparator) {

		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByRoleId(
			roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param userGroupGroupRoleId the primary key of the current user group group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
			long userGroupGroupRoleId, long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(
			userGroupGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(
				session, userGroupGroupRole, roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByRoleId_PrevAndNext(
				session, userGroupGroupRole, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByRoleId_PrevAndNext(
		Session session, UserGroupGroupRole userGroupGroupRole, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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
			sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
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
						userGroupGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group group roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (UserGroupGroupRole userGroupGroupRole :
				findByRoleId(
					roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByRoleId(long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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
		"userGroupGroupRole.roleId = ?";

	private FinderPath _finderPathWithPaginationFindByU_G;
	private FinderPath _finderPathWithoutPaginationFindByU_G;
	private FinderPath _finderPathCountByU_G;

	/**
	 * Returns all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId) {
		return findByU_G(
			userGroupId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end) {

		return findByU_G(userGroupId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findByU_G(
			userGroupId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU_G;
				finderArgs = new Object[] {userGroupId, groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU_G;
			finderArgs = new Object[] {
				userGroupId, groupId, start, end, orderByComparator
			};
		}

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if ((userGroupId != userGroupGroupRole.getUserGroupId()) ||
						(groupId != userGroupGroupRole.getGroupId())) {

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

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

				queryPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Returns the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByU_G_First(
			long userGroupId, long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByU_G_First(
			userGroupId, groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userGroupId=");
		sb.append(userGroupId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByU_G_First(
		long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		List<UserGroupGroupRole> list = findByU_G(
			userGroupId, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByU_G_Last(
			long userGroupId, long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByU_G_Last(
			userGroupId, groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userGroupId=");
		sb.append(userGroupId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByU_G_Last(
		long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		int count = countByU_G(userGroupId, groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByU_G(
			userGroupId, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupGroupRoleId the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByU_G_PrevAndNext(
			long userGroupGroupRoleId, long userGroupId, long groupId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(
			userGroupGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByU_G_PrevAndNext(
				session, userGroupGroupRole, userGroupId, groupId,
				orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByU_G_PrevAndNext(
				session, userGroupGroupRole, userGroupId, groupId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByU_G_PrevAndNext(
		Session session, UserGroupGroupRole userGroupGroupRole,
		long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		sb.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

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
			sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userGroupId);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userGroupGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group group roles where userGroupId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByU_G(long userGroupId, long groupId) {
		for (UserGroupGroupRole userGroupGroupRole :
				findByU_G(
					userGroupId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByU_G(long userGroupId, long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_G;

			finderArgs = new Object[] {userGroupId, groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

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

	private static final String _FINDER_COLUMN_U_G_USERGROUPID_2 =
		"userGroupGroupRole.userGroupId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_GROUPID_2 =
		"userGroupGroupRole.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_R;
	private FinderPath _finderPathWithoutPaginationFindByG_R;
	private FinderPath _finderPathCountByG_R;

	/**
	 * Returns all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId) {
		return findByG_R(
			groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end) {

		return findByG_R(groupId, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findByG_R(groupId, roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if ((groupId != userGroupGroupRole.getGroupId()) ||
						(roleId != userGroupGroupRole.getRoleId())) {

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

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Returns the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByG_R_First(
			long groupId, long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByG_R_First(
			groupId, roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByG_R_First(
		long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		List<UserGroupGroupRole> list = findByG_R(
			groupId, roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByG_R_Last(
			long groupId, long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByG_R_Last(
			groupId, roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchUserGroupGroupRoleException(sb.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByG_R_Last(
		long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		int count = countByG_R(groupId, roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByG_R(
			groupId, roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param userGroupGroupRoleId the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByG_R_PrevAndNext(
			long userGroupGroupRoleId, long groupId, long roleId,
			OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(
			userGroupGroupRoleId);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByG_R_PrevAndNext(
				session, userGroupGroupRole, groupId, roleId, orderByComparator,
				true);

			array[1] = userGroupGroupRole;

			array[2] = getByG_R_PrevAndNext(
				session, userGroupGroupRole, groupId, roleId, orderByComparator,
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

	protected UserGroupGroupRole getByG_R_PrevAndNext(
		Session session, UserGroupGroupRole userGroupGroupRole, long groupId,
		long roleId, OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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
			sb.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
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
						userGroupGroupRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserGroupGroupRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user group group roles where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 */
	@Override
	public void removeByG_R(long groupId, long roleId) {
		for (UserGroupGroupRole userGroupGroupRole :
				findByG_R(
					groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByG_R(long groupId, long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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
		"userGroupGroupRole.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_R_ROLEID_2 =
		"userGroupGroupRole.roleId = ?";

	private FinderPath _finderPathFetchByU_G_R;
	private FinderPath _finderPathCountByU_G_R;

	/**
	 * Returns the user group group role where userGroupId = &#63; and groupId = &#63; and roleId = &#63; or throws a <code>NoSuchUserGroupGroupRoleException</code> if it could not be found.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole findByU_G_R(
			long userGroupId, long groupId, long roleId)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByU_G_R(
			userGroupId, groupId, roleId);

		if (userGroupGroupRole == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userGroupId=");
			sb.append(userGroupId);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", roleId=");
			sb.append(roleId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserGroupGroupRoleException(sb.toString());
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role where userGroupId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByU_G_R(
		long userGroupId, long groupId, long roleId) {

		return fetchByU_G_R(userGroupId, groupId, roleId, true);
	}

	/**
	 * Returns the user group group role where userGroupId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByU_G_R(
		long userGroupId, long groupId, long roleId, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {userGroupId, groupId, roleId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_G_R, finderArgs, this);
		}

		if (result instanceof UserGroupGroupRole) {
			UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)result;

			if ((userGroupId != userGroupGroupRole.getUserGroupId()) ||
				(groupId != userGroupGroupRole.getGroupId()) ||
				(roleId != userGroupGroupRole.getRoleId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_R_USERGROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

				queryPos.add(groupId);

				queryPos.add(roleId);

				List<UserGroupGroupRole> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_G_R, finderArgs, list);
					}
				}
				else {
					UserGroupGroupRole userGroupGroupRole = list.get(0);

					result = userGroupGroupRole;

					cacheResult(userGroupGroupRole);
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
			return (UserGroupGroupRole)result;
		}
	}

	/**
	 * Removes the user group group role where userGroupId = &#63; and groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the user group group role that was removed
	 */
	@Override
	public UserGroupGroupRole removeByU_G_R(
			long userGroupId, long groupId, long roleId)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = findByU_G_R(
			userGroupId, groupId, roleId);

		return remove(userGroupGroupRole);
	}

	/**
	 * Returns the number of user group group roles where userGroupId = &#63; and groupId = &#63; and roleId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group group roles
	 */
	@Override
	public int countByU_G_R(long userGroupId, long groupId, long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_G_R;

			finderArgs = new Object[] {userGroupId, groupId, roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			sb.append(_FINDER_COLUMN_U_G_R_USERGROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_U_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userGroupId);

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

	private static final String _FINDER_COLUMN_U_G_R_USERGROUPID_2 =
		"userGroupGroupRole.userGroupId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_R_GROUPID_2 =
		"userGroupGroupRole.groupId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_R_ROLEID_2 =
		"userGroupGroupRole.roleId = ?";

	public UserGroupGroupRolePersistenceImpl() {
		setModelClass(UserGroupGroupRole.class);

		setModelImplClass(UserGroupGroupRoleImpl.class);
		setModelPKClass(long.class);

		setTable(UserGroupGroupRoleTable.INSTANCE);
	}

	/**
	 * Caches the user group group role in the entity cache if it is enabled.
	 *
	 * @param userGroupGroupRole the user group group role
	 */
	@Override
	public void cacheResult(UserGroupGroupRole userGroupGroupRole) {
		if (userGroupGroupRole.getCtCollectionId() != 0) {
			userGroupGroupRole.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_R,
			new Object[] {
				userGroupGroupRole.getUserGroupId(),
				userGroupGroupRole.getGroupId(), userGroupGroupRole.getRoleId()
			},
			userGroupGroupRole);

		userGroupGroupRole.resetOriginalValues();
	}

	/**
	 * Caches the user group group roles in the entity cache if it is enabled.
	 *
	 * @param userGroupGroupRoles the user group group roles
	 */
	@Override
	public void cacheResult(List<UserGroupGroupRole> userGroupGroupRoles) {
		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			if (userGroupGroupRole.getCtCollectionId() != 0) {
				userGroupGroupRole.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					UserGroupGroupRoleImpl.class,
					userGroupGroupRole.getPrimaryKey()) == null) {

				cacheResult(userGroupGroupRole);
			}
			else {
				userGroupGroupRole.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user group group roles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserGroupGroupRoleImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user group group role.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.removeResult(
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(UserGroupGroupRoleModelImpl)userGroupGroupRole, true);
	}

	@Override
	public void clearCache(List<UserGroupGroupRole> userGroupGroupRoles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			EntityCacheUtil.removeResult(
				UserGroupGroupRoleImpl.class,
				userGroupGroupRole.getPrimaryKey());

			clearUniqueFindersCache(
				(UserGroupGroupRoleModelImpl)userGroupGroupRole, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				UserGroupGroupRoleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UserGroupGroupRoleModelImpl userGroupGroupRoleModelImpl) {

		Object[] args = new Object[] {
			userGroupGroupRoleModelImpl.getUserGroupId(),
			userGroupGroupRoleModelImpl.getGroupId(),
			userGroupGroupRoleModelImpl.getRoleId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_G_R, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_R, args, userGroupGroupRoleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		UserGroupGroupRoleModelImpl userGroupGroupRoleModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				userGroupGroupRoleModelImpl.getUserGroupId(),
				userGroupGroupRoleModelImpl.getGroupId(),
				userGroupGroupRoleModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_R, args);
		}

		if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_G_R.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				userGroupGroupRoleModelImpl.getOriginalUserGroupId(),
				userGroupGroupRoleModelImpl.getOriginalGroupId(),
				userGroupGroupRoleModelImpl.getOriginalRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_R, args);
		}
	}

	/**
	 * Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	 *
	 * @param userGroupGroupRoleId the primary key for the new user group group role
	 * @return the new user group group role
	 */
	@Override
	public UserGroupGroupRole create(long userGroupGroupRoleId) {
		UserGroupGroupRole userGroupGroupRole = new UserGroupGroupRoleImpl();

		userGroupGroupRole.setNew(true);
		userGroupGroupRole.setPrimaryKey(userGroupGroupRoleId);

		userGroupGroupRole.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userGroupGroupRole;
	}

	/**
	 * Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRoleId the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole remove(long userGroupGroupRoleId)
		throws NoSuchUserGroupGroupRoleException {

		return remove((Serializable)userGroupGroupRoleId);
	}

	/**
	 * Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole remove(Serializable primaryKey)
		throws NoSuchUserGroupGroupRoleException {

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole userGroupGroupRole =
				(UserGroupGroupRole)session.get(
					UserGroupGroupRoleImpl.class, primaryKey);

			if (userGroupGroupRole == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserGroupGroupRoleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userGroupGroupRole);
		}
		catch (NoSuchUserGroupGroupRoleException noSuchEntityException) {
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
	protected UserGroupGroupRole removeImpl(
		UserGroupGroupRole userGroupGroupRole) {

		if (!CTPersistenceHelperUtil.isRemove(userGroupGroupRole)) {
			return userGroupGroupRole;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userGroupGroupRole)) {
				userGroupGroupRole = (UserGroupGroupRole)session.get(
					UserGroupGroupRoleImpl.class,
					userGroupGroupRole.getPrimaryKeyObj());
			}

			if (userGroupGroupRole != null) {
				session.delete(userGroupGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userGroupGroupRole != null) {
			clearCache(userGroupGroupRole);
		}

		return userGroupGroupRole;
	}

	@Override
	public UserGroupGroupRole updateImpl(
		UserGroupGroupRole userGroupGroupRole) {

		boolean isNew = userGroupGroupRole.isNew();

		if (!(userGroupGroupRole instanceof UserGroupGroupRoleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userGroupGroupRole.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userGroupGroupRole);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userGroupGroupRole proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserGroupGroupRole implementation " +
					userGroupGroupRole.getClass());
		}

		UserGroupGroupRoleModelImpl userGroupGroupRoleModelImpl =
			(UserGroupGroupRoleModelImpl)userGroupGroupRole;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(userGroupGroupRole)) {
				if (!isNew) {
					session.evict(
						UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKeyObj());
				}

				session.save(userGroupGroupRole);

				userGroupGroupRole.setNew(false);
			}
			else {
				userGroupGroupRole = (UserGroupGroupRole)session.merge(
					userGroupGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userGroupGroupRole.getCtCollectionId() != 0) {
			userGroupGroupRole.resetOriginalValues();

			return userGroupGroupRole;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				userGroupGroupRoleModelImpl.getUserGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUserGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserGroupId, args);

			args = new Object[] {userGroupGroupRoleModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {userGroupGroupRoleModelImpl.getRoleId()};

			FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByRoleId, args);

			args = new Object[] {
				userGroupGroupRoleModelImpl.getUserGroupId(),
				userGroupGroupRoleModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_G, args);

			args = new Object[] {
				userGroupGroupRoleModelImpl.getGroupId(),
				userGroupGroupRoleModelImpl.getRoleId()
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
			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userGroupGroupRoleModelImpl.getOriginalUserGroupId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUserGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserGroupId, args);

				args = new Object[] {
					userGroupGroupRoleModelImpl.getUserGroupId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUserGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserGroupId, args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userGroupGroupRoleModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {userGroupGroupRoleModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupGroupRoleModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);

				args = new Object[] {userGroupGroupRoleModelImpl.getRoleId()};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_G.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupGroupRoleModelImpl.getOriginalUserGroupId(),
					userGroupGroupRoleModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_G, args);

				args = new Object[] {
					userGroupGroupRoleModelImpl.getUserGroupId(),
					userGroupGroupRoleModelImpl.getGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_G, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_G, args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_R.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userGroupGroupRoleModelImpl.getOriginalGroupId(),
					userGroupGroupRoleModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_R, args);

				args = new Object[] {
					userGroupGroupRoleModelImpl.getGroupId(),
					userGroupGroupRoleModelImpl.getRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_R, args);
			}
		}

		EntityCacheUtil.putResult(
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole, false);

		clearUniqueFindersCache(userGroupGroupRoleModelImpl, false);
		cacheUniqueFindersCache(userGroupGroupRoleModelImpl);

		userGroupGroupRole.resetOriginalValues();

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserGroupGroupRoleException {

		UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(primaryKey);

		if (userGroupGroupRole == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserGroupGroupRoleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or throws a <code>NoSuchUserGroupGroupRoleException</code> if it could not be found.
	 *
	 * @param userGroupGroupRoleId the primary key of the user group group role
	 * @return the user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole findByPrimaryKey(long userGroupGroupRoleId)
		throws NoSuchUserGroupGroupRoleException {

		return findByPrimaryKey((Serializable)userGroupGroupRoleId);
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				UserGroupGroupRole.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		UserGroupGroupRole userGroupGroupRole = null;

		Session session = null;

		try {
			session = openSession();

			userGroupGroupRole = (UserGroupGroupRole)session.get(
				UserGroupGroupRoleImpl.class, primaryKey);

			if (userGroupGroupRole != null) {
				cacheResult(userGroupGroupRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupGroupRoleId the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByPrimaryKey(long userGroupGroupRoleId) {
		return fetchByPrimaryKey((Serializable)userGroupGroupRoleId);
	}

	@Override
	public Map<Serializable, UserGroupGroupRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				UserGroupGroupRole.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserGroupGroupRole> map =
			new HashMap<Serializable, UserGroupGroupRole>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(
				primaryKey);

			if (userGroupGroupRole != null) {
				map.put(primaryKey, userGroupGroupRole);
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

			for (UserGroupGroupRole userGroupGroupRole :
					(List<UserGroupGroupRole>)query.list()) {

				map.put(
					userGroupGroupRole.getPrimaryKeyObj(), userGroupGroupRole);

				cacheResult(userGroupGroupRole);
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
	 * Returns all the user group group roles.
	 *
	 * @return the user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll(
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll(
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

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

		List<UserGroupGroupRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERGROUPGROUPROLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERGROUPGROUPROLE;

				sql = sql.concat(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserGroupGroupRole>)QueryUtil.list(
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
	 * Removes all the user group group roles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserGroupGroupRole userGroupGroupRole : findAll()) {
			remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles.
	 *
	 * @return the number of user group group roles
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			UserGroupGroupRole.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_USERGROUPGROUPROLE);

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
		return "userGroupGroupRoleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERGROUPGROUPROLE;
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
		return UserGroupGroupRoleModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "UserGroupGroupRole";
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
		ctStrictColumnNames.add("userGroupId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("roleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("userGroupGroupRoleId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"userGroupId", "groupId", "roleId"});
	}

	/**
	 * Initializes the user group group role persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserGroupId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserGroupId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserGroupId",
			new String[] {Long.class.getName()},
			UserGroupGroupRoleModelImpl.USERGROUPID_COLUMN_BITMASK);

		_finderPathCountByUserGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRoleId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] {Long.class.getName()},
			UserGroupGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRoleId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByU_G = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_G = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			UserGroupGroupRoleModelImpl.USERGROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByU_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_R = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_R = new FinderPath(
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByG_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_R",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByU_G_R = new FinderPath(
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_G_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			UserGroupGroupRoleModelImpl.USERGROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByU_G_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_G_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserGroupGroupRoleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERGROUPGROUPROLE =
		"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole";

	private static final String _SQL_SELECT_USERGROUPGROUPROLE_WHERE =
		"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ";

	private static final String _SQL_COUNT_USERGROUPGROUPROLE =
		"SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole";

	private static final String _SQL_COUNT_USERGROUPGROUPROLE_WHERE =
		"SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroupGroupRole.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserGroupGroupRole exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserGroupGroupRole exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupGroupRolePersistenceImpl.class);

}