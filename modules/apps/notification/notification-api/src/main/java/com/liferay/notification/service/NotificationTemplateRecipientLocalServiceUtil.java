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

import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for NotificationTemplateRecipient. This utility wraps
 * <code>com.liferay.notification.service.impl.NotificationTemplateRecipientLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientLocalService
 * @generated
 */
public class NotificationTemplateRecipientLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notification.service.impl.NotificationTemplateRecipientLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static NotificationTemplateRecipient
		addNotificationTemplateRecipient(
			NotificationTemplateRecipient notificationTemplateRecipient) {

		return getService().addNotificationTemplateRecipient(
			notificationTemplateRecipient);
	}

	/**
	 * Creates a new notification template recipient with the primary key. Does not add the notification template recipient to the database.
	 *
	 * @param notificationTemplateRecipientId the primary key for the new notification template recipient
	 * @return the new notification template recipient
	 */
	public static NotificationTemplateRecipient
		createNotificationTemplateRecipient(
			long notificationTemplateRecipientId) {

		return getService().createNotificationTemplateRecipient(
			notificationTemplateRecipientId);
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
	public static NotificationTemplateRecipient
			deleteNotificationTemplateRecipient(
				long notificationTemplateRecipientId)
		throws PortalException {

		return getService().deleteNotificationTemplateRecipient(
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
	public static NotificationTemplateRecipient
		deleteNotificationTemplateRecipient(
			NotificationTemplateRecipient notificationTemplateRecipient) {

		return getService().deleteNotificationTemplateRecipient(
			notificationTemplateRecipient);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationTemplateRecipientModelImpl</code>.
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

	public static NotificationTemplateRecipient
		fetchNotificationTemplateRecipient(
			long notificationTemplateRecipientId) {

		return getService().fetchNotificationTemplateRecipient(
			notificationTemplateRecipientId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static NotificationTemplateRecipient
		getNotificationTemplateNotificationTemplateRecipient(
			long notificationTemplateId) {

		return getService().
			getNotificationTemplateNotificationTemplateRecipient(
				notificationTemplateId);
	}

	/**
	 * Returns the notification template recipient with the primary key.
	 *
	 * @param notificationTemplateRecipientId the primary key of the notification template recipient
	 * @return the notification template recipient
	 * @throws PortalException if a notification template recipient with the primary key could not be found
	 */
	public static NotificationTemplateRecipient
			getNotificationTemplateRecipient(
				long notificationTemplateRecipientId)
		throws PortalException {

		return getService().getNotificationTemplateRecipient(
			notificationTemplateRecipientId);
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
	public static List<NotificationTemplateRecipient>
		getNotificationTemplateRecipients(int start, int end) {

		return getService().getNotificationTemplateRecipients(start, end);
	}

	/**
	 * Returns the number of notification template recipients.
	 *
	 * @return the number of notification template recipients
	 */
	public static int getNotificationTemplateRecipientsCount() {
		return getService().getNotificationTemplateRecipientsCount();
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
	public static NotificationTemplateRecipient
		updateNotificationTemplateRecipient(
			NotificationTemplateRecipient notificationTemplateRecipient) {

		return getService().updateNotificationTemplateRecipient(
			notificationTemplateRecipient);
	}

	public static NotificationTemplateRecipientLocalService getService() {
		return _service;
	}

	private static volatile NotificationTemplateRecipientLocalService _service;

}