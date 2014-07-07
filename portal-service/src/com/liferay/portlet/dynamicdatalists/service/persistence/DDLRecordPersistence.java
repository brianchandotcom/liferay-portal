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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatalists.model.DDLRecord;

/**
 * The persistence interface for the d d l record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordPersistenceImpl
 * @see DDLRecordUtil
 * @generated
 */
@ProviderType
public interface DDLRecordPersistence extends BasePersistence<DDLRecord> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordUtil} to access the d d l record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d l records where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByUuid_PrevAndNext(
		long recordId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Removes all the d d l records where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d l records where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d l records
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the d d l record where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d l record that was removed
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the number of d d l records where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d l records
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByUuid_C_PrevAndNext(
		long recordId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Removes all the d d l records where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d l records where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d l records
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the d d l records where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the first d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where companyId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByCompanyId_PrevAndNext(
		long recordId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Removes all the d d l records where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of d d l records where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d l records
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the d d l records where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId);

	/**
	* Returns a range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByRecordSetId_First(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByRecordSetId_First(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByRecordSetId_Last(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByRecordSetId_Last(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByRecordSetId_PrevAndNext(
		long recordId, long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Removes all the d d l records where recordSetId = &#63; from the database.
	*
	* @param recordSetId the record set ID
	*/
	public void removeByRecordSetId(long recordSetId);

	/**
	* Returns the number of d d l records where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the number of matching d d l records
	*/
	public int countByRecordSetId(long recordSetId);

	/**
	* Returns all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @return the matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId);

	/**
	* Returns a range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByR_U_First(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByR_U_First(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByR_U_Last(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByR_U_Last(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord[] findByR_U_PrevAndNext(
		long recordId, long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Removes all the d d l records where recordSetId = &#63; and userId = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	*/
	public void removeByR_U(long recordSetId, long userId);

	/**
	* Returns the number of d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @return the number of matching d d l records
	*/
	public int countByR_U(long recordSetId, long userId);

	/**
	* Caches the d d l record in the entity cache if it is enabled.
	*
	* @param ddlRecord the d d l record
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord);

	/**
	* Caches the d d l records in the entity cache if it is enabled.
	*
	* @param ddlRecords the d d l records
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> ddlRecords);

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord create(
		long recordId);

	/**
	* Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord remove(
		long recordId)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord);

	/**
	* Returns the d d l record with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordException} if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord findByPrimaryKey(
		long recordId)
		throws com.liferay.portlet.dynamicdatalists.NoSuchRecordException;

	/**
	* Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchByPrimaryKey(
		long recordId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portlet.dynamicdatalists.model.DDLRecord> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d l records.
	*
	* @return the d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll();

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l records
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecord> orderByComparator);

	/**
	* Removes all the d d l records from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	*/
	public int countAll();
}