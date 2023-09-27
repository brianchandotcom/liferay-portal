/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence;

import com.liferay.notification.exception.NoSuchNotificationRecipientSettingException;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification recipient setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientSettingUtil
 * @generated
 */
@ProviderType
public interface NotificationRecipientSettingPersistence
	extends BasePersistence<NotificationRecipientSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationRecipientSettingUtil} to access the notification recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting[] findByUuid_PrevAndNext(
			long notificationRecipientSettingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Removes all the notification recipient settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipient settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting[] findByUuid_C_PrevAndNext(
			long notificationRecipientSettingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Removes all the notification recipient settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipient settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting>
		findByNotificationRecipientId(long notificationRecipientId);

	/**
	 * Returns a range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end);

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting>
		findByNotificationRecipientId(
			long notificationRecipientId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByNotificationRecipientId_First(
			long notificationRecipientId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByNotificationRecipientId_First(
		long notificationRecipientId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByNotificationRecipientId_Last(
			long notificationRecipientId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByNotificationRecipientId_Last(
		long notificationRecipientId,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting[]
			findByNotificationRecipientId_PrevAndNext(
				long notificationRecipientSettingId,
				long notificationRecipientId,
				com.liferay.portal.kernel.util.OrderByComparator
					<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Removes all the notification recipient settings where notificationRecipientId = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 */
	public void removeByNotificationRecipientId(long notificationRecipientId);

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the number of matching notification recipient settings
	 */
	public int countByNotificationRecipientId(long notificationRecipientId);

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting findByNRI_N(
			long notificationRecipientId, String name)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name);

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	public NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name, boolean useFinderCache);

	/**
	 * Removes the notification recipient setting where notificationRecipientId = &#63; and name = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the notification recipient setting that was removed
	 */
	public NotificationRecipientSetting removeByNRI_N(
			long notificationRecipientId, String name)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63; and name = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the number of matching notification recipient settings
	 */
	public int countByNRI_N(long notificationRecipientId, String name);

	/**
	 * Caches the notification recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSetting the notification recipient setting
	 */
	public void cacheResult(
		NotificationRecipientSetting notificationRecipientSetting);

	/**
	 * Caches the notification recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSettings the notification recipient settings
	 */
	public void cacheResult(
		java.util.List<NotificationRecipientSetting>
			notificationRecipientSettings);

	/**
	 * Creates a new notification recipient setting with the primary key. Does not add the notification recipient setting to the database.
	 *
	 * @param notificationRecipientSettingId the primary key for the new notification recipient setting
	 * @return the new notification recipient setting
	 */
	public NotificationRecipientSetting create(
		long notificationRecipientSettingId);

	/**
	 * Removes the notification recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting that was removed
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting remove(
			long notificationRecipientSettingId)
		throws NoSuchNotificationRecipientSettingException;

	public NotificationRecipientSetting updateImpl(
		NotificationRecipientSetting notificationRecipientSetting);

	/**
	 * Returns the notification recipient setting with the primary key or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting findByPrimaryKey(
			long notificationRecipientSettingId)
		throws NoSuchNotificationRecipientSettingException;

	/**
	 * Returns the notification recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting, or <code>null</code> if a notification recipient setting with the primary key could not be found
	 */
	public NotificationRecipientSetting fetchByPrimaryKey(
		long notificationRecipientSettingId);

	/**
	 * Returns all the notification recipient settings.
	 *
	 * @return the notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findAll();

	/**
	 * Returns a range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipient settings
	 */
	public java.util.List<NotificationRecipientSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification recipient settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification recipient settings.
	 *
	 * @return the number of notification recipient settings
	 */
	public int countAll();

}