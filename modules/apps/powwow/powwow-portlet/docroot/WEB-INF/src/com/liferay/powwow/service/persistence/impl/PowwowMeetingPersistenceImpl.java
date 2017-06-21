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

package com.liferay.powwow.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.powwow.exception.NoSuchMeetingException;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.impl.PowwowMeetingImpl;
import com.liferay.powwow.model.impl.PowwowMeetingModelImpl;
import com.liferay.powwow.service.persistence.PowwowMeetingPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the powwow meeting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeetingPersistence
 * @see com.liferay.powwow.service.persistence.PowwowMeetingUtil
 * @generated
 */
@ProviderType
public class PowwowMeetingPersistenceImpl extends BasePersistenceImpl<PowwowMeeting>
	implements PowwowMeetingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PowwowMeetingUtil} to access the powwow meeting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PowwowMeetingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			PowwowMeetingModelImpl.GROUPID_COLUMN_BITMASK |
			PowwowMeetingModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the powwow meetings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByGroupId(long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByGroupId(long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
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

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowMeeting powwowMeeting : list) {
					if ((groupId != powwowMeeting.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first powwow meeting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByGroupId_First(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByGroupId_First(groupId,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the first powwow meeting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByGroupId_First(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		List<PowwowMeeting> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow meeting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByGroupId_Last(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the last powwow meeting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByGroupId_Last(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<PowwowMeeting> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set where groupId = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] findByGroupId_PrevAndNext(long powwowMeetingId,
		long groupId, OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, powwowMeeting,
					groupId, orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = getByGroupId_PrevAndNext(session, powwowMeeting,
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

	protected PowwowMeeting getByGroupId_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

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
			query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the powwow meetings that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching powwow meetings that the user has permission to view
	 */
	@Override
	public List<PowwowMeeting> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings that the user has permission to view
	 */
	@Override
	public List<PowwowMeeting> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings that the user has permission to view
	 */
	@Override
	public List<PowwowMeeting> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PowwowMeetingModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PowwowMeeting.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, PowwowMeetingImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, PowwowMeetingImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<PowwowMeeting>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set of powwow meetings that the user has permission to view where groupId = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] filterFindByGroupId_PrevAndNext(
		long powwowMeetingId, long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(powwowMeetingId, groupId,
				orderByComparator);
		}

		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, powwowMeeting,
					groupId, orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = filterGetByGroupId_PrevAndNext(session, powwowMeeting,
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

	protected PowwowMeeting filterGetByGroupId_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PowwowMeetingModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PowwowMeeting.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, PowwowMeetingImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, PowwowMeetingImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow meetings where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (PowwowMeeting powwowMeeting : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching powwow meetings
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POWWOWMEETING_WHERE);

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

	/**
	 * Returns the number of powwow meetings that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching powwow meetings that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_POWWOWMEETING_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PowwowMeeting.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "powwowMeeting.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_POWWOWSERVERID =
		new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPowwowServerId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID =
		new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPowwowServerId", new String[] { Long.class.getName() },
			PowwowMeetingModelImpl.POWWOWSERVERID_COLUMN_BITMASK |
			PowwowMeetingModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_POWWOWSERVERID = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPowwowServerId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the powwow meetings where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @return the matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPowwowServerId(long powwowServerId) {
		return findByPowwowServerId(powwowServerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings where powwowServerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPowwowServerId(long powwowServerId,
		int start, int end) {
		return findByPowwowServerId(powwowServerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where powwowServerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPowwowServerId(long powwowServerId,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return findByPowwowServerId(powwowServerId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where powwowServerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPowwowServerId(long powwowServerId,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID;
			finderArgs = new Object[] { powwowServerId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_POWWOWSERVERID;
			finderArgs = new Object[] {
					powwowServerId,
					
					start, end, orderByComparator
				};
		}

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowMeeting powwowMeeting : list) {
					if ((powwowServerId != powwowMeeting.getPowwowServerId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_POWWOWSERVERID_POWWOWSERVERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowServerId);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByPowwowServerId_First(long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByPowwowServerId_First(powwowServerId,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowServerId=");
		msg.append(powwowServerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByPowwowServerId_First(long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		List<PowwowMeeting> list = findByPowwowServerId(powwowServerId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByPowwowServerId_Last(long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByPowwowServerId_Last(powwowServerId,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowServerId=");
		msg.append(powwowServerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByPowwowServerId_Last(long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		int count = countByPowwowServerId(powwowServerId);

		if (count == 0) {
			return null;
		}

		List<PowwowMeeting> list = findByPowwowServerId(powwowServerId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param powwowServerId the powwow server ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] findByPowwowServerId_PrevAndNext(
		long powwowMeetingId, long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = getByPowwowServerId_PrevAndNext(session, powwowMeeting,
					powwowServerId, orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = getByPowwowServerId_PrevAndNext(session, powwowMeeting,
					powwowServerId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowMeeting getByPowwowServerId_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

		query.append(_FINDER_COLUMN_POWWOWSERVERID_POWWOWSERVERID_2);

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
			query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(powwowServerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow meetings where powwowServerId = &#63; from the database.
	 *
	 * @param powwowServerId the powwow server ID
	 */
	@Override
	public void removeByPowwowServerId(long powwowServerId) {
		for (PowwowMeeting powwowMeeting : findByPowwowServerId(
				powwowServerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings where powwowServerId = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @return the number of matching powwow meetings
	 */
	@Override
	public int countByPowwowServerId(long powwowServerId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_POWWOWSERVERID;

		Object[] finderArgs = new Object[] { powwowServerId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_POWWOWSERVERID_POWWOWSERVERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowServerId);

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

	private static final String _FINDER_COLUMN_POWWOWSERVERID_POWWOWSERVERID_2 = "powwowMeeting.powwowServerId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STATUS = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStatus",
			new String[] {
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS =
		new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByStatus", new String[] { Integer.class.getName() },
			PowwowMeetingModelImpl.STATUS_COLUMN_BITMASK |
			PowwowMeetingModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STATUS = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] { Integer.class.getName() });

	/**
	 * Returns all the powwow meetings where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByStatus(int status) {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByStatus(int status, int start, int end) {
		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByStatus(int status, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return findByStatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByStatus(int status, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS;
			finderArgs = new Object[] { status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STATUS;
			finderArgs = new Object[] { status, start, end, orderByComparator };
		}

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowMeeting powwowMeeting : list) {
					if ((status != powwowMeeting.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_STATUS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(status);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first powwow meeting in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByStatus_First(int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByStatus_First(status,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the first powwow meeting in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByStatus_First(int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		List<PowwowMeeting> list = findByStatus(status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow meeting in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByStatus_Last(int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByStatus_Last(status,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the last powwow meeting in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByStatus_Last(int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		int count = countByStatus(status);

		if (count == 0) {
			return null;
		}

		List<PowwowMeeting> list = findByStatus(status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set where status = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] findByStatus_PrevAndNext(long powwowMeetingId,
		int status, OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = getByStatus_PrevAndNext(session, powwowMeeting, status,
					orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = getByStatus_PrevAndNext(session, powwowMeeting, status,
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

	protected PowwowMeeting getByStatus_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, int status,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

		query.append(_FINDER_COLUMN_STATUS_STATUS_2);

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
			query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow meetings where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeByStatus(int status) {
		for (PowwowMeeting powwowMeeting : findByStatus(status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching powwow meetings
	 */
	@Override
	public int countByStatus(int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STATUS;

		Object[] finderArgs = new Object[] { status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_STATUS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_STATUS_STATUS_2 = "powwowMeeting.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			PowwowMeetingModelImpl.USERID_COLUMN_BITMASK |
			PowwowMeetingModelImpl.STATUS_COLUMN_BITMASK |
			PowwowMeetingModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the powwow meetings where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByU_S(long userId, int status) {
		return findByU_S(userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the powwow meetings where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByU_S(long userId, int status, int start,
		int end) {
		return findByU_S(userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByU_S(long userId, int status, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return findByU_S(userId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByU_S(long userId, int status, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] { userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] {
					userId, status,
					
					start, end, orderByComparator
				};
		}

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowMeeting powwowMeeting : list) {
					if ((userId != powwowMeeting.getUserId()) ||
							(status != powwowMeeting.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByU_S_First(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByU_S_First(userId, status,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByU_S_First(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		List<PowwowMeeting> list = findByU_S(userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByU_S_Last(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByU_S_Last(userId, status,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByU_S_Last(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		int count = countByU_S(userId, status);

		if (count == 0) {
			return null;
		}

		List<PowwowMeeting> list = findByU_S(userId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] findByU_S_PrevAndNext(long powwowMeetingId,
		long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = getByU_S_PrevAndNext(session, powwowMeeting, userId,
					status, orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = getByU_S_PrevAndNext(session, powwowMeeting, userId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowMeeting getByU_S_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

		query.append(_FINDER_COLUMN_U_S_USERID_2);

		query.append(_FINDER_COLUMN_U_S_STATUS_2);

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
			query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow meetings where userId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByU_S(long userId, int status) {
		for (PowwowMeeting powwowMeeting : findByU_S(userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching powwow meetings
	 */
	@Override
	public int countByU_S(long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_S;

		Object[] finderArgs = new Object[] { userId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_U_S_USERID_2 = "powwowMeeting.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_S_STATUS_2 = "powwowMeeting.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PSI_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPSI_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED,
			PowwowMeetingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPSI_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			PowwowMeetingModelImpl.POWWOWSERVERID_COLUMN_BITMASK |
			PowwowMeetingModelImpl.STATUS_COLUMN_BITMASK |
			PowwowMeetingModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PSI_S = new FinderPath(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPSI_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @return the matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPSI_S(long powwowServerId, int status) {
		return findByPSI_S(powwowServerId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPSI_S(long powwowServerId, int status,
		int start, int end) {
		return findByPSI_S(powwowServerId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPSI_S(long powwowServerId, int status,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return findByPSI_S(powwowServerId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findByPSI_S(long powwowServerId, int status,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S;
			finderArgs = new Object[] { powwowServerId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PSI_S;
			finderArgs = new Object[] {
					powwowServerId, status,
					
					start, end, orderByComparator
				};
		}

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowMeeting powwowMeeting : list) {
					if ((powwowServerId != powwowMeeting.getPowwowServerId()) ||
							(status != powwowMeeting.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_PSI_S_POWWOWSERVERID_2);

			query.append(_FINDER_COLUMN_PSI_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowServerId);

				qPos.add(status);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByPSI_S_First(long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByPSI_S_First(powwowServerId,
				status, orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowServerId=");
		msg.append(powwowServerId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByPSI_S_First(long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		List<PowwowMeeting> list = findByPSI_S(powwowServerId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting
	 * @throws NoSuchMeetingException if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting findByPSI_S_Last(long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByPSI_S_Last(powwowServerId, status,
				orderByComparator);

		if (powwowMeeting != null) {
			return powwowMeeting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowServerId=");
		msg.append(powwowServerId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMeetingException(msg.toString());
	}

	/**
	 * Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	 */
	@Override
	public PowwowMeeting fetchByPSI_S_Last(long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		int count = countByPSI_S(powwowServerId, status);

		if (count == 0) {
			return null;
		}

		List<PowwowMeeting> list = findByPSI_S(powwowServerId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowMeetingId the primary key of the current powwow meeting
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting[] findByPSI_S_PrevAndNext(long powwowMeetingId,
		long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = findByPrimaryKey(powwowMeetingId);

		Session session = null;

		try {
			session = openSession();

			PowwowMeeting[] array = new PowwowMeetingImpl[3];

			array[0] = getByPSI_S_PrevAndNext(session, powwowMeeting,
					powwowServerId, status, orderByComparator, true);

			array[1] = powwowMeeting;

			array[2] = getByPSI_S_PrevAndNext(session, powwowMeeting,
					powwowServerId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowMeeting getByPSI_S_PrevAndNext(Session session,
		PowwowMeeting powwowMeeting, long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE);

		query.append(_FINDER_COLUMN_PSI_S_POWWOWSERVERID_2);

		query.append(_FINDER_COLUMN_PSI_S_STATUS_2);

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
			query.append(PowwowMeetingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(powwowServerId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowMeeting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowMeeting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow meetings where powwowServerId = &#63; and status = &#63; from the database.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 */
	@Override
	public void removeByPSI_S(long powwowServerId, int status) {
		for (PowwowMeeting powwowMeeting : findByPSI_S(powwowServerId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings where powwowServerId = &#63; and status = &#63;.
	 *
	 * @param powwowServerId the powwow server ID
	 * @param status the status
	 * @return the number of matching powwow meetings
	 */
	@Override
	public int countByPSI_S(long powwowServerId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PSI_S;

		Object[] finderArgs = new Object[] { powwowServerId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWMEETING_WHERE);

			query.append(_FINDER_COLUMN_PSI_S_POWWOWSERVERID_2);

			query.append(_FINDER_COLUMN_PSI_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowServerId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_PSI_S_POWWOWSERVERID_2 = "powwowMeeting.powwowServerId = ? AND ";
	private static final String _FINDER_COLUMN_PSI_S_STATUS_2 = "powwowMeeting.status = ?";

	public PowwowMeetingPersistenceImpl() {
		setModelClass(PowwowMeeting.class);
	}

	/**
	 * Caches the powwow meeting in the entity cache if it is enabled.
	 *
	 * @param powwowMeeting the powwow meeting
	 */
	@Override
	public void cacheResult(PowwowMeeting powwowMeeting) {
		entityCache.putResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingImpl.class, powwowMeeting.getPrimaryKey(),
			powwowMeeting);

		powwowMeeting.resetOriginalValues();
	}

	/**
	 * Caches the powwow meetings in the entity cache if it is enabled.
	 *
	 * @param powwowMeetings the powwow meetings
	 */
	@Override
	public void cacheResult(List<PowwowMeeting> powwowMeetings) {
		for (PowwowMeeting powwowMeeting : powwowMeetings) {
			if (entityCache.getResult(
						PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
						PowwowMeetingImpl.class, powwowMeeting.getPrimaryKey()) == null) {
				cacheResult(powwowMeeting);
			}
			else {
				powwowMeeting.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all powwow meetings.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PowwowMeetingImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the powwow meeting.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowMeeting powwowMeeting) {
		entityCache.removeResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingImpl.class, powwowMeeting.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PowwowMeeting> powwowMeetings) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PowwowMeeting powwowMeeting : powwowMeetings) {
			entityCache.removeResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
				PowwowMeetingImpl.class, powwowMeeting.getPrimaryKey());
		}
	}

	/**
	 * Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	 *
	 * @param powwowMeetingId the primary key for the new powwow meeting
	 * @return the new powwow meeting
	 */
	@Override
	public PowwowMeeting create(long powwowMeetingId) {
		PowwowMeeting powwowMeeting = new PowwowMeetingImpl();

		powwowMeeting.setNew(true);
		powwowMeeting.setPrimaryKey(powwowMeetingId);

		powwowMeeting.setCompanyId(companyProvider.getCompanyId());

		return powwowMeeting;
	}

	/**
	 * Removes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting remove(long powwowMeetingId)
		throws NoSuchMeetingException {
		return remove((Serializable)powwowMeetingId);
	}

	/**
	 * Removes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting remove(Serializable primaryKey)
		throws NoSuchMeetingException {
		Session session = null;

		try {
			session = openSession();

			PowwowMeeting powwowMeeting = (PowwowMeeting)session.get(PowwowMeetingImpl.class,
					primaryKey);

			if (powwowMeeting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMeetingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(powwowMeeting);
		}
		catch (NoSuchMeetingException nsee) {
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
	protected PowwowMeeting removeImpl(PowwowMeeting powwowMeeting) {
		powwowMeeting = toUnwrappedModel(powwowMeeting);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(powwowMeeting)) {
				powwowMeeting = (PowwowMeeting)session.get(PowwowMeetingImpl.class,
						powwowMeeting.getPrimaryKeyObj());
			}

			if (powwowMeeting != null) {
				session.delete(powwowMeeting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (powwowMeeting != null) {
			clearCache(powwowMeeting);
		}

		return powwowMeeting;
	}

	@Override
	public PowwowMeeting updateImpl(PowwowMeeting powwowMeeting) {
		powwowMeeting = toUnwrappedModel(powwowMeeting);

		boolean isNew = powwowMeeting.isNew();

		PowwowMeetingModelImpl powwowMeetingModelImpl = (PowwowMeetingModelImpl)powwowMeeting;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (powwowMeeting.getCreateDate() == null)) {
			if (serviceContext == null) {
				powwowMeeting.setCreateDate(now);
			}
			else {
				powwowMeeting.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!powwowMeetingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				powwowMeeting.setModifiedDate(now);
			}
			else {
				powwowMeeting.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (powwowMeeting.isNew()) {
				session.save(powwowMeeting);

				powwowMeeting.setNew(false);
			}
			else {
				powwowMeeting = (PowwowMeeting)session.merge(powwowMeeting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!PowwowMeetingModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { powwowMeetingModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { powwowMeetingModelImpl.getPowwowServerId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWSERVERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID,
				args);

			args = new Object[] { powwowMeetingModelImpl.getStatus() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_STATUS, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS,
				args);

			args = new Object[] {
					powwowMeetingModelImpl.getUserId(),
					powwowMeetingModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
				args);

			args = new Object[] {
					powwowMeetingModelImpl.getPowwowServerId(),
					powwowMeetingModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PSI_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((powwowMeetingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowMeetingModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { powwowMeetingModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((powwowMeetingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowMeetingModelImpl.getOriginalPowwowServerId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWSERVERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID,
					args);

				args = new Object[] { powwowMeetingModelImpl.getPowwowServerId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWSERVERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWSERVERID,
					args);
			}

			if ((powwowMeetingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowMeetingModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STATUS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS,
					args);

				args = new Object[] { powwowMeetingModelImpl.getStatus() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STATUS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS,
					args);
			}

			if ((powwowMeetingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowMeetingModelImpl.getOriginalUserId(),
						powwowMeetingModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);

				args = new Object[] {
						powwowMeetingModelImpl.getUserId(),
						powwowMeetingModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);
			}

			if ((powwowMeetingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowMeetingModelImpl.getOriginalPowwowServerId(),
						powwowMeetingModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PSI_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S,
					args);

				args = new Object[] {
						powwowMeetingModelImpl.getPowwowServerId(),
						powwowMeetingModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PSI_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PSI_S,
					args);
			}
		}

		entityCache.putResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
			PowwowMeetingImpl.class, powwowMeeting.getPrimaryKey(),
			powwowMeeting, false);

		powwowMeeting.resetOriginalValues();

		return powwowMeeting;
	}

	protected PowwowMeeting toUnwrappedModel(PowwowMeeting powwowMeeting) {
		if (powwowMeeting instanceof PowwowMeetingImpl) {
			return powwowMeeting;
		}

		PowwowMeetingImpl powwowMeetingImpl = new PowwowMeetingImpl();

		powwowMeetingImpl.setNew(powwowMeeting.isNew());
		powwowMeetingImpl.setPrimaryKey(powwowMeeting.getPrimaryKey());

		powwowMeetingImpl.setPowwowMeetingId(powwowMeeting.getPowwowMeetingId());
		powwowMeetingImpl.setGroupId(powwowMeeting.getGroupId());
		powwowMeetingImpl.setCompanyId(powwowMeeting.getCompanyId());
		powwowMeetingImpl.setUserId(powwowMeeting.getUserId());
		powwowMeetingImpl.setUserName(powwowMeeting.getUserName());
		powwowMeetingImpl.setCreateDate(powwowMeeting.getCreateDate());
		powwowMeetingImpl.setModifiedDate(powwowMeeting.getModifiedDate());
		powwowMeetingImpl.setPowwowServerId(powwowMeeting.getPowwowServerId());
		powwowMeetingImpl.setName(powwowMeeting.getName());
		powwowMeetingImpl.setDescription(powwowMeeting.getDescription());
		powwowMeetingImpl.setProviderType(powwowMeeting.getProviderType());
		powwowMeetingImpl.setProviderTypeMetadata(powwowMeeting.getProviderTypeMetadata());
		powwowMeetingImpl.setLanguageId(powwowMeeting.getLanguageId());
		powwowMeetingImpl.setCalendarBookingId(powwowMeeting.getCalendarBookingId());
		powwowMeetingImpl.setStatus(powwowMeeting.getStatus());

		return powwowMeetingImpl;
	}

	/**
	 * Returns the powwow meeting with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow meeting
	 * @return the powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMeetingException {
		PowwowMeeting powwowMeeting = fetchByPrimaryKey(primaryKey);

		if (powwowMeeting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMeetingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return powwowMeeting;
	}

	/**
	 * Returns the powwow meeting with the primary key or throws a {@link NoSuchMeetingException} if it could not be found.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting
	 * @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting findByPrimaryKey(long powwowMeetingId)
		throws NoSuchMeetingException {
		return findByPrimaryKey((Serializable)powwowMeetingId);
	}

	/**
	 * Returns the powwow meeting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow meeting
	 * @return the powwow meeting, or <code>null</code> if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
				PowwowMeetingImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		PowwowMeeting powwowMeeting = (PowwowMeeting)serializable;

		if (powwowMeeting == null) {
			Session session = null;

			try {
				session = openSession();

				powwowMeeting = (PowwowMeeting)session.get(PowwowMeetingImpl.class,
						primaryKey);

				if (powwowMeeting != null) {
					cacheResult(powwowMeeting);
				}
				else {
					entityCache.putResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
						PowwowMeetingImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
					PowwowMeetingImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return powwowMeeting;
	}

	/**
	 * Returns the powwow meeting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting, or <code>null</code> if a powwow meeting with the primary key could not be found
	 */
	@Override
	public PowwowMeeting fetchByPrimaryKey(long powwowMeetingId) {
		return fetchByPrimaryKey((Serializable)powwowMeetingId);
	}

	@Override
	public Map<Serializable, PowwowMeeting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PowwowMeeting> map = new HashMap<Serializable, PowwowMeeting>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PowwowMeeting powwowMeeting = fetchByPrimaryKey(primaryKey);

			if (powwowMeeting != null) {
				map.put(primaryKey, powwowMeeting);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
					PowwowMeetingImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (PowwowMeeting)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_POWWOWMEETING_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (PowwowMeeting powwowMeeting : (List<PowwowMeeting>)q.list()) {
				map.put(powwowMeeting.getPrimaryKeyObj(), powwowMeeting);

				cacheResult(powwowMeeting);

				uncachedPrimaryKeys.remove(powwowMeeting.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(PowwowMeetingModelImpl.ENTITY_CACHE_ENABLED,
					PowwowMeetingImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the powwow meetings.
	 *
	 * @return the powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow meetings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow meetings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findAll(int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow meetings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of powwow meetings
	 */
	@Override
	public List<PowwowMeeting> findAll(int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
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

		List<PowwowMeeting> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowMeeting>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_POWWOWMEETING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_POWWOWMEETING;

				if (pagination) {
					sql = sql.concat(PowwowMeetingModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowMeeting>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the powwow meetings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PowwowMeeting powwowMeeting : findAll()) {
			remove(powwowMeeting);
		}
	}

	/**
	 * Returns the number of powwow meetings.
	 *
	 * @return the number of powwow meetings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POWWOWMEETING);

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
		return PowwowMeetingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the powwow meeting persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(PowwowMeetingImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_POWWOWMEETING = "SELECT powwowMeeting FROM PowwowMeeting powwowMeeting";
	private static final String _SQL_SELECT_POWWOWMEETING_WHERE_PKS_IN = "SELECT powwowMeeting FROM PowwowMeeting powwowMeeting WHERE powwowMeetingId IN (";
	private static final String _SQL_SELECT_POWWOWMEETING_WHERE = "SELECT powwowMeeting FROM PowwowMeeting powwowMeeting WHERE ";
	private static final String _SQL_COUNT_POWWOWMEETING = "SELECT COUNT(powwowMeeting) FROM PowwowMeeting powwowMeeting";
	private static final String _SQL_COUNT_POWWOWMEETING_WHERE = "SELECT COUNT(powwowMeeting) FROM PowwowMeeting powwowMeeting WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "powwowMeeting.powwowMeetingId";
	private static final String _FILTER_SQL_SELECT_POWWOWMEETING_WHERE = "SELECT DISTINCT {powwowMeeting.*} FROM PowwowMeeting powwowMeeting WHERE ";
	private static final String _FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {PowwowMeeting.*} FROM (SELECT DISTINCT powwowMeeting.powwowMeetingId FROM PowwowMeeting powwowMeeting WHERE ";
	private static final String _FILTER_SQL_SELECT_POWWOWMEETING_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN PowwowMeeting ON TEMP_TABLE.powwowMeetingId = PowwowMeeting.powwowMeetingId";
	private static final String _FILTER_SQL_COUNT_POWWOWMEETING_WHERE = "SELECT COUNT(DISTINCT powwowMeeting.powwowMeetingId) AS COUNT_VALUE FROM PowwowMeeting powwowMeeting WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "powwowMeeting";
	private static final String _FILTER_ENTITY_TABLE = "PowwowMeeting";
	private static final String _ORDER_BY_ENTITY_ALIAS = "powwowMeeting.";
	private static final String _ORDER_BY_ENTITY_TABLE = "PowwowMeeting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PowwowMeeting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PowwowMeeting exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(PowwowMeetingPersistenceImpl.class);
}