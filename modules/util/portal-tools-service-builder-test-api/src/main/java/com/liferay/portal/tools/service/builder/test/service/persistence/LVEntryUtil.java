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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the lv entry service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class LVEntryUtil {
	/*
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
	public static void clearCache(LVEntry lvEntry) {
		getPersistence().clearCache(lvEntry);
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
	public static Map<Serializable, LVEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LVEntry> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntry update(LVEntry lvEntry) {
		return getPersistence().update(lvEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntry update(LVEntry lvEntry, ServiceContext serviceContext) {
		return getPersistence().update(lvEntry, serviceContext);
	}

	/**
	* Returns all the lv entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching lv entries
	*/
	public static List<LVEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the lv entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of matching lv entries
	*/
	public static List<LVEntry> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the lv entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first lv entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByUuid_First(String uuid,
		OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first lv entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByUuid_First(String uuid,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last lv entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByUuid_Last(String uuid,
		OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last lv entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByUuid_Last(String uuid,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63;.
	*
	* @param lvEntryId the primary key of the current lv entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public static LVEntry[] findByUuid_PrevAndNext(long lvEntryId, String uuid,
		OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(lvEntryId, uuid, orderByComparator);
	}

	/**
	* Removes all the lv entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of lv entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching lv entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param head the head
	* @return the matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByUUID_G(String uuid, long groupId, boolean head)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByUUID_G(uuid, groupId, head);
	}

	/**
	* Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param head the head
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByUUID_G(String uuid, long groupId, boolean head) {
		return getPersistence().fetchByUUID_G(uuid, groupId, head);
	}

	/**
	* Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param head the head
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByUUID_G(String uuid, long groupId,
		boolean head, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByUUID_G(uuid, groupId, head, retrieveFromCache);
	}

	/**
	* Removes the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param head the head
	* @return the lv entry that was removed
	*/
	public static LVEntry removeByUUID_G(String uuid, long groupId, boolean head)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId, head);
	}

	/**
	* Returns the number of lv entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param head the head
	* @return the number of matching lv entries
	*/
	public static int countByUUID_G(String uuid, long groupId, boolean head) {
		return getPersistence().countByUUID_G(uuid, groupId, head);
	}

	/**
	* Returns all the lv entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByGroupId_First(long groupId,
		OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByGroupId_First(long groupId,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByGroupId_Last(long groupId,
		OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63;.
	*
	* @param lvEntryId the primary key of the current lv entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public static LVEntry[] findByGroupId_PrevAndNext(long lvEntryId,
		long groupId, OrderByComparator<LVEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(lvEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the lv entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the lv entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long[] groupIds, int start,
		int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the lv entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entries where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entries
	*/
	public static List<LVEntry> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<LVEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the lv entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of lv entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching lv entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of lv entries where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching lv entries
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param uniqueGroupKey the unique group key
	* @param head the head
	* @return the matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByG_UGK(long groupId, String uniqueGroupKey,
		boolean head)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByG_UGK(groupId, uniqueGroupKey, head);
	}

	/**
	* Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param uniqueGroupKey the unique group key
	* @param head the head
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByG_UGK(long groupId, String uniqueGroupKey,
		boolean head) {
		return getPersistence().fetchByG_UGK(groupId, uniqueGroupKey, head);
	}

	/**
	* Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param uniqueGroupKey the unique group key
	* @param head the head
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByG_UGK(long groupId, String uniqueGroupKey,
		boolean head, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_UGK(groupId, uniqueGroupKey, head,
			retrieveFromCache);
	}

	/**
	* Removes the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; from the database.
	*
	* @param groupId the group ID
	* @param uniqueGroupKey the unique group key
	* @param head the head
	* @return the lv entry that was removed
	*/
	public static LVEntry removeByG_UGK(long groupId, String uniqueGroupKey,
		boolean head)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().removeByG_UGK(groupId, uniqueGroupKey, head);
	}

	/**
	* Returns the number of lv entries where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param uniqueGroupKey the unique group key
	* @param head the head
	* @return the number of matching lv entries
	*/
	public static int countByG_UGK(long groupId, String uniqueGroupKey,
		boolean head) {
		return getPersistence().countByG_UGK(groupId, uniqueGroupKey, head);
	}

	/**
	* Returns the lv entry where headId = &#63; or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param headId the head ID
	* @return the matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public static LVEntry findByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByHeadId(headId);
	}

	/**
	* Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	* Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public static LVEntry fetchByHeadId(long headId, boolean retrieveFromCache) {
		return getPersistence().fetchByHeadId(headId, retrieveFromCache);
	}

	/**
	* Removes the lv entry where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the lv entry that was removed
	*/
	public static LVEntry removeByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().removeByHeadId(headId);
	}

	/**
	* Returns the number of lv entries where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching lv entries
	*/
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	* Caches the lv entry in the entity cache if it is enabled.
	*
	* @param lvEntry the lv entry
	*/
	public static void cacheResult(LVEntry lvEntry) {
		getPersistence().cacheResult(lvEntry);
	}

	/**
	* Caches the lv entries in the entity cache if it is enabled.
	*
	* @param lvEntries the lv entries
	*/
	public static void cacheResult(List<LVEntry> lvEntries) {
		getPersistence().cacheResult(lvEntries);
	}

	/**
	* Creates a new lv entry with the primary key. Does not add the lv entry to the database.
	*
	* @param lvEntryId the primary key for the new lv entry
	* @return the new lv entry
	*/
	public static LVEntry create(long lvEntryId) {
		return getPersistence().create(lvEntryId);
	}

	/**
	* Removes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry that was removed
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public static LVEntry remove(long lvEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().remove(lvEntryId);
	}

	public static LVEntry updateImpl(LVEntry lvEntry) {
		return getPersistence().updateImpl(lvEntry);
	}

	/**
	* Returns the lv entry with the primary key or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public static LVEntry findByPrimaryKey(long lvEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException {
		return getPersistence().findByPrimaryKey(lvEntryId);
	}

	/**
	* Returns the lv entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry, or <code>null</code> if a lv entry with the primary key could not be found
	*/
	public static LVEntry fetchByPrimaryKey(long lvEntryId) {
		return getPersistence().fetchByPrimaryKey(lvEntryId);
	}

	/**
	* Returns all the lv entries.
	*
	* @return the lv entries
	*/
	public static List<LVEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of lv entries
	*/
	public static List<LVEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lv entries
	*/
	public static List<LVEntry> findAll(int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of lv entries
	*/
	public static List<LVEntry> findAll(int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the lv entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of lv entries.
	*
	* @return the number of lv entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static LVEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LVEntryPersistence, LVEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LVEntryPersistence.class);

		ServiceTracker<LVEntryPersistence, LVEntryPersistence> serviceTracker = new ServiceTracker<LVEntryPersistence, LVEntryPersistence>(bundle.getBundleContext(),
				LVEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}