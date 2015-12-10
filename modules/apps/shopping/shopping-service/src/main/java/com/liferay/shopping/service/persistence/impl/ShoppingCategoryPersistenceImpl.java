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

package com.liferay.shopping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.exception.NoSuchCategoryException;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.shopping.model.impl.ShoppingCategoryModelImpl;
import com.liferay.shopping.service.persistence.ShoppingCategoryPersistence;

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
 * The persistence implementation for the shopping category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryPersistence
 * @see com.liferay.shopping.service.persistence.ShoppingCategoryUtil
 * @generated
 */
@ProviderType
public class ShoppingCategoryPersistenceImpl extends BasePersistenceImpl<ShoppingCategory>
	implements ShoppingCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingCategoryUtil} to access the shopping category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ShoppingCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			ShoppingCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the shopping categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @return the range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCategory> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCategory> orderByComparator,
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

		List<ShoppingCategory> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCategory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingCategory shoppingCategory : list) {
					if ((groupId != shoppingCategory.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
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
	 * Returns the first shopping category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory findByGroupId_First(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByGroupId_First(groupId,
				orderByComparator);

		if (shoppingCategory != null) {
			return shoppingCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first shopping category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByGroupId_First(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		List<ShoppingCategory> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory findByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (shoppingCategory != null) {
			return shoppingCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last shopping category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ShoppingCategory> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current shopping category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, shoppingCategory,
					groupId, orderByComparator, true);

			array[1] = shoppingCategory;

			array[2] = getByGroupId_PrevAndNext(session, shoppingCategory,
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

	protected ShoppingCategory getByGroupId_PrevAndNext(Session session,
		ShoppingCategory shoppingCategory, long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE);

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
			query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping categories that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @return the range of matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCategory> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<ShoppingCategory>)QueryUtil.list(q, getDialect(),
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
	 * Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current shopping category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory[] filterFindByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(categoryId, groupId,
				orderByComparator);
		}

		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					shoppingCategory, groupId, orderByComparator, true);

			array[1] = shoppingCategory;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					shoppingCategory, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingCategory filterGetByGroupId_PrevAndNext(Session session,
		ShoppingCategory shoppingCategory, long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ShoppingCategory shoppingCategory : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingCategory);
		}
	}

	/**
	 * Returns the number of shopping categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping categories
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGCATEGORY_WHERE);

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
	 * Returns the number of shopping categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping categories that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SHOPPINGCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "shoppingCategory.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			ShoppingCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			ShoppingCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByG_P(long groupId, long parentCategoryId) {
		return findByG_P(groupId, parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @return the range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end) {
		return findByG_P(groupId, parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return findByG_P(groupId, parentCategoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping categories
	 */
	@Override
	public List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, parentCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, parentCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<ShoppingCategory> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCategory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingCategory shoppingCategory : list) {
					if ((groupId != shoppingCategory.getGroupId()) ||
							(parentCategoryId != shoppingCategory.getParentCategoryId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				if (!pagination) {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
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
	 * Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory findByG_P_First(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByG_P_First(groupId,
				parentCategoryId, orderByComparator);

		if (shoppingCategory != null) {
			return shoppingCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByG_P_First(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		List<ShoppingCategory> list = findByG_P(groupId, parentCategoryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory findByG_P_Last(long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByG_P_Last(groupId,
				parentCategoryId, orderByComparator);

		if (shoppingCategory != null) {
			return shoppingCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByG_P_Last(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		int count = countByG_P(groupId, parentCategoryId);

		if (count == 0) {
			return null;
		}

		List<ShoppingCategory> list = findByG_P(groupId, parentCategoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param categoryId the primary key of the current shopping category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory[] findByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = getByG_P_PrevAndNext(session, shoppingCategory, groupId,
					parentCategoryId, orderByComparator, true);

			array[1] = shoppingCategory;

			array[2] = getByG_P_PrevAndNext(session, shoppingCategory, groupId,
					parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingCategory getByG_P_PrevAndNext(Session session,
		ShoppingCategory shoppingCategory, long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

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
			query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId) {
		return filterFindByG_P(groupId, parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @return the range of matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end) {
		return filterFindByG_P(groupId, parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping categories that the user has permission to view
	 */
	@Override
	public List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentCategoryId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			return (List<ShoppingCategory>)QueryUtil.list(q, getDialect(),
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
	 * Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param categoryId the primary key of the current shopping category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory[] filterFindByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(categoryId, groupId, parentCategoryId,
				orderByComparator);
		}

		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, shoppingCategory,
					groupId, parentCategoryId, orderByComparator, true);

			array[1] = shoppingCategory;

			array[2] = filterGetByG_P_PrevAndNext(session, shoppingCategory,
					groupId, parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingCategory filterGetByG_P_PrevAndNext(Session session,
		ShoppingCategory shoppingCategory, long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 */
	@Override
	public void removeByG_P(long groupId, long parentCategoryId) {
		for (ShoppingCategory shoppingCategory : findByG_P(groupId,
				parentCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingCategory);
		}
	}

	/**
	 * Returns the number of shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching shopping categories
	 */
	@Override
	public int countByG_P(long groupId, long parentCategoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P;

		Object[] finderArgs = new Object[] { groupId, parentCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

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
	 * Returns the number of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching shopping categories that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, long parentCategoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentCategoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SHOPPINGCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "shoppingCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTCATEGORYID_2 = "shoppingCategory.parentCategoryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			ShoppingCategoryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ShoppingCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the shopping category where groupId = &#63; and name = &#63; or throws a {@link com.liferay.shopping.NoSuchCategoryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory findByG_N(long groupId, String name)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByG_N(groupId, name);

		if (shoppingCategory == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryException(msg.toString());
		}

		return shoppingCategory;
	}

	/**
	 * Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	 */
	@Override
	public ShoppingCategory fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof ShoppingCategory) {
			ShoppingCategory shoppingCategory = (ShoppingCategory)result;

			if ((groupId != shoppingCategory.getGroupId()) ||
					!Validator.equals(name, shoppingCategory.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE);

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

				List<ShoppingCategory> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ShoppingCategoryPersistenceImpl.fetchByG_N(long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ShoppingCategory shoppingCategory = list.get(0);

					result = shoppingCategory;

					cacheResult(shoppingCategory);

					if ((shoppingCategory.getGroupId() != groupId) ||
							(shoppingCategory.getName() == null) ||
							!shoppingCategory.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, shoppingCategory);
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
			return (ShoppingCategory)result;
		}
	}

	/**
	 * Removes the shopping category where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the shopping category that was removed
	 */
	@Override
	public ShoppingCategory removeByG_N(long groupId, String name)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = findByG_N(groupId, name);

		return remove(shoppingCategory);
	}

	/**
	 * Returns the number of shopping categories where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching shopping categories
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGCATEGORY_WHERE);

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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "shoppingCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "shoppingCategory.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "shoppingCategory.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(shoppingCategory.name IS NULL OR shoppingCategory.name = '')";

	public ShoppingCategoryPersistenceImpl() {
		setModelClass(ShoppingCategory.class);
	}

	/**
	 * Caches the shopping category in the entity cache if it is enabled.
	 *
	 * @param shoppingCategory the shopping category
	 */
	@Override
	public void cacheResult(ShoppingCategory shoppingCategory) {
		entityCache.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey(),
			shoppingCategory);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				shoppingCategory.getGroupId(), shoppingCategory.getName()
			}, shoppingCategory);

		shoppingCategory.resetOriginalValues();
	}

	/**
	 * Caches the shopping categories in the entity cache if it is enabled.
	 *
	 * @param shoppingCategories the shopping categories
	 */
	@Override
	public void cacheResult(List<ShoppingCategory> shoppingCategories) {
		for (ShoppingCategory shoppingCategory : shoppingCategories) {
			if (entityCache.getResult(
						ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCategoryImpl.class,
						shoppingCategory.getPrimaryKey()) == null) {
				cacheResult(shoppingCategory);
			}
			else {
				shoppingCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping categories.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingCategoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping category.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingCategory shoppingCategory) {
		entityCache.removeResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ShoppingCategoryModelImpl)shoppingCategory);
	}

	@Override
	public void clearCache(List<ShoppingCategory> shoppingCategories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingCategory shoppingCategory : shoppingCategories) {
			entityCache.removeResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey());

			clearUniqueFindersCache((ShoppingCategoryModelImpl)shoppingCategory);
		}
	}

	protected void cacheUniqueFindersCache(
		ShoppingCategoryModelImpl shoppingCategoryModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					shoppingCategoryModelImpl.getGroupId(),
					shoppingCategoryModelImpl.getName()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
				shoppingCategoryModelImpl);
		}
		else {
			if ((shoppingCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCategoryModelImpl.getGroupId(),
						shoppingCategoryModelImpl.getName()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
					shoppingCategoryModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ShoppingCategoryModelImpl shoppingCategoryModelImpl) {
		Object[] args = new Object[] {
				shoppingCategoryModelImpl.getGroupId(),
				shoppingCategoryModelImpl.getName()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);

		if ((shoppingCategoryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					shoppingCategoryModelImpl.getOriginalGroupId(),
					shoppingCategoryModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new shopping category with the primary key. Does not add the shopping category to the database.
	 *
	 * @param categoryId the primary key for the new shopping category
	 * @return the new shopping category
	 */
	@Override
	public ShoppingCategory create(long categoryId) {
		ShoppingCategory shoppingCategory = new ShoppingCategoryImpl();

		shoppingCategory.setNew(true);
		shoppingCategory.setPrimaryKey(categoryId);

		shoppingCategory.setCompanyId(companyProvider.getCompanyId());

		return shoppingCategory;
	}

	/**
	 * Removes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryId the primary key of the shopping category
	 * @return the shopping category that was removed
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory remove(long categoryId)
		throws NoSuchCategoryException {
		return remove((Serializable)categoryId);
	}

	/**
	 * Removes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping category
	 * @return the shopping category that was removed
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory remove(Serializable primaryKey)
		throws NoSuchCategoryException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCategory shoppingCategory = (ShoppingCategory)session.get(ShoppingCategoryImpl.class,
					primaryKey);

			if (shoppingCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingCategory);
		}
		catch (NoSuchCategoryException nsee) {
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
	protected ShoppingCategory removeImpl(ShoppingCategory shoppingCategory) {
		shoppingCategory = toUnwrappedModel(shoppingCategory);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingCategory)) {
				shoppingCategory = (ShoppingCategory)session.get(ShoppingCategoryImpl.class,
						shoppingCategory.getPrimaryKeyObj());
			}

			if (shoppingCategory != null) {
				session.delete(shoppingCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingCategory != null) {
			clearCache(shoppingCategory);
		}

		return shoppingCategory;
	}

	@Override
	public ShoppingCategory updateImpl(ShoppingCategory shoppingCategory) {
		shoppingCategory = toUnwrappedModel(shoppingCategory);

		boolean isNew = shoppingCategory.isNew();

		ShoppingCategoryModelImpl shoppingCategoryModelImpl = (ShoppingCategoryModelImpl)shoppingCategory;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (shoppingCategory.getCreateDate() == null)) {
			if (serviceContext == null) {
				shoppingCategory.setCreateDate(now);
			}
			else {
				shoppingCategory.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!shoppingCategoryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				shoppingCategory.setModifiedDate(now);
			}
			else {
				shoppingCategory.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (shoppingCategory.isNew()) {
				session.save(shoppingCategory);

				shoppingCategory.setNew(false);
			}
			else {
				shoppingCategory = (ShoppingCategory)session.merge(shoppingCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCategoryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { shoppingCategoryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((shoppingCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCategoryModelImpl.getOriginalGroupId(),
						shoppingCategoryModelImpl.getOriginalParentCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						shoppingCategoryModelImpl.getGroupId(),
						shoppingCategoryModelImpl.getParentCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}
		}

		entityCache.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey(),
			shoppingCategory, false);

		clearUniqueFindersCache(shoppingCategoryModelImpl);
		cacheUniqueFindersCache(shoppingCategoryModelImpl, isNew);

		shoppingCategory.resetOriginalValues();

		return shoppingCategory;
	}

	protected ShoppingCategory toUnwrappedModel(
		ShoppingCategory shoppingCategory) {
		if (shoppingCategory instanceof ShoppingCategoryImpl) {
			return shoppingCategory;
		}

		ShoppingCategoryImpl shoppingCategoryImpl = new ShoppingCategoryImpl();

		shoppingCategoryImpl.setNew(shoppingCategory.isNew());
		shoppingCategoryImpl.setPrimaryKey(shoppingCategory.getPrimaryKey());

		shoppingCategoryImpl.setCategoryId(shoppingCategory.getCategoryId());
		shoppingCategoryImpl.setGroupId(shoppingCategory.getGroupId());
		shoppingCategoryImpl.setCompanyId(shoppingCategory.getCompanyId());
		shoppingCategoryImpl.setUserId(shoppingCategory.getUserId());
		shoppingCategoryImpl.setUserName(shoppingCategory.getUserName());
		shoppingCategoryImpl.setCreateDate(shoppingCategory.getCreateDate());
		shoppingCategoryImpl.setModifiedDate(shoppingCategory.getModifiedDate());
		shoppingCategoryImpl.setParentCategoryId(shoppingCategory.getParentCategoryId());
		shoppingCategoryImpl.setName(shoppingCategory.getName());
		shoppingCategoryImpl.setDescription(shoppingCategory.getDescription());

		return shoppingCategoryImpl;
	}

	/**
	 * Returns the shopping category with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping category
	 * @return the shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCategoryException {
		ShoppingCategory shoppingCategory = fetchByPrimaryKey(primaryKey);

		if (shoppingCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return shoppingCategory;
	}

	/**
	 * Returns the shopping category with the primary key or throws a {@link com.liferay.shopping.NoSuchCategoryException} if it could not be found.
	 *
	 * @param categoryId the primary key of the shopping category
	 * @return the shopping category
	 * @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException {
		return findByPrimaryKey((Serializable)categoryId);
	}

	/**
	 * Returns the shopping category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping category
	 * @return the shopping category, or <code>null</code> if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory fetchByPrimaryKey(Serializable primaryKey) {
		ShoppingCategory shoppingCategory = (ShoppingCategory)entityCache.getResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCategoryImpl.class, primaryKey);

		if (shoppingCategory == _nullShoppingCategory) {
			return null;
		}

		if (shoppingCategory == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingCategory = (ShoppingCategory)session.get(ShoppingCategoryImpl.class,
						primaryKey);

				if (shoppingCategory != null) {
					cacheResult(shoppingCategory);
				}
				else {
					entityCache.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCategoryImpl.class, primaryKey,
						_nullShoppingCategory);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCategoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingCategory;
	}

	/**
	 * Returns the shopping category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryId the primary key of the shopping category
	 * @return the shopping category, or <code>null</code> if a shopping category with the primary key could not be found
	 */
	@Override
	public ShoppingCategory fetchByPrimaryKey(long categoryId) {
		return fetchByPrimaryKey((Serializable)categoryId);
	}

	@Override
	public Map<Serializable, ShoppingCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingCategory> map = new HashMap<Serializable, ShoppingCategory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingCategory shoppingCategory = fetchByPrimaryKey(primaryKey);

			if (shoppingCategory != null) {
				map.put(primaryKey, shoppingCategory);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ShoppingCategory shoppingCategory = (ShoppingCategory)entityCache.getResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCategoryImpl.class, primaryKey);

			if (shoppingCategory == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, shoppingCategory);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHOPPINGCATEGORY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ShoppingCategory shoppingCategory : (List<ShoppingCategory>)q.list()) {
				map.put(shoppingCategory.getPrimaryKeyObj(), shoppingCategory);

				cacheResult(shoppingCategory);

				uncachedPrimaryKeys.remove(shoppingCategory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCategoryImpl.class, primaryKey,
					_nullShoppingCategory);
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
	 * Returns all the shopping categories.
	 *
	 * @return the shopping categories
	 */
	@Override
	public List<ShoppingCategory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @return the range of shopping categories
	 */
	@Override
	public List<ShoppingCategory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping categories
	 */
	@Override
	public List<ShoppingCategory> findAll(int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping categories
	 * @param end the upper bound of the range of shopping categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping categories
	 */
	@Override
	public List<ShoppingCategory> findAll(int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator,
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

		List<ShoppingCategory> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCategory>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGCATEGORY;

				if (pagination) {
					sql = sql.concat(ShoppingCategoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
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
	 * Removes all the shopping categories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingCategory shoppingCategory : findAll()) {
			remove(shoppingCategory);
		}
	}

	/**
	 * Returns the number of shopping categories.
	 *
	 * @return the number of shopping categories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGCATEGORY);

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
		return ShoppingCategoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping category persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ShoppingCategoryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SHOPPINGCATEGORY = "SELECT shoppingCategory FROM ShoppingCategory shoppingCategory";
	private static final String _SQL_SELECT_SHOPPINGCATEGORY_WHERE_PKS_IN = "SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE categoryId IN (";
	private static final String _SQL_SELECT_SHOPPINGCATEGORY_WHERE = "SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ";
	private static final String _SQL_COUNT_SHOPPINGCATEGORY = "SELECT COUNT(shoppingCategory) FROM ShoppingCategory shoppingCategory";
	private static final String _SQL_COUNT_SHOPPINGCATEGORY_WHERE = "SELECT COUNT(shoppingCategory) FROM ShoppingCategory shoppingCategory WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "shoppingCategory.categoryId";
	private static final String _FILTER_SQL_SELECT_SHOPPINGCATEGORY_WHERE = "SELECT DISTINCT {shoppingCategory.*} FROM ShoppingCategory shoppingCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {ShoppingCategory.*} FROM (SELECT DISTINCT shoppingCategory.categoryId FROM ShoppingCategory shoppingCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGCATEGORY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN ShoppingCategory ON TEMP_TABLE.categoryId = ShoppingCategory.categoryId";
	private static final String _FILTER_SQL_COUNT_SHOPPINGCATEGORY_WHERE = "SELECT COUNT(DISTINCT shoppingCategory.categoryId) AS COUNT_VALUE FROM ShoppingCategory shoppingCategory WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "shoppingCategory";
	private static final String _FILTER_ENTITY_TABLE = "ShoppingCategory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingCategory.";
	private static final String _ORDER_BY_ENTITY_TABLE = "ShoppingCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingCategory exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ShoppingCategoryPersistenceImpl.class);
	private static final ShoppingCategory _nullShoppingCategory = new ShoppingCategoryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingCategory> toCacheModel() {
				return _nullShoppingCategoryCacheModel;
			}
		};

	private static final CacheModel<ShoppingCategory> _nullShoppingCategoryCacheModel =
		new CacheModel<ShoppingCategory>() {
			@Override
			public ShoppingCategory toEntityModel() {
				return _nullShoppingCategory;
			}
		};
}