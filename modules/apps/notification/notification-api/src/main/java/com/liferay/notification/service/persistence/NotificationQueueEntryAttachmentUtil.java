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

import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification queue entry attachment service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationQueueEntryAttachmentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachmentPersistence
 * @generated
 */
public class NotificationQueueEntryAttachmentUtil {

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
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		getPersistence().clearCache(notificationQueueEntryAttachment);
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
	public static Map<Serializable, NotificationQueueEntryAttachment>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationQueueEntryAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationQueueEntryAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationQueueEntryAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationQueueEntryAttachment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationQueueEntryAttachment update(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		return getPersistence().update(notificationQueueEntryAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationQueueEntryAttachment update(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment,
		ServiceContext serviceContext) {

		return getPersistence().update(
			notificationQueueEntryAttachment, serviceContext);
	}

	/**
	 * Returns all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the matching notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(long notificationQueueEntryId) {

		return getPersistence().findByNotificationQueueEntryId(
			notificationQueueEntryId);
	}

	/**
	 * Returns a range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of matching notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end) {

		return getPersistence().findByNotificationQueueEntryId(
			notificationQueueEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		return getPersistence().findByNotificationQueueEntryId(
			notificationQueueEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByNotificationQueueEntryId(
			notificationQueueEntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	public static NotificationQueueEntryAttachment
			findByNotificationQueueEntryId_First(
				long notificationQueueEntryId,
				OrderByComparator<NotificationQueueEntryAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryAttachmentException {

		return getPersistence().findByNotificationQueueEntryId_First(
			notificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	public static NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_First(
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		return getPersistence().fetchByNotificationQueueEntryId_First(
			notificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	public static NotificationQueueEntryAttachment
			findByNotificationQueueEntryId_Last(
				long notificationQueueEntryId,
				OrderByComparator<NotificationQueueEntryAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryAttachmentException {

		return getPersistence().findByNotificationQueueEntryId_Last(
			notificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	public static NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_Last(
			long notificationQueueEntryId,
			OrderByComparator<NotificationQueueEntryAttachment>
				orderByComparator) {

		return getPersistence().fetchByNotificationQueueEntryId_Last(
			notificationQueueEntryId, orderByComparator);
	}

	/**
	 * Returns the notification queue entry attachments before and after the current notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the current notification queue entry attachment
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public static NotificationQueueEntryAttachment[]
			findByNotificationQueueEntryId_PrevAndNext(
				long notificationQueueEntryAttachmentId,
				long notificationQueueEntryId,
				OrderByComparator<NotificationQueueEntryAttachment>
					orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryAttachmentException {

		return getPersistence().findByNotificationQueueEntryId_PrevAndNext(
			notificationQueueEntryAttachmentId, notificationQueueEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the notification queue entry attachments where notificationQueueEntryId = &#63; from the database.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 */
	public static void removeByNotificationQueueEntryId(
		long notificationQueueEntryId) {

		getPersistence().removeByNotificationQueueEntryId(
			notificationQueueEntryId);
	}

	/**
	 * Returns the number of notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the number of matching notification queue entry attachments
	 */
	public static int countByNotificationQueueEntryId(
		long notificationQueueEntryId) {

		return getPersistence().countByNotificationQueueEntryId(
			notificationQueueEntryId);
	}

	/**
	 * Caches the notification queue entry attachment in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 */
	public static void cacheResult(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		getPersistence().cacheResult(notificationQueueEntryAttachment);
	}

	/**
	 * Caches the notification queue entry attachments in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachments the notification queue entry attachments
	 */
	public static void cacheResult(
		List<NotificationQueueEntryAttachment>
			notificationQueueEntryAttachments) {

		getPersistence().cacheResult(notificationQueueEntryAttachments);
	}

	/**
	 * Creates a new notification queue entry attachment with the primary key. Does not add the notification queue entry attachment to the database.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key for the new notification queue entry attachment
	 * @return the new notification queue entry attachment
	 */
	public static NotificationQueueEntryAttachment create(
		long notificationQueueEntryAttachmentId) {

		return getPersistence().create(notificationQueueEntryAttachmentId);
	}

	/**
	 * Removes the notification queue entry attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public static NotificationQueueEntryAttachment remove(
			long notificationQueueEntryAttachmentId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryAttachmentException {

		return getPersistence().remove(notificationQueueEntryAttachmentId);
	}

	public static NotificationQueueEntryAttachment updateImpl(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		return getPersistence().updateImpl(notificationQueueEntryAttachment);
	}

	/**
	 * Returns the notification queue entry attachment with the primary key or throws a <code>NoSuchNotificationQueueEntryAttachmentException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public static NotificationQueueEntryAttachment findByPrimaryKey(
			long notificationQueueEntryAttachmentId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryAttachmentException {

		return getPersistence().findByPrimaryKey(
			notificationQueueEntryAttachmentId);
	}

	/**
	 * Returns the notification queue entry attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment, or <code>null</code> if a notification queue entry attachment with the primary key could not be found
	 */
	public static NotificationQueueEntryAttachment fetchByPrimaryKey(
		long notificationQueueEntryAttachmentId) {

		return getPersistence().fetchByPrimaryKey(
			notificationQueueEntryAttachmentId);
	}

	/**
	 * Returns all the notification queue entry attachments.
	 *
	 * @return the notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryAttachment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry attachments
	 */
	public static List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntryAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification queue entry attachments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification queue entry attachments.
	 *
	 * @return the number of notification queue entry attachments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationQueueEntryAttachmentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NotificationQueueEntryAttachmentPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NotificationQueueEntryAttachmentPersistence
		_persistence;

}