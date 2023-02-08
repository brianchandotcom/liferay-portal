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

import com.liferay.notification.exception.NoSuchNotificationQueueEntryException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryUtil
 * @generated
 */
@ProviderType
public interface NotificationQueueEntryPersistence
	extends BasePersistence<NotificationQueueEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationQueueEntryUtil} to access the notification queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification queue entries where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification queue entries
	 */
	public java.util.List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId);

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
	public java.util.List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end);

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
	public java.util.List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public java.util.List<NotificationQueueEntry> findByNotificationTemplateId(
		long notificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByNotificationTemplateId_First(
			long notificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByNotificationTemplateId_First(
		long notificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the last notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByNotificationTemplateId_Last(
			long notificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByNotificationTemplateId_Last(
		long notificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry[] findByNotificationTemplateId_PrevAndNext(
			long notificationQueueEntryId, long notificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns all the notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public java.util.List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(long notificationTemplateId);

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
	public java.util.List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(
			long notificationTemplateId, int start, int end);

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
	public java.util.List<NotificationQueueEntry>
		filterFindByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator);

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set of notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry[]
			filterFindByNotificationTemplateId_PrevAndNext(
				long notificationQueueEntryId, long notificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the notification queue entries where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 */
	public void removeByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns the number of notification queue entries where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification queue entries
	 */
	public int countByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns the number of notification queue entries that the user has permission to view where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public int filterCountByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns all the notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notification queue entries
	 */
	public java.util.List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate);

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
	public java.util.List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end);

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
	public java.util.List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public java.util.List<NotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByLtSentDate_First(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the last notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByLtSentDate_Last(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry[] findByLtSentDate_PrevAndNext(
			long notificationQueueEntryId, Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns all the notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public java.util.List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate);

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
	public java.util.List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate, int start, int end);

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
	public java.util.List<NotificationQueueEntry> filterFindByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the notification queue entries before and after the current notification queue entry in the ordered set of notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param notificationQueueEntryId the primary key of the current notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry[] filterFindByLtSentDate_PrevAndNext(
			long notificationQueueEntryId, Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public void removeByLtSentDate(Date sentDate);

	/**
	 * Returns the number of notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notification queue entries
	 */
	public int countByLtSentDate(Date sentDate);

	/**
	 * Returns the number of notification queue entries that the user has permission to view where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public int filterCountByLtSentDate(Date sentDate);

	/**
	 * Returns all the notification queue entries where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the matching notification queue entries
	 */
	public java.util.List<NotificationQueueEntry> findByT_S(
		String type, int status);

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
	public java.util.List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end);

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
	public java.util.List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public java.util.List<NotificationQueueEntry> findByT_S(
		String type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByT_S_First(
			String type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByT_S_First(
		String type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

	/**
	 * Returns the last notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry findByT_S_Last(
			String type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last notification queue entry in the ordered set where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry, or <code>null</code> if a matching notification queue entry could not be found
	 */
	public NotificationQueueEntry fetchByT_S_Last(
		String type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public NotificationQueueEntry[] findByT_S_PrevAndNext(
			long notificationQueueEntryId, String type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns all the notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the matching notification queue entries that the user has permission to view
	 */
	public java.util.List<NotificationQueueEntry> filterFindByT_S(
		String type, int status);

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
	public java.util.List<NotificationQueueEntry> filterFindByT_S(
		String type, int status, int start, int end);

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
	public java.util.List<NotificationQueueEntry> filterFindByT_S(
		String type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public NotificationQueueEntry[] filterFindByT_S_PrevAndNext(
			long notificationQueueEntryId, String type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the notification queue entries where type = &#63; and status = &#63; from the database.
	 *
	 * @param type the type
	 * @param status the status
	 */
	public void removeByT_S(String type, int status);

	/**
	 * Returns the number of notification queue entries where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the number of matching notification queue entries
	 */
	public int countByT_S(String type, int status);

	/**
	 * Returns the number of notification queue entries that the user has permission to view where type = &#63; and status = &#63;.
	 *
	 * @param type the type
	 * @param status the status
	 * @return the number of matching notification queue entries that the user has permission to view
	 */
	public int filterCountByT_S(String type, int status);

	/**
	 * Caches the notification queue entry in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntry the notification queue entry
	 */
	public void cacheResult(NotificationQueueEntry notificationQueueEntry);

	/**
	 * Caches the notification queue entries in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntries the notification queue entries
	 */
	public void cacheResult(
		java.util.List<NotificationQueueEntry> notificationQueueEntries);

	/**
	 * Creates a new notification queue entry with the primary key. Does not add the notification queue entry to the database.
	 *
	 * @param notificationQueueEntryId the primary key for the new notification queue entry
	 * @return the new notification queue entry
	 */
	public NotificationQueueEntry create(long notificationQueueEntryId);

	/**
	 * Removes the notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry remove(long notificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException;

	public NotificationQueueEntry updateImpl(
		NotificationQueueEntry notificationQueueEntry);

	/**
	 * Returns the notification queue entry with the primary key or throws a <code>NoSuchNotificationQueueEntryException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry findByPrimaryKey(
			long notificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryId the primary key of the notification queue entry
	 * @return the notification queue entry, or <code>null</code> if a notification queue entry with the primary key could not be found
	 */
	public NotificationQueueEntry fetchByPrimaryKey(
		long notificationQueueEntryId);

	/**
	 * Returns all the notification queue entries.
	 *
	 * @return the notification queue entries
	 */
	public java.util.List<NotificationQueueEntry> findAll();

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
	public java.util.List<NotificationQueueEntry> findAll(int start, int end);

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
	public java.util.List<NotificationQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator);

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
	public java.util.List<NotificationQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationQueueEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification queue entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification queue entries.
	 *
	 * @return the number of notification queue entries
	 */
	public int countAll();

}