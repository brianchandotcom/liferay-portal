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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchUADPartialEntryException;
import com.liferay.portal.tools.service.builder.test.model.UADPartialEntry;
import com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.UADPartialEntryPersistence;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the uad partial entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UADPartialEntryPersistenceImpl
	extends BasePersistenceImpl<UADPartialEntry>
	implements UADPartialEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UADPartialEntryUtil</code> to access the uad partial entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UADPartialEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public UADPartialEntryPersistenceImpl() {
		setModelClass(UADPartialEntry.class);

		setModelImplClass(UADPartialEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the uad partial entry in the entity cache if it is enabled.
	 *
	 * @param uadPartialEntry the uad partial entry
	 */
	@Override
	public void cacheResult(UADPartialEntry uadPartialEntry) {
		entityCache.putResult(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryImpl.class, uadPartialEntry.getPrimaryKey(),
			uadPartialEntry);

		uadPartialEntry.resetOriginalValues();
	}

	/**
	 * Caches the uad partial entries in the entity cache if it is enabled.
	 *
	 * @param uadPartialEntries the uad partial entries
	 */
	@Override
	public void cacheResult(List<UADPartialEntry> uadPartialEntries) {
		for (UADPartialEntry uadPartialEntry : uadPartialEntries) {
			if (entityCache.getResult(
					UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
					UADPartialEntryImpl.class,
					uadPartialEntry.getPrimaryKey()) == null) {

				cacheResult(uadPartialEntry);
			}
			else {
				uadPartialEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all uad partial entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UADPartialEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the uad partial entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UADPartialEntry uadPartialEntry) {
		entityCache.removeResult(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryImpl.class, uadPartialEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UADPartialEntry> uadPartialEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UADPartialEntry uadPartialEntry : uadPartialEntries) {
			entityCache.removeResult(
				UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
				UADPartialEntryImpl.class, uadPartialEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
				UADPartialEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new uad partial entry with the primary key. Does not add the uad partial entry to the database.
	 *
	 * @param uadPartialEntryId the primary key for the new uad partial entry
	 * @return the new uad partial entry
	 */
	@Override
	public UADPartialEntry create(long uadPartialEntryId) {
		UADPartialEntry uadPartialEntry = new UADPartialEntryImpl();

		uadPartialEntry.setNew(true);
		uadPartialEntry.setPrimaryKey(uadPartialEntryId);

		return uadPartialEntry;
	}

	/**
	 * Removes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry that was removed
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public UADPartialEntry remove(long uadPartialEntryId)
		throws NoSuchUADPartialEntryException {

		return remove((Serializable)uadPartialEntryId);
	}

	/**
	 * Removes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the uad partial entry
	 * @return the uad partial entry that was removed
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public UADPartialEntry remove(Serializable primaryKey)
		throws NoSuchUADPartialEntryException {

		Session session = null;

		try {
			session = openSession();

			UADPartialEntry uadPartialEntry = (UADPartialEntry)session.get(
				UADPartialEntryImpl.class, primaryKey);

			if (uadPartialEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUADPartialEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(uadPartialEntry);
		}
		catch (NoSuchUADPartialEntryException nsee) {
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
	protected UADPartialEntry removeImpl(UADPartialEntry uadPartialEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(uadPartialEntry)) {
				uadPartialEntry = (UADPartialEntry)session.get(
					UADPartialEntryImpl.class,
					uadPartialEntry.getPrimaryKeyObj());
			}

			if (uadPartialEntry != null) {
				session.delete(uadPartialEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (uadPartialEntry != null) {
			clearCache(uadPartialEntry);
		}

		return uadPartialEntry;
	}

	@Override
	public UADPartialEntry updateImpl(UADPartialEntry uadPartialEntry) {
		boolean isNew = uadPartialEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (uadPartialEntry.isNew()) {
				session.save(uadPartialEntry);

				uadPartialEntry.setNew(false);
			}
			else {
				uadPartialEntry = (UADPartialEntry)session.merge(
					uadPartialEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryImpl.class, uadPartialEntry.getPrimaryKey(),
			uadPartialEntry, false);

		uadPartialEntry.resetOriginalValues();

		return uadPartialEntry;
	}

	/**
	 * Returns the uad partial entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the uad partial entry
	 * @return the uad partial entry
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public UADPartialEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUADPartialEntryException {

		UADPartialEntry uadPartialEntry = fetchByPrimaryKey(primaryKey);

		if (uadPartialEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUADPartialEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return uadPartialEntry;
	}

	/**
	 * Returns the uad partial entry with the primary key or throws a <code>NoSuchUADPartialEntryException</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public UADPartialEntry findByPrimaryKey(long uadPartialEntryId)
		throws NoSuchUADPartialEntryException {

		return findByPrimaryKey((Serializable)uadPartialEntryId);
	}

	/**
	 * Returns the uad partial entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry, or <code>null</code> if a uad partial entry with the primary key could not be found
	 */
	@Override
	public UADPartialEntry fetchByPrimaryKey(long uadPartialEntryId) {
		return fetchByPrimaryKey((Serializable)uadPartialEntryId);
	}

	/**
	 * Returns all the uad partial entries.
	 *
	 * @return the uad partial entries
	 */
	@Override
	public List<UADPartialEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @return the range of uad partial entries
	 */
	@Override
	public List<UADPartialEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of uad partial entries
	 */
	@Override
	public List<UADPartialEntry> findAll(
		int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of uad partial entries
	 */
	@Override
	public List<UADPartialEntry> findAll(
		int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator,
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

		List<UADPartialEntry> list = null;

		if (useFinderCache) {
			list = (List<UADPartialEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_UADPARTIALENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_UADPARTIALENTRY;

				sql = sql.concat(UADPartialEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<UADPartialEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the uad partial entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UADPartialEntry uadPartialEntry : findAll()) {
			remove(uadPartialEntry);
		}
	}

	/**
	 * Returns the number of uad partial entries.
	 *
	 * @return the number of uad partial entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_UADPARTIALENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "uadPartialEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_UADPARTIALENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UADPartialEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the uad partial entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryModelImpl.FINDER_CACHE_ENABLED,
			UADPartialEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryModelImpl.FINDER_CACHE_ENABLED,
			UADPartialEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			UADPartialEntryModelImpl.ENTITY_CACHE_ENABLED,
			UADPartialEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	public void destroy() {
		entityCache.removeCache(UADPartialEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_UADPARTIALENTRY =
		"SELECT uadPartialEntry FROM UADPartialEntry uadPartialEntry";

	private static final String _SQL_COUNT_UADPARTIALENTRY =
		"SELECT COUNT(uadPartialEntry) FROM UADPartialEntry uadPartialEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "uadPartialEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UADPartialEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		UADPartialEntryPersistenceImpl.class);

}