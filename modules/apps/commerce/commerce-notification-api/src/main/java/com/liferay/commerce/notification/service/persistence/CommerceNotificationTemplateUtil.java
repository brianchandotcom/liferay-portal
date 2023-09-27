/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce notification template service. This utility wraps <code>com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplatePersistence
 * @generated
 */
public class CommerceNotificationTemplateUtil {

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
	public static void clearCache(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		getPersistence().clearCache(commerceNotificationTemplate);
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
	public static Map<Serializable, CommerceNotificationTemplate>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationTemplate update(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		return getPersistence().update(commerceNotificationTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationTemplate update(
		CommerceNotificationTemplate commerceNotificationTemplate,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceNotificationTemplate, serviceContext);
	}

	/**
	 * Returns all the commerce notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByUuid_First(
			String uuid,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] findByUuid_PrevAndNext(
			long commerceNotificationTemplateId, String uuid,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceNotificationTemplateId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce notification templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce notification templates
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification template that was removed
	 */
	public static CommerceNotificationTemplate removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] findByUuid_C_PrevAndNext(
			long commerceNotificationTemplateId, String uuid, long companyId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceNotificationTemplateId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce notification templates
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByGroupId(
		long groupId) {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] findByGroupId_PrevAndNext(
			long commerceNotificationTemplateId, long groupId,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByGroupId_PrevAndNext(
			commerceNotificationTemplateId, groupId, orderByComparator);
	}

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId) {

		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[]
			filterFindByGroupId_PrevAndNext(
				long commerceNotificationTemplateId, long groupId,
				OrderByComparator<CommerceNotificationTemplate>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			commerceNotificationTemplateId, groupId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce notification templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled) {

		return getPersistence().findByG_E(groupId, enabled);
	}

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end) {

		return getPersistence().findByG_E(groupId, enabled, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_E(
			groupId, enabled, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByG_E_First(
			long groupId, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByG_E_First(
		long groupId, boolean enabled,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_First(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByG_E_Last(
			long groupId, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_Last(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByG_E_Last(
		long groupId, boolean enabled,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_E_Last(
			groupId, enabled, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] findByG_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_E_PrevAndNext(
			commerceNotificationTemplateId, groupId, enabled,
			orderByComparator);
	}

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled) {

		return getPersistence().filterFindByG_E(groupId, enabled);
	}

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled, int start, int end) {

		return getPersistence().filterFindByG_E(groupId, enabled, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().filterFindByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] filterFindByG_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().filterFindByG_E_PrevAndNext(
			commerceNotificationTemplateId, groupId, enabled,
			orderByComparator);
	}

	/**
	 * Removes all the commerce notification templates where groupId = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 */
	public static void removeByG_E(long groupId, boolean enabled) {
		getPersistence().removeByG_E(groupId, enabled);
	}

	/**
	 * Returns the number of commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates
	 */
	public static int countByG_E(long groupId, boolean enabled) {
		return getPersistence().countByG_E(groupId, enabled);
	}

	/**
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	public static int filterCountByG_E(long groupId, boolean enabled) {
		return getPersistence().filterCountByG_E(groupId, enabled);
	}

	/**
	 * Returns all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled) {

		return getPersistence().findByG_T_E(groupId, type, enabled);
	}

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end) {

		return getPersistence().findByG_T_E(groupId, type, enabled, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findByG_T_E(
			groupId, type, enabled, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_T_E(
			groupId, type, enabled, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByG_T_E_First(
			long groupId, String type, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_T_E_First(
			groupId, type, enabled, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByG_T_E_First(
		long groupId, String type, boolean enabled,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_T_E_First(
			groupId, type, enabled, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate findByG_T_E_Last(
			long groupId, String type, boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_T_E_Last(
			groupId, type, enabled, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public static CommerceNotificationTemplate fetchByG_T_E_Last(
		long groupId, String type, boolean enabled,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().fetchByG_T_E_Last(
			groupId, type, enabled, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] findByG_T_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, String type,
			boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByG_T_E_PrevAndNext(
			commerceNotificationTemplateId, groupId, type, enabled,
			orderByComparator);
	}

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled) {

		return getPersistence().filterFindByG_T_E(groupId, type, enabled);
	}

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled, int start, int end) {

		return getPersistence().filterFindByG_T_E(
			groupId, type, enabled, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	public static List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().filterFindByG_T_E(
			groupId, type, enabled, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate[] filterFindByG_T_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, String type,
			boolean enabled,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().filterFindByG_T_E_PrevAndNext(
			commerceNotificationTemplateId, groupId, type, enabled,
			orderByComparator);
	}

	/**
	 * Removes all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 */
	public static void removeByG_T_E(
		long groupId, String type, boolean enabled) {

		getPersistence().removeByG_T_E(groupId, type, enabled);
	}

	/**
	 * Returns the number of commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates
	 */
	public static int countByG_T_E(long groupId, String type, boolean enabled) {
		return getPersistence().countByG_T_E(groupId, type, enabled);
	}

	/**
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	public static int filterCountByG_T_E(
		long groupId, String type, boolean enabled) {

		return getPersistence().filterCountByG_T_E(groupId, type, enabled);
	}

	/**
	 * Caches the commerce notification template in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplate the commerce notification template
	 */
	public static void cacheResult(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		getPersistence().cacheResult(commerceNotificationTemplate);
	}

	/**
	 * Caches the commerce notification templates in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplates the commerce notification templates
	 */
	public static void cacheResult(
		List<CommerceNotificationTemplate> commerceNotificationTemplates) {

		getPersistence().cacheResult(commerceNotificationTemplates);
	}

	/**
	 * Creates a new commerce notification template with the primary key. Does not add the commerce notification template to the database.
	 *
	 * @param commerceNotificationTemplateId the primary key for the new commerce notification template
	 * @return the new commerce notification template
	 */
	public static CommerceNotificationTemplate create(
		long commerceNotificationTemplateId) {

		return getPersistence().create(commerceNotificationTemplateId);
	}

	/**
	 * Removes the commerce notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate remove(
			long commerceNotificationTemplateId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().remove(commerceNotificationTemplateId);
	}

	public static CommerceNotificationTemplate updateImpl(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		return getPersistence().updateImpl(commerceNotificationTemplate);
	}

	/**
	 * Returns the commerce notification template with the primary key or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate findByPrimaryKey(
			long commerceNotificationTemplateId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateException {

		return getPersistence().findByPrimaryKey(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns the commerce notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the primary key of the commerce notification template
	 * @return the commerce notification template, or <code>null</code> if a commerce notification template with the primary key could not be found
	 */
	public static CommerceNotificationTemplate fetchByPrimaryKey(
		long commerceNotificationTemplateId) {

		return getPersistence().fetchByPrimaryKey(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns all the commerce notification templates.
	 *
	 * @return the commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification templates
	 */
	public static List<CommerceNotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce notification templates from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce notification templates.
	 *
	 * @return the number of commerce notification templates
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceNotificationTemplatePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceNotificationTemplatePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceNotificationTemplatePersistence
		_persistence;

}