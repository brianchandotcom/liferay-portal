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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.messageboards.model.MBMessageFlag;

import java.util.List;

/**
 * The persistence utility for the message boards message flag service. This utility wraps {@link MBMessageFlagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageFlagPersistence
 * @see MBMessageFlagPersistenceImpl
 * @generated
 */
public class MBMessageFlagUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(MBMessageFlag mbMessageFlag) {
		getPersistence().clearCache(mbMessageFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBMessageFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBMessageFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBMessageFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static MBMessageFlag remove(MBMessageFlag mbMessageFlag)
		throws SystemException {
		return getPersistence().remove(mbMessageFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBMessageFlag update(MBMessageFlag mbMessageFlag,
		boolean merge) throws SystemException {
		return getPersistence().update(mbMessageFlag, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static MBMessageFlag update(MBMessageFlag mbMessageFlag,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbMessageFlag, merge, serviceContext);
	}

	/**
	* Caches the message boards message flag in the entity cache if it is enabled.
	*
	* @param mbMessageFlag the message boards message flag
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag) {
		getPersistence().cacheResult(mbMessageFlag);
	}

	/**
	* Caches the message boards message flags in the entity cache if it is enabled.
	*
	* @param mbMessageFlags the message boards message flags
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> mbMessageFlags) {
		getPersistence().cacheResult(mbMessageFlags);
	}

	/**
	* Creates a new message boards message flag with the primary key. Does not add the message boards message flag to the database.
	*
	* @param messageFlagId the primary key for the new message boards message flag
	* @return the new message boards message flag
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag create(
		long messageFlagId) {
		return getPersistence().create(messageFlagId);
	}

	/**
	* Removes the message boards message flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageFlagId the primary key of the message boards message flag
	* @return the message boards message flag that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().remove(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbMessageFlag, merge);
	}

	/**
	* Returns the message boards message flag with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageFlagException} if it could not be found.
	*
	* @param messageFlagId the primary key of the message boards message flag
	* @return the message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByPrimaryKey(messageFlagId);
	}

	/**
	* Returns the message boards message flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param messageFlagId the primary key of the message boards message flag
	* @return the message boards message flag, or <code>null</code> if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(messageFlagId);
	}

	/**
	* Returns all the message boards message flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the message boards message flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByUserId_PrevAndNext(
		long messageFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByUserId_PrevAndNext(messageFlagId, userId,
			orderByComparator);
	}

	/**
	* Returns all the message boards message flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

	/**
	* Returns a range of all the message boards message flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByThreadId(threadId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_First(threadId, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_Last(threadId, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByThreadId_PrevAndNext(
		long messageFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(messageFlagId, threadId,
			orderByComparator);
	}

	/**
	* Returns all the message boards message flags where messageId = &#63;.
	*
	* @param messageId the message ID
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByMessageId(messageId);
	}

	/**
	* Returns a range of all the message boards message flags where messageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByMessageId(messageId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where messageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByMessageId(messageId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where messageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_First(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_First(messageId, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where messageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_Last(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_Last(messageId, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where messageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param messageId the message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByMessageId_PrevAndNext(
		long messageFlagId, long messageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_PrevAndNext(messageFlagId, messageId,
			orderByComparator);
	}

	/**
	* Returns all the message boards message flags where threadId = &#63; and flag = &#63;.
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_F(threadId, flag);
	}

	/**
	* Returns a range of all the message boards message flags where threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_F(threadId, flag, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_F(threadId, flag, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_First(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByT_F_First(threadId, flag, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_Last(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByT_F_Last(threadId, flag, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_F_PrevAndNext(
		long messageFlagId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByT_F_PrevAndNext(messageFlagId, threadId, flag,
			orderByComparator);
	}

	/**
	* Returns all the message boards message flags where messageId = &#63; and flag = &#63;.
	*
	* @param messageId the message ID
	* @param flag the flag
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByM_F(messageId, flag);
	}

	/**
	* Returns a range of all the message boards message flags where messageId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByM_F(messageId, flag, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where messageId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByM_F(messageId, flag, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_First(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_First(messageId, flag, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageId the message ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_Last(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_Last(messageId, flag, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param messageId the message ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByM_F_PrevAndNext(
		long messageFlagId, long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_PrevAndNext(messageFlagId, messageId, flag,
			orderByComparator);
	}

	/**
	* Returns all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @return the matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag);
	}

	/**
	* Returns a range of all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag, start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_T_F(userId, threadId, flag, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_First(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_First(userId, threadId, flag, orderByComparator);
	}

	/**
	* Returns the last message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_Last(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_Last(userId, threadId, flag, orderByComparator);
	}

	/**
	* Returns the message boards message flags before and after the current message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param messageFlagId the primary key of the current message boards message flag
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByU_T_F_PrevAndNext(
		long messageFlagId, long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_PrevAndNext(messageFlagId, userId, threadId,
			flag, orderByComparator);
	}

	/**
	* Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param messageId the message ID
	* @param flag the flag
	* @return the matching message boards message flag
	* @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByU_M_F(userId, messageId, flag);
	}

	/**
	* Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param messageId the message ID
	* @param flag the flag
	* @return the matching message boards message flag, or <code>null</code> if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_M_F(userId, messageId, flag);
	}

	/**
	* Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param messageId the message ID
	* @param flag the flag
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards message flag, or <code>null</code> if a matching message boards message flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_M_F(userId, messageId, flag, retrieveFromCache);
	}

	/**
	* Returns all the message boards message flags.
	*
	* @return the message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message boards message flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @return the range of message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message boards message flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards message flags
	* @param end the upper bound of the range of message boards message flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards message flags where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the message boards message flags where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	/**
	* Removes all the message boards message flags where messageId = &#63; from the database.
	*
	* @param messageId the message ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByMessageId(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByMessageId(messageId);
	}

	/**
	* Removes all the message boards message flags where threadId = &#63; and flag = &#63; from the database.
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_F(long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_F(threadId, flag);
	}

	/**
	* Removes all the message boards message flags where messageId = &#63; and flag = &#63; from the database.
	*
	* @param messageId the message ID
	* @param flag the flag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByM_F(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByM_F(messageId, flag);
	}

	/**
	* Removes all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63; from the database.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_T_F(userId, threadId, flag);
	}

	/**
	* Removes the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; from the database.
	*
	* @param userId the user ID
	* @param messageId the message ID
	* @param flag the flag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		getPersistence().removeByU_M_F(userId, messageId, flag);
	}

	/**
	* Removes all the message boards message flags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards message flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of message boards message flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	/**
	* Returns the number of message boards message flags where messageId = &#63;.
	*
	* @param messageId the message ID
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByMessageId(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByMessageId(messageId);
	}

	/**
	* Returns the number of message boards message flags where threadId = &#63; and flag = &#63;.
	*
	* @param threadId the thread ID
	* @param flag the flag
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_F(long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_F(threadId, flag);
	}

	/**
	* Returns the number of message boards message flags where messageId = &#63; and flag = &#63;.
	*
	* @param messageId the message ID
	* @param flag the flag
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByM_F(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByM_F(messageId, flag);
	}

	/**
	* Returns the number of message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param flag the flag
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_T_F(userId, threadId, flag);
	}

	/**
	* Returns the number of message boards message flags where userId = &#63; and messageId = &#63; and flag = &#63;.
	*
	* @param userId the user ID
	* @param messageId the message ID
	* @param flag the flag
	* @return the number of matching message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_M_F(userId, messageId, flag);
	}

	/**
	* Returns the number of message boards message flags.
	*
	* @return the number of message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBMessageFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMessageFlagPersistence)PortalBeanLocatorUtil.locate(MBMessageFlagPersistence.class.getName());

			ReferenceRegistry.registerReference(MBMessageFlagUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(MBMessageFlagPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(MBMessageFlagUtil.class,
			"_persistence");
	}

	private static MBMessageFlagPersistence _persistence;
}