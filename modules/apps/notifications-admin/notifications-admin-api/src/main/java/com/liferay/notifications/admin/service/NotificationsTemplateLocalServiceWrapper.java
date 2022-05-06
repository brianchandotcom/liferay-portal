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
 * Provides a wrapper for {@link NotificationsTemplateLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationsTemplateLocalService
 * @generated
 */
public class NotificationsTemplateLocalServiceWrapper
	implements NotificationsTemplateLocalService,
			   ServiceWrapper<NotificationsTemplateLocalService> {

	public NotificationsTemplateLocalServiceWrapper() {
		this(null);
	}

	public NotificationsTemplateLocalServiceWrapper(
		NotificationsTemplateLocalService notificationsTemplateLocalService) {

		_notificationsTemplateLocalService = notificationsTemplateLocalService;
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			addNotificationsTemplate(
				long userId, long groupId, String name, String description,
				String from,
				java.util.Map<java.util.Locale, String> fromNameMap, String to,
				String cc, String bcc, boolean enabled,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.addNotificationsTemplate(
			userId, groupId, name, description, from, fromNameMap, to, cc, bcc,
			enabled, subjectMap, bodyMap, serviceContext);
	}

	/**
	 * Adds the notifications template to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsTemplate the notifications template
	 * @return the notifications template that was added
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
		addNotificationsTemplate(
			com.liferay.notifications.admin.model.NotificationsTemplate
				notificationsTemplate) {

		return _notificationsTemplateLocalService.addNotificationsTemplate(
			notificationsTemplate);
	}

	/**
	 * Creates a new notifications template with the primary key. Does not add the notifications template to the database.
	 *
	 * @param notificationsTemplateId the primary key for the new notifications template
	 * @return the new notifications template
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
		createNotificationsTemplate(long notificationsTemplateId) {

		return _notificationsTemplateLocalService.createNotificationsTemplate(
			notificationsTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the notifications template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template that was removed
	 * @throws PortalException if a notifications template with the primary key could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			deleteNotificationsTemplate(long notificationsTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.deleteNotificationsTemplate(
			notificationsTemplateId);
	}

	/**
	 * Deletes the notifications template from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsTemplate the notifications template
	 * @return the notifications template that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			deleteNotificationsTemplate(
				com.liferay.notifications.admin.model.NotificationsTemplate
					notificationsTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.deleteNotificationsTemplate(
			notificationsTemplate);
	}

	@Override
	public void deleteNotificationsTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_notificationsTemplateLocalService.deleteNotificationsTemplates(
			groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationsTemplateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationsTemplateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationsTemplateLocalService.dynamicQuery();
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

		return _notificationsTemplateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsTemplateModelImpl</code>.
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

		return _notificationsTemplateLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsTemplateModelImpl</code>.
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

		return _notificationsTemplateLocalService.dynamicQuery(
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

		return _notificationsTemplateLocalService.dynamicQueryCount(
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

		return _notificationsTemplateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
		fetchNotificationsTemplate(long notificationsTemplateId) {

		return _notificationsTemplateLocalService.fetchNotificationsTemplate(
			notificationsTemplateId);
	}

	/**
	 * Returns the notifications template matching the UUID and group.
	 *
	 * @param uuid the notifications template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
		fetchNotificationsTemplateByUuidAndGroupId(String uuid, long groupId) {

		return _notificationsTemplateLocalService.
			fetchNotificationsTemplateByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationsTemplateLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _notificationsTemplateLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationsTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notifications template with the primary key.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template
	 * @throws PortalException if a notifications template with the primary key could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			getNotificationsTemplate(long notificationsTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.getNotificationsTemplate(
			notificationsTemplateId);
	}

	/**
	 * Returns the notifications template matching the UUID and group.
	 *
	 * @param uuid the notifications template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching notifications template
	 * @throws PortalException if a matching notifications template could not be found
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			getNotificationsTemplateByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.
			getNotificationsTemplateByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of notifications templates
	 */
	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsTemplate>
			getNotificationsTemplates(int start, int end) {

		return _notificationsTemplateLocalService.getNotificationsTemplates(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsTemplate>
			getNotificationsTemplates(
				long groupId, boolean enabled, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.notifications.admin.model.
						NotificationsTemplate> orderByComparator) {

		return _notificationsTemplateLocalService.getNotificationsTemplates(
			groupId, enabled, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsTemplate>
			getNotificationsTemplates(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.notifications.admin.model.
						NotificationsTemplate> orderByComparator) {

		return _notificationsTemplateLocalService.getNotificationsTemplates(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns all the notifications templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the notifications templates
	 * @param companyId the primary key of the company
	 * @return the matching notifications templates, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsTemplate>
			getNotificationsTemplatesByUuidAndCompanyId(
				String uuid, long companyId) {

		return _notificationsTemplateLocalService.
			getNotificationsTemplatesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of notifications templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the notifications templates
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching notifications templates, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.notifications.admin.model.NotificationsTemplate>
			getNotificationsTemplatesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.notifications.admin.model.
						NotificationsTemplate> orderByComparator) {

		return _notificationsTemplateLocalService.
			getNotificationsTemplatesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of notifications templates.
	 *
	 * @return the number of notifications templates
	 */
	@Override
	public int getNotificationsTemplatesCount() {
		return _notificationsTemplateLocalService.
			getNotificationsTemplatesCount();
	}

	@Override
	public int getNotificationsTemplatesCount(long groupId) {
		return _notificationsTemplateLocalService.
			getNotificationsTemplatesCount(groupId);
	}

	@Override
	public int getNotificationsTemplatesCount(long groupId, boolean enabled) {
		return _notificationsTemplateLocalService.
			getNotificationsTemplatesCount(groupId, enabled);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationsTemplateLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
			updateNotificationsTemplate(
				long notificationsTemplateId, String name, String description,
				String from,
				java.util.Map<java.util.Locale, String> fromNameMap, String to,
				String cc, String bcc, boolean enabled,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationsTemplateLocalService.updateNotificationsTemplate(
			notificationsTemplateId, name, description, from, fromNameMap, to,
			cc, bcc, enabled, subjectMap, bodyMap, serviceContext);
	}

	/**
	 * Updates the notifications template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationsTemplateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationsTemplate the notifications template
	 * @return the notifications template that was updated
	 */
	@Override
	public com.liferay.notifications.admin.model.NotificationsTemplate
		updateNotificationsTemplate(
			com.liferay.notifications.admin.model.NotificationsTemplate
				notificationsTemplate) {

		return _notificationsTemplateLocalService.updateNotificationsTemplate(
			notificationsTemplate);
	}

	@Override
	public NotificationsTemplateLocalService getWrappedService() {
		return _notificationsTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationsTemplateLocalService notificationsTemplateLocalService) {

		_notificationsTemplateLocalService = notificationsTemplateLocalService;
	}

	private NotificationsTemplateLocalService
		_notificationsTemplateLocalService;

}