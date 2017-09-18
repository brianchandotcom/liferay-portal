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

package com.liferay.modern.site.building.fragment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the msb fragment entry service. This utility wraps {@link com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryPersistence
 * @see com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentEntryUtil {
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
	public static void clearCache(MSBFragmentEntry msbFragmentEntry) {
		getPersistence().clearCache(msbFragmentEntry);
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
	public static List<MSBFragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MSBFragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MSBFragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MSBFragmentEntry update(MSBFragmentEntry msbFragmentEntry) {
		return getPersistence().update(msbFragmentEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MSBFragmentEntry update(MSBFragmentEntry msbFragmentEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(msbFragmentEntry, serviceContext);
	}

	/**
	* Returns all the msb fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the msb fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] findByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(msbFragmentEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] filterFindByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(msbFragmentEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the msb fragment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of msb fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId) {
		return getPersistence()
				   .findByMSBFragmentCollectionId(msbFragmentCollectionId);
	}

	/**
	* Returns a range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end) {
		return getPersistence()
				   .findByMSBFragmentCollectionId(msbFragmentCollectionId,
			start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByMSBFragmentCollectionId(msbFragmentCollectionId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByMSBFragmentCollectionId(msbFragmentCollectionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByMSBFragmentCollectionId_First(msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByMSBFragmentCollectionId_First(msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByMSBFragmentCollectionId_Last(msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByMSBFragmentCollectionId_Last(msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] findByMSBFragmentCollectionId_PrevAndNext(
		long msbFragmentEntryId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByMSBFragmentCollectionId_PrevAndNext(msbFragmentEntryId,
			msbFragmentCollectionId, orderByComparator);
	}

	/**
	* Removes all the msb fragment entries where msbFragmentCollectionId = &#63; from the database.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	*/
	public static void removeByMSBFragmentCollectionId(
		long msbFragmentCollectionId) {
		getPersistence().removeByMSBFragmentCollectionId(msbFragmentCollectionId);
	}

	/**
	* Returns the number of msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries
	*/
	public static int countByMSBFragmentCollectionId(
		long msbFragmentCollectionId) {
		return getPersistence()
				   .countByMSBFragmentCollectionId(msbFragmentCollectionId);
	}

	/**
	* Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		return getPersistence().findByG_MSBFCI(groupId, msbFragmentCollectionId);
	}

	/**
	* Returns a range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end) {
		return getPersistence()
				   .findByG_MSBFCI(groupId, msbFragmentCollectionId, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByG_MSBFCI(groupId, msbFragmentCollectionId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_MSBFCI(groupId, msbFragmentCollectionId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_First(groupId, msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_MSBFCI_First(groupId, msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_Last(groupId, msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_MSBFCI_Last(groupId, msbFragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] findByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_PrevAndNext(msbFragmentEntryId, groupId,
			msbFragmentCollectionId, orderByComparator);
	}

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		return getPersistence()
				   .filterFindByG_MSBFCI(groupId, msbFragmentCollectionId);
	}

	/**
	* Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end) {
		return getPersistence()
				   .filterFindByG_MSBFCI(groupId, msbFragmentCollectionId,
			start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_MSBFCI(groupId, msbFragmentCollectionId,
			start, end, orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] filterFindByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .filterFindByG_MSBFCI_PrevAndNext(msbFragmentEntryId,
			groupId, msbFragmentCollectionId, orderByComparator);
	}

	/**
	* Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	*/
	public static void removeByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		getPersistence().removeByG_MSBFCI(groupId, msbFragmentCollectionId);
	}

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries
	*/
	public static int countByG_MSBFCI(long groupId, long msbFragmentCollectionId) {
		return getPersistence().countByG_MSBFCI(groupId, msbFragmentCollectionId);
	}

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public static int filterCountByG_MSBFCI(long groupId,
		long msbFragmentCollectionId) {
		return getPersistence()
				   .filterCountByG_MSBFCI(groupId, msbFragmentCollectionId);
	}

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByG_N(long groupId, java.lang.String name)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the msb fragment entry where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the msb fragment entry that was removed
	*/
	public static MSBFragmentEntry removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment entries
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name);
	}

	/**
	* Returns a range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start, int end) {
		return getPersistence()
				   .findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId,
			name, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment entries
	*/
	public static List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start,
		int end, OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId,
			name, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_LikeN_First(groupId,
			msbFragmentCollectionId, name, orderByComparator);
	}

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_MSBFCI_LikeN_First(groupId,
			msbFragmentCollectionId, name, orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry findByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_LikeN_Last(groupId, msbFragmentCollectionId,
			name, orderByComparator);
	}

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public static MSBFragmentEntry fetchByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_MSBFCI_LikeN_Last(groupId,
			msbFragmentCollectionId, name, orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] findByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .findByG_MSBFCI_LikeN_PrevAndNext(msbFragmentEntryId,
			groupId, msbFragmentCollectionId, name, orderByComparator);
	}

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterFindByG_MSBFCI_LikeN(groupId,
			msbFragmentCollectionId, name);
	}

	/**
	* Returns a range of all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end) {
		return getPersistence()
				   .filterFindByG_MSBFCI_LikeN(groupId,
			msbFragmentCollectionId, name, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries that the user has permissions to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment entries that the user has permission to view
	*/
	public static List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_MSBFCI_LikeN(groupId,
			msbFragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry[] filterFindByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		java.lang.String name,
		OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence()
				   .filterFindByG_MSBFCI_LikeN_PrevAndNext(msbFragmentEntryId,
			groupId, msbFragmentCollectionId, name, orderByComparator);
	}

	/**
	* Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	*/
	public static void removeByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name) {
		getPersistence()
			.removeByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name);
	}

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the number of matching msb fragment entries
	*/
	public static int countByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .countByG_MSBFCI_LikeN(groupId, msbFragmentCollectionId, name);
	}

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public static int filterCountByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterCountByG_MSBFCI_LikeN(groupId,
			msbFragmentCollectionId, name);
	}

	/**
	* Caches the msb fragment entry in the entity cache if it is enabled.
	*
	* @param msbFragmentEntry the msb fragment entry
	*/
	public static void cacheResult(MSBFragmentEntry msbFragmentEntry) {
		getPersistence().cacheResult(msbFragmentEntry);
	}

	/**
	* Caches the msb fragment entries in the entity cache if it is enabled.
	*
	* @param msbFragmentEntries the msb fragment entries
	*/
	public static void cacheResult(List<MSBFragmentEntry> msbFragmentEntries) {
		getPersistence().cacheResult(msbFragmentEntries);
	}

	/**
	* Creates a new msb fragment entry with the primary key. Does not add the msb fragment entry to the database.
	*
	* @param msbFragmentEntryId the primary key for the new msb fragment entry
	* @return the new msb fragment entry
	*/
	public static MSBFragmentEntry create(long msbFragmentEntryId) {
		return getPersistence().create(msbFragmentEntryId);
	}

	/**
	* Removes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry remove(long msbFragmentEntryId)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().remove(msbFragmentEntryId);
	}

	public static MSBFragmentEntry updateImpl(MSBFragmentEntry msbFragmentEntry) {
		return getPersistence().updateImpl(msbFragmentEntry);
	}

	/**
	* Returns the msb fragment entry with the primary key or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry findByPrimaryKey(long msbFragmentEntryId)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException {
		return getPersistence().findByPrimaryKey(msbFragmentEntryId);
	}

	/**
	* Returns the msb fragment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry, or <code>null</code> if a msb fragment entry with the primary key could not be found
	*/
	public static MSBFragmentEntry fetchByPrimaryKey(long msbFragmentEntryId) {
		return getPersistence().fetchByPrimaryKey(msbFragmentEntryId);
	}

	public static java.util.Map<java.io.Serializable, MSBFragmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the msb fragment entries.
	*
	* @return the msb fragment entries
	*/
	public static List<MSBFragmentEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the msb fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of msb fragment entries
	*/
	public static List<MSBFragmentEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of msb fragment entries
	*/
	public static List<MSBFragmentEntry> findAll(int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of msb fragment entries
	*/
	public static List<MSBFragmentEntry> findAll(int start, int end,
		OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the msb fragment entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of msb fragment entries.
	*
	* @return the number of msb fragment entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MSBFragmentEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentEntryPersistence, MSBFragmentEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentEntryPersistence.class);
}