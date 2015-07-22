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

package com.liferay.service.access.control.profile.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.service.access.control.profile.model.SACPEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the s a c p entry service. This utility wraps {@link com.liferay.service.access.control.profile.service.persistence.impl.SACPEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryPersistence
 * @see com.liferay.service.access.control.profile.service.persistence.impl.SACPEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class SACPEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SACPEntry sacpEntry) {
		getPersistence().clearCache(sacpEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SACPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SACPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SACPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SACPEntry update(SACPEntry sacpEntry) {
		return getPersistence().update(sacpEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SACPEntry update(SACPEntry sacpEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(sacpEntry, serviceContext);
	}

	/**
	* Returns all the s a c p entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the s a c p entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByUuid_First(java.lang.String uuid,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByUuid_Last(java.lang.String uuid,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where uuid = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] findByUuid_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(sacpEntryId, uuid, orderByComparator);
	}

	/**
	* Returns all the s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid(java.lang.String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] filterFindByUuid_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(sacpEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the s a c p entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of s a c p entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s a c p entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of s a c p entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public static int filterCountByUuid(java.lang.String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public static List<SACPEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] findByUuid_C_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, long companyId,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(sacpEntryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Returns all the s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] filterFindByUuid_C_PrevAndNext(long sacpEntryId,
		java.lang.String uuid, long companyId,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(sacpEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the s a c p entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of s a c p entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching s a c p entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of s a c p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public static int filterCountByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the s a c p entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s a c p entries
	*/
	public static List<SACPEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the s a c p entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries
	*/
	public static List<SACPEntry> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries
	*/
	public static List<SACPEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByCompanyId_First(long companyId,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByCompanyId_Last(long companyId,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set where companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] findByCompanyId_PrevAndNext(long sacpEntryId,
		long companyId, OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(sacpEntryId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s a c p entries that the user has permission to view
	*/
	public static List<SACPEntry> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the s a c p entries before and after the current s a c p entry in the ordered set of s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param sacpEntryId the primary key of the current s a c p entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry[] filterFindByCompanyId_PrevAndNext(
		long sacpEntryId, long companyId,
		OrderByComparator<SACPEntry> orderByComparator)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(sacpEntryId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the s a c p entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of s a c p entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s a c p entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of s a c p entries that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s a c p entries that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.control.profile.NoSuchEntryException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a matching s a c p entry could not be found
	*/
	public static SACPEntry findByC_N(long companyId, java.lang.String name)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByC_N(long companyId, java.lang.String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the s a c p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static SACPEntry fetchByC_N(long companyId, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the s a c p entry where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the s a c p entry that was removed
	*/
	public static SACPEntry removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of s a c p entries where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching s a c p entries
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the s a c p entry in the entity cache if it is enabled.
	*
	* @param sacpEntry the s a c p entry
	*/
	public static void cacheResult(SACPEntry sacpEntry) {
		getPersistence().cacheResult(sacpEntry);
	}

	/**
	* Caches the s a c p entries in the entity cache if it is enabled.
	*
	* @param sacpEntries the s a c p entries
	*/
	public static void cacheResult(List<SACPEntry> sacpEntries) {
		getPersistence().cacheResult(sacpEntries);
	}

	/**
	* Creates a new s a c p entry with the primary key. Does not add the s a c p entry to the database.
	*
	* @param sacpEntryId the primary key for the new s a c p entry
	* @return the new s a c p entry
	*/
	public static SACPEntry create(long sacpEntryId) {
		return getPersistence().create(sacpEntryId);
	}

	/**
	* Removes the s a c p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry that was removed
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry remove(long sacpEntryId)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().remove(sacpEntryId);
	}

	public static SACPEntry updateImpl(SACPEntry sacpEntry) {
		return getPersistence().updateImpl(sacpEntry);
	}

	/**
	* Returns the s a c p entry with the primary key or throws a {@link com.liferay.service.access.control.profile.NoSuchEntryException} if it could not be found.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry
	* @throws com.liferay.service.access.control.profile.NoSuchEntryException if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry findByPrimaryKey(long sacpEntryId)
		throws com.liferay.service.access.control.profile.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(sacpEntryId);
	}

	/**
	* Returns the s a c p entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry, or <code>null</code> if a s a c p entry with the primary key could not be found
	*/
	public static SACPEntry fetchByPrimaryKey(long sacpEntryId) {
		return getPersistence().fetchByPrimaryKey(sacpEntryId);
	}

	public static java.util.Map<java.io.Serializable, SACPEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the s a c p entries.
	*
	* @return the s a c p entries
	*/
	public static List<SACPEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of s a c p entries
	*/
	public static List<SACPEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s a c p entries
	*/
	public static List<SACPEntry> findAll(int start, int end,
		OrderByComparator<SACPEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the s a c p entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s a c p entries.
	*
	* @return the number of s a c p entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SACPEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(SACPEntryPersistence persistence) {
	}

	private static ServiceTracker<SACPEntryPersistence, SACPEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SACPEntryUtil.class);

		_serviceTracker = new ServiceTracker<SACPEntryPersistence, SACPEntryPersistence>(bundle.getBundleContext(),
				SACPEntryPersistence.class, null);

		_serviceTracker.open();
	}
}