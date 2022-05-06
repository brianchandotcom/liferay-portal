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

import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for NotificationsQueueEntry. This utility wraps
 * <code>com.liferay.notifications.admin.service.impl.NotificationsQueueEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Gabriel Albuquerque
 * @see NotificationsQueueEntryLocalService
 * @generated
 */
public class NotificationsQueueEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notifications.admin.service.impl.NotificationsQueueEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static NotificationsQueueEntry addNotificationsQueueEntry(
			long userId, long groupId, String className, long classPK,
			long notificationsTemplateId, String from, String fromName,
			String to, String toName, String cc, String bcc, String subject,
			String body, double priority)
		throws PortalException {

		return getService().addNotificationsQueueEntry(
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
	public static NotificationsQueueEntry addNotificationsQueueEntry(
		NotificationsQueueEntry notificationsQueueEntry) {

		return getService().addNotificationsQueueEntry(notificationsQueueEntry);
	}

	/**
	 * Creates a new notifications queue entry with the primary key. Does not add the notifications queue entry to the database.
	 *
	 * @param notificationsQueueEntryId the primary key for the new notifications queue entry
	 * @return the new notifications queue entry
	 */
	public static NotificationsQueueEntry createNotificationsQueueEntry(
		long notificationsQueueEntryId) {

		return getService().createNotificationsQueueEntry(
			notificationsQueueEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteNotificationsQueueEntries(
		java.util.Date sentDate) {

		getService().deleteNotificationsQueueEntries(sentDate);
	}

	public static void deleteNotificationsQueueEntries(long groupId)
		throws PortalException {

		getService().deleteNotificationsQueueEntries(groupId);
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
	public static NotificationsQueueEntry deleteNotificationsQueueEntry(
			long notificationsQueueEntryId)
		throws PortalException {

		return getService().deleteNotificationsQueueEntry(
			notificationsQueueEntryId);
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
	public static NotificationsQueueEntry deleteNotificationsQueueEntry(
		NotificationsQueueEntry notificationsQueueEntry) {

		return getService().deleteNotificationsQueueEntry(
			notificationsQueueEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl</code>.
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

	public static NotificationsQueueEntry fetchNotificationsQueueEntry(
		long notificationsQueueEntryId) {

		return getService().fetchNotificationsQueueEntry(
			notificationsQueueEntryId);
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

	public static List<NotificationsQueueEntry> getNotificationsQueueEntries(
		boolean sent) {

		return getService().getNotificationsQueueEntries(sent);
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
	public static List<NotificationsQueueEntry> getNotificationsQueueEntries(
		int start, int end) {

		return getService().getNotificationsQueueEntries(start, end);
	}

	public static List<NotificationsQueueEntry> getNotificationsQueueEntries(
		long groupId, String className, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getService().getNotificationsQueueEntries(
			groupId, className, classPK, sent, start, end, orderByComparator);
	}

	/**
	 * Returns the number of notifications queue entries.
	 *
	 * @return the number of notifications queue entries
	 */
	public static int getNotificationsQueueEntriesCount() {
		return getService().getNotificationsQueueEntriesCount();
	}

	public static int getNotificationsQueueEntriesCount(long groupId) {
		return getService().getNotificationsQueueEntriesCount(groupId);
	}

	public static int getNotificationsQueueEntriesCount(
		long groupId, String className, long classPK, boolean sent) {

		return getService().getNotificationsQueueEntriesCount(
			groupId, className, classPK, sent);
	}

	/**
	 * Returns the notifications queue entry with the primary key.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws PortalException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry getNotificationsQueueEntry(
			long notificationsQueueEntryId)
		throws PortalException {

		return getService().getNotificationsQueueEntry(
			notificationsQueueEntryId);
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

	public static NotificationsQueueEntry resendNotificationsQueueEntry(
			long notificationsQueueEntryId)
		throws PortalException {

		return getService().resendNotificationsQueueEntry(
			notificationsQueueEntryId);
	}

	public static void sendNotificationsQueueEntries() throws Exception {
		getService().sendNotificationsQueueEntries();
	}

	public static void updateNotificationsQueueEntriesTemplateIds(
		long notificationsTemplateId) {

		getService().updateNotificationsQueueEntriesTemplateIds(
			notificationsTemplateId);
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
	public static NotificationsQueueEntry updateNotificationsQueueEntry(
		NotificationsQueueEntry notificationsQueueEntry) {

		return getService().updateNotificationsQueueEntry(
			notificationsQueueEntry);
	}

	public static NotificationsQueueEntry updateSent(
			long notificationsQueueEntryId, boolean sent)
		throws PortalException {

		return getService().updateSent(notificationsQueueEntryId, sent);
	}

	public static NotificationsQueueEntryLocalService getService() {
		return _service;
	}

	private static volatile NotificationsQueueEntryLocalService _service;

}