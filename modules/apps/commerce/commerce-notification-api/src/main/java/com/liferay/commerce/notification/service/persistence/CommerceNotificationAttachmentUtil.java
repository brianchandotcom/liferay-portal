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

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce notification attachment service. This utility wraps <code>com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationAttachmentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachmentPersistence
 * @generated
 */
public class CommerceNotificationAttachmentUtil {

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
	public static void clearCache(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		getPersistence().clearCache(commerceNotificationAttachment);
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
	public static Map<Serializable, CommerceNotificationAttachment>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationAttachment update(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		return getPersistence().update(commerceNotificationAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationAttachment update(
		CommerceNotificationAttachment commerceNotificationAttachment,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceNotificationAttachment, serviceContext);
	}

	/**
	 * Returns all the commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment findByUuid_First(
			String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment[] findByUuid_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceNotificationAttachmentId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce notification attachments
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce notification attachment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification attachment that was removed
	 */
	public static CommerceNotificationAttachment removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce notification attachments
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment[] findByUuid_C_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceNotificationAttachmentId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce notification attachments
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId) {

		return getPersistence().findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Returns a range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end) {

		return getPersistence().findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		return getPersistence().findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_First(
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByCommerceNotificationQueueEntryId_First(
			commerceNotificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_First(
			long commerceNotificationQueueEntryId,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		return getPersistence().fetchByCommerceNotificationQueueEntryId_First(
			commerceNotificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_Last(
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByCommerceNotificationQueueEntryId_Last(
			commerceNotificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public static CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_Last(
			long commerceNotificationQueueEntryId,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		return getPersistence().fetchByCommerceNotificationQueueEntryId_Last(
			commerceNotificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment[]
			findByCommerceNotificationQueueEntryId_PrevAndNext(
				long commerceNotificationAttachmentId,
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().
			findByCommerceNotificationQueueEntryId_PrevAndNext(
				commerceNotificationAttachmentId,
				commerceNotificationQueueEntryId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification attachments where commerceNotificationQueueEntryId = &#63; from the database.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 */
	public static void removeByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		getPersistence().removeByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Returns the number of commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the number of matching commerce notification attachments
	 */
	public static int countByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		return getPersistence().countByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Caches the commerce notification attachment in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachment the commerce notification attachment
	 */
	public static void cacheResult(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		getPersistence().cacheResult(commerceNotificationAttachment);
	}

	/**
	 * Caches the commerce notification attachments in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachments the commerce notification attachments
	 */
	public static void cacheResult(
		List<CommerceNotificationAttachment> commerceNotificationAttachments) {

		getPersistence().cacheResult(commerceNotificationAttachments);
	}

	/**
	 * Creates a new commerce notification attachment with the primary key. Does not add the commerce notification attachment to the database.
	 *
	 * @param commerceNotificationAttachmentId the primary key for the new commerce notification attachment
	 * @return the new commerce notification attachment
	 */
	public static CommerceNotificationAttachment create(
		long commerceNotificationAttachmentId) {

		return getPersistence().create(commerceNotificationAttachmentId);
	}

	/**
	 * Removes the commerce notification attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment that was removed
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment remove(
			long commerceNotificationAttachmentId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().remove(commerceNotificationAttachmentId);
	}

	public static CommerceNotificationAttachment updateImpl(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		return getPersistence().updateImpl(commerceNotificationAttachment);
	}

	/**
	 * Returns the commerce notification attachment with the primary key or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment findByPrimaryKey(
			long commerceNotificationAttachmentId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationAttachmentException {

		return getPersistence().findByPrimaryKey(
			commerceNotificationAttachmentId);
	}

	/**
	 * Returns the commerce notification attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment, or <code>null</code> if a commerce notification attachment with the primary key could not be found
	 */
	public static CommerceNotificationAttachment fetchByPrimaryKey(
		long commerceNotificationAttachmentId) {

		return getPersistence().fetchByPrimaryKey(
			commerceNotificationAttachmentId);
	}

	/**
	 * Returns all the commerce notification attachments.
	 *
	 * @return the commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification attachments
	 */
	public static List<CommerceNotificationAttachment> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce notification attachments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce notification attachments.
	 *
	 * @return the number of commerce notification attachments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceNotificationAttachmentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceNotificationAttachmentPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceNotificationAttachmentPersistence
		_persistence;

}