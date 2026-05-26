/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class UniquePersistenceFinder
	<T extends BaseModel<T>, E extends NoSuchModelException>
		extends BasePersistenceFinder<T, E> {

	@SafeVarargs
	public UniquePersistenceFinder(
		BasePersistenceImpl<T, E> basePersistenceImpl, FinderPath fetchPath,
		String sqlSelectWhere, String where, FinderColumn<T>... finderColumns) {

		super(basePersistenceImpl, sqlSelectWhere, where, finderColumns);

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

		try (SafeCloseable safeCloseable =
				setCTCollectionIdWithSafeCloseable()) {

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
				String sql = buildSQLWhere(sqlSelectWhere, values);

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
	}

	public T find(FinderCache finderCache, Object[] values) throws E {
		T entity = fetch(finderCache, values, true);

		if (entity != null) {
			return entity;
		}

		throw basePersistenceImpl.newNoSuchModelException(
			buildNoSuchKeyMessage(values));
	}

	private final FinderPath _fetchPath;

}