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
 * Provides a wrapper for {@link NotificationQueueEntryLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryLocalService
 * @generated
 */
public class NotificationQueueEntryLocalServiceWrapper
	implements NotificationQueueEntryLocalService,
			   ServiceWrapper<NotificationQueueEntryLocalService> {

	public NotificationQueueEntryLocalServiceWrapper() {
		this(null);
	}

	public NotificationQueueEntryLocalServiceWrapper(
		NotificationQueueEntryLocalService notificationQueueEntryLocalService) {

		_notificationQueueEntryLocalService =
			notificationQueueEntryLocalService;
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			addNotificationQueueEntry(
				long userId, long notificationTemplateId, String bcc,
				String body, String cc, String className, long classPK,
				String from, String fromName, double priority, String subject,
				String to, String toName, String type,
				java.util.List<Long> fileEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			userId, notificationTemplateId, bcc, body, cc, className, classPK,
			from, fromName, priority, subject, to, toName, type, fileEntryIds);
	}

	/**
	 * Adds the notification queue entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntry the notification queue entry
	 * @return the notification queue entry that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
		addNotificationQueueEntry(
			com.liferay.notification.model.NotificationQueueEntry
				notificationQueueEntry) {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			notificationQueueEntry);
	}

	/**
	 * Creates a new notification queue entry with the primary key. Does not add the notification queue entry to the database.
	 *
	 * @param notificationQueueEntryId the primary key for the new notification queue entry
	 * @return the new notification queue entry
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
		createNotificationQueueEntry(long notificationQueueEntryId) {

		return _notificationQueueEntryLocalService.createNotificationQueueEntry(
			notificationQueueEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void deleteNotificationQueueEntries(java.util.Date sentDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
			sentDate);
	}

	/**
	 * Deletes the notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry that was removed
	 * @throws PortalException if a notification queue entry with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			deleteNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	/**
	 * Deletes the notification queue entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntry the notification queue entry
	 * @return the notification queue entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			deleteNotificationQueueEntry(
				com.liferay.notification.model.NotificationQueueEntry
					notificationQueueEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationQueueEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationQueueEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationQueueEntryLocalService.dynamicQuery();
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

		return _notificationQueueEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryModelImpl</code>.
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

		return _notificationQueueEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryModelImpl</code>.
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

		return _notificationQueueEntryLocalService.dynamicQuery(
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

		return _notificationQueueEntryLocalService.dynamicQueryCount(
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

		return _notificationQueueEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
		fetchNotificationQueueEntry(long notificationQueueEntryId) {

		return _notificationQueueEntryLocalService.fetchNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationQueueEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationQueueEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of notification queue entries
	 */
	@Override
	public java.util.List<com.liferay.notification.model.NotificationQueueEntry>
		getNotificationQueueEntries(int start, int end) {

		return _notificationQueueEntryLocalService.getNotificationQueueEntries(
			start, end);
	}

	/**
	 * Returns the number of notification queue entries.
	 *
	 * @return the number of notification queue entries
	 */
	@Override
	public int getNotificationQueueEntriesCount() {
		return _notificationQueueEntryLocalService.
			getNotificationQueueEntriesCount();
	}

	/**
	 * Returns the notification queue entry with the primary key.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry
	 * @throws PortalException if a notification queue entry with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			getNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.getNotificationQueueEntry(
			notificationQueueEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationQueueEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			resendNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.resendNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public void sendNotificationQueueEntries() {
		_notificationQueueEntryLocalService.sendNotificationQueueEntries();
	}

	/**
	 * Updates the notification queue entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntry the notification queue entry
	 * @return the notification queue entry that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntry
		updateNotificationQueueEntry(
			com.liferay.notification.model.NotificationQueueEntry
				notificationQueueEntry) {

		return _notificationQueueEntryLocalService.updateNotificationQueueEntry(
			notificationQueueEntry);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry updateStatus(
			long notificationQueueEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryLocalService.updateStatus(
			notificationQueueEntryId, status);
	}

	@Override
	public NotificationQueueEntryLocalService getWrappedService() {
		return _notificationQueueEntryLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationQueueEntryLocalService notificationQueueEntryLocalService) {

		_notificationQueueEntryLocalService =
			notificationQueueEntryLocalService;
	}

	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

}