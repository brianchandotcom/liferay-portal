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

package com.liferay.notification.admin.service;

import com.liferay.notification.admin.model.NotificationTemplate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for NotificationTemplate. This utility wraps
 * <code>com.liferay.notification.admin.service.impl.NotificationTemplateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateLocalService
 * @generated
 */
public class NotificationTemplateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notification.admin.service.impl.NotificationTemplateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static NotificationTemplate addNotificationTemplate(
			long userId, long groupId, String name, String description,
			String from, Map<java.util.Locale, String> fromNameMap, String to,
			String cc, String bcc, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addNotificationTemplate(
			userId, groupId, name, description, from, fromNameMap, to, cc, bcc,
			enabled, subjectMap, bodyMap, serviceContext);
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
	public static NotificationTemplate addNotificationTemplate(
		NotificationTemplate notificationTemplate) {

		return getService().addNotificationTemplate(notificationTemplate);
	}

	/**
	 * Creates a new notification template with the primary key. Does not add the notification template to the database.
	 *
	 * @param notificationTemplateId the primary key for the new notification template
	 * @return the new notification template
	 */
	public static NotificationTemplate createNotificationTemplate(
		long notificationTemplateId) {

		return getService().createNotificationTemplate(notificationTemplateId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		return getService().deleteNotificationTemplate(notificationTemplateId);
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
	public static NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		return getService().deleteNotificationTemplate(notificationTemplate);
	}

	public static void deleteNotificationTemplates(long groupId)
		throws PortalException {

		getService().deleteNotificationTemplates(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static NotificationTemplate fetchNotificationTemplate(
		long notificationTemplateId) {

		return getService().fetchNotificationTemplate(notificationTemplateId);
	}

	/**
	 * Returns the notification template matching the UUID and group.
	 *
	 * @param uuid the notification template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate
		fetchNotificationTemplateByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchNotificationTemplateByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification template with the primary key.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template
	 * @throws PortalException if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate getNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		return getService().getNotificationTemplate(notificationTemplateId);
	}

	/**
	 * Returns the notification template matching the UUID and group.
	 *
	 * @param uuid the notification template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching notification template
	 * @throws PortalException if a matching notification template could not be found
	 */
	public static NotificationTemplate getNotificationTemplateByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getNotificationTemplateByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.admin.model.impl.NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of notification templates
	 */
	public static List<NotificationTemplate> getNotificationTemplates(
		int start, int end) {

		return getService().getNotificationTemplates(start, end);
	}

	public static List<NotificationTemplate> getNotificationTemplates(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getService().getNotificationTemplates(
			groupId, enabled, start, end, orderByComparator);
	}

	public static List<NotificationTemplate> getNotificationTemplates(
		long groupId, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getService().getNotificationTemplates(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns all the notification templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the notification templates
	 * @param companyId the primary key of the company
	 * @return the matching notification templates, or an empty list if no matches were found
	 */
	public static List<NotificationTemplate>
		getNotificationTemplatesByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getNotificationTemplatesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of notification templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the notification templates
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching notification templates, or an empty list if no matches were found
	 */
	public static List<NotificationTemplate>
		getNotificationTemplatesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<NotificationTemplate> orderByComparator) {

		return getService().getNotificationTemplatesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of notification templates.
	 *
	 * @return the number of notification templates
	 */
	public static int getNotificationTemplatesCount() {
		return getService().getNotificationTemplatesCount();
	}

	public static int getNotificationTemplatesCount(long groupId) {
		return getService().getNotificationTemplatesCount(groupId);
	}

	public static int getNotificationTemplatesCount(
		long groupId, boolean enabled) {

		return getService().getNotificationTemplatesCount(groupId, enabled);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, String name, String description,
			String from, Map<java.util.Locale, String> fromNameMap, String to,
			String cc, String bcc, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateNotificationTemplate(
			notificationTemplateId, name, description, from, fromNameMap, to,
			cc, bcc, enabled, subjectMap, bodyMap, serviceContext);
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
	public static NotificationTemplate updateNotificationTemplate(
		NotificationTemplate notificationTemplate) {

		return getService().updateNotificationTemplate(notificationTemplate);
	}

	public static NotificationTemplateLocalService getService() {
		return _service;
	}

	private static volatile NotificationTemplateLocalService _service;

}