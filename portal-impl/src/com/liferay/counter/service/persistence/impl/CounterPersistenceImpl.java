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

package com.liferay.counter.service.persistence.impl;

import com.liferay.counter.kernel.exception.NoSuchCounterException;
import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.service.persistence.CounterPersistence;
import com.liferay.counter.model.impl.CounterImpl;
import com.liferay.counter.model.impl.CounterModelImpl;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CounterPersistenceImpl
	extends BasePersistenceImpl<Counter> implements CounterPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CounterUtil</code> to access the counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CounterImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public CounterPersistenceImpl() {
		setModelClass(Counter.class);
	}

	/**
	 * Caches the counter in the entity cache if it is enabled.
	 *
	 * @param counter the counter
	 */
	@Override
	public void cacheResult(Counter counter) {
		EntityCacheUtil.putResult(
			CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
			counter.getPrimaryKey(), counter);

		counter.resetOriginalValues();
	}

	/**
	 * Caches the counters in the entity cache if it is enabled.
	 *
	 * @param counters the counters
	 */
	@Override
	public void cacheResult(List<Counter> counters) {
		for (Counter counter : counters) {
			if (EntityCacheUtil.getResult(
					CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
					counter.getPrimaryKey()) == null) {

				cacheResult(counter);
			}
			else {
				counter.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all counters.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CounterImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the counter.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Counter counter) {
		EntityCacheUtil.removeResult(
			CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
			counter.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Counter> counters) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Counter counter : counters) {
			EntityCacheUtil.removeResult(
				CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
				counter.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
				primaryKey);
		}
	}

	/**
	 * Creates a new counter with the primary key. Does not add the counter to the database.
	 *
	 * @param name the primary key for the new counter
	 * @return the new counter
	 */
	@Override
	public Counter create(String name) {
		Counter counter = new CounterImpl();

		counter.setNew(true);
		counter.setPrimaryKey(name);

		return counter;
	}

	/**
	 * Removes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param name the primary key of the counter
	 * @return the counter that was removed
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	@Override
	public Counter remove(String name) throws NoSuchCounterException {
		return remove((Serializable)name);
	}

	/**
	 * Removes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the counter
	 * @return the counter that was removed
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	@Override
	public Counter remove(Serializable primaryKey)
		throws NoSuchCounterException {

		Session session = null;

		try {
			session = openSession();

			Counter counter = (Counter)session.get(
				CounterImpl.class, primaryKey);

			if (counter == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCounterException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(counter);
		}
		catch (NoSuchCounterException noSuchEntityException) {
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
	protected Counter removeImpl(Counter counter) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(counter)) {
				counter = (Counter)session.get(
					CounterImpl.class, counter.getPrimaryKeyObj());
			}

			if (counter != null) {
				session.delete(counter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (counter != null) {
			clearCache(counter);
		}

		return counter;
	}

	@Override
	public Counter updateImpl(Counter counter) {
		boolean isNew = counter.isNew();

		Session session = null;

		try {
			session = openSession();

			if (counter.isNew()) {
				session.save(counter);

				counter.setNew(false);
			}
			else {
				counter = (Counter)session.merge(counter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		EntityCacheUtil.putResult(
			CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
			counter.getPrimaryKey(), counter, false);

		counter.resetOriginalValues();

		return counter;
	}

	/**
	 * Returns the counter with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the counter
	 * @return the counter
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	@Override
	public Counter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCounterException {

		Counter counter = fetchByPrimaryKey(primaryKey);

		if (counter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCounterException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return counter;
	}

	/**
	 * Returns the counter with the primary key or throws a <code>NoSuchCounterException</code> if it could not be found.
	 *
	 * @param name the primary key of the counter
	 * @return the counter
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	@Override
	public Counter findByPrimaryKey(String name) throws NoSuchCounterException {
		return findByPrimaryKey((Serializable)name);
	}

	/**
	 * Returns the counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the counter
	 * @return the counter, or <code>null</code> if a counter with the primary key could not be found
	 */
	@Override
	public Counter fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = EntityCacheUtil.getResult(
			CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Counter counter = (Counter)serializable;

		if (counter == null) {
			Session session = null;

			try {
				session = openSession();

				counter = (Counter)session.get(CounterImpl.class, primaryKey);

				if (counter != null) {
					cacheResult(counter);
				}
				else {
					EntityCacheUtil.putResult(
						CounterModelImpl.ENTITY_CACHE_ENABLED,
						CounterImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				EntityCacheUtil.removeResult(
					CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
					primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return counter;
	}

	/**
	 * Returns the counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param name the primary key of the counter
	 * @return the counter, or <code>null</code> if a counter with the primary key could not be found
	 */
	@Override
	public Counter fetchByPrimaryKey(String name) {
		return fetchByPrimaryKey((Serializable)name);
	}

	@Override
	public Map<Serializable, Counter> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Counter> map = new HashMap<Serializable, Counter>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Counter counter = fetchByPrimaryKey(primaryKey);

			if (counter != null) {
				map.put(primaryKey, counter);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = EntityCacheUtil.getResult(
				CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Counter)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COUNTER_WHERE_PKS_IN);

		for (int i = 0; i < uncachedPrimaryKeys.size(); i++) {
			sb.append("?");

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(query);

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				queryPos.add((String)primaryKey);
			}

			for (Counter counter : (List<Counter>)query.list()) {
				map.put(counter.getPrimaryKeyObj(), counter);

				cacheResult(counter);

				uncachedPrimaryKeys.remove(counter.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(
					CounterModelImpl.ENTITY_CACHE_ENABLED, CounterImpl.class,
					primaryKey, nullModel);
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
	 * Returns all the counters.
	 *
	 * @return the counters
	 */
	@Override
	public List<Counter> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @return the range of counters
	 */
	@Override
	public List<Counter> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of counters
	 */
	@Override
	public List<Counter> findAll(
		int start, int end, OrderByComparator<Counter> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of counters
	 */
	@Override
	public List<Counter> findAll(
		int start, int end, OrderByComparator<Counter> orderByComparator,
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

		List<Counter> list = null;

		if (useFinderCache) {
			list = (List<Counter>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COUNTER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COUNTER;

				sql = sql.concat(CounterModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Counter>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the counters from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Counter counter : findAll()) {
			remove(counter);
		}
	}

	/**
	 * Returns the number of counters.
	 *
	 * @return the number of counters
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COUNTER);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CounterModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the counter persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterModelImpl.FINDER_CACHE_ENABLED, CounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterModelImpl.FINDER_CACHE_ENABLED, CounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(CounterImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_COUNTER =
		"SELECT counter FROM Counter counter";

	private static final String _SQL_SELECT_COUNTER_WHERE_PKS_IN =
		"SELECT counter FROM Counter counter WHERE name IN (";

	private static final String _SQL_COUNT_COUNTER =
		"SELECT COUNT(counter) FROM Counter counter";

	private static final String _ORDER_BY_ENTITY_ALIAS = "counter.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Counter exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		CounterPersistenceImpl.class);

}