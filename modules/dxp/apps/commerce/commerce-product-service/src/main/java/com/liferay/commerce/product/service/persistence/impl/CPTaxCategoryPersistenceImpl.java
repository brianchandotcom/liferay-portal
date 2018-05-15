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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.impl.CPTaxCategoryImpl;
import com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cp tax category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPTaxCategoryPersistence
 * @see com.liferay.commerce.product.service.persistence.CPTaxCategoryUtil
 * @generated
 */
@ProviderType
public class CPTaxCategoryPersistenceImpl extends BasePersistenceImpl<CPTaxCategory>
	implements CPTaxCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPTaxCategoryUtil} to access the cp tax category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPTaxCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CPTaxCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CPTaxCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CPTaxCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CPTaxCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			CPTaxCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			CPTaxCategoryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp tax categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @return the range of matching cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator,
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

		List<CPTaxCategory> list = null;

		if (retrieveFromCache) {
			list = (List<CPTaxCategory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPTaxCategory cpTaxCategory : list) {
					if ((groupId != cpTaxCategory.getGroupId())) {
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

			query.append(_SQL_SELECT_CPTAXCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPTaxCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPTaxCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPTaxCategory>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp tax category
	 * @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	 */
	@Override
	public CPTaxCategory findByGroupId_First(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator)
		throws NoSuchCPTaxCategoryException {
		CPTaxCategory cpTaxCategory = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpTaxCategory != null) {
			return cpTaxCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPTaxCategoryException(msg.toString());
	}

	/**
	 * Returns the first cp tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	@Override
	public CPTaxCategory fetchByGroupId_First(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		List<CPTaxCategory> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp tax category
	 * @throws NoSuchCPTaxCategoryException if a matching cp tax category could not be found
	 */
	@Override
	public CPTaxCategory findByGroupId_Last(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator)
		throws NoSuchCPTaxCategoryException {
		CPTaxCategory cpTaxCategory = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpTaxCategory != null) {
			return cpTaxCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPTaxCategoryException(msg.toString());
	}

	/**
	 * Returns the last cp tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp tax category, or <code>null</code> if a matching cp tax category could not be found
	 */
	@Override
	public CPTaxCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPTaxCategory> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp tax categories before and after the current cp tax category in the ordered set where groupId = &#63;.
	 *
	 * @param CPTaxCategoryId the primary key of the current cp tax category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp tax category
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory[] findByGroupId_PrevAndNext(long CPTaxCategoryId,
		long groupId, OrderByComparator<CPTaxCategory> orderByComparator)
		throws NoSuchCPTaxCategoryException {
		CPTaxCategory cpTaxCategory = findByPrimaryKey(CPTaxCategoryId);

		Session session = null;

		try {
			session = openSession();

			CPTaxCategory[] array = new CPTaxCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, cpTaxCategory,
					groupId, orderByComparator, true);

			array[1] = cpTaxCategory;

			array[2] = getByGroupId_PrevAndNext(session, cpTaxCategory,
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

	protected CPTaxCategory getByGroupId_PrevAndNext(Session session,
		CPTaxCategory cpTaxCategory, long groupId,
		OrderByComparator<CPTaxCategory> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPTAXCATEGORY_WHERE);

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
			query.append(CPTaxCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpTaxCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPTaxCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp tax categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPTaxCategory cpTaxCategory : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpTaxCategory);
		}
	}

	/**
	 * Returns the number of cp tax categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp tax categories
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPTAXCATEGORY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpTaxCategory.groupId = ?";

	public CPTaxCategoryPersistenceImpl() {
		setModelClass(CPTaxCategory.class);
	}

	/**
	 * Caches the cp tax category in the entity cache if it is enabled.
	 *
	 * @param cpTaxCategory the cp tax category
	 */
	@Override
	public void cacheResult(CPTaxCategory cpTaxCategory) {
		entityCache.putResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryImpl.class, cpTaxCategory.getPrimaryKey(),
			cpTaxCategory);

		cpTaxCategory.resetOriginalValues();
	}

	/**
	 * Caches the cp tax categories in the entity cache if it is enabled.
	 *
	 * @param cpTaxCategories the cp tax categories
	 */
	@Override
	public void cacheResult(List<CPTaxCategory> cpTaxCategories) {
		for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
			if (entityCache.getResult(
						CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
						CPTaxCategoryImpl.class, cpTaxCategory.getPrimaryKey()) == null) {
				cacheResult(cpTaxCategory);
			}
			else {
				cpTaxCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp tax categories.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPTaxCategoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp tax category.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPTaxCategory cpTaxCategory) {
		entityCache.removeResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryImpl.class, cpTaxCategory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CPTaxCategory> cpTaxCategories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
			entityCache.removeResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
				CPTaxCategoryImpl.class, cpTaxCategory.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cp tax category with the primary key. Does not add the cp tax category to the database.
	 *
	 * @param CPTaxCategoryId the primary key for the new cp tax category
	 * @return the new cp tax category
	 */
	@Override
	public CPTaxCategory create(long CPTaxCategoryId) {
		CPTaxCategory cpTaxCategory = new CPTaxCategoryImpl();

		cpTaxCategory.setNew(true);
		cpTaxCategory.setPrimaryKey(CPTaxCategoryId);

		cpTaxCategory.setCompanyId(companyProvider.getCompanyId());

		return cpTaxCategory;
	}

	/**
	 * Removes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category that was removed
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory remove(long CPTaxCategoryId)
		throws NoSuchCPTaxCategoryException {
		return remove((Serializable)CPTaxCategoryId);
	}

	/**
	 * Removes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp tax category
	 * @return the cp tax category that was removed
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory remove(Serializable primaryKey)
		throws NoSuchCPTaxCategoryException {
		Session session = null;

		try {
			session = openSession();

			CPTaxCategory cpTaxCategory = (CPTaxCategory)session.get(CPTaxCategoryImpl.class,
					primaryKey);

			if (cpTaxCategory == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPTaxCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpTaxCategory);
		}
		catch (NoSuchCPTaxCategoryException nsee) {
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
	protected CPTaxCategory removeImpl(CPTaxCategory cpTaxCategory) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpTaxCategory)) {
				cpTaxCategory = (CPTaxCategory)session.get(CPTaxCategoryImpl.class,
						cpTaxCategory.getPrimaryKeyObj());
			}

			if (cpTaxCategory != null) {
				session.delete(cpTaxCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpTaxCategory != null) {
			clearCache(cpTaxCategory);
		}

		return cpTaxCategory;
	}

	@Override
	public CPTaxCategory updateImpl(CPTaxCategory cpTaxCategory) {
		boolean isNew = cpTaxCategory.isNew();

		if (!(cpTaxCategory instanceof CPTaxCategoryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpTaxCategory.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpTaxCategory);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpTaxCategory proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPTaxCategory implementation " +
				cpTaxCategory.getClass());
		}

		CPTaxCategoryModelImpl cpTaxCategoryModelImpl = (CPTaxCategoryModelImpl)cpTaxCategory;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpTaxCategory.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpTaxCategory.setCreateDate(now);
			}
			else {
				cpTaxCategory.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cpTaxCategoryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpTaxCategory.setModifiedDate(now);
			}
			else {
				cpTaxCategory.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpTaxCategory.isNew()) {
				session.save(cpTaxCategory);

				cpTaxCategory.setNew(false);
			}
			else {
				cpTaxCategory = (CPTaxCategory)session.merge(cpTaxCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPTaxCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpTaxCategoryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpTaxCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpTaxCategoryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { cpTaxCategoryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CPTaxCategoryImpl.class, cpTaxCategory.getPrimaryKey(),
			cpTaxCategory, false);

		cpTaxCategory.resetOriginalValues();

		return cpTaxCategory;
	}

	/**
	 * Returns the cp tax category with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp tax category
	 * @return the cp tax category
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPTaxCategoryException {
		CPTaxCategory cpTaxCategory = fetchByPrimaryKey(primaryKey);

		if (cpTaxCategory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPTaxCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpTaxCategory;
	}

	/**
	 * Returns the cp tax category with the primary key or throws a {@link NoSuchCPTaxCategoryException} if it could not be found.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category
	 * @throws NoSuchCPTaxCategoryException if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory findByPrimaryKey(long CPTaxCategoryId)
		throws NoSuchCPTaxCategoryException {
		return findByPrimaryKey((Serializable)CPTaxCategoryId);
	}

	/**
	 * Returns the cp tax category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp tax category
	 * @return the cp tax category, or <code>null</code> if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
				CPTaxCategoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPTaxCategory cpTaxCategory = (CPTaxCategory)serializable;

		if (cpTaxCategory == null) {
			Session session = null;

			try {
				session = openSession();

				cpTaxCategory = (CPTaxCategory)session.get(CPTaxCategoryImpl.class,
						primaryKey);

				if (cpTaxCategory != null) {
					cacheResult(cpTaxCategory);
				}
				else {
					entityCache.putResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
						CPTaxCategoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CPTaxCategoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpTaxCategory;
	}

	/**
	 * Returns the cp tax category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the primary key of the cp tax category
	 * @return the cp tax category, or <code>null</code> if a cp tax category with the primary key could not be found
	 */
	@Override
	public CPTaxCategory fetchByPrimaryKey(long CPTaxCategoryId) {
		return fetchByPrimaryKey((Serializable)CPTaxCategoryId);
	}

	@Override
	public Map<Serializable, CPTaxCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPTaxCategory> map = new HashMap<Serializable, CPTaxCategory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPTaxCategory cpTaxCategory = fetchByPrimaryKey(primaryKey);

			if (cpTaxCategory != null) {
				map.put(primaryKey, cpTaxCategory);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CPTaxCategoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPTaxCategory)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPTAXCATEGORY_WHERE_PKS_IN);

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

			for (CPTaxCategory cpTaxCategory : (List<CPTaxCategory>)q.list()) {
				map.put(cpTaxCategory.getPrimaryKeyObj(), cpTaxCategory);

				cacheResult(cpTaxCategory);

				uncachedPrimaryKeys.remove(cpTaxCategory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CPTaxCategoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp tax categories.
	 *
	 * @return the cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @return the range of cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findAll(int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp tax categories
	 * @param end the upper bound of the range of cp tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp tax categories
	 */
	@Override
	public List<CPTaxCategory> findAll(int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator,
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

		List<CPTaxCategory> list = null;

		if (retrieveFromCache) {
			list = (List<CPTaxCategory>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPTAXCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPTAXCATEGORY;

				if (pagination) {
					sql = sql.concat(CPTaxCategoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPTaxCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPTaxCategory>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the cp tax categories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPTaxCategory cpTaxCategory : findAll()) {
			remove(cpTaxCategory);
		}
	}

	/**
	 * Returns the number of cp tax categories.
	 *
	 * @return the number of cp tax categories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPTAXCATEGORY);

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
		return CPTaxCategoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp tax category persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPTaxCategoryImpl.class.getName());
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
	private static final String _SQL_SELECT_CPTAXCATEGORY = "SELECT cpTaxCategory FROM CPTaxCategory cpTaxCategory";
	private static final String _SQL_SELECT_CPTAXCATEGORY_WHERE_PKS_IN = "SELECT cpTaxCategory FROM CPTaxCategory cpTaxCategory WHERE CPTaxCategoryId IN (";
	private static final String _SQL_SELECT_CPTAXCATEGORY_WHERE = "SELECT cpTaxCategory FROM CPTaxCategory cpTaxCategory WHERE ";
	private static final String _SQL_COUNT_CPTAXCATEGORY = "SELECT COUNT(cpTaxCategory) FROM CPTaxCategory cpTaxCategory";
	private static final String _SQL_COUNT_CPTAXCATEGORY_WHERE = "SELECT COUNT(cpTaxCategory) FROM CPTaxCategory cpTaxCategory WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpTaxCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPTaxCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPTaxCategory exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPTaxCategoryPersistenceImpl.class);
}