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

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce notification queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationQueueEntryPersistence
	extends BasePersistence<CommerceNotificationQueueEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationQueueEntryUtil} to access the commerce notification queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId);

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry[] findByGroupId_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification queue entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId);

	/**
	 * Returns a range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationQueueEntryId,
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the commerce notification queue entries where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	 * Returns the number of commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification queue entries
	 */
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	 * Returns all the commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findBySent(
		boolean sent);

	/**
	 * Returns a range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findBySent_First(
			boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchBySent_First(
		boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findBySent_Last(
			boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchBySent_Last(
		boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry[] findBySent_PrevAndNext(
			long commerceNotificationQueueEntryId, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the commerce notification queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	public void removeBySent(boolean sent);

	/**
	 * Returns the number of commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	public int countBySent(boolean sent);

	/**
	 * Returns all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate);

	/**
	 * Returns a range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByLtSentDate_First(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByLtSentDate_Last(
			Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry[] findByLtSentDate_PrevAndNext(
			long commerceNotificationQueueEntryId, Date sentDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the commerce notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public void removeByLtSentDate(Date sentDate);

	/**
	 * Returns the number of commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching commerce notification queue entries
	 */
	public int countByLtSentDate(Date sentDate);

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public CommerceNotificationQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry[] findByG_C_C_S_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			long classNameId, long classPK, boolean sent,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	public void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	public int countByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent);

	/**
	 * Caches the commerce notification queue entry in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntry the commerce notification queue entry
	 */
	public void cacheResult(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry);

	/**
	 * Caches the commerce notification queue entries in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntries the commerce notification queue entries
	 */
	public void cacheResult(
		java.util.List<CommerceNotificationQueueEntry>
			commerceNotificationQueueEntries);

	/**
	 * Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	 *
	 * @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	 * @return the new commerce notification queue entry
	 */
	public CommerceNotificationQueueEntry create(
		long commerceNotificationQueueEntryId);

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry remove(
			long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException;

	public CommerceNotificationQueueEntry updateImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry);

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a <code>NoSuchNotificationQueueEntryException</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry findByPrimaryKey(
			long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException;

	/**
	 * Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	 */
	public CommerceNotificationQueueEntry fetchByPrimaryKey(
		long commerceNotificationQueueEntryId);

	/**
	 * Returns all the commerce notification queue entries.
	 *
	 * @return the commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findAll();

	/**
	 * Returns a range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification queue entries
	 */
	public java.util.List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce notification queue entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce notification queue entries.
	 *
	 * @return the number of commerce notification queue entries
	 */
	public int countAll();

}