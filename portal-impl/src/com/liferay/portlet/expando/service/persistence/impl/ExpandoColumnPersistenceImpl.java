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

import com.liferay.expando.kernel.exception.NoSuchColumnException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.persistence.ExpandoColumnPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the expando column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnPersistenceImpl
	extends BasePersistenceImpl<ExpandoColumn>
	implements ExpandoColumnPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExpandoColumnUtil</code> to access the expando column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExpandoColumnImpl.class.getName();

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
	 * Returns all the expando columns where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByTableId(long tableId) {
		return findByTableId(
			tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando columns where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByTableId(long tableId, int start, int end) {
		return findByTableId(tableId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {

		return findByTableId(tableId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTableId;
				finderArgs = new Object[] {tableId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTableId;
			finderArgs = new Object[] {tableId, start, end, orderByComparator};
		}

		List<ExpandoColumn> list = null;

		if (useFinderCache) {
			list = (List<ExpandoColumn>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoColumn expandoColumn : list) {
					if (tableId != expandoColumn.getTableId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = (List<ExpandoColumn>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn findByTableId_First(
			long tableId, OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = fetchByTableId_First(
			tableId, orderByComparator);

		if (expandoColumn != null) {
			return expandoColumn;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append("}");

		throw new NoSuchColumnException(msg.toString());
	}

	/**
	 * Returns the first expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn fetchByTableId_First(
		long tableId, OrderByComparator<ExpandoColumn> orderByComparator) {

		List<ExpandoColumn> list = findByTableId(
			tableId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn findByTableId_Last(
			long tableId, OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = fetchByTableId_Last(
			tableId, orderByComparator);

		if (expandoColumn != null) {
			return expandoColumn;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append("}");

		throw new NoSuchColumnException(msg.toString());
	}

	/**
	 * Returns the last expando column in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn fetchByTableId_Last(
		long tableId, OrderByComparator<ExpandoColumn> orderByComparator) {

		int count = countByTableId(tableId);

		if (count == 0) {
			return null;
		}

		List<ExpandoColumn> list = findByTableId(
			tableId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63;.
	 *
	 * @param columnId the primary key of the current expando column
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn[] findByTableId_PrevAndNext(
			long columnId, long tableId,
			OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		Session session = null;

		try {
			session = openSession();

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = getByTableId_PrevAndNext(
				session, expandoColumn, tableId, orderByComparator, true);

			array[1] = expandoColumn;

			array[2] = getByTableId_PrevAndNext(
				session, expandoColumn, tableId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoColumn getByTableId_PrevAndNext(
		Session session, ExpandoColumn expandoColumn, long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						expandoColumn)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ExpandoColumn> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the expando columns that the user has permission to view where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the matching expando columns that the user has permission to view
	 */
	@Override
	public List<ExpandoColumn> filterFindByTableId(long tableId) {
		return filterFindByTableId(
			tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando columns that the user has permission to view where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns that the user has permission to view
	 */
	@Override
	public List<ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end) {

		return filterFindByTableId(tableId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns that the user has permission to view
	 */
	@Override
	public List<ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByTableId(tableId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_EXPANDOCOLUMN_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ExpandoColumnModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ExpandoColumn.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ExpandoColumnImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ExpandoColumnImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			return (List<ExpandoColumn>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the expando columns before and after the current expando column in the ordered set of expando columns that the user has permission to view where tableId = &#63;.
	 *
	 * @param columnId the primary key of the current expando column
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn[] filterFindByTableId_PrevAndNext(
			long columnId, long tableId,
			OrderByComparator<ExpandoColumn> orderByComparator)
		throws NoSuchColumnException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByTableId_PrevAndNext(
				columnId, tableId, orderByComparator);
		}

		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		Session session = null;

		try {
			session = openSession();

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = filterGetByTableId_PrevAndNext(
				session, expandoColumn, tableId, orderByComparator, true);

			array[1] = expandoColumn;

			array[2] = filterGetByTableId_PrevAndNext(
				session, expandoColumn, tableId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoColumn filterGetByTableId_PrevAndNext(
		Session session, ExpandoColumn expandoColumn, long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_EXPANDOCOLUMN_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ExpandoColumnModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ExpandoColumn.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ExpandoColumnImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ExpandoColumnImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						expandoColumn)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ExpandoColumn> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando columns where tableId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 */
	@Override
	public void removeByTableId(long tableId) {
		for (ExpandoColumn expandoColumn :
				findByTableId(
					tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(expandoColumn);
		}
	}

	/**
	 * Returns the number of expando columns where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the number of matching expando columns
	 */
	@Override
	public int countByTableId(long tableId) {
		FinderPath finderPath = _finderPathCountByTableId;

		Object[] finderArgs = new Object[] {tableId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of expando columns that the user has permission to view where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the number of matching expando columns that the user has permission to view
	 */
	@Override
	public int filterCountByTableId(long tableId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByTableId(tableId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_EXPANDOCOLUMN_WHERE);

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ExpandoColumn.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

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

	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 =
		"expandoColumn.tableId = ?";

	private FinderPath _finderPathWithPaginationFindByT_N;
	private FinderPath _finderPathWithoutPaginationFindByT_N;
	private FinderPath _finderPathFetchByT_N;
	private FinderPath _finderPathCountByT_N;
	private FinderPath _finderPathWithPaginationCountByT_N;

	/**
	 * Returns all the expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByT_N(long tableId, String[] names) {
		return findByT_N(
			tableId, names, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end) {

		return findByT_N(tableId, names, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {

		return findByT_N(tableId, names, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando columns where tableId = &#63; and name = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando columns
	 */
	@Override
	public List<ExpandoColumn> findByT_N(
		long tableId, String[] names, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator,
		boolean useFinderCache) {

		if (names == null) {
			names = new String[0];
		}
		else if (names.length > 1) {
			for (int i = 0; i < names.length; i++) {
				names[i] = Objects.toString(names[i], "");
			}

			names = ArrayUtil.sortedUnique(names);
		}

		if (names.length == 1) {
			ExpandoColumn expandoColumn = fetchByT_N(tableId, names[0]);

			if (expandoColumn == null) {
				return Collections.emptyList();
			}
			else {
				List<ExpandoColumn> list = new ArrayList<ExpandoColumn>(1);

				list.add(expandoColumn);

				return list;
			}
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {tableId, StringUtil.merge(names)};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				tableId, StringUtil.merge(names), start, end, orderByComparator
			};
		}

		List<ExpandoColumn> list = null;

		if (useFinderCache) {
			list = (List<ExpandoColumn>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByT_N, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoColumn expandoColumn : list) {
					if ((tableId != expandoColumn.getTableId()) ||
						!ArrayUtil.contains(names, expandoColumn.getName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_T_N_TABLEID_2);

			if (names.length > 0) {
				query.append("(");

				for (int i = 0; i < names.length; i++) {
					String name = names[i];

					if (name.isEmpty()) {
						query.append(_FINDER_COLUMN_T_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_N_NAME_2);
					}

					if ((i + 1) < names.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ExpandoColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				for (String name : names) {
					if ((name != null) && !name.isEmpty()) {
						qPos.add(name);
					}
				}

				list = (List<ExpandoColumn>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByT_N, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationFindByT_N, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or throws a <code>NoSuchColumnException</code> if it could not be found.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the matching expando column
	 * @throws NoSuchColumnException if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn findByT_N(long tableId, String name)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = fetchByT_N(tableId, name);

		if (expandoColumn == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchColumnException(msg.toString());
		}

		return expandoColumn;
	}

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn fetchByT_N(long tableId, String name) {
		return fetchByT_N(tableId, name, true);
	}

	/**
	 * Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	 */
	@Override
	public ExpandoColumn fetchByT_N(
		long tableId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {tableId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByT_N, finderArgs, this);
		}

		if (result instanceof ExpandoColumn) {
			ExpandoColumn expandoColumn = (ExpandoColumn)result;

			if ((tableId != expandoColumn.getTableId()) ||
				!Objects.equals(name, expandoColumn.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_T_N_TABLEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_T_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_T_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (bindName) {
					qPos.add(name);
				}

				List<ExpandoColumn> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByT_N, finderArgs, list);
					}
				}
				else {
					ExpandoColumn expandoColumn = list.get(0);

					result = expandoColumn;

					cacheResult(expandoColumn);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByT_N, finderArgs);
				}

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
			return (ExpandoColumn)result;
		}
	}

	/**
	 * Removes the expando column where tableId = &#63; and name = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the expando column that was removed
	 */
	@Override
	public ExpandoColumn removeByT_N(long tableId, String name)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = findByT_N(tableId, name);

		return remove(expandoColumn);
	}

	/**
	 * Returns the number of expando columns where tableId = &#63; and name = &#63;.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the number of matching expando columns
	 */
	@Override
	public int countByT_N(long tableId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByT_N;

		Object[] finderArgs = new Object[] {tableId, name};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_T_N_TABLEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_T_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_T_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the number of matching expando columns
	 */
	@Override
	public int countByT_N(long tableId, String[] names) {
		if (names == null) {
			names = new String[0];
		}
		else if (names.length > 1) {
			for (int i = 0; i < names.length; i++) {
				names[i] = Objects.toString(names[i], "");
			}

			names = ArrayUtil.sortedUnique(names);
		}

		Object[] finderArgs = new Object[] {tableId, StringUtil.merge(names)};

		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathWithPaginationCountByT_N, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_EXPANDOCOLUMN_WHERE);

			query.append(_FINDER_COLUMN_T_N_TABLEID_2);

			if (names.length > 0) {
				query.append("(");

				for (int i = 0; i < names.length; i++) {
					String name = names[i];

					if (name.isEmpty()) {
						query.append(_FINDER_COLUMN_T_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_N_NAME_2);
					}

					if ((i + 1) < names.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				for (String name : names) {
					if ((name != null) && !name.isEmpty()) {
						qPos.add(name);
					}
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathWithPaginationCountByT_N, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathWithPaginationCountByT_N, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	 *
	 * @param tableId the table ID
	 * @param name the name
	 * @return the number of matching expando columns that the user has permission to view
	 */
	@Override
	public int filterCountByT_N(long tableId, String name) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByT_N(tableId, name);
		}

		name = Objects.toString(name, "");

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_EXPANDOCOLUMN_WHERE);

		query.append(_FINDER_COLUMN_T_N_TABLEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_T_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_T_N_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ExpandoColumn.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			if (bindName) {
				qPos.add(name);
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

	/**
	 * Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	 *
	 * @param tableId the table ID
	 * @param names the names
	 * @return the number of matching expando columns that the user has permission to view
	 */
	@Override
	public int filterCountByT_N(long tableId, String[] names) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByT_N(tableId, names);
		}

		if (names == null) {
			names = new String[0];
		}
		else if (names.length > 1) {
			for (int i = 0; i < names.length; i++) {
				names[i] = Objects.toString(names[i], "");
			}

			names = ArrayUtil.sortedUnique(names);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_EXPANDOCOLUMN_WHERE);

		query.append(_FINDER_COLUMN_T_N_TABLEID_2);

		if (names.length > 0) {
			query.append("(");

			for (int i = 0; i < names.length; i++) {
				String name = names[i];

				if (name.isEmpty()) {
					query.append(_FINDER_COLUMN_T_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_N_NAME_2);
				}

				if ((i + 1) < names.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ExpandoColumn.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			for (String name : names) {
				if ((name != null) && !name.isEmpty()) {
					qPos.add(name);
				}
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

	private static final String _FINDER_COLUMN_T_N_TABLEID_2 =
		"expandoColumn.tableId = ? AND ";

	private static final String _FINDER_COLUMN_T_N_NAME_2 =
		"expandoColumn.name = ?";

	private static final String _FINDER_COLUMN_T_N_NAME_3 =
		"(expandoColumn.name IS NULL OR expandoColumn.name = '')";

	public ExpandoColumnPersistenceImpl() {
		setModelClass(ExpandoColumn.class);

		setModelImplClass(ExpandoColumnImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the expando column in the entity cache if it is enabled.
	 *
	 * @param expandoColumn the expando column
	 */
	@Override
	public void cacheResult(ExpandoColumn expandoColumn) {
		EntityCacheUtil.putResult(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey(),
			expandoColumn);

		FinderCacheUtil.putResult(
			_finderPathFetchByT_N,
			new Object[] {expandoColumn.getTableId(), expandoColumn.getName()},
			expandoColumn);

		expandoColumn.resetOriginalValues();
	}

	/**
	 * Caches the expando columns in the entity cache if it is enabled.
	 *
	 * @param expandoColumns the expando columns
	 */
	@Override
	public void cacheResult(List<ExpandoColumn> expandoColumns) {
		for (ExpandoColumn expandoColumn : expandoColumns) {
			if (EntityCacheUtil.getResult(
					ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
					ExpandoColumnImpl.class, expandoColumn.getPrimaryKey()) ==
						null) {

				cacheResult(expandoColumn);
			}
			else {
				expandoColumn.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all expando columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ExpandoColumnImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the expando column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExpandoColumn expandoColumn) {
		EntityCacheUtil.removeResult(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ExpandoColumnModelImpl)expandoColumn, true);
	}

	@Override
	public void clearCache(List<ExpandoColumn> expandoColumns) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExpandoColumn expandoColumn : expandoColumns) {
			EntityCacheUtil.removeResult(
				ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoColumnImpl.class, expandoColumn.getPrimaryKey());

			clearUniqueFindersCache(
				(ExpandoColumnModelImpl)expandoColumn, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoColumnImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ExpandoColumnModelImpl expandoColumnModelImpl) {

		Object[] args = new Object[] {
			expandoColumnModelImpl.getTableId(),
			expandoColumnModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByT_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByT_N, args, expandoColumnModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ExpandoColumnModelImpl expandoColumnModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				expandoColumnModelImpl.getTableId(),
				expandoColumnModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_N, args);
		}

		if ((expandoColumnModelImpl.getColumnBitmask() &
			 _finderPathFetchByT_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				expandoColumnModelImpl.getOriginalTableId(),
				expandoColumnModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByT_N, args);
		}
	}

	/**
	 * Creates a new expando column with the primary key. Does not add the expando column to the database.
	 *
	 * @param columnId the primary key for the new expando column
	 * @return the new expando column
	 */
	@Override
	public ExpandoColumn create(long columnId) {
		ExpandoColumn expandoColumn = new ExpandoColumnImpl();

		expandoColumn.setNew(true);
		expandoColumn.setPrimaryKey(columnId);

		expandoColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return expandoColumn;
	}

	/**
	 * Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param columnId the primary key of the expando column
	 * @return the expando column that was removed
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn remove(long columnId) throws NoSuchColumnException {
		return remove((Serializable)columnId);
	}

	/**
	 * Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the expando column
	 * @return the expando column that was removed
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn remove(Serializable primaryKey)
		throws NoSuchColumnException {

		Session session = null;

		try {
			session = openSession();

			ExpandoColumn expandoColumn = (ExpandoColumn)session.get(
				ExpandoColumnImpl.class, primaryKey);

			if (expandoColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(expandoColumn);
		}
		catch (NoSuchColumnException nsee) {
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
	protected ExpandoColumn removeImpl(ExpandoColumn expandoColumn) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(expandoColumn)) {
				expandoColumn = (ExpandoColumn)session.get(
					ExpandoColumnImpl.class, expandoColumn.getPrimaryKeyObj());
			}

			if (expandoColumn != null) {
				session.delete(expandoColumn);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (expandoColumn != null) {
			clearCache(expandoColumn);
		}

		return expandoColumn;
	}

	@Override
	public ExpandoColumn updateImpl(ExpandoColumn expandoColumn) {
		boolean isNew = expandoColumn.isNew();

		if (!(expandoColumn instanceof ExpandoColumnModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(expandoColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					expandoColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in expandoColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExpandoColumn implementation " +
					expandoColumn.getClass());
		}

		ExpandoColumnModelImpl expandoColumnModelImpl =
			(ExpandoColumnModelImpl)expandoColumn;

		Session session = null;

		try {
			session = openSession();

			if (expandoColumn.isNew()) {
				session.save(expandoColumn);

				expandoColumn.setNew(false);
			}
			else {
				expandoColumn = (ExpandoColumn)session.merge(expandoColumn);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ExpandoColumnModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {expandoColumnModelImpl.getTableId()};

			FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByTableId, args);

			args = new Object[] {
				expandoColumnModelImpl.getTableId(),
				expandoColumnModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByT_N, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByT_N, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((expandoColumnModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTableId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					expandoColumnModelImpl.getOriginalTableId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);

				args = new Object[] {expandoColumnModelImpl.getTableId()};

				FinderCacheUtil.removeResult(_finderPathCountByTableId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByTableId, args);
			}

			if ((expandoColumnModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_N.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoColumnModelImpl.getOriginalTableId(),
					expandoColumnModelImpl.getOriginalName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_N, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_N, args);

				args = new Object[] {
					expandoColumnModelImpl.getTableId(),
					expandoColumnModelImpl.getName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByT_N, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByT_N, args);
			}
		}

		EntityCacheUtil.putResult(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnImpl.class, expandoColumn.getPrimaryKey(),
			expandoColumn, false);

		clearUniqueFindersCache(expandoColumnModelImpl, false);
		cacheUniqueFindersCache(expandoColumnModelImpl);

		expandoColumn.resetOriginalValues();

		return expandoColumn;
	}

	/**
	 * Returns the expando column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando column
	 * @return the expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchColumnException {

		ExpandoColumn expandoColumn = fetchByPrimaryKey(primaryKey);

		if (expandoColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return expandoColumn;
	}

	/**
	 * Returns the expando column with the primary key or throws a <code>NoSuchColumnException</code> if it could not be found.
	 *
	 * @param columnId the primary key of the expando column
	 * @return the expando column
	 * @throws NoSuchColumnException if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchColumnException {

		return findByPrimaryKey((Serializable)columnId);
	}

	/**
	 * Returns the expando column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param columnId the primary key of the expando column
	 * @return the expando column, or <code>null</code> if a expando column with the primary key could not be found
	 */
	@Override
	public ExpandoColumn fetchByPrimaryKey(long columnId) {
		return fetchByPrimaryKey((Serializable)columnId);
	}

	/**
	 * Returns all the expando columns.
	 *
	 * @return the expando columns
	 */
	@Override
	public List<ExpandoColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @return the range of expando columns
	 */
	@Override
	public List<ExpandoColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando columns
	 */
	@Override
	public List<ExpandoColumn> findAll(
		int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando columns
	 * @param end the upper bound of the range of expando columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando columns
	 */
	@Override
	public List<ExpandoColumn> findAll(
		int start, int end, OrderByComparator<ExpandoColumn> orderByComparator,
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

		List<ExpandoColumn> list = null;

		if (useFinderCache) {
			list = (List<ExpandoColumn>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_EXPANDOCOLUMN);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EXPANDOCOLUMN;

				sql = sql.concat(ExpandoColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<ExpandoColumn>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the expando columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExpandoColumn expandoColumn : findAll()) {
			remove(expandoColumn);
		}
	}

	/**
	 * Returns the number of expando columns.
	 *
	 * @return the number of expando columns
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EXPANDOCOLUMN);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "columnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXPANDOCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ExpandoColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the expando column persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByTableId = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTableId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTableId = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTableId", new String[] {Long.class.getName()},
			ExpandoColumnModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoColumnModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByTableId = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTableId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByT_N = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_N = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByT_N",
			new String[] {Long.class.getName(), String.class.getName()},
			ExpandoColumnModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoColumnModelImpl.NAME_COLUMN_BITMASK);

		_finderPathFetchByT_N = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			ExpandoColumnImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByT_N",
			new String[] {Long.class.getName(), String.class.getName()},
			ExpandoColumnModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoColumnModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByT_N = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationCountByT_N = new FinderPath(
			ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByT_N",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExpandoColumnImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPANDOCOLUMN =
		"SELECT expandoColumn FROM ExpandoColumn expandoColumn";

	private static final String _SQL_SELECT_EXPANDOCOLUMN_WHERE =
		"SELECT expandoColumn FROM ExpandoColumn expandoColumn WHERE ";

	private static final String _SQL_COUNT_EXPANDOCOLUMN =
		"SELECT COUNT(expandoColumn) FROM ExpandoColumn expandoColumn";

	private static final String _SQL_COUNT_EXPANDOCOLUMN_WHERE =
		"SELECT COUNT(expandoColumn) FROM ExpandoColumn expandoColumn WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"expandoColumn.columnId";

	private static final String _FILTER_SQL_SELECT_EXPANDOCOLUMN_WHERE =
		"SELECT DISTINCT {expandoColumn.*} FROM ExpandoColumn expandoColumn WHERE ";

	private static final String
		_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {ExpandoColumn.*} FROM (SELECT DISTINCT expandoColumn.columnId FROM ExpandoColumn expandoColumn WHERE ";

	private static final String
		_FILTER_SQL_SELECT_EXPANDOCOLUMN_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN ExpandoColumn ON TEMP_TABLE.columnId = ExpandoColumn.columnId";

	private static final String _FILTER_SQL_COUNT_EXPANDOCOLUMN_WHERE =
		"SELECT COUNT(DISTINCT expandoColumn.columnId) AS COUNT_VALUE FROM ExpandoColumn expandoColumn WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "expandoColumn";

	private static final String _FILTER_ENTITY_TABLE = "ExpandoColumn";

	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoColumn.";

	private static final String _ORDER_BY_ENTITY_TABLE = "ExpandoColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExpandoColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExpandoColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}