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
 * Provides a wrapper for {@link NotificationQueueEntryRecipientLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientLocalService
 * @generated
 */
public class NotificationQueueEntryRecipientLocalServiceWrapper
	implements NotificationQueueEntryRecipientLocalService,
			   ServiceWrapper<NotificationQueueEntryRecipientLocalService> {

	public NotificationQueueEntryRecipientLocalServiceWrapper() {
		this(null);
	}

	public NotificationQueueEntryRecipientLocalServiceWrapper(
		NotificationQueueEntryRecipientLocalService
			notificationQueueEntryRecipientLocalService) {

		_notificationQueueEntryRecipientLocalService =
			notificationQueueEntryRecipientLocalService;
	}

	/**
	 * Adds the notification queue entry recipient to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 * @return the notification queue entry recipient that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
		addNotificationQueueEntryRecipient(
			com.liferay.notification.model.NotificationQueueEntryRecipient
				notificationQueueEntryRecipient) {

		return _notificationQueueEntryRecipientLocalService.
			addNotificationQueueEntryRecipient(notificationQueueEntryRecipient);
	}

	/**
	 * Creates a new notification queue entry recipient with the primary key. Does not add the notification queue entry recipient to the database.
	 *
	 * @param notificationQueueEntryRecipientId the primary key for the new notification queue entry recipient
	 * @return the new notification queue entry recipient
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
		createNotificationQueueEntryRecipient(
			long notificationQueueEntryRecipientId) {

		return _notificationQueueEntryRecipientLocalService.
			createNotificationQueueEntryRecipient(
				notificationQueueEntryRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the notification queue entry recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 * @throws PortalException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
			deleteNotificationQueueEntryRecipient(
				long notificationQueueEntryRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientLocalService.
			deleteNotificationQueueEntryRecipient(
				notificationQueueEntryRecipientId);
	}

	/**
	 * Deletes the notification queue entry recipient from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 * @return the notification queue entry recipient that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
		deleteNotificationQueueEntryRecipient(
			com.liferay.notification.model.NotificationQueueEntryRecipient
				notificationQueueEntryRecipient) {

		return _notificationQueueEntryRecipientLocalService.
			deleteNotificationQueueEntryRecipient(
				notificationQueueEntryRecipient);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationQueueEntryRecipientLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationQueueEntryRecipientLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationQueueEntryRecipientLocalService.dynamicQuery();
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

		return _notificationQueueEntryRecipientLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientModelImpl</code>.
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

		return _notificationQueueEntryRecipientLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientModelImpl</code>.
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

		return _notificationQueueEntryRecipientLocalService.dynamicQuery(
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

		return _notificationQueueEntryRecipientLocalService.dynamicQueryCount(
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

		return _notificationQueueEntryRecipientLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
		fetchNotificationQueueEntryRecipient(
			long notificationQueueEntryRecipientId) {

		return _notificationQueueEntryRecipientLocalService.
			fetchNotificationQueueEntryRecipient(
				notificationQueueEntryRecipientId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationQueueEntryRecipientLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationQueueEntryRecipientLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification queue entry recipient with the primary key.
	 *
	 * @param notificationQueueEntryRecipientId the primary key of the notification queue entry recipient
	 * @return the notification queue entry recipient
	 * @throws PortalException if a notification queue entry recipient with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
			getNotificationQueueEntryRecipient(
				long notificationQueueEntryRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientLocalService.
			getNotificationQueueEntryRecipient(
				notificationQueueEntryRecipientId);
	}

	/**
	 * Returns a range of all the notification queue entry recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipients
	 * @param end the upper bound of the range of notification queue entry recipients (not inclusive)
	 * @return the range of notification queue entry recipients
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationQueueEntryRecipient>
			getNotificationQueueEntryRecipients(int start, int end) {

		return _notificationQueueEntryRecipientLocalService.
			getNotificationQueueEntryRecipients(start, end);
	}

	/**
	 * Returns the number of notification queue entry recipients.
	 *
	 * @return the number of notification queue entry recipients
	 */
	@Override
	public int getNotificationQueueEntryRecipientsCount() {
		return _notificationQueueEntryRecipientLocalService.
			getNotificationQueueEntryRecipientsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationQueueEntryRecipientLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryRecipientLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the notification queue entry recipient in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipient the notification queue entry recipient
	 * @return the notification queue entry recipient that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryRecipient
		updateNotificationQueueEntryRecipient(
			com.liferay.notification.model.NotificationQueueEntryRecipient
				notificationQueueEntryRecipient) {

		return _notificationQueueEntryRecipientLocalService.
			updateNotificationQueueEntryRecipient(
				notificationQueueEntryRecipient);
	}

	@Override
	public NotificationQueueEntryRecipientLocalService getWrappedService() {
		return _notificationQueueEntryRecipientLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationQueueEntryRecipientLocalService
			notificationQueueEntryRecipientLocalService) {

		_notificationQueueEntryRecipientLocalService =
			notificationQueueEntryRecipientLocalService;
	}

	private NotificationQueueEntryRecipientLocalService
		_notificationQueueEntryRecipientLocalService;

}