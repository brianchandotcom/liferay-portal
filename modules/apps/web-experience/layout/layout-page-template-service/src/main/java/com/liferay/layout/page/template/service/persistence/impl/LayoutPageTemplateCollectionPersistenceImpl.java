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

package com.liferay.layout.page.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateCollectionImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateCollectionModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionPersistence;

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
import com.liferay.portal.kernel.util.StringUtil;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout page template collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionPersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCollectionPersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateCollection>
	implements LayoutPageTemplateCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateCollectionUtil} to access the layout page template collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page template collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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

		List<LayoutPageTemplateCollection> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateCollection layoutPageTemplateCollection : list) {
					if ((groupId != layoutPageTemplateCollection.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the first layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		List<LayoutPageTemplateCollection> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateCollection> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] findByGroupId_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, orderByComparator,
					true);

			array[1] = layoutPageTemplateCollection;

			array[2] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, orderByComparator,
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

	protected LayoutPageTemplateCollection getByGroupId_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
			query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(layoutPageTemplateCollectionId,
				groupId, orderByComparator);
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, orderByComparator,
					true);

			array[1] = layoutPageTemplateCollection;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, orderByComparator,
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

	protected LayoutPageTemplateCollection filterGetByGroupId_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS,
				LayoutPageTemplateCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE,
				LayoutPageTemplateCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateCollection layoutPageTemplateCollection : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateCollection);
		}
	}

	/**
	 * Returns the number of layout page template collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
	 * Returns the number of layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template collections that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplateCollection.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			LayoutPageTemplateCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateCollectionException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByG_N(long groupId, String name)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByG_N(groupId,
				name);

		if (layoutPageTemplateCollection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPageTemplateCollectionException(msg.toString());
		}

		return layoutPageTemplateCollection;
	}

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateCollection) {
			LayoutPageTemplateCollection layoutPageTemplateCollection = (LayoutPageTemplateCollection)result;

			if ((groupId != layoutPageTemplateCollection.getGroupId()) ||
					!Objects.equals(name, layoutPageTemplateCollection.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
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

				List<LayoutPageTemplateCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					LayoutPageTemplateCollection layoutPageTemplateCollection = list.get(0);

					result = layoutPageTemplateCollection;

					cacheResult(layoutPageTemplateCollection);

					if ((layoutPageTemplateCollection.getGroupId() != groupId) ||
							(layoutPageTemplateCollection.getName() == null) ||
							!layoutPageTemplateCollection.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, layoutPageTemplateCollection);
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
			return (LayoutPageTemplateCollection)result;
		}
	}

	/**
	 * Removes the layout page template collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the layout page template collection that was removed
	 */
	@Override
	public LayoutPageTemplateCollection removeByG_N(long groupId, String name)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = findByG_N(groupId,
				name);

		return remove(layoutPageTemplateCollection);
	}

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "layoutPageTemplateCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "layoutPageTemplateCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "layoutPageTemplateCollection.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(layoutPageTemplateCollection.name IS NULL OR layoutPageTemplateCollection.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_LikeN(long groupId,
		String name) {
		return findByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_LikeN(long groupId,
		String name, int start, int end) {
		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		return findByG_LikeN(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_LikeN(long groupId,
		String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<LayoutPageTemplateCollection> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateCollection layoutPageTemplateCollection : list) {
					if ((groupId != layoutPageTemplateCollection.getGroupId()) ||
							!StringUtil.wildcardMatches(
								layoutPageTemplateCollection.getName(), name,
								'_', '%', '\\', false)) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByG_LikeN_First(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByG_LikeN_First(groupId,
				name, orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_LikeN_First(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		List<LayoutPageTemplateCollection> list = findByG_LikeN(groupId, name,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByG_LikeN_Last(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByG_LikeN_Last(groupId,
				name, orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_LikeN_Last(long groupId,
		String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateCollection> list = findByG_LikeN(groupId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] findByG_LikeN_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, name,
					orderByComparator, true);

			array[1] = layoutPageTemplateCollection;

			array[2] = getByG_LikeN_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, name,
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

	protected LayoutPageTemplateCollection getByG_LikeN_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
			query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name) {
		return filterFindByG_LikeN(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name, int start, int end) {
		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] filterFindByG_LikeN_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(layoutPageTemplateCollectionId,
				groupId, name, orderByComparator);
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, name,
					orderByComparator, true);

			array[1] = layoutPageTemplateCollection;

			array[2] = filterGetByG_LikeN_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, name,
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

	protected LayoutPageTemplateCollection filterGetByG_LikeN_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS,
				LayoutPageTemplateCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE,
				LayoutPageTemplateCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (LayoutPageTemplateCollection layoutPageTemplateCollection : findByG_LikeN(
				groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateCollection);
		}
	}

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
	 * Returns the number of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

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
				LayoutPageTemplateCollection.class.getName(),
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 = "layoutPageTemplateCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_1 = "layoutPageTemplateCollection.name IS NULL";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 = "lower(layoutPageTemplateCollection.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 = "(layoutPageTemplateCollection.name IS NULL OR layoutPageTemplateCollection.name LIKE '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LayoutPageTemplateCollectionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateCollectionModelImpl.TYPE_COLUMN_BITMASK |
			LayoutPageTemplateCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the layout page template collections where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout page template collections where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_T(long groupId, int type,
		int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateCollection> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateCollection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateCollection layoutPageTemplateCollection : list) {
					if ((groupId != layoutPageTemplateCollection.getGroupId()) ||
							(type != layoutPageTemplateCollection.getType())) {
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

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByG_T_First(long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByG_T_First(groupId,
				type, orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_T_First(long groupId,
		int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		List<LayoutPageTemplateCollection> list = findByG_T(groupId, type, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByG_T_Last(long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByG_T_Last(groupId,
				type, orderByComparator);

		if (layoutPageTemplateCollection != null) {
			return layoutPageTemplateCollection;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateCollectionException(msg.toString());
	}

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByG_T_Last(long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateCollection> list = findByG_T(groupId, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] findByG_T_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = getByG_T_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, type,
					orderByComparator, true);

			array[1] = layoutPageTemplateCollection;

			array[2] = getByG_T_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, type,
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

	protected LayoutPageTemplateCollection getByG_T_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_T(long groupId,
		int type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_T(long groupId,
		int type, int start, int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateCollection> filterFindByG_T(long groupId,
		int type, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateCollectionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateCollectionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			return (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection[] filterFindByG_T_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(layoutPageTemplateCollectionId,
				groupId, type, orderByComparator);
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection = findByPrimaryKey(layoutPageTemplateCollectionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection[] array = new LayoutPageTemplateCollectionImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, type,
					orderByComparator, true);

			array[1] = layoutPageTemplateCollection;

			array[2] = filterGetByG_T_PrevAndNext(session,
					layoutPageTemplateCollection, groupId, type,
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

	protected LayoutPageTemplateCollection filterGetByG_T_PrevAndNext(
		Session session,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateCollectionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS,
				LayoutPageTemplateCollectionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE,
				LayoutPageTemplateCollectionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateCollection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template collections where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (LayoutPageTemplateCollection layoutPageTemplateCollection : findByG_T(
				groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateCollection);
		}
	}

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template collections
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

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
	 * Returns the number of layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template collections that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateCollection.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "layoutPageTemplateCollection.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "layoutPageTemplateCollection.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "layoutPageTemplateCollection.type_ = ?";

	public LayoutPageTemplateCollectionPersistenceImpl() {
		setModelClass(LayoutPageTemplateCollection.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the layout page template collection in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateCollection the layout page template collection
	 */
	@Override
	public void cacheResult(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		entityCache.putResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			layoutPageTemplateCollection.getPrimaryKey(),
			layoutPageTemplateCollection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollection.getName()
			}, layoutPageTemplateCollection);

		layoutPageTemplateCollection.resetOriginalValues();
	}

	/**
	 * Caches the layout page template collections in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateCollections the layout page template collections
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateCollection> layoutPageTemplateCollections) {
		for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
			if (entityCache.getResult(
						LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateCollectionImpl.class,
						layoutPageTemplateCollection.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplateCollection);
			}
			else {
				layoutPageTemplateCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		entityCache.removeResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			layoutPageTemplateCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutPageTemplateCollectionModelImpl)layoutPageTemplateCollection,
			true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateCollection> layoutPageTemplateCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
			entityCache.removeResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateCollectionImpl.class,
				layoutPageTemplateCollection.getPrimaryKey());

			clearUniqueFindersCache((LayoutPageTemplateCollectionModelImpl)layoutPageTemplateCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateCollectionModelImpl layoutPageTemplateCollectionModelImpl) {
		Object[] args = new Object[] {
				layoutPageTemplateCollectionModelImpl.getGroupId(),
				layoutPageTemplateCollectionModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			layoutPageTemplateCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateCollectionModelImpl layoutPageTemplateCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateCollectionModelImpl.getGroupId(),
					layoutPageTemplateCollectionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((layoutPageTemplateCollectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateCollectionModelImpl.getOriginalGroupId(),
					layoutPageTemplateCollectionModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new layout page template collection with the primary key. Does not add the layout page template collection to the database.
	 *
	 * @param layoutPageTemplateCollectionId the primary key for the new layout page template collection
	 * @return the new layout page template collection
	 */
	@Override
	public LayoutPageTemplateCollection create(
		long layoutPageTemplateCollectionId) {
		LayoutPageTemplateCollection layoutPageTemplateCollection = new LayoutPageTemplateCollectionImpl();

		layoutPageTemplateCollection.setNew(true);
		layoutPageTemplateCollection.setPrimaryKey(layoutPageTemplateCollectionId);

		layoutPageTemplateCollection.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateCollection;
	}

	/**
	 * Removes the layout page template collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection that was removed
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection remove(
		long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException {
		return remove((Serializable)layoutPageTemplateCollectionId);
	}

	/**
	 * Removes the layout page template collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template collection
	 * @return the layout page template collection that was removed
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection remove(Serializable primaryKey)
		throws NoSuchPageTemplateCollectionException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateCollection layoutPageTemplateCollection = (LayoutPageTemplateCollection)session.get(LayoutPageTemplateCollectionImpl.class,
					primaryKey);

			if (layoutPageTemplateCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplateCollection);
		}
		catch (NoSuchPageTemplateCollectionException nsee) {
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
	protected LayoutPageTemplateCollection removeImpl(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		layoutPageTemplateCollection = toUnwrappedModel(layoutPageTemplateCollection);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateCollection)) {
				layoutPageTemplateCollection = (LayoutPageTemplateCollection)session.get(LayoutPageTemplateCollectionImpl.class,
						layoutPageTemplateCollection.getPrimaryKeyObj());
			}

			if (layoutPageTemplateCollection != null) {
				session.delete(layoutPageTemplateCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateCollection != null) {
			clearCache(layoutPageTemplateCollection);
		}

		return layoutPageTemplateCollection;
	}

	@Override
	public LayoutPageTemplateCollection updateImpl(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		layoutPageTemplateCollection = toUnwrappedModel(layoutPageTemplateCollection);

		boolean isNew = layoutPageTemplateCollection.isNew();

		LayoutPageTemplateCollectionModelImpl layoutPageTemplateCollectionModelImpl =
			(LayoutPageTemplateCollectionModelImpl)layoutPageTemplateCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateCollection.setCreateDate(now);
			}
			else {
				layoutPageTemplateCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateCollection.setModifiedDate(now);
			}
			else {
				layoutPageTemplateCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateCollection.isNew()) {
				session.save(layoutPageTemplateCollection);

				layoutPageTemplateCollection.setNew(false);
			}
			else {
				layoutPageTemplateCollection = (LayoutPageTemplateCollection)session.merge(layoutPageTemplateCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateCollectionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					layoutPageTemplateCollectionModelImpl.getGroupId(),
					layoutPageTemplateCollectionModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateCollectionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						layoutPageTemplateCollectionModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutPageTemplateCollectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateCollectionModelImpl.getOriginalGroupId(),
						layoutPageTemplateCollectionModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						layoutPageTemplateCollectionModelImpl.getGroupId(),
						layoutPageTemplateCollectionModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateCollectionImpl.class,
			layoutPageTemplateCollection.getPrimaryKey(),
			layoutPageTemplateCollection, false);

		clearUniqueFindersCache(layoutPageTemplateCollectionModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateCollectionModelImpl);

		layoutPageTemplateCollection.resetOriginalValues();

		return layoutPageTemplateCollection;
	}

	protected LayoutPageTemplateCollection toUnwrappedModel(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		if (layoutPageTemplateCollection instanceof LayoutPageTemplateCollectionImpl) {
			return layoutPageTemplateCollection;
		}

		LayoutPageTemplateCollectionImpl layoutPageTemplateCollectionImpl = new LayoutPageTemplateCollectionImpl();

		layoutPageTemplateCollectionImpl.setNew(layoutPageTemplateCollection.isNew());
		layoutPageTemplateCollectionImpl.setPrimaryKey(layoutPageTemplateCollection.getPrimaryKey());

		layoutPageTemplateCollectionImpl.setLayoutPageTemplateCollectionId(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());
		layoutPageTemplateCollectionImpl.setGroupId(layoutPageTemplateCollection.getGroupId());
		layoutPageTemplateCollectionImpl.setCompanyId(layoutPageTemplateCollection.getCompanyId());
		layoutPageTemplateCollectionImpl.setUserId(layoutPageTemplateCollection.getUserId());
		layoutPageTemplateCollectionImpl.setUserName(layoutPageTemplateCollection.getUserName());
		layoutPageTemplateCollectionImpl.setCreateDate(layoutPageTemplateCollection.getCreateDate());
		layoutPageTemplateCollectionImpl.setModifiedDate(layoutPageTemplateCollection.getModifiedDate());
		layoutPageTemplateCollectionImpl.setName(layoutPageTemplateCollection.getName());
		layoutPageTemplateCollectionImpl.setDescription(layoutPageTemplateCollection.getDescription());
		layoutPageTemplateCollectionImpl.setType(layoutPageTemplateCollection.getType());

		return layoutPageTemplateCollectionImpl;
	}

	/**
	 * Returns the layout page template collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template collection
	 * @return the layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByPrimaryKey(
		Serializable primaryKey) throws NoSuchPageTemplateCollectionException {
		LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplateCollection;
	}

	/**
	 * Returns the layout page template collection with the primary key or throws a {@link NoSuchPageTemplateCollectionException} if it could not be found.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection findByPrimaryKey(
		long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException {
		return findByPrimaryKey((Serializable)layoutPageTemplateCollectionId);
	}

	/**
	 * Returns the layout page template collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template collection
	 * @return the layout page template collection, or <code>null</code> if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection = (LayoutPageTemplateCollection)serializable;

		if (layoutPageTemplateCollection == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPageTemplateCollection = (LayoutPageTemplateCollection)session.get(LayoutPageTemplateCollectionImpl.class,
						primaryKey);

				if (layoutPageTemplateCollection != null) {
					cacheResult(layoutPageTemplateCollection);
				}
				else {
					entityCache.putResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateCollectionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPageTemplateCollection;
	}

	/**
	 * Returns the layout page template collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection, or <code>null</code> if a layout page template collection with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateCollection fetchByPrimaryKey(
		long layoutPageTemplateCollectionId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateCollectionId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplateCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplateCollection> map = new HashMap<Serializable, LayoutPageTemplateCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplateCollection layoutPageTemplateCollection = fetchByPrimaryKey(primaryKey);

			if (layoutPageTemplateCollection != null) {
				map.put(primaryKey, layoutPageTemplateCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(LayoutPageTemplateCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE_PKS_IN);

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

			for (LayoutPageTemplateCollection layoutPageTemplateCollection : (List<LayoutPageTemplateCollection>)q.list()) {
				map.put(layoutPageTemplateCollection.getPrimaryKeyObj(),
					layoutPageTemplateCollection);

				cacheResult(layoutPageTemplateCollection);

				uncachedPrimaryKeys.remove(layoutPageTemplateCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPageTemplateCollectionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateCollectionImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the layout page template collections.
	 *
	 * @return the layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template collections
	 */
	@Override
	public List<LayoutPageTemplateCollection> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
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

		List<LayoutPageTemplateCollection> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateCollection>)QueryUtil.list(q,
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
	 * Removes all the layout page template collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateCollection layoutPageTemplateCollection : findAll()) {
			remove(layoutPageTemplateCollection);
		}
	}

	/**
	 * Returns the number of layout page template collections.
	 *
	 * @return the number of layout page template collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION);

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
		return LayoutPageTemplateCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateCollectionImpl.class.getName());
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
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION = "SELECT layoutPageTemplateCollection FROM LayoutPageTemplateCollection layoutPageTemplateCollection";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE_PKS_IN =
		"SELECT layoutPageTemplateCollection FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE layoutPageTemplateCollectionId IN (";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE = "SELECT layoutPageTemplateCollection FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION = "SELECT COUNT(layoutPageTemplateCollection) FROM LayoutPageTemplateCollection layoutPageTemplateCollection";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE = "SELECT COUNT(layoutPageTemplateCollection) FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutPageTemplateCollection.layoutPageTemplateCollectionId";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_WHERE =
		"SELECT DISTINCT {layoutPageTemplateCollection.*} FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutPageTemplateCollection.*} FROM (SELECT DISTINCT layoutPageTemplateCollection.layoutPageTemplateCollectionId FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATECOLLECTION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutPageTemplateCollection ON TEMP_TABLE.layoutPageTemplateCollectionId = LayoutPageTemplateCollection.layoutPageTemplateCollectionId";
	private static final String _FILTER_SQL_COUNT_LAYOUTPAGETEMPLATECOLLECTION_WHERE =
		"SELECT COUNT(DISTINCT layoutPageTemplateCollection.layoutPageTemplateCollectionId) AS COUNT_VALUE FROM LayoutPageTemplateCollection layoutPageTemplateCollection WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutPageTemplateCollection";
	private static final String _FILTER_ENTITY_TABLE = "LayoutPageTemplateCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplateCollection.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutPageTemplateCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplateCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplateCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplateCollectionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}