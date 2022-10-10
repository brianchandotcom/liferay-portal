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
 * Provides a wrapper for {@link NotificationTemplateLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateLocalService
 * @generated
 */
public class NotificationTemplateLocalServiceWrapper
	implements NotificationTemplateLocalService,
			   ServiceWrapper<NotificationTemplateLocalService> {

	public NotificationTemplateLocalServiceWrapper() {
		this(null);
	}

	public NotificationTemplateLocalServiceWrapper(
		NotificationTemplateLocalService notificationTemplateLocalService) {

		_notificationTemplateLocalService = notificationTemplateLocalService;
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			addNotificationTemplate(
				long userId, long objectDefinitionId, String bcc,
				java.util.Map<java.util.Locale, String> bodyMap, String cc,
				String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, String recipientType,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> toMap, String type,
				java.util.List<Long> attachmentObjectFieldIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.addNotificationTemplate(
			userId, objectDefinitionId, bcc, bodyMap, cc, description, from,
			fromNameMap, name, recipientType, subjectMap, toMap, type,
			attachmentObjectFieldIds);
	}

	/**
	 * Adds the notification template to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplate the notification template
	 * @return the notification template that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
		addNotificationTemplate(
			com.liferay.notification.model.NotificationTemplate
				notificationTemplate) {

		return _notificationTemplateLocalService.addNotificationTemplate(
			notificationTemplate);
	}

	/**
	 * Creates a new notification template with the primary key. Does not add the notification template to the database.
	 *
	 * @param notificationTemplateId the primary key for the new notification template
	 * @return the new notification template
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
		createNotificationTemplate(long notificationTemplateId) {

		return _notificationTemplateLocalService.createNotificationTemplate(
			notificationTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template that was removed
	 * @throws PortalException if a notification template with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplateId);
	}

	/**
	 * Deletes the notification template from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplate the notification template
	 * @return the notification template that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(
				com.liferay.notification.model.NotificationTemplate
					notificationTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationTemplateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationTemplateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationTemplateLocalService.dynamicQuery();
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

		return _notificationTemplateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateModelImpl</code>.
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

		return _notificationTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateModelImpl</code>.
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

		return _notificationTemplateLocalService.dynamicQuery(
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

		return _notificationTemplateLocalService.dynamicQueryCount(
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

		return _notificationTemplateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
		fetchNotificationTemplate(long notificationTemplateId) {

		return _notificationTemplateLocalService.fetchNotificationTemplate(
			notificationTemplateId);
	}

	/**
	 * Returns the notification template with the matching UUID and company.
	 *
	 * @param uuid the notification template's UUID
	 * @param companyId the primary key of the company
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
		fetchNotificationTemplateByUuidAndCompanyId(
			String uuid, long companyId) {

		return _notificationTemplateLocalService.
			fetchNotificationTemplateByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationTemplateLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _notificationTemplateLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification template with the primary key.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template
	 * @throws PortalException if a notification template with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
			getNotificationTemplate(long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.getNotificationTemplate(
			notificationTemplateId);
	}

	/**
	 * Returns the notification template with the matching UUID and company.
	 *
	 * @param uuid the notification template's UUID
	 * @param companyId the primary key of the company
	 * @return the matching notification template
	 * @throws PortalException if a matching notification template could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
			getNotificationTemplateByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.
			getNotificationTemplateByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of notification templates
	 */
	@Override
	public java.util.List<com.liferay.notification.model.NotificationTemplate>
		getNotificationTemplates(int start, int end) {

		return _notificationTemplateLocalService.getNotificationTemplates(
			start, end);
	}

	/**
	 * Returns the number of notification templates.
	 *
	 * @return the number of notification templates
	 */
	@Override
	public int getNotificationTemplatesCount() {
		return _notificationTemplateLocalService.
			getNotificationTemplatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			updateNotificationTemplate(
				long notificationTemplateId, long objectDefinitionId,
				String bcc, java.util.Map<java.util.Locale, String> bodyMap,
				String cc, String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, String recipientType,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> toMap, String type,
				java.util.List<Long> attachmentObjectFieldIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateLocalService.updateNotificationTemplate(
			notificationTemplateId, objectDefinitionId, bcc, bodyMap, cc,
			description, from, fromNameMap, name, recipientType, subjectMap,
			toMap, type, attachmentObjectFieldIds);
	}

	/**
	 * Updates the notification template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationTemplate the notification template
	 * @return the notification template that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationTemplate
		updateNotificationTemplate(
			com.liferay.notification.model.NotificationTemplate
				notificationTemplate) {

		return _notificationTemplateLocalService.updateNotificationTemplate(
			notificationTemplate);
	}

	@Override
	public NotificationTemplateLocalService getWrappedService() {
		return _notificationTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateLocalService notificationTemplateLocalService) {

		_notificationTemplateLocalService = notificationTemplateLocalService;
	}

	private NotificationTemplateLocalService _notificationTemplateLocalService;

}