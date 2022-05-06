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

package com.liferay.notifications.admin.service.persistence;

import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notifications queue entry service. This utility wraps <code>com.liferay.notifications.admin.service.persistence.impl.NotificationsQueueEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationsQueueEntryPersistence
 * @generated
 */
public class NotificationsQueueEntryUtil {

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
		NotificationsQueueEntry notificationsQueueEntry) {

		getPersistence().clearCache(notificationsQueueEntry);
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
	public static Map<Serializable, NotificationsQueueEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationsQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationsQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationsQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationsQueueEntry update(
		NotificationsQueueEntry notificationsQueueEntry) {

		return getPersistence().update(notificationsQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationsQueueEntry update(
		NotificationsQueueEntry notificationsQueueEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(notificationsQueueEntry, serviceContext);
	}

	/**
	 * Returns all the notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByGroupId_First(
			long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry[] findByGroupId_PrevAndNext(
			long notificationsQueueEntryId, long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			notificationsQueueEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the notifications queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notifications queue entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId) {

		return getPersistence().findByNotificationsTemplateId(
			notificationsTemplateId);
	}

	/**
	 * Returns a range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end) {

		return getPersistence().findByNotificationsTemplateId(
			notificationsTemplateId, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findByNotificationsTemplateId(
			notificationsTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByNotificationsTemplateId(
			notificationsTemplateId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByNotificationsTemplateId_First(
			long notificationsTemplateId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByNotificationsTemplateId_First(
			notificationsTemplateId, orderByComparator);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByNotificationsTemplateId_First(
		long notificationsTemplateId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByNotificationsTemplateId_First(
			notificationsTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByNotificationsTemplateId_Last(
			long notificationsTemplateId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByNotificationsTemplateId_Last(
			notificationsTemplateId, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByNotificationsTemplateId_Last(
		long notificationsTemplateId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByNotificationsTemplateId_Last(
			notificationsTemplateId, orderByComparator);
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry[]
			findByNotificationsTemplateId_PrevAndNext(
				long notificationsQueueEntryId, long notificationsTemplateId,
				OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByNotificationsTemplateId_PrevAndNext(
			notificationsQueueEntryId, notificationsTemplateId,
			orderByComparator);
	}

	/**
	 * Removes all the notifications queue entries where notificationsTemplateId = &#63; from the database.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 */
	public static void removeByNotificationsTemplateId(
		long notificationsTemplateId) {

		getPersistence().removeByNotificationsTemplateId(
			notificationsTemplateId);
	}

	/**
	 * Returns the number of notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the number of matching notifications queue entries
	 */
	public static int countByNotificationsTemplateId(
		long notificationsTemplateId) {

		return getPersistence().countByNotificationsTemplateId(
			notificationsTemplateId);
	}

	/**
	 * Returns all the notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findBySent(boolean sent) {
		return getPersistence().findBySent(sent);
	}

	/**
	 * Returns a range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end) {

		return getPersistence().findBySent(sent, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findBySent(sent, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySent(
			sent, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findBySent_First(
			boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findBySent_First(sent, orderByComparator);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchBySent_First(
		boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchBySent_First(sent, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findBySent_Last(
			boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findBySent_Last(sent, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchBySent_Last(
		boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchBySent_Last(sent, orderByComparator);
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry[] findBySent_PrevAndNext(
			long notificationsQueueEntryId, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findBySent_PrevAndNext(
			notificationsQueueEntryId, sent, orderByComparator);
	}

	/**
	 * Removes all the notifications queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	public static void removeBySent(boolean sent) {
		getPersistence().removeBySent(sent);
	}

	/**
	 * Returns the number of notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	public static int countBySent(boolean sent) {
		return getPersistence().countBySent(sent);
	}

	/**
	 * Returns all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate) {

		return getPersistence().findByLtSentDate(sentDate);
	}

	/**
	 * Returns a range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end) {

		return getPersistence().findByLtSentDate(sentDate, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByLtSentDate_First(
			Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByLtSentDate_Last(
			Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry[] findByLtSentDate_PrevAndNext(
			long notificationsQueueEntryId, Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByLtSentDate_PrevAndNext(
			notificationsQueueEntryId, sentDate, orderByComparator);
	}

	/**
	 * Removes all the notifications queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public static void removeByLtSentDate(Date sentDate) {
		getPersistence().removeByLtSentDate(sentDate);
	}

	/**
	 * Returns the number of notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notifications queue entries
	 */
	public static int countByLtSentDate(Date sentDate) {
		return getPersistence().countByLtSentDate(sentDate);
	}

	/**
	 * Returns all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent);
	}

	/**
	 * Returns a range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByG_C_C_S_First(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByG_C_C_S_First(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByG_C_C_S_Last(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public static NotificationsQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().fetchByG_C_C_S_Last(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry[] findByG_C_C_S_PrevAndNext(
			long notificationsQueueEntryId, long groupId, long classNameId,
			long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByG_C_C_S_PrevAndNext(
			notificationsQueueEntryId, groupId, classNameId, classPK, sent,
			orderByComparator);
	}

	/**
	 * Removes all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	public static void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		getPersistence().removeByG_C_C_S(groupId, classNameId, classPK, sent);
	}

	/**
	 * Returns the number of notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	public static int countByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return getPersistence().countByG_C_C_S(
			groupId, classNameId, classPK, sent);
	}

	/**
	 * Caches the notifications queue entry in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 */
	public static void cacheResult(
		NotificationsQueueEntry notificationsQueueEntry) {

		getPersistence().cacheResult(notificationsQueueEntry);
	}

	/**
	 * Caches the notifications queue entries in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntries the notifications queue entries
	 */
	public static void cacheResult(
		List<NotificationsQueueEntry> notificationsQueueEntries) {

		getPersistence().cacheResult(notificationsQueueEntries);
	}

	/**
	 * Creates a new notifications queue entry with the primary key. Does not add the notifications queue entry to the database.
	 *
	 * @param notificationsQueueEntryId the primary key for the new notifications queue entry
	 * @return the new notifications queue entry
	 */
	public static NotificationsQueueEntry create(
		long notificationsQueueEntryId) {

		return getPersistence().create(notificationsQueueEntryId);
	}

	/**
	 * Removes the notifications queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry that was removed
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry remove(long notificationsQueueEntryId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().remove(notificationsQueueEntryId);
	}

	public static NotificationsQueueEntry updateImpl(
		NotificationsQueueEntry notificationsQueueEntry) {

		return getPersistence().updateImpl(notificationsQueueEntry);
	}

	/**
	 * Returns the notifications queue entry with the primary key or throws a <code>NoSuchNotificationsQueueEntryException</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry findByPrimaryKey(
			long notificationsQueueEntryId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsQueueEntryException {

		return getPersistence().findByPrimaryKey(notificationsQueueEntryId);
	}

	/**
	 * Returns the notifications queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry, or <code>null</code> if a notifications queue entry with the primary key could not be found
	 */
	public static NotificationsQueueEntry fetchByPrimaryKey(
		long notificationsQueueEntryId) {

		return getPersistence().fetchByPrimaryKey(notificationsQueueEntryId);
	}

	/**
	 * Returns all the notifications queue entries.
	 *
	 * @return the notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications queue entries
	 */
	public static List<NotificationsQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notifications queue entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notifications queue entries.
	 *
	 * @return the number of notifications queue entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationsQueueEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationsQueueEntryPersistence _persistence;

}