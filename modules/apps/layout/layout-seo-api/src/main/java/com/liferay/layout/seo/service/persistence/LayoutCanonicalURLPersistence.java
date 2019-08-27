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
import com.liferay.layout.seo.model.LayoutCanonicalURL;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout canonical url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCanonicalURLUtil
 * @generated
 */
@ProviderType
public interface LayoutCanonicalURLPersistence
	extends BasePersistence<LayoutCanonicalURL> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutCanonicalURLUtil} to access the layout canonical url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid(String, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByUuid_First(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public LayoutCanonicalURL[] findByUuid_PrevAndNext(
			long layoutCanonicalURLId, String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Removes all the layout canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout canonical urls
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByUUID_G(String,long)}
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	public LayoutCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByUUID_G(String uuid, long groupId);

	/**
	 * Removes the layout canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout canonical url that was removed
	 */
	public LayoutCanonicalURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout canonical urls
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid_C(String,long, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public LayoutCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException;

	/**
	 * Removes all the layout canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout canonical urls
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByG_P_L(long,boolean,long)}
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	public LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache);

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId);

	/**
	 * Removes the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout canonical url that was removed
	 */
	public LayoutCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the number of layout canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout canonical urls
	 */
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	 * Caches the layout canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 */
	public void cacheResult(LayoutCanonicalURL layoutCanonicalURL);

	/**
	 * Caches the layout canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURLs the layout canonical urls
	 */
	public void cacheResult(
		java.util.List<LayoutCanonicalURL> layoutCanonicalURLs);

	/**
	 * Creates a new layout canonical url with the primary key. Does not add the layout canonical url to the database.
	 *
	 * @param layoutCanonicalURLId the primary key for the new layout canonical url
	 * @return the new layout canonical url
	 */
	public LayoutCanonicalURL create(long layoutCanonicalURLId);

	/**
	 * Removes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public LayoutCanonicalURL remove(long layoutCanonicalURLId)
		throws NoSuchCanonicalURLException;

	public LayoutCanonicalURL updateImpl(LayoutCanonicalURL layoutCanonicalURL);

	/**
	 * Returns the layout canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public LayoutCanonicalURL findByPrimaryKey(long layoutCanonicalURLId)
		throws NoSuchCanonicalURLException;

	/**
	 * Returns the layout canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url, or <code>null</code> if a layout canonical url with the primary key could not be found
	 */
	public LayoutCanonicalURL fetchByPrimaryKey(long layoutCanonicalURLId);

	/**
	 * Returns all the layout canonical urls.
	 *
	 * @return the layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findAll();

	/**
	 * Returns a range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findAll(int, int, OrderByComparator)}
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout canonical urls
	 */
	@Deprecated
	public java.util.List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout canonical urls
	 */
	public java.util.List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator);

	/**
	 * Removes all the layout canonical urls from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout canonical urls.
	 *
	 * @return the number of layout canonical urls
	 */
	public int countAll();

}