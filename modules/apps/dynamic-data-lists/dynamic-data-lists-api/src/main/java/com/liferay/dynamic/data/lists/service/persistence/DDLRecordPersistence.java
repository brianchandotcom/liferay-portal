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

import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d l record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordPersistenceImpl
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
	public java.util.List<DDLRecord> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord[] findByUuid_PrevAndNext(long recordId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

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
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRecordException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRecordException;

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the d d l record where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d l record that was removed
	*/
	public DDLRecord removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRecordException;

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
	public java.util.List<DDLRecord> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord[] findByUuid_C_PrevAndNext(long recordId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

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
	public java.util.List<DDLRecord> findByCompanyId(long companyId);

	/**
	* Returns a range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where companyId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord[] findByCompanyId_PrevAndNext(long recordId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

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
	public java.util.List<DDLRecord> findByRecordSetId(long recordSetId);

	/**
	* Returns a range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByRecordSetId(long recordSetId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByRecordSetId(long recordSetId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByRecordSetId(long recordSetId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByRecordSetId_First(long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByRecordSetId_First(long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByRecordSetId_Last(long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByRecordSetId_Last(long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord[] findByRecordSetId_PrevAndNext(long recordId,
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

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
	public java.util.List<DDLRecord> findByR_U(long recordSetId, long userId);

	/**
	* Returns a range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByR_U(long recordSetId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByR_U(long recordSetId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l records
	*/
	public java.util.List<DDLRecord> findByR_U(long recordSetId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByR_U_First(long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByR_U_First(long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record
	* @throws NoSuchRecordException if a matching d d l record could not be found
	*/
	public DDLRecord findByR_U_Last(long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

	/**
	* Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public DDLRecord fetchByR_U_Last(long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	*
	* @param recordId the primary key of the current d d l record
	* @param recordSetId the record set ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord[] findByR_U_PrevAndNext(long recordId, long recordSetId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException;

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
	public void cacheResult(DDLRecord ddlRecord);

	/**
	* Caches the d d l records in the entity cache if it is enabled.
	*
	* @param ddlRecords the d d l records
	*/
	public void cacheResult(java.util.List<DDLRecord> ddlRecords);

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public DDLRecord create(long recordId);

	/**
	* Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord remove(long recordId) throws NoSuchRecordException;

	public DDLRecord updateImpl(DDLRecord ddlRecord);

	/**
	* Returns the d d l record with the primary key or throws a {@link NoSuchRecordException} if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws NoSuchRecordException if a d d l record with the primary key could not be found
	*/
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException;

	/**
	* Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	*/
	public DDLRecord fetchByPrimaryKey(long recordId);

	@Override
	public java.util.Map<java.io.Serializable, DDLRecord> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d l records.
	*
	* @return the d d l records
	*/
	public java.util.List<DDLRecord> findAll();

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	*/
	public java.util.List<DDLRecord> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l records
	*/
	public java.util.List<DDLRecord> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns an ordered range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d l records
	*/
	public java.util.List<DDLRecord> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache);

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

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}