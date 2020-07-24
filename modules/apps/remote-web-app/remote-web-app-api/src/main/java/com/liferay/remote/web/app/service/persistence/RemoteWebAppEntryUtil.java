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

package com.liferay.remote.web.app.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the remote web app entry service. This utility wraps <code>com.liferay.remote.web.app.service.persistence.impl.RemoteWebAppEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebAppEntryPersistence
 * @generated
 */
public class RemoteWebAppEntryUtil {

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
	public static void clearCache(RemoteWebAppEntry remoteWebAppEntry) {
		getPersistence().clearCache(remoteWebAppEntry);
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
	public static Map<Serializable, RemoteWebAppEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RemoteWebAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RemoteWebAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RemoteWebAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RemoteWebAppEntry update(
		RemoteWebAppEntry remoteWebAppEntry) {

		return getPersistence().update(remoteWebAppEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RemoteWebAppEntry update(
		RemoteWebAppEntry remoteWebAppEntry, ServiceContext serviceContext) {

		return getPersistence().update(remoteWebAppEntry, serviceContext);
	}

	/**
	 * Returns all the remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry findByUuid_First(
			String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByUuid_First(
		String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry findByUuid_Last(
			String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public static RemoteWebAppEntry[] findByUuid_PrevAndNext(
			long entryId, String uuid,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			entryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the remote web app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote web app entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	public static List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public static RemoteWebAppEntry[] findByUuid_C_PrevAndNext(
			long entryId, String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			entryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the remote web app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote web app entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry findByC_U(long companyId, String url)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByC_U(companyId, url);
	}

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByC_U(long companyId, String url) {
		return getPersistence().fetchByC_U(companyId, url);
	}

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static RemoteWebAppEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache) {

		return getPersistence().fetchByC_U(companyId, url, useFinderCache);
	}

	/**
	 * Removes the remote web app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote web app entry that was removed
	 */
	public static RemoteWebAppEntry removeByC_U(long companyId, String url)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().removeByC_U(companyId, url);
	}

	/**
	 * Returns the number of remote web app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote web app entries
	 */
	public static int countByC_U(long companyId, String url) {
		return getPersistence().countByC_U(companyId, url);
	}

	/**
	 * Caches the remote web app entry in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 */
	public static void cacheResult(RemoteWebAppEntry remoteWebAppEntry) {
		getPersistence().cacheResult(remoteWebAppEntry);
	}

	/**
	 * Caches the remote web app entries in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntries the remote web app entries
	 */
	public static void cacheResult(
		List<RemoteWebAppEntry> remoteWebAppEntries) {

		getPersistence().cacheResult(remoteWebAppEntries);
	}

	/**
	 * Creates a new remote web app entry with the primary key. Does not add the remote web app entry to the database.
	 *
	 * @param entryId the primary key for the new remote web app entry
	 * @return the new remote web app entry
	 */
	public static RemoteWebAppEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	 * Removes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public static RemoteWebAppEntry remove(long entryId)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().remove(entryId);
	}

	public static RemoteWebAppEntry updateImpl(
		RemoteWebAppEntry remoteWebAppEntry) {

		return getPersistence().updateImpl(remoteWebAppEntry);
	}

	/**
	 * Returns the remote web app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public static RemoteWebAppEntry findByPrimaryKey(long entryId)
		throws com.liferay.remote.web.app.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	 * Returns the remote web app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry, or <code>null</code> if a remote web app entry with the primary key could not be found
	 */
	public static RemoteWebAppEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns all the remote web app entries.
	 *
	 * @return the remote web app entries
	 */
	public static List<RemoteWebAppEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of remote web app entries
	 */
	public static List<RemoteWebAppEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote web app entries
	 */
	public static List<RemoteWebAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote web app entries
	 */
	public static List<RemoteWebAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the remote web app entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of remote web app entries.
	 *
	 * @return the number of remote web app entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RemoteWebAppEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RemoteWebAppEntryPersistence, RemoteWebAppEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RemoteWebAppEntryPersistence.class);

		ServiceTracker
			<RemoteWebAppEntryPersistence, RemoteWebAppEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<RemoteWebAppEntryPersistence,
						 RemoteWebAppEntryPersistence>(
							 bundle.getBundleContext(),
							 RemoteWebAppEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}