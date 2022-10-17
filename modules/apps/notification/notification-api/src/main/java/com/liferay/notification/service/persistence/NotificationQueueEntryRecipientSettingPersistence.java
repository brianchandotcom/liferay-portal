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

import com.liferay.notification.exception.NoSuchNotificationQueueEntryRecipientSettingException;
import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification queue entry recipient setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientSettingUtil
 * @generated
 */
@ProviderType
public interface NotificationQueueEntryRecipientSettingPersistence
	extends BasePersistence<NotificationQueueEntryRecipientSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationQueueEntryRecipientSettingUtil} to access the notification queue entry recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the notification queue entry recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 */
	public void cacheResult(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting);

	/**
	 * Caches the notification queue entry recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryRecipientSettings the notification queue entry recipient settings
	 */
	public void cacheResult(
		java.util.List<NotificationQueueEntryRecipientSetting>
			notificationQueueEntryRecipientSettings);

	/**
	 * Creates a new notification queue entry recipient setting with the primary key. Does not add the notification queue entry recipient setting to the database.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key for the new notification queue entry recipient setting
	 * @return the new notification queue entry recipient setting
	 */
	public NotificationQueueEntryRecipientSetting create(
		long notificationQueueEntryRecipientSettingId);

	/**
	 * Removes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public NotificationQueueEntryRecipientSetting remove(
			long notificationQueueEntryRecipientSettingId)
		throws NoSuchNotificationQueueEntryRecipientSettingException;

	public NotificationQueueEntryRecipientSetting updateImpl(
		NotificationQueueEntryRecipientSetting
			notificationQueueEntryRecipientSetting);

	/**
	 * Returns the notification queue entry recipient setting with the primary key or throws a <code>NoSuchNotificationQueueEntryRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws NoSuchNotificationQueueEntryRecipientSettingException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public NotificationQueueEntryRecipientSetting findByPrimaryKey(
			long notificationQueueEntryRecipientSettingId)
		throws NoSuchNotificationQueueEntryRecipientSettingException;

	/**
	 * Returns the notification queue entry recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting, or <code>null</code> if a notification queue entry recipient setting with the primary key could not be found
	 */
	public NotificationQueueEntryRecipientSetting fetchByPrimaryKey(
		long notificationQueueEntryRecipientSettingId);

	/**
	 * Returns all the notification queue entry recipient settings.
	 *
	 * @return the notification queue entry recipient settings
	 */
	public java.util.List<NotificationQueueEntryRecipientSetting> findAll();

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
	public java.util.List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end);

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
	public java.util.List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryRecipientSetting> orderByComparator);

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
	public java.util.List<NotificationQueueEntryRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryRecipientSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification queue entry recipient settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification queue entry recipient settings.
	 *
	 * @return the number of notification queue entry recipient settings
	 */
	public int countAll();

}