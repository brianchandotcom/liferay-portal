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

import com.liferay.commerce.inventory.exception.NoSuchInventoryReplenishmentItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryReplenishmentItemPersistence;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the commerce inventory replenishment item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryReplenishmentItemPersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryReplenishmentItem>
	implements CommerceInventoryReplenishmentItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryReplenishmentItemUtil</code> to access the commerce inventory replenishment item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryReplenishmentItemImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath _finderPathCountByCommerceInventoryWarehouseId;

	/**
	 * Returns all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator,
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

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem : list) {

					if (commerceInventoryWarehouseId !=
							commerceInventoryReplenishmentItem.
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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Returns the first commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryReplenishmentItem>
					orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByCommerceInventoryWarehouseId_First(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator) {

		List<CommerceInventoryReplenishmentItem> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
			findByCommerceInventoryWarehouseId_Last(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryReplenishmentItem>
					orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByCommerceInventoryWarehouseId_Last(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem
		fetchByCommerceInventoryWarehouseId_Last(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator) {

		int count = countByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryReplenishmentItem> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory replenishment items before and after the current commerce inventory replenishment item in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the current commerce inventory replenishment item
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem[]
			findByCommerceInventoryWarehouseId_PrevAndNext(
				long commerceInventoryReplenishmentItemId,
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryReplenishmentItem>
					orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByPrimaryKey(commerceInventoryReplenishmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem[] array =
				new CommerceInventoryReplenishmentItemImpl[3];

			array[0] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryReplenishmentItem,
				commerceInventoryWarehouseId, orderByComparator, true);

			array[1] = commerceInventoryReplenishmentItem;

			array[2] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryReplenishmentItem,
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

	protected CommerceInventoryReplenishmentItem
		getByCommerceInventoryWarehouseId_PrevAndNext(
			Session session,
			CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem,
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

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
			sb.append(
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
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
						commerceInventoryReplenishmentItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryReplenishmentItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory replenishment items where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	@Override
	public void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					findByCommerceInventoryWarehouseId(
						commerceInventoryWarehouseId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		FinderPath finderPath = _finderPathCountByCommerceInventoryWarehouseId;

		Object[] finderArgs = new Object[] {commerceInventoryWarehouseId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

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
			"commerceInventoryReplenishmentItem.commerceInventoryWarehouseId = ?";

	private FinderPath _finderPathWithPaginationFindBySku;
	private FinderPath _finderPathWithoutPaginationFindBySku;
	private FinderPath _finderPathCountBySku;

	/**
	 * Returns all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(String sku) {
		return findBySku(sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end) {

		return findBySku(sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findBySku(sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySku;
				finderArgs = new Object[] {sku};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySku;
			finderArgs = new Object[] {sku, start, end, orderByComparator};
		}

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem : list) {

					if (!sku.equals(
							commerceInventoryReplenishmentItem.getSku())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_SKU_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_SKU_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSku) {
					queryPos.add(sku);
				}

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findBySku_First(
			String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchBySku_First(sku, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchBySku_First(
		String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		List<CommerceInventoryReplenishmentItem> list = findBySku(
			sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findBySku_Last(
			String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchBySku_Last(sku, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchBySku_Last(
		String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		int count = countBySku(sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryReplenishmentItem> list = findBySku(
			sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory replenishment items before and after the current commerce inventory replenishment item in the ordered set where sku = &#63;.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the current commerce inventory replenishment item
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem[] findBySku_PrevAndNext(
			long commerceInventoryReplenishmentItemId, String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		sku = Objects.toString(sku, "");

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByPrimaryKey(commerceInventoryReplenishmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem[] array =
				new CommerceInventoryReplenishmentItemImpl[3];

			array[0] = getBySku_PrevAndNext(
				session, commerceInventoryReplenishmentItem, sku,
				orderByComparator, true);

			array[1] = commerceInventoryReplenishmentItem;

			array[2] = getBySku_PrevAndNext(
				session, commerceInventoryReplenishmentItem, sku,
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

	protected CommerceInventoryReplenishmentItem getBySku_PrevAndNext(
		Session session,
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem,
		String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_SKU_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_SKU_SKU_2);
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
			sb.append(
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSku) {
			queryPos.add(sku);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryReplenishmentItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryReplenishmentItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory replenishment items where sku = &#63; from the database.
	 *
	 * @param sku the sku
	 */
	@Override
	public void removeBySku(String sku) {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					findBySku(
						sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countBySku(String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountBySku;

		Object[] finderArgs = new Object[] {sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_SKU_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_SKU_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_SKU_SKU_2 =
		"commerceInventoryReplenishmentItem.sku = ?";

	private static final String _FINDER_COLUMN_SKU_SKU_3 =
		"(commerceInventoryReplenishmentItem.sku IS NULL OR commerceInventoryReplenishmentItem.sku = '')";

	private FinderPath _finderPathWithPaginationFindByAvailabilityDate;
	private FinderPath _finderPathWithoutPaginationFindByAvailabilityDate;
	private FinderPath _finderPathCountByAvailabilityDate;

	/**
	 * Returns all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate) {

		return findByAvailabilityDate(
			availabilityDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end) {

		return findByAvailabilityDate(availabilityDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByAvailabilityDate(
			availabilityDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByAvailabilityDate(
		Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAvailabilityDate;
				finderArgs = new Object[] {_getTime(availabilityDate)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAvailabilityDate;
			finderArgs = new Object[] {
				_getTime(availabilityDate), start, end, orderByComparator
			};
		}

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem : list) {

					if (!Objects.equals(
							availabilityDate,
							commerceInventoryReplenishmentItem.
								getAvailabilityDate())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindAvailabilityDate = false;

			if (availabilityDate == null) {
				sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_1);
			}
			else {
				bindAvailabilityDate = true;

				sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAvailabilityDate) {
					queryPos.add(new Timestamp(availabilityDate.getTime()));
				}

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByAvailabilityDate_First(
			Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByAvailabilityDate_First(availabilityDate, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("availabilityDate=");
		sb.append(availabilityDate);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByAvailabilityDate_First(
		Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		List<CommerceInventoryReplenishmentItem> list = findByAvailabilityDate(
			availabilityDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByAvailabilityDate_Last(
			Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByAvailabilityDate_Last(availabilityDate, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("availabilityDate=");
		sb.append(availabilityDate);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByAvailabilityDate_Last(
		Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		int count = countByAvailabilityDate(availabilityDate);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryReplenishmentItem> list = findByAvailabilityDate(
			availabilityDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory replenishment items before and after the current commerce inventory replenishment item in the ordered set where availabilityDate = &#63;.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the current commerce inventory replenishment item
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem[]
			findByAvailabilityDate_PrevAndNext(
				long commerceInventoryReplenishmentItemId,
				Date availabilityDate,
				OrderByComparator<CommerceInventoryReplenishmentItem>
					orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByPrimaryKey(commerceInventoryReplenishmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem[] array =
				new CommerceInventoryReplenishmentItemImpl[3];

			array[0] = getByAvailabilityDate_PrevAndNext(
				session, commerceInventoryReplenishmentItem, availabilityDate,
				orderByComparator, true);

			array[1] = commerceInventoryReplenishmentItem;

			array[2] = getByAvailabilityDate_PrevAndNext(
				session, commerceInventoryReplenishmentItem, availabilityDate,
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

	protected CommerceInventoryReplenishmentItem
		getByAvailabilityDate_PrevAndNext(
			Session session,
			CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem,
			Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

		boolean bindAvailabilityDate = false;

		if (availabilityDate == null) {
			sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_1);
		}
		else {
			bindAvailabilityDate = true;

			sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_2);
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
			sb.append(
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindAvailabilityDate) {
			queryPos.add(new Timestamp(availabilityDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryReplenishmentItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryReplenishmentItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory replenishment items where availabilityDate = &#63; from the database.
	 *
	 * @param availabilityDate the availability date
	 */
	@Override
	public void removeByAvailabilityDate(Date availabilityDate) {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					findByAvailabilityDate(
						availabilityDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items where availabilityDate = &#63;.
	 *
	 * @param availabilityDate the availability date
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByAvailabilityDate(Date availabilityDate) {
		FinderPath finderPath = _finderPathCountByAvailabilityDate;

		Object[] finderArgs = new Object[] {_getTime(availabilityDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindAvailabilityDate = false;

			if (availabilityDate == null) {
				sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_1);
			}
			else {
				bindAvailabilityDate = true;

				sb.append(_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAvailabilityDate) {
					queryPos.add(new Timestamp(availabilityDate.getTime()));
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
		_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_1 =
			"commerceInventoryReplenishmentItem.availabilityDate IS NULL";

	private static final String
		_FINDER_COLUMN_AVAILABILITYDATE_AVAILABILITYDATE_2 =
			"commerceInventoryReplenishmentItem.availabilityDate = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the commerce inventory replenishment items where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S(
		long companyId, String sku) {

		return findByC_S(
			companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S(
		long companyId, String sku, int start, int end) {

		return findByC_S(companyId, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByC_S(companyId, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, sku};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, sku, start, end, orderByComparator
			};
		}

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem : list) {

					if ((companyId !=
							commerceInventoryReplenishmentItem.
								getCompanyId()) ||
						!sku.equals(
							commerceInventoryReplenishmentItem.getSku())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Returns the first commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByC_S_First(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByC_S_First(companyId, sku, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByC_S_First(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		List<CommerceInventoryReplenishmentItem> list = findByC_S(
			companyId, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByC_S_Last(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByC_S_Last(companyId, sku, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByC_S_Last(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		int count = countByC_S(companyId, sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryReplenishmentItem> list = findByC_S(
			companyId, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory replenishment items before and after the current commerce inventory replenishment item in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the current commerce inventory replenishment item
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem[] findByC_S_PrevAndNext(
			long commerceInventoryReplenishmentItemId, long companyId,
			String sku,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		sku = Objects.toString(sku, "");

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByPrimaryKey(commerceInventoryReplenishmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem[] array =
				new CommerceInventoryReplenishmentItemImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, commerceInventoryReplenishmentItem, companyId, sku,
				orderByComparator, true);

			array[1] = commerceInventoryReplenishmentItem;

			array[2] = getByC_S_PrevAndNext(
				session, commerceInventoryReplenishmentItem, companyId, sku,
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

	protected CommerceInventoryReplenishmentItem getByC_S_PrevAndNext(
		Session session,
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem,
		long companyId, String sku,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_C_S_SKU_2);
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
			sb.append(
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
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
						commerceInventoryReplenishmentItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryReplenishmentItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory replenishment items where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	@Override
	public void removeByC_S(long companyId, String sku) {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					findByC_S(
						companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByC_S(long companyId, String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"commerceInventoryReplenishmentItem.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SKU_2 =
		"commerceInventoryReplenishmentItem.sku = ?";

	private static final String _FINDER_COLUMN_C_S_SKU_3 =
		"(commerceInventoryReplenishmentItem.sku IS NULL OR commerceInventoryReplenishmentItem.sku = '')";

	private FinderPath _finderPathWithPaginationFindByS_AD;
	private FinderPath _finderPathWithoutPaginationFindByS_AD;
	private FinderPath _finderPathCountByS_AD;

	/**
	 * Returns all the commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @return the matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByS_AD(
		String sku, Date availabilityDate) {

		return findByS_AD(
			sku, availabilityDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByS_AD(
		String sku, Date availabilityDate, int start, int end) {

		return findByS_AD(sku, availabilityDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByS_AD(
		String sku, Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findByS_AD(
			sku, availabilityDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findByS_AD(
		String sku, Date availabilityDate, int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_AD;
				finderArgs = new Object[] {sku, _getTime(availabilityDate)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_AD;
			finderArgs = new Object[] {
				sku, _getTime(availabilityDate), start, end, orderByComparator
			};
		}

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem : list) {

					if (!sku.equals(
							commerceInventoryReplenishmentItem.getSku()) ||
						!Objects.equals(
							availabilityDate,
							commerceInventoryReplenishmentItem.
								getAvailabilityDate())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_AD_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_S_AD_SKU_2);
			}

			boolean bindAvailabilityDate = false;

			if (availabilityDate == null) {
				sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_1);
			}
			else {
				bindAvailabilityDate = true;

				sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSku) {
					queryPos.add(sku);
				}

				if (bindAvailabilityDate) {
					queryPos.add(new Timestamp(availabilityDate.getTime()));
				}

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByS_AD_First(
			String sku, Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByS_AD_First(sku, availabilityDate, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append(", availabilityDate=");
		sb.append(availabilityDate);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory replenishment item in the ordered set where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByS_AD_First(
		String sku, Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		List<CommerceInventoryReplenishmentItem> list = findByS_AD(
			sku, availabilityDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByS_AD_Last(
			String sku, Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByS_AD_Last(sku, availabilityDate, orderByComparator);

		if (commerceInventoryReplenishmentItem != null) {
			return commerceInventoryReplenishmentItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append(", availabilityDate=");
		sb.append(availabilityDate);

		sb.append("}");

		throw new NoSuchInventoryReplenishmentItemException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory replenishment item in the ordered set where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory replenishment item, or <code>null</code> if a matching commerce inventory replenishment item could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByS_AD_Last(
		String sku, Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		int count = countByS_AD(sku, availabilityDate);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryReplenishmentItem> list = findByS_AD(
			sku, availabilityDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory replenishment items before and after the current commerce inventory replenishment item in the ordered set where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the current commerce inventory replenishment item
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem[] findByS_AD_PrevAndNext(
			long commerceInventoryReplenishmentItemId, String sku,
			Date availabilityDate,
			OrderByComparator<CommerceInventoryReplenishmentItem>
				orderByComparator)
		throws NoSuchInventoryReplenishmentItemException {

		sku = Objects.toString(sku, "");

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			findByPrimaryKey(commerceInventoryReplenishmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem[] array =
				new CommerceInventoryReplenishmentItemImpl[3];

			array[0] = getByS_AD_PrevAndNext(
				session, commerceInventoryReplenishmentItem, sku,
				availabilityDate, orderByComparator, true);

			array[1] = commerceInventoryReplenishmentItem;

			array[2] = getByS_AD_PrevAndNext(
				session, commerceInventoryReplenishmentItem, sku,
				availabilityDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryReplenishmentItem getByS_AD_PrevAndNext(
		Session session,
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem,
		String sku, Date availabilityDate,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_S_AD_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_S_AD_SKU_2);
		}

		boolean bindAvailabilityDate = false;

		if (availabilityDate == null) {
			sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_1);
		}
		else {
			bindAvailabilityDate = true;

			sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_2);
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
			sb.append(
				CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSku) {
			queryPos.add(sku);
		}

		if (bindAvailabilityDate) {
			queryPos.add(new Timestamp(availabilityDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryReplenishmentItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryReplenishmentItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63; from the database.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 */
	@Override
	public void removeByS_AD(String sku, Date availabilityDate) {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					findByS_AD(
						sku, availabilityDate, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items where sku = &#63; and availabilityDate = &#63;.
	 *
	 * @param sku the sku
	 * @param availabilityDate the availability date
	 * @return the number of matching commerce inventory replenishment items
	 */
	@Override
	public int countByS_AD(String sku, Date availabilityDate) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByS_AD;

		Object[] finderArgs = new Object[] {sku, _getTime(availabilityDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_AD_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_S_AD_SKU_2);
			}

			boolean bindAvailabilityDate = false;

			if (availabilityDate == null) {
				sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_1);
			}
			else {
				bindAvailabilityDate = true;

				sb.append(_FINDER_COLUMN_S_AD_AVAILABILITYDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSku) {
					queryPos.add(sku);
				}

				if (bindAvailabilityDate) {
					queryPos.add(new Timestamp(availabilityDate.getTime()));
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

	private static final String _FINDER_COLUMN_S_AD_SKU_2 =
		"commerceInventoryReplenishmentItem.sku = ? AND ";

	private static final String _FINDER_COLUMN_S_AD_SKU_3 =
		"(commerceInventoryReplenishmentItem.sku IS NULL OR commerceInventoryReplenishmentItem.sku = '') AND ";

	private static final String _FINDER_COLUMN_S_AD_AVAILABILITYDATE_1 =
		"commerceInventoryReplenishmentItem.availabilityDate IS NULL";

	private static final String _FINDER_COLUMN_S_AD_AVAILABILITYDATE_2 =
		"commerceInventoryReplenishmentItem.availabilityDate = ?";

	public CommerceInventoryReplenishmentItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceInventoryReplenishmentItemId", "CIReplenishmentItemId");

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

		setModelClass(CommerceInventoryReplenishmentItem.class);
	}

	/**
	 * Caches the commerce inventory replenishment item in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryReplenishmentItem the commerce inventory replenishment item
	 */
	@Override
	public void cacheResult(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		entityCache.putResult(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			commerceInventoryReplenishmentItem.getPrimaryKey(),
			commerceInventoryReplenishmentItem);

		commerceInventoryReplenishmentItem.resetOriginalValues();
	}

	/**
	 * Caches the commerce inventory replenishment items in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryReplenishmentItems the commerce inventory replenishment items
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems) {

		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					commerceInventoryReplenishmentItems) {

			if (entityCache.getResult(
					CommerceInventoryReplenishmentItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryReplenishmentItemImpl.class,
					commerceInventoryReplenishmentItem.getPrimaryKey()) ==
						null) {

				cacheResult(commerceInventoryReplenishmentItem);
			}
			else {
				commerceInventoryReplenishmentItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory replenishment items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryReplenishmentItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce inventory replenishment item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		entityCache.removeResult(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			commerceInventoryReplenishmentItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					commerceInventoryReplenishmentItems) {

			entityCache.removeResult(
				CommerceInventoryReplenishmentItemModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemImpl.class,
				commerceInventoryReplenishmentItem.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryReplenishmentItemModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce inventory replenishment item with the primary key. Does not add the commerce inventory replenishment item to the database.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key for the new commerce inventory replenishment item
	 * @return the new commerce inventory replenishment item
	 */
	@Override
	public CommerceInventoryReplenishmentItem create(
		long commerceInventoryReplenishmentItemId) {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			new CommerceInventoryReplenishmentItemImpl();

		commerceInventoryReplenishmentItem.setNew(true);
		commerceInventoryReplenishmentItem.setPrimaryKey(
			commerceInventoryReplenishmentItemId);

		commerceInventoryReplenishmentItem.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Removes the commerce inventory replenishment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item that was removed
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem remove(
			long commerceInventoryReplenishmentItemId)
		throws NoSuchInventoryReplenishmentItemException {

		return remove((Serializable)commerceInventoryReplenishmentItemId);
	}

	/**
	 * Removes the commerce inventory replenishment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item that was removed
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem remove(Serializable primaryKey)
		throws NoSuchInventoryReplenishmentItemException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.get(
						CommerceInventoryReplenishmentItemImpl.class,
						primaryKey);

			if (commerceInventoryReplenishmentItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryReplenishmentItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryReplenishmentItem);
		}
		catch (NoSuchInventoryReplenishmentItemException
					noSuchEntityException) {

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
	protected CommerceInventoryReplenishmentItem removeImpl(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryReplenishmentItem)) {
				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.get(
						CommerceInventoryReplenishmentItemImpl.class,
						commerceInventoryReplenishmentItem.getPrimaryKeyObj());
			}

			if (commerceInventoryReplenishmentItem != null) {
				session.delete(commerceInventoryReplenishmentItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryReplenishmentItem != null) {
			clearCache(commerceInventoryReplenishmentItem);
		}

		return commerceInventoryReplenishmentItem;
	}

	@Override
	public CommerceInventoryReplenishmentItem updateImpl(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		boolean isNew = commerceInventoryReplenishmentItem.isNew();

		if (!(commerceInventoryReplenishmentItem instanceof
				CommerceInventoryReplenishmentItemModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceInventoryReplenishmentItem.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryReplenishmentItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryReplenishmentItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryReplenishmentItem implementation " +
					commerceInventoryReplenishmentItem.getClass());
		}

		CommerceInventoryReplenishmentItemModelImpl
			commerceInventoryReplenishmentItemModelImpl =
				(CommerceInventoryReplenishmentItemModelImpl)
					commerceInventoryReplenishmentItem;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(commerceInventoryReplenishmentItem.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceInventoryReplenishmentItem.setCreateDate(now);
			}
			else {
				commerceInventoryReplenishmentItem.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceInventoryReplenishmentItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryReplenishmentItem.setModifiedDate(now);
			}
			else {
				commerceInventoryReplenishmentItem.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceInventoryReplenishmentItem.isNew()) {
				session.save(commerceInventoryReplenishmentItem);

				commerceInventoryReplenishmentItem.setNew(false);
			}
			else {
				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.merge(
						commerceInventoryReplenishmentItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceInventoryReplenishmentItemModelImpl.
				COLUMN_BITMASK_ENABLED) {

			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceInventoryReplenishmentItemModelImpl.
					getCommerceInventoryWarehouseId()
			};

			finderCache.removeResult(
				_finderPathCountByCommerceInventoryWarehouseId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
				args);

			args = new Object[] {
				commerceInventoryReplenishmentItemModelImpl.getSku()
			};

			finderCache.removeResult(_finderPathCountBySku, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySku, args);

			args = new Object[] {
				commerceInventoryReplenishmentItemModelImpl.
					getAvailabilityDate()
			};

			finderCache.removeResult(_finderPathCountByAvailabilityDate, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAvailabilityDate, args);

			args = new Object[] {
				commerceInventoryReplenishmentItemModelImpl.getCompanyId(),
				commerceInventoryReplenishmentItemModelImpl.getSku()
			};

			finderCache.removeResult(_finderPathCountByC_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_S, args);

			args = new Object[] {
				commerceInventoryReplenishmentItemModelImpl.getSku(),
				commerceInventoryReplenishmentItemModelImpl.
					getAvailabilityDate()
			};

			finderCache.removeResult(_finderPathCountByS_AD, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByS_AD, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceInventoryReplenishmentItemModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCommerceInventoryWarehouseId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getOriginalCommerceInventoryWarehouseId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceInventoryWarehouseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
					args);

				args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getCommerceInventoryWarehouseId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceInventoryWarehouseId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId,
					args);
			}

			if ((commerceInventoryReplenishmentItemModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySku.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.getOriginalSku()
				};

				finderCache.removeResult(_finderPathCountBySku, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySku, args);

				args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.getSku()
				};

				finderCache.removeResult(_finderPathCountBySku, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySku, args);
			}

			if ((commerceInventoryReplenishmentItemModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAvailabilityDate.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getOriginalAvailabilityDate()
				};

				finderCache.removeResult(
					_finderPathCountByAvailabilityDate, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAvailabilityDate, args);

				args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getAvailabilityDate()
				};

				finderCache.removeResult(
					_finderPathCountByAvailabilityDate, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAvailabilityDate, args);
			}

			if ((commerceInventoryReplenishmentItemModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getOriginalCompanyId(),
					commerceInventoryReplenishmentItemModelImpl.getOriginalSku()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);

				args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.getCompanyId(),
					commerceInventoryReplenishmentItemModelImpl.getSku()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);
			}

			if ((commerceInventoryReplenishmentItemModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByS_AD.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.
						getOriginalSku(),
					commerceInventoryReplenishmentItemModelImpl.
						getOriginalAvailabilityDate()
				};

				finderCache.removeResult(_finderPathCountByS_AD, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_AD, args);

				args = new Object[] {
					commerceInventoryReplenishmentItemModelImpl.getSku(),
					commerceInventoryReplenishmentItemModelImpl.
						getAvailabilityDate()
				};

				finderCache.removeResult(_finderPathCountByS_AD, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_AD, args);
			}
		}

		entityCache.putResult(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			commerceInventoryReplenishmentItem.getPrimaryKey(),
			commerceInventoryReplenishmentItem, false);

		commerceInventoryReplenishmentItem.resetOriginalValues();

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchInventoryReplenishmentItemException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			fetchByPrimaryKey(primaryKey);

		if (commerceInventoryReplenishmentItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryReplenishmentItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or throws a <code>NoSuchInventoryReplenishmentItemException</code> if it could not be found.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item
	 * @throws NoSuchInventoryReplenishmentItemException if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem findByPrimaryKey(
			long commerceInventoryReplenishmentItemId)
		throws NoSuchInventoryReplenishmentItemException {

		return findByPrimaryKey(
			(Serializable)commerceInventoryReplenishmentItemId);
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item, or <code>null</code> if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			(CommerceInventoryReplenishmentItem)serializable;

		if (commerceInventoryReplenishmentItem == null) {
			Session session = null;

			try {
				session = openSession();

				commerceInventoryReplenishmentItem =
					(CommerceInventoryReplenishmentItem)session.get(
						CommerceInventoryReplenishmentItemImpl.class,
						primaryKey);

				if (commerceInventoryReplenishmentItem != null) {
					cacheResult(commerceInventoryReplenishmentItem);
				}
				else {
					entityCache.putResult(
						CommerceInventoryReplenishmentItemModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceInventoryReplenishmentItemImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceInventoryReplenishmentItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryReplenishmentItemImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceInventoryReplenishmentItem;
	}

	/**
	 * Returns the commerce inventory replenishment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryReplenishmentItemId the primary key of the commerce inventory replenishment item
	 * @return the commerce inventory replenishment item, or <code>null</code> if a commerce inventory replenishment item with the primary key could not be found
	 */
	@Override
	public CommerceInventoryReplenishmentItem fetchByPrimaryKey(
		long commerceInventoryReplenishmentItemId) {

		return fetchByPrimaryKey(
			(Serializable)commerceInventoryReplenishmentItemId);
	}

	@Override
	public Map<Serializable, CommerceInventoryReplenishmentItem>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceInventoryReplenishmentItem> map =
			new HashMap<Serializable, CommerceInventoryReplenishmentItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem = fetchByPrimaryKey(
					primaryKey);

			if (commerceInventoryReplenishmentItem != null) {
				map.put(primaryKey, commerceInventoryReplenishmentItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceInventoryReplenishmentItemModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemImpl.class, primaryKey);

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
						(CommerceInventoryReplenishmentItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE_PKS_IN);

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

			for (CommerceInventoryReplenishmentItem
					commerceInventoryReplenishmentItem :
						(List<CommerceInventoryReplenishmentItem>)
							query.list()) {

				map.put(
					commerceInventoryReplenishmentItem.getPrimaryKeyObj(),
					commerceInventoryReplenishmentItem);

				cacheResult(commerceInventoryReplenishmentItem);

				uncachedPrimaryKeys.remove(
					commerceInventoryReplenishmentItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceInventoryReplenishmentItemModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceInventoryReplenishmentItemImpl.class, primaryKey,
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
	 * Returns all the commerce inventory replenishment items.
	 *
	 * @return the commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @return the range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory replenishment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryReplenishmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory replenishment items
	 * @param end the upper bound of the range of commerce inventory replenishment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory replenishment items
	 */
	@Override
	public List<CommerceInventoryReplenishmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryReplenishmentItem> orderByComparator,
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

		List<CommerceInventoryReplenishmentItem> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceInventoryReplenishmentItem>)finderCache.getResult(
					finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM;

				sql = sql.concat(
					CommerceInventoryReplenishmentItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryReplenishmentItem>)QueryUtil.list(
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
	 * Removes all the commerce inventory replenishment items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem : findAll()) {

			remove(commerceInventoryReplenishmentItem);
		}
	}

	/**
	 * Returns the number of commerce inventory replenishment items.
	 *
	 * @return the number of commerce inventory replenishment items
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
					_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM);

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
		return CommerceInventoryReplenishmentItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory replenishment item persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				CommerceInventoryReplenishmentItemModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemModelImpl.
					FINDER_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				CommerceInventoryReplenishmentItemModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemModelImpl.
					FINDER_CACHE_ENABLED,
				CommerceInventoryReplenishmentItemImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {Long.class.getName()},
				CommerceInventoryReplenishmentItemModelImpl.
					COMMERCEINVENTORYWAREHOUSEID_COLUMN_BITMASK);

		_finderPathCountByCommerceInventoryWarehouseId = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceInventoryWarehouseId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySku = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySku",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySku = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySku",
			new String[] {String.class.getName()},
			CommerceInventoryReplenishmentItemModelImpl.SKU_COLUMN_BITMASK);

		_finderPathCountBySku = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySku",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByAvailabilityDate = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAvailabilityDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAvailabilityDate = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAvailabilityDate",
			new String[] {Date.class.getName()},
			CommerceInventoryReplenishmentItemModelImpl.
				AVAILABILITYDATE_COLUMN_BITMASK);

		_finderPathCountByAvailabilityDate = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAvailabilityDate", new String[] {Date.class.getName()});

		_finderPathWithPaginationFindByC_S = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryReplenishmentItemModelImpl.
				COMPANYID_COLUMN_BITMASK |
			CommerceInventoryReplenishmentItemModelImpl.SKU_COLUMN_BITMASK);

		_finderPathCountByC_S = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByS_AD = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_AD",
			new String[] {
				String.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByS_AD = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_AD",
			new String[] {String.class.getName(), Date.class.getName()},
			CommerceInventoryReplenishmentItemModelImpl.SKU_COLUMN_BITMASK |
			CommerceInventoryReplenishmentItemModelImpl.
				AVAILABILITYDATE_COLUMN_BITMASK);

		_finderPathCountByS_AD = new FinderPath(
			CommerceInventoryReplenishmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryReplenishmentItemModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByS_AD",
			new String[] {String.class.getName(), Date.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceInventoryReplenishmentItemImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM =
		"SELECT commerceInventoryReplenishmentItem FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE_PKS_IN =
			"SELECT commerceInventoryReplenishmentItem FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem WHERE CIReplenishmentItemId IN (";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE =
			"SELECT commerceInventoryReplenishmentItem FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM =
		"SELECT COUNT(commerceInventoryReplenishmentItem) FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem";

	private static final String
		_SQL_COUNT_COMMERCEINVENTORYREPLENISHMENTITEM_WHERE =
			"SELECT COUNT(commerceInventoryReplenishmentItem) FROM CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryReplenishmentItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryReplenishmentItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryReplenishmentItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryReplenishmentItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceInventoryReplenishmentItemId"});

}