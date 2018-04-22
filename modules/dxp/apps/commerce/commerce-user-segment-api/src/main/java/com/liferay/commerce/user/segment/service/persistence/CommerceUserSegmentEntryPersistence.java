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

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce user segment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentEntryPersistenceImpl
 * @see CommerceUserSegmentEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceUserSegmentEntryPersistence extends BasePersistence<CommerceUserSegmentEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserSegmentEntryUtil} to access the commerce user segment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce user segment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce user segment entries
	*/
	public java.util.List<CommerceUserSegmentEntry> findByGroupId(long groupId);

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
	public java.util.List<CommerceUserSegmentEntry> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<CommerceUserSegmentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

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
	public java.util.List<CommerceUserSegmentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set where groupId = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public CommerceUserSegmentEntry[] findByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns all the commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce user segment entries that the user has permission to view
	*/
	public java.util.List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId);

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
	public java.util.List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<CommerceUserSegmentEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

	/**
	* Returns the commerce user segment entries before and after the current commerce user segment entry in the ordered set of commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param commerceUserSegmentEntryId the primary key of the current commerce user segment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public CommerceUserSegmentEntry[] filterFindByGroupId_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Removes all the commerce user segment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce user segment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce user segment entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce user segment entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce user segment entries
	*/
	public java.util.List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active);

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
	public java.util.List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end);

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
	public java.util.List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

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
	public java.util.List<CommerceUserSegmentEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry findByG_A_First(long groupId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns the first commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByG_A_First(long groupId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry findByG_A_Last(long groupId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns the last commerce user segment entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByG_A_Last(long groupId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

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
	public CommerceUserSegmentEntry[] findByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns all the commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce user segment entries that the user has permission to view
	*/
	public java.util.List<CommerceUserSegmentEntry> filterFindByG_A(
		long groupId, boolean active);

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
	public java.util.List<CommerceUserSegmentEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end);

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
	public java.util.List<CommerceUserSegmentEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

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
	public CommerceUserSegmentEntry[] filterFindByG_A_PrevAndNext(
		long commerceUserSegmentEntryId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws NoSuchUserSegmentEntryException;

	/**
	* Removes all the commerce user segment entries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce user segment entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce user segment entries
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce user segment entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce user segment entries that the user has permission to view
	*/
	public int filterCountByG_A(long groupId, boolean active);

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or throws a {@link NoSuchUserSegmentEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry findByG_K(long groupId, java.lang.String key)
		throws NoSuchUserSegmentEntryException;

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByG_K(long groupId,
		java.lang.String key);

	/**
	* Returns the commerce user segment entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce user segment entry, or <code>null</code> if a matching commerce user segment entry could not be found
	*/
	public CommerceUserSegmentEntry fetchByG_K(long groupId,
		java.lang.String key, boolean retrieveFromCache);

	/**
	* Removes the commerce user segment entry where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the commerce user segment entry that was removed
	*/
	public CommerceUserSegmentEntry removeByG_K(long groupId,
		java.lang.String key) throws NoSuchUserSegmentEntryException;

	/**
	* Returns the number of commerce user segment entries where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching commerce user segment entries
	*/
	public int countByG_K(long groupId, java.lang.String key);

	/**
	* Caches the commerce user segment entry in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	*/
	public void cacheResult(CommerceUserSegmentEntry commerceUserSegmentEntry);

	/**
	* Caches the commerce user segment entries in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentEntries the commerce user segment entries
	*/
	public void cacheResult(
		java.util.List<CommerceUserSegmentEntry> commerceUserSegmentEntries);

	/**
	* Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	*
	* @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	* @return the new commerce user segment entry
	*/
	public CommerceUserSegmentEntry create(long commerceUserSegmentEntryId);

	/**
	* Removes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public CommerceUserSegmentEntry remove(long commerceUserSegmentEntryId)
		throws NoSuchUserSegmentEntryException;

	public CommerceUserSegmentEntry updateImpl(
		CommerceUserSegmentEntry commerceUserSegmentEntry);

	/**
	* Returns the commerce user segment entry with the primary key or throws a {@link NoSuchUserSegmentEntryException} if it could not be found.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry
	* @throws NoSuchUserSegmentEntryException if a commerce user segment entry with the primary key could not be found
	*/
	public CommerceUserSegmentEntry findByPrimaryKey(
		long commerceUserSegmentEntryId) throws NoSuchUserSegmentEntryException;

	/**
	* Returns the commerce user segment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry, or <code>null</code> if a commerce user segment entry with the primary key could not be found
	*/
	public CommerceUserSegmentEntry fetchByPrimaryKey(
		long commerceUserSegmentEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceUserSegmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce user segment entries.
	*
	* @return the commerce user segment entries
	*/
	public java.util.List<CommerceUserSegmentEntry> findAll();

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
	public java.util.List<CommerceUserSegmentEntry> findAll(int start, int end);

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
	public java.util.List<CommerceUserSegmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

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
	public java.util.List<CommerceUserSegmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce user segment entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce user segment entries.
	*
	* @return the number of commerce user segment entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}