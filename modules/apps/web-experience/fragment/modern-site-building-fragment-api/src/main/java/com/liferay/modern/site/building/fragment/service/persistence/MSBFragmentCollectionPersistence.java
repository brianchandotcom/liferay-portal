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

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentCollectionException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the msb fragment collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentCollectionPersistenceImpl
 * @see MSBFragmentCollectionUtil
 * @generated
 */
@ProviderType
public interface MSBFragmentCollectionPersistence extends BasePersistence<MSBFragmentCollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MSBFragmentCollectionUtil} to access the msb fragment collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the msb fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment collections
	*/
	public java.util.List<MSBFragmentCollection> findByGroupId(long groupId);

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
	public java.util.List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

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
	public java.util.List<MSBFragmentCollection> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set where groupId = &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public MSBFragmentCollection[] findByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns all the msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment collections that the user has permission to view
	*/
	public java.util.List<MSBFragmentCollection> filterFindByGroupId(
		long groupId);

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
	public java.util.List<MSBFragmentCollection> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<MSBFragmentCollection> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

	/**
	* Returns the msb fragment collections before and after the current msb fragment collection in the ordered set of msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param msbFragmentCollectionId the primary key of the current msb fragment collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public MSBFragmentCollection[] filterFindByGroupId_PrevAndNext(
		long msbFragmentCollectionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Removes all the msb fragment collections where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of msb fragment collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment collections
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of msb fragment collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment collections that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection findByG_N(long groupId, java.lang.String name)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the msb fragment collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the msb fragment collection where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the msb fragment collection that was removed
	*/
	public MSBFragmentCollection removeByG_N(long groupId, java.lang.String name)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the number of msb fragment collections where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collections
	*/
	public java.util.List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name);

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
	public java.util.List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end);

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
	public java.util.List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

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
	public java.util.List<MSBFragmentCollection> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection findByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the first msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection
	* @throws NoSuchFragmentCollectionException if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection findByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the last msb fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment collection, or <code>null</code> if a matching msb fragment collection could not be found
	*/
	public MSBFragmentCollection fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

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
	public MSBFragmentCollection[] findByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns all the msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment collections that the user has permission to view
	*/
	public java.util.List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name);

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
	public java.util.List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

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
	public java.util.List<MSBFragmentCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

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
	public MSBFragmentCollection[] filterFindByG_LikeN_PrevAndNext(
		long msbFragmentCollectionId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws NoSuchFragmentCollectionException;

	/**
	* Removes all the msb fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of msb fragment collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections
	*/
	public int countByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of msb fragment collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment collections that the user has permission to view
	*/
	public int filterCountByG_LikeN(long groupId, java.lang.String name);

	/**
	* Caches the msb fragment collection in the entity cache if it is enabled.
	*
	* @param msbFragmentCollection the msb fragment collection
	*/
	public void cacheResult(MSBFragmentCollection msbFragmentCollection);

	/**
	* Caches the msb fragment collections in the entity cache if it is enabled.
	*
	* @param msbFragmentCollections the msb fragment collections
	*/
	public void cacheResult(
		java.util.List<MSBFragmentCollection> msbFragmentCollections);

	/**
	* Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	*
	* @param msbFragmentCollectionId the primary key for the new msb fragment collection
	* @return the new msb fragment collection
	*/
	public MSBFragmentCollection create(long msbFragmentCollectionId);

	/**
	* Removes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public MSBFragmentCollection remove(long msbFragmentCollectionId)
		throws NoSuchFragmentCollectionException;

	public MSBFragmentCollection updateImpl(
		MSBFragmentCollection msbFragmentCollection);

	/**
	* Returns the msb fragment collection with the primary key or throws a {@link NoSuchFragmentCollectionException} if it could not be found.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection
	* @throws NoSuchFragmentCollectionException if a msb fragment collection with the primary key could not be found
	*/
	public MSBFragmentCollection findByPrimaryKey(long msbFragmentCollectionId)
		throws NoSuchFragmentCollectionException;

	/**
	* Returns the msb fragment collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection, or <code>null</code> if a msb fragment collection with the primary key could not be found
	*/
	public MSBFragmentCollection fetchByPrimaryKey(long msbFragmentCollectionId);

	@Override
	public java.util.Map<java.io.Serializable, MSBFragmentCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the msb fragment collections.
	*
	* @return the msb fragment collections
	*/
	public java.util.List<MSBFragmentCollection> findAll();

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
	public java.util.List<MSBFragmentCollection> findAll(int start, int end);

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
	public java.util.List<MSBFragmentCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator);

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
	public java.util.List<MSBFragmentCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the msb fragment collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of msb fragment collections.
	*
	* @return the number of msb fragment collections
	*/
	public int countAll();
}