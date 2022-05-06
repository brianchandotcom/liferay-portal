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

import com.liferay.notifications.admin.exception.NoSuchNotificationsQueueEntryException;
import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notifications queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationsQueueEntryUtil
 * @generated
 */
@ProviderType
public interface NotificationsQueueEntryPersistence
	extends BasePersistence<NotificationsQueueEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationsQueueEntryUtil} to access the notifications queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry> findByGroupId(long groupId);

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
	public java.util.List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry[] findByGroupId_PrevAndNext(
			long notificationsQueueEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Removes all the notifications queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notifications queue entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the matching notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry>
		findByNotificationsTemplateId(long notificationsTemplateId);

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
	public java.util.List<NotificationsQueueEntry>
		findByNotificationsTemplateId(
			long notificationsTemplateId, int start, int end);

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
	public java.util.List<NotificationsQueueEntry>
		findByNotificationsTemplateId(
			long notificationsTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry>
		findByNotificationsTemplateId(
			long notificationsTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByNotificationsTemplateId_First(
			long notificationsTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByNotificationsTemplateId_First(
		long notificationsTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByNotificationsTemplateId_Last(
			long notificationsTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByNotificationsTemplateId_Last(
		long notificationsTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry[] findByNotificationsTemplateId_PrevAndNext(
			long notificationsQueueEntryId, long notificationsTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Removes all the notifications queue entries where notificationsTemplateId = &#63; from the database.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 */
	public void removeByNotificationsTemplateId(long notificationsTemplateId);

	/**
	 * Returns the number of notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the number of matching notifications queue entries
	 */
	public int countByNotificationsTemplateId(long notificationsTemplateId);

	/**
	 * Returns all the notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry> findBySent(boolean sent);

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
	public java.util.List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end);

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
	public java.util.List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findBySent_First(
			boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchBySent_First(
		boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findBySent_Last(
			boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchBySent_Last(
		boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry[] findBySent_PrevAndNext(
			long notificationsQueueEntryId, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Removes all the notifications queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	public void removeBySent(boolean sent);

	/**
	 * Returns the number of notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	public int countBySent(boolean sent);

	/**
	 * Returns all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate);

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
	public java.util.List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end);

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
	public java.util.List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByLtSentDate_First(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry findByLtSentDate_Last(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	public NotificationsQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry[] findByLtSentDate_PrevAndNext(
			long notificationsQueueEntryId, Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Removes all the notifications queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public void removeByLtSentDate(Date sentDate);

	/**
	 * Returns the number of notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notifications queue entries
	 */
	public int countByLtSentDate(Date sentDate);

	/**
	 * Returns all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

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
	public java.util.List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end);

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
	public java.util.List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache);

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
	public NotificationsQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

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
	public NotificationsQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public NotificationsQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

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
	public NotificationsQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public NotificationsQueueEntry[] findByG_C_C_S_PrevAndNext(
			long notificationsQueueEntryId, long groupId, long classNameId,
			long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Removes all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	public void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

	/**
	 * Returns the number of notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	public int countByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

	/**
	 * Caches the notifications queue entry in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 */
	public void cacheResult(NotificationsQueueEntry notificationsQueueEntry);

	/**
	 * Caches the notifications queue entries in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntries the notifications queue entries
	 */
	public void cacheResult(
		java.util.List<NotificationsQueueEntry> notificationsQueueEntries);

	/**
	 * Creates a new notifications queue entry with the primary key. Does not add the notifications queue entry to the database.
	 *
	 * @param notificationsQueueEntryId the primary key for the new notifications queue entry
	 * @return the new notifications queue entry
	 */
	public NotificationsQueueEntry create(long notificationsQueueEntryId);

	/**
	 * Removes the notifications queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry that was removed
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry remove(long notificationsQueueEntryId)
		throws NoSuchNotificationsQueueEntryException;

	public NotificationsQueueEntry updateImpl(
		NotificationsQueueEntry notificationsQueueEntry);

	/**
	 * Returns the notifications queue entry with the primary key or throws a <code>NoSuchNotificationsQueueEntryException</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry findByPrimaryKey(
			long notificationsQueueEntryId)
		throws NoSuchNotificationsQueueEntryException;

	/**
	 * Returns the notifications queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry, or <code>null</code> if a notifications queue entry with the primary key could not be found
	 */
	public NotificationsQueueEntry fetchByPrimaryKey(
		long notificationsQueueEntryId);

	/**
	 * Returns all the notifications queue entries.
	 *
	 * @return the notifications queue entries
	 */
	public java.util.List<NotificationsQueueEntry> findAll();

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
	public java.util.List<NotificationsQueueEntry> findAll(int start, int end);

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
	public java.util.List<NotificationsQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator);

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
	public java.util.List<NotificationsQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notifications queue entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notifications queue entries.
	 *
	 * @return the number of notifications queue entries
	 */
	public int countAll();

}