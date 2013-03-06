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

import com.liferay.portlet.messageboards.model.MBMailingList;

import java.util.List;

/**
 * The persistence utility for the message boards mailing list service. This utility wraps {@link MBMailingListPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListPersistence
 * @see MBMailingListPersistenceImpl
 * @generated
 */
public class MBMailingListUtil {
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
	public static void clearCache(MBMailingList mbMailingList) {
		getPersistence().clearCache(mbMailingList);
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
	public static List<MBMailingList> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBMailingList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBMailingList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MBMailingList update(MBMailingList mbMailingList)
		throws SystemException {
		return getPersistence().update(mbMailingList);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MBMailingList update(MBMailingList mbMailingList,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbMailingList, serviceContext);
	}

	/**
	* Returns all the message boards mailing lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the message boards mailing lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the message boards mailing lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where uuid = &#63;.
	*
	* @param mailingListId the primary key of the current message boards mailing list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList[] findByUuid_PrevAndNext(
		long mailingListId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByUuid_PrevAndNext(mailingListId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the message boards mailing lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of message boards mailing lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the message boards mailing list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards mailing list that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of message boards mailing lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards mailing lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param mailingListId the primary key of the current message boards mailing list
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList[] findByUuid_C_PrevAndNext(
		long mailingListId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(mailingListId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the message boards mailing lists where uuid = &#63; and companyId = &#63; from the database.
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
	* Returns the number of message boards mailing lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the message boards mailing lists where active = &#63;.
	*
	* @param active the active
	* @return the matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active);
	}

	/**
	* Returns a range of all the message boards mailing lists where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	/**
	* Returns an ordered range of all the message boards mailing lists where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByActive_First(active, orderByComparator);
	}

	/**
	* Returns the first message boards mailing list in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByActive_First(active, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the last message boards mailing list in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where active = &#63;.
	*
	* @param mailingListId the primary key of the current message boards mailing list
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList[] findByActive_PrevAndNext(
		long mailingListId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByActive_PrevAndNext(mailingListId, active,
			orderByComparator);
	}

	/**
	* Removes all the message boards mailing lists where active = &#63; from the database.
	*
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActive(active);
	}

	/**
	* Returns the number of message boards mailing lists where active = &#63;.
	*
	* @param active the active
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActive(active);
	}

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_C(groupId, categoryId);
	}

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C(groupId, categoryId, retrieveFromCache);
	}

	/**
	* Removes the message boards mailing list where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the message boards mailing list that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList removeByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().removeByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of message boards mailing lists where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	/**
	* Caches the message boards mailing list in the entity cache if it is enabled.
	*
	* @param mbMailingList the message boards mailing list
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		getPersistence().cacheResult(mbMailingList);
	}

	/**
	* Caches the message boards mailing lists in the entity cache if it is enabled.
	*
	* @param mbMailingLists the message boards mailing lists
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> mbMailingLists) {
		getPersistence().cacheResult(mbMailingLists);
	}

	/**
	* Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	*
	* @param mailingListId the primary key for the new message boards mailing list
	* @return the new message boards mailing list
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList create(
		long mailingListId) {
		return getPersistence().create(mailingListId);
	}

	/**
	* Removes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList remove(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().remove(mailingListId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbMailingList);
	}

	/**
	* Returns the message boards mailing list with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList findByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByPrimaryKey(mailingListId);
	}

	/**
	* Returns the message boards mailing list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list, or <code>null</code> if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(mailingListId);
	}

	/**
	* Returns all the message boards mailing lists.
	*
	* @return the message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message boards mailing lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message boards mailing lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards mailing lists from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards mailing lists.
	*
	* @return the number of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBMailingListPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMailingListPersistence)PortalBeanLocatorUtil.locate(MBMailingListPersistence.class.getName());

			ReferenceRegistry.registerReference(MBMailingListUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(MBMailingListPersistence persistence) {
	}

	private static MBMailingListPersistence _persistence;
}