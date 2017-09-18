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

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionImpl;
import com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentCollectionPersistence;

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
 * The persistence implementation for the msb fragment collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionPersistence
 * @see com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentCollectionUtil
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionPersistenceImpl extends BasePersistenceImpl<MSBFragmentCollection>
	implements MSBFragmentCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MSBFragmentCollectionUtil} to access the msb fragment collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MSBFragmentCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MSBFragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			MSBFragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the msb fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentCollection> orderByComparator,
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

		List<MSBFragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentCollection msbFragmentCollection : list) {
					if ((groupId != msbFragmentCollection.getGroupId())) {
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

			query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
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
	 * Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection findByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByGroupId_First(groupId,
				orderByComparator);

		if (msbFragmentCollection != null) {
			return msbFragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentCollectionException(msg.toString());
	}

	/**
	 * Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		List<MSBFragmentCollection> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection findByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (msbFragmentCollection != null) {
			return msbFragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentCollectionException(msg.toString());
	}

	/**
	 * Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentCollection> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment collections before and after the current msb fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param msbFragmentCollectionId the primary key of the current msb fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection[] findByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = findByPrimaryKey(msbFragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentCollection[] array = new MSBFragmentCollectionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, msbFragmentCollection,
					groupId, orderByComparator, true);

			array[1] = msbFragmentCollection;

			array[2] = getByGroupId_PrevAndNext(session, msbFragmentCollection,
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

	protected MSBFragmentCollection getByGroupId_PrevAndNext(Session session,
		MSBFragmentCollection msbFragmentCollection, long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
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

		query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);

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
			query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the msb fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					MSBFragmentCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					MSBFragmentCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MSBFragmentCollection>)QueryUtil.list(q, getDialect(),
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
	 * Returns the msb fragment collections before and after the current msb fragment collection in the ordered set of msb fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param msbFragmentCollectionId the primary key of the current msb fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection[] filterFindByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(msbFragmentCollectionId, groupId,
				orderByComparator);
		}

		MSBFragmentCollection msbFragmentCollection = findByPrimaryKey(msbFragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentCollection[] array = new MSBFragmentCollectionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					msbFragmentCollection, groupId, orderByComparator, true);

			array[1] = msbFragmentCollection;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					msbFragmentCollection, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MSBFragmentCollection filterGetByGroupId_PrevAndNext(
		Session session, MSBFragmentCollection msbFragmentCollection,
		long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MSBFragmentCollection msbFragmentCollection : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(msbFragmentCollection);
		}
	}

	/**
	 * Returns the number of msb fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching msb fragment collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE);

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
	 * Returns the number of msb fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "msbFragmentCollection.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			MSBFragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			MSBFragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the msb fragment collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection findByG_N(long groupId, String name)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByG_N(groupId, name);

		if (msbFragmentCollection == null) {
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

			throw new NoSuchFragmentCollectionException(msg.toString());
		}

		return msbFragmentCollection;
	}

	/**
	 * Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof MSBFragmentCollection) {
			MSBFragmentCollection msbFragmentCollection = (MSBFragmentCollection)result;

			if ((groupId != msbFragmentCollection.getGroupId()) ||
					!Objects.equals(name, msbFragmentCollection.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);

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

				List<MSBFragmentCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					MSBFragmentCollection msbFragmentCollection = list.get(0);

					result = msbFragmentCollection;

					cacheResult(msbFragmentCollection);

					if ((msbFragmentCollection.getGroupId() != groupId) ||
							(msbFragmentCollection.getName() == null) ||
							!msbFragmentCollection.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, msbFragmentCollection);
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
			return (MSBFragmentCollection)result;
		}
	}

	/**
	 * Removes the msb fragment collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the msb fragment collection that was removed
	 */
	@Override
	public MSBFragmentCollection removeByG_N(long groupId, String name)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = findByG_N(groupId, name);

		return remove(msbFragmentCollection);
	}

	/**
	 * Returns the number of msb fragment collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching msb fragment collections
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE);

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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "msbFragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "msbFragmentCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "msbFragmentCollection.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(msbFragmentCollection.name IS NULL OR msbFragmentCollection.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN = new FinderPath(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<MSBFragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MSBFragmentCollection msbFragmentCollection : list) {
					if ((groupId != msbFragmentCollection.getGroupId()) ||
							!StringUtil.wildcardMatches(
								msbFragmentCollection.getName(), name,
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
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
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
	 * Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection findByG_LikeN_First(long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (msbFragmentCollection != null) {
			return msbFragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentCollectionException(msg.toString());
	}

	/**
	 * Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByG_LikeN_First(long groupId,
		String name, OrderByComparator<MSBFragmentCollection> orderByComparator) {
		List<MSBFragmentCollection> list = findByG_LikeN(groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection findByG_LikeN_Last(long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (msbFragmentCollection != null) {
			return msbFragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFragmentCollectionException(msg.toString());
	}

	/**
	 * Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByG_LikeN_Last(long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<MSBFragmentCollection> list = findByG_LikeN(groupId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the msb fragment collections before and after the current msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param msbFragmentCollectionId the primary key of the current msb fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection[] findByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = findByPrimaryKey(msbFragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentCollection[] array = new MSBFragmentCollectionImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session, msbFragmentCollection,
					groupId, name, orderByComparator, true);

			array[1] = msbFragmentCollection;

			array[2] = getByG_LikeN_PrevAndNext(session, msbFragmentCollection,
					groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MSBFragmentCollection getByG_LikeN_PrevAndNext(Session session,
		MSBFragmentCollection msbFragmentCollection, long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
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

		query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByG_LikeN(long groupId,
		String name) {
		return filterFindByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByG_LikeN(long groupId,
		String name, int start, int end) {
		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public List<MSBFragmentCollection> filterFindByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN(groupId, name, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					MSBFragmentCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					MSBFragmentCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<MSBFragmentCollection>)QueryUtil.list(q, getDialect(),
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
	 * Returns the msb fragment collections before and after the current msb fragment collection in the ordered set of msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param msbFragmentCollectionId the primary key of the current msb fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection[] filterFindByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(msbFragmentCollectionId, groupId,
				name, orderByComparator);
		}

		MSBFragmentCollection msbFragmentCollection = findByPrimaryKey(msbFragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			MSBFragmentCollection[] array = new MSBFragmentCollectionImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(session,
					msbFragmentCollection, groupId, name, orderByComparator,
					true);

			array[1] = msbFragmentCollection;

			array[2] = filterGetByG_LikeN_PrevAndNext(session,
					msbFragmentCollection, groupId, name, orderByComparator,
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

	protected MSBFragmentCollection filterGetByG_LikeN_PrevAndNext(
		Session session, MSBFragmentCollection msbFragmentCollection,
		long groupId, String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MSBFragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MSBFragmentCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MSBFragmentCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(msbFragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MSBFragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the msb fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (MSBFragmentCollection msbFragmentCollection : findByG_LikeN(
				groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(msbFragmentCollection);
		}
	}

	/**
	 * Returns the number of msb fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching msb fragment collections
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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
	 * Returns the number of msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching msb fragment collections that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MSBFragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "msbFragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_1 = "msbFragmentCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(msbFragmentCollection.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(msbFragmentCollection.name IS NULL OR msbFragmentCollection.name LIKE '')";

	public MSBFragmentCollectionPersistenceImpl() {
		setModelClass(MSBFragmentCollection.class);
	}

	/**
	 * Caches the msb fragment collection in the entity cache if it is enabled.
	 *
	 * @param msbFragmentCollection the msb fragment collection
	 */
	@Override
	public void cacheResult(MSBFragmentCollection msbFragmentCollection) {
		entityCache.putResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			msbFragmentCollection.getPrimaryKey(), msbFragmentCollection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				msbFragmentCollection.getGroupId(),
				msbFragmentCollection.getName()
			}, msbFragmentCollection);

		msbFragmentCollection.resetOriginalValues();
	}

	/**
	 * Caches the msb fragment collections in the entity cache if it is enabled.
	 *
	 * @param msbFragmentCollections the msb fragment collections
	 */
	@Override
	public void cacheResult(List<MSBFragmentCollection> msbFragmentCollections) {
		for (MSBFragmentCollection msbFragmentCollection : msbFragmentCollections) {
			if (entityCache.getResult(
						MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						MSBFragmentCollectionImpl.class,
						msbFragmentCollection.getPrimaryKey()) == null) {
				cacheResult(msbFragmentCollection);
			}
			else {
				msbFragmentCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all msb fragment collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MSBFragmentCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the msb fragment collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MSBFragmentCollection msbFragmentCollection) {
		entityCache.removeResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			msbFragmentCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MSBFragmentCollectionModelImpl)msbFragmentCollection,
			true);
	}

	@Override
	public void clearCache(List<MSBFragmentCollection> msbFragmentCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MSBFragmentCollection msbFragmentCollection : msbFragmentCollections) {
			entityCache.removeResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				MSBFragmentCollectionImpl.class,
				msbFragmentCollection.getPrimaryKey());

			clearUniqueFindersCache((MSBFragmentCollectionModelImpl)msbFragmentCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		MSBFragmentCollectionModelImpl msbFragmentCollectionModelImpl) {
		Object[] args = new Object[] {
				msbFragmentCollectionModelImpl.getGroupId(),
				msbFragmentCollectionModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			msbFragmentCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MSBFragmentCollectionModelImpl msbFragmentCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					msbFragmentCollectionModelImpl.getGroupId(),
					msbFragmentCollectionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((msbFragmentCollectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					msbFragmentCollectionModelImpl.getOriginalGroupId(),
					msbFragmentCollectionModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	 *
	 * @param msbFragmentCollectionId the primary key for the new msb fragment collection
	 * @return the new msb fragment collection
	 */
	@Override
	public MSBFragmentCollection create(long msbFragmentCollectionId) {
		MSBFragmentCollection msbFragmentCollection = new MSBFragmentCollectionImpl();

		msbFragmentCollection.setNew(true);
		msbFragmentCollection.setPrimaryKey(msbFragmentCollectionId);

		msbFragmentCollection.setCompanyId(companyProvider.getCompanyId());

		return msbFragmentCollection;
	}

	/**
	 * Removes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentCollectionId the primary key of the msb fragment collection
	 * @return the msb fragment collection that was removed
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection remove(long msbFragmentCollectionId)
		throws NoSuchFragmentCollectionException {
		return remove((Serializable)msbFragmentCollectionId);
	}

	/**
	 * Removes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the msb fragment collection
	 * @return the msb fragment collection that was removed
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection remove(Serializable primaryKey)
		throws NoSuchFragmentCollectionException {
		Session session = null;

		try {
			session = openSession();

			MSBFragmentCollection msbFragmentCollection = (MSBFragmentCollection)session.get(MSBFragmentCollectionImpl.class,
					primaryKey);

			if (msbFragmentCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFragmentCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(msbFragmentCollection);
		}
		catch (NoSuchFragmentCollectionException nsee) {
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
	protected MSBFragmentCollection removeImpl(
		MSBFragmentCollection msbFragmentCollection) {
		msbFragmentCollection = toUnwrappedModel(msbFragmentCollection);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(msbFragmentCollection)) {
				msbFragmentCollection = (MSBFragmentCollection)session.get(MSBFragmentCollectionImpl.class,
						msbFragmentCollection.getPrimaryKeyObj());
			}

			if (msbFragmentCollection != null) {
				session.delete(msbFragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (msbFragmentCollection != null) {
			clearCache(msbFragmentCollection);
		}

		return msbFragmentCollection;
	}

	@Override
	public MSBFragmentCollection updateImpl(
		MSBFragmentCollection msbFragmentCollection) {
		msbFragmentCollection = toUnwrappedModel(msbFragmentCollection);

		boolean isNew = msbFragmentCollection.isNew();

		MSBFragmentCollectionModelImpl msbFragmentCollectionModelImpl = (MSBFragmentCollectionModelImpl)msbFragmentCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (msbFragmentCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				msbFragmentCollection.setCreateDate(now);
			}
			else {
				msbFragmentCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!msbFragmentCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				msbFragmentCollection.setModifiedDate(now);
			}
			else {
				msbFragmentCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (msbFragmentCollection.isNew()) {
				session.save(msbFragmentCollection);

				msbFragmentCollection.setNew(false);
			}
			else {
				msbFragmentCollection = (MSBFragmentCollection)session.merge(msbFragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!MSBFragmentCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					msbFragmentCollectionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((msbFragmentCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						msbFragmentCollectionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { msbFragmentCollectionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			MSBFragmentCollectionImpl.class,
			msbFragmentCollection.getPrimaryKey(), msbFragmentCollection, false);

		clearUniqueFindersCache(msbFragmentCollectionModelImpl, false);
		cacheUniqueFindersCache(msbFragmentCollectionModelImpl);

		msbFragmentCollection.resetOriginalValues();

		return msbFragmentCollection;
	}

	protected MSBFragmentCollection toUnwrappedModel(
		MSBFragmentCollection msbFragmentCollection) {
		if (msbFragmentCollection instanceof MSBFragmentCollectionImpl) {
			return msbFragmentCollection;
		}

		MSBFragmentCollectionImpl msbFragmentCollectionImpl = new MSBFragmentCollectionImpl();

		msbFragmentCollectionImpl.setNew(msbFragmentCollection.isNew());
		msbFragmentCollectionImpl.setPrimaryKey(msbFragmentCollection.getPrimaryKey());

		msbFragmentCollectionImpl.setMsbFragmentCollectionId(msbFragmentCollection.getMsbFragmentCollectionId());
		msbFragmentCollectionImpl.setGroupId(msbFragmentCollection.getGroupId());
		msbFragmentCollectionImpl.setCompanyId(msbFragmentCollection.getCompanyId());
		msbFragmentCollectionImpl.setUserId(msbFragmentCollection.getUserId());
		msbFragmentCollectionImpl.setUserName(msbFragmentCollection.getUserName());
		msbFragmentCollectionImpl.setCreateDate(msbFragmentCollection.getCreateDate());
		msbFragmentCollectionImpl.setModifiedDate(msbFragmentCollection.getModifiedDate());
		msbFragmentCollectionImpl.setName(msbFragmentCollection.getName());
		msbFragmentCollectionImpl.setDescription(msbFragmentCollection.getDescription());

		return msbFragmentCollectionImpl;
	}

	/**
	 * Returns the msb fragment collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the msb fragment collection
	 * @return the msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFragmentCollectionException {
		MSBFragmentCollection msbFragmentCollection = fetchByPrimaryKey(primaryKey);

		if (msbFragmentCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFragmentCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return msbFragmentCollection;
	}

	/**
	 * Returns the msb fragment collection with the primary key or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	 *
	 * @param msbFragmentCollectionId the primary key of the msb fragment collection
	 * @return the msb fragment collection
	 * @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection findByPrimaryKey(long msbFragmentCollectionId)
		throws NoSuchFragmentCollectionException {
		return findByPrimaryKey((Serializable)msbFragmentCollectionId);
	}

	/**
	 * Returns the msb fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the msb fragment collection
	 * @return the msb fragment collection, or <code>null</code> if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				MSBFragmentCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		MSBFragmentCollection msbFragmentCollection = (MSBFragmentCollection)serializable;

		if (msbFragmentCollection == null) {
			Session session = null;

			try {
				session = openSession();

				msbFragmentCollection = (MSBFragmentCollection)session.get(MSBFragmentCollectionImpl.class,
						primaryKey);

				if (msbFragmentCollection != null) {
					cacheResult(msbFragmentCollection);
				}
				else {
					entityCache.putResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						MSBFragmentCollectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return msbFragmentCollection;
	}

	/**
	 * Returns the msb fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param msbFragmentCollectionId the primary key of the msb fragment collection
	 * @return the msb fragment collection, or <code>null</code> if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection fetchByPrimaryKey(long msbFragmentCollectionId) {
		return fetchByPrimaryKey((Serializable)msbFragmentCollectionId);
	}

	@Override
	public Map<Serializable, MSBFragmentCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MSBFragmentCollection> map = new HashMap<Serializable, MSBFragmentCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MSBFragmentCollection msbFragmentCollection = fetchByPrimaryKey(primaryKey);

			if (msbFragmentCollection != null) {
				map.put(primaryKey, msbFragmentCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (MSBFragmentCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE_PKS_IN);

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

			for (MSBFragmentCollection msbFragmentCollection : (List<MSBFragmentCollection>)q.list()) {
				map.put(msbFragmentCollection.getPrimaryKeyObj(),
					msbFragmentCollection);

				cacheResult(msbFragmentCollection);

				uncachedPrimaryKeys.remove(msbFragmentCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MSBFragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					MSBFragmentCollectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the msb fragment collections.
	 *
	 * @return the msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the msb fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findAll(int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the msb fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> findAll(int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
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

		List<MSBFragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<MSBFragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MSBFRAGMENTCOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MSBFRAGMENTCOLLECTION;

				if (pagination) {
					sql = sql.concat(MSBFragmentCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MSBFragmentCollection>)QueryUtil.list(q,
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
	 * Removes all the msb fragment collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MSBFragmentCollection msbFragmentCollection : findAll()) {
			remove(msbFragmentCollection);
		}
	}

	/**
	 * Returns the number of msb fragment collections.
	 *
	 * @return the number of msb fragment collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MSBFRAGMENTCOLLECTION);

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
		return MSBFragmentCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the msb fragment collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MSBFragmentCollectionImpl.class.getName());
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
	private static final String _SQL_SELECT_MSBFRAGMENTCOLLECTION = "SELECT msbFragmentCollection FROM MSBFragmentCollection msbFragmentCollection";
	private static final String _SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE_PKS_IN = "SELECT msbFragmentCollection FROM MSBFragmentCollection msbFragmentCollection WHERE msbFragmentCollectionId IN (";
	private static final String _SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE = "SELECT msbFragmentCollection FROM MSBFragmentCollection msbFragmentCollection WHERE ";
	private static final String _SQL_COUNT_MSBFRAGMENTCOLLECTION = "SELECT COUNT(msbFragmentCollection) FROM MSBFragmentCollection msbFragmentCollection";
	private static final String _SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE = "SELECT COUNT(msbFragmentCollection) FROM MSBFragmentCollection msbFragmentCollection WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "msbFragmentCollection.msbFragmentCollectionId";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_WHERE = "SELECT DISTINCT {msbFragmentCollection.*} FROM MSBFragmentCollection msbFragmentCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MSBFragmentCollection.*} FROM (SELECT DISTINCT msbFragmentCollection.msbFragmentCollectionId FROM MSBFragmentCollection msbFragmentCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_MSBFRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MSBFragmentCollection ON TEMP_TABLE.msbFragmentCollectionId = MSBFragmentCollection.msbFragmentCollectionId";
	private static final String _FILTER_SQL_COUNT_MSBFRAGMENTCOLLECTION_WHERE = "SELECT COUNT(DISTINCT msbFragmentCollection.msbFragmentCollectionId) AS COUNT_VALUE FROM MSBFragmentCollection msbFragmentCollection WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "msbFragmentCollection";
	private static final String _FILTER_ENTITY_TABLE = "MSBFragmentCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "msbFragmentCollection.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MSBFragmentCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MSBFragmentCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MSBFragmentCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MSBFragmentCollectionPersistenceImpl.class);
}