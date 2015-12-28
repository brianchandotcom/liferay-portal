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

import com.liferay.portlet.messageboards.model.MBBan;

import java.util.List;

/**
 * The persistence utility for the message boards ban service. This utility wraps {@link com.liferay.portlet.messageboards.service.persistence.impl.MBBanPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBanPersistence
 * @see com.liferay.portlet.messageboards.service.persistence.impl.MBBanPersistenceImpl
 * @generated
 */
@ProviderType
public class MBBanUtil {
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
	public static void clearCache(MBBan mbBan) {
		getPersistence().clearCache(mbBan);
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
	public static List<MBBan> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBBan> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBBan> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MBBan update(MBBan mbBan) {
		return getPersistence().update(mbBan);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MBBan update(MBBan mbBan, ServiceContext serviceContext) {
		return getPersistence().update(mbBan, serviceContext);
	}

	/**
	* Returns all the message boards bans where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards bans
	*/
	public static List<MBBan> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the message boards bans where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	*/
	public static List<MBBan> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<MBBan> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message boards ban in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUuid_First(java.lang.String uuid,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first message boards ban in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUuid_Last(java.lang.String uuid,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where uuid = &#63;.
	*
	* @param banId the primary key of the current message boards ban
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan[] findByUuid_PrevAndNext(long banId,
		java.lang.String uuid, OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByUuid_PrevAndNext(banId, uuid, orderByComparator);
	}

	/**
	* Removes all the message boards bans where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of message boards bans where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards bans
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the message boards ban where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchBanException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards ban where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards ban where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the message boards ban where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards ban that was removed
	*/
	public static MBBan removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of message boards bans where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards bans
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the message boards bans where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards bans
	*/
	public static List<MBBan> findByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the message boards bans where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	*/
	public static List<MBBan> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<MBBan> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first message boards ban in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first message boards ban in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param banId the primary key of the current message boards ban
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan[] findByUuid_C_PrevAndNext(long banId,
		java.lang.String uuid, long companyId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(banId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the message boards bans where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of message boards bans where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards bans
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the message boards bans where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards bans
	*/
	public static List<MBBan> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the message boards bans where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	*/
	public static List<MBBan> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MBBan> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message boards ban in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByGroupId_First(long groupId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first message boards ban in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByGroupId_First(long groupId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByGroupId_Last(long groupId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByGroupId_Last(long groupId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where groupId = &#63;.
	*
	* @param banId the primary key of the current message boards ban
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan[] findByGroupId_PrevAndNext(long banId, long groupId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(banId, groupId, orderByComparator);
	}

	/**
	* Removes all the message boards bans where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of message boards bans where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards bans
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the message boards bans where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards bans
	*/
	public static List<MBBan> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the message boards bans where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	*/
	public static List<MBBan> findByUserId(long userId, int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUserId(long userId, int start, int end,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByUserId(long userId, int start, int end,
		OrderByComparator<MBBan> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message boards ban in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUserId_First(long userId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first message boards ban in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUserId_First(long userId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByUserId_Last(long userId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByUserId_Last(long userId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where userId = &#63;.
	*
	* @param banId the primary key of the current message boards ban
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan[] findByUserId_PrevAndNext(long banId, long userId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByUserId_PrevAndNext(banId, userId, orderByComparator);
	}

	/**
	* Removes all the message boards bans where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of message boards bans where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards bans
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the message boards bans where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @return the matching message boards bans
	*/
	public static List<MBBan> findByBanUserId(long banUserId) {
		return getPersistence().findByBanUserId(banUserId);
	}

	/**
	* Returns a range of all the message boards bans where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of matching message boards bans
	*/
	public static List<MBBan> findByBanUserId(long banUserId, int start, int end) {
		return getPersistence().findByBanUserId(banUserId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByBanUserId(long banUserId, int start,
		int end, OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .findByBanUserId(banUserId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans where banUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param banUserId the ban user ID
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards bans
	*/
	public static List<MBBan> findByBanUserId(long banUserId, int start,
		int end, OrderByComparator<MBBan> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByBanUserId(banUserId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first message boards ban in the ordered set where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByBanUserId_First(long banUserId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByBanUserId_First(banUserId, orderByComparator);
	}

	/**
	* Returns the first message boards ban in the ordered set where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByBanUserId_First(long banUserId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .fetchByBanUserId_First(banUserId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByBanUserId_Last(long banUserId,
		OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByBanUserId_Last(banUserId, orderByComparator);
	}

	/**
	* Returns the last message boards ban in the ordered set where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByBanUserId_Last(long banUserId,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence()
				   .fetchByBanUserId_Last(banUserId, orderByComparator);
	}

	/**
	* Returns the message boards bans before and after the current message boards ban in the ordered set where banUserId = &#63;.
	*
	* @param banId the primary key of the current message boards ban
	* @param banUserId the ban user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan[] findByBanUserId_PrevAndNext(long banId,
		long banUserId, OrderByComparator<MBBan> orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence()
				   .findByBanUserId_PrevAndNext(banId, banUserId,
			orderByComparator);
	}

	/**
	* Removes all the message boards bans where banUserId = &#63; from the database.
	*
	* @param banUserId the ban user ID
	*/
	public static void removeByBanUserId(long banUserId) {
		getPersistence().removeByBanUserId(banUserId);
	}

	/**
	* Returns the number of message boards bans where banUserId = &#63;.
	*
	* @param banUserId the ban user ID
	* @return the number of matching message boards bans
	*/
	public static int countByBanUserId(long banUserId) {
		return getPersistence().countByBanUserId(banUserId);
	}

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or throws a {@link NoSuchBanException} if it could not be found.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the matching message boards ban
	* @throws NoSuchBanException if a matching message boards ban could not be found
	*/
	public static MBBan findByG_B(long groupId, long banUserId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByG_B(groupId, banUserId);
	}

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByG_B(long groupId, long banUserId) {
		return getPersistence().fetchByG_B(groupId, banUserId);
	}

	/**
	* Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	*/
	public static MBBan fetchByG_B(long groupId, long banUserId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_B(groupId, banUserId, retrieveFromCache);
	}

	/**
	* Removes the message boards ban where groupId = &#63; and banUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the message boards ban that was removed
	*/
	public static MBBan removeByG_B(long groupId, long banUserId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().removeByG_B(groupId, banUserId);
	}

	/**
	* Returns the number of message boards bans where groupId = &#63; and banUserId = &#63;.
	*
	* @param groupId the group ID
	* @param banUserId the ban user ID
	* @return the number of matching message boards bans
	*/
	public static int countByG_B(long groupId, long banUserId) {
		return getPersistence().countByG_B(groupId, banUserId);
	}

	/**
	* Caches the message boards ban in the entity cache if it is enabled.
	*
	* @param mbBan the message boards ban
	*/
	public static void cacheResult(MBBan mbBan) {
		getPersistence().cacheResult(mbBan);
	}

	/**
	* Caches the message boards bans in the entity cache if it is enabled.
	*
	* @param mbBans the message boards bans
	*/
	public static void cacheResult(List<MBBan> mbBans) {
		getPersistence().cacheResult(mbBans);
	}

	/**
	* Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	*
	* @param banId the primary key for the new message boards ban
	* @return the new message boards ban
	*/
	public static MBBan create(long banId) {
		return getPersistence().create(banId);
	}

	/**
	* Removes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban that was removed
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan remove(long banId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().remove(banId);
	}

	public static MBBan updateImpl(MBBan mbBan) {
		return getPersistence().updateImpl(mbBan);
	}

	/**
	* Returns the message boards ban with the primary key or throws a {@link NoSuchBanException} if it could not be found.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban
	* @throws NoSuchBanException if a message boards ban with the primary key could not be found
	*/
	public static MBBan findByPrimaryKey(long banId)
		throws com.liferay.portlet.messageboards.NoSuchBanException {
		return getPersistence().findByPrimaryKey(banId);
	}

	/**
	* Returns the message boards ban with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban, or <code>null</code> if a message boards ban with the primary key could not be found
	*/
	public static MBBan fetchByPrimaryKey(long banId) {
		return getPersistence().fetchByPrimaryKey(banId);
	}

	public static java.util.Map<java.io.Serializable, MBBan> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the message boards bans.
	*
	* @return the message boards bans
	*/
	public static List<MBBan> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of message boards bans
	*/
	public static List<MBBan> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards bans
	*/
	public static List<MBBan> findAll(int start, int end,
		OrderByComparator<MBBan> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBBanModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of message boards bans
	*/
	public static List<MBBan> findAll(int start, int end,
		OrderByComparator<MBBan> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the message boards bans from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards bans.
	*
	* @return the number of message boards bans
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static MBBanPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName());

			ReferenceRegistry.registerReference(MBBanUtil.class, "_persistence");
		}

		return _persistence;
	}

	private static MBBanPersistence _persistence;
}