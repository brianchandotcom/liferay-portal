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
 * Provides a wrapper for {@link NotificationTemplateRecipientSettingLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientSettingLocalService
 * @generated
 */
public class NotificationTemplateRecipientSettingLocalServiceWrapper
	implements NotificationTemplateRecipientSettingLocalService,
			   ServiceWrapper
				   <NotificationTemplateRecipientSettingLocalService> {

	public NotificationTemplateRecipientSettingLocalServiceWrapper() {
		this(null);
	}

	public NotificationTemplateRecipientSettingLocalServiceWrapper(
		NotificationTemplateRecipientSettingLocalService
			notificationTemplateRecipientSettingLocalService) {

		_notificationTemplateRecipientSettingLocalService =
			notificationTemplateRecipientSettingLocalService;
	}

	/**
	 * Adds the notification template recipient setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 * @return the notification template recipient setting that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
		addNotificationTemplateRecipientSetting(
			com.liferay.notification.model.NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting) {

		return _notificationTemplateRecipientSettingLocalService.
			addNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSetting);
	}

	/**
	 * Creates a new notification template recipient setting with the primary key. Does not add the notification template recipient setting to the database.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key for the new notification template recipient setting
	 * @return the new notification template recipient setting
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
		createNotificationTemplateRecipientSetting(
			long notificationTemplateRecipientSettingId) {

		return _notificationTemplateRecipientSettingLocalService.
			createNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientSettingLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the notification template recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 * @throws PortalException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
			deleteNotificationTemplateRecipientSetting(
				long notificationTemplateRecipientSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientSettingLocalService.
			deleteNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSettingId);
	}

	/**
	 * Deletes the notification template recipient setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
		deleteNotificationTemplateRecipientSetting(
			com.liferay.notification.model.NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting) {

		return _notificationTemplateRecipientSettingLocalService.
			deleteNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSetting);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientSettingLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationTemplateRecipientSettingLocalService.dslQuery(
			dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationTemplateRecipientSettingLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationTemplateRecipientSettingLocalService.dynamicQuery();
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

		return _notificationTemplateRecipientSettingLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientSettingModelImpl</code>.
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

		return _notificationTemplateRecipientSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientSettingModelImpl</code>.
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

		return _notificationTemplateRecipientSettingLocalService.dynamicQuery(
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

		return _notificationTemplateRecipientSettingLocalService.
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

		return _notificationTemplateRecipientSettingLocalService.
			dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
		fetchNotificationTemplateRecipientSetting(
			long notificationTemplateRecipientSettingId) {

		return _notificationTemplateRecipientSettingLocalService.
			fetchNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSettingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationTemplateRecipientSettingLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationTemplateRecipientSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification template recipient setting with the primary key.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting
	 * @throws PortalException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
			getNotificationTemplateRecipientSetting(
				long notificationTemplateRecipientSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientSettingLocalService.
			getNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSettingId);
	}

	/**
	 * Returns a range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @return the range of notification template recipient settings
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationTemplateRecipientSetting>
			getNotificationTemplateRecipientSettings(int start, int end) {

		return _notificationTemplateRecipientSettingLocalService.
			getNotificationTemplateRecipientSettings(start, end);
	}

	/**
	 * Returns the number of notification template recipient settings.
	 *
	 * @return the number of notification template recipient settings
	 */
	@Override
	public int getNotificationTemplateRecipientSettingsCount() {
		return _notificationTemplateRecipientSettingLocalService.
			getNotificationTemplateRecipientSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateRecipientSettingLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientSettingLocalService.
			getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the notification template recipient setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 * @return the notification template recipient setting that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipientSetting
		updateNotificationTemplateRecipientSetting(
			com.liferay.notification.model.NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting) {

		return _notificationTemplateRecipientSettingLocalService.
			updateNotificationTemplateRecipientSetting(
				notificationTemplateRecipientSetting);
	}

	@Override
	public NotificationTemplateRecipientSettingLocalService
		getWrappedService() {

		return _notificationTemplateRecipientSettingLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateRecipientSettingLocalService
			notificationTemplateRecipientSettingLocalService) {

		_notificationTemplateRecipientSettingLocalService =
			notificationTemplateRecipientSettingLocalService;
	}

	private NotificationTemplateRecipientSettingLocalService
		_notificationTemplateRecipientSettingLocalService;

}