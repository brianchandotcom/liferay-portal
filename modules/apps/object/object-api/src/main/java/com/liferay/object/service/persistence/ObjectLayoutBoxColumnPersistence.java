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

import com.liferay.object.exception.NoSuchObjectLayoutBoxColumnException;
import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout box column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutBoxColumnPersistence
	extends BasePersistence<ObjectLayoutBoxColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutBoxColumnUtil} to access the object layout box column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout box columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns the object layout box columns before and after the current object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the current object layout box column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	public ObjectLayoutBoxColumn[] findByUuid_PrevAndNext(
			long objectLayoutBoxColumnId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Removes all the object layout box columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout box columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout box columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	public ObjectLayoutBoxColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns the object layout box columns before and after the current object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the current object layout box column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	public ObjectLayoutBoxColumn[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxColumnId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Removes all the object layout box columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout box columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the object layout box column in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 */
	public void cacheResult(ObjectLayoutBoxColumn objectLayoutBoxColumn);

	/**
	 * Caches the object layout box columns in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxColumns the object layout box columns
	 */
	public void cacheResult(
		java.util.List<ObjectLayoutBoxColumn> objectLayoutBoxColumns);

	/**
	 * Creates a new object layout box column with the primary key. Does not add the object layout box column to the database.
	 *
	 * @param objectLayoutBoxColumnId the primary key for the new object layout box column
	 * @return the new object layout box column
	 */
	public ObjectLayoutBoxColumn create(long objectLayoutBoxColumnId);

	/**
	 * Removes the object layout box column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column that was removed
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	public ObjectLayoutBoxColumn remove(long objectLayoutBoxColumnId)
		throws NoSuchObjectLayoutBoxColumnException;

	public ObjectLayoutBoxColumn updateImpl(
		ObjectLayoutBoxColumn objectLayoutBoxColumn);

	/**
	 * Returns the object layout box column with the primary key or throws a <code>NoSuchObjectLayoutBoxColumnException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	public ObjectLayoutBoxColumn findByPrimaryKey(long objectLayoutBoxColumnId)
		throws NoSuchObjectLayoutBoxColumnException;

	/**
	 * Returns the object layout box column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column, or <code>null</code> if a object layout box column with the primary key could not be found
	 */
	public ObjectLayoutBoxColumn fetchByPrimaryKey(
		long objectLayoutBoxColumnId);

	/**
	 * Returns all the object layout box columns.
	 *
	 * @return the object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findAll();

	/**
	 * Returns a range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout box columns
	 */
	public java.util.List<ObjectLayoutBoxColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBoxColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout box columns from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout box columns.
	 *
	 * @return the number of object layout box columns
	 */
	public int countAll();

}