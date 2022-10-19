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

import com.liferay.notification.model.NotificationQueueEntryRecipient;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification queue entry recipient service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationQueueEntryRecipientPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientPersistence
 * @generated
 */
public class NotificationQueueEntryRecipientUtil {

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
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		getPersistence().clearCache(notificationQueueEntryRecipient);
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
	public static Map<Serializable, NotificationQueueEntryRecipient>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationQueueEntryRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationQueueEntryRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationQueueEntryRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationQueueEntryRecipient> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationQueueEntryRecipient update(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		return getPersistence().update(notificationQueueEntryRecipient);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationQueueEntryRecipient update(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationQueueEntryRecipient, serviceContext);
	}

	/**
	 * Caches the notification queue entry recipient in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 */
	public static void cacheResult(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		getPersistence().cacheResult(notificationQueueEntryRecipient);
	}

	/**
	 * Caches the notification queue entry recipients in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipients the notification queue entry recipients
	 */
	public static void cacheResult(
		List<NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients) {

		getPersistence().cacheResult(notificationQueueEntryRecipients);
	}

	/**
	 * Creates a new notification queue entry recipient with the primary key. Does not add the notification queue entry recipient to the database.
	 *
	 * @param notificationQueueEntryRecipientId the primary key for the new notification queue entry recipient
	 * @return the new notification queue entry recipient
	 */
	public static NotificationQueueEntryRecipient create(
		long notificationQueueEntryRecipientId) {

		return getPersistence().create(notificationQueueEntryRecipientId);
	}

	/**
	 * Removes the notification queue entry recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipient remove(
			long notificationQueueEntryRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryRecipientException {

		return getPersistence().remove(notificationQueueEntryRecipientId);
	}

	public static NotificationQueueEntryRecipient updateImpl(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		return getPersistence().updateImpl(notificationQueueEntryRecipient);
	}

	/**
	 * Returns the notification queue entry recipient with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipient findByPrimaryKey(
			long notificationQueueEntryRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryRecipientException {

		return getPersistence().findByPrimaryKey(
			notificationQueueEntryRecipientId);
	}

	/**
	 * Returns the notification queue entry recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient, or <code>null</code> if a notification queue entry recipient with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipient fetchByPrimaryKey(
		long notificationQueueEntryRecipientId) {

		return getPersistence().fetchByPrimaryKey(
			notificationQueueEntryRecipientId);
	}

	/**
	 * Returns all the notification queue entry recipients.
	 *
	 * @return the notification queue entry recipients
	 */
	public static List<NotificationQueueEntryRecipient> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @return the range of notification queue entry recipients
	 */
	public static List<NotificationQueueEntryRecipient> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry recipients
	 */
	public static List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipient> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry recipients
	 */
	public static List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryRecipient> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification queue entry recipients from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification queue entry recipients.
	 *
	 * @return the number of notification queue entry recipients
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationQueueEntryRecipientPersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationQueueEntryRecipientPersistence
		_persistence;

}