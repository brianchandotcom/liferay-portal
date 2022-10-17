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

import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientSettingException;
import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification template recipient setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientSettingUtil
 * @generated
 */
@ProviderType
public interface NotificationTemplateRecipientSettingPersistence
	extends BasePersistence<NotificationTemplateRecipientSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationTemplateRecipientSettingUtil} to access the notification template recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the notification template recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 */
	public void cacheResult(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting);

	/**
	 * Caches the notification template recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSettings the notification template recipient settings
	 */
	public void cacheResult(
		java.util.List<NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings);

	/**
	 * Creates a new notification template recipient setting with the primary key. Does not add the notification template recipient setting to the database.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key for the new notification template recipient setting
	 * @return the new notification template recipient setting
	 */
	public NotificationTemplateRecipientSetting create(
		long notificationTemplateRecipientSettingId);

	/**
	 * Removes the notification template recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	public NotificationTemplateRecipientSetting remove(
			long notificationTemplateRecipientSettingId)
		throws NoSuchNotificationTemplateRecipientSettingException;

	public NotificationTemplateRecipientSetting updateImpl(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting);

	/**
	 * Returns the notification template recipient setting with the primary key or throws a <code>NoSuchNotificationTemplateRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	public NotificationTemplateRecipientSetting findByPrimaryKey(
			long notificationTemplateRecipientSettingId)
		throws NoSuchNotificationTemplateRecipientSettingException;

	/**
	 * Returns the notification template recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting, or <code>null</code> if a notification template recipient setting with the primary key could not be found
	 */
	public NotificationTemplateRecipientSetting fetchByPrimaryKey(
		long notificationTemplateRecipientSettingId);

	/**
	 * Returns all the notification template recipient settings.
	 *
	 * @return the notification template recipient settings
	 */
	public java.util.List<NotificationTemplateRecipientSetting> findAll();

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
	public java.util.List<NotificationTemplateRecipientSetting> findAll(
		int start, int end);

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
	public java.util.List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateRecipientSetting> orderByComparator);

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
	public java.util.List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateRecipientSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification template recipient settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification template recipient settings.
	 *
	 * @return the number of notification template recipient settings
	 */
	public int countAll();

}