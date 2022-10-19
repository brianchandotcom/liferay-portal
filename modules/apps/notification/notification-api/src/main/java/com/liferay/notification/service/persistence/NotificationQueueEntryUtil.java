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

package com.liferay.notification.service.persistence;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification queue entry service. This utility wraps <code>com.liferay.notification.service.persistence.impl.NotificationQueueEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryPersistence
 * @generated
 */
public class NotificationQueueEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		NotificationQueueEntry notificationQueueEntry) {

		getPersistence().clearCache(notificationQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, NotificationQueueEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationQueueEntry update(
		NotificationQueueEntry notificationQueueEntry) {

		return getPersistence().update(notificationQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationQueueEntry update(
		NotificationQueueEntry notificationQueueEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(notificationQueueEntry, serviceContext);
	}

	/**
	 * Returns all the notification queue entries where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns a range of all the notification queue entries where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByNotificationTemplateId_First(
			long notificationTemplateId,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByNotificationTemplateId_First(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByNotificationTemplateId_First(
		long notificationTemplateId,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByNotificationTemplateId_First(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByNotificationTemplateId_Last(
			long notificationTemplateId,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByNotificationTemplateId_Last(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByNotificationTemplateId_Last(
		long notificationTemplateId,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByNotificationTemplateId_Last(
			notificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[]
			findByNotificationTemplateId_PrevAndNext(
				long notificationQueueEntryId, long notificationTemplateId,
				OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByNotificationTemplateId_PrevAndNext(
			notificationQueueEntryId, notificationTemplateId,
			orderByComparator);
	}

	/**
	 * Returns all the notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(long notificationTemplateId) {

		return getPersistence().filterFindByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns a range of all the notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(
			long notificationTemplateId, int start, int end) {

		return getPersistence().filterFindByNotificationTemplateId(
			notificationTemplateId, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries that the user has permissions to view where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().filterFindByNotificationTemplateId(
			notificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set of notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[]
			filterFindByNotificationTemplateId_PrevAndNext(
				long notificationQueueEntryId, long notificationTemplateId,
				OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().filterFindByNotificationTemplateId_PrevAndNext(
			notificationQueueEntryId, notificationTemplateId,
			orderByComparator);
	}

	/**
	 * Removes all the notification queue entries where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 */
	public static void removeByNotificationTemplateId(
		long notificationTemplateId) {

		getPersistence().removeByNotificationTemplateId(notificationTemplateId);
	}

	/**
	 * Returns the number of notification queue entries where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification queue entries
	 */
	public static int countByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().countByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns the number of notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public static int filterCountByNotificationTemplateId(
		long notificationTemplateId) {

		return getPersistence().filterCountByNotificationTemplateId(
			notificationTemplateId);
	}

	/**
	 * Returns all the notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByLtSentDate(Date sentDate) {
		return getPersistence().findByLtSentDate(sentDate);
	}

	/**
	 * Returns a range of all the notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end) {

		return getPersistence().findByLtSentDate(sentDate, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByLtSentDate_First(
			Date sentDate,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByLtSentDate_Last(
			Date sentDate,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[] findByLtSentDate_PrevAndNext(
			long notificationQueueEntryId, Date sentDate,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_PrevAndNext(
			notificationQueueEntryId, sentDate, orderByComparator);
	}

	/**
	 * Returns all the notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate) {

		return getPersistence().filterFindByLtSentDate(sentDate);
	}

	/**
	 * Returns a range of all the notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate, int start, int end) {

		return getPersistence().filterFindByLtSentDate(sentDate, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries that the user has permissions to view where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().filterFindByLtSentDate(
			sentDate, start, end, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set of notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[] filterFindByLtSentDate_PrevAndNext(
			long notificationQueueEntryId, Date sentDate,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().filterFindByLtSentDate_PrevAndNext(
			notificationQueueEntryId, sentDate, orderByComparator);
	}

	/**
	 * Removes all the notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public static void removeByLtSentDate(Date sentDate) {
		getPersistence().removeByLtSentDate(sentDate);
	}

	/**
	 * Returns the number of notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notification queue entries
	 */
	public static int countByLtSentDate(Date sentDate) {
		return getPersistence().countByLtSentDate(sentDate);
	}

	/**
	 * Returns the number of notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public static int filterCountByLtSentDate(Date sentDate) {
		return getPersistence().filterCountByLtSentDate(sentDate);
	}

	/**
	 * Returns all the notification queue entries where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByT_S(
		String type, int status) {

		return getPersistence().findByT_S(type, status);
	}

	/**
	 * Returns a range of all the notification queue entries where type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end) {

		return getPersistence().findByT_S(type, status, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().findByT_S(
			type, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entries where type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entries
	 */
	public static List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByT_S(
			type, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByT_S_First(
			String type, int status,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByT_S_First(
			type, status, orderByComparator);
	}

	/**
	 * Returns the first notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByT_S_First(
		String type, int status,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByT_S_First(
			type, status, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry findByT_S_Last(
			String type, int status,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByT_S_Last(type, status, orderByComparator);
	}

	/**
	 * Returns the last notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public static NotificationQueueEntry fetchByT_S_Last(
		String type, int status,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByT_S_Last(
			type, status, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[] findByT_S_PrevAndNext(
			long notificationQueueEntryId, String type, int status,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByT_S_PrevAndNext(
			notificationQueueEntryId, type, status, orderByComparator);
	}

	/**
	 * Returns all the notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByT_S(
		String type, int status) {

		return getPersistence().filterFindByT_S(type, status);
	}

	/**
	 * Returns a range of all the notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByT_S(
		String type, int status, int start, int end) {

		return getPersistence().filterFindByT_S(type, status, start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries that the user has permissions to view where type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entries that the user has permission to view
	 */
	public static List<NotificationQueueEntry> filterFindByT_S(
		String type, int status, int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().filterFindByT_S(
			type, status, start, end, orderByComparator);
	}

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set of notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry[] filterFindByT_S_PrevAndNext(
			long notificationQueueEntryId, String type, int status,
			OrderByComparator<NotificationQueueEntry> orderByComparator)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().filterFindByT_S_PrevAndNext(
			notificationQueueEntryId, type, status, orderByComparator);
	}

	/**
	 * Removes all the notification queue entries where type = &#63; and status = &#63; from the database.
	 *
	 * @param type the type
	 * @param status the status
	 */
	public static void removeByT_S(String type, int status) {
		getPersistence().removeByT_S(type, status);
	}

	/**
	 * Returns the number of notification queue entries where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the number of matching notification queue entries
	 */
	public static int countByT_S(String type, int status) {
		return getPersistence().countByT_S(type, status);
	}

	/**
	 * Returns the number of notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public static int filterCountByT_S(String type, int status) {
		return getPersistence().filterCountByT_S(type, status);
	}

	/**
	 * Caches the notification queue entry in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntry the notification queue entry
	 */
	public static void cacheResult(
		NotificationQueueEntry notificationQueueEntry) {

		getPersistence().cacheResult(notificationQueueEntry);
	}

	/**
	 * Caches the notification queue entries in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntries the notification queue entries
	 */
	public static void cacheResult(
		List<NotificationQueueEntry> notificationQueueEntries) {

		getPersistence().cacheResult(notificationQueueEntries);
	}

	/**
	 * Creates a new notification queue entry with the primary key. Does not add the notification queue entry to the database.
	 *
	 * @param notificationQueueEntryId the primary key for the new notification queue entry
	 * @return the new notification queue entry
	 */
	public static NotificationQueueEntry create(long notificationQueueEntryId) {
		return getPersistence().create(notificationQueueEntryId);
	}

	/**
	 * Removes the notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry remove(long notificationQueueEntryId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().remove(notificationQueueEntryId);
	}

	public static NotificationQueueEntry updateImpl(
		NotificationQueueEntry notificationQueueEntry) {

		return getPersistence().updateImpl(notificationQueueEntry);
	}

	/**
	 * Returns the notification queue entry with the primary key or throws a <code>NoSuchNotificationQueueEntryException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry findByPrimaryKey(
			long notificationQueueEntryId)
		throws com.liferay.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByPrimaryKey(notificationQueueEntryId);
	}

	/**
	 * Returns the notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry, or <code>null</code> if a notification queue entry with the primary key could not be found
	 */
	public static NotificationQueueEntry fetchByPrimaryKey(
		long notificationQueueEntryId) {

		return getPersistence().fetchByPrimaryKey(notificationQueueEntryId);
	}

	/**
	 * Returns all the notification queue entries.
	 *
	 * @return the notification queue entries
	 */
	public static List<NotificationQueueEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @return the range of notification queue entries
	 */
	public static List<NotificationQueueEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entries
	 */
	public static List<NotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entries
	 * @param end the upper bound of the range of notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entries
	 */
	public static List<NotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification queue entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification queue entries.
	 *
	 * @return the number of notification queue entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationQueueEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationQueueEntryPersistence _persistence;

}