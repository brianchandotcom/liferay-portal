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

package com.liferay.segments.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.segments.model.SegmentsEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the segments entry service. This utility wraps {@link com.liferay.segments.service.persistence.impl.SegmentsEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryPersistence
 * @see com.liferay.segments.service.persistence.impl.SegmentsEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class SegmentsEntryUtil {
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
	public static void clearCache(SegmentsEntry segmentsEntry) {
		getPersistence().clearCache(segmentsEntry);
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
	public static Map<Serializable, SegmentsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SegmentsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SegmentsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SegmentsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SegmentsEntry update(SegmentsEntry segmentsEntry) {
		return getPersistence().update(segmentsEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SegmentsEntry update(SegmentsEntry segmentsEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(segmentsEntry, serviceContext);
	}

	/**
	* Returns all the segments entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByGroupId_First(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByGroupId_First(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByGroupId_Last(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findByGroupId_PrevAndNext(
		long segmentsEntryId, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(segmentsEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] filterFindByGroupId_PrevAndNext(
		long segmentsEntryId, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(segmentsEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long[] groupIds) {
		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long[] groupIds, int start,
		int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the segments entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of segments entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of segments entries where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching segments entries
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	* Returns all the segments entries where source = &#63;.
	*
	* @param source the source
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findBySource(String source) {
		return getPersistence().findBySource(source);
	}

	/**
	* Returns a range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findBySource(String source, int start,
		int end) {
		return getPersistence().findBySource(source, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findBySource(String source, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findBySource(source, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findBySource(String source, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySource(source, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findBySource_First(String source,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findBySource_First(source, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchBySource_First(String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchBySource_First(source, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findBySource_Last(String source,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findBySource_Last(source, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchBySource_Last(String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchBySource_Last(source, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where source = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findBySource_PrevAndNext(
		long segmentsEntryId, String source,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findBySource_PrevAndNext(segmentsEntryId, source,
			orderByComparator);
	}

	/**
	* Removes all the segments entries where source = &#63; from the database.
	*
	* @param source the source
	*/
	public static void removeBySource(String source) {
		getPersistence().removeBySource(source);
	}

	/**
	* Returns the number of segments entries where source = &#63;.
	*
	* @param source the source
	* @return the number of matching segments entries
	*/
	public static int countBySource(String source) {
		return getPersistence().countBySource(source);
	}

	/**
	* Returns all the segments entries where type = &#63;.
	*
	* @param type the type
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByType(String type) {
		return getPersistence().findByType(type);
	}

	/**
	* Returns a range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByType(String type, int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByType(String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByType(String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByType_First(String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByType_First(String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByType_Last(String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByType_Last(String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findByType_PrevAndNext(long segmentsEntryId,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByType_PrevAndNext(segmentsEntryId, type,
			orderByComparator);
	}

	/**
	* Removes all the segments entries where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(String type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of segments entries where type = &#63;.
	*
	* @param type the type
	* @return the number of matching segments entries
	*/
	public static int countByType(String type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns all the segments entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByG_A_First(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByG_A_Last(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findByG_A_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_PrevAndNext(segmentsEntryId, groupId, active,
			orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active) {
		return getPersistence().filterFindByG_A(groupId, active);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().filterFindByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A(groupId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] filterFindByG_A_PrevAndNext(
		long segmentsEntryId, long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_A_PrevAndNext(segmentsEntryId, groupId,
			active, orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active) {
		return getPersistence().filterFindByG_A(groupIds, active);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active, int start, int end) {
		return getPersistence().filterFindByG_A(groupIds, active, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A(groupIds, active, start, end,
			orderByComparator);
	}

	/**
	* Returns all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long[] groupIds, boolean active) {
		return getPersistence().findByG_A(groupIds, active);
	}

	/**
	* Returns a range of all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupIds, active, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupIds, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupIds, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the segments entries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of segments entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching segments entries
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns the number of segments entries where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the number of matching segments entries
	*/
	public static int countByG_A(long[] groupIds, boolean active) {
		return getPersistence().countByG_A(groupIds, active);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByG_A(long groupId, boolean active) {
		return getPersistence().filterCountByG_A(groupId, active);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByG_A(long[] groupIds, boolean active) {
		return getPersistence().filterCountByG_A(groupIds, active);
	}

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByG_K(long groupId, String key)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_K(long groupId, String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the segments entry where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the segments entry that was removed
	*/
	public static SegmentsEntry removeByG_K(long groupId, String key)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of segments entries where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching segments entries
	*/
	public static int countByG_K(long groupId, String key) {
		return getPersistence().countByG_K(groupId, key);
	}

	/**
	* Returns all the segments entries where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByA_T(boolean active, String type) {
		return getPersistence().findByA_T(active, type);
	}

	/**
	* Returns a range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end) {
		return getPersistence().findByA_T(active, type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByA_T(active, type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByA_T(active, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByA_T_First(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByA_T_First(active, type, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByA_T_First(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByA_T_First(active, type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByA_T_Last(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByA_T_Last(active, type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByA_T_Last(boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().fetchByA_T_Last(active, type, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findByA_T_PrevAndNext(long segmentsEntryId,
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByA_T_PrevAndNext(segmentsEntryId, active, type,
			orderByComparator);
	}

	/**
	* Removes all the segments entries where active = &#63; and type = &#63; from the database.
	*
	* @param active the active
	* @param type the type
	*/
	public static void removeByA_T(boolean active, String type) {
		getPersistence().removeByA_T(active, type);
	}

	/**
	* Returns the number of segments entries where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public static int countByA_T(boolean active, String type) {
		return getPersistence().countByA_T(active, type);
	}

	/**
	* Returns all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type) {
		return getPersistence().findByG_A_T(groupId, active, type);
	}

	/**
	* Returns a range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end) {
		return getPersistence().findByG_A_T(groupId, active, type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_A_T(groupId, active, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long groupId, boolean active,
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A_T(groupId, active, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByG_A_T_First(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_T_First(groupId, active, type, orderByComparator);
	}

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_A_T_First(long groupId,
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_T_First(groupId, active, type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public static SegmentsEntry findByG_A_T_Last(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_T_Last(groupId, active, type, orderByComparator);
	}

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public static SegmentsEntry fetchByG_A_T_Last(long groupId, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_T_Last(groupId, active, type, orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] findByG_A_T_PrevAndNext(
		long segmentsEntryId, long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_A_T_PrevAndNext(segmentsEntryId, groupId, active,
			type, orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type) {
		return getPersistence().filterFindByG_A_T(groupId, active, type);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type, int start, int end) {
		return getPersistence()
				   .filterFindByG_A_T(groupId, active, type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A_T(groupId, active, type, start, end,
			orderByComparator);
	}

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry[] filterFindByG_A_T_PrevAndNext(
		long segmentsEntryId, long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_A_T_PrevAndNext(segmentsEntryId, groupId,
			active, type, orderByComparator);
	}

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type) {
		return getPersistence().filterFindByG_A_T(groupIds, active, type);
	}

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end) {
		return getPersistence()
				   .filterFindByG_A_T(groupIds, active, type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public static List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A_T(groupIds, active, type, start, end,
			orderByComparator);
	}

	/**
	* Returns all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type) {
		return getPersistence().findByG_A_T(groupIds, active, type);
	}

	/**
	* Returns a range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end) {
		return getPersistence().findByG_A_T(groupIds, active, type, start, end);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence()
				   .findByG_A_T(groupIds, active, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public static List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A_T(groupIds, active, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the segments entries where groupId = &#63; and active = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	*/
	public static void removeByG_A_T(long groupId, boolean active, String type) {
		getPersistence().removeByG_A_T(groupId, active, type);
	}

	/**
	* Returns the number of segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public static int countByG_A_T(long groupId, boolean active, String type) {
		return getPersistence().countByG_A_T(groupId, active, type);
	}

	/**
	* Returns the number of segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public static int countByG_A_T(long[] groupIds, boolean active, String type) {
		return getPersistence().countByG_A_T(groupIds, active, type);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByG_A_T(long groupId, boolean active,
		String type) {
		return getPersistence().filterCountByG_A_T(groupId, active, type);
	}

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries that the user has permission to view
	*/
	public static int filterCountByG_A_T(long[] groupIds, boolean active,
		String type) {
		return getPersistence().filterCountByG_A_T(groupIds, active, type);
	}

	/**
	* Caches the segments entry in the entity cache if it is enabled.
	*
	* @param segmentsEntry the segments entry
	*/
	public static void cacheResult(SegmentsEntry segmentsEntry) {
		getPersistence().cacheResult(segmentsEntry);
	}

	/**
	* Caches the segments entries in the entity cache if it is enabled.
	*
	* @param segmentsEntries the segments entries
	*/
	public static void cacheResult(List<SegmentsEntry> segmentsEntries) {
		getPersistence().cacheResult(segmentsEntries);
	}

	/**
	* Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	*
	* @param segmentsEntryId the primary key for the new segments entry
	* @return the new segments entry
	*/
	public static SegmentsEntry create(long segmentsEntryId) {
		return getPersistence().create(segmentsEntryId);
	}

	/**
	* Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry that was removed
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry remove(long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().remove(segmentsEntryId);
	}

	public static SegmentsEntry updateImpl(SegmentsEntry segmentsEntry) {
		return getPersistence().updateImpl(segmentsEntry);
	}

	/**
	* Returns the segments entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry findByPrimaryKey(long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(segmentsEntryId);
	}

	/**
	* Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	*/
	public static SegmentsEntry fetchByPrimaryKey(long segmentsEntryId) {
		return getPersistence().fetchByPrimaryKey(segmentsEntryId);
	}

	/**
	* Returns all the segments entries.
	*
	* @return the segments entries
	*/
	public static List<SegmentsEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of segments entries
	*/
	public static List<SegmentsEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of segments entries
	*/
	public static List<SegmentsEntry> findAll(int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of segments entries
	*/
	public static List<SegmentsEntry> findAll(int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the segments entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of segments entries.
	*
	* @return the number of segments entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SegmentsEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SegmentsEntryPersistence, SegmentsEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsEntryPersistence.class);

		ServiceTracker<SegmentsEntryPersistence, SegmentsEntryPersistence> serviceTracker =
			new ServiceTracker<SegmentsEntryPersistence, SegmentsEntryPersistence>(bundle.getBundleContext(),
				SegmentsEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}