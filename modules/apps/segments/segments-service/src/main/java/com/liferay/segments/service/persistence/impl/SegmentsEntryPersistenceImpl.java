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

package com.liferay.segments.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.impl.SegmentsEntryImpl;
import com.liferay.segments.model.impl.SegmentsEntryModelImpl;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the segments entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryPersistence
 * @see com.liferay.segments.service.persistence.SegmentsEntryUtil
 * @generated
 */
@ProviderType
public class SegmentsEntryPersistenceImpl extends BasePersistenceImpl<SegmentsEntry>
	implements SegmentsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SegmentsEntryUtil} to access the segments entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SegmentsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByGroupId_First(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByGroupId_First(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByGroupId_Last(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByGroupId_PrevAndNext(long segmentsEntryId,
		long groupId, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, segmentsEntry,
					groupId, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByGroupId_PrevAndNext(session, segmentsEntry,
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

	protected SegmentsEntry getByGroupId_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SegmentsEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByGroupId_PrevAndNext(
		long segmentsEntryId, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(segmentsEntryId, groupId,
				orderByComparator);
		}

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, segmentsEntry,
					groupId, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session, segmentsEntry,
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

	protected SegmentsEntry filterGetByGroupId_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SegmentsEntry segmentsEntry : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "segmentsEntry.groupId = ?";
	private FinderPath _finderPathWithPaginationFindBySource;
	private FinderPath _finderPathWithoutPaginationFindBySource;
	private FinderPath _finderPathCountBySource;

	/**
	 * Returns all the segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source) {
		return findBySource(source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source, int start, int end) {
		return findBySource(source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return findBySource(source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		source = Objects.toString(source, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBySource;
			finderArgs = new Object[] { source };
		}
		else {
			finderPath = _finderPathWithPaginationFindBySource;
			finderArgs = new Object[] { source, start, end, orderByComparator };
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!source.equals(segmentsEntry.getSource())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			boolean bindSource = false;

			if (source.isEmpty()) {
				query.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
			}
			else {
				bindSource = true;

				query.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSource) {
					qPos.add(source);
				}

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySource_First(String source,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchBySource_First(source,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("source=");
		msg.append(source);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySource_First(String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findBySource(source, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySource_Last(String source,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchBySource_Last(source,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("source=");
		msg.append(source);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySource_Last(String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countBySource(source);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findBySource(source, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where source = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findBySource_PrevAndNext(long segmentsEntryId,
		String source, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		source = Objects.toString(source, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getBySource_PrevAndNext(session, segmentsEntry, source,
					orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getBySource_PrevAndNext(session, segmentsEntry, source,
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

	protected SegmentsEntry getBySource_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, String source,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		boolean bindSource = false;

		if (source.isEmpty()) {
			query.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
		}
		else {
			bindSource = true;

			query.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
		}

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSource) {
			qPos.add(source);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where source = &#63; from the database.
	 *
	 * @param source the source
	 */
	@Override
	public void removeBySource(String source) {
		for (SegmentsEntry segmentsEntry : findBySource(source,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countBySource(String source) {
		source = Objects.toString(source, "");

		FinderPath finderPath = _finderPathCountBySource;

		Object[] finderArgs = new Object[] { source };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			boolean bindSource = false;

			if (source.isEmpty()) {
				query.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
			}
			else {
				bindSource = true;

				query.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSource) {
					qPos.add(source);
				}

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

	private static final String _FINDER_COLUMN_SOURCE_SOURCE_2 = "segmentsEntry.source = ?";
	private static final String _FINDER_COLUMN_SOURCE_SOURCE_3 = "(segmentsEntry.source IS NULL OR segmentsEntry.source = '')";
	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the segments entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByType;
			finderArgs = new Object[] { type };
		}
		else {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] { type, start, end, orderByComparator };
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!type.equals(segmentsEntry.getType())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByType_First(String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByType_First(type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByType_First(String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByType_Last(String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByType_Last(type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByType_Last(String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByType(type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByType_PrevAndNext(long segmentsEntryId,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByType_PrevAndNext(session, segmentsEntry, type,
					orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByType_PrevAndNext(session, segmentsEntry, type,
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

	protected SegmentsEntry getByType_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);
		}

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(String type) {
		for (SegmentsEntry segmentsEntry : findByType(type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByType(String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByType;

		Object[] finderArgs = new Object[] { type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "segmentsEntry.type = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_3 = "(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";
	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId()) ||
							(active != segmentsEntry.isActive())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_First(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByG_A_First(groupId, active,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_Last(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByG_A_Last(groupId, active,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByG_A(groupId, active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByG_A_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByG_A_PrevAndNext(session, segmentsEntry, groupId,
					active, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByG_A_PrevAndNext(session, segmentsEntry, groupId,
					active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByG_A_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(long groupId, boolean active) {
		return filterFindByG_A(groupId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(long groupId, boolean active,
		int start, int end) {
		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<SegmentsEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByG_A_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(segmentsEntryId, groupId, active,
				orderByComparator);
		}

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session, segmentsEntry,
					groupId, active, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByG_A_PrevAndNext(session, segmentsEntry,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry filterGetByG_A_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (SegmentsEntry segmentsEntry : findByG_A(groupId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "segmentsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "segmentsEntry.active = ?";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2_SQL = "segmentsEntry.active_ = ?";
	private FinderPath _finderPathFetchByG_K;
	private FinderPath _finderPathCountByG_K;

	/**
	 * Returns the segments entry where groupId = &#63; and key = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_K(long groupId, String key)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByG_K(groupId, key);

		if (segmentsEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", key=");
			msg.append(key);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_K(long groupId, String key) {
		return fetchByG_K(groupId, key, true);
	}

	/**
	 * Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		key = Objects.toString(key, "");

		Object[] finderArgs = new Object[] { groupId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByG_K, finderArgs,
					this);
		}

		if (result instanceof SegmentsEntry) {
			SegmentsEntry segmentsEntry = (SegmentsEntry)result;

			if ((groupId != segmentsEntry.getGroupId()) ||
					!Objects.equals(key, segmentsEntry.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_K_GROUPID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				query.append(_FINDER_COLUMN_G_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
				}

				List<SegmentsEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByG_K, finderArgs,
						list);
				}
				else {
					SegmentsEntry segmentsEntry = list.get(0);

					result = segmentsEntry;

					cacheResult(segmentsEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByG_K, finderArgs);

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
			return (SegmentsEntry)result;
		}
	}

	/**
	 * Removes the segments entry where groupId = &#63; and key = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByG_K(long groupId, String key)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = findByG_K(groupId, key);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_K(long groupId, String key) {
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByG_K;

		Object[] finderArgs = new Object[] { groupId, key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_K_GROUPID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				query.append(_FINDER_COLUMN_G_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
				}

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

	private static final String _FINDER_COLUMN_G_K_GROUPID_2 = "segmentsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_K_KEY_2 = "segmentsEntry.key = ?";
	private static final String _FINDER_COLUMN_G_K_KEY_3 = "(segmentsEntry.key IS NULL OR segmentsEntry.key = '')";
	private FinderPath _finderPathWithPaginationFindByA_T;
	private FinderPath _finderPathWithoutPaginationFindByA_T;
	private FinderPath _finderPathCountByA_T;

	/**
	 * Returns all the segments entries where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(boolean active, String type) {
		return findByA_T(active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end) {
		return findByA_T(active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return findByA_T(active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByA_T;
			finderArgs = new Object[] { active, type };
		}
		else {
			finderPath = _finderPathWithPaginationFindByA_T;
			finderArgs = new Object[] {
					active, type,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((active != segmentsEntry.isActive()) ||
							!type.equals(segmentsEntry.getType())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByA_T_First(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByA_T_First(active, type,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByA_T_First(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findByA_T(active, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByA_T_Last(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByA_T_Last(active, type,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByA_T_Last(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countByA_T(active, type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByA_T(active, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByA_T_PrevAndNext(long segmentsEntryId,
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByA_T_PrevAndNext(session, segmentsEntry, active,
					type, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByA_T_PrevAndNext(session, segmentsEntry, active,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByA_T_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_A_T_ACTIVE_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_A_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_A_T_TYPE_2);
		}

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where active = &#63; and type = &#63; from the database.
	 *
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByA_T(boolean active, String type) {
		for (SegmentsEntry segmentsEntry : findByA_T(active, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByA_T(boolean active, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByA_T;

		Object[] finderArgs = new Object[] { active, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				if (bindType) {
					qPos.add(type);
				}

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

	private static final String _FINDER_COLUMN_A_T_ACTIVE_2 = "segmentsEntry.active = ? AND ";
	private static final String _FINDER_COLUMN_A_T_TYPE_2 = "segmentsEntry.type = ?";
	private static final String _FINDER_COLUMN_A_T_TYPE_3 = "(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";
	private FinderPath _finderPathWithPaginationFindByG_A_T;
	private FinderPath _finderPathWithoutPaginationFindByG_A_T;
	private FinderPath _finderPathCountByG_A_T;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type) {
		return findByG_A_T(groupId, active, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end) {
		return findByG_A_T(groupId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return findByG_A_T(groupId, active, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_A_T;
			finderArgs = new Object[] { groupId, active, type };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_A_T;
			finderArgs = new Object[] {
					groupId, active, type,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId()) ||
							(active != segmentsEntry.isActive()) ||
							!type.equals(segmentsEntry.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_T_First(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByG_A_T_First(groupId, active, type,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_T_First(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator) {
		List<SegmentsEntry> list = findByG_A_T(groupId, active, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_T_Last(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByG_A_T_Last(groupId, active, type,
				orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_T_Last(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator) {
		int count = countByG_A_T(groupId, active, type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByG_A_T(groupId, active, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByG_A_T_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByG_A_T_PrevAndNext(session, segmentsEntry, groupId,
					active, type, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByG_A_T_PrevAndNext(session, segmentsEntry, groupId,
					active, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByG_A_T_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_A_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_A_T_TYPE_2);
		}

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
			query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(long groupId, boolean active,
		String type) {
		return filterFindByG_A_T(groupId, active, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(long groupId, boolean active,
		String type, int start, int end) {
		return filterFindByG_A_T(groupId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(long groupId, boolean active,
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_T(groupId, active, type, start, end,
				orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			if (bindType) {
				qPos.add(type);
			}

			return (List<SegmentsEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByG_A_T_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_T_PrevAndNext(segmentsEntryId, groupId, active,
				type, orderByComparator);
		}

		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByG_A_T_PrevAndNext(session, segmentsEntry,
					groupId, active, type, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByG_A_T_PrevAndNext(session, segmentsEntry,
					groupId, active, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry filterGetByG_A_T_PrevAndNext(Session session,
		SegmentsEntry segmentsEntry, long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsEntry)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where groupId = &#63; and active = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByG_A_T(long groupId, boolean active, String type) {
		for (SegmentsEntry segmentsEntry : findByG_A_T(groupId, active, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_T(long groupId, boolean active, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_A_T;

		Object[] finderArgs = new Object[] { groupId, active, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (bindType) {
					qPos.add(type);
				}

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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_T(long groupId, boolean active, String type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_T(groupId, active, type);
		}

		type = Objects.toString(type, "");

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SegmentsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			if (bindType) {
				qPos.add(type);
			}

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

	private static final String _FINDER_COLUMN_G_A_T_GROUPID_2 = "segmentsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_T_ACTIVE_2 = "segmentsEntry.active = ? AND ";
	private static final String _FINDER_COLUMN_G_A_T_ACTIVE_2_SQL = "segmentsEntry.active_ = ? AND ";
	private static final String _FINDER_COLUMN_G_A_T_TYPE_2 = "segmentsEntry.type = ?";
	private static final String _FINDER_COLUMN_G_A_T_TYPE_3 = "(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";
	private static final String _FINDER_COLUMN_G_A_T_TYPE_2_SQL = "segmentsEntry.type_ = ?";
	private static final String _FINDER_COLUMN_G_A_T_TYPE_3_SQL = "(segmentsEntry.type_ IS NULL OR segmentsEntry.type_ = '')";

	public SegmentsEntryPersistenceImpl() {
		setModelClass(SegmentsEntry.class);

		setModelImplClass(SegmentsEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the segments entry in the entity cache if it is enabled.
	 *
	 * @param segmentsEntry the segments entry
	 */
	@Override
	public void cacheResult(SegmentsEntry segmentsEntry) {
		entityCache.putResult(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey(),
			segmentsEntry);

		finderCache.putResult(_finderPathFetchByG_K,
			new Object[] { segmentsEntry.getGroupId(), segmentsEntry.getKey() },
			segmentsEntry);

		segmentsEntry.resetOriginalValues();
	}

	/**
	 * Caches the segments entries in the entity cache if it is enabled.
	 *
	 * @param segmentsEntries the segments entries
	 */
	@Override
	public void cacheResult(List<SegmentsEntry> segmentsEntries) {
		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			if (entityCache.getResult(
						SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
						SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey()) == null) {
				cacheResult(segmentsEntry);
			}
			else {
				segmentsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsEntry segmentsEntry) {
		entityCache.removeResult(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SegmentsEntryModelImpl)segmentsEntry, true);
	}

	@Override
	public void clearCache(List<SegmentsEntry> segmentsEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			entityCache.removeResult(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey());

			clearUniqueFindersCache((SegmentsEntryModelImpl)segmentsEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsEntryModelImpl segmentsEntryModelImpl) {
		Object[] args = new Object[] {
				segmentsEntryModelImpl.getGroupId(),
				segmentsEntryModelImpl.getKey()
			};

		finderCache.putResult(_finderPathCountByG_K, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByG_K, args,
			segmentsEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsEntryModelImpl segmentsEntryModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.getKey()
				};

			finderCache.removeResult(_finderPathCountByG_K, args);
			finderCache.removeResult(_finderPathFetchByG_K, args);
		}

		if ((segmentsEntryModelImpl.getColumnBitmask() &
				_finderPathFetchByG_K.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalGroupId(),
					segmentsEntryModelImpl.getOriginalKey()
				};

			finderCache.removeResult(_finderPathCountByG_K, args);
			finderCache.removeResult(_finderPathFetchByG_K, args);
		}
	}

	/**
	 * Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	 *
	 * @param segmentsEntryId the primary key for the new segments entry
	 * @return the new segments entry
	 */
	@Override
	public SegmentsEntry create(long segmentsEntryId) {
		SegmentsEntry segmentsEntry = new SegmentsEntryImpl();

		segmentsEntry.setNew(true);
		segmentsEntry.setPrimaryKey(segmentsEntryId);

		segmentsEntry.setCompanyId(companyProvider.getCompanyId());

		return segmentsEntry;
	}

	/**
	 * Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry remove(long segmentsEntryId)
		throws NoSuchEntryException {
		return remove((Serializable)segmentsEntryId);
	}

	/**
	 * Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			SegmentsEntry segmentsEntry = (SegmentsEntry)session.get(SegmentsEntryImpl.class,
					primaryKey);

			if (segmentsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(segmentsEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected SegmentsEntry removeImpl(SegmentsEntry segmentsEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsEntry)) {
				segmentsEntry = (SegmentsEntry)session.get(SegmentsEntryImpl.class,
						segmentsEntry.getPrimaryKeyObj());
			}

			if (segmentsEntry != null) {
				session.delete(segmentsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntry != null) {
			clearCache(segmentsEntry);
		}

		return segmentsEntry;
	}

	@Override
	public SegmentsEntry updateImpl(SegmentsEntry segmentsEntry) {
		boolean isNew = segmentsEntry.isNew();

		if (!(segmentsEntry instanceof SegmentsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(segmentsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsEntry implementation " +
				segmentsEntry.getClass());
		}

		SegmentsEntryModelImpl segmentsEntryModelImpl = (SegmentsEntryModelImpl)segmentsEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsEntry.setCreateDate(now);
			}
			else {
				segmentsEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsEntry.setModifiedDate(now);
			}
			else {
				segmentsEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (segmentsEntry.isNew()) {
				session.save(segmentsEntry);

				segmentsEntry.setNew(false);
			}
			else {
				segmentsEntry = (SegmentsEntry)session.merge(segmentsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SegmentsEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { segmentsEntryModelImpl.getGroupId() };

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] { segmentsEntryModelImpl.getSource() };

			finderCache.removeResult(_finderPathCountBySource, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindBySource,
				args);

			args = new Object[] { segmentsEntryModelImpl.getType() };

			finderCache.removeResult(_finderPathCountByType, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByType,
				args);

			args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.isActive()
				};

			finderCache.removeResult(_finderPathCountByG_A, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByG_A, args);

			args = new Object[] {
					segmentsEntryModelImpl.isActive(),
					segmentsEntryModelImpl.getType()
				};

			finderCache.removeResult(_finderPathCountByA_T, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByA_T, args);

			args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.isActive(),
					segmentsEntryModelImpl.getType()
				};

			finderCache.removeResult(_finderPathCountByG_A_T, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByG_A_T,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { segmentsEntryModelImpl.getGroupId() };

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindBySource.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalSource()
					};

				finderCache.removeResult(_finderPathCountBySource, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindBySource,
					args);

				args = new Object[] { segmentsEntryModelImpl.getSource() };

				finderCache.removeResult(_finderPathCountBySource, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindBySource,
					args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByType.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalType()
					};

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByType,
					args);

				args = new Object[] { segmentsEntryModelImpl.getType() };

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByType,
					args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalGroupId(),
						segmentsEntryModelImpl.getOriginalActive()
					};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_A,
					args);

				args = new Object[] {
						segmentsEntryModelImpl.getGroupId(),
						segmentsEntryModelImpl.isActive()
					};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_A,
					args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByA_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalActive(),
						segmentsEntryModelImpl.getOriginalType()
					};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByA_T,
					args);

				args = new Object[] {
						segmentsEntryModelImpl.isActive(),
						segmentsEntryModelImpl.getType()
					};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByA_T,
					args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_A_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryModelImpl.getOriginalGroupId(),
						segmentsEntryModelImpl.getOriginalActive(),
						segmentsEntryModelImpl.getOriginalType()
					};

				finderCache.removeResult(_finderPathCountByG_A_T, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_A_T,
					args);

				args = new Object[] {
						segmentsEntryModelImpl.getGroupId(),
						segmentsEntryModelImpl.isActive(),
						segmentsEntryModelImpl.getType()
					};

				finderCache.removeResult(_finderPathCountByG_A_T, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_A_T,
					args);
			}
		}

		entityCache.putResult(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey(),
			segmentsEntry, false);

		clearUniqueFindersCache(segmentsEntryModelImpl, false);
		cacheUniqueFindersCache(segmentsEntryModelImpl);

		segmentsEntry.resetOriginalValues();

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry
	 * @return the segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		SegmentsEntry segmentsEntry = fetchByPrimaryKey(primaryKey);

		if (segmentsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry findByPrimaryKey(long segmentsEntryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)segmentsEntryId);
	}

	/**
	 * Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry fetchByPrimaryKey(long segmentsEntryId) {
		return fetchByPrimaryKey((Serializable)segmentsEntryId);
	}

	/**
	 * Returns all the segments entries.
	 *
	 * @return the segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SegmentsEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SEGMENTSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSENTRY;

				if (pagination) {
					sql = sql.concat(SegmentsEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the segments entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsEntry segmentsEntry : findAll()) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries.
	 *
	 * @return the number of segments entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SEGMENTSENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "segmentsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SegmentsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the segments entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindBySource = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySource",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBySource = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySource",
				new String[] { String.class.getName() },
				SegmentsEntryModelImpl.SOURCE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountBySource = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySource",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindByType = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByType = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
				new String[] { String.class.getName() },
				SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByType = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindByG_A = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
				new String[] { Long.class.getName(), Boolean.class.getName() },
				SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_A = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
				new String[] { Long.class.getName(), Boolean.class.getName() });

		_finderPathFetchByG_K = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByG_K",
				new String[] { Long.class.getName(), String.class.getName() },
				SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsEntryModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByG_K = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_K",
				new String[] { Long.class.getName(), String.class.getName() });

		_finderPathWithPaginationFindByA_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_T",
				new String[] {
					Boolean.class.getName(), String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByA_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_T",
				new String[] { Boolean.class.getName(), String.class.getName() },
				SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByA_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_T",
				new String[] { Boolean.class.getName(), String.class.getName() });

		_finderPathWithPaginationFindByG_A_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A_T",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_A_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED,
				SegmentsEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_T",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				},
				SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
				SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_A_T = new FinderPath(SegmentsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_T",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				});
	}

	public void destroy() {
		entityCache.removeCache(SegmentsEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SEGMENTSENTRY = "SELECT segmentsEntry FROM SegmentsEntry segmentsEntry";
	private static final String _SQL_SELECT_SEGMENTSENTRY_WHERE = "SELECT segmentsEntry FROM SegmentsEntry segmentsEntry WHERE ";
	private static final String _SQL_COUNT_SEGMENTSENTRY = "SELECT COUNT(segmentsEntry) FROM SegmentsEntry segmentsEntry";
	private static final String _SQL_COUNT_SEGMENTSENTRY_WHERE = "SELECT COUNT(segmentsEntry) FROM SegmentsEntry segmentsEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "segmentsEntry.segmentsEntryId";
	private static final String _FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE = "SELECT DISTINCT {segmentsEntry.*} FROM SegmentsEntry segmentsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SegmentsEntry.*} FROM (SELECT DISTINCT segmentsEntry.segmentsEntryId FROM SegmentsEntry segmentsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SegmentsEntry ON TEMP_TABLE.segmentsEntryId = SegmentsEntry.segmentsEntryId";
	private static final String _FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE = "SELECT COUNT(DISTINCT segmentsEntry.segmentsEntryId) AS COUNT_VALUE FROM SegmentsEntry segmentsEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "segmentsEntry";
	private static final String _FILTER_ENTITY_TABLE = "SegmentsEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SegmentsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SegmentsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SegmentsEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SegmentsEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active", "key", "type"
			});
}