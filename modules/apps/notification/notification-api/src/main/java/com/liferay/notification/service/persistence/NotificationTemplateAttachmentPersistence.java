/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.exception.NoSuchNotificationTemplateAttachmentException;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification template attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachmentUtil
 * @generated
 */
@ProviderType
public interface NotificationTemplateAttachmentPersistence
	extends BasePersistence<NotificationTemplateAttachment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationTemplateAttachmentUtil} to access the notification template attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the matching notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment>
		findByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns a range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @return the range of matching notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end);

	/**
	 * Returns an ordered range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplateAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the notification template attachments where notificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment>
		findByNotificationTemplateId(
			long notificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplateAttachment> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment findByNotificationTemplateId_First(
			long notificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplateAttachment> orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Returns the first notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment fetchByNotificationTemplateId_First(
		long notificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateAttachment> orderByComparator);

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment findByNotificationTemplateId_Last(
			long notificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplateAttachment> orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Returns the last notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment fetchByNotificationTemplateId_Last(
		long notificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateAttachment> orderByComparator);

	/**
	 * Returns the notification template attachments before and after the current notification template attachment in the ordered set where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the current notification template attachment
	 * @param notificationTemplateId the notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	public NotificationTemplateAttachment[]
			findByNotificationTemplateId_PrevAndNext(
				long notificationTemplateAttachmentId,
				long notificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<NotificationTemplateAttachment> orderByComparator)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Removes all the notification template attachments where notificationTemplateId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 */
	public void removeByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @return the number of matching notification template attachments
	 */
	public int countByNotificationTemplateId(long notificationTemplateId);

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment findByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId);

	/**
	 * Returns the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template attachment, or <code>null</code> if a matching notification template attachment could not be found
	 */
	public NotificationTemplateAttachment fetchByNTI_OFI(
		long notificationTemplateId, long objectFieldId,
		boolean useFinderCache);

	/**
	 * Removes the notification template attachment where notificationTemplateId = &#63; and objectFieldId = &#63; from the database.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the notification template attachment that was removed
	 */
	public NotificationTemplateAttachment removeByNTI_OFI(
			long notificationTemplateId, long objectFieldId)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Returns the number of notification template attachments where notificationTemplateId = &#63; and objectFieldId = &#63;.
	 *
	 * @param notificationTemplateId the notification template ID
	 * @param objectFieldId the object field ID
	 * @return the number of matching notification template attachments
	 */
	public int countByNTI_OFI(long notificationTemplateId, long objectFieldId);

	/**
	 * Caches the notification template attachment in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachment the notification template attachment
	 */
	public void cacheResult(
		NotificationTemplateAttachment notificationTemplateAttachment);

	/**
	 * Caches the notification template attachments in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateAttachments the notification template attachments
	 */
	public void cacheResult(
		java.util.List<NotificationTemplateAttachment>
			notificationTemplateAttachments);

	/**
	 * Creates a new notification template attachment with the primary key. Does not add the notification template attachment to the database.
	 *
	 * @param notificationTemplateAttachmentId the primary key for the new notification template attachment
	 * @return the new notification template attachment
	 */
	public NotificationTemplateAttachment create(
		long notificationTemplateAttachmentId);

	/**
	 * Removes the notification template attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment that was removed
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	public NotificationTemplateAttachment remove(
			long notificationTemplateAttachmentId)
		throws NoSuchNotificationTemplateAttachmentException;

	public NotificationTemplateAttachment updateImpl(
		NotificationTemplateAttachment notificationTemplateAttachment);

	/**
	 * Returns the notification template attachment with the primary key or throws a <code>NoSuchNotificationTemplateAttachmentException</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment
	 * @throws NoSuchNotificationTemplateAttachmentException if a notification template attachment with the primary key could not be found
	 */
	public NotificationTemplateAttachment findByPrimaryKey(
			long notificationTemplateAttachmentId)
		throws NoSuchNotificationTemplateAttachmentException;

	/**
	 * Returns the notification template attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateAttachmentId the primary key of the notification template attachment
	 * @return the notification template attachment, or <code>null</code> if a notification template attachment with the primary key could not be found
	 */
	public NotificationTemplateAttachment fetchByPrimaryKey(
		long notificationTemplateAttachmentId);

	/**
	 * Returns all the notification template attachments.
	 *
	 * @return the notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment> findAll();

	/**
	 * Returns a range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @return the range of notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the notification template attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template attachments
	 * @param end the upper bound of the range of notification template attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template attachments
	 */
	public java.util.List<NotificationTemplateAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationTemplateAttachment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification template attachments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification template attachments.
	 *
	 * @return the number of notification template attachments
	 */
	public int countAll();

}