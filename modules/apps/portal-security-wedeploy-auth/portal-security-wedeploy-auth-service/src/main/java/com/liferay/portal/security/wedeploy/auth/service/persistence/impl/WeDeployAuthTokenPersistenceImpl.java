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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchTokenException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenImpl;
import com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthTokenPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

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
 * The persistence implementation for the we deploy auth token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenPersistence
 * @see com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthTokenUtil
 * @generated
 */
@ProviderType
public class WeDeployAuthTokenPersistenceImpl extends BasePersistenceImpl<WeDeployAuthToken>
	implements WeDeployAuthTokenPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WeDeployAuthTokenUtil} to access the we deploy auth token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WeDeployAuthTokenImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_T_T = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByT_T",
			new String[] { String.class.getName(), Integer.class.getName() },
			WeDeployAuthTokenModelImpl.TOKEN_COLUMN_BITMASK |
			WeDeployAuthTokenModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_T = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_T",
			new String[] { String.class.getName(), Integer.class.getName() });

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or throws a {@link NoSuchTokenException} if it could not be found.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken findByT_T(String token, int type)
		throws NoSuchTokenException {
		WeDeployAuthToken weDeployAuthToken = fetchByT_T(token, type);

		if (weDeployAuthToken == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("token=");
			msg.append(token);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTokenException(msg.toString());
		}

		return weDeployAuthToken;
	}

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByT_T(String token, int type) {
		return fetchByT_T(token, type, true);
	}

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByT_T(String token, int type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { token, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_T_T,
					finderArgs, this);
		}

		if (result instanceof WeDeployAuthToken) {
			WeDeployAuthToken weDeployAuthToken = (WeDeployAuthToken)result;

			if (!Objects.equals(token, weDeployAuthToken.getToken()) ||
					(type != weDeployAuthToken.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WEDEPLOYAUTHTOKEN_WHERE);

			boolean bindToken = false;

			if (token == null) {
				query.append(_FINDER_COLUMN_T_T_TOKEN_1);
			}
			else if (token.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_T_TOKEN_3);
			}
			else {
				bindToken = true;

				query.append(_FINDER_COLUMN_T_T_TOKEN_2);
			}

			query.append(_FINDER_COLUMN_T_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindToken) {
					qPos.add(token);
				}

				qPos.add(type);

				List<WeDeployAuthToken> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_T_T, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"WeDeployAuthTokenPersistenceImpl.fetchByT_T(String, int, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WeDeployAuthToken weDeployAuthToken = list.get(0);

					result = weDeployAuthToken;

					cacheResult(weDeployAuthToken);

					if ((weDeployAuthToken.getToken() == null) ||
							!weDeployAuthToken.getToken().equals(token) ||
							(weDeployAuthToken.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_T_T,
							finderArgs, weDeployAuthToken);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_T_T, finderArgs);

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
			return (WeDeployAuthToken)result;
		}
	}

	/**
	 * Removes the we deploy auth token where token = &#63; and type = &#63; from the database.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	@Override
	public WeDeployAuthToken removeByT_T(String token, int type)
		throws NoSuchTokenException {
		WeDeployAuthToken weDeployAuthToken = findByT_T(token, type);

		return remove(weDeployAuthToken);
	}

	/**
	 * Returns the number of we deploy auth tokens where token = &#63; and type = &#63;.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	@Override
	public int countByT_T(String token, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_T;

		Object[] finderArgs = new Object[] { token, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WEDEPLOYAUTHTOKEN_WHERE);

			boolean bindToken = false;

			if (token == null) {
				query.append(_FINDER_COLUMN_T_T_TOKEN_1);
			}
			else if (token.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_T_TOKEN_3);
			}
			else {
				bindToken = true;

				query.append(_FINDER_COLUMN_T_T_TOKEN_2);
			}

			query.append(_FINDER_COLUMN_T_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindToken) {
					qPos.add(token);
				}

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_T_T_TOKEN_1 = "weDeployAuthToken.token IS NULL AND ";
	private static final String _FINDER_COLUMN_T_T_TOKEN_2 = "weDeployAuthToken.token = ? AND ";
	private static final String _FINDER_COLUMN_T_T_TOKEN_3 = "(weDeployAuthToken.token IS NULL OR weDeployAuthToken.token = '') AND ";
	private static final String _FINDER_COLUMN_T_T_TYPE_2 = "weDeployAuthToken.type = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CI_T_T = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCI_T_T",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			WeDeployAuthTokenModelImpl.CLIENTID_COLUMN_BITMASK |
			WeDeployAuthTokenModelImpl.TOKEN_COLUMN_BITMASK |
			WeDeployAuthTokenModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CI_T_T = new FinderPath(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCI_T_T",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or throws a {@link NoSuchTokenException} if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken findByCI_T_T(String clientId, String token,
		int type) throws NoSuchTokenException {
		WeDeployAuthToken weDeployAuthToken = fetchByCI_T_T(clientId, token,
				type);

		if (weDeployAuthToken == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("clientId=");
			msg.append(clientId);

			msg.append(", token=");
			msg.append(token);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTokenException(msg.toString());
		}

		return weDeployAuthToken;
	}

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByCI_T_T(String clientId, String token,
		int type) {
		return fetchByCI_T_T(clientId, token, type, true);
	}

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByCI_T_T(String clientId, String token,
		int type, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { clientId, token, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CI_T_T,
					finderArgs, this);
		}

		if (result instanceof WeDeployAuthToken) {
			WeDeployAuthToken weDeployAuthToken = (WeDeployAuthToken)result;

			if (!Objects.equals(clientId, weDeployAuthToken.getClientId()) ||
					!Objects.equals(token, weDeployAuthToken.getToken()) ||
					(type != weDeployAuthToken.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_WEDEPLOYAUTHTOKEN_WHERE);

			boolean bindClientId = false;

			if (clientId == null) {
				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_1);
			}
			else if (clientId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_2);
			}

			boolean bindToken = false;

			if (token == null) {
				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_1);
			}
			else if (token.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_3);
			}
			else {
				bindToken = true;

				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_2);
			}

			query.append(_FINDER_COLUMN_CI_T_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClientId) {
					qPos.add(clientId);
				}

				if (bindToken) {
					qPos.add(token);
				}

				qPos.add(type);

				List<WeDeployAuthToken> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CI_T_T,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"WeDeployAuthTokenPersistenceImpl.fetchByCI_T_T(String, String, int, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WeDeployAuthToken weDeployAuthToken = list.get(0);

					result = weDeployAuthToken;

					cacheResult(weDeployAuthToken);

					if ((weDeployAuthToken.getClientId() == null) ||
							!weDeployAuthToken.getClientId().equals(clientId) ||
							(weDeployAuthToken.getToken() == null) ||
							!weDeployAuthToken.getToken().equals(token) ||
							(weDeployAuthToken.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CI_T_T,
							finderArgs, weDeployAuthToken);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_T_T, finderArgs);

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
			return (WeDeployAuthToken)result;
		}
	}

	/**
	 * Removes the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	@Override
	public WeDeployAuthToken removeByCI_T_T(String clientId, String token,
		int type) throws NoSuchTokenException {
		WeDeployAuthToken weDeployAuthToken = findByCI_T_T(clientId, token, type);

		return remove(weDeployAuthToken);
	}

	/**
	 * Returns the number of we deploy auth tokens where clientId = &#63; and token = &#63; and type = &#63;.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	@Override
	public int countByCI_T_T(String clientId, String token, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CI_T_T;

		Object[] finderArgs = new Object[] { clientId, token, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WEDEPLOYAUTHTOKEN_WHERE);

			boolean bindClientId = false;

			if (clientId == null) {
				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_1);
			}
			else if (clientId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_CI_T_T_CLIENTID_2);
			}

			boolean bindToken = false;

			if (token == null) {
				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_1);
			}
			else if (token.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_3);
			}
			else {
				bindToken = true;

				query.append(_FINDER_COLUMN_CI_T_T_TOKEN_2);
			}

			query.append(_FINDER_COLUMN_CI_T_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClientId) {
					qPos.add(clientId);
				}

				if (bindToken) {
					qPos.add(token);
				}

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_CI_T_T_CLIENTID_1 = "weDeployAuthToken.clientId IS NULL AND ";
	private static final String _FINDER_COLUMN_CI_T_T_CLIENTID_2 = "weDeployAuthToken.clientId = ? AND ";
	private static final String _FINDER_COLUMN_CI_T_T_CLIENTID_3 = "(weDeployAuthToken.clientId IS NULL OR weDeployAuthToken.clientId = '') AND ";
	private static final String _FINDER_COLUMN_CI_T_T_TOKEN_1 = "weDeployAuthToken.token IS NULL AND ";
	private static final String _FINDER_COLUMN_CI_T_T_TOKEN_2 = "weDeployAuthToken.token = ? AND ";
	private static final String _FINDER_COLUMN_CI_T_T_TOKEN_3 = "(weDeployAuthToken.token IS NULL OR weDeployAuthToken.token = '') AND ";
	private static final String _FINDER_COLUMN_CI_T_T_TYPE_2 = "weDeployAuthToken.type = ?";

	public WeDeployAuthTokenPersistenceImpl() {
		setModelClass(WeDeployAuthToken.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the we deploy auth token in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 */
	@Override
	public void cacheResult(WeDeployAuthToken weDeployAuthToken) {
		entityCache.putResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class, weDeployAuthToken.getPrimaryKey(),
			weDeployAuthToken);

		finderCache.putResult(FINDER_PATH_FETCH_BY_T_T,
			new Object[] {
				weDeployAuthToken.getToken(), weDeployAuthToken.getType()
			}, weDeployAuthToken);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CI_T_T,
			new Object[] {
				weDeployAuthToken.getClientId(), weDeployAuthToken.getToken(),
				weDeployAuthToken.getType()
			}, weDeployAuthToken);

		weDeployAuthToken.resetOriginalValues();
	}

	/**
	 * Caches the we deploy auth tokens in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthTokens the we deploy auth tokens
	 */
	@Override
	public void cacheResult(List<WeDeployAuthToken> weDeployAuthTokens) {
		for (WeDeployAuthToken weDeployAuthToken : weDeployAuthTokens) {
			if (entityCache.getResult(
						WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
						WeDeployAuthTokenImpl.class,
						weDeployAuthToken.getPrimaryKey()) == null) {
				cacheResult(weDeployAuthToken);
			}
			else {
				weDeployAuthToken.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all we deploy auth tokens.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WeDeployAuthTokenImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the we deploy auth token.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WeDeployAuthToken weDeployAuthToken) {
		entityCache.removeResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class, weDeployAuthToken.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((WeDeployAuthTokenModelImpl)weDeployAuthToken,
			true);
	}

	@Override
	public void clearCache(List<WeDeployAuthToken> weDeployAuthTokens) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WeDeployAuthToken weDeployAuthToken : weDeployAuthTokens) {
			entityCache.removeResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
				WeDeployAuthTokenImpl.class, weDeployAuthToken.getPrimaryKey());

			clearUniqueFindersCache((WeDeployAuthTokenModelImpl)weDeployAuthToken,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		WeDeployAuthTokenModelImpl weDeployAuthTokenModelImpl) {
		Object[] args = new Object[] {
				weDeployAuthTokenModelImpl.getToken(),
				weDeployAuthTokenModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_T_T, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_T_T, args,
			weDeployAuthTokenModelImpl, false);

		args = new Object[] {
				weDeployAuthTokenModelImpl.getClientId(),
				weDeployAuthTokenModelImpl.getToken(),
				weDeployAuthTokenModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CI_T_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CI_T_T, args,
			weDeployAuthTokenModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WeDeployAuthTokenModelImpl weDeployAuthTokenModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					weDeployAuthTokenModelImpl.getToken(),
					weDeployAuthTokenModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_T_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_T_T, args);
		}

		if ((weDeployAuthTokenModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_T_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					weDeployAuthTokenModelImpl.getOriginalToken(),
					weDeployAuthTokenModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_T_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_T_T, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					weDeployAuthTokenModelImpl.getClientId(),
					weDeployAuthTokenModelImpl.getToken(),
					weDeployAuthTokenModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CI_T_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_T_T, args);
		}

		if ((weDeployAuthTokenModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CI_T_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					weDeployAuthTokenModelImpl.getOriginalClientId(),
					weDeployAuthTokenModelImpl.getOriginalToken(),
					weDeployAuthTokenModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CI_T_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CI_T_T, args);
		}
	}

	/**
	 * Creates a new we deploy auth token with the primary key. Does not add the we deploy auth token to the database.
	 *
	 * @param weDeployAuthTokenId the primary key for the new we deploy auth token
	 * @return the new we deploy auth token
	 */
	@Override
	public WeDeployAuthToken create(long weDeployAuthTokenId) {
		WeDeployAuthToken weDeployAuthToken = new WeDeployAuthTokenImpl();

		weDeployAuthToken.setNew(true);
		weDeployAuthToken.setPrimaryKey(weDeployAuthTokenId);

		weDeployAuthToken.setCompanyId(companyProvider.getCompanyId());

		return weDeployAuthToken;
	}

	/**
	 * Removes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token that was removed
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken remove(long weDeployAuthTokenId)
		throws NoSuchTokenException {
		return remove((Serializable)weDeployAuthTokenId);
	}

	/**
	 * Removes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the we deploy auth token
	 * @return the we deploy auth token that was removed
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken remove(Serializable primaryKey)
		throws NoSuchTokenException {
		Session session = null;

		try {
			session = openSession();

			WeDeployAuthToken weDeployAuthToken = (WeDeployAuthToken)session.get(WeDeployAuthTokenImpl.class,
					primaryKey);

			if (weDeployAuthToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(weDeployAuthToken);
		}
		catch (NoSuchTokenException nsee) {
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
	protected WeDeployAuthToken removeImpl(WeDeployAuthToken weDeployAuthToken) {
		weDeployAuthToken = toUnwrappedModel(weDeployAuthToken);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(weDeployAuthToken)) {
				weDeployAuthToken = (WeDeployAuthToken)session.get(WeDeployAuthTokenImpl.class,
						weDeployAuthToken.getPrimaryKeyObj());
			}

			if (weDeployAuthToken != null) {
				session.delete(weDeployAuthToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (weDeployAuthToken != null) {
			clearCache(weDeployAuthToken);
		}

		return weDeployAuthToken;
	}

	@Override
	public WeDeployAuthToken updateImpl(WeDeployAuthToken weDeployAuthToken) {
		weDeployAuthToken = toUnwrappedModel(weDeployAuthToken);

		boolean isNew = weDeployAuthToken.isNew();

		WeDeployAuthTokenModelImpl weDeployAuthTokenModelImpl = (WeDeployAuthTokenModelImpl)weDeployAuthToken;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (weDeployAuthToken.getCreateDate() == null)) {
			if (serviceContext == null) {
				weDeployAuthToken.setCreateDate(now);
			}
			else {
				weDeployAuthToken.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!weDeployAuthTokenModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				weDeployAuthToken.setModifiedDate(now);
			}
			else {
				weDeployAuthToken.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (weDeployAuthToken.isNew()) {
				session.save(weDeployAuthToken);

				weDeployAuthToken.setNew(false);
			}
			else {
				weDeployAuthToken = (WeDeployAuthToken)session.merge(weDeployAuthToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!WeDeployAuthTokenModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			WeDeployAuthTokenImpl.class, weDeployAuthToken.getPrimaryKey(),
			weDeployAuthToken, false);

		clearUniqueFindersCache(weDeployAuthTokenModelImpl, false);
		cacheUniqueFindersCache(weDeployAuthTokenModelImpl);

		weDeployAuthToken.resetOriginalValues();

		return weDeployAuthToken;
	}

	protected WeDeployAuthToken toUnwrappedModel(
		WeDeployAuthToken weDeployAuthToken) {
		if (weDeployAuthToken instanceof WeDeployAuthTokenImpl) {
			return weDeployAuthToken;
		}

		WeDeployAuthTokenImpl weDeployAuthTokenImpl = new WeDeployAuthTokenImpl();

		weDeployAuthTokenImpl.setNew(weDeployAuthToken.isNew());
		weDeployAuthTokenImpl.setPrimaryKey(weDeployAuthToken.getPrimaryKey());

		weDeployAuthTokenImpl.setWeDeployAuthTokenId(weDeployAuthToken.getWeDeployAuthTokenId());
		weDeployAuthTokenImpl.setCompanyId(weDeployAuthToken.getCompanyId());
		weDeployAuthTokenImpl.setUserId(weDeployAuthToken.getUserId());
		weDeployAuthTokenImpl.setUserName(weDeployAuthToken.getUserName());
		weDeployAuthTokenImpl.setCreateDate(weDeployAuthToken.getCreateDate());
		weDeployAuthTokenImpl.setModifiedDate(weDeployAuthToken.getModifiedDate());
		weDeployAuthTokenImpl.setClientId(weDeployAuthToken.getClientId());
		weDeployAuthTokenImpl.setToken(weDeployAuthToken.getToken());
		weDeployAuthTokenImpl.setType(weDeployAuthToken.getType());

		return weDeployAuthTokenImpl;
	}

	/**
	 * Returns the we deploy auth token with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the we deploy auth token
	 * @return the we deploy auth token
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTokenException {
		WeDeployAuthToken weDeployAuthToken = fetchByPrimaryKey(primaryKey);

		if (weDeployAuthToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return weDeployAuthToken;
	}

	/**
	 * Returns the we deploy auth token with the primary key or throws a {@link NoSuchTokenException} if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken findByPrimaryKey(long weDeployAuthTokenId)
		throws NoSuchTokenException {
		return findByPrimaryKey((Serializable)weDeployAuthTokenId);
	}

	/**
	 * Returns the we deploy auth token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the we deploy auth token
	 * @return the we deploy auth token, or <code>null</code> if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
				WeDeployAuthTokenImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		WeDeployAuthToken weDeployAuthToken = (WeDeployAuthToken)serializable;

		if (weDeployAuthToken == null) {
			Session session = null;

			try {
				session = openSession();

				weDeployAuthToken = (WeDeployAuthToken)session.get(WeDeployAuthTokenImpl.class,
						primaryKey);

				if (weDeployAuthToken != null) {
					cacheResult(weDeployAuthToken);
				}
				else {
					entityCache.putResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
						WeDeployAuthTokenImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthTokenImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return weDeployAuthToken;
	}

	/**
	 * Returns the we deploy auth token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token, or <code>null</code> if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public WeDeployAuthToken fetchByPrimaryKey(long weDeployAuthTokenId) {
		return fetchByPrimaryKey((Serializable)weDeployAuthTokenId);
	}

	@Override
	public Map<Serializable, WeDeployAuthToken> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, WeDeployAuthToken> map = new HashMap<Serializable, WeDeployAuthToken>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			WeDeployAuthToken weDeployAuthToken = fetchByPrimaryKey(primaryKey);

			if (weDeployAuthToken != null) {
				map.put(primaryKey, weDeployAuthToken);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthTokenImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (WeDeployAuthToken)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_WEDEPLOYAUTHTOKEN_WHERE_PKS_IN);

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

			for (WeDeployAuthToken weDeployAuthToken : (List<WeDeployAuthToken>)q.list()) {
				map.put(weDeployAuthToken.getPrimaryKeyObj(), weDeployAuthToken);

				cacheResult(weDeployAuthToken);

				uncachedPrimaryKeys.remove(weDeployAuthToken.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(WeDeployAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
					WeDeployAuthTokenImpl.class, primaryKey, nullModel);
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
	 * Returns all the we deploy auth tokens.
	 *
	 * @return the we deploy auth tokens
	 */
	@Override
	public List<WeDeployAuthToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @return the range of we deploy auth tokens
	 */
	@Override
	public List<WeDeployAuthToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of we deploy auth tokens
	 */
	@Override
	public List<WeDeployAuthToken> findAll(int start, int end,
		OrderByComparator<WeDeployAuthToken> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of we deploy auth tokens
	 */
	@Override
	public List<WeDeployAuthToken> findAll(int start, int end,
		OrderByComparator<WeDeployAuthToken> orderByComparator,
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

		List<WeDeployAuthToken> list = null;

		if (retrieveFromCache) {
			list = (List<WeDeployAuthToken>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WEDEPLOYAUTHTOKEN);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WEDEPLOYAUTHTOKEN;

				if (pagination) {
					sql = sql.concat(WeDeployAuthTokenModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WeDeployAuthToken>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WeDeployAuthToken>)QueryUtil.list(q,
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
	 * Removes all the we deploy auth tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WeDeployAuthToken weDeployAuthToken : findAll()) {
			remove(weDeployAuthToken);
		}
	}

	/**
	 * Returns the number of we deploy auth tokens.
	 *
	 * @return the number of we deploy auth tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WEDEPLOYAUTHTOKEN);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WeDeployAuthTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the we deploy auth token persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(WeDeployAuthTokenImpl.class.getName());
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
	private static final String _SQL_SELECT_WEDEPLOYAUTHTOKEN = "SELECT weDeployAuthToken FROM WeDeployAuthToken weDeployAuthToken";
	private static final String _SQL_SELECT_WEDEPLOYAUTHTOKEN_WHERE_PKS_IN = "SELECT weDeployAuthToken FROM WeDeployAuthToken weDeployAuthToken WHERE weDeployAuthTokenId IN (";
	private static final String _SQL_SELECT_WEDEPLOYAUTHTOKEN_WHERE = "SELECT weDeployAuthToken FROM WeDeployAuthToken weDeployAuthToken WHERE ";
	private static final String _SQL_COUNT_WEDEPLOYAUTHTOKEN = "SELECT COUNT(weDeployAuthToken) FROM WeDeployAuthToken weDeployAuthToken";
	private static final String _SQL_COUNT_WEDEPLOYAUTHTOKEN_WHERE = "SELECT COUNT(weDeployAuthToken) FROM WeDeployAuthToken weDeployAuthToken WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "weDeployAuthToken.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WeDeployAuthToken exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WeDeployAuthToken exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(WeDeployAuthTokenPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}