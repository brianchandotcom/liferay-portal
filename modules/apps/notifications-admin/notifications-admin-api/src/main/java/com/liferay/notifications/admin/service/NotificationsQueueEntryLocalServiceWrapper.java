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

package com.liferay.notifications.admin.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationsQueueEntryLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationsQueueEntryLocalService
 * @generated
 */
public class NotificationsQueueEntryLocalServiceWrapper
	implements NotificationsQueueEntryLocalService,
			   ServiceWrapper<NotificationsQueueEntryLocalService> {

	public NotificationsQueueEntryLocalServiceWrapper() {
		this(null);
	}

	public NotificationsQueueEntryLocalServiceWrapper(
		NotificationsQueueEntryLocalService
			notificationsQueueEntryLocalService) {

		_notificationsQueueEntryLocalService =
			notificationsQueueEntryLocalService;
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
			addNotificationsQueueEntry(
				long userId, long groupId, String className, long classPK,
				long notificationsTemplateId, String from, String fromName,
				String to, String toName, String cc, String bcc, String subject,
				String body, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.addNotificationsQueueEntry(
			userId, groupId, className, classPK, notificationsTemplateId, from,
			fromName, to, toName, cc, bcc, subject, body, priority);
	}

	/**
	 * Adds the notifications queue entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 * @return the notifications queue entry that was added
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
		addNotificationsQueueEntry(
			com.liferay.notifications.admin.model.NotificationsQueueEntry
				notificationsQueueEntry) {

		return _notificationsQueueEntryLocalService.addNotificationsQueueEntry(
			notificationsQueueEntry);
	}

	/**
	 * Creates a new notifications queue entry with the primary key. Does not add the notifications queue entry to the database.
	 *
	 * @param notificationsQueueEntryId the primary key for the new notifications queue entry
	 * @return the new notifications queue entry
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
		createNotificationsQueueEntry(long notificationsQueueEntryId) {

		return _notificationsQueueEntryLocalService.
			createNotificationsQueueEntry(notificationsQueueEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void deleteNotificationsQueueEntries(java.util.Date sentDate) {
		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntries(
			sentDate);
	}

	@Override
	public void deleteNotificationsQueueEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntries(
			groupId);
	}

	/**
	 * Deletes the notifications queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry that was removed
	 * @throws PortalException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
			deleteNotificationsQueueEntry(long notificationsQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.
			deleteNotificationsQueueEntry(notificationsQueueEntryId);
	}

	/**
	 * Deletes the notifications queue entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 * @return the notifications queue entry that was removed
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
		deleteNotificationsQueueEntry(
			com.liferay.notifications.admin.model.NotificationsQueueEntry
				notificationsQueueEntry) {

		return _notificationsQueueEntryLocalService.
			deleteNotificationsQueueEntry(notificationsQueueEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationsQueueEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationsQueueEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationsQueueEntryLocalService.dynamicQuery();
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

		return _notificationsQueueEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl</code>.
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

		return _notificationsQueueEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl</code>.
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

		return _notificationsQueueEntryLocalService.dynamicQuery(
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

		return _notificationsQueueEntryLocalService.dynamicQueryCount(
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

		return _notificationsQueueEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
		fetchNotificationsQueueEntry(long notificationsQueueEntryId) {

		return _notificationsQueueEntryLocalService.
			fetchNotificationsQueueEntry(notificationsQueueEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationsQueueEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationsQueueEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsQueueEntry>
			getNotificationsQueueEntries(boolean sent) {

		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntries(sent);
	}

	/**
	 * Returns a range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of notifications queue entries
	 */
	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsQueueEntry>
			getNotificationsQueueEntries(int start, int end) {

		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsQueueEntry>
			getNotificationsQueueEntries(
				long groupId, String className, long classPK, boolean sent,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.notifications.admin.model.
						NotificationsQueueEntry> orderByComparator) {

		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntries(
				groupId, className, classPK, sent, start, end,
				orderByComparator);
	}

	/**
	 * Returns the number of notifications queue entries.
	 *
	 * @return the number of notifications queue entries
	 */
	@Override
	public int getNotificationsQueueEntriesCount() {
		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntriesCount();
	}

	@Override
	public int getNotificationsQueueEntriesCount(long groupId) {
		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntriesCount(groupId);
	}

	@Override
	public int getNotificationsQueueEntriesCount(
		long groupId, String className, long classPK, boolean sent) {

		return _notificationsQueueEntryLocalService.
			getNotificationsQueueEntriesCount(
				groupId, className, classPK, sent);
	}

	/**
	 * Returns the notifications queue entry with the primary key.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws PortalException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
			getNotificationsQueueEntry(long notificationsQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.getNotificationsQueueEntry(
			notificationsQueueEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationsQueueEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
			resendNotificationsQueueEntry(long notificationsQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.
			resendNotificationsQueueEntry(notificationsQueueEntryId);
	}

	@Override
	public void sendNotificationsQueueEntries() throws Exception {
		_notificationsQueueEntryLocalService.sendNotificationsQueueEntries();
	}

	@Override
	public void updateNotificationsQueueEntriesTemplateIds(
		long notificationsTemplateId) {

		_notificationsQueueEntryLocalService.
			updateNotificationsQueueEntriesTemplateIds(notificationsTemplateId);
	}

	/**
	 * Updates the notifications queue entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsQueueEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 * @return the notifications queue entry that was updated
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
		updateNotificationsQueueEntry(
			com.liferay.notifications.admin.model.NotificationsQueueEntry
				notificationsQueueEntry) {

		return _notificationsQueueEntryLocalService.
			updateNotificationsQueueEntry(notificationsQueueEntry);
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsQueueEntry
			updateSent(long notificationsQueueEntryId, boolean sent)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsQueueEntryLocalService.updateSent(
			notificationsQueueEntryId, sent);
	}

	@Override
	public NotificationsQueueEntryLocalService getWrappedService() {
		return _notificationsQueueEntryLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationsQueueEntryLocalService
			notificationsQueueEntryLocalService) {

		_notificationsQueueEntryLocalService =
			notificationsQueueEntryLocalService;
	}

	private NotificationsQueueEntryLocalService
		_notificationsQueueEntryLocalService;

}