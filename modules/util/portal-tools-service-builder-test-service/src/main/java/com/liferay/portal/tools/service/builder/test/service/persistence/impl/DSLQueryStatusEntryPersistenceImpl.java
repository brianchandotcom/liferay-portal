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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDSLQueryStatusEntryException;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryStatusEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryStatusEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.DSLQueryStatusEntryPersistence;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the dsl query status entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DSLQueryStatusEntryPersistenceImpl
	extends BasePersistenceImpl<DSLQueryStatusEntry>
	implements DSLQueryStatusEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DSLQueryStatusEntryUtil</code> to access the dsl query status entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DSLQueryStatusEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public DSLQueryStatusEntryPersistenceImpl() {
		setModelClass(DSLQueryStatusEntry.class);

		setModelImplClass(DSLQueryStatusEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DSLQueryStatusEntryTable.INSTANCE);
	}

	/**
	 * Caches the dsl query status entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 */
	@Override
	public void cacheResult(DSLQueryStatusEntry dslQueryStatusEntry) {
		entityCache.putResult(
			DSLQueryStatusEntryImpl.class, dslQueryStatusEntry.getPrimaryKey(),
			dslQueryStatusEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dsl query status entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntries the dsl query status entries
	 */
	@Override
	public void cacheResult(List<DSLQueryStatusEntry> dslQueryStatusEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dslQueryStatusEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DSLQueryStatusEntry dslQueryStatusEntry : dslQueryStatusEntries) {
			if (entityCache.getResult(
					DSLQueryStatusEntryImpl.class,
					dslQueryStatusEntry.getPrimaryKey()) == null) {

				cacheResult(dslQueryStatusEntry);
			}
		}
	}

	/**
	 * Clears the cache for all dsl query status entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DSLQueryStatusEntryImpl.class);

		finderCache.clearCache(DSLQueryStatusEntryImpl.class);
	}

	/**
	 * Clears the cache for the dsl query status entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DSLQueryStatusEntry dslQueryStatusEntry) {
		entityCache.removeResult(
			DSLQueryStatusEntryImpl.class, dslQueryStatusEntry);
	}

	@Override
	public void clearCache(List<DSLQueryStatusEntry> dslQueryStatusEntries) {
		for (DSLQueryStatusEntry dslQueryStatusEntry : dslQueryStatusEntries) {
			entityCache.removeResult(
				DSLQueryStatusEntryImpl.class, dslQueryStatusEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DSLQueryStatusEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DSLQueryStatusEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new dsl query status entry with the primary key. Does not add the dsl query status entry to the database.
	 *
	 * @param dslQueryStatusEntryId the primary key for the new dsl query status entry
	 * @return the new dsl query status entry
	 */
	@Override
	public DSLQueryStatusEntry create(long dslQueryStatusEntryId) {
		DSLQueryStatusEntry dslQueryStatusEntry = new DSLQueryStatusEntryImpl();

		dslQueryStatusEntry.setNew(true);
		dslQueryStatusEntry.setPrimaryKey(dslQueryStatusEntryId);

		return dslQueryStatusEntry;
	}

	/**
	 * Removes the dsl query status entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry that was removed
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public DSLQueryStatusEntry remove(long dslQueryStatusEntryId)
		throws NoSuchDSLQueryStatusEntryException {

		return remove((Serializable)dslQueryStatusEntryId);
	}

	/**
	 * Removes the dsl query status entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dsl query status entry
	 * @return the dsl query status entry that was removed
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public DSLQueryStatusEntry remove(Serializable primaryKey)
		throws NoSuchDSLQueryStatusEntryException {

		Session session = null;

		try {
			session = openSession();

			DSLQueryStatusEntry dslQueryStatusEntry =
				(DSLQueryStatusEntry)session.get(
					DSLQueryStatusEntryImpl.class, primaryKey);

			if (dslQueryStatusEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDSLQueryStatusEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dslQueryStatusEntry);
		}
		catch (NoSuchDSLQueryStatusEntryException noSuchEntityException) {
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
	protected DSLQueryStatusEntry removeImpl(
		DSLQueryStatusEntry dslQueryStatusEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dslQueryStatusEntry)) {
				dslQueryStatusEntry = (DSLQueryStatusEntry)session.get(
					DSLQueryStatusEntryImpl.class,
					dslQueryStatusEntry.getPrimaryKeyObj());
			}

			if (dslQueryStatusEntry != null) {
				session.delete(dslQueryStatusEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dslQueryStatusEntry != null) {
			clearCache(dslQueryStatusEntry);
		}

		return dslQueryStatusEntry;
	}

	@Override
	public DSLQueryStatusEntry updateImpl(
		DSLQueryStatusEntry dslQueryStatusEntry) {

		boolean isNew = dslQueryStatusEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(dslQueryStatusEntry);
			}
			else {
				dslQueryStatusEntry = (DSLQueryStatusEntry)session.merge(
					dslQueryStatusEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DSLQueryStatusEntryImpl.class, dslQueryStatusEntry, false, true);

		if (isNew) {
			dslQueryStatusEntry.setNew(false);
		}

		dslQueryStatusEntry.resetOriginalValues();

		return dslQueryStatusEntry;
	}

	/**
	 * Returns the dsl query status entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dsl query status entry
	 * @return the dsl query status entry
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public DSLQueryStatusEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDSLQueryStatusEntryException {

		DSLQueryStatusEntry dslQueryStatusEntry = fetchByPrimaryKey(primaryKey);

		if (dslQueryStatusEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDSLQueryStatusEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dslQueryStatusEntry;
	}

	/**
	 * Returns the dsl query status entry with the primary key or throws a <code>NoSuchDSLQueryStatusEntryException</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public DSLQueryStatusEntry findByPrimaryKey(long dslQueryStatusEntryId)
		throws NoSuchDSLQueryStatusEntryException {

		return findByPrimaryKey((Serializable)dslQueryStatusEntryId);
	}

	/**
	 * Returns the dsl query status entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry, or <code>null</code> if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public DSLQueryStatusEntry fetchByPrimaryKey(long dslQueryStatusEntryId) {
		return fetchByPrimaryKey((Serializable)dslQueryStatusEntryId);
	}

	/**
	 * Returns all the dsl query status entries.
	 *
	 * @return the dsl query status entries
	 */
	@Override
	public List<DSLQueryStatusEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @return the range of dsl query status entries
	 */
	@Override
	public List<DSLQueryStatusEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dsl query status entries
	 */
	@Override
	public List<DSLQueryStatusEntry> findAll(
		int start, int end,
		OrderByComparator<DSLQueryStatusEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dsl query status entries
	 */
	@Override
	public List<DSLQueryStatusEntry> findAll(
		int start, int end,
		OrderByComparator<DSLQueryStatusEntry> orderByComparator,
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

		List<DSLQueryStatusEntry> list = null;

		if (useFinderCache) {
			list = (List<DSLQueryStatusEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DSLQUERYSTATUSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DSLQUERYSTATUSENTRY;

				sql = sql.concat(DSLQueryStatusEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DSLQueryStatusEntry>)QueryUtil.list(
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
	 * Removes all the dsl query status entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DSLQueryStatusEntry dslQueryStatusEntry : findAll()) {
			remove(dslQueryStatusEntry);
		}
	}

	/**
	 * Returns the number of dsl query status entries.
	 *
	 * @return the number of dsl query status entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DSLQUERYSTATUSENTRY);

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
		return "dslQueryStatusEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DSLQUERYSTATUSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DSLQueryStatusEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dsl query status entry persistence.
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
		entityCache.removeCache(DSLQueryStatusEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DSLQUERYSTATUSENTRY =
		"SELECT dslQueryStatusEntry FROM DSLQueryStatusEntry dslQueryStatusEntry";

	private static final String _SQL_COUNT_DSLQUERYSTATUSENTRY =
		"SELECT COUNT(dslQueryStatusEntry) FROM DSLQueryStatusEntry dslQueryStatusEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dslQueryStatusEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DSLQueryStatusEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		DSLQueryStatusEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}