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

package com.liferay.fragment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the fragment entry service. This utility wraps {@link com.liferay.fragment.service.persistence.impl.FragmentEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryPersistence
 * @see com.liferay.fragment.service.persistence.impl.FragmentEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class FragmentEntryUtil {
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
	public static void clearCache(FragmentEntry fragmentEntry) {
		getPersistence().clearCache(fragmentEntry);
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
	public static List<FragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FragmentEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FragmentEntry update(FragmentEntry fragmentEntry) {
		return getPersistence().update(fragmentEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FragmentEntry update(FragmentEntry fragmentEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(fragmentEntry, serviceContext);
	}

	/**
	* Returns all the fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entries
	*/
	public static List<FragmentEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries
	*/
	public static List<FragmentEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByGroupId_First(long groupId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] findByGroupId_PrevAndNext(
		long fragmentEntryId, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(fragmentEntryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the fragment entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set of fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] filterFindByGroupId_PrevAndNext(
		long fragmentEntryId, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(fragmentEntryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the fragment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the fragment entries where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries
	*/
	public static List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId) {
		return getPersistence().findByFragmentCollectionId(fragmentCollectionId);
	}

	/**
	* Returns a range of all the fragment entries where fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end) {
		return getPersistence()
				   .findByFragmentCollectionId(fragmentCollectionId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByFragmentCollectionId(fragmentCollectionId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFragmentCollectionId(fragmentCollectionId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFragmentCollectionId_First(fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFragmentCollectionId_First(fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByFragmentCollectionId_Last(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFragmentCollectionId_Last(fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFragmentCollectionId_Last(fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] findByFragmentCollectionId_PrevAndNext(
		long fragmentEntryId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFragmentCollectionId_PrevAndNext(fragmentEntryId,
			fragmentCollectionId, orderByComparator);
	}

	/**
	* Removes all the fragment entries where fragmentCollectionId = &#63; from the database.
	*
	* @param fragmentCollectionId the fragment collection ID
	*/
	public static void removeByFragmentCollectionId(long fragmentCollectionId) {
		getPersistence().removeByFragmentCollectionId(fragmentCollectionId);
	}

	/**
	* Returns the number of fragment entries where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries
	*/
	public static int countByFragmentCollectionId(long fragmentCollectionId) {
		return getPersistence().countByFragmentCollectionId(fragmentCollectionId);
	}

	/**
	* Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId) {
		return getPersistence().findByG_FCI(groupId, fragmentCollectionId);
	}

	/**
	* Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end) {
		return getPersistence()
				   .findByG_FCI(groupId, fragmentCollectionId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByG_FCI(groupId, fragmentCollectionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_FCI(groupId, fragmentCollectionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByG_FCI_First(long groupId,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_First(groupId, fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FCI_First(long groupId,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_FCI_First(groupId, fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByG_FCI_Last(long groupId,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_Last(groupId, fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FCI_Last(long groupId,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_FCI_Last(groupId, fragmentCollectionId,
			orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] findByG_FCI_PrevAndNext(
		long fragmentEntryId, long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_PrevAndNext(fragmentEntryId, groupId,
			fragmentCollectionId, orderByComparator);
	}

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId) {
		return getPersistence().filterFindByG_FCI(groupId, fragmentCollectionId);
	}

	/**
	* Returns a range of all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end) {
		return getPersistence()
				   .filterFindByG_FCI(groupId, fragmentCollectionId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries that the user has permissions to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_FCI(groupId, fragmentCollectionId, start,
			end, orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] filterFindByG_FCI_PrevAndNext(
		long fragmentEntryId, long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_FCI_PrevAndNext(fragmentEntryId, groupId,
			fragmentCollectionId, orderByComparator);
	}

	/**
	* Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	*/
	public static void removeByG_FCI(long groupId, long fragmentCollectionId) {
		getPersistence().removeByG_FCI(groupId, fragmentCollectionId);
	}

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries
	*/
	public static int countByG_FCI(long groupId, long fragmentCollectionId) {
		return getPersistence().countByG_FCI(groupId, fragmentCollectionId);
	}

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public static int filterCountByG_FCI(long groupId, long fragmentCollectionId) {
		return getPersistence().filterCountByG_FCI(groupId, fragmentCollectionId);
	}

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByG_FEK(long groupId,
		java.lang.String fragmentEntryKey)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().findByG_FEK(groupId, fragmentEntryKey);
	}

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FEK(long groupId,
		java.lang.String fragmentEntryKey) {
		return getPersistence().fetchByG_FEK(groupId, fragmentEntryKey);
	}

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FEK(long groupId,
		java.lang.String fragmentEntryKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_FEK(groupId, fragmentEntryKey, retrieveFromCache);
	}

	/**
	* Removes the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the fragment entry that was removed
	*/
	public static FragmentEntry removeByG_FEK(long groupId,
		java.lang.String fragmentEntryKey)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().removeByG_FEK(groupId, fragmentEntryKey);
	}

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the number of matching fragment entries
	*/
	public static int countByG_FEK(long groupId,
		java.lang.String fragmentEntryKey) {
		return getPersistence().countByG_FEK(groupId, fragmentEntryKey);
	}

	/**
	* Returns all the fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @return the matching fragment entries
	*/
	public static List<FragmentEntry> findByFCI_S(long fragmentCollectionId,
		int status) {
		return getPersistence().findByFCI_S(fragmentCollectionId, status);
	}

	/**
	* Returns a range of all the fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFCI_S(long fragmentCollectionId,
		int status, int start, int end) {
		return getPersistence()
				   .findByFCI_S(fragmentCollectionId, status, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFCI_S(long fragmentCollectionId,
		int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByFCI_S(fragmentCollectionId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByFCI_S(long fragmentCollectionId,
		int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFCI_S(fragmentCollectionId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByFCI_S_First(long fragmentCollectionId,
		int status, OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFCI_S_First(fragmentCollectionId, status,
			orderByComparator);
	}

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByFCI_S_First(long fragmentCollectionId,
		int status, OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFCI_S_First(fragmentCollectionId, status,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByFCI_S_Last(long fragmentCollectionId,
		int status, OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFCI_S_Last(fragmentCollectionId, status,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByFCI_S_Last(long fragmentCollectionId,
		int status, OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFCI_S_Last(fragmentCollectionId, status,
			orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] findByFCI_S_PrevAndNext(
		long fragmentEntryId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFCI_S_PrevAndNext(fragmentEntryId,
			fragmentCollectionId, status, orderByComparator);
	}

	/**
	* Removes all the fragment entries where fragmentCollectionId = &#63; and status = &#63; from the database.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	*/
	public static void removeByFCI_S(long fragmentCollectionId, int status) {
		getPersistence().removeByFCI_S(fragmentCollectionId, status);
	}

	/**
	* Returns the number of fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @return the number of matching fragment entries
	*/
	public static int countByFCI_S(long fragmentCollectionId, int status) {
		return getPersistence().countByFCI_S(fragmentCollectionId, status);
	}

	/**
	* Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .findByG_FCI_LikeN(groupId, fragmentCollectionId, name);
	}

	/**
	* Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end) {
		return getPersistence()
				   .findByG_FCI_LikeN(groupId, fragmentCollectionId, name,
			start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .findByG_FCI_LikeN(groupId, fragmentCollectionId, name,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entries
	*/
	public static List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_FCI_LikeN(groupId, fragmentCollectionId, name,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByG_FCI_LikeN_First(long groupId,
		long fragmentCollectionId, java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_LikeN_First(groupId, fragmentCollectionId,
			name, orderByComparator);
	}

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FCI_LikeN_First(long groupId,
		long fragmentCollectionId, java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_FCI_LikeN_First(groupId, fragmentCollectionId,
			name, orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public static FragmentEntry findByG_FCI_LikeN_Last(long groupId,
		long fragmentCollectionId, java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_LikeN_Last(groupId, fragmentCollectionId, name,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public static FragmentEntry fetchByG_FCI_LikeN_Last(long groupId,
		long fragmentCollectionId, java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_FCI_LikeN_Last(groupId, fragmentCollectionId,
			name, orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] findByG_FCI_LikeN_PrevAndNext(
		long fragmentEntryId, long groupId, long fragmentCollectionId,
		java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .findByG_FCI_LikeN_PrevAndNext(fragmentEntryId, groupId,
			fragmentCollectionId, name, orderByComparator);
	}

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterFindByG_FCI_LikeN(groupId, fragmentCollectionId, name);
	}

	/**
	* Returns a range of all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end) {
		return getPersistence()
				   .filterFindByG_FCI_LikeN(groupId, fragmentCollectionId,
			name, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries that the user has permissions to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entries that the user has permission to view
	*/
	public static List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_FCI_LikeN(groupId, fragmentCollectionId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry[] filterFindByG_FCI_LikeN_PrevAndNext(
		long fragmentEntryId, long groupId, long fragmentCollectionId,
		java.lang.String name,
		OrderByComparator<FragmentEntry> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence()
				   .filterFindByG_FCI_LikeN_PrevAndNext(fragmentEntryId,
			groupId, fragmentCollectionId, name, orderByComparator);
	}

	/**
	* Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	*/
	public static void removeByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		getPersistence().removeByG_FCI_LikeN(groupId, fragmentCollectionId, name);
	}

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the number of matching fragment entries
	*/
	public static int countByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .countByG_FCI_LikeN(groupId, fragmentCollectionId, name);
	}

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public static int filterCountByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterCountByG_FCI_LikeN(groupId, fragmentCollectionId, name);
	}

	/**
	* Caches the fragment entry in the entity cache if it is enabled.
	*
	* @param fragmentEntry the fragment entry
	*/
	public static void cacheResult(FragmentEntry fragmentEntry) {
		getPersistence().cacheResult(fragmentEntry);
	}

	/**
	* Caches the fragment entries in the entity cache if it is enabled.
	*
	* @param fragmentEntries the fragment entries
	*/
	public static void cacheResult(List<FragmentEntry> fragmentEntries) {
		getPersistence().cacheResult(fragmentEntries);
	}

	/**
	* Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	*
	* @param fragmentEntryId the primary key for the new fragment entry
	* @return the new fragment entry
	*/
	public static FragmentEntry create(long fragmentEntryId) {
		return getPersistence().create(fragmentEntryId);
	}

	/**
	* Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry that was removed
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry remove(long fragmentEntryId)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().remove(fragmentEntryId);
	}

	public static FragmentEntry updateImpl(FragmentEntry fragmentEntry) {
		return getPersistence().updateImpl(fragmentEntry);
	}

	/**
	* Returns the fragment entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry findByPrimaryKey(long fragmentEntryId)
		throws com.liferay.fragment.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(fragmentEntryId);
	}

	/**
	* Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	*/
	public static FragmentEntry fetchByPrimaryKey(long fragmentEntryId) {
		return getPersistence().fetchByPrimaryKey(fragmentEntryId);
	}

	public static java.util.Map<java.io.Serializable, FragmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the fragment entries.
	*
	* @return the fragment entries
	*/
	public static List<FragmentEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of fragment entries
	*/
	public static List<FragmentEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of fragment entries
	*/
	public static List<FragmentEntry> findAll(int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of fragment entries
	*/
	public static List<FragmentEntry> findAll(int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the fragment entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of fragment entries.
	*
	* @return the number of fragment entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FragmentEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentEntryPersistence, FragmentEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryPersistence.class);

		ServiceTracker<FragmentEntryPersistence, FragmentEntryPersistence> serviceTracker =
			new ServiceTracker<FragmentEntryPersistence, FragmentEntryPersistence>(bundle.getBundleContext(),
				FragmentEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}