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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CAvailabilityRangeEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the c availability range entry service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CAvailabilityRangeEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CAvailabilityRangeEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryUtil {
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
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		getPersistence().clearCache(cAvailabilityRangeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CAvailabilityRangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CAvailabilityRangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CAvailabilityRangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CAvailabilityRangeEntry update(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return getPersistence().update(cAvailabilityRangeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CAvailabilityRangeEntry update(
		CAvailabilityRangeEntry cAvailabilityRangeEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(cAvailabilityRangeEntry, serviceContext);
	}

	/**
	* Returns all the c availability range entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry[] findByUuid_PrevAndNext(
		long CAvailabilityRangeEntryId, java.lang.String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CAvailabilityRangeEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the c availability range entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of c availability range entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c availability range entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the c availability range entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c availability range entry that was removed
	*/
	public static CAvailabilityRangeEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of c availability range entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c availability range entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry[] findByUuid_C_PrevAndNext(
		long CAvailabilityRangeEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CAvailabilityRangeEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the c availability range entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c availability range entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the c availability range entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByGroupId_First(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByGroupId_Last(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry[] findByGroupId_PrevAndNext(
		long CAvailabilityRangeEntryId, long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CAvailabilityRangeEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the c availability range entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of c availability range entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching c availability range entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByG_C(long groupId,
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByG_C(groupId, CPDefinitionId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByG_C(long groupId,
		long CPDefinitionId) {
		return getPersistence().fetchByG_C(groupId, CPDefinitionId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByG_C(long groupId,
		long CPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C(groupId, CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the c availability range entry that was removed
	*/
	public static CAvailabilityRangeEntry removeByG_C(long groupId,
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().removeByG_C(groupId, CPDefinitionId);
	}

	/**
	* Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63;.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching c availability range entries
	*/
	public static int countByG_C(long groupId, long CPDefinitionId) {
		return getPersistence().countByG_C(groupId, CPDefinitionId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry findByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .findByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId) {
		return getPersistence()
				   .fetchByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId);
	}

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public static CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId, retrieveFromCache);
	}

	/**
	* Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the c availability range entry that was removed
	*/
	public static CAvailabilityRangeEntry removeByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence()
				   .removeByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId);
	}

	/**
	* Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63;.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the number of matching c availability range entries
	*/
	public static int countByG_C_C(long groupId, long CPDefinitionId,
		long commerceAvailabilityRangeId) {
		return getPersistence()
				   .countByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId);
	}

	/**
	* Caches the c availability range entry in the entity cache if it is enabled.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	*/
	public static void cacheResult(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		getPersistence().cacheResult(cAvailabilityRangeEntry);
	}

	/**
	* Caches the c availability range entries in the entity cache if it is enabled.
	*
	* @param cAvailabilityRangeEntries the c availability range entries
	*/
	public static void cacheResult(
		List<CAvailabilityRangeEntry> cAvailabilityRangeEntries) {
		getPersistence().cacheResult(cAvailabilityRangeEntries);
	}

	/**
	* Creates a new c availability range entry with the primary key. Does not add the c availability range entry to the database.
	*
	* @param CAvailabilityRangeEntryId the primary key for the new c availability range entry
	* @return the new c availability range entry
	*/
	public static CAvailabilityRangeEntry create(long CAvailabilityRangeEntryId) {
		return getPersistence().create(CAvailabilityRangeEntryId);
	}

	/**
	* Removes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry that was removed
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry remove(long CAvailabilityRangeEntryId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().remove(CAvailabilityRangeEntryId);
	}

	public static CAvailabilityRangeEntry updateImpl(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return getPersistence().updateImpl(cAvailabilityRangeEntry);
	}

	/**
	* Returns the c availability range entry with the primary key or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry findByPrimaryKey(
		long CAvailabilityRangeEntryId)
		throws com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException {
		return getPersistence().findByPrimaryKey(CAvailabilityRangeEntryId);
	}

	/**
	* Returns the c availability range entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry, or <code>null</code> if a c availability range entry with the primary key could not be found
	*/
	public static CAvailabilityRangeEntry fetchByPrimaryKey(
		long CAvailabilityRangeEntryId) {
		return getPersistence().fetchByPrimaryKey(CAvailabilityRangeEntryId);
	}

	public static java.util.Map<java.io.Serializable, CAvailabilityRangeEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the c availability range entries.
	*
	* @return the c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findAll(int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c availability range entries
	*/
	public static List<CAvailabilityRangeEntry> findAll(int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the c availability range entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of c availability range entries.
	*
	* @return the number of c availability range entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CAvailabilityRangeEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CAvailabilityRangeEntryPersistence, CAvailabilityRangeEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CAvailabilityRangeEntryPersistence.class);
}