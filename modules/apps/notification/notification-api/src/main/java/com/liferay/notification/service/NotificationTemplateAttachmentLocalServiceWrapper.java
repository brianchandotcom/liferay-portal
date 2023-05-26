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
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link NotificationTemplateAttachmentLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachmentLocalService
 * @generated
 */
public class NotificationTemplateAttachmentLocalServiceWrapper
	implements NotificationTemplateAttachmentLocalService,
			   ServiceWrapper<NotificationTemplateAttachmentLocalService> {

	public NotificationTemplateAttachmentLocalServiceWrapper() {
		this(null);
	}

	public NotificationTemplateAttachmentLocalServiceWrapper(
		NotificationTemplateAttachmentLocalService
			notificationTemplateAttachmentLocalService) {

		_notificationTemplateAttachmentLocalService =
			notificationTemplateAttachmentLocalService;
	}

	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
			addNotificationTemplateAttachment(
				long companyId, long notificationTemplateId, long objectFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.
			addNotificationTemplateAttachment(
				companyId, notificationTemplateId, objectFieldId);
	}

	/**
	 * Adds the notification template attachment to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 * @return the notification template attachment that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
		addNotificationTemplateAttachment(
			com.liferay.notification.model.NotificationTemplateAttachment
				notificationTemplateAttachment) {

		return _notificationTemplateAttachmentLocalService.
			addNotificationTemplateAttachment(notificationTemplateAttachment);
	}

	/**
	 * Creates a new notification template attachment with the primary key. Does not add the notification template attachment to the database.
	 *
	 * @param notificationTemplateAttachmentId the primary key for the new notification template attachment
	 * @return the new notification template attachment
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
		createNotificationTemplateAttachment(
			long notificationTemplateAttachmentId) {

		return _notificationTemplateAttachmentLocalService.
			createNotificationTemplateAttachment(
				notificationTemplateAttachmentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the notification template attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment that was removed
	 * @throws PortalException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
			deleteNotificationTemplateAttachment(
				long notificationTemplateAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.
			deleteNotificationTemplateAttachment(
				notificationTemplateAttachmentId);
	}

	/**
	 * Deletes the notification template attachment from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 * @return the notification template attachment that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
		deleteNotificationTemplateAttachment(
			com.liferay.notification.model.NotificationTemplateAttachment
				notificationTemplateAttachment) {

		return _notificationTemplateAttachmentLocalService.
			deleteNotificationTemplateAttachment(
				notificationTemplateAttachment);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationTemplateAttachmentLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationTemplateAttachmentLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationTemplateAttachmentLocalService.dynamicQuery();
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

		return _notificationTemplateAttachmentLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateAttachmentModelImpl</code>.
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

		return _notificationTemplateAttachmentLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateAttachmentModelImpl</code>.
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

		return _notificationTemplateAttachmentLocalService.dynamicQuery(
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

		return _notificationTemplateAttachmentLocalService.dynamicQueryCount(
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

		return _notificationTemplateAttachmentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
		fetchNotificationTemplateAttachment(
			long notificationTemplateAttachmentId) {

		return _notificationTemplateAttachmentLocalService.
			fetchNotificationTemplateAttachment(
				notificationTemplateAttachmentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationTemplateAttachmentLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationTemplateAttachmentLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification template attachment with the primary key.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment
	 * @throws PortalException if a notification template attachment with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
			getNotificationTemplateAttachment(
				long notificationTemplateAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.
			getNotificationTemplateAttachment(notificationTemplateAttachmentId);
	}

	/**
	 * Returns a range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @return the range of notification template attachments
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationTemplateAttachment>
			getNotificationTemplateAttachments(int start, int end) {

		return _notificationTemplateAttachmentLocalService.
			getNotificationTemplateAttachments(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationTemplateAttachment>
			getNotificationTemplateAttachments(long notificationTemplateId) {

		return _notificationTemplateAttachmentLocalService.
			getNotificationTemplateAttachments(notificationTemplateId);
	}

	/**
	 * Returns the number of notification template attachments.
	 *
	 * @return the number of notification template attachments
	 */
	@Override
	public int getNotificationTemplateAttachmentsCount() {
		return _notificationTemplateAttachmentLocalService.
			getNotificationTemplateAttachmentsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateAttachmentLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateAttachmentLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the notification template attachment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 * @return the notification template attachment that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplateAttachment
		updateNotificationTemplateAttachment(
			com.liferay.notification.model.NotificationTemplateAttachment
				notificationTemplateAttachment) {

		return _notificationTemplateAttachmentLocalService.
			updateNotificationTemplateAttachment(
				notificationTemplateAttachment);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _notificationTemplateAttachmentLocalService.getBasePersistence();
	}

	@Override
	public NotificationTemplateAttachmentLocalService getWrappedService() {
		return _notificationTemplateAttachmentLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateAttachmentLocalService
			notificationTemplateAttachmentLocalService) {

		_notificationTemplateAttachmentLocalService =
			notificationTemplateAttachmentLocalService;
	}

	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

}