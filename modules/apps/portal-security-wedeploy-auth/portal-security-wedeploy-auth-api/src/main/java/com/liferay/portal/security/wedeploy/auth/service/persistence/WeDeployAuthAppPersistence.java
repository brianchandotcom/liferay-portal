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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchAppException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the we deploy auth app service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppUtil
 * @generated
 */
@ProviderType
public interface WeDeployAuthAppPersistence
	extends BasePersistence<WeDeployAuthApp> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WeDeployAuthAppUtil} to access the we deploy auth app persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp findByRU_CI(String redirectURI, String clientId)
		throws NoSuchAppException;

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp fetchByRU_CI(String redirectURI, String clientId);

	/**
	 * Returns the we deploy auth app where redirectURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp fetchByRU_CI(
		String redirectURI, String clientId, boolean useFinderCache);

	/**
	 * Removes the we deploy auth app where redirectURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the we deploy auth app that was removed
	 */
	public WeDeployAuthApp removeByRU_CI(String redirectURI, String clientId)
		throws NoSuchAppException;

	/**
	 * Returns the number of we deploy auth apps where redirectURI = &#63; and clientId = &#63;.
	 *
	 * @param redirectURI the redirect uri
	 * @param clientId the client ID
	 * @return the number of matching we deploy auth apps
	 */
	public int countByRU_CI(String redirectURI, String clientId);

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app
	 * @throws NoSuchAppException if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp findByCI_CS(String clientId, String clientSecret)
		throws NoSuchAppException;

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp fetchByCI_CS(String clientId, String clientSecret);

	/**
	 * Returns the we deploy auth app where clientId = &#63; and clientSecret = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth app, or <code>null</code> if a matching we deploy auth app could not be found
	 */
	public WeDeployAuthApp fetchByCI_CS(
		String clientId, String clientSecret, boolean useFinderCache);

	/**
	 * Removes the we deploy auth app where clientId = &#63; and clientSecret = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the we deploy auth app that was removed
	 */
	public WeDeployAuthApp removeByCI_CS(String clientId, String clientSecret)
		throws NoSuchAppException;

	/**
	 * Returns the number of we deploy auth apps where clientId = &#63; and clientSecret = &#63;.
	 *
	 * @param clientId the client ID
	 * @param clientSecret the client secret
	 * @return the number of matching we deploy auth apps
	 */
	public int countByCI_CS(String clientId, String clientSecret);

	/**
	 * Caches the we deploy auth app in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApp the we deploy auth app
	 */
	public void cacheResult(WeDeployAuthApp weDeployAuthApp);

	/**
	 * Caches the we deploy auth apps in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthApps the we deploy auth apps
	 */
	public void cacheResult(java.util.List<WeDeployAuthApp> weDeployAuthApps);

	/**
	 * Creates a new we deploy auth app with the primary key. Does not add the we deploy auth app to the database.
	 *
	 * @param weDeployAuthAppId the primary key for the new we deploy auth app
	 * @return the new we deploy auth app
	 */
	public WeDeployAuthApp create(long weDeployAuthAppId);

	/**
	 * Removes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app that was removed
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	public WeDeployAuthApp remove(long weDeployAuthAppId)
		throws NoSuchAppException;

	public WeDeployAuthApp updateImpl(WeDeployAuthApp weDeployAuthApp);

	/**
	 * Returns the we deploy auth app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app
	 * @throws NoSuchAppException if a we deploy auth app with the primary key could not be found
	 */
	public WeDeployAuthApp findByPrimaryKey(long weDeployAuthAppId)
		throws NoSuchAppException;

	/**
	 * Returns the we deploy auth app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthAppId the primary key of the we deploy auth app
	 * @return the we deploy auth app, or <code>null</code> if a we deploy auth app with the primary key could not be found
	 */
	public WeDeployAuthApp fetchByPrimaryKey(long weDeployAuthAppId);

	/**
	 * Returns all the we deploy auth apps.
	 *
	 * @return the we deploy auth apps
	 */
	public java.util.List<WeDeployAuthApp> findAll();

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
	public java.util.List<WeDeployAuthApp> findAll(int start, int end);

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
	public java.util.List<WeDeployAuthApp> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WeDeployAuthApp>
			orderByComparator);

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
	public java.util.List<WeDeployAuthApp> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WeDeployAuthApp>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the we deploy auth apps from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of we deploy auth apps.
	 *
	 * @return the number of we deploy auth apps
	 */
	public int countAll();

}