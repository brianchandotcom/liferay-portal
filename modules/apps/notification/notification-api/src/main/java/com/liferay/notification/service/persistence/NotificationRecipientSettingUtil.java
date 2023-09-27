/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification recipient setting service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationRecipientSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientSettingPersistence
 * @generated
 */
public class NotificationRecipientSettingUtil {

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
		NotificationRecipientSetting notificationRecipientSetting) {

		getPersistence().clearCache(notificationRecipientSetting);
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
	public static Map<Serializable, NotificationRecipientSetting>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationRecipientSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationRecipientSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationRecipientSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationRecipientSetting update(
		NotificationRecipientSetting notificationRecipientSetting) {

		return getPersistence().update(notificationRecipientSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationRecipientSetting update(
		NotificationRecipientSetting notificationRecipientSetting,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationRecipientSetting, serviceContext);
	}

	/**
	 * Returns all the notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting findByUuid_First(
			String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting[] findByUuid_PrevAndNext(
			long notificationRecipientSettingId, String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_PrevAndNext(
			notificationRecipientSettingId, uuid, orderByComparator);
	}

	/**
	 * Removes all the notification recipient settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipient settings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting[] findByUuid_C_PrevAndNext(
			long notificationRecipientSettingId, String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByUuid_C_PrevAndNext(
			notificationRecipientSettingId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the notification recipient settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipient settings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting>
		findByNotificationRecipientId(long notificationRecipientId) {

		return getPersistence().findByNotificationRecipientId(
			notificationRecipientId);
	}

	/**
	 * Returns a range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end) {

		return getPersistence().findByNotificationRecipientId(
			notificationRecipientId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end,
			OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().findByNotificationRecipientId(
			notificationRecipientId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public static List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end,
			OrderByComparator<NotificationRecipientSetting> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByNotificationRecipientId(
			notificationRecipientId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting
			findByNotificationRecipientId_First(
				long notificationRecipientId,
				OrderByComparator<NotificationRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByNotificationRecipientId_First(
			notificationRecipientId, orderByComparator);
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting
		fetchByNotificationRecipientId_First(
			long notificationRecipientId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByNotificationRecipientId_First(
			notificationRecipientId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting
			findByNotificationRecipientId_Last(
				long notificationRecipientId,
				OrderByComparator<NotificationRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByNotificationRecipientId_Last(
			notificationRecipientId, orderByComparator);
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting
		fetchByNotificationRecipientId_Last(
			long notificationRecipientId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().fetchByNotificationRecipientId_Last(
			notificationRecipientId, orderByComparator);
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting[]
			findByNotificationRecipientId_PrevAndNext(
				long notificationRecipientSettingId,
				long notificationRecipientId,
				OrderByComparator<NotificationRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByNotificationRecipientId_PrevAndNext(
			notificationRecipientSettingId, notificationRecipientId,
			orderByComparator);
	}

	/**
	 * Removes all the notification recipient settings where notificationRecipientId = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 */
	public static void removeByNotificationRecipientId(
		long notificationRecipientId) {

		getPersistence().removeByNotificationRecipientId(
			notificationRecipientId);
	}

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the number of matching notification recipient settings
	 */
	public static int countByNotificationRecipientId(
		long notificationRecipientId) {

		return getPersistence().countByNotificationRecipientId(
			notificationRecipientId);
	}

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting findByNRI_N(
			long notificationRecipientId, String name)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByNRI_N(notificationRecipientId, name);
	}

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name) {

		return getPersistence().fetchByNRI_N(notificationRecipientId, name);
	}

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public static NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name, boolean useFinderCache) {

		return getPersistence().fetchByNRI_N(
			notificationRecipientId, name, useFinderCache);
	}

	/**
	 * Removes the notification recipient setting where notificationRecipientId = &#63; and name = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the notification recipient setting that was removed
	 */
	public static NotificationRecipientSetting removeByNRI_N(
			long notificationRecipientId, String name)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().removeByNRI_N(notificationRecipientId, name);
	}

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63; and name = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the number of matching notification recipient settings
	 */
	public static int countByNRI_N(long notificationRecipientId, String name) {
		return getPersistence().countByNRI_N(notificationRecipientId, name);
	}

	/**
	 * Caches the notification recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSetting the notification recipient setting
	 */
	public static void cacheResult(
		NotificationRecipientSetting notificationRecipientSetting) {

		getPersistence().cacheResult(notificationRecipientSetting);
	}

	/**
	 * Caches the notification recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSettings the notification recipient settings
	 */
	public static void cacheResult(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		getPersistence().cacheResult(notificationRecipientSettings);
	}

	/**
	 * Creates a new notification recipient setting with the primary key. Does not add the notification recipient setting to the database.
	 *
	 * @param notificationRecipientSettingId the primary key for the new notification recipient setting
	 * @return the new notification recipient setting
	 */
	public static NotificationRecipientSetting create(
		long notificationRecipientSettingId) {

		return getPersistence().create(notificationRecipientSettingId);
	}

	/**
	 * Removes the notification recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting that was removed
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting remove(
			long notificationRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().remove(notificationRecipientSettingId);
	}

	public static NotificationRecipientSetting updateImpl(
		NotificationRecipientSetting notificationRecipientSetting) {

		return getPersistence().updateImpl(notificationRecipientSetting);
	}

	/**
	 * Returns the notification recipient setting with the primary key or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting findByPrimaryKey(
			long notificationRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationRecipientSettingException {

		return getPersistence().findByPrimaryKey(
			notificationRecipientSettingId);
	}

	/**
	 * Returns the notification recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting, or <code>null</code> if a notification recipient setting with the primary key could not be found
	 */
	public static NotificationRecipientSetting fetchByPrimaryKey(
		long notificationRecipientSettingId) {

		return getPersistence().fetchByPrimaryKey(
			notificationRecipientSettingId);
	}

	/**
	 * Returns all the notification recipient settings.
	 *
	 * @return the notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipient settings
	 */
	public static List<NotificationRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification recipient settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification recipient settings.
	 *
	 * @return the number of notification recipient settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationRecipientSettingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NotificationRecipientSettingPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NotificationRecipientSettingPersistence
		_persistence;

}