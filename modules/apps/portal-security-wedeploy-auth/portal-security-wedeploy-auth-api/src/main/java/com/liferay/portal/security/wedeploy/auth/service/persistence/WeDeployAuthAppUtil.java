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
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the we deploy auth app service. This utility wraps <code>com.liferay.portal.security.wedeploy.auth.service.persistence.impl.WeDeployAuthAppPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppPersistence
 * @generated
 */
public class WeDeployAuthAppUtil {

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
	public static void clearCache(WeDeployAuthApp weDeployAuthApp) {
		getPersistence().clearCache(weDeployAuthApp);
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
	public static Map<Serializable, WeDeployAuthApp> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WeDeployAuthApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WeDeployAuthApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WeDeployAuthApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WeDeployAuthApp update(WeDeployAuthApp weDeployAuthApp) {
		return getPersistence().update(weDeployAuthApp);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WeDeployAuthApp update(
		WeDeployAuthApp weDeployAuthApp, ServiceContext serviceContext) {

		return getPersistence().update(weDeployAuthApp, serviceContext);
	}

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp findByRU_CI(
			String redirectURI, String clientId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().findByRU_CI(redirectURI, clientId);
	}

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp fetchByRU_CI(
		String redirectURI, String clientId) {

		return getPersistence().fetchByRU_CI(redirectURI, clientId);
	}

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp fetchByRU_CI(
		String redirectURI, String clientId, boolean useFinderCache) {

		return getPersistence().fetchByRU_CI(
			redirectURI, clientId, useFinderCache);
	}

	/**
	 * Removes the we deploy auth app where redirectURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the we deploy auth app that was removed
	 */
	public static WeDeployAuthApp removeByRU_CI(
			String redirectURI, String clientId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().removeByRU_CI(redirectURI, clientId);
	}

	/**
	 * Returns the number of we deploy auth apps where redirectURI = &#63; and clientId = &#63;.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the number of matching we deploy auth apps
	 */
	public static int countByRU_CI(String redirectURI, String clientId) {
		return getPersistence().countByRU_CI(redirectURI, clientId);
	}

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp findByCI_CS(
			String clientId, String clientSecret)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().findByCI_CS(clientId, clientSecret);
	}

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp fetchByCI_CS(
		String clientId, String clientSecret) {

		return getPersistence().fetchByCI_CS(clientId, clientSecret);
	}

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public static WeDeployAuthApp fetchByCI_CS(
		String clientId, String clientSecret, boolean useFinderCache) {

		return getPersistence().fetchByCI_CS(
			clientId, clientSecret, useFinderCache);
	}

	/**
	 * Removes the we deploy auth app where clientId = &#63; and clientSecret = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the we deploy auth app that was removed
	 */
	public static WeDeployAuthApp removeByCI_CS(
			String clientId, String clientSecret)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().removeByCI_CS(clientId, clientSecret);
	}

	/**
	 * Returns the number of we deploy auth apps where clientId = &#63; and clientSecret = &#63;.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the number of matching we deploy auth apps
	 */
	public static int countByCI_CS(String clientId, String clientSecret) {
		return getPersistence().countByCI_CS(clientId, clientSecret);
	}

	/**
	 * Caches the we deploy auth app in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApp the we deploy auth app
	 */
	public static void cacheResult(WeDeployAuthApp weDeployAuthApp) {
		getPersistence().cacheResult(weDeployAuthApp);
	}

	/**
	 * Caches the we deploy auth apps in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApps the we deploy auth apps
	 */
	public static void cacheResult(List<WeDeployAuthApp> weDeployAuthApps) {
		getPersistence().cacheResult(weDeployAuthApps);
	}

	/**
	 * Creates a new we deploy auth app with the primary key. Does not add the we deploy auth app to the database.
	 *
	 * @param weDeployAuthAppId the primary key for the new we deploy auth app
	 * @return the new we deploy auth app
	 */
	public static WeDeployAuthApp create(long weDeployAuthAppId) {
		return getPersistence().create(weDeployAuthAppId);
	}

	/**
	 * Removes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app that was removed
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	public static WeDeployAuthApp remove(long weDeployAuthAppId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().remove(weDeployAuthAppId);
	}

	public static WeDeployAuthApp updateImpl(WeDeployAuthApp weDeployAuthApp) {
		return getPersistence().updateImpl(weDeployAuthApp);
	}

	/**
	 * Returns the we deploy auth app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	public static WeDeployAuthApp findByPrimaryKey(long weDeployAuthAppId)
		throws com.liferay.portal.security.wedeploy.auth.exception.
			NoSuchAppException {

		return getPersistence().findByPrimaryKey(weDeployAuthAppId);
	}

	/**
	 * Returns the we deploy auth app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app, or <code>null</code> if a we deploy auth app with the primary key could not be found
	 */
	public static WeDeployAuthApp fetchByPrimaryKey(long weDeployAuthAppId) {
		return getPersistence().fetchByPrimaryKey(weDeployAuthAppId);
	}

	/**
	 * Returns all the we deploy auth apps.
	 *
	 * @return the we deploy auth apps
	 */
	public static List<WeDeployAuthApp> findAll() {
		return getPersistence().findAll();
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
	public static List<WeDeployAuthApp> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<WeDeployAuthApp> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<WeDeployAuthApp> findAll(
		int start, int end,
		OrderByComparator<WeDeployAuthApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the we deploy auth apps from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of we deploy auth apps.
	 *
	 * @return the number of we deploy auth apps
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WeDeployAuthAppPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WeDeployAuthAppPersistence, WeDeployAuthAppPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WeDeployAuthAppPersistence.class);

		ServiceTracker<WeDeployAuthAppPersistence, WeDeployAuthAppPersistence>
			serviceTracker =
				new ServiceTracker
					<WeDeployAuthAppPersistence, WeDeployAuthAppPersistence>(
						bundle.getBundleContext(),
						WeDeployAuthAppPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}