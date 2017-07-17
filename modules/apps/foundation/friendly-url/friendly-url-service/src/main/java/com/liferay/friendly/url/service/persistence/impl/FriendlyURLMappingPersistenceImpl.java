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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLMappingException;
import com.liferay.friendly.url.model.FriendlyURLMapping;
import com.liferay.friendly.url.model.impl.FriendlyURLMappingImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLMappingModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPersistence;

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
 * The persistence implementation for the friendly url mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLMappingPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLMappingUtil
 * @generated
 */
@ProviderType
public class FriendlyURLMappingPersistenceImpl extends BasePersistenceImpl<FriendlyURLMapping>
	implements FriendlyURLMappingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLMappingUtil} to access the friendly url mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLMappingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLMappingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLMappingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public FriendlyURLMappingPersistenceImpl() {
		setModelClass(FriendlyURLMapping.class);
	}

	/**
	 * Caches the friendly url mapping in the entity cache if it is enabled.
	 *
	 * @param friendlyURLMapping the friendly url mapping
	 */
	@Override
	public void cacheResult(FriendlyURLMapping friendlyURLMapping) {
		entityCache.putResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingImpl.class, friendlyURLMapping.getPrimaryKey(),
			friendlyURLMapping);

		friendlyURLMapping.resetOriginalValues();
	}

	/**
	 * Caches the friendly url mappings in the entity cache if it is enabled.
	 *
	 * @param friendlyURLMappings the friendly url mappings
	 */
	@Override
	public void cacheResult(List<FriendlyURLMapping> friendlyURLMappings) {
		for (FriendlyURLMapping friendlyURLMapping : friendlyURLMappings) {
			if (entityCache.getResult(
						FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLMappingImpl.class,
						friendlyURLMapping.getPrimaryKey()) == null) {
				cacheResult(friendlyURLMapping);
			}
			else {
				friendlyURLMapping.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url mappings.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLMappingImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url mapping.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FriendlyURLMapping friendlyURLMapping) {
		entityCache.removeResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingImpl.class, friendlyURLMapping.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<FriendlyURLMapping> friendlyURLMappings) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLMapping friendlyURLMapping : friendlyURLMappings) {
			entityCache.removeResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLMappingImpl.class, friendlyURLMapping.getPrimaryKey());
		}
	}

	/**
	 * Creates a new friendly url mapping with the primary key. Does not add the friendly url mapping to the database.
	 *
	 * @param friendlyURLMappingPK the primary key for the new friendly url mapping
	 * @return the new friendly url mapping
	 */
	@Override
	public FriendlyURLMapping create(FriendlyURLMappingPK friendlyURLMappingPK) {
		FriendlyURLMapping friendlyURLMapping = new FriendlyURLMappingImpl();

		friendlyURLMapping.setNew(true);
		friendlyURLMapping.setPrimaryKey(friendlyURLMappingPK);

		return friendlyURLMapping;
	}

	/**
	 * Removes the friendly url mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLMappingPK the primary key of the friendly url mapping
	 * @return the friendly url mapping that was removed
	 * @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping remove(FriendlyURLMappingPK friendlyURLMappingPK)
		throws NoSuchFriendlyURLMappingException {
		return remove((Serializable)friendlyURLMappingPK);
	}

	/**
	 * Removes the friendly url mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url mapping
	 * @return the friendly url mapping that was removed
	 * @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping remove(Serializable primaryKey)
		throws NoSuchFriendlyURLMappingException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLMapping friendlyURLMapping = (FriendlyURLMapping)session.get(FriendlyURLMappingImpl.class,
					primaryKey);

			if (friendlyURLMapping == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLMappingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLMapping);
		}
		catch (NoSuchFriendlyURLMappingException nsee) {
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
	protected FriendlyURLMapping removeImpl(
		FriendlyURLMapping friendlyURLMapping) {
		friendlyURLMapping = toUnwrappedModel(friendlyURLMapping);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLMapping)) {
				friendlyURLMapping = (FriendlyURLMapping)session.get(FriendlyURLMappingImpl.class,
						friendlyURLMapping.getPrimaryKeyObj());
			}

			if (friendlyURLMapping != null) {
				session.delete(friendlyURLMapping);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLMapping != null) {
			clearCache(friendlyURLMapping);
		}

		return friendlyURLMapping;
	}

	@Override
	public FriendlyURLMapping updateImpl(FriendlyURLMapping friendlyURLMapping) {
		friendlyURLMapping = toUnwrappedModel(friendlyURLMapping);

		boolean isNew = friendlyURLMapping.isNew();

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLMapping.isNew()) {
				session.save(friendlyURLMapping);

				friendlyURLMapping.setNew(false);
			}
			else {
				friendlyURLMapping = (FriendlyURLMapping)session.merge(friendlyURLMapping);
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

		entityCache.putResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLMappingImpl.class, friendlyURLMapping.getPrimaryKey(),
			friendlyURLMapping, false);

		friendlyURLMapping.resetOriginalValues();

		return friendlyURLMapping;
	}

	protected FriendlyURLMapping toUnwrappedModel(
		FriendlyURLMapping friendlyURLMapping) {
		if (friendlyURLMapping instanceof FriendlyURLMappingImpl) {
			return friendlyURLMapping;
		}

		FriendlyURLMappingImpl friendlyURLMappingImpl = new FriendlyURLMappingImpl();

		friendlyURLMappingImpl.setNew(friendlyURLMapping.isNew());
		friendlyURLMappingImpl.setPrimaryKey(friendlyURLMapping.getPrimaryKey());

		friendlyURLMappingImpl.setClassNameId(friendlyURLMapping.getClassNameId());
		friendlyURLMappingImpl.setClassPK(friendlyURLMapping.getClassPK());
		friendlyURLMappingImpl.setFriendlyURLId(friendlyURLMapping.getFriendlyURLId());

		return friendlyURLMappingImpl;
	}

	/**
	 * Returns the friendly url mapping with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url mapping
	 * @return the friendly url mapping
	 * @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLMappingException {
		FriendlyURLMapping friendlyURLMapping = fetchByPrimaryKey(primaryKey);

		if (friendlyURLMapping == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLMappingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLMapping;
	}

	/**
	 * Returns the friendly url mapping with the primary key or throws a {@link NoSuchFriendlyURLMappingException} if it could not be found.
	 *
	 * @param friendlyURLMappingPK the primary key of the friendly url mapping
	 * @return the friendly url mapping
	 * @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping findByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK)
		throws NoSuchFriendlyURLMappingException {
		return findByPrimaryKey((Serializable)friendlyURLMappingPK);
	}

	/**
	 * Returns the friendly url mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url mapping
	 * @return the friendly url mapping, or <code>null</code> if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLMappingImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLMapping friendlyURLMapping = (FriendlyURLMapping)serializable;

		if (friendlyURLMapping == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLMapping = (FriendlyURLMapping)session.get(FriendlyURLMappingImpl.class,
						primaryKey);

				if (friendlyURLMapping != null) {
					cacheResult(friendlyURLMapping);
				}
				else {
					entityCache.putResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLMappingImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLMappingModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLMappingImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLMapping;
	}

	/**
	 * Returns the friendly url mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLMappingPK the primary key of the friendly url mapping
	 * @return the friendly url mapping, or <code>null</code> if a friendly url mapping with the primary key could not be found
	 */
	@Override
	public FriendlyURLMapping fetchByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK) {
		return fetchByPrimaryKey((Serializable)friendlyURLMappingPK);
	}

	@Override
	public Map<Serializable, FriendlyURLMapping> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLMapping> map = new HashMap<Serializable, FriendlyURLMapping>();

		for (Serializable primaryKey : primaryKeys) {
			FriendlyURLMapping friendlyURLMapping = fetchByPrimaryKey(primaryKey);

			if (friendlyURLMapping != null) {
				map.put(primaryKey, friendlyURLMapping);
			}
		}

		return map;
	}

	/**
	 * Returns all the friendly url mappings.
	 *
	 * @return the friendly url mappings
	 */
	@Override
	public List<FriendlyURLMapping> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url mappings
	 * @param end the upper bound of the range of friendly url mappings (not inclusive)
	 * @return the range of friendly url mappings
	 */
	@Override
	public List<FriendlyURLMapping> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url mappings
	 * @param end the upper bound of the range of friendly url mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url mappings
	 */
	@Override
	public List<FriendlyURLMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLMapping> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url mappings
	 * @param end the upper bound of the range of friendly url mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url mappings
	 */
	@Override
	public List<FriendlyURLMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLMapping> orderByComparator,
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

		List<FriendlyURLMapping> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLMapping>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLMAPPING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLMAPPING;

				if (pagination) {
					sql = sql.concat(FriendlyURLMappingModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLMapping>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLMapping>)QueryUtil.list(q,
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
	 * Removes all the friendly url mappings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLMapping friendlyURLMapping : findAll()) {
			remove(friendlyURLMapping);
		}
	}

	/**
	 * Returns the number of friendly url mappings.
	 *
	 * @return the number of friendly url mappings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLMAPPING);

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
		return FriendlyURLMappingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url mapping persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLMappingImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_FRIENDLYURLMAPPING = "SELECT friendlyURLMapping FROM FriendlyURLMapping friendlyURLMapping";
	private static final String _SQL_COUNT_FRIENDLYURLMAPPING = "SELECT COUNT(friendlyURLMapping) FROM FriendlyURLMapping friendlyURLMapping";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLMapping.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLMapping exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLMappingPersistenceImpl.class);
}