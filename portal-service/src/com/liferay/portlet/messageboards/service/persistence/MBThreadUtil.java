/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.messageboards.model.MBThread;

import java.util.List;

/**
 * The persistence utility for the message boards thread service. This utility wraps {@link MBThreadPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadPersistence
 * @see MBThreadPersistenceImpl
 * @generated
 */
public class MBThreadUtil {
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
	public static void clearCache(MBThread mbThread) {
		getPersistence().clearCache(mbThread);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBThread> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBThread> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBThread> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MBThread update(MBThread mbThread) throws SystemException {
		return getPersistence().update(mbThread);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MBThread update(MBThread mbThread,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbThread, serviceContext);
	}

	/**
	* Returns all the message boards threads where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the message boards threads where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByUuid_PrevAndNext(
		long threadId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByUuid_PrevAndNext(threadId, uuid, orderByComparator);
	}

	/**
	* Removes all the message boards threads where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of message boards threads where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the message boards thread where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the message boards thread where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards thread that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of message boards threads where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the message boards threads where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByUuid_C_PrevAndNext(
		long threadId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(threadId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of message boards threads where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the message boards threads where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByGroupId_PrevAndNext(
		long threadId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(threadId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByGroupId_PrevAndNext(
		long threadId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(threadId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the message boards thread where rootMessageId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadException} if it could not be found.
	*
	* @param rootMessageId the root message ID
	* @return the matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByRootMessageId(
		long rootMessageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByRootMessageId(rootMessageId);
	}

	/**
	* Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param rootMessageId the root message ID
	* @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByRootMessageId(
		long rootMessageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByRootMessageId(rootMessageId);
	}

	/**
	* Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param rootMessageId the root message ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByRootMessageId(
		long rootMessageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRootMessageId(rootMessageId, retrieveFromCache);
	}

	/**
	* Removes the message boards thread where rootMessageId = &#63; from the database.
	*
	* @param rootMessageId the root message ID
	* @return the message boards thread that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread removeByRootMessageId(
		long rootMessageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().removeByRootMessageId(rootMessageId);
	}

	/**
	* Returns the number of message boards threads where rootMessageId = &#63;.
	*
	* @param rootMessageId the root message ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRootMessageId(long rootMessageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRootMessageId(rootMessageId);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, categoryId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_PrevAndNext(threadId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C(groupId, categoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(threadId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryIds);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long[] categoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryIds, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long[] categoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C(groupId, categoryIds, start, end,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryIds);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryIds, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, categoryIds, start, end,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryIds);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C(groupId, categoryIds);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotC(groupId, categoryId);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotC(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotC(groupId, categoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_NotC_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_PrevAndNext(threadId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_NotC(groupId, categoryId);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC(groupId, categoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_NotC_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_NotC_PrevAndNext(threadId, groupId,
			categoryId, orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_NotC(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_NotC(groupId, categoryId);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_NotC(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_NotC(groupId, categoryId);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_NotC(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_NotC(groupId, categoryId);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_S_PrevAndNext(
		long threadId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_S_PrevAndNext(threadId, groupId, status,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_S(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_S_PrevAndNext(
		long threadId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_S_PrevAndNext(threadId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_S(groupId, status);
	}

	/**
	* Returns all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(categoryId, priority);
	}

	/**
	* Returns a range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(categoryId, priority, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(categoryId, priority, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByC_P_First(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_First(categoryId, priority, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByC_P_First(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_P_First(categoryId, priority, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByC_P_Last(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_Last(categoryId, priority, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByC_P_Last(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_P_Last(categoryId, priority, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param categoryId the category ID
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByC_P_PrevAndNext(
		long threadId, long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByC_P_PrevAndNext(threadId, categoryId, priority,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where categoryId = &#63; and priority = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(categoryId, priority);
	}

	/**
	* Returns the number of message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID
	* @param priority the priority
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(categoryId, priority);
	}

	/**
	* Returns all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByL_P(
		java.util.Date lastPostDate, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_P(lastPostDate, priority);
	}

	/**
	* Returns a range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByL_P(
		java.util.Date lastPostDate, double priority, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_P(lastPostDate, priority, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByL_P(
		java.util.Date lastPostDate, double priority, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P(lastPostDate, priority, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByL_P_First(
		java.util.Date lastPostDate, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByL_P_First(lastPostDate, priority, orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByL_P_First(
		java.util.Date lastPostDate, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByL_P_First(lastPostDate, priority, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByL_P_Last(
		java.util.Date lastPostDate, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByL_P_Last(lastPostDate, priority, orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByL_P_Last(
		java.util.Date lastPostDate, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByL_P_Last(lastPostDate, priority, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param lastPostDate the last post date
	* @param priority the priority
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByL_P_PrevAndNext(
		long threadId, java.util.Date lastPostDate, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByL_P_PrevAndNext(threadId, lastPostDate, priority,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where lastPostDate = &#63; and priority = &#63; from the database.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByL_P(java.util.Date lastPostDate, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByL_P(lastPostDate, priority);
	}

	/**
	* Returns the number of message boards threads where lastPostDate = &#63; and priority = &#63;.
	*
	* @param lastPostDate the last post date
	* @param priority the priority
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByL_P(java.util.Date lastPostDate, double priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByL_P(lastPostDate, priority);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_L(groupId, categoryId, lastPostDate);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_L(groupId, categoryId, lastPostDate, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_L(groupId, categoryId, lastPostDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_L_First(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_First(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_L_First(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_L_First(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_L_Last(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_Last(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_L_Last(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_L_Last(groupId, categoryId, lastPostDate,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_L_PrevAndNext(
		long threadId, long groupId, long categoryId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_L_PrevAndNext(threadId, groupId, categoryId,
			lastPostDate, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_L(groupId, categoryId, lastPostDate);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_L(groupId, categoryId, lastPostDate, start,
			end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_L(groupId, categoryId, lastPostDate, start,
			end, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_L_PrevAndNext(
		long threadId, long groupId, long categoryId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_C_L_PrevAndNext(threadId, groupId,
			categoryId, lastPostDate, orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_L(groupId, categoryId, lastPostDate);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_L(groupId, categoryId, lastPostDate);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param lastPostDate the last post date
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_C_L(groupId, categoryId, lastPostDate);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_S_PrevAndNext(threadId, groupId, categoryId,
			status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_C_S_PrevAndNext(threadId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C_S(groupId, categoryIds, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryIds, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryIds, status, start, end,
			orderByComparator);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_S(groupId, categoryIds, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryIds, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryIds, status, start, end,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_S(long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_S(groupId, categoryIds, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_S(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_S(long groupId, long[] categoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C_S(groupId, categoryIds, status);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_NotS(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_NotS(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_NotS(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_NotS_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_NotS_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_NotS_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_NotS_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_C_NotS_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_NotS_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_C_NotS_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_NotS_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_C_NotS_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_C_NotS_PrevAndNext(threadId, groupId, categoryId,
			status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C_NotS(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_NotS(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_NotS(groupId, categoryId, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_NotS_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_C_NotS_PrevAndNext(threadId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_NotS(groupId, categoryIds, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_NotS(groupId, categoryIds, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_NotS(groupId, categoryIds, status, start,
			end, orderByComparator);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_NotS(groupId, categoryIds, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_NotS(groupId, categoryIds, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_NotS(groupId, categoryIds, status, start, end,
			orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_NotS(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_NotS(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_NotS(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_NotS(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_NotS(long groupId, long[] categoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_NotS(groupId, categoryIds, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_NotS(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_C_NotS(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryIds the category IDs
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_NotS(long groupId, long[] categoryIds,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_C_NotS(groupId, categoryIds, status);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotC_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotC_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotC_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_NotC_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_S_PrevAndNext(threadId, groupId, categoryId,
			status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_NotC_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC_S(groupId, categoryId, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_NotC_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_NotC_S_PrevAndNext(threadId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_NotC_S(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_NotC_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_NotC_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_NotC_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_NotC_S(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_NotC_S(groupId, categoryId, status);
	}

	/**
	* Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotC_NotS(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotC_NotS(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotC_NotS(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_NotS_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_NotS_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_NotS_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_NotS_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByG_NotC_NotS_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_NotS_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByG_NotC_NotS_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_NotC_NotS_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] findByG_NotC_NotS_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .findByG_NotC_NotS_PrevAndNext(threadId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC_NotS(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC_NotS(groupId, categoryId, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_NotC_NotS(groupId, categoryId, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_NotC_NotS_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence()
				   .filterFindByG_NotC_NotS_PrevAndNext(threadId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_NotC_NotS(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_NotC_NotS(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_NotC_NotS(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_NotC_NotS(groupId, categoryId, status);
	}

	/**
	* Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message boards threads that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_NotC_NotS(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_NotC_NotS(groupId, categoryId, status);
	}

	/**
	* Caches the message boards thread in the entity cache if it is enabled.
	*
	* @param mbThread the message boards thread
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		getPersistence().cacheResult(mbThread);
	}

	/**
	* Caches the message boards threads in the entity cache if it is enabled.
	*
	* @param mbThreads the message boards threads
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBThread> mbThreads) {
		getPersistence().cacheResult(mbThreads);
	}

	/**
	* Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	*
	* @param threadId the primary key for the new message boards thread
	* @return the new message boards thread
	*/
	public static com.liferay.portlet.messageboards.model.MBThread create(
		long threadId) {
		return getPersistence().create(threadId);
	}

	/**
	* Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread remove(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().remove(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateImpl(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbThread);
	}

	/**
	* Returns the message boards thread with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadException} if it could not be found.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread findByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByPrimaryKey(threadId);
	}

	/**
	* Returns the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(threadId);
	}

	/**
	* Returns all the message boards threads.
	*
	* @return the message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message boards threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message boards threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards threads from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards threads.
	*
	* @return the number of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBThreadPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName());

			ReferenceRegistry.registerReference(MBThreadUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(MBThreadPersistence persistence) {
	}

	private static MBThreadPersistence _persistence;
}