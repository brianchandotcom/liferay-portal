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
import com.liferay.portlet.social.model.impl.SocialActivityCounterImpl;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;
import com.liferay.social.kernel.exception.NoSuchActivityCounterException;
import com.liferay.social.kernel.model.SocialActivityCounter;
import com.liferay.social.kernel.model.SocialActivityCounterTable;
import com.liferay.social.kernel.service.persistence.SocialActivityCounterPersistence;

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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the social activity counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCounterPersistenceImpl
	extends BasePersistenceImpl<SocialActivityCounter>
	implements SocialActivityCounterPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SocialActivityCounterUtil</code> to access the social activity counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SocialActivityCounterImpl.class.getName();

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
	 * Returns all the social activity counters where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

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

		List<SocialActivityCounter> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if (groupId != socialActivityCounter.getGroupId()) {
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

			sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SocialActivityCounter>)QueryUtil.list(
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
	 * Returns the first social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByGroupId_First(
			long groupId,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByGroupId_First(
			groupId, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByGroupId_First(
		long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		List<SocialActivityCounter> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByGroupId_Last(
			long groupId,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByGroupId_Last(
		long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByGroupId_PrevAndNext(
			long activityCounterId, long groupId,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = findByPrimaryKey(
			activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, socialActivityCounter, groupId, orderByComparator,
				true);

			array[1] = socialActivityCounter;

			array[2] = getByGroupId_PrevAndNext(
				session, socialActivityCounter, groupId, orderByComparator,
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

	protected SocialActivityCounter getByGroupId_PrevAndNext(
		Session session, SocialActivityCounter socialActivityCounter,
		long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator,
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

		sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

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
			sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
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
						socialActivityCounter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityCounter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivityCounter socialActivityCounter :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

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

			sb.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

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
		"socialActivityCounter.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<SocialActivityCounter> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if ((classNameId !=
							socialActivityCounter.getClassNameId()) ||
						(classPK != socialActivityCounter.getClassPK())) {

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

			sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<SocialActivityCounter>)QueryUtil.list(
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
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		List<SocialActivityCounter> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByC_C_PrevAndNext(
			long activityCounterId, long classNameId, long classPK,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = findByPrimaryKey(
			activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, socialActivityCounter, classNameId, classPK,
				orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByC_C_PrevAndNext(
				session, socialActivityCounter, classNameId, classPK,
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

	protected SocialActivityCounter getByC_C_PrevAndNext(
		Session session, SocialActivityCounter socialActivityCounter,
		long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator,
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

		sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivityCounter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityCounter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (SocialActivityCounter socialActivityCounter :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"socialActivityCounter.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"socialActivityCounter.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_O;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_O;
	private FinderPath _finderPathCountByG_C_C_O;

	/**
	 * Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType) {

		return findByG_C_C_O(
			groupId, classNameId, classPK, ownerType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType, int start,
		int end) {

		return findByG_C_C_O(
			groupId, classNameId, classPK, ownerType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType, int start,
		int end, OrderByComparator<SocialActivityCounter> orderByComparator) {

		return findByG_C_C_O(
			groupId, classNameId, classPK, ownerType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType, int start,
		int end, OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_O;
				finderArgs = new Object[] {
					groupId, classNameId, classPK, ownerType
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_O;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, ownerType, start, end,
				orderByComparator
			};
		}

		List<SocialActivityCounter> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if ((groupId != socialActivityCounter.getGroupId()) ||
						(classNameId !=
							socialActivityCounter.getClassNameId()) ||
						(classPK != socialActivityCounter.getClassPK()) ||
						(ownerType != socialActivityCounter.getOwnerType())) {

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

			sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(ownerType);

				list = (List<SocialActivityCounter>)QueryUtil.list(
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
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_O_First(
			long groupId, long classNameId, long classPK, int ownerType,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_First(
			groupId, classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_O_First(
		long groupId, long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		List<SocialActivityCounter> list = findByG_C_C_O(
			groupId, classNameId, classPK, ownerType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_O_Last(
			long groupId, long classNameId, long classPK, int ownerType,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_Last(
			groupId, classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append("}");

		throw new NoSuchActivityCounterException(sb.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_O_Last(
		long groupId, long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		int count = countByG_C_C_O(groupId, classNameId, classPK, ownerType);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByG_C_C_O(
			groupId, classNameId, classPK, ownerType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
			long activityCounterId, long groupId, long classNameId,
			long classPK, int ownerType,
			OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = findByPrimaryKey(
			activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByG_C_C_O_PrevAndNext(
				session, socialActivityCounter, groupId, classNameId, classPK,
				ownerType, orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByG_C_C_O_PrevAndNext(
				session, socialActivityCounter, groupId, classNameId, classPK,
				ownerType, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityCounter getByG_C_C_O_PrevAndNext(
		Session session, SocialActivityCounter socialActivityCounter,
		long groupId, long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator,
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

		sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

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
			sb.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(ownerType);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivityCounter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialActivityCounter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 */
	@Override
	public void removeByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType) {

		for (SocialActivityCounter socialActivityCounter :
				findByG_C_C_O(
					groupId, classNameId, classPK, ownerType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ownerType the owner type
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_O;

			finderArgs = new Object[] {
				groupId, classNameId, classPK, ownerType
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(ownerType);

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

	private static final String _FINDER_COLUMN_G_C_C_O_GROUPID_2 =
		"socialActivityCounter.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2 =
		"socialActivityCounter.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_O_CLASSPK_2 =
		"socialActivityCounter.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_O_OWNERTYPE_2 =
		"socialActivityCounter.ownerType = ? AND socialActivityCounter.endPeriod = -1";

	private FinderPath _finderPathFetchByG_C_C_N_O_S;
	private FinderPath _finderPathCountByG_C_C_N_O_S;

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a <code>NoSuchActivityCounterException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_N_O_S(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_S(
			groupId, classNameId, classPK, name, ownerType, startPeriod);

		if (socialActivityCounter == null) {
			StringBundler sb = new StringBundler(14);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", name=");
			sb.append(name);

			sb.append(", ownerType=");
			sb.append(ownerType);

			sb.append(", startPeriod=");
			sb.append(startPeriod);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchActivityCounterException(sb.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int startPeriod) {

		return fetchByG_C_C_N_O_S(
			groupId, classNameId, classPK, name, ownerType, startPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int startPeriod, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_C_C_N_O_S, finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter =
				(SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
				(classNameId != socialActivityCounter.getClassNameId()) ||
				(classPK != socialActivityCounter.getClassPK()) ||
				!Objects.equals(name, socialActivityCounter.getName()) ||
				(ownerType != socialActivityCounter.getOwnerType()) ||
				(startPeriod != socialActivityCounter.getStartPeriod())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(ownerType);

				queryPos.add(startPeriod);

				List<SocialActivityCounter> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_C_C_N_O_S, finderArgs, list);
					}
				}
				else {
					SocialActivityCounter socialActivityCounter = list.get(0);

					result = socialActivityCounter;

					cacheResult(socialActivityCounter);
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
			return (SocialActivityCounter)result;
		}
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the social activity counter that was removed
	 */
	@Override
	public SocialActivityCounter removeByG_C_C_N_O_S(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_S(
			groupId, classNameId, classPK, name, ownerType, startPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int startPeriod) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_N_O_S;

			finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(ownerType);

				queryPos.add(startPeriod);

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

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2 =
		"socialActivityCounter.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2 =
		"socialActivityCounter.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2 =
		"socialActivityCounter.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_2 =
		"socialActivityCounter.name = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_3 =
		"(socialActivityCounter.name IS NULL OR socialActivityCounter.name = '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2 =
		"socialActivityCounter.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2 =
		"socialActivityCounter.startPeriod = ?";

	private FinderPath _finderPathFetchByG_C_C_N_O_E;
	private FinderPath _finderPathCountByG_C_C_N_O_E;

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a <code>NoSuchActivityCounterException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_N_O_E(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int endPeriod)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType, endPeriod);

		if (socialActivityCounter == null) {
			StringBundler sb = new StringBundler(14);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", name=");
			sb.append(name);

			sb.append(", ownerType=");
			sb.append(ownerType);

			sb.append(", endPeriod=");
			sb.append(endPeriod);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchActivityCounterException(sb.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int endPeriod) {

		return fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType, endPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int endPeriod, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_C_C_N_O_E, finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter =
				(SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
				(classNameId != socialActivityCounter.getClassNameId()) ||
				(classPK != socialActivityCounter.getClassPK()) ||
				!Objects.equals(name, socialActivityCounter.getName()) ||
				(ownerType != socialActivityCounter.getOwnerType()) ||
				(endPeriod != socialActivityCounter.getEndPeriod())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(ownerType);

				queryPos.add(endPeriod);

				List<SocialActivityCounter> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_C_C_N_O_E, finderArgs, list);
					}
				}
				else {
					SocialActivityCounter socialActivityCounter = list.get(0);

					result = socialActivityCounter;

					cacheResult(socialActivityCounter);
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
			return (SocialActivityCounter)result;
		}
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the social activity counter that was removed
	 */
	@Override
	public SocialActivityCounter removeByG_C_C_N_O_E(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int endPeriod)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType, endPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, String name,
		int ownerType, int endPeriod) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_N_O_E;

			finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(ownerType);

				queryPos.add(endPeriod);

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

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2 =
		"socialActivityCounter.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2 =
		"socialActivityCounter.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2 =
		"socialActivityCounter.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_2 =
		"socialActivityCounter.name = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_3 =
		"(socialActivityCounter.name IS NULL OR socialActivityCounter.name = '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2 =
		"socialActivityCounter.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2 =
		"socialActivityCounter.endPeriod = ?";

	public SocialActivityCounterPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SocialActivityCounter.class);

		setModelImplClass(SocialActivityCounterImpl.class);
		setModelPKClass(long.class);

		setTable(SocialActivityCounterTable.INSTANCE);
	}

	/**
	 * Caches the social activity counter in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounter the social activity counter
	 */
	@Override
	public void cacheResult(SocialActivityCounter socialActivityCounter) {
		if (socialActivityCounter.getCtCollectionId() != 0) {
			socialActivityCounter.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_N_O_S,
			new Object[] {
				socialActivityCounter.getGroupId(),
				socialActivityCounter.getClassNameId(),
				socialActivityCounter.getClassPK(),
				socialActivityCounter.getName(),
				socialActivityCounter.getOwnerType(),
				socialActivityCounter.getStartPeriod()
			},
			socialActivityCounter);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_N_O_E,
			new Object[] {
				socialActivityCounter.getGroupId(),
				socialActivityCounter.getClassNameId(),
				socialActivityCounter.getClassPK(),
				socialActivityCounter.getName(),
				socialActivityCounter.getOwnerType(),
				socialActivityCounter.getEndPeriod()
			},
			socialActivityCounter);

		socialActivityCounter.resetOriginalValues();
	}

	/**
	 * Caches the social activity counters in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounters the social activity counters
	 */
	@Override
	public void cacheResult(
		List<SocialActivityCounter> socialActivityCounters) {

		for (SocialActivityCounter socialActivityCounter :
				socialActivityCounters) {

			if (socialActivityCounter.getCtCollectionId() != 0) {
				socialActivityCounter.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					SocialActivityCounterImpl.class,
					socialActivityCounter.getPrimaryKey()) == null) {

				cacheResult(socialActivityCounter);
			}
			else {
				socialActivityCounter.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity counters.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SocialActivityCounterImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity counter.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityCounter socialActivityCounter) {
		EntityCacheUtil.removeResult(
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SocialActivityCounterModelImpl)socialActivityCounter, true);
	}

	@Override
	public void clearCache(List<SocialActivityCounter> socialActivityCounters) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityCounter socialActivityCounter :
				socialActivityCounters) {

			EntityCacheUtil.removeResult(
				SocialActivityCounterImpl.class,
				socialActivityCounter.getPrimaryKey());

			clearUniqueFindersCache(
				(SocialActivityCounterModelImpl)socialActivityCounter, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				SocialActivityCounterImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SocialActivityCounterModelImpl socialActivityCounterModelImpl) {

		Object[] args = new Object[] {
			socialActivityCounterModelImpl.getGroupId(),
			socialActivityCounterModelImpl.getClassNameId(),
			socialActivityCounterModelImpl.getClassPK(),
			socialActivityCounterModelImpl.getName(),
			socialActivityCounterModelImpl.getOwnerType(),
			socialActivityCounterModelImpl.getStartPeriod()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_C_C_N_O_S, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_N_O_S, args, socialActivityCounterModelImpl,
			false);

		args = new Object[] {
			socialActivityCounterModelImpl.getGroupId(),
			socialActivityCounterModelImpl.getClassNameId(),
			socialActivityCounterModelImpl.getClassPK(),
			socialActivityCounterModelImpl.getName(),
			socialActivityCounterModelImpl.getOwnerType(),
			socialActivityCounterModelImpl.getEndPeriod()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_C_C_N_O_E, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_N_O_E, args, socialActivityCounterModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SocialActivityCounterModelImpl socialActivityCounterModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				socialActivityCounterModelImpl.getGroupId(),
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK(),
				socialActivityCounterModelImpl.getName(),
				socialActivityCounterModelImpl.getOwnerType(),
				socialActivityCounterModelImpl.getStartPeriod()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_N_O_S, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_C_N_O_S, args);
		}

		if ((socialActivityCounterModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_C_N_O_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				socialActivityCounterModelImpl.getOriginalGroupId(),
				socialActivityCounterModelImpl.getOriginalClassNameId(),
				socialActivityCounterModelImpl.getOriginalClassPK(),
				socialActivityCounterModelImpl.getOriginalName(),
				socialActivityCounterModelImpl.getOriginalOwnerType(),
				socialActivityCounterModelImpl.getOriginalStartPeriod()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_N_O_S, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_C_N_O_S, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				socialActivityCounterModelImpl.getGroupId(),
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK(),
				socialActivityCounterModelImpl.getName(),
				socialActivityCounterModelImpl.getOwnerType(),
				socialActivityCounterModelImpl.getEndPeriod()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_N_O_E, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_C_N_O_E, args);
		}

		if ((socialActivityCounterModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_C_N_O_E.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				socialActivityCounterModelImpl.getOriginalGroupId(),
				socialActivityCounterModelImpl.getOriginalClassNameId(),
				socialActivityCounterModelImpl.getOriginalClassPK(),
				socialActivityCounterModelImpl.getOriginalName(),
				socialActivityCounterModelImpl.getOriginalOwnerType(),
				socialActivityCounterModelImpl.getOriginalEndPeriod()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_N_O_E, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_C_N_O_E, args);
		}
	}

	/**
	 * Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	 *
	 * @param activityCounterId the primary key for the new social activity counter
	 * @return the new social activity counter
	 */
	@Override
	public SocialActivityCounter create(long activityCounterId) {
		SocialActivityCounter socialActivityCounter =
			new SocialActivityCounterImpl();

		socialActivityCounter.setNew(true);
		socialActivityCounter.setPrimaryKey(activityCounterId);

		socialActivityCounter.setCompanyId(CompanyThreadLocal.getCompanyId());

		return socialActivityCounter;
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter remove(long activityCounterId)
		throws NoSuchActivityCounterException {

		return remove((Serializable)activityCounterId);
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter remove(Serializable primaryKey)
		throws NoSuchActivityCounterException {

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter socialActivityCounter =
				(SocialActivityCounter)session.get(
					SocialActivityCounterImpl.class, primaryKey);

			if (socialActivityCounter == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityCounterException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(socialActivityCounter);
		}
		catch (NoSuchActivityCounterException noSuchEntityException) {
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
	protected SocialActivityCounter removeImpl(
		SocialActivityCounter socialActivityCounter) {

		if (!CTPersistenceHelperUtil.isRemove(socialActivityCounter)) {
			return socialActivityCounter;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivityCounter)) {
				socialActivityCounter = (SocialActivityCounter)session.get(
					SocialActivityCounterImpl.class,
					socialActivityCounter.getPrimaryKeyObj());
			}

			if (socialActivityCounter != null) {
				session.delete(socialActivityCounter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityCounter != null) {
			clearCache(socialActivityCounter);
		}

		return socialActivityCounter;
	}

	@Override
	public SocialActivityCounter updateImpl(
		SocialActivityCounter socialActivityCounter) {

		boolean isNew = socialActivityCounter.isNew();

		if (!(socialActivityCounter instanceof
				SocialActivityCounterModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(socialActivityCounter.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					socialActivityCounter);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in socialActivityCounter proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SocialActivityCounter implementation " +
					socialActivityCounter.getClass());
		}

		SocialActivityCounterModelImpl socialActivityCounterModelImpl =
			(SocialActivityCounterModelImpl)socialActivityCounter;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(socialActivityCounter)) {
				if (!isNew) {
					session.evict(
						SocialActivityCounterImpl.class,
						socialActivityCounter.getPrimaryKeyObj());
				}

				session.save(socialActivityCounter);

				socialActivityCounter.setNew(false);
			}
			else {
				socialActivityCounter = (SocialActivityCounter)session.merge(
					socialActivityCounter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityCounter.getCtCollectionId() != 0) {
			socialActivityCounter.resetOriginalValues();

			return socialActivityCounter;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				socialActivityCounterModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				socialActivityCounterModelImpl.getGroupId(),
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK(),
				socialActivityCounterModelImpl.getOwnerType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_O, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_O, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((socialActivityCounterModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivityCounterModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					socialActivityCounterModelImpl.getGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivityCounterModelImpl.getOriginalClassNameId(),
					socialActivityCounterModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					socialActivityCounterModelImpl.getClassNameId(),
					socialActivityCounterModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_O.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivityCounterModelImpl.getOriginalGroupId(),
					socialActivityCounterModelImpl.getOriginalClassNameId(),
					socialActivityCounterModelImpl.getOriginalClassPK(),
					socialActivityCounterModelImpl.getOriginalOwnerType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C_O, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_O, args);

				args = new Object[] {
					socialActivityCounterModelImpl.getGroupId(),
					socialActivityCounterModelImpl.getClassNameId(),
					socialActivityCounterModelImpl.getClassPK(),
					socialActivityCounterModelImpl.getOwnerType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C_O, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_O, args);
			}
		}

		EntityCacheUtil.putResult(
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter,
			false);

		clearUniqueFindersCache(socialActivityCounterModelImpl, false);
		cacheUniqueFindersCache(socialActivityCounterModelImpl);

		socialActivityCounter.resetOriginalValues();

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivityCounterException {

		SocialActivityCounter socialActivityCounter = fetchByPrimaryKey(
			primaryKey);

		if (socialActivityCounter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivityCounterException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or throws a <code>NoSuchActivityCounterException</code> if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter findByPrimaryKey(long activityCounterId)
		throws NoSuchActivityCounterException {

		return findByPrimaryKey((Serializable)activityCounterId);
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				SocialActivityCounter.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		SocialActivityCounter socialActivityCounter = null;

		Session session = null;

		try {
			session = openSession();

			socialActivityCounter = (SocialActivityCounter)session.get(
				SocialActivityCounterImpl.class, primaryKey);

			if (socialActivityCounter != null) {
				cacheResult(socialActivityCounter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter fetchByPrimaryKey(long activityCounterId) {
		return fetchByPrimaryKey((Serializable)activityCounterId);
	}

	@Override
	public Map<Serializable, SocialActivityCounter> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				SocialActivityCounter.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SocialActivityCounter> map =
			new HashMap<Serializable, SocialActivityCounter>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SocialActivityCounter socialActivityCounter = fetchByPrimaryKey(
				primaryKey);

			if (socialActivityCounter != null) {
				map.put(primaryKey, socialActivityCounter);
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

			for (SocialActivityCounter socialActivityCounter :
					(List<SocialActivityCounter>)query.list()) {

				map.put(
					socialActivityCounter.getPrimaryKeyObj(),
					socialActivityCounter);

				cacheResult(socialActivityCounter);
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
	 * Returns all the social activity counters.
	 *
	 * @return the social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(
		int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(
		int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

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

		List<SocialActivityCounter> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYCOUNTER;

				sql = sql.concat(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SocialActivityCounter>)QueryUtil.list(
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
	 * Removes all the social activity counters from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivityCounter socialActivityCounter : findAll()) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters.
	 *
	 * @return the number of social activity counters
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialActivityCounter.class);

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
					_SQL_COUNT_SOCIALACTIVITYCOUNTER);

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
		return "activityCounterId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SOCIALACTIVITYCOUNTER;
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
		return SocialActivityCounterModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SocialActivityCounter";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("ownerType");
		ctStrictColumnNames.add("currentValue");
		ctStrictColumnNames.add("totalValue");
		ctStrictColumnNames.add("graceValue");
		ctStrictColumnNames.add("startPeriod");
		ctStrictColumnNames.add("endPeriod");
		ctStrictColumnNames.add("active_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("activityCounterId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"groupId", "classNameId", "classPK", "name", "ownerType",
				"startPeriod"
			});

		_uniqueIndexColumnNames.add(
			new String[] {
				"groupId", "classNameId", "classPK", "name", "ownerType",
				"endPeriod"
			});
	}

	/**
	 * Initializes the social activity counter persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_C_C_O = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_O = new FinderPath(
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK);

		_finderPathCountByG_C_C_O = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathFetchByG_C_C_N_O_S = new FinderPath(
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.STARTPERIOD_COLUMN_BITMASK);

		_finderPathCountByG_C_C_N_O_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathFetchByG_C_C_N_O_E = new FinderPath(
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.ENDPERIOD_COLUMN_BITMASK);

		_finderPathCountByG_C_C_N_O_E = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivityCounterImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER =
		"SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter";

	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE =
		"SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter WHERE ";

	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER =
		"SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter";

	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE =
		"SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"socialActivityCounter.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SocialActivityCounter exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SocialActivityCounter exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityCounterPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

}