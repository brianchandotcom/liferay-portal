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

package com.liferay.web.hook.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.web.hook.model.WebHookEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the web hook entry service. This utility wraps <code>com.liferay.web.hook.service.persistence.impl.WebHookEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebHookEntryPersistence
 * @generated
 */
public class WebHookEntryUtil {

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
	public static void clearCache(WebHookEntry webHookEntry) {
		getPersistence().clearCache(webHookEntry);
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
	public static Map<Serializable, WebHookEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WebHookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WebHookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WebHookEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WebHookEntry update(WebHookEntry webHookEntry) {
		return getPersistence().update(webHookEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WebHookEntry update(
		WebHookEntry webHookEntry, ServiceContext serviceContext) {

		return getPersistence().update(webHookEntry, serviceContext);
	}

	/**
	 * Returns all the web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public static WebHookEntry findByUuid_First(
			String uuid, OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByUuid_First(
		String uuid, OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public static WebHookEntry findByUuid_Last(
			String uuid, OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry[] findByUuid_PrevAndNext(
			long webHookEntryId, String uuid,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			webHookEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the web hook entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching web hook entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	public static List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public static WebHookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public static WebHookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry[] findByUuid_C_PrevAndNext(
			long webHookEntryId, String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			webHookEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the web hook entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching web hook entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public static WebHookEntry findByC_D_U(
			long companyId, String destination, String url)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByC_D_U(companyId, destination, url);
	}

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url) {

		return getPersistence().fetchByC_D_U(companyId, destination, url);
	}

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url,
		boolean useFinderCache) {

		return getPersistence().fetchByC_D_U(
			companyId, destination, url, useFinderCache);
	}

	/**
	 * Removes the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the web hook entry that was removed
	 */
	public static WebHookEntry removeByC_D_U(
			long companyId, String destination, String url)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().removeByC_D_U(companyId, destination, url);
	}

	/**
	 * Returns the number of web hook entries where companyId = &#63; and destination = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the number of matching web hook entries
	 */
	public static int countByC_D_U(
		long companyId, String destination, String url) {

		return getPersistence().countByC_D_U(companyId, destination, url);
	}

	/**
	 * Caches the web hook entry in the entity cache if it is enabled.
	 *
	 * @param webHookEntry the web hook entry
	 */
	public static void cacheResult(WebHookEntry webHookEntry) {
		getPersistence().cacheResult(webHookEntry);
	}

	/**
	 * Caches the web hook entries in the entity cache if it is enabled.
	 *
	 * @param webHookEntries the web hook entries
	 */
	public static void cacheResult(List<WebHookEntry> webHookEntries) {
		getPersistence().cacheResult(webHookEntries);
	}

	/**
	 * Creates a new web hook entry with the primary key. Does not add the web hook entry to the database.
	 *
	 * @param webHookEntryId the primary key for the new web hook entry
	 * @return the new web hook entry
	 */
	public static WebHookEntry create(long webHookEntryId) {
		return getPersistence().create(webHookEntryId);
	}

	/**
	 * Removes the web hook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry that was removed
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry remove(long webHookEntryId)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().remove(webHookEntryId);
	}

	public static WebHookEntry updateImpl(WebHookEntry webHookEntry) {
		return getPersistence().updateImpl(webHookEntry);
	}

	/**
	 * Returns the web hook entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry findByPrimaryKey(long webHookEntryId)
		throws com.liferay.web.hook.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry, or <code>null</code> if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry fetchByPrimaryKey(long webHookEntryId) {
		return getPersistence().fetchByPrimaryKey(webHookEntryId);
	}

	/**
	 * Returns all the web hook entries.
	 *
	 * @return the web hook entries
	 */
	public static List<WebHookEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of web hook entries
	 */
	public static List<WebHookEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web hook entries
	 */
	public static List<WebHookEntry> findAll(
		int start, int end, OrderByComparator<WebHookEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of web hook entries
	 */
	public static List<WebHookEntry> findAll(
		int start, int end, OrderByComparator<WebHookEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the web hook entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of web hook entries.
	 *
	 * @return the number of web hook entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WebHookEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WebHookEntryPersistence, WebHookEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WebHookEntryPersistence.class);

		ServiceTracker<WebHookEntryPersistence, WebHookEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<WebHookEntryPersistence, WebHookEntryPersistence>(
						bundle.getBundleContext(),
						WebHookEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}