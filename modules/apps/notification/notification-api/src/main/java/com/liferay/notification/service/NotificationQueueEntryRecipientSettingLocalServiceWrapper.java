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

package com.liferay.notification.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationQueueEntryRecipientSettingLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientSettingLocalService
 * @generated
 */
public class NotificationQueueEntryRecipientSettingLocalServiceWrapper
	implements NotificationQueueEntryRecipientSettingLocalService,
			   ServiceWrapper
				   <NotificationQueueEntryRecipientSettingLocalService> {

	public NotificationQueueEntryRecipientSettingLocalServiceWrapper() {
		this(null);
	}

	public NotificationQueueEntryRecipientSettingLocalServiceWrapper(
		NotificationQueueEntryRecipientSettingLocalService
			notificationQueueEntryRecipientSettingLocalService) {

		_notificationQueueEntryRecipientSettingLocalService =
			notificationQueueEntryRecipientSettingLocalService;
	}

	/**
	 * Adds the notification queue entry recipient setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
		addNotificationQueueEntryRecipientSetting(
			com.liferay.notification.model.
				NotificationQueueEntryRecipientSetting
					notificationQueueEntryRecipientSetting) {

		return _notificationQueueEntryRecipientSettingLocalService.
			addNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSetting);
	}

	/**
	 * Creates a new notification queue entry recipient setting with the primary key. Does not add the notification queue entry recipient setting to the database.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key for the new notification queue entry recipient setting
	 * @return the new notification queue entry recipient setting
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
		createNotificationQueueEntryRecipientSetting(
			long notificationQueueEntryRecipientSettingId) {

		return _notificationQueueEntryRecipientSettingLocalService.
			createNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientSettingLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws PortalException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
			deleteNotificationQueueEntryRecipientSetting(
				long notificationQueueEntryRecipientSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientSettingLocalService.
			deleteNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Deletes the notification queue entry recipient setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
		deleteNotificationQueueEntryRecipientSetting(
			com.liferay.notification.model.
				NotificationQueueEntryRecipientSetting
					notificationQueueEntryRecipientSetting) {

		return _notificationQueueEntryRecipientSettingLocalService.
			deleteNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSetting);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientSettingLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationQueueEntryRecipientSettingLocalService.dslQuery(
			dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationQueueEntryRecipientSettingLocalService.
			dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationQueueEntryRecipientSettingLocalService.
			dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationQueueEntryRecipientSettingLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _notificationQueueEntryRecipientSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _notificationQueueEntryRecipientSettingLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationQueueEntryRecipientSettingLocalService.
			dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _notificationQueueEntryRecipientSettingLocalService.
			dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
		fetchNotificationQueueEntryRecipientSetting(
			long notificationQueueEntryRecipientSettingId) {

		return _notificationQueueEntryRecipientSettingLocalService.
			fetchNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSettingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationQueueEntryRecipientSettingLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationQueueEntryRecipientSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification queue entry recipient setting with the primary key.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws PortalException if a notification queue entry recipient setting with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
			getNotificationQueueEntryRecipientSetting(
				long notificationQueueEntryRecipientSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientSettingLocalService.
			getNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns a range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @return the range of notification queue entry recipient settings
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationQueueEntryRecipientSetting>
			getNotificationQueueEntryRecipientSettings(int start, int end) {

		return _notificationQueueEntryRecipientSettingLocalService.
			getNotificationQueueEntryRecipientSettings(start, end);
	}

	/**
	 * Returns the number of notification queue entry recipient settings.
	 *
	 * @return the number of notification queue entry recipient settings
	 */
	@Override
	public int getNotificationQueueEntryRecipientSettingsCount() {
		return _notificationQueueEntryRecipientSettingLocalService.
			getNotificationQueueEntryRecipientSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationQueueEntryRecipientSettingLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientSettingLocalService.
			getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the notification queue entry recipient setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipientSetting
		updateNotificationQueueEntryRecipientSetting(
			com.liferay.notification.model.
				NotificationQueueEntryRecipientSetting
					notificationQueueEntryRecipientSetting) {

		return _notificationQueueEntryRecipientSettingLocalService.
			updateNotificationQueueEntryRecipientSetting(
				notificationQueueEntryRecipientSetting);
	}

	@Override
	public NotificationQueueEntryRecipientSettingLocalService
		getWrappedService() {

		return _notificationQueueEntryRecipientSettingLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationQueueEntryRecipientSettingLocalService
			notificationQueueEntryRecipientSettingLocalService) {

		_notificationQueueEntryRecipientSettingLocalService =
			notificationQueueEntryRecipientSettingLocalService;
	}

	private NotificationQueueEntryRecipientSettingLocalService
		_notificationQueueEntryRecipientSettingLocalService;

}