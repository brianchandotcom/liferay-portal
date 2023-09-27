/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence;

import com.liferay.osb.faro.exception.NoSuchFaroUserException;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the faro user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @see FaroUserUtil
 * @generated
 */
@ProviderType
public interface FaroUserPersistence extends BasePersistence<FaroUser> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FaroUserUtil} to access the faro user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the faro users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro users
	 */
	public java.util.List<FaroUser> findByGroupId(long groupId);

	/**
	 * Returns a range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	public java.util.List<FaroUser> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser[] findByGroupId_PrevAndNext(
			long faroUserId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Removes all the faro users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of faro users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro users
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the faro user where key = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param key the key
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByKey(String key) throws NoSuchFaroUserException;

	/**
	 * Returns the faro user where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByKey(String key);

	/**
	 * Returns the faro user where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByKey(String key, boolean useFinderCache);

	/**
	 * Removes the faro user where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the faro user that was removed
	 */
	public FaroUser removeByKey(String key) throws NoSuchFaroUserException;

	/**
	 * Returns the number of faro users where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching faro users
	 */
	public int countByKey(String key);

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_L(long groupId, long liveUserId)
		throws NoSuchFaroUserException;

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_L(long groupId, long liveUserId);

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_L(
		long groupId, long liveUserId, boolean useFinderCache);

	/**
	 * Removes the faro user where groupId = &#63; and liveUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the faro user that was removed
	 */
	public FaroUser removeByG_L(long groupId, long liveUserId)
		throws NoSuchFaroUserException;

	/**
	 * Returns the number of faro users where groupId = &#63; and liveUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the number of matching faro users
	 */
	public int countByG_L(long groupId, long liveUserId);

	/**
	 * Returns all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching faro users
	 */
	public java.util.List<FaroUser> findByG_R(long groupId, long roleId);

	/**
	 * Returns a range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_R_First(
			long groupId, long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_R_First(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_R_Last(
			long groupId, long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_R_Last(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser[] findByG_R_PrevAndNext(
			long faroUserId, long groupId, long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Removes all the faro users where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 */
	public void removeByG_R(long groupId, long roleId);

	/**
	 * Returns the number of faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching faro users
	 */
	public int countByG_R(long groupId, long roleId);

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_E(long groupId, String emailAddress)
		throws NoSuchFaroUserException;

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_E(long groupId, String emailAddress);

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_E(
		long groupId, String emailAddress, boolean useFinderCache);

	/**
	 * Removes the faro user where groupId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the faro user that was removed
	 */
	public FaroUser removeByG_E(long groupId, String emailAddress)
		throws NoSuchFaroUserException;

	/**
	 * Returns the number of faro users where groupId = &#63; and emailAddress = &#63;.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the number of matching faro users
	 */
	public int countByG_E(long groupId, String emailAddress);

	/**
	 * Returns all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching faro users
	 */
	public java.util.List<FaroUser> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser[] findByG_S_PrevAndNext(
			long faroUserId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Removes all the faro users where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of faro users where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching faro users
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @return the matching faro users
	 */
	public java.util.List<FaroUser> findByL_S(long liveUserId, int status);

	/**
	 * Returns a range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	public java.util.List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByL_S_First(
			long liveUserId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the first faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByL_S_First(
		long liveUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the last faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByL_S_Last(
			long liveUserId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the last faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByL_S_Last(
		long liveUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser[] findByL_S_PrevAndNext(
			long faroUserId, long liveUserId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Removes all the faro users where liveUserId = &#63; and status = &#63; from the database.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 */
	public void removeByL_S(long liveUserId, int status);

	/**
	 * Returns the number of faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @return the number of matching faro users
	 */
	public int countByL_S(long liveUserId, int status);

	/**
	 * Returns all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @return the matching faro users
	 */
	public java.util.List<FaroUser> findByE_S(String emailAddress, int status);

	/**
	 * Returns a range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	public java.util.List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end);

	/**
	 * Returns an ordered range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	public java.util.List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByE_S_First(
			String emailAddress, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the first faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByE_S_First(
		String emailAddress, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the last faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	public FaroUser findByE_S_Last(
			String emailAddress, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Returns the last faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	public FaroUser fetchByE_S_Last(
		String emailAddress, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser[] findByE_S_PrevAndNext(
			long faroUserId, String emailAddress, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
				orderByComparator)
		throws NoSuchFaroUserException;

	/**
	 * Removes all the faro users where emailAddress = &#63; and status = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 */
	public void removeByE_S(String emailAddress, int status);

	/**
	 * Returns the number of faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @return the number of matching faro users
	 */
	public int countByE_S(String emailAddress, int status);

	/**
	 * Caches the faro user in the entity cache if it is enabled.
	 *
	 * @param faroUser the faro user
	 */
	public void cacheResult(FaroUser faroUser);

	/**
	 * Caches the faro users in the entity cache if it is enabled.
	 *
	 * @param faroUsers the faro users
	 */
	public void cacheResult(java.util.List<FaroUser> faroUsers);

	/**
	 * Creates a new faro user with the primary key. Does not add the faro user to the database.
	 *
	 * @param faroUserId the primary key for the new faro user
	 * @return the new faro user
	 */
	public FaroUser create(long faroUserId);

	/**
	 * Removes the faro user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user that was removed
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser remove(long faroUserId) throws NoSuchFaroUserException;

	public FaroUser updateImpl(FaroUser faroUser);

	/**
	 * Returns the faro user with the primary key or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	public FaroUser findByPrimaryKey(long faroUserId)
		throws NoSuchFaroUserException;

	/**
	 * Returns the faro user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user, or <code>null</code> if a faro user with the primary key could not be found
	 */
	public FaroUser fetchByPrimaryKey(long faroUserId);

	/**
	 * Returns all the faro users.
	 *
	 * @return the faro users
	 */
	public java.util.List<FaroUser> findAll();

	/**
	 * Returns a range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of faro users
	 */
	public java.util.List<FaroUser> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro users
	 */
	public java.util.List<FaroUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro users
	 */
	public java.util.List<FaroUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro users from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of faro users.
	 *
	 * @return the number of faro users
	 */
	public int countAll();

}