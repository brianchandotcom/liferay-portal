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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
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
 * The persistence utility for the ddm form instance record service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMFormInstanceRecordPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordPersistence
 * @generated
 */
public class DDMFormInstanceRecordUtil {

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
	public static void clearCache(DDMFormInstanceRecord ddmFormInstanceRecord) {
		getPersistence().clearCache(ddmFormInstanceRecord);
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
	public static Map<Serializable, DDMFormInstanceRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMFormInstanceRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMFormInstanceRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMFormInstanceRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMFormInstanceRecord update(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return getPersistence().update(ddmFormInstanceRecord);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMFormInstanceRecord update(
		DDMFormInstanceRecord ddmFormInstanceRecord,
		ServiceContext serviceContext) {

		return getPersistence().update(ddmFormInstanceRecord, serviceContext);
	}

	/**
	 * Returns all the ddm form instance records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByUuid_First(
			String uuid,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUuid_First(
		String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByUuid_Last(
			String uuid,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUuid_Last(
		String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByUuid_PrevAndNext(
			long formInstanceRecordId, String uuid,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_PrevAndNext(
			formInstanceRecordId, uuid, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm form instance records
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFormInstanceRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByUUID_G(String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the ddm form instance record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm form instance record that was removed
	 */
	public static DDMFormInstanceRecord removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm form instance records
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByUuid_C_PrevAndNext(
			long formInstanceRecordId, String uuid, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByUuid_C_PrevAndNext(
			formInstanceRecordId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm form instance records
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the ddm form instance records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByCompanyId_First(
			long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByCompanyId_First(
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByCompanyId_Last(
			long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByCompanyId_PrevAndNext(
			long formInstanceRecordId, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByCompanyId_PrevAndNext(
			formInstanceRecordId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ddm form instance records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddm form instance records
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId) {

		return getPersistence().findByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByFormInstanceId_First(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByFormInstanceId_First(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByFormInstanceId_First(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByFormInstanceId_Last(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByFormInstanceId_Last(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByFormInstanceId_Last(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByFormInstanceId_PrevAndNext(
			long formInstanceRecordId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByFormInstanceId_PrevAndNext(
			formInstanceRecordId, formInstanceId, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 */
	public static void removeByFormInstanceId(long formInstanceId) {
		getPersistence().removeByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns the number of ddm form instance records where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance records
	 */
	public static int countByFormInstanceId(long formInstanceId) {
		return getPersistence().countByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId) {

		return getPersistence().findByU_F(userId, formInstanceId);
	}

	/**
	 * Returns a range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end) {

		return getPersistence().findByU_F(userId, formInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByU_F(
			userId, formInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_F(
			userId, formInstanceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByU_F_First(
			long userId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByU_F_First(
			userId, formInstanceId, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByU_F_First(
		long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByU_F_First(
			userId, formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByU_F_Last(
			long userId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByU_F_Last(
			userId, formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByU_F_Last(
		long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByU_F_Last(
			userId, formInstanceId, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByU_F_PrevAndNext(
			long formInstanceRecordId, long userId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByU_F_PrevAndNext(
			formInstanceRecordId, userId, formInstanceId, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where userId = &#63; and formInstanceId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 */
	public static void removeByU_F(long userId, long formInstanceId) {
		getPersistence().removeByU_F(userId, formInstanceId);
	}

	/**
	 * Returns the number of ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance records
	 */
	public static int countByU_F(long userId, long formInstanceId) {
		return getPersistence().countByU_F(userId, formInstanceId);
	}

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion) {

		return getPersistence().findByF_F(formInstanceId, formInstanceVersion);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end) {

		return getPersistence().findByF_F(
			formInstanceId, formInstanceVersion, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findByF_F(
			formInstanceId, formInstanceVersion, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByF_F(
			formInstanceId, formInstanceVersion, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByF_F_First(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByF_F_First(
			formInstanceId, formInstanceVersion, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByF_F_First(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByF_F_First(
			formInstanceId, formInstanceVersion, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord findByF_F_Last(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByF_F_Last(
			formInstanceId, formInstanceVersion, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord fetchByF_F_Last(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().fetchByF_F_Last(
			formInstanceId, formInstanceVersion, orderByComparator);
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord[] findByF_F_PrevAndNext(
			long formInstanceRecordId, long formInstanceId,
			String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByF_F_PrevAndNext(
			formInstanceRecordId, formInstanceId, formInstanceVersion,
			orderByComparator);
	}

	/**
	 * Removes all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 */
	public static void removeByF_F(
		long formInstanceId, String formInstanceVersion) {

		getPersistence().removeByF_F(formInstanceId, formInstanceVersion);
	}

	/**
	 * Returns the number of ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the number of matching ddm form instance records
	 */
	public static int countByF_F(
		long formInstanceId, String formInstanceVersion) {

		return getPersistence().countByF_F(formInstanceId, formInstanceVersion);
	}

	/**
	 * Caches the ddm form instance record in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 */
	public static void cacheResult(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		getPersistence().cacheResult(ddmFormInstanceRecord);
	}

	/**
	 * Caches the ddm form instance records in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecords the ddm form instance records
	 */
	public static void cacheResult(
		List<DDMFormInstanceRecord> ddmFormInstanceRecords) {

		getPersistence().cacheResult(ddmFormInstanceRecords);
	}

	/**
	 * Creates a new ddm form instance record with the primary key. Does not add the ddm form instance record to the database.
	 *
	 * @param formInstanceRecordId the primary key for the new ddm form instance record
	 * @return the new ddm form instance record
	 */
	public static DDMFormInstanceRecord create(long formInstanceRecordId) {
		return getPersistence().create(formInstanceRecordId);
	}

	/**
	 * Removes the ddm form instance record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record that was removed
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord remove(long formInstanceRecordId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().remove(formInstanceRecordId);
	}

	public static DDMFormInstanceRecord updateImpl(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return getPersistence().updateImpl(ddmFormInstanceRecord);
	}

	/**
	 * Returns the ddm form instance record with the primary key or throws a <code>NoSuchFormInstanceRecordException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord findByPrimaryKey(
			long formInstanceRecordId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceRecordException {

		return getPersistence().findByPrimaryKey(formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record, or <code>null</code> if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord fetchByPrimaryKey(
		long formInstanceRecordId) {

		return getPersistence().fetchByPrimaryKey(formInstanceRecordId);
	}

	/**
	 * Returns all the ddm form instance records.
	 *
	 * @return the ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance records
	 */
	public static List<DDMFormInstanceRecord> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm form instance records from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm form instance records.
	 *
	 * @return the number of ddm form instance records
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMFormInstanceRecordPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMFormInstanceRecordPersistence, DDMFormInstanceRecordPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMFormInstanceRecordPersistence.class);

		ServiceTracker
			<DDMFormInstanceRecordPersistence, DDMFormInstanceRecordPersistence>
				serviceTracker =
					new ServiceTracker
						<DDMFormInstanceRecordPersistence,
						 DDMFormInstanceRecordPersistence>(
							 bundle.getBundleContext(),
							 DDMFormInstanceRecordPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}