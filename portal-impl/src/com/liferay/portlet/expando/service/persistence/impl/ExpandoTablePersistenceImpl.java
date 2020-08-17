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

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.service.persistence.ExpandoTablePersistence;
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
import com.liferay.portlet.expando.model.impl.ExpandoTableImpl;
import com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl;

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
 * The persistence implementation for the expando table service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoTablePersistenceImpl
	extends BasePersistenceImpl<ExpandoTable>
	implements ExpandoTablePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExpandoTableUtil</code> to access the expando table persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExpandoTableImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching expando tables
	 */
	@Override
	public List<ExpandoTable> findByC_C(long companyId, long classNameId) {
		return findByC_C(
			companyId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @return the range of matching expando tables
	 */
	@Override
	public List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end) {

		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando tables
	 */
	@Override
	public List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<ExpandoTable> orderByComparator) {

		return findByC_C(
			companyId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching expando tables
	 */
	@Override
	public List<ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<ExpandoTable> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {companyId, classNameId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				companyId, classNameId, start, end, orderByComparator
			};
		}

		List<ExpandoTable> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoTable>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExpandoTable expandoTable : list) {
					if ((companyId != expandoTable.getCompanyId()) ||
						(classNameId != expandoTable.getClassNameId())) {

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

			sb.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ExpandoTableModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				list = (List<ExpandoTable>)QueryUtil.list(
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
	 * Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable findByC_C_First(
			long companyId, long classNameId,
			OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException {

		ExpandoTable expandoTable = fetchByC_C_First(
			companyId, classNameId, orderByComparator);

		if (expandoTable != null) {
			return expandoTable;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchTableException(sb.toString());
	}

	/**
	 * Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable fetchByC_C_First(
		long companyId, long classNameId,
		OrderByComparator<ExpandoTable> orderByComparator) {

		List<ExpandoTable> list = findByC_C(
			companyId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable findByC_C_Last(
			long companyId, long classNameId,
			OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException {

		ExpandoTable expandoTable = fetchByC_C_Last(
			companyId, classNameId, orderByComparator);

		if (expandoTable != null) {
			return expandoTable;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchTableException(sb.toString());
	}

	/**
	 * Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable fetchByC_C_Last(
		long companyId, long classNameId,
		OrderByComparator<ExpandoTable> orderByComparator) {

		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return null;
		}

		List<ExpandoTable> list = findByC_C(
			companyId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param tableId the primary key of the current expando table
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando table
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable[] findByC_C_PrevAndNext(
			long tableId, long companyId, long classNameId,
			OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException {

		ExpandoTable expandoTable = findByPrimaryKey(tableId);

		Session session = null;

		try {
			session = openSession();

			ExpandoTable[] array = new ExpandoTableImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, expandoTable, companyId, classNameId,
				orderByComparator, true);

			array[1] = expandoTable;

			array[2] = getByC_C_PrevAndNext(
				session, expandoTable, companyId, classNameId,
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

	protected ExpandoTable getByC_C_PrevAndNext(
		Session session, ExpandoTable expandoTable, long companyId,
		long classNameId, OrderByComparator<ExpandoTable> orderByComparator,
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

		sb.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

		sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			sb.append(ExpandoTableModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(expandoTable)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ExpandoTable> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long companyId, long classNameId) {
		for (ExpandoTable expandoTable :
				findByC_C(
					companyId, classNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(expandoTable);
		}
	}

	/**
	 * Returns the number of expando tables where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching expando tables
	 */
	@Override
	public int countByC_C(long companyId, long classNameId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {companyId, classNameId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_EXPANDOTABLE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 =
		"expandoTable.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"expandoTable.classNameId = ?";

	private FinderPath _finderPathFetchByC_C_N;
	private FinderPath _finderPathCountByC_C_N;

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or throws a <code>NoSuchTableException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the matching expando table
	 * @throws NoSuchTableException if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable findByC_C_N(
			long companyId, long classNameId, String name)
		throws NoSuchTableException {

		ExpandoTable expandoTable = fetchByC_C_N(companyId, classNameId, name);

		if (expandoTable == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTableException(sb.toString());
		}

		return expandoTable;
	}

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, String name) {

		return fetchByC_C_N(companyId, classNameId, name, true);
	}

	/**
	 * Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	 */
	@Override
	public ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, classNameId, name};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C_N, finderArgs, this);
		}

		if (result instanceof ExpandoTable) {
			ExpandoTable expandoTable = (ExpandoTable)result;

			if ((companyId != expandoTable.getCompanyId()) ||
				(classNameId != expandoTable.getClassNameId()) ||
				!Objects.equals(name, expandoTable.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_EXPANDOTABLE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_N_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_N_CLASSNAMEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				if (bindName) {
					queryPos.add(name);
				}

				List<ExpandoTable> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C_N, finderArgs, list);
					}
				}
				else {
					ExpandoTable expandoTable = list.get(0);

					result = expandoTable;

					cacheResult(expandoTable);
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
			return (ExpandoTable)result;
		}
	}

	/**
	 * Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the expando table that was removed
	 */
	@Override
	public ExpandoTable removeByC_C_N(
			long companyId, long classNameId, String name)
		throws NoSuchTableException {

		ExpandoTable expandoTable = findByC_C_N(companyId, classNameId, name);

		return remove(expandoTable);
	}

	/**
	 * Returns the number of expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param name the name
	 * @return the number of matching expando tables
	 */
	@Override
	public int countByC_C_N(long companyId, long classNameId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_N;

			finderArgs = new Object[] {companyId, classNameId, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_EXPANDOTABLE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_N_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_N_CLASSNAMEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_C_C_N_COMPANYID_2 =
		"expandoTable.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_N_CLASSNAMEID_2 =
		"expandoTable.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_N_NAME_2 =
		"expandoTable.name = ?";

	private static final String _FINDER_COLUMN_C_C_N_NAME_3 =
		"(expandoTable.name IS NULL OR expandoTable.name = '')";

	public ExpandoTablePersistenceImpl() {
		setModelClass(ExpandoTable.class);

		setModelImplClass(ExpandoTableImpl.class);
		setModelPKClass(long.class);

		setTable(ExpandoTableTable.INSTANCE);
	}

	/**
	 * Caches the expando table in the entity cache if it is enabled.
	 *
	 * @param expandoTable the expando table
	 */
	@Override
	public void cacheResult(ExpandoTable expandoTable) {
		if (expandoTable.getCtCollectionId() != 0) {
			expandoTable.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			ExpandoTableImpl.class, expandoTable.getPrimaryKey(), expandoTable);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C_N,
			new Object[] {
				expandoTable.getCompanyId(), expandoTable.getClassNameId(),
				expandoTable.getName()
			},
			expandoTable);

		expandoTable.resetOriginalValues();
	}

	/**
	 * Caches the expando tables in the entity cache if it is enabled.
	 *
	 * @param expandoTables the expando tables
	 */
	@Override
	public void cacheResult(List<ExpandoTable> expandoTables) {
		for (ExpandoTable expandoTable : expandoTables) {
			if (expandoTable.getCtCollectionId() != 0) {
				expandoTable.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					ExpandoTableImpl.class, expandoTable.getPrimaryKey()) ==
						null) {

				cacheResult(expandoTable);
			}
			else {
				expandoTable.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all expando tables.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ExpandoTableImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the expando table.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExpandoTable expandoTable) {
		EntityCacheUtil.removeResult(
			ExpandoTableImpl.class, expandoTable.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ExpandoTableModelImpl)expandoTable, true);
	}

	@Override
	public void clearCache(List<ExpandoTable> expandoTables) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExpandoTable expandoTable : expandoTables) {
			EntityCacheUtil.removeResult(
				ExpandoTableImpl.class, expandoTable.getPrimaryKey());

			clearUniqueFindersCache((ExpandoTableModelImpl)expandoTable, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ExpandoTableImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ExpandoTableModelImpl expandoTableModelImpl) {

		Object[] args = new Object[] {
			expandoTableModelImpl.getCompanyId(),
			expandoTableModelImpl.getClassNameId(),
			expandoTableModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_C_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C_N, args, expandoTableModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ExpandoTableModelImpl expandoTableModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				expandoTableModelImpl.getCompanyId(),
				expandoTableModelImpl.getClassNameId(),
				expandoTableModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C_N, args);
		}

		if ((expandoTableModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				expandoTableModelImpl.getOriginalCompanyId(),
				expandoTableModelImpl.getOriginalClassNameId(),
				expandoTableModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C_N, args);
		}
	}

	/**
	 * Creates a new expando table with the primary key. Does not add the expando table to the database.
	 *
	 * @param tableId the primary key for the new expando table
	 * @return the new expando table
	 */
	@Override
	public ExpandoTable create(long tableId) {
		ExpandoTable expandoTable = new ExpandoTableImpl();

		expandoTable.setNew(true);
		expandoTable.setPrimaryKey(tableId);

		expandoTable.setCompanyId(CompanyThreadLocal.getCompanyId());

		return expandoTable;
	}

	/**
	 * Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table that was removed
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable remove(long tableId) throws NoSuchTableException {
		return remove((Serializable)tableId);
	}

	/**
	 * Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the expando table
	 * @return the expando table that was removed
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable remove(Serializable primaryKey)
		throws NoSuchTableException {

		Session session = null;

		try {
			session = openSession();

			ExpandoTable expandoTable = (ExpandoTable)session.get(
				ExpandoTableImpl.class, primaryKey);

			if (expandoTable == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTableException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(expandoTable);
		}
		catch (NoSuchTableException noSuchEntityException) {
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
	protected ExpandoTable removeImpl(ExpandoTable expandoTable) {
		if (!CTPersistenceHelperUtil.isRemove(expandoTable)) {
			return expandoTable;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(expandoTable)) {
				expandoTable = (ExpandoTable)session.get(
					ExpandoTableImpl.class, expandoTable.getPrimaryKeyObj());
			}

			if (expandoTable != null) {
				session.delete(expandoTable);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoTable != null) {
			clearCache(expandoTable);
		}

		return expandoTable;
	}

	@Override
	public ExpandoTable updateImpl(ExpandoTable expandoTable) {
		boolean isNew = expandoTable.isNew();

		if (!(expandoTable instanceof ExpandoTableModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(expandoTable.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					expandoTable);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in expandoTable proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExpandoTable implementation " +
					expandoTable.getClass());
		}

		ExpandoTableModelImpl expandoTableModelImpl =
			(ExpandoTableModelImpl)expandoTable;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(expandoTable)) {
				if (!isNew) {
					session.evict(
						ExpandoTableImpl.class,
						expandoTable.getPrimaryKeyObj());
				}

				session.save(expandoTable);

				expandoTable.setNew(false);
			}
			else {
				expandoTable = (ExpandoTable)session.merge(expandoTable);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (expandoTable.getCtCollectionId() != 0) {
			expandoTable.resetOriginalValues();

			return expandoTable;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				expandoTableModelImpl.getCompanyId(),
				expandoTableModelImpl.getClassNameId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((expandoTableModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					expandoTableModelImpl.getOriginalCompanyId(),
					expandoTableModelImpl.getOriginalClassNameId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					expandoTableModelImpl.getCompanyId(),
					expandoTableModelImpl.getClassNameId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}
		}

		EntityCacheUtil.putResult(
			ExpandoTableImpl.class, expandoTable.getPrimaryKey(), expandoTable,
			false);

		clearUniqueFindersCache(expandoTableModelImpl, false);
		cacheUniqueFindersCache(expandoTableModelImpl);

		expandoTable.resetOriginalValues();

		return expandoTable;
	}

	/**
	 * Returns the expando table with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando table
	 * @return the expando table
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTableException {

		ExpandoTable expandoTable = fetchByPrimaryKey(primaryKey);

		if (expandoTable == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTableException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return expandoTable;
	}

	/**
	 * Returns the expando table with the primary key or throws a <code>NoSuchTableException</code> if it could not be found.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table
	 * @throws NoSuchTableException if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchTableException {

		return findByPrimaryKey((Serializable)tableId);
	}

	/**
	 * Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando table
	 * @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(ExpandoTable.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		ExpandoTable expandoTable = null;

		Session session = null;

		try {
			session = openSession();

			expandoTable = (ExpandoTable)session.get(
				ExpandoTableImpl.class, primaryKey);

			if (expandoTable != null) {
				cacheResult(expandoTable);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return expandoTable;
	}

	/**
	 * Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	 */
	@Override
	public ExpandoTable fetchByPrimaryKey(long tableId) {
		return fetchByPrimaryKey((Serializable)tableId);
	}

	@Override
	public Map<Serializable, ExpandoTable> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(ExpandoTable.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ExpandoTable> map =
			new HashMap<Serializable, ExpandoTable>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ExpandoTable expandoTable = fetchByPrimaryKey(primaryKey);

			if (expandoTable != null) {
				map.put(primaryKey, expandoTable);
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

			for (ExpandoTable expandoTable : (List<ExpandoTable>)query.list()) {
				map.put(expandoTable.getPrimaryKeyObj(), expandoTable);

				cacheResult(expandoTable);
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
	 * Returns all the expando tables.
	 *
	 * @return the expando tables
	 */
	@Override
	public List<ExpandoTable> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @return the range of expando tables
	 */
	@Override
	public List<ExpandoTable> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando tables
	 */
	@Override
	public List<ExpandoTable> findAll(
		int start, int end, OrderByComparator<ExpandoTable> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of expando tables
	 */
	@Override
	public List<ExpandoTable> findAll(
		int start, int end, OrderByComparator<ExpandoTable> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

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

		List<ExpandoTable> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ExpandoTable>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_EXPANDOTABLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_EXPANDOTABLE;

				sql = sql.concat(ExpandoTableModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ExpandoTable>)QueryUtil.list(
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
	 * Removes all the expando tables from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExpandoTable expandoTable : findAll()) {
			remove(expandoTable);
		}
	}

	/**
	 * Returns the number of expando tables.
	 *
	 * @return the number of expando tables
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ExpandoTable.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_EXPANDOTABLE);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "tableId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXPANDOTABLE;
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
		return ExpandoTableModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ExpandoTable";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("name");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("tableId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"companyId", "classNameId", "name"});
	}

	/**
	 * Initializes the expando table persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ExpandoTableImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ExpandoTableImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			ExpandoTableImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			ExpandoTableImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExpandoTableModelImpl.COMPANYID_COLUMN_BITMASK |
			ExpandoTableModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_C_N = new FinderPath(
			ExpandoTableImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ExpandoTableModelImpl.COMPANYID_COLUMN_BITMASK |
			ExpandoTableModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExpandoTableModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_C_N = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExpandoTableImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPANDOTABLE =
		"SELECT expandoTable FROM ExpandoTable expandoTable";

	private static final String _SQL_SELECT_EXPANDOTABLE_WHERE =
		"SELECT expandoTable FROM ExpandoTable expandoTable WHERE ";

	private static final String _SQL_COUNT_EXPANDOTABLE =
		"SELECT COUNT(expandoTable) FROM ExpandoTable expandoTable";

	private static final String _SQL_COUNT_EXPANDOTABLE_WHERE =
		"SELECT COUNT(expandoTable) FROM ExpandoTable expandoTable WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoTable.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExpandoTable exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExpandoTable exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoTablePersistenceImpl.class);

}