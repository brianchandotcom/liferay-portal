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

import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddl record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordUtil
 * @generated
 */
@ProviderType
public interface DDLRecordPersistence extends BasePersistence<DDLRecord> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordUtil} to access the ddl record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByUuid(String uuid);

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
	public java.util.List<DDLRecord> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public DDLRecord[] findByUuid_PrevAndNext(
			long recordId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddl records
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException;

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the ddl record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddl record that was removed
	 */
	public DDLRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException;

	/**
	 * Returns the number of ddl records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddl records
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByUuid_C(String uuid, long companyId);

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
	public java.util.List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public DDLRecord[] findByUuid_C_PrevAndNext(
			long recordId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByCompanyId(long companyId);

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
	public java.util.List<DDLRecord> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public DDLRecord[] findByCompanyId_PrevAndNext(
			long recordId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByRecordSetId(long recordSetId);

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
	public java.util.List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end);

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
	public java.util.List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByRecordSetId_First(
			long recordSetId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByRecordSetId_First(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByRecordSetId_Last(
			long recordSetId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByRecordSetId_Last(
		long recordSetId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public DDLRecord[] findByRecordSetId_PrevAndNext(
			long recordId, long recordSetId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	public void removeByRecordSetId(long recordSetId);

	/**
	 * Returns the number of ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching ddl records
	 */
	public int countByRecordSetId(long recordSetId);

	/**
	 * Returns all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByR_U(long recordSetId, long userId);

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
	public java.util.List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end);

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
	public java.util.List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByR_U_First(
			long recordSetId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByR_U_First(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByR_U_Last(
			long recordSetId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByR_U_Last(
		long recordSetId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public DDLRecord[] findByR_U_PrevAndNext(
			long recordId, long recordSetId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 */
	public void removeByR_U(long recordSetId, long userId);

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching ddl records
	 */
	public int countByR_U(long recordSetId, long userId);

	/**
	 * Returns all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the matching ddl records
	 */
	public java.util.List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion);

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
	public java.util.List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end);

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
	public java.util.List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByR_R_First(
			long recordSetId, String recordSetVersion,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByR_R_First(
		long recordSetId, String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	public DDLRecord findByR_R_Last(
			long recordSetId, String recordSetVersion,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	public DDLRecord fetchByR_R_Last(
		long recordSetId, String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public DDLRecord[] findByR_R_PrevAndNext(
			long recordId, long recordSetId, String recordSetVersion,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
				orderByComparator)
		throws NoSuchRecordException;

	/**
	 * Removes all the ddl records where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 */
	public void removeByR_R(long recordSetId, String recordSetVersion);

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the number of matching ddl records
	 */
	public int countByR_R(long recordSetId, String recordSetVersion);

	/**
	 * Caches the ddl record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the ddl record
	 */
	public void cacheResult(DDLRecord ddlRecord);

	/**
	 * Caches the ddl records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the ddl records
	 */
	public void cacheResult(java.util.List<DDLRecord> ddlRecords);

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	public DDLRecord create(long recordId);

	/**
	 * Removes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public DDLRecord remove(long recordId) throws NoSuchRecordException;

	public DDLRecord updateImpl(DDLRecord ddlRecord);

	/**
	 * Returns the ddl record with the primary key or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException;

	/**
	 * Returns the ddl record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record, or <code>null</code> if a ddl record with the primary key could not be found
	 */
	public DDLRecord fetchByPrimaryKey(long recordId);

	/**
	 * Returns all the ddl records.
	 *
	 * @return the ddl records
	 */
	public java.util.List<DDLRecord> findAll();

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
	public java.util.List<DDLRecord> findAll(int start, int end);

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
	public java.util.List<DDLRecord> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator);

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
	public java.util.List<DDLRecord> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecord>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddl records from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	public int countAll();

}