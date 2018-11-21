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

package com.liferay.change.tracking.engine.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.exception.NoSuchChangeCollectionException;
import com.liferay.change.tracking.engine.model.ChangeCollection;
import com.liferay.change.tracking.engine.model.impl.ChangeCollectionImpl;
import com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl;
import com.liferay.change.tracking.engine.service.persistence.ChangeCollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.ChangeEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the change collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollectionPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.ChangeCollectionUtil
 * @generated
 */
@ProviderType
public class ChangeCollectionPersistenceImpl extends BasePersistenceImpl<ChangeCollection>
	implements ChangeCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangeCollectionUtil} to access the change collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangeCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangeCollectionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangeCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public ChangeCollectionPersistenceImpl() {
		setModelClass(ChangeCollection.class);
	}

	/**
	 * Caches the change collection in the entity cache if it is enabled.
	 *
	 * @param changeCollection the change collection
	 */
	@Override
	public void cacheResult(ChangeCollection changeCollection) {
		entityCache.putResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionImpl.class, changeCollection.getPrimaryKey(),
			changeCollection);

		changeCollection.resetOriginalValues();
	}

	/**
	 * Caches the change collections in the entity cache if it is enabled.
	 *
	 * @param changeCollections the change collections
	 */
	@Override
	public void cacheResult(List<ChangeCollection> changeCollections) {
		for (ChangeCollection changeCollection : changeCollections) {
			if (entityCache.getResult(
						ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangeCollectionImpl.class,
						changeCollection.getPrimaryKey()) == null) {
				cacheResult(changeCollection);
			}
			else {
				changeCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all change collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangeCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the change collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangeCollection changeCollection) {
		entityCache.removeResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionImpl.class, changeCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ChangeCollection> changeCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangeCollection changeCollection : changeCollections) {
			entityCache.removeResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangeCollectionImpl.class, changeCollection.getPrimaryKey());
		}
	}

	/**
	 * Creates a new change collection with the primary key. Does not add the change collection to the database.
	 *
	 * @param changeCollectionId the primary key for the new change collection
	 * @return the new change collection
	 */
	@Override
	public ChangeCollection create(long changeCollectionId) {
		ChangeCollection changeCollection = new ChangeCollectionImpl();

		changeCollection.setNew(true);
		changeCollection.setPrimaryKey(changeCollectionId);

		changeCollection.setCompanyId(companyProvider.getCompanyId());

		return changeCollection;
	}

	/**
	 * Removes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changeCollectionId the primary key of the change collection
	 * @return the change collection that was removed
	 * @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection remove(long changeCollectionId)
		throws NoSuchChangeCollectionException {
		return remove((Serializable)changeCollectionId);
	}

	/**
	 * Removes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the change collection
	 * @return the change collection that was removed
	 * @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection remove(Serializable primaryKey)
		throws NoSuchChangeCollectionException {
		Session session = null;

		try {
			session = openSession();

			ChangeCollection changeCollection = (ChangeCollection)session.get(ChangeCollectionImpl.class,
					primaryKey);

			if (changeCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChangeCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changeCollection);
		}
		catch (NoSuchChangeCollectionException nsee) {
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
	protected ChangeCollection removeImpl(ChangeCollection changeCollection) {
		changeCollectionToChangeEntryTableMapper.deleteLeftPrimaryKeyTableMappings(changeCollection.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changeCollection)) {
				changeCollection = (ChangeCollection)session.get(ChangeCollectionImpl.class,
						changeCollection.getPrimaryKeyObj());
			}

			if (changeCollection != null) {
				session.delete(changeCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changeCollection != null) {
			clearCache(changeCollection);
		}

		return changeCollection;
	}

	@Override
	public ChangeCollection updateImpl(ChangeCollection changeCollection) {
		boolean isNew = changeCollection.isNew();

		if (!(changeCollection instanceof ChangeCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changeCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changeCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changeCollection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangeCollection implementation " +
				changeCollection.getClass());
		}

		ChangeCollectionModelImpl changeCollectionModelImpl = (ChangeCollectionModelImpl)changeCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changeCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				changeCollection.setCreateDate(now);
			}
			else {
				changeCollection.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!changeCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changeCollection.setModifiedDate(now);
			}
			else {
				changeCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changeCollection.isNew()) {
				session.save(changeCollection);

				changeCollection.setNew(false);
			}
			else {
				changeCollection = (ChangeCollection)session.merge(changeCollection);
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
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeCollectionImpl.class, changeCollection.getPrimaryKey(),
			changeCollection, false);

		changeCollection.resetOriginalValues();

		return changeCollection;
	}

	/**
	 * Returns the change collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the change collection
	 * @return the change collection
	 * @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchChangeCollectionException {
		ChangeCollection changeCollection = fetchByPrimaryKey(primaryKey);

		if (changeCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChangeCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changeCollection;
	}

	/**
	 * Returns the change collection with the primary key or throws a {@link NoSuchChangeCollectionException} if it could not be found.
	 *
	 * @param changeCollectionId the primary key of the change collection
	 * @return the change collection
	 * @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection findByPrimaryKey(long changeCollectionId)
		throws NoSuchChangeCollectionException {
		return findByPrimaryKey((Serializable)changeCollectionId);
	}

	/**
	 * Returns the change collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the change collection
	 * @return the change collection, or <code>null</code> if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangeCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ChangeCollection changeCollection = (ChangeCollection)serializable;

		if (changeCollection == null) {
			Session session = null;

			try {
				session = openSession();

				changeCollection = (ChangeCollection)session.get(ChangeCollectionImpl.class,
						primaryKey);

				if (changeCollection != null) {
					cacheResult(changeCollection);
				}
				else {
					entityCache.putResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangeCollectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangeCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return changeCollection;
	}

	/**
	 * Returns the change collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changeCollectionId the primary key of the change collection
	 * @return the change collection, or <code>null</code> if a change collection with the primary key could not be found
	 */
	@Override
	public ChangeCollection fetchByPrimaryKey(long changeCollectionId) {
		return fetchByPrimaryKey((Serializable)changeCollectionId);
	}

	@Override
	public Map<Serializable, ChangeCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangeCollection> map = new HashMap<Serializable, ChangeCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangeCollection changeCollection = fetchByPrimaryKey(primaryKey);

			if (changeCollection != null) {
				map.put(primaryKey, changeCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangeCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangeCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGECOLLECTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ChangeCollection changeCollection : (List<ChangeCollection>)q.list()) {
				map.put(changeCollection.getPrimaryKeyObj(), changeCollection);

				cacheResult(changeCollection);

				uncachedPrimaryKeys.remove(changeCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangeCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangeCollectionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the change collections.
	 *
	 * @return the change collections
	 */
	@Override
	public List<ChangeCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change collections
	 * @param end the upper bound of the range of change collections (not inclusive)
	 * @return the range of change collections
	 */
	@Override
	public List<ChangeCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the change collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change collections
	 * @param end the upper bound of the range of change collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change collections
	 */
	@Override
	public List<ChangeCollection> findAll(int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change collections
	 * @param end the upper bound of the range of change collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of change collections
	 */
	@Override
	public List<ChangeCollection> findAll(int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ChangeCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGECOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGECOLLECTION;

				if (pagination) {
					sql = sql.concat(ChangeCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangeCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the change collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangeCollection changeCollection : findAll()) {
			remove(changeCollection);
		}
	}

	/**
	 * Returns the number of change collections.
	 *
	 * @return the number of change collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGECOLLECTION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of change entries associated with the change collection.
	 *
	 * @param pk the primary key of the change collection
	 * @return long[] of the primaryKeys of change entries associated with the change collection
	 */
	@Override
	public long[] getChangeEntryPrimaryKeys(long pk) {
		long[] pks = changeCollectionToChangeEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the change entries associated with the change collection.
	 *
	 * @param pk the primary key of the change collection
	 * @return the change entries associated with the change collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk) {
		return getChangeEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the change entries associated with the change collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change collection
	 * @param start the lower bound of the range of change collections
	 * @param end the upper bound of the range of change collections (not inclusive)
	 * @return the range of change entries associated with the change collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end) {
		return getChangeEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change entries associated with the change collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change collection
	 * @param start the lower bound of the range of change collections
	 * @param end the upper bound of the range of change collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change entries associated with the change collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.ChangeEntry> orderByComparator) {
		return changeCollectionToChangeEntryTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of change entries associated with the change collection.
	 *
	 * @param pk the primary key of the change collection
	 * @return the number of change entries associated with the change collection
	 */
	@Override
	public int getChangeEntriesSize(long pk) {
		long[] pks = changeCollectionToChangeEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the change entry is associated with the change collection.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPK the primary key of the change entry
	 * @return <code>true</code> if the change entry is associated with the change collection; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeEntry(long pk, long changeEntryPK) {
		return changeCollectionToChangeEntryTableMapper.containsTableMapping(pk,
			changeEntryPK);
	}

	/**
	 * Returns <code>true</code> if the change collection has any change entries associated with it.
	 *
	 * @param pk the primary key of the change collection to check for associations with change entries
	 * @return <code>true</code> if the change collection has any change entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeEntries(long pk) {
		if (getChangeEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPK the primary key of the change entry
	 */
	@Override
	public void addChangeEntry(long pk, long changeEntryPK) {
		ChangeCollection changeCollection = fetchByPrimaryKey(pk);

		if (changeCollection == null) {
			changeCollectionToChangeEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeEntryPK);
		}
		else {
			changeCollectionToChangeEntryTableMapper.addTableMapping(changeCollection.getCompanyId(),
				pk, changeEntryPK);
		}
	}

	/**
	 * Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntry the change entry
	 */
	@Override
	public void addChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		ChangeCollection changeCollection = fetchByPrimaryKey(pk);

		if (changeCollection == null) {
			changeCollectionToChangeEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeEntry.getPrimaryKey());
		}
		else {
			changeCollectionToChangeEntryTableMapper.addTableMapping(changeCollection.getCompanyId(),
				pk, changeEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPKs the primary keys of the change entries
	 */
	@Override
	public void addChangeEntries(long pk, long[] changeEntryPKs) {
		long companyId = 0;

		ChangeCollection changeCollection = fetchByPrimaryKey(pk);

		if (changeCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeCollection.getCompanyId();
		}

		changeCollectionToChangeEntryTableMapper.addTableMappings(companyId,
			pk, changeEntryPKs);
	}

	/**
	 * Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntries the change entries
	 */
	@Override
	public void addChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		addChangeEntries(pk,
			ListUtil.toLongArray(changeEntries,
				com.liferay.change.tracking.engine.model.ChangeEntry.CHANGE_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the change collection and its change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection to clear the associated change entries from
	 */
	@Override
	public void clearChangeEntries(long pk) {
		changeCollectionToChangeEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPK the primary key of the change entry
	 */
	@Override
	public void removeChangeEntry(long pk, long changeEntryPK) {
		changeCollectionToChangeEntryTableMapper.deleteTableMapping(pk,
			changeEntryPK);
	}

	/**
	 * Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntry the change entry
	 */
	@Override
	public void removeChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		changeCollectionToChangeEntryTableMapper.deleteTableMapping(pk,
			changeEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPKs the primary keys of the change entries
	 */
	@Override
	public void removeChangeEntries(long pk, long[] changeEntryPKs) {
		changeCollectionToChangeEntryTableMapper.deleteTableMappings(pk,
			changeEntryPKs);
	}

	/**
	 * Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntries the change entries
	 */
	@Override
	public void removeChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		removeChangeEntries(pk,
			ListUtil.toLongArray(changeEntries,
				com.liferay.change.tracking.engine.model.ChangeEntry.CHANGE_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntryPKs the primary keys of the change entries to be associated with the change collection
	 */
	@Override
	public void setChangeEntries(long pk, long[] changeEntryPKs) {
		Set<Long> newChangeEntryPKsSet = SetUtil.fromArray(changeEntryPKs);
		Set<Long> oldChangeEntryPKsSet = SetUtil.fromArray(changeCollectionToChangeEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeChangeEntryPKsSet = new HashSet<Long>(oldChangeEntryPKsSet);

		removeChangeEntryPKsSet.removeAll(newChangeEntryPKsSet);

		changeCollectionToChangeEntryTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeChangeEntryPKsSet));

		newChangeEntryPKsSet.removeAll(oldChangeEntryPKsSet);

		long companyId = 0;

		ChangeCollection changeCollection = fetchByPrimaryKey(pk);

		if (changeCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeCollection.getCompanyId();
		}

		changeCollectionToChangeEntryTableMapper.addTableMappings(companyId,
			pk, ArrayUtil.toLongArray(newChangeEntryPKsSet));
	}

	/**
	 * Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change collection
	 * @param changeEntries the change entries to be associated with the change collection
	 */
	@Override
	public void setChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		try {
			long[] changeEntryPKs = new long[changeEntries.size()];

			for (int i = 0; i < changeEntries.size(); i++) {
				com.liferay.change.tracking.engine.model.ChangeEntry changeEntry =
					changeEntries.get(i);

				changeEntryPKs[i] = changeEntry.getPrimaryKey();
			}

			setChangeEntries(pk, changeEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangeCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the change collection persistence.
	 */
	public void afterPropertiesSet() {
		changeCollectionToChangeEntryTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "changeCollectionId", "changeEntryId", this,
				changeEntryPersistence);
	}

	public void destroy() {
		entityCache.removeCache(ChangeCollectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("Collections_Entries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	@BeanReference(type = ChangeEntryPersistence.class)
	protected ChangeEntryPersistence changeEntryPersistence;
	protected TableMapper<ChangeCollection, com.liferay.change.tracking.engine.model.ChangeEntry> changeCollectionToChangeEntryTableMapper;
	private static final String _SQL_SELECT_CHANGECOLLECTION = "SELECT changeCollection FROM ChangeCollection changeCollection";
	private static final String _SQL_SELECT_CHANGECOLLECTION_WHERE_PKS_IN = "SELECT changeCollection FROM ChangeCollection changeCollection WHERE changeCollectionId IN (";
	private static final String _SQL_COUNT_CHANGECOLLECTION = "SELECT COUNT(changeCollection) FROM ChangeCollection changeCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changeCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangeCollection exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(ChangeCollectionPersistenceImpl.class);
}