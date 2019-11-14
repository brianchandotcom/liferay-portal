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

import com.liferay.layout.seo.exception.NoSuchSiteException;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout seo site service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteUtil
 * @generated
 */
@ProviderType
public interface LayoutSEOSitePersistence
	extends BasePersistence<LayoutSEOSite> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSEOSiteUtil} to access the layout seo site persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public LayoutSEOSite[] findByUuid_PrevAndNext(
			long layoutSEOSiteId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Removes all the layout seo sites where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo sites
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByUUID_G(String uuid, long groupId)
		throws NoSuchSiteException;

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout seo site where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	public LayoutSEOSite removeByUUID_G(String uuid, long groupId)
		throws NoSuchSiteException;

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public LayoutSEOSite[] findByUuid_C_PrevAndNext(
			long layoutSEOSiteId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
				orderByComparator)
		throws NoSuchSiteException;

	/**
	 * Removes all the layout seo sites where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo sites
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the layout seo site where groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public LayoutSEOSite findByGroupId(long groupId) throws NoSuchSiteException;

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByGroupId(long groupId);

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public LayoutSEOSite fetchByGroupId(long groupId, boolean useFinderCache);

	/**
	 * Removes the layout seo site where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	public LayoutSEOSite removeByGroupId(long groupId)
		throws NoSuchSiteException;

	/**
	 * Returns the number of layout seo sites where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the layout seo site in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSite the layout seo site
	 */
	public void cacheResult(LayoutSEOSite layoutSEOSite);

	/**
	 * Caches the layout seo sites in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSites the layout seo sites
	 */
	public void cacheResult(java.util.List<LayoutSEOSite> layoutSEOSites);

	/**
	 * Creates a new layout seo site with the primary key. Does not add the layout seo site to the database.
	 *
	 * @param layoutSEOSiteId the primary key for the new layout seo site
	 * @return the new layout seo site
	 */
	public LayoutSEOSite create(long layoutSEOSiteId);

	/**
	 * Removes the layout seo site with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site that was removed
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public LayoutSEOSite remove(long layoutSEOSiteId)
		throws NoSuchSiteException;

	public LayoutSEOSite updateImpl(LayoutSEOSite layoutSEOSite);

	/**
	 * Returns the layout seo site with the primary key or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public LayoutSEOSite findByPrimaryKey(long layoutSEOSiteId)
		throws NoSuchSiteException;

	/**
	 * Returns the layout seo site with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site, or <code>null</code> if a layout seo site with the primary key could not be found
	 */
	public LayoutSEOSite fetchByPrimaryKey(long layoutSEOSiteId);

	/**
	 * Returns all the layout seo sites.
	 *
	 * @return the layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findAll();

	/**
	 * Returns a range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo sites
	 */
	public java.util.List<LayoutSEOSite> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSEOSite>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout seo sites from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout seo sites.
	 *
	 * @return the number of layout seo sites
	 */
	public int countAll();

}