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

package com.liferay.layout.seo.service.persistence;

import com.liferay.layout.seo.exception.NoSuchSiteEntryException;
import com.liferay.layout.seo.model.LayoutSEOSiteEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout seo site entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteEntryUtil
 * @generated
 */
@ProviderType
public interface LayoutSEOSiteEntryPersistence
	extends BasePersistence<LayoutSEOSiteEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSEOSiteEntryUtil} to access the layout seo site entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout seo site entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns the layout seo site entries before and after the current layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the current layout seo site entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public LayoutSEOSiteEntry[] findByUuid_PrevAndNext(
			long layoutSEOSiteEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Removes all the layout seo site entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout seo site entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo site entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout seo site entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo site entry that was removed
	 */
	public LayoutSEOSiteEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the number of layout seo site entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo site entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns the layout seo site entries before and after the current layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the current layout seo site entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public LayoutSEOSiteEntry[] findByUuid_C_PrevAndNext(
			long layoutSEOSiteEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
				orderByComparator)
		throws NoSuchSiteEntryException;

	/**
	 * Removes all the layout seo site entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo site entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the layout seo site entry where groupId = &#63; or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry findByGroupId(long groupId)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the layout seo site entry where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByGroupId(long groupId);

	/**
	 * Returns the layout seo site entry where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public LayoutSEOSiteEntry fetchByGroupId(
		long groupId, boolean useFinderCache);

	/**
	 * Removes the layout seo site entry where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the layout seo site entry that was removed
	 */
	public LayoutSEOSiteEntry removeByGroupId(long groupId)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the number of layout seo site entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout seo site entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the layout seo site entry in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSiteEntry the layout seo site entry
	 */
	public void cacheResult(LayoutSEOSiteEntry layoutSEOSiteEntry);

	/**
	 * Caches the layout seo site entries in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSiteEntries the layout seo site entries
	 */
	public void cacheResult(
		java.util.List<LayoutSEOSiteEntry> layoutSEOSiteEntries);

	/**
	 * Creates a new layout seo site entry with the primary key. Does not add the layout seo site entry to the database.
	 *
	 * @param layoutSEOSiteEntryId the primary key for the new layout seo site entry
	 * @return the new layout seo site entry
	 */
	public LayoutSEOSiteEntry create(long layoutSEOSiteEntryId);

	/**
	 * Removes the layout seo site entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry that was removed
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public LayoutSEOSiteEntry remove(long layoutSEOSiteEntryId)
		throws NoSuchSiteEntryException;

	public LayoutSEOSiteEntry updateImpl(LayoutSEOSiteEntry layoutSEOSiteEntry);

	/**
	 * Returns the layout seo site entry with the primary key or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public LayoutSEOSiteEntry findByPrimaryKey(long layoutSEOSiteEntryId)
		throws NoSuchSiteEntryException;

	/**
	 * Returns the layout seo site entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry, or <code>null</code> if a layout seo site entry with the primary key could not be found
	 */
	public LayoutSEOSiteEntry fetchByPrimaryKey(long layoutSEOSiteEntryId);

	/**
	 * Returns all the layout seo site entries.
	 *
	 * @return the layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findAll();

	/**
	 * Returns a range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo site entries
	 */
	public java.util.List<LayoutSEOSiteEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSiteEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout seo site entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout seo site entries.
	 *
	 * @return the number of layout seo site entries
	 */
	public int countAll();

}