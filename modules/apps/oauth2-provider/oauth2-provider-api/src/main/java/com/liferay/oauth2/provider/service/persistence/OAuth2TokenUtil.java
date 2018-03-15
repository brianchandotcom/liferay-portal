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

import com.liferay.oauth2.provider.model.OAuth2Token;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the o auth2 token service. This utility wraps {@link com.liferay.oauth2.provider.service.persistence.impl.OAuth2TokenPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenPersistence
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2TokenPersistenceImpl
 * @generated
 */
@ProviderType
public class OAuth2TokenUtil {
	/*
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
	public static void clearCache(OAuth2Token oAuth2Token) {
		getPersistence().clearCache(oAuth2Token);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuth2Token> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2Token> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2Token> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2Token update(OAuth2Token oAuth2Token) {
		return getPersistence().update(oAuth2Token);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2Token update(OAuth2Token oAuth2Token,
		ServiceContext serviceContext) {
		return getPersistence().update(oAuth2Token, serviceContext);
	}

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public static OAuth2Token findByContent(java.lang.String oAuth2TokenContent)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence().findByContent(oAuth2TokenContent);
	}

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByContent(
		java.lang.String oAuth2TokenContent) {
		return getPersistence().fetchByContent(oAuth2TokenContent);
	}

	/**
	* Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByContent(
		java.lang.String oAuth2TokenContent, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByContent(oAuth2TokenContent, retrieveFromCache);
	}

	/**
	* Removes the o auth2 token where oAuth2TokenContent = &#63; from the database.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the o auth2 token that was removed
	*/
	public static OAuth2Token removeByContent(
		java.lang.String oAuth2TokenContent)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence().removeByContent(oAuth2TokenContent);
	}

	/**
	* Returns the number of o auth2 tokens where oAuth2TokenContent = &#63;.
	*
	* @param oAuth2TokenContent the o auth2 token content
	* @return the number of matching o auth2 tokens
	*/
	public static int countByContent(java.lang.String oAuth2TokenContent) {
		return getPersistence().countByContent(oAuth2TokenContent);
	}

	/**
	* Returns all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the matching o auth2 tokens
	*/
	public static List<OAuth2Token> findByA(long oAuth2ApplicationId) {
		return getPersistence().findByA(oAuth2ApplicationId);
	}

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
	public static List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end) {
		return getPersistence().findByA(oAuth2ApplicationId, start, end);
	}

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
	public static List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end, OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .findByA(oAuth2ApplicationId, start, end, orderByComparator);
	}

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
	public static List<OAuth2Token> findByA(long oAuth2ApplicationId,
		int start, int end, OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByA(oAuth2ApplicationId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public static OAuth2Token findByA_First(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByA_First(oAuth2ApplicationId, orderByComparator);
	}

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByA_First(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .fetchByA_First(oAuth2ApplicationId, orderByComparator);
	}

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public static OAuth2Token findByA_Last(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByA_Last(oAuth2ApplicationId, orderByComparator);
	}

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByA_Last(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .fetchByA_Last(oAuth2ApplicationId, orderByComparator);
	}

	/**
	* Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2TokenId the primary key of the current o auth2 token
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public static OAuth2Token[] findByA_PrevAndNext(long oAuth2TokenId,
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByA_PrevAndNext(oAuth2TokenId, oAuth2ApplicationId,
			orderByComparator);
	}

	/**
	* Removes all the o auth2 tokens where oAuth2ApplicationId = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	*/
	public static void removeByA(long oAuth2ApplicationId) {
		getPersistence().removeByA(oAuth2ApplicationId);
	}

	/**
	* Returns the number of o auth2 tokens where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the number of matching o auth2 tokens
	*/
	public static int countByA(long oAuth2ApplicationId) {
		return getPersistence().countByA(oAuth2ApplicationId);
	}

	/**
	* Returns all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the matching o auth2 tokens
	*/
	public static List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId) {
		return getPersistence().findByRefreshToken(oAuth2RefreshTokenId);
	}

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
	public static List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end) {
		return getPersistence()
				   .findByRefreshToken(oAuth2RefreshTokenId, start, end);
	}

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
	public static List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .findByRefreshToken(oAuth2RefreshTokenId, start, end,
			orderByComparator);
	}

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
	public static List<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId, int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRefreshToken(oAuth2RefreshTokenId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public static OAuth2Token findByRefreshToken_First(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByRefreshToken_First(oAuth2RefreshTokenId,
			orderByComparator);
	}

	/**
	* Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByRefreshToken_First(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .fetchByRefreshToken_First(oAuth2RefreshTokenId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token
	* @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	*/
	public static OAuth2Token findByRefreshToken_Last(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByRefreshToken_Last(oAuth2RefreshTokenId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	*/
	public static OAuth2Token fetchByRefreshToken_Last(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence()
				   .fetchByRefreshToken_Last(oAuth2RefreshTokenId,
			orderByComparator);
	}

	/**
	* Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2TokenId the primary key of the current o auth2 token
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public static OAuth2Token[] findByRefreshToken_PrevAndNext(
		long oAuth2TokenId, long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence()
				   .findByRefreshToken_PrevAndNext(oAuth2TokenId,
			oAuth2RefreshTokenId, orderByComparator);
	}

	/**
	* Removes all the o auth2 tokens where oAuth2RefreshTokenId = &#63; from the database.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	*/
	public static void removeByRefreshToken(long oAuth2RefreshTokenId) {
		getPersistence().removeByRefreshToken(oAuth2RefreshTokenId);
	}

	/**
	* Returns the number of o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID
	* @return the number of matching o auth2 tokens
	*/
	public static int countByRefreshToken(long oAuth2RefreshTokenId) {
		return getPersistence().countByRefreshToken(oAuth2RefreshTokenId);
	}

	/**
	* Caches the o auth2 token in the entity cache if it is enabled.
	*
	* @param oAuth2Token the o auth2 token
	*/
	public static void cacheResult(OAuth2Token oAuth2Token) {
		getPersistence().cacheResult(oAuth2Token);
	}

	/**
	* Caches the o auth2 tokens in the entity cache if it is enabled.
	*
	* @param oAuth2Tokens the o auth2 tokens
	*/
	public static void cacheResult(List<OAuth2Token> oAuth2Tokens) {
		getPersistence().cacheResult(oAuth2Tokens);
	}

	/**
	* Creates a new o auth2 token with the primary key. Does not add the o auth2 token to the database.
	*
	* @param oAuth2TokenId the primary key for the new o auth2 token
	* @return the new o auth2 token
	*/
	public static OAuth2Token create(long oAuth2TokenId) {
		return getPersistence().create(oAuth2TokenId);
	}

	/**
	* Removes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token that was removed
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public static OAuth2Token remove(long oAuth2TokenId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence().remove(oAuth2TokenId);
	}

	public static OAuth2Token updateImpl(OAuth2Token oAuth2Token) {
		return getPersistence().updateImpl(oAuth2Token);
	}

	/**
	* Returns the o auth2 token with the primary key or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token
	* @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	*/
	public static OAuth2Token findByPrimaryKey(long oAuth2TokenId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getPersistence().findByPrimaryKey(oAuth2TokenId);
	}

	/**
	* Returns the o auth2 token with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token, or <code>null</code> if a o auth2 token with the primary key could not be found
	*/
	public static OAuth2Token fetchByPrimaryKey(long oAuth2TokenId) {
		return getPersistence().fetchByPrimaryKey(oAuth2TokenId);
	}

	public static java.util.Map<java.io.Serializable, OAuth2Token> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the o auth2 tokens.
	*
	* @return the o auth2 tokens
	*/
	public static List<OAuth2Token> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<OAuth2Token> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<OAuth2Token> findAll(int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<OAuth2Token> findAll(int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the o auth2 tokens from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of o auth2 tokens.
	*
	* @return the number of o auth2 tokens
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuth2TokenPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2TokenPersistence, OAuth2TokenPersistence> _serviceTracker =
		ServiceTrackerFactory.open(OAuth2TokenPersistence.class);
}