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

import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for NotificationQueueEntryRecipientSetting. This utility wraps
 * <code>com.liferay.notification.service.impl.NotificationQueueEntryRecipientSettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientSettingLocalService
 * @generated
 */
public class NotificationQueueEntryRecipientSettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notification.service.impl.NotificationQueueEntryRecipientSettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the notification queue entry recipient setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was added
	 */
	public static NotificationQueueEntryRecipientSetting
		addNotificationQueueEntryRecipientSetting(
			NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting) {

		return getService().addNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSetting);
	}

	/**
	 * Creates a new notification queue entry recipient setting with the primary key. Does not add the notification queue entry recipient setting to the database.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key for the new notification queue entry recipient setting
	 * @return the new notification queue entry recipient setting
	 */
	public static NotificationQueueEntryRecipientSetting
		createNotificationQueueEntryRecipientSetting(
			long notificationQueueEntryRecipientSettingId) {

		return getService().createNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSettingId);
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
	 * Deletes the notification queue entry recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 * @throws PortalException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipientSetting
			deleteNotificationQueueEntryRecipientSetting(
				long notificationQueueEntryRecipientSettingId)
		throws PortalException {

		return getService().deleteNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Deletes the notification queue entry recipient setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was removed
	 */
	public static NotificationQueueEntryRecipientSetting
		deleteNotificationQueueEntryRecipientSetting(
			NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting) {

		return getService().deleteNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSetting);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
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

	public static NotificationQueueEntryRecipientSetting
		fetchNotificationQueueEntryRecipientSetting(
			long notificationQueueEntryRecipientSettingId) {

		return getService().fetchNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSettingId);
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

	/**
	 * Returns the notification queue entry recipient setting with the primary key.
	 *
	 * @param notificationQueueEntryRecipientSettingId the primary key of the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting
	 * @throws PortalException if a notification queue entry recipient setting with the primary key could not be found
	 */
	public static NotificationQueueEntryRecipientSetting
			getNotificationQueueEntryRecipientSetting(
				long notificationQueueEntryRecipientSettingId)
		throws PortalException {

		return getService().getNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSettingId);
	}

	/**
	 * Returns a range of all the notification queue entry recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry recipient settings
	 * @param end the upper bound of the range of notification queue entry recipient settings (not inclusive)
	 * @return the range of notification queue entry recipient settings
	 */
	public static List<NotificationQueueEntryRecipientSetting>
		getNotificationQueueEntryRecipientSettings(int start, int end) {

		return getService().getNotificationQueueEntryRecipientSettings(
			start, end);
	}

	/**
	 * Returns the number of notification queue entry recipient settings.
	 *
	 * @return the number of notification queue entry recipient settings
	 */
	public static int getNotificationQueueEntryRecipientSettingsCount() {
		return getService().getNotificationQueueEntryRecipientSettingsCount();
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
	 * Updates the notification queue entry recipient setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryRecipientSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryRecipientSetting the notification queue entry recipient setting
	 * @return the notification queue entry recipient setting that was updated
	 */
	public static NotificationQueueEntryRecipientSetting
		updateNotificationQueueEntryRecipientSetting(
			NotificationQueueEntryRecipientSetting
				notificationQueueEntryRecipientSetting) {

		return getService().updateNotificationQueueEntryRecipientSetting(
			notificationQueueEntryRecipientSetting);
	}

	public static NotificationQueueEntryRecipientSettingLocalService
		getService() {

		return _service;
	}

	private static volatile NotificationQueueEntryRecipientSettingLocalService
		_service;

}