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

package com.liferay.dataset.view.service.persistence;

import com.liferay.dataset.view.exception.NoSuchStateEntryException;
import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dataset view state entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntryUtil
 * @generated
 */
@ProviderType
public interface DatasetViewStateEntryPersistence
	extends BasePersistence<DatasetViewStateEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DatasetViewStateEntryUtil} to access the dataset view state entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public DatasetViewStateEntry[] findByUuid_PrevAndNext(
			long datasetViewStateEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Removes all the dataset view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset view state entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public DatasetViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public DatasetViewStateEntry[] findByUuid_C_PrevAndNext(
			long datasetViewStateEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException;

	/**
	 * Removes all the dataset view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset view state entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the dataset view state entry in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 */
	public void cacheResult(DatasetViewStateEntry datasetViewStateEntry);

	/**
	 * Caches the dataset view state entries in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntries the dataset view state entries
	 */
	public void cacheResult(
		java.util.List<DatasetViewStateEntry> datasetViewStateEntries);

	/**
	 * Creates a new dataset view state entry with the primary key. Does not add the dataset view state entry to the database.
	 *
	 * @param datasetViewStateEntryId the primary key for the new dataset view state entry
	 * @return the new dataset view state entry
	 */
	public DatasetViewStateEntry create(long datasetViewStateEntryId);

	/**
	 * Removes the dataset view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry that was removed
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public DatasetViewStateEntry remove(long datasetViewStateEntryId)
		throws NoSuchStateEntryException;

	public DatasetViewStateEntry updateImpl(
		DatasetViewStateEntry datasetViewStateEntry);

	/**
	 * Returns the dataset view state entry with the primary key or throws a <code>NoSuchStateEntryException</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public DatasetViewStateEntry findByPrimaryKey(long datasetViewStateEntryId)
		throws NoSuchStateEntryException;

	/**
	 * Returns the dataset view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry, or <code>null</code> if a dataset view state entry with the primary key could not be found
	 */
	public DatasetViewStateEntry fetchByPrimaryKey(
		long datasetViewStateEntryId);

	/**
	 * Returns all the dataset view state entries.
	 *
	 * @return the dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findAll();

	/**
	 * Returns a range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dataset view state entries
	 */
	public java.util.List<DatasetViewStateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dataset view state entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dataset view state entries.
	 *
	 * @return the number of dataset view state entries
	 */
	public int countAll();

}