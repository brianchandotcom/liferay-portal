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
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portlet.social.model.impl.SocialActivitySetImpl;
import com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl;
import com.liferay.social.kernel.exception.NoSuchActivitySetException;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.model.SocialActivitySetTable;
import com.liferay.social.kernel.service.persistence.SocialActivitySetPersistence;

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
 * The persistence implementation for the social activity set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySetPersistenceImpl
	extends BasePersistenceImpl<SocialActivitySet>
	implements SocialActivitySetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SocialActivitySetUtil</code> to access the social activity set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SocialActivitySetImpl.class.getName();

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
	 * Returns all the social activity sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

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

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if (groupId != socialActivitySet.getGroupId()) {
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

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByGroupId_First(
			long groupId,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByGroupId_First(
			groupId, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByGroupId_First(
		long groupId, OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByGroupId_Last(
			long groupId,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByGroupId_Last(
		long groupId, OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByGroupId_PrevAndNext(
			long activitySetId, long groupId,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, socialActivitySet, groupId, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByGroupId_PrevAndNext(
				session, socialActivitySet, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByGroupId_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator,
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

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
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
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivitySet socialActivitySet :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

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

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

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
		"socialActivitySet.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the social activity sets where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

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

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if (userId != socialActivitySet.getUserId()) {
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

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByUserId_First(
			long userId, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByUserId_First(
			userId, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByUserId_First(
		long userId, OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByUserId_Last(
			long userId, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByUserId_Last(
			userId, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByUserId_Last(
		long userId, OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByUserId_PrevAndNext(
			long activitySetId, long userId,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, socialActivitySet, userId, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByUserId_PrevAndNext(
				session, socialActivitySet, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByUserId_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long userId,
		OrderByComparator<SocialActivitySet> orderByComparator,
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

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
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
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SocialActivitySet socialActivitySet :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByUserId(long userId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

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

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

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
		"socialActivitySet.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_U_T;
	private FinderPath _finderPathWithoutPaginationFindByG_U_T;
	private FinderPath _finderPathCountByG_U_T;

	/**
	 * Returns all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(
		long groupId, long userId, int type) {

		return findByG_U_T(
			groupId, userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(
		long groupId, long userId, int type, int start, int end) {

		return findByG_U_T(groupId, userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(
		long groupId, long userId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByG_U_T(
			groupId, userId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(
		long groupId, long userId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_U_T;
				finderArgs = new Object[] {groupId, userId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_U_T;
			finderArgs = new Object[] {
				groupId, userId, type, start, end, orderByComparator
			};
		}

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((groupId != socialActivitySet.getGroupId()) ||
						(userId != socialActivitySet.getUserId()) ||
						(type != socialActivitySet.getType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(type);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_T_First(
			long groupId, long userId, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByG_U_T_First(
			groupId, userId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_T_First(
		long groupId, long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByG_U_T(
			groupId, userId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_T_Last(
			long groupId, long userId, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByG_U_T_Last(
			groupId, userId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_T_Last(
		long groupId, long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByG_U_T(groupId, userId, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByG_U_T(
			groupId, userId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByG_U_T_PrevAndNext(
			long activitySetId, long groupId, long userId, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByG_U_T_PrevAndNext(
				session, socialActivitySet, groupId, userId, type,
				orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByG_U_T_PrevAndNext(
				session, socialActivitySet, groupId, userId, type,
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

	protected SocialActivitySet getByG_U_T_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long groupId,
		long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		sb.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_T_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_T_TYPE_2);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 */
	@Override
	public void removeByG_U_T(long groupId, long userId, int type) {
		for (SocialActivitySet socialActivitySet :
				findByG_U_T(
					groupId, userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByG_U_T(long groupId, long userId, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_U_T;

			finderArgs = new Object[] {groupId, userId, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_G_U_T_GROUPID_2 =
		"socialActivitySet.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_T_USERID_2 =
		"socialActivitySet.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_T_TYPE_2 =
		"socialActivitySet.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(
		long classNameId, long classPK, int type) {

		return findByC_C_T(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_T;
				finderArgs = new Object[] {classNameId, classPK, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_T;
			finderArgs = new Object[] {
				classNameId, classPK, type, start, end, orderByComparator
			};
		}

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((classNameId != socialActivitySet.getClassNameId()) ||
						(classPK != socialActivitySet.getClassPK()) ||
						(type != socialActivitySet.getType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByC_C_T_First(
			long classNameId, long classPK, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByC_C_T(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByC_C_T_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByC_C_T(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByC_C_T_PrevAndNext(
			long activitySetId, long classNameId, long classPK, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByC_C_T_PrevAndNext(
				session, socialActivitySet, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByC_C_T_PrevAndNext(
				session, socialActivitySet, classNameId, classPK, type,
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

	protected SocialActivitySet getByC_C_T_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, int type) {
		for (SocialActivitySet socialActivitySet :
				findByC_C_T(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_T;

			finderArgs = new Object[] {classNameId, classPK, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 =
		"socialActivitySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 =
		"socialActivitySet.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 =
		"socialActivitySet.type = ?";

	private FinderPath _finderPathWithPaginationFindByG_U_C_T;
	private FinderPath _finderPathWithoutPaginationFindByG_U_C_T;
	private FinderPath _finderPathCountByG_U_C_T;

	/**
	 * Returns all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type) {

		return findByG_U_C_T(
			groupId, userId, classNameId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end) {

		return findByG_U_C_T(
			groupId, userId, classNameId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByG_U_C_T(
			groupId, userId, classNameId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_U_C_T;
				finderArgs = new Object[] {groupId, userId, classNameId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_U_C_T;
			finderArgs = new Object[] {
				groupId, userId, classNameId, type, start, end,
				orderByComparator
			};
		}

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((groupId != socialActivitySet.getGroupId()) ||
						(userId != socialActivitySet.getUserId()) ||
						(classNameId != socialActivitySet.getClassNameId()) ||
						(type != socialActivitySet.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(type);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_C_T_First(
			long groupId, long userId, long classNameId, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByG_U_C_T_First(
			groupId, userId, classNameId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_C_T_First(
		long groupId, long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByG_U_C_T(
			groupId, userId, classNameId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_C_T_Last(
			long groupId, long userId, long classNameId, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByG_U_C_T_Last(
			groupId, userId, classNameId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_C_T_Last(
		long groupId, long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByG_U_C_T(groupId, userId, classNameId, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByG_U_C_T(
			groupId, userId, classNameId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByG_U_C_T_PrevAndNext(
			long activitySetId, long groupId, long userId, long classNameId,
			int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByG_U_C_T_PrevAndNext(
				session, socialActivitySet, groupId, userId, classNameId, type,
				orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByG_U_C_T_PrevAndNext(
				session, socialActivitySet, groupId, userId, classNameId, type,
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

	protected SocialActivitySet getByG_U_C_T_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long groupId,
		long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		sb.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(classNameId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 */
	@Override
	public void removeByG_U_C_T(
		long groupId, long userId, long classNameId, int type) {

		for (SocialActivitySet socialActivitySet :
				findByG_U_C_T(
					groupId, userId, classNameId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByG_U_C_T(
		long groupId, long userId, long classNameId, int type) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_U_C_T;

			finderArgs = new Object[] {groupId, userId, classNameId, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_G_U_C_T_GROUPID_2 =
		"socialActivitySet.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_T_USERID_2 =
		"socialActivitySet.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2 =
		"socialActivitySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_T_TYPE_2 =
		"socialActivitySet.type = ?";

	private FinderPath _finderPathWithPaginationFindByU_C_C_T;
	private FinderPath _finderPathWithoutPaginationFindByU_C_C_T;
	private FinderPath _finderPathCountByU_C_C_T;

	/**
	 * Returns all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(
		long userId, long classNameId, long classPK, int type) {

		return findByU_C_C_T(
			userId, classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(
		long userId, long classNameId, long classPK, int type, int start,
		int end) {

		return findByU_C_C_T(
			userId, classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(
		long userId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator) {

		return findByU_C_C_T(
			userId, classNameId, classPK, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(
		long userId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU_C_C_T;
				finderArgs = new Object[] {userId, classNameId, classPK, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU_C_C_T;
			finderArgs = new Object[] {
				userId, classNameId, classPK, type, start, end,
				orderByComparator
			};
		}

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((userId != socialActivitySet.getUserId()) ||
						(classNameId != socialActivitySet.getClassNameId()) ||
						(classPK != socialActivitySet.getClassPK()) ||
						(type != socialActivitySet.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByU_C_C_T_First(
			long userId, long classNameId, long classPK, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByU_C_C_T_First(
			userId, classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByU_C_C_T_First(
		long userId, long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		List<SocialActivitySet> list = findByU_C_C_T(
			userId, classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByU_C_C_T_Last(
			long userId, long classNameId, long classPK, int type,
			OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByU_C_C_T_Last(
			userId, classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchActivitySetException(sb.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByU_C_C_T_Last(
		long userId, long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		int count = countByU_C_C_T(userId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByU_C_C_T(
			userId, classNameId, classPK, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByU_C_C_T_PrevAndNext(
			long activitySetId, long userId, long classNameId, long classPK,
			int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByU_C_C_T_PrevAndNext(
				session, socialActivitySet, userId, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByU_C_C_T_PrevAndNext(
				session, socialActivitySet, userId, classNameId, classPK, type,
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

	protected SocialActivitySet getByU_C_C_T_PrevAndNext(
		Session session, SocialActivitySet socialActivitySet, long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		sb.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

		sb.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

		sb.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

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
			sb.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByU_C_C_T(
		long userId, long classNameId, long classPK, int type) {

		for (SocialActivitySet socialActivitySet :
				findByU_C_C_T(
					userId, classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByU_C_C_T(
		long userId, long classNameId, long classPK, int type) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_C_C_T;

			finderArgs = new Object[] {userId, classNameId, classPK, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_U_C_C_T_USERID_2 =
		"socialActivitySet.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2 =
		"socialActivitySet.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_C_T_CLASSPK_2 =
		"socialActivitySet.classPK = ? AND ";

	private static final String _FINDER_COLUMN_U_C_C_T_TYPE_2 =
		"socialActivitySet.type = ?";

	public SocialActivitySetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SocialActivitySet.class);

		setModelImplClass(SocialActivitySetImpl.class);
		setModelPKClass(long.class);

		setTable(SocialActivitySetTable.INSTANCE);
	}

	/**
	 * Caches the social activity set in the entity cache if it is enabled.
	 *
	 * @param socialActivitySet the social activity set
	 */
	@Override
	public void cacheResult(SocialActivitySet socialActivitySet) {
		if (socialActivitySet.getCtCollectionId() != 0) {
			socialActivitySet.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet);

		socialActivitySet.resetOriginalValues();
	}

	/**
	 * Caches the social activity sets in the entity cache if it is enabled.
	 *
	 * @param socialActivitySets the social activity sets
	 */
	@Override
	public void cacheResult(List<SocialActivitySet> socialActivitySets) {
		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			if (socialActivitySet.getCtCollectionId() != 0) {
				socialActivitySet.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					SocialActivitySetImpl.class,
					socialActivitySet.getPrimaryKey()) == null) {

				cacheResult(socialActivitySet);
			}
			else {
				socialActivitySet.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity sets.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SocialActivitySetImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity set.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivitySet socialActivitySet) {
		EntityCacheUtil.removeResult(
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SocialActivitySet> socialActivitySets) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			EntityCacheUtil.removeResult(
				SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				SocialActivitySetImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	 *
	 * @param activitySetId the primary key for the new social activity set
	 * @return the new social activity set
	 */
	@Override
	public SocialActivitySet create(long activitySetId) {
		SocialActivitySet socialActivitySet = new SocialActivitySetImpl();

		socialActivitySet.setNew(true);
		socialActivitySet.setPrimaryKey(activitySetId);

		socialActivitySet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return socialActivitySet;
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet remove(long activitySetId)
		throws NoSuchActivitySetException {

		return remove((Serializable)activitySetId);
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet remove(Serializable primaryKey)
		throws NoSuchActivitySetException {

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet socialActivitySet =
				(SocialActivitySet)session.get(
					SocialActivitySetImpl.class, primaryKey);

			if (socialActivitySet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivitySetException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(socialActivitySet);
		}
		catch (NoSuchActivitySetException noSuchEntityException) {
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
	protected SocialActivitySet removeImpl(
		SocialActivitySet socialActivitySet) {

		if (!CTPersistenceHelperUtil.isRemove(socialActivitySet)) {
			return socialActivitySet;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivitySet)) {
				socialActivitySet = (SocialActivitySet)session.get(
					SocialActivitySetImpl.class,
					socialActivitySet.getPrimaryKeyObj());
			}

			if (socialActivitySet != null) {
				session.delete(socialActivitySet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySet != null) {
			clearCache(socialActivitySet);
		}

		return socialActivitySet;
	}

	@Override
	public SocialActivitySet updateImpl(SocialActivitySet socialActivitySet) {
		boolean isNew = socialActivitySet.isNew();

		if (!(socialActivitySet instanceof SocialActivitySetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(socialActivitySet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					socialActivitySet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in socialActivitySet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SocialActivitySet implementation " +
					socialActivitySet.getClass());
		}

		SocialActivitySetModelImpl socialActivitySetModelImpl =
			(SocialActivitySetModelImpl)socialActivitySet;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(socialActivitySet)) {
				if (!isNew) {
					session.evict(
						SocialActivitySetImpl.class,
						socialActivitySet.getPrimaryKeyObj());
				}

				session.save(socialActivitySet);

				socialActivitySet.setNew(false);
			}
			else {
				socialActivitySet = (SocialActivitySet)session.merge(
					socialActivitySet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySet.getCtCollectionId() != 0) {
			socialActivitySet.resetOriginalValues();

			return socialActivitySet;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				socialActivitySetModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {socialActivitySetModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {
				socialActivitySetModelImpl.getGroupId(),
				socialActivitySetModelImpl.getUserId(),
				socialActivitySetModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_U_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_U_T, args);

			args = new Object[] {
				socialActivitySetModelImpl.getClassNameId(),
				socialActivitySetModelImpl.getClassPK(),
				socialActivitySetModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C_T, args);

			args = new Object[] {
				socialActivitySetModelImpl.getGroupId(),
				socialActivitySetModelImpl.getUserId(),
				socialActivitySetModelImpl.getClassNameId(),
				socialActivitySetModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_U_C_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_U_C_T, args);

			args = new Object[] {
				socialActivitySetModelImpl.getUserId(),
				socialActivitySetModelImpl.getClassNameId(),
				socialActivitySetModelImpl.getClassPK(),
				socialActivitySetModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_C_C_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_C_C_T, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {socialActivitySetModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {socialActivitySetModelImpl.getUserId()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_U_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalGroupId(),
					socialActivitySetModelImpl.getOriginalUserId(),
					socialActivitySetModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_U_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_U_T, args);

				args = new Object[] {
					socialActivitySetModelImpl.getGroupId(),
					socialActivitySetModelImpl.getUserId(),
					socialActivitySetModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_U_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_U_T, args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalClassNameId(),
					socialActivitySetModelImpl.getOriginalClassPK(),
					socialActivitySetModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);

				args = new Object[] {
					socialActivitySetModelImpl.getClassNameId(),
					socialActivitySetModelImpl.getClassPK(),
					socialActivitySetModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_U_C_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalGroupId(),
					socialActivitySetModelImpl.getOriginalUserId(),
					socialActivitySetModelImpl.getOriginalClassNameId(),
					socialActivitySetModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_U_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_U_C_T, args);

				args = new Object[] {
					socialActivitySetModelImpl.getGroupId(),
					socialActivitySetModelImpl.getUserId(),
					socialActivitySetModelImpl.getClassNameId(),
					socialActivitySetModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_U_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_U_C_T, args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_C_C_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivitySetModelImpl.getOriginalUserId(),
					socialActivitySetModelImpl.getOriginalClassNameId(),
					socialActivitySetModelImpl.getOriginalClassPK(),
					socialActivitySetModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_C_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_C_C_T, args);

				args = new Object[] {
					socialActivitySetModelImpl.getUserId(),
					socialActivitySetModelImpl.getClassNameId(),
					socialActivitySetModelImpl.getClassPK(),
					socialActivitySetModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_C_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_C_C_T, args);
			}
		}

		EntityCacheUtil.putResult(
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet, false);

		socialActivitySet.resetOriginalValues();

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivitySetException {

		SocialActivitySet socialActivitySet = fetchByPrimaryKey(primaryKey);

		if (socialActivitySet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivitySetException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or throws a <code>NoSuchActivitySetException</code> if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet findByPrimaryKey(long activitySetId)
		throws NoSuchActivitySetException {

		return findByPrimaryKey((Serializable)activitySetId);
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(SocialActivitySet.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SocialActivitySet socialActivitySet = null;

		Session session = null;

		try {
			session = openSession();

			socialActivitySet = (SocialActivitySet)session.get(
				SocialActivitySetImpl.class, primaryKey);

			if (socialActivitySet != null) {
				cacheResult(socialActivitySet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet fetchByPrimaryKey(long activitySetId) {
		return fetchByPrimaryKey((Serializable)activitySetId);
	}

	@Override
	public Map<Serializable, SocialActivitySet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(SocialActivitySet.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SocialActivitySet> map =
			new HashMap<Serializable, SocialActivitySet>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SocialActivitySet socialActivitySet = fetchByPrimaryKey(primaryKey);

			if (socialActivitySet != null) {
				map.put(primaryKey, socialActivitySet);
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

			for (SocialActivitySet socialActivitySet :
					(List<SocialActivitySet>)query.list()) {

				map.put(
					socialActivitySet.getPrimaryKeyObj(), socialActivitySet);

				cacheResult(socialActivitySet);
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
	 * Returns all the social activity sets.
	 *
	 * @return the social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(
		int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(
		int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

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

		List<SocialActivitySet> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivitySet>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SOCIALACTIVITYSET);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSET;

				sql = sql.concat(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SocialActivitySet>)QueryUtil.list(
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
	 * Removes all the social activity sets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivitySet socialActivitySet : findAll()) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets.
	 *
	 * @return the number of social activity sets
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivitySet.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSET);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "activitySetId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SOCIALACTIVITYSET;
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
		return SocialActivitySetModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SocialActivitySet";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("createDate");
		ctStrictColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("extraData");
		ctStrictColumnNames.add("activityCount");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("activitySetId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the social activity set persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_U_T = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_U_T = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_U_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_C_T = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_T = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByC_C_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_U_C_T = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_U_C_T = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_U_C_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByU_C_C_T = new FinderPath(
			SocialActivitySetImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_C_C_T = new FinderPath(
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByU_C_C_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivitySetImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYSET =
		"SELECT socialActivitySet FROM SocialActivitySet socialActivitySet";

	private static final String _SQL_SELECT_SOCIALACTIVITYSET_WHERE =
		"SELECT socialActivitySet FROM SocialActivitySet socialActivitySet WHERE ";

	private static final String _SQL_COUNT_SOCIALACTIVITYSET =
		"SELECT COUNT(socialActivitySet) FROM SocialActivitySet socialActivitySet";

	private static final String _SQL_COUNT_SOCIALACTIVITYSET_WHERE =
		"SELECT COUNT(socialActivitySet) FROM SocialActivitySet socialActivitySet WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivitySet.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SocialActivitySet exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SocialActivitySet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivitySetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}