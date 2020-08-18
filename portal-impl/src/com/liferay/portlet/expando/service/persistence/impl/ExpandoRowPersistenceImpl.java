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

package com.liferay.portlet.expando.service.persistence.impl;

import com.liferay.expando.kernel.exception.NoSuchRowException;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoRowTable;
import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portlet.expando.model.impl.ExpandoRowImpl;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the expando row service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoRowPersistenceImpl
	extends BasePersistenceImpl<ExpandoRow> implements ExpandoRowPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExpandoRowUtil</code> to access the expando row persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExpandoRowImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByTableId;
	private FinderPath _finderPathWithoutPaginationFindByTableId;
	private FinderPath _finderPathCountByTableId;

	/**
	 * Returns all the expando rows where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByTableId(long tableId) {
		return findByTableId(
			tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando rows where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @return the range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByTableId(long tableId, int start, int end) {
		return findByTableId(tableId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando rows where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator) {

		return findByTableId(tableId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando rows where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByTableId;
				finderArgs = new Object[] {tableId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByTableId;
			finderArgs = new Object[] {tableId, start, end, orderByComparator};
		}

		List<ExpandoRow> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoRow>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoRow expandoRow : list) {
					if (tableId != expandoRow.getTableId()) {
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

			sb.append(_SQL_SELECT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoRowModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				list = (List<ExpandoRow>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first expando row in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando row
	 * @throws NoSuchRowException if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow findByTableId_First(
			long tableId, OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByTableId_First(
			tableId, orderByComparator);

		if (expandoRow != null) {
			return expandoRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append("}");

		throw new NoSuchRowException(sb.toString());
	}

	/**
	 * Returns the first expando row in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByTableId_First(
		long tableId, OrderByComparator<ExpandoRow> orderByComparator) {

		List<ExpandoRow> list = findByTableId(tableId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando row in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando row
	 * @throws NoSuchRowException if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow findByTableId_Last(
			long tableId, OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByTableId_Last(tableId, orderByComparator);

		if (expandoRow != null) {
			return expandoRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append("}");

		throw new NoSuchRowException(sb.toString());
	}

	/**
	 * Returns the last expando row in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByTableId_Last(
		long tableId, OrderByComparator<ExpandoRow> orderByComparator) {

		int count = countByTableId(tableId);

		if (count == 0) {
			return null;
		}

		List<ExpandoRow> list = findByTableId(
			tableId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando rows before and after the current expando row in the ordered set where tableId = &#63;.
	 *
	 * @param rowId the primary key of the current expando row
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando row
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow[] findByTableId_PrevAndNext(
			long rowId, long tableId,
			OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = findByPrimaryKey(rowId);

		Session session = null;

		try {
			session = openSession();

			ExpandoRow[] array = new ExpandoRowImpl[3];

			array[0] = getByTableId_PrevAndNext(
				session, expandoRow, tableId, orderByComparator, true);

			array[1] = expandoRow;

			array[2] = getByTableId_PrevAndNext(
				session, expandoRow, tableId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoRow getByTableId_PrevAndNext(
		Session session, ExpandoRow expandoRow, long tableId,
		OrderByComparator<ExpandoRow> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_EXPANDOROW_WHERE);

		sb.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

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
			sb.append(ExpandoRowModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoRow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoRow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando rows where tableId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 */
	@Override
	public void removeByTableId(long tableId) {
		for (ExpandoRow expandoRow :
				findByTableId(
					tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoRow);
		}
	}

	/**
	 * Returns the number of expando rows where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the number of matching expando rows
	 */
	@Override
	public int countByTableId(long tableId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByTableId;

			finderArgs = new Object[] {tableId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 =
		"expandoRow.tableId = ?";

	private FinderPath _finderPathWithPaginationFindByClassPK;
	private FinderPath _finderPathWithoutPaginationFindByClassPK;
	private FinderPath _finderPathCountByClassPK;

	/**
	 * Returns all the expando rows where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByClassPK(long classPK) {
		return findByClassPK(
			classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando rows where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @return the range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByClassPK(long classPK, int start, int end) {
		return findByClassPK(classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando rows where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator) {

		return findByClassPK(classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando rows where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando rows
	 */
	@Override
	public List<ExpandoRow> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByClassPK;
				finderArgs = new Object[] {classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByClassPK;
			finderArgs = new Object[] {classPK, start, end, orderByComparator};
		}

		List<ExpandoRow> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoRow>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoRow expandoRow : list) {
					if (classPK != expandoRow.getClassPK()) {
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

			sb.append(_SQL_SELECT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoRowModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classPK);

				list = (List<ExpandoRow>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first expando row in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando row
	 * @throws NoSuchRowException if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow findByClassPK_First(
			long classPK, OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByClassPK_First(
			classPK, orderByComparator);

		if (expandoRow != null) {
			return expandoRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRowException(sb.toString());
	}

	/**
	 * Returns the first expando row in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByClassPK_First(
		long classPK, OrderByComparator<ExpandoRow> orderByComparator) {

		List<ExpandoRow> list = findByClassPK(classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando row in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando row
	 * @throws NoSuchRowException if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow findByClassPK_Last(
			long classPK, OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByClassPK_Last(classPK, orderByComparator);

		if (expandoRow != null) {
			return expandoRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRowException(sb.toString());
	}

	/**
	 * Returns the last expando row in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByClassPK_Last(
		long classPK, OrderByComparator<ExpandoRow> orderByComparator) {

		int count = countByClassPK(classPK);

		if (count == 0) {
			return null;
		}

		List<ExpandoRow> list = findByClassPK(
			classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando rows before and after the current expando row in the ordered set where classPK = &#63;.
	 *
	 * @param rowId the primary key of the current expando row
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando row
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow[] findByClassPK_PrevAndNext(
			long rowId, long classPK,
			OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException {

		ExpandoRow expandoRow = findByPrimaryKey(rowId);

		Session session = null;

		try {
			session = openSession();

			ExpandoRow[] array = new ExpandoRowImpl[3];

			array[0] = getByClassPK_PrevAndNext(
				session, expandoRow, classPK, orderByComparator, true);

			array[1] = expandoRow;

			array[2] = getByClassPK_PrevAndNext(
				session, expandoRow, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoRow getByClassPK_PrevAndNext(
		Session session, ExpandoRow expandoRow, long classPK,
		OrderByComparator<ExpandoRow> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_EXPANDOROW_WHERE);

		sb.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

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
			sb.append(ExpandoRowModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoRow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoRow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando rows where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 */
	@Override
	public void removeByClassPK(long classPK) {
		for (ExpandoRow expandoRow :
				findByClassPK(
					classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoRow);
		}
	}

	/**
	 * Returns the number of expando rows where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching expando rows
	 */
	@Override
	public int countByClassPK(long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByClassPK;

			finderArgs = new Object[] {classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 =
		"expandoRow.classPK = ?";

	private FinderPath _finderPathFetchByT_C;
	private FinderPath _finderPathCountByT_C;

	/**
	 * Returns the expando row where tableId = &#63; and classPK = &#63; or throws a <code>NoSuchRowException</code> if it could not be found.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the matching expando row
	 * @throws NoSuchRowException if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow findByT_C(long tableId, long classPK)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByT_C(tableId, classPK);

		if (expandoRow == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("tableId=");
			sb.append(tableId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRowException(sb.toString());
		}

		return expandoRow;
	}

	/**
	 * Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByT_C(long tableId, long classPK) {
		return fetchByT_C(tableId, classPK, true);
	}

	/**
	 * Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	 */
	@Override
	public ExpandoRow fetchByT_C(
		long tableId, long classPK, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {tableId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByT_C, finderArgs, this);
		}

		if (result instanceof ExpandoRow) {
			ExpandoRow expandoRow = (ExpandoRow)result;

			if ((tableId != expandoRow.getTableId()) ||
				(classPK != expandoRow.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_T_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(classPK);

				List<ExpandoRow> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByT_C, finderArgs, list);
					}
				}
				else {
					ExpandoRow expandoRow = list.get(0);

					result = expandoRow;

					cacheResult(expandoRow);
				}
			}
			catch (Exception exception) {
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
			return (ExpandoRow)result;
		}
	}

	/**
	 * Removes the expando row where tableId = &#63; and classPK = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the expando row that was removed
	 */
	@Override
	public ExpandoRow removeByT_C(long tableId, long classPK)
		throws NoSuchRowException {

		ExpandoRow expandoRow = findByT_C(tableId, classPK);

		return remove(expandoRow);
	}

	/**
	 * Returns the number of expando rows where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the number of matching expando rows
	 */
	@Override
	public int countByT_C(long tableId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_C;

			finderArgs = new Object[] {tableId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOROW_WHERE);

			sb.append(_FINDER_COLUMN_T_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_T_C_TABLEID_2 =
		"expandoRow.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_CLASSPK_2 =
		"expandoRow.classPK = ?";

	public ExpandoRowPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("rowId", "rowId_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ExpandoRow.class);

		setModelImplClass(ExpandoRowImpl.class);
		setModelPKClass(long.class);

		setTable(ExpandoRowTable.INSTANCE);
	}

	/**
	 * Caches the expando row in the entity cache if it is enabled.
	 *
	 * @param expandoRow the expando row
	 */
	@Override
	public void cacheResult(ExpandoRow expandoRow) {
		if (expandoRow.getCtCollectionId() != 0) {
			expandoRow.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			ExpandoRowImpl.class, expandoRow.getPrimaryKey(), expandoRow);

		FinderCacheUtil.putResult(
			_finderPathFetchByT_C,
			new Object[] {expandoRow.getTableId(), expandoRow.getClassPK()},
			expandoRow);

		expandoRow.resetOriginalValues();
	}

	/**
	 * Caches the expando rows in the entity cache if it is enabled.
	 *
	 * @param expandoRows the expando rows
	 */
	@Override
	public void cacheResult(List<ExpandoRow> expandoRows) {
		for (ExpandoRow expandoRow : expandoRows) {
			if (expandoRow.getCtCollectionId() != 0) {
				expandoRow.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					ExpandoRowImpl.class, expandoRow.getPrimaryKey()) == null) {

				cacheResult(expandoRow);
			}
			else {
				expandoRow.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all expando rows.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ExpandoRowImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the expando row.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExpandoRow expandoRow) {
		EntityCacheUtil.removeResult(
			ExpandoRowImpl.class, expandoRow.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ExpandoRowModelImpl)expandoRow, true);
	}

	@Override
	public void clearCache(List<ExpandoRow> expandoRows) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExpandoRow expandoRow : expandoRows) {
			EntityCacheUtil.removeResult(
				ExpandoRowImpl.class, expandoRow.getPrimaryKey());

			clearUniqueFindersCache((ExpandoRowModelImpl)expandoRow, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ExpandoRowImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ExpandoRowModelImpl expandoRowModelImpl) {

		Object[] args = new Object[] {
			expandoRowModelImpl.getTableId(), expandoRowModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByT_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByT_C, args, expandoRowModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ExpandoRowModelImpl expandoRowModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				expandoRowModelImpl.getTableId(),
				expandoRowModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_C, args);
		}

		if ((expandoRowModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				expandoRowModelImpl.getOriginalTableId(),
				expandoRowModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_C, args);
		}
	}

	/**
	 * Creates a new expando row with the primary key. Does not add the expando row to the database.
	 *
	 * @param rowId the primary key for the new expando row
	 * @return the new expando row
	 */
	@Override
	public ExpandoRow create(long rowId) {
		ExpandoRow expandoRow = new ExpandoRowImpl();

		expandoRow.setNew(true);
		expandoRow.setPrimaryKey(rowId);

		expandoRow.setCompanyId(CompanyThreadLocal.getCompanyId());

		return expandoRow;
	}

	/**
	 * Removes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rowId the primary key of the expando row
	 * @return the expando row that was removed
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow remove(long rowId) throws NoSuchRowException {
		return remove((Serializable)rowId);
	}

	/**
	 * Removes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the expando row
	 * @return the expando row that was removed
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow remove(Serializable primaryKey)
		throws NoSuchRowException {

		Session session = null;

		try {
			session = openSession();

			ExpandoRow expandoRow = (ExpandoRow)session.get(
				ExpandoRowImpl.class, primaryKey);

			if (expandoRow == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRowException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(expandoRow);
		}
		catch (NoSuchRowException noSuchEntityException) {
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
	protected ExpandoRow removeImpl(ExpandoRow expandoRow) {
		if (!CTPersistenceHelperUtil.isRemove(expandoRow)) {
			return expandoRow;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(expandoRow)) {
				expandoRow = (ExpandoRow)session.get(
					ExpandoRowImpl.class, expandoRow.getPrimaryKeyObj());
			}

			if (expandoRow != null) {
				session.delete(expandoRow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoRow != null) {
			clearCache(expandoRow);
		}

		return expandoRow;
	}

	@Override
	public ExpandoRow updateImpl(ExpandoRow expandoRow) {
		boolean isNew = expandoRow.isNew();

		if (!(expandoRow instanceof ExpandoRowModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(expandoRow.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(expandoRow);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in expandoRow proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExpandoRow implementation " +
					expandoRow.getClass());
		}

		ExpandoRowModelImpl expandoRowModelImpl =
			(ExpandoRowModelImpl)expandoRow;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(expandoRow)) {
				if (!isNew) {
					session.evict(
						ExpandoRowImpl.class, expandoRow.getPrimaryKeyObj());
				}

				session.save(expandoRow);

				expandoRow.setNew(false);
			}
			else {
				expandoRow = (ExpandoRow)session.merge(expandoRow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoRow.getCtCollectionId() != 0) {
			expandoRow.resetOriginalValues();

			return expandoRow;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {expandoRowModelImpl.getTableId()};

			FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByTableId, args);

			args = new Object[] {expandoRowModelImpl.getClassPK()};

			FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByClassPK, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((expandoRowModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTableId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					expandoRowModelImpl.getOriginalTableId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);

				args = new Object[] {expandoRowModelImpl.getTableId()};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);
			}

			if ((expandoRowModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByClassPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					expandoRowModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassPK, args);

				args = new Object[] {expandoRowModelImpl.getClassPK()};

				FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassPK, args);
			}
		}

		EntityCacheUtil.putResult(
			ExpandoRowImpl.class, expandoRow.getPrimaryKey(), expandoRow,
			false);

		clearUniqueFindersCache(expandoRowModelImpl, false);
		cacheUniqueFindersCache(expandoRowModelImpl);

		expandoRow.resetOriginalValues();

		return expandoRow;
	}

	/**
	 * Returns the expando row with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando row
	 * @return the expando row
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRowException {

		ExpandoRow expandoRow = fetchByPrimaryKey(primaryKey);

		if (expandoRow == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRowException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return expandoRow;
	}

	/**
	 * Returns the expando row with the primary key or throws a <code>NoSuchRowException</code> if it could not be found.
	 *
	 * @param rowId the primary key of the expando row
	 * @return the expando row
	 * @throws NoSuchRowException if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow findByPrimaryKey(long rowId) throws NoSuchRowException {
		return findByPrimaryKey((Serializable)rowId);
	}

	/**
	 * Returns the expando row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando row
	 * @return the expando row, or <code>null</code> if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(ExpandoRow.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		ExpandoRow expandoRow = null;

		Session session = null;

		try {
			session = openSession();

			expandoRow = (ExpandoRow)session.get(
				ExpandoRowImpl.class, primaryKey);

			if (expandoRow != null) {
				cacheResult(expandoRow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return expandoRow;
	}

	/**
	 * Returns the expando row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rowId the primary key of the expando row
	 * @return the expando row, or <code>null</code> if a expando row with the primary key could not be found
	 */
	@Override
	public ExpandoRow fetchByPrimaryKey(long rowId) {
		return fetchByPrimaryKey((Serializable)rowId);
	}

	@Override
	public Map<Serializable, ExpandoRow> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(ExpandoRow.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ExpandoRow> map =
			new HashMap<Serializable, ExpandoRow>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ExpandoRow expandoRow = fetchByPrimaryKey(primaryKey);

			if (expandoRow != null) {
				map.put(primaryKey, expandoRow);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
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

			for (ExpandoRow expandoRow : (List<ExpandoRow>)query.list()) {
				map.put(expandoRow.getPrimaryKeyObj(), expandoRow);

				cacheResult(expandoRow);
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
	 * Returns all the expando rows.
	 *
	 * @return the expando rows
	 */
	@Override
	public List<ExpandoRow> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @return the range of expando rows
	 */
	@Override
	public List<ExpandoRow> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando rows
	 */
	@Override
	public List<ExpandoRow> findAll(
		int start, int end, OrderByComparator<ExpandoRow> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando rows
	 * @param end the upper bound of the range of expando rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando rows
	 */
	@Override
	public List<ExpandoRow> findAll(
		int start, int end, OrderByComparator<ExpandoRow> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ExpandoRow> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoRow>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_EXPANDOROW);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_EXPANDOROW;

				sql = sql.concat(ExpandoRowModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ExpandoRow>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the expando rows from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExpandoRow expandoRow : findAll()) {
			remove(expandoRow);
		}
	}

	/**
	 * Returns the number of expando rows.
	 *
	 * @return the number of expando rows
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoRow.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_EXPANDOROW);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "rowId_";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXPANDOROW;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return ExpandoRowModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ExpandoRow";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("tableId");
		ctStrictColumnNames.add("classPK");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("rowId_"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"tableId", "classPK"});
	}

	/**
	 * Initializes the expando row persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByTableId = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTableId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTableId = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTableId", new String[] {Long.class.getName()},
			ExpandoRowModelImpl.TABLEID_COLUMN_BITMASK);

		_finderPathCountByTableId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByTableId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByClassPK = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByClassPK",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByClassPK = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByClassPK", new String[] {Long.class.getName()},
			ExpandoRowModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByClassPK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByClassPK", new String[] {Long.class.getName()});

		_finderPathFetchByT_C = new FinderPath(
			ExpandoRowImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByT_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoRowModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoRowModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByT_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExpandoRowImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPANDOROW =
		"SELECT expandoRow FROM ExpandoRow expandoRow";

	private static final String _SQL_SELECT_EXPANDOROW_WHERE =
		"SELECT expandoRow FROM ExpandoRow expandoRow WHERE ";

	private static final String _SQL_COUNT_EXPANDOROW =
		"SELECT COUNT(expandoRow) FROM ExpandoRow expandoRow";

	private static final String _SQL_COUNT_EXPANDOROW_WHERE =
		"SELECT COUNT(expandoRow) FROM ExpandoRow expandoRow WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoRow.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExpandoRow exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExpandoRow exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoRowPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"rowId"});

}