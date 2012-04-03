/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBStatsUser;

/**
 * The persistence interface for the message boards stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserPersistenceImpl
 * @see MBStatsUserUtil
 * @generated
 */
public interface MBStatsUserPersistence extends BasePersistence<MBStatsUser> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBStatsUserUtil} to access the message boards stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards stats user in the entity cache if it is enabled.
	*
	* @param mbStatsUser the message boards stats user
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser);

	/**
	* Caches the message boards stats users in the entity cache if it is enabled.
	*
	* @param mbStatsUsers the message boards stats users
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> mbStatsUsers);

	/**
	* Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	*
	* @param statsUserId the primary key for the new message boards stats user
	* @return the new message boards stats user
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser create(
		long statsUserId);

	/**
	* Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser remove(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	public com.liferay.portlet.messageboards.model.MBStatsUser updateImpl(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards stats user with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser fetchByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards stats users where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the last message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByGroupId_PrevAndNext(
		long statsUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns all the message boards stats users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the last message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByUserId_PrevAndNext(
		long statsUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByG_NotU_NotM_First(
		long groupId, long userId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser findByG_NotU_NotM_Last(
		long groupId, long userId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser[] findByG_NotU_NotM_PrevAndNext(
		long statsUserId, long groupId, long userId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Returns all the message boards stats users.
	*
	* @return the message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards stats users where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards stats users where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the message boards stats user that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser removeByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException;

	/**
	* Removes all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_NotU_NotM(long groupId, long userId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards stats users from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards stats users where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards stats users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards stats users where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param messageCount the message count
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_NotU_NotM(long groupId, long userId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards stats users.
	*
	* @return the number of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}