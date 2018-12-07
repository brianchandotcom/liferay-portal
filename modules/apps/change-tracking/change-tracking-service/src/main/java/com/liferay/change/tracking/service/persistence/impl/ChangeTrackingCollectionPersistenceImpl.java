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

package com.liferay.change.tracking.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.ChangeTrackingCollection;
import com.liferay.change.tracking.model.impl.ChangeTrackingCollectionImpl;
import com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl;
import com.liferay.change.tracking.service.persistence.ChangeTrackingCollectionPersistence;
import com.liferay.change.tracking.service.persistence.ChangeTrackingEntryPersistence;

import com.liferay.petra.string.StringBundler;

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
 * The persistence implementation for the change tracking collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollectionPersistence
 * @see com.liferay.change.tracking.service.persistence.ChangeTrackingCollectionUtil
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionPersistenceImpl extends BasePersistenceImpl<ChangeTrackingCollection>
	implements ChangeTrackingCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangeTrackingCollectionUtil} to access the change tracking collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangeTrackingCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public ChangeTrackingCollectionPersistenceImpl() {
		setModelClass(ChangeTrackingCollection.class);

		setModelImplClass(ChangeTrackingCollectionImpl.class);
		setEntityCacheEnabled(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the change tracking collection in the entity cache if it is enabled.
	 *
	 * @param changeTrackingCollection the change tracking collection
	 */
	@Override
	public void cacheResult(ChangeTrackingCollection changeTrackingCollection) {
		entityCache.putResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionImpl.class,
			changeTrackingCollection.getPrimaryKey(), changeTrackingCollection);

		changeTrackingCollection.resetOriginalValues();
	}

	/**
	 * Caches the change tracking collections in the entity cache if it is enabled.
	 *
	 * @param changeTrackingCollections the change tracking collections
	 */
	@Override
	public void cacheResult(
		List<ChangeTrackingCollection> changeTrackingCollections) {
		for (ChangeTrackingCollection changeTrackingCollection : changeTrackingCollections) {
			if (entityCache.getResult(
						ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangeTrackingCollectionImpl.class,
						changeTrackingCollection.getPrimaryKey()) == null) {
				cacheResult(changeTrackingCollection);
			}
			else {
				changeTrackingCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all change tracking collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangeTrackingCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the change tracking collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangeTrackingCollection changeTrackingCollection) {
		entityCache.removeResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionImpl.class,
			changeTrackingCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<ChangeTrackingCollection> changeTrackingCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangeTrackingCollection changeTrackingCollection : changeTrackingCollections) {
			entityCache.removeResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangeTrackingCollectionImpl.class,
				changeTrackingCollection.getPrimaryKey());
		}
	}

	/**
	 * Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	 *
	 * @param changeTrackingCollectionId the primary key for the new change tracking collection
	 * @return the new change tracking collection
	 */
	@Override
	public ChangeTrackingCollection create(long changeTrackingCollectionId) {
		ChangeTrackingCollection changeTrackingCollection = new ChangeTrackingCollectionImpl();

		changeTrackingCollection.setNew(true);
		changeTrackingCollection.setPrimaryKey(changeTrackingCollectionId);

		changeTrackingCollection.setCompanyId(companyProvider.getCompanyId());

		return changeTrackingCollection;
	}

	/**
	 * Removes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changeTrackingCollectionId the primary key of the change tracking collection
	 * @return the change tracking collection that was removed
	 * @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	 */
	@Override
	public ChangeTrackingCollection remove(long changeTrackingCollectionId)
		throws NoSuchCollectionException {
		return remove((Serializable)changeTrackingCollectionId);
	}

	/**
	 * Removes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the change tracking collection
	 * @return the change tracking collection that was removed
	 * @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	 */
	@Override
	public ChangeTrackingCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {
		Session session = null;

		try {
			session = openSession();

			ChangeTrackingCollection changeTrackingCollection = (ChangeTrackingCollection)session.get(ChangeTrackingCollectionImpl.class,
					primaryKey);

			if (changeTrackingCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changeTrackingCollection);
		}
		catch (NoSuchCollectionException nsee) {
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
	protected ChangeTrackingCollection removeImpl(
		ChangeTrackingCollection changeTrackingCollection) {
		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteLeftPrimaryKeyTableMappings(changeTrackingCollection.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changeTrackingCollection)) {
				changeTrackingCollection = (ChangeTrackingCollection)session.get(ChangeTrackingCollectionImpl.class,
						changeTrackingCollection.getPrimaryKeyObj());
			}

			if (changeTrackingCollection != null) {
				session.delete(changeTrackingCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changeTrackingCollection != null) {
			clearCache(changeTrackingCollection);
		}

		return changeTrackingCollection;
	}

	@Override
	public ChangeTrackingCollection updateImpl(
		ChangeTrackingCollection changeTrackingCollection) {
		boolean isNew = changeTrackingCollection.isNew();

		if (!(changeTrackingCollection instanceof ChangeTrackingCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changeTrackingCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changeTrackingCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changeTrackingCollection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangeTrackingCollection implementation " +
				changeTrackingCollection.getClass());
		}

		ChangeTrackingCollectionModelImpl changeTrackingCollectionModelImpl = (ChangeTrackingCollectionModelImpl)changeTrackingCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changeTrackingCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				changeTrackingCollection.setCreateDate(now);
			}
			else {
				changeTrackingCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!changeTrackingCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changeTrackingCollection.setModifiedDate(now);
			}
			else {
				changeTrackingCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changeTrackingCollection.isNew()) {
				session.save(changeTrackingCollection);

				changeTrackingCollection.setNew(false);
			}
			else {
				changeTrackingCollection = (ChangeTrackingCollection)session.merge(changeTrackingCollection);
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

		entityCache.putResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingCollectionImpl.class,
			changeTrackingCollection.getPrimaryKey(), changeTrackingCollection,
			false);

		changeTrackingCollection.resetOriginalValues();

		return changeTrackingCollection;
	}

	/**
	 * Returns the change tracking collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the change tracking collection
	 * @return the change tracking collection
	 * @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	 */
	@Override
	public ChangeTrackingCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {
		ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(primaryKey);

		if (changeTrackingCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changeTrackingCollection;
	}

	/**
	 * Returns the change tracking collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	 *
	 * @param changeTrackingCollectionId the primary key of the change tracking collection
	 * @return the change tracking collection
	 * @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	 */
	@Override
	public ChangeTrackingCollection findByPrimaryKey(
		long changeTrackingCollectionId) throws NoSuchCollectionException {
		return findByPrimaryKey((Serializable)changeTrackingCollectionId);
	}

	/**
	 * Returns the change tracking collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changeTrackingCollectionId the primary key of the change tracking collection
	 * @return the change tracking collection, or <code>null</code> if a change tracking collection with the primary key could not be found
	 */
	@Override
	public ChangeTrackingCollection fetchByPrimaryKey(
		long changeTrackingCollectionId) {
		return fetchByPrimaryKey((Serializable)changeTrackingCollectionId);
	}

	@Override
	public Map<Serializable, ChangeTrackingCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangeTrackingCollection> map = new HashMap<Serializable, ChangeTrackingCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(primaryKey);

			if (changeTrackingCollection != null) {
				map.put(primaryKey, changeTrackingCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangeTrackingCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangeTrackingCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGETRACKINGCOLLECTION_WHERE_PKS_IN);

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

			for (ChangeTrackingCollection changeTrackingCollection : (List<ChangeTrackingCollection>)q.list()) {
				map.put(changeTrackingCollection.getPrimaryKeyObj(),
					changeTrackingCollection);

				cacheResult(changeTrackingCollection);

				uncachedPrimaryKeys.remove(changeTrackingCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangeTrackingCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangeTrackingCollectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the change tracking collections.
	 *
	 * @return the change tracking collections
	 */
	@Override
	public List<ChangeTrackingCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change tracking collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking collections
	 * @param end the upper bound of the range of change tracking collections (not inclusive)
	 * @return the range of change tracking collections
	 */
	@Override
	public List<ChangeTrackingCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the change tracking collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking collections
	 * @param end the upper bound of the range of change tracking collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change tracking collections
	 */
	@Override
	public List<ChangeTrackingCollection> findAll(int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change tracking collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking collections
	 * @param end the upper bound of the range of change tracking collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of change tracking collections
	 */
	@Override
	public List<ChangeTrackingCollection> findAll(int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator,
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

		List<ChangeTrackingCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeTrackingCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGETRACKINGCOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGETRACKINGCOLLECTION;

				if (pagination) {
					sql = sql.concat(ChangeTrackingCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangeTrackingCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeTrackingCollection>)QueryUtil.list(q,
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
	 * Removes all the change tracking collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangeTrackingCollection changeTrackingCollection : findAll()) {
			remove(changeTrackingCollection);
		}
	}

	/**
	 * Returns the number of change tracking collections.
	 *
	 * @return the number of change tracking collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGETRACKINGCOLLECTION);

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
	 * Returns the primaryKeys of change tracking entries associated with the change tracking collection.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @return long[] of the primaryKeys of change tracking entries associated with the change tracking collection
	 */
	@Override
	public long[] getChangeTrackingEntryPrimaryKeys(long pk) {
		long[] pks = changeTrackingCollectionToChangeTrackingEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the change tracking entries associated with the change tracking collection.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @return the change tracking entries associated with the change tracking collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk) {
		return getChangeTrackingEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the change tracking entries associated with the change tracking collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param start the lower bound of the range of change tracking collections
	 * @param end the upper bound of the range of change tracking collections (not inclusive)
	 * @return the range of change tracking entries associated with the change tracking collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end) {
		return getChangeTrackingEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change tracking entries associated with the change tracking collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param start the lower bound of the range of change tracking collections
	 * @param end the upper bound of the range of change tracking collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change tracking entries associated with the change tracking collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingEntry> orderByComparator) {
		return changeTrackingCollectionToChangeTrackingEntryTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of change tracking entries associated with the change tracking collection.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @return the number of change tracking entries associated with the change tracking collection
	 */
	@Override
	public int getChangeTrackingEntriesSize(long pk) {
		long[] pks = changeTrackingCollectionToChangeTrackingEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the change tracking entry is associated with the change tracking collection.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPK the primary key of the change tracking entry
	 * @return <code>true</code> if the change tracking entry is associated with the change tracking collection; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeTrackingEntry(long pk,
		long changeTrackingEntryPK) {
		return changeTrackingCollectionToChangeTrackingEntryTableMapper.containsTableMapping(pk,
			changeTrackingEntryPK);
	}

	/**
	 * Returns <code>true</code> if the change tracking collection has any change tracking entries associated with it.
	 *
	 * @param pk the primary key of the change tracking collection to check for associations with change tracking entries
	 * @return <code>true</code> if the change tracking collection has any change tracking entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeTrackingEntries(long pk) {
		if (getChangeTrackingEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPK the primary key of the change tracking entry
	 */
	@Override
	public void addChangeTrackingEntry(long pk, long changeTrackingEntryPK) {
		ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(pk);

		if (changeTrackingCollection == null) {
			changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeTrackingEntryPK);
		}
		else {
			changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMapping(changeTrackingCollection.getCompanyId(),
				pk, changeTrackingEntryPK);
		}
	}

	/**
	 * Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntry the change tracking entry
	 */
	@Override
	public void addChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(pk);

		if (changeTrackingCollection == null) {
			changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeTrackingEntry.getPrimaryKey());
		}
		else {
			changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMapping(changeTrackingCollection.getCompanyId(),
				pk, changeTrackingEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPKs the primary keys of the change tracking entries
	 */
	@Override
	public void addChangeTrackingEntries(long pk, long[] changeTrackingEntryPKs) {
		long companyId = 0;

		ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(pk);

		if (changeTrackingCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeTrackingCollection.getCompanyId();
		}

		changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMappings(companyId,
			pk, changeTrackingEntryPKs);
	}

	/**
	 * Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntries the change tracking entries
	 */
	@Override
	public void addChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		addChangeTrackingEntries(pk,
			ListUtil.toLongArray(changeTrackingEntries,
				com.liferay.change.tracking.model.ChangeTrackingEntry.CHANGE_TRACKING_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the change tracking collection and its change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection to clear the associated change tracking entries from
	 */
	@Override
	public void clearChangeTrackingEntries(long pk) {
		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPK the primary key of the change tracking entry
	 */
	@Override
	public void removeChangeTrackingEntry(long pk, long changeTrackingEntryPK) {
		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteTableMapping(pk,
			changeTrackingEntryPK);
	}

	/**
	 * Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntry the change tracking entry
	 */
	@Override
	public void removeChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteTableMapping(pk,
			changeTrackingEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPKs the primary keys of the change tracking entries
	 */
	@Override
	public void removeChangeTrackingEntries(long pk,
		long[] changeTrackingEntryPKs) {
		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteTableMappings(pk,
			changeTrackingEntryPKs);
	}

	/**
	 * Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntries the change tracking entries
	 */
	@Override
	public void removeChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		removeChangeTrackingEntries(pk,
			ListUtil.toLongArray(changeTrackingEntries,
				com.liferay.change.tracking.model.ChangeTrackingEntry.CHANGE_TRACKING_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntryPKs the primary keys of the change tracking entries to be associated with the change tracking collection
	 */
	@Override
	public void setChangeTrackingEntries(long pk, long[] changeTrackingEntryPKs) {
		Set<Long> newChangeTrackingEntryPKsSet = SetUtil.fromArray(changeTrackingEntryPKs);
		Set<Long> oldChangeTrackingEntryPKsSet = SetUtil.fromArray(changeTrackingCollectionToChangeTrackingEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeChangeTrackingEntryPKsSet = new HashSet<Long>(oldChangeTrackingEntryPKsSet);

		removeChangeTrackingEntryPKsSet.removeAll(newChangeTrackingEntryPKsSet);

		changeTrackingCollectionToChangeTrackingEntryTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeChangeTrackingEntryPKsSet));

		newChangeTrackingEntryPKsSet.removeAll(oldChangeTrackingEntryPKsSet);

		long companyId = 0;

		ChangeTrackingCollection changeTrackingCollection = fetchByPrimaryKey(pk);

		if (changeTrackingCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeTrackingCollection.getCompanyId();
		}

		changeTrackingCollectionToChangeTrackingEntryTableMapper.addTableMappings(companyId,
			pk, ArrayUtil.toLongArray(newChangeTrackingEntryPKsSet));
	}

	/**
	 * Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking collection
	 * @param changeTrackingEntries the change tracking entries to be associated with the change tracking collection
	 */
	@Override
	public void setChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		try {
			long[] changeTrackingEntryPKs = new long[changeTrackingEntries.size()];

			for (int i = 0; i < changeTrackingEntries.size(); i++) {
				com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry =
					changeTrackingEntries.get(i);

				changeTrackingEntryPKs[i] = changeTrackingEntry.getPrimaryKey();
			}

			setChangeTrackingEntries(pk, changeTrackingEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangeTrackingCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the change tracking collection persistence.
	 */
	public void afterPropertiesSet() {
		changeTrackingCollectionToChangeTrackingEntryTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "changeTrackingCollectionId",
				"changeTrackingEntryId", this, changeTrackingEntryPersistence);
	}

	public void destroy() {
		entityCache.removeCache(ChangeTrackingCollectionImpl.class.getName());
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
	@BeanReference(type = ChangeTrackingEntryPersistence.class)
	protected ChangeTrackingEntryPersistence changeTrackingEntryPersistence;
	protected TableMapper<ChangeTrackingCollection, com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingCollectionToChangeTrackingEntryTableMapper;
	private static final String _SQL_SELECT_CHANGETRACKINGCOLLECTION = "SELECT changeTrackingCollection FROM ChangeTrackingCollection changeTrackingCollection";
	private static final String _SQL_SELECT_CHANGETRACKINGCOLLECTION_WHERE_PKS_IN =
		"SELECT changeTrackingCollection FROM ChangeTrackingCollection changeTrackingCollection WHERE changeTrackingCollectionId IN (";
	private static final String _SQL_COUNT_CHANGETRACKINGCOLLECTION = "SELECT COUNT(changeTrackingCollection) FROM ChangeTrackingCollection changeTrackingCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changeTrackingCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangeTrackingCollection exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(ChangeTrackingCollectionPersistenceImpl.class);
}