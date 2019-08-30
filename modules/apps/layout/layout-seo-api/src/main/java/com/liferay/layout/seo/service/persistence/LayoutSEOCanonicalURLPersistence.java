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

import com.liferay.layout.seo.exception.NoSuchCanonicalURLException;
import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout seo canonical url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOCanonicalURLUtil
 * @generated
 */
@ProviderType
public interface LayoutSEOCanonicalURLPersistence
	extends BasePersistence<LayoutSEOCanonicalURL> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSEOCanonicalURLUtil} to access the layout seo canonical url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid(String, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public LayoutSEOCanonicalURL[] findByUuid_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo canonical urls
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByUUID_G(String,long)}
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Deprecated
	public LayoutSEOCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByUUID_G(String uuid, long groupId);

	/**
	 * Removes the layout seo canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo canonical url that was removed
	 */
	public LayoutSEOCanonicalURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo canonical urls
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid_C(String,long, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public LayoutSEOCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo canonical urls
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByG_P_L(long,boolean,long)}
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Deprecated
	public LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache);

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId);

	/**
	 * Removes the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo canonical url that was removed
	 */
	public LayoutSEOCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the number of layout seo canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo canonical urls
	 */
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	 * Caches the layout seo canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 */
	public void cacheResult(LayoutSEOCanonicalURL layoutSEOCanonicalURL);

	/**
	 * Caches the layout seo canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURLs the layout seo canonical urls
	 */
	public void cacheResult(
		java.util.List<LayoutSEOCanonicalURL> layoutSEOCanonicalURLs);

	/**
	 * Creates a new layout seo canonical url with the primary key. Does not add the layout seo canonical url to the database.
	 *
	 * @param layoutSEOCanonicalURLId the primary key for the new layout seo canonical url
	 * @return the new layout seo canonical url
	 */
	public LayoutSEOCanonicalURL create(long layoutSEOCanonicalURLId);

	/**
	 * Removes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public LayoutSEOCanonicalURL remove(long layoutSEOCanonicalURLId)
		throws NoSuchCanonicalURLException;

	public LayoutSEOCanonicalURL updateImpl(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL);

	/**
	 * Returns the layout seo canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public LayoutSEOCanonicalURL findByPrimaryKey(long layoutSEOCanonicalURLId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout seo canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url, or <code>null</code> if a layout seo canonical url with the primary key could not be found
	 */
	public LayoutSEOCanonicalURL fetchByPrimaryKey(
		long layoutSEOCanonicalURLId);

	/**
	 * Returns all the layout seo canonical urls.
	 *
	 * @return the layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findAll();

	/**
	 * Returns a range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findAll(int, int, OrderByComparator)}
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo canonical urls
	 */
	public java.util.List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator);

	/**
	 * Removes all the layout seo canonical urls from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout seo canonical urls.
	 *
	 * @return the number of layout seo canonical urls
	 */
	public int countAll();

}