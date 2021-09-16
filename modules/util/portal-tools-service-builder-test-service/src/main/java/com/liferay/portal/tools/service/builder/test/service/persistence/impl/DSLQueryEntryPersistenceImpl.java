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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDSLQueryEntryException;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.DSLQueryEntryPersistence;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the dsl query entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DSLQueryEntryPersistenceImpl
	extends BasePersistenceImpl<DSLQueryEntry>
	implements DSLQueryEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DSLQueryEntryUtil</code> to access the dsl query entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DSLQueryEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public DSLQueryEntryPersistenceImpl() {
		setModelClass(DSLQueryEntry.class);

		setModelImplClass(DSLQueryEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DSLQueryEntryTable.INSTANCE);
	}

	/**
	 * Caches the dsl query entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntry the dsl query entry
	 */
	@Override
	public void cacheResult(DSLQueryEntry dslQueryEntry) {
		entityCache.putResult(
			DSLQueryEntryImpl.class, dslQueryEntry.getPrimaryKey(),
			dslQueryEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dsl query entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntries the dsl query entries
	 */
	@Override
	public void cacheResult(List<DSLQueryEntry> dslQueryEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dslQueryEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DSLQueryEntry dslQueryEntry : dslQueryEntries) {
			if (entityCache.getResult(
					DSLQueryEntryImpl.class, dslQueryEntry.getPrimaryKey()) ==
						null) {

				cacheResult(dslQueryEntry);
			}
		}
	}

	/**
	 * Clears the cache for all dsl query entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DSLQueryEntryImpl.class);

		finderCache.clearCache(DSLQueryEntryImpl.class);
	}

	/**
	 * Clears the cache for the dsl query entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DSLQueryEntry dslQueryEntry) {
		entityCache.removeResult(DSLQueryEntryImpl.class, dslQueryEntry);
	}

	@Override
	public void clearCache(List<DSLQueryEntry> dslQueryEntries) {
		for (DSLQueryEntry dslQueryEntry : dslQueryEntries) {
			entityCache.removeResult(DSLQueryEntryImpl.class, dslQueryEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DSLQueryEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DSLQueryEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new dsl query entry with the primary key. Does not add the dsl query entry to the database.
	 *
	 * @param dslQueryEntryId the primary key for the new dsl query entry
	 * @return the new dsl query entry
	 */
	@Override
	public DSLQueryEntry create(long dslQueryEntryId) {
		DSLQueryEntry dslQueryEntry = new DSLQueryEntryImpl();

		dslQueryEntry.setNew(true);
		dslQueryEntry.setPrimaryKey(dslQueryEntryId);

		return dslQueryEntry;
	}

	/**
	 * Removes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry remove(long dslQueryEntryId)
		throws NoSuchDSLQueryEntryException {

		return remove((Serializable)dslQueryEntryId);
	}

	/**
	 * Removes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry remove(Serializable primaryKey)
		throws NoSuchDSLQueryEntryException {

		Session session = null;

		try {
			session = openSession();

			DSLQueryEntry dslQueryEntry = (DSLQueryEntry)session.get(
				DSLQueryEntryImpl.class, primaryKey);

			if (dslQueryEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDSLQueryEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dslQueryEntry);
		}
		catch (NoSuchDSLQueryEntryException noSuchEntityException) {
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
	protected DSLQueryEntry removeImpl(DSLQueryEntry dslQueryEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dslQueryEntry)) {
				dslQueryEntry = (DSLQueryEntry)session.get(
					DSLQueryEntryImpl.class, dslQueryEntry.getPrimaryKeyObj());
			}

			if (dslQueryEntry != null) {
				session.delete(dslQueryEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dslQueryEntry != null) {
			clearCache(dslQueryEntry);
		}

		return dslQueryEntry;
	}

	@Override
	public DSLQueryEntry updateImpl(DSLQueryEntry dslQueryEntry) {
		boolean isNew = dslQueryEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(dslQueryEntry);
			}
			else {
				dslQueryEntry = (DSLQueryEntry)session.merge(dslQueryEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DSLQueryEntryImpl.class, dslQueryEntry, false, true);

		if (isNew) {
			dslQueryEntry.setNew(false);
		}

		dslQueryEntry.resetOriginalValues();

		return dslQueryEntry;
	}

	/**
	 * Returns the dsl query entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDSLQueryEntryException {

		DSLQueryEntry dslQueryEntry = fetchByPrimaryKey(primaryKey);

		if (dslQueryEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDSLQueryEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dslQueryEntry;
	}

	/**
	 * Returns the dsl query entry with the primary key or throws a <code>NoSuchDSLQueryEntryException</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry findByPrimaryKey(long dslQueryEntryId)
		throws NoSuchDSLQueryEntryException {

		return findByPrimaryKey((Serializable)dslQueryEntryId);
	}

	/**
	 * Returns the dsl query entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry, or <code>null</code> if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry fetchByPrimaryKey(long dslQueryEntryId) {
		return fetchByPrimaryKey((Serializable)dslQueryEntryId);
	}

	/**
	 * Returns all the dsl query entries.
	 *
	 * @return the dsl query entries
	 */
	@Override
	public List<DSLQueryEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @return the range of dsl query entries
	 */
	@Override
	public List<DSLQueryEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dsl query entries
	 */
	@Override
	public List<DSLQueryEntry> findAll(
		int start, int end,
		OrderByComparator<DSLQueryEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dsl query entries
	 */
	@Override
	public List<DSLQueryEntry> findAll(
		int start, int end, OrderByComparator<DSLQueryEntry> orderByComparator,
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

		List<DSLQueryEntry> list = null;

		if (useFinderCache) {
			list = (List<DSLQueryEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DSLQUERYENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DSLQUERYENTRY;

				sql = sql.concat(DSLQueryEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DSLQueryEntry>)QueryUtil.list(
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
	 * Removes all the dsl query entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DSLQueryEntry dslQueryEntry : findAll()) {
			remove(dslQueryEntry);
		}
	}

	/**
	 * Returns the number of dsl query entries.
	 *
	 * @return the number of dsl query entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DSLQUERYENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "dslQueryEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DSLQUERYENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DSLQueryEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dsl query entry persistence.
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
	}

	public void destroy() {
		entityCache.removeCache(DSLQueryEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DSLQUERYENTRY =
		"SELECT dslQueryEntry FROM DSLQueryEntry dslQueryEntry";

	private static final String _SQL_COUNT_DSLQUERYENTRY =
		"SELECT COUNT(dslQueryEntry) FROM DSLQueryEntry dslQueryEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dslQueryEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DSLQueryEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		DSLQueryEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}