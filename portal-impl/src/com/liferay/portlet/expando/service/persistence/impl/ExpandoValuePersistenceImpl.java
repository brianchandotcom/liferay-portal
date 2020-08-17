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

import com.liferay.expando.kernel.exception.NoSuchValueException;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.model.ExpandoValueTable;
import com.liferay.expando.kernel.service.persistence.ExpandoValuePersistence;
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
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the expando value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoValuePersistenceImpl
	extends BasePersistenceImpl<ExpandoValue>
	implements ExpandoValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExpandoValueUtil</code> to access the expando value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExpandoValueImpl.class.getName();

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
	 * Returns all the expando values where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByTableId(long tableId) {
		return findByTableId(
			tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByTableId(long tableId, int start, int end) {
		return findByTableId(tableId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByTableId(tableId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

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

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if (tableId != expandoValue.getTableId()) {
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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByTableId_First(
			long tableId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByTableId_First(
			tableId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByTableId_First(
		long tableId, OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByTableId(
			tableId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByTableId_Last(
			long tableId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByTableId_Last(
			tableId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByTableId_Last(
		long tableId, OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByTableId(tableId);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByTableId(
			tableId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByTableId_PrevAndNext(
			long valueId, long tableId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByTableId_PrevAndNext(
				session, expandoValue, tableId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByTableId_PrevAndNext(
				session, expandoValue, tableId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByTableId_PrevAndNext(
		Session session, ExpandoValue expandoValue, long tableId,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 */
	@Override
	public void removeByTableId(long tableId) {
		for (ExpandoValue expandoValue :
				findByTableId(
					tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByTableId(long tableId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

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

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

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
		"expandoValue.tableId = ?";

	private FinderPath _finderPathWithPaginationFindByColumnId;
	private FinderPath _finderPathWithoutPaginationFindByColumnId;
	private FinderPath _finderPathCountByColumnId;

	/**
	 * Returns all the expando values where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByColumnId(long columnId) {
		return findByColumnId(
			columnId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByColumnId(
		long columnId, int start, int end) {

		return findByColumnId(columnId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByColumnId(columnId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByColumnId;
				finderArgs = new Object[] {columnId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByColumnId;
			finderArgs = new Object[] {columnId, start, end, orderByComparator};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if (columnId != expandoValue.getColumnId()) {
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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByColumnId_First(
			long columnId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByColumnId_First(
			columnId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("columnId=");
		sb.append(columnId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByColumnId_First(
		long columnId, OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByColumnId(
			columnId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByColumnId_Last(
			long columnId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByColumnId_Last(
			columnId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("columnId=");
		sb.append(columnId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByColumnId_Last(
		long columnId, OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByColumnId(columnId);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByColumnId(
			columnId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where columnId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByColumnId_PrevAndNext(
			long valueId, long columnId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByColumnId_PrevAndNext(
				session, expandoValue, columnId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByColumnId_PrevAndNext(
				session, expandoValue, columnId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByColumnId_PrevAndNext(
		Session session, ExpandoValue expandoValue, long columnId,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(columnId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where columnId = &#63; from the database.
	 *
	 * @param columnId the column ID
	 */
	@Override
	public void removeByColumnId(long columnId) {
		for (ExpandoValue expandoValue :
				findByColumnId(
					columnId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByColumnId(long columnId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByColumnId;

			finderArgs = new Object[] {columnId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(columnId);

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

	private static final String _FINDER_COLUMN_COLUMNID_COLUMNID_2 =
		"expandoValue.columnId = ?";

	private FinderPath _finderPathWithPaginationFindByRowId;
	private FinderPath _finderPathWithoutPaginationFindByRowId;
	private FinderPath _finderPathCountByRowId;

	/**
	 * Returns all the expando values where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByRowId(long rowId) {
		return findByRowId(rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByRowId(long rowId, int start, int end) {
		return findByRowId(rowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByRowId(
		long rowId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByRowId(rowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByRowId(
		long rowId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRowId;
				finderArgs = new Object[] {rowId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRowId;
			finderArgs = new Object[] {rowId, start, end, orderByComparator};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if (rowId != expandoValue.getRowId()) {
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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_ROWID_ROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByRowId_First(
			long rowId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByRowId_First(
			rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("rowId=");
		sb.append(rowId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByRowId_First(
		long rowId, OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByRowId(rowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByRowId_Last(
			long rowId, OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByRowId_Last(rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("rowId=");
		sb.append(rowId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByRowId_Last(
		long rowId, OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByRowId(rowId);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByRowId(
			rowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where rowId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByRowId_PrevAndNext(
			long valueId, long rowId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByRowId_PrevAndNext(
				session, expandoValue, rowId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByRowId_PrevAndNext(
				session, expandoValue, rowId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByRowId_PrevAndNext(
		Session session, ExpandoValue expandoValue, long rowId,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_ROWID_ROWID_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(rowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where rowId = &#63; from the database.
	 *
	 * @param rowId the row ID
	 */
	@Override
	public void removeByRowId(long rowId) {
		for (ExpandoValue expandoValue :
				findByRowId(
					rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByRowId(long rowId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRowId;

			finderArgs = new Object[] {rowId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_ROWID_ROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(rowId);

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

	private static final String _FINDER_COLUMN_ROWID_ROWID_2 =
		"expandoValue.rowId = ?";

	private FinderPath _finderPathWithPaginationFindByT_C;
	private FinderPath _finderPathWithoutPaginationFindByT_C;
	private FinderPath _finderPathCountByT_C;

	/**
	 * Returns all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C(long tableId, long columnId) {
		return findByT_C(
			tableId, columnId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end) {

		return findByT_C(tableId, columnId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByT_C(
			tableId, columnId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByT_C;
				finderArgs = new Object[] {tableId, columnId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByT_C;
			finderArgs = new Object[] {
				tableId, columnId, start, end, orderByComparator
			};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if ((tableId != expandoValue.getTableId()) ||
						(columnId != expandoValue.getColumnId())) {

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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_COLUMNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_C_First(
			long tableId, long columnId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_C_First(
			tableId, columnId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", columnId=");
		sb.append(columnId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_First(
		long tableId, long columnId,
		OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByT_C(
			tableId, columnId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_C_Last(
			long tableId, long columnId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_C_Last(
			tableId, columnId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", columnId=");
		sb.append(columnId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_Last(
		long tableId, long columnId,
		OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByT_C(tableId, columnId);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByT_C(
			tableId, columnId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByT_C_PrevAndNext(
			long valueId, long tableId, long columnId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_C_PrevAndNext(
				session, expandoValue, tableId, columnId, orderByComparator,
				true);

			array[1] = expandoValue;

			array[2] = getByT_C_PrevAndNext(
				session, expandoValue, tableId, columnId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_C_PrevAndNext(
		Session session, ExpandoValue expandoValue, long tableId, long columnId,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_T_C_TABLEID_2);

		sb.append(_FINDER_COLUMN_T_C_COLUMNID_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		queryPos.add(columnId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and columnId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 */
	@Override
	public void removeByT_C(long tableId, long columnId) {
		for (ExpandoValue expandoValue :
				findByT_C(
					tableId, columnId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByT_C(long tableId, long columnId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_C;

			finderArgs = new Object[] {tableId, columnId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_COLUMNID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

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
		"expandoValue.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_COLUMNID_2 =
		"expandoValue.columnId = ?";

	private FinderPath _finderPathWithPaginationFindByT_R;
	private FinderPath _finderPathWithoutPaginationFindByT_R;
	private FinderPath _finderPathCountByT_R;

	/**
	 * Returns all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_R(long tableId, long rowId) {
		return findByT_R(
			tableId, rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end) {

		return findByT_R(tableId, rowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByT_R(tableId, rowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByT_R;
				finderArgs = new Object[] {tableId, rowId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByT_R;
			finderArgs = new Object[] {
				tableId, rowId, start, end, orderByComparator
			};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if ((tableId != expandoValue.getTableId()) ||
						(rowId != expandoValue.getRowId())) {

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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_R_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_R_ROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_R_First(
			long tableId, long rowId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_R_First(
			tableId, rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", rowId=");
		sb.append(rowId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_R_First(
		long tableId, long rowId,
		OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByT_R(
			tableId, rowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_R_Last(
			long tableId, long rowId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_R_Last(
			tableId, rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", rowId=");
		sb.append(rowId);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_R_Last(
		long tableId, long rowId,
		OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByT_R(tableId, rowId);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByT_R(
			tableId, rowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByT_R_PrevAndNext(
			long valueId, long tableId, long rowId,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_R_PrevAndNext(
				session, expandoValue, tableId, rowId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_R_PrevAndNext(
				session, expandoValue, tableId, rowId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_R_PrevAndNext(
		Session session, ExpandoValue expandoValue, long tableId, long rowId,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_T_R_TABLEID_2);

		sb.append(_FINDER_COLUMN_T_R_ROWID_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		queryPos.add(rowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and rowId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 */
	@Override
	public void removeByT_R(long tableId, long rowId) {
		for (ExpandoValue expandoValue :
				findByT_R(
					tableId, rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByT_R(long tableId, long rowId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_R;

			finderArgs = new Object[] {tableId, rowId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_R_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_R_ROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(rowId);

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

	private static final String _FINDER_COLUMN_T_R_TABLEID_2 =
		"expandoValue.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_R_ROWID_2 =
		"expandoValue.rowId = ?";

	private FinderPath _finderPathWithPaginationFindByT_CPK;
	private FinderPath _finderPathWithoutPaginationFindByT_CPK;
	private FinderPath _finderPathCountByT_CPK;

	/**
	 * Returns all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_CPK(long tableId, long classPK) {
		return findByT_CPK(
			tableId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end) {

		return findByT_CPK(tableId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByT_CPK(
			tableId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByT_CPK;
				finderArgs = new Object[] {tableId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByT_CPK;
			finderArgs = new Object[] {
				tableId, classPK, start, end, orderByComparator
			};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if ((tableId != expandoValue.getTableId()) ||
						(classPK != expandoValue.getClassPK())) {

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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_CPK_First(
			long tableId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_CPK_First(
			tableId, classPK, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_CPK_First(
		long tableId, long classPK,
		OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByT_CPK(
			tableId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_CPK_Last(
			long tableId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_CPK_Last(
			tableId, classPK, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_CPK_Last(
		long tableId, long classPK,
		OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByT_CPK(tableId, classPK);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByT_CPK(
			tableId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByT_CPK_PrevAndNext(
			long valueId, long tableId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_CPK_PrevAndNext(
				session, expandoValue, tableId, classPK, orderByComparator,
				true);

			array[1] = expandoValue;

			array[2] = getByT_CPK_PrevAndNext(
				session, expandoValue, tableId, classPK, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_CPK_PrevAndNext(
		Session session, ExpandoValue expandoValue, long tableId, long classPK,
		OrderByComparator<ExpandoValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

		sb.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and classPK = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByT_CPK(long tableId, long classPK) {
		for (ExpandoValue expandoValue :
				findByT_CPK(
					tableId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class pk
	 * @return the number of matching expando values
	 */
	@Override
	public int countByT_CPK(long tableId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_CPK;

			finderArgs = new Object[] {tableId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

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

	private static final String _FINDER_COLUMN_T_CPK_TABLEID_2 =
		"expandoValue.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_CPK_CLASSPK_2 =
		"expandoValue.classPK = ?";

	private FinderPath _finderPathFetchByC_R;
	private FinderPath _finderPathCountByC_R;

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or throws a <code>NoSuchValueException</code> if it could not be found.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByC_R(columnId, rowId);

		if (expandoValue == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("columnId=");
			sb.append(columnId);

			sb.append(", rowId=");
			sb.append(rowId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchValueException(sb.toString());
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByC_R(long columnId, long rowId) {
		return fetchByC_R(columnId, rowId, true);
	}

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByC_R(
		long columnId, long rowId, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {columnId, rowId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_R, finderArgs, this);
		}

		if (result instanceof ExpandoValue) {
			ExpandoValue expandoValue = (ExpandoValue)result;

			if ((columnId != expandoValue.getColumnId()) ||
				(rowId != expandoValue.getRowId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_R_COLUMNID_2);

			sb.append(_FINDER_COLUMN_C_R_ROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(columnId);

				queryPos.add(rowId);

				List<ExpandoValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_R, finderArgs, list);
					}
				}
				else {
					ExpandoValue expandoValue = list.get(0);

					result = expandoValue;

					cacheResult(expandoValue);
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
			return (ExpandoValue)result;
		}
	}

	/**
	 * Removes the expando value where columnId = &#63; and rowId = &#63; from the database.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the expando value that was removed
	 */
	@Override
	public ExpandoValue removeByC_R(long columnId, long rowId)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByC_R(columnId, rowId);

		return remove(expandoValue);
	}

	/**
	 * Returns the number of expando values where columnId = &#63; and rowId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 */
	@Override
	public int countByC_R(long columnId, long rowId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_R;

			finderArgs = new Object[] {columnId, rowId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_R_COLUMNID_2);

			sb.append(_FINDER_COLUMN_C_R_ROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(columnId);

				queryPos.add(rowId);

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

	private static final String _FINDER_COLUMN_C_R_COLUMNID_2 =
		"expandoValue.columnId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_ROWID_2 =
		"expandoValue.rowId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if ((classNameId != expandoValue.getClassNameId()) ||
						(classPK != expandoValue.getClassPK())) {

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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByC_C_PrevAndNext(
			long valueId, long classNameId, long classPK,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, expandoValue, classNameId, classPK, orderByComparator,
				true);

			array[1] = expandoValue;

			array[2] = getByC_C_PrevAndNext(
				session, expandoValue, classNameId, classPK, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByC_C_PrevAndNext(
		Session session, ExpandoValue expandoValue, long classNameId,
		long classPK, OrderByComparator<ExpandoValue> orderByComparator,
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

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (ExpandoValue expandoValue :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching expando values
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"expandoValue.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"expandoValue.classPK = ?";

	private FinderPath _finderPathFetchByT_C_C;
	private FinderPath _finderPathCountByT_C_C;

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or throws a <code>NoSuchValueException</code> if it could not be found.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class pk
	 * @return the matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_C_C(tableId, columnId, classPK);

		if (expandoValue == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("tableId=");
			sb.append(tableId);

			sb.append(", columnId=");
			sb.append(columnId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchValueException(sb.toString());
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class pk
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK) {

		return fetchByT_C_C(tableId, columnId, classPK, true);
	}

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {tableId, columnId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByT_C_C, finderArgs, this);
		}

		if (result instanceof ExpandoValue) {
			ExpandoValue expandoValue = (ExpandoValue)result;

			if ((tableId != expandoValue.getTableId()) ||
				(columnId != expandoValue.getColumnId()) ||
				(classPK != expandoValue.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

			sb.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

				queryPos.add(classPK);

				List<ExpandoValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByT_C_C, finderArgs, list);
					}
				}
				else {
					ExpandoValue expandoValue = list.get(0);

					result = expandoValue;

					cacheResult(expandoValue);
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
			return (ExpandoValue)result;
		}
	}

	/**
	 * Removes the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class pk
	 * @return the expando value that was removed
	 */
	@Override
	public ExpandoValue removeByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException {

		ExpandoValue expandoValue = findByT_C_C(tableId, columnId, classPK);

		return remove(expandoValue);
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class pk
	 * @return the number of matching expando values
	 */
	@Override
	public int countByT_C_C(long tableId, long columnId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_C_C;

			finderArgs = new Object[] {tableId, columnId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

			sb.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

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

	private static final String _FINDER_COLUMN_T_C_C_TABLEID_2 =
		"expandoValue.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_C_COLUMNID_2 =
		"expandoValue.columnId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_C_CLASSPK_2 =
		"expandoValue.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByT_C_D;
	private FinderPath _finderPathWithoutPaginationFindByT_C_D;
	private FinderPath _finderPathCountByT_C_D;

	/**
	 * Returns all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @return the matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C_D(
		long tableId, long columnId, String data) {

		return findByT_C_D(
			tableId, columnId, data, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C_D(
		long tableId, long columnId, String data, int start, int end) {

		return findByT_C_D(tableId, columnId, data, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C_D(
		long tableId, long columnId, String data, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator) {

		return findByT_C_D(
			tableId, columnId, data, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando values
	 */
	@Override
	public List<ExpandoValue> findByT_C_D(
		long tableId, long columnId, String data, int start, int end,
		OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		data = Objects.toString(data, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByT_C_D;
				finderArgs = new Object[] {tableId, columnId, data};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByT_C_D;
			finderArgs = new Object[] {
				tableId, columnId, data, start, end, orderByComparator
			};
		}

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoValue expandoValue : list) {
					if ((tableId != expandoValue.getTableId()) ||
						(columnId != expandoValue.getColumnId()) ||
						!data.equals(expandoValue.getData())) {

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

			sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

			boolean bindData = false;

			if (data.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_C_D_DATA_3);
			}
			else {
				bindData = true;

				sb.append(_FINDER_COLUMN_T_C_D_DATA_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

				if (bindData) {
					queryPos.add(data);
				}

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_C_D_First(
			long tableId, long columnId, String data,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_C_D_First(
			tableId, columnId, data, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", columnId=");
		sb.append(columnId);

		sb.append(", data=");
		sb.append(data);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_D_First(
		long tableId, long columnId, String data,
		OrderByComparator<ExpandoValue> orderByComparator) {

		List<ExpandoValue> list = findByT_C_D(
			tableId, columnId, data, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws NoSuchValueException if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue findByT_C_D_Last(
			long tableId, long columnId, String data,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByT_C_D_Last(
			tableId, columnId, data, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("tableId=");
		sb.append(tableId);

		sb.append(", columnId=");
		sb.append(columnId);

		sb.append(", data=");
		sb.append(data);

		sb.append("}");

		throw new NoSuchValueException(sb.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 */
	@Override
	public ExpandoValue fetchByT_C_D_Last(
		long tableId, long columnId, String data,
		OrderByComparator<ExpandoValue> orderByComparator) {

		int count = countByT_C_D(tableId, columnId, data);

		if (count == 0) {
			return null;
		}

		List<ExpandoValue> list = findByT_C_D(
			tableId, columnId, data, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue[] findByT_C_D_PrevAndNext(
			long valueId, long tableId, long columnId, String data,
			OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException {

		data = Objects.toString(data, "");

		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_C_D_PrevAndNext(
				session, expandoValue, tableId, columnId, data,
				orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_C_D_PrevAndNext(
				session, expandoValue, tableId, columnId, data,
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

	protected ExpandoValue getByT_C_D_PrevAndNext(
		Session session, ExpandoValue expandoValue, long tableId, long columnId,
		String data, OrderByComparator<ExpandoValue> orderByComparator,
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

		sb.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		sb.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

		sb.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

		boolean bindData = false;

		if (data.isEmpty()) {
			sb.append(_FINDER_COLUMN_T_C_D_DATA_3);
		}
		else {
			bindData = true;

			sb.append(_FINDER_COLUMN_T_C_D_DATA_2);
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
			sb.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(tableId);

		queryPos.add(columnId);

		if (bindData) {
			queryPos.add(data);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and columnId = &#63; and data = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 */
	@Override
	public void removeByT_C_D(long tableId, long columnId, String data) {
		for (ExpandoValue expandoValue :
				findByT_C_D(
					tableId, columnId, data, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @return the number of matching expando values
	 */
	@Override
	public int countByT_C_D(long tableId, long columnId, String data) {
		data = Objects.toString(data, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByT_C_D;

			finderArgs = new Object[] {tableId, columnId, data};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			sb.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

			sb.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

			boolean bindData = false;

			if (data.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_C_D_DATA_3);
			}
			else {
				bindData = true;

				sb.append(_FINDER_COLUMN_T_C_D_DATA_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(tableId);

				queryPos.add(columnId);

				if (bindData) {
					queryPos.add(data);
				}

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

	private static final String _FINDER_COLUMN_T_C_D_TABLEID_2 =
		"expandoValue.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_D_COLUMNID_2 =
		"expandoValue.columnId = ? AND ";

	private static final String _FINDER_COLUMN_T_C_D_DATA_2 =
		"CAST_CLOB_TEXT(expandoValue.data) = ?";

	private static final String _FINDER_COLUMN_T_C_D_DATA_3 =
		"(expandoValue.data IS NULL OR CAST_CLOB_TEXT(expandoValue.data) = '')";

	public ExpandoValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("rowId", "rowId_");
		dbColumnNames.put("data", "data_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ExpandoValue.class);

		setModelImplClass(ExpandoValueImpl.class);
		setModelPKClass(long.class);

		setTable(ExpandoValueTable.INSTANCE);
	}

	/**
	 * Caches the expando value in the entity cache if it is enabled.
	 *
	 * @param expandoValue the expando value
	 */
	@Override
	public void cacheResult(ExpandoValue expandoValue) {
		if (expandoValue.getCtCollectionId() != 0) {
			expandoValue.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_R,
			new Object[] {expandoValue.getColumnId(), expandoValue.getRowId()},
			expandoValue);

		FinderCacheUtil.putResult(
			_finderPathFetchByT_C_C,
			new Object[] {
				expandoValue.getTableId(), expandoValue.getColumnId(),
				expandoValue.getClassPK()
			},
			expandoValue);

		expandoValue.resetOriginalValues();
	}

	/**
	 * Caches the expando values in the entity cache if it is enabled.
	 *
	 * @param expandoValues the expando values
	 */
	@Override
	public void cacheResult(List<ExpandoValue> expandoValues) {
		for (ExpandoValue expandoValue : expandoValues) {
			if (expandoValue.getCtCollectionId() != 0) {
				expandoValue.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					ExpandoValueImpl.class, expandoValue.getPrimaryKey()) ==
						null) {

				cacheResult(expandoValue);
			}
			else {
				expandoValue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all expando values.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ExpandoValueImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the expando value.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExpandoValue expandoValue) {
		EntityCacheUtil.removeResult(
			ExpandoValueImpl.class, expandoValue.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ExpandoValueModelImpl)expandoValue, true);
	}

	@Override
	public void clearCache(List<ExpandoValue> expandoValues) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExpandoValue expandoValue : expandoValues) {
			EntityCacheUtil.removeResult(
				ExpandoValueImpl.class, expandoValue.getPrimaryKey());

			clearUniqueFindersCache((ExpandoValueModelImpl)expandoValue, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ExpandoValueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ExpandoValueModelImpl expandoValueModelImpl) {

		Object[] args = new Object[] {
			expandoValueModelImpl.getColumnId(),
			expandoValueModelImpl.getRowId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_R, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_R, args, expandoValueModelImpl, false);

		args = new Object[] {
			expandoValueModelImpl.getTableId(),
			expandoValueModelImpl.getColumnId(),
			expandoValueModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByT_C_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByT_C_C, args, expandoValueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ExpandoValueModelImpl expandoValueModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				expandoValueModelImpl.getColumnId(),
				expandoValueModelImpl.getRowId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_R, args);
		}

		if ((expandoValueModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_R.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				expandoValueModelImpl.getOriginalColumnId(),
				expandoValueModelImpl.getOriginalRowId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_R, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				expandoValueModelImpl.getTableId(),
				expandoValueModelImpl.getColumnId(),
				expandoValueModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_C_C, args);
		}

		if ((expandoValueModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_C_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				expandoValueModelImpl.getOriginalTableId(),
				expandoValueModelImpl.getOriginalColumnId(),
				expandoValueModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_C_C, args);
		}
	}

	/**
	 * Creates a new expando value with the primary key. Does not add the expando value to the database.
	 *
	 * @param valueId the primary key for the new expando value
	 * @return the new expando value
	 */
	@Override
	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		expandoValue.setCompanyId(CompanyThreadLocal.getCompanyId());

		return expandoValue;
	}

	/**
	 * Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value that was removed
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue remove(long valueId) throws NoSuchValueException {
		return remove((Serializable)valueId);
	}

	/**
	 * Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value that was removed
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue remove(Serializable primaryKey)
		throws NoSuchValueException {

		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(
				ExpandoValueImpl.class, primaryKey);

			if (expandoValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchValueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(expandoValue);
		}
		catch (NoSuchValueException noSuchEntityException) {
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
	protected ExpandoValue removeImpl(ExpandoValue expandoValue) {
		if (!CTPersistenceHelperUtil.isRemove(expandoValue)) {
			return expandoValue;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(expandoValue)) {
				expandoValue = (ExpandoValue)session.get(
					ExpandoValueImpl.class, expandoValue.getPrimaryKeyObj());
			}

			if (expandoValue != null) {
				session.delete(expandoValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoValue != null) {
			clearCache(expandoValue);
		}

		return expandoValue;
	}

	@Override
	public ExpandoValue updateImpl(ExpandoValue expandoValue) {
		boolean isNew = expandoValue.isNew();

		if (!(expandoValue instanceof ExpandoValueModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(expandoValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					expandoValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in expandoValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExpandoValue implementation " +
					expandoValue.getClass());
		}

		ExpandoValueModelImpl expandoValueModelImpl =
			(ExpandoValueModelImpl)expandoValue;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(expandoValue)) {
				if (!isNew) {
					session.evict(
						ExpandoValueImpl.class,
						expandoValue.getPrimaryKeyObj());
				}

				session.save(expandoValue);

				expandoValue.setNew(false);
			}
			else {
				expandoValue = (ExpandoValue)session.merge(expandoValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoValue.getCtCollectionId() != 0) {
			expandoValue.resetOriginalValues();

			return expandoValue;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {expandoValueModelImpl.getTableId()};

			FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByTableId, args);

			args = new Object[] {expandoValueModelImpl.getColumnId()};

			FinderCacheUtil.removeResult(_finderPathCountByColumnId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByColumnId, args);

			args = new Object[] {expandoValueModelImpl.getRowId()};

			FinderCacheUtil.removeResult(_finderPathCountByRowId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByRowId, args);

			args = new Object[] {
				expandoValueModelImpl.getTableId(),
				expandoValueModelImpl.getColumnId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByT_C, args);

			args = new Object[] {
				expandoValueModelImpl.getTableId(),
				expandoValueModelImpl.getRowId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_R, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByT_R, args);

			args = new Object[] {
				expandoValueModelImpl.getTableId(),
				expandoValueModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_CPK, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByT_CPK, args);

			args = new Object[] {
				expandoValueModelImpl.getClassNameId(),
				expandoValueModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				expandoValueModelImpl.getTableId(),
				expandoValueModelImpl.getColumnId(),
				expandoValueModelImpl.getData()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_C_D, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByT_C_D, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTableId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalTableId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);

				args = new Object[] {expandoValueModelImpl.getTableId()};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByColumnId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalColumnId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByColumnId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByColumnId, args);

				args = new Object[] {expandoValueModelImpl.getColumnId()};

				FinderCacheUtil.removeResult(_finderPathCountByColumnId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByColumnId, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRowId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalRowId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByRowId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRowId, args);

				args = new Object[] {expandoValueModelImpl.getRowId()};

				FinderCacheUtil.removeResult(_finderPathCountByRowId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRowId, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalTableId(),
					expandoValueModelImpl.getOriginalColumnId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_C, args);

				args = new Object[] {
					expandoValueModelImpl.getTableId(),
					expandoValueModelImpl.getColumnId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_C, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_R.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalTableId(),
					expandoValueModelImpl.getOriginalRowId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_R, args);

				args = new Object[] {
					expandoValueModelImpl.getTableId(),
					expandoValueModelImpl.getRowId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_R, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_CPK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalTableId(),
					expandoValueModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_CPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_CPK, args);

				args = new Object[] {
					expandoValueModelImpl.getTableId(),
					expandoValueModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_CPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_CPK, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalClassNameId(),
					expandoValueModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					expandoValueModelImpl.getClassNameId(),
					expandoValueModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_C_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoValueModelImpl.getOriginalTableId(),
					expandoValueModelImpl.getOriginalColumnId(),
					expandoValueModelImpl.getOriginalData()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_C_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_C_D, args);

				args = new Object[] {
					expandoValueModelImpl.getTableId(),
					expandoValueModelImpl.getColumnId(),
					expandoValueModelImpl.getData()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_C_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_C_D, args);
			}
		}

		EntityCacheUtil.putResult(
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue,
			false);

		clearUniqueFindersCache(expandoValueModelImpl, false);
		cacheUniqueFindersCache(expandoValueModelImpl);

		expandoValue.resetOriginalValues();

		return expandoValue;
	}

	/**
	 * Returns the expando value with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchValueException {

		ExpandoValue expandoValue = fetchByPrimaryKey(primaryKey);

		if (expandoValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchValueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value with the primary key or throws a <code>NoSuchValueException</code> if it could not be found.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value
	 * @throws NoSuchValueException if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException {

		return findByPrimaryKey((Serializable)valueId);
	}

	/**
	 * Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(ExpandoValue.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		ExpandoValue expandoValue = null;

		Session session = null;

		try {
			session = openSession();

			expandoValue = (ExpandoValue)session.get(
				ExpandoValueImpl.class, primaryKey);

			if (expandoValue != null) {
				cacheResult(expandoValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	 */
	@Override
	public ExpandoValue fetchByPrimaryKey(long valueId) {
		return fetchByPrimaryKey((Serializable)valueId);
	}

	@Override
	public Map<Serializable, ExpandoValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(ExpandoValue.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ExpandoValue> map =
			new HashMap<Serializable, ExpandoValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ExpandoValue expandoValue = fetchByPrimaryKey(primaryKey);

			if (expandoValue != null) {
				map.put(primaryKey, expandoValue);
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

			for (ExpandoValue expandoValue : (List<ExpandoValue>)query.list()) {
				map.put(expandoValue.getPrimaryKeyObj(), expandoValue);

				cacheResult(expandoValue);
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
	 * Returns all the expando values.
	 *
	 * @return the expando values
	 */
	@Override
	public List<ExpandoValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of expando values
	 */
	@Override
	public List<ExpandoValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando values
	 */
	@Override
	public List<ExpandoValue> findAll(
		int start, int end, OrderByComparator<ExpandoValue> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando values
	 */
	@Override
	public List<ExpandoValue> findAll(
		int start, int end, OrderByComparator<ExpandoValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

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

		List<ExpandoValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_EXPANDOVALUE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_EXPANDOVALUE;

				sql = sql.concat(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ExpandoValue>)QueryUtil.list(
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
	 * Removes all the expando values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values.
	 *
	 * @return the number of expando values
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoValue.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_EXPANDOVALUE);

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
		return "valueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXPANDOVALUE;
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
		return ExpandoValueModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ExpandoValue";
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
		ctStrictColumnNames.add("tableId");
		ctStrictColumnNames.add("columnId");
		ctStrictColumnNames.add("rowId_");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("data_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("valueId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"columnId", "rowId_"});

		_uniqueIndexColumnNames.add(
			new String[] {"tableId", "columnId", "classPK"});
	}

	/**
	 * Initializes the expando value persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByTableId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTableId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTableId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTableId", new String[] {Long.class.getName()},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);

		_finderPathCountByTableId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByTableId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByColumnId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByColumnId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByColumnId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByColumnId", new String[] {Long.class.getName()},
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);

		_finderPathCountByColumnId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByColumnId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRowId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRowId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRowId = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByRowId", new String[] {Long.class.getName()},
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);

		_finderPathCountByRowId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRowId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByT_C = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_C = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByT_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);

		_finderPathCountByT_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByT_R = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_R = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByT_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);

		_finderPathCountByT_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_R",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByT_CPK = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_CPK = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByT_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);

		_finderPathCountByT_CPK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByT_CPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_R = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);

		_finderPathCountByC_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoValueModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByT_C_C = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByT_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByT_C_D = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_C_D = new FinderPath(
			ExpandoValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.DATA_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);

		_finderPathCountByT_C_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExpandoValueImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPANDOVALUE =
		"SELECT expandoValue FROM ExpandoValue expandoValue";

	private static final String _SQL_SELECT_EXPANDOVALUE_WHERE =
		"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ";

	private static final String _SQL_COUNT_EXPANDOVALUE =
		"SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue";

	private static final String _SQL_COUNT_EXPANDOVALUE_WHERE =
		"SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoValue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExpandoValue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExpandoValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"rowId", "data"});

}