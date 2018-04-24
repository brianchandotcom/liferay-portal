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

package com.liferay.fragment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.impl.FragmentCollectionImpl;
import com.liferay.fragment.model.impl.FragmentCollectionModelImpl;
import com.liferay.fragment.service.persistence.FragmentCollectionPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
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
 * The persistence implementation for the fragment collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionPersistence
 * @see com.liferay.fragment.service.persistence.FragmentCollectionUtil
 * @generated
 */
@ProviderType
public class FragmentCollectionPersistenceImpl extends BasePersistenceImpl<FragmentCollection>
	implements FragmentCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FragmentCollectionUtil} to access the fragment collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FragmentCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			FragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentCollection> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentCollection> orderByComparator,
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

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if ((groupId != fragmentCollection.getGroupId())) {
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

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
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
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByGroupId_First(groupId,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByGroupId_PrevAndNext(
		long fragmentCollectionId, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, fragmentCollection,
					groupId, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByGroupId_PrevAndNext(session, fragmentCollection,
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

	protected FragmentCollection getByGroupId_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator,
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

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

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
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(FragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, FragmentCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, FragmentCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<FragmentCollection>)QueryUtil.list(q, getDialect(),
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
	 * Returns the fragment collections before and after the current fragment collection in the ordered set of fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] filterFindByGroupId_PrevAndNext(
		long fragmentCollectionId, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(fragmentCollectionId, groupId,
				orderByComparator);
		}

		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					fragmentCollection, groupId, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					fragmentCollection, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection filterGetByGroupId_PrevAndNext(
		Session session, FragmentCollection fragmentCollection, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(FragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, FragmentCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, FragmentCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentCollection fragmentCollection : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

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
	 * Returns the number of fragment collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment collections that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "fragmentCollection.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_FCK = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_FCK",
			new String[] { Long.class.getName(), String.class.getName() },
			FragmentCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentCollectionModelImpl.FRAGMENTCOLLECTIONKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_FCK = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCK",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_FCK(long groupId,
		String fragmentCollectionKey) throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_FCK(groupId,
				fragmentCollectionKey);

		if (fragmentCollection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", fragmentCollectionKey=");
			msg.append(fragmentCollectionKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCollectionException(msg.toString());
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_FCK(long groupId,
		String fragmentCollectionKey) {
		return fetchByG_FCK(groupId, fragmentCollectionKey, true);
	}

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_FCK(long groupId,
		String fragmentCollectionKey, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, fragmentCollectionKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_FCK,
					finderArgs, this);
		}

		if (result instanceof FragmentCollection) {
			FragmentCollection fragmentCollection = (FragmentCollection)result;

			if ((groupId != fragmentCollection.getGroupId()) ||
					!Objects.equals(fragmentCollectionKey,
						fragmentCollection.getFragmentCollectionKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_FCK_GROUPID_2);

			boolean bindFragmentCollectionKey = false;

			if (fragmentCollectionKey == null) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_1);
			}
			else if (fragmentCollectionKey.equals("")) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3);
			}
			else {
				bindFragmentCollectionKey = true;

				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindFragmentCollectionKey) {
					qPos.add(fragmentCollectionKey);
				}

				List<FragmentCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_FCK,
						finderArgs, list);
				}
				else {
					FragmentCollection fragmentCollection = list.get(0);

					result = fragmentCollection;

					cacheResult(fragmentCollection);

					if ((fragmentCollection.getGroupId() != groupId) ||
							(fragmentCollection.getFragmentCollectionKey() == null) ||
							!fragmentCollection.getFragmentCollectionKey()
												   .equals(fragmentCollectionKey)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_FCK,
							finderArgs, fragmentCollection);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_FCK, finderArgs);

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
			return (FragmentCollection)result;
		}
	}

	/**
	 * Removes the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the fragment collection that was removed
	 */
	@Override
	public FragmentCollection removeByG_FCK(long groupId,
		String fragmentCollectionKey) throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByG_FCK(groupId,
				fragmentCollectionKey);

		return remove(fragmentCollection);
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63; and fragmentCollectionKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByG_FCK(long groupId, String fragmentCollectionKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_FCK;

		Object[] finderArgs = new Object[] { groupId, fragmentCollectionKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_FCK_GROUPID_2);

			boolean bindFragmentCollectionKey = false;

			if (fragmentCollectionKey == null) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_1);
			}
			else if (fragmentCollectionKey.equals("")) {
				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3);
			}
			else {
				bindFragmentCollectionKey = true;

				query.append(_FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindFragmentCollectionKey) {
					qPos.add(fragmentCollectionKey);
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

	private static final String _FINDER_COLUMN_G_FCK_GROUPID_2 = "fragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_1 = "fragmentCollection.fragmentCollectionKey IS NULL";
	private static final String _FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_2 = "fragmentCollection.fragmentCollectionKey = ?";
	private static final String _FINDER_COLUMN_G_FCK_FRAGMENTCOLLECTIONKEY_3 = "(fragmentCollection.fragmentCollectionKey IS NULL OR fragmentCollection.fragmentCollectionKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED,
			FragmentCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN = new FinderPath(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	@Override
	public List<FragmentCollection> findByG_LikeN(long groupId, String name,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentCollection fragmentCollection : list) {
					if ((groupId != fragmentCollection.getGroupId()) ||
							!StringUtil.wildcardMatches(
								fragmentCollection.getName(), name, '_', '%',
								'\\', false)) {
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

			query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
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
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
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
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
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
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_LikeN_First(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_LikeN_First(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		List<FragmentCollection> list = findByG_LikeN(groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection findByG_LikeN_Last(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (fragmentCollection != null) {
			return fragmentCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	@Override
	public FragmentCollection fetchByG_LikeN_Last(long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<FragmentCollection> list = findByG_LikeN(groupId, name, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] findByG_LikeN_PrevAndNext(
		long fragmentCollectionId, long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session, fragmentCollection,
					groupId, name, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = getByG_LikeN_PrevAndNext(session, fragmentCollection,
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

	protected FragmentCollection getByG_LikeN_PrevAndNext(Session session,
		FragmentCollection fragmentCollection, long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator,
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

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
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
			query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByG_LikeN(long groupId,
		String name) {
		return filterFindByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByG_LikeN(long groupId,
		String name, int start, int end) {
		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections that the user has permission to view
	 */
	@Override
	public List<FragmentCollection> filterFindByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(FragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, FragmentCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, FragmentCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<FragmentCollection>)QueryUtil.list(q, getDialect(),
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
	 * Returns the fragment collections before and after the current fragment collection in the ordered set of fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection[] filterFindByG_LikeN_PrevAndNext(
		long fragmentCollectionId, long groupId, String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws NoSuchCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(fragmentCollectionId, groupId,
				name, orderByComparator);
		}

		FragmentCollection fragmentCollection = findByPrimaryKey(fragmentCollectionId);

		Session session = null;

		try {
			session = openSession();

			FragmentCollection[] array = new FragmentCollectionImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(session,
					fragmentCollection, groupId, name, orderByComparator, true);

			array[1] = fragmentCollection;

			array[2] = filterGetByG_LikeN_PrevAndNext(session,
					fragmentCollection, groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentCollection filterGetByG_LikeN_PrevAndNext(
		Session session, FragmentCollection fragmentCollection, long groupId,
		String name, OrderByComparator<FragmentCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(FragmentCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(FragmentCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, FragmentCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, FragmentCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(fragmentCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FragmentCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (FragmentCollection fragmentCollection : findByG_LikeN(groupId,
				name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching fragment collections
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
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
	 * Returns the number of fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching fragment collections that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_FRAGMENTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FragmentCollection.class.getName(),
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "fragmentCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_1 = "fragmentCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(fragmentCollection.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(fragmentCollection.name IS NULL OR fragmentCollection.name LIKE '')";

	public FragmentCollectionPersistenceImpl() {
		setModelClass(FragmentCollection.class);
	}

	/**
	 * Caches the fragment collection in the entity cache if it is enabled.
	 *
	 * @param fragmentCollection the fragment collection
	 */
	@Override
	public void cacheResult(FragmentCollection fragmentCollection) {
		entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey(),
			fragmentCollection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_FCK,
			new Object[] {
				fragmentCollection.getGroupId(),
				fragmentCollection.getFragmentCollectionKey()
			}, fragmentCollection);

		fragmentCollection.resetOriginalValues();
	}

	/**
	 * Caches the fragment collections in the entity cache if it is enabled.
	 *
	 * @param fragmentCollections the fragment collections
	 */
	@Override
	public void cacheResult(List<FragmentCollection> fragmentCollections) {
		for (FragmentCollection fragmentCollection : fragmentCollections) {
			if (entityCache.getResult(
						FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						FragmentCollectionImpl.class,
						fragmentCollection.getPrimaryKey()) == null) {
				cacheResult(fragmentCollection);
			}
			else {
				fragmentCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentCollection fragmentCollection) {
		entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FragmentCollectionModelImpl)fragmentCollection,
			true);
	}

	@Override
	public void clearCache(List<FragmentCollection> fragmentCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey());

			clearUniqueFindersCache((FragmentCollectionModelImpl)fragmentCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentCollectionModelImpl fragmentCollectionModelImpl) {
		Object[] args = new Object[] {
				fragmentCollectionModelImpl.getGroupId(),
				fragmentCollectionModelImpl.getFragmentCollectionKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_FCK, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_FCK, args,
			fragmentCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FragmentCollectionModelImpl fragmentCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getGroupId(),
					fragmentCollectionModelImpl.getFragmentCollectionKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_FCK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_FCK, args);
		}

		if ((fragmentCollectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_FCK.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getOriginalGroupId(),
					fragmentCollectionModelImpl.getOriginalFragmentCollectionKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_FCK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_FCK, args);
		}
	}

	/**
	 * Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	 *
	 * @param fragmentCollectionId the primary key for the new fragment collection
	 * @return the new fragment collection
	 */
	@Override
	public FragmentCollection create(long fragmentCollectionId) {
		FragmentCollection fragmentCollection = new FragmentCollectionImpl();

		fragmentCollection.setNew(true);
		fragmentCollection.setPrimaryKey(fragmentCollectionId);

		fragmentCollection.setCompanyId(companyProvider.getCompanyId());

		return fragmentCollection;
	}

	/**
	 * Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection that was removed
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection remove(long fragmentCollectionId)
		throws NoSuchCollectionException {
		return remove((Serializable)fragmentCollectionId);
	}

	/**
	 * Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection that was removed
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {
		Session session = null;

		try {
			session = openSession();

			FragmentCollection fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
					primaryKey);

			if (fragmentCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(fragmentCollection);
		}
		catch (NoSuchCollectionException nsee) {
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
	protected FragmentCollection removeImpl(
		FragmentCollection fragmentCollection) {
		fragmentCollection = toUnwrappedModel(fragmentCollection);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentCollection)) {
				fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
						fragmentCollection.getPrimaryKeyObj());
			}

			if (fragmentCollection != null) {
				session.delete(fragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (fragmentCollection != null) {
			clearCache(fragmentCollection);
		}

		return fragmentCollection;
	}

	@Override
	public FragmentCollection updateImpl(FragmentCollection fragmentCollection) {
		fragmentCollection = toUnwrappedModel(fragmentCollection);

		boolean isNew = fragmentCollection.isNew();

		FragmentCollectionModelImpl fragmentCollectionModelImpl = (FragmentCollectionModelImpl)fragmentCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (fragmentCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentCollection.setCreateDate(now);
			}
			else {
				fragmentCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!fragmentCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentCollection.setModifiedDate(now);
			}
			else {
				fragmentCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (fragmentCollection.isNew()) {
				session.save(fragmentCollection);

				fragmentCollection.setNew(false);
			}
			else {
				fragmentCollection = (FragmentCollection)session.merge(fragmentCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FragmentCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					fragmentCollectionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((fragmentCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						fragmentCollectionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { fragmentCollectionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
			FragmentCollectionImpl.class, fragmentCollection.getPrimaryKey(),
			fragmentCollection, false);

		clearUniqueFindersCache(fragmentCollectionModelImpl, false);
		cacheUniqueFindersCache(fragmentCollectionModelImpl);

		fragmentCollection.resetOriginalValues();

		return fragmentCollection;
	}

	protected FragmentCollection toUnwrappedModel(
		FragmentCollection fragmentCollection) {
		if (fragmentCollection instanceof FragmentCollectionImpl) {
			return fragmentCollection;
		}

		FragmentCollectionImpl fragmentCollectionImpl = new FragmentCollectionImpl();

		fragmentCollectionImpl.setNew(fragmentCollection.isNew());
		fragmentCollectionImpl.setPrimaryKey(fragmentCollection.getPrimaryKey());

		fragmentCollectionImpl.setFragmentCollectionId(fragmentCollection.getFragmentCollectionId());
		fragmentCollectionImpl.setGroupId(fragmentCollection.getGroupId());
		fragmentCollectionImpl.setCompanyId(fragmentCollection.getCompanyId());
		fragmentCollectionImpl.setUserId(fragmentCollection.getUserId());
		fragmentCollectionImpl.setUserName(fragmentCollection.getUserName());
		fragmentCollectionImpl.setCreateDate(fragmentCollection.getCreateDate());
		fragmentCollectionImpl.setModifiedDate(fragmentCollection.getModifiedDate());
		fragmentCollectionImpl.setFragmentCollectionKey(fragmentCollection.getFragmentCollectionKey());
		fragmentCollectionImpl.setName(fragmentCollection.getName());
		fragmentCollectionImpl.setDescription(fragmentCollection.getDescription());

		return fragmentCollectionImpl;
	}

	/**
	 * Returns the fragment collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {
		FragmentCollection fragmentCollection = fetchByPrimaryKey(primaryKey);

		if (fragmentCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection findByPrimaryKey(long fragmentCollectionId)
		throws NoSuchCollectionException {
		return findByPrimaryKey((Serializable)fragmentCollectionId);
	}

	/**
	 * Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment collection
	 * @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
				FragmentCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FragmentCollection fragmentCollection = (FragmentCollection)serializable;

		if (fragmentCollection == null) {
			Session session = null;

			try {
				session = openSession();

				fragmentCollection = (FragmentCollection)session.get(FragmentCollectionImpl.class,
						primaryKey);

				if (fragmentCollection != null) {
					cacheResult(fragmentCollection);
				}
				else {
					entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
						FragmentCollectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return fragmentCollection;
	}

	/**
	 * Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	 */
	@Override
	public FragmentCollection fetchByPrimaryKey(long fragmentCollectionId) {
		return fetchByPrimaryKey((Serializable)fragmentCollectionId);
	}

	@Override
	public Map<Serializable, FragmentCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentCollection> map = new HashMap<Serializable, FragmentCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentCollection fragmentCollection = fetchByPrimaryKey(primaryKey);

			if (fragmentCollection != null) {
				map.put(primaryKey, fragmentCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FragmentCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRAGMENTCOLLECTION_WHERE_PKS_IN);

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

			for (FragmentCollection fragmentCollection : (List<FragmentCollection>)q.list()) {
				map.put(fragmentCollection.getPrimaryKeyObj(),
					fragmentCollection);

				cacheResult(fragmentCollection);

				uncachedPrimaryKeys.remove(fragmentCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FragmentCollectionModelImpl.ENTITY_CACHE_ENABLED,
					FragmentCollectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the fragment collections.
	 *
	 * @return the fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of fragment collections
	 */
	@Override
	public List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
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

		List<FragmentCollection> list = null;

		if (retrieveFromCache) {
			list = (List<FragmentCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRAGMENTCOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTCOLLECTION;

				if (pagination) {
					sql = sql.concat(FragmentCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FragmentCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FragmentCollection>)QueryUtil.list(q,
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
	 * Removes all the fragment collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentCollection fragmentCollection : findAll()) {
			remove(fragmentCollection);
		}
	}

	/**
	 * Returns the number of fragment collections.
	 *
	 * @return the number of fragment collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRAGMENTCOLLECTION);

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
		return FragmentCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fragment collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FragmentCollectionImpl.class.getName());
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
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection";
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION_WHERE_PKS_IN = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection WHERE fragmentCollectionId IN (";
	private static final String _SQL_SELECT_FRAGMENTCOLLECTION_WHERE = "SELECT fragmentCollection FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _SQL_COUNT_FRAGMENTCOLLECTION = "SELECT COUNT(fragmentCollection) FROM FragmentCollection fragmentCollection";
	private static final String _SQL_COUNT_FRAGMENTCOLLECTION_WHERE = "SELECT COUNT(fragmentCollection) FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "fragmentCollection.fragmentCollectionId";
	private static final String _FILTER_SQL_SELECT_FRAGMENTCOLLECTION_WHERE = "SELECT DISTINCT {fragmentCollection.*} FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {FragmentCollection.*} FROM (SELECT DISTINCT fragmentCollection.fragmentCollectionId FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_FRAGMENTCOLLECTION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN FragmentCollection ON TEMP_TABLE.fragmentCollectionId = FragmentCollection.fragmentCollectionId";
	private static final String _FILTER_SQL_COUNT_FRAGMENTCOLLECTION_WHERE = "SELECT COUNT(DISTINCT fragmentCollection.fragmentCollectionId) AS COUNT_VALUE FROM FragmentCollection fragmentCollection WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "fragmentCollection";
	private static final String _FILTER_ENTITY_TABLE = "FragmentCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentCollection.";
	private static final String _ORDER_BY_ENTITY_TABLE = "FragmentCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FragmentCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FragmentCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FragmentCollectionPersistenceImpl.class);
}