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

import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientException;
import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification template recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientUtil
 * @generated
 */
@ProviderType
public interface NotificationTemplateRecipientPersistence
	extends BasePersistence<NotificationTemplateRecipient> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationTemplateRecipientUtil} to access the notification template recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a matching notification template recipient could not be found
	 */
	public NotificationTemplateRecipient findByNotificationTemplateId(
			long notificationTemplateId)
		throws NoSuchNotificationTemplateRecipientException;

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	public NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId);

	/**
	 * Returns the notification template recipient where notificationTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template recipient, or <code>null</code> if a matching notification template recipient could not be found
	 */
	public NotificationTemplateRecipient fetchByNotificationTemplateId(
		long notificationTemplateId, boolean useFinderCache);

	/**
	 * Removes the notification template recipient where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the notification template recipient that was removed
	 */
	public NotificationTemplateRecipient removeByNotificationTemplateId(
			long notificationTemplateId)
		throws NoSuchNotificationTemplateRecipientException;

	/**
	 * Returns the number of notification template recipients where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template recipients
	 */
	public int countByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Caches the notification template recipient in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 */
	public void cacheResult(
		NotificationTemplateRecipient notificationTemplateRecipient);

	/**
	 * Caches the notification template recipients in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipients the notification template recipients
	 */
	public void cacheResult(
		java.util.List<NotificationTemplateRecipient>
			notificationTemplateRecipients);

	/**
	 * Creates a new notification template recipient with the primary key. Does not add the notification template recipient to the database.
	 *
	 * @param notificationTemplateRecipientId the primary key for the new notification template recipient
	 * @return the new notification template recipient
	 */
	public NotificationTemplateRecipient create(
		long notificationTemplateRecipientId);

	/**
	 * Removes the notification template recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient that was removed
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	public NotificationTemplateRecipient remove(
			long notificationTemplateRecipientId)
		throws NoSuchNotificationTemplateRecipientException;

	public NotificationTemplateRecipient updateImpl(
		NotificationTemplateRecipient notificationTemplateRecipient);

	/**
	 * Returns the notification template recipient with the primary key or throws a <code>NoSuchNotificationTemplateRecipientException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws NoSuchNotificationTemplateRecipientException if a notification template recipient with the primary key could not be found
	 */
	public NotificationTemplateRecipient findByPrimaryKey(
			long notificationTemplateRecipientId)
		throws NoSuchNotificationTemplateRecipientException;

	/**
	 * Returns the notification template recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient, or <code>null</code> if a notification template recipient with the primary key could not be found
	 */
	public NotificationTemplateRecipient fetchByPrimaryKey(
		long notificationTemplateRecipientId);

	/**
	 * Returns all the notification template recipients.
	 *
	 * @return the notification template recipients
	 */
	public java.util.List<NotificationTemplateRecipient> findAll();

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
	public java.util.List<NotificationTemplateRecipient> findAll(
		int start, int end);

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
	public java.util.List<NotificationTemplateRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateRecipient> orderByComparator);

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
	public java.util.List<NotificationTemplateRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateRecipient> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification template recipients from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification template recipients.
	 *
	 * @return the number of notification template recipients
	 */
	public int countAll();

}