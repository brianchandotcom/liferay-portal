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

package com.liferay.notification.service.persistence;

import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification template recipient setting service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationTemplateRecipientSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientSettingPersistence
 * @generated
 */
public class NotificationTemplateRecipientSettingUtil {

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
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		getPersistence().clearCache(notificationTemplateRecipientSetting);
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
	public static Map<Serializable, NotificationTemplateRecipientSetting>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationTemplateRecipientSetting>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationTemplateRecipientSetting>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationTemplateRecipientSetting>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationTemplateRecipientSetting update(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		return getPersistence().update(notificationTemplateRecipientSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationTemplateRecipientSetting update(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationTemplateRecipientSetting, serviceContext);
	}

	/**
	 * Returns all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @return the matching notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId) {

		return getPersistence().findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId);
	}

	/**
	 * Returns a range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @return the range of matching notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end) {

		return getPersistence().findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		return getPersistence().findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting
			findByNotificationTemplateRecipientId_First(
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().findByNotificationTemplateRecipientId_First(
			notificationTemplateRecipientId, orderByComparator);
	}

	/**
	 * Returns the first notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting
		fetchByNotificationTemplateRecipientId_First(
			long notificationTemplateRecipientId,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		return getPersistence().fetchByNotificationTemplateRecipientId_First(
			notificationTemplateRecipientId, orderByComparator);
	}

	/**
	 * Returns the last notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting
			findByNotificationTemplateRecipientId_Last(
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().findByNotificationTemplateRecipientId_Last(
			notificationTemplateRecipientId, orderByComparator);
	}

	/**
	 * Returns the last notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting
		fetchByNotificationTemplateRecipientId_Last(
			long notificationTemplateRecipientId,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		return getPersistence().fetchByNotificationTemplateRecipientId_Last(
			notificationTemplateRecipientId, orderByComparator);
	}

	/**
	 * Returns the notification template recipient settings before and after the current notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the current notification template recipient setting
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	public static NotificationTemplateRecipientSetting[]
			findByNotificationTemplateRecipientId_PrevAndNext(
				long notificationTemplateRecipientSettingId,
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().
			findByNotificationTemplateRecipientId_PrevAndNext(
				notificationTemplateRecipientSettingId,
				notificationTemplateRecipientId, orderByComparator);
	}

	/**
	 * Removes all the notification template recipient settings where notificationTemplateRecipientId = &#63; from the database.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 */
	public static void removeByNotificationTemplateRecipientId(
		long notificationTemplateRecipientId) {

		getPersistence().removeByNotificationTemplateRecipientId(
			notificationTemplateRecipientId);
	}

	/**
	 * Returns the number of notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @return the number of matching notification template recipient settings
	 */
	public static int countByNotificationTemplateRecipientId(
		long notificationTemplateRecipientId) {

		return getPersistence().countByNotificationTemplateRecipientId(
			notificationTemplateRecipientId);
	}

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or throws a <code>NoSuchNotificationTemplateRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting findByN_NTRI(
			long notificationTemplateRecipientId, String name)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().findByN_NTRI(
			notificationTemplateRecipientId, name);
	}

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting fetchByN_NTRI(
		long notificationTemplateRecipientId, String name) {

		return getPersistence().fetchByN_NTRI(
			notificationTemplateRecipientId, name);
	}

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	public static NotificationTemplateRecipientSetting fetchByN_NTRI(
		long notificationTemplateRecipientId, String name,
		boolean useFinderCache) {

		return getPersistence().fetchByN_NTRI(
			notificationTemplateRecipientId, name, useFinderCache);
	}

	/**
	 * Removes the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; from the database.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the notification template recipient setting that was removed
	 */
	public static NotificationTemplateRecipientSetting removeByN_NTRI(
			long notificationTemplateRecipientId, String name)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().removeByN_NTRI(
			notificationTemplateRecipientId, name);
	}

	/**
	 * Returns the number of notification template recipient settings where notificationTemplateRecipientId = &#63; and name = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the number of matching notification template recipient settings
	 */
	public static int countByN_NTRI(
		long notificationTemplateRecipientId, String name) {

		return getPersistence().countByN_NTRI(
			notificationTemplateRecipientId, name);
	}

	/**
	 * Caches the notification template recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 */
	public static void cacheResult(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		getPersistence().cacheResult(notificationTemplateRecipientSetting);
	}

	/**
	 * Caches the notification template recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSettings the notification template recipient settings
	 */
	public static void cacheResult(
		List<NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings) {

		getPersistence().cacheResult(notificationTemplateRecipientSettings);
	}

	/**
	 * Creates a new notification template recipient setting with the primary key. Does not add the notification template recipient setting to the database.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key for the new notification template recipient setting
	 * @return the new notification template recipient setting
	 */
	public static NotificationTemplateRecipientSetting create(
		long notificationTemplateRecipientSettingId) {

		return getPersistence().create(notificationTemplateRecipientSettingId);
	}

	/**
	 * Removes the notification template recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	public static NotificationTemplateRecipientSetting remove(
			long notificationTemplateRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().remove(notificationTemplateRecipientSettingId);
	}

	public static NotificationTemplateRecipientSetting updateImpl(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		return getPersistence().updateImpl(
			notificationTemplateRecipientSetting);
	}

	/**
	 * Returns the notification template recipient setting with the primary key or throws a <code>NoSuchNotificationTemplateRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	public static NotificationTemplateRecipientSetting findByPrimaryKey(
			long notificationTemplateRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientSettingException {

		return getPersistence().findByPrimaryKey(
			notificationTemplateRecipientSettingId);
	}

	/**
	 * Returns the notification template recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting, or <code>null</code> if a notification template recipient setting with the primary key could not be found
	 */
	public static NotificationTemplateRecipientSetting fetchByPrimaryKey(
		long notificationTemplateRecipientSettingId) {

		return getPersistence().fetchByPrimaryKey(
			notificationTemplateRecipientSettingId);
	}

	/**
	 * Returns all the notification template recipient settings.
	 *
	 * @return the notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @return the range of notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipientSetting>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template recipient settings
	 */
	public static List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipientSetting>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification template recipient settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification template recipient settings.
	 *
	 * @return the number of notification template recipient settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationTemplateRecipientSettingPersistence
		getPersistence() {

		return _persistence;
	}

	private static volatile NotificationTemplateRecipientSettingPersistence
		_persistence;

}