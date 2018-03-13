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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchTaxCategoryException;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.impl.CommerceTaxCategoryImpl;
import com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

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
 * The persistence implementation for the commerce tax category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryPersistence
 * @see com.liferay.commerce.service.persistence.CommerceTaxCategoryUtil
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryPersistenceImpl extends BasePersistenceImpl<CommerceTaxCategory>
	implements CommerceTaxCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTaxCategoryUtil} to access the commerce tax category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTaxCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceTaxCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTaxCategoryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @return the range of matching commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTaxCategory> orderByComparator,
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

		List<CommerceTaxCategory> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxCategory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxCategory commerceTaxCategory : list) {
					if ((groupId != commerceTaxCategory.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceTaxCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxCategory>)QueryUtil.list(q,
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
	 * Returns the first commerce tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category
	 * @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	 */
	@Override
	public CommerceTaxCategory findByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException {
		CommerceTaxCategory commerceTaxCategory = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceTaxCategory != null) {
			return commerceTaxCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTaxCategoryException(msg.toString());
	}

	/**
	 * Returns the first commerce tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	 */
	@Override
	public CommerceTaxCategory fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		List<CommerceTaxCategory> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category
	 * @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	 */
	@Override
	public CommerceTaxCategory findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException {
		CommerceTaxCategory commerceTaxCategory = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceTaxCategory != null) {
			return commerceTaxCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTaxCategoryException(msg.toString());
	}

	/**
	 * Returns the last commerce tax category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	 */
	@Override
	public CommerceTaxCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxCategory> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax categories before and after the current commerce tax category in the ordered set where groupId = &#63;.
	 *
	 * @param commerceTaxCategoryId the primary key of the current commerce tax category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax category
	 * @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory[] findByGroupId_PrevAndNext(
		long commerceTaxCategoryId, long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException {
		CommerceTaxCategory commerceTaxCategory = findByPrimaryKey(commerceTaxCategoryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxCategory[] array = new CommerceTaxCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceTaxCategory,
					groupId, orderByComparator, true);

			array[1] = commerceTaxCategory;

			array[2] = getByGroupId_PrevAndNext(session, commerceTaxCategory,
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

	protected CommerceTaxCategory getByGroupId_PrevAndNext(Session session,
		CommerceTaxCategory commerceTaxCategory, long groupId,
		OrderByComparator<CommerceTaxCategory> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETAXCATEGORY_WHERE);

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
			query.append(CommerceTaxCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceTaxCategory commerceTaxCategory : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTaxCategory);
		}
	}

	/**
	 * Returns the number of commerce tax categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce tax categories
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXCATEGORY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceTaxCategory.groupId = ?";

	public CommerceTaxCategoryPersistenceImpl() {
		setModelClass(CommerceTaxCategory.class);
	}

	/**
	 * Caches the commerce tax category in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategory the commerce tax category
	 */
	@Override
	public void cacheResult(CommerceTaxCategory commerceTaxCategory) {
		entityCache.putResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class, commerceTaxCategory.getPrimaryKey(),
			commerceTaxCategory);

		commerceTaxCategory.resetOriginalValues();
	}

	/**
	 * Caches the commerce tax categories in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategories the commerce tax categories
	 */
	@Override
	public void cacheResult(List<CommerceTaxCategory> commerceTaxCategories) {
		for (CommerceTaxCategory commerceTaxCategory : commerceTaxCategories) {
			if (entityCache.getResult(
						CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxCategoryImpl.class,
						commerceTaxCategory.getPrimaryKey()) == null) {
				cacheResult(commerceTaxCategory);
			}
			else {
				commerceTaxCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax categories.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxCategoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tax category.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxCategory commerceTaxCategory) {
		entityCache.removeResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class, commerceTaxCategory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceTaxCategory> commerceTaxCategories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTaxCategory commerceTaxCategory : commerceTaxCategories) {
			entityCache.removeResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxCategoryImpl.class,
				commerceTaxCategory.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce tax category with the primary key. Does not add the commerce tax category to the database.
	 *
	 * @param commerceTaxCategoryId the primary key for the new commerce tax category
	 * @return the new commerce tax category
	 */
	@Override
	public CommerceTaxCategory create(long commerceTaxCategoryId) {
		CommerceTaxCategory commerceTaxCategory = new CommerceTaxCategoryImpl();

		commerceTaxCategory.setNew(true);
		commerceTaxCategory.setPrimaryKey(commerceTaxCategoryId);

		commerceTaxCategory.setCompanyId(companyProvider.getCompanyId());

		return commerceTaxCategory;
	}

	/**
	 * Removes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxCategoryId the primary key of the commerce tax category
	 * @return the commerce tax category that was removed
	 * @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory remove(long commerceTaxCategoryId)
		throws NoSuchTaxCategoryException {
		return remove((Serializable)commerceTaxCategoryId);
	}

	/**
	 * Removes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax category
	 * @return the commerce tax category that was removed
	 * @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory remove(Serializable primaryKey)
		throws NoSuchTaxCategoryException {
		Session session = null;

		try {
			session = openSession();

			CommerceTaxCategory commerceTaxCategory = (CommerceTaxCategory)session.get(CommerceTaxCategoryImpl.class,
					primaryKey);

			if (commerceTaxCategory == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTaxCategory);
		}
		catch (NoSuchTaxCategoryException nsee) {
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
	protected CommerceTaxCategory removeImpl(
		CommerceTaxCategory commerceTaxCategory) {
		commerceTaxCategory = toUnwrappedModel(commerceTaxCategory);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxCategory)) {
				commerceTaxCategory = (CommerceTaxCategory)session.get(CommerceTaxCategoryImpl.class,
						commerceTaxCategory.getPrimaryKeyObj());
			}

			if (commerceTaxCategory != null) {
				session.delete(commerceTaxCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxCategory != null) {
			clearCache(commerceTaxCategory);
		}

		return commerceTaxCategory;
	}

	@Override
	public CommerceTaxCategory updateImpl(
		CommerceTaxCategory commerceTaxCategory) {
		commerceTaxCategory = toUnwrappedModel(commerceTaxCategory);

		boolean isNew = commerceTaxCategory.isNew();

		CommerceTaxCategoryModelImpl commerceTaxCategoryModelImpl = (CommerceTaxCategoryModelImpl)commerceTaxCategory;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTaxCategory.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxCategory.setCreateDate(now);
			}
			else {
				commerceTaxCategory.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTaxCategoryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxCategory.setModifiedDate(now);
			}
			else {
				commerceTaxCategory.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTaxCategory.isNew()) {
				session.save(commerceTaxCategory);

				commerceTaxCategory.setNew(false);
			}
			else {
				commerceTaxCategory = (CommerceTaxCategory)session.merge(commerceTaxCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTaxCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTaxCategoryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTaxCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxCategoryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceTaxCategoryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxCategoryImpl.class, commerceTaxCategory.getPrimaryKey(),
			commerceTaxCategory, false);

		commerceTaxCategory.resetOriginalValues();

		return commerceTaxCategory;
	}

	protected CommerceTaxCategory toUnwrappedModel(
		CommerceTaxCategory commerceTaxCategory) {
		if (commerceTaxCategory instanceof CommerceTaxCategoryImpl) {
			return commerceTaxCategory;
		}

		CommerceTaxCategoryImpl commerceTaxCategoryImpl = new CommerceTaxCategoryImpl();

		commerceTaxCategoryImpl.setNew(commerceTaxCategory.isNew());
		commerceTaxCategoryImpl.setPrimaryKey(commerceTaxCategory.getPrimaryKey());

		commerceTaxCategoryImpl.setCommerceTaxCategoryId(commerceTaxCategory.getCommerceTaxCategoryId());
		commerceTaxCategoryImpl.setGroupId(commerceTaxCategory.getGroupId());
		commerceTaxCategoryImpl.setCompanyId(commerceTaxCategory.getCompanyId());
		commerceTaxCategoryImpl.setUserId(commerceTaxCategory.getUserId());
		commerceTaxCategoryImpl.setUserName(commerceTaxCategory.getUserName());
		commerceTaxCategoryImpl.setCreateDate(commerceTaxCategory.getCreateDate());
		commerceTaxCategoryImpl.setModifiedDate(commerceTaxCategory.getModifiedDate());
		commerceTaxCategoryImpl.setName(commerceTaxCategory.getName());
		commerceTaxCategoryImpl.setDescription(commerceTaxCategory.getDescription());

		return commerceTaxCategoryImpl;
	}

	/**
	 * Returns the commerce tax category with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax category
	 * @return the commerce tax category
	 * @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxCategoryException {
		CommerceTaxCategory commerceTaxCategory = fetchByPrimaryKey(primaryKey);

		if (commerceTaxCategory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTaxCategory;
	}

	/**
	 * Returns the commerce tax category with the primary key or throws a {@link NoSuchTaxCategoryException} if it could not be found.
	 *
	 * @param commerceTaxCategoryId the primary key of the commerce tax category
	 * @return the commerce tax category
	 * @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory findByPrimaryKey(long commerceTaxCategoryId)
		throws NoSuchTaxCategoryException {
		return findByPrimaryKey((Serializable)commerceTaxCategoryId);
	}

	/**
	 * Returns the commerce tax category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax category
	 * @return the commerce tax category, or <code>null</code> if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxCategoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTaxCategory commerceTaxCategory = (CommerceTaxCategory)serializable;

		if (commerceTaxCategory == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTaxCategory = (CommerceTaxCategory)session.get(CommerceTaxCategoryImpl.class,
						primaryKey);

				if (commerceTaxCategory != null) {
					cacheResult(commerceTaxCategory);
				}
				else {
					entityCache.putResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxCategoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTaxCategory;
	}

	/**
	 * Returns the commerce tax category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryId the primary key of the commerce tax category
	 * @return the commerce tax category, or <code>null</code> if a commerce tax category with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategory fetchByPrimaryKey(long commerceTaxCategoryId) {
		return fetchByPrimaryKey((Serializable)commerceTaxCategoryId);
	}

	@Override
	public Map<Serializable, CommerceTaxCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTaxCategory> map = new HashMap<Serializable, CommerceTaxCategory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTaxCategory commerceTaxCategory = fetchByPrimaryKey(primaryKey);

			if (commerceTaxCategory != null) {
				map.put(primaryKey, commerceTaxCategory);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTaxCategory)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETAXCATEGORY_WHERE_PKS_IN);

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

			for (CommerceTaxCategory commerceTaxCategory : (List<CommerceTaxCategory>)q.list()) {
				map.put(commerceTaxCategory.getPrimaryKeyObj(),
					commerceTaxCategory);

				cacheResult(commerceTaxCategory);

				uncachedPrimaryKeys.remove(commerceTaxCategory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTaxCategoryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxCategoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tax categories.
	 *
	 * @return the commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @return the range of commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax categories
	 * @param end the upper bound of the range of commerce tax categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tax categories
	 */
	@Override
	public List<CommerceTaxCategory> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator,
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

		List<CommerceTaxCategory> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxCategory>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETAXCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXCATEGORY;

				if (pagination) {
					sql = sql.concat(CommerceTaxCategoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTaxCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxCategory>)QueryUtil.list(q,
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
	 * Removes all the commerce tax categories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxCategory commerceTaxCategory : findAll()) {
			remove(commerceTaxCategory);
		}
	}

	/**
	 * Returns the number of commerce tax categories.
	 *
	 * @return the number of commerce tax categories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETAXCATEGORY);

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
		return CommerceTaxCategoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax category persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxCategoryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETAXCATEGORY = "SELECT commerceTaxCategory FROM CommerceTaxCategory commerceTaxCategory";
	private static final String _SQL_SELECT_COMMERCETAXCATEGORY_WHERE_PKS_IN = "SELECT commerceTaxCategory FROM CommerceTaxCategory commerceTaxCategory WHERE commerceTaxCategoryId IN (";
	private static final String _SQL_SELECT_COMMERCETAXCATEGORY_WHERE = "SELECT commerceTaxCategory FROM CommerceTaxCategory commerceTaxCategory WHERE ";
	private static final String _SQL_COUNT_COMMERCETAXCATEGORY = "SELECT COUNT(commerceTaxCategory) FROM CommerceTaxCategory commerceTaxCategory";
	private static final String _SQL_COUNT_COMMERCETAXCATEGORY_WHERE = "SELECT COUNT(commerceTaxCategory) FROM CommerceTaxCategory commerceTaxCategory WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTaxCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTaxCategory exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxCategoryPersistenceImpl.class);
}