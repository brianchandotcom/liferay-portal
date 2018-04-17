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

package com.liferay.commerce.user.segment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryImpl;
import com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce user segment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryPersistence
 * @see com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryUtil
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryPersistenceImpl extends BasePersistenceImpl<CommerceUserSegmentEntry>
	implements CommerceUserSegmentEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceUserSegmentEntryUtil} to access the commerce user segment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceUserSegmentEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceUserSegmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceUserSegmentEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce user segment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @return the range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
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

		List<CommerceUserSegmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceUserSegmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceUserSegmentEntry commerceUserSegmentEntry : list) {
					if ((groupId != commerceUserSegmentEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceUserSegmentEntry != null) {
			return commerceUserSegmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchUserSegmentEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		List<CommerceUserSegmentEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceUserSegmentEntry != null) {
			return commerceUserSegmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchUserSegmentEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceUserSegmentEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry[] findByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = findByPrimaryKey(commerceUserSegmentEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentEntry[] array = new CommerceUserSegmentEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, orderByComparator, true);

			array[1] = commerceUserSegmentEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceUserSegmentEntry getByGroupId_PrevAndNext(
		Session session, CommerceUserSegmentEntry commerceUserSegmentEntry,
		long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);

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
			query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceUserSegmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceUserSegmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce user segment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @return the range of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					CommerceUserSegmentEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					CommerceUserSegmentEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set of commerce user segment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry[] filterFindByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(commerceUserSegmentEntryId,
				groupId, orderByComparator);
		}

		CommerceUserSegmentEntry commerceUserSegmentEntry = findByPrimaryKey(commerceUserSegmentEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentEntry[] array = new CommerceUserSegmentEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, orderByComparator, true);

			array[1] = commerceUserSegmentEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceUserSegmentEntry filterGetByGroupId_PrevAndNext(
		Session session, CommerceUserSegmentEntry commerceUserSegmentEntry,
		long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceUserSegmentEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceUserSegmentEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceUserSegmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceUserSegmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce user segment entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceUserSegmentEntry commerceUserSegmentEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceUserSegmentEntry);
		}
	}

	/**
	 * Returns the number of commerce user segment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce user segment entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE);

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
	 * Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceUserSegmentEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CommerceUserSegmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceUserSegmentEntryModelImpl.ACTIVE_COLUMN_BITMASK |
			CommerceUserSegmentEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the commerce user segment entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @return the range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<CommerceUserSegmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceUserSegmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceUserSegmentEntry commerceUserSegmentEntry : list) {
					if ((groupId != commerceUserSegmentEntry.getGroupId()) ||
							(active != commerceUserSegmentEntry.getActive())) {
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

			query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByG_A_First(groupId,
				active, orderByComparator);

		if (commerceUserSegmentEntry != null) {
			return commerceUserSegmentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchUserSegmentEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		List<CommerceUserSegmentEntry> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByG_A_Last(groupId,
				active, orderByComparator);

		if (commerceUserSegmentEntry != null) {
			return commerceUserSegmentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchUserSegmentEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceUserSegmentEntry> list = findByG_A(groupId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry[] findByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = findByPrimaryKey(commerceUserSegmentEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentEntry[] array = new CommerceUserSegmentEntryImpl[3];

			array[0] = getByG_A_PrevAndNext(session, commerceUserSegmentEntry,
					groupId, active, orderByComparator, true);

			array[1] = commerceUserSegmentEntry;

			array[2] = getByG_A_PrevAndNext(session, commerceUserSegmentEntry,
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

	protected CommerceUserSegmentEntry getByG_A_PrevAndNext(Session session,
		CommerceUserSegmentEntry commerceUserSegmentEntry, long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);

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
			query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceUserSegmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceUserSegmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active) {
		return filterFindByG_A(groupId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @return the range of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end) {
		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					CommerceUserSegmentEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					CommerceUserSegmentEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set of commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry[] filterFindByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(commerceUserSegmentEntryId, groupId,
				active, orderByComparator);
		}

		CommerceUserSegmentEntry commerceUserSegmentEntry = findByPrimaryKey(commerceUserSegmentEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentEntry[] array = new CommerceUserSegmentEntryImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, active,
					orderByComparator, true);

			array[1] = commerceUserSegmentEntry;

			array[2] = filterGetByG_A_PrevAndNext(session,
					commerceUserSegmentEntry, groupId, active,
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

	protected CommerceUserSegmentEntry filterGetByG_A_PrevAndNext(
		Session session, CommerceUserSegmentEntry commerceUserSegmentEntry,
		long groupId, boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceUserSegmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceUserSegmentEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceUserSegmentEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceUserSegmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceUserSegmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce user segment entries where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (CommerceUserSegmentEntry commerceUserSegmentEntry : findByG_A(
				groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceUserSegmentEntry);
		}
	}

	/**
	 * Returns the number of commerce user segment entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce user segment entries
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE);

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
	 * Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce user segment entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceUserSegmentEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "commerceUserSegmentEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "commerceUserSegmentEntry.active = ?";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2_SQL = "commerceUserSegmentEntry.active_ = ?";

	public CommerceUserSegmentEntryPersistenceImpl() {
		setModelClass(CommerceUserSegmentEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("active", "active_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce user segment entry in the entity cache if it is enabled.
	 *
	 * @param commerceUserSegmentEntry the commerce user segment entry
	 */
	@Override
	public void cacheResult(CommerceUserSegmentEntry commerceUserSegmentEntry) {
		entityCache.putResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			commerceUserSegmentEntry.getPrimaryKey(), commerceUserSegmentEntry);

		commerceUserSegmentEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce user segment entries in the entity cache if it is enabled.
	 *
	 * @param commerceUserSegmentEntries the commerce user segment entries
	 */
	@Override
	public void cacheResult(
		List<CommerceUserSegmentEntry> commerceUserSegmentEntries) {
		for (CommerceUserSegmentEntry commerceUserSegmentEntry : commerceUserSegmentEntries) {
			if (entityCache.getResult(
						CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceUserSegmentEntryImpl.class,
						commerceUserSegmentEntry.getPrimaryKey()) == null) {
				cacheResult(commerceUserSegmentEntry);
			}
			else {
				commerceUserSegmentEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce user segment entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceUserSegmentEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce user segment entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceUserSegmentEntry commerceUserSegmentEntry) {
		entityCache.removeResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			commerceUserSegmentEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceUserSegmentEntry> commerceUserSegmentEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceUserSegmentEntry commerceUserSegmentEntry : commerceUserSegmentEntries) {
			entityCache.removeResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceUserSegmentEntryImpl.class,
				commerceUserSegmentEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	 *
	 * @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	 * @return the new commerce user segment entry
	 */
	@Override
	public CommerceUserSegmentEntry create(long commerceUserSegmentEntryId) {
		CommerceUserSegmentEntry commerceUserSegmentEntry = new CommerceUserSegmentEntryImpl();

		commerceUserSegmentEntry.setNew(true);
		commerceUserSegmentEntry.setPrimaryKey(commerceUserSegmentEntryId);

		commerceUserSegmentEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceUserSegmentEntry;
	}

	/**
	 * Removes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	 * @return the commerce user segment entry that was removed
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry remove(long commerceUserSegmentEntryId)
		throws NoSuchUserSegmentEntryException {
		return remove((Serializable)commerceUserSegmentEntryId);
	}

	/**
	 * Removes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce user segment entry
	 * @return the commerce user segment entry that was removed
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry remove(Serializable primaryKey)
		throws NoSuchUserSegmentEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentEntry commerceUserSegmentEntry = (CommerceUserSegmentEntry)session.get(CommerceUserSegmentEntryImpl.class,
					primaryKey);

			if (commerceUserSegmentEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserSegmentEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceUserSegmentEntry);
		}
		catch (NoSuchUserSegmentEntryException nsee) {
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
	protected CommerceUserSegmentEntry removeImpl(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		commerceUserSegmentEntry = toUnwrappedModel(commerceUserSegmentEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceUserSegmentEntry)) {
				commerceUserSegmentEntry = (CommerceUserSegmentEntry)session.get(CommerceUserSegmentEntryImpl.class,
						commerceUserSegmentEntry.getPrimaryKeyObj());
			}

			if (commerceUserSegmentEntry != null) {
				session.delete(commerceUserSegmentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceUserSegmentEntry != null) {
			clearCache(commerceUserSegmentEntry);
		}

		return commerceUserSegmentEntry;
	}

	@Override
	public CommerceUserSegmentEntry updateImpl(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		commerceUserSegmentEntry = toUnwrappedModel(commerceUserSegmentEntry);

		boolean isNew = commerceUserSegmentEntry.isNew();

		CommerceUserSegmentEntryModelImpl commerceUserSegmentEntryModelImpl = (CommerceUserSegmentEntryModelImpl)commerceUserSegmentEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceUserSegmentEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceUserSegmentEntry.setCreateDate(now);
			}
			else {
				commerceUserSegmentEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceUserSegmentEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceUserSegmentEntry.setModifiedDate(now);
			}
			else {
				commerceUserSegmentEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceUserSegmentEntry.isNew()) {
				session.save(commerceUserSegmentEntry);

				commerceUserSegmentEntry.setNew(false);
			}
			else {
				commerceUserSegmentEntry = (CommerceUserSegmentEntry)session.merge(commerceUserSegmentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceUserSegmentEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceUserSegmentEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceUserSegmentEntryModelImpl.getGroupId(),
					commerceUserSegmentEntryModelImpl.getActive()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceUserSegmentEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceUserSegmentEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceUserSegmentEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceUserSegmentEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceUserSegmentEntryModelImpl.getOriginalGroupId(),
						commerceUserSegmentEntryModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						commerceUserSegmentEntryModelImpl.getGroupId(),
						commerceUserSegmentEntryModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentEntryImpl.class,
			commerceUserSegmentEntry.getPrimaryKey(), commerceUserSegmentEntry,
			false);

		commerceUserSegmentEntry.resetOriginalValues();

		return commerceUserSegmentEntry;
	}

	protected CommerceUserSegmentEntry toUnwrappedModel(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		if (commerceUserSegmentEntry instanceof CommerceUserSegmentEntryImpl) {
			return commerceUserSegmentEntry;
		}

		CommerceUserSegmentEntryImpl commerceUserSegmentEntryImpl = new CommerceUserSegmentEntryImpl();

		commerceUserSegmentEntryImpl.setNew(commerceUserSegmentEntry.isNew());
		commerceUserSegmentEntryImpl.setPrimaryKey(commerceUserSegmentEntry.getPrimaryKey());

		commerceUserSegmentEntryImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
		commerceUserSegmentEntryImpl.setGroupId(commerceUserSegmentEntry.getGroupId());
		commerceUserSegmentEntryImpl.setCompanyId(commerceUserSegmentEntry.getCompanyId());
		commerceUserSegmentEntryImpl.setUserId(commerceUserSegmentEntry.getUserId());
		commerceUserSegmentEntryImpl.setUserName(commerceUserSegmentEntry.getUserName());
		commerceUserSegmentEntryImpl.setCreateDate(commerceUserSegmentEntry.getCreateDate());
		commerceUserSegmentEntryImpl.setModifiedDate(commerceUserSegmentEntry.getModifiedDate());
		commerceUserSegmentEntryImpl.setName(commerceUserSegmentEntry.getName());
		commerceUserSegmentEntryImpl.setPriority(commerceUserSegmentEntry.getPriority());
		commerceUserSegmentEntryImpl.setActive(commerceUserSegmentEntry.isActive());

		return commerceUserSegmentEntryImpl;
	}

	/**
	 * Returns the commerce user segment entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce user segment entry
	 * @return the commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserSegmentEntryException {
		CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByPrimaryKey(primaryKey);

		if (commerceUserSegmentEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserSegmentEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceUserSegmentEntry;
	}

	/**
	 * Returns the commerce user segment entry with the primary key or throws a {@link NoSuchUserSegmentEntryException} if it could not be found.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	 * @return the commerce user segment entry
	 * @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry findByPrimaryKey(
		long commerceUserSegmentEntryId) throws NoSuchUserSegmentEntryException {
		return findByPrimaryKey((Serializable)commerceUserSegmentEntryId);
	}

	/**
	 * Returns the commerce user segment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce user segment entry
	 * @return the commerce user segment entry, or <code>null</code> if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceUserSegmentEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceUserSegmentEntry commerceUserSegmentEntry = (CommerceUserSegmentEntry)serializable;

		if (commerceUserSegmentEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceUserSegmentEntry = (CommerceUserSegmentEntry)session.get(CommerceUserSegmentEntryImpl.class,
						primaryKey);

				if (commerceUserSegmentEntry != null) {
					cacheResult(commerceUserSegmentEntry);
				}
				else {
					entityCache.putResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceUserSegmentEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceUserSegmentEntry;
	}

	/**
	 * Returns the commerce user segment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	 * @return the commerce user segment entry, or <code>null</code> if a commerce user segment entry with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentEntry fetchByPrimaryKey(
		long commerceUserSegmentEntryId) {
		return fetchByPrimaryKey((Serializable)commerceUserSegmentEntryId);
	}

	@Override
	public Map<Serializable, CommerceUserSegmentEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceUserSegmentEntry> map = new HashMap<Serializable, CommerceUserSegmentEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceUserSegmentEntry commerceUserSegmentEntry = fetchByPrimaryKey(primaryKey);

			if (commerceUserSegmentEntry != null) {
				map.put(primaryKey, commerceUserSegmentEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceUserSegmentEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceUserSegmentEntry commerceUserSegmentEntry : (List<CommerceUserSegmentEntry>)q.list()) {
				map.put(commerceUserSegmentEntry.getPrimaryKeyObj(),
					commerceUserSegmentEntry);

				cacheResult(commerceUserSegmentEntry);

				uncachedPrimaryKeys.remove(commerceUserSegmentEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceUserSegmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce user segment entries.
	 *
	 * @return the commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @return the range of commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce user segment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment entries
	 * @param end the upper bound of the range of commerce user segment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce user segment entries
	 */
	@Override
	public List<CommerceUserSegmentEntry> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
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

		List<CommerceUserSegmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceUserSegmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEUSERSEGMENTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEUSERSEGMENTENTRY;

				if (pagination) {
					sql = sql.concat(CommerceUserSegmentEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceUserSegmentEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce user segment entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceUserSegmentEntry commerceUserSegmentEntry : findAll()) {
			remove(commerceUserSegmentEntry);
		}
	}

	/**
	 * Returns the number of commerce user segment entries.
	 *
	 * @return the number of commerce user segment entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEUSERSEGMENTENTRY);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceUserSegmentEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce user segment entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceUserSegmentEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTENTRY = "SELECT commerceUserSegmentEntry FROM CommerceUserSegmentEntry commerceUserSegmentEntry";
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE_PKS_IN =
		"SELECT commerceUserSegmentEntry FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE commerceUserSegmentEntryId IN (";
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE = "SELECT commerceUserSegmentEntry FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCEUSERSEGMENTENTRY = "SELECT COUNT(commerceUserSegmentEntry) FROM CommerceUserSegmentEntry commerceUserSegmentEntry";
	private static final String _SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE = "SELECT COUNT(commerceUserSegmentEntry) FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "commerceUserSegmentEntry.commerceUserSegmentEntryId";
	private static final String _FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_WHERE =
		"SELECT DISTINCT {commerceUserSegmentEntry.*} FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CommerceUserSegmentEntry.*} FROM (SELECT DISTINCT commerceUserSegmentEntry.commerceUserSegmentEntryId FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEUSERSEGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CommerceUserSegmentEntry ON TEMP_TABLE.commerceUserSegmentEntryId = CommerceUserSegmentEntry.commerceUserSegmentEntryId";
	private static final String _FILTER_SQL_COUNT_COMMERCEUSERSEGMENTENTRY_WHERE =
		"SELECT COUNT(DISTINCT commerceUserSegmentEntry.commerceUserSegmentEntryId) AS COUNT_VALUE FROM CommerceUserSegmentEntry commerceUserSegmentEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "commerceUserSegmentEntry";
	private static final String _FILTER_ENTITY_TABLE = "CommerceUserSegmentEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceUserSegmentEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceUserSegmentEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceUserSegmentEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceUserSegmentEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceUserSegmentEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
}