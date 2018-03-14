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

package com.liferay.oauth2.provider.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2RefreshTokenException;
import com.liferay.oauth2.provider.model.OAuth2RefreshToken;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the o auth2 refresh token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2RefreshTokenPersistenceImpl
 * @see OAuth2RefreshTokenUtil
 * @generated
 */
@ProviderType
public interface OAuth2RefreshTokenPersistence extends BasePersistence<OAuth2RefreshToken> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2RefreshTokenUtil} to access the o auth2 refresh token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the o auth2 refresh token where oAuth2RefreshTokenContent = &#63; or throws a {@link NoSuchOAuth2RefreshTokenException} if it could not be found.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content
	* @return the matching o auth2 refresh token
	* @throws NoSuchOAuth2RefreshTokenException if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken findByContent(
		java.lang.String oAuth2RefreshTokenContent)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Returns the o auth2 refresh token where oAuth2RefreshTokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content
	* @return the matching o auth2 refresh token, or <code>null</code> if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken fetchByContent(
		java.lang.String oAuth2RefreshTokenContent);

	/**
	* Returns the o auth2 refresh token where oAuth2RefreshTokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 refresh token, or <code>null</code> if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken fetchByContent(
		java.lang.String oAuth2RefreshTokenContent, boolean retrieveFromCache);

	/**
	* Removes the o auth2 refresh token where oAuth2RefreshTokenContent = &#63; from the database.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content
	* @return the o auth2 refresh token that was removed
	*/
	public OAuth2RefreshToken removeByContent(
		java.lang.String oAuth2RefreshTokenContent)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Returns the number of o auth2 refresh tokens where oAuth2RefreshTokenContent = &#63;.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content
	* @return the number of matching o auth2 refresh tokens
	*/
	public int countByContent(java.lang.String oAuth2RefreshTokenContent);

	/**
	* Returns all the o auth2 refresh tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the matching o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findByA(long oAuth2ApplicationId);

	/**
	* Returns a range of all the o auth2 refresh tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @return the range of matching o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findByA(
		long oAuth2ApplicationId, int start, int end);

	/**
	* Returns an ordered range of all the o auth2 refresh tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findByA(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 refresh tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findByA(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 refresh token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 refresh token
	* @throws NoSuchOAuth2RefreshTokenException if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken findByA_First(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Returns the first o auth2 refresh token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 refresh token, or <code>null</code> if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken fetchByA_First(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator);

	/**
	* Returns the last o auth2 refresh token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 refresh token
	* @throws NoSuchOAuth2RefreshTokenException if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken findByA_Last(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Returns the last o auth2 refresh token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 refresh token, or <code>null</code> if a matching o auth2 refresh token could not be found
	*/
	public OAuth2RefreshToken fetchByA_Last(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator);

	/**
	* Returns the o auth2 refresh tokens before and after the current o auth2 refresh token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2RefreshTokenId the primary key of the current o auth2 refresh token
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 refresh token
	* @throws NoSuchOAuth2RefreshTokenException if a o auth2 refresh token with the primary key could not be found
	*/
	public OAuth2RefreshToken[] findByA_PrevAndNext(long oAuth2RefreshTokenId,
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Removes all the o auth2 refresh tokens where oAuth2ApplicationId = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	*/
	public void removeByA(long oAuth2ApplicationId);

	/**
	* Returns the number of o auth2 refresh tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the number of matching o auth2 refresh tokens
	*/
	public int countByA(long oAuth2ApplicationId);

	/**
	* Caches the o auth2 refresh token in the entity cache if it is enabled.
	*
	* @param oAuth2RefreshToken the o auth2 refresh token
	*/
	public void cacheResult(OAuth2RefreshToken oAuth2RefreshToken);

	/**
	* Caches the o auth2 refresh tokens in the entity cache if it is enabled.
	*
	* @param oAuth2RefreshTokens the o auth2 refresh tokens
	*/
	public void cacheResult(
		java.util.List<OAuth2RefreshToken> oAuth2RefreshTokens);

	/**
	* Creates a new o auth2 refresh token with the primary key. Does not add the o auth2 refresh token to the database.
	*
	* @param oAuth2RefreshTokenId the primary key for the new o auth2 refresh token
	* @return the new o auth2 refresh token
	*/
	public OAuth2RefreshToken create(long oAuth2RefreshTokenId);

	/**
	* Removes the o auth2 refresh token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2RefreshTokenId the primary key of the o auth2 refresh token
	* @return the o auth2 refresh token that was removed
	* @throws NoSuchOAuth2RefreshTokenException if a o auth2 refresh token with the primary key could not be found
	*/
	public OAuth2RefreshToken remove(long oAuth2RefreshTokenId)
		throws NoSuchOAuth2RefreshTokenException;

	public OAuth2RefreshToken updateImpl(OAuth2RefreshToken oAuth2RefreshToken);

	/**
	* Returns the o auth2 refresh token with the primary key or throws a {@link NoSuchOAuth2RefreshTokenException} if it could not be found.
	*
	* @param oAuth2RefreshTokenId the primary key of the o auth2 refresh token
	* @return the o auth2 refresh token
	* @throws NoSuchOAuth2RefreshTokenException if a o auth2 refresh token with the primary key could not be found
	*/
	public OAuth2RefreshToken findByPrimaryKey(long oAuth2RefreshTokenId)
		throws NoSuchOAuth2RefreshTokenException;

	/**
	* Returns the o auth2 refresh token with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2RefreshTokenId the primary key of the o auth2 refresh token
	* @return the o auth2 refresh token, or <code>null</code> if a o auth2 refresh token with the primary key could not be found
	*/
	public OAuth2RefreshToken fetchByPrimaryKey(long oAuth2RefreshTokenId);

	@Override
	public java.util.Map<java.io.Serializable, OAuth2RefreshToken> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the o auth2 refresh tokens.
	*
	* @return the o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findAll();

	/**
	* Returns a range of all the o auth2 refresh tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @return the range of o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findAll(int start, int end);

	/**
	* Returns an ordered range of all the o auth2 refresh tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 refresh tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 refresh tokens
	*/
	public java.util.List<OAuth2RefreshToken> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2RefreshToken> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the o auth2 refresh tokens from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of o auth2 refresh tokens.
	*
	* @return the number of o auth2 refresh tokens
	*/
	public int countAll();
}