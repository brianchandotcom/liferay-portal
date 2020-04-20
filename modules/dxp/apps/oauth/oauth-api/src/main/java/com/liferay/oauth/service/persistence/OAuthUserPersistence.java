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

import com.liferay.oauth.exception.NoSuchUserException;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the o auth user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthUserUtil
 * @generated
 */
@ProviderType
public interface OAuthUserPersistence extends BasePersistence<OAuthUser> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthUserUtil} to access the o auth user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth users
	 */
	public java.util.List<OAuthUser> findByUserId(long userId);

	/**
	 * Returns a range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where userId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser[] findByUserId_PrevAndNext(
			long oAuthUserId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns all the o auth users that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByUserId(long userId);

	/**
	 * Returns a range of all the o auth users that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth users that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set of o auth users that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser[] filterFindByUserId_PrevAndNext(
			long oAuthUserId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Removes all the o auth users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth users
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the number of o auth users that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth users that the user has permission to view
	 */
	public int filterCountByUserId(long userId);

	/**
	 * Returns all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth users
	 */
	public java.util.List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId);

	/**
	 * Returns a range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	public java.util.List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByOAuthApplicationId_First(
			long oAuthApplicationId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByOAuthApplicationId_First(
		long oAuthApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByOAuthApplicationId_Last(
			long oAuthApplicationId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByOAuthApplicationId_Last(
		long oAuthApplicationId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser[] findByOAuthApplicationId_PrevAndNext(
			long oAuthUserId, long oAuthApplicationId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Returns all the o auth users that the user has permission to view where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByOAuthApplicationId(
		long oAuthApplicationId);

	/**
	 * Returns a range of all the o auth users that the user has permission to view where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByOAuthApplicationId(
		long oAuthApplicationId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth users that the user has permissions to view where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users that the user has permission to view
	 */
	public java.util.List<OAuthUser> filterFindByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set of o auth users that the user has permission to view where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser[] filterFindByOAuthApplicationId_PrevAndNext(
			long oAuthUserId, long oAuthApplicationId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
				orderByComparator)
		throws NoSuchUserException;

	/**
	 * Removes all the o auth users where oAuthApplicationId = &#63; from the database.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 */
	public void removeByOAuthApplicationId(long oAuthApplicationId);

	/**
	 * Returns the number of o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	public int countByOAuthApplicationId(long oAuthApplicationId);

	/**
	 * Returns the number of o auth users that the user has permission to view where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users that the user has permission to view
	 */
	public int filterCountByOAuthApplicationId(long oAuthApplicationId);

	/**
	 * Returns the o auth user where accessToken = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByAccessToken(String accessToken)
		throws NoSuchUserException;

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByAccessToken(String accessToken);

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accessToken the access token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByAccessToken(
		String accessToken, boolean useFinderCache);

	/**
	 * Removes the o auth user where accessToken = &#63; from the database.
	 *
	 * @param accessToken the access token
	 * @return the o auth user that was removed
	 */
	public OAuthUser removeByAccessToken(String accessToken)
		throws NoSuchUserException;

	/**
	 * Returns the number of o auth users where accessToken = &#63;.
	 *
	 * @param accessToken the access token
	 * @return the number of matching o auth users
	 */
	public int countByAccessToken(String accessToken);

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	public OAuthUser findByU_OAI(long userId, long oAuthApplicationId)
		throws NoSuchUserException;

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByU_OAI(long userId, long oAuthApplicationId);

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	public OAuthUser fetchByU_OAI(
		long userId, long oAuthApplicationId, boolean useFinderCache);

	/**
	 * Removes the o auth user where userId = &#63; and oAuthApplicationId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the o auth user that was removed
	 */
	public OAuthUser removeByU_OAI(long userId, long oAuthApplicationId)
		throws NoSuchUserException;

	/**
	 * Returns the number of o auth users where userId = &#63; and oAuthApplicationId = &#63;.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	public int countByU_OAI(long userId, long oAuthApplicationId);

	/**
	 * Caches the o auth user in the entity cache if it is enabled.
	 *
	 * @param oAuthUser the o auth user
	 */
	public void cacheResult(OAuthUser oAuthUser);

	/**
	 * Caches the o auth users in the entity cache if it is enabled.
	 *
	 * @param oAuthUsers the o auth users
	 */
	public void cacheResult(java.util.List<OAuthUser> oAuthUsers);

	/**
	 * Creates a new o auth user with the primary key. Does not add the o auth user to the database.
	 *
	 * @param oAuthUserId the primary key for the new o auth user
	 * @return the new o auth user
	 */
	public OAuthUser create(long oAuthUserId);

	/**
	 * Removes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user that was removed
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser remove(long oAuthUserId) throws NoSuchUserException;

	public OAuthUser updateImpl(OAuthUser oAuthUser);

	/**
	 * Returns the o auth user with the primary key or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	public OAuthUser findByPrimaryKey(long oAuthUserId)
		throws NoSuchUserException;

	/**
	 * Returns the o auth user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user, or <code>null</code> if a o auth user with the primary key could not be found
	 */
	public OAuthUser fetchByPrimaryKey(long oAuthUserId);

	/**
	 * Returns all the o auth users.
	 *
	 * @return the o auth users
	 */
	public java.util.List<OAuthUser> findAll();

	/**
	 * Returns a range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of o auth users
	 */
	public java.util.List<OAuthUser> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth users
	 */
	public java.util.List<OAuthUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth users
	 */
	public java.util.List<OAuthUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuthUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the o auth users from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of o auth users.
	 *
	 * @return the number of o auth users
	 */
	public int countAll();

}