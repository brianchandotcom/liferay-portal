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

package com.liferay.dynamic.data.lists.service.persistence;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ddl record service. This utility wraps <code>com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordPersistence
 * @generated
 */
public class DDLRecordUtil {

	/**
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
	public static void clearCache(DDLRecord ddlRecord) {
		getPersistence().clearCache(ddlRecord);
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
	public static Map<Serializable, DDLRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDLRecord update(DDLRecord ddlRecord) {
		return getPersistence().update(ddlRecord);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDLRecord update(
		DDLRecord ddlRecord, ServiceContext serviceContext) {

		return getPersistence().update(ddlRecord, serviceContext);
	}

	/**
	 * Returns all the ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByUuid_First(
			String uuid, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUuid_First(
		String uuid, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByUuid_Last(
			String uuid, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUuid_Last(
		String uuid, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByUuid_PrevAndNext(
			long recordId, String uuid,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_PrevAndNext(
			recordId, uuid, orderByComparator);
	}

	/**
	 * Removes all the ddl records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddl records
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByUUID_G(String uuid, long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the ddl record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddl record that was removed
	 */
	public static DDLRecord removeByUUID_G(String uuid, long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of ddl records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddl records
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByUuid_C_PrevAndNext(
			long recordId, String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByUuid_C_PrevAndNext(
			recordId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddl records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByCompanyId_First(
			long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByCompanyId_First(
		long companyId, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByCompanyId_Last(
			long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByCompanyId_Last(
		long companyId, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByCompanyId_PrevAndNext(
			long recordId, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByCompanyId_PrevAndNext(
			recordId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddl records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByRecordSetId(long recordSetId) {
		return getPersistence().findByRecordSetId(recordSetId);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end) {

		return getPersistence().findByRecordSetId(recordSetId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByRecordSetId(
			recordSetId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRecordSetId(
			recordSetId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByRecordSetId_First(
			long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByRecordSetId_First(
			recordSetId, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByRecordSetId_First(
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByRecordSetId_First(
			recordSetId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByRecordSetId_Last(
			long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByRecordSetId_Last(
			recordSetId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByRecordSetId_Last(
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByRecordSetId_Last(
			recordSetId, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByRecordSetId_PrevAndNext(
			long recordId, long recordSetId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByRecordSetId_PrevAndNext(
			recordId, recordSetId, orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	public static void removeByRecordSetId(long recordSetId) {
		getPersistence().removeByRecordSetId(recordSetId);
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching ddl records
	 */
	public static int countByRecordSetId(long recordSetId) {
		return getPersistence().countByRecordSetId(recordSetId);
	}

	/**
	 * Returns all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByR_U(long recordSetId, long userId) {
		return getPersistence().findByR_U(recordSetId, userId);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end) {

		return getPersistence().findByR_U(recordSetId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByR_U(
			recordSetId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_U(
			recordSetId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByR_U_First(
			long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_U_First(
			recordSetId, userId, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByR_U_First(
		long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByR_U_First(
			recordSetId, userId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByR_U_Last(
			long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_U_Last(
			recordSetId, userId, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByR_U_Last(
		long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByR_U_Last(
			recordSetId, userId, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByR_U_PrevAndNext(
			long recordId, long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_U_PrevAndNext(
			recordId, recordSetId, userId, orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 */
	public static void removeByR_U(long recordSetId, long userId) {
		getPersistence().removeByR_U(recordSetId, userId);
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching ddl records
	 */
	public static int countByR_U(long recordSetId, long userId) {
		return getPersistence().countByR_U(recordSetId, userId);
	}

	/**
	 * Returns all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the matching ddl records
	 */
	public static List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion) {

		return getPersistence().findByR_R(recordSetId, recordSetVersion);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	public static List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end) {

		return getPersistence().findByR_R(
			recordSetId, recordSetVersion, start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findByR_R(
			recordSetId, recordSetVersion, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	public static List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_R(
			recordSetId, recordSetVersion, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByR_R_First(
			long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_R_First(
			recordSetId, recordSetVersion, orderByComparator);
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByR_R_First(
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByR_R_First(
			recordSetId, recordSetVersion, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public static DDLRecord findByR_R_Last(
			long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_R_Last(
			recordSetId, recordSetVersion, orderByComparator);
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public static DDLRecord fetchByR_R_Last(
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().fetchByR_R_Last(
			recordSetId, recordSetVersion, orderByComparator);
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord[] findByR_R_PrevAndNext(
			long recordId, long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByR_R_PrevAndNext(
			recordId, recordSetId, recordSetVersion, orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 */
	public static void removeByR_R(long recordSetId, String recordSetVersion) {
		getPersistence().removeByR_R(recordSetId, recordSetVersion);
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the number of matching ddl records
	 */
	public static int countByR_R(long recordSetId, String recordSetVersion) {
		return getPersistence().countByR_R(recordSetId, recordSetVersion);
	}

	/**
	 * Caches the ddl record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the ddl record
	 */
	public static void cacheResult(DDLRecord ddlRecord) {
		getPersistence().cacheResult(ddlRecord);
	}

	/**
	 * Caches the ddl records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the ddl records
	 */
	public static void cacheResult(List<DDLRecord> ddlRecords) {
		getPersistence().cacheResult(ddlRecords);
	}

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	public static DDLRecord create(long recordId) {
		return getPersistence().create(recordId);
	}

	/**
	 * Removes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord remove(long recordId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().remove(recordId);
	}

	public static DDLRecord updateImpl(DDLRecord ddlRecord) {
		return getPersistence().updateImpl(ddlRecord);
	}

	/**
	 * Returns the ddl record with the primary key or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public static DDLRecord findByPrimaryKey(long recordId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordException {

		return getPersistence().findByPrimaryKey(recordId);
	}

	/**
	 * Returns the ddl record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record, or <code>null</code> if a ddl record with the primary key could not be found
	 */
	public static DDLRecord fetchByPrimaryKey(long recordId) {
		return getPersistence().fetchByPrimaryKey(recordId);
	}

	/**
	 * Returns all the ddl records.
	 *
	 * @return the ddl records
	 */
	public static List<DDLRecord> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of ddl records
	 */
	public static List<DDLRecord> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl records
	 */
	public static List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddl records
	 */
	public static List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddl records from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDLRecordPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordPersistence, DDLRecordPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDLRecordPersistence.class);

		ServiceTracker<DDLRecordPersistence, DDLRecordPersistence>
			serviceTracker =
				new ServiceTracker<DDLRecordPersistence, DDLRecordPersistence>(
					bundle.getBundleContext(), DDLRecordPersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}