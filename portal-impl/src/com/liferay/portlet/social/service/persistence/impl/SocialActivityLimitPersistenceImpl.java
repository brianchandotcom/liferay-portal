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

package com.liferay.portlet.social.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portlet.social.model.impl.SocialActivityLimitImpl;
import com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl;
import com.liferay.social.kernel.exception.NoSuchActivityLimitException;
import com.liferay.social.kernel.model.SocialActivityLimit;
import com.liferay.social.kernel.service.persistence.SocialActivityLimitPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the social activity limit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityLimitPersistenceImpl
	extends BasePersistenceImpl<SocialActivityLimit>
	implements SocialActivityLimitPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SocialActivityLimitUtil</code> to access the social activity limit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SocialActivityLimitImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the social activity limits where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity limits where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<SocialActivityLimit> list = null;

		if (useFinderCache) {
			list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityLimit socialActivityLimit : list) {
					if (groupId != socialActivityLimit.getGroupId()) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SocialActivityLimit>)QueryUtil.list(
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
	 * Returns the first social activity limit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByGroupId_First(
			long groupId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByGroupId_First(
			groupId, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the first social activity limit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByGroupId_First(
		long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		List<SocialActivityLimit> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity limit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByGroupId_Last(
			long groupId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the last social activity limit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByGroupId_Last(
		long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivityLimit> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where groupId = &#63;.
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit[] findByGroupId_PrevAndNext(
			long activityLimitId, long groupId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = findByPrimaryKey(
			activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, socialActivityLimit, groupId, orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByGroupId_PrevAndNext(
				session, socialActivityLimit, groupId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityLimit getByGroupId_PrevAndNext(
		Session session, SocialActivityLimit socialActivityLimit, long groupId,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivityLimit)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityLimit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity limits where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivityLimit socialActivityLimit :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity limits
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"socialActivityLimit.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the social activity limits where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity limits where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
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

		List<SocialActivityLimit> list = null;

		if (useFinderCache) {
			list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityLimit socialActivityLimit : list) {
					if (userId != socialActivityLimit.getUserId()) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<SocialActivityLimit>)QueryUtil.list(
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
	 * Returns the first social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByUserId_First(
			long userId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByUserId_First(
			userId, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the first social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByUserId_First(
		long userId, OrderByComparator<SocialActivityLimit> orderByComparator) {

		List<SocialActivityLimit> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByUserId_Last(
			long userId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByUserId_Last(
			userId, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the last social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByUserId_Last(
		long userId, OrderByComparator<SocialActivityLimit> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SocialActivityLimit> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit[] findByUserId_PrevAndNext(
			long activityLimitId, long userId,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = findByPrimaryKey(
			activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, socialActivityLimit, userId, orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByUserId_PrevAndNext(
				session, socialActivityLimit, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityLimit getByUserId_PrevAndNext(
		Session session, SocialActivityLimit socialActivityLimit, long userId,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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
			query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivityLimit)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityLimit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity limits where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SocialActivityLimit socialActivityLimit :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social activity limits
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

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
		"socialActivityLimit.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<SocialActivityLimit> list = null;

		if (useFinderCache) {
			list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityLimit socialActivityLimit : list) {
					if ((classNameId != socialActivityLimit.getClassNameId()) ||
						(classPK != socialActivityLimit.getClassPK())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SocialActivityLimit>)QueryUtil.list(
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
	 * Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		List<SocialActivityLimit> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SocialActivityLimit> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit[] findByC_C_PrevAndNext(
			long activityLimitId, long classNameId, long classPK,
			OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = findByPrimaryKey(
			activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, socialActivityLimit, classNameId, classPK,
				orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByC_C_PrevAndNext(
				session, socialActivityLimit, classNameId, classPK,
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

	protected SocialActivityLimit getByC_C_PrevAndNext(
		Session session, SocialActivityLimit socialActivityLimit,
		long classNameId, long classPK,
		OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivityLimit)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityLimit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity limits where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (SocialActivityLimit socialActivityLimit :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching social activity limits
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"socialActivityLimit.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"socialActivityLimit.classPK = ?";

	private FinderPath _finderPathFetchByG_U_C_C_A_A;
	private FinderPath _finderPathCountByG_U_C_C_A_A;

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or throws a <code>NoSuchActivityLimitException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the matching social activity limit
	 * @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit findByG_U_C_C_A_A(
			long groupId, long userId, long classNameId, long classPK,
			int activityType, String activityCounterName)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByG_U_C_C_A_A(
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName);

		if (socialActivityLimit == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", activityType=");
			msg.append(activityType);

			msg.append(", activityCounterName=");
			msg.append(activityCounterName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchActivityLimitException(msg.toString());
		}

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByG_U_C_C_A_A(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, String activityCounterName) {

		return fetchByG_U_C_C_A_A(
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName, true);
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 */
	@Override
	public SocialActivityLimit fetchByG_U_C_C_A_A(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, String activityCounterName, boolean useFinderCache) {

		activityCounterName = Objects.toString(activityCounterName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, activityType,
				activityCounterName
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_U_C_C_A_A, finderArgs, this);
		}

		if (result instanceof SocialActivityLimit) {
			SocialActivityLimit socialActivityLimit =
				(SocialActivityLimit)result;

			if ((groupId != socialActivityLimit.getGroupId()) ||
				(userId != socialActivityLimit.getUserId()) ||
				(classNameId != socialActivityLimit.getClassNameId()) ||
				(classPK != socialActivityLimit.getClassPK()) ||
				(activityType != socialActivityLimit.getActivityType()) ||
				!Objects.equals(
					activityCounterName,
					socialActivityLimit.getActivityCounterName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2);

			boolean bindActivityCounterName = false;

			if (activityCounterName.isEmpty()) {
				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3);
			}
			else {
				bindActivityCounterName = true;

				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(activityType);

				if (bindActivityCounterName) {
					qPos.add(activityCounterName);
				}

				List<SocialActivityLimit> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_U_C_C_A_A, finderArgs, list);
					}
				}
				else {
					SocialActivityLimit socialActivityLimit = list.get(0);

					result = socialActivityLimit;

					cacheResult(socialActivityLimit);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByG_U_C_C_A_A, finderArgs);
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
			return (SocialActivityLimit)result;
		}
	}

	/**
	 * Removes the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the social activity limit that was removed
	 */
	@Override
	public SocialActivityLimit removeByG_U_C_C_A_A(
			long groupId, long userId, long classNameId, long classPK,
			int activityType, String activityCounterName)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = findByG_U_C_C_A_A(
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName);

		return remove(socialActivityLimit);
	}

	/**
	 * Returns the number of social activity limits where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the number of matching social activity limits
	 */
	@Override
	public int countByG_U_C_C_A_A(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, String activityCounterName) {

		activityCounterName = Objects.toString(activityCounterName, "");

		FinderPath finderPath = _finderPathCountByG_U_C_C_A_A;

		Object[] finderArgs = new Object[] {
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2);

			boolean bindActivityCounterName = false;

			if (activityCounterName.isEmpty()) {
				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3);
			}
			else {
				bindActivityCounterName = true;

				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(activityType);

				if (bindActivityCounterName) {
					qPos.add(activityCounterName);
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

	private static final String _FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2 =
		"socialActivityLimit.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_C_A_A_USERID_2 =
		"socialActivityLimit.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2 =
		"socialActivityLimit.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2 =
		"socialActivityLimit.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2 =
		"socialActivityLimit.activityType = ? AND ";

	private static final String
		_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2 =
			"socialActivityLimit.activityCounterName = ?";

	private static final String
		_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3 =
			"(socialActivityLimit.activityCounterName IS NULL OR socialActivityLimit.activityCounterName = '')";

	public SocialActivityLimitPersistenceImpl() {
		setModelClass(SocialActivityLimit.class);

		setModelImplClass(SocialActivityLimitImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the social activity limit in the entity cache if it is enabled.
	 *
	 * @param socialActivityLimit the social activity limit
	 */
	@Override
	public void cacheResult(SocialActivityLimit socialActivityLimit) {
		EntityCacheUtil.putResult(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_U_C_C_A_A,
			new Object[] {
				socialActivityLimit.getGroupId(),
				socialActivityLimit.getUserId(),
				socialActivityLimit.getClassNameId(),
				socialActivityLimit.getClassPK(),
				socialActivityLimit.getActivityType(),
				socialActivityLimit.getActivityCounterName()
			},
			socialActivityLimit);

		socialActivityLimit.resetOriginalValues();
	}

	/**
	 * Caches the social activity limits in the entity cache if it is enabled.
	 *
	 * @param socialActivityLimits the social activity limits
	 */
	@Override
	public void cacheResult(List<SocialActivityLimit> socialActivityLimits) {
		for (SocialActivityLimit socialActivityLimit : socialActivityLimits) {
			if (EntityCacheUtil.getResult(
					SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivityLimitImpl.class,
					socialActivityLimit.getPrimaryKey()) == null) {

				cacheResult(socialActivityLimit);
			}
			else {
				socialActivityLimit.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity limits.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SocialActivityLimitImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity limit.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityLimit socialActivityLimit) {
		EntityCacheUtil.removeResult(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SocialActivityLimitModelImpl)socialActivityLimit, true);
	}

	@Override
	public void clearCache(List<SocialActivityLimit> socialActivityLimits) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityLimit socialActivityLimit : socialActivityLimits) {
			EntityCacheUtil.removeResult(
				SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityLimitImpl.class,
				socialActivityLimit.getPrimaryKey());

			clearUniqueFindersCache(
				(SocialActivityLimitModelImpl)socialActivityLimit, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityLimitImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SocialActivityLimitModelImpl socialActivityLimitModelImpl) {

		Object[] args = new Object[] {
			socialActivityLimitModelImpl.getGroupId(),
			socialActivityLimitModelImpl.getUserId(),
			socialActivityLimitModelImpl.getClassNameId(),
			socialActivityLimitModelImpl.getClassPK(),
			socialActivityLimitModelImpl.getActivityType(),
			socialActivityLimitModelImpl.getActivityCounterName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_U_C_C_A_A, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_U_C_C_A_A, args, socialActivityLimitModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SocialActivityLimitModelImpl socialActivityLimitModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				socialActivityLimitModelImpl.getGroupId(),
				socialActivityLimitModelImpl.getUserId(),
				socialActivityLimitModelImpl.getClassNameId(),
				socialActivityLimitModelImpl.getClassPK(),
				socialActivityLimitModelImpl.getActivityType(),
				socialActivityLimitModelImpl.getActivityCounterName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_U_C_C_A_A, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_U_C_C_A_A, args);
		}

		if ((socialActivityLimitModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_U_C_C_A_A.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				socialActivityLimitModelImpl.getOriginalGroupId(),
				socialActivityLimitModelImpl.getOriginalUserId(),
				socialActivityLimitModelImpl.getOriginalClassNameId(),
				socialActivityLimitModelImpl.getOriginalClassPK(),
				socialActivityLimitModelImpl.getOriginalActivityType(),
				socialActivityLimitModelImpl.getOriginalActivityCounterName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_U_C_C_A_A, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_U_C_C_A_A, args);
		}
	}

	/**
	 * Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	 *
	 * @param activityLimitId the primary key for the new social activity limit
	 * @return the new social activity limit
	 */
	@Override
	public SocialActivityLimit create(long activityLimitId) {
		SocialActivityLimit socialActivityLimit = new SocialActivityLimitImpl();

		socialActivityLimit.setNew(true);
		socialActivityLimit.setPrimaryKey(activityLimitId);

		socialActivityLimit.setCompanyId(CompanyThreadLocal.getCompanyId());

		return socialActivityLimit;
	}

	/**
	 * Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit remove(long activityLimitId)
		throws NoSuchActivityLimitException {

		return remove((Serializable)activityLimitId);
	}

	/**
	 * Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit remove(Serializable primaryKey)
		throws NoSuchActivityLimitException {

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit socialActivityLimit =
				(SocialActivityLimit)session.get(
					SocialActivityLimitImpl.class, primaryKey);

			if (socialActivityLimit == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityLimitException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(socialActivityLimit);
		}
		catch (NoSuchActivityLimitException nsee) {
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
	protected SocialActivityLimit removeImpl(
		SocialActivityLimit socialActivityLimit) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivityLimit)) {
				socialActivityLimit = (SocialActivityLimit)session.get(
					SocialActivityLimitImpl.class,
					socialActivityLimit.getPrimaryKeyObj());
			}

			if (socialActivityLimit != null) {
				session.delete(socialActivityLimit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityLimit != null) {
			clearCache(socialActivityLimit);
		}

		return socialActivityLimit;
	}

	@Override
	public SocialActivityLimit updateImpl(
		SocialActivityLimit socialActivityLimit) {

		boolean isNew = socialActivityLimit.isNew();

		if (!(socialActivityLimit instanceof SocialActivityLimitModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(socialActivityLimit.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					socialActivityLimit);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in socialActivityLimit proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SocialActivityLimit implementation " +
					socialActivityLimit.getClass());
		}

		SocialActivityLimitModelImpl socialActivityLimitModelImpl =
			(SocialActivityLimitModelImpl)socialActivityLimit;

		Session session = null;

		try {
			session = openSession();

			if (socialActivityLimit.isNew()) {
				session.save(socialActivityLimit);

				socialActivityLimit.setNew(false);
			}
			else {
				socialActivityLimit = (SocialActivityLimit)session.merge(
					socialActivityLimit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SocialActivityLimitModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				socialActivityLimitModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {socialActivityLimitModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {
				socialActivityLimitModelImpl.getClassNameId(),
				socialActivityLimitModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((socialActivityLimitModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivityLimitModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {socialActivityLimitModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((socialActivityLimitModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivityLimitModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {socialActivityLimitModelImpl.getUserId()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((socialActivityLimitModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivityLimitModelImpl.getOriginalClassNameId(),
					socialActivityLimitModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					socialActivityLimitModelImpl.getClassNameId(),
					socialActivityLimitModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}
		}

		EntityCacheUtil.putResult(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit, false);

		clearUniqueFindersCache(socialActivityLimitModelImpl, false);
		cacheUniqueFindersCache(socialActivityLimitModelImpl);

		socialActivityLimit.resetOriginalValues();

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivityLimitException {

		SocialActivityLimit socialActivityLimit = fetchByPrimaryKey(primaryKey);

		if (socialActivityLimit == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivityLimitException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit with the primary key or throws a <code>NoSuchActivityLimitException</code> if it could not be found.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit
	 * @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit findByPrimaryKey(long activityLimitId)
		throws NoSuchActivityLimitException {

		return findByPrimaryKey((Serializable)activityLimitId);
	}

	/**
	 * Returns the social activity limit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit, or <code>null</code> if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit fetchByPrimaryKey(long activityLimitId) {
		return fetchByPrimaryKey((Serializable)activityLimitId);
	}

	/**
	 * Returns all the social activity limits.
	 *
	 * @return the social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findAll(
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of social activity limits
	 */
	@Override
	public List<SocialActivityLimit> findAll(
		int start, int end,
		OrderByComparator<SocialActivityLimit> orderByComparator,
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

		List<SocialActivityLimit> list = null;

		if (useFinderCache) {
			list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYLIMIT;

				sql = sql.concat(SocialActivityLimitModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SocialActivityLimit>)QueryUtil.list(
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
	 * Removes all the social activity limits from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivityLimit socialActivityLimit : findAll()) {
			remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits.
	 *
	 * @return the number of social activity limits
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYLIMIT);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "activityLimitId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SOCIALACTIVITYLIMIT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SocialActivityLimitModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the social activity limit persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SocialActivityLimitModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			SocialActivityLimitModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			SocialActivityLimitModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByG_U_C_C_A_A = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_C_C_A_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			SocialActivityLimitModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.ACTIVITYTYPE_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.ACTIVITYCOUNTERNAME_COLUMN_BITMASK);

		_finderPathCountByG_U_C_C_A_A = new FinderPath(
			SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_C_A_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivityLimitImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT =
		"SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit";

	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE =
		"SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit WHERE ";

	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT =
		"SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit";

	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE =
		"SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityLimit.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SocialActivityLimit exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SocialActivityLimit exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityLimitPersistenceImpl.class);

}