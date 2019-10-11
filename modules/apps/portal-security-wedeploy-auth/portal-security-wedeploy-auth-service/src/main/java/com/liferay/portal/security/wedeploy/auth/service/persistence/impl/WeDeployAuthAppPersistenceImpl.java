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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchAppException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppImpl;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppModelImpl;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthAppPersistence;
import com.liferay.portal.security.wedeploy.auth.service.persistence.impl.constants.WeDeployAuthPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the we deploy auth app service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @generated
 */
@Component(service = WeDeployAuthAppPersistence.class)
public class WeDeployAuthAppPersistenceImpl
	extends BasePersistenceImpl<WeDeployAuthApp>
	implements WeDeployAuthAppPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WeDeployAuthAppUtil</code> to access the we deploy auth app persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WeDeployAuthAppImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByRU_CI;
	private FinderPath _finderPathCountByRU_CI;

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp findByRU_CI(String redirectURI, String clientId)
		throws NoSuchAppException {

		WeDeployAuthApp weDeployAuthApp = fetchByRU_CI(redirectURI, clientId);

		if (weDeployAuthApp == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("redirectURI=");
			msg.append(redirectURI);

			msg.append(", clientId=");
			msg.append(clientId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAppException(msg.toString());
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByRU_CI(String redirectURI, String clientId) {
		return fetchByRU_CI(redirectURI, clientId, true);
	}

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByRU_CI(
		String redirectURI, String clientId, boolean useFinderCache) {

		redirectURI = Objects.toString(redirectURI, "");
		clientId = Objects.toString(clientId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {redirectURI, clientId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByRU_CI, finderArgs, this);
		}

		if (result instanceof WeDeployAuthApp) {
			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)result;

			if (!Objects.equals(
					redirectURI, weDeployAuthApp.getRedirectURI()) ||
				!Objects.equals(clientId, weDeployAuthApp.getClientId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindRedirectURI = false;

			if (redirectURI.isEmpty()) {
				query.append(_FINDER_COLUMN_RU_CI_REDIRECTURI_3);
			}
			else {
				bindRedirectURI = true;

				query.append(_FINDER_COLUMN_RU_CI_REDIRECTURI_2);
			}

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_RU_CI_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_RU_CI_CLIENTID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindRedirectURI) {
					qPos.add(redirectURI);
				}

				if (bindClientId) {
					qPos.add(clientId);
				}

				List<WeDeployAuthApp> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByRU_CI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									redirectURI, clientId
								};
							}

							_log.warn(
								"WeDeployAuthAppPersistenceImpl.fetchByRU_CI(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WeDeployAuthApp weDeployAuthApp = list.get(0);

					result = weDeployAuthApp;

					cacheResult(weDeployAuthApp);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByRU_CI, finderArgs);
				}

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
	 * Removes the we deploy auth app where redirectURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the we deploy auth app that was removed
	 */
	@Override
	public WeDeployAuthApp removeByRU_CI(String redirectURI, String clientId)
		throws NoSuchAppException {

		WeDeployAuthApp weDeployAuthApp = findByRU_CI(redirectURI, clientId);

		return remove(weDeployAuthApp);
	}

	/**
	 * Returns the number of we deploy auth apps where redirectURI = &#63; and clientId = &#63;.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the number of matching we deploy auth apps
	 */
	@Override
	public int countByRU_CI(String redirectURI, String clientId) {
		redirectURI = Objects.toString(redirectURI, "");
		clientId = Objects.toString(clientId, "");

		FinderPath finderPath = _finderPathCountByRU_CI;

		Object[] finderArgs = new Object[] {redirectURI, clientId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindRedirectURI = false;

			if (redirectURI.isEmpty()) {
				query.append(_FINDER_COLUMN_RU_CI_REDIRECTURI_3);
			}
			else {
				bindRedirectURI = true;

				query.append(_FINDER_COLUMN_RU_CI_REDIRECTURI_2);
			}

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_RU_CI_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_RU_CI_CLIENTID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindRedirectURI) {
					qPos.add(redirectURI);
				}

				if (bindClientId) {
					qPos.add(clientId);
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

	private static final String _FINDER_COLUMN_RU_CI_REDIRECTURI_2 =
		"weDeployAuthApp.redirectURI = ? AND ";

	private static final String _FINDER_COLUMN_RU_CI_REDIRECTURI_3 =
		"(weDeployAuthApp.redirectURI IS NULL OR weDeployAuthApp.redirectURI = '') AND ";

	private static final String _FINDER_COLUMN_RU_CI_CLIENTID_2 =
		"weDeployAuthApp.clientId = ?";

	private static final String _FINDER_COLUMN_RU_CI_CLIENTID_3 =
		"(weDeployAuthApp.clientId IS NULL OR weDeployAuthApp.clientId = '')";

	private FinderPath _finderPathFetchByCI_CS;
	private FinderPath _finderPathCountByCI_CS;

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
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

			msg.append("}");

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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByCI_CS(
		String clientId, String clientSecret, boolean useFinderCache) {

		clientId = Objects.toString(clientId, "");
		clientSecret = Objects.toString(clientSecret, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {clientId, clientSecret};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCI_CS, finderArgs, this);
		}

		if (result instanceof WeDeployAuthApp) {
			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)result;

			if (!Objects.equals(clientId, weDeployAuthApp.getClientId()) ||
				!Objects.equals(
					clientSecret, weDeployAuthApp.getClientSecret())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_2);
			}

			boolean bindClientSecret = false;

			if (clientSecret.isEmpty()) {
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
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCI_CS, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									clientId, clientSecret
								};
							}

							_log.warn(
								"WeDeployAuthAppPersistenceImpl.fetchByCI_CS(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WeDeployAuthApp weDeployAuthApp = list.get(0);

					result = weDeployAuthApp;

					cacheResult(weDeployAuthApp);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByCI_CS, finderArgs);
				}

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
		clientId = Objects.toString(clientId, "");
		clientSecret = Objects.toString(clientSecret, "");

		FinderPath finderPath = _finderPathCountByCI_CS;

		Object[] finderArgs = new Object[] {clientId, clientSecret};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WEDEPLOYAUTHAPP_WHERE);

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_CS_CLIENTID_2);
			}

			boolean bindClientSecret = false;

			if (clientSecret.isEmpty()) {
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

	private static final String _FINDER_COLUMN_CI_CS_CLIENTID_2 =
		"weDeployAuthApp.clientId = ? AND ";

	private static final String _FINDER_COLUMN_CI_CS_CLIENTID_3 =
		"(weDeployAuthApp.clientId IS NULL OR weDeployAuthApp.clientId = '') AND ";

	private static final String _FINDER_COLUMN_CI_CS_CLIENTSECRET_2 =
		"weDeployAuthApp.clientSecret = ?";

	private static final String _FINDER_COLUMN_CI_CS_CLIENTSECRET_3 =
		"(weDeployAuthApp.clientSecret IS NULL OR weDeployAuthApp.clientSecret = '')";

	public WeDeployAuthAppPersistenceImpl() {
		setModelClass(WeDeployAuthApp.class);

		setModelImplClass(WeDeployAuthAppImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the we deploy auth app in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApp the we deploy auth app
	 */
	@Override
	public void cacheResult(WeDeployAuthApp weDeployAuthApp) {
		entityCache.putResult(
			entityCacheEnabled, WeDeployAuthAppImpl.class,
			weDeployAuthApp.getPrimaryKey(), weDeployAuthApp);

		finderCache.putResult(
			_finderPathFetchByRU_CI,
			new Object[] {
				weDeployAuthApp.getRedirectURI(), weDeployAuthApp.getClientId()
			},
			weDeployAuthApp);

		finderCache.putResult(
			_finderPathFetchByCI_CS,
			new Object[] {
				weDeployAuthApp.getClientId(), weDeployAuthApp.getClientSecret()
			},
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
					entityCacheEnabled, WeDeployAuthAppImpl.class,
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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WeDeployAuthApp weDeployAuthApp) {
		entityCache.removeResult(
			entityCacheEnabled, WeDeployAuthAppImpl.class,
			weDeployAuthApp.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WeDeployAuthAppModelImpl)weDeployAuthApp, true);
	}

	@Override
	public void clearCache(List<WeDeployAuthApp> weDeployAuthApps) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WeDeployAuthApp weDeployAuthApp : weDeployAuthApps) {
			entityCache.removeResult(
				entityCacheEnabled, WeDeployAuthAppImpl.class,
				weDeployAuthApp.getPrimaryKey());

			clearUniqueFindersCache(
				(WeDeployAuthAppModelImpl)weDeployAuthApp, true);
		}
	}

	protected void cacheUniqueFindersCache(
		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl) {

		Object[] args = new Object[] {
			weDeployAuthAppModelImpl.getRedirectURI(),
			weDeployAuthAppModelImpl.getClientId()
		};

		finderCache.putResult(
			_finderPathCountByRU_CI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByRU_CI, args, weDeployAuthAppModelImpl, false);

		args = new Object[] {
			weDeployAuthAppModelImpl.getClientId(),
			weDeployAuthAppModelImpl.getClientSecret()
		};

		finderCache.putResult(
			_finderPathCountByCI_CS, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCI_CS, args, weDeployAuthAppModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				weDeployAuthAppModelImpl.getRedirectURI(),
				weDeployAuthAppModelImpl.getClientId()
			};

			finderCache.removeResult(_finderPathCountByRU_CI, args);
			finderCache.removeResult(_finderPathFetchByRU_CI, args);
		}

		if ((weDeployAuthAppModelImpl.getColumnBitmask() &
			 _finderPathFetchByRU_CI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				weDeployAuthAppModelImpl.getOriginalRedirectURI(),
				weDeployAuthAppModelImpl.getOriginalClientId()
			};

			finderCache.removeResult(_finderPathCountByRU_CI, args);
			finderCache.removeResult(_finderPathFetchByRU_CI, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				weDeployAuthAppModelImpl.getClientId(),
				weDeployAuthAppModelImpl.getClientSecret()
			};

			finderCache.removeResult(_finderPathCountByCI_CS, args);
			finderCache.removeResult(_finderPathFetchByCI_CS, args);
		}

		if ((weDeployAuthAppModelImpl.getColumnBitmask() &
			 _finderPathFetchByCI_CS.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				weDeployAuthAppModelImpl.getOriginalClientId(),
				weDeployAuthAppModelImpl.getOriginalClientSecret()
			};

			finderCache.removeResult(_finderPathCountByCI_CS, args);
			finderCache.removeResult(_finderPathFetchByCI_CS, args);
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

		weDeployAuthApp.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)session.get(
				WeDeployAuthAppImpl.class, primaryKey);

			if (weDeployAuthApp == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(weDeployAuthApp)) {
				weDeployAuthApp = (WeDeployAuthApp)session.get(
					WeDeployAuthAppImpl.class,
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
		boolean isNew = weDeployAuthApp.isNew();

		if (!(weDeployAuthApp instanceof WeDeployAuthAppModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(weDeployAuthApp.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					weDeployAuthApp);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in weDeployAuthApp proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WeDeployAuthApp implementation " +
					weDeployAuthApp.getClass());
		}

		WeDeployAuthAppModelImpl weDeployAuthAppModelImpl =
			(WeDeployAuthAppModelImpl)weDeployAuthApp;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (weDeployAuthApp.getCreateDate() == null)) {
			if (serviceContext == null) {
				weDeployAuthApp.setCreateDate(now);
			}
			else {
				weDeployAuthApp.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!weDeployAuthAppModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				weDeployAuthApp.setModifiedDate(now);
			}
			else {
				weDeployAuthApp.setModifiedDate(
					serviceContext.getModifiedDate(now));
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
				weDeployAuthApp = (WeDeployAuthApp)session.merge(
					weDeployAuthApp);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, WeDeployAuthAppImpl.class,
			weDeployAuthApp.getPrimaryKey(), weDeployAuthApp, false);

		clearUniqueFindersCache(weDeployAuthAppModelImpl, false);
		cacheUniqueFindersCache(weDeployAuthAppModelImpl);

		weDeployAuthApp.resetOriginalValues();

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchAppException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return weDeployAuthApp;
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
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
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app, or <code>null</code> if a we deploy auth app with the primary key could not be found
	 */
	@Override
	public WeDeployAuthApp fetchByPrimaryKey(long weDeployAuthAppId) {
		return fetchByPrimaryKey((Serializable)weDeployAuthAppId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthAppModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth apps
	 * @param end the upper bound of the range of we deploy auth apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the we deploy auth apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth apps
	 * @param end the upper bound of the range of we deploy auth apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of we deploy auth apps
	 */
	@Override
	public List<WeDeployAuthApp> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator,
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

		List<WeDeployAuthApp> list = null;

		if (useFinderCache) {
			list = (List<WeDeployAuthApp>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WEDEPLOYAUTHAPP);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WEDEPLOYAUTHAPP;

				sql = sql.concat(WeDeployAuthAppModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<WeDeployAuthApp>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WEDEPLOYAUTHAPP);

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
		return "weDeployAuthAppId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEDEPLOYAUTHAPP;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WeDeployAuthAppModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the we deploy auth app persistence.
	 */
	@Activate
	public void activate() {
		WeDeployAuthAppModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		WeDeployAuthAppModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, WeDeployAuthAppImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, WeDeployAuthAppImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByRU_CI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, WeDeployAuthAppImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByRU_CI",
			new String[] {String.class.getName(), String.class.getName()},
			WeDeployAuthAppModelImpl.REDIRECTURI_COLUMN_BITMASK |
			WeDeployAuthAppModelImpl.CLIENTID_COLUMN_BITMASK);

		_finderPathCountByRU_CI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRU_CI",
			new String[] {String.class.getName(), String.class.getName()});

		_finderPathFetchByCI_CS = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, WeDeployAuthAppImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCI_CS",
			new String[] {String.class.getName(), String.class.getName()},
			WeDeployAuthAppModelImpl.CLIENTID_COLUMN_BITMASK |
			WeDeployAuthAppModelImpl.CLIENTSECRET_COLUMN_BITMASK);

		_finderPathCountByCI_CS = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCI_CS",
			new String[] {String.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(WeDeployAuthAppImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = WeDeployAuthPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp"),
			true);
	}

	@Override
	@Reference(
		target = WeDeployAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WeDeployAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_WEDEPLOYAUTHAPP =
		"SELECT weDeployAuthApp FROM WeDeployAuthApp weDeployAuthApp";

	private static final String _SQL_SELECT_WEDEPLOYAUTHAPP_WHERE =
		"SELECT weDeployAuthApp FROM WeDeployAuthApp weDeployAuthApp WHERE ";

	private static final String _SQL_COUNT_WEDEPLOYAUTHAPP =
		"SELECT COUNT(weDeployAuthApp) FROM WeDeployAuthApp weDeployAuthApp";

	private static final String _SQL_COUNT_WEDEPLOYAUTHAPP_WHERE =
		"SELECT COUNT(weDeployAuthApp) FROM WeDeployAuthApp weDeployAuthApp WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "weDeployAuthApp.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WeDeployAuthApp exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WeDeployAuthApp exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WeDeployAuthAppPersistenceImpl.class);

	static {
		try {
			Class.forName(WeDeployAuthPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}