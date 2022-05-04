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

import com.liferay.notifications.admin.exception.NoSuchNotificationsTemplateException;
import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notifications template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationsTemplateUtil
 * @generated
 */
@ProviderType
public interface NotificationsTemplatePersistence
	extends BasePersistence<NotificationsTemplate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationsTemplateUtil} to access the notifications template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid(String uuid);

	/**
	 * Returns a range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public NotificationsTemplate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public NotificationsTemplate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public NotificationsTemplate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public NotificationsTemplate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate[] findByUuid_PrevAndNext(
			long notificationsTemplateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns all the notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the notifications templates that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set of notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate[] filterFindByUuid_PrevAndNext(
			long notificationsTemplateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Removes all the notifications templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notifications templates
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notifications templates that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	public java.util.List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public NotificationsTemplate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public NotificationsTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public NotificationsTemplate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public NotificationsTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate[] findByUuid_C_PrevAndNext(
			long notificationsTemplateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns all the notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the notifications templates that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates that the user has permission to view
	 */
	public java.util.List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set of notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate[] filterFindByUuid_C_PrevAndNext(
			long notificationsTemplateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Removes all the notifications templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notifications templates
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notifications templates that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Caches the notifications template in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplate the notifications template
	 */
	public void cacheResult(NotificationsTemplate notificationsTemplate);

	/**
	 * Caches the notifications templates in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplates the notifications templates
	 */
	public void cacheResult(
		java.util.List<NotificationsTemplate> notificationsTemplates);

	/**
	 * Creates a new notifications template with the primary key. Does not add the notifications template to the database.
	 *
	 * @param notificationsTemplateId the primary key for the new notifications template
	 * @return the new notifications template
	 */
	public NotificationsTemplate create(long notificationsTemplateId);

	/**
	 * Removes the notifications template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template that was removed
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate remove(long notificationsTemplateId)
		throws NoSuchNotificationsTemplateException;

	public NotificationsTemplate updateImpl(
		NotificationsTemplate notificationsTemplate);

	/**
	 * Returns the notifications template with the primary key or throws a <code>NoSuchNotificationsTemplateException</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate findByPrimaryKey(long notificationsTemplateId)
		throws NoSuchNotificationsTemplateException;

	/**
	 * Returns the notifications template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template, or <code>null</code> if a notifications template with the primary key could not be found
	 */
	public NotificationsTemplate fetchByPrimaryKey(
		long notificationsTemplateId);

	/**
	 * Returns all the notifications templates.
	 *
	 * @return the notifications templates
	 */
	public java.util.List<NotificationsTemplate> findAll();

	/**
	 * Returns a range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of notifications templates
	 */
	public java.util.List<NotificationsTemplate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications templates
	 */
	public java.util.List<NotificationsTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications templates
	 */
	public java.util.List<NotificationsTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NotificationsTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notifications templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notifications templates.
	 *
	 * @return the number of notifications templates
	 */
	public int countAll();

}