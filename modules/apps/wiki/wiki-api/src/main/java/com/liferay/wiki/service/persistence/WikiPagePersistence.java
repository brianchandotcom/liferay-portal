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

package com.liferay.wiki.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiPage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the wiki page service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageUtil
 * @generated
 */
@ProviderType
public interface WikiPagePersistence extends BasePersistence<WikiPage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageUtil} to access the wiki page persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByResourcePrimKey(long resourcePrimKey);

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByResourcePrimKey(
		long resourcePrimKey, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByResourcePrimKey_First(
			long resourcePrimKey,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByResourcePrimKey_First(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByResourcePrimKey_Last(
			long resourcePrimKey,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByResourcePrimKey_PrevAndNext(
			long pageId, long resourcePrimKey,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	public void removeByResourcePrimKey(long resourcePrimKey);

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching wiki pages
	 */
	public int countByResourcePrimKey(long resourcePrimKey);

	/**
	 * Returns all the wiki pages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid(String uuid);

	/**
	 * Returns a range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByUuid_PrevAndNext(
			long pageId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of wiki pages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching wiki pages
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByUUID_G(String uuid, long groupId)
		throws NoSuchPageException;

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the wiki page where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the wiki page that was removed
	 */
	public WikiPage removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageException;

	/**
	 * Returns the number of wiki pages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching wiki pages
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByUuid_C_PrevAndNext(
			long pageId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching wiki pages
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the wiki pages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the wiki pages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where companyId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByCompanyId_PrevAndNext(
			long pageId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of wiki pages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching wiki pages
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the wiki pages where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByNodeId(long nodeId);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByNodeId(
		long nodeId, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByNodeId_First(
			long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByNodeId_First(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByNodeId_Last(
			long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByNodeId_Last(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByNodeId_PrevAndNext(
			long pageId, long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 */
	public void removeByNodeId(long nodeId);

	/**
	 * Returns the number of wiki pages where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @return the number of matching wiki pages
	 */
	public int countByNodeId(long nodeId);

	/**
	 * Returns all the wiki pages where format = &#63;.
	 *
	 * @param format the format
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByFormat(String format);

	/**
	 * Returns a range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByFormat(
		String format, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByFormat(
		String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByFormat(
		String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByFormat_First(
			String format,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByFormat_First(
		String format,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByFormat_Last(
			String format,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByFormat_Last(
		String format,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where format = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByFormat_PrevAndNext(
			long pageId, String format,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where format = &#63; from the database.
	 *
	 * @param format the format
	 */
	public void removeByFormat(String format);

	/**
	 * Returns the number of wiki pages where format = &#63;.
	 *
	 * @param format the format
	 * @return the number of matching wiki pages
	 */
	public int countByFormat(String format);

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N(
		long resourcePrimKey, long nodeId);

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N(
		long resourcePrimKey, long nodeId, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N(
		long resourcePrimKey, long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N(
		long resourcePrimKey, long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_First(
			long resourcePrimKey, long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_First(
		long resourcePrimKey, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_Last(
			long resourcePrimKey, long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_Last(
		long resourcePrimKey, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByR_N_PrevAndNext(
			long pageId, long resourcePrimKey, long nodeId,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 */
	public void removeByR_N(long resourcePrimKey, long nodeId);

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @return the number of matching wiki pages
	 */
	public int countByR_N(long resourcePrimKey, long nodeId);

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_S(long resourcePrimKey, int status);

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_S(
		long resourcePrimKey, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_S(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_S(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_S_First(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_S_First(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_S_Last(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_S_Last(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByR_S_PrevAndNext(
			long pageId, long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 */
	public void removeByR_S(long resourcePrimKey, int status);

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByR_S(long resourcePrimKey, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T(long nodeId, String title);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T(
		long nodeId, String title, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T(
		long nodeId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T(
		long nodeId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_First(
			long nodeId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_First(
		long nodeId, String title,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_Last(
			long nodeId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_Last(
		long nodeId, String title,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_T_PrevAndNext(
			long pageId, long nodeId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 */
	public void removeByN_T(long nodeId, String title);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the number of matching wiki pages
	 */
	public int countByN_T(long nodeId, String title);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H(long nodeId, boolean head);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_First(
			long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_First(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_Last(
			long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_Last(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_PrevAndNext(
			long pageId, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 */
	public void removeByN_H(long nodeId, boolean head);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	public int countByN_H(long nodeId, boolean head);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_P(long nodeId, String parentTitle);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_P(
		long nodeId, String parentTitle, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_P(
		long nodeId, String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_P(
		long nodeId, String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_P_First(
			long nodeId, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_P_First(
		long nodeId, String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_P_Last(
			long nodeId, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_P_Last(
		long nodeId, String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_P_PrevAndNext(
			long pageId, long nodeId, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and parentTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 */
	public void removeByN_P(long nodeId, String parentTitle);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @return the number of matching wiki pages
	 */
	public int countByN_P(long nodeId, String parentTitle);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_R(
		long nodeId, String redirectTitle);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_R(
		long nodeId, String redirectTitle, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_R(
		long nodeId, String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_R(
		long nodeId, String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_R_First(
			long nodeId, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_R_First(
		long nodeId, String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_R_Last(
			long nodeId, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_R_Last(
		long nodeId, String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_R_PrevAndNext(
			long pageId, long nodeId, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and redirectTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 */
	public void removeByN_R(long nodeId, String redirectTitle);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @return the number of matching wiki pages
	 */
	public int countByN_R(long nodeId, String redirectTitle);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_S(long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_S(
		long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_S(
		long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_S(
		long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_S_First(
			long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_S_First(
		long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_S_Last(
			long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_S_Last(
		long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_S_PrevAndNext(
			long pageId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 */
	public void removeByN_S(long nodeId, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_S(long nodeId, int status);

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or throws a <code>NoSuchPageException</code> if it could not be found.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_V(
			long resourcePrimKey, long nodeId, double version)
		throws NoSuchPageException;

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_V(
		long resourcePrimKey, long nodeId, double version);

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_V(
		long resourcePrimKey, long nodeId, double version,
		boolean useFinderCache);

	/**
	 * Removes the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the wiki page that was removed
	 */
	public WikiPage removeByR_N_V(
			long resourcePrimKey, long nodeId, double version)
		throws NoSuchPageException;

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the number of matching wiki pages
	 */
	public int countByR_N_V(long resourcePrimKey, long nodeId, double version);

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_H(
		long resourcePrimKey, long nodeId, boolean head);

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_H(
		long resourcePrimKey, long nodeId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_H(
		long resourcePrimKey, long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_H(
		long resourcePrimKey, long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_H_First(
			long resourcePrimKey, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_H_First(
		long resourcePrimKey, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_H_Last(
			long resourcePrimKey, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_H_Last(
		long resourcePrimKey, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByR_N_H_PrevAndNext(
			long pageId, long resourcePrimKey, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 */
	public void removeByR_N_H(long resourcePrimKey, long nodeId, boolean head);

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	public int countByR_N_H(long resourcePrimKey, long nodeId, boolean head);

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_S_First(
			long resourcePrimKey, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_S_First(
		long resourcePrimKey, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByR_N_S_Last(
			long resourcePrimKey, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByR_N_S_Last(
		long resourcePrimKey, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByR_N_S_PrevAndNext(
			long pageId, long resourcePrimKey, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 */
	public void removeByR_N_S(long resourcePrimKey, long nodeId, int status);

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByR_N_S(long resourcePrimKey, long nodeId, int status);

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H(
		long groupId, long nodeId, boolean head);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H(
		long groupId, long nodeId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H(
		long groupId, long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H(
		long groupId, long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_First(
			long groupId, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_First(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_Last(
			long groupId, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_Last(
		long groupId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_N_H_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H(
		long groupId, long nodeId, boolean head);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H(
		long groupId, long nodeId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H(
		long groupId, long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_N_H_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 */
	public void removeByG_N_H(long groupId, long nodeId, boolean head);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	public int countByG_N_H(long groupId, long nodeId, boolean head);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_N_H(long groupId, long nodeId, boolean head);

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_S(
		long groupId, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_S(
		long groupId, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_S(
		long groupId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_S(
		long groupId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_S_First(
			long groupId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_S_First(
		long groupId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_S_Last(
			long groupId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_S_Last(
		long groupId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_N_S_PrevAndNext(
			long pageId, long groupId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_S(
		long groupId, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_S(
		long groupId, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_S(
		long groupId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_N_S_PrevAndNext(
			long pageId, long groupId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	public void removeByG_N_S(long groupId, long nodeId, int status);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByG_N_S(long groupId, long nodeId, int status);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_N_S(long groupId, long nodeId, int status);

	/**
	 * Returns all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByU_N_S(
		long userId, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByU_N_S(
		long userId, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByU_N_S(
		long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByU_N_S(
		long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByU_N_S_First(
			long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByU_N_S_First(
		long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByU_N_S_Last(
			long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByU_N_S_Last(
		long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByU_N_S_PrevAndNext(
			long pageId, long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	public void removeByU_N_S(long userId, long nodeId, int status);

	/**
	 * Returns the number of wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByU_N_S(long userId, long nodeId, int status);

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or throws a <code>NoSuchPageException</code> if it could not be found.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException;

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_V(long nodeId, String title, double version);

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_V(
		long nodeId, String title, double version, boolean useFinderCache);

	/**
	 * Removes the wiki page where nodeId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the wiki page that was removed
	 */
	public WikiPage removeByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException;

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching wiki pages
	 */
	public int countByN_T_V(long nodeId, String title, double version);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_H(
		long nodeId, String title, boolean head);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_H(
		long nodeId, String title, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_H(
		long nodeId, String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_H(
		long nodeId, String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_H_First(
			long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_H_First(
		long nodeId, String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_H_Last(
			long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_H_Last(
		long nodeId, String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_T_H_PrevAndNext(
			long pageId, long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 */
	public void removeByN_T_H(long nodeId, String title, boolean head);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	public int countByN_T_H(long nodeId, String title, boolean head);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_S(
		long nodeId, String title, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_S(
		long nodeId, String title, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_S(
		long nodeId, String title, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_T_S(
		long nodeId, String title, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_S_First(
			long nodeId, String title, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_S_First(
		long nodeId, String title, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_T_S_Last(
			long nodeId, String title, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_T_S_Last(
		long nodeId, String title, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_T_S_PrevAndNext(
			long pageId, long nodeId, String title, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 */
	public void removeByN_T_S(long nodeId, String title, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_T_S(long nodeId, String title, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P(
		long nodeId, boolean head, String parentTitle);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P(
		long nodeId, boolean head, String parentTitle, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P(
		long nodeId, boolean head, String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P(
		long nodeId, boolean head, String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_First(
			long nodeId, boolean head, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_First(
		long nodeId, boolean head, String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_Last(
			long nodeId, boolean head, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_Last(
		long nodeId, boolean head, String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_P_PrevAndNext(
			long pageId, long nodeId, boolean head, String parentTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 */
	public void removeByN_H_P(long nodeId, boolean head, String parentTitle);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_P(long nodeId, boolean head, String parentTitle);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R(
		long nodeId, boolean head, String redirectTitle);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R(
		long nodeId, boolean head, String redirectTitle, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R(
		long nodeId, boolean head, String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R(
		long nodeId, boolean head, String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_First(
			long nodeId, boolean head, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_First(
		long nodeId, boolean head, String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_Last(
			long nodeId, boolean head, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_Last(
		long nodeId, boolean head, String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_R_PrevAndNext(
			long pageId, long nodeId, boolean head, String redirectTitle,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 */
	public void removeByN_H_R(long nodeId, boolean head, String redirectTitle);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_R(long nodeId, boolean head, String redirectTitle);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_S(
		long nodeId, boolean head, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_S(
		long nodeId, boolean head, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_S(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_S(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_S_First(
			long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_S_First(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_S_Last(
			long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_S_Last(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_S_PrevAndNext(
			long pageId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	public void removeByN_H_S(long nodeId, boolean head, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_S(long nodeId, boolean head, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_NotS(
		long nodeId, boolean head, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_NotS(
		long nodeId, boolean head, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_NotS(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_NotS(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_NotS_First(
			long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_NotS_First(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_NotS_Last(
			long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_NotS_Last(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_NotS_PrevAndNext(
			long pageId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	public void removeByN_H_NotS(long nodeId, boolean head, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_NotS(long nodeId, boolean head, int status);

	/**
	 * Returns all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_U_N_S(
		long groupId, long userId, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_U_N_S(
		long groupId, long userId, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_U_N_S(
		long groupId, long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_U_N_S(
		long groupId, long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_U_N_S_First(
			long groupId, long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_U_N_S_First(
		long groupId, long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_U_N_S_Last(
			long groupId, long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_U_N_S_Last(
		long groupId, long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_U_N_S_PrevAndNext(
			long pageId, long groupId, long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_U_N_S(
		long groupId, long userId, long nodeId, int status);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_U_N_S(
		long groupId, long userId, long nodeId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_U_N_S(
		long groupId, long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_U_N_S_PrevAndNext(
			long pageId, long groupId, long userId, long nodeId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	public void removeByG_U_N_S(
		long groupId, long userId, long nodeId, int status);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByG_U_N_S(
		long groupId, long userId, long nodeId, int status);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_U_N_S(
		long groupId, long userId, long nodeId, int status);

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_T_H(
		long groupId, long nodeId, String title, boolean head);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_T_H(
		long groupId, long nodeId, String title, boolean head, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_T_H(
		long groupId, long nodeId, String title, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_T_H(
		long groupId, long nodeId, String title, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_T_H_First(
			long groupId, long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_T_H_First(
		long groupId, long nodeId, String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_T_H_Last(
			long groupId, long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_T_H_Last(
		long groupId, long nodeId, String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_N_T_H_PrevAndNext(
			long pageId, long groupId, long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_T_H(
		long groupId, long nodeId, String title, boolean head);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_T_H(
		long groupId, long nodeId, String title, boolean head, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_T_H(
		long groupId, long nodeId, String title, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_N_T_H_PrevAndNext(
			long pageId, long groupId, long nodeId, String title, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 */
	public void removeByG_N_T_H(
		long groupId, long nodeId, String title, boolean head);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	public int countByG_N_T_H(
		long groupId, long nodeId, String title, boolean head);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_N_T_H(
		long groupId, long nodeId, String title, boolean head);

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head, int status);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_S_First(
			long groupId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_S_First(
		long groupId, long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_S_Last(
			long groupId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_S_Last(
		long groupId, long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_N_H_S_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_S(
		long groupId, long nodeId, boolean head, int status);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_S(
		long groupId, long nodeId, boolean head, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_S(
		long groupId, long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_N_H_S_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	public void removeByG_N_H_S(
		long groupId, long nodeId, boolean head, int status);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByG_N_H_S(
		long groupId, long nodeId, boolean head, int status);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_N_H_S(
		long groupId, long nodeId, boolean head, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_S_First(
			long nodeId, boolean head, String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_S_First(
		long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_S_Last(
			long nodeId, boolean head, String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_S_Last(
		long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_P_S_PrevAndNext(
			long pageId, long nodeId, boolean head, String parentTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	public void removeByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_P_S(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_NotS_First(
			long nodeId, boolean head, String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_NotS_First(
		long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_P_NotS_Last(
			long nodeId, boolean head, String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_P_NotS_Last(
		long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_P_NotS_PrevAndNext(
			long pageId, long nodeId, boolean head, String parentTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	public void removeByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_P_NotS(
		long nodeId, boolean head, String parentTitle, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_S_First(
			long nodeId, boolean head, String redirectTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_S_First(
		long nodeId, boolean head, String redirectTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_S_Last(
			long nodeId, boolean head, String redirectTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_S_Last(
		long nodeId, boolean head, String redirectTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_R_S_PrevAndNext(
			long pageId, long nodeId, boolean head, String redirectTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 */
	public void removeByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_R_S(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_NotS_First(
			long nodeId, boolean head, String redirectTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_NotS_First(
		long nodeId, boolean head, String redirectTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByN_H_R_NotS_Last(
			long nodeId, boolean head, String redirectTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByN_H_R_NotS_Last(
		long nodeId, boolean head, String redirectTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByN_H_R_NotS_PrevAndNext(
			long pageId, long nodeId, boolean head, String redirectTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 */
	public void removeByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByN_H_R_NotS(
		long nodeId, boolean head, String redirectTitle, int status);

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle,
		int status);

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	public java.util.List<WikiPage> findByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_P_S_First(
			long groupId, long nodeId, boolean head, String parentTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_P_S_First(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_N_H_P_S_Last(
			long groupId, long nodeId, boolean head, String parentTitle,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_N_H_P_S_Last(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] findByG_N_H_P_S_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head,
			String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle,
		int status);

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	public java.util.List<WikiPage> filterFindByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage[] filterFindByG_N_H_P_S_PrevAndNext(
			long pageId, long groupId, long nodeId, boolean head,
			String parentTitle, int status,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws NoSuchPageException;

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	public void removeByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle,
		int status);

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	public int countByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle,
		int status);

	/**
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	public int filterCountByG_N_H_P_S(
		long groupId, long nodeId, boolean head, String parentTitle,
		int status);

	/**
	 * Returns the wiki page where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPageException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching wiki page
	 * @throws NoSuchPageException if a matching wiki page could not be found
	 */
	public WikiPage findByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchPageException;

	/**
	 * Returns the wiki page where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Returns the wiki page where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	public WikiPage fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the wiki page where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the wiki page that was removed
	 */
	public WikiPage removeByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchPageException;

	/**
	 * Returns the number of wiki pages where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching wiki pages
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the wiki page in the entity cache if it is enabled.
	 *
	 * @param wikiPage the wiki page
	 */
	public void cacheResult(WikiPage wikiPage);

	/**
	 * Caches the wiki pages in the entity cache if it is enabled.
	 *
	 * @param wikiPages the wiki pages
	 */
	public void cacheResult(java.util.List<WikiPage> wikiPages);

	/**
	 * Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	 *
	 * @param pageId the primary key for the new wiki page
	 * @return the new wiki page
	 */
	public WikiPage create(long pageId);

	/**
	 * Removes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page that was removed
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage remove(long pageId) throws NoSuchPageException;

	public WikiPage updateImpl(WikiPage wikiPage);

	/**
	 * Returns the wiki page with the primary key or throws a <code>NoSuchPageException</code> if it could not be found.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page
	 * @throws NoSuchPageException if a wiki page with the primary key could not be found
	 */
	public WikiPage findByPrimaryKey(long pageId) throws NoSuchPageException;

	/**
	 * Returns the wiki page with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page, or <code>null</code> if a wiki page with the primary key could not be found
	 */
	public WikiPage fetchByPrimaryKey(long pageId);

	/**
	 * Returns all the wiki pages.
	 *
	 * @return the wiki pages
	 */
	public java.util.List<WikiPage> findAll();

	/**
	 * Returns a range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of wiki pages
	 */
	public java.util.List<WikiPage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of wiki pages
	 */
	public java.util.List<WikiPage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of wiki pages
	 */
	public java.util.List<WikiPage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the wiki pages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of wiki pages.
	 *
	 * @return the number of wiki pages
	 */
	public int countAll();

}