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

package com.liferay.portal.security.wedeploy.auth.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchWeDeployAuthAppException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppImpl;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppModelImpl;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthAppPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the we deploy auth app service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppPersistence
 * @see com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthAppUtil
 * @generated
 */
@ProviderType
public class WeDeployAuthAppPersistenceImpl extends BasePersistenceImpl<WeDeployAuthApp>
	implements WeDeployAuthAppPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WeDeployAuthAppUtil} to access the we deploy auth app persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WeDeployAuthAppImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthAppImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public WeDeployAuthAppPersistenceImpl() {
		setModelClass(WeDeployAuthApp.class);
	}

	/**
	 * Caches the we deploy auth app in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApp the we deploy auth app
	 */
	@Override
	public void cacheResult(WeDeployAuthApp weDeployAuthApp) {
		entityCache.putResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey(),
			weDeployAuthApp);

		weDeployAuthApp.resetOriginalValues();
	}

	/**
	 * Caches the we deploy auth apps in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApps the we deploy auth apps
	 */
	@Override
	public void cacheResult(List<WeDeployAuthApp> weDeployAuthApps) {
		for (WeDeployAuthApp weDeployAuthApp : weDeployAuthApps) {
			if (entityCache.getResult(
						WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
						WeDeployAuthAppImpl.class,
						weDeployAuthApp.getPrimaryKey()) == null) {
				cacheResult(weDeployAuthApp);
			}
			else {
				weDeployAuthApp.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all we deploy auth apps.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WeDeployAuthAppImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the we deploy auth app.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WeDeployAuthApp weDeployAuthApp) {
		entityCache.removeResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<WeDeployAuthApp> weDeployAuthApps) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WeDeployAuthApp weDeployAuthApp : weDeployAuthApps) {
			entityCache.removeResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
				WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey());
		}
	}

	/**
	 * Creates a new we deploy auth app with the primary key. Does not add the we deploy auth app to the database.
	 *
	 * @param weDeployAuthAppId the primary key for the new we deploy auth app
	 * @return the new we deploy auth app
	 */
	@Override
	public WeDeployAuthApp create(long weDeployAuthAppId) {
		WeDeployAuthApp weDeployAuthApp = new WeDeployAuthAppImpl();

		weDeployAuthApp.setNew(true);
		weDeployAuthApp.setPrimaryKey(weDeployAuthAppId);

		weDeployAuthApp.setCompanyId(companyProvider.getCompanyId());

		return weDeployAuthApp;
	}

	/**
	 * Removes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app that was removed
	 * @throws NoSuchWeDeployAuthAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp remove(long weDeployAuthAppId)
		throws NoSuchWeDeployAuthAppException {
		return remove((Serializable)weDeployAuthAppId);
	}

	/**
	 * Removes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the we deploy auth app
	 * @return the we deploy auth app that was removed
	 * @throws NoSuchWeDeployAuthAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp remove(Serializable primaryKey)
		throws NoSuchWeDeployAuthAppException {
		Session session = null;

		try {
			session = openSession();

			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)session.get(WeDeployAuthAppImpl.class,
					primaryKey);

			if (weDeployAuthApp == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWeDeployAuthAppException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(weDeployAuthApp);
		}
		catch (NoSuchWeDeployAuthAppException nsee) {
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
	protected WeDeployAuthApp removeImpl(WeDeployAuthApp weDeployAuthApp) {
		weDeployAuthApp = toUnwrappedModel(weDeployAuthApp);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(weDeployAuthApp)) {
				weDeployAuthApp = (WeDeployAuthApp)session.get(WeDeployAuthAppImpl.class,
						weDeployAuthApp.getPrimaryKeyObj());
			}

			if (weDeployAuthApp != null) {
				session.delete(weDeployAuthApp);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (weDeployAuthApp != null) {
			clearCache(weDeployAuthApp);
		}

		return weDeployAuthApp;
	}

	@Override
	public WeDeployAuthApp updateImpl(WeDeployAuthApp weDeployAuthApp) {
		weDeployAuthApp = toUnwrappedModel(weDeployAuthApp);

		boolean isNew = weDeployAuthApp.isNew();

		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl = (WeDeployAuthAppModelImpl)weDeployAuthApp;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (weDeployAuthApp.getCreateDate() == null)) {
			if (serviceContext == null) {
				weDeployAuthApp.setCreateDate(now);
			}
			else {
				weDeployAuthApp.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!weDeployAuthAppModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				weDeployAuthApp.setModifiedDate(now);
			}
			else {
				weDeployAuthApp.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (weDeployAuthApp.isNew()) {
				session.save(weDeployAuthApp);

				weDeployAuthApp.setNew(false);
			}
			else {
				weDeployAuthApp = (WeDeployAuthApp)session.merge(weDeployAuthApp);
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

		entityCache.putResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey(),
			weDeployAuthApp, false);

		weDeployAuthApp.resetOriginalValues();

		return weDeployAuthApp;
	}

	protected WeDeployAuthApp toUnwrappedModel(WeDeployAuthApp weDeployAuthApp) {
		if (weDeployAuthApp instanceof WeDeployAuthAppImpl) {
			return weDeployAuthApp;
		}

		WeDeployAuthAppImpl weDeployAuthAppImpl = new WeDeployAuthAppImpl();

		weDeployAuthAppImpl.setNew(weDeployAuthApp.isNew());
		weDeployAuthAppImpl.setPrimaryKey(weDeployAuthApp.getPrimaryKey());

		weDeployAuthAppImpl.setWeDeployAuthAppId(weDeployAuthApp.getWeDeployAuthAppId());
		weDeployAuthAppImpl.setCompanyId(weDeployAuthApp.getCompanyId());
		weDeployAuthAppImpl.setUserId(weDeployAuthApp.getUserId());
		weDeployAuthAppImpl.setUserName(weDeployAuthApp.getUserName());
		weDeployAuthAppImpl.setCreateDate(weDeployAuthApp.getCreateDate());
		weDeployAuthAppImpl.setModifiedDate(weDeployAuthApp.getModifiedDate());
		weDeployAuthAppImpl.setName(weDeployAuthApp.getName());
		weDeployAuthAppImpl.setClientId(weDeployAuthApp.getClientId());
		weDeployAuthAppImpl.setClientSecret(weDeployAuthApp.getClientSecret());

		return weDeployAuthAppImpl;
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the we deploy auth app
	 * @return the we deploy auth app
	 * @throws NoSuchWeDeployAuthAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWeDeployAuthAppException {
		WeDeployAuthApp weDeployAuthApp = fetchByPrimaryKey(primaryKey);

		if (weDeployAuthApp == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWeDeployAuthAppException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a {@link NoSuchWeDeployAuthAppException} if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app
	 * @throws NoSuchWeDeployAuthAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp findByPrimaryKey(long weDeployAuthAppId)
		throws NoSuchWeDeployAuthAppException {
		return findByPrimaryKey((Serializable)weDeployAuthAppId);
	}

	/**
	 * Returns the we deploy auth app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the we deploy auth app
	 * @return the we deploy auth app, or <code>null</code> if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
				WeDeployAuthAppImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)serializable;

		if (weDeployAuthApp == null) {
			Session session = null;

			try {
				session = openSession();

				weDeployAuthApp = (WeDeployAuthApp)session.get(WeDeployAuthAppImpl.class,
						primaryKey);

				if (weDeployAuthApp != null) {
					cacheResult(weDeployAuthApp);
				}
				else {
					entityCache.putResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
						WeDeployAuthAppImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthAppImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app, or <code>null</code> if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByPrimaryKey(long weDeployAuthAppId) {
		return fetchByPrimaryKey((Serializable)weDeployAuthAppId);
	}

	@Override
	public Map<Serializable, WeDeployAuthApp> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, WeDeployAuthApp> map = new HashMap<Serializable, WeDeployAuthApp>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			WeDeployAuthApp weDeployAuthApp = fetchByPrimaryKey(primaryKey);

			if (weDeployAuthApp != null) {
				map.put(primaryKey, weDeployAuthApp);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthAppImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (WeDeployAuthApp)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_WEDEPLOYAUTHAPP_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (WeDeployAuthApp weDeployAuthApp : (List<WeDeployAuthApp>)q.list()) {
				map.put(weDeployAuthApp.getPrimaryKeyObj(), weDeployAuthApp);

				cacheResult(weDeployAuthApp);

				uncachedPrimaryKeys.remove(weDeployAuthApp.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthAppImpl.class, primaryKey, nullModel);
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
	 * Returns all the we deploy auth apps.
	 *
	 * @return the we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the we deploy auth apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth apps
	 * @param end the upper bound of the range of we deploy auth apps (not inclusive)
	 * @return the range of we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the we deploy auth apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth apps
	 * @param end the upper bound of the range of we deploy auth apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll(int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the we deploy auth apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth apps
	 * @param end the upper bound of the range of we deploy auth apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll(int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator,
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

		List<WeDeployAuthApp> list = null;

		if (retrieveFromCache) {
			list = (List<WeDeployAuthApp>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WEDEPLOYAUTHAPP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WEDEPLOYAUTHAPP;

				if (pagination) {
					sql = sql.concat(WeDeployAuthAppModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WeDeployAuthApp>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WeDeployAuthApp>)QueryUtil.list(q,
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
	 * Removes all the we deploy auth apps from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WeDeployAuthApp weDeployAuthApp : findAll()) {
			remove(weDeployAuthApp);
		}
	}

	/**
	 * Returns the number of we deploy auth apps.
	 *
	 * @return the number of we deploy auth apps
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WEDEPLOYAUTHAPP);

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
		return WeDeployAuthAppModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the we deploy auth app persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(WeDeployAuthAppImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_WEDEPLOYAUTHAPP = "SELECT weDeployAuthApp FROM WeDeployAuthApp weDeployAuthApp";
	private static final String _SQL_SELECT_WEDEPLOYAUTHAPP_WHERE_PKS_IN = "SELECT weDeployAuthApp FROM WeDeployAuthApp weDeployAuthApp WHERE weDeployAuthAppId IN (";
	private static final String _SQL_COUNT_WEDEPLOYAUTHAPP = "SELECT COUNT(weDeployAuthApp) FROM WeDeployAuthApp weDeployAuthApp";
	private static final String _ORDER_BY_ENTITY_ALIAS = "weDeployAuthApp.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WeDeployAuthApp exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(WeDeployAuthAppPersistenceImpl.class);
}