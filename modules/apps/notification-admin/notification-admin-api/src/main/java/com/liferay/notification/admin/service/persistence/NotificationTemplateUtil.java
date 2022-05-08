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

import com.liferay.notification.admin.model.NotificationTemplate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification template service. This utility wraps <code>com.liferay.notification.admin.service.persistence.impl.NotificationTemplatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplatePersistence
 * @generated
 */
public class NotificationTemplateUtil {

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
	public static void clearCache(NotificationTemplate notificationTemplate) {
		getPersistence().clearCache(notificationTemplate);
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
	public static Map<Serializable, NotificationTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationTemplate update(
		NotificationTemplate notificationTemplate) {

		return getPersistence().update(notificationTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationTemplate update(
		NotificationTemplate notificationTemplate,
		ServiceContext serviceContext) {

		return getPersistence().update(notificationTemplate, serviceContext);
	}

	/**
	 * Returns all the notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification templates
	 */
	public static List<NotificationTemplate> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<NotificationTemplate> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<NotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<NotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByUuid_First(
			String uuid,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate[] findByUuid_PrevAndNext(
			long notificationTemplateId, String uuid,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_PrevAndNext(
			notificationTemplateId, uuid, orderByComparator);
	}

	/**
	 * Removes all the notification templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification templates
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByUUID_G(String uuid, long groupId)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the notification template that was removed
	 */
	public static NotificationTemplate removeByUUID_G(String uuid, long groupId)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of notification templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching notification templates
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification templates
	 */
	public static List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<NotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static NotificationTemplate[] findByUuid_C_PrevAndNext(
			long notificationTemplateId, String uuid, long companyId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_PrevAndNext(
			notificationTemplateId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the notification templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification templates
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notification templates
	 */
	public static List<NotificationTemplate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

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
	public static List<NotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByGroupId_First(
			long groupId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByGroupId_First(
		long groupId,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByGroupId_Last(
			long groupId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the notification templates before and after the current notification template in the ordered set where groupId = &#63;.
	 *
	 * @param notificationTemplateId the primary key of the current notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate[] findByGroupId_PrevAndNext(
			long notificationTemplateId, long groupId,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_PrevAndNext(
			notificationTemplateId, groupId, orderByComparator);
	}

	/**
	 * Removes all the notification templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notification templates
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching notification templates
	 */
	public static List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled) {

		return getPersistence().findByG_E(groupId, enabled);
	}

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
	public static List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end) {

		return getPersistence().findByG_E(groupId, enabled, start, end);
	}

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
	public static List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

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
	public static List<NotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByG_E_First(
			long groupId, boolean enabled,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the first notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByG_E_First(
		long groupId, boolean enabled,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template
	 * @throws NoSuchNotificationTemplateException if a matching notification template could not be found
	 */
	public static NotificationTemplate findByG_E_Last(
			long groupId, boolean enabled,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_Last(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template, or <code>null</code> if a matching notification template could not be found
	 */
	public static NotificationTemplate fetchByG_E_Last(
		long groupId, boolean enabled,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_Last(
			groupId, enabled, orderByComparator);
	}

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
	public static NotificationTemplate[] findByG_E_PrevAndNext(
			long notificationTemplateId, long groupId, boolean enabled,
			OrderByComparator<NotificationTemplate> orderByComparator)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_PrevAndNext(
			notificationTemplateId, groupId, enabled, orderByComparator);
	}

	/**
	 * Removes all the notification templates where groupId = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 */
	public static void removeByG_E(long groupId, boolean enabled) {
		getPersistence().removeByG_E(groupId, enabled);
	}

	/**
	 * Returns the number of notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching notification templates
	 */
	public static int countByG_E(long groupId, boolean enabled) {
		return getPersistence().countByG_E(groupId, enabled);
	}

	/**
	 * Caches the notification template in the entity cache if it is enabled.
	 *
	 * @param notificationTemplate the notification template
	 */
	public static void cacheResult(NotificationTemplate notificationTemplate) {
		getPersistence().cacheResult(notificationTemplate);
	}

	/**
	 * Caches the notification templates in the entity cache if it is enabled.
	 *
	 * @param notificationTemplates the notification templates
	 */
	public static void cacheResult(
		List<NotificationTemplate> notificationTemplates) {

		getPersistence().cacheResult(notificationTemplates);
	}

	/**
	 * Creates a new notification template with the primary key. Does not add the notification template to the database.
	 *
	 * @param notificationTemplateId the primary key for the new notification template
	 * @return the new notification template
	 */
	public static NotificationTemplate create(long notificationTemplateId) {
		return getPersistence().create(notificationTemplateId);
	}

	/**
	 * Removes the notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate remove(long notificationTemplateId)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().remove(notificationTemplateId);
	}

	public static NotificationTemplate updateImpl(
		NotificationTemplate notificationTemplate) {

		return getPersistence().updateImpl(notificationTemplate);
	}

	/**
	 * Returns the notification template with the primary key or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template
	 * @throws NoSuchNotificationTemplateException if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate findByPrimaryKey(
			long notificationTemplateId)
		throws com.liferay.notification.admin.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByPrimaryKey(notificationTemplateId);
	}

	/**
	 * Returns the notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateId the primary key of the notification template
	 * @return the notification template, or <code>null</code> if a notification template with the primary key could not be found
	 */
	public static NotificationTemplate fetchByPrimaryKey(
		long notificationTemplateId) {

		return getPersistence().fetchByPrimaryKey(notificationTemplateId);
	}

	/**
	 * Returns all the notification templates.
	 *
	 * @return the notification templates
	 */
	public static List<NotificationTemplate> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<NotificationTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<NotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<NotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notification templates from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notification templates.
	 *
	 * @return the number of notification templates
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationTemplatePersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationTemplatePersistence _persistence;

}