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

package com.liferay.friendly.url.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryMappingException;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPK;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the friendly url entry mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMappingPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingUtil
 * @generated
 */
@ProviderType
public class FriendlyURLEntryMappingPersistenceImpl extends BasePersistenceImpl<FriendlyURLEntryMapping>
	implements FriendlyURLEntryMappingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLEntryMappingUtil} to access the friendly url entry mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLEntryMappingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryMappingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryMappingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public FriendlyURLEntryMappingPersistenceImpl() {
		setModelClass(FriendlyURLEntryMapping.class);
	}

	/**
	 * Caches the friendly url entry mapping in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryMapping the friendly url entry mapping
	 */
	@Override
	public void cacheResult(FriendlyURLEntryMapping friendlyURLEntryMapping) {
		entityCache.putResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingImpl.class,
			friendlyURLEntryMapping.getPrimaryKey(), friendlyURLEntryMapping);

		friendlyURLEntryMapping.resetOriginalValues();
	}

	/**
	 * Caches the friendly url entry mappings in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryMappings the friendly url entry mappings
	 */
	@Override
	public void cacheResult(
		List<FriendlyURLEntryMapping> friendlyURLEntryMappings) {
		for (FriendlyURLEntryMapping friendlyURLEntryMapping : friendlyURLEntryMappings) {
			if (entityCache.getResult(
						FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryMappingImpl.class,
						friendlyURLEntryMapping.getPrimaryKey()) == null) {
				cacheResult(friendlyURLEntryMapping);
			}
			else {
				friendlyURLEntryMapping.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url entry mappings.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLEntryMappingImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url entry mapping.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FriendlyURLEntryMapping friendlyURLEntryMapping) {
		entityCache.removeResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingImpl.class,
			friendlyURLEntryMapping.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<FriendlyURLEntryMapping> friendlyURLEntryMappings) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLEntryMapping friendlyURLEntryMapping : friendlyURLEntryMappings) {
			entityCache.removeResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryMappingImpl.class,
				friendlyURLEntryMapping.getPrimaryKey());
		}
	}

	/**
	 * Creates a new friendly url entry mapping with the primary key. Does not add the friendly url entry mapping to the database.
	 *
	 * @param friendlyURLEntryMappingPK the primary key for the new friendly url entry mapping
	 * @return the new friendly url entry mapping
	 */
	@Override
	public FriendlyURLEntryMapping create(
		FriendlyURLEntryMappingPK friendlyURLEntryMappingPK) {
		FriendlyURLEntryMapping friendlyURLEntryMapping = new FriendlyURLEntryMappingImpl();

		friendlyURLEntryMapping.setNew(true);
		friendlyURLEntryMapping.setPrimaryKey(friendlyURLEntryMappingPK);

		return friendlyURLEntryMapping;
	}

	/**
	 * Removes the friendly url entry mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLEntryMappingPK the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping that was removed
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping remove(
		FriendlyURLEntryMappingPK friendlyURLEntryMappingPK)
		throws NoSuchFriendlyURLEntryMappingException {
		return remove((Serializable)friendlyURLEntryMappingPK);
	}

	/**
	 * Removes the friendly url entry mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping that was removed
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping remove(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryMappingException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryMapping friendlyURLEntryMapping = (FriendlyURLEntryMapping)session.get(FriendlyURLEntryMappingImpl.class,
					primaryKey);

			if (friendlyURLEntryMapping == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLEntryMappingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLEntryMapping);
		}
		catch (NoSuchFriendlyURLEntryMappingException nsee) {
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
	protected FriendlyURLEntryMapping removeImpl(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {
		friendlyURLEntryMapping = toUnwrappedModel(friendlyURLEntryMapping);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLEntryMapping)) {
				friendlyURLEntryMapping = (FriendlyURLEntryMapping)session.get(FriendlyURLEntryMappingImpl.class,
						friendlyURLEntryMapping.getPrimaryKeyObj());
			}

			if (friendlyURLEntryMapping != null) {
				session.delete(friendlyURLEntryMapping);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLEntryMapping != null) {
			clearCache(friendlyURLEntryMapping);
		}

		return friendlyURLEntryMapping;
	}

	@Override
	public FriendlyURLEntryMapping updateImpl(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {
		friendlyURLEntryMapping = toUnwrappedModel(friendlyURLEntryMapping);

		boolean isNew = friendlyURLEntryMapping.isNew();

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLEntryMapping.isNew()) {
				session.save(friendlyURLEntryMapping);

				friendlyURLEntryMapping.setNew(false);
			}
			else {
				friendlyURLEntryMapping = (FriendlyURLEntryMapping)session.merge(friendlyURLEntryMapping);
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

		entityCache.putResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryMappingImpl.class,
			friendlyURLEntryMapping.getPrimaryKey(), friendlyURLEntryMapping,
			false);

		friendlyURLEntryMapping.resetOriginalValues();

		return friendlyURLEntryMapping;
	}

	protected FriendlyURLEntryMapping toUnwrappedModel(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {
		if (friendlyURLEntryMapping instanceof FriendlyURLEntryMappingImpl) {
			return friendlyURLEntryMapping;
		}

		FriendlyURLEntryMappingImpl friendlyURLEntryMappingImpl = new FriendlyURLEntryMappingImpl();

		friendlyURLEntryMappingImpl.setNew(friendlyURLEntryMapping.isNew());
		friendlyURLEntryMappingImpl.setPrimaryKey(friendlyURLEntryMapping.getPrimaryKey());

		friendlyURLEntryMappingImpl.setClassNameId(friendlyURLEntryMapping.getClassNameId());
		friendlyURLEntryMappingImpl.setClassPK(friendlyURLEntryMapping.getClassPK());
		friendlyURLEntryMappingImpl.setFriendlyURLEntryId(friendlyURLEntryMapping.getFriendlyURLEntryId());

		return friendlyURLEntryMappingImpl;
	}

	/**
	 * Returns the friendly url entry mapping with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryMappingException {
		FriendlyURLEntryMapping friendlyURLEntryMapping = fetchByPrimaryKey(primaryKey);

		if (friendlyURLEntryMapping == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLEntryMappingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLEntryMapping;
	}

	/**
	 * Returns the friendly url entry mapping with the primary key or throws a {@link NoSuchFriendlyURLEntryMappingException} if it could not be found.
	 *
	 * @param friendlyURLEntryMappingPK the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping findByPrimaryKey(
		FriendlyURLEntryMappingPK friendlyURLEntryMappingPK)
		throws NoSuchFriendlyURLEntryMappingException {
		return findByPrimaryKey((Serializable)friendlyURLEntryMappingPK);
	}

	/**
	 * Returns the friendly url entry mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping, or <code>null</code> if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryMappingImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLEntryMapping friendlyURLEntryMapping = (FriendlyURLEntryMapping)serializable;

		if (friendlyURLEntryMapping == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLEntryMapping = (FriendlyURLEntryMapping)session.get(FriendlyURLEntryMappingImpl.class,
						primaryKey);

				if (friendlyURLEntryMapping != null) {
					cacheResult(friendlyURLEntryMapping);
				}
				else {
					entityCache.putResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryMappingImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLEntryMappingModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryMappingImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLEntryMapping;
	}

	/**
	 * Returns the friendly url entry mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLEntryMappingPK the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping, or <code>null</code> if a friendly url entry mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryMapping fetchByPrimaryKey(
		FriendlyURLEntryMappingPK friendlyURLEntryMappingPK) {
		return fetchByPrimaryKey((Serializable)friendlyURLEntryMappingPK);
	}

	@Override
	public Map<Serializable, FriendlyURLEntryMapping> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLEntryMapping> map = new HashMap<Serializable, FriendlyURLEntryMapping>();

		for (Serializable primaryKey : primaryKeys) {
			FriendlyURLEntryMapping friendlyURLEntryMapping = fetchByPrimaryKey(primaryKey);

			if (friendlyURLEntryMapping != null) {
				map.put(primaryKey, friendlyURLEntryMapping);
			}
		}

		return map;
	}

	/**
	 * Returns all the friendly url entry mappings.
	 *
	 * @return the friendly url entry mappings
	 */
	@Override
	public List<FriendlyURLEntryMapping> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @return the range of friendly url entry mappings
	 */
	@Override
	public List<FriendlyURLEntryMapping> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url entry mappings
	 */
	@Override
	public List<FriendlyURLEntryMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntryMapping> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url entry mappings
	 */
	@Override
	public List<FriendlyURLEntryMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntryMapping> orderByComparator,
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

		List<FriendlyURLEntryMapping> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntryMapping>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLENTRYMAPPING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLENTRYMAPPING;

				if (pagination) {
					sql = sql.concat(FriendlyURLEntryMappingModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLEntryMapping>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntryMapping>)QueryUtil.list(q,
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
	 * Removes all the friendly url entry mappings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLEntryMapping friendlyURLEntryMapping : findAll()) {
			remove(friendlyURLEntryMapping);
		}
	}

	/**
	 * Returns the number of friendly url entry mappings.
	 *
	 * @return the number of friendly url entry mappings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLENTRYMAPPING);

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

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FriendlyURLEntryMappingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url entry mapping persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLEntryMappingImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_FRIENDLYURLENTRYMAPPING = "SELECT friendlyURLEntryMapping FROM FriendlyURLEntryMapping friendlyURLEntryMapping";
	private static final String _SQL_COUNT_FRIENDLYURLENTRYMAPPING = "SELECT COUNT(friendlyURLEntryMapping) FROM FriendlyURLEntryMapping friendlyURLEntryMapping";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLEntryMapping.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLEntryMapping exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLEntryMappingPersistenceImpl.class);
}