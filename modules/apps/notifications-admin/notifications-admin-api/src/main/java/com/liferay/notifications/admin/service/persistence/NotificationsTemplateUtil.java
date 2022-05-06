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

import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notifications template service. This utility wraps <code>com.liferay.notifications.admin.service.persistence.impl.NotificationsTemplatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationsTemplatePersistence
 * @generated
 */
public class NotificationsTemplateUtil {

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
	public static void clearCache(NotificationsTemplate notificationsTemplate) {
		getPersistence().clearCache(notificationsTemplate);
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
	public static Map<Serializable, NotificationsTemplate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NotificationsTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NotificationsTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NotificationsTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NotificationsTemplate update(
		NotificationsTemplate notificationsTemplate) {

		return getPersistence().update(notificationsTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NotificationsTemplate update(
		NotificationsTemplate notificationsTemplate,
		ServiceContext serviceContext) {

		return getPersistence().update(notificationsTemplate, serviceContext);
	}

	/**
	 * Returns all the notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notifications templates
	 */
	public static List<NotificationsTemplate> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByUuid_First(
			String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate[] findByUuid_PrevAndNext(
			long notificationsTemplateId, String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_PrevAndNext(
			notificationsTemplateId, uuid, orderByComparator);
	}

	/**
	 * Removes all the notifications templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notifications templates
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the notifications template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationsTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByUUID_G(String uuid, long groupId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the notifications template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the notifications template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the notifications template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the notifications template that was removed
	 */
	public static NotificationsTemplate removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of notifications templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching notifications templates
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notifications templates
	 */
	public static List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static NotificationsTemplate[] findByUuid_C_PrevAndNext(
			long notificationsTemplateId, String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByUuid_C_PrevAndNext(
			notificationsTemplateId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the notifications templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notifications templates
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the notifications templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notifications templates
	 */
	public static List<NotificationsTemplate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the notifications templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByGroupId_First(
			long groupId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first notifications template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByGroupId_First(
		long groupId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByGroupId_Last(
			long groupId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where groupId = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate[] findByGroupId_PrevAndNext(
			long notificationsTemplateId, long groupId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByGroupId_PrevAndNext(
			notificationsTemplateId, groupId, orderByComparator);
	}

	/**
	 * Removes all the notifications templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of notifications templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notifications templates
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the notifications templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching notifications templates
	 */
	public static List<NotificationsTemplate> findByG_E(
		long groupId, boolean enabled) {

		return getPersistence().findByG_E(groupId, enabled);
	}

	/**
	 * Returns a range of all the notifications templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end) {

		return getPersistence().findByG_E(groupId, enabled, start, end);
	}

	/**
	 * Returns an ordered range of all the notifications templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the notifications templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	public static List<NotificationsTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notifications template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByG_E_First(
			long groupId, boolean enabled,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the first notifications template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByG_E_First(
		long groupId, boolean enabled,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	public static NotificationsTemplate findByG_E_Last(
			long groupId, boolean enabled,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByG_E_Last(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last notifications template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	public static NotificationsTemplate fetchByG_E_Last(
		long groupId, boolean enabled,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_Last(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate[] findByG_E_PrevAndNext(
			long notificationsTemplateId, long groupId, boolean enabled,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByG_E_PrevAndNext(
			notificationsTemplateId, groupId, enabled, orderByComparator);
	}

	/**
	 * Removes all the notifications templates where groupId = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 */
	public static void removeByG_E(long groupId, boolean enabled) {
		getPersistence().removeByG_E(groupId, enabled);
	}

	/**
	 * Returns the number of notifications templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching notifications templates
	 */
	public static int countByG_E(long groupId, boolean enabled) {
		return getPersistence().countByG_E(groupId, enabled);
	}

	/**
	 * Caches the notifications template in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplate the notifications template
	 */
	public static void cacheResult(
		NotificationsTemplate notificationsTemplate) {

		getPersistence().cacheResult(notificationsTemplate);
	}

	/**
	 * Caches the notifications templates in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplates the notifications templates
	 */
	public static void cacheResult(
		List<NotificationsTemplate> notificationsTemplates) {

		getPersistence().cacheResult(notificationsTemplates);
	}

	/**
	 * Creates a new notifications template with the primary key. Does not add the notifications template to the database.
	 *
	 * @param notificationsTemplateId the primary key for the new notifications template
	 * @return the new notifications template
	 */
	public static NotificationsTemplate create(long notificationsTemplateId) {
		return getPersistence().create(notificationsTemplateId);
	}

	/**
	 * Removes the notifications template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template that was removed
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate remove(long notificationsTemplateId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().remove(notificationsTemplateId);
	}

	public static NotificationsTemplate updateImpl(
		NotificationsTemplate notificationsTemplate) {

		return getPersistence().updateImpl(notificationsTemplate);
	}

	/**
	 * Returns the notifications template with the primary key or throws a <code>NoSuchNotificationsTemplateException</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate findByPrimaryKey(
			long notificationsTemplateId)
		throws com.liferay.notifications.admin.exception.
			NoSuchNotificationsTemplateException {

		return getPersistence().findByPrimaryKey(notificationsTemplateId);
	}

	/**
	 * Returns the notifications template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template, or <code>null</code> if a notifications template with the primary key could not be found
	 */
	public static NotificationsTemplate fetchByPrimaryKey(
		long notificationsTemplateId) {

		return getPersistence().fetchByPrimaryKey(notificationsTemplateId);
	}

	/**
	 * Returns all the notifications templates.
	 *
	 * @return the notifications templates
	 */
	public static List<NotificationsTemplate> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<NotificationsTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<NotificationsTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<NotificationsTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notifications templates from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notifications templates.
	 *
	 * @return the number of notifications templates
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationsTemplatePersistence getPersistence() {
		return _persistence;
	}

	private static volatile NotificationsTemplatePersistence _persistence;

}