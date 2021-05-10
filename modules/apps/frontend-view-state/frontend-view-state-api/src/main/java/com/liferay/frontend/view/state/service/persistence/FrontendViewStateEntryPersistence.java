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
import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the frontend view state entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateEntryUtil
 * @generated
 */
@ProviderType
public interface FrontendViewStateEntryPersistence
	extends BasePersistence<FrontendViewStateEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FrontendViewStateEntryUtil} to access the frontend view state entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public FrontendViewStateEntry[] findByUuid_PrevAndNext(
			long frontendViewStateEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the frontend view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching frontend view state entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public FrontendViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public FrontendViewStateEntry[] findByUuid_C_PrevAndNext(
			long frontendViewStateEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the frontend view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching frontend view state entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the frontend view state entry in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 */
	public void cacheResult(FrontendViewStateEntry frontendViewStateEntry);

	/**
	 * Caches the frontend view state entries in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntries the frontend view state entries
	 */
	public void cacheResult(
		java.util.List<FrontendViewStateEntry> frontendViewStateEntries);

	/**
	 * Creates a new frontend view state entry with the primary key. Does not add the frontend view state entry to the database.
	 *
	 * @param frontendViewStateEntryId the primary key for the new frontend view state entry
	 * @return the new frontend view state entry
	 */
	public FrontendViewStateEntry create(long frontendViewStateEntryId);

	/**
	 * Removes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public FrontendViewStateEntry remove(long frontendViewStateEntryId)
		throws NoSuchEntryException;

	public FrontendViewStateEntry updateImpl(
		FrontendViewStateEntry frontendViewStateEntry);

	/**
	 * Returns the frontend view state entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public FrontendViewStateEntry findByPrimaryKey(
			long frontendViewStateEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the frontend view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry, or <code>null</code> if a frontend view state entry with the primary key could not be found
	 */
	public FrontendViewStateEntry fetchByPrimaryKey(
		long frontendViewStateEntryId);

	/**
	 * Returns all the frontend view state entries.
	 *
	 * @return the frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findAll();

	/**
	 * Returns a range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of frontend view state entries
	 */
	public java.util.List<FrontendViewStateEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FrontendViewStateEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the frontend view state entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of frontend view state entries.
	 *
	 * @return the number of frontend view state entries
	 */
	public int countAll();

}