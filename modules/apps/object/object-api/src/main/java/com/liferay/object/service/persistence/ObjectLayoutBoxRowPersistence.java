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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectLayoutBoxRowException;
import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout box row service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRowUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutBoxRowPersistence
	extends BasePersistence<ObjectLayoutBoxRow> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutBoxRowUtil} to access the object layout box row persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout box rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns the object layout box rows before and after the current object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxRowId the primary key of the current object layout box row
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public ObjectLayoutBoxRow[] findByUuid_PrevAndNext(
			long objectLayoutBoxRowId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Removes all the object layout box rows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout box rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout box rows
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public ObjectLayoutBoxRow fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns the object layout box rows before and after the current object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the primary key of the current object layout box row
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public ObjectLayoutBoxRow[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxRowId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
				orderByComparator)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Removes all the object layout box rows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout box rows
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the object layout box row in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 */
	public void cacheResult(ObjectLayoutBoxRow objectLayoutBoxRow);

	/**
	 * Caches the object layout box rows in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxRows the object layout box rows
	 */
	public void cacheResult(
		java.util.List<ObjectLayoutBoxRow> objectLayoutBoxRows);

	/**
	 * Creates a new object layout box row with the primary key. Does not add the object layout box row to the database.
	 *
	 * @param objectLayoutBoxRowId the primary key for the new object layout box row
	 * @return the new object layout box row
	 */
	public ObjectLayoutBoxRow create(long objectLayoutBoxRowId);

	/**
	 * Removes the object layout box row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row that was removed
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public ObjectLayoutBoxRow remove(long objectLayoutBoxRowId)
		throws NoSuchObjectLayoutBoxRowException;

	public ObjectLayoutBoxRow updateImpl(ObjectLayoutBoxRow objectLayoutBoxRow);

	/**
	 * Returns the object layout box row with the primary key or throws a <code>NoSuchObjectLayoutBoxRowException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public ObjectLayoutBoxRow findByPrimaryKey(long objectLayoutBoxRowId)
		throws NoSuchObjectLayoutBoxRowException;

	/**
	 * Returns the object layout box row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row, or <code>null</code> if a object layout box row with the primary key could not be found
	 */
	public ObjectLayoutBoxRow fetchByPrimaryKey(long objectLayoutBoxRowId);

	/**
	 * Returns all the object layout box rows.
	 *
	 * @return the object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findAll();

	/**
	 * Returns a range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout box rows
	 */
	public java.util.List<ObjectLayoutBoxRow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxRow>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout box rows from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout box rows.
	 *
	 * @return the number of object layout box rows
	 */
	public int countAll();

}