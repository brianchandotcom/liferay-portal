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
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchTokenException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the we deploy auth token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenUtil
 * @generated
 */
@ProviderType
public interface WeDeployAuthTokenPersistence
	extends BasePersistence<WeDeployAuthToken> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WeDeployAuthTokenUtil} to access the we deploy auth token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken findByT_T(String token, int type)
		throws NoSuchTokenException;

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken fetchByT_T(String token, int type);

	/**
	 * Returns the we deploy auth token where token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken fetchByT_T(
		String token, int type, boolean useFinderCache);

	/**
	 * Removes the we deploy auth token where token = &#63; and type = &#63; from the database.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	public WeDeployAuthToken removeByT_T(String token, int type)
		throws NoSuchTokenException;

	/**
	 * Returns the number of we deploy auth tokens where token = &#63; and type = &#63;.
	 *
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	public int countByT_T(String token, int type);

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token
	 * @throws NoSuchTokenException if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken findByCI_T_T(
			String clientId, String token, int type)
		throws NoSuchTokenException;

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken fetchByCI_T_T(
		String clientId, String token, int type);

	/**
	 * Returns the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching we deploy auth token, or <code>null</code> if a matching we deploy auth token could not be found
	 */
	public WeDeployAuthToken fetchByCI_T_T(
		String clientId, String token, int type, boolean useFinderCache);

	/**
	 * Removes the we deploy auth token where clientId = &#63; and token = &#63; and type = &#63; from the database.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the we deploy auth token that was removed
	 */
	public WeDeployAuthToken removeByCI_T_T(
			String clientId, String token, int type)
		throws NoSuchTokenException;

	/**
	 * Returns the number of we deploy auth tokens where clientId = &#63; and token = &#63; and type = &#63;.
	 *
	 * @param clientId the client ID
	 * @param token the token
	 * @param type the type
	 * @return the number of matching we deploy auth tokens
	 */
	public int countByCI_T_T(String clientId, String token, int type);

	/**
	 * Caches the we deploy auth token in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 */
	public void cacheResult(WeDeployAuthToken weDeployAuthToken);

	/**
	 * Caches the we deploy auth tokens in the entity cache if it is enabled.
	 *
	 * @param weDeployAuthTokens the we deploy auth tokens
	 */
	public void cacheResult(
		java.util.List<WeDeployAuthToken> weDeployAuthTokens);

	/**
	 * Creates a new we deploy auth token with the primary key. Does not add the we deploy auth token to the database.
	 *
	 * @param weDeployAuthTokenId the primary key for the new we deploy auth token
	 * @return the new we deploy auth token
	 */
	public WeDeployAuthToken create(long weDeployAuthTokenId);

	/**
	 * Removes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token that was removed
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	public WeDeployAuthToken remove(long weDeployAuthTokenId)
		throws NoSuchTokenException;

	public WeDeployAuthToken updateImpl(WeDeployAuthToken weDeployAuthToken);

	/**
	 * Returns the we deploy auth token with the primary key or throws a <code>NoSuchTokenException</code> if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token
	 * @throws NoSuchTokenException if a we deploy auth token with the primary key could not be found
	 */
	public WeDeployAuthToken findByPrimaryKey(long weDeployAuthTokenId)
		throws NoSuchTokenException;

	/**
	 * Returns the we deploy auth token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token, or <code>null</code> if a we deploy auth token with the primary key could not be found
	 */
	public WeDeployAuthToken fetchByPrimaryKey(long weDeployAuthTokenId);

	/**
	 * Returns all the we deploy auth tokens.
	 *
	 * @return the we deploy auth tokens
	 */
	public java.util.List<WeDeployAuthToken> findAll();

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
	public java.util.List<WeDeployAuthToken> findAll(int start, int end);

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
	public java.util.List<WeDeployAuthToken> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WeDeployAuthToken>
			orderByComparator);

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
	public java.util.List<WeDeployAuthToken> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WeDeployAuthToken>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the we deploy auth tokens from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of we deploy auth tokens.
	 *
	 * @return the number of we deploy auth tokens
	 */
	public int countAll();

}