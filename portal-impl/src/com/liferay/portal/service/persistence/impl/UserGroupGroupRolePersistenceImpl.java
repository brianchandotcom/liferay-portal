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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.UserGroupGroupRolePK;
import com.liferay.portal.service.persistence.UserGroupGroupRolePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
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
 * @see UserGroupGroupRolePersistence
 * @see com.liferay.portal.service.persistence.UserGroupGroupRoleUtil
 * @generated
 */
@ProviderType
public class UserGroupGroupRolePersistenceImpl extends BasePersistenceImpl<UserGroupGroupRole>
	implements UserGroupGroupRolePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserGroupGroupRoleUtil} to access the user group group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERGROUPID =
		new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERGROUPID =
		new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserGroupId",
			new String[] { Long.class.getName() },
			UserGroupGroupRoleModelImpl.USERGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the user group group roles where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId) {
		return findByUserGroupId(userGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end) {
		return findByUserGroupId(userGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findByUserGroupId(userGroupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERGROUPID;
			finderArgs = new Object[] { userGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERGROUPID;
			finderArgs = new Object[] { userGroupId, start, end, orderByComparator };
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if ((userGroupId != userGroupGroupRole.getUserGroupId())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public UserGroupGroupRole findByUserGroupId_First(long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByUserGroupId_First(userGroupId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userGroupId=");
		msg.append(userGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByUserGroupId_First(long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId, 0, 1,
				orderByComparator);

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
	public UserGroupGroupRole findByUserGroupId_Last(long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByUserGroupId_Last(userGroupId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userGroupId=");
		msg.append(userGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByUserGroupId_Last(long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		int count = countByUserGroupId(userGroupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByUserGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

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
			query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

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
		for (UserGroupGroupRole userGroupGroupRole : findByUserGroupId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERGROUPID;

		Object[] finderArgs = new Object[] { userGroupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERGROUPID_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the user group group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if ((groupId != userGroupGroupRole.getGroupId())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public UserGroupGroupRole findByGroupId_First(long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByGroupId_First(groupId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByGroupId_First(long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		List<UserGroupGroupRole> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

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
	public UserGroupGroupRole findByGroupId_Last(long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByGroupId_Last(long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63;.
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
					groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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
			query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

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
		for (UserGroupGroupRole userGroupGroupRole : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID =
		new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] { Long.class.getName() },
			UserGroupGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRoleId",
			new String[] { Long.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(long roleId, int start, int end) {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId, start, end, orderByComparator };
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserGroupGroupRole userGroupGroupRole : list) {
					if ((roleId != userGroupGroupRole.getRoleId())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public UserGroupGroupRole findByRoleId_First(long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByRoleId_First(roleId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the first user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByRoleId_First(long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		List<UserGroupGroupRole> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

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
	public UserGroupGroupRole findByRoleId_Last(long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByRoleId_Last(roleId,
				orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
	}

	/**
	 * Returns the last user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByRoleId_Last(long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByRoleId(roleId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where roleId = &#63;.
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
					roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
					roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByRoleId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

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
			query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

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
		for (UserGroupGroupRole userGroupGroupRole : findByRoleId(roleId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROLEID;

		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_G",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserGroupGroupRoleModelImpl.USERGROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId) {
		return findByU_G(userGroupId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end) {
		return findByU_G(userGroupId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findByU_G(userGroupId, groupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G;
			finderArgs = new Object[] { userGroupId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G;
			finderArgs = new Object[] {
					userGroupId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public UserGroupGroupRole findByU_G_First(long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByU_G_First(userGroupId,
				groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userGroupId=");
		msg.append(userGroupId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
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
	public UserGroupGroupRole fetchByU_G_First(long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId, 0, 1,
				orderByComparator);

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
	public UserGroupGroupRole findByU_G_Last(long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByU_G_Last(userGroupId,
				groupId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userGroupId=");
		msg.append(userGroupId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
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
	public UserGroupGroupRole fetchByU_G_Last(long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		int count = countByU_G(userGroupId, groupId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByU_G_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		long groupId, OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByU_G_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId, long groupId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

		query.append(_FINDER_COLUMN_U_G_GROUPID_2);

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
			query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userGroupId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

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
		for (UserGroupGroupRole userGroupGroupRole : findByU_G(userGroupId,
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_G;

		Object[] finderArgs = new Object[] { userGroupId, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_G_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserGroupGroupRoleModelImpl.GROUPID_COLUMN_BITMASK |
			UserGroupGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId) {
		return findByG_R(groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end) {
		return findByG_R(groupId, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findByG_R(groupId, roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R;
			finderArgs = new Object[] { groupId, roleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_R;
			finderArgs = new Object[] {
					groupId, roleId,
					
					start, end, orderByComparator
				};
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public UserGroupGroupRole findByG_R_First(long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByG_R_First(groupId,
				roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
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
	public UserGroupGroupRole fetchByG_R_First(long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, 0, 1,
				orderByComparator);

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
	public UserGroupGroupRole findByG_R_Last(long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = fetchByG_R_Last(groupId,
				roleId, orderByComparator);

		if (userGroupGroupRole != null) {
			return userGroupGroupRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupGroupRoleException(msg.toString());
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
	public UserGroupGroupRole fetchByG_R_Last(long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		int count = countByG_R(groupId, roleId);

		if (count == 0) {
			return null;
		}

		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole[] findByG_R_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByG_R_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId, long roleId,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_ROLEID_2);

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
			query.append(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

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
		for (UserGroupGroupRole userGroupGroupRole : findByG_R(groupId, roleId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_R;

		Object[] finderArgs = new Object[] { groupId, roleId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_R_GROUPID_2 = "userGroupGroupRole.id.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_R_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";

	public UserGroupGroupRolePersistenceImpl() {
		setModelClass(UserGroupGroupRole.class);
	}

	/**
	 * Caches the user group group role in the entity cache if it is enabled.
	 *
	 * @param userGroupGroupRole the user group group role
	 */
	@Override
	public void cacheResult(UserGroupGroupRole userGroupGroupRole) {
		entityCache.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
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
			if (entityCache.getResult(
						UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UserGroupGroupRoleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user group group role.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserGroupGroupRole userGroupGroupRole) {
		entityCache.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserGroupGroupRole> userGroupGroupRoles) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			entityCache.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());
		}
	}

	/**
	 * Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	 *
	 * @param userGroupGroupRolePK the primary key for the new user group group role
	 * @return the new user group group role
	 */
	@Override
	public UserGroupGroupRole create(UserGroupGroupRolePK userGroupGroupRolePK) {
		UserGroupGroupRole userGroupGroupRole = new UserGroupGroupRoleImpl();

		userGroupGroupRole.setNew(true);
		userGroupGroupRole.setPrimaryKey(userGroupGroupRolePK);

		userGroupGroupRole.setCompanyId(companyProvider.getCompanyId());

		return userGroupGroupRole;
	}

	/**
	 * Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole remove(UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException {
		return remove((Serializable)userGroupGroupRolePK);
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

			UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
					primaryKey);

			if (userGroupGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userGroupGroupRole);
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
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
	protected UserGroupGroupRole removeImpl(
		UserGroupGroupRole userGroupGroupRole) {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userGroupGroupRole)) {
				userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKeyObj());
			}

			if (userGroupGroupRole != null) {
				session.delete(userGroupGroupRole);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
	public UserGroupGroupRole updateImpl(UserGroupGroupRole userGroupGroupRole) {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		boolean isNew = userGroupGroupRole.isNew();

		UserGroupGroupRoleModelImpl userGroupGroupRoleModelImpl = (UserGroupGroupRoleModelImpl)userGroupGroupRole;

		Session session = null;

		try {
			session = openSession();

			if (userGroupGroupRole.isNew()) {
				session.save(userGroupGroupRole);

				userGroupGroupRole.setNew(false);
			}
			else {
				userGroupGroupRole = (UserGroupGroupRole)session.merge(userGroupGroupRole);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserGroupGroupRoleModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userGroupGroupRoleModelImpl.getOriginalUserGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERGROUPID,
					args);

				args = new Object[] { userGroupGroupRoleModelImpl.getUserGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERGROUPID,
					args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userGroupGroupRoleModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { userGroupGroupRoleModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userGroupGroupRoleModelImpl.getOriginalRoleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);

				args = new Object[] { userGroupGroupRoleModelImpl.getRoleId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userGroupGroupRoleModelImpl.getOriginalUserGroupId(),
						userGroupGroupRoleModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G,
					args);

				args = new Object[] {
						userGroupGroupRoleModelImpl.getUserGroupId(),
						userGroupGroupRoleModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G,
					args);
			}

			if ((userGroupGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userGroupGroupRoleModelImpl.getOriginalGroupId(),
						userGroupGroupRoleModelImpl.getOriginalRoleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);

				args = new Object[] {
						userGroupGroupRoleModelImpl.getGroupId(),
						userGroupGroupRoleModelImpl.getRoleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);
			}
		}

		entityCache.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole, false);

		userGroupGroupRole.resetOriginalValues();

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole toUnwrappedModel(
		UserGroupGroupRole userGroupGroupRole) {
		if (userGroupGroupRole instanceof UserGroupGroupRoleImpl) {
			return userGroupGroupRole;
		}

		UserGroupGroupRoleImpl userGroupGroupRoleImpl = new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setNew(userGroupGroupRole.isNew());
		userGroupGroupRoleImpl.setPrimaryKey(userGroupGroupRole.getPrimaryKey());

		userGroupGroupRoleImpl.setMvccVersion(userGroupGroupRole.getMvccVersion());
		userGroupGroupRoleImpl.setUserGroupId(userGroupGroupRole.getUserGroupId());
		userGroupGroupRoleImpl.setGroupId(userGroupGroupRole.getGroupId());
		userGroupGroupRoleImpl.setRoleId(userGroupGroupRole.getRoleId());
		userGroupGroupRoleImpl.setCompanyId(userGroupGroupRole.getCompanyId());

		return userGroupGroupRoleImpl;
	}

	/**
	 * Returns the user group group role with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
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
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or throws a {@link NoSuchUserGroupGroupRoleException} if it could not be found.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role
	 * @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole findByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException {
		return findByPrimaryKey((Serializable)userGroupGroupRolePK);
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByPrimaryKey(Serializable primaryKey) {
		UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)entityCache.getResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupGroupRoleImpl.class, primaryKey);

		if (userGroupGroupRole == _nullUserGroupGroupRole) {
			return null;
		}

		if (userGroupGroupRole == null) {
			Session session = null;

			try {
				session = openSession();

				userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
						primaryKey);

				if (userGroupGroupRole != null) {
					cacheResult(userGroupGroupRole);
				}
				else {
					entityCache.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupGroupRoleImpl.class, primaryKey,
						_nullUserGroupGroupRole);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
					UserGroupGroupRoleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 */
	@Override
	public UserGroupGroupRole fetchByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK) {
		return fetchByPrimaryKey((Serializable)userGroupGroupRolePK);
	}

	@Override
	public Map<Serializable, UserGroupGroupRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserGroupGroupRole> map = new HashMap<Serializable, UserGroupGroupRole>();

		for (Serializable primaryKey : primaryKeys) {
			UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(primaryKey);

			if (userGroupGroupRole != null) {
				map.put(primaryKey, userGroupGroupRole);
			}
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll(int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of user group group roles
	 */
	@Override
	public List<UserGroupGroupRole> findAll(int start, int end,
		OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<UserGroupGroupRole> list = null;

		if (retrieveFromCache) {
			list = (List<UserGroupGroupRole>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERGROUPGROUPROLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERGROUPGROUPROLE;

				if (pagination) {
					sql = sql.concat(UserGroupGroupRoleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERGROUPGROUPROLE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserGroupGroupRoleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user group group role persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(UserGroupGroupRoleImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_USERGROUPGROUPROLE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_SELECT_USERGROUPGROUPROLE_WHERE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE_WHERE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroupGroupRole.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserGroupGroupRole exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserGroupGroupRole exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(UserGroupGroupRolePersistenceImpl.class);
	private static final UserGroupGroupRole _nullUserGroupGroupRole = new UserGroupGroupRoleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserGroupGroupRole> toCacheModel() {
				return _nullUserGroupGroupRoleCacheModel;
			}
		};

	private static final CacheModel<UserGroupGroupRole> _nullUserGroupGroupRoleCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<UserGroupGroupRole>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public UserGroupGroupRole toEntityModel() {
			return _nullUserGroupGroupRole;
		}
	}
}