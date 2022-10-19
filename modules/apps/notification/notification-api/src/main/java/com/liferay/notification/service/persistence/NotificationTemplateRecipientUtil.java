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

import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification template recipient service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationTemplateRecipientPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientPersistence
 * @generated
 */
public class NotificationTemplateRecipientUtil {

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
		NotificationTemplateRecipient notificationTemplateRecipient) {

		getPersistence().clearCache(notificationTemplateRecipient);
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
	public static Map<Serializable, NotificationTemplateRecipient>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationTemplateRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationTemplateRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationTemplateRecipient> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationTemplateRecipient> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationTemplateRecipient update(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		return getPersistence().update(notificationTemplateRecipient);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationTemplateRecipient update(
		NotificationTemplateRecipient notificationTemplateRecipient,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationTemplateRecipient, serviceContext);
	}

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a matching notification template recipient could not be found
	 */
	public static NotificationTemplateRecipient findByNotificationTemplateId(
			long notificationTemplateId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientException {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	public static NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().fetchByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	public static NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId, boolean useFinderCache) {

		return getPersistence().fetchByNotificationTemplateId(
			notificationTemplateId, useFinderCache);
	}

	/**
	 * Removes the notification template recipient where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the notification template recipient that was removed
	 */
	public static NotificationTemplateRecipient removeByNotificationTemplateId(
			long notificationTemplateId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientException {

		return getPersistence().removeByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns the number of notification template recipients where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template recipients
	 */
	public static int countByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().countByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Caches the notification template recipient in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 */
	public static void cacheResult(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		getPersistence().cacheResult(notificationTemplateRecipient);
	}

	/**
	 * Caches the notification template recipients in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipients the notification template recipients
	 */
	public static void cacheResult(
		List<NotificationTemplateRecipient> notificationTemplateRecipients) {

		getPersistence().cacheResult(notificationTemplateRecipients);
	}

	/**
	 * Creates a new notification template recipient with the primary key. Does not add the notification template recipient to the database.
	 *
	 * @param notificationTemplateRecipientId the primary key for the new notification template recipient
	 * @return the new notification template recipient
	 */
	public static NotificationTemplateRecipient create(
		long notificationTemplateRecipientId) {

		return getPersistence().create(notificationTemplateRecipientId);
	}

	/**
	 * Removes the notification template recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient that was removed
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	public static NotificationTemplateRecipient remove(
			long notificationTemplateRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientException {

		return getPersistence().remove(notificationTemplateRecipientId);
	}

	public static NotificationTemplateRecipient updateImpl(
		NotificationTemplateRecipient notificationTemplateRecipient) {

		return getPersistence().updateImpl(notificationTemplateRecipient);
	}

	/**
	 * Returns the notification template recipient with the primary key or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	public static NotificationTemplateRecipient findByPrimaryKey(
			long notificationTemplateRecipientId)
		throws com.liferay.notification.exception.
			NoSuchNotificationTemplateRecipientException {

		return getPersistence().findByPrimaryKey(
			notificationTemplateRecipientId);
	}

	/**
	 * Returns the notification template recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient, or <code>null</code> if a notification template recipient with the primary key could not be found
	 */
	public static NotificationTemplateRecipient fetchByPrimaryKey(
		long notificationTemplateRecipientId) {

		return getPersistence().fetchByPrimaryKey(
			notificationTemplateRecipientId);
	}

	/**
	 * Returns all the notification template recipients.
	 *
	 * @return the notification template recipients
	 */
	public static List<NotificationTemplateRecipient> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @return the range of notification template recipients
	 */
	public static List<NotificationTemplateRecipient> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template recipients
	 */
	public static List<NotificationTemplateRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipient> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template recipients
	 */
	public static List<NotificationTemplateRecipient> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipient> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification template recipients from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification template recipients.
	 *
	 * @return the number of notification template recipients
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationTemplateRecipientPersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationTemplateRecipientPersistence
		_persistence;

}