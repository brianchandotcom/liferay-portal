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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d l record set service. This utility wraps {@link com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordSetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetPersistence
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordSetPersistenceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetUtil {
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
	public static void clearCache(DDLRecordSet ddlRecordSet) {
		getPersistence().clearCache(ddlRecordSet);
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
	public static List<DDLRecordSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecordSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecordSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDLRecordSet update(DDLRecordSet ddlRecordSet) {
		return getPersistence().update(ddlRecordSet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDLRecordSet update(DDLRecordSet ddlRecordSet,
		ServiceContext serviceContext) {
		return getPersistence().update(ddlRecordSet, serviceContext);
	}

	/**
	* Returns all the d d l record sets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d l record sets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record sets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDLRecordSet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d l record set in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d l record set in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d l record sets before and after the current d d l record set in the ordered set where uuid = &#63;.
	*
	* @param recordSetId the primary key of the current d d l record set
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet[] findByUuid_PrevAndNext(long recordSetId,
		java.lang.String uuid, OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .findByUuid_PrevAndNext(recordSetId, uuid, orderByComparator);
	}

	/**
	* Removes all the d d l record sets where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d l record sets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d l record sets
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the d d l record set where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordSetException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d l record set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d l record set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the d d l record set where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d l record set that was removed
	*/
	public static DDLRecordSet removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d l record sets where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d l record sets
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the d d l record sets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d l record sets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record sets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d l record set in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d l record set in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d l record sets before and after the current d d l record set in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param recordSetId the primary key of the current d d l record set
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet[] findByUuid_C_PrevAndNext(long recordSetId,
		java.lang.String uuid, long companyId,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(recordSetId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d l record sets where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d l record sets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d l record sets
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the d d l record sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d l record sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d l record set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByGroupId_First(long groupId,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first d d l record set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByGroupId_First(long groupId,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByGroupId_Last(long groupId,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last d d l record set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByGroupId_Last(long groupId,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the d d l record sets before and after the current d d l record set in the ordered set where groupId = &#63;.
	*
	* @param recordSetId the primary key of the current d d l record set
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet[] findByGroupId_PrevAndNext(long recordSetId,
		long groupId, OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(recordSetId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d l record sets that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d l record sets that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the d d l record sets before and after the current d d l record set in the ordered set of d d l record sets that the user has permission to view where groupId = &#63;.
	*
	* @param recordSetId the primary key of the current d d l record set
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet[] filterFindByGroupId_PrevAndNext(
		long recordSetId, long groupId,
		OrderByComparator<DDLRecordSet> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(recordSetId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d l record sets that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long[] groupIds) {
		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	* Returns a range of all the d d l record sets that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets that the user has permission to view
	*/
	public static List<DDLRecordSet> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns all the d d l record sets where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the d d l record sets where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long[] groupIds, int start,
		int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record sets where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record sets
	*/
	public static List<DDLRecordSet> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the d d l record sets where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of d d l record sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d l record sets
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of d d l record sets where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d l record sets
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the number of d d l record sets that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d l record sets that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of d d l record sets that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d l record sets that the user has permission to view
	*/
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	* Returns the d d l record set where groupId = &#63; and recordSetKey = &#63; or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordSetException} if it could not be found.
	*
	* @param groupId the group ID
	* @param recordSetKey the record set key
	* @return the matching d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a matching d d l record set could not be found
	*/
	public static DDLRecordSet findByG_R(long groupId,
		java.lang.String recordSetKey)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByG_R(groupId, recordSetKey);
	}

	/**
	* Returns the d d l record set where groupId = &#63; and recordSetKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param recordSetKey the record set key
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByG_R(long groupId,
		java.lang.String recordSetKey) {
		return getPersistence().fetchByG_R(groupId, recordSetKey);
	}

	/**
	* Returns the d d l record set where groupId = &#63; and recordSetKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param recordSetKey the record set key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static DDLRecordSet fetchByG_R(long groupId,
		java.lang.String recordSetKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_R(groupId, recordSetKey, retrieveFromCache);
	}

	/**
	* Removes the d d l record set where groupId = &#63; and recordSetKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param recordSetKey the record set key
	* @return the d d l record set that was removed
	*/
	public static DDLRecordSet removeByG_R(long groupId,
		java.lang.String recordSetKey)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().removeByG_R(groupId, recordSetKey);
	}

	/**
	* Returns the number of d d l record sets where groupId = &#63; and recordSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param recordSetKey the record set key
	* @return the number of matching d d l record sets
	*/
	public static int countByG_R(long groupId, java.lang.String recordSetKey) {
		return getPersistence().countByG_R(groupId, recordSetKey);
	}

	/**
	* Caches the d d l record set in the entity cache if it is enabled.
	*
	* @param ddlRecordSet the d d l record set
	*/
	public static void cacheResult(DDLRecordSet ddlRecordSet) {
		getPersistence().cacheResult(ddlRecordSet);
	}

	/**
	* Caches the d d l record sets in the entity cache if it is enabled.
	*
	* @param ddlRecordSets the d d l record sets
	*/
	public static void cacheResult(List<DDLRecordSet> ddlRecordSets) {
		getPersistence().cacheResult(ddlRecordSets);
	}

	/**
	* Creates a new d d l record set with the primary key. Does not add the d d l record set to the database.
	*
	* @param recordSetId the primary key for the new d d l record set
	* @return the new d d l record set
	*/
	public static DDLRecordSet create(long recordSetId) {
		return getPersistence().create(recordSetId);
	}

	/**
	* Removes the d d l record set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set that was removed
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet remove(long recordSetId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().remove(recordSetId);
	}

	public static DDLRecordSet updateImpl(DDLRecordSet ddlRecordSet) {
		return getPersistence().updateImpl(ddlRecordSet);
	}

	/**
	* Returns the d d l record set with the primary key or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordSetException} if it could not be found.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordSetException if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet findByPrimaryKey(long recordSetId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException {
		return getPersistence().findByPrimaryKey(recordSetId);
	}

	/**
	* Returns the d d l record set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set, or <code>null</code> if a d d l record set with the primary key could not be found
	*/
	public static DDLRecordSet fetchByPrimaryKey(long recordSetId) {
		return getPersistence().fetchByPrimaryKey(recordSetId);
	}

	public static java.util.Map<java.io.Serializable, DDLRecordSet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d l record sets.
	*
	* @return the d d l record sets
	*/
	public static List<DDLRecordSet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of d d l record sets
	*/
	public static List<DDLRecordSet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l record sets
	*/
	public static List<DDLRecordSet> findAll(int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d l record sets
	*/
	public static List<DDLRecordSet> findAll(int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d l record sets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d l record sets.
	*
	* @return the number of d d l record sets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDLRecordSetPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordSetPersistence, DDLRecordSetPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordSetPersistence.class);
}