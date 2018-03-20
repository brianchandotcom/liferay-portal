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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2AccessTokenException;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the o auth2 access token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2AccessTokenPersistenceImpl
 * @see OAuth2AccessTokenUtil
 * @generated
 */
@ProviderType
public interface OAuth2AccessTokenPersistence extends BasePersistence<OAuth2AccessToken> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2AccessTokenUtil} to access the o auth2 access token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId);

	/**
	* Returns a range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @return the range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end);

	/**
	* Returns an ordered range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken findByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the first o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns the last o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken findByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the last o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns the o auth2 access tokens before and after the current o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2AccessTokenId the primary key of the current o auth2 access token
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	*/
	public OAuth2AccessToken[] findByOAuth2ApplicationId_PrevAndNext(
		long oAuth2AccessTokenId, long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Removes all the o auth2 access tokens where oAuth2ApplicationId = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	*/
	public void removeByOAuth2ApplicationId(long oAuth2ApplicationId);

	/**
	* Returns the number of o auth2 access tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the number of matching o auth2 access tokens
	*/
	public int countByOAuth2ApplicationId(long oAuth2ApplicationId);

	/**
	* Returns all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId);

	/**
	* Returns a range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @return the range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end);

	/**
	* Returns an ordered range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken findByOAuth2RefreshTokenId_First(
		long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the first o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByOAuth2RefreshTokenId_First(
		long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns the last o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken findByOAuth2RefreshTokenId_Last(
		long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the last o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByOAuth2RefreshTokenId_Last(
		long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns the o auth2 access tokens before and after the current o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the primary key of the current o auth2 access token
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	*/
	public OAuth2AccessToken[] findByOAuth2RefreshTokenId_PrevAndNext(
		long oAuth2AccessTokenId, long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Removes all the o auth2 access tokens where oAuth2RefreshTokenId = &#63; from the database.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	*/
	public void removeByOAuth2RefreshTokenId(long oAuth2RefreshTokenId);

	/**
	* Returns the number of o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the number of matching o auth2 access tokens
	*/
	public int countByOAuth2RefreshTokenId(long oAuth2RefreshTokenId);

	/**
	* Returns the o auth2 access token where tokenContent = &#63; or throws a {@link NoSuchOAuth2AccessTokenException} if it could not be found.
	*
	* @param tokenContent the token content
	* @return the matching o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken findByTokenContent(java.lang.String tokenContent)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the o auth2 access token where tokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tokenContent the token content
	* @return the matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByTokenContent(java.lang.String tokenContent);

	/**
	* Returns the o auth2 access token where tokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tokenContent the token content
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	*/
	public OAuth2AccessToken fetchByTokenContent(
		java.lang.String tokenContent, boolean retrieveFromCache);

	/**
	* Removes the o auth2 access token where tokenContent = &#63; from the database.
	*
	* @param tokenContent the token content
	* @return the o auth2 access token that was removed
	*/
	public OAuth2AccessToken removeByTokenContent(java.lang.String tokenContent)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the number of o auth2 access tokens where tokenContent = &#63;.
	*
	* @param tokenContent the token content
	* @return the number of matching o auth2 access tokens
	*/
	public int countByTokenContent(java.lang.String tokenContent);

	/**
	* Caches the o auth2 access token in the entity cache if it is enabled.
	*
	* @param oAuth2AccessToken the o auth2 access token
	*/
	public void cacheResult(OAuth2AccessToken oAuth2AccessToken);

	/**
	* Caches the o auth2 access tokens in the entity cache if it is enabled.
	*
	* @param oAuth2AccessTokens the o auth2 access tokens
	*/
	public void cacheResult(
		java.util.List<OAuth2AccessToken> oAuth2AccessTokens);

	/**
	* Creates a new o auth2 access token with the primary key. Does not add the o auth2 access token to the database.
	*
	* @param oAuth2AccessTokenId the primary key for the new o auth2 access token
	* @return the new o auth2 access token
	*/
	public OAuth2AccessToken create(long oAuth2AccessTokenId);

	/**
	* Removes the o auth2 access token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2AccessTokenId the primary key of the o auth2 access token
	* @return the o auth2 access token that was removed
	* @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	*/
	public OAuth2AccessToken remove(long oAuth2AccessTokenId)
		throws NoSuchOAuth2AccessTokenException;

	public OAuth2AccessToken updateImpl(OAuth2AccessToken oAuth2AccessToken);

	/**
	* Returns the o auth2 access token with the primary key or throws a {@link NoSuchOAuth2AccessTokenException} if it could not be found.
	*
	* @param oAuth2AccessTokenId the primary key of the o auth2 access token
	* @return the o auth2 access token
	* @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	*/
	public OAuth2AccessToken findByPrimaryKey(long oAuth2AccessTokenId)
		throws NoSuchOAuth2AccessTokenException;

	/**
	* Returns the o auth2 access token with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2AccessTokenId the primary key of the o auth2 access token
	* @return the o auth2 access token, or <code>null</code> if a o auth2 access token with the primary key could not be found
	*/
	public OAuth2AccessToken fetchByPrimaryKey(long oAuth2AccessTokenId);

	@Override
	public java.util.Map<java.io.Serializable, OAuth2AccessToken> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the o auth2 access tokens.
	*
	* @return the o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findAll();

	/**
	* Returns a range of all the o auth2 access tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @return the range of o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findAll(int start, int end);

	/**
	* Returns an ordered range of all the o auth2 access tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 access tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 access tokens
	*/
	public java.util.List<OAuth2AccessToken> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2AccessToken> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the o auth2 access tokens from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of o auth2 access tokens.
	*
	* @return the number of o auth2 access tokens
	*/
	public int countAll();
}