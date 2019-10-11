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

package com.liferay.message.boards.service.persistence;

import com.liferay.message.boards.exception.NoSuchStatsUserException;
import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the message boards stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserUtil
 * @generated
 */
@ProviderType
public interface MBStatsUserPersistence extends BasePersistence<MBStatsUser> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBStatsUserUtil} to access the message boards stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByGroupId(long groupId);

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser[] findByGroupId_PrevAndNext(
			long statsUserId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Removes all the message boards stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards stats users
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByUserId(long userId);

	/**
	 * Returns a range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser[] findByUserId_PrevAndNext(
			long statsUserId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Removes all the message boards stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException;

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByG_U(long groupId, long userId);

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByG_U(
		long groupId, long userId, boolean useFinderCache);

	/**
	 * Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the message boards stats user that was removed
	 */
	public MBStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException;

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	public int countByG_U(long groupId, long userId);

	/**
	 * Returns all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount);

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end);

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public java.util.List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByG_NotU_NotM_First(
			long groupId, long userId, int messageCount,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByG_NotU_NotM_First(
		long groupId, long userId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public MBStatsUser findByG_NotU_NotM_Last(
			long groupId, long userId, int messageCount,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public MBStatsUser fetchByG_NotU_NotM_Last(
		long groupId, long userId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser[] findByG_NotU_NotM_PrevAndNext(
			long statsUserId, long groupId, long userId, int messageCount,
			com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
				orderByComparator)
		throws NoSuchStatsUserException;

	/**
	 * Removes all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 */
	public void removeByG_NotU_NotM(
		long groupId, long userId, int messageCount);

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the number of matching message boards stats users
	 */
	public int countByG_NotU_NotM(long groupId, long userId, int messageCount);

	/**
	 * Caches the message boards stats user in the entity cache if it is enabled.
	 *
	 * @param mbStatsUser the message boards stats user
	 */
	public void cacheResult(MBStatsUser mbStatsUser);

	/**
	 * Caches the message boards stats users in the entity cache if it is enabled.
	 *
	 * @param mbStatsUsers the message boards stats users
	 */
	public void cacheResult(java.util.List<MBStatsUser> mbStatsUsers);

	/**
	 * Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	 *
	 * @param statsUserId the primary key for the new message boards stats user
	 * @return the new message boards stats user
	 */
	public MBStatsUser create(long statsUserId);

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser remove(long statsUserId) throws NoSuchStatsUserException;

	public MBStatsUser updateImpl(MBStatsUser mbStatsUser);

	/**
	 * Returns the message boards stats user with the primary key or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException;

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 */
	public MBStatsUser fetchByPrimaryKey(long statsUserId);

	/**
	 * Returns all the message boards stats users.
	 *
	 * @return the message boards stats users
	 */
	public java.util.List<MBStatsUser> findAll();

	/**
	 * Returns a range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of message boards stats users
	 */
	public java.util.List<MBStatsUser> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards stats users
	 */
	public java.util.List<MBStatsUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards stats users
	 */
	public java.util.List<MBStatsUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBStatsUser>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the message boards stats users from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of message boards stats users.
	 *
	 * @return the number of message boards stats users
	 */
	public int countAll();

}