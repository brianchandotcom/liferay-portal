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

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseItemPersistence;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the commerce inventory warehouse item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehouseItemPersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryWarehouseItem>
	implements CommerceInventoryWarehouseItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryWarehouseItemUtil</code> to access the commerce inventory warehouse item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryWarehouseItemImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce inventory warehouse items where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @return the range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CommerceInventoryWarehouseItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem : list) {

					if (companyId !=
							commerceInventoryWarehouseItem.getCompanyId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		List<CommerceInventoryWarehouseItem> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouseItem> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouse items before and after the current commerce inventory warehouse item in the ordered set where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the current commerce inventory warehouse item
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem[] findByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseItemId, long companyId,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			findByPrimaryKey(commerceInventoryWarehouseItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseItem[] array =
				new CommerceInventoryWarehouseItemImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouseItem, companyId,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouseItem;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouseItem, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouseItem getByCompanyId_PrevAndNext(
		Session session,
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem,
		long companyId,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouseItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouseItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouse items where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouseItem);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse items where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouse items
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceInventoryWarehouseItem.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath _finderPathCountByCommerceInventoryWarehouseId;

	/**
	 * Returns all the commerce inventory warehouse items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @return the range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseItem>
				orderByComparator) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
				finderArgs = new Object[] {commerceInventoryWarehouseId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
			finderArgs = new Object[] {
				commerceInventoryWarehouseId, start, end, orderByComparator
			};
		}

		List<CommerceInventoryWarehouseItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem : list) {

					if (commerceInventoryWarehouseId !=
							commerceInventoryWarehouseItem.
								getCommerceInventoryWarehouseId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				list = (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseItem>
					orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCommerceInventoryWarehouseId_First(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseItem>
				orderByComparator) {

		List<CommerceInventoryWarehouseItem> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem
			findByCommerceInventoryWarehouseId_Last(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseItem>
					orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCommerceInventoryWarehouseId_Last(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem
		fetchByCommerceInventoryWarehouseId_Last(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseItem>
				orderByComparator) {

		int count = countByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouseItem> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouse items before and after the current commerce inventory warehouse item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the current commerce inventory warehouse item
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem[]
			findByCommerceInventoryWarehouseId_PrevAndNext(
				long commerceInventoryWarehouseItemId,
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseItem>
					orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			findByPrimaryKey(commerceInventoryWarehouseItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseItem[] array =
				new CommerceInventoryWarehouseItemImpl[3];

			array[0] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryWarehouseItem,
				commerceInventoryWarehouseId, orderByComparator, true);

			array[1] = commerceInventoryWarehouseItem;

			array[2] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryWarehouseItem,
				commerceInventoryWarehouseId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouseItem
		getByCommerceInventoryWarehouseId_PrevAndNext(
			Session session,
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem,
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceInventoryWarehouseId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouseItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouseItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouse items where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	@Override
	public void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				findByCommerceInventoryWarehouseId(
					commerceInventoryWarehouseId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouseItem);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse items
	 */
	@Override
	public int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		FinderPath finderPath = _finderPathCountByCommerceInventoryWarehouseId;

		Object[] finderArgs = new Object[] {commerceInventoryWarehouseId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2 =
			"commerceInventoryWarehouseItem.commerceInventoryWarehouseId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId_Sku;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId_Sku;
	private FinderPath _finderPathCountByCompanyId_Sku;

	/**
	 * Returns all the commerce inventory warehouse items where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId_Sku(
		long companyId, String sku) {

		return findByCompanyId_Sku(
			companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @return the range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId_Sku(
		long companyId, String sku, int start, int end) {

		return findByCompanyId_Sku(companyId, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId_Sku(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		return findByCompanyId_Sku(
			companyId, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findByCompanyId_Sku(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId_Sku;
				finderArgs = new Object[] {companyId, sku};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId_Sku;
			finderArgs = new Object[] {
				companyId, sku, start, end, orderByComparator
			};
		}

		List<CommerceInventoryWarehouseItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem : list) {

					if ((companyId !=
							commerceInventoryWarehouseItem.getCompanyId()) ||
						!sku.equals(commerceInventoryWarehouseItem.getSku())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_SKU_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
				}

				list = (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByCompanyId_Sku_First(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCompanyId_Sku_First(companyId, sku, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByCompanyId_Sku_First(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		List<CommerceInventoryWarehouseItem> list = findByCompanyId_Sku(
			companyId, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByCompanyId_Sku_Last(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByCompanyId_Sku_Last(companyId, sku, orderByComparator);

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryWarehouseItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByCompanyId_Sku_Last(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		int count = countByCompanyId_Sku(companyId, sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouseItem> list = findByCompanyId_Sku(
			companyId, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouse items before and after the current commerce inventory warehouse item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the current commerce inventory warehouse item
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem[] findByCompanyId_Sku_PrevAndNext(
			long commerceInventoryWarehouseItemId, long companyId, String sku,
			OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator)
		throws NoSuchInventoryWarehouseItemException {

		sku = Objects.toString(sku, "");

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			findByPrimaryKey(commerceInventoryWarehouseItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseItem[] array =
				new CommerceInventoryWarehouseItemImpl[3];

			array[0] = getByCompanyId_Sku_PrevAndNext(
				session, commerceInventoryWarehouseItem, companyId, sku,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouseItem;

			array[2] = getByCompanyId_Sku_PrevAndNext(
				session, commerceInventoryWarehouseItem, companyId, sku,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouseItem getByCompanyId_Sku_PrevAndNext(
		Session session,
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem,
		long companyId, String sku,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_SKU_COMPANYID_2);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindSku) {
			queryPos.add(sku);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouseItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouseItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouse items where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	@Override
	public void removeByCompanyId_Sku(long companyId, String sku) {
		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				findByCompanyId_Sku(
					companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceInventoryWarehouseItem);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse items where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching commerce inventory warehouse items
	 */
	@Override
	public int countByCompanyId_Sku(long companyId, String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByCompanyId_Sku;

		Object[] finderArgs = new Object[] {companyId, sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_SKU_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_COMPANYID_SKU_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_SKU_COMPANYID_2 =
		"commerceInventoryWarehouseItem.companyId = ? AND ";

	private static final String _FINDER_COLUMN_COMPANYID_SKU_SKU_2 =
		"commerceInventoryWarehouseItem.sku = ?";

	private static final String _FINDER_COLUMN_COMPANYID_SKU_SKU_3 =
		"(commerceInventoryWarehouseItem.sku IS NULL OR commerceInventoryWarehouseItem.sku = '')";

	private FinderPath _finderPathFetchByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns the commerce inventory warehouse item where commerceInventoryWarehouseId = &#63; and sku = &#63; or throws a <code>NoSuchInventoryWarehouseItemException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param sku the sku
	 * @return the matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByC_S(
			long commerceInventoryWarehouseId, String sku)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByC_S(commerceInventoryWarehouseId, sku);

		if (commerceInventoryWarehouseItem == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceInventoryWarehouseId=");
			sb.append(commerceInventoryWarehouseId);

			sb.append(", sku=");
			sb.append(sku);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchInventoryWarehouseItemException(sb.toString());
		}

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Returns the commerce inventory warehouse item where commerceInventoryWarehouseId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param sku the sku
	 * @return the matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByC_S(
		long commerceInventoryWarehouseId, String sku) {

		return fetchByC_S(commerceInventoryWarehouseId, sku, true);
	}

	/**
	 * Returns the commerce inventory warehouse item where commerceInventoryWarehouseId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param sku the sku
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByC_S(
		long commerceInventoryWarehouseId, String sku, boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceInventoryWarehouseId, sku};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_S, finderArgs, this);
		}

		if (result instanceof CommerceInventoryWarehouseItem) {
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				(CommerceInventoryWarehouseItem)result;

			if ((commerceInventoryWarehouseId !=
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseId()) ||
				!Objects.equals(sku, commerceInventoryWarehouseItem.getSku())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMMERCEINVENTORYWAREHOUSEID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				if (bindSku) {
					queryPos.add(sku);
				}

				List<CommerceInventoryWarehouseItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_S, finderArgs, list);
					}
				}
				else {
					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem = list.get(0);

					result = commerceInventoryWarehouseItem;

					cacheResult(commerceInventoryWarehouseItem);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_S, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CommerceInventoryWarehouseItem)result;
		}
	}

	/**
	 * Removes the commerce inventory warehouse item where commerceInventoryWarehouseId = &#63; and sku = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param sku the sku
	 * @return the commerce inventory warehouse item that was removed
	 */
	@Override
	public CommerceInventoryWarehouseItem removeByC_S(
			long commerceInventoryWarehouseId, String sku)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			findByC_S(commerceInventoryWarehouseId, sku);

		return remove(commerceInventoryWarehouseItem);
	}

	/**
	 * Returns the number of commerce inventory warehouse items where commerceInventoryWarehouseId = &#63; and sku = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param sku the sku
	 * @return the number of matching commerce inventory warehouse items
	 */
	@Override
	public int countByC_S(long commerceInventoryWarehouseId, String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {commerceInventoryWarehouseId, sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMMERCEINVENTORYWAREHOUSEID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				if (bindSku) {
					queryPos.add(sku);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_C_S_COMMERCEINVENTORYWAREHOUSEID_2 =
			"commerceInventoryWarehouseItem.commerceInventoryWarehouseId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SKU_2 =
		"commerceInventoryWarehouseItem.sku = ?";

	private static final String _FINDER_COLUMN_C_S_SKU_3 =
		"(commerceInventoryWarehouseItem.sku IS NULL OR commerceInventoryWarehouseItem.sku = '')";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce inventory warehouse item where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchInventoryWarehouseItemException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByC_ERC(companyId, externalReferenceCode);

		if (commerceInventoryWarehouseItem == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchInventoryWarehouseItemException(sb.toString());
		}

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Returns the commerce inventory warehouse item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce inventory warehouse item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_ERC, finderArgs, this);
		}

		if (result instanceof CommerceInventoryWarehouseItem) {
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				(CommerceInventoryWarehouseItem)result;

			if ((companyId != commerceInventoryWarehouseItem.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceInventoryWarehouseItem.
						getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommerceInventoryWarehouseItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommerceInventoryWarehouseItemPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem = list.get(0);

					result = commerceInventoryWarehouseItem;

					cacheResult(commerceInventoryWarehouseItem);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_ERC, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CommerceInventoryWarehouseItem)result;
		}
	}

	/**
	 * Removes the commerce inventory warehouse item where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce inventory warehouse item that was removed
	 */
	@Override
	public CommerceInventoryWarehouseItem removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			findByC_ERC(companyId, externalReferenceCode);

		return remove(commerceInventoryWarehouseItem);
	}

	/**
	 * Returns the number of commerce inventory warehouse items where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce inventory warehouse items
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commerceInventoryWarehouseItem.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceInventoryWarehouseItem.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceInventoryWarehouseItem.externalReferenceCode IS NULL OR commerceInventoryWarehouseItem.externalReferenceCode = '')";

	public CommerceInventoryWarehouseItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceInventoryWarehouseItemId", "CIWarehouseItemId");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(CommerceInventoryWarehouseItem.class);
	}

	/**
	 * Caches the commerce inventory warehouse item in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseItem the commerce inventory warehouse item
	 */
	@Override
	public void cacheResult(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		entityCache.putResult(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			commerceInventoryWarehouseItem.getPrimaryKey(),
			commerceInventoryWarehouseItem);

		finderCache.putResult(
			_finderPathFetchByC_S,
			new Object[] {
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				commerceInventoryWarehouseItem.getSku()
			},
			commerceInventoryWarehouseItem);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceInventoryWarehouseItem.getCompanyId(),
				commerceInventoryWarehouseItem.getExternalReferenceCode()
			},
			commerceInventoryWarehouseItem);

		commerceInventoryWarehouseItem.resetOriginalValues();
	}

	/**
	 * Caches the commerce inventory warehouse items in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseItems the commerce inventory warehouse items
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems) {

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				commerceInventoryWarehouseItems) {

			if (entityCache.getResult(
					CommerceInventoryWarehouseItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseItemImpl.class,
					commerceInventoryWarehouseItem.getPrimaryKey()) == null) {

				cacheResult(commerceInventoryWarehouseItem);
			}
			else {
				commerceInventoryWarehouseItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory warehouse items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryWarehouseItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce inventory warehouse item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		entityCache.removeResult(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			commerceInventoryWarehouseItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CommerceInventoryWarehouseItemModelImpl)
				commerceInventoryWarehouseItem,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				commerceInventoryWarehouseItems) {

			entityCache.removeResult(
				CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseItemImpl.class,
				commerceInventoryWarehouseItem.getPrimaryKey());

			clearUniqueFindersCache(
				(CommerceInventoryWarehouseItemModelImpl)
					commerceInventoryWarehouseItem,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceInventoryWarehouseItemModelImpl
			commerceInventoryWarehouseItemModelImpl) {

		Object[] args = new Object[] {
			commerceInventoryWarehouseItemModelImpl.
				getCommerceInventoryWarehouseId(),
			commerceInventoryWarehouseItemModelImpl.getSku()
		};

		finderCache.putResult(
			_finderPathCountByC_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_S, args,
			commerceInventoryWarehouseItemModelImpl, false);

		args = new Object[] {
			commerceInventoryWarehouseItemModelImpl.getCompanyId(),
			commerceInventoryWarehouseItemModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_ERC, args,
			commerceInventoryWarehouseItemModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceInventoryWarehouseItemModelImpl
			commerceInventoryWarehouseItemModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.
					getCommerceInventoryWarehouseId(),
				commerceInventoryWarehouseItemModelImpl.getSku()
			};

			finderCache.removeResult(_finderPathCountByC_S, args);
			finderCache.removeResult(_finderPathFetchByC_S, args);
		}

		if ((commerceInventoryWarehouseItemModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.
					getOriginalCommerceInventoryWarehouseId(),
				commerceInventoryWarehouseItemModelImpl.getOriginalSku()
			};

			finderCache.removeResult(_finderPathCountByC_S, args);
			finderCache.removeResult(_finderPathFetchByC_S, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.getCompanyId(),
				commerceInventoryWarehouseItemModelImpl.
					getExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}

		if ((commerceInventoryWarehouseItemModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_ERC.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.getOriginalCompanyId(),
				commerceInventoryWarehouseItemModelImpl.
					getOriginalExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}
	}

	/**
	 * Creates a new commerce inventory warehouse item with the primary key. Does not add the commerce inventory warehouse item to the database.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key for the new commerce inventory warehouse item
	 * @return the new commerce inventory warehouse item
	 */
	@Override
	public CommerceInventoryWarehouseItem create(
		long commerceInventoryWarehouseItemId) {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			new CommerceInventoryWarehouseItemImpl();

		commerceInventoryWarehouseItem.setNew(true);
		commerceInventoryWarehouseItem.setPrimaryKey(
			commerceInventoryWarehouseItemId);

		commerceInventoryWarehouseItem.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Removes the commerce inventory warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was removed
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem remove(
			long commerceInventoryWarehouseItemId)
		throws NoSuchInventoryWarehouseItemException {

		return remove((Serializable)commerceInventoryWarehouseItemId);
	}

	/**
	 * Removes the commerce inventory warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was removed
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem remove(Serializable primaryKey)
		throws NoSuchInventoryWarehouseItemException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				(CommerceInventoryWarehouseItem)session.get(
					CommerceInventoryWarehouseItemImpl.class, primaryKey);

			if (commerceInventoryWarehouseItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryWarehouseItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryWarehouseItem);
		}
		catch (NoSuchInventoryWarehouseItemException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CommerceInventoryWarehouseItem removeImpl(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryWarehouseItem)) {
				commerceInventoryWarehouseItem =
					(CommerceInventoryWarehouseItem)session.get(
						CommerceInventoryWarehouseItemImpl.class,
						commerceInventoryWarehouseItem.getPrimaryKeyObj());
			}

			if (commerceInventoryWarehouseItem != null) {
				session.delete(commerceInventoryWarehouseItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryWarehouseItem != null) {
			clearCache(commerceInventoryWarehouseItem);
		}

		return commerceInventoryWarehouseItem;
	}

	@Override
	public CommerceInventoryWarehouseItem updateImpl(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		boolean isNew = commerceInventoryWarehouseItem.isNew();

		if (!(commerceInventoryWarehouseItem instanceof
				CommerceInventoryWarehouseItemModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceInventoryWarehouseItem.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryWarehouseItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryWarehouseItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryWarehouseItem implementation " +
					commerceInventoryWarehouseItem.getClass());
		}

		CommerceInventoryWarehouseItemModelImpl
			commerceInventoryWarehouseItemModelImpl =
				(CommerceInventoryWarehouseItemModelImpl)
					commerceInventoryWarehouseItem;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceInventoryWarehouseItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceInventoryWarehouseItem.setCreateDate(now);
			}
			else {
				commerceInventoryWarehouseItem.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceInventoryWarehouseItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryWarehouseItem.setModifiedDate(now);
			}
			else {
				commerceInventoryWarehouseItem.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceInventoryWarehouseItem.isNew()) {
				session.save(commerceInventoryWarehouseItem);

				commerceInventoryWarehouseItem.setNew(false);
			}
			else {
				commerceInventoryWarehouseItem =
					(CommerceInventoryWarehouseItem)session.merge(
						commerceInventoryWarehouseItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceInventoryWarehouseItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.
					getCommerceInventoryWarehouseId()
			};

			finderCache.removeResult(
				_finderPathCountByCommerceInventoryWarehouseId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
				args);

			args = new Object[] {
				commerceInventoryWarehouseItemModelImpl.getCompanyId(),
				commerceInventoryWarehouseItemModelImpl.getSku()
			};

			finderCache.removeResult(_finderPathCountByCompanyId_Sku, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId_Sku, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceInventoryWarehouseItemModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.
						getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((commerceInventoryWarehouseItemModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCommerceInventoryWarehouseId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.
						getOriginalCommerceInventoryWarehouseId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceInventoryWarehouseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
					args);

				args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.
						getCommerceInventoryWarehouseId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceInventoryWarehouseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
					args);
			}

			if ((commerceInventoryWarehouseItemModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId_Sku.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.
						getOriginalCompanyId(),
					commerceInventoryWarehouseItemModelImpl.getOriginalSku()
				};

				finderCache.removeResult(_finderPathCountByCompanyId_Sku, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_Sku, args);

				args = new Object[] {
					commerceInventoryWarehouseItemModelImpl.getCompanyId(),
					commerceInventoryWarehouseItemModelImpl.getSku()
				};

				finderCache.removeResult(_finderPathCountByCompanyId_Sku, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_Sku, args);
			}
		}

		entityCache.putResult(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			commerceInventoryWarehouseItem.getPrimaryKey(),
			commerceInventoryWarehouseItem, false);

		clearUniqueFindersCache(commerceInventoryWarehouseItemModelImpl, false);
		cacheUniqueFindersCache(commerceInventoryWarehouseItemModelImpl);

		commerceInventoryWarehouseItem.resetOriginalValues();

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Returns the commerce inventory warehouse item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchInventoryWarehouseItemException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			fetchByPrimaryKey(primaryKey);

		if (commerceInventoryWarehouseItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryWarehouseItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Returns the commerce inventory warehouse item with the primary key or throws a <code>NoSuchInventoryWarehouseItemException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item
	 * @throws NoSuchInventoryWarehouseItemException if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem findByPrimaryKey(
			long commerceInventoryWarehouseItemId)
		throws NoSuchInventoryWarehouseItemException {

		return findByPrimaryKey((Serializable)commerceInventoryWarehouseItemId);
	}

	/**
	 * Returns the commerce inventory warehouse item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item, or <code>null</code> if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			(CommerceInventoryWarehouseItem)serializable;

		if (commerceInventoryWarehouseItem == null) {
			Session session = null;

			try {
				session = openSession();

				commerceInventoryWarehouseItem =
					(CommerceInventoryWarehouseItem)session.get(
						CommerceInventoryWarehouseItemImpl.class, primaryKey);

				if (commerceInventoryWarehouseItem != null) {
					cacheResult(commerceInventoryWarehouseItem);
				}
				else {
					entityCache.putResult(
						CommerceInventoryWarehouseItemModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceInventoryWarehouseItemImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceInventoryWarehouseItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseItemImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceInventoryWarehouseItem;
	}

	/**
	 * Returns the commerce inventory warehouse item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item, or <code>null</code> if a commerce inventory warehouse item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseItem fetchByPrimaryKey(
		long commerceInventoryWarehouseItemId) {

		return fetchByPrimaryKey(
			(Serializable)commerceInventoryWarehouseItemId);
	}

	@Override
	public Map<Serializable, CommerceInventoryWarehouseItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceInventoryWarehouseItem> map =
			new HashMap<Serializable, CommerceInventoryWarehouseItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				fetchByPrimaryKey(primaryKey);

			if (commerceInventoryWarehouseItem != null) {
				map.put(primaryKey, commerceInventoryWarehouseItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey,
						(CommerceInventoryWarehouseItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
					(List<CommerceInventoryWarehouseItem>)query.list()) {

				map.put(
					commerceInventoryWarehouseItem.getPrimaryKeyObj(),
					commerceInventoryWarehouseItem);

				cacheResult(commerceInventoryWarehouseItem);

				uncachedPrimaryKeys.remove(
					commerceInventoryWarehouseItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceInventoryWarehouseItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseItemImpl.class, primaryKey,
					nullModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce inventory warehouse items.
	 *
	 * @return the commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @return the range of commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouse items
	 */
	@Override
	public List<CommerceInventoryWarehouseItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommerceInventoryWarehouseItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseItem>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM;

				sql = sql.concat(
					CommerceInventoryWarehouseItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce inventory warehouse items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				findAll()) {

			remove(commerceInventoryWarehouseItem);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse items.
	 *
	 * @return the number of commerce inventory warehouse items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
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
		return CommerceInventoryWarehouseItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory warehouse item persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			CommerceInventoryWarehouseItemModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
				CommerceInventoryWarehouseItemImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
				CommerceInventoryWarehouseItemImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {Long.class.getName()},
				CommerceInventoryWarehouseItemModelImpl.
					COMMERCEINVENTORYWAREHOUSEID_COLUMN_BITMASK);

		_finderPathCountByCommerceInventoryWarehouseId = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceInventoryWarehouseId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId_Sku = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId_Sku",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId_Sku = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId_Sku",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryWarehouseItemModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseItemModelImpl.SKU_COLUMN_BITMASK);

		_finderPathCountByCompanyId_Sku = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId_Sku",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_S = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryWarehouseItemModelImpl.
				COMMERCEINVENTORYWAREHOUSEID_COLUMN_BITMASK |
			CommerceInventoryWarehouseItemModelImpl.SKU_COLUMN_BITMASK);

		_finderPathCountByC_S = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_ERC = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseItemImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryWarehouseItemModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseItemModelImpl.
				EXTERNALREFERENCECODE_COLUMN_BITMASK);

		_finderPathCountByC_ERC = new FinderPath(
			CommerceInventoryWarehouseItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceInventoryWarehouseItemImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM =
		"SELECT commerceInventoryWarehouseItem FROM CommerceInventoryWarehouseItem commerceInventoryWarehouseItem";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE_PKS_IN =
			"SELECT commerceInventoryWarehouseItem FROM CommerceInventoryWarehouseItem commerceInventoryWarehouseItem WHERE CIWarehouseItemId IN (";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE =
			"SELECT commerceInventoryWarehouseItem FROM CommerceInventoryWarehouseItem commerceInventoryWarehouseItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM =
		"SELECT COUNT(commerceInventoryWarehouseItem) FROM CommerceInventoryWarehouseItem commerceInventoryWarehouseItem";

	private static final String
		_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEITEM_WHERE =
			"SELECT COUNT(commerceInventoryWarehouseItem) FROM CommerceInventoryWarehouseItem commerceInventoryWarehouseItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryWarehouseItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryWarehouseItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryWarehouseItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceInventoryWarehouseItemId"});

}