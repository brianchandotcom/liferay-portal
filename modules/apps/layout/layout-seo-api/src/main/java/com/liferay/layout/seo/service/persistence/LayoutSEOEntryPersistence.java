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

import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout seo entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryUtil
 * @generated
 */
@ProviderType
public interface LayoutSEOEntryPersistence
	extends BasePersistence<LayoutSEOEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSEOEntryUtil} to access the layout seo entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns the layout seo entries before and after the current layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	public LayoutSEOEntry[] findByUuid_PrevAndNext(
			long layoutSEOEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the layout seo entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout seo entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo entry that was removed
	 */
	public LayoutSEOEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of layout seo entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns the layout seo entries before and after the current layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	public LayoutSEOEntry[] findByUuid_C_PrevAndNext(
			long layoutSEOEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the layout seo entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchEntryException;

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId);

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public LayoutSEOEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache);

	/**
	 * Removes the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo entry that was removed
	 */
	public LayoutSEOEntry removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of layout seo entries where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo entries
	 */
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	 * Caches the layout seo entry in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntry the layout seo entry
	 */
	public void cacheResult(LayoutSEOEntry layoutSEOEntry);

	/**
	 * Caches the layout seo entries in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntries the layout seo entries
	 */
	public void cacheResult(java.util.List<LayoutSEOEntry> layoutSEOEntries);

	/**
	 * Creates a new layout seo entry with the primary key. Does not add the layout seo entry to the database.
	 *
	 * @param layoutSEOEntryId the primary key for the new layout seo entry
	 * @return the new layout seo entry
	 */
	public LayoutSEOEntry create(long layoutSEOEntryId);

	/**
	 * Removes the layout seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry that was removed
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	public LayoutSEOEntry remove(long layoutSEOEntryId)
		throws NoSuchEntryException;

	public LayoutSEOEntry updateImpl(LayoutSEOEntry layoutSEOEntry);

	/**
	 * Returns the layout seo entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	public LayoutSEOEntry findByPrimaryKey(long layoutSEOEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the layout seo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry, or <code>null</code> if a layout seo entry with the primary key could not be found
	 */
	public LayoutSEOEntry fetchByPrimaryKey(long layoutSEOEntryId);

	/**
	 * Returns all the layout seo entries.
	 *
	 * @return the layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findAll();

	/**
	 * Returns a range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo entries
	 */
	public java.util.List<LayoutSEOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout seo entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout seo entries.
	 *
	 * @return the number of layout seo entries
	 */
	public int countAll();

}