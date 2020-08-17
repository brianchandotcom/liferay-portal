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
import com.liferay.remote.app.model.RemoteAppEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the remote app entry service. This utility wraps <code>com.liferay.remote.app.service.persistence.impl.RemoteAppEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryPersistence
 * @generated
 */
public class RemoteAppEntryUtil {

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
	public static void clearCache(RemoteAppEntry remoteAppEntry) {
		getPersistence().clearCache(remoteAppEntry);
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
	public static Map<Serializable, RemoteAppEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RemoteAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RemoteAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RemoteAppEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RemoteAppEntry update(RemoteAppEntry remoteAppEntry) {
		return getPersistence().update(remoteAppEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RemoteAppEntry update(
		RemoteAppEntry remoteAppEntry, ServiceContext serviceContext) {

		return getPersistence().update(remoteAppEntry, serviceContext);
	}

	/**
	 * Returns all the remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry findByUuid_First(
			String uuid, OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByUuid_First(
		String uuid, OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry findByUuid_Last(
			String uuid, OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteAppEntryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry[] findByUuid_PrevAndNext(
			long remoteAppEntryId, String uuid,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			remoteAppEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the remote app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote app entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	public static List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param remoteAppEntryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry[] findByUuid_C_PrevAndNext(
			long remoteAppEntryId, String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			remoteAppEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the remote app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote app entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry findByC_U(long companyId, String url)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByC_U(companyId, url);
	}

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByC_U(long companyId, String url) {
		return getPersistence().fetchByC_U(companyId, url);
	}

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache) {

		return getPersistence().fetchByC_U(companyId, url, useFinderCache);
	}

	/**
	 * Removes the remote app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote app entry that was removed
	 */
	public static RemoteAppEntry removeByC_U(long companyId, String url)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().removeByC_U(companyId, url);
	}

	/**
	 * Returns the number of remote app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote app entries
	 */
	public static int countByC_U(long companyId, String url) {
		return getPersistence().countByC_U(companyId, url);
	}

	/**
	 * Caches the remote app entry in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntry the remote app entry
	 */
	public static void cacheResult(RemoteAppEntry remoteAppEntry) {
		getPersistence().cacheResult(remoteAppEntry);
	}

	/**
	 * Caches the remote app entries in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntries the remote app entries
	 */
	public static void cacheResult(List<RemoteAppEntry> remoteAppEntries) {
		getPersistence().cacheResult(remoteAppEntries);
	}

	/**
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param remoteAppEntryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	public static RemoteAppEntry create(long remoteAppEntryId) {
		return getPersistence().create(remoteAppEntryId);
	}

	/**
	 * Removes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry remove(long remoteAppEntryId)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().remove(remoteAppEntryId);
	}

	public static RemoteAppEntry updateImpl(RemoteAppEntry remoteAppEntry) {
		return getPersistence().updateImpl(remoteAppEntry);
	}

	/**
	 * Returns the remote app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry findByPrimaryKey(long remoteAppEntryId)
		throws com.liferay.remote.app.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(remoteAppEntryId);
	}

	/**
	 * Returns the remote app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry, or <code>null</code> if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry fetchByPrimaryKey(long remoteAppEntryId) {
		return getPersistence().fetchByPrimaryKey(remoteAppEntryId);
	}

	/**
	 * Returns all the remote app entries.
	 *
	 * @return the remote app entries
	 */
	public static List<RemoteAppEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	public static List<RemoteAppEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote app entries
	 */
	public static List<RemoteAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote app entries
	 */
	public static List<RemoteAppEntry> findAll(
		int start, int end, OrderByComparator<RemoteAppEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the remote app entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RemoteAppEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RemoteAppEntryPersistence, RemoteAppEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RemoteAppEntryPersistence.class);

		ServiceTracker<RemoteAppEntryPersistence, RemoteAppEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<RemoteAppEntryPersistence, RemoteAppEntryPersistence>(
						bundle.getBundleContext(),
						RemoteAppEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}