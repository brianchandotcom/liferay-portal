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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.messageboards.model.MBThreadFlag;

import java.util.List;

/**
 * The persistence utility for the message boards thread flag service. This utility wraps {@link MBThreadFlagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagPersistence
 * @see MBThreadFlagPersistenceImpl
 * @generated
 */
public class MBThreadFlagUtil {
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
	public static void clearCache(MBThreadFlag mbThreadFlag) {
		getPersistence().clearCache(mbThreadFlag);
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
	public static List<MBThreadFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBThreadFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBThreadFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBThreadFlag update(MBThreadFlag mbThreadFlag, boolean merge)
		throws SystemException {
		return getPersistence().update(mbThreadFlag, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static MBThreadFlag update(MBThreadFlag mbThreadFlag, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbThreadFlag, merge, serviceContext);
	}

	/**
	* Caches the message boards thread flag in the entity cache if it is enabled.
	*
	* @param mbThreadFlag the message boards thread flag
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag) {
		getPersistence().cacheResult(mbThreadFlag);
	}

	/**
	* Caches the message boards thread flags in the entity cache if it is enabled.
	*
	* @param mbThreadFlags the message boards thread flags
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> mbThreadFlags) {
		getPersistence().cacheResult(mbThreadFlags);
	}

	/**
	* Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	*
	* @param threadFlagId the primary key for the new message boards thread flag
	* @return the new message boards thread flag
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag create(
		long threadFlagId) {
		return getPersistence().create(threadFlagId);
	}

	/**
	* Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag remove(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().remove(threadFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBThreadFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbThreadFlag, merge);
	}

	/**
	* Returns the message boards thread flag with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByPrimaryKey(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByPrimaryKey(threadFlagId);
	}

	/**
	* Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag fetchByPrimaryKey(
		long threadFlagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(threadFlagId);
	}

	/**
	* Returns all the message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag[] findByUserId_PrevAndNext(
		long threadFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence()
				   .findByUserId_PrevAndNext(threadFlagId, userId,
			orderByComparator);
	}

	/**
	* Returns all the message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId, start, end);
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByThreadId(threadId, start, end, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByThreadId_First(threadId, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByThreadId_Last(threadId, orderByComparator);
	}

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
	public static com.liferay.portlet.messageboards.model.MBThreadFlag[] findByThreadId_PrevAndNext(
		long threadFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(threadFlagId, threadId,
			orderByComparator);
	}

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag
	* @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag findByU_T(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().findByU_T(userId, threadId);
	}

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag fetchByU_T(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, threadId);
	}

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag fetchByU_T(
		long userId, long threadId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, threadId, retrieveFromCache);
	}

	/**
	* Returns all the message boards thread flags.
	*
	* @return the message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThreadFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards thread flags where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the message boards thread flags where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	/**
	* Removes the message boards thread flag where userId = &#63; and threadId = &#63; from the database.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the message boards thread flag that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThreadFlag removeByU_T(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadFlagException {
		return getPersistence().removeByU_T(userId, threadId);
	}

	/**
	* Removes all the message boards thread flags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	/**
	* Returns the number of message boards thread flags where userId = &#63; and threadId = &#63;.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_T(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_T(userId, threadId);
	}

	/**
	* Returns the number of message boards thread flags.
	*
	* @return the number of message boards thread flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBThreadFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBThreadFlagPersistence)PortalBeanLocatorUtil.locate(MBThreadFlagPersistence.class.getName());

			ReferenceRegistry.registerReference(MBThreadFlagUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(MBThreadFlagPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(MBThreadFlagUtil.class,
			"_persistence");
	}

	private static MBThreadFlagPersistence _persistence;
}