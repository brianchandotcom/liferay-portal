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

import com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException;
import com.liferay.layout.seo.model.SiteSEOEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the site seo entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryUtil
 * @generated
 */
@ProviderType
public interface SiteSEOEntryPersistence extends BasePersistence<SiteSEOEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteSEOEntryUtil} to access the site seo entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the site seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns the site seo entries before and after the current site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param siteSEOEntryId the primary key of the current site seo entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public SiteSEOEntry[] findByUuid_PrevAndNext(
			long siteSEOEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Removes all the site seo entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of site seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site seo entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the site seo entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site seo entry that was removed
	 */
	public SiteSEOEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the number of site seo entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site seo entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site seo entries
	 */
	public java.util.List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns the site seo entries before and after the current site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteSEOEntryId the primary key of the current site seo entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public SiteSEOEntry[] findByUuid_C_PrevAndNext(
			long siteSEOEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
				orderByComparator)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Removes all the site seo entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site seo entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the site seo entry where groupId = &#63; or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public SiteSEOEntry findByGroupId(long groupId)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the site seo entry where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByGroupId(long groupId);

	/**
	 * Returns the site seo entry where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public SiteSEOEntry fetchByGroupId(long groupId, boolean useFinderCache);

	/**
	 * Removes the site seo entry where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the site seo entry that was removed
	 */
	public SiteSEOEntry removeByGroupId(long groupId)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the number of site seo entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site seo entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the site seo entry in the entity cache if it is enabled.
	 *
	 * @param siteSEOEntry the site seo entry
	 */
	public void cacheResult(SiteSEOEntry siteSEOEntry);

	/**
	 * Caches the site seo entries in the entity cache if it is enabled.
	 *
	 * @param siteSEOEntries the site seo entries
	 */
	public void cacheResult(java.util.List<SiteSEOEntry> siteSEOEntries);

	/**
	 * Creates a new site seo entry with the primary key. Does not add the site seo entry to the database.
	 *
	 * @param siteSEOEntryId the primary key for the new site seo entry
	 * @return the new site seo entry
	 */
	public SiteSEOEntry create(long siteSEOEntryId);

	/**
	 * Removes the site seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry that was removed
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public SiteSEOEntry remove(long siteSEOEntryId)
		throws NoSuchSiteSEOEntryException;

	public SiteSEOEntry updateImpl(SiteSEOEntry siteSEOEntry);

	/**
	 * Returns the site seo entry with the primary key or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public SiteSEOEntry findByPrimaryKey(long siteSEOEntryId)
		throws NoSuchSiteSEOEntryException;

	/**
	 * Returns the site seo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry, or <code>null</code> if a site seo entry with the primary key could not be found
	 */
	public SiteSEOEntry fetchByPrimaryKey(long siteSEOEntryId);

	/**
	 * Returns all the site seo entries.
	 *
	 * @return the site seo entries
	 */
	public java.util.List<SiteSEOEntry> findAll();

	/**
	 * Returns a range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of site seo entries
	 */
	public java.util.List<SiteSEOEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site seo entries
	 */
	public java.util.List<SiteSEOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site seo entries
	 */
	public java.util.List<SiteSEOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteSEOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the site seo entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of site seo entries.
	 *
	 * @return the number of site seo entries
	 */
	public int countAll();

}