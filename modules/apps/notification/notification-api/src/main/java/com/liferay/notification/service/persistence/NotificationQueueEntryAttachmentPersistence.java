/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.exception.NoSuchNotificationQueueEntryAttachmentException;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification queue entry attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachmentUtil
 * @generated
 */
@ProviderType
public interface NotificationQueueEntryAttachmentPersistence
	extends BasePersistence<NotificationQueueEntryAttachment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationQueueEntryAttachmentUtil} to access the notification queue entry attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the matching notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(long notificationQueueEntryId);

	/**
	 * Returns a range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of matching notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntryAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment>
		findByNotificationQueueEntryId(
			long notificationQueueEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntryAttachment> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	public NotificationQueueEntryAttachment
			findByNotificationQueueEntryId_First(
				long notificationQueueEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<NotificationQueueEntryAttachment> orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException;

	/**
	 * Returns the first notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	public NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_First(
			long notificationQueueEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntryAttachment> orderByComparator);

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a matching notification queue entry attachment could not be found
	 */
	public NotificationQueueEntryAttachment findByNotificationQueueEntryId_Last(
			long notificationQueueEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntryAttachment> orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException;

	/**
	 * Returns the last notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification queue entry attachment, or <code>null</code> if a matching notification queue entry attachment could not be found
	 */
	public NotificationQueueEntryAttachment
		fetchByNotificationQueueEntryId_Last(
			long notificationQueueEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationQueueEntryAttachment> orderByComparator);

	/**
	 * Returns the notification queue entry attachments before and after the current notification queue entry attachment in the ordered set where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the current notification queue entry attachment
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public NotificationQueueEntryAttachment[]
			findByNotificationQueueEntryId_PrevAndNext(
				long notificationQueueEntryAttachmentId,
				long notificationQueueEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<NotificationQueueEntryAttachment> orderByComparator)
		throws NoSuchNotificationQueueEntryAttachmentException;

	/**
	 * Removes all the notification queue entry attachments where notificationQueueEntryId = &#63; from the database.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 */
	public void removeByNotificationQueueEntryId(long notificationQueueEntryId);

	/**
	 * Returns the number of notification queue entry attachments where notificationQueueEntryId = &#63;.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID
	 * @return the number of matching notification queue entry attachments
	 */
	public int countByNotificationQueueEntryId(long notificationQueueEntryId);

	/**
	 * Caches the notification queue entry attachment in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 */
	public void cacheResult(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment);

	/**
	 * Caches the notification queue entry attachments in the entity cache if it is enabled.
	 *
	 * @param notificationQueueEntryAttachments the notification queue entry attachments
	 */
	public void cacheResult(
		java.util.List<NotificationQueueEntryAttachment>
			notificationQueueEntryAttachments);

	/**
	 * Creates a new notification queue entry attachment with the primary key. Does not add the notification queue entry attachment to the database.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key for the new notification queue entry attachment
	 * @return the new notification queue entry attachment
	 */
	public NotificationQueueEntryAttachment create(
		long notificationQueueEntryAttachmentId);

	/**
	 * Removes the notification queue entry attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public NotificationQueueEntryAttachment remove(
			long notificationQueueEntryAttachmentId)
		throws NoSuchNotificationQueueEntryAttachmentException;

	public NotificationQueueEntryAttachment updateImpl(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment);

	/**
	 * Returns the notification queue entry attachment with the primary key or throws a <code>NoSuchNotificationQueueEntryAttachmentException</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment
	 * @throws NoSuchNotificationQueueEntryAttachmentException if a notification queue entry attachment with the primary key could not be found
	 */
	public NotificationQueueEntryAttachment findByPrimaryKey(
			long notificationQueueEntryAttachmentId)
		throws NoSuchNotificationQueueEntryAttachmentException;

	/**
	 * Returns the notification queue entry attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment, or <code>null</code> if a notification queue entry attachment with the primary key could not be found
	 */
	public NotificationQueueEntryAttachment fetchByPrimaryKey(
		long notificationQueueEntryAttachmentId);

	/**
	 * Returns all the notification queue entry attachments.
	 *
	 * @return the notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment> findAll();

	/**
	 * Returns a range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification queue entry attachments
	 */
	public java.util.List<NotificationQueueEntryAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationQueueEntryAttachment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification queue entry attachments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification queue entry attachments.
	 *
	 * @return the number of notification queue entry attachments
	 */
	public int countAll();

}