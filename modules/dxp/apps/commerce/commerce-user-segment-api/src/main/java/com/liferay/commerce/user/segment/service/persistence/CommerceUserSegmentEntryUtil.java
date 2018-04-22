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

package com.liferay.commerce.user.segment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce user segment entry service. This utility wraps {@link com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryPersistence
 * @see com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryUtil {
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
	public static void clearCache(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		getPersistence().clearCache(commerceUserSegmentEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceUserSegmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceUserSegmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceUserSegmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceUserSegmentEntry update(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return getPersistence().update(commerceUserSegmentEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceUserSegmentEntry update(
		CommerceUserSegmentEntry commerceUserSegmentEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceUserSegmentEntry, serviceContext);
	}

	/**
	* Returns all the commerce user segment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce user segment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry[] findByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceUserSegmentEntryId,
			groupId, orderByComparator);
	}

	/**
	* Returns all the commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set of commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry[] filterFindByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(commerceUserSegmentEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce user segment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce user segment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce user segment entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce user segment entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry findByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry findByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByG_A_Last(long groupId,
		boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry[] findByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commerceUserSegmentEntryId, groupId,
			active, orderByComparator);
	}

	/**
	* Returns all the commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active) {
		return getPersistence().filterFindByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().filterFindByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment entries that the user has permission to view
	*/
	public static List<CommerceUserSegmentEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A(groupId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set of commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry[] filterFindByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence()
				   .filterFindByG_A_PrevAndNext(commerceUserSegmentEntryId,
			groupId, active, orderByComparator);
	}

	/**
	* Removes all the commerce user segment entries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce user segment entries
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce user segment entries that the user has permission to view
	*/
	public static int filterCountByG_A(long groupId, boolean active) {
		return getPersistence().filterCountByG_A(groupId, active);
	}

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or throws a {@link NoSuchUserSegmentEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry findByG_K(long groupId,
		java.lang.String key)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByG_K(long groupId,
		java.lang.String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public static CommerceUserSegmentEntry fetchByG_K(long groupId,
		java.lang.String key, boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the commerce user segment entry where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the commerce user segment entry that was removed
	*/
	public static CommerceUserSegmentEntry removeByG_K(long groupId,
		java.lang.String key)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of commerce user segment entries where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching commerce user segment entries
	*/
	public static int countByG_K(long groupId, java.lang.String key) {
		return getPersistence().countByG_K(groupId, key);
	}

	/**
	* Caches the commerce user segment entry in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	*/
	public static void cacheResult(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		getPersistence().cacheResult(commerceUserSegmentEntry);
	}

	/**
	* Caches the commerce user segment entries in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentEntries the commerce user segment entries
	*/
	public static void cacheResult(
		List<CommerceUserSegmentEntry> commerceUserSegmentEntries) {
		getPersistence().cacheResult(commerceUserSegmentEntries);
	}

	/**
	* Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	*
	* @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	* @return the new commerce user segment entry
	*/
	public static CommerceUserSegmentEntry create(
		long commerceUserSegmentEntryId) {
		return getPersistence().create(commerceUserSegmentEntryId);
	}

	/**
	* Removes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry remove(
		long commerceUserSegmentEntryId)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().remove(commerceUserSegmentEntryId);
	}

	public static CommerceUserSegmentEntry updateImpl(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return getPersistence().updateImpl(commerceUserSegmentEntry);
	}

	/**
	* Returns the commerce user segment entry with the primary key or throws a {@link NoSuchUserSegmentEntryException} if it could not be found.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry findByPrimaryKey(
		long commerceUserSegmentEntryId)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException {
		return getPersistence().findByPrimaryKey(commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce user segment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry, or <code>null</code> if a commerce user segment entry with the primary key could not be found
	*/
	public static CommerceUserSegmentEntry fetchByPrimaryKey(
		long commerceUserSegmentEntryId) {
		return getPersistence().fetchByPrimaryKey(commerceUserSegmentEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceUserSegmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce user segment entries.
	*
	* @return the commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce user segment entries
	*/
	public static List<CommerceUserSegmentEntry> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce user segment entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce user segment entries.
	*
	* @return the number of commerce user segment entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceUserSegmentEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentEntryPersistence, CommerceUserSegmentEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentEntryPersistence.class);

		ServiceTracker<CommerceUserSegmentEntryPersistence, CommerceUserSegmentEntryPersistence> serviceTracker =
			new ServiceTracker<CommerceUserSegmentEntryPersistence, CommerceUserSegmentEntryPersistence>(bundle.getBundleContext(),
				CommerceUserSegmentEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}