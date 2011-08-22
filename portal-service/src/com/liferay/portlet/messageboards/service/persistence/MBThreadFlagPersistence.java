/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBThreadFlag;

/**
 * The persistence interface for the message boards thread flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagPersistenceImpl
 * @see MBThreadFlagUtil
 * @generated
 */
public interface MBThreadFlagPersistence extends BasePersistence<MBThreadFlag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBThreadFlagUtil} to access the message boards thread flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards thread flag in the entity cache if it is enabled.
	*
	* @param mbThreadFlag the message boards thread flag
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag);

	/**
	* Caches the message boards thread flags in the entity cache if it is enabled.
	*
	* @param mbThreadFlags the message boards thread flags
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> mbThreadFlags);

	/**
	* Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	*
	* @param threadFlagId the primary key for the new message boards thread flag
	* @return the new message boards thread flag
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag create(
		long threadFlagId);

	/**
	* Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag remove(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	public com.liferay.portlet.messageboards.model.MBThreadFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards thread flag with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByPrimaryKey(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag fetchByPrimaryKey(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards thread flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards thread flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards thread flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag[] findByUserId_PrevAndNext(
		long threadFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns all the message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards thread flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag[] findByThreadId_PrevAndNext(
		long threadFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag findByU_T(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag fetchByU_T(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThreadFlag fetchByU_T(
		long userId, long threadId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards thread flags.
	*
	* @return the message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards thread flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards thread flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards thread flags where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards thread flags where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards thread flag where userId = &#63; and threadId = &#63; from the database.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_T(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException;

	/**
	* Removes all the message boards thread flags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards thread flags where userId = &#63; and threadId = &#63;.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_T(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards thread flags.
	*
	* @return the number of message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public MBThreadFlag remove(MBThreadFlag mbThreadFlag)
		throws SystemException;
}