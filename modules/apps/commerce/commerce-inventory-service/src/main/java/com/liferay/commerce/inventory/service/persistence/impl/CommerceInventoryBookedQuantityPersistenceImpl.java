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

import com.liferay.commerce.inventory.exception.NoSuchInventoryBookedQuantityException;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantityTable;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryBookedQuantityImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryBookedQuantityModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryBookedQuantityPersistence;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce inventory booked quantity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryBookedQuantityPersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryBookedQuantity>
	implements CommerceInventoryBookedQuantityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryBookedQuantityUtil</code> to access the commerce inventory booked quantity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryBookedQuantityImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBySku;
	private FinderPath _finderPathWithoutPaginationFindBySku;
	private FinderPath _finderPathCountBySku;

	/**
	 * Returns all the commerce inventory booked quantities where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findBySku(String sku) {
		return findBySku(sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory booked quantities where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @return the range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findBySku(
		String sku, int start, int end) {

		return findBySku(sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		return findBySku(sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findBySku(
		String sku, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		List<CommerceInventoryBookedQuantity> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryBookedQuantity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryBookedQuantity
						commerceInventoryBookedQuantity : list) {

					if (!sku.equals(commerceInventoryBookedQuantity.getSku())) {
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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
					CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceInventoryBookedQuantity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findBySku_First(
			String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchBySku_First(sku, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchBySku_First(
		String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		List<CommerceInventoryBookedQuantity> list = findBySku(
			sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findBySku_Last(
			String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchBySku_Last(sku, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where sku = &#63;.
	 *
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchBySku_Last(
		String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		int count = countBySku(sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryBookedQuantity> list = findBySku(
			sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory booked quantities before and after the current commerce inventory booked quantity in the ordered set where sku = &#63;.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the current commerce inventory booked quantity
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity[] findBySku_PrevAndNext(
			long commerceInventoryBookedQuantityId, String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		sku = Objects.toString(sku, "");

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			findByPrimaryKey(commerceInventoryBookedQuantityId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryBookedQuantity[] array =
				new CommerceInventoryBookedQuantityImpl[3];

			array[0] = getBySku_PrevAndNext(
				session, commerceInventoryBookedQuantity, sku,
				orderByComparator, true);

			array[1] = commerceInventoryBookedQuantity;

			array[2] = getBySku_PrevAndNext(
				session, commerceInventoryBookedQuantity, sku,
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

	protected CommerceInventoryBookedQuantity getBySku_PrevAndNext(
		Session session,
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity,
		String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
			sb.append(CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
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
						commerceInventoryBookedQuantity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryBookedQuantity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory booked quantities where sku = &#63; from the database.
	 *
	 * @param sku the sku
	 */
	@Override
	public void removeBySku(String sku) {
		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				findBySku(sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryBookedQuantity);
		}
	}

	/**
	 * Returns the number of commerce inventory booked quantities where sku = &#63;.
	 *
	 * @param sku the sku
	 * @return the number of matching commerce inventory booked quantities
	 */
	@Override
	public int countBySku(String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountBySku;

		Object[] finderArgs = new Object[] {sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SKU_SKU_2 =
		"commerceInventoryBookedQuantity.sku = ?";

	private static final String _FINDER_COLUMN_SKU_SKU_3 =
		"(commerceInventoryBookedQuantity.sku IS NULL OR commerceInventoryBookedQuantity.sku = '')";

	private FinderPath _finderPathWithPaginationFindByLtExpirationDate;
	private FinderPath _finderPathWithPaginationCountByLtExpirationDate;

	/**
	 * Returns all the commerce inventory booked quantities where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByLtExpirationDate(
		Date expirationDate) {

		return findByLtExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory booked quantities where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @return the range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByLtExpirationDate(
		Date expirationDate, int start, int end) {

		return findByLtExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		return findByLtExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtExpirationDate;
		finderArgs = new Object[] {
			_getTime(expirationDate), start, end, orderByComparator
		};

		List<CommerceInventoryBookedQuantity> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryBookedQuantity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryBookedQuantity
						commerceInventoryBookedQuantity : list) {

					if (expirationDate.getTime() <=
							commerceInventoryBookedQuantity.getExpirationDate(
							).getTime()) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<CommerceInventoryBookedQuantity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByLtExpirationDate_First(
			Date expirationDate,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchByLtExpirationDate_First(expirationDate, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchByLtExpirationDate_First(
		Date expirationDate,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		List<CommerceInventoryBookedQuantity> list = findByLtExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByLtExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchByLtExpirationDate_Last(expirationDate, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchByLtExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		int count = countByLtExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryBookedQuantity> list = findByLtExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory booked quantities before and after the current commerce inventory booked quantity in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the current commerce inventory booked quantity
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity[] findByLtExpirationDate_PrevAndNext(
			long commerceInventoryBookedQuantityId, Date expirationDate,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			findByPrimaryKey(commerceInventoryBookedQuantityId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryBookedQuantity[] array =
				new CommerceInventoryBookedQuantityImpl[3];

			array[0] = getByLtExpirationDate_PrevAndNext(
				session, commerceInventoryBookedQuantity, expirationDate,
				orderByComparator, true);

			array[1] = commerceInventoryBookedQuantity;

			array[2] = getByLtExpirationDate_PrevAndNext(
				session, commerceInventoryBookedQuantity, expirationDate,
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

	protected CommerceInventoryBookedQuantity getByLtExpirationDate_PrevAndNext(
		Session session,
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity,
		Date expirationDate,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
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
			sb.append(CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryBookedQuantity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryBookedQuantity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory booked quantities where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByLtExpirationDate(Date expirationDate) {
		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				findByLtExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceInventoryBookedQuantity);
		}
	}

	/**
	 * Returns the number of commerce inventory booked quantities where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching commerce inventory booked quantities
	 */
	@Override
	public int countByLtExpirationDate(Date expirationDate) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByLtExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1 =
			"commerceInventoryBookedQuantity.expirationDate IS NULL";

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2 =
			"commerceInventoryBookedQuantity.expirationDate < ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the commerce inventory booked quantities where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByC_S(
		long companyId, String sku) {

		return findByC_S(
			companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory booked quantities where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @return the range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByC_S(
		long companyId, String sku, int start, int end) {

		return findByC_S(companyId, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		return findByC_S(companyId, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		List<CommerceInventoryBookedQuantity> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryBookedQuantity>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryBookedQuantity
						commerceInventoryBookedQuantity : list) {

					if ((companyId !=
							commerceInventoryBookedQuantity.getCompanyId()) ||
						!sku.equals(commerceInventoryBookedQuantity.getSku())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
					CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceInventoryBookedQuantity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByC_S_First(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchByC_S_First(companyId, sku, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory booked quantity in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchByC_S_First(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		List<CommerceInventoryBookedQuantity> list = findByC_S(
			companyId, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByC_S_Last(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchByC_S_Last(companyId, sku, orderByComparator);

		if (commerceInventoryBookedQuantity != null) {
			return commerceInventoryBookedQuantity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryBookedQuantityException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory booked quantity in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory booked quantity, or <code>null</code> if a matching commerce inventory booked quantity could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchByC_S_Last(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		int count = countByC_S(companyId, sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryBookedQuantity> list = findByC_S(
			companyId, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory booked quantities before and after the current commerce inventory booked quantity in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the current commerce inventory booked quantity
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity[] findByC_S_PrevAndNext(
			long commerceInventoryBookedQuantityId, long companyId, String sku,
			OrderByComparator<CommerceInventoryBookedQuantity>
				orderByComparator)
		throws NoSuchInventoryBookedQuantityException {

		sku = Objects.toString(sku, "");

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			findByPrimaryKey(commerceInventoryBookedQuantityId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryBookedQuantity[] array =
				new CommerceInventoryBookedQuantityImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, commerceInventoryBookedQuantity, companyId, sku,
				orderByComparator, true);

			array[1] = commerceInventoryBookedQuantity;

			array[2] = getByC_S_PrevAndNext(
				session, commerceInventoryBookedQuantity, companyId, sku,
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

	protected CommerceInventoryBookedQuantity getByC_S_PrevAndNext(
		Session session,
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity,
		long companyId, String sku,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
			sb.append(CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
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
						commerceInventoryBookedQuantity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryBookedQuantity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory booked quantities where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	@Override
	public void removeByC_S(long companyId, String sku) {
		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				findByC_S(
					companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceInventoryBookedQuantity);
		}
	}

	/**
	 * Returns the number of commerce inventory booked quantities where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching commerce inventory booked quantities
	 */
	@Override
	public int countByC_S(long companyId, String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"commerceInventoryBookedQuantity.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SKU_2 =
		"commerceInventoryBookedQuantity.sku = ?";

	private static final String _FINDER_COLUMN_C_S_SKU_3 =
		"(commerceInventoryBookedQuantity.sku IS NULL OR commerceInventoryBookedQuantity.sku = '')";

	public CommerceInventoryBookedQuantityPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceInventoryBookedQuantityId", "CIBookedQuantityId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceInventoryBookedQuantity.class);

		setModelImplClass(CommerceInventoryBookedQuantityImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceInventoryBookedQuantityTable.INSTANCE);
	}

	/**
	 * Caches the commerce inventory booked quantity in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryBookedQuantity the commerce inventory booked quantity
	 */
	@Override
	public void cacheResult(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		entityCache.putResult(
			CommerceInventoryBookedQuantityImpl.class,
			commerceInventoryBookedQuantity.getPrimaryKey(),
			commerceInventoryBookedQuantity);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce inventory booked quantities in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryBookedQuantities the commerce inventory booked quantities
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryBookedQuantity>
			commerceInventoryBookedQuantities) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceInventoryBookedQuantities.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				commerceInventoryBookedQuantities) {

			if (entityCache.getResult(
					CommerceInventoryBookedQuantityImpl.class,
					commerceInventoryBookedQuantity.getPrimaryKey()) == null) {

				cacheResult(commerceInventoryBookedQuantity);
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory booked quantities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryBookedQuantityImpl.class);

		finderCache.clearCache(CommerceInventoryBookedQuantityImpl.class);
	}

	/**
	 * Clears the cache for the commerce inventory booked quantity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		entityCache.removeResult(
			CommerceInventoryBookedQuantityImpl.class,
			commerceInventoryBookedQuantity);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryBookedQuantity>
			commerceInventoryBookedQuantities) {

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				commerceInventoryBookedQuantities) {

			entityCache.removeResult(
				CommerceInventoryBookedQuantityImpl.class,
				commerceInventoryBookedQuantity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceInventoryBookedQuantityImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryBookedQuantityImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce inventory booked quantity with the primary key. Does not add the commerce inventory booked quantity to the database.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key for the new commerce inventory booked quantity
	 * @return the new commerce inventory booked quantity
	 */
	@Override
	public CommerceInventoryBookedQuantity create(
		long commerceInventoryBookedQuantityId) {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			new CommerceInventoryBookedQuantityImpl();

		commerceInventoryBookedQuantity.setNew(true);
		commerceInventoryBookedQuantity.setPrimaryKey(
			commerceInventoryBookedQuantityId);

		commerceInventoryBookedQuantity.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryBookedQuantity;
	}

	/**
	 * Removes the commerce inventory booked quantity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the commerce inventory booked quantity
	 * @return the commerce inventory booked quantity that was removed
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity remove(
			long commerceInventoryBookedQuantityId)
		throws NoSuchInventoryBookedQuantityException {

		return remove((Serializable)commerceInventoryBookedQuantityId);
	}

	/**
	 * Removes the commerce inventory booked quantity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory booked quantity
	 * @return the commerce inventory booked quantity that was removed
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity remove(Serializable primaryKey)
		throws NoSuchInventoryBookedQuantityException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
				(CommerceInventoryBookedQuantity)session.get(
					CommerceInventoryBookedQuantityImpl.class, primaryKey);

			if (commerceInventoryBookedQuantity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryBookedQuantityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryBookedQuantity);
		}
		catch (NoSuchInventoryBookedQuantityException noSuchEntityException) {
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
	protected CommerceInventoryBookedQuantity removeImpl(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryBookedQuantity)) {
				commerceInventoryBookedQuantity =
					(CommerceInventoryBookedQuantity)session.get(
						CommerceInventoryBookedQuantityImpl.class,
						commerceInventoryBookedQuantity.getPrimaryKeyObj());
			}

			if (commerceInventoryBookedQuantity != null) {
				session.delete(commerceInventoryBookedQuantity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryBookedQuantity != null) {
			clearCache(commerceInventoryBookedQuantity);
		}

		return commerceInventoryBookedQuantity;
	}

	@Override
	public CommerceInventoryBookedQuantity updateImpl(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		boolean isNew = commerceInventoryBookedQuantity.isNew();

		if (!(commerceInventoryBookedQuantity instanceof
				CommerceInventoryBookedQuantityModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceInventoryBookedQuantity.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryBookedQuantity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryBookedQuantity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryBookedQuantity implementation " +
					commerceInventoryBookedQuantity.getClass());
		}

		CommerceInventoryBookedQuantityModelImpl
			commerceInventoryBookedQuantityModelImpl =
				(CommerceInventoryBookedQuantityModelImpl)
					commerceInventoryBookedQuantity;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceInventoryBookedQuantity.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceInventoryBookedQuantity.setCreateDate(date);
			}
			else {
				commerceInventoryBookedQuantity.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceInventoryBookedQuantityModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryBookedQuantity.setModifiedDate(date);
			}
			else {
				commerceInventoryBookedQuantity.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceInventoryBookedQuantity);
			}
			else {
				commerceInventoryBookedQuantity =
					(CommerceInventoryBookedQuantity)session.merge(
						commerceInventoryBookedQuantity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceInventoryBookedQuantityImpl.class,
			commerceInventoryBookedQuantityModelImpl, false, true);

		if (isNew) {
			commerceInventoryBookedQuantity.setNew(false);
		}

		commerceInventoryBookedQuantity.resetOriginalValues();

		return commerceInventoryBookedQuantity;
	}

	/**
	 * Returns the commerce inventory booked quantity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory booked quantity
	 * @return the commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			fetchByPrimaryKey(primaryKey);

		if (commerceInventoryBookedQuantity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryBookedQuantityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryBookedQuantity;
	}

	/**
	 * Returns the commerce inventory booked quantity with the primary key or throws a <code>NoSuchInventoryBookedQuantityException</code> if it could not be found.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the commerce inventory booked quantity
	 * @return the commerce inventory booked quantity
	 * @throws NoSuchInventoryBookedQuantityException if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity findByPrimaryKey(
			long commerceInventoryBookedQuantityId)
		throws NoSuchInventoryBookedQuantityException {

		return findByPrimaryKey(
			(Serializable)commerceInventoryBookedQuantityId);
	}

	/**
	 * Returns the commerce inventory booked quantity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryBookedQuantityId the primary key of the commerce inventory booked quantity
	 * @return the commerce inventory booked quantity, or <code>null</code> if a commerce inventory booked quantity with the primary key could not be found
	 */
	@Override
	public CommerceInventoryBookedQuantity fetchByPrimaryKey(
		long commerceInventoryBookedQuantityId) {

		return fetchByPrimaryKey(
			(Serializable)commerceInventoryBookedQuantityId);
	}

	/**
	 * Returns all the commerce inventory booked quantities.
	 *
	 * @return the commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory booked quantities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @return the range of commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory booked quantities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryBookedQuantityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory booked quantities
	 * @param end the upper bound of the range of commerce inventory booked quantities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory booked quantities
	 */
	@Override
	public List<CommerceInventoryBookedQuantity> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryBookedQuantity> orderByComparator,
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

		List<CommerceInventoryBookedQuantity> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryBookedQuantity>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY;

				sql = sql.concat(
					CommerceInventoryBookedQuantityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryBookedQuantity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce inventory booked quantities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				findAll()) {

			remove(commerceInventoryBookedQuantity);
		}
	}

	/**
	 * Returns the number of commerce inventory booked quantities.
	 *
	 * @return the number of commerce inventory booked quantities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CIBookedQuantityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceInventoryBookedQuantityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory booked quantity persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySku",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sku"}, true);

		_finderPathWithoutPaginationFindBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySku",
			new String[] {String.class.getName()}, new String[] {"sku"}, true);

		_finderPathCountBySku = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySku",
			new String[] {String.class.getName()}, new String[] {"sku"}, false);

		_finderPathWithPaginationFindByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"expirationDate"}, true);

		_finderPathWithPaginationCountByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "sku"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceInventoryBookedQuantityImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY =
		"SELECT commerceInventoryBookedQuantity FROM CommerceInventoryBookedQuantity commerceInventoryBookedQuantity";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE =
			"SELECT commerceInventoryBookedQuantity FROM CommerceInventoryBookedQuantity commerceInventoryBookedQuantity WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY =
		"SELECT COUNT(commerceInventoryBookedQuantity) FROM CommerceInventoryBookedQuantity commerceInventoryBookedQuantity";

	private static final String
		_SQL_COUNT_COMMERCEINVENTORYBOOKEDQUANTITY_WHERE =
			"SELECT COUNT(commerceInventoryBookedQuantity) FROM CommerceInventoryBookedQuantity commerceInventoryBookedQuantity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryBookedQuantity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryBookedQuantity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryBookedQuantity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryBookedQuantityPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceInventoryBookedQuantityId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}