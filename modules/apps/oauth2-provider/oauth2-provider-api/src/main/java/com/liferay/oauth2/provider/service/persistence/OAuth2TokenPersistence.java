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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException;
import com.liferay.oauth2.provider.model.OAuth2Token;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the o auth2 token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2TokenPersistenceImpl
 * @see OAuth2TokenUtil
 * @generated
 */
@ProviderType
public interface OAuth2TokenPersistence extends BasePersistence<OAuth2Token> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2TokenUtil} to access the o auth2 token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public OAuth2Token findByContent(java.lang.String oAuth2TokenContent)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByContent(java.lang.String oAuth2TokenContent);

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByContent(java.lang.String oAuth2TokenContent,
		boolean retrieveFromCache);

	/**
	* Removes the o auth2 token where oAuth2TokenContent = &#63; from the database.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the o auth2 token that was removed
	*/
	public OAuth2Token removeByContent(java.lang.String oAuth2TokenContent)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the number of o auth2 tokens where oAuth2TokenContent = &#63;.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the number of matching o auth2 tokens
	*/
	public int countByContent(java.lang.String oAuth2TokenContent);

	/**
	* Returns all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByA(long oAuth2ApplicationId);

	/**
	* Returns a range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @return the range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end);

	/**
	* Returns an ordered range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public OAuth2Token findByA_First(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByA_First(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public OAuth2Token findByA_Last(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByA_Last(long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2TokenId the primary key of the current o auth2 token
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public OAuth2Token[] findByA_PrevAndNext(long oAuth2TokenId,
		long oAuth2ApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Removes all the o auth2 tokens where oAuth2ApplicationId = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	*/
	public void removeByA(long oAuth2ApplicationId);

	/**
	* Returns the number of o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the number of matching o auth2 tokens
	*/
	public int countByA(long oAuth2ApplicationId);

	/**
	* Returns all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId);

	/**
	* Returns a range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @return the range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end);

	/**
	* Returns an ordered range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public OAuth2Token findByRefreshToken_First(long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByRefreshToken_First(long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public OAuth2Token findByRefreshToken_Last(long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public OAuth2Token fetchByRefreshToken_Last(long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2TokenId the primary key of the current o auth2 token
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public OAuth2Token[] findByRefreshToken_PrevAndNext(long oAuth2TokenId,
		long oAuth2RefreshTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException;

	/**
	* Removes all the o auth2 tokens where oAuth2RefreshTokenId = &#63; from the database.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	*/
	public void removeByRefreshToken(long oAuth2RefreshTokenId);

	/**
	* Returns the number of o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the number of matching o auth2 tokens
	*/
	public int countByRefreshToken(long oAuth2RefreshTokenId);

	/**
	* Caches the o auth2 token in the entity cache if it is enabled.
	*
	* @param oAuth2Token the o auth2 token
	*/
	public void cacheResult(OAuth2Token oAuth2Token);

	/**
	* Caches the o auth2 tokens in the entity cache if it is enabled.
	*
	* @param oAuth2Tokens the o auth2 tokens
	*/
	public void cacheResult(java.util.List<OAuth2Token> oAuth2Tokens);

	/**
	* Creates a new o auth2 token with the primary key. Does not add the o auth2 token to the database.
	*
	* @param oAuth2TokenId the primary key for the new o auth2 token
	* @return the new o auth2 token
	*/
	public OAuth2Token create(long oAuth2TokenId);

	/**
	* Removes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token that was removed
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public OAuth2Token remove(long oAuth2TokenId)
		throws NoSuchOAuth2TokenException;

	public OAuth2Token updateImpl(OAuth2Token oAuth2Token);

	/**
	* Returns the o auth2 token with the primary key or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public OAuth2Token findByPrimaryKey(long oAuth2TokenId)
		throws NoSuchOAuth2TokenException;

	/**
	* Returns the o auth2 token with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token, or <code>null</code> if a o auth2 token with the primary key could not be found
	*/
	public OAuth2Token fetchByPrimaryKey(long oAuth2TokenId);

	@Override
	public java.util.Map<java.io.Serializable, OAuth2Token> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the o auth2 tokens.
	*
	* @return the o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findAll();

	/**
	* Returns a range of all the o auth2 tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @return the range of o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findAll(int start, int end);

	/**
	* Returns an ordered range of all the o auth2 tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 tokens
	*/
	public java.util.List<OAuth2Token> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the o auth2 tokens from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of o auth2 tokens.
	*
	* @return the number of o auth2 tokens
	*/
	public int countAll();
}