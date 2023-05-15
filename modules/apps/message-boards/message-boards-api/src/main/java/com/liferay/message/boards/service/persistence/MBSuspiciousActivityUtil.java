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

import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the message boards suspicious activity service. This utility wraps <code>com.liferay.message.boards.service.persistence.impl.MBSuspiciousActivityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityPersistence
 * @generated
 */
public class MBSuspiciousActivityUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(MBSuspiciousActivity mbSuspiciousActivity) {
		getPersistence().clearCache(mbSuspiciousActivity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, MBSuspiciousActivity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBSuspiciousActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBSuspiciousActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBSuspiciousActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MBSuspiciousActivity update(
		MBSuspiciousActivity mbSuspiciousActivity) {

		return getPersistence().update(mbSuspiciousActivity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MBSuspiciousActivity update(
		MBSuspiciousActivity mbSuspiciousActivity,
		ServiceContext serviceContext) {

		return getPersistence().update(mbSuspiciousActivity, serviceContext);
	}

	/**
	 * Returns all the message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByUuid_First(
			String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUuid_First(
		String uuid,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByUuid_Last(
			String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUuid_Last(
		String uuid,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity[] findByUuid_PrevAndNext(
			long suspiciousActivityId, String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_PrevAndNext(
			suspiciousActivityId, uuid, orderByComparator);
	}

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByUUID_G(String uuid, long groupId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the message boards suspicious activity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards suspicious activity that was removed
	 */
	public static MBSuspiciousActivity removeByUUID_G(String uuid, long groupId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity[] findByUuid_C_PrevAndNext(
			long suspiciousActivityId, String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByUuid_C_PrevAndNext(
			suspiciousActivityId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByMessageId(long messageId) {
		return getPersistence().findByMessageId(messageId);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end) {

		return getPersistence().findByMessageId(messageId, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findByMessageId(
			messageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByMessageId(
			messageId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByMessageId_First(
			long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByMessageId_First(
			messageId, orderByComparator);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByMessageId_First(
		long messageId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByMessageId_First(
			messageId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByMessageId_Last(
			long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByMessageId_Last(
			messageId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByMessageId_Last(
		long messageId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByMessageId_Last(
			messageId, orderByComparator);
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity[] findByMessageId_PrevAndNext(
			long suspiciousActivityId, long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByMessageId_PrevAndNext(
			suspiciousActivityId, messageId, orderByComparator);
	}

	/**
	 * Removes all the message boards suspicious activities where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 */
	public static void removeByMessageId(long messageId) {
		getPersistence().removeByMessageId(messageId);
	}

	/**
	 * Returns the number of message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByMessageId(long messageId) {
		return getPersistence().countByMessageId(messageId);
	}

	/**
	 * Returns all the message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByThreadId(long threadId) {
		return getPersistence().findByThreadId(threadId);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end) {

		return getPersistence().findByThreadId(threadId, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findByThreadId(
			threadId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByThreadId(
			threadId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByThreadId_First(
			long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByThreadId_First(
			threadId, orderByComparator);
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByThreadId_First(
		long threadId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByThreadId_First(
			threadId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByThreadId_Last(
			long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByThreadId_Last(
			threadId, orderByComparator);
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByThreadId_Last(
		long threadId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().fetchByThreadId_Last(
			threadId, orderByComparator);
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity[] findByThreadId_PrevAndNext(
			long suspiciousActivityId, long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByThreadId_PrevAndNext(
			suspiciousActivityId, threadId, orderByComparator);
	}

	/**
	 * Removes all the message boards suspicious activities where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	public static void removeByThreadId(long threadId) {
		getPersistence().removeByThreadId(threadId);
	}

	/**
	 * Returns the number of message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByThreadId(long threadId) {
		return getPersistence().countByThreadId(threadId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByU_M(long userId, long messageId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByU_M(userId, messageId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByU_M(long userId, long messageId) {
		return getPersistence().fetchByU_M(userId, messageId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByU_M(
		long userId, long messageId, boolean useFinderCache) {

		return getPersistence().fetchByU_M(userId, messageId, useFinderCache);
	}

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and messageId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the message boards suspicious activity that was removed
	 */
	public static MBSuspiciousActivity removeByU_M(long userId, long messageId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().removeByU_M(userId, messageId);
	}

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and messageId = &#63;.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByU_M(long userId, long messageId) {
		return getPersistence().countByU_M(userId, messageId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity findByU_T(long userId, long threadId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByU_T(userId, threadId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByU_T(long userId, long threadId) {
		return getPersistence().fetchByU_T(userId, threadId);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public static MBSuspiciousActivity fetchByU_T(
		long userId, long threadId, boolean useFinderCache) {

		return getPersistence().fetchByU_T(userId, threadId, useFinderCache);
	}

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and threadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the message boards suspicious activity that was removed
	 */
	public static MBSuspiciousActivity removeByU_T(long userId, long threadId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().removeByU_T(userId, threadId);
	}

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and threadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	public static int countByU_T(long userId, long threadId) {
		return getPersistence().countByU_T(userId, threadId);
	}

	/**
	 * Caches the message boards suspicious activity in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivity the message boards suspicious activity
	 */
	public static void cacheResult(MBSuspiciousActivity mbSuspiciousActivity) {
		getPersistence().cacheResult(mbSuspiciousActivity);
	}

	/**
	 * Caches the message boards suspicious activities in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivities the message boards suspicious activities
	 */
	public static void cacheResult(
		List<MBSuspiciousActivity> mbSuspiciousActivities) {

		getPersistence().cacheResult(mbSuspiciousActivities);
	}

	/**
	 * Creates a new message boards suspicious activity with the primary key. Does not add the message boards suspicious activity to the database.
	 *
	 * @param suspiciousActivityId the primary key for the new message boards suspicious activity
	 * @return the new message boards suspicious activity
	 */
	public static MBSuspiciousActivity create(long suspiciousActivityId) {
		return getPersistence().create(suspiciousActivityId);
	}

	/**
	 * Removes the message boards suspicious activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity that was removed
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity remove(long suspiciousActivityId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().remove(suspiciousActivityId);
	}

	public static MBSuspiciousActivity updateImpl(
		MBSuspiciousActivity mbSuspiciousActivity) {

		return getPersistence().updateImpl(mbSuspiciousActivity);
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity findByPrimaryKey(
			long suspiciousActivityId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return getPersistence().findByPrimaryKey(suspiciousActivityId);
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity, or <code>null</code> if a message boards suspicious activity with the primary key could not be found
	 */
	public static MBSuspiciousActivity fetchByPrimaryKey(
		long suspiciousActivityId) {

		return getPersistence().fetchByPrimaryKey(suspiciousActivityId);
	}

	/**
	 * Returns all the message boards suspicious activities.
	 *
	 * @return the message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findAll(
		int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards suspicious activities
	 */
	public static List<MBSuspiciousActivity> findAll(
		int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the message boards suspicious activities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of message boards suspicious activities.
	 *
	 * @return the number of message boards suspicious activities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MBSuspiciousActivityPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		MBSuspiciousActivityPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile MBSuspiciousActivityPersistence _persistence;

}