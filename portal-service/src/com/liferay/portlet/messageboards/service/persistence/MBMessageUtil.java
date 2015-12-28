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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.List;

/**
 * The persistence utility for the message-boards message service. This utility wraps {@link com.liferay.portlet.messageboards.service.persistence.impl.MBMessagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessagePersistence
 * @see com.liferay.portlet.messageboards.service.persistence.impl.MBMessagePersistenceImpl
 * @generated
 */
@ProviderType
public class MBMessageUtil {
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
	public static void clearCache(MBMessage mbMessage) {
		getPersistence().clearCache(mbMessage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MBMessage update(MBMessage mbMessage) {
		return getPersistence().update(mbMessage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MBMessage update(MBMessage mbMessage,
		ServiceContext serviceContext) {
		return getPersistence().update(mbMessage, serviceContext);
	}

	/**
	* Returns all the message-boards messages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the message-boards messages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUuid_First(java.lang.String uuid,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUuid_Last(java.lang.String uuid,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByUuid_PrevAndNext(long messageId,
		java.lang.String uuid, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(messageId, uuid, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of message-boards messages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message-boards messages
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the message-boards message where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchMessageException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the message-boards message where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message-boards message that was removed
	*/
	public static MBMessage removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of message-boards messages where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message-boards messages
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the message-boards messages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByUuid_C_PrevAndNext(long messageId,
		java.lang.String uuid, long companyId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(messageId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of message-boards messages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message-boards messages
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByGroupId_First(long groupId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByGroupId_First(long groupId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByGroupId_Last(long groupId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByGroupId_Last(long groupId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByGroupId_PrevAndNext(long messageId,
		long groupId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(messageId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByGroupId_PrevAndNext(long messageId,
		long groupId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(messageId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message-boards messages
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the message-boards messages where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the message-boards messages where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByCompanyId_First(long companyId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByCompanyId_First(long companyId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByCompanyId_Last(long companyId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByCompanyId_Last(long companyId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByCompanyId_PrevAndNext(long messageId,
		long companyId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(messageId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of message-boards messages where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching message-boards messages
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the message-boards messages where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByUserId(long userId, int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUserId(long userId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByUserId(long userId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUserId_First(long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUserId_First(long userId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByUserId_Last(long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByUserId_Last(long userId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByUserId_PrevAndNext(long messageId,
		long userId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByUserId_PrevAndNext(messageId, userId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message-boards messages
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByThreadId(long threadId) {
		return getPersistence().findByThreadId(threadId);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadId(long threadId, int start,
		int end) {
		return getPersistence().findByThreadId(threadId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadId(long threadId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByThreadId(threadId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadId(long threadId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByThreadId(threadId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByThreadId_First(long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_First(threadId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByThreadId_First(long threadId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByThreadId_First(threadId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByThreadId_Last(long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_Last(threadId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByThreadId_Last(long threadId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().fetchByThreadId_Last(threadId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByThreadId_PrevAndNext(long messageId,
		long threadId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(messageId, threadId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	*/
	public static void removeByThreadId(long threadId) {
		getPersistence().removeByThreadId(threadId);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message-boards messages
	*/
	public static int countByThreadId(long threadId) {
		return getPersistence().countByThreadId(threadId);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByThreadReplies(long threadId) {
		return getPersistence().findByThreadReplies(threadId);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadReplies(long threadId, int start,
		int end) {
		return getPersistence().findByThreadReplies(threadId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadReplies(long threadId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByThreadReplies(threadId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByThreadReplies(long threadId, int start,
		int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByThreadReplies(threadId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByThreadReplies_First(long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadReplies_First(threadId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByThreadReplies_First(long threadId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByThreadReplies_First(threadId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByThreadReplies_Last(long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadReplies_Last(threadId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByThreadReplies_Last(long threadId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByThreadReplies_Last(threadId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByThreadReplies_PrevAndNext(long messageId,
		long threadId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadReplies_PrevAndNext(messageId, threadId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	*/
	public static void removeByThreadReplies(long threadId) {
		getPersistence().removeByThreadReplies(threadId);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message-boards messages
	*/
	public static int countByThreadReplies(long threadId) {
		return getPersistence().countByThreadReplies(threadId);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_U(long groupId, long userId) {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U(long groupId, long userId,
		int start, int end) {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_U_First(long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_U_First(long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_U_Last(long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_U_PrevAndNext(long messageId,
		long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_PrevAndNext(messageId, groupId, userId,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U(long groupId, long userId) {
		return getPersistence().filterFindByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U(long groupId, long userId,
		int start, int end) {
		return getPersistence().filterFindByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U(groupId, userId, start, end,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_U_PrevAndNext(long messageId,
		long groupId, long userId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_U_PrevAndNext(messageId, groupId, userId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public static void removeByG_U(long groupId, long userId) {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching message-boards messages
	*/
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_U(long groupId, long userId) {
		return getPersistence().filterCountByG_U(groupId, userId);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_C(long groupId, long categoryId) {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C(long groupId, long categoryId,
		int start, int end) {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, categoryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, categoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_First(long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_First(long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_Last(long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_Last(long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_C_PrevAndNext(long messageId,
		long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_PrevAndNext(messageId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C(long groupId, long categoryId) {
		return getPersistence().filterFindByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C(long groupId,
		long categoryId, int start, int end) {
		return getPersistence().filterFindByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C(long groupId,
		long categoryId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C(groupId, categoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_C_PrevAndNext(long messageId,
		long groupId, long categoryId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(messageId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	*/
	public static void removeByG_C(long groupId, long categoryId) {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message-boards messages
	*/
	public static int countByG_C(long groupId, long categoryId) {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_C(long groupId, long categoryId) {
		return getPersistence().filterCountByG_C(groupId, categoryId);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_S(long groupId, int status,
		int start, int end) {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_S(long groupId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_S(long groupId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_S_First(long groupId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_S_First(long groupId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_S_Last(long groupId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_S_Last(long groupId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_S_PrevAndNext(long messageId,
		long groupId, int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_S_PrevAndNext(messageId, groupId, status,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_S(long groupId, int status) {
		return getPersistence().filterFindByG_S(groupId, status);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_S(long groupId, int status,
		int start, int end) {
		return getPersistence().filterFindByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_S(long groupId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_S(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_S_PrevAndNext(long messageId,
		long groupId, int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_S_PrevAndNext(messageId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_S(long groupId, int status) {
		return getPersistence().filterCountByG_S(groupId, status);
	}

	/**
	* Returns all the message-boards messages where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByC_S(long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	* Returns a range of all the message-boards messages where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_S(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_S(long companyId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_S(long companyId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_S_First(long companyId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_S_First(long companyId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_S_Last(long companyId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_S_Last(long companyId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByC_S_PrevAndNext(long messageId,
		long companyId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_S_PrevAndNext(messageId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	* Returns the number of message-boards messages where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long classNameId) {
		return getPersistence().findByU_C(userId, classNameId);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long classNameId,
		int start, int end) {
		return getPersistence().findByU_C(userId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long classNameId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C(userId, classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long classNameId,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C(userId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_First(long userId, long classNameId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_First(userId, classNameId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_First(long userId, long classNameId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_First(userId, classNameId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_Last(long userId, long classNameId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_Last(userId, classNameId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_Last(long userId, long classNameId,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_Last(userId, classNameId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByU_C_PrevAndNext(long messageId,
		long userId, long classNameId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_PrevAndNext(messageId, userId, classNameId,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long[] classNameIds) {
		return getPersistence().findByU_C(userId, classNameIds);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long[] classNameIds,
		int start, int end) {
		return getPersistence().findByU_C(userId, classNameIds, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long[] classNameIds,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C(userId, classNameIds, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C(long userId, long[] classNameIds,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C(userId, classNameIds, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the message-boards messages where userId = &#63; and classNameId = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	*/
	public static void removeByU_C(long userId, long classNameId) {
		getPersistence().removeByU_C(userId, classNameId);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C(long userId, long classNameId) {
		return getPersistence().countByU_C(userId, classNameId);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63;.
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C(long userId, long[] classNameIds) {
		return getPersistence().countByU_C(userId, classNameIds);
	}

	/**
	* Returns all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByC_C(long classNameId, long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_C_First(long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByC_C_PrevAndNext(long messageId,
		long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_PrevAndNext(messageId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching message-boards messages
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByT_P(long threadId, long parentMessageId) {
		return getPersistence().findByT_P(threadId, parentMessageId);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_P(long threadId,
		long parentMessageId, int start, int end) {
		return getPersistence().findByT_P(threadId, parentMessageId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_P(long threadId,
		long parentMessageId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByT_P(threadId, parentMessageId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_P(long threadId,
		long parentMessageId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_P(threadId, parentMessageId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_P_First(long threadId,
		long parentMessageId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_P_First(threadId, parentMessageId, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_P_First(long threadId,
		long parentMessageId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_P_First(threadId, parentMessageId,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_P_Last(long threadId, long parentMessageId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_P_Last(threadId, parentMessageId, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_P_Last(long threadId,
		long parentMessageId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_P_Last(threadId, parentMessageId, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByT_P_PrevAndNext(long messageId,
		long threadId, long parentMessageId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_P_PrevAndNext(messageId, threadId, parentMessageId,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; and parentMessageId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	*/
	public static void removeByT_P(long threadId, long parentMessageId) {
		getPersistence().removeByT_P(threadId, parentMessageId);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	*
	* @param threadId the thread ID
	* @param parentMessageId the parent message ID
	* @return the number of matching message-boards messages
	*/
	public static int countByT_P(long threadId, long parentMessageId) {
		return getPersistence().countByT_P(threadId, parentMessageId);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByT_A(long threadId, boolean answer) {
		return getPersistence().findByT_A(threadId, answer);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_A(long threadId, boolean answer,
		int start, int end) {
		return getPersistence().findByT_A(threadId, answer, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_A(long threadId, boolean answer,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByT_A(threadId, answer, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_A(long threadId, boolean answer,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_A(threadId, answer, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_A_First(long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_A_First(threadId, answer, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_A_First(long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_A_First(threadId, answer, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_A_Last(long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_A_Last(threadId, answer, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_A_Last(long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_A_Last(threadId, answer, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByT_A_PrevAndNext(long messageId,
		long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_A_PrevAndNext(messageId, threadId, answer,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; and answer = &#63; from the database.
	*
	* @param threadId the thread ID
	* @param answer the answer
	*/
	public static void removeByT_A(long threadId, boolean answer) {
		getPersistence().removeByT_A(threadId, answer);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63; and answer = &#63;.
	*
	* @param threadId the thread ID
	* @param answer the answer
	* @return the number of matching message-boards messages
	*/
	public static int countByT_A(long threadId, boolean answer) {
		return getPersistence().countByT_A(threadId, answer);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByT_S(long threadId, int status) {
		return getPersistence().findByT_S(threadId, status);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_S(long threadId, int status,
		int start, int end) {
		return getPersistence().findByT_S(threadId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_S(long threadId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByT_S(threadId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByT_S(long threadId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_S(threadId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_S_First(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_S_First(threadId, status, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_S_First(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_S_First(threadId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByT_S_Last(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_S_Last(threadId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByT_S_Last(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByT_S_Last(threadId, status, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByT_S_PrevAndNext(long messageId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_S_PrevAndNext(messageId, threadId, status,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	*
	* @param threadId the thread ID
	* @param status the status
	*/
	public static void removeByT_S(long threadId, int status) {
		getPersistence().removeByT_S(threadId, status);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByT_S(long threadId, int status) {
		return getPersistence().countByT_S(threadId, status);
	}

	/**
	* Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByTR_S(long threadId, int status) {
		return getPersistence().findByTR_S(threadId, status);
	}

	/**
	* Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByTR_S(long threadId, int status,
		int start, int end) {
		return getPersistence().findByTR_S(threadId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByTR_S(long threadId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByTR_S(threadId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByTR_S(long threadId, int status,
		int start, int end, OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTR_S(threadId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByTR_S_First(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByTR_S_First(threadId, status, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByTR_S_First(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByTR_S_First(threadId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByTR_S_Last(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByTR_S_Last(threadId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByTR_S_Last(long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByTR_S_Last(threadId, status, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByTR_S_PrevAndNext(long messageId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByTR_S_PrevAndNext(messageId, threadId, status,
			orderByComparator);
	}

	/**
	* Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	*
	* @param threadId the thread ID
	* @param status the status
	*/
	public static void removeByTR_S(long threadId, int status) {
		getPersistence().removeByTR_S(threadId, status);
	}

	/**
	* Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	*
	* @param threadId the thread ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByTR_S(long threadId, int status) {
		return getPersistence().countByTR_S(threadId, status);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_U_S(long groupId, long userId,
		int status) {
		return getPersistence().findByG_U_S(groupId, userId, status);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U_S(long groupId, long userId,
		int status, int start, int end) {
		return getPersistence().findByG_U_S(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_U_S_First(long groupId, long userId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_S_First(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_U_S_First(long groupId, long userId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_First(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_U_S_Last(long groupId, long userId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_S_Last(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_U_S_Last(long groupId, long userId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_Last(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_U_S_PrevAndNext(long messageId,
		long groupId, long userId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_S_PrevAndNext(messageId, groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status) {
		return getPersistence().filterFindByG_U_S(groupId, userId, status);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_S(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_S(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_U_S_PrevAndNext(long messageId,
		long groupId, long userId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_U_S_PrevAndNext(messageId, groupId, userId,
			status, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByG_U_S(long groupId, long userId, int status) {
		getPersistence().removeByG_U_S(groupId, userId, status);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByG_U_S(long groupId, long userId, int status) {
		return getPersistence().countByG_U_S(groupId, userId, status);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_U_S(long groupId, long userId, int status) {
		return getPersistence().filterCountByG_U_S(groupId, userId, status);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId) {
		return getPersistence().findByG_C_T(groupId, categoryId, threadId);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end) {
		return getPersistence()
				   .findByG_C_T(groupId, categoryId, threadId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_C_T(groupId, categoryId, threadId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_T(groupId, categoryId, threadId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_First(long groupId, long categoryId,
		long threadId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_First(groupId, categoryId, threadId,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_First(long groupId, long categoryId,
		long threadId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_First(groupId, categoryId, threadId,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_Last(long groupId, long categoryId,
		long threadId, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_Last(groupId, categoryId, threadId,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_Last(long groupId, long categoryId,
		long threadId, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_Last(groupId, categoryId, threadId,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_C_T_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_PrevAndNext(messageId, groupId, categoryId,
			threadId, orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T(long groupId,
		long categoryId, long threadId) {
		return getPersistence().filterFindByG_C_T(groupId, categoryId, threadId);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T(long groupId,
		long categoryId, long threadId, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_T(groupId, categoryId, threadId, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T(long groupId,
		long categoryId, long threadId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_T(groupId, categoryId, threadId, start,
			end, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_C_T_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_C_T_PrevAndNext(messageId, groupId,
			categoryId, threadId, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	*/
	public static void removeByG_C_T(long groupId, long categoryId,
		long threadId) {
		getPersistence().removeByG_C_T(groupId, categoryId, threadId);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @return the number of matching message-boards messages
	*/
	public static int countByG_C_T(long groupId, long categoryId, long threadId) {
		return getPersistence().countByG_C_T(groupId, categoryId, threadId);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_C_T(long groupId, long categoryId,
		long threadId) {
		return getPersistence().filterCountByG_C_T(groupId, categoryId, threadId);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status) {
		return getPersistence().findByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end) {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_S_First(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_S_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_S_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_S_Last(groupId, categoryId, status,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_C_S_PrevAndNext(long messageId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_PrevAndNext(messageId, groupId, categoryId,
			status, orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_S(long groupId,
		long categoryId, int status) {
		return getPersistence().filterFindByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_S(long groupId,
		long categoryId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_S(long groupId,
		long categoryId, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_C_S_PrevAndNext(long messageId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_C_S_PrevAndNext(messageId, groupId,
			categoryId, status, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	*/
	public static void removeByG_C_S(long groupId, long categoryId, int status) {
		getPersistence().removeByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByG_C_S(long groupId, long categoryId, int status) {
		return getPersistence().countByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param status the status
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_C_S(long groupId, long categoryId,
		int status) {
		return getPersistence().filterCountByG_C_S(groupId, categoryId, status);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK) {
		return getPersistence().findByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK, int start, int end) {
		return getPersistence()
				   .findByU_C_C(userId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C_C(userId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C(long userId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_C(userId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_C_First(long userId, long classNameId,
		long classPK, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_First(userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_C_First(long userId, long classNameId,
		long classPK, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_First(userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_C_Last(long userId, long classNameId,
		long classPK, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_Last(userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_C_Last(long userId, long classNameId,
		long classPK, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_Last(userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByU_C_C_PrevAndNext(long messageId,
		long userId, long classNameId, long classPK,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_PrevAndNext(messageId, userId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByU_C_C(long userId, long classNameId, long classPK) {
		getPersistence().removeByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C_C(long userId, long classNameId, long classPK) {
		return getPersistence().countByU_C_C(userId, classNameId, classPK);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status) {
		return getPersistence().findByU_C_S(userId, classNameId, status);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status, int start, int end) {
		return getPersistence()
				   .findByU_C_S(userId, classNameId, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C_S(userId, classNameId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long classNameId,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_S(userId, classNameId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_S_First(long userId, long classNameId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_S_First(userId, classNameId, status,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_S_First(long userId, long classNameId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_S_First(userId, classNameId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_S_Last(long userId, long classNameId,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_S_Last(userId, classNameId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_S_Last(long userId, long classNameId,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_S_Last(userId, classNameId, status,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByU_C_S_PrevAndNext(long messageId,
		long userId, long classNameId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_S_PrevAndNext(messageId, userId, classNameId,
			status, orderByComparator);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status) {
		return getPersistence().findByU_C_S(userId, classNameIds, status);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status, int start, int end) {
		return getPersistence()
				   .findByU_C_S(userId, classNameIds, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C_S(userId, classNameIds, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_S(long userId, long[] classNameIds,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_S(userId, classNameIds, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	*/
	public static void removeByU_C_S(long userId, long classNameId, int status) {
		getPersistence().removeByU_C_S(userId, classNameId, status);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C_S(long userId, long classNameId, int status) {
		return getPersistence().countByU_C_S(userId, classNameId, status);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameIds the class name IDs
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C_S(long userId, long[] classNameIds, int status) {
		return getPersistence().countByU_C_S(userId, classNameIds, status);
	}

	/**
	* Returns all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status) {
		return getPersistence().findByC_C_S(classNameId, classPK, status);
	}

	/**
	* Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status, int start, int end) {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, status, start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByC_C_S(long classNameId, long classPK,
		int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_C_S_First(long classNameId, long classPK,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_First(classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_C_S_First(long classNameId, long classPK,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_S_First(classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByC_C_S_Last(long classNameId, long classPK,
		int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_Last(classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByC_C_S_Last(long classNameId, long classPK,
		int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_S_Last(classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByC_C_S_PrevAndNext(long messageId,
		long classNameId, long classPK, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_PrevAndNext(messageId, classNameId, classPK,
			status, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	*/
	public static void removeByC_C_S(long classNameId, long classPK, int status) {
		getPersistence().removeByC_C_S(classNameId, classPK, status);
	}

	/**
	* Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByC_C_S(long classNameId, long classPK, int status) {
		return getPersistence().countByC_C_S(classNameId, classPK, status);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) {
		return getPersistence()
				   .findByG_C_T_A(groupId, categoryId, threadId, answer);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end) {
		return getPersistence()
				   .findByG_C_T_A(groupId, categoryId, threadId, answer, start,
			end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_C_T_A(groupId, categoryId, threadId, answer, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_T_A(groupId, categoryId, threadId, answer, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_A_First(long groupId, long categoryId,
		long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_A_First(groupId, categoryId, threadId, answer,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_A_First(long groupId, long categoryId,
		long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_A_First(groupId, categoryId, threadId, answer,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_A_Last(long groupId, long categoryId,
		long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_A_Last(groupId, categoryId, threadId, answer,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_A_Last(long groupId, long categoryId,
		long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_A_Last(groupId, categoryId, threadId, answer,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_C_T_A_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_A_PrevAndNext(messageId, groupId, categoryId,
			threadId, answer, orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_A(long groupId,
		long categoryId, long threadId, boolean answer) {
		return getPersistence()
				   .filterFindByG_C_T_A(groupId, categoryId, threadId, answer);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_A(long groupId,
		long categoryId, long threadId, boolean answer, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_T_A(groupId, categoryId, threadId, answer,
			start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_A(long groupId,
		long categoryId, long threadId, boolean answer, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_T_A(groupId, categoryId, threadId, answer,
			start, end, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_C_T_A_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, boolean answer,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_C_T_A_PrevAndNext(messageId, groupId,
			categoryId, threadId, answer, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	*/
	public static void removeByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) {
		getPersistence().removeByG_C_T_A(groupId, categoryId, threadId, answer);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @return the number of matching message-boards messages
	*/
	public static int countByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) {
		return getPersistence()
				   .countByG_C_T_A(groupId, categoryId, threadId, answer);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param answer the answer
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_C_T_A(long groupId, long categoryId,
		long threadId, boolean answer) {
		return getPersistence()
				   .filterCountByG_C_T_A(groupId, categoryId, threadId, answer);
	}

	/**
	* Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status);
	}

	/**
	* Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end) {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByG_C_T_S(long groupId, long categoryId,
		long threadId, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_S_First(long groupId, long categoryId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_First(groupId, categoryId, threadId, status,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_S_First(long groupId, long categoryId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_S_First(groupId, categoryId, threadId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByG_C_T_S_Last(long groupId, long categoryId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_Last(groupId, categoryId, threadId, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByG_C_T_S_Last(long groupId, long categoryId,
		long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_T_S_Last(groupId, categoryId, threadId, status,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByG_C_T_S_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_PrevAndNext(messageId, groupId, categoryId,
			threadId, status, orderByComparator);
	}

	/**
	* Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @return the matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_S(long groupId,
		long categoryId, long threadId, int status) {
		return getPersistence()
				   .filterFindByG_C_T_S(groupId, categoryId, threadId, status);
	}

	/**
	* Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_S(long groupId,
		long categoryId, long threadId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_T_S(groupId, categoryId, threadId, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages that the user has permission to view
	*/
	public static List<MBMessage> filterFindByG_C_T_S(long groupId,
		long categoryId, long threadId, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_T_S(groupId, categoryId, threadId, status,
			start, end, orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] filterFindByG_C_T_S_PrevAndNext(long messageId,
		long groupId, long categoryId, long threadId, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .filterFindByG_C_T_S_PrevAndNext(messageId, groupId,
			categoryId, threadId, status, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	*/
	public static void removeByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) {
		getPersistence().removeByG_C_T_S(groupId, categoryId, threadId, status);
	}

	/**
	* Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) {
		return getPersistence()
				   .countByG_C_T_S(groupId, categoryId, threadId, status);
	}

	/**
	* Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param threadId the thread ID
	* @param status the status
	* @return the number of matching message-boards messages that the user has permission to view
	*/
	public static int filterCountByG_C_T_S(long groupId, long categoryId,
		long threadId, int status) {
		return getPersistence()
				   .filterCountByG_C_T_S(groupId, categoryId, threadId, status);
	}

	/**
	* Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @return the matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status) {
		return getPersistence()
				   .findByU_C_C_S(userId, classNameId, classPK, status);
	}

	/**
	* Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status, int start, int end) {
		return getPersistence()
				   .findByU_C_C_S(userId, classNameId, classPK, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .findByU_C_C_S(userId, classNameId, classPK, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message-boards messages
	*/
	public static List<MBMessage> findByU_C_C_S(long userId, long classNameId,
		long classPK, int status, int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_C_S(userId, classNameId, classPK, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_C_S_First(long userId, long classNameId,
		long classPK, int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_S_First(userId, classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_C_S_First(long userId, long classNameId,
		long classPK, int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_S_First(userId, classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message
	* @throws NoSuchMessageException if a matching message-boards message could not be found
	*/
	public static MBMessage findByU_C_C_S_Last(long userId, long classNameId,
		long classPK, int status, OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_S_Last(userId, classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	public static MBMessage fetchByU_C_C_S_Last(long userId, long classNameId,
		long classPK, int status, OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_S_Last(userId, classNameId, classPK, status,
			orderByComparator);
	}

	/**
	* Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param messageId the primary key of the current message-boards message
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage[] findByU_C_C_S_PrevAndNext(long messageId,
		long userId, long classNameId, long classPK, int status,
		OrderByComparator<MBMessage> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByU_C_C_S_PrevAndNext(messageId, userId, classNameId,
			classPK, status, orderByComparator);
	}

	/**
	* Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	*/
	public static void removeByU_C_C_S(long userId, long classNameId,
		long classPK, int status) {
		getPersistence().removeByU_C_C_S(userId, classNameId, classPK, status);
	}

	/**
	* Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param status the status
	* @return the number of matching message-boards messages
	*/
	public static int countByU_C_C_S(long userId, long classNameId,
		long classPK, int status) {
		return getPersistence()
				   .countByU_C_C_S(userId, classNameId, classPK, status);
	}

	/**
	* Caches the message-boards message in the entity cache if it is enabled.
	*
	* @param mbMessage the message-boards message
	*/
	public static void cacheResult(MBMessage mbMessage) {
		getPersistence().cacheResult(mbMessage);
	}

	/**
	* Caches the message-boards messages in the entity cache if it is enabled.
	*
	* @param mbMessages the message-boards messages
	*/
	public static void cacheResult(List<MBMessage> mbMessages) {
		getPersistence().cacheResult(mbMessages);
	}

	/**
	* Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	*
	* @param messageId the primary key for the new message-boards message
	* @return the new message-boards message
	*/
	public static MBMessage create(long messageId) {
		return getPersistence().create(messageId);
	}

	/**
	* Removes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message that was removed
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage remove(long messageId)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().remove(messageId);
	}

	public static MBMessage updateImpl(MBMessage mbMessage) {
		return getPersistence().updateImpl(mbMessage);
	}

	/**
	* Returns the message-boards message with the primary key or throws a {@link NoSuchMessageException} if it could not be found.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message
	* @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	*/
	public static MBMessage findByPrimaryKey(long messageId)
		throws com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByPrimaryKey(messageId);
	}

	/**
	* Returns the message-boards message with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message, or <code>null</code> if a message-boards message with the primary key could not be found
	*/
	public static MBMessage fetchByPrimaryKey(long messageId) {
		return getPersistence().fetchByPrimaryKey(messageId);
	}

	public static java.util.Map<java.io.Serializable, MBMessage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the message-boards messages.
	*
	* @return the message-boards messages
	*/
	public static List<MBMessage> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of message-boards messages
	*/
	public static List<MBMessage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message-boards messages
	*/
	public static List<MBMessage> findAll(int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of message-boards messages
	*/
	public static List<MBMessage> findAll(int start, int end,
		OrderByComparator<MBMessage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the message-boards messages from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message-boards messages.
	*
	* @return the number of message-boards messages
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static MBMessagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMessagePersistence)PortalBeanLocatorUtil.locate(MBMessagePersistence.class.getName());

			ReferenceRegistry.registerReference(MBMessageUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static MBMessagePersistence _persistence;
}