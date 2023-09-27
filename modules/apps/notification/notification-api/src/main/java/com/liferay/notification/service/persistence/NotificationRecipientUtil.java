/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.model.NotificationRecipient;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification recipient service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationRecipientPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientPersistence
 * @generated
 */
public class NotificationRecipientUtil {

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
	public static void clearCache(NotificationRecipient notificationRecipient) {
		getPersistence().clearCache(notificationRecipient);
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
	public static Map<Serializable, NotificationRecipient> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationRecipient update(
		NotificationRecipient notificationRecipient) {

		return getPersistence().update(notificationRecipient);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationRecipient update(
		NotificationRecipient notificationRecipient,
		ServiceContext serviceContext) {

		return getPersistence().update(notificationRecipient, serviceContext);
	}

	/**
	 * Returns all the notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public static NotificationRecipient findByUuid_First(
			String uuid,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public static NotificationRecipient findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the notification recipients before and after the current notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param notificationRecipientId the primary key of the current notification recipient
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public static NotificationRecipient[] findByUuid_PrevAndNext(
			long notificationRecipientId, String uuid,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_PrevAndNext(
			notificationRecipientId, uuid, orderByComparator);
	}

	/**
	 * Removes all the notification recipients where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipients
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	public static List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public static NotificationRecipient findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public static NotificationRecipient findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the notification recipients before and after the current notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationRecipientId the primary key of the current notification recipient
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public static NotificationRecipient[] findByUuid_C_PrevAndNext(
			long notificationRecipientId, String uuid, long companyId,
			OrderByComparator<NotificationRecipient> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByUuid_C_PrevAndNext(
			notificationRecipientId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the notification recipients where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipients
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the notification recipient where classPK = &#63; or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public static NotificationRecipient findByClassPK(long classPK)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByClassPK(classPK);
	}

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByClassPK(long classPK) {
		return getPersistence().fetchByClassPK(classPK);
	}

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public static NotificationRecipient fetchByClassPK(
		long classPK, boolean useFinderCache) {

		return getPersistence().fetchByClassPK(classPK, useFinderCache);
	}

	/**
	 * Removes the notification recipient where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @return the notification recipient that was removed
	 */
	public static NotificationRecipient removeByClassPK(long classPK)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().removeByClassPK(classPK);
	}

	/**
	 * Returns the number of notification recipients where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching notification recipients
	 */
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	 * Caches the notification recipient in the entity cache if it is enabled.
	 *
	 * @param notificationRecipient the notification recipient
	 */
	public static void cacheResult(
		NotificationRecipient notificationRecipient) {

		getPersistence().cacheResult(notificationRecipient);
	}

	/**
	 * Caches the notification recipients in the entity cache if it is enabled.
	 *
	 * @param notificationRecipients the notification recipients
	 */
	public static void cacheResult(
		List<NotificationRecipient> notificationRecipients) {

		getPersistence().cacheResult(notificationRecipients);
	}

	/**
	 * Creates a new notification recipient with the primary key. Does not add the notification recipient to the database.
	 *
	 * @param notificationRecipientId the primary key for the new notification recipient
	 * @return the new notification recipient
	 */
	public static NotificationRecipient create(long notificationRecipientId) {
		return getPersistence().create(notificationRecipientId);
	}

	/**
	 * Removes the notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient that was removed
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public static NotificationRecipient remove(long notificationRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().remove(notificationRecipientId);
	}

	public static NotificationRecipient updateImpl(
		NotificationRecipient notificationRecipient) {

		return getPersistence().updateImpl(notificationRecipient);
	}

	/**
	 * Returns the notification recipient with the primary key or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public static NotificationRecipient findByPrimaryKey(
			long notificationRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientException {

		return getPersistence().findByPrimaryKey(notificationRecipientId);
	}

	/**
	 * Returns the notification recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient, or <code>null</code> if a notification recipient with the primary key could not be found
	 */
	public static NotificationRecipient fetchByPrimaryKey(
		long notificationRecipientId) {

		return getPersistence().fetchByPrimaryKey(notificationRecipientId);
	}

	/**
	 * Returns all the notification recipients.
	 *
	 * @return the notification recipients
	 */
	public static List<NotificationRecipient> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of notification recipients
	 */
	public static List<NotificationRecipient> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipients
	 */
	public static List<NotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipients
	 */
	public static List<NotificationRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipient> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification recipients from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification recipients.
	 *
	 * @return the number of notification recipients
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationRecipientPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NotificationRecipientPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NotificationRecipientPersistence _persistence;

}