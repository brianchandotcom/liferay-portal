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

package com.liferay.remote.app.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.app.model.RemoteCustomElementEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the remote custom element entry service. This utility wraps <code>com.liferay.remote.app.service.persistence.impl.RemoteCustomElementEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteCustomElementEntryPersistence
 * @generated
 */
public class RemoteCustomElementEntryUtil {

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
		RemoteCustomElementEntry remoteCustomElementEntry) {

		getPersistence().clearCache(remoteCustomElementEntry);
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
	public static Map<Serializable, RemoteCustomElementEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RemoteCustomElementEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RemoteCustomElementEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RemoteCustomElementEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RemoteCustomElementEntry update(
		RemoteCustomElementEntry remoteCustomElementEntry) {

		return getPersistence().update(remoteCustomElementEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RemoteCustomElementEntry update(
		RemoteCustomElementEntry remoteCustomElementEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			remoteCustomElementEntry, serviceContext);
	}

	/**
	 * Returns all the remote custom element entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry findByUuid_First(
			String uuid,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry findByUuid_Last(
			String uuid,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the remote custom element entries before and after the current remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteCustomElementEntryId the primary key of the current remote custom element entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public static RemoteCustomElementEntry[] findByUuid_PrevAndNext(
			long remoteCustomElementEntryId, String uuid,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			remoteCustomElementEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the remote custom element entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of remote custom element entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote custom element entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the remote custom element entries before and after the current remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param remoteCustomElementEntryId the primary key of the current remote custom element entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public static RemoteCustomElementEntry[] findByUuid_C_PrevAndNext(
			long remoteCustomElementEntryId, String uuid, long companyId,
			OrderByComparator<RemoteCustomElementEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			remoteCustomElementEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the remote custom element entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote custom element entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchRemoteCustomElementEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry findByC_U(long companyId, String url)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByC_U(companyId, url);
	}

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByC_U(
		long companyId, String url) {

		return getPersistence().fetchByC_U(companyId, url);
	}

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public static RemoteCustomElementEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache) {

		return getPersistence().fetchByC_U(companyId, url, useFinderCache);
	}

	/**
	 * Removes the remote custom element entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote custom element entry that was removed
	 */
	public static RemoteCustomElementEntry removeByC_U(
			long companyId, String url)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().removeByC_U(companyId, url);
	}

	/**
	 * Returns the number of remote custom element entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote custom element entries
	 */
	public static int countByC_U(long companyId, String url) {
		return getPersistence().countByC_U(companyId, url);
	}

	/**
	 * Caches the remote custom element entry in the entity cache if it is enabled.
	 *
	 * @param remoteCustomElementEntry the remote custom element entry
	 */
	public static void cacheResult(
		RemoteCustomElementEntry remoteCustomElementEntry) {

		getPersistence().cacheResult(remoteCustomElementEntry);
	}

	/**
	 * Caches the remote custom element entries in the entity cache if it is enabled.
	 *
	 * @param remoteCustomElementEntries the remote custom element entries
	 */
	public static void cacheResult(
		List<RemoteCustomElementEntry> remoteCustomElementEntries) {

		getPersistence().cacheResult(remoteCustomElementEntries);
	}

	/**
	 * Creates a new remote custom element entry with the primary key. Does not add the remote custom element entry to the database.
	 *
	 * @param remoteCustomElementEntryId the primary key for the new remote custom element entry
	 * @return the new remote custom element entry
	 */
	public static RemoteCustomElementEntry create(
		long remoteCustomElementEntryId) {

		return getPersistence().create(remoteCustomElementEntryId);
	}

	/**
	 * Removes the remote custom element entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry that was removed
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public static RemoteCustomElementEntry remove(
			long remoteCustomElementEntryId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().remove(remoteCustomElementEntryId);
	}

	public static RemoteCustomElementEntry updateImpl(
		RemoteCustomElementEntry remoteCustomElementEntry) {

		return getPersistence().updateImpl(remoteCustomElementEntry);
	}

	/**
	 * Returns the remote custom element entry with the primary key or throws a <code>NoSuchRemoteCustomElementEntryException</code> if it could not be found.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public static RemoteCustomElementEntry findByPrimaryKey(
			long remoteCustomElementEntryId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteCustomElementEntryException {

		return getPersistence().findByPrimaryKey(remoteCustomElementEntryId);
	}

	/**
	 * Returns the remote custom element entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry, or <code>null</code> if a remote custom element entry with the primary key could not be found
	 */
	public static RemoteCustomElementEntry fetchByPrimaryKey(
		long remoteCustomElementEntryId) {

		return getPersistence().fetchByPrimaryKey(remoteCustomElementEntryId);
	}

	/**
	 * Returns all the remote custom element entries.
	 *
	 * @return the remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote custom element entries
	 */
	public static List<RemoteCustomElementEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the remote custom element entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of remote custom element entries.
	 *
	 * @return the number of remote custom element entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RemoteCustomElementEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RemoteCustomElementEntryPersistence,
		 RemoteCustomElementEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RemoteCustomElementEntryPersistence.class);

		ServiceTracker
			<RemoteCustomElementEntryPersistence,
			 RemoteCustomElementEntryPersistence> serviceTracker =
				new ServiceTracker
					<RemoteCustomElementEntryPersistence,
					 RemoteCustomElementEntryPersistence>(
						 bundle.getBundleContext(),
						 RemoteCustomElementEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}