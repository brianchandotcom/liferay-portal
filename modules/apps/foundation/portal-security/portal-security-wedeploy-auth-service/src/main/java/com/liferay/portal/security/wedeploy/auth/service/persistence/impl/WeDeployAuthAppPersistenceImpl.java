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
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchAppException;
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
import java.util.Objects;
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
	public static final FinderPath FINDER_PATH_FETCH_BY_CI_CS = new FinderPath(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCI_CS",
			new String[] { String.class.getName(), String.class.getName() },
			WeDeployAuthAppModelImpl.CLIENTID_COLUMN_BITMASK |
			WeDeployAuthAppModelImpl.CLIENTSECRET_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CI_CS = new FinderPath(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCI_CS",
			new String[] { String.class.getName(), String.class.getName() });

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or throws a {@link NoSuchAppException} if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp findByCI_CS(String clientId, String clientSecret)
		throws NoSuchAppException {
		WeDeployAuthApp weDeployAuthApp = fetchByCI_CS(clientId, clientSecret);

		if (weDeployAuthApp == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("clientId=");
			msg.append(clientId);

			msg.append(", clientSecret=");
			msg.append(clientSecret);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAppException(msg.toString());
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByCI_CS(String clientId, String clientSecret) {
		return fetchByCI_CS(clientId, clientSecret, true);
	}

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByCI_CS(String clientId, String clientSecret,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { clientId, clientSecret };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CI_CS,
					finderArgs, this);
		}

		if (result instanceof WeDeployAuthApp) {
			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)result;

			if (!Objects.equals(clientId, weDeployAuthApp.getClientId()) ||
					!Objects.equals(clientSecret,
						weDeployAuthApp.getClientSecret())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindClientId = false;

			if (clientId == null) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_1);
			}
			else if (clientId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_2);
			}

			boolean bindClientSecret = false;

			if (clientSecret == null) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_1);
			}
			else if (clientSecret.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_3);
			}
			else {
				bindClientSecret = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClientId) {
					qPos.add(clientId);
				}

				if (bindClientSecret) {
					qPos.add(clientSecret);
				}

				List<WeDeployAuthApp> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CI_CS,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"WeDeployAuthAppPersistenceImpl.fetchByCI_CS(String, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WeDeployAuthApp weDeployAuthApp = list.get(0);

					result = weDeployAuthApp;

					cacheResult(weDeployAuthApp);

					if ((weDeployAuthApp.getClientId() == null) ||
							!weDeployAuthApp.getClientId().equals(clientId) ||
							(weDeployAuthApp.getClientSecret() == null) ||
							!weDeployAuthApp.getClientSecret()
												.equals(clientSecret)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CI_CS,
							finderArgs, weDeployAuthApp);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_CS, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (WeDeployAuthApp)result;
		}
	}

	/**
	 * Removes the we deploy auth app where clientId = &#63; and clientSecret = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the we deploy auth app that was removed
	 */
	@Override
	public WeDeployAuthApp removeByCI_CS(String clientId, String clientSecret)
		throws NoSuchAppException {
		WeDeployAuthApp weDeployAuthApp = findByCI_CS(clientId, clientSecret);

		return remove(weDeployAuthApp);
	}

	/**
	 * Returns the number of we deploy auth apps where clientId = &#63; and clientSecret = &#63;.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the number of matching we deploy auth apps
	 */
	@Override
	public int countByCI_CS(String clientId, String clientSecret) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CI_CS;

		Object[] finderArgs = new Object[] { clientId, clientSecret };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindClientId = false;

			if (clientId == null) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_1);
			}
			else if (clientId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_2);
			}

			boolean bindClientSecret = false;

			if (clientSecret == null) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_1);
			}
			else if (clientSecret.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_3);
			}
			else {
				bindClientSecret = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTSECRET_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClientId) {
					qPos.add(clientId);
				}

				if (bindClientSecret) {
					qPos.add(clientSecret);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CI_CS_CLIENTID_1 = "weDeployAuthApp.clientId IS NULL AND ";
	private static final String _FINDER_COLUMN_CI_CS_CLIENTID_2 = "weDeployAuthApp.clientId = ? AND ";
	private static final String _FINDER_COLUMN_CI_CS_CLIENTID_3 = "(weDeployAuthApp.clientId IS NULL OR weDeployAuthApp.clientId = '') AND ";
	private static final String _FINDER_COLUMN_CI_CS_CLIENTSECRET_1 = "weDeployAuthApp.clientSecret IS NULL";
	private static final String _FINDER_COLUMN_CI_CS_CLIENTSECRET_2 = "weDeployAuthApp.clientSecret = ?";
	private static final String _FINDER_COLUMN_CI_CS_CLIENTSECRET_3 = "(weDeployAuthApp.clientSecret IS NULL OR weDeployAuthApp.clientSecret = '')";

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

		finderCache.putResult(FINDER_PATH_FETCH_BY_CI_CS,
			new Object[] {
				weDeployAuthApp.getClientId(), weDeployAuthApp.getClientSecret()
			}, weDeployAuthApp);

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

		clearUniqueFindersCache((WeDeployAuthAppModelImpl)weDeployAuthApp, true);
	}

	@Override
	public void clearCache(List<WeDeployAuthApp> weDeployAuthApps) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WeDeployAuthApp weDeployAuthApp : weDeployAuthApps) {
			entityCache.removeResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
				WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey());

			clearUniqueFindersCache((WeDeployAuthAppModelImpl)weDeployAuthApp,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl) {
		Object[] args = new Object[] {
				weDeployAuthAppModelImpl.getClientId(),
				weDeployAuthAppModelImpl.getClientSecret()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CI_CS, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CI_CS, args,
			weDeployAuthAppModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					weDeployAuthAppModelImpl.getClientId(),
					weDeployAuthAppModelImpl.getClientSecret()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CI_CS, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_CS, args);
		}

		if ((weDeployAuthAppModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CI_CS.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					weDeployAuthAppModelImpl.getOriginalClientId(),
					weDeployAuthAppModelImpl.getOriginalClientSecret()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CI_CS, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_CS, args);
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
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp remove(long weDeployAuthAppId)
		throws NoSuchAppException {
		return remove((Serializable)weDeployAuthAppId);
	}

	/**
	 * Removes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the we deploy auth app
	 * @return the we deploy auth app that was removed
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp remove(Serializable primaryKey)
		throws NoSuchAppException {
		Session session = null;

		try {
			session = openSession();

			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)session.get(WeDeployAuthAppImpl.class,
					primaryKey);

			if (weDeployAuthApp == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(weDeployAuthApp);
		}
		catch (NoSuchAppException nsee) {
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

		if (!WeDeployAuthAppModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(WeDeployAuthAppModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthAppImpl.class, weDeployAuthApp.getPrimaryKey(),
			weDeployAuthApp, false);

		clearUniqueFindersCache(weDeployAuthAppModelImpl, false);
		cacheUniqueFindersCache(weDeployAuthAppModelImpl);

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
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppException {
		WeDeployAuthApp weDeployAuthApp = fetchByPrimaryKey(primaryKey);

		if (weDeployAuthApp == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a {@link NoSuchAppException} if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp findByPrimaryKey(long weDeployAuthAppId)
		throws NoSuchAppException {
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
	private static final String _SQL_SELECT_WEDEPLOYAUTHAPP_WHERE = "SELECT weDeployAuthApp FROM WeDeployAuthApp weDeployAuthApp WHERE ";
	private static final String _SQL_COUNT_WEDEPLOYAUTHAPP = "SELECT COUNT(weDeployAuthApp) FROM WeDeployAuthApp weDeployAuthApp";
	private static final String _SQL_COUNT_WEDEPLOYAUTHAPP_WHERE = "SELECT COUNT(weDeployAuthApp) FROM WeDeployAuthApp weDeployAuthApp WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "weDeployAuthApp.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WeDeployAuthApp exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WeDeployAuthApp exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(WeDeployAuthAppPersistenceImpl.class);
}