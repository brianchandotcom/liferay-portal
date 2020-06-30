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

import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehousePersistence;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
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
 * The persistence implementation for the commerce inventory warehouse service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehousePersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryWarehouse>
	implements CommerceInventoryWarehousePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryWarehouseUtil</code> to access the commerce inventory warehouse persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryWarehouseImpl.class.getName();

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
	 * Returns all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		List<CommerceInventoryWarehouse> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouse>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouse commerceInventoryWarehouse :
						list) {

					if (companyId !=
							commerceInventoryWarehouse.getCompanyId()) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceInventoryWarehouse>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		List<CommerceInventoryWarehouse> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouse> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] findByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
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

	protected CommerceInventoryWarehouse getByCompanyId_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

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
			sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
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
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] filterFindByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceInventoryWarehouseId, companyId, orderByComparator);
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
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

	protected CommerceInventoryWarehouse filterGetByCompanyId_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouse);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

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

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceInventoryWarehouse.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active) {

		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {companyId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				companyId, active, start, end, orderByComparator
			};
		}

		List<CommerceInventoryWarehouse> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouse>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouse commerceInventoryWarehouse :
						list) {

					if ((companyId !=
							commerceInventoryWarehouse.getCompanyId()) ||
						(active != commerceInventoryWarehouse.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				list = (List<CommerceInventoryWarehouse>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByC_A_First(companyId, active, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		List<CommerceInventoryWarehouse> list = findByC_A(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse = fetchByC_A_Last(
			companyId, active, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouse> list = findByC_A(
			companyId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] findByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = getByC_A_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
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

	protected CommerceInventoryWarehouse getByC_A_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active) {

		return filterFindByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return filterFindByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A(companyId, active, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] filterFindByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_PrevAndNext(
				commerceInventoryWarehouseId, companyId, active,
				orderByComparator);
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = filterGetByC_A_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = filterGetByC_A_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
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

	protected CommerceInventoryWarehouse filterGetByC_A_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceInventoryWarehouse);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {companyId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

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

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public int filterCountByC_A(long companyId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A(companyId, active);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"commerceInventoryWarehouse.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"commerceInventoryWarehouse.active = ?";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2_SQL =
		"commerceInventoryWarehouse.active_ = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return findByC_C(
			companyId, countryTwoLettersISOCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end) {

		return findByC_C(companyId, countryTwoLettersISOCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return findByC_C(
			companyId, countryTwoLettersISOCode, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {companyId, countryTwoLettersISOCode};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				companyId, countryTwoLettersISOCode, start, end,
				orderByComparator
			};
		}

		List<CommerceInventoryWarehouse> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouse>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouse commerceInventoryWarehouse :
						list) {

					if ((companyId !=
							commerceInventoryWarehouse.getCompanyId()) ||
						!countryTwoLettersISOCode.equals(
							commerceInventoryWarehouse.
								getCountryTwoLettersISOCode())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindCountryTwoLettersISOCode = false;

			if (countryTwoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
			}
			else {
				bindCountryTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCountryTwoLettersISOCode) {
					queryPos.add(countryTwoLettersISOCode);
				}

				list = (List<CommerceInventoryWarehouse>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_C_First(
			long companyId, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByC_C_First(
				companyId, countryTwoLettersISOCode, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", countryTwoLettersISOCode=");
		sb.append(countryTwoLettersISOCode);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_C_First(
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		List<CommerceInventoryWarehouse> list = findByC_C(
			companyId, countryTwoLettersISOCode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_C_Last(
			long companyId, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse = fetchByC_C_Last(
			companyId, countryTwoLettersISOCode, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", countryTwoLettersISOCode=");
		sb.append(countryTwoLettersISOCode);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_C_Last(
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		int count = countByC_C(companyId, countryTwoLettersISOCode);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouse> list = findByC_C(
			companyId, countryTwoLettersISOCode, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				countryTwoLettersISOCode, orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = getByC_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				countryTwoLettersISOCode, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouse getByC_C_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
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
			sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindCountryTwoLettersISOCode) {
			queryPos.add(countryTwoLettersISOCode);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return filterFindByC_C(
			companyId, countryTwoLettersISOCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end) {

		return filterFindByC_C(
			companyId, countryTwoLettersISOCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_C(
				companyId, countryTwoLettersISOCode, start, end,
				orderByComparator);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindCountryTwoLettersISOCode) {
				queryPos.add(countryTwoLettersISOCode);
			}

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] filterFindByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_C_PrevAndNext(
				commerceInventoryWarehouseId, companyId,
				countryTwoLettersISOCode, orderByComparator);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = filterGetByC_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				countryTwoLettersISOCode, orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = filterGetByC_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId,
				countryTwoLettersISOCode, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouse filterGetByC_C_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindCountryTwoLettersISOCode) {
			queryPos.add(countryTwoLettersISOCode);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	@Override
	public void removeByC_C(long companyId, String countryTwoLettersISOCode) {
		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				findByC_C(
					companyId, countryTwoLettersISOCode, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouse);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	@Override
	public int countByC_C(long companyId, String countryTwoLettersISOCode) {
		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			companyId, countryTwoLettersISOCode
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindCountryTwoLettersISOCode = false;

			if (countryTwoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
			}
			else {
				bindCountryTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCountryTwoLettersISOCode) {
					queryPos.add(countryTwoLettersISOCode);
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

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public int filterCountByC_C(
		long companyId, String countryTwoLettersISOCode) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_C(companyId, countryTwoLettersISOCode);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindCountryTwoLettersISOCode) {
				queryPos.add(countryTwoLettersISOCode);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 =
		"commerceInventoryWarehouse.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_2 =
		"commerceInventoryWarehouse.countryTwoLettersISOCode = ?";

	private static final String _FINDER_COLUMN_C_C_COUNTRYTWOLETTERSISOCODE_3 =
		"(commerceInventoryWarehouse.countryTwoLettersISOCode IS NULL OR commerceInventoryWarehouse.countryTwoLettersISOCode = '')";

	private FinderPath _finderPathWithPaginationFindByC_A_C;
	private FinderPath _finderPathWithoutPaginationFindByC_A_C;
	private FinderPath _finderPathCountByC_A_C;

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return findByC_A_C(
			companyId, active, countryTwoLettersISOCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end) {

		return findByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return findByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_C;
				finderArgs = new Object[] {
					companyId, active, countryTwoLettersISOCode
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_C;
			finderArgs = new Object[] {
				companyId, active, countryTwoLettersISOCode, start, end,
				orderByComparator
			};
		}

		List<CommerceInventoryWarehouse> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouse>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouse commerceInventoryWarehouse :
						list) {

					if ((companyId !=
							commerceInventoryWarehouse.getCompanyId()) ||
						(active != commerceInventoryWarehouse.isActive()) ||
						!countryTwoLettersISOCode.equals(
							commerceInventoryWarehouse.
								getCountryTwoLettersISOCode())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2);

			boolean bindCountryTwoLettersISOCode = false;

			if (countryTwoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
			}
			else {
				bindCountryTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindCountryTwoLettersISOCode) {
					queryPos.add(countryTwoLettersISOCode);
				}

				list = (List<CommerceInventoryWarehouse>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_A_C_First(
			long companyId, boolean active, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByC_A_C_First(
				companyId, active, countryTwoLettersISOCode, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", countryTwoLettersISOCode=");
		sb.append(countryTwoLettersISOCode);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_A_C_First(
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		List<CommerceInventoryWarehouse> list = findByC_A_C(
			companyId, active, countryTwoLettersISOCode, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_A_C_Last(
			long companyId, boolean active, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByC_A_C_Last(
				companyId, active, countryTwoLettersISOCode, orderByComparator);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", countryTwoLettersISOCode=");
		sb.append(countryTwoLettersISOCode);

		sb.append("}");

		throw new NoSuchInventoryWarehouseException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_A_C_Last(
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		int count = countByC_A_C(companyId, active, countryTwoLettersISOCode);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouse> list = findByC_A_C(
			companyId, active, countryTwoLettersISOCode, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] findByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = getByC_A_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				countryTwoLettersISOCode, orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = getByC_A_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				countryTwoLettersISOCode, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouse getByC_A_C_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
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
			sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (bindCountryTwoLettersISOCode) {
			queryPos.add(countryTwoLettersISOCode);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return filterFindByC_A_C(
			companyId, active, countryTwoLettersISOCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end) {

		return filterFindByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_C(
				companyId, active, countryTwoLettersISOCode, start, end,
				orderByComparator);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2_SQL);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			if (bindCountryTwoLettersISOCode) {
				queryPos.add(countryTwoLettersISOCode);
			}

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse[] filterFindByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_C_PrevAndNext(
				commerceInventoryWarehouseId, companyId, active,
				countryTwoLettersISOCode, orderByComparator);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			findByPrimaryKey(commerceInventoryWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse[] array =
				new CommerceInventoryWarehouseImpl[3];

			array[0] = filterGetByC_A_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				countryTwoLettersISOCode, orderByComparator, true);

			array[1] = commerceInventoryWarehouse;

			array[2] = filterGetByC_A_C_PrevAndNext(
				session, commerceInventoryWarehouse, companyId, active,
				countryTwoLettersISOCode, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouse filterGetByC_A_C_PrevAndNext(
		Session session, CommerceInventoryWarehouse commerceInventoryWarehouse,
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2_SQL);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceInventoryWarehouseModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceInventoryWarehouseImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceInventoryWarehouseImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(active);

		if (bindCountryTwoLettersISOCode) {
			queryPos.add(countryTwoLettersISOCode);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouse)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouse> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	@Override
	public void removeByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				findByC_A_C(
					companyId, active, countryTwoLettersISOCode,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouse);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	@Override
	public int countByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		FinderPath finderPath = _finderPathCountByC_A_C;

		Object[] finderArgs = new Object[] {
			companyId, active, countryTwoLettersISOCode
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

			sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2);

			boolean bindCountryTwoLettersISOCode = false;

			if (countryTwoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
			}
			else {
				bindCountryTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindCountryTwoLettersISOCode) {
					queryPos.add(countryTwoLettersISOCode);
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

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_C(companyId, active, countryTwoLettersISOCode);
		}

		countryTwoLettersISOCode = Objects.toString(
			countryTwoLettersISOCode, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

		sb.append(_FINDER_COLUMN_C_A_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_C_ACTIVE_2_SQL);

		boolean bindCountryTwoLettersISOCode = false;

		if (countryTwoLettersISOCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3);
		}
		else {
			bindCountryTwoLettersISOCode = true;

			sb.append(_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceInventoryWarehouse.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			if (bindCountryTwoLettersISOCode) {
				queryPos.add(countryTwoLettersISOCode);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_C_COMPANYID_2 =
		"commerceInventoryWarehouse.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_C_ACTIVE_2 =
		"commerceInventoryWarehouse.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_C_ACTIVE_2_SQL =
		"commerceInventoryWarehouse.active_ = ? AND ";

	private static final String
		_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_2 =
			"commerceInventoryWarehouse.countryTwoLettersISOCode = ?";

	private static final String
		_FINDER_COLUMN_C_A_C_COUNTRYTWOLETTERSISOCODE_3 =
			"(commerceInventoryWarehouse.countryTwoLettersISOCode IS NULL OR commerceInventoryWarehouse.countryTwoLettersISOCode = '')";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce inventory warehouse where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceInventoryWarehouse == null) {
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

			throw new NoSuchInventoryWarehouseException(sb.toString());
		}

		return commerceInventoryWarehouse;
	}

	/**
	 * Returns the commerce inventory warehouse where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce inventory warehouse where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByC_ERC(
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

		if (result instanceof CommerceInventoryWarehouse) {
			CommerceInventoryWarehouse commerceInventoryWarehouse =
				(CommerceInventoryWarehouse)result;

			if ((companyId != commerceInventoryWarehouse.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceInventoryWarehouse.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE);

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

				List<CommerceInventoryWarehouse> list = query.list();

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
								"CommerceInventoryWarehousePersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceInventoryWarehouse commerceInventoryWarehouse =
						list.get(0);

					result = commerceInventoryWarehouse;

					cacheResult(commerceInventoryWarehouse);
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
			return (CommerceInventoryWarehouse)result;
		}
	}

	/**
	 * Removes the commerce inventory warehouse where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce inventory warehouse that was removed
	 */
	@Override
	public CommerceInventoryWarehouse removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceInventoryWarehouse);
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce inventory warehouses
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE);

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
		"commerceInventoryWarehouse.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceInventoryWarehouse.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceInventoryWarehouse.externalReferenceCode IS NULL OR commerceInventoryWarehouse.externalReferenceCode = '')";

	public CommerceInventoryWarehousePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("commerceInventoryWarehouseId", "CIWarehouseId");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("type", "type_");

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

		setModelClass(CommerceInventoryWarehouse.class);
	}

	/**
	 * Caches the commerce inventory warehouse in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 */
	@Override
	public void cacheResult(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		entityCache.putResult(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			commerceInventoryWarehouse.getPrimaryKey(),
			commerceInventoryWarehouse);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceInventoryWarehouse.getCompanyId(),
				commerceInventoryWarehouse.getExternalReferenceCode()
			},
			commerceInventoryWarehouse);

		commerceInventoryWarehouse.resetOriginalValues();
	}

	/**
	 * Caches the commerce inventory warehouses in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouses the commerce inventory warehouses
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryWarehouse> commerceInventoryWarehouses) {

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			if (entityCache.getResult(
					CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseImpl.class,
					commerceInventoryWarehouse.getPrimaryKey()) == null) {

				cacheResult(commerceInventoryWarehouse);
			}
			else {
				commerceInventoryWarehouse.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory warehouses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryWarehouseImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce inventory warehouse.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		entityCache.removeResult(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			commerceInventoryWarehouse.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CommerceInventoryWarehouseModelImpl)commerceInventoryWarehouse,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryWarehouse> commerceInventoryWarehouses) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			entityCache.removeResult(
				CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseImpl.class,
				commerceInventoryWarehouse.getPrimaryKey());

			clearUniqueFindersCache(
				(CommerceInventoryWarehouseModelImpl)commerceInventoryWarehouse,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceInventoryWarehouseModelImpl
			commerceInventoryWarehouseModelImpl) {

		Object[] args = new Object[] {
			commerceInventoryWarehouseModelImpl.getCompanyId(),
			commerceInventoryWarehouseModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceInventoryWarehouseModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		CommerceInventoryWarehouseModelImpl commerceInventoryWarehouseModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				commerceInventoryWarehouseModelImpl.getCompanyId(),
				commerceInventoryWarehouseModelImpl.getExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}

		if ((commerceInventoryWarehouseModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_ERC.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				commerceInventoryWarehouseModelImpl.getOriginalCompanyId(),
				commerceInventoryWarehouseModelImpl.
					getOriginalExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}
	}

	/**
	 * Creates a new commerce inventory warehouse with the primary key. Does not add the commerce inventory warehouse to the database.
	 *
	 * @param commerceInventoryWarehouseId the primary key for the new commerce inventory warehouse
	 * @return the new commerce inventory warehouse
	 */
	@Override
	public CommerceInventoryWarehouse create(
		long commerceInventoryWarehouseId) {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			new CommerceInventoryWarehouseImpl();

		commerceInventoryWarehouse.setNew(true);
		commerceInventoryWarehouse.setPrimaryKey(commerceInventoryWarehouseId);

		commerceInventoryWarehouse.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryWarehouse;
	}

	/**
	 * Removes the commerce inventory warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse remove(long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseException {

		return remove((Serializable)commerceInventoryWarehouseId);
	}

	/**
	 * Removes the commerce inventory warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse remove(Serializable primaryKey)
		throws NoSuchInventoryWarehouseException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				(CommerceInventoryWarehouse)session.get(
					CommerceInventoryWarehouseImpl.class, primaryKey);

			if (commerceInventoryWarehouse == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryWarehouseException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryWarehouse);
		}
		catch (NoSuchInventoryWarehouseException noSuchEntityException) {
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
	protected CommerceInventoryWarehouse removeImpl(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryWarehouse)) {
				commerceInventoryWarehouse =
					(CommerceInventoryWarehouse)session.get(
						CommerceInventoryWarehouseImpl.class,
						commerceInventoryWarehouse.getPrimaryKeyObj());
			}

			if (commerceInventoryWarehouse != null) {
				session.delete(commerceInventoryWarehouse);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryWarehouse != null) {
			clearCache(commerceInventoryWarehouse);
		}

		return commerceInventoryWarehouse;
	}

	@Override
	public CommerceInventoryWarehouse updateImpl(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		boolean isNew = commerceInventoryWarehouse.isNew();

		if (!(commerceInventoryWarehouse instanceof
				CommerceInventoryWarehouseModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceInventoryWarehouse.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryWarehouse);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryWarehouse proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryWarehouse implementation " +
					commerceInventoryWarehouse.getClass());
		}

		CommerceInventoryWarehouseModelImpl
			commerceInventoryWarehouseModelImpl =
				(CommerceInventoryWarehouseModelImpl)commerceInventoryWarehouse;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceInventoryWarehouse.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceInventoryWarehouse.setCreateDate(now);
			}
			else {
				commerceInventoryWarehouse.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceInventoryWarehouseModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryWarehouse.setModifiedDate(now);
			}
			else {
				commerceInventoryWarehouse.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceInventoryWarehouse.isNew()) {
				session.save(commerceInventoryWarehouse);

				commerceInventoryWarehouse.setNew(false);
			}
			else {
				commerceInventoryWarehouse =
					(CommerceInventoryWarehouse)session.merge(
						commerceInventoryWarehouse);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceInventoryWarehouseModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceInventoryWarehouseModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				commerceInventoryWarehouseModelImpl.getCompanyId(),
				commerceInventoryWarehouseModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByC_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_A, args);

			args = new Object[] {
				commerceInventoryWarehouseModelImpl.getCompanyId(),
				commerceInventoryWarehouseModelImpl.
					getCountryTwoLettersISOCode()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				commerceInventoryWarehouseModelImpl.getCompanyId(),
				commerceInventoryWarehouseModelImpl.isActive(),
				commerceInventoryWarehouseModelImpl.
					getCountryTwoLettersISOCode()
			};

			finderCache.removeResult(_finderPathCountByC_A_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_A_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceInventoryWarehouseModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					commerceInventoryWarehouseModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((commerceInventoryWarehouseModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseModelImpl.getOriginalCompanyId(),
					commerceInventoryWarehouseModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByC_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A, args);

				args = new Object[] {
					commerceInventoryWarehouseModelImpl.getCompanyId(),
					commerceInventoryWarehouseModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByC_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A, args);
			}

			if ((commerceInventoryWarehouseModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseModelImpl.getOriginalCompanyId(),
					commerceInventoryWarehouseModelImpl.
						getOriginalCountryTwoLettersISOCode()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					commerceInventoryWarehouseModelImpl.getCompanyId(),
					commerceInventoryWarehouseModelImpl.
						getCountryTwoLettersISOCode()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((commerceInventoryWarehouseModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_A_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceInventoryWarehouseModelImpl.getOriginalCompanyId(),
					commerceInventoryWarehouseModelImpl.getOriginalActive(),
					commerceInventoryWarehouseModelImpl.
						getOriginalCountryTwoLettersISOCode()
				};

				finderCache.removeResult(_finderPathCountByC_A_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A_C, args);

				args = new Object[] {
					commerceInventoryWarehouseModelImpl.getCompanyId(),
					commerceInventoryWarehouseModelImpl.isActive(),
					commerceInventoryWarehouseModelImpl.
						getCountryTwoLettersISOCode()
				};

				finderCache.removeResult(_finderPathCountByC_A_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_A_C, args);
			}
		}

		entityCache.putResult(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			commerceInventoryWarehouse.getPrimaryKey(),
			commerceInventoryWarehouse, false);

		clearUniqueFindersCache(commerceInventoryWarehouseModelImpl, false);
		cacheUniqueFindersCache(commerceInventoryWarehouseModelImpl);

		commerceInventoryWarehouse.resetOriginalValues();

		return commerceInventoryWarehouse;
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByPrimaryKey(Serializable primaryKey)
		throws NoSuchInventoryWarehouseException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			fetchByPrimaryKey(primaryKey);

		if (commerceInventoryWarehouse == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryWarehouseException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryWarehouse;
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse findByPrimaryKey(
			long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseException {

		return findByPrimaryKey((Serializable)commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse, or <code>null</code> if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			(CommerceInventoryWarehouse)serializable;

		if (commerceInventoryWarehouse == null) {
			Session session = null;

			try {
				session = openSession();

				commerceInventoryWarehouse =
					(CommerceInventoryWarehouse)session.get(
						CommerceInventoryWarehouseImpl.class, primaryKey);

				if (commerceInventoryWarehouse != null) {
					cacheResult(commerceInventoryWarehouse);
				}
				else {
					entityCache.putResult(
						CommerceInventoryWarehouseModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceInventoryWarehouseImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceInventoryWarehouse;
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse, or <code>null</code> if a commerce inventory warehouse with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouse fetchByPrimaryKey(
		long commerceInventoryWarehouseId) {

		return fetchByPrimaryKey((Serializable)commerceInventoryWarehouseId);
	}

	@Override
	public Map<Serializable, CommerceInventoryWarehouse> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceInventoryWarehouse> map =
			new HashMap<Serializable, CommerceInventoryWarehouse>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				fetchByPrimaryKey(primaryKey);

			if (commerceInventoryWarehouse != null) {
				map.put(primaryKey, commerceInventoryWarehouse);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
				CommerceInventoryWarehouseImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (CommerceInventoryWarehouse)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE_PKS_IN);

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

			for (CommerceInventoryWarehouse commerceInventoryWarehouse :
					(List<CommerceInventoryWarehouse>)query.list()) {

				map.put(
					commerceInventoryWarehouse.getPrimaryKeyObj(),
					commerceInventoryWarehouse);

				cacheResult(commerceInventoryWarehouse);

				uncachedPrimaryKeys.remove(
					commerceInventoryWarehouse.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceInventoryWarehouseImpl.class, primaryKey,
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
	 * Returns all the commerce inventory warehouses.
	 *
	 * @return the commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouses
	 */
	@Override
	public List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
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

		List<CommerceInventoryWarehouse> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouse>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYWAREHOUSE;

				sql = sql.concat(
					CommerceInventoryWarehouseModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryWarehouse>)QueryUtil.list(
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
	 * Removes all the commerce inventory warehouses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				findAll()) {

			remove(commerceInventoryWarehouse);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouses.
	 *
	 * @return the number of commerce inventory warehouses
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
					_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE);

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
		return CommerceInventoryWarehouseModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory warehouse persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			CommerceInventoryWarehouseModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_A = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			CommerceInventoryWarehouseModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.ACTIVE_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_A = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryWarehouseModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.
				COUNTRYTWOLETTERSISOCODE_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_A_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_C",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_A_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_C",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			CommerceInventoryWarehouseModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.ACTIVE_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.
				COUNTRYTWOLETTERSISOCODE_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_A_C = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_A_C",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathFetchByC_ERC = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceInventoryWarehouseImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceInventoryWarehouseModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceInventoryWarehouseModelImpl.
				EXTERNALREFERENCECODE_COLUMN_BITMASK);

		_finderPathCountByC_ERC = new FinderPath(
			CommerceInventoryWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceInventoryWarehouseModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(CommerceInventoryWarehouseImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEINVENTORYWAREHOUSE =
		"SELECT commerceInventoryWarehouse FROM CommerceInventoryWarehouse commerceInventoryWarehouse";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE_PKS_IN =
			"SELECT commerceInventoryWarehouse FROM CommerceInventoryWarehouse commerceInventoryWarehouse WHERE CIWarehouseId IN (";

	private static final String _SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE =
		"SELECT commerceInventoryWarehouse FROM CommerceInventoryWarehouse commerceInventoryWarehouse WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYWAREHOUSE =
		"SELECT COUNT(commerceInventoryWarehouse) FROM CommerceInventoryWarehouse commerceInventoryWarehouse";

	private static final String _SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE =
		"SELECT COUNT(commerceInventoryWarehouse) FROM CommerceInventoryWarehouse commerceInventoryWarehouse WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceInventoryWarehouse.CIWarehouseId";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_WHERE =
			"SELECT DISTINCT {commerceInventoryWarehouse.*} FROM CIWarehouse commerceInventoryWarehouse WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CIWarehouse.*} FROM (SELECT DISTINCT commerceInventoryWarehouse.CIWarehouseId FROM CIWarehouse commerceInventoryWarehouse WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEINVENTORYWAREHOUSE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CIWarehouse ON TEMP_TABLE.CIWarehouseId = CIWarehouse.CIWarehouseId";

	private static final String
		_FILTER_SQL_COUNT_COMMERCEINVENTORYWAREHOUSE_WHERE =
			"SELECT COUNT(DISTINCT commerceInventoryWarehouse.CIWarehouseId) AS COUNT_VALUE FROM CIWarehouse commerceInventoryWarehouse WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"commerceInventoryWarehouse";

	private static final String _FILTER_ENTITY_TABLE = "CIWarehouse";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryWarehouse.";

	private static final String _ORDER_BY_ENTITY_TABLE = "CIWarehouse.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryWarehouse exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryWarehouse exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehousePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceInventoryWarehouseId", "active", "type"});

}