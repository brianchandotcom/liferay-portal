/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.exception.NoSuchNotificationRecipientException;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification recipient service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientUtil
 * @generated
 */
@ProviderType
public interface NotificationRecipientPersistence
	extends BasePersistence<NotificationRecipient> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationRecipientUtil} to access the notification recipient persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid(String uuid);

	/**
	 * Returns a range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public NotificationRecipient findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public NotificationRecipient findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns the notification recipients before and after the current notification recipient in the ordered set where uuid = &#63;.
	 *
	 * @param notificationRecipientId the primary key of the current notification recipient
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public NotificationRecipient[] findByUuid_PrevAndNext(
			long notificationRecipientId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Removes all the notification recipients where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of notification recipients where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipients
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipients
	 */
	public java.util.List<NotificationRecipient> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public NotificationRecipient findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the first notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public NotificationRecipient findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the last notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns the notification recipients before and after the current notification recipient in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationRecipientId the primary key of the current notification recipient
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public NotificationRecipient[] findByUuid_C_PrevAndNext(
			long notificationRecipientId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipient> orderByComparator)
		throws NoSuchNotificationRecipientException;

	/**
	 * Removes all the notification recipients where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notification recipients where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipients
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the notification recipient where classPK = &#63; or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient
	 * @throws NoSuchNotificationRecipientException if a matching notification recipient could not be found
	 */
	public NotificationRecipient findByClassPK(long classPK)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class pk
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByClassPK(long classPK);

	/**
	 * Returns the notification recipient where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient, or <code>null</code> if a matching notification recipient could not be found
	 */
	public NotificationRecipient fetchByClassPK(
		long classPK, boolean useFinderCache);

	/**
	 * Removes the notification recipient where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @return the notification recipient that was removed
	 */
	public NotificationRecipient removeByClassPK(long classPK)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the number of notification recipients where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching notification recipients
	 */
	public int countByClassPK(long classPK);

	/**
	 * Caches the notification recipient in the entity cache if it is enabled.
	 *
	 * @param notificationRecipient the notification recipient
	 */
	public void cacheResult(NotificationRecipient notificationRecipient);

	/**
	 * Caches the notification recipients in the entity cache if it is enabled.
	 *
	 * @param notificationRecipients the notification recipients
	 */
	public void cacheResult(
		java.util.List<NotificationRecipient> notificationRecipients);

	/**
	 * Creates a new notification recipient with the primary key. Does not add the notification recipient to the database.
	 *
	 * @param notificationRecipientId the primary key for the new notification recipient
	 * @return the new notification recipient
	 */
	public NotificationRecipient create(long notificationRecipientId);

	/**
	 * Removes the notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient that was removed
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public NotificationRecipient remove(long notificationRecipientId)
		throws NoSuchNotificationRecipientException;

	public NotificationRecipient updateImpl(
		NotificationRecipient notificationRecipient);

	/**
	 * Returns the notification recipient with the primary key or throws a <code>NoSuchNotificationRecipientException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient
	 * @throws NoSuchNotificationRecipientException if a notification recipient with the primary key could not be found
	 */
	public NotificationRecipient findByPrimaryKey(long notificationRecipientId)
		throws NoSuchNotificationRecipientException;

	/**
	 * Returns the notification recipient with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientId the primary key of the notification recipient
	 * @return the notification recipient, or <code>null</code> if a notification recipient with the primary key could not be found
	 */
	public NotificationRecipient fetchByPrimaryKey(
		long notificationRecipientId);

	/**
	 * Returns all the notification recipients.
	 *
	 * @return the notification recipients
	 */
	public java.util.List<NotificationRecipient> findAll();

	/**
	 * Returns a range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @return the range of notification recipients
	 */
	public java.util.List<NotificationRecipient> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipients
	 */
	public java.util.List<NotificationRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipients
	 * @param end the upper bound of the range of notification recipients (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipients
	 */
	public java.util.List<NotificationRecipient> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationRecipient>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification recipients from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification recipients.
	 *
	 * @return the number of notification recipients
	 */
	public int countAll();

}