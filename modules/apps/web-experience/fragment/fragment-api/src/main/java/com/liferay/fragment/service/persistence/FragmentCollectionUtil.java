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

import com.liferay.fragment.model.FragmentCollection;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the fragment collection service. This utility wraps {@link com.liferay.fragment.service.persistence.impl.FragmentCollectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionPersistence
 * @see com.liferay.fragment.service.persistence.impl.FragmentCollectionPersistenceImpl
 * @generated
 */
@ProviderType
public class FragmentCollectionUtil {
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
	public static void clearCache(FragmentCollection fragmentCollection) {
		getPersistence().clearCache(fragmentCollection);
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
	public static List<FragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FragmentCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FragmentCollection update(
		FragmentCollection fragmentCollection) {
		return getPersistence().update(fragmentCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FragmentCollection update(
		FragmentCollection fragmentCollection, ServiceContext serviceContext) {
		return getPersistence().update(fragmentCollection, serviceContext);
	}

	/**
	* Returns all the fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment collections
	*/
	public static List<FragmentCollection> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of matching fragment collections
	*/
	public static List<FragmentCollection> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment collections
	*/
	public static List<FragmentCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment collections
	*/
	public static List<FragmentCollection> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment collection
	* @throws NoSuchCollectionException if a matching fragment collection could not be found
	*/
	public static FragmentCollection findByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment collection
	* @throws NoSuchCollectionException if a matching fragment collection could not be found
	*/
	public static FragmentCollection findByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63;.
	*
	* @param fragmentCollectionId the primary key of the current fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment collection
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection[] findByGroupId_PrevAndNext(
		long fragmentCollectionId, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(fragmentCollectionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the fragment collections that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment collections that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the fragment collections before and after the current fragment collection in the ordered set of fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param fragmentCollectionId the primary key of the current fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment collection
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection[] filterFindByGroupId_PrevAndNext(
		long fragmentCollectionId, long groupId,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(fragmentCollectionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the fragment collections where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment collections
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment collections that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or throws a {@link NoSuchCollectionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fragmentCollectionKey the fragment collection key
	* @return the matching fragment collection
	* @throws NoSuchCollectionException if a matching fragment collection could not be found
	*/
	public static FragmentCollection findByG_FCK(long groupId,
		java.lang.String fragmentCollectionKey)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().findByG_FCK(groupId, fragmentCollectionKey);
	}

	/**
	* Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentCollectionKey the fragment collection key
	* @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByG_FCK(long groupId,
		java.lang.String fragmentCollectionKey) {
		return getPersistence().fetchByG_FCK(groupId, fragmentCollectionKey);
	}

	/**
	* Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentCollectionKey the fragment collection key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByG_FCK(long groupId,
		java.lang.String fragmentCollectionKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_FCK(groupId, fragmentCollectionKey,
			retrieveFromCache);
	}

	/**
	* Removes the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentCollectionKey the fragment collection key
	* @return the fragment collection that was removed
	*/
	public static FragmentCollection removeByG_FCK(long groupId,
		java.lang.String fragmentCollectionKey)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().removeByG_FCK(groupId, fragmentCollectionKey);
	}

	/**
	* Returns the number of fragment collections where groupId = &#63; and fragmentCollectionKey = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionKey the fragment collection key
	* @return the number of matching fragment collections
	*/
	public static int countByG_FCK(long groupId,
		java.lang.String fragmentCollectionKey) {
		return getPersistence().countByG_FCK(groupId, fragmentCollectionKey);
	}

	/**
	* Returns all the fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching fragment collections
	*/
	public static List<FragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of matching fragment collections
	*/
	public static List<FragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment collections
	*/
	public static List<FragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment collections
	*/
	public static List<FragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment collection
	* @throws NoSuchCollectionException if a matching fragment collection could not be found
	*/
	public static FragmentCollection findByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .findByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment collection
	* @throws NoSuchCollectionException if a matching fragment collection could not be found
	*/
	public static FragmentCollection findByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .findByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static FragmentCollection fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param fragmentCollectionId the primary key of the current fragment collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment collection
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection[] findByG_LikeN_PrevAndNext(
		long fragmentCollectionId, long groupId, java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .findByG_LikeN_PrevAndNext(fragmentCollectionId, groupId,
			name, orderByComparator);
	}

	/**
	* Returns all the fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().filterFindByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the fragment collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment collections that the user has permission to view
	*/
	public static List<FragmentCollection> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the fragment collections before and after the current fragment collection in the ordered set of fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param fragmentCollectionId the primary key of the current fragment collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment collection
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection[] filterFindByG_LikeN_PrevAndNext(
		long fragmentCollectionId, long groupId, java.lang.String name,
		OrderByComparator<FragmentCollection> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence()
				   .filterFindByG_LikeN_PrevAndNext(fragmentCollectionId,
			groupId, name, orderByComparator);
	}

	/**
	* Removes all the fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByG_LikeN(long groupId, java.lang.String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching fragment collections
	*/
	public static int countByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching fragment collections that the user has permission to view
	*/
	public static int filterCountByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().filterCountByG_LikeN(groupId, name);
	}

	/**
	* Caches the fragment collection in the entity cache if it is enabled.
	*
	* @param fragmentCollection the fragment collection
	*/
	public static void cacheResult(FragmentCollection fragmentCollection) {
		getPersistence().cacheResult(fragmentCollection);
	}

	/**
	* Caches the fragment collections in the entity cache if it is enabled.
	*
	* @param fragmentCollections the fragment collections
	*/
	public static void cacheResult(List<FragmentCollection> fragmentCollections) {
		getPersistence().cacheResult(fragmentCollections);
	}

	/**
	* Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	*
	* @param fragmentCollectionId the primary key for the new fragment collection
	* @return the new fragment collection
	*/
	public static FragmentCollection create(long fragmentCollectionId) {
		return getPersistence().create(fragmentCollectionId);
	}

	/**
	* Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection that was removed
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection remove(long fragmentCollectionId)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().remove(fragmentCollectionId);
	}

	public static FragmentCollection updateImpl(
		FragmentCollection fragmentCollection) {
		return getPersistence().updateImpl(fragmentCollection);
	}

	/**
	* Returns the fragment collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection
	* @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection findByPrimaryKey(long fragmentCollectionId)
		throws com.liferay.fragment.exception.NoSuchCollectionException {
		return getPersistence().findByPrimaryKey(fragmentCollectionId);
	}

	/**
	* Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	*/
	public static FragmentCollection fetchByPrimaryKey(
		long fragmentCollectionId) {
		return getPersistence().fetchByPrimaryKey(fragmentCollectionId);
	}

	public static java.util.Map<java.io.Serializable, FragmentCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the fragment collections.
	*
	* @return the fragment collections
	*/
	public static List<FragmentCollection> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of fragment collections
	*/
	public static List<FragmentCollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of fragment collections
	*/
	public static List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of fragment collections
	*/
	public static List<FragmentCollection> findAll(int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the fragment collections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of fragment collections.
	*
	* @return the number of fragment collections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FragmentCollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentCollectionPersistence, FragmentCollectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentCollectionPersistence.class);

		ServiceTracker<FragmentCollectionPersistence, FragmentCollectionPersistence> serviceTracker =
			new ServiceTracker<FragmentCollectionPersistence, FragmentCollectionPersistence>(bundle.getBundleContext(),
				FragmentCollectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}