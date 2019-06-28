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

import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddl record set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetUtil
 * @generated
 */
@ProviderType
public interface DDLRecordSetPersistence extends BasePersistence<DDLRecordSet> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordSetUtil} to access the ddl record set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddl record sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid(String uuid);

	/**
	 * Returns a range of all the ddl record sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ddl record set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the first ddl record set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the last ddl record set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the last ddl record set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the ddl record sets before and after the current ddl record set in the ordered set where uuid = &#63;.
	 *
	 * @param recordSetId the primary key of the current ddl record set
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet[] findByUuid_PrevAndNext(
			long recordSetId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Removes all the ddl record sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of ddl record sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddl record sets
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the ddl record set where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRecordSetException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordSetException;

	/**
	 * Returns the ddl record set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the ddl record set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the ddl record set where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddl record set that was removed
	 */
	public DDLRecordSet removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordSetException;

	/**
	 * Returns the number of ddl record sets where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddl record sets
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the ddl record sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the ddl record sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ddl record set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the first ddl record set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the last ddl record set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the last ddl record set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the ddl record sets before and after the current ddl record set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param recordSetId the primary key of the current ddl record set
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet[] findByUuid_C_PrevAndNext(
			long recordSetId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Removes all the ddl record sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of ddl record sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddl record sets
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the ddl record sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(long groupId);

	/**
	 * Returns a range of all the ddl record sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ddl record set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the first ddl record set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the last ddl record set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the last ddl record set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the ddl record sets before and after the current ddl record set in the ordered set where groupId = &#63;.
	 *
	 * @param recordSetId the primary key of the current ddl record set
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet[] findByGroupId_PrevAndNext(
			long recordSetId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns all the ddl record sets that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the ddl record sets that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the ddl record sets before and after the current ddl record set in the ordered set of ddl record sets that the user has permission to view where groupId = &#63;.
	 *
	 * @param recordSetId the primary key of the current ddl record set
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet[] filterFindByGroupId_PrevAndNext(
			long recordSetId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns all the ddl record sets that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the ddl record sets that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets that the user has permission to view
	 */
	public java.util.List<DDLRecordSet> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns all the ddl record sets where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the ddl record sets where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the ddl record sets where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of ddl record sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddl record sets
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of ddl record sets where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddl record sets
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of ddl record sets that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddl record sets that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of ddl record sets that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching ddl record sets that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Returns all the ddl record sets where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long DDMStructureId);

	/**
	 * Returns a range of all the ddl record sets where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long DDMStructureId, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long DDMStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long DDMStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ddl record set in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByDDMStructureId_First(
			long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the first ddl record set in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByDDMStructureId_First(
		long DDMStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the last ddl record set in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByDDMStructureId_Last(
			long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns the last ddl record set in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByDDMStructureId_Last(
		long DDMStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns the ddl record sets before and after the current ddl record set in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param recordSetId the primary key of the current ddl record set
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet[] findByDDMStructureId_PrevAndNext(
			long recordSetId, long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
				orderByComparator)
		throws NoSuchRecordSetException;

	/**
	 * Returns all the ddl record sets where DDMStructureId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureIds the ddm structure IDs
	 * @return the matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long[] DDMStructureIds);

	/**
	 * Returns a range of all the ddl record sets where DDMStructureId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureIds the ddm structure IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long[] DDMStructureIds, int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets where DDMStructureId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureIds the ddm structure IDs
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long[] DDMStructureIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets where DDMStructureId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record sets
	 */
	public java.util.List<DDLRecordSet> findByDDMStructureId(
		long[] DDMStructureIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the ddl record sets where DDMStructureId = &#63; from the database.
	 *
	 * @param DDMStructureId the ddm structure ID
	 */
	public void removeByDDMStructureId(long DDMStructureId);

	/**
	 * Returns the number of ddl record sets where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching ddl record sets
	 */
	public int countByDDMStructureId(long DDMStructureId);

	/**
	 * Returns the number of ddl record sets where DDMStructureId = any &#63;.
	 *
	 * @param DDMStructureIds the ddm structure IDs
	 * @return the number of matching ddl record sets
	 */
	public int countByDDMStructureId(long[] DDMStructureIds);

	/**
	 * Returns the ddl record set where groupId = &#63; and recordSetKey = &#63; or throws a <code>NoSuchRecordSetException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param recordSetKey the record set key
	 * @return the matching ddl record set
	 * @throws NoSuchRecordSetException if a matching ddl record set could not be found
	 */
	public DDLRecordSet findByG_R(long groupId, String recordSetKey)
		throws NoSuchRecordSetException;

	/**
	 * Returns the ddl record set where groupId = &#63; and recordSetKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param recordSetKey the record set key
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByG_R(long groupId, String recordSetKey);

	/**
	 * Returns the ddl record set where groupId = &#63; and recordSetKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param recordSetKey the record set key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public DDLRecordSet fetchByG_R(
		long groupId, String recordSetKey, boolean retrieveFromCache);

	/**
	 * Removes the ddl record set where groupId = &#63; and recordSetKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param recordSetKey the record set key
	 * @return the ddl record set that was removed
	 */
	public DDLRecordSet removeByG_R(long groupId, String recordSetKey)
		throws NoSuchRecordSetException;

	/**
	 * Returns the number of ddl record sets where groupId = &#63; and recordSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param recordSetKey the record set key
	 * @return the number of matching ddl record sets
	 */
	public int countByG_R(long groupId, String recordSetKey);

	/**
	 * Caches the ddl record set in the entity cache if it is enabled.
	 *
	 * @param ddlRecordSet the ddl record set
	 */
	public void cacheResult(DDLRecordSet ddlRecordSet);

	/**
	 * Caches the ddl record sets in the entity cache if it is enabled.
	 *
	 * @param ddlRecordSets the ddl record sets
	 */
	public void cacheResult(java.util.List<DDLRecordSet> ddlRecordSets);

	/**
	 * Creates a new ddl record set with the primary key. Does not add the ddl record set to the database.
	 *
	 * @param recordSetId the primary key for the new ddl record set
	 * @return the new ddl record set
	 */
	public DDLRecordSet create(long recordSetId);

	/**
	 * Removes the ddl record set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set that was removed
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet remove(long recordSetId)
		throws NoSuchRecordSetException;

	public DDLRecordSet updateImpl(DDLRecordSet ddlRecordSet);

	/**
	 * Returns the ddl record set with the primary key or throws a <code>NoSuchRecordSetException</code> if it could not be found.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set
	 * @throws NoSuchRecordSetException if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet findByPrimaryKey(long recordSetId)
		throws NoSuchRecordSetException;

	/**
	 * Returns the ddl record set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set, or <code>null</code> if a ddl record set with the primary key could not be found
	 */
	public DDLRecordSet fetchByPrimaryKey(long recordSetId);

	/**
	 * Returns all the ddl record sets.
	 *
	 * @return the ddl record sets
	 */
	public java.util.List<DDLRecordSet> findAll();

	/**
	 * Returns a range of all the ddl record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of ddl record sets
	 */
	public java.util.List<DDLRecordSet> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddl record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl record sets
	 */
	public java.util.List<DDLRecordSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddl record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ddl record sets
	 */
	public java.util.List<DDLRecordSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordSet>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the ddl record sets from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddl record sets.
	 *
	 * @return the number of ddl record sets
	 */
	public int countAll();

}