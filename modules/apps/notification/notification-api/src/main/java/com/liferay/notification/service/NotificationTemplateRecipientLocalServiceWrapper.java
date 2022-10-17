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
 * Provides a wrapper for {@link NotificationTemplateRecipientLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientLocalService
 * @generated
 */
public class NotificationTemplateRecipientLocalServiceWrapper
	implements NotificationTemplateRecipientLocalService,
			   ServiceWrapper<NotificationTemplateRecipientLocalService> {

	public NotificationTemplateRecipientLocalServiceWrapper() {
		this(null);
	}

	public NotificationTemplateRecipientLocalServiceWrapper(
		NotificationTemplateRecipientLocalService
			notificationTemplateRecipientLocalService) {

		_notificationTemplateRecipientLocalService =
			notificationTemplateRecipientLocalService;
	}

	/**
	 * Adds the notification template recipient to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 * @return the notification template recipient that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
		addNotificationTemplateRecipient(
			com.liferay.notification.model.NotificationTemplateRecipient
				notificationTemplateRecipient) {

		return _notificationTemplateRecipientLocalService.
			addNotificationTemplateRecipient(notificationTemplateRecipient);
	}

	/**
	 * Creates a new notification template recipient with the primary key. Does not add the notification template recipient to the database.
	 *
	 * @param notificationTemplateRecipientId the primary key for the new notification template recipient
	 * @return the new notification template recipient
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
		createNotificationTemplateRecipient(
			long notificationTemplateRecipientId) {

		return _notificationTemplateRecipientLocalService.
			createNotificationTemplateRecipient(
				notificationTemplateRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the notification template recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient that was removed
	 * @throws PortalException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
			deleteNotificationTemplateRecipient(
				long notificationTemplateRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientLocalService.
			deleteNotificationTemplateRecipient(
				notificationTemplateRecipientId);
	}

	/**
	 * Deletes the notification template recipient from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 * @return the notification template recipient that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
		deleteNotificationTemplateRecipient(
			com.liferay.notification.model.NotificationTemplateRecipient
				notificationTemplateRecipient) {

		return _notificationTemplateRecipientLocalService.
			deleteNotificationTemplateRecipient(notificationTemplateRecipient);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationTemplateRecipientLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationTemplateRecipientLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationTemplateRecipientLocalService.dynamicQuery();
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

		return _notificationTemplateRecipientLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl</code>.
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

		return _notificationTemplateRecipientLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl</code>.
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

		return _notificationTemplateRecipientLocalService.dynamicQuery(
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

		return _notificationTemplateRecipientLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _notificationTemplateRecipientLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
		fetchNotificationTemplateRecipient(
			long notificationTemplateRecipientId) {

		return _notificationTemplateRecipientLocalService.
			fetchNotificationTemplateRecipient(notificationTemplateRecipientId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationTemplateRecipientLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationTemplateRecipientLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification template recipient with the primary key.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws PortalException if a notification template recipient with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
			getNotificationTemplateRecipient(
				long notificationTemplateRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientLocalService.
			getNotificationTemplateRecipient(notificationTemplateRecipientId);
	}

	/**
	 * Returns a range of all the notification template recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipients
	 * @param end the upper bound of the range of notification template recipients (not inclusive)
	 * @return the range of notification template recipients
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationTemplateRecipient>
			getNotificationTemplateRecipients(int start, int end) {

		return _notificationTemplateRecipientLocalService.
			getNotificationTemplateRecipients(start, end);
	}

	/**
	 * Returns the number of notification template recipients.
	 *
	 * @return the number of notification template recipients
	 */
	@Override
	public int getNotificationTemplateRecipientsCount() {
		return _notificationTemplateRecipientLocalService.
			getNotificationTemplateRecipientsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateRecipientLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateRecipientLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the notification template recipient in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateRecipient the notification template recipient
	 * @return the notification template recipient that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateRecipient
		updateNotificationTemplateRecipient(
			com.liferay.notification.model.NotificationTemplateRecipient
				notificationTemplateRecipient) {

		return _notificationTemplateRecipientLocalService.
			updateNotificationTemplateRecipient(notificationTemplateRecipient);
	}

	@Override
	public NotificationTemplateRecipientLocalService getWrappedService() {
		return _notificationTemplateRecipientLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateRecipientLocalService
			notificationTemplateRecipientLocalService) {

		_notificationTemplateRecipientLocalService =
			notificationTemplateRecipientLocalService;
	}

	private NotificationTemplateRecipientLocalService
		_notificationTemplateRecipientLocalService;

}