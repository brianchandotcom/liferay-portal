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

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.exception.NoSuchEntryException;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fvs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSEntryUtil
 * @generated
 */
@ProviderType
public interface FVSEntryPersistence extends BasePersistence<FVSEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FVSEntryUtil} to access the fvs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fvs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public FVSEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public FVSEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public FVSEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public FVSEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns the fvs entries before and after the current fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsEntryId the primary key of the current fvs entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public FVSEntry[] findByUuid_PrevAndNext(
			long fvsEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fvs entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fvs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs entries
	 */
	public java.util.List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public FVSEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public FVSEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public FVSEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public FVSEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns the fvs entries before and after the current fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsEntryId the primary key of the current fvs entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public FVSEntry[] findByUuid_C_PrevAndNext(
			long fvsEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fvs entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the fvs entry in the entity cache if it is enabled.
	 *
	 * @param fvsEntry the fvs entry
	 */
	public void cacheResult(FVSEntry fvsEntry);

	/**
	 * Caches the fvs entries in the entity cache if it is enabled.
	 *
	 * @param fvsEntries the fvs entries
	 */
	public void cacheResult(java.util.List<FVSEntry> fvsEntries);

	/**
	 * Creates a new fvs entry with the primary key. Does not add the fvs entry to the database.
	 *
	 * @param fvsEntryId the primary key for the new fvs entry
	 * @return the new fvs entry
	 */
	public FVSEntry create(long fvsEntryId);

	/**
	 * Removes the fvs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry that was removed
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public FVSEntry remove(long fvsEntryId) throws NoSuchEntryException;

	public FVSEntry updateImpl(FVSEntry fvsEntry);

	/**
	 * Returns the fvs entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public FVSEntry findByPrimaryKey(long fvsEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the fvs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry, or <code>null</code> if a fvs entry with the primary key could not be found
	 */
	public FVSEntry fetchByPrimaryKey(long fvsEntryId);

	/**
	 * Returns all the fvs entries.
	 *
	 * @return the fvs entries
	 */
	public java.util.List<FVSEntry> findAll();

	/**
	 * Returns a range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of fvs entries
	 */
	public java.util.List<FVSEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs entries
	 */
	public java.util.List<FVSEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs entries
	 */
	public java.util.List<FVSEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fvs entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fvs entries.
	 *
	 * @return the number of fvs entries
	 */
	public int countAll();

}