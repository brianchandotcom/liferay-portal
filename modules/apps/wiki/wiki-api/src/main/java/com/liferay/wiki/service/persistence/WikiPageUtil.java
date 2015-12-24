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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.wiki.model.WikiPage;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the wiki page service. This utility wraps {@link com.liferay.wiki.service.persistence.impl.WikiPagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPagePersistence
 * @see com.liferay.wiki.service.persistence.impl.WikiPagePersistenceImpl
 * @generated
 */
@ProviderType
public class WikiPageUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(WikiPage wikiPage) {
		getPersistence().clearCache(wikiPage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WikiPage> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WikiPage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WikiPage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WikiPage update(WikiPage wikiPage) {
		return getPersistence().update(wikiPage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WikiPage update(WikiPage wikiPage,
		ServiceContext serviceContext) {
		return getPersistence().update(wikiPage, serviceContext);
	}

	/**
	* Returns all the wiki pages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the wiki pages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByUuid_First(java.lang.String uuid,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByUuid_Last(java.lang.String uuid,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByUuid_PrevAndNext(long pageId,
		java.lang.String uuid, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(pageId, uuid, orderByComparator);
	}

	/**
	* Removes all the wiki pages where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of wiki pages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki pages
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the wiki page where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the wiki page where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the wiki page that was removed
	*/
	public static WikiPage removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of wiki pages where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching wiki pages
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the wiki pages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByUuid_C_PrevAndNext(long pageId,
		java.lang.String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(pageId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of wiki pages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wiki pages
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the wiki pages where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().findByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns a range of all the wiki pages where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByResourcePrimKey_PrevAndNext(long pageId,
		long resourcePrimKey, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByResourcePrimKey_PrevAndNext(pageId, resourcePrimKey,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public static void removeByResourcePrimKey(long resourcePrimKey) {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching wiki pages
	*/
	public static int countByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByNodeId(long nodeId) {
		return getPersistence().findByNodeId(nodeId);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByNodeId(long nodeId, int start, int end) {
		return getPersistence().findByNodeId(nodeId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByNodeId(nodeId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByNodeId(nodeId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByNodeId_First(long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByNodeId_First(nodeId, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByNodeId_First(long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByNodeId_First(nodeId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByNodeId_Last(long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByNodeId_Last(nodeId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByNodeId_Last(long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByNodeId_Last(nodeId, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByNodeId_PrevAndNext(long pageId, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByNodeId_PrevAndNext(pageId, nodeId, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; from the database.
	*
	* @param nodeId the node ID
	*/
	public static void removeByNodeId(long nodeId) {
		getPersistence().removeByNodeId(nodeId);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63;.
	*
	* @param nodeId the node ID
	* @return the number of matching wiki pages
	*/
	public static int countByNodeId(long nodeId) {
		return getPersistence().countByNodeId(nodeId);
	}

	/**
	* Returns all the wiki pages where format = &#63;.
	*
	* @param format the format
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByFormat(java.lang.String format) {
		return getPersistence().findByFormat(format);
	}

	/**
	* Returns a range of all the wiki pages where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param format the format
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByFormat(java.lang.String format,
		int start, int end) {
		return getPersistence().findByFormat(format, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param format the format
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByFormat(java.lang.String format,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByFormat(format, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param format the format
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByFormat(java.lang.String format,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFormat(format, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where format = &#63;.
	*
	* @param format the format
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByFormat_First(java.lang.String format,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByFormat_First(format, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where format = &#63;.
	*
	* @param format the format
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByFormat_First(java.lang.String format,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByFormat_First(format, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where format = &#63;.
	*
	* @param format the format
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByFormat_Last(java.lang.String format,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByFormat_Last(format, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where format = &#63;.
	*
	* @param format the format
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByFormat_Last(java.lang.String format,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByFormat_Last(format, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where format = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param format the format
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByFormat_PrevAndNext(long pageId,
		java.lang.String format, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByFormat_PrevAndNext(pageId, format, orderByComparator);
	}

	/**
	* Removes all the wiki pages where format = &#63; from the database.
	*
	* @param format the format
	*/
	public static void removeByFormat(java.lang.String format) {
		getPersistence().removeByFormat(format);
	}

	/**
	* Returns the number of wiki pages where format = &#63;.
	*
	* @param format the format
	* @return the number of matching wiki pages
	*/
	public static int countByFormat(java.lang.String format) {
		return getPersistence().countByFormat(format);
	}

	/**
	* Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByR_N(long resourcePrimKey, long nodeId) {
		return getPersistence().findByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end) {
		return getPersistence().findByR_N(resourcePrimKey, nodeId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByR_N(resourcePrimKey, nodeId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_N(resourcePrimKey, nodeId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_First(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_First(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_First(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_First(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_Last(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_Last(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_Last(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_Last(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByR_N_PrevAndNext(long pageId,
		long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_PrevAndNext(pageId, resourcePrimKey, nodeId,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	*/
	public static void removeByR_N(long resourcePrimKey, long nodeId) {
		getPersistence().removeByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @return the number of matching wiki pages
	*/
	public static int countByR_N(long resourcePrimKey, long nodeId) {
		return getPersistence().countByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Returns all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByR_S(long resourcePrimKey, int status) {
		return getPersistence().findByR_S(resourcePrimKey, status);
	}

	/**
	* Returns a range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end) {
		return getPersistence().findByR_S(resourcePrimKey, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByR_S(resourcePrimKey, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_S(resourcePrimKey, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_S_First(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_S_First(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_S_First(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_First(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_S_Last(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_Last(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByR_S_PrevAndNext(long pageId,
		long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_S_PrevAndNext(pageId, resourcePrimKey, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	*/
	public static void removeByR_S(long resourcePrimKey, int status) {
		getPersistence().removeByR_S(resourcePrimKey, status);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByR_S(long resourcePrimKey, int status) {
		return getPersistence().countByR_S(resourcePrimKey, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_T(long nodeId, java.lang.String title) {
		return getPersistence().findByN_T(nodeId, title);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T(long nodeId, java.lang.String title,
		int start, int end) {
		return getPersistence().findByN_T(nodeId, title, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T(long nodeId, java.lang.String title,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_T(nodeId, title, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T(long nodeId, java.lang.String title,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_T(nodeId, title, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_First(long nodeId, java.lang.String title,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_T_First(nodeId, title, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_First(long nodeId,
		java.lang.String title, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_T_First(nodeId, title, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_Last(long nodeId, java.lang.String title,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_T_Last(nodeId, title, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_Last(long nodeId, java.lang.String title,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByN_T_Last(nodeId, title, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_T_PrevAndNext(long pageId, long nodeId,
		java.lang.String title, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_PrevAndNext(pageId, nodeId, title,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	*/
	public static void removeByN_T(long nodeId, java.lang.String title) {
		getPersistence().removeByN_T(nodeId, title);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the number of matching wiki pages
	*/
	public static int countByN_T(long nodeId, java.lang.String title) {
		return getPersistence().countByN_T(nodeId, title);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H(long nodeId, boolean head) {
		return getPersistence().findByN_H(nodeId, head);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H(long nodeId, boolean head,
		int start, int end) {
		return getPersistence().findByN_H(nodeId, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H(long nodeId, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H(nodeId, head, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H(long nodeId, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H(nodeId, head, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_First(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_H_First(nodeId, head, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_First(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByN_H_First(nodeId, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_Last(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_H_Last(nodeId, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_Last(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().fetchByN_H_Last(nodeId, head, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_PrevAndNext(long pageId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_PrevAndNext(pageId, nodeId, head,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	*/
	public static void removeByN_H(long nodeId, boolean head) {
		getPersistence().removeByN_H(nodeId, head);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @return the number of matching wiki pages
	*/
	public static int countByN_H(long nodeId, boolean head) {
		return getPersistence().countByN_H(nodeId, head);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_P(long nodeId,
		java.lang.String parentTitle) {
		return getPersistence().findByN_P(nodeId, parentTitle);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_P(long nodeId,
		java.lang.String parentTitle, int start, int end) {
		return getPersistence().findByN_P(nodeId, parentTitle, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_P(long nodeId,
		java.lang.String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_P(nodeId, parentTitle, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_P(long nodeId,
		java.lang.String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_P(nodeId, parentTitle, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_P_First(long nodeId,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_P_First(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_P_First(long nodeId,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_P_First(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_P_Last(long nodeId,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_P_Last(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_P_Last(long nodeId,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_P_Last(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_P_PrevAndNext(long pageId, long nodeId,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_P_PrevAndNext(pageId, nodeId, parentTitle,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and parentTitle = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	*/
	public static void removeByN_P(long nodeId, java.lang.String parentTitle) {
		getPersistence().removeByN_P(nodeId, parentTitle);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param parentTitle the parent title
	* @return the number of matching wiki pages
	*/
	public static int countByN_P(long nodeId, java.lang.String parentTitle) {
		return getPersistence().countByN_P(nodeId, parentTitle);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_R(long nodeId,
		java.lang.String redirectTitle) {
		return getPersistence().findByN_R(nodeId, redirectTitle);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_R(long nodeId,
		java.lang.String redirectTitle, int start, int end) {
		return getPersistence().findByN_R(nodeId, redirectTitle, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_R(long nodeId,
		java.lang.String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_R(nodeId, redirectTitle, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_R(long nodeId,
		java.lang.String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_R(nodeId, redirectTitle, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_R_First(long nodeId,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_R_First(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_R_First(long nodeId,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_R_First(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_R_Last(long nodeId,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_R_Last(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_R_Last(long nodeId,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_R_Last(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_R_PrevAndNext(long pageId, long nodeId,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_R_PrevAndNext(pageId, nodeId, redirectTitle,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and redirectTitle = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	*/
	public static void removeByN_R(long nodeId, java.lang.String redirectTitle) {
		getPersistence().removeByN_R(nodeId, redirectTitle);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param redirectTitle the redirect title
	* @return the number of matching wiki pages
	*/
	public static int countByN_R(long nodeId, java.lang.String redirectTitle) {
		return getPersistence().countByN_R(nodeId, redirectTitle);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_S(long nodeId, int status) {
		return getPersistence().findByN_S(nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end) {
		return getPersistence().findByN_S(nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_S(nodeId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_S(nodeId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_S_First(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_S_First(nodeId, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_S_First(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_S_First(nodeId, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_S_Last(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_S_Last(nodeId, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_S_Last(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_S_Last(nodeId, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_S_PrevAndNext(long pageId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_S_PrevAndNext(pageId, nodeId, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param status the status
	*/
	public static void removeByN_S(long nodeId, int status) {
		getPersistence().removeByN_S(nodeId, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_S(long nodeId, int status) {
		return getPersistence().countByN_S(nodeId, status);
	}

	/**
	* Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param version the version
	* @return the matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_V(long resourcePrimKey, long nodeId,
		double version) throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param version the version
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_V(long resourcePrimKey, long nodeId,
		double version) {
		return getPersistence().fetchByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_V(long resourcePrimKey, long nodeId,
		double version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByR_N_V(resourcePrimKey, nodeId, version,
			retrieveFromCache);
	}

	/**
	* Removes the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param version the version
	* @return the wiki page that was removed
	*/
	public static WikiPage removeByR_N_V(long resourcePrimKey, long nodeId,
		double version) throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().removeByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param version the version
	* @return the number of matching wiki pages
	*/
	public static int countByR_N_V(long resourcePrimKey, long nodeId,
		double version) {
		return getPersistence().countByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head) {
		return getPersistence().findByR_N_H(resourcePrimKey, nodeId, head);
	}

	/**
	* Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end) {
		return getPersistence()
				   .findByR_N_H(resourcePrimKey, nodeId, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByR_N_H(resourcePrimKey, nodeId, head, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_N_H(resourcePrimKey, nodeId, head, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_H_First(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_H_First(resourcePrimKey, nodeId, head,
			orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_H_First(long resourcePrimKey,
		long nodeId, boolean head, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_H_First(resourcePrimKey, nodeId, head,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_H_Last(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_H_Last(resourcePrimKey, nodeId, head,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_H_Last(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_H_Last(resourcePrimKey, nodeId, head,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByR_N_H_PrevAndNext(long pageId,
		long resourcePrimKey, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_H_PrevAndNext(pageId, resourcePrimKey, nodeId,
			head, orderByComparator);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	*/
	public static void removeByR_N_H(long resourcePrimKey, long nodeId,
		boolean head) {
		getPersistence().removeByR_N_H(resourcePrimKey, nodeId, head);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param head the head
	* @return the number of matching wiki pages
	*/
	public static int countByR_N_H(long resourcePrimKey, long nodeId,
		boolean head) {
		return getPersistence().countByR_N_H(resourcePrimKey, nodeId, head);
	}

	/**
	* Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status) {
		return getPersistence().findByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end) {
		return getPersistence()
				   .findByR_N_S(resourcePrimKey, nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByR_N_S(resourcePrimKey, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_N_S(resourcePrimKey, nodeId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_S_First(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_First(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_S_First(long resourcePrimKey,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_S_First(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByR_N_S_Last(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_Last(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByR_N_S_Last(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByR_N_S_Last(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByR_N_S_PrevAndNext(long pageId,
		long resourcePrimKey, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_PrevAndNext(pageId, resourcePrimKey, nodeId,
			status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	*/
	public static void removeByR_N_S(long resourcePrimKey, long nodeId,
		int status) {
		getPersistence().removeByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByR_N_S(long resourcePrimKey, long nodeId, int status) {
		return getPersistence().countByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H(long groupId, long nodeId,
		boolean head) {
		return getPersistence().findByG_N_H(groupId, nodeId, head);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end) {
		return getPersistence().findByG_N_H(groupId, nodeId, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_N_H(groupId, nodeId, head, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N_H(groupId, nodeId, head, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_First(long groupId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_First(groupId, nodeId, head, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByG_N_H_First(long groupId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_First(groupId, nodeId, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_Last(long groupId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_Last(groupId, nodeId, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByG_N_H_Last(long groupId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_Last(groupId, nodeId, head, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_N_H_PrevAndNext(long pageId, long groupId,
		long nodeId, boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_PrevAndNext(pageId, groupId, nodeId, head,
			orderByComparator);
	}

	/**
	* Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @return the matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head) {
		return getPersistence().filterFindByG_N_H(groupId, nodeId, head);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end) {
		return getPersistence()
				   .filterFindByG_N_H(groupId, nodeId, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_H(groupId, nodeId, head, start, end,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_N_H_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_N_H_PrevAndNext(pageId, groupId, nodeId,
			head, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; from the database.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	*/
	public static void removeByG_N_H(long groupId, long nodeId, boolean head) {
		getPersistence().removeByG_N_H(groupId, nodeId, head);
	}

	/**
	* Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @return the number of matching wiki pages
	*/
	public static int countByG_N_H(long groupId, long nodeId, boolean head) {
		return getPersistence().countByG_N_H(groupId, nodeId, head);
	}

	/**
	* Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @return the number of matching wiki pages that the user has permission to view
	*/
	public static int filterCountByG_N_H(long groupId, long nodeId, boolean head) {
		return getPersistence().filterCountByG_N_H(groupId, nodeId, head);
	}

	/**
	* Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByG_N_S(long groupId, long nodeId,
		int status) {
		return getPersistence().findByG_N_S(groupId, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_S(long groupId, long nodeId,
		int status, int start, int end) {
		return getPersistence().findByG_N_S(groupId, nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_S(long groupId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_N_S(groupId, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_S(long groupId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N_S(groupId, nodeId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_S_First(long groupId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_S_First(groupId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByG_N_S_First(long groupId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_S_First(groupId, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_S_Last(long groupId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_S_Last(groupId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByG_N_S_Last(long groupId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_S_Last(groupId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_N_S_PrevAndNext(long pageId, long groupId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_S_PrevAndNext(pageId, groupId, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status) {
		return getPersistence().filterFindByG_N_S(groupId, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_N_S(groupId, nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_S(groupId, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_N_S_PrevAndNext(long pageId,
		long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_N_S_PrevAndNext(pageId, groupId, nodeId,
			status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	*/
	public static void removeByG_N_S(long groupId, long nodeId, int status) {
		getPersistence().removeByG_N_S(groupId, nodeId, status);
	}

	/**
	* Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByG_N_S(long groupId, long nodeId, int status) {
		return getPersistence().countByG_N_S(groupId, nodeId, status);
	}

	/**
	* Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages that the user has permission to view
	*/
	public static int filterCountByG_N_S(long groupId, long nodeId, int status) {
		return getPersistence().filterCountByG_N_S(groupId, nodeId, status);
	}

	/**
	* Returns all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByU_N_S(long userId, long nodeId,
		int status) {
		return getPersistence().findByU_N_S(userId, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByU_N_S(long userId, long nodeId,
		int status, int start, int end) {
		return getPersistence().findByU_N_S(userId, nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByU_N_S(long userId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByU_N_S(userId, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByU_N_S(long userId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_N_S(userId, nodeId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByU_N_S_First(long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_First(userId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByU_N_S_First(long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByU_N_S_First(userId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByU_N_S_Last(long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_Last(userId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByU_N_S_Last(long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByU_N_S_Last(userId, nodeId, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByU_N_S_PrevAndNext(long pageId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_PrevAndNext(pageId, userId, nodeId, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	*/
	public static void removeByU_N_S(long userId, long nodeId, int status) {
		getPersistence().removeByU_N_S(userId, nodeId, status);
	}

	/**
	* Returns the number of wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByU_N_S(long userId, long nodeId, int status) {
		return getPersistence().countByU_N_S(userId, nodeId, status);
	}

	/**
	* Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param version the version
	* @return the matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_V(long nodeId, java.lang.String title,
		double version) throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByN_T_V(nodeId, title, version);
	}

	/**
	* Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param version the version
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_V(long nodeId, java.lang.String title,
		double version) {
		return getPersistence().fetchByN_T_V(nodeId, title, version);
	}

	/**
	* Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_V(long nodeId, java.lang.String title,
		double version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByN_T_V(nodeId, title, version, retrieveFromCache);
	}

	/**
	* Removes the wiki page where nodeId = &#63; and title = &#63; and version = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param version the version
	* @return the wiki page that was removed
	*/
	public static WikiPage removeByN_T_V(long nodeId, java.lang.String title,
		double version) throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().removeByN_T_V(nodeId, title, version);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and title = &#63; and version = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param version the version
	* @return the number of matching wiki pages
	*/
	public static int countByN_T_V(long nodeId, java.lang.String title,
		double version) {
		return getPersistence().countByN_T_V(nodeId, title, version);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_T_H(long nodeId,
		java.lang.String title, boolean head) {
		return getPersistence().findByN_T_H(nodeId, title, head);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T_H(long nodeId,
		java.lang.String title, boolean head, int start, int end) {
		return getPersistence().findByN_T_H(nodeId, title, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_T_H(long nodeId,
		java.lang.String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_T_H(nodeId, title, head, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T_H(long nodeId,
		java.lang.String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_T_H(nodeId, title, head, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_H_First(long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_First(nodeId, title, head, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_H_First(long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_T_H_First(nodeId, title, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_H_Last(long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_Last(nodeId, title, head, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_H_Last(long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_T_H_Last(nodeId, title, head, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_T_H_PrevAndNext(long pageId, long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_PrevAndNext(pageId, nodeId, title, head,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	*/
	public static void removeByN_T_H(long nodeId, java.lang.String title,
		boolean head) {
		getPersistence().removeByN_T_H(nodeId, title, head);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the number of matching wiki pages
	*/
	public static int countByN_T_H(long nodeId, java.lang.String title,
		boolean head) {
		return getPersistence().countByN_T_H(nodeId, title, head);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_T_S(long nodeId,
		java.lang.String title, int status) {
		return getPersistence().findByN_T_S(nodeId, title, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T_S(long nodeId,
		java.lang.String title, int status, int start, int end) {
		return getPersistence().findByN_T_S(nodeId, title, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_T_S(long nodeId,
		java.lang.String title, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_T_S(nodeId, title, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_T_S(long nodeId,
		java.lang.String title, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_T_S(nodeId, title, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_S_First(long nodeId,
		java.lang.String title, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_First(nodeId, title, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_S_First(long nodeId,
		java.lang.String title, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_T_S_First(nodeId, title, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_T_S_Last(long nodeId,
		java.lang.String title, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_Last(nodeId, title, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_T_S_Last(long nodeId,
		java.lang.String title, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_T_S_Last(nodeId, title, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_T_S_PrevAndNext(long pageId, long nodeId,
		java.lang.String title, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_PrevAndNext(pageId, nodeId, title, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	*/
	public static void removeByN_T_S(long nodeId, java.lang.String title,
		int status) {
		getPersistence().removeByN_T_S(nodeId, title, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_T_S(long nodeId, java.lang.String title,
		int status) {
		return getPersistence().countByN_T_S(nodeId, title, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) {
		return getPersistence().findByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle, int start, int end) {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_First(long nodeId, boolean head,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_First(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_P_First(long nodeId, boolean head,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_First(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_Last(long nodeId, boolean head,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_Last(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_P_Last(long nodeId, boolean head,
		java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_Last(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_P_PrevAndNext(long pageId, long nodeId,
		boolean head, java.lang.String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_PrevAndNext(pageId, nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	*/
	public static void removeByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) {
		getPersistence().removeByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) {
		return getPersistence().countByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle) {
		return getPersistence().findByN_H_R(nodeId, head, redirectTitle);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle, int start, int end) {
		return getPersistence()
				   .findByN_H_R(nodeId, head, redirectTitle, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_R(nodeId, head, redirectTitle, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_R(nodeId, head, redirectTitle, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_First(long nodeId, boolean head,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_First(nodeId, head, redirectTitle,
			orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_R_First(long nodeId, boolean head,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_First(nodeId, head, redirectTitle,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_Last(long nodeId, boolean head,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_Last(nodeId, head, redirectTitle,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_R_Last(long nodeId, boolean head,
		java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_Last(nodeId, head, redirectTitle,
			orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_R_PrevAndNext(long pageId, long nodeId,
		boolean head, java.lang.String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_PrevAndNext(pageId, nodeId, head,
			redirectTitle, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	*/
	public static void removeByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle) {
		getPersistence().removeByN_H_R(nodeId, head, redirectTitle);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_R(long nodeId, boolean head,
		java.lang.String redirectTitle) {
		return getPersistence().countByN_H_R(nodeId, head, redirectTitle);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_S(long nodeId, boolean head,
		int status) {
		return getPersistence().findByN_H_S(nodeId, head, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_S(long nodeId, boolean head,
		int status, int start, int end) {
		return getPersistence().findByN_H_S(nodeId, head, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_S(long nodeId, boolean head,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_S(nodeId, head, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_S(long nodeId, boolean head,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_S(nodeId, head, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_S_First(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_First(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_S_First(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_S_First(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_S_Last(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_Last(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_S_Last(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_S_Last(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_S_PrevAndNext(long pageId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_PrevAndNext(pageId, nodeId, head, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	*/
	public static void removeByN_H_S(long nodeId, boolean head, int status) {
		getPersistence().removeByN_H_S(nodeId, head, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_S(long nodeId, boolean head, int status) {
		return getPersistence().countByN_H_S(nodeId, head, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_NotS(long nodeId, boolean head,
		int status) {
		return getPersistence().findByN_H_NotS(nodeId, head, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_NotS(long nodeId, boolean head,
		int status, int start, int end) {
		return getPersistence().findByN_H_NotS(nodeId, head, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_NotS(long nodeId, boolean head,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_NotS(nodeId, head, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_NotS(long nodeId, boolean head,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_NotS(nodeId, head, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_NotS_First(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_NotS_First(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_NotS_First(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_NotS_First(nodeId, head, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_NotS_Last(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_NotS_Last(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	public static WikiPage fetchByN_H_NotS_Last(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_NotS_Last(nodeId, head, status, orderByComparator);
	}

	/**
	* Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_NotS_PrevAndNext(long pageId,
		long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_NotS_PrevAndNext(pageId, nodeId, head, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	*/
	public static void removeByN_H_NotS(long nodeId, boolean head, int status) {
		getPersistence().removeByN_H_NotS(nodeId, head, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_NotS(long nodeId, boolean head, int status) {
		return getPersistence().countByN_H_NotS(nodeId, head, status);
	}

	/**
	* Returns all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByG_U_N_S(long groupId, long userId,
		long nodeId, int status) {
		return getPersistence().findByG_U_N_S(groupId, userId, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end) {
		return getPersistence()
				   .findByG_U_N_S(groupId, userId, nodeId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_U_N_S(groupId, userId, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_N_S(groupId, userId, nodeId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_U_N_S_First(long groupId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_U_N_S_First(groupId, userId, nodeId, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_U_N_S_First(long groupId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_N_S_First(groupId, userId, nodeId, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_U_N_S_Last(long groupId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_U_N_S_Last(groupId, userId, nodeId, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_U_N_S_Last(long groupId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_N_S_Last(groupId, userId, nodeId, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_U_N_S_PrevAndNext(long pageId,
		long groupId, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_U_N_S_PrevAndNext(pageId, groupId, userId, nodeId,
			status, orderByComparator);
	}

	/**
	* Returns all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status) {
		return getPersistence()
				   .filterFindByG_U_N_S(groupId, userId, nodeId, status);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_N_S(groupId, userId, nodeId, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_N_S(groupId, userId, nodeId, status, start,
			end, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_U_N_S_PrevAndNext(long pageId,
		long groupId, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_U_N_S_PrevAndNext(pageId, groupId, userId,
			nodeId, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	*/
	public static void removeByG_U_N_S(long groupId, long userId, long nodeId,
		int status) {
		getPersistence().removeByG_U_N_S(groupId, userId, nodeId, status);
	}

	/**
	* Returns the number of wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByG_U_N_S(long groupId, long userId, long nodeId,
		int status) {
		return getPersistence().countByG_U_N_S(groupId, userId, nodeId, status);
	}

	/**
	* Returns the number of wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param nodeId the node ID
	* @param status the status
	* @return the number of matching wiki pages that the user has permission to view
	*/
	public static int filterCountByG_U_N_S(long groupId, long userId,
		long nodeId, int status) {
		return getPersistence()
				   .filterCountByG_U_N_S(groupId, userId, nodeId, status);
	}

	/**
	* Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head) {
		return getPersistence().findByG_N_T_H(groupId, nodeId, title, head);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head, int start, int end) {
		return getPersistence()
				   .findByG_N_T_H(groupId, nodeId, title, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_N_T_H(groupId, nodeId, title, head, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N_T_H(groupId, nodeId, title, head, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_T_H_First(long groupId, long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_T_H_First(groupId, nodeId, title, head,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_N_T_H_First(long groupId, long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_T_H_First(groupId, nodeId, title, head,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_T_H_Last(long groupId, long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_T_H_Last(groupId, nodeId, title, head,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_N_T_H_Last(long groupId, long nodeId,
		java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_T_H_Last(groupId, nodeId, title, head,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_N_T_H_PrevAndNext(long pageId,
		long groupId, long nodeId, java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_T_H_PrevAndNext(pageId, groupId, nodeId, title,
			head, orderByComparator);
	}

	/**
	* Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head) {
		return getPersistence().filterFindByG_N_T_H(groupId, nodeId, title, head);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head, int start, int end) {
		return getPersistence()
				   .filterFindByG_N_T_H(groupId, nodeId, title, head, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_T_H(groupId, nodeId, title, head, start,
			end, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_N_T_H_PrevAndNext(long pageId,
		long groupId, long nodeId, java.lang.String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_N_T_H_PrevAndNext(pageId, groupId, nodeId,
			title, head, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63; from the database.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	*/
	public static void removeByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head) {
		getPersistence().removeByG_N_T_H(groupId, nodeId, title, head);
	}

	/**
	* Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the number of matching wiki pages
	*/
	public static int countByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head) {
		return getPersistence().countByG_N_T_H(groupId, nodeId, title, head);
	}

	/**
	* Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param title the title
	* @param head the head
	* @return the number of matching wiki pages that the user has permission to view
	*/
	public static int filterCountByG_N_T_H(long groupId, long nodeId,
		java.lang.String title, boolean head) {
		return getPersistence()
				   .filterCountByG_N_T_H(groupId, nodeId, title, head);
	}

	/**
	* Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status) {
		return getPersistence().findByG_N_H_S(groupId, nodeId, head, status);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end) {
		return getPersistence()
				   .findByG_N_H_S(groupId, nodeId, head, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_N_H_S(groupId, nodeId, head, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N_H_S(groupId, nodeId, head, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_S_First(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_S_First(groupId, nodeId, head, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_N_H_S_First(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_S_First(groupId, nodeId, head, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_S_Last(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_S_Last(groupId, nodeId, head, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByG_N_H_S_Last(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_S_Last(groupId, nodeId, head, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_N_H_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_S_PrevAndNext(pageId, groupId, nodeId, head,
			status, orderByComparator);
	}

	/**
	* Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the matching wiki pages that the user has permission to view
	*/
	public static List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status) {
		return getPersistence()
				   .filterFindByG_N_H_S(groupId, nodeId, head, status);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_N_H_S(groupId, nodeId, head, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_H_S(groupId, nodeId, head, status, start,
			end, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_N_H_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_N_H_S_PrevAndNext(pageId, groupId, nodeId,
			head, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	*/
	public static void removeByG_N_H_S(long groupId, long nodeId, boolean head,
		int status) {
		getPersistence().removeByG_N_H_S(groupId, nodeId, head, status);
	}

	/**
	* Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByG_N_H_S(long groupId, long nodeId, boolean head,
		int status) {
		return getPersistence().countByG_N_H_S(groupId, nodeId, head, status);
	}

	/**
	* Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param status the status
	* @return the number of matching wiki pages that the user has permission to view
	*/
	public static int filterCountByG_N_H_S(long groupId, long nodeId,
		boolean head, int status) {
		return getPersistence()
				   .filterCountByG_N_H_S(groupId, nodeId, head, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return getPersistence().findByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end) {
		return getPersistence()
				   .findByN_H_P_S(nodeId, head, parentTitle, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_P_S(nodeId, head, parentTitle, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_P_S(nodeId, head, parentTitle, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_S_First(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_First(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_P_S_First(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_S_First(nodeId, head, parentTitle, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_S_Last(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_Last(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_P_S_Last(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_S_Last(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_P_S_PrevAndNext(long pageId,
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_PrevAndNext(pageId, nodeId, head,
			parentTitle, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	*/
	public static void removeByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		getPersistence().removeByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return getPersistence().countByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return getPersistence()
				   .findByN_H_P_NotS(nodeId, head, parentTitle, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end) {
		return getPersistence()
				   .findByN_H_P_NotS(nodeId, head, parentTitle, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_P_NotS(nodeId, head, parentTitle, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_P_NotS(nodeId, head, parentTitle, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_NotS_First(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_NotS_First(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_P_NotS_First(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_NotS_First(nodeId, head, parentTitle, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_P_NotS_Last(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_NotS_Last(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_P_NotS_Last(long nodeId, boolean head,
		java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_P_NotS_Last(nodeId, head, parentTitle, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_P_NotS_PrevAndNext(long pageId,
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_NotS_PrevAndNext(pageId, nodeId, head,
			parentTitle, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	*/
	public static void removeByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		getPersistence().removeByN_H_P_NotS(nodeId, head, parentTitle, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_P_NotS(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return getPersistence()
				   .countByN_H_P_NotS(nodeId, head, parentTitle, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		return getPersistence()
				   .findByN_H_R_S(nodeId, head, redirectTitle, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end) {
		return getPersistence()
				   .findByN_H_R_S(nodeId, head, redirectTitle, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_R_S(nodeId, head, redirectTitle, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_R_S(nodeId, head, redirectTitle, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_S_First(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_S_First(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_R_S_First(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_S_First(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_S_Last(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_S_Last(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_R_S_Last(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_S_Last(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_R_S_PrevAndNext(long pageId,
		long nodeId, boolean head, java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_S_PrevAndNext(pageId, nodeId, head,
			redirectTitle, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	*/
	public static void removeByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		getPersistence().removeByN_H_R_S(nodeId, head, redirectTitle, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_R_S(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		return getPersistence()
				   .countByN_H_R_S(nodeId, head, redirectTitle, status);
	}

	/**
	* Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @return the matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		return getPersistence()
				   .findByN_H_R_NotS(nodeId, head, redirectTitle, status);
	}

	/**
	* Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end) {
		return getPersistence()
				   .findByN_H_R_NotS(nodeId, head, redirectTitle, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByN_H_R_NotS(nodeId, head, redirectTitle, status,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_H_R_NotS(nodeId, head, redirectTitle, status,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_NotS_First(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_NotS_First(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_R_NotS_First(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_NotS_First(nodeId, head, redirectTitle,
			status, orderByComparator);
	}

	/**
	* Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByN_H_R_NotS_Last(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_NotS_Last(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	public static WikiPage fetchByN_H_R_NotS_Last(long nodeId, boolean head,
		java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByN_H_R_NotS_Last(nodeId, head, redirectTitle, status,
			orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByN_H_R_NotS_PrevAndNext(long pageId,
		long nodeId, boolean head, java.lang.String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByN_H_R_NotS_PrevAndNext(pageId, nodeId, head,
			redirectTitle, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63; from the database.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	*/
	public static void removeByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		getPersistence().removeByN_H_R_NotS(nodeId, head, redirectTitle, status);
	}

	/**
	* Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	*
	* @param nodeId the node ID
	* @param head the head
	* @param redirectTitle the redirect title
	* @param status the status
	* @return the number of matching wiki pages
	*/
	public static int countByN_H_R_NotS(long nodeId, boolean head,
		java.lang.String redirectTitle, int status) {
		return getPersistence()
				   .countByN_H_R_NotS(nodeId, head, redirectTitle, status);
	}

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
	public static List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status) {
		return getPersistence()
				   .findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status);
	}

	/**
	* Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status, int start,
		int end) {
		return getPersistence()
				   .findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki pages
	*/
	public static List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			start, end, orderByComparator, retrieveFromCache);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_P_S_First(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_P_S_First(groupId, nodeId, head, parentTitle,
			status, orderByComparator);
	}

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
	public static WikiPage fetchByG_N_H_P_S_First(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_P_S_First(groupId, nodeId, head, parentTitle,
			status, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	*/
	public static WikiPage findByG_N_H_P_S_Last(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_P_S_Last(groupId, nodeId, head, parentTitle,
			status, orderByComparator);
	}

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
	public static WikiPage fetchByG_N_H_P_S_Last(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_H_P_S_Last(groupId, nodeId, head, parentTitle,
			status, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] findByG_N_H_P_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, java.lang.String parentTitle,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .findByG_N_H_P_S_PrevAndNext(pageId, groupId, nodeId, head,
			parentTitle, status, orderByComparator);
	}

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
	public static List<WikiPage> filterFindByG_N_H_P_S(long groupId,
		long nodeId, boolean head, java.lang.String parentTitle, int status) {
		return getPersistence()
				   .filterFindByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status);
	}

	/**
	* Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_H_P_S(long groupId,
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		int start, int end) {
		return getPersistence()
				   .filterFindByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WikiPage> filterFindByG_N_H_P_S(long groupId,
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status, start, end, orderByComparator);
	}

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
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage[] filterFindByG_N_H_P_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, java.lang.String parentTitle,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence()
				   .filterFindByG_N_H_P_S_PrevAndNext(pageId, groupId, nodeId,
			head, parentTitle, status, orderByComparator);
	}

	/**
	* Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param nodeId the node ID
	* @param head the head
	* @param parentTitle the parent title
	* @param status the status
	*/
	public static void removeByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status) {
		getPersistence()
			.removeByG_N_H_P_S(groupId, nodeId, head, parentTitle, status);
	}

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
	public static int countByG_N_H_P_S(long groupId, long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return getPersistence()
				   .countByG_N_H_P_S(groupId, nodeId, head, parentTitle, status);
	}

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
	public static int filterCountByG_N_H_P_S(long groupId, long nodeId,
		boolean head, java.lang.String parentTitle, int status) {
		return getPersistence()
				   .filterCountByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status);
	}

	/**
	* Caches the wiki page in the entity cache if it is enabled.
	*
	* @param wikiPage the wiki page
	*/
	public static void cacheResult(WikiPage wikiPage) {
		getPersistence().cacheResult(wikiPage);
	}

	/**
	* Caches the wiki pages in the entity cache if it is enabled.
	*
	* @param wikiPages the wiki pages
	*/
	public static void cacheResult(List<WikiPage> wikiPages) {
		getPersistence().cacheResult(wikiPages);
	}

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public static WikiPage create(long pageId) {
		return getPersistence().create(pageId);
	}

	/**
	* Removes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage remove(long pageId)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().remove(pageId);
	}

	public static WikiPage updateImpl(WikiPage wikiPage) {
		return getPersistence().updateImpl(wikiPage);
	}

	/**
	* Returns the wiki page with the primary key or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	*/
	public static WikiPage findByPrimaryKey(long pageId)
		throws com.liferay.wiki.exception.NoSuchPageException {
		return getPersistence().findByPrimaryKey(pageId);
	}

	/**
	* Returns the wiki page with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page, or <code>null</code> if a wiki page with the primary key could not be found
	*/
	public static WikiPage fetchByPrimaryKey(long pageId) {
		return getPersistence().fetchByPrimaryKey(pageId);
	}

	public static java.util.Map<java.io.Serializable, WikiPage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the wiki pages.
	*
	* @return the wiki pages
	*/
	public static List<WikiPage> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of wiki pages
	*/
	public static List<WikiPage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki pages
	*/
	public static List<WikiPage> findAll(int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wiki pages
	*/
	public static List<WikiPage> findAll(int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the wiki pages from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static WikiPagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WikiPagePersistence, WikiPagePersistence> _serviceTracker =
		ServiceTrackerFactory.open(WikiPagePersistence.class);
}