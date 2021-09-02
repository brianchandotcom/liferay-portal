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

package com.liferay.webhook.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.webhook.model.WebhookEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the webhook entry service. This utility wraps <code>com.liferay.webhook.service.persistence.impl.WebhookEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntryPersistence
 * @generated
 */
public class WebhookEntryUtil {

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
	public static void clearCache(WebhookEntry webhookEntry) {
		getPersistence().clearCache(webhookEntry);
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
	public static Map<Serializable, WebhookEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WebhookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WebhookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WebhookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WebhookEntry update(WebhookEntry webhookEntry) {
		return getPersistence().update(webhookEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WebhookEntry update(
		WebhookEntry webhookEntry, ServiceContext serviceContext) {

		return getPersistence().update(webhookEntry, serviceContext);
	}

	/**
	 * Returns all the webhook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByUuid_First(
			String uuid, OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByUuid_First(
		String uuid, OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByUuid_Last(
			String uuid, OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the webhook entries before and after the current webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param webhookEntryId the primary key of the current webhook entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry[] findByUuid_PrevAndNext(
			long webhookEntryId, String uuid,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			webhookEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the webhook entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of webhook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhook entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the webhook entries before and after the current webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webhookEntryId the primary key of the current webhook entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry[] findByUuid_C_PrevAndNext(
			long webhookEntryId, String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			webhookEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the webhook entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhook entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the webhook entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching webhook entries
	 */
	public static List<WebhookEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the webhook entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of matching webhook entries
	 */
	public static List<WebhookEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the webhook entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the webhook entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entries
	 */
	public static List<WebhookEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first webhook entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByCompanyId_First(
			long companyId, OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first webhook entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByCompanyId_Last(
			long companyId, OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last webhook entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the webhook entries before and after the current webhook entry in the ordered set where companyId = &#63;.
	 *
	 * @param webhookEntryId the primary key of the current webhook entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry[] findByCompanyId_PrevAndNext(
			long webhookEntryId, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			webhookEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the webhook entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of webhook entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching webhook entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the webhook entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchWebhookEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	public static WebhookEntry findByC_N(long companyId, String name)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the webhook entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the webhook entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	public static WebhookEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the webhook entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the webhook entry that was removed
	 */
	public static WebhookEntry removeByC_N(long companyId, String name)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of webhook entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching webhook entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the webhook entry in the entity cache if it is enabled.
	 *
	 * @param webhookEntry the webhook entry
	 */
	public static void cacheResult(WebhookEntry webhookEntry) {
		getPersistence().cacheResult(webhookEntry);
	}

	/**
	 * Caches the webhook entries in the entity cache if it is enabled.
	 *
	 * @param webhookEntries the webhook entries
	 */
	public static void cacheResult(List<WebhookEntry> webhookEntries) {
		getPersistence().cacheResult(webhookEntries);
	}

	/**
	 * Creates a new webhook entry with the primary key. Does not add the webhook entry to the database.
	 *
	 * @param webhookEntryId the primary key for the new webhook entry
	 * @return the new webhook entry
	 */
	public static WebhookEntry create(long webhookEntryId) {
		return getPersistence().create(webhookEntryId);
	}

	/**
	 * Removes the webhook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry that was removed
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry remove(long webhookEntryId)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().remove(webhookEntryId);
	}

	public static WebhookEntry updateImpl(WebhookEntry webhookEntry) {
		return getPersistence().updateImpl(webhookEntry);
	}

	/**
	 * Returns the webhook entry with the primary key or throws a <code>NoSuchWebhookEntryException</code> if it could not be found.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry findByPrimaryKey(long webhookEntryId)
		throws com.liferay.webhook.exception.NoSuchWebhookEntryException {

		return getPersistence().findByPrimaryKey(webhookEntryId);
	}

	/**
	 * Returns the webhook entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry, or <code>null</code> if a webhook entry with the primary key could not be found
	 */
	public static WebhookEntry fetchByPrimaryKey(long webhookEntryId) {
		return getPersistence().fetchByPrimaryKey(webhookEntryId);
	}

	/**
	 * Returns all the webhook entries.
	 *
	 * @return the webhook entries
	 */
	public static List<WebhookEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of webhook entries
	 */
	public static List<WebhookEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of webhook entries
	 */
	public static List<WebhookEntry> findAll(
		int start, int end, OrderByComparator<WebhookEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of webhook entries
	 */
	public static List<WebhookEntry> findAll(
		int start, int end, OrderByComparator<WebhookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the webhook entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of webhook entries.
	 *
	 * @return the number of webhook entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WebhookEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WebhookEntryPersistence, WebhookEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WebhookEntryPersistence.class);

		ServiceTracker<WebhookEntryPersistence, WebhookEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<WebhookEntryPersistence, WebhookEntryPersistence>(
						bundle.getBundleContext(),
						WebhookEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}