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

import com.liferay.portlet.messageboards.model.MBBan;

/**
 * The persistence interface for the message boards ban service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBanPersistenceImpl
 * @see MBBanUtil
 * @generated
 */
public interface MBBanPersistence extends BasePersistence<MBBan> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBBanUtil} to access the message boards ban persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards ban in the entity cache if it is enabled.
	*
	* @param mbBan the message boards ban
	*/
	public void cacheResult(com.liferay.portlet.messageboards.model.MBBan mbBan);

	/**
	* Caches the message boards bans in the entity cache if it is enabled.
	*
	* @param mbBans the message boards bans
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBBan> mbBans);

	/**
	* Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	*
	* @param banId the primary key for the new message boards ban
	* @return the new message boards ban
	*/
	public com.liferay.portlet.messageboards.model.MBBan create(long banId);

	/**
	* Removes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan remove(long banId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	public com.liferay.portlet.messageboards.model.MBBan updateImpl(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards ban with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchBanException} if it could not be found.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByPrimaryKey(
		long banId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards ban with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban, or <code>null</code> if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan fetchByPrimaryKey(
		long banId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards bans where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards bans where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards bans where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards ban in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the last message boards ban in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banId the primary key of the current message boards ban
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan[] findByGroupId_PrevAndNext(
		long banId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns all the message boards bans where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards bans where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards bans where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards ban in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the last message boards ban in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banId the primary key of the current message boards ban
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan[] findByUserId_PrevAndNext(
		long banId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns all the message boards bans where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @return the matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards bans where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards bans where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards ban in the ordered set where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByBanUserId_First(
		long banUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the last message boards ban in the ordered set where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByBanUserId_Last(
		long banUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param banId the primary key of the current message boards ban
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan[] findByBanUserId_PrevAndNext(
		long banId, long banUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchBanException} if it could not be found.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the matching message boards ban
	* @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan findByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, long banUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards bans.
	*
	* @return the message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards bans where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards bans where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards bans where banUserId = &#63; from the database.
	*
	* @param banUserId the ban user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards ban where groupId = &#63; and banUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the message boards ban that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan removeByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	/**
	* Removes all the message boards bans from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards bans where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards bans where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards bans where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @return the number of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int countByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards bans where groupId = &#63; and banUserId = &#63;.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the number of matching message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_B(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards bans.
	*
	* @return the number of message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}