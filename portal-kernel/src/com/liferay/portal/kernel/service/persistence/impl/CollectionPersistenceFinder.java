/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class CollectionPersistenceFinder<T extends BaseModel<T>>
	extends BasePersistenceFinder<T> {

	@SafeVarargs
	public CollectionPersistenceFinder(
		BasePersistenceImpl<T> basePersistenceImpl,
		FinderPath paginatedFindPath, FinderPath unpaginatedFindPath,
		FinderPath countFinderPath, String sqlSelectWhere, String sqlCountWhere,
		String defaultOrderByJpql, String orderByEntityAlias,
		FinderColumn<T>... finderColumns) {

		super(basePersistenceImpl, sqlSelectWhere, finderColumns);

		_paginatedFindPath = paginatedFindPath;
		_unpaginatedFindPath = unpaginatedFindPath;
		_countFinderPath = countFinderPath;
		_sqlCountWhere = sqlCountWhere;
		_defaultOrderByJpql = defaultOrderByJpql;
		_orderByEntityAlias = orderByEntityAlias;
	}

	public int count(FinderCache finderCache, Object[] values) {
		normalizeValues(values);

		Object[] finderArgs = buildFinderArgs(values);

		Long count = (Long)finderCache.getResult(
			_countFinderPath, finderArgs, basePersistenceImpl);

		if (count == null) {
			String sql = buildSQLWhere(_sqlCountWhere, values);

			Session session = null;

			try {
				session = basePersistenceImpl.openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				bindQueryParams(queryPos, values);

				count = (Long)query.uniqueResult();

				finderCache.putResult(_countFinderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw basePersistenceImpl.processException(exception);
			}
			finally {
				basePersistenceImpl.closeSession(session);
			}
		}

		return count.intValue();
	}

	public T fetchFirst(
		FinderCache finderCache, Object[] values,
		OrderByComparator<T> orderByComparator) {

		List<T> list = find(finderCache, values, 0, 1, orderByComparator, true);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(
		FinderCache finderCache, Object[] values, int start, int end,
		OrderByComparator<T> orderByComparator, boolean useFinderCache) {

		normalizeValues(values);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if (_unpaginatedFindPath != null) {
			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _unpaginatedFindPath;
					finderArgs = buildFinderArgs(values);
				}
			}
			else if (useFinderCache) {
				finderPath = _paginatedFindPath;
				finderArgs = _buildPaginatedFinderArgs(
					values, start, end, orderByComparator);
			}
		}
		else if (useFinderCache) {
			finderPath = _paginatedFindPath;
			finderArgs = _buildPaginatedFinderArgs(
				values, start, end, orderByComparator);
		}

		List<T> list = null;

		if (useFinderCache) {
			list = (List<T>)finderCache.getResult(
				finderPath, finderArgs, basePersistenceImpl);

			if ((list != null) && !list.isEmpty()) {
				for (T entity : list) {
					if (!matchesAll(entity, values)) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			String sql = _buildFindSql(values, orderByComparator);

			Session session = null;

			try {
				session = basePersistenceImpl.openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				bindQueryParams(queryPos, values);

				list = (List<T>)QueryUtil.list(
					query, basePersistenceImpl.getDialect(), start, end);

				basePersistenceImpl.cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw basePersistenceImpl.processException(exception);
			}
			finally {
				basePersistenceImpl.closeSession(session);
			}
		}

		return list;
	}

	private String _buildFindSql(
		Object[] values, OrderByComparator<T> orderByComparator) {

		StringBundler sb = null;

		if (orderByComparator == null) {
			sb = new StringBundler(finderColumns.length + 2);
		}
		else {
			sb = new StringBundler(
				finderColumns.length + 2 +
					(orderByComparator.getOrderByFields().length * 2));
		}

		sb.append(sqlSelectWhere);

		for (int i = 0; i < finderColumns.length; i++) {
			sb.append(finderColumns[i].getSqlFragment(values[i]));
		}

		if (orderByComparator == null) {
			sb.append(_defaultOrderByJpql);
		}
		else {
			basePersistenceImpl.appendOrderByComparator(
				sb, _orderByEntityAlias, orderByComparator);
		}

		return sb.toString();
	}

	private Object[] _buildPaginatedFinderArgs(
		Object[] values, int start, int end,
		OrderByComparator<T> orderByComparator) {

		Object[] finderArgs = new Object[finderColumns.length + 3];

		for (int i = 0; i < finderColumns.length; i++) {
			finderArgs[i] = finderColumns[i].toFinderArg(values[i]);
		}

		finderArgs[finderColumns.length] = start;
		finderArgs[finderColumns.length + 1] = end;
		finderArgs[finderColumns.length + 2] = orderByComparator;

		return finderArgs;
	}

	private final FinderPath _countFinderPath;
	private final String _defaultOrderByJpql;
	private final String _orderByEntityAlias;
	private final FinderPath _paginatedFindPath;
	private final String _sqlCountWhere;
	private final FinderPath _unpaginatedFindPath;

}