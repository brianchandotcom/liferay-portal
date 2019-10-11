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

package com.liferay.portal.security.wedeploy.auth.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the we deploy auth token service. This utility wraps <code>com.liferay.portal.security.wedeploy.auth.service.persistence.impl.WeDeployAuthTokenPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenPersistence
 * @generated
 */
public class WeDeployAuthTokenUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(WeDeployAuthToken weDeployAuthToken) {
		getPersistence().clearCache(weDeployAuthToken);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, WeDeployAuthToken> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WeDeployAuthToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WeDeployAuthToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WeDeployAuthToken> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WeDeployAuthToken> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WeDeployAuthToken update(
		WeDeployAuthToken weDeployAuthToken) {

		return getPersistence().update(weDeployAuthToken);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WeDeployAuthToken update(
		WeDeployAuthToken weDeployAuthToken, ServiceContext serviceContext) {

		return getPersistence().update(weDeployAuthToken, serviceContext);
	}

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken findByT_T(String token, int type)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().findByT_T(token, type);
	}

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken fetchByT_T(String token, int type) {
		return getPersistence().fetchByT_T(token, type);
	}

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken fetchByT_T(
		String token, int type, boolean useFinderCache) {

		return getPersistence().fetchByT_T(token, type, useFinderCache);
	}

	/**
	 * Removes the we deploy auth token where token = &#63; and type = &#63; from the database.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	public static WeDeployAuthToken removeByT_T(String token, int type)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().removeByT_T(token, type);
	}

	/**
	 * Returns the number of we deploy auth tokens where token = &#63; and type = &#63;.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	public static int countByT_T(String token, int type) {
		return getPersistence().countByT_T(token, type);
	}

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken findByCI_T_T(
			String clientId, String token, int type)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().findByCI_T_T(clientId, token, type);
	}

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken fetchByCI_T_T(
		String clientId, String token, int type) {

		return getPersistence().fetchByCI_T_T(clientId, token, type);
	}

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public static WeDeployAuthToken fetchByCI_T_T(
		String clientId, String token, int type, boolean useFinderCache) {

		return getPersistence().fetchByCI_T_T(
			clientId, token, type, useFinderCache);
	}

	/**
	 * Removes the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	public static WeDeployAuthToken removeByCI_T_T(
			String clientId, String token, int type)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().removeByCI_T_T(clientId, token, type);
	}

	/**
	 * Returns the number of we deploy auth tokens where clientId = &#63; and token = &#63; and type = &#63;.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	public static int countByCI_T_T(String clientId, String token, int type) {
		return getPersistence().countByCI_T_T(clientId, token, type);
	}

	/**
	 * Caches the we deploy auth token in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 */
	public static void cacheResult(WeDeployAuthToken weDeployAuthToken) {
		getPersistence().cacheResult(weDeployAuthToken);
	}

	/**
	 * Caches the we deploy auth tokens in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthTokens the we deploy auth tokens
	 */
	public static void cacheResult(List<WeDeployAuthToken> weDeployAuthTokens) {
		getPersistence().cacheResult(weDeployAuthTokens);
	}

	/**
	 * Creates a new we deploy auth token with the primary key. Does not add the we deploy auth token to the database.
	 *
	 * @param weDeployAuthTokenId the primary key for the new we deploy auth token
	 * @return the new we deploy auth token
	 */
	public static WeDeployAuthToken create(long weDeployAuthTokenId) {
		return getPersistence().create(weDeployAuthTokenId);
	}

	/**
	 * Removes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token that was removed
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	public static WeDeployAuthToken remove(long weDeployAuthTokenId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().remove(weDeployAuthTokenId);
	}

	public static WeDeployAuthToken updateImpl(
		WeDeployAuthToken weDeployAuthToken) {

		return getPersistence().updateImpl(weDeployAuthToken);
	}

	/**
	 * Returns the we deploy auth token with the primary key or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	public static WeDeployAuthToken findByPrimaryKey(long weDeployAuthTokenId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchTokenException {

		return getPersistence().findByPrimaryKey(weDeployAuthTokenId);
	}

	/**
	 * Returns the we deploy auth token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token, or <code>null</code> if a we deploy auth token with the primary key could not be found
	 */
	public static WeDeployAuthToken fetchByPrimaryKey(
		long weDeployAuthTokenId) {

		return getPersistence().fetchByPrimaryKey(weDeployAuthTokenId);
	}

	/**
	 * Returns all the we deploy auth tokens.
	 *
	 * @return the we deploy auth tokens
	 */
	public static List<WeDeployAuthToken> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @return the range of we deploy auth tokens
	 */
	public static List<WeDeployAuthToken> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of we deploy auth tokens
	 */
	public static List<WeDeployAuthToken> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthToken> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of we deploy auth tokens
	 */
	public static List<WeDeployAuthToken> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthToken> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the we deploy auth tokens from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of we deploy auth tokens.
	 *
	 * @return the number of we deploy auth tokens
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WeDeployAuthTokenPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WeDeployAuthTokenPersistence, WeDeployAuthTokenPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WeDeployAuthTokenPersistence.class);

		ServiceTracker
			<WeDeployAuthTokenPersistence, WeDeployAuthTokenPersistence>
				serviceTracker =
					new ServiceTracker
						<WeDeployAuthTokenPersistence,
						 WeDeployAuthTokenPersistence>(
							 bundle.getBundleContext(),
							 WeDeployAuthTokenPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}