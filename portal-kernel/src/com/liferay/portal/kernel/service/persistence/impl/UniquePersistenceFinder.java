/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * Reusable implementation of unique finder operations (finders that return a
 * single entity). Encapsulates the core fetch-with-cache logic that is
 * otherwise generated identically for every unique finder in every
 * PersistenceImpl class.
 *
 * <p>
 * The finder cache stores either the found entity or an empty {@code List} as
 * a not-found sentinel. This matches the caching contract of the originally
 * generated code.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UniquePersistenceFinder<T extends BaseModel<T>>
	extends BasePersistenceFinder<T> {

	@SafeVarargs
	public UniquePersistenceFinder(
		BasePersistenceImpl<T> basePersistenceImpl, FinderPath fetchPath,
		String sqlSelectWhere, FinderColumn<T>... finderColumns) {

		super(basePersistenceImpl, sqlSelectWhere, finderColumns);

		_fetchPath = fetchPath;
	}

	public int count(FinderCache finderCache, Object[] values) {
		if (fetch(finderCache, values, true) == null) {
			return 0;
		}

		return 1;
	}

	@SuppressWarnings("unchecked")
	public T fetch(
		FinderCache finderCache, Object[] values, boolean useFinderCache) {

		normalizeValues(values);

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = buildFinderArgs(values);
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_fetchPath, finderArgs, basePersistenceImpl);
		}

		if (result instanceof BaseModel) {
			T entity = (T)result;

			if (!matchesAll(entity, values)) {
				result = null;
			}
		}

		if (result == null) {
			String sql = buildSqlWhere(sqlSelectWhere, values);

			Session session = null;

			try {
				session = basePersistenceImpl.openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				bindQueryParams(queryPos, values);

				List<T> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(_fetchPath, finderArgs, list);
					}
				}
				else {
					T entity = list.get(0);

					result = entity;

					basePersistenceImpl.cacheResult(entity);
				}
			}
			catch (Exception exception) {
				throw basePersistenceImpl.processException(exception);
			}
			finally {
				basePersistenceImpl.closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}

		return (T)result;
	}

	private final FinderPath _fetchPath;

}