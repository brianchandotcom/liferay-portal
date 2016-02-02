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

import com.liferay.portal.exception.NoSuchRecentLayoutRevisionException;
import com.liferay.portal.model.RecentLayoutRevision;

/**
 * The persistence interface for the recent layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.RecentLayoutRevisionPersistenceImpl
 * @see RecentLayoutRevisionUtil
 * @generated
 */
@ProviderType
public interface RecentLayoutRevisionPersistence extends BasePersistence<RecentLayoutRevision> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RecentLayoutRevisionUtil} to access the recent layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the recent layout revisions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByGroupId(long groupId);

	/**
	* Returns a range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the first recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the last recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the last recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where groupId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision[] findByGroupId_PrevAndNext(
		long recentLayoutRevisionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Removes all the recent layout revisions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of recent layout revisions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching recent layout revisions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the recent layout revisions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByUserId(long userId);

	/**
	* Returns a range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout revisions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the first recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the last recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the last recent layout revision in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where userId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision[] findByUserId_PrevAndNext(
		long recentLayoutRevisionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Removes all the recent layout revisions where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of recent layout revisions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching recent layout revisions
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @return the matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId);

	/**
	* Returns a range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end);

	/**
	* Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutRevisionId the layout revision ID
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByLayoutRevisionId_First(
		long layoutRevisionId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByLayoutRevisionId_First(
		long layoutRevisionId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByLayoutRevisionId_Last(
		long layoutRevisionId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByLayoutRevisionId_Last(
		long layoutRevisionId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns the recent layout revisions before and after the current recent layout revision in the ordered set where layoutRevisionId = &#63;.
	*
	* @param recentLayoutRevisionId the primary key of the current recent layout revision
	* @param layoutRevisionId the layout revision ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision[] findByLayoutRevisionId_PrevAndNext(
		long recentLayoutRevisionId, long layoutRevisionId,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Removes all the recent layout revisions where layoutRevisionId = &#63; from the database.
	*
	* @param layoutRevisionId the layout revision ID
	*/
	public void removeByLayoutRevisionId(long layoutRevisionId);

	/**
	* Returns the number of recent layout revisions where layoutRevisionId = &#63;.
	*
	* @param layoutRevisionId the layout revision ID
	* @return the number of matching recent layout revisions
	*/
	public int countByLayoutRevisionId(long layoutRevisionId);

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision findByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid);

	/**
	* Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	*/
	public RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid, boolean retrieveFromCache);

	/**
	* Removes the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the recent layout revision that was removed
	*/
	public RecentLayoutRevision removeByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the number of recent layout revisions where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param userId the user ID
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching recent layout revisions
	*/
	public int countByU_L_P(long userId, long layoutSetBranchId, long plid);

	/**
	* Caches the recent layout revision in the entity cache if it is enabled.
	*
	* @param recentLayoutRevision the recent layout revision
	*/
	public void cacheResult(RecentLayoutRevision recentLayoutRevision);

	/**
	* Caches the recent layout revisions in the entity cache if it is enabled.
	*
	* @param recentLayoutRevisions the recent layout revisions
	*/
	public void cacheResult(
		java.util.List<RecentLayoutRevision> recentLayoutRevisions);

	/**
	* Creates a new recent layout revision with the primary key. Does not add the recent layout revision to the database.
	*
	* @param recentLayoutRevisionId the primary key for the new recent layout revision
	* @return the new recent layout revision
	*/
	public RecentLayoutRevision create(long recentLayoutRevisionId);

	/**
	* Removes the recent layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision that was removed
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision remove(long recentLayoutRevisionId)
		throws NoSuchRecentLayoutRevisionException;

	public RecentLayoutRevision updateImpl(
		RecentLayoutRevision recentLayoutRevision);

	/**
	* Returns the recent layout revision with the primary key or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision
	* @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision findByPrimaryKey(long recentLayoutRevisionId)
		throws NoSuchRecentLayoutRevisionException;

	/**
	* Returns the recent layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recentLayoutRevisionId the primary key of the recent layout revision
	* @return the recent layout revision, or <code>null</code> if a recent layout revision with the primary key could not be found
	*/
	public RecentLayoutRevision fetchByPrimaryKey(long recentLayoutRevisionId);

	@Override
	public java.util.Map<java.io.Serializable, RecentLayoutRevision> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the recent layout revisions.
	*
	* @return the recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findAll();

	/**
	* Returns a range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @return the range of recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findAll(int start, int end);

	/**
	* Returns an ordered range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the recent layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recent layout revisions
	* @param end the upper bound of the range of recent layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recent layout revisions
	*/
	public java.util.List<RecentLayoutRevision> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the recent layout revisions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of recent layout revisions.
	*
	* @return the number of recent layout revisions
	*/
	public int countAll();
}