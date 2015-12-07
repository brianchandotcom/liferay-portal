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

import com.liferay.wiki.model.WikiPageResource;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the wiki page resource service. This utility wraps {@link com.liferay.wiki.service.persistence.impl.WikiPageResourcePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourcePersistence
 * @see com.liferay.wiki.service.persistence.impl.WikiPageResourcePersistenceImpl
 * @generated
 */
@ProviderType
public class WikiPageResourceUtil {
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
	public static void clearCache(WikiPageResource wikiPageResource) {
		getPersistence().clearCache(wikiPageResource);
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
	public static List<WikiPageResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WikiPageResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WikiPageResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WikiPageResource update(WikiPageResource wikiPageResource) {
		return getPersistence().update(wikiPageResource);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WikiPageResource update(WikiPageResource wikiPageResource,
		ServiceContext serviceContext) {
		return getPersistence().update(wikiPageResource, serviceContext);
	}

	/**
	* Returns all the wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByUuid_First(java.lang.String uuid,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByUuid_Last(java.lang.String uuid,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param resourcePrimKey the primary key of the current wiki page resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public static WikiPageResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(resourcePrimKey, uuid,
			orderByComparator);
	}

	/**
	* Removes all the wiki page resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki page resources
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the wiki page resource where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the wiki page resource that was removed
	*/
	public static WikiPageResource removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of wiki page resources where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching wiki page resources
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki page resources
	*/
	public static List<WikiPageResource> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current wiki page resource
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public static WikiPageResource[] findByUuid_C_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(resourcePrimKey, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the wiki page resources where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wiki page resources
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public static WikiPageResource findByN_T(long nodeId, java.lang.String title)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().findByN_T(nodeId, title);
	}

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByN_T(long nodeId,
		java.lang.String title) {
		return getPersistence().fetchByN_T(nodeId, title);
	}

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public static WikiPageResource fetchByN_T(long nodeId,
		java.lang.String title, boolean retrieveFromCache) {
		return getPersistence().fetchByN_T(nodeId, title, retrieveFromCache);
	}

	/**
	* Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the wiki page resource that was removed
	*/
	public static WikiPageResource removeByN_T(long nodeId,
		java.lang.String title)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().removeByN_T(nodeId, title);
	}

	/**
	* Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the number of matching wiki page resources
	*/
	public static int countByN_T(long nodeId, java.lang.String title) {
		return getPersistence().countByN_T(nodeId, title);
	}

	/**
	* Caches the wiki page resource in the entity cache if it is enabled.
	*
	* @param wikiPageResource the wiki page resource
	*/
	public static void cacheResult(WikiPageResource wikiPageResource) {
		getPersistence().cacheResult(wikiPageResource);
	}

	/**
	* Caches the wiki page resources in the entity cache if it is enabled.
	*
	* @param wikiPageResources the wiki page resources
	*/
	public static void cacheResult(List<WikiPageResource> wikiPageResources) {
		getPersistence().cacheResult(wikiPageResources);
	}

	/**
	* Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	*
	* @param resourcePrimKey the primary key for the new wiki page resource
	* @return the new wiki page resource
	*/
	public static WikiPageResource create(long resourcePrimKey) {
		return getPersistence().create(resourcePrimKey);
	}

	/**
	* Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource that was removed
	* @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public static WikiPageResource remove(long resourcePrimKey)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().remove(resourcePrimKey);
	}

	public static WikiPageResource updateImpl(WikiPageResource wikiPageResource) {
		return getPersistence().updateImpl(wikiPageResource);
	}

	/**
	* Returns the wiki page resource with the primary key or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource
	* @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public static WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws com.liferay.wiki.exception.NoSuchPageResourceException {
		return getPersistence().findByPrimaryKey(resourcePrimKey);
	}

	/**
	* Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	*/
	public static WikiPageResource fetchByPrimaryKey(long resourcePrimKey) {
		return getPersistence().fetchByPrimaryKey(resourcePrimKey);
	}

	public static java.util.Map<java.io.Serializable, WikiPageResource> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the wiki page resources.
	*
	* @return the wiki page resources
	*/
	public static List<WikiPageResource> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of wiki page resources
	*/
	public static List<WikiPageResource> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki page resources
	*/
	public static List<WikiPageResource> findAll(int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wiki page resources
	*/
	public static List<WikiPageResource> findAll(int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the wiki page resources from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of wiki page resources.
	*
	* @return the number of wiki page resources
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static WikiPageResourcePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(WikiPageResourcePersistence persistence) {
	}

	private static ServiceTracker<WikiPageResourcePersistence, WikiPageResourcePersistence> _serviceTracker =
		ServiceTrackerFactory.open(WikiPageResourcePersistence.class);
}