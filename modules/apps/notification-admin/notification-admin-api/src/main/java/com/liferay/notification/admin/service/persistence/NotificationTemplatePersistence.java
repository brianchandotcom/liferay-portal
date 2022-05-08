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

package com.liferay.notification.admin.service.persistence;

import com.liferay.notification.admin.exception.NoSuchNotificationTemplateException;
import com.liferay.notification.admin.model.NotificationTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateUtil
 * @generated
 */
@ProviderType
public interface NotificationTemplatePersistence
	extends BasePersistence<NotificationTemplate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationTemplateUtil} to access the notification template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid(String uuid);

	/**
	 * Returns a range of all the notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate[] findByUuid_PrevAndNext(
			long notificationTemplateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the notification templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification templates
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the notification template that was removed
	 */
	public NotificationTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the number of notification templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching notification templates
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate[] findByUuid_C_PrevAndNext(
			long notificationTemplateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the notification templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification templates
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByGroupId(long groupId);

	/**
	 * Returns a range of all the notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where groupId = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate[] findByGroupId_PrevAndNext(
			long notificationTemplateId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the notification templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notification templates
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled);

	/**
	 * Returns a range of all the notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end);

	/**
	 * Returns an ordered range of all the notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification templates
	 */
	public java.util.List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByG_E_First(
			long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByG_E_First(
		long groupId, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public NotificationTemplate findByG_E_Last(
			long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public NotificationTemplate fetchByG_E_Last(
		long groupId, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate[] findByG_E_PrevAndNext(
			long notificationTemplateId, long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the notification templates where groupId = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 */
	public void removeByG_E(long groupId, boolean enabled);

	/**
	 * Returns the number of notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching notification templates
	 */
	public int countByG_E(long groupId, boolean enabled);

	/**
	 * Caches the notification template in the entity cache if it is enabled.
	 *
	 * @param notificationTemplate the notification template
	 */
	public void cacheResult(NotificationTemplate notificationTemplate);

	/**
	 * Caches the notification templates in the entity cache if it is enabled.
	 *
	 * @param notificationTemplates the notification templates
	 */
	public void cacheResult(
		java.util.List<NotificationTemplate> notificationTemplates);

	/**
	 * Creates a new notification template with the primary key. Does not add the notification template to the database.
	 *
	 * @param notificationTemplateId the primary key for the new notification template
	 * @return the new notification template
	 */
	public NotificationTemplate create(long notificationTemplateId);

	/**
	 * Removes the notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate remove(long notificationTemplateId)
		throws NoSuchNotificationTemplateException;

	public NotificationTemplate updateImpl(
		NotificationTemplate notificationTemplate);

	/**
	 * Returns the notification template with the primary key or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public NotificationTemplate findByPrimaryKey(long notificationTemplateId)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template, or <code>null</code> if a notification template with the primary key could not be found
	 */
	public NotificationTemplate fetchByPrimaryKey(long notificationTemplateId);

	/**
	 * Returns all the notification templates.
	 *
	 * @return the notification templates
	 */
	public java.util.List<NotificationTemplate> findAll();

	/**
	 * Returns a range of all the notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @return the range of notification templates
	 */
	public java.util.List<NotificationTemplate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification templates
	 */
	public java.util.List<NotificationTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification templates
	 * @param end the upper bound of the range of notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification templates
	 */
	public java.util.List<NotificationTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notification templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notification templates.
	 *
	 * @return the number of notification templates
	 */
	public int countAll();

}