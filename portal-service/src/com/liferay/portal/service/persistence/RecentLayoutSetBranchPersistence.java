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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.RecentLayoutSetBranch;

/**
 * The persistence interface for the recent layout set branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.RecentLayoutSetBranchPersistenceImpl
 * @see RecentLayoutSetBranchUtil
 * @generated
 */
@ProviderType
public interface RecentLayoutSetBranchPersistence extends BasePersistence<RecentLayoutSetBranch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RecentLayoutSetBranchUtil} to access the recent layout set branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the recent layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByGroupId(long groupId);

	/**
	* Returns a range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where groupId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch[] findByGroupId_PrevAndNext(
		long recentLayoutSetBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Removes all the recent layout set branchs where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of recent layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching recent layout set branchs
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the recent layout set branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByUserId(long userId);

	/**
	* Returns a range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the first recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the last recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the last recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where userId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch[] findByUserId_PrevAndNext(
		long recentLayoutSetBranchId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Removes all the recent layout set branchs where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of recent layout set branchs where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching recent layout set branchs
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId);

	/**
	* Returns a range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end);

	/**
	* Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch[] findByLayoutSetBranchId_PrevAndNext(
		long recentLayoutSetBranchId, long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Removes all the recent layout set branchs where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	*/
	public void removeByLayoutSetBranchId(long layoutSetBranchId);

	/**
	* Returns the number of recent layout set branchs where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching recent layout set branchs
	*/
	public int countByLayoutSetBranchId(long layoutSetBranchId);

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the matching recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch findByU_L(long userId, long layoutSetId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByU_L(long userId, long layoutSetId);

	/**
	* Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	*/
	public RecentLayoutSetBranch fetchByU_L(long userId, long layoutSetId,
		boolean retrieveFromCache);

	/**
	* Removes the recent layout set branch where userId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the recent layout set branch that was removed
	*/
	public RecentLayoutSetBranch removeByU_L(long userId, long layoutSetId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the number of recent layout set branchs where userId = &#63; and layoutSetId = &#63;.
	*
	* @param userId the user ID
	* @param layoutSetId the layout set ID
	* @return the number of matching recent layout set branchs
	*/
	public int countByU_L(long userId, long layoutSetId);

	/**
	* Caches the recent layout set branch in the entity cache if it is enabled.
	*
	* @param recentLayoutSetBranch the recent layout set branch
	*/
	public void cacheResult(RecentLayoutSetBranch recentLayoutSetBranch);

	/**
	* Caches the recent layout set branchs in the entity cache if it is enabled.
	*
	* @param recentLayoutSetBranchs the recent layout set branchs
	*/
	public void cacheResult(
		java.util.List<RecentLayoutSetBranch> recentLayoutSetBranchs);

	/**
	* Creates a new recent layout set branch with the primary key. Does not add the recent layout set branch to the database.
	*
	* @param recentLayoutSetBranchId the primary key for the new recent layout set branch
	* @return the new recent layout set branch
	*/
	public RecentLayoutSetBranch create(long recentLayoutSetBranchId);

	/**
	* Removes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch that was removed
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch remove(long recentLayoutSetBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	public RecentLayoutSetBranch updateImpl(
		RecentLayoutSetBranch recentLayoutSetBranch);

	/**
	* Returns the recent layout set branch with the primary key or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch
	* @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch findByPrimaryKey(long recentLayoutSetBranchId)
		throws com.liferay.portal.NoSuchRecentLayoutSetBranchException;

	/**
	* Returns the recent layout set branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recentLayoutSetBranchId the primary key of the recent layout set branch
	* @return the recent layout set branch, or <code>null</code> if a recent layout set branch with the primary key could not be found
	*/
	public RecentLayoutSetBranch fetchByPrimaryKey(long recentLayoutSetBranchId);

	@Override
	public java.util.Map<java.io.Serializable, RecentLayoutSetBranch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the recent layout set branchs.
	*
	* @return the recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findAll();

	/**
	* Returns a range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @return the range of recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findAll(int start, int end);

	/**
	* Returns an ordered range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout set branchs
	* @param end the upper bound of the range of recent layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recent layout set branchs
	*/
	public java.util.List<RecentLayoutSetBranch> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the recent layout set branchs from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of recent layout set branchs.
	*
	* @return the number of recent layout set branchs
	*/
	public int countAll();
}