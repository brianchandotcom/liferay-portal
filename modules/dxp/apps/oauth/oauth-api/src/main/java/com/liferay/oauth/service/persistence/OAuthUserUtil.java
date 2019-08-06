/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.service.persistence;

import com.liferay.oauth.model.OAuthUser;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the o auth user service. This utility wraps <code>com.liferay.oauth.service.persistence.impl.OAuthUserPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthUserPersistence
 * @generated
 */
@ProviderType
public class OAuthUserUtil {

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
	public static void clearCache(OAuthUser oAuthUser) {
		getPersistence().clearCache(oAuthUser);
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
	public static Map<Serializable, OAuthUser> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuthUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuthUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuthUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuthUser update(OAuthUser oAuthUser) {
		return getPersistence().update(oAuthUser);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuthUser update(
		OAuthUser oAuthUser, ServiceContext serviceContext) {

		return getPersistence().update(oAuthUser, serviceContext);
	}

	/**
	 * Returns all the o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth users
	 */
	public static List<OAuthUser> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	public static List<OAuthUser> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	public static List<OAuthUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	public static List<OAuthUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByUserId_First(
			long userId, OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByUserId_First(
		long userId, OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByUserId_Last(
			long userId, OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByUserId_Last(
		long userId, OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where userId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public static OAuthUser[] findByUserId_PrevAndNext(
			long oAuthUserId, long userId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByUserId_PrevAndNext(
			oAuthUserId, userId, orderByComparator);
	}

	/**
	 * Removes all the o auth users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth users
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth users
	 */
	public static List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId) {

		return getPersistence().findByOAuthApplicationId(oAuthApplicationId);
	}

	/**
	 * Returns a range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	public static List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end) {

		return getPersistence().findByOAuthApplicationId(
			oAuthApplicationId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	public static List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().findByOAuthApplicationId(
			oAuthApplicationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	public static List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOAuthApplicationId(
			oAuthApplicationId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByOAuthApplicationId_First(
			long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByOAuthApplicationId_First(
			oAuthApplicationId, orderByComparator);
	}

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByOAuthApplicationId_First(
		long oAuthApplicationId,
		OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().fetchByOAuthApplicationId_First(
			oAuthApplicationId, orderByComparator);
	}

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByOAuthApplicationId_Last(
			long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByOAuthApplicationId_Last(
			oAuthApplicationId, orderByComparator);
	}

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByOAuthApplicationId_Last(
		long oAuthApplicationId,
		OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().fetchByOAuthApplicationId_Last(
			oAuthApplicationId, orderByComparator);
	}

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public static OAuthUser[] findByOAuthApplicationId_PrevAndNext(
			long oAuthUserId, long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByOAuthApplicationId_PrevAndNext(
			oAuthUserId, oAuthApplicationId, orderByComparator);
	}

	/**
	 * Removes all the o auth users where oAuthApplicationId = &#63; from the database.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 */
	public static void removeByOAuthApplicationId(long oAuthApplicationId) {
		getPersistence().removeByOAuthApplicationId(oAuthApplicationId);
	}

	/**
	 * Returns the number of o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	public static int countByOAuthApplicationId(long oAuthApplicationId) {
		return getPersistence().countByOAuthApplicationId(oAuthApplicationId);
	}

	/**
	 * Returns the o auth user where accessToken = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByAccessToken(String accessToken)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByAccessToken(accessToken);
	}

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByAccessToken(String accessToken) {
		return getPersistence().fetchByAccessToken(accessToken);
	}

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accessToken the access token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByAccessToken(
		String accessToken, boolean useFinderCache) {

		return getPersistence().fetchByAccessToken(accessToken, useFinderCache);
	}

	/**
	 * Removes the o auth user where accessToken = &#63; from the database.
	 *
	 * @param accessToken the access token
	 * @return the o auth user that was removed
	 */
	public static OAuthUser removeByAccessToken(String accessToken)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().removeByAccessToken(accessToken);
	}

	/**
	 * Returns the number of o auth users where accessToken = &#63;.
	 *
	 * @param accessToken the access token
	 * @return the number of matching o auth users
	 */
	public static int countByAccessToken(String accessToken) {
		return getPersistence().countByAccessToken(accessToken);
	}

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public static OAuthUser findByU_OAI(long userId, long oAuthApplicationId)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByU_OAI(userId, oAuthApplicationId);
	}

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByU_OAI(long userId, long oAuthApplicationId) {
		return getPersistence().fetchByU_OAI(userId, oAuthApplicationId);
	}

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public static OAuthUser fetchByU_OAI(
		long userId, long oAuthApplicationId, boolean useFinderCache) {

		return getPersistence().fetchByU_OAI(
			userId, oAuthApplicationId, useFinderCache);
	}

	/**
	 * Removes the o auth user where userId = &#63; and oAuthApplicationId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the o auth user that was removed
	 */
	public static OAuthUser removeByU_OAI(long userId, long oAuthApplicationId)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().removeByU_OAI(userId, oAuthApplicationId);
	}

	/**
	 * Returns the number of o auth users where userId = &#63; and oAuthApplicationId = &#63;.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	public static int countByU_OAI(long userId, long oAuthApplicationId) {
		return getPersistence().countByU_OAI(userId, oAuthApplicationId);
	}

	/**
	 * Caches the o auth user in the entity cache if it is enabled.
	 *
	 * @param oAuthUser the o auth user
	 */
	public static void cacheResult(OAuthUser oAuthUser) {
		getPersistence().cacheResult(oAuthUser);
	}

	/**
	 * Caches the o auth users in the entity cache if it is enabled.
	 *
	 * @param oAuthUsers the o auth users
	 */
	public static void cacheResult(List<OAuthUser> oAuthUsers) {
		getPersistence().cacheResult(oAuthUsers);
	}

	/**
	 * Creates a new o auth user with the primary key. Does not add the o auth user to the database.
	 *
	 * @param oAuthUserId the primary key for the new o auth user
	 * @return the new o auth user
	 */
	public static OAuthUser create(long oAuthUserId) {
		return getPersistence().create(oAuthUserId);
	}

	/**
	 * Removes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user that was removed
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public static OAuthUser remove(long oAuthUserId)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().remove(oAuthUserId);
	}

	public static OAuthUser updateImpl(OAuthUser oAuthUser) {
		return getPersistence().updateImpl(oAuthUser);
	}

	/**
	 * Returns the o auth user with the primary key or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public static OAuthUser findByPrimaryKey(long oAuthUserId)
		throws com.liferay.oauth.exception.NoSuchUserException {

		return getPersistence().findByPrimaryKey(oAuthUserId);
	}

	/**
	 * Returns the o auth user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user, or <code>null</code> if a o auth user with the primary key could not be found
	 */
	public static OAuthUser fetchByPrimaryKey(long oAuthUserId) {
		return getPersistence().fetchByPrimaryKey(oAuthUserId);
	}

	/**
	 * Returns all the o auth users.
	 *
	 * @return the o auth users
	 */
	public static List<OAuthUser> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of o auth users
	 */
	public static List<OAuthUser> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth users
	 */
	public static List<OAuthUser> findAll(
		int start, int end, OrderByComparator<OAuthUser> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth users
	 */
	public static List<OAuthUser> findAll(
		int start, int end, OrderByComparator<OAuthUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth users from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth users.
	 *
	 * @return the number of o auth users
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuthUserPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuthUserPersistence, OAuthUserPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuthUserPersistence.class);

		ServiceTracker<OAuthUserPersistence, OAuthUserPersistence>
			serviceTracker =
				new ServiceTracker<OAuthUserPersistence, OAuthUserPersistence>(
					bundle.getBundleContext(), OAuthUserPersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}