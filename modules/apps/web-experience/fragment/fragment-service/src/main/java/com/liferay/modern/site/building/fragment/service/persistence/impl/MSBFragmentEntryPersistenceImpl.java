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

package com.liferay.modern.site.building.fragment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryImpl;
import com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentEntryPersistence;

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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the msb fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryPersistence
 * @see com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentEntryUtil
 * @generated
 */
@ProviderType
public class MSBFragmentEntryPersistenceImpl extends BasePersistenceImpl<MSBFragmentEntry>
	implements MSBFragmentEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MSBFragmentEntryUtil} to access the msb fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MSBFragmentEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MSBFragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			MSBFragmentEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the msb fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator,
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

		List<MSBFragmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentEntry msbFragmentEntry : list) {
					if ((groupId != msbFragmentEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
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
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		List<MSBFragmentEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] findByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, msbFragmentEntry,
					groupId, orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = getByGroupId_PrevAndNext(session, msbFragmentEntry,
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

	protected MSBFragmentEntry getByGroupId_PrevAndNext(Session session,
		MSBFragmentEntry msbFragmentEntry, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

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
			query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the msb fragment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MSBFragmentEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] filterFindByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(msbFragmentEntryId, groupId,
				orderByComparator);
		}

		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					msbFragmentEntry, groupId, orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					msbFragmentEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MSBFragmentEntry filterGetByGroupId_PrevAndNext(Session session,
		MSBFragmentEntry msbFragmentEntry, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MSBFragmentEntry msbFragmentEntry : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(msbFragmentEntry);
		}
	}

	/**
	 * Returns the number of msb fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching msb fragment entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

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
	 * Returns the number of msb fragment entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "msbFragmentEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByMSBFragmentCollectionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByMSBFragmentCollectionId",
			new String[] { Long.class.getName() },
			MSBFragmentEntryModelImpl.MSBFRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			MSBFragmentEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MSBFRAGMENTCOLLECTIONID = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByMSBFragmentCollectionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the msb fragment entries where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId) {
		return findByMSBFragmentCollectionId(msbFragmentCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end) {
		return findByMSBFragmentCollectionId(msbFragmentCollectionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return findByMSBFragmentCollectionId(msbFragmentCollectionId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID;
			finderArgs = new Object[] { msbFragmentCollectionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID;
			finderArgs = new Object[] {
					msbFragmentCollectionId,
					
					start, end, orderByComparator
				};
		}

		List<MSBFragmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentEntry msbFragmentEntry : list) {
					if ((msbFragmentCollectionId != msbFragmentEntry.getMsbFragmentCollectionId())) {
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

			query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MSBFRAGMENTCOLLECTIONID_MSBFRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(msbFragmentCollectionId);

				if (!pagination) {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
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
	 * Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByMSBFragmentCollectionId_First(msbFragmentCollectionId,
				orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		List<MSBFragmentEntry> list = findByMSBFragmentCollectionId(msbFragmentCollectionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByMSBFragmentCollectionId_Last(msbFragmentCollectionId,
				orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		int count = countByMSBFragmentCollectionId(msbFragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentEntry> list = findByMSBFragmentCollectionId(msbFragmentCollectionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] findByMSBFragmentCollectionId_PrevAndNext(
		long msbFragmentEntryId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = getByMSBFragmentCollectionId_PrevAndNext(session,
					msbFragmentEntry, msbFragmentCollectionId,
					orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = getByMSBFragmentCollectionId_PrevAndNext(session,
					msbFragmentEntry, msbFragmentCollectionId,
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

	protected MSBFragmentEntry getByMSBFragmentCollectionId_PrevAndNext(
		Session session, MSBFragmentEntry msbFragmentEntry,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_MSBFRAGMENTCOLLECTIONID_MSBFRAGMENTCOLLECTIONID_2);

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
			query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(msbFragmentCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment entries where msbFragmentCollectionId = &#63; from the database.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 */
	@Override
	public void removeByMSBFragmentCollectionId(long msbFragmentCollectionId) {
		for (MSBFragmentEntry msbFragmentEntry : findByMSBFragmentCollectionId(
				msbFragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(msbFragmentEntry);
		}
	}

	/**
	 * Returns the number of msb fragment entries where msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the number of matching msb fragment entries
	 */
	@Override
	public int countByMSBFragmentCollectionId(long msbFragmentCollectionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MSBFRAGMENTCOLLECTIONID;

		Object[] finderArgs = new Object[] { msbFragmentCollectionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MSBFRAGMENTCOLLECTIONID_MSBFRAGMENTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(msbFragmentCollectionId);

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

	private static final String _FINDER_COLUMN_MSBFRAGMENTCOLLECTIONID_MSBFRAGMENTCOLLECTIONID_2 =
		"msbFragmentEntry.msbFragmentCollectionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_MSBFCI = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_MSBFCI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_MSBFCI",
			new String[] { Long.class.getName(), Long.class.getName() },
			MSBFragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			MSBFragmentEntryModelImpl.MSBFRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			MSBFragmentEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_MSBFCI = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_MSBFCI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		return findByG_MSBFCI(groupId, msbFragmentCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end) {
		return findByG_MSBFCI(groupId, msbFragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return findByG_MSBFCI(groupId, msbFragmentCollectionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI;
			finderArgs = new Object[] { groupId, msbFragmentCollectionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_MSBFCI;
			finderArgs = new Object[] {
					groupId, msbFragmentCollectionId,
					
					start, end, orderByComparator
				};
		}

		List<MSBFragmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentEntry msbFragmentEntry : list) {
					if ((groupId != msbFragmentEntry.getGroupId()) ||
							(msbFragmentCollectionId != msbFragmentEntry.getMsbFragmentCollectionId())) {
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

			query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

			query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(msbFragmentCollectionId);

				if (!pagination) {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
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
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByG_MSBFCI_First(groupId,
				msbFragmentCollectionId, orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		List<MSBFragmentEntry> list = findByG_MSBFCI(groupId,
				msbFragmentCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByG_MSBFCI_Last(groupId,
				msbFragmentCollectionId, orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		int count = countByG_MSBFCI(groupId, msbFragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentEntry> list = findByG_MSBFCI(groupId,
				msbFragmentCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] findByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = getByG_MSBFCI_PrevAndNext(session, msbFragmentEntry,
					groupId, msbFragmentCollectionId, orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = getByG_MSBFCI_PrevAndNext(session, msbFragmentEntry,
					groupId, msbFragmentCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MSBFragmentEntry getByG_MSBFCI_PrevAndNext(Session session,
		MSBFragmentEntry msbFragmentEntry, long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

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
			query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(msbFragmentCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		return filterFindByG_MSBFCI(groupId, msbFragmentCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end) {
		return filterFindByG_MSBFCI(groupId, msbFragmentCollectionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_MSBFCI(groupId, msbFragmentCollectionId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(msbFragmentCollectionId);

			return (List<MSBFragmentEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] filterFindByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_MSBFCI_PrevAndNext(msbFragmentEntryId, groupId,
				msbFragmentCollectionId, orderByComparator);
		}

		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = filterGetByG_MSBFCI_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId,
					orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = filterGetByG_MSBFCI_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId,
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

	protected MSBFragmentEntry filterGetByG_MSBFCI_PrevAndNext(
		Session session, MSBFragmentEntry msbFragmentEntry, long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(msbFragmentCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 */
	@Override
	public void removeByG_MSBFCI(long groupId, long msbFragmentCollectionId) {
		for (MSBFragmentEntry msbFragmentEntry : findByG_MSBFCI(groupId,
				msbFragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(msbFragmentEntry);
		}
	}

	/**
	 * Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the number of matching msb fragment entries
	 */
	@Override
	public int countByG_MSBFCI(long groupId, long msbFragmentCollectionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_MSBFCI;

		Object[] finderArgs = new Object[] { groupId, msbFragmentCollectionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

			query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(msbFragmentCollectionId);

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
	 * Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @return the number of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_MSBFCI(long groupId, long msbFragmentCollectionId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_MSBFCI(groupId, msbFragmentCollectionId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_MSBFCI_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(msbFragmentCollectionId);

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

	private static final String _FINDER_COLUMN_G_MSBFCI_GROUPID_2 = "msbFragmentEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_MSBFCI_MSBFRAGMENTCOLLECTIONID_2 =
		"msbFragmentEntry.msbFragmentCollectionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			MSBFragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			MSBFragmentEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the msb fragment entry where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByG_N(long groupId, String name)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByG_N(groupId, name);

		if (msbFragmentEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFragmentEntryException(msg.toString());
		}

		return msbFragmentEntry;
	}

	/**
	 * Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof MSBFragmentEntry) {
			MSBFragmentEntry msbFragmentEntry = (MSBFragmentEntry)result;

			if ((groupId != msbFragmentEntry.getGroupId()) ||
					!Objects.equals(name, msbFragmentEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				List<MSBFragmentEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					MSBFragmentEntry msbFragmentEntry = list.get(0);

					result = msbFragmentEntry;

					cacheResult(msbFragmentEntry);

					if ((msbFragmentEntry.getGroupId() != groupId) ||
							(msbFragmentEntry.getName() == null) ||
							!msbFragmentEntry.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, msbFragmentEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, finderArgs);

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
			return (MSBFragmentEntry)result;
		}
	}

	/**
	 * Removes the msb fragment entry where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the msb fragment entry that was removed
	 */
	@Override
	public MSBFragmentEntry removeByG_N(long groupId, String name)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = findByG_N(groupId, name);

		return remove(msbFragmentEntry);
	}

	/**
	 * Returns the number of msb fragment entries where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching msb fragment entries
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "msbFragmentEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "msbFragmentEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "msbFragmentEntry.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(msbFragmentEntry.name IS NULL OR msbFragmentEntry.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_MSBFCI_LIKEN =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_MSBFCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_MSBFCI_LIKEN =
		new FinderPath(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_MSBFCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @return the matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name) {
		return findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name, int start, int end) {
		return findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_MSBFCI_LIKEN;
		finderArgs = new Object[] {
				groupId, msbFragmentCollectionId, name,
				
				start, end, orderByComparator
			};

		List<MSBFragmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentEntry msbFragmentEntry : list) {
					if ((groupId != msbFragmentEntry.getGroupId()) ||
							(msbFragmentCollectionId != msbFragmentEntry.getMsbFragmentCollectionId()) ||
							!StringUtil.wildcardMatches(
								msbFragmentEntry.getName(), name,
								CharPool.UNDERLINE, CharPool.PERCENT,
								CharPool.BACK_SLASH, false)) {
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

			query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(msbFragmentCollectionId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
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
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByG_MSBFCI_LikeN_First(groupId,
				msbFragmentCollectionId, name, orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		List<MSBFragmentEntry> list = findByG_MSBFCI_LikeN(groupId,
				msbFragmentCollectionId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry
	 * @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry findByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByG_MSBFCI_LikeN_Last(groupId,
				msbFragmentCollectionId, name, orderByComparator);

		if (msbFragmentEntry != null) {
			return msbFragmentEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", msbFragmentCollectionId=");
		msg.append(msbFragmentCollectionId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentEntryException(msg.toString());
	}

	/**
	 * Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		int count = countByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentEntry> list = findByG_MSBFCI_LikeN(groupId,
				msbFragmentCollectionId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] findByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		String name, OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = getByG_MSBFCI_LikeN_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId, name,
					orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = getByG_MSBFCI_LikeN_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId, name,
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

	protected MSBFragmentEntry getByG_MSBFCI_LikeN_PrevAndNext(
		Session session, MSBFragmentEntry msbFragmentEntry, long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
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
			query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(msbFragmentCollectionId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @return the matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name) {
		return filterFindByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId,
			name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name, int start, int end) {
		return filterFindByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId,
			name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(msbFragmentCollectionId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<MSBFragmentEntry>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param msbFragmentEntryId the primary key of the current msb fragment entry
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry[] filterFindByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		String name, OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_MSBFCI_LikeN_PrevAndNext(msbFragmentEntryId,
				groupId, msbFragmentCollectionId, name, orderByComparator);
		}

		MSBFragmentEntry msbFragmentEntry = findByPrimaryKey(msbFragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry[] array = new MSBFragmentEntryImpl[3];

			array[0] = filterGetByG_MSBFCI_LikeN_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId, name,
					orderByComparator, true);

			array[1] = msbFragmentEntry;

			array[2] = filterGetByG_MSBFCI_LikeN_PrevAndNext(session,
					msbFragmentEntry, groupId, msbFragmentCollectionId, name,
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

	protected MSBFragmentEntry filterGetByG_MSBFCI_LikeN_PrevAndNext(
		Session session, MSBFragmentEntry msbFragmentEntry, long groupId,
		long msbFragmentCollectionId, String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator, boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(msbFragmentCollectionId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name) {
		for (MSBFragmentEntry msbFragmentEntry : findByG_MSBFCI_LikeN(groupId,
				msbFragmentCollectionId, name, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(msbFragmentEntry);
		}
	}

	/**
	 * Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @return the number of matching msb fragment entries
	 */
	@Override
	public int countByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_MSBFCI_LIKEN;

		Object[] finderArgs = new Object[] {
				groupId, msbFragmentCollectionId, name
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(msbFragmentCollectionId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
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
	 * Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param msbFragmentCollectionId the msb fragment collection ID
	 * @param name the name
	 * @return the number of matching msb fragment entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MSBFRAGMENTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(msbFragmentCollectionId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
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

	private static final String _FINDER_COLUMN_G_MSBFCI_LIKEN_GROUPID_2 = "msbFragmentEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_MSBFCI_LIKEN_MSBFRAGMENTCOLLECTIONID_2 =
		"msbFragmentEntry.msbFragmentCollectionId = ? AND ";
	private static final String _FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_1 = "msbFragmentEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_2 = "lower(msbFragmentEntry.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_MSBFCI_LIKEN_NAME_3 = "(msbFragmentEntry.name IS NULL OR msbFragmentEntry.name LIKE '')";

	public MSBFragmentEntryPersistenceImpl() {
		setModelClass(MSBFragmentEntry.class);
	}

	/**
	 * Caches the msb fragment entry in the entity cache if it is enabled.
	 *
	 * @param msbFragmentEntry the msb fragment entry
	 */
	@Override
	public void cacheResult(MSBFragmentEntry msbFragmentEntry) {
		entityCache.putResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, msbFragmentEntry.getPrimaryKey(),
			msbFragmentEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				msbFragmentEntry.getGroupId(), msbFragmentEntry.getName()
			}, msbFragmentEntry);

		msbFragmentEntry.resetOriginalValues();
	}

	/**
	 * Caches the msb fragment entries in the entity cache if it is enabled.
	 *
	 * @param msbFragmentEntries the msb fragment entries
	 */
	@Override
	public void cacheResult(List<MSBFragmentEntry> msbFragmentEntries) {
		for (MSBFragmentEntry msbFragmentEntry : msbFragmentEntries) {
			if (entityCache.getResult(
						MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
						MSBFragmentEntryImpl.class,
						msbFragmentEntry.getPrimaryKey()) == null) {
				cacheResult(msbFragmentEntry);
			}
			else {
				msbFragmentEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all msb fragment entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MSBFragmentEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the msb fragment entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MSBFragmentEntry msbFragmentEntry) {
		entityCache.removeResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, msbFragmentEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MSBFragmentEntryModelImpl)msbFragmentEntry,
			true);
	}

	@Override
	public void clearCache(List<MSBFragmentEntry> msbFragmentEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MSBFragmentEntry msbFragmentEntry : msbFragmentEntries) {
			entityCache.removeResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
				MSBFragmentEntryImpl.class, msbFragmentEntry.getPrimaryKey());

			clearUniqueFindersCache((MSBFragmentEntryModelImpl)msbFragmentEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		MSBFragmentEntryModelImpl msbFragmentEntryModelImpl) {
		Object[] args = new Object[] {
				msbFragmentEntryModelImpl.getGroupId(),
				msbFragmentEntryModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			msbFragmentEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MSBFragmentEntryModelImpl msbFragmentEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					msbFragmentEntryModelImpl.getGroupId(),
					msbFragmentEntryModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((msbFragmentEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					msbFragmentEntryModelImpl.getOriginalGroupId(),
					msbFragmentEntryModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new msb fragment entry with the primary key. Does not add the msb fragment entry to the database.
	 *
	 * @param msbFragmentEntryId the primary key for the new msb fragment entry
	 * @return the new msb fragment entry
	 */
	@Override
	public MSBFragmentEntry create(long msbFragmentEntryId) {
		MSBFragmentEntry msbFragmentEntry = new MSBFragmentEntryImpl();

		msbFragmentEntry.setNew(true);
		msbFragmentEntry.setPrimaryKey(msbFragmentEntryId);

		msbFragmentEntry.setCompanyId(companyProvider.getCompanyId());

		return msbFragmentEntry;
	}

	/**
	 * Removes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentEntryId the primary key of the msb fragment entry
	 * @return the msb fragment entry that was removed
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry remove(long msbFragmentEntryId)
		throws NoSuchFragmentEntryException {
		return remove((Serializable)msbFragmentEntryId);
	}

	/**
	 * Removes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the msb fragment entry
	 * @return the msb fragment entry that was removed
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry remove(Serializable primaryKey)
		throws NoSuchFragmentEntryException {
		Session session = null;

		try {
			session = openSession();

			MSBFragmentEntry msbFragmentEntry = (MSBFragmentEntry)session.get(MSBFragmentEntryImpl.class,
					primaryKey);

			if (msbFragmentEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFragmentEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(msbFragmentEntry);
		}
		catch (NoSuchFragmentEntryException nsee) {
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
	protected MSBFragmentEntry removeImpl(MSBFragmentEntry msbFragmentEntry) {
		msbFragmentEntry = toUnwrappedModel(msbFragmentEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(msbFragmentEntry)) {
				msbFragmentEntry = (MSBFragmentEntry)session.get(MSBFragmentEntryImpl.class,
						msbFragmentEntry.getPrimaryKeyObj());
			}

			if (msbFragmentEntry != null) {
				session.delete(msbFragmentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (msbFragmentEntry != null) {
			clearCache(msbFragmentEntry);
		}

		return msbFragmentEntry;
	}

	@Override
	public MSBFragmentEntry updateImpl(MSBFragmentEntry msbFragmentEntry) {
		msbFragmentEntry = toUnwrappedModel(msbFragmentEntry);

		boolean isNew = msbFragmentEntry.isNew();

		MSBFragmentEntryModelImpl msbFragmentEntryModelImpl = (MSBFragmentEntryModelImpl)msbFragmentEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (msbFragmentEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				msbFragmentEntry.setCreateDate(now);
			}
			else {
				msbFragmentEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!msbFragmentEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				msbFragmentEntry.setModifiedDate(now);
			}
			else {
				msbFragmentEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (msbFragmentEntry.isNew()) {
				session.save(msbFragmentEntry);

				msbFragmentEntry.setNew(false);
			}
			else {
				msbFragmentEntry = (MSBFragmentEntry)session.merge(msbFragmentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!MSBFragmentEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { msbFragmentEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					msbFragmentEntryModelImpl.getMsbFragmentCollectionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_MSBFRAGMENTCOLLECTIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID,
				args);

			args = new Object[] {
					msbFragmentEntryModelImpl.getGroupId(),
					msbFragmentEntryModelImpl.getMsbFragmentCollectionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_MSBFCI, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((msbFragmentEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						msbFragmentEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { msbFragmentEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((msbFragmentEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						msbFragmentEntryModelImpl.getOriginalMsbFragmentCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MSBFRAGMENTCOLLECTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID,
					args);

				args = new Object[] {
						msbFragmentEntryModelImpl.getMsbFragmentCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MSBFRAGMENTCOLLECTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MSBFRAGMENTCOLLECTIONID,
					args);
			}

			if ((msbFragmentEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						msbFragmentEntryModelImpl.getOriginalGroupId(),
						msbFragmentEntryModelImpl.getOriginalMsbFragmentCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_MSBFCI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI,
					args);

				args = new Object[] {
						msbFragmentEntryModelImpl.getGroupId(),
						msbFragmentEntryModelImpl.getMsbFragmentCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_MSBFCI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_MSBFCI,
					args);
			}
		}

		entityCache.putResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentEntryImpl.class, msbFragmentEntry.getPrimaryKey(),
			msbFragmentEntry, false);

		clearUniqueFindersCache(msbFragmentEntryModelImpl, false);
		cacheUniqueFindersCache(msbFragmentEntryModelImpl);

		msbFragmentEntry.resetOriginalValues();

		return msbFragmentEntry;
	}

	protected MSBFragmentEntry toUnwrappedModel(
		MSBFragmentEntry msbFragmentEntry) {
		if (msbFragmentEntry instanceof MSBFragmentEntryImpl) {
			return msbFragmentEntry;
		}

		MSBFragmentEntryImpl msbFragmentEntryImpl = new MSBFragmentEntryImpl();

		msbFragmentEntryImpl.setNew(msbFragmentEntry.isNew());
		msbFragmentEntryImpl.setPrimaryKey(msbFragmentEntry.getPrimaryKey());

		msbFragmentEntryImpl.setMsbFragmentEntryId(msbFragmentEntry.getMsbFragmentEntryId());
		msbFragmentEntryImpl.setGroupId(msbFragmentEntry.getGroupId());
		msbFragmentEntryImpl.setCompanyId(msbFragmentEntry.getCompanyId());
		msbFragmentEntryImpl.setUserId(msbFragmentEntry.getUserId());
		msbFragmentEntryImpl.setUserName(msbFragmentEntry.getUserName());
		msbFragmentEntryImpl.setCreateDate(msbFragmentEntry.getCreateDate());
		msbFragmentEntryImpl.setModifiedDate(msbFragmentEntry.getModifiedDate());
		msbFragmentEntryImpl.setMsbFragmentCollectionId(msbFragmentEntry.getMsbFragmentCollectionId());
		msbFragmentEntryImpl.setName(msbFragmentEntry.getName());
		msbFragmentEntryImpl.setCss(msbFragmentEntry.getCss());
		msbFragmentEntryImpl.setHtml(msbFragmentEntry.getHtml());
		msbFragmentEntryImpl.setJs(msbFragmentEntry.getJs());

		return msbFragmentEntryImpl;
	}

	/**
	 * Returns the msb fragment entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the msb fragment entry
	 * @return the msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFragmentEntryException {
		MSBFragmentEntry msbFragmentEntry = fetchByPrimaryKey(primaryKey);

		if (msbFragmentEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFragmentEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return msbFragmentEntry;
	}

	/**
	 * Returns the msb fragment entry with the primary key or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	 *
	 * @param msbFragmentEntryId the primary key of the msb fragment entry
	 * @return the msb fragment entry
	 * @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry findByPrimaryKey(long msbFragmentEntryId)
		throws NoSuchFragmentEntryException {
		return findByPrimaryKey((Serializable)msbFragmentEntryId);
	}

	/**
	 * Returns the msb fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the msb fragment entry
	 * @return the msb fragment entry, or <code>null</code> if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
				MSBFragmentEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		MSBFragmentEntry msbFragmentEntry = (MSBFragmentEntry)serializable;

		if (msbFragmentEntry == null) {
			Session session = null;

			try {
				session = openSession();

				msbFragmentEntry = (MSBFragmentEntry)session.get(MSBFragmentEntryImpl.class,
						primaryKey);

				if (msbFragmentEntry != null) {
					cacheResult(msbFragmentEntry);
				}
				else {
					entityCache.putResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
						MSBFragmentEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return msbFragmentEntry;
	}

	/**
	 * Returns the msb fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param msbFragmentEntryId the primary key of the msb fragment entry
	 * @return the msb fragment entry, or <code>null</code> if a msb fragment entry with the primary key could not be found
	 */
	@Override
	public MSBFragmentEntry fetchByPrimaryKey(long msbFragmentEntryId) {
		return fetchByPrimaryKey((Serializable)msbFragmentEntryId);
	}

	@Override
	public Map<Serializable, MSBFragmentEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MSBFragmentEntry> map = new HashMap<Serializable, MSBFragmentEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MSBFragmentEntry msbFragmentEntry = fetchByPrimaryKey(primaryKey);

			if (msbFragmentEntry != null) {
				map.put(primaryKey, msbFragmentEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (MSBFragmentEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MSBFRAGMENTENTRY_WHERE_PKS_IN);

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

			for (MSBFragmentEntry msbFragmentEntry : (List<MSBFragmentEntry>)q.list()) {
				map.put(msbFragmentEntry.getPrimaryKeyObj(), msbFragmentEntry);

				cacheResult(msbFragmentEntry);

				uncachedPrimaryKeys.remove(msbFragmentEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MSBFragmentEntryModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the msb fragment entries.
	 *
	 * @return the msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @return the range of msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findAll(int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment entries
	 * @param end the upper bound of the range of msb fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of msb fragment entries
	 */
	@Override
	public List<MSBFragmentEntry> findAll(int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
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

		List<MSBFragmentEntry> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MSBFRAGMENTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MSBFRAGMENTENTRY;

				if (pagination) {
					sql = sql.concat(MSBFragmentEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentEntry>)QueryUtil.list(q,
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
	 * Removes all the msb fragment entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MSBFragmentEntry msbFragmentEntry : findAll()) {
			remove(msbFragmentEntry);
		}
	}

	/**
	 * Returns the number of msb fragment entries.
	 *
	 * @return the number of msb fragment entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MSBFRAGMENTENTRY);

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
		return MSBFragmentEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the msb fragment entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MSBFragmentEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_MSBFRAGMENTENTRY = "SELECT msbFragmentEntry FROM MSBFragmentEntry msbFragmentEntry";
	private static final String _SQL_SELECT_MSBFRAGMENTENTRY_WHERE_PKS_IN = "SELECT msbFragmentEntry FROM MSBFragmentEntry msbFragmentEntry WHERE msbFragmentEntryId IN (";
	private static final String _SQL_SELECT_MSBFRAGMENTENTRY_WHERE = "SELECT msbFragmentEntry FROM MSBFragmentEntry msbFragmentEntry WHERE ";
	private static final String _SQL_COUNT_MSBFRAGMENTENTRY = "SELECT COUNT(msbFragmentEntry) FROM MSBFragmentEntry msbFragmentEntry";
	private static final String _SQL_COUNT_MSBFRAGMENTENTRY_WHERE = "SELECT COUNT(msbFragmentEntry) FROM MSBFragmentEntry msbFragmentEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "msbFragmentEntry.msbFragmentEntryId";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTENTRY_WHERE = "SELECT DISTINCT {msbFragmentEntry.*} FROM MSBFragmentEntry msbFragmentEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MSBFragmentEntry.*} FROM (SELECT DISTINCT msbFragmentEntry.msbFragmentEntryId FROM MSBFragmentEntry msbFragmentEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MSBFragmentEntry ON TEMP_TABLE.msbFragmentEntryId = MSBFragmentEntry.msbFragmentEntryId";
	private static final String _FILTER_SQL_COUNT_MSBFRAGMENTENTRY_WHERE = "SELECT COUNT(DISTINCT msbFragmentEntry.msbFragmentEntryId) AS COUNT_VALUE FROM MSBFragmentEntry msbFragmentEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "msbFragmentEntry";
	private static final String _FILTER_ENTITY_TABLE = "MSBFragmentEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "msbFragmentEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MSBFragmentEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MSBFragmentEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MSBFragmentEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MSBFragmentEntryPersistenceImpl.class);
}