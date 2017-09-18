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

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the msb fragment collection service. This utility wraps {@link com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentCollectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionPersistence
 * @see com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentCollectionPersistenceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionUtil {
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
	public static void clearCache(MSBFragmentCollection msbFragmentCollection) {
		getPersistence().clearCache(msbFragmentCollection);
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
	public static List<MSBFragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MSBFragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MSBFragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MSBFragmentCollection update(
		MSBFragmentCollection msbFragmentCollection) {
		return getPersistence().update(msbFragmentCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MSBFragmentCollection update(
		MSBFragmentCollection msbFragmentCollection,
		ServiceContext serviceContext) {
		return getPersistence().update(msbFragmentCollection, serviceContext);
	}

	/**
	* Returns all the msb fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the msb fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection findByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByGroupId_First(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection findByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection[] findByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(msbFragmentCollectionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment collections that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set of msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection[] filterFindByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(msbFragmentCollectionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the msb fragment collections where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of msb fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment collections
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment collections that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the msb fragment collection where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the msb fragment collection that was removed
	*/
	public static MSBFragmentCollection removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of msb fragment collections where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching msb fragment collections
	*/
	public static List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection findByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .findByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection findByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .findByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public static MSBFragmentCollection fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection[] findByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .findByG_LikeN_PrevAndNext(msbFragmentCollectionId, groupId,
			name, orderByComparator);
	}

	/**
	* Returns all the msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name) {
		return getPersistence().filterFindByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching msb fragment collections that the user has permission to view
	*/
	public static List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set of msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection[] filterFindByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, java.lang.String name,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence()
				   .filterFindByG_LikeN_PrevAndNext(msbFragmentCollectionId,
			groupId, name, orderByComparator);
	}

	/**
	* Removes all the msb fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByG_LikeN(long groupId, java.lang.String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections
	*/
	public static int countByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections that the user has permission to view
	*/
	public static int filterCountByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().filterCountByG_LikeN(groupId, name);
	}

	/**
	* Caches the msb fragment collection in the entity cache if it is enabled.
	*
	* @param msbFragmentCollection the msb fragment collection
	*/
	public static void cacheResult(MSBFragmentCollection msbFragmentCollection) {
		getPersistence().cacheResult(msbFragmentCollection);
	}

	/**
	* Caches the msb fragment collections in the entity cache if it is enabled.
	*
	* @param msbFragmentCollections the msb fragment collections
	*/
	public static void cacheResult(
		List<MSBFragmentCollection> msbFragmentCollections) {
		getPersistence().cacheResult(msbFragmentCollections);
	}

	/**
	* Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	*
	* @param msbFragmentCollectionId the primary key for the new msb fragment collection
	* @return the new msb fragment collection
	*/
	public static MSBFragmentCollection create(long msbFragmentCollectionId) {
		return getPersistence().create(msbFragmentCollectionId);
	}

	/**
	* Removes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection remove(long msbFragmentCollectionId)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().remove(msbFragmentCollectionId);
	}

	public static MSBFragmentCollection updateImpl(
		MSBFragmentCollection msbFragmentCollection) {
		return getPersistence().updateImpl(msbFragmentCollection);
	}

	/**
	* Returns the msb fragment collection with the primary key or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection findByPrimaryKey(
		long msbFragmentCollectionId)
		throws com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException {
		return getPersistence().findByPrimaryKey(msbFragmentCollectionId);
	}

	/**
	* Returns the msb fragment collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection, or <code>null</code> if a msb fragment collection with the primary key could not be found
	*/
	public static MSBFragmentCollection fetchByPrimaryKey(
		long msbFragmentCollectionId) {
		return getPersistence().fetchByPrimaryKey(msbFragmentCollectionId);
	}

	public static java.util.Map<java.io.Serializable, MSBFragmentCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the msb fragment collections.
	*
	* @return the msb fragment collections
	*/
	public static List<MSBFragmentCollection> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the msb fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of msb fragment collections
	*/
	public static List<MSBFragmentCollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the msb fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of msb fragment collections
	*/
	public static List<MSBFragmentCollection> findAll(int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the msb fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of msb fragment collections
	*/
	public static List<MSBFragmentCollection> findAll(int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the msb fragment collections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of msb fragment collections.
	*
	* @return the number of msb fragment collections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MSBFragmentCollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentCollectionPersistence, MSBFragmentCollectionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentCollectionPersistence.class);
}