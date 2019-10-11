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

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the o auth2 authorization service. This utility wraps <code>com.liferay.oauth2.provider.service.persistence.impl.OAuth2AuthorizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationPersistence
 * @generated
 */
public class OAuth2AuthorizationUtil {

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
	public static void clearCache(OAuth2Authorization oAuth2Authorization) {
		getPersistence().clearCache(oAuth2Authorization);
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
	public static Map<Serializable, OAuth2Authorization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuth2Authorization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2Authorization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2Authorization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2Authorization update(
		OAuth2Authorization oAuth2Authorization) {

		return getPersistence().update(oAuth2Authorization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2Authorization update(
		OAuth2Authorization oAuth2Authorization,
		ServiceContext serviceContext) {

		return getPersistence().update(oAuth2Authorization, serviceContext);
	}

	/**
	 * Returns all the o auth2 authorizations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByUserId_First(
			long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByUserId_First(
		long userId, OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByUserId_Last(
			long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByUserId_Last(
		long userId, OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization[] findByUserId_PrevAndNext(
			long oAuth2AuthorizationId, long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByUserId_PrevAndNext(
			oAuth2AuthorizationId, userId, orderByComparator);
	}

	/**
	 * Removes all the o auth2 authorizations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of o auth2 authorizations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth2 authorizations
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {

		return getPersistence().findByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end) {

		return getPersistence().findByOAuth2ApplicationId(
			oAuth2ApplicationId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findByOAuth2ApplicationId(
			oAuth2ApplicationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOAuth2ApplicationId(
			oAuth2ApplicationId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByOAuth2ApplicationId_First(
			long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByOAuth2ApplicationId_First(
			oAuth2ApplicationId, orderByComparator);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByOAuth2ApplicationId_First(
			oAuth2ApplicationId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByOAuth2ApplicationId_Last(
			long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByOAuth2ApplicationId_Last(
			oAuth2ApplicationId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByOAuth2ApplicationId_Last(
			oAuth2ApplicationId, orderByComparator);
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization[] findByOAuth2ApplicationId_PrevAndNext(
			long oAuth2AuthorizationId, long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByOAuth2ApplicationId_PrevAndNext(
			oAuth2AuthorizationId, oAuth2ApplicationId, orderByComparator);
	}

	/**
	 * Removes all the o auth2 authorizations where oAuth2ApplicationId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 */
	public static void removeByOAuth2ApplicationId(long oAuth2ApplicationId) {
		getPersistence().removeByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Returns the number of o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the number of matching o auth2 authorizations
	 */
	public static int countByOAuth2ApplicationId(long oAuth2ApplicationId) {
		return getPersistence().countByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Returns all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @return the matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash) {

		return getPersistence().findByAccessTokenContentHash(
			accessTokenContentHash);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end) {

		return getPersistence().findByAccessTokenContentHash(
			accessTokenContentHash, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findByAccessTokenContentHash(
			accessTokenContentHash, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccessTokenContentHash(
			accessTokenContentHash, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByAccessTokenContentHash_First(
			long accessTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByAccessTokenContentHash_First(
			accessTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByAccessTokenContentHash_First(
		long accessTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByAccessTokenContentHash_First(
			accessTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByAccessTokenContentHash_Last(
			long accessTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByAccessTokenContentHash_Last(
			accessTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByAccessTokenContentHash_Last(
		long accessTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByAccessTokenContentHash_Last(
			accessTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization[]
			findByAccessTokenContentHash_PrevAndNext(
				long oAuth2AuthorizationId, long accessTokenContentHash,
				OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByAccessTokenContentHash_PrevAndNext(
			oAuth2AuthorizationId, accessTokenContentHash, orderByComparator);
	}

	/**
	 * Removes all the o auth2 authorizations where accessTokenContentHash = &#63; from the database.
	 *
	 * @param accessTokenContentHash the access token content hash
	 */
	public static void removeByAccessTokenContentHash(
		long accessTokenContentHash) {

		getPersistence().removeByAccessTokenContentHash(accessTokenContentHash);
	}

	/**
	 * Returns the number of o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @return the number of matching o auth2 authorizations
	 */
	public static int countByAccessTokenContentHash(
		long accessTokenContentHash) {

		return getPersistence().countByAccessTokenContentHash(
			accessTokenContentHash);
	}

	/**
	 * Returns all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @return the matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash) {

		return getPersistence().findByRefreshTokenContentHash(
			refreshTokenContentHash);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end) {

		return getPersistence().findByRefreshTokenContentHash(
			refreshTokenContentHash, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findByRefreshTokenContentHash(
			refreshTokenContentHash, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRefreshTokenContentHash(
			refreshTokenContentHash, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByRefreshTokenContentHash_First(
			long refreshTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByRefreshTokenContentHash_First(
			refreshTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByRefreshTokenContentHash_First(
		long refreshTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByRefreshTokenContentHash_First(
			refreshTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization findByRefreshTokenContentHash_Last(
			long refreshTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByRefreshTokenContentHash_Last(
			refreshTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	public static OAuth2Authorization fetchByRefreshTokenContentHash_Last(
		long refreshTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().fetchByRefreshTokenContentHash_Last(
			refreshTokenContentHash, orderByComparator);
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization[]
			findByRefreshTokenContentHash_PrevAndNext(
				long oAuth2AuthorizationId, long refreshTokenContentHash,
				OrderByComparator<OAuth2Authorization> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByRefreshTokenContentHash_PrevAndNext(
			oAuth2AuthorizationId, refreshTokenContentHash, orderByComparator);
	}

	/**
	 * Removes all the o auth2 authorizations where refreshTokenContentHash = &#63; from the database.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 */
	public static void removeByRefreshTokenContentHash(
		long refreshTokenContentHash) {

		getPersistence().removeByRefreshTokenContentHash(
			refreshTokenContentHash);
	}

	/**
	 * Returns the number of o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @return the number of matching o auth2 authorizations
	 */
	public static int countByRefreshTokenContentHash(
		long refreshTokenContentHash) {

		return getPersistence().countByRefreshTokenContentHash(
			refreshTokenContentHash);
	}

	/**
	 * Caches the o auth2 authorization in the entity cache if it is enabled.
	 *
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	public static void cacheResult(OAuth2Authorization oAuth2Authorization) {
		getPersistence().cacheResult(oAuth2Authorization);
	}

	/**
	 * Caches the o auth2 authorizations in the entity cache if it is enabled.
	 *
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	public static void cacheResult(
		List<OAuth2Authorization> oAuth2Authorizations) {

		getPersistence().cacheResult(oAuth2Authorizations);
	}

	/**
	 * Creates a new o auth2 authorization with the primary key. Does not add the o auth2 authorization to the database.
	 *
	 * @param oAuth2AuthorizationId the primary key for the new o auth2 authorization
	 * @return the new o auth2 authorization
	 */
	public static OAuth2Authorization create(long oAuth2AuthorizationId) {
		return getPersistence().create(oAuth2AuthorizationId);
	}

	/**
	 * Removes the o auth2 authorization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization that was removed
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization remove(long oAuth2AuthorizationId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().remove(oAuth2AuthorizationId);
	}

	public static OAuth2Authorization updateImpl(
		OAuth2Authorization oAuth2Authorization) {

		return getPersistence().updateImpl(oAuth2Authorization);
	}

	/**
	 * Returns the o auth2 authorization with the primary key or throws a <code>NoSuchOAuth2AuthorizationException</code> if it could not be found.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization findByPrimaryKey(
			long oAuth2AuthorizationId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2AuthorizationException {

		return getPersistence().findByPrimaryKey(oAuth2AuthorizationId);
	}

	/**
	 * Returns the o auth2 authorization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization, or <code>null</code> if a o auth2 authorization with the primary key could not be found
	 */
	public static OAuth2Authorization fetchByPrimaryKey(
		long oAuth2AuthorizationId) {

		return getPersistence().fetchByPrimaryKey(oAuth2AuthorizationId);
	}

	/**
	 * Returns all the o auth2 authorizations.
	 *
	 * @return the o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findAll(
		int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 authorizations
	 */
	public static List<OAuth2Authorization> findAll(
		int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth2 authorizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth2 authorizations.
	 *
	 * @return the number of o auth2 authorizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of o auth2 scope grants associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @return long[] of the primaryKeys of o auth2 scope grants associated with the o auth2 authorization
	 */
	public static long[] getOAuth2ScopeGrantPrimaryKeys(long pk) {
		return getPersistence().getOAuth2ScopeGrantPrimaryKeys(pk);
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return the o auth2 authorizations associated with the o auth2 scope grant
	 */
	public static List<OAuth2Authorization>
		getOAuth2ScopeGrantOAuth2Authorizations(long pk) {

		return getPersistence().getOAuth2ScopeGrantOAuth2Authorizations(pk);
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	public static List<OAuth2Authorization>
		getOAuth2ScopeGrantOAuth2Authorizations(long pk, int start, int end) {

		return getPersistence().getOAuth2ScopeGrantOAuth2Authorizations(
			pk, start, end);
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	public static List<OAuth2Authorization>
		getOAuth2ScopeGrantOAuth2Authorizations(
			long pk, int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator) {

		return getPersistence().getOAuth2ScopeGrantOAuth2Authorizations(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of o auth2 scope grants associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @return the number of o auth2 scope grants associated with the o auth2 authorization
	 */
	public static int getOAuth2ScopeGrantsSize(long pk) {
		return getPersistence().getOAuth2ScopeGrantsSize(pk);
	}

	/**
	 * Returns <code>true</code> if the o auth2 scope grant is associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 * @return <code>true</code> if the o auth2 scope grant is associated with the o auth2 authorization; <code>false</code> otherwise
	 */
	public static boolean containsOAuth2ScopeGrant(
		long pk, long oAuth2ScopeGrantPK) {

		return getPersistence().containsOAuth2ScopeGrant(
			pk, oAuth2ScopeGrantPK);
	}

	/**
	 * Returns <code>true</code> if the o auth2 authorization has any o auth2 scope grants associated with it.
	 *
	 * @param pk the primary key of the o auth2 authorization to check for associations with o auth2 scope grants
	 * @return <code>true</code> if the o auth2 authorization has any o auth2 scope grants associated with it; <code>false</code> otherwise
	 */
	public static boolean containsOAuth2ScopeGrants(long pk) {
		return getPersistence().containsOAuth2ScopeGrants(pk);
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 */
	public static void addOAuth2ScopeGrant(long pk, long oAuth2ScopeGrantPK) {
		getPersistence().addOAuth2ScopeGrant(pk, oAuth2ScopeGrantPK);
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 */
	public static void addOAuth2ScopeGrant(
		long pk,
		com.liferay.oauth2.provider.model.OAuth2ScopeGrant oAuth2ScopeGrant) {

		getPersistence().addOAuth2ScopeGrant(pk, oAuth2ScopeGrant);
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants
	 */
	public static void addOAuth2ScopeGrants(
		long pk, long[] oAuth2ScopeGrantPKs) {

		getPersistence().addOAuth2ScopeGrants(pk, oAuth2ScopeGrantPKs);
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants
	 */
	public static void addOAuth2ScopeGrants(
		long pk,
		List<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			oAuth2ScopeGrants) {

		getPersistence().addOAuth2ScopeGrants(pk, oAuth2ScopeGrants);
	}

	/**
	 * Clears all associations between the o auth2 authorization and its o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization to clear the associated o auth2 scope grants from
	 */
	public static void clearOAuth2ScopeGrants(long pk) {
		getPersistence().clearOAuth2ScopeGrants(pk);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 */
	public static void removeOAuth2ScopeGrant(
		long pk, long oAuth2ScopeGrantPK) {

		getPersistence().removeOAuth2ScopeGrant(pk, oAuth2ScopeGrantPK);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 */
	public static void removeOAuth2ScopeGrant(
		long pk,
		com.liferay.oauth2.provider.model.OAuth2ScopeGrant oAuth2ScopeGrant) {

		getPersistence().removeOAuth2ScopeGrant(pk, oAuth2ScopeGrant);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants
	 */
	public static void removeOAuth2ScopeGrants(
		long pk, long[] oAuth2ScopeGrantPKs) {

		getPersistence().removeOAuth2ScopeGrants(pk, oAuth2ScopeGrantPKs);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants
	 */
	public static void removeOAuth2ScopeGrants(
		long pk,
		List<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			oAuth2ScopeGrants) {

		getPersistence().removeOAuth2ScopeGrants(pk, oAuth2ScopeGrants);
	}

	/**
	 * Sets the o auth2 scope grants associated with the o auth2 authorization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants to be associated with the o auth2 authorization
	 */
	public static void setOAuth2ScopeGrants(
		long pk, long[] oAuth2ScopeGrantPKs) {

		getPersistence().setOAuth2ScopeGrants(pk, oAuth2ScopeGrantPKs);
	}

	/**
	 * Sets the o auth2 scope grants associated with the o auth2 authorization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants to be associated with the o auth2 authorization
	 */
	public static void setOAuth2ScopeGrants(
		long pk,
		List<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			oAuth2ScopeGrants) {

		getPersistence().setOAuth2ScopeGrants(pk, oAuth2ScopeGrants);
	}

	public static OAuth2AuthorizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<OAuth2AuthorizationPersistence, OAuth2AuthorizationPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			OAuth2AuthorizationPersistence.class);

		ServiceTracker
			<OAuth2AuthorizationPersistence, OAuth2AuthorizationPersistence>
				serviceTracker =
					new ServiceTracker
						<OAuth2AuthorizationPersistence,
						 OAuth2AuthorizationPersistence>(
							 bundle.getBundleContext(),
							 OAuth2AuthorizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}