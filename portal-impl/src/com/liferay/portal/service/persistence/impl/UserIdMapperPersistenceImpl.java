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
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserIdMapperException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserIdMapper;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserIdMapperPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the user ID mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserIdMapperPersistenceImpl
	extends BasePersistenceImpl<UserIdMapper>
	implements UserIdMapperPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserIdMapperUtil</code> to access the user ID mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserIdMapperImpl.class.getName();

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
	 * Returns all the user ID mappers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user ID mappers
	 */
	@Override
	public List<UserIdMapper> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @return the range of matching user ID mappers
	 */
	@Override
	public List<UserIdMapper> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user ID mappers
	 */
	@Override
	public List<UserIdMapper> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserIdMapper> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user ID mappers
	 */
	@Override
	public List<UserIdMapper> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserIdMapper> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<UserIdMapper> list = null;

		if (useFinderCache) {
			list = (List<UserIdMapper>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserIdMapper userIdMapper : list) {
					if (userId != userIdMapper.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserIdMapperModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserIdMapper>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper findByUserId_First(
			long userId, OrderByComparator<UserIdMapper> orderByComparator)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = fetchByUserId_First(
			userId, orderByComparator);

		if (userIdMapper != null) {
			return userIdMapper;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserIdMapperException(msg.toString());
	}

	/**
	 * Returns the first user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByUserId_First(
		long userId, OrderByComparator<UserIdMapper> orderByComparator) {

		List<UserIdMapper> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper findByUserId_Last(
			long userId, OrderByComparator<UserIdMapper> orderByComparator)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = fetchByUserId_Last(
			userId, orderByComparator);

		if (userIdMapper != null) {
			return userIdMapper;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserIdMapperException(msg.toString());
	}

	/**
	 * Returns the last user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByUserId_Last(
		long userId, OrderByComparator<UserIdMapper> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserIdMapper> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user ID mappers before and after the current user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userIdMapperId the primary key of the current user ID mapper
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user ID mapper
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper[] findByUserId_PrevAndNext(
			long userIdMapperId, long userId,
			OrderByComparator<UserIdMapper> orderByComparator)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = findByPrimaryKey(userIdMapperId);

		Session session = null;

		try {
			session = openSession();

			UserIdMapper[] array = new UserIdMapperImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userIdMapper, userId, orderByComparator, true);

			array[1] = userIdMapper;

			array[2] = getByUserId_PrevAndNext(
				session, userIdMapper, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserIdMapper getByUserId_PrevAndNext(
		Session session, UserIdMapper userIdMapper, long userId,
		OrderByComparator<UserIdMapper> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserIdMapperModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userIdMapper)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserIdMapper> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user ID mappers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserIdMapper userIdMapper :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userIdMapper);
		}
	}

	/**
	 * Returns the number of user ID mappers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user ID mappers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"userIdMapper.userId = ?";

	private FinderPath _finderPathFetchByU_T;
	private FinderPath _finderPathCountByU_T;

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper findByU_T(long userId, String type)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = fetchByU_T(userId, type);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByU_T(long userId, String type) {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByU_T(
		long userId, String type, boolean useFinderCache) {

		type = Objects.toString(type, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_T, finderArgs, this);
		}

		if (result instanceof UserIdMapper) {
			UserIdMapper userIdMapper = (UserIdMapper)result;

			if ((userId != userIdMapper.getUserId()) ||
				!Objects.equals(type, userIdMapper.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				List<UserIdMapper> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_T, finderArgs, list);
					}
				}
				else {
					UserIdMapper userIdMapper = list.get(0);

					result = userIdMapper;

					cacheResult(userIdMapper);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByU_T, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (UserIdMapper)result;
		}
	}

	/**
	 * Removes the user ID mapper where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the user ID mapper that was removed
	 */
	@Override
	public UserIdMapper removeByU_T(long userId, String type)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = findByU_T(userId, type);

		return remove(userIdMapper);
	}

	/**
	 * Returns the number of user ID mappers where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching user ID mappers
	 */
	@Override
	public int countByU_T(long userId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByU_T;

		Object[] finderArgs = new Object[] {userId, type};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_T_USERID_2 =
		"userIdMapper.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_TYPE_2 =
		"userIdMapper.type = ?";

	private static final String _FINDER_COLUMN_U_T_TYPE_3 =
		"(userIdMapper.type IS NULL OR userIdMapper.type = '')";

	private FinderPath _finderPathFetchByT_E;
	private FinderPath _finderPathCountByT_E;

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper findByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = fetchByT_E(type, externalUserId);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(", externalUserId=");
			msg.append(externalUserId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByT_E(String type, String externalUserId) {
		return fetchByT_E(type, externalUserId, true);
	}

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	@Override
	public UserIdMapper fetchByT_E(
		String type, String externalUserId, boolean useFinderCache) {

		type = Objects.toString(type, "");
		externalUserId = Objects.toString(externalUserId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {type, externalUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByT_E, finderArgs, this);
		}

		if (result instanceof UserIdMapper) {
			UserIdMapper userIdMapper = (UserIdMapper)result;

			if (!Objects.equals(type, userIdMapper.getType()) ||
				!Objects.equals(
					externalUserId, userIdMapper.getExternalUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_T_E_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_T_E_TYPE_2);
			}

			boolean bindExternalUserId = false;

			if (externalUserId.isEmpty()) {
				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
			}
			else {
				bindExternalUserId = true;

				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindType) {
					qPos.add(type);
				}

				if (bindExternalUserId) {
					qPos.add(externalUserId);
				}

				List<UserIdMapper> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByT_E, finderArgs, list);
					}
				}
				else {
					UserIdMapper userIdMapper = list.get(0);

					result = userIdMapper;

					cacheResult(userIdMapper);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByT_E, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (UserIdMapper)result;
		}
	}

	/**
	 * Removes the user ID mapper where type = &#63; and externalUserId = &#63; from the database.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the user ID mapper that was removed
	 */
	@Override
	public UserIdMapper removeByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = findByT_E(type, externalUserId);

		return remove(userIdMapper);
	}

	/**
	 * Returns the number of user ID mappers where type = &#63; and externalUserId = &#63;.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the number of matching user ID mappers
	 */
	@Override
	public int countByT_E(String type, String externalUserId) {
		type = Objects.toString(type, "");
		externalUserId = Objects.toString(externalUserId, "");

		FinderPath finderPath = _finderPathCountByT_E;

		Object[] finderArgs = new Object[] {type, externalUserId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_T_E_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_T_E_TYPE_2);
			}

			boolean bindExternalUserId = false;

			if (externalUserId.isEmpty()) {
				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
			}
			else {
				bindExternalUserId = true;

				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindType) {
					qPos.add(type);
				}

				if (bindExternalUserId) {
					qPos.add(externalUserId);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_E_TYPE_2 =
		"userIdMapper.type = ? AND ";

	private static final String _FINDER_COLUMN_T_E_TYPE_3 =
		"(userIdMapper.type IS NULL OR userIdMapper.type = '') AND ";

	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_2 =
		"userIdMapper.externalUserId = ?";

	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_3 =
		"(userIdMapper.externalUserId IS NULL OR userIdMapper.externalUserId = '')";

	public UserIdMapperPersistenceImpl() {
		setModelClass(UserIdMapper.class);

		setModelImplClass(UserIdMapperImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the user ID mapper in the entity cache if it is enabled.
	 *
	 * @param userIdMapper the user ID mapper
	 */
	@Override
	public void cacheResult(UserIdMapper userIdMapper) {
		EntityCacheUtil.putResult(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED, UserIdMapperImpl.class,
			userIdMapper.getPrimaryKey(), userIdMapper);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_T,
			new Object[] {userIdMapper.getUserId(), userIdMapper.getType()},
			userIdMapper);

		FinderCacheUtil.putResult(
			_finderPathFetchByT_E,
			new Object[] {
				userIdMapper.getType(), userIdMapper.getExternalUserId()
			},
			userIdMapper);

		userIdMapper.resetOriginalValues();
	}

	/**
	 * Caches the user ID mappers in the entity cache if it is enabled.
	 *
	 * @param userIdMappers the user ID mappers
	 */
	@Override
	public void cacheResult(List<UserIdMapper> userIdMappers) {
		for (UserIdMapper userIdMapper : userIdMappers) {
			if (EntityCacheUtil.getResult(
					UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
					UserIdMapperImpl.class, userIdMapper.getPrimaryKey()) ==
						null) {

				cacheResult(userIdMapper);
			}
			else {
				userIdMapper.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user ID mappers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserIdMapperImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user ID mapper.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserIdMapper userIdMapper) {
		EntityCacheUtil.removeResult(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED, UserIdMapperImpl.class,
			userIdMapper.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((UserIdMapperModelImpl)userIdMapper, true);
	}

	@Override
	public void clearCache(List<UserIdMapper> userIdMappers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserIdMapper userIdMapper : userIdMappers) {
			EntityCacheUtil.removeResult(
				UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				UserIdMapperImpl.class, userIdMapper.getPrimaryKey());

			clearUniqueFindersCache((UserIdMapperModelImpl)userIdMapper, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				UserIdMapperImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UserIdMapperModelImpl userIdMapperModelImpl) {

		Object[] args = new Object[] {
			userIdMapperModelImpl.getUserId(), userIdMapperModelImpl.getType()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_T, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_T, args, userIdMapperModelImpl, false);

		args = new Object[] {
			userIdMapperModelImpl.getType(),
			userIdMapperModelImpl.getExternalUserId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByT_E, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByT_E, args, userIdMapperModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		UserIdMapperModelImpl userIdMapperModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				userIdMapperModelImpl.getUserId(),
				userIdMapperModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_T, args);
		}

		if ((userIdMapperModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				userIdMapperModelImpl.getOriginalUserId(),
				userIdMapperModelImpl.getOriginalType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_T, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				userIdMapperModelImpl.getType(),
				userIdMapperModelImpl.getExternalUserId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_E, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_E, args);
		}

		if ((userIdMapperModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_E.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				userIdMapperModelImpl.getOriginalType(),
				userIdMapperModelImpl.getOriginalExternalUserId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_E, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_E, args);
		}
	}

	/**
	 * Creates a new user ID mapper with the primary key. Does not add the user ID mapper to the database.
	 *
	 * @param userIdMapperId the primary key for the new user ID mapper
	 * @return the new user ID mapper
	 */
	@Override
	public UserIdMapper create(long userIdMapperId) {
		UserIdMapper userIdMapper = new UserIdMapperImpl();

		userIdMapper.setNew(true);
		userIdMapper.setPrimaryKey(userIdMapperId);

		userIdMapper.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userIdMapper;
	}

	/**
	 * Removes the user ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper that was removed
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper remove(long userIdMapperId)
		throws NoSuchUserIdMapperException {

		return remove((Serializable)userIdMapperId);
	}

	/**
	 * Removes the user ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user ID mapper
	 * @return the user ID mapper that was removed
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper remove(Serializable primaryKey)
		throws NoSuchUserIdMapperException {

		Session session = null;

		try {
			session = openSession();

			UserIdMapper userIdMapper = (UserIdMapper)session.get(
				UserIdMapperImpl.class, primaryKey);

			if (userIdMapper == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserIdMapperException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userIdMapper);
		}
		catch (NoSuchUserIdMapperException nsee) {
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
	protected UserIdMapper removeImpl(UserIdMapper userIdMapper) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userIdMapper)) {
				userIdMapper = (UserIdMapper)session.get(
					UserIdMapperImpl.class, userIdMapper.getPrimaryKeyObj());
			}

			if (userIdMapper != null) {
				session.delete(userIdMapper);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userIdMapper != null) {
			clearCache(userIdMapper);
		}

		return userIdMapper;
	}

	@Override
	public UserIdMapper updateImpl(UserIdMapper userIdMapper) {
		boolean isNew = userIdMapper.isNew();

		if (!(userIdMapper instanceof UserIdMapperModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userIdMapper.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userIdMapper);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userIdMapper proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserIdMapper implementation " +
					userIdMapper.getClass());
		}

		UserIdMapperModelImpl userIdMapperModelImpl =
			(UserIdMapperModelImpl)userIdMapper;

		Session session = null;

		try {
			session = openSession();

			if (userIdMapper.isNew()) {
				session.save(userIdMapper);

				userIdMapper.setNew(false);
			}
			else {
				userIdMapper = (UserIdMapper)session.merge(userIdMapper);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!UserIdMapperModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {userIdMapperModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((userIdMapperModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userIdMapperModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {userIdMapperModelImpl.getUserId()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}
		}

		EntityCacheUtil.putResult(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED, UserIdMapperImpl.class,
			userIdMapper.getPrimaryKey(), userIdMapper, false);

		clearUniqueFindersCache(userIdMapperModelImpl, false);
		cacheUniqueFindersCache(userIdMapperModelImpl);

		userIdMapper.resetOriginalValues();

		return userIdMapper;
	}

	/**
	 * Returns the user ID mapper with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user ID mapper
	 * @return the user ID mapper
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserIdMapperException {

		UserIdMapper userIdMapper = fetchByPrimaryKey(primaryKey);

		if (userIdMapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserIdMapperException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userIdMapper;
	}

	/**
	 * Returns the user ID mapper with the primary key or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper findByPrimaryKey(long userIdMapperId)
		throws NoSuchUserIdMapperException {

		return findByPrimaryKey((Serializable)userIdMapperId);
	}

	/**
	 * Returns the user ID mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper, or <code>null</code> if a user ID mapper with the primary key could not be found
	 */
	@Override
	public UserIdMapper fetchByPrimaryKey(long userIdMapperId) {
		return fetchByPrimaryKey((Serializable)userIdMapperId);
	}

	/**
	 * Returns all the user ID mappers.
	 *
	 * @return the user ID mappers
	 */
	@Override
	public List<UserIdMapper> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @return the range of user ID mappers
	 */
	@Override
	public List<UserIdMapper> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user ID mappers
	 */
	@Override
	public List<UserIdMapper> findAll(
		int start, int end, OrderByComparator<UserIdMapper> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user ID mappers
	 */
	@Override
	public List<UserIdMapper> findAll(
		int start, int end, OrderByComparator<UserIdMapper> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserIdMapper> list = null;

		if (useFinderCache) {
			list = (List<UserIdMapper>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_USERIDMAPPER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERIDMAPPER;

				sql = sql.concat(UserIdMapperModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<UserIdMapper>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the user ID mappers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserIdMapper userIdMapper : findAll()) {
			remove(userIdMapper);
		}
	}

	/**
	 * Returns the number of user ID mappers.
	 *
	 * @return the number of user ID mappers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERIDMAPPER);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userIdMapperId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERIDMAPPER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserIdMapperModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user ID mapper persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			UserIdMapperModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathFetchByU_T = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] {Long.class.getName(), String.class.getName()},
			UserIdMapperModelImpl.USERID_COLUMN_BITMASK |
			UserIdMapperModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByU_T = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByT_E = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, UserIdMapperImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_E",
			new String[] {String.class.getName(), String.class.getName()},
			UserIdMapperModelImpl.TYPE_COLUMN_BITMASK |
			UserIdMapperModelImpl.EXTERNALUSERID_COLUMN_BITMASK);

		_finderPathCountByT_E = new FinderPath(
			UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_E",
			new String[] {String.class.getName(), String.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserIdMapperImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERIDMAPPER =
		"SELECT userIdMapper FROM UserIdMapper userIdMapper";

	private static final String _SQL_SELECT_USERIDMAPPER_WHERE =
		"SELECT userIdMapper FROM UserIdMapper userIdMapper WHERE ";

	private static final String _SQL_COUNT_USERIDMAPPER =
		"SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper";

	private static final String _SQL_COUNT_USERIDMAPPER_WHERE =
		"SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userIdMapper.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserIdMapper exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserIdMapper exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserIdMapperPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}