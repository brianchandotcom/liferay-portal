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

import com.liferay.object.exception.NoSuchObjectViewColumnException;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object view column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectViewColumnPersistence
	extends BasePersistence<ObjectViewColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectViewColumnUtil} to access the object view column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid(String uuid);

	/**
	 * Returns a range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn[] findByUuid_PrevAndNext(
			long objectViewColumnId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Removes all the object view columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn[] findByUuid_C_PrevAndNext(
			long objectViewColumnId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Removes all the object view columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId);

	/**
	 * Returns a range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end);

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByObjectViewId_First(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByObjectViewId_First(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByObjectViewId_Last(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByObjectViewId_Last(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn[] findByObjectViewId_PrevAndNext(
			long objectViewColumnId, long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Removes all the object view columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public void removeByObjectViewId(long objectViewId);

	/**
	 * Returns the number of object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view columns
	 */
	public int countByObjectViewId(long objectViewId);

	/**
	 * Caches the object view column in the entity cache if it is enabled.
	 *
	 * @param objectViewColumn the object view column
	 */
	public void cacheResult(ObjectViewColumn objectViewColumn);

	/**
	 * Caches the object view columns in the entity cache if it is enabled.
	 *
	 * @param objectViewColumns the object view columns
	 */
	public void cacheResult(java.util.List<ObjectViewColumn> objectViewColumns);

	/**
	 * Creates a new object view column with the primary key. Does not add the object view column to the database.
	 *
	 * @param objectViewColumnId the primary key for the new object view column
	 * @return the new object view column
	 */
	public ObjectViewColumn create(long objectViewColumnId);

	/**
	 * Removes the object view column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column that was removed
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn remove(long objectViewColumnId)
		throws NoSuchObjectViewColumnException;

	public ObjectViewColumn updateImpl(ObjectViewColumn objectViewColumn);

	/**
	 * Returns the object view column with the primary key or throws a <code>NoSuchObjectViewColumnException</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn findByPrimaryKey(long objectViewColumnId)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the object view column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column, or <code>null</code> if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn fetchByPrimaryKey(long objectViewColumnId);

	/**
	 * Returns all the object view columns.
	 *
	 * @return the object view columns
	 */
	public java.util.List<ObjectViewColumn> findAll();

	/**
	 * Returns a range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of object view columns
	 */
	public java.util.List<ObjectViewColumn> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view columns
	 */
	public java.util.List<ObjectViewColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view columns
	 */
	public java.util.List<ObjectViewColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object view columns from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object view columns.
	 *
	 * @return the number of object view columns
	 */
	public int countAll();

}