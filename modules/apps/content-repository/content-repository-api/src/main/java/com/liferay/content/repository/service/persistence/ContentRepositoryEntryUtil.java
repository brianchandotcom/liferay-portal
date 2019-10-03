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

package com.liferay.content.repository.service.persistence;

import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the content repository entry service. This utility wraps <code>com.liferay.content.repository.service.persistence.impl.ContentRepositoryEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntryPersistence
 * @generated
 */
public class ContentRepositoryEntryUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		ContentRepositoryEntry contentRepositoryEntry) {

		getPersistence().clearCache(contentRepositoryEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ContentRepositoryEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ContentRepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ContentRepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ContentRepositoryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ContentRepositoryEntry update(
		ContentRepositoryEntry contentRepositoryEntry) {

		return getPersistence().update(contentRepositoryEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ContentRepositoryEntry update(
		ContentRepositoryEntry contentRepositoryEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(contentRepositoryEntry, serviceContext);
	}

	/**
	 * Returns all the content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry findByUuid_First(
			String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry findByUuid_Last(
			String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public static ContentRepositoryEntry[] findByUuid_PrevAndNext(
			long contentRepositoryEntryId, String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			contentRepositoryEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the content repository entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching content repository entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the content repository entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the content repository entry that was removed
	 */
	public static ContentRepositoryEntry removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching content repository entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	public static List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static ContentRepositoryEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public static ContentRepositoryEntry[] findByUuid_C_PrevAndNext(
			long contentRepositoryEntryId, String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			contentRepositoryEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the content repository entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching content repository entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the content repository entry in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 */
	public static void cacheResult(
		ContentRepositoryEntry contentRepositoryEntry) {

		getPersistence().cacheResult(contentRepositoryEntry);
	}

	/**
	 * Caches the content repository entries in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntries the content repository entries
	 */
	public static void cacheResult(
		List<ContentRepositoryEntry> contentRepositoryEntries) {

		getPersistence().cacheResult(contentRepositoryEntries);
	}

	/**
	 * Creates a new content repository entry with the primary key. Does not add the content repository entry to the database.
	 *
	 * @param contentRepositoryEntryId the primary key for the new content repository entry
	 * @return the new content repository entry
	 */
	public static ContentRepositoryEntry create(long contentRepositoryEntryId) {
		return getPersistence().create(contentRepositoryEntryId);
	}

	/**
	 * Removes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public static ContentRepositoryEntry remove(long contentRepositoryEntryId)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().remove(contentRepositoryEntryId);
	}

	public static ContentRepositoryEntry updateImpl(
		ContentRepositoryEntry contentRepositoryEntry) {

		return getPersistence().updateImpl(contentRepositoryEntry);
	}

	/**
	 * Returns the content repository entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public static ContentRepositoryEntry findByPrimaryKey(
			long contentRepositoryEntryId)
		throws com.liferay.content.repository.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry, or <code>null</code> if a content repository entry with the primary key could not be found
	 */
	public static ContentRepositoryEntry fetchByPrimaryKey(
		long contentRepositoryEntryId) {

		return getPersistence().fetchByPrimaryKey(contentRepositoryEntryId);
	}

	/**
	 * Returns all the content repository entries.
	 *
	 * @return the content repository entries
	 */
	public static List<ContentRepositoryEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of content repository entries
	 */
	public static List<ContentRepositoryEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of content repository entries
	 */
	public static List<ContentRepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of content repository entries
	 */
	public static List<ContentRepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the content repository entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of content repository entries.
	 *
	 * @return the number of content repository entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ContentRepositoryEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContentRepositoryEntryPersistence, ContentRepositoryEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ContentRepositoryEntryPersistence.class);

		ServiceTracker
			<ContentRepositoryEntryPersistence,
			 ContentRepositoryEntryPersistence> serviceTracker =
				new ServiceTracker
					<ContentRepositoryEntryPersistence,
					 ContentRepositoryEntryPersistence>(
						 bundle.getBundleContext(),
						 ContentRepositoryEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}