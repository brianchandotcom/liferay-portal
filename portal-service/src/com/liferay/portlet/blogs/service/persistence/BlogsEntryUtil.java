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

package com.liferay.portlet.blogs.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.List;

/**
 * The persistence utility for the blogs entry service. This utility wraps {@link com.liferay.portlet.blogs.service.persistence.impl.BlogsEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryPersistence
 * @see com.liferay.portlet.blogs.service.persistence.impl.BlogsEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class BlogsEntryUtil {
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
	public static void clearCache(BlogsEntry blogsEntry) {
		getPersistence().clearCache(blogsEntry);
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
	public static List<BlogsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BlogsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BlogsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static BlogsEntry update(BlogsEntry blogsEntry) {
		return getPersistence().update(blogsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static BlogsEntry update(BlogsEntry blogsEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(blogsEntry, serviceContext);
	}

	/**
	* Returns all the blogs entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByUuid_First(java.lang.String uuid,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByUuid_Last(java.lang.String uuid,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByUuid_PrevAndNext(long entryId,
		java.lang.String uuid, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(entryId, uuid, orderByComparator);
	}

	/**
	* Removes all the blogs entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of blogs entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching blogs entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the blogs entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the blogs entry that was removed
	*/
	public static BlogsEntry removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of blogs entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching blogs entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(entryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching blogs entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the blogs entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByGroupId_First(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByGroupId_First(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByGroupId_Last(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(entryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(entryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching blogs entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the blogs entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByCompanyId_First(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByCompanyId_Last(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching blogs entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_UT(long groupId, java.lang.String urlTitle) {
		return getPersistence().fetchByG_UT(groupId, urlTitle);
	}

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_UT(long groupId,
		java.lang.String urlTitle, boolean retrieveFromCache) {
		return getPersistence().fetchByG_UT(groupId, urlTitle, retrieveFromCache);
	}

	/**
	* Removes the blogs entry where groupId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the blogs entry that was removed
	*/
	public static BlogsEntry removeByG_UT(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().removeByG_UT(groupId, urlTitle);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the number of matching blogs entries
	*/
	public static int countByG_UT(long groupId, java.lang.String urlTitle) {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD(long groupId,
		java.util.Date displayDate) {
		return getPersistence().findByG_LtD(groupId, displayDate);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD(long groupId,
		java.util.Date displayDate, int start, int end) {
		return getPersistence().findByG_LtD(groupId, displayDate, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD(long groupId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_LtD(groupId, displayDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD(long groupId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LtD(groupId, displayDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_First(long groupId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_First(groupId, displayDate, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_First(long groupId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_First(groupId, displayDate, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_Last(long groupId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_Last(groupId, displayDate, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_Last(long groupId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_Last(groupId, displayDate, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_LtD_PrevAndNext(long entryId,
		long groupId, java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_PrevAndNext(entryId, groupId, displayDate,
			orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD(long groupId,
		java.util.Date displayDate) {
		return getPersistence().filterFindByG_LtD(groupId, displayDate);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD(long groupId,
		java.util.Date displayDate, int start, int end) {
		return getPersistence()
				   .filterFindByG_LtD(groupId, displayDate, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD(long groupId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LtD(groupId, displayDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_LtD_PrevAndNext(long entryId,
		long groupId, java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_LtD_PrevAndNext(entryId, groupId,
			displayDate, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	*/
	public static void removeByG_LtD(long groupId, java.util.Date displayDate) {
		getPersistence().removeByG_LtD(groupId, displayDate);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public static int countByG_LtD(long groupId, java.util.Date displayDate) {
		return getPersistence().countByG_LtD(groupId, displayDate);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_LtD(long groupId,
		java.util.Date displayDate) {
		return getPersistence().filterCountByG_LtD(groupId, displayDate);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end) {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_S_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_S_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_S_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_S_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_S_PrevAndNext(long entryId,
		long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_S_PrevAndNext(entryId, groupId, status,
			orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_S(long groupId, int status) {
		return getPersistence().filterFindByG_S(groupId, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end) {
		return getPersistence().filterFindByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_S(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_S_PrevAndNext(long entryId,
		long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_S_PrevAndNext(entryId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_S(long groupId, int status) {
		return getPersistence().filterCountByG_S(groupId, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_NotS(long groupId, int status) {
		return getPersistence().findByG_NotS(groupId, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end) {
		return getPersistence().findByG_NotS(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_NotS(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_NotS(groupId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_NotS_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_NotS_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_NotS_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_NotS_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_NotS_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_NotS_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_NotS_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_NotS_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_NotS_PrevAndNext(long entryId,
		long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_NotS_PrevAndNext(entryId, groupId, status,
			orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_NotS(long groupId, int status) {
		return getPersistence().filterFindByG_NotS(groupId, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_NotS(long groupId, int status,
		int start, int end) {
		return getPersistence().filterFindByG_NotS(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_NotS(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_NotS(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_NotS_PrevAndNext(long entryId,
		long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_NotS_PrevAndNext(entryId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_NotS(long groupId, int status) {
		getPersistence().removeByG_NotS(groupId, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_NotS(long groupId, int status) {
		return getPersistence().countByG_NotS(groupId, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_NotS(long groupId, int status) {
		return getPersistence().filterCountByG_NotS(groupId, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U(long companyId, long userId) {
		return getPersistence().findByC_U(companyId, userId);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end) {
		return getPersistence().findByC_U(companyId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_U(companyId, userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_U(companyId, userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_First(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_First(companyId, userId, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_First(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_First(companyId, userId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_Last(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_Last(companyId, userId, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_Last(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_Last(companyId, userId, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_U_PrevAndNext(long entryId,
		long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_PrevAndNext(entryId, companyId, userId,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	*/
	public static void removeByC_U(long companyId, long userId) {
		getPersistence().removeByC_U(companyId, userId);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the number of matching blogs entries
	*/
	public static int countByC_U(long companyId, long userId) {
		return getPersistence().countByC_U(companyId, userId);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD(long companyId,
		java.util.Date displayDate) {
		return getPersistence().findByC_LtD(companyId, displayDate);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD(long companyId,
		java.util.Date displayDate, int start, int end) {
		return getPersistence().findByC_LtD(companyId, displayDate, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD(long companyId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_LtD(companyId, displayDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD(long companyId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_LtD(companyId, displayDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_First(long companyId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_First(companyId, displayDate, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_First(long companyId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_First(companyId, displayDate, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_Last(long companyId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_Last(companyId, displayDate, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_Last(long companyId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_Last(companyId, displayDate, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_LtD_PrevAndNext(long entryId,
		long companyId, java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_PrevAndNext(entryId, companyId, displayDate,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	*/
	public static void removeByC_LtD(long companyId, java.util.Date displayDate) {
		getPersistence().removeByC_LtD(companyId, displayDate);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public static int countByC_LtD(long companyId, java.util.Date displayDate) {
		return getPersistence().countByC_LtD(companyId, displayDate);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_S(long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_S_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_S_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_S_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_S_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_S_PrevAndNext(long entryId,
		long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_S_PrevAndNext(entryId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_NotS(long companyId, int status) {
		return getPersistence().findByC_NotS(companyId, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_NotS(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_NotS_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_NotS_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_NotS_PrevAndNext(long entryId,
		long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_NotS_PrevAndNext(entryId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_NotS(long companyId, int status) {
		getPersistence().removeByC_NotS(companyId, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_NotS(long companyId, int status) {
		return getPersistence().countByC_NotS(companyId, status);
	}

	/**
	* Returns all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByLtD_S(java.util.Date displayDate,
		int status) {
		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByLtD_S(java.util.Date displayDate,
		int status, int start, int end) {
		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByLtD_S(java.util.Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByLtD_S(java.util.Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByLtD_S_First(java.util.Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByLtD_S_First(java.util.Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByLtD_S_Last(java.util.Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByLtD_S_Last(java.util.Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByLtD_S_PrevAndNext(long entryId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByLtD_S_PrevAndNext(entryId, displayDate, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByLtD_S(java.util.Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	* Returns the number of blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByLtD_S(java.util.Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate) {
		return getPersistence().findByG_U_LtD(groupId, userId, displayDate);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate, int start, int end) {
		return getPersistence()
				   .findByG_U_LtD(groupId, userId, displayDate, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_U_LtD(groupId, userId, displayDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_LtD(groupId, userId, displayDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_First(long groupId, long userId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_First(groupId, userId, displayDate,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_First(long groupId, long userId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_First(groupId, userId, displayDate,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_Last(long groupId, long userId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_Last(groupId, userId, displayDate,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_Last(long groupId, long userId,
		java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_Last(groupId, userId, displayDate,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_U_LtD_PrevAndNext(long entryId,
		long groupId, long userId, java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_PrevAndNext(entryId, groupId, userId,
			displayDate, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, java.util.Date displayDate) {
		return getPersistence().filterFindByG_U_LtD(groupId, userId, displayDate);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, java.util.Date displayDate, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_LtD(groupId, userId, displayDate, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, java.util.Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_LtD(groupId, userId, displayDate, start,
			end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_U_LtD_PrevAndNext(long entryId,
		long groupId, long userId, java.util.Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_U_LtD_PrevAndNext(entryId, groupId, userId,
			displayDate, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	*/
	public static void removeByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate) {
		getPersistence().removeByG_U_LtD(groupId, userId, displayDate);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public static int countByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate) {
		return getPersistence().countByG_U_LtD(groupId, userId, displayDate);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_U_LtD(long groupId, long userId,
		java.util.Date displayDate) {
		return getPersistence()
				   .filterCountByG_U_LtD(groupId, userId, displayDate);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status) {
		return getPersistence().findByG_U_S(groupId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end) {
		return getPersistence().findByG_U_S(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_S_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_S_First(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_S_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_First(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_S_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_S_Last(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_S_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_Last(groupId, userId, status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_U_S_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_S_PrevAndNext(entryId, groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status) {
		return getPersistence().filterFindByG_U_S(groupId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_S(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_S(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_U_S_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_U_S_PrevAndNext(entryId, groupId, userId,
			status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByG_U_S(long groupId, long userId, int status) {
		getPersistence().removeByG_U_S(groupId, userId, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_U_S(long groupId, long userId, int status) {
		return getPersistence().countByG_U_S(groupId, userId, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_U_S(long groupId, long userId, int status) {
		return getPersistence().filterCountByG_U_S(groupId, userId, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status) {
		return getPersistence().findByG_U_NotS(groupId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end) {
		return getPersistence()
				   .findByG_U_NotS(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_U_NotS(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_NotS(groupId, userId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_NotS_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_NotS_First(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_NotS_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_NotS_First(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_NotS_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_NotS_Last(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_NotS_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_NotS_Last(groupId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_U_NotS_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_NotS_PrevAndNext(entryId, groupId, userId,
			status, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status) {
		return getPersistence().filterFindByG_U_NotS(groupId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_NotS(groupId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_NotS(groupId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_U_NotS_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_U_NotS_PrevAndNext(entryId, groupId, userId,
			status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByG_U_NotS(long groupId, long userId, int status) {
		getPersistence().removeByG_U_NotS(groupId, userId, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_U_NotS(long groupId, long userId, int status) {
		return getPersistence().countByG_U_NotS(groupId, userId, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_U_NotS(long groupId, long userId,
		int status) {
		return getPersistence().filterCountByG_U_NotS(groupId, userId, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_S(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence().findByG_LtD_S(groupId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_S(long groupId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByG_LtD_S(groupId, displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_S(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_LtD_S(groupId, displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_S(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LtD_S(groupId, displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_S_First(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_S_First(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_S_First(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_S_First(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_S_Last(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_S_Last(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_S_Last(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_S_Last(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_LtD_S_PrevAndNext(long entryId,
		long groupId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_S_PrevAndNext(entryId, groupId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence().filterFindByG_LtD_S(groupId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_LtD_S(groupId, displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LtD_S(groupId, displayDate, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_LtD_S_PrevAndNext(long entryId,
		long groupId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_LtD_S_PrevAndNext(entryId, groupId,
			displayDate, status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByG_LtD_S(long groupId,
		java.util.Date displayDate, int status) {
		getPersistence().removeByG_LtD_S(groupId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_LtD_S(long groupId, java.util.Date displayDate,
		int status) {
		return getPersistence().countByG_LtD_S(groupId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_LtD_S(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterCountByG_LtD_S(groupId, displayDate, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence().findByG_LtD_NotS(groupId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByG_LtD_NotS(groupId, displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_LtD_NotS(groupId, displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LtD_NotS(groupId, displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_NotS_First(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_NotS_First(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_NotS_First(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_NotS_First(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_LtD_NotS_Last(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_NotS_Last(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_LtD_NotS_Last(long groupId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_LtD_NotS_Last(groupId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_LtD_NotS_PrevAndNext(long entryId,
		long groupId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_LtD_NotS_PrevAndNext(entryId, groupId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterFindByG_LtD_NotS(groupId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_LtD_NotS(groupId, displayDate, status, start,
			end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LtD_NotS(groupId, displayDate, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_LtD_NotS_PrevAndNext(
		long entryId, long groupId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_LtD_NotS_PrevAndNext(entryId, groupId,
			displayDate, status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status) {
		getPersistence().removeByG_LtD_NotS(groupId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence().countByG_LtD_NotS(groupId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_LtD_NotS(long groupId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterCountByG_LtD_NotS(groupId, displayDate, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status) {
		return getPersistence().findByC_U_S(companyId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end) {
		return getPersistence()
				   .findByC_U_S(companyId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_U_S(companyId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_U_S(companyId, userId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_S_First(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_S_First(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_S_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_S_Last(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_S_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_S_Last(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_U_S_PrevAndNext(long entryId,
		long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_S_PrevAndNext(entryId, companyId, userId, status,
			orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByC_U_S(long companyId, long userId, int status) {
		getPersistence().removeByC_U_S(companyId, userId, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_U_S(long companyId, long userId, int status) {
		return getPersistence().countByC_U_S(companyId, userId, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status) {
		return getPersistence().findByC_U_NotS(companyId, userId, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status, int start, int end) {
		return getPersistence()
				   .findByC_U_NotS(companyId, userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_U_NotS(companyId, userId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_U_NotS(companyId, userId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_NotS_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_NotS_First(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_NotS_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_NotS_First(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_U_NotS_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_NotS_Last(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_U_NotS_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_U_NotS_Last(companyId, userId, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_U_NotS_PrevAndNext(long entryId,
		long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_NotS_PrevAndNext(entryId, companyId, userId,
			status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByC_U_NotS(long companyId, long userId, int status) {
		getPersistence().removeByC_U_NotS(companyId, userId, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_U_NotS(long companyId, long userId, int status) {
		return getPersistence().countByC_U_NotS(companyId, userId, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_S(long companyId,
		java.util.Date displayDate, int status) {
		return getPersistence().findByC_LtD_S(companyId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_S(long companyId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByC_LtD_S(companyId, displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_S(long companyId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_LtD_S(companyId, displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_S(long companyId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_LtD_S(companyId, displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_S_First(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_S_First(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_S_First(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_S_First(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_S_Last(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_S_Last(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_S_Last(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_S_Last(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_LtD_S_PrevAndNext(long entryId,
		long companyId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_S_PrevAndNext(entryId, companyId, displayDate,
			status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByC_LtD_S(long companyId,
		java.util.Date displayDate, int status) {
		getPersistence().removeByC_LtD_S(companyId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_LtD_S(long companyId,
		java.util.Date displayDate, int status) {
		return getPersistence().countByC_LtD_S(companyId, displayDate, status);
	}

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status) {
		return getPersistence().findByC_LtD_NotS(companyId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByC_LtD_NotS(companyId, displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByC_LtD_NotS(companyId, displayDate, status, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_LtD_NotS(companyId, displayDate, status, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_NotS_First(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_NotS_First(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_NotS_First(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_NotS_First(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByC_LtD_NotS_Last(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_NotS_Last(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByC_LtD_NotS_Last(long companyId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtD_NotS_Last(companyId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByC_LtD_NotS_PrevAndNext(long entryId,
		long companyId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_LtD_NotS_PrevAndNext(entryId, companyId,
			displayDate, status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status) {
		getPersistence().removeByC_LtD_NotS(companyId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByC_LtD_NotS(long companyId,
		java.util.Date displayDate, int status) {
		return getPersistence().countByC_LtD_NotS(companyId, displayDate, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .findByG_U_LtD_S(groupId, userId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_S_First(long groupId, long userId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_S_First(groupId, userId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_S_First(long groupId, long userId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_S_First(groupId, userId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_S_Last(long groupId, long userId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_S_Last(groupId, userId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_S_Last(long groupId, long userId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_S_Last(groupId, userId, displayDate, status,
			orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_U_LtD_S_PrevAndNext(long entryId,
		long groupId, long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_S_PrevAndNext(entryId, groupId, userId,
			displayDate, status, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterFindByG_U_LtD_S(groupId, userId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, java.util.Date displayDate, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_U_LtD_S_PrevAndNext(long entryId,
		long groupId, long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_U_LtD_S_PrevAndNext(entryId, groupId, userId,
			displayDate, status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status) {
		getPersistence().removeByG_U_LtD_S(groupId, userId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .countByG_U_LtD_S(groupId, userId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_U_LtD_S(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterCountByG_U_LtD_S(groupId, userId, displayDate, status);
	}

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status) {
		return getPersistence()
				   .findByG_U_LtD_NotS(groupId, userId, displayDate, status);
	}

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .findByG_U_LtD_NotS(groupId, userId, displayDate, status,
			start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_U_LtD_NotS(groupId, userId, displayDate, status,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public static List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_LtD_NotS(groupId, userId, displayDate, status,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_NotS_First(long groupId,
		long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_NotS_First(groupId, userId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_NotS_First(long groupId,
		long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_NotS_First(groupId, userId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public static BlogsEntry findByG_U_LtD_NotS_Last(long groupId, long userId,
		java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_NotS_Last(groupId, userId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public static BlogsEntry fetchByG_U_LtD_NotS_Last(long groupId,
		long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_LtD_NotS_Last(groupId, userId, displayDate,
			status, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] findByG_U_LtD_NotS_PrevAndNext(long entryId,
		long groupId, long userId, java.util.Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_LtD_NotS_PrevAndNext(entryId, groupId, userId,
			displayDate, status, orderByComparator);
	}

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterFindByG_U_LtD_NotS(groupId, userId, displayDate,
			status);
	}

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_LtD_NotS(groupId, userId, displayDate,
			status, start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public static List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, java.util.Date displayDate, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_LtD_NotS(groupId, userId, displayDate,
			status, start, end, orderByComparator);
	}

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry[] filterFindByG_U_LtD_NotS_PrevAndNext(
		long entryId, long groupId, long userId, java.util.Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_U_LtD_NotS_PrevAndNext(entryId, groupId,
			userId, displayDate, status, orderByComparator);
	}

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByG_U_LtD_NotS(long groupId, long userId,
		java.util.Date displayDate, int status) {
		getPersistence()
			.removeByG_U_LtD_NotS(groupId, userId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public static int countByG_U_LtD_NotS(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .countByG_U_LtD_NotS(groupId, userId, displayDate, status);
	}

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public static int filterCountByG_U_LtD_NotS(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return getPersistence()
				   .filterCountByG_U_LtD_NotS(groupId, userId, displayDate,
			status);
	}

	/**
	* Caches the blogs entry in the entity cache if it is enabled.
	*
	* @param blogsEntry the blogs entry
	*/
	public static void cacheResult(BlogsEntry blogsEntry) {
		getPersistence().cacheResult(blogsEntry);
	}

	/**
	* Caches the blogs entries in the entity cache if it is enabled.
	*
	* @param blogsEntries the blogs entries
	*/
	public static void cacheResult(List<BlogsEntry> blogsEntries) {
		getPersistence().cacheResult(blogsEntries);
	}

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	public static BlogsEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry that was removed
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry remove(long entryId)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static BlogsEntry updateImpl(BlogsEntry blogsEntry) {
		return getPersistence().updateImpl(blogsEntry);
	}

	/**
	* Returns the blogs entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry findByPrimaryKey(long entryId)
		throws com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	*/
	public static BlogsEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.Map<java.io.Serializable, BlogsEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the blogs entries.
	*
	* @return the blogs entries
	*/
	public static List<BlogsEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of blogs entries
	*/
	public static List<BlogsEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of blogs entries
	*/
	public static List<BlogsEntry> findAll(int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of blogs entries
	*/
	public static List<BlogsEntry> findAll(int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the blogs entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of blogs entries.
	*
	* @return the number of blogs entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static BlogsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BlogsEntryPersistence)PortalBeanLocatorUtil.locate(BlogsEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(BlogsEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static BlogsEntryPersistence _persistence;
}