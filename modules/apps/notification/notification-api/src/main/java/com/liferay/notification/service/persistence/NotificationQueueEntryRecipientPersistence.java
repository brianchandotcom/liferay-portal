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

import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientException;
import com.liferay.notification.model.NotificationQueueEntryRecipient;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification queue entry recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientUtil
 * @generated
 */
@ProviderType
public interface NotificationQueueEntryRecipientPersistence
	extends BasePersistence<NotificationQueueEntryRecipient> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationQueueEntryRecipientUtil} to access the notification queue entry recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the notification queue entry recipient in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 */
	public void cacheResult(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient);

	/**
	 * Caches the notification queue entry recipients in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipients the notification queue entry recipients
	 */
	public void cacheResult(
		java.util.List<NotificationQueueEntryRecipient>
			notificationQueueEntryRecipients);

	/**
	 * Creates a new notification queue entry recipient with the primary key. Does not add the notification queue entry recipient to the database.
	 *
	 * @param notificationQueueEntryRecipientId the primary key for the new notification queue entry recipient
	 * @return the new notification queue entry recipient
	 */
	public NotificationQueueEntryRecipient create(
		long notificationQueueEntryRecipientId);

	/**
	 * Removes the notification queue entry recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	public NotificationQueueEntryRecipient remove(
			long notificationQueueEntryRecipientId)
		throws NoSuchNotificationQueueEntryRecipientException;

	public NotificationQueueEntryRecipient updateImpl(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient);

	/**
	 * Returns the notification queue entry recipient with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient
	 * @throws NoSuchNotificationQueueEntryRecipientException if a notification queue entry recipient with the primary key could not be found
	 */
	public NotificationQueueEntryRecipient findByPrimaryKey(
			long notificationQueueEntryRecipientId)
		throws NoSuchNotificationQueueEntryRecipientException;

	/**
	 * Returns the notification queue entry recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient, or <code>null</code> if a notification queue entry recipient with the primary key could not be found
	 */
	public NotificationQueueEntryRecipient fetchByPrimaryKey(
		long notificationQueueEntryRecipientId);

	/**
	 * Returns all the notification queue entry recipients.
	 *
	 * @return the notification queue entry recipients
	 */
	public java.util.List<NotificationQueueEntryRecipient> findAll();

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
	public java.util.List<NotificationQueueEntryRecipient> findAll(
		int start, int end);

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
	public java.util.List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryRecipient> orderByComparator);

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
	public java.util.List<NotificationQueueEntryRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryRecipient> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification queue entry recipients from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification queue entry recipients.
	 *
	 * @return the number of notification queue entry recipients
	 */
	public int countAll();

}