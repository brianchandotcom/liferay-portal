/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatalists.model.DDLRecord;

import java.util.List;

/**
 * The persistence utility for the d d l record service. This utility wraps {@link DDLRecordPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordPersistence
 * @see DDLRecordPersistenceImpl
 * @generated
 */
public class DDLRecordUtil {
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
	public static void clearCache(DDLRecord ddlRecord) {
		getPersistence().clearCache(ddlRecord);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecord> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DDLRecord update(DDLRecord ddlRecord, boolean merge)
		throws SystemException {
		return getPersistence().update(ddlRecord, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DDLRecord update(DDLRecord ddlRecord, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ddlRecord, merge, serviceContext);
	}

	/**
	* Caches the d d l record in the entity cache if it is enabled.
	*
	* @param ddlRecord the d d l record
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord) {
		getPersistence().cacheResult(ddlRecord);
	}

	/**
	* Caches the d d l records in the entity cache if it is enabled.
	*
	* @param ddlRecords the d d l records
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> ddlRecords) {
		getPersistence().cacheResult(ddlRecords);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord create(
		long recordId) {
		return getPersistence().create(recordId);
	}

	/**
	* Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord remove(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().remove(recordId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddlRecord, merge);
	}

	/**
	* Returns the d d l record with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByPrimaryKey(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().findByPrimaryKey(recordId);
	}

	/**
	* Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByPrimaryKey(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(recordId);
	}

	/**
	* Returns all the d d l records where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the primary key of the current d d l record
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByUuid_PrevAndNext(
		long recordId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByUuid_PrevAndNext(recordId, uuid, orderByComparator);
	}

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the d d l records where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last d d l record in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the primary key of the current d d l record
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByCompanyId_PrevAndNext(
		long recordId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(recordId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the d d l records where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRecordSetId(recordSetId);
	}

	/**
	* Returns a range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRecordSetId(recordSetId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRecordSetId(recordSetId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByRecordSetId_First(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByRecordSetId_First(recordSetId, orderByComparator);
	}

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByRecordSetId_Last(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByRecordSetId_Last(recordSetId, orderByComparator);
	}

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByRecordSetId_PrevAndNext(
		long recordId, long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByRecordSetId_PrevAndNext(recordId, recordSetId,
			orderByComparator);
	}

	/**
	* Returns all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @return the matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_U(recordSetId, userId);
	}

	/**
	* Returns a range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_U(recordSetId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_U(recordSetId, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByR_U_First(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByR_U_First(recordSetId, userId, orderByComparator);
	}

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord findByR_U_Last(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByR_U_Last(recordSetId, userId, orderByComparator);
	}

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByR_U_PrevAndNext(
		long recordId, long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence()
				   .findByR_U_PrevAndNext(recordId, recordSetId, userId,
			orderByComparator);
	}

	/**
	* Returns all the d d l records.
	*
	* @return the d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d l records where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the d d l record where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d l record that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the d d l records where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the d d l records where recordSetId = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRecordSetId(long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRecordSetId(recordSetId);
	}

	/**
	* Removes all the d d l records where recordSetId = &#63; and userId = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_U(long recordSetId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_U(recordSetId, userId);
	}

	/**
	* Removes all the d d l records from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d l records where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of d d l records where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d l records where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of d d l records where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the number of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRecordSetId(long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRecordSetId(recordSetId);
	}

	/**
	* Returns the number of d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @return the number of matching d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_U(long recordSetId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_U(recordSetId, userId);
	}

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDLRecordPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDLRecordPersistence)PortalBeanLocatorUtil.locate(DDLRecordPersistence.class.getName());

			ReferenceRegistry.registerReference(DDLRecordUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DDLRecordPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DDLRecordUtil.class, "_persistence");
	}

	private static DDLRecordPersistence _persistence;
}