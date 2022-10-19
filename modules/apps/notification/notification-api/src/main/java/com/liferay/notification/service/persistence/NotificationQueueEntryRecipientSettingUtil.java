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

import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification queue entry recipient setting service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationQueueEntryRecipientSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientSettingPersistence
 * @generated
 */
public class NotificationQueueEntryRecipientSettingUtil {

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
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		getPersistence().clearCache(notificationQueueEntryRecipientSetting);
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
	public static Map<Serializable, NotificationQueueEntryRecipientSetting>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationQueueEntryRecipientSetting>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationQueueEntryRecipientSetting>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationQueueEntryRecipientSetting>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<NotificationQueueEntryRecipientSetting>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationQueueEntryRecipientSetting update(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		return getPersistence().update(notificationQueueEntryRecipientSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationQueueEntryRecipientSetting update(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationQueueEntryRecipientSetting, serviceContext);
	}

	/**
	 * Caches the notification queue entry recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 */
	public static void cacheResult(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		getPersistence().cacheResult(notificationQueueEntryRecipientSetting);
	}

	/**
	 * Caches the notification queue entry recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSettings the notification queue entry recipient settings
	 */
	public static void cacheResult(
		List<NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings) {

		getPersistence().cacheResult(notificationQueueEntryRecipientSettings);
	}

	/**
	 * Creates a new notification queue entry recipient setting with the primary key. Does not add the notification queue entry recipient setting to the database.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key for the new notification queue entry recipient setting
	 * @return the new notification queue entry recipient setting
	 */
	public static NotificationQueueEntryRecipientSetting create(
		long notificationQueueEntryRecipientSettingId) {

		return getPersistence().create(
			notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Removes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipientSetting remove(
			long notificationQueueEntryRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryRecipientSettingException {

		return getPersistence().remove(
			notificationQueueEntryRecipientSettingId);
	}

	public static NotificationQueueEntryRecipientSetting updateImpl(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting) {

		return getPersistence().updateImpl(
			notificationQueueEntryRecipientSetting);
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipientSetting findByPrimaryKey(
			long notificationQueueEntryRecipientSettingId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryRecipientSettingException {

		return getPersistence().findByPrimaryKey(
			notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting, or <code>null</code> if a notification queue entry recipient setting with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipientSetting fetchByPrimaryKey(
		long notificationQueueEntryRecipientSettingId) {

		return getPersistence().fetchByPrimaryKey(
			notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns all the notification queue entry recipient settings.
	 *
	 * @return the notification queue entry recipient settings
	 */
	public static List<NotificationQueueEntryRecipientSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @return the range of notification queue entry recipient settings
	 */
	public static List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry recipient settings
	 */
	public static List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipientSetting>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry recipient settings
	 */
	public static List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipientSetting>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification queue entry recipient settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification queue entry recipient settings.
	 *
	 * @return the number of notification queue entry recipient settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationQueueEntryRecipientSettingPersistence
		getPersistence() {

		return _persistence;
	}

	private static volatile NotificationQueueEntryRecipientSettingPersistence
		_persistence;

}